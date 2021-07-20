package com.clinbrain.mq.client.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

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

        channel.basicPublish("clinbrain.amq.sms.direct",
                "inform.sms.default" ,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                msg.getBytes());

        channel.close();
        conn.close();

    }

}
