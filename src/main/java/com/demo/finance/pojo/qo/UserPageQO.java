package com.demo.finance.pojo.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPageQO extends PageQO{

    private Integer id;
    private String userName;
    private String loginName;
    private String password;
    private Integer type;
    private String idNo;
    private String phoneNo;
}
