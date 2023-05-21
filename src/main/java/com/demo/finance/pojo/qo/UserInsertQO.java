package com.demo.finance.pojo.qo;


import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class UserInsertQO {

    @NotBlank(message = "姓名不能为空！")
    private String userName;
    @NotBlank(message = "用户名不能为空！")
    private String loginName;
    private String password;
    private String idNo;
    @NotBlank(message = "手机号不能为空！")
    private String phoneNo;
}
