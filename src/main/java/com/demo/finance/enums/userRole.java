package com.demo.finance.enums;


import java.util.Arrays;
import java.util.Optional;

public enum userRole {
    ADMIN(1,"管理员"),
    COMMON(2,"普通用户");


    userRole(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private final  Integer code;
    private final  String name;

    public Integer getCode() {
        return code;
    }
    public String getName() {
        return name;
    }

    public static userRole findByCode(Integer code){
        Optional<userRole> first =  Arrays.stream(userRole.values())
                .filter(p -> p.getCode().equals(code))
                .findFirst();
        return   first.orElse(null);
    }
}
