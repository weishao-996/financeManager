package com.demo.finance.config.securityHandler;

import com.demo.finance.config.result.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @file: CustomAuthenticationFailureHandler
 * @version: 1.0.0
 * @Description:
 * 全局异常处理器
 * 自定义认证失败处理器
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //认证失败后响应json数据给前端
        Resp resp = Resp.error(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        String s = resp.toJsonString();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(s);
    }
}
