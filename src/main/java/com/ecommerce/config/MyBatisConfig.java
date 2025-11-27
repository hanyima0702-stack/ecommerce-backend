package com.ecommerce.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置类
 *
 * @author 系统生成
 * @version 1.0.0
 */
@Configuration
@MapperScan("com.ecommerce.mapper")
public class MyBatisConfig {

    // MyBatis 配置主要通过 application.yml 进行配置
    // 这里主要用于配置 Mapper 接口扫描路径
}