
package com.demo.finance.aop.LogParams;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @file:LogParamsAspect
 * @version:1.0.0
 * @Description:
 * 切面类用于定义自定义打印方法
 * @Author: XU WEI
 * @Date:  2023-5-10
 */
@Aspect
@Component
@Slf4j
public class LogParamsAspect {

    /**
     * 定义切点，匹配所有打了 @LogParams 注解的方法
     */
    @Pointcut("@annotation(com.demo.finance.aop.LogParams.LogParams)")
    public void logParamsPointcut() {}

    /**
     * 切面方法，在方法执行前打印入参信息
     */
    @Before("logParamsPointcut()")
    public void logParamsBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("开始执行方法：{}", method.getName());
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Map<String, Object> paramMap = new HashMap<>();
            for (Object arg : args) {
                paramMap.put(arg.getClass().getSimpleName(), arg);
            }
            log.info("入参信息：{}", paramMap);
        }
    }

    /**
     * 切面方法，在方法执行后打印出参信息
     */
    @AfterReturning(value = "logParamsPointcut()", returning = "returnValue")
    public void logParamsAfterReturning(JoinPoint joinPoint, Object returnValue) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        log.info("方法执行完毕：{}", method.getName());
        if (returnValue != null) {
            log.info("出参信息：{}", returnValue);
        }
    }
}
