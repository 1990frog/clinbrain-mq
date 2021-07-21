package com.clinbrain.mq.rabbitmq.consumer;

import com.clinbrain.mq.common.rabbitmq.BindingsConfig;
import com.clinbrain.mq.common.rabbitmq.ExchangeConfig;
import com.clinbrain.mq.common.rabbitmq.QueueConfig;
import com.clinbrain.mq.mapper.custom.UContactDetailsMapper;
import com.clinbrain.mq.mapper.custom.UMqMessageMapper;
import com.clinbrain.mq.model.custom.MessageModel;
import com.clinbrain.mq.model.custom.UMqMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class EmailHandler {

    @Autowired
    private UMqMessageMapper uMqMessageMapper;
    @Autowired
    private UContactDetailsMapper uContactDetailsMapper;

    @RabbitListener(queues = {QueueConfig.CLINBRAIN_EMAIL_DEFAULT_QUEUE})
    public void receiveEmail(Message message, Channel channel) throws IOException {
        try {
            byte[] body = message.getBody();
            String emailStr = new String(body, "utf-8");
            log.info("queue email inform receive msg={}",emailStr);
            MessageModel model = new ObjectMapper().readValue(emailStr, MessageModel.class);
            List<UMqMessage> uMqMessages = uMqMessageMapper.selectListByIds(model.getUMqMessageIds());
            // 处理邮件消息


            uMqMessages.forEach(item ->{
                item.setLog("消息处理成功");
                item.setUpdateTime(new Date());
                item.setStatus("发送成功");
                uMqMessageMapper.updateById(item);
            });
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息已重复处理失败,拒绝再次接收,error msg={}",e.getMessage());
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.error("消息处理失败,再次返回队列,error msg={}",e.getMessage());
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

}
