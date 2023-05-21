package com.demo.finance.config.exception;

import com.demo.finance.config.result.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
  * @file: GlobalExceptionHandler
  * @version: 1.0.0
  * @Description:
  * 全局异常处理器
  * 该类是一个全局异常处理器，用于处理未被其他异常处理方法处理的异常以及方法参数校验失败和 Bean 校验失败的异常。
  * @Author: XU WEI
  * @Date: 2023-5-10
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理所有未被其他异常处理方法处理的异常。
     * 如果异常是 AppException 类型，则返回该异常中定义的错误码和错误信息；
     * 如果异常不是 AppException 类型，则打印异常堆栈跟踪信息，并返回服务器端异常的默认错误码和错误信息。
     * @param e 异常对象
     * @return 包含错误码和错误信息的 Resp 对象
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> Resp<T> exceptionHandler(Exception e){
        if (e instanceof AppException){
            AppException appException = (AppException) e;
            return Resp.error(appException.getCode(),appException.getMsg());
        }
        String stackTrace = ExceptionUtil.getStackTrace(e);
        log.error("服务器端异常，堆栈跟踪信息：" + stackTrace);
        return Resp.error(500,"服务器端异常，堆栈跟踪信息：" + stackTrace);
    }


    /**
     * 处理方法参数校验失败异常。
     * 如果校验失败，则返回校验失败的错误信息列表。
     *
     * @param ex 方法参数校验失败异常对象
     * @return 包含错误码和错误信息的 Resp 对象
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public <T> Resp handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errors = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return Resp.error(400, String.join(",", errors));
    }


    /**
     * 处理 Bean 校验失败异常。
     * 如果校验失败，则返回校验失败的错误信息列表。
     *
     * @param ex Bean 校验失败异常对象
     * @return 包含错误码和错误信息的 Resp 对象
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public <T> Resp handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> errors = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return Resp.error(400, String.join(",", errors));
    }
}
