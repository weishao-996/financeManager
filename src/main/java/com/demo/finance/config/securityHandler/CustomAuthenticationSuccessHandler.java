package com.demo.finance.config.securityHandler;

import com.demo.finance.config.result.Resp;
import com.demo.finance.utils.UserContextUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @file: CustomAuthenticationSuccessHandler
 * @version: 1.0.0
 * @Description:
 * 自定义的认证成功处理器
 * 决定响应json还是跳转页面，或者认证成功进行其他处理
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException {
        //认证成功后响应json数据给前端
        Resp resp = Resp.success("认证成功", UserContextUtil.get());
        String s = resp.toJsonString();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(s);
    }
}
