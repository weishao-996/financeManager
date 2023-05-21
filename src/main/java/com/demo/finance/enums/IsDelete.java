package com.demo.finance.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsDelete {

    NOT_IS_DELETE(0,"未删除"),
    IS_DELETE(1,"已删除");


    private final  Integer code;
    private final  String name;
}
