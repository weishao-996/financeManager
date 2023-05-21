package com.demo.finance.pojo.vo;

import com.demo.finance.enums.BalanceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TbFinanceInfoVO {

    private Integer id;
    private Integer claimerUserId;
    private String claimerUserName;
    private String reason;
    private String balance;
    private Integer recorderUserId;
    private String recorderUserName;
    private Integer isDeleted;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;
    private String createTimeStr;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;
    private String updateTimeStr;
    private String sum;
    private Integer type;
    private String typeStr;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private LocalDateTime reasonTime;
    private String reasonTimeStr;

    public void setType(Integer type) {
        this.type=type;
        if (type.equals(BalanceType.EXPENSE.getCode())){
            this.typeStr="支出";
        }else {
            this.typeStr="收入";
        }

    }

}
