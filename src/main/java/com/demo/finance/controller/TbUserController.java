
package com.demo.finance.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.finance.aop.LogParams.LogParams;
import com.demo.finance.config.exception.AppExceptionCodeMsg;
import com.demo.finance.config.result.Resp;
import com.demo.finance.pojo.TbUser;
import com.demo.finance.pojo.qo.UserInsertQO;
import com.demo.finance.pojo.qo.UserPageQO;
import com.demo.finance.pojo.qo.UserUpdateQO;
import com.demo.finance.pojo.vo.TbUserPageVO;
import com.demo.finance.pojo.vo.TbUserVO;
import com.demo.finance.pojo.vo.UserSelectVO;
import com.demo.finance.service.TbFinanceInfoService;
import com.demo.finance.service.TbUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @file:TbUserController
 * @version:1.0.0
 * @Description:
 * 人员接口控制器，编写人员相关接口
 * @Author: XU WEI
 * @Date:  2023-5-10
 */
@RestController
@RequestMapping("/user")
public class TbUserController {

    @Resource
    TbUserService tbUserService;
    @Resource
    TbFinanceInfoService tbFinanceInfoService;

    @Value("${DEFAULT_PASSWORD}")
    private String DEFAULT_PASSWORD;
    @Value("${DEFAULT_USER_TYPE}")
    private Integer DEFAULT_USER_TYPE;

    /**
     * 用户注册
     * 该方法用于用户注册操作。在注册之前，会进行一系列操作，包括检查用户名是否已存在、用户数据的处理等。
     * @param userInsertQO 用户注册请求对象，包含用户的登录名、密码等信息。
     * @return 返回操作结果的响应对象。如果注册成功，返回注册成功的响应；如果用户名已存在，返回包含重复用户错误信息的响应；否则，返回包含错误信息的响应。
     */
    @PostMapping ("/register")
    @LogParams
    public Resp<?> register(@RequestBody @Validated UserInsertQO userInsertQO){
        if (tbUserService.checkUserExist(userInsertQO.getLoginName())){
            return Resp.error(AppExceptionCodeMsg.DUPLICATE_USER);
        }else {
            TbUser tbUser = new TbUser();
            BeanUtils.copyProperties(userInsertQO,tbUser);
            if (!StringUtils.hasText(tbUser.getPassword())) tbUser.setPassword(DEFAULT_PASSWORD);
            tbUser.setType(DEFAULT_USER_TYPE);
            tbUserService.save(tbUser);
            return Resp.success("注册成功！",null);
        }
    }

    /**
     * 更新用户信息接口
     * 该方法用于更新用户信息。在更新之前，会进行一系列操作，包括检查用户名是否已存在、用户数据的处理等。
     * @param userUpdateQO 用户更新信息的请求对象，包含用户的登录名、密码等信息。
     * @return 返回更新结果的响应对象。如果更新成功，返回更新成功的响应；如果用户名已存在且满足特定条件，返回包含重复用户错误信息的响应；否则，返回包含错误信息的响应。
     */
    @PostMapping ("/updateUser")
    @LogParams
    public Resp<?> updateUser(@RequestBody @Validated UserUpdateQO userUpdateQO){
        if (tbUserService.checkUserExist(userUpdateQO.getLoginName())&&isaBoolean(userUpdateQO)){
                return Resp.error(AppExceptionCodeMsg.DUPLICATE_USER);
        }else {
            TbUser tbUser = new TbUser();
            BeanUtils.copyProperties(userUpdateQO,tbUser);
            if (!StringUtils.hasText(tbUser.getPassword())) tbUser.setPassword(null);
            tbUser.setType(DEFAULT_USER_TYPE);
            tbUserService.updateById(tbUser);
            return Resp.success("更新成功！",null);
        }
    }


    private boolean isaBoolean(UserUpdateQO userUpdateQO) {
        return !userUpdateQO.getId().equals(tbUserService.getUserIdByLoginName(userUpdateQO.getLoginName()));
    }


    /**
     * 获取用户选择列表接口
     * 该方法用于获取用户选择列表，返回可供选择的用户信息列表。
     @return 返回包含用户选择列表的响应对象。如果获取成功，返回包含用户选择列表的成功响应；否则，返回包含错误信息的响应。
     */
    @GetMapping("/getUserSelectList")
    @LogParams
    public Resp<?> getUserSelectList() {
        List<UserSelectVO> userSelectVOList=tbUserService.getUserSelectList();
        return Resp.success(userSelectVOList);
    }


    /**
     * 用户分页查询接口
     * 该方法用于进行用户信息的分页查询操作。根据给定的查询条件，返回符合条件的用户信息列表。
     * @param userPageQO 用户分页查询的请求对象，包含当前页码、每页记录数等信息。
     * @return 返回包含用户分页查询结果的响应对象。如果查询成功，返回包含用户分页查询结果的成功响应；否则，返回包含错误信息的响应。
     */
    @PostMapping("/page")
    @LogParams
    public Resp<?> userPage(@RequestBody @Validated UserPageQO userPageQO){
        Page<TbUserVO> page = new Page<>(userPageQO.getCurrentPage(), userPageQO.getPageSize());
        Page<TbUserVO> TbUserPage =tbUserService.selectPage(page, userPageQO);
        return Resp.success(new TbUserPageVO(TbUserPage.getTotal(),TbUserPage.getPages(),TbUserPage.getRecords()));
    }

    /**
     * 用户信息下载接口
     * 该方法用于将用户信息以Excel文件的形式进行下载。将用户信息列表写入Excel文件，并将文件返回给客户端进行下载。
     * @param response HTTP响应对象，用于设置下载文件的相关信息。
     * @throws IOException 如果在文件写入过程中发生IO异常，则抛出该异常。
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        List<TbUserVO> tbUserVOList=tbUserService.download();
        OutputStream os = null;
        try {
            String fileName = URLEncoder.encode("用户表","UTF-8");
            os = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xls");
            response.addHeader("Access-Control-Expose-Headers", "Content-disposition");
            EasyExcel.write(os, TbUserVO.class).sheet("用户表").doWrite(tbUserVOList);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 删除用户接口
     * 该方法用于删除指定用户及其相关的财务信息。根据给定的用户更新信息中的用户ID，删除对应的用户记录以及与该用户相关的财务信息记录。
     * @param userUpdateQO 用户更新信息，包含要删除的用户的ID。
     * @return 返回删除操作的结果响应。
     */
    @PostMapping("/delete")
    @LogParams
    @Transactional
    public Resp<?> userDelete(@RequestBody @Validated UserUpdateQO userUpdateQO){
        Integer id=userUpdateQO.getId();
        this.tbUserService.removeById(id);
        this.tbFinanceInfoService.deleteRecordByUserId(id);
        return Resp.success("删除成功！",null);
    }
}
