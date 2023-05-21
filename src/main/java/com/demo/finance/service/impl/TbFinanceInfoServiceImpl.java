package com.demo.finance.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.finance.dao.TbFinanceInfoMapper;
import com.demo.finance.dao.TbUserMapper;
import com.demo.finance.enums.BalanceType;
import com.demo.finance.enums.IsDelete;
import com.demo.finance.enums.UpdateStatus;
import com.demo.finance.enums.userRole;
import com.demo.finance.pojo.TbFinanceInfo;
import com.demo.finance.pojo.qo.TbFinanceInfoInsertQO;
import com.demo.finance.pojo.qo.TbFinanceInfoPageQO;
import com.demo.finance.pojo.vo.TbFinanceCompareVO;
import com.demo.finance.pojo.vo.TbFinanceInfoVO;
import com.demo.finance.service.TbFinanceInfoService;
import com.demo.finance.utils.UserContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @file: TbFinanceInfoServiceImpl
 * @version: 1.0.0
 * @Description:
 * 财务信息服务实现类
 * TbFinanceInfoServiceImpl是TbFinanceInfoService接口的实现类，提供了财务信息相关的服务。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Service
public class TbFinanceInfoServiceImpl extends ServiceImpl<TbFinanceInfoMapper, TbFinanceInfo> implements TbFinanceInfoService {

    @Resource
    private TbUserMapper tbUserMapper;
    @Value("${DEFAULT_EXPENSE}")
    private String DEFAULT_EXPENSE;
    @Value("${DEFAULT_INCOME}")
    private String DEFAULT_INCOME;
    @Value("${DEFAULT_BALANCE}")
    private String DEFAULT_BALANCE;
    @Value("${DEFAULT_TIME_FORMAT}")
    private String DEFAULT_TIME_FORMAT;

    /**
     * 分页查询财务信息
     *
     * @param page              分页对象
     * @param tbFinanceInfoPageQO 查询条件对象
     * @return Page<TbFinanceInfoVO> 分页查询结果
     */
    @Override
    public  Page<TbFinanceInfoVO> selectPage(Page<TbFinanceInfo> page, TbFinanceInfoPageQO tbFinanceInfoPageQO) {
        Integer adminId=tbUserMapper.getAdminId(userRole.ADMIN.getCode());
        //如果当前登录用户不是admin只能查询登录用户报销记录
        if (!UserContextUtil.get().getId().equals(adminId)) tbFinanceInfoPageQO.setClaimerUserId(UserContextUtil.get().getId());
        return this.baseMapper.queryPage(page,tbFinanceInfoPageQO,IsDelete.NOT_IS_DELETE.getCode());
    }

    /**
     * 根据总金额和更新时间获取余额
     *
     * @param tbFinanceInfoInsertQO 包含总金额和更新时间的查询对象
     * @return String 余额
     */
    @Override
    public String getBalanceBySumAndUpdateTime(TbFinanceInfoInsertQO tbFinanceInfoInsertQO) {
        String LatestBalance=this.baseMapper.getLatestLatestBalanceByTime(tbFinanceInfoInsertQO.getUpdateTime(),tbFinanceInfoInsertQO.getClaimerUserId());
        if (!StringUtils.hasText(LatestBalance)){
            LatestBalance=DEFAULT_BALANCE;
        }
        BigDecimal balanceBigDecimal = new BigDecimal(LatestBalance);
        BigDecimal newBalance;
        if (tbFinanceInfoInsertQO.getType().equals(BalanceType.EXPENSE.getCode())){
            newBalance = balanceBigDecimal.subtract(new BigDecimal(tbFinanceInfoInsertQO.getSum()));
        }else {
            newBalance = balanceBigDecimal.add(new BigDecimal(tbFinanceInfoInsertQO.getSum()));
        }
        return newBalance.toString();
    }


    /**
     * 删除记录
     *
     * @param id 记录ID
     */
    @Override
    public void deleteRecord(Integer id) {
        //删除记录
        this.baseMapper.deleteById(id);
    }


    /**
     * 查询历史记录
     *
     * @param id 记录ID
     * @return List<TbFinanceInfo> 历史记录列表
     */
    @Override
    public List<TbFinanceInfo> selectHistory(Integer id) {
        return this.baseMapper.selectHistory(id,IsDelete.NOT_IS_DELETE.getCode());
    }

    /**
     * 根据时间范围查询财务信息并比较收入和支出
     *
     * @param id        用户ID
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return TbFinanceCompareVO 比较结果对象
     */

