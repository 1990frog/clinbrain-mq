package com.clinbrain.mq.model.custom.sms;

import lombok.SneakyThrows;

public class YanTaiRequest {
    private String SpCode;          //企业编号
    private String LoginName;       //用户名称
    private String Password;        //用户密码
    private String MessageContent;  //短信内容
    private String UserNumber;      //手机号码(多个号码用”,”分隔)，最多1000个号
    private String SerialNumber;    //流水号，20位数字，每个请求流水号要求唯一.必填参数，与回执接口中的流水号一一对应，不传后面回执接口无法查询数据。
    private String ScheduleTime;    //预约发送时间，格式:yyyyMMddHHmmss,如‘20090901010101’，立即发送请填空
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
        ScheduleTime = new String(scheduleTime.getBytes(),"GBK");
    }

    public String getF() {
        return f;
    }

    @SneakyThrows
    public void setF(String f) {
        this.f = new String(f.getBytes(),"GBK");
    }
}
