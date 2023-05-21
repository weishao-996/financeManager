package com.demo.finance.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UpdateStatus {

    DELETE(0,"删除"),
    INSERT(1,"新增");


    private final  Integer code;
    private final  String name;


}
