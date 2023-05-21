package com.demo.finance.utils;

import com.demo.finance.pojo.TbUser;

/**
 * @file: UserContextUtil
 * @version: 1.0.0
 * @Description:
 * 用户上下文工具类
 * UserContextUtil是一个工具类，用于在当前线程中保存和获取用户信息。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
public class UserContextUtil {

    private static final ThreadLocal<TbUser> USERINFO_CONTEXT = new ThreadLocal<>();

    /**
     * 用户id保存到ThreadLocal中
     *
     * @return
     */
    public static void set(TbUser userInfo) {
        USERINFO_CONTEXT.set(userInfo);
    }

    /**
     * 清空上下文
     */
    public static void clear() {
        USERINFO_CONTEXT.remove();
    }

    /**
     * 获取上下中的记录操作日志命令
     *
     * @return
     */
    public static TbUser get() {
        return USERINFO_CONTEXT.get();
    }


}