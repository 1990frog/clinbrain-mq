package com.clinbrain.mq.common.conf;

import com.clinbrain.mq.message.ISmsSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Liaopan
 * @date 2021-12-07
 */
@Configuration
@Slf4j
public class SmsSenderBeanConfig implements InitializingBean, ApplicationContextAware {

    private final Map<String, ISmsSender> smsSenderMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    public ISmsSender createSmsSender(String aliasName) {
        ISmsSender smsSender = smsSenderMap.get(aliasName);
        if(smsSender == null) {
            return smsSenderMap.get("");
        }
        return  smsSender;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final Map<String, ISmsSender> smsBeans = applicationContext.getBeansOfType(ISmsSender.class);
        smsBeans.forEach((key, value) -> {
            log.info("加载短信接口实现:{}, {}", key, value);
        });
        smsSenderMap.putAll(smsBeans);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
