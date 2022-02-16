package com.clinbrain.mq.message.send;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.Foshan11Properties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * 佛山市一发送短信的方法
 *
 * @author Liaopan
 * @date 2021-12-06
 */
@Service("foshan11")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "foshan11")
public class Foshan11Sender implements ISmsSender {

    @Autowired
    private Foshan11Properties foshan11Properties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String content = uMqMessage.getContent();

        if (StrUtil.isEmpty(content)) {
            throw new SMSException("短信内容为空!");
        }

        String requestUrl = foshan11Properties.getUrl();
        StringBuilder sendSoapString = new StringBuilder();
        String Phno = StrUtil.emptyToDefault(phoneNumber, "");
        String Msg = StrUtil.emptyToDefault(content, "");
        String SndTm = DateUtil.now();
        String MzNo = "";
        String Inid = "";
        String PatNm = "";
        String SndNm = "消息系统";
        String Postsys = "";
        String Dspt = "";
        String str = "Phno=" + "\"" + Phno + "\"" + ";" +
                "Msg=" + "\"" + Msg + "\"" + ";" +
                "SndTm=" + "\"" + SndTm + "\"" + ";" +
                "MzNo=" + "\"" + MzNo + "\"" + ";" +
                "Inid=" + "\"" + Inid + "\"" + ";" +
                "PatNm=" + "\"" + PatNm + "\"" + ";" +
                "SndNm=" + "\"" + SndNm + "\"" + ";" +
                "Postsys=" + "\"" + Postsys + "\"" + ";" +
                "Dspt=" + "\"" + Dspt + "\"" + ";";

        sendSoapString.append(
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "  <soap:Body>\n" +
                        "    <SfSendMsg xmlns=\"http://tempuri.org/\">\n" +
                        "      <strParm>" + str + "</strParm>\n" +
                        "    </SfSendMsg>\n" +
                        "  </soap:Body>\n" +
                        "</soap:Envelope>");

        try {
            String resp = getWebServiceAndSoap(requestUrl, foshan11Properties.getClazz(), foshan11Properties.getMethod(), sendSoapString);
            log.info("调用短信接口返回:" + resp);
        } catch (Exception e) {
            throw new SMSException(e.getMessage());
        }
    }

    private String getWebServiceAndSoap(String url, String isClass, String isMethod, StringBuilder sendSoapString) throws IOException {
        String soap = sendSoapString.toString();
        if (soap == null) {
            return null;
        }
        URL soapUrl = new URL(url);
        URLConnection conn = soapUrl.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Length", Integer.toString(soap.length()));
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        // 调用的接口方法是
        conn.setRequestProperty(isClass, isMethod);
        OutputStream os = conn.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
        osw.write(soap);
        osw.flush();
        osw.close();
        // 获取webserivce返回的流
        InputStream is = conn.getInputStream();
        return IoUtil.read(is, StandardCharsets.UTF_8);
    }

}
