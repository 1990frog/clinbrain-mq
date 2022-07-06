package com.clinbrain.mq.message.send;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.clinbrain.mq.message.ISmsSender;
import com.clinbrain.mq.message.SMSException;
import com.clinbrain.mq.message.conf.LinshuProperties;
import com.clinbrain.mq.message.conf.PengzhouProperties;
import com.clinbrain.mq.model.custom.MqMessageObject;
import com.clinbrain.mq.model.custom.UMqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 彭州发送短信的方法
 *
 * @author yuehl
 * @date 2022-02-24
 */
@Service("pengzhou")
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles.", name = "active", havingValue = "pengzhou")
public class PengzhouSender implements ISmsSender {

    @Autowired
    private PengzhouProperties pengzhouProperties;

    @Override
    public void sendSms(MqMessageObject mqMessageObject) throws SMSException {
        String authorizeUrl = pengzhouProperties.getAuthorizeUrl();
        String sendSmsUrl = pengzhouProperties.getSendSmsUrl();
        String ec_name = pengzhouProperties.getEc_name();
        String user_name = pengzhouProperties.getUser_name();
        String user_passwd = pengzhouProperties.getUser_passwd();
        String sign = pengzhouProperties.getSign();
        String serial = pengzhouProperties.getSerial();

        String phoneNumber = mqMessageObject.getPhoneNumber();
        UMqMessage uMqMessage = mqMessageObject.getUMqMessage();
        String msg = uMqMessage.getContent();

        try {
            ec_name = java.net.URLEncoder.encode(ec_name, "utf-8");
            //认证参数
            String authorizeParam = "ec_name=" + ec_name + "&user_name=" + user_name
                    + "&user_passwd=" + user_passwd;
            //认证
            final String authorizePostBody = sendPost(authorizeUrl, authorizeParam);
            if(StrUtil.isNotEmpty(authorizePostBody)) {
                JSONObject resultObj = new JSONObject(authorizePostBody);
                if("Success".equalsIgnoreCase(resultObj.getStr("status"))) {
                    String mac = encryptToMD5(resultObj.getStr("mas_user_id") + phoneNumber + msg + sign + serial
                            + resultObj.getStr("access_token"));
                    msg = java.net.URLEncoder.encode(msg, "utf-8");
                    //发短信参数
                    String smsParam = "mas_user_id=" + resultObj.getStr("mas_user_id") + "&mobiles=" + phoneNumber
                            + "&" + "content=" + msg + "&sign=" + sign + "&serial="
                            + serial + "&mac=" + mac;
                    //发短信
                    final String smsPostBody = sendPost(sendSmsUrl, smsParam);
                    if(StrUtil.isNotEmpty(smsPostBody)) {
                        JSONObject smsResultObj = new JSONObject(smsPostBody);
                        if(!"SC:0000".equalsIgnoreCase(smsResultObj.getStr("RET-CODE"))) {
                            throw new SMSException("短信平台返回错误: 错误信息->" + smsParam + smsResultObj.getStr("RET-CODE"));
                        }
                    }else {
                        throw new SMSException("获取短信平台信息出错: 系统未知错误->" +smsParam +  smsPostBody);
                    }
                }else{
                    throw new SMSException("短信平台认证返回错误: 错误信息->" + resultObj.getStr("status"));
                }
            }else {
                throw new SMSException("获取短信平台认证信息出错: 系统未知错误->" + authorizePostBody);
            }
        } catch (Exception e) {
            throw new SMSException("短信发送失败: "  + ExceptionUtil.getRootCauseMessage(e));
        }

    }

    //发送请求
    public static String sendPost(String url, String param) throws Exception {
        // PrintWriter out = null;
        OutputStreamWriter out = null;

        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(param);
            out.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            throw new Exception("发送异常:" +  e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    // MD5转换
    public static String encryptToMD5(String plainText) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] digesta = null;
        String result = null;
        // 得到一个MD5的消息摘要
        MessageDigest mdi = MessageDigest.getInstance("MD5");
        // 添加要进行计算摘要的信息
        mdi.update(plainText.getBytes("utf-8"));
        // 得到该摘要
        digesta = mdi.digest();
        result = byteToHex(digesta);

        return result;
    }

    /**
     * 将二进制转化为16进制字符串
     */
    public static String byteToHex(byte[] plainText) {
        StringBuilder hs = new StringBuilder("");
        String temp = "";
        for (int i = 0; i < plainText.length; i++) {
            temp = Integer.toHexString(plainText[i] & 0XFF);
            if (temp.length() == 1) {
                hs.append("0").append(temp);
            } else {
                hs.append(temp);
            }
        }
        return hs.toString().toUpperCase();
    }

}
