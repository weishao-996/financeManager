package com.demo.finance;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Generator {
    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();

        //设置数据源和账号密码
        String url="jdbc:postgresql://127.0.0.1:5432/finance";
        String username="postgres";
        String password="postgres";

        //添加需要生成的表
        tables.add("tb_finance_info");
        tables.add("tb_user");


        DataSourceConfig.Builder build = new DataSourceConfig.Builder(url, username, password)
                .schema("public");


        FastAutoGenerator.create(build)
                .globalConfig(builder -> {
                    builder.author("XUWEI")//作者
                            .outputDir("/Users/xuwei/Desktop/工作文件/烽火/启明星计划/financeManager/src/main/java")    //输出路径(写到java目录)
                            .commentDate("yyyy-MM-dd");
//                          .enableSwagger()           //开启swagger
//                          .fileOverride();           //开启覆盖之前生成的文件

                })
                .packageConfig(builder -> {
                    builder.parent("com.demo")
                            .moduleName("finance")
                            .entity("pojo")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("dao")
                            .xml("dao.xml")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,"/Users/xuwei/Desktop/工作文件/烽火/启明星计划/financeManager/src/main/resources/xml"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
//                          .addTablePrefix("p_")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .enableLombok()
                            .logicDeleteColumnName("is_deleted")
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            // 映射路径使用连字符格式，而不是驼峰
                            .enableHyphenStyle()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .mapperBuilder()
                            //生成通用的resultMap
                            .enableBaseResultMap()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper");
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
