package com.demo.finance.config.exception;

import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * @file: ExceptionUtil
 * @version: 1.0.0
 * @Description:
 * 工具类，用于获取异常堆栈信息字符串
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
public class ExceptionUtil {
    /**
     * 获取异常的堆栈跟踪信息
     * @param throwable 异常对象
     * @return 堆栈跟踪信息字符串
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        return stringWriter.toString();
    }

}
