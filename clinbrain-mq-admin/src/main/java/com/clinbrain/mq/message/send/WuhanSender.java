package com.clinbrain.mq.message.send;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.sql.SqlExecutor;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.WuhanProperties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * 烟台发送短信的方法
 *
 * @author Liaopan
 * @date 2021-12-06
 */
@Service("wuhan")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "wuhan")
public class WuhanSender implements ISmsSender {

    @Autowired
    private WuhanProperties wuhanProperties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String content = uMqMessage.getContent();

        SimpleDataSource dataSource = new SimpleDataSource(wuhanProperties.getJdbcUrl(),
                wuhanProperties.getUsername(), wuhanProperties.getPassword(), "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try {
            SqlExecutor.execute(dataSource.getConnection(), WuhanProperties.INSERT_SQL, phoneNumber, content);
        }catch (Exception e) {
            throw new SMSException("插入数据库出错: " + ExceptionUtil.getRootCauseMessage(e));
        }

    }

}
