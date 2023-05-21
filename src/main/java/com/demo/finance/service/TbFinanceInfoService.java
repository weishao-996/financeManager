package com.demo.finance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.finance.pojo.TbFinanceInfo;
import com.demo.finance.pojo.qo.TbFinanceInfoInsertQO;
import com.demo.finance.pojo.qo.TbFinanceInfoPageQO;
import com.demo.finance.pojo.vo.TbFinanceCompareVO;
import com.demo.finance.pojo.vo.TbFinanceInfoVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XUWEI
 * @since 2023-03-11
 */
public interface TbFinanceInfoService extends IService<TbFinanceInfo> {

    Page<TbFinanceInfoVO>  selectPage(Page<TbFinanceInfo> page, TbFinanceInfoPageQO tbFinanceInfoPageQO);

    String getBalanceBySumAndUpdateTime(TbFinanceInfoInsertQO tbFinanceInfoInsertQO);

    void deleteRecord(Integer id);

    List<TbFinanceInfo> selectHistory(Integer id);

    TbFinanceCompareVO selectRange(Integer id, LocalDateTime startTime, LocalDateTime endTime);

    void deleteRecordByUserId(Integer id);

    List<TbFinanceInfo> tbFinanceInfoAddress(List<TbFinanceInfo> tbFinanceInfoList);

    List<String> getBalanceList(List<TbFinanceInfo> result);

    List<String> getUpdateTimeList(List<TbFinanceInfo> result);

    TbFinanceInfo saveNewRecord(TbFinanceInfo tbFinanceInfo);

    TbFinanceInfo convertInsertQOtoPOJO(TbFinanceInfoInsertQO tbFinanceInfoInsertQO);

    void updateRecordList(TbFinanceInfo tbFinanceInfo, Integer updateStatus);
}
