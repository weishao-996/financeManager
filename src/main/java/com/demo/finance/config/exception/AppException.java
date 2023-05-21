
package com.demo.finance.config.exception;

import lombok.Getter;

/**
 * @file: AppException
 * @version: 1.0.0
 * @Description:
 * 该类是一个自定义的应用异常类，继承自RuntimeException。用于表示应用程序中发生的异常情况。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Getter
public class AppException extends RuntimeException{
    private int code=500;
    private String msg="服务器异常";

    public  AppException(AppExceptionCodeMsg appExceptionCodeMsg){
        super();
        this.code=appExceptionCodeMsg.getCode();
        this.msg=appExceptionCodeMsg.getMsg();
    }

    public AppException(int code,String msg){
        super();
        this.code=code;
        this.msg=msg;
    }

}
