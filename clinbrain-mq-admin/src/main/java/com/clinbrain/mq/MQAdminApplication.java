package com.clinbrain.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,scanBasePackages = "com.clinbrain.mq.admin")
public class MQAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(MQAdminApplication.class,args);
    }
}
