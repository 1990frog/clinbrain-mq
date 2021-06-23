import com.xxl.mq.client.message.XxlMqMessage;
import com.xxl.mq.client.producer.XxlMqProducer;

import java.util.concurrent.TimeUnit;

/**
 * Created by Liaopan on 2021-06-23.
 */
public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws Exception {
        int msgNum = 1;
        long start = System.currentTimeMillis();
        for (int i = 0; i < msgNum; i++) {
            XxlMqProducer.produce(new XxlMqMessage("topic_1", "Data:"+i));
        }
        long end = System.currentTimeMillis();
        System.out.println("msgNum = " + msgNum + ", cost = " + (end-start));

        // async send msg, wait
        TimeUnit.SECONDS.sleep(30);
    }
}
