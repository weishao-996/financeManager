package com.demo.finance.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author XUWEI
 * @since 2023-03-11
 */
@Getter
@Setter
@TableName("tb_finance_info")
@ToString
public class TbFinanceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 报销人
     */
    @TableField("claimer_user_id")
    private Integer claimerUserId;

    /**
     * 事由
     */
    @TableField("reason")
    private String reason;

    /**
     * 余额
     */
    @TableField("balance")
    private String balance;

    /**
     * 记录人
     */
    @TableField("recorder_user_id")
    private Integer recorderUserId;

    /**
     * 逻辑删除 0=未删除 1=已删除
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 报销金额数
     */
    @TableField("sum")
    private String sum;

    /**
     * 报销类型 1=支出 2=收入
     */
    @TableField("type")
    private Integer type;

    /**
     * 事由发生时间
     */
    @TableField("reason_time")
    private LocalDateTime reasonTime;


}
