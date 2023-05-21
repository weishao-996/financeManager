package com.demo.finance.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @file: MybatisPlusConfig
 * @version: 1.0.0
 * @Description:
 *  Mybatis Plus配置类
 *  MybatisPlusConfig类用于配置Mybatis Plus相关的配置项，如分页插件等。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Configuration
public class MybatisPlusConfig {

    /*分页插件*/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();// 配置分页插件 并 选择数据库类型
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

}