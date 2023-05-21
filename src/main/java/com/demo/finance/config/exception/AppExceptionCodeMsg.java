package com.demo.finance.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @file: AppException
 * @version: 1.0.0
 * @Description:
 *     该枚举类定义了应用异常的代码和消息，用于标识不同的异常情况。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Getter
@AllArgsConstructor
public enum AppExceptionCodeMsg {
    
    INVALID_CODE(10000,"验证码无效"),
    DUPLICATE_USER(10001,"用户名重复"),
    NOT_EXIST_USER(10002,"用户名不存在"),
    ;

    private int code;
    private String msg;



}
