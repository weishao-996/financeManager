
package com.demo.finance.config.result;

import com.alibaba.fastjson.JSON;
import com.demo.finance.config.exception.AppExceptionCodeMsg;
import lombok.Getter;


/**
 * @file: Resp
 * @version: 1.0.0
 * @Description:
 * Resp类用于表示服务端返回的响应结果，包含错误码、错误信息和数据。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Getter
public class Resp<T> {

    //服务端返回的错误码
    private int code=200;
    //服务端返回的错误信息
    private String msg="success";
    //服务端返回的数据
    private T data;

    private Resp(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    public static <T> Resp success(T data){
        Resp resp = new Resp<>(200, "success", data);
        return resp;
    }


    public static <T> Resp success(String msg,T data){
        Resp resp = new Resp(200, msg, data);
        return resp;
    }

    public static <T> Resp error(AppExceptionCodeMsg appExceptionCodeMsg){
        Resp resp = new Resp(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMsg(), null);
        return resp;
    }

    public static <T> Resp error(int code,String msg){
        Resp resp = new Resp(code, msg, null);
        return resp;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    @Override
    public String toString() {
        return "Resp{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