    @Override
    public TbFinanceCompareVO selectRange(Integer id, LocalDateTime startTime, LocalDateTime endTime) {
        List<TbFinanceInfo> tbFinanceInfoList = this.baseMapper.selectRange(id, startTime, endTime, IsDelete.NOT_IS_DELETE.getCode());
        TbFinanceCompareVO vo = new TbFinanceCompareVO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);
        vo.setDateList(new ArrayList<>());
        vo.setExpenses(DEFAULT_EXPENSE);
        vo.setIncome(DEFAULT_INCOME);
        tbFinanceInfoList.stream()
                .filter(info -> info.getType() != null && info.getSum() != null)
                .forEach(info -> {
                    double sum = Double.parseDouble(info.getSum());
                    if (info.getType().equals(BalanceType.EXPENSE.getCode()) ) {
                        vo.setExpenses(new BigDecimal(vo.getExpenses()).add(new BigDecimal(sum)).toString());
                    } else if (info.getType().equals(BalanceType.INCOME.getCode())){
                        vo.setIncome(new BigDecimal(vo.getIncome()).add(new BigDecimal(sum)).toString());
                    }
                    //如果无时间范围则需返回时间列表进行初始化
                    if (startTime == null && endTime == null) {
                        vo.getDateList().add(info.getUpdateTime().format(formatter));
                    }
                });
        // 去重和排序
        if (startTime == null && endTime == null) {
            vo.setDateList(vo.getDateList().stream().distinct().sorted().collect(Collectors.toList()));
        }
        return vo;
    }

    /**
     * 根据用户ID删除记录
     *
     * @param id 用户ID
     */
    @Override
    public void deleteRecordByUserId(Integer id) {
        this.baseMapper.deleteRecordByUserId(id,IsDelete.IS_DELETE.getCode());
    }

    /**
     * 历史信息处理
     *
     * @param tbFinanceInfoList 历史信息列表
     * @return List<TbFinanceInfo> 历史报销记录列表
     */
    @Override
    public List<TbFinanceInfo> tbFinanceInfoAddress(List<TbFinanceInfo> tbFinanceInfoList) {
        return  tbFinanceInfoList.stream()
                .collect(Collectors.groupingBy(TbFinanceInfo::getUpdateTime))
                .values()
                .stream()
                .map(list -> list.stream()
                        .max(Comparator.comparing(TbFinanceInfo::getId))
                        .orElse(null))
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(TbFinanceInfo::getUpdateTime))
                .collect(Collectors.toList());
    }


    /**
     * 获取历史余额列表
     *
     * @param result 历史信息列表
     * @return List<String> 比较结果对象
     */
    @Override
    public List<String> getBalanceList(List<TbFinanceInfo> result) {
        return result.stream()
                .map(TbFinanceInfo::getBalance)
                .collect(Collectors.toList());
    }



    /**
     * 获取历史时间列表
     *
     */
    @Override
    public List<String> getUpdateTimeList(List<TbFinanceInfo> result) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);
        return result.stream()
                .map(TbFinanceInfo::getUpdateTime)
                .map(formatter::format)
                .collect(Collectors.toList());
    }


    /**
     * 保存新增记录
     *
     */
    @Override
    public TbFinanceInfo saveNewRecord(TbFinanceInfo tbFinanceInfo) {
        this.baseMapper.insert(tbFinanceInfo);
        return tbFinanceInfo;
    }


    /**
     * QO转换成POJO
     *
     * @return TbFinanceInfo 转换结果
     */
    @Override
    public TbFinanceInfo convertInsertQOtoPOJO(TbFinanceInfoInsertQO tbFinanceInfoInsertQO) {
        TbFinanceInfo tbFinanceInfo = new TbFinanceInfo();
        BeanUtils.copyProperties(tbFinanceInfoInsertQO,tbFinanceInfo);
        tbFinanceInfo.setBalance(getBalanceBySumAndUpdateTime(tbFinanceInfoInsertQO));
        tbFinanceInfo.setIsDeleted(IsDelete.NOT_IS_DELETE.getCode());
        tbFinanceInfo.setRecorderUserId(UserContextUtil.get().getId());
        tbFinanceInfo.setCreateTime(LocalDateTime.now());
        tbFinanceInfo.setId(null);
        return tbFinanceInfo;
    }



    /**
     * 删除或新增报销记录时，更新报销时间大于该记录的所有记录余额信息
     *
     * @param tbFinanceInfo        新增或删除的报销记录
     * @param updateStatus         新增操作或删除操作标志
     */
    @Override
    public void updateRecordList(TbFinanceInfo tbFinanceInfo, Integer updateStatus) {
        //查出所有需要更新的记录
        List<TbFinanceInfo> tbFinanceInfoList=this.baseMapper.selectRecordByUpdateTime(tbFinanceInfo.getId(),tbFinanceInfo.getUpdateTime(),tbFinanceInfo.getClaimerUserId(), IsDelete.NOT_IS_DELETE.getCode());
        //更新所有记录的余额
        tbFinanceInfoList = tbFinanceInfoList.stream()
                .peek(Info -> {
                    BigDecimal balanceBigDecimal = new BigDecimal(Info.getBalance());
                    BigDecimal sumBigDecimal= new BigDecimal(tbFinanceInfo.getSum());
                    BigDecimal newBalance = null;
                    //如果是删除操作则取反
                    if (updateStatus.equals(UpdateStatus.DELETE.getCode()))sumBigDecimal=sumBigDecimal.negate();
                    //如果记录为支出且为新增操作，则待更新记录均减少；如果记录为支出且为删除操作，sum取反则待更新记录均增加
                    if (tbFinanceInfo.getType().equals(BalanceType.EXPENSE.getCode())){
                        newBalance = balanceBigDecimal.subtract(sumBigDecimal);
                        //如果记录为收入且为新增操作，则待更新记录均增加；如果记录为收入且为删除操作，sum取反则待更新记录均减少
                    }else if (tbFinanceInfo.getType().equals(BalanceType.INCOME.getCode())){
                        newBalance = balanceBigDecimal.add(sumBigDecimal);
                    }
                    assert newBalance != null;
                    Info.setBalance(newBalance.toString());
                })
                .collect(Collectors.toList());
        this.baseMapper.updateBatchBalance(tbFinanceInfoList);
    }

}
