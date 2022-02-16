package com.clinbrain.mq.common.conf;

import com.clinbrain.mq.service.BaseSmsService;
import com.clinbrain.mq.service.ISmsTemplateService;
import com.clinbrain.mq.service.custom.DefaultSmsService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Liaopan
 * @date 2021-12-07
 */
@Configuration
@Slf4j
public class SmsServiceBeanConfig implements InitializingBean, ApplicationContextAware {

    private final Map<String, ISmsTemplateService> smsServiceMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    public ISmsTemplateService createSmsService(String aliasName) {
        ISmsTemplateService smsService = smsServiceMap.get(aliasName);
        if(smsService == null) {
            return smsServiceMap.get("defaultService");
        }
        return  smsService;
    }

    @Bean
    @ConditionalOnMissingBean
    public ISmsTemplateService defaultService() {
        log.info("加载templateService 默认实现: DefaultSmsService");
        return new DefaultSmsService();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final Map<String, ISmsTemplateService> smsBeans = applicationContext.getBeansOfType(ISmsTemplateService.class);
        smsBeans.forEach((key, value) -> {
            log.info("加载tempalateservice:{}, {}", key, value);
        });
        smsServiceMap.putAll(smsBeans);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
