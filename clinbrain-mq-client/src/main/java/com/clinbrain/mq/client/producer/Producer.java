package com.clinbrain.mq.client.producer;


import com.clinbrain.mq.common.config.rabbitmq.ExchangeConfig;
import com.rabbitmq.client.*;


public class Producer {


    public static void main(String[] args) throws Exception {

        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("192.168.0.115");
        cf.setPort(5672);
        cf.setUsername("clinbrain");
        cf.setPassword("clinbrain");

        //建立连接
        Connection conn = cf.newConnection();
        //创建消息通道
        Channel channel = conn.createChannel();

        //消息内容
        String msg = " hello world! ";

        channel.basicPublish(ExchangeConfig.EXCHANGE_TOPICS_INFORM,
                "inform.warn.sms.db" ,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                msg.getBytes());

        channel.close();
        conn.close();

    }

}
