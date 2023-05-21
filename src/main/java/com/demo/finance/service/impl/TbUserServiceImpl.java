package com.demo.finance.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.finance.dao.TbUserMapper;
import com.demo.finance.enums.userRole;
import com.demo.finance.pojo.TbUser;
import com.demo.finance.pojo.qo.UserPageQO;
import com.demo.finance.pojo.vo.TbUserVO;
import com.demo.finance.pojo.vo.UserSelectVO;
import com.demo.finance.service.TbUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @file: TbUserServiceImpl
 * @version: 1.0.0
 * @Description:
 * 用户信息服务实现类
 * TbUserServiceImpl是TbUserService接口的实现类，提供了用户信息相关的服务。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {


    /**
     * 检查用户是否存在
     *
     * @param userName 用户名
     * @return Boolean 存在返回true，不存在返回false
     */
    @Override
    public Boolean checkUserExist(String userName) {
        return !(null == this.baseMapper.selectOneByUsername(userName));
    }

    /**
     * 获取用户选择列表
     *
     * @return List<UserSelectVO> 用户选择列表
     */
    @Override
    public List<UserSelectVO> getUserSelectList() {
        return this.baseMapper.getUserSelectList();
    }

    /**
     * 分页查询用户信息
     *
     * @param page        分页对象
     * @param userPageQO  查询条件对象
     * @return Page<TbUserVO> 分页查询结果
     */
    @Override
    public Page<TbUserVO> selectPage(Page<TbUserVO> page, UserPageQO userPageQO) {
        return this.baseMapper.queryPage(page,userPageQO);
    }

    /**
     * 根据登录名获取用户ID
     *
     * @param loginName 登录名
     * @return Integer 用户ID
     */
    @Override
    public Integer getUserIdByLoginName(String loginName) {
        return this.baseMapper.getUserIdByLoginName(loginName);
    }

    /**
     * 下载用户信息
     *
     * @return List<TbUserVO> 用户信息列表
     */
    @Override
    public List<TbUserVO> download() {
        return this.baseMapper.downloadByType(userRole.COMMON.getCode());
    }
}
