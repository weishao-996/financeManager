
package com.demo.finance.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.finance.aop.LogParams.LogParams;
import com.demo.finance.aop.userInfo.InitUser;
import com.demo.finance.config.result.Resp;
import com.demo.finance.enums.UpdateStatus;
import com.demo.finance.pojo.TbFinanceInfo;
import com.demo.finance.pojo.qo.CompareQO;
import com.demo.finance.pojo.qo.TbFinanceInfoInsertQO;
import com.demo.finance.pojo.qo.TbFinanceInfoPageQO;
import com.demo.finance.pojo.qo.TbFinanceInfoUpdateQO;
import com.demo.finance.pojo.vo.TbFinanceCompareVO;
import com.demo.finance.pojo.vo.TbFinanceHistoryVO;
import com.demo.finance.pojo.vo.TbFinanceInfoPageVO;
import com.demo.finance.pojo.vo.TbFinanceInfoVO;
import com.demo.finance.service.TbFinanceInfoService;
import com.demo.finance.utils.UserContextUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @file:TbFinanceInfoController
 * @version:1.0.0
 * @Description:
 * 经费接口控制器，编写经费相关接口
 * @Author: XU WEI
 * @Date:  2023-5-10
 */
@RestController
@RequestMapping("/finance")
public class TbFinanceInfoController {
    @Resource
    TbFinanceInfoService tbFinanceInfoService;

    private final Object lock = new Object();


    /**
     * 插入财务信息
     * 该方法用于插入一条新的财务信息记录。在插入之前，会进行一系列操作，包括数据复制、余额计算、记录创建等。同时，通过加锁确保线程安全。
     * @param tbFinanceInfoInsertQO 财务信息的插入请求对象，包含需要插入的财务信息的相关字段和数据。
     * @return 返回操作结果的响应对象。如果插入成功，返回成功的响应；否则，返回包含错误信息的响应。
     */
    @PostMapping("/insert")
    @InitUser
    @LogParams
    @Transactional
    public Resp<?> financeInfoInsert(@RequestBody @Validated TbFinanceInfoInsertQO tbFinanceInfoInsertQO){
        synchronized (lock){
            //数据转换
            TbFinanceInfo tbFinanceInfo=tbFinanceInfoService.convertInsertQOtoPOJO(tbFinanceInfoInsertQO);
            //插入新数据,并返回住建
            tbFinanceInfo = tbFinanceInfoService.saveNewRecord(tbFinanceInfo);
            //更新报销时间大于新增数据的余额信息
            tbFinanceInfoService.updateRecordList(tbFinanceInfo, UpdateStatus.INSERT.getCode());
            return Resp.success("新增成功！",null);
        }
    }

    /**
     * 更新财务信息
     * 该方法用于更新财务信息记录。在更新之前，会进行一系列操作，包括旧数据删除、新数据插入、余额计算、记录创建等。同时，通过加锁确保线程安全。
     * @param tbFinanceInfoUpdateQO 财务信息的更新请求对象，包含需要更新的财务信息的相关字段和数据。
     * @return 返回操作结果的响应对象。如果更新成功，返回成功的响应；否则，返回包含错误信息的响应。
     */
    @PostMapping("/update")
    @InitUser
    @LogParams
    @Transactional
    public Resp<?> financeInfoUpdate(@RequestBody @Validated TbFinanceInfoUpdateQO tbFinanceInfoUpdateQO){
        synchronized (lock){
            //根据id获取原记录信息
            TbFinanceInfo tbFinanceInfoOld=tbFinanceInfoService.getById(tbFinanceInfoUpdateQO.getId());
            //删除旧记录
            tbFinanceInfoService.deleteRecord(tbFinanceInfoUpdateQO.getId());
            //更新报销时间大于删除记录的余额信息
            tbFinanceInfoService.updateRecordList(tbFinanceInfoOld, UpdateStatus.DELETE.getCode());
            //数据转换
            TbFinanceInfo tbFinanceInfo=tbFinanceInfoService.convertInsertQOtoPOJO(tbFinanceInfoUpdateQO);
            //插入新数据
            tbFinanceInfo = tbFinanceInfoService.saveNewRecord(tbFinanceInfo);
            //更新报销时间大于新增数据的余额信息
            tbFinanceInfoService.updateRecordList(tbFinanceInfo, UpdateStatus.INSERT.getCode());
            return Resp.success("更新成功！",null);
        }
    }

