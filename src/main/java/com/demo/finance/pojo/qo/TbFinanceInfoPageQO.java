package com.demo.finance.pojo.qo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
public class TbFinanceInfoPageQO  extends PageQO{

    //报销人姓名
    private String claimerUserName;

    //报销人id
    private Integer claimerUserId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endTime;


    private String claimerUserNameOrder;

    private String updateTimeOrder;



}
