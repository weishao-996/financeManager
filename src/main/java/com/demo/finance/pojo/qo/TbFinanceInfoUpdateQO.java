package com.demo.finance.pojo.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class TbFinanceInfoUpdateQO extends TbFinanceInfoInsertQO{

    @NotNull(message = "主键不能为空！")
    private Integer id;
}
