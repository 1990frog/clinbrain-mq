package com.xxl.mq.admin.conf;

import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.annotation.MqConsumer;
import com.xxl.mq.client.factory.XxlMqClientFactory;
import com.xxl.mq.client.factory.impl.XxlMqSpringClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class XxlMqConf {

    // ---------------------- param ----------------------

    @Value("${xxl.mq.admin.address}")
    private String adminAddress;
    @Value("${xxl.mq.accessToken}")
    private String accessToken;

    // XxlMqClientFactory
    private XxlMqClientFactory xxlMqClientFactory;

    public void init(ApplicationContext applicationContext) {
        List<IMqConsumer> consumerList = new ArrayList<>();

        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(MqConsumer.class);
        if (serviceMap!=null && serviceMap.size()>0) {
            for (Object serviceBean : serviceMap.values()) {
                if (serviceBean instanceof IMqConsumer) {
                    consumerList.add((IMqConsumer) serviceBean);
                }
            }
        }

        start(consumerList);
    }

    public void start(List<IMqConsumer> consumerList){

        xxlMqClientFactory = new XxlMqClientFactory();
        xxlMqClientFactory.setAdminAddress(adminAddress);
        xxlMqClientFactory.setAccessToken(accessToken);
        xxlMqClientFactory.setConsumerList(consumerList);

        xxlMqClientFactory.init();

    }

    /**
     * stop
     */
    public void stop() throws Exception {
        if (xxlMqClientFactory != null) {
            xxlMqClientFactory.destroy();
        }
    }


}
