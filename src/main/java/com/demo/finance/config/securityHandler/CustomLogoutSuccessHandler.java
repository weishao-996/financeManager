package com.demo.finance.config.securityHandler;
import com.demo.finance.config.result.Resp;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @file: CustomLogoutSuccessHandler
 * @version: 1.0.0
 * @Description:
 * 自定义的登出处理器
 * 决定响应json还是跳转页面，或者登出成功进行其他处理
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Component("customLogoutSuccessHandler")
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Resp resp = Resp.success("退出成功，请重新登录",null);
        String s = resp.toJsonString();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);
    }
}
