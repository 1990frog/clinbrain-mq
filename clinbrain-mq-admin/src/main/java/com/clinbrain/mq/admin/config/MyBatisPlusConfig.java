package com.clinbrain.mq.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.clinbrain.mq.admin.dao")
public class MyBatisPlusConfig {



}
