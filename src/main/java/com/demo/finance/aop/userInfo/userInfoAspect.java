
package com.demo.finance.aop.userInfo;

import com.demo.finance.dao.TbUserMapper;
import com.demo.finance.pojo.TbUser;
import com.demo.finance.utils.UserContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @file:userInfoAspect
 * @version:1.0.0
 * @Description:
 * 切面类用于获取用户信息
 * @Author: XU WEI
 * @Date:  2023-5-10
 */
@Aspect
@Component
@Slf4j
public class userInfoAspect {

    @Resource
    TbUserMapper tbUserMapper;
    /**
     * 定义切点，匹配所有打了 @InitUser 注解的方法
     */
    @Pointcut("@annotation(com.demo.finance.aop.userInfo.InitUser)")
    public void getUserInfo() {}

    /**
     * 切面方法，在方法执行前获取用户信息
     */
    @Before("getUserInfo()")
    public void logParamsBefore(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String userName = userDetails.getUsername();
            TbUser tbUser=tbUserMapper.selectOneByUsername(userName);
            UserContextUtil.set(tbUser);
        }
    }

    /**
     * 切面方法，在方法执行后打印出参信息
     */
    @AfterReturning(value = "getUserInfo()", returning = "returnValue")
    public void logParamsAfterReturning(JoinPoint joinPoint, Object returnValue) {
        UserContextUtil.clear();
    }
}
