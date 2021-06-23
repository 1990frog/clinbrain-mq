package com.xxl.mq.admin;

import com.xxl.mq.admin.conf.XxlMqConf;
import com.xxl.mq.client.factory.XxlMqClientFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author xuxueli 2018-11-17 17:21:44
 */
@SpringBootApplication
public class XxlMqAdminApplication {

	public static void main(String[] args) {
        final ApplicationContext run = SpringApplication.run(XxlMqAdminApplication.class, args);
        final XxlMqConf bean = run.getBean(XxlMqConf.class);
        bean.init(run);
    }

}