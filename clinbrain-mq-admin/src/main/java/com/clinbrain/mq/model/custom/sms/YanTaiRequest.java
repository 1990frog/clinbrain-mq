package com.clinbrain.mq.model.custom.sms;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.SneakyThrows;

/**
 *  所有参数，必须转换为GBK或GB2312编码
 */
public class YanTaiRequest {
    @JSONField(name = "SpCode")
    private String SpCode;          //企业编号
    @JSONField(name = "LoginName")
    private String LoginName;       //用户名称
    @JSONField(name = "Password")
    private String Password;        //用户密码
    @JSONField(name = "MessageContent")
    private String MessageContent;  //短信内容
    @JSONField(name = "UserNumber")
    private String UserNumber;      //手机号码(多个号码用”,”分隔)，最多1000个号
    @JSONField(name = "SerialNumber")
    private String SerialNumber;    //流水号，20位数字，每个请求流水号要求唯一.必填参数，与回执接口中的流水号一一对应，不传后面回执接口无法查询数据。
    @JSONField(name = "ScheduleTime")
    private String ScheduleTime;    //预约发送时间，格式:yyyyMMddHHmmss,如‘20090901010101’，立即发送请填空
    @JSONField(name = "ExtendAccessNum")
    private String ExtendAccessNum; //接入号扩展号（默认不填，扩展号为数字，扩展位数由当前所配的接入号长度决定，整个接入号最长20位）
    @JSONField(name = "f")
    private String f;               //提交时检测方式 1---提交号码中有效的号码仍正常发出短信，无效的号码在返回参数faillist中列出不为1或该参数不存在---提交号码中只要有无效的号码，那么所有的号码都不发出短信，所有的号码在返回参数faillist中列出

    public String getSpCode() {
        return SpCode;
    }

    @SneakyThrows
    public void setSpCode(String spCode) {
        SpCode = new String(spCode.getBytes(),"GBK");
    }

    public String getLoginName() {
        return LoginName;
    }

    @SneakyThrows
    public void setLoginName(String loginName) {
        LoginName = new String(loginName.getBytes(),"GBK");
    }

    public String getPassword() {
        return Password;
    }

    @SneakyThrows
    public void setPassword(String password) {
        Password = new String(password.getBytes(),"GBK");
    }

    public String getMessageContent() {
        return MessageContent;
    }

    @SneakyThrows
    public void setMessageContent(String messageContent) {
        MessageContent = new String(messageContent.getBytes(),"GBK");
    }

    public String getUserNumber() {
        return UserNumber;
    }

    @SneakyThrows
    public void setUserNumber(String userNumber) {
        UserNumber = new String(userNumber.getBytes(),"GBK");
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    @SneakyThrows
    public void setSerialNumber(String serialNumber) {
        SerialNumber = new String(serialNumber.getBytes(),"GBK");
    }

    public String getScheduleTime() {
        return ScheduleTime;
    }

    @SneakyThrows
    public void setScheduleTime(String scheduleTime) {
        if(StrUtil.isEmpty(scheduleTime)){
            ScheduleTime = null;
            return;
        }
        ScheduleTime = new String(scheduleTime.getBytes(),"GBK");
    }

    public String getF() {
        return f;
    }

    @SneakyThrows
    public void setF(String f) {
        this.f = new String(f.getBytes(),"GBK");
    }

    public String getExtendAccessNum() {
        return ExtendAccessNum;
    }

    @SneakyThrows
    public void setExtendAccessNum(String extendAccessNum) {
        if(StrUtil.isEmpty(extendAccessNum)){
            ExtendAccessNum = null;
            return;
        }
        ExtendAccessNum = new String(extendAccessNum.getBytes(),"GBK");
    }
}
