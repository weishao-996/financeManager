package com.demo.finance.aop.userInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @file:InitUser
 * @version:1.0.0
 * @Description:
 * 自定义注解，用于获取用户信息
 * @Author: XU WEI
 * @Date:  2023-5-10
 */
@Target(ElementType.METHOD)//表示该注解只能用于方法上。
@Retention(RetentionPolicy.RUNTIME)//表示在运行时保留，可以通过反射获取。
public @interface InitUser {
}
