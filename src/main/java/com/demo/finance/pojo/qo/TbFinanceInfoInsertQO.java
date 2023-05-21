package com.demo.finance.pojo.qo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author XUWEI
 * @since 2023-03-11
 */
@Data
public class TbFinanceInfoInsertQO {

    @NotNull(message = "报销人不能为空！")
    private Integer claimerUserId;
    @NotBlank(message = "报销事由不能为空！")
    private String reason;
    @NotNull(message = "报销时间不能为空！")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reasonTime;
    @NotBlank(message = "收支金额不能为空！")
    private String sum;
    @NotNull(message = "报销类型不能为空！")
    private Integer type;
    @NotNull(message = "报销日期不能为空！")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}
