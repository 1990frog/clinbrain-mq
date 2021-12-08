package com.clinbrain.mq.message.send;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.WuhanProperties;
import com.clinbrain.mq.message.conf.YanTaiProperties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 烟台发送短信的方法
 * @author Liaopan
 * @date 2021-12-06
 */
@Service("wuhan")
@Slf4j
public class WuhanSender implements ISmsSender {

    @Autowired
    private WuhanProperties wuhanProperties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String phoneNumbers = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String content = uMqMessage.getContent();
        System.out.println("电话："+phoneNumbers);
        System.out.println("uMqMessage："+uMqMessage);
        System.out.println("mqMessageObject："+mqMessageObject);
        wuhanProperties.getJdbcUrl();
        SimpleDataSource dataSource = new SimpleDataSource(wuhanProperties.getJdbcUrl(),
                wuhanProperties.getUsername(), wuhanProperties.getPassword(),"");
        Db db = new Db()

    }

}