    /**
     * 删除财务信息
     * 该方法用于删除财务信息记录。在删除之前，会进行一系列操作，包括旧数据删除。同时，通过加锁确保线程安全。
     * @param tbFinanceInfoUpdateQO 财务信息的更新请求对象，包含需要删除的财务信息的相关字段和数据。
     * @return 返回操作结果的响应对象。如果删除成功，返回成功的响应；否则，返回包含错误信息的响应。
     */
    @PostMapping("/delete")
    @InitUser
    @LogParams
    @Transactional
    public Resp<?> financeInfoDelete(@RequestBody @Validated TbFinanceInfoUpdateQO tbFinanceInfoUpdateQO){
        synchronized (lock){
            //根据id获取原记录信息
            TbFinanceInfo tbFinanceInfoOld=tbFinanceInfoService.getById(tbFinanceInfoUpdateQO.getId());
            //删除旧数据
            tbFinanceInfoService.deleteRecord(tbFinanceInfoUpdateQO.getId());
            //更新报销时间大于删除记录的余额信息
            tbFinanceInfoService.updateRecordList(tbFinanceInfoOld, UpdateStatus.DELETE.getCode());
            return Resp.success("删除成功！",null);
        }
    }


    /**
     * 分页查询财务信息
     * 该方法用于进行分页查询财务信息记录。在查询之前，会进行一系列操作，包括分页参数设置、查询执行等。
     * @param tbFinanceInfoPageQO 财务信息的分页查询请求对象，包含需要查询的财务信息的相关字段和数据。
     * @return 返回操作结果的响应对象。如果查询成功，返回包含查询结果的响应；否则，返回包含错误信息的响应。
     */
    @PostMapping("/page")
    @InitUser
    @LogParams
    public Resp<?> financePage(@RequestBody @Validated TbFinanceInfoPageQO tbFinanceInfoPageQO){
        Page<TbFinanceInfo> page = new Page<>(tbFinanceInfoPageQO.getCurrentPage(), tbFinanceInfoPageQO.getPageSize());
        Page<TbFinanceInfoVO> tbFinanceInfoPage =tbFinanceInfoService.selectPage(page, tbFinanceInfoPageQO);
        return Resp.success(new TbFinanceInfoPageVO(tbFinanceInfoPage.getTotal(),tbFinanceInfoPage.getPages(),tbFinanceInfoPage.getRecords()));
    }


    /**
     * 获取财务信息历史记录
     * 该方法用于获取当前用户的财务信息历史记录。在获取历史记录之前，会进行一系列操作，包括根据用户ID查询历史记录、数据处理等。
     * @return 返回操作结果的响应对象。如果获取成功，返回包含财务信息历史记录的响应；否则，返回包含错误信息的响应。
     */
    @GetMapping("/history")
    @InitUser
    @LogParams
    public Resp<?> history(){
        Integer id=UserContextUtil.get().getId();
        List<TbFinanceInfo> tbFinanceInfoList=tbFinanceInfoService.selectHistory(id);
        //对查询结果进行处理
        List<TbFinanceInfo> result = tbFinanceInfoService.tbFinanceInfoAddress(tbFinanceInfoList);
        //获取收入历史列表
        List<String> balanceList = tbFinanceInfoService.getBalanceList(result);
        //获取时间列表
        List<String> updateTimeList = tbFinanceInfoService.getUpdateTimeList(result);
        TbFinanceHistoryVO tbFinanceHistoryVO = new TbFinanceHistoryVO();
        tbFinanceHistoryVO.setBalance(balanceList);
        tbFinanceHistoryVO.setDateList(updateTimeList);
        return Resp.success("成功",tbFinanceHistoryVO);
    }



    /**
     * 财务对比信息
     * 该方法用于比较指定时间范围内的支出和收入财务信息。
     * @param compareQO 比较财务信息的请求对象，包含比较的起始时间和结束时间。
     * @return 返回操作结果的响应对象。如果比较成功，返回包含比较结果的响应；否则，返回包含错误信息的响应。
     */
    @PostMapping("/compare")
    @InitUser
    @LogParams
    public Resp<?> compare(@RequestBody @Validated CompareQO compareQO){
        Integer id=UserContextUtil.get().getId();
        //根据当前登录用户和起始时间获取对比信息
        TbFinanceCompareVO tbFinanceCompareVO=tbFinanceInfoService.selectRange(id,compareQO.getStartTime(),compareQO.getEndTime());
        return Resp.success("成功",tbFinanceCompareVO);
    }

}
