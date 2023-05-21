package com.demo.finance.config;

import com.demo.finance.config.securityHandler.CustomAuthenticationFailureHandler;
import com.demo.finance.config.securityHandler.CustomAuthenticationSuccessHandler;
import com.demo.finance.config.securityHandler.CustomLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @file: SecurityConfig
 * @version: 1.0.0
 * @Description:
 *  安全配置类
 *  SecurityConfig类继承自WebSecurityConfigurerAdapter，用于配置Spring Security相关的安全配置项。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Resource
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Resource
    CustomLogoutSuccessHandler customLogoutSuccessHandler;

    /**
     * 配置认证管理器
     *
     * @param auth 认证管理器构造器
     * @throws Exception 异常对象
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    /**
     * 配置HTTP请求安全处理
     *
     * @param http HTTP安全对象
     * @throws Exception 异常对象
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutUrl("/user/logout").logoutSuccessHandler(customLogoutSuccessHandler)   //处理退出成功
                .deleteCookies("JSESSIONID");
        http.formLogin()//自定义自己编写的登录页面
                .loginPage("/login.html")//登录页面设置
                .loginProcessingUrl("/user/login")//登录访问路径,与前端一致
                .successHandler(customAuthenticationSuccessHandler)//自定义认证成功处理器
                .failureHandler(customAuthenticationFailureHandler)//自定义认证失败处理器
                .and().authorizeRequests()
                .antMatchers("/login.html","/user/login").permitAll()//设置哪些路径可以直接访问，不需要认证
                .anyRequest().authenticated()
                .and().csrf().disable();//关闭csrf防护
    }

    /**
     * 配置静态资源的访问权限
     *
     * @param web Web安全对象
     * @throws Exception 异常对象
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/api/**","/images/**","/js/**","/plugins/**","/styles/**"
                ,"/user/register",
                "/user/hello");
    }


    /**
     * 配置密码加密器
     *
     * @return PasswordEncoder 密码加密器实例
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
