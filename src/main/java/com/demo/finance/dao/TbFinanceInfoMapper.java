package com.demo.finance.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.finance.pojo.TbFinanceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.finance.pojo.qo.TbFinanceInfoPageQO;
import com.demo.finance.pojo.vo.TbFinanceInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XUWEI
 * @since 2023-03-11
 */
@Mapper
public interface TbFinanceInfoMapper extends BaseMapper<TbFinanceInfo> {

    String getLatestLatestBalanceByTime(@Param("updateTime") LocalDateTime updateTime,@Param("claimerUserId") Integer claimerUserId);

    Page<TbFinanceInfoVO> queryPage(Page<TbFinanceInfo> page, @Param("pageQO") TbFinanceInfoPageQO tbFinanceInfoPageQO,@Param("isNotDeleted") Integer isNotDeleted);

    Integer getAdminIdByUserRole(@Param("type") Integer type);

    List<TbFinanceInfo> selectRecordByUpdateTime(@Param("id")Integer id,@Param("updateTime")LocalDateTime updateTime, @Param("claimerUserId")Integer claimerUserId,@Param("isDeleted")Integer isDeleted);

    void updateBatchBalance(List<TbFinanceInfo> tbFinanceInfoList);

    List<TbFinanceInfo> selectHistory(@Param("id")Integer id,@Param("code") Integer code);

    List<TbFinanceInfo> selectRange(@Param("id")Integer id, @Param("startTime")LocalDateTime startTime, @Param("endTime")LocalDateTime endTime,@Param("code")Integer code);

    void deleteRecordByUserId(@Param("id")Integer id,@Param("isDeleted")Integer isDeleted);
}
