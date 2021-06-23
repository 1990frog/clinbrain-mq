package com.xxl.mq.admin.test.rpc;

import com.xxl.mq.client.broker.IXxlMqBroker;
import com.xxl.rpc.core.remoting.invoker.reference.XxlRpcReferenceBean;


public class MqBrokerTest {

    public static void main(String[] args) throws Exception {
        String address = "127.0.0.1:7080";

        IXxlMqBroker xxlMqBroker = (IXxlMqBroker) new XxlRpcReferenceBean().getObject();

        // test
        xxlMqBroker.addMessages(null);

    }
}
