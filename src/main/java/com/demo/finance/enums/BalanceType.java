package com.demo.finance.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BalanceType {
    EXPENSE(1,"支出"),
    INCOME(2,"收入");


    private final  Integer code;
    private final  String name;

}
