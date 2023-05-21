package com.demo.finance.pojo.qo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateQO extends UserInsertQO{

    @NotBlank(message = "主键不能为空！")
    private Integer id;
}
