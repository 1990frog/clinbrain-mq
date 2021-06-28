package com.clinbrain.mq.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MQAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(MQAdminApplication.class,args);
    }
}
