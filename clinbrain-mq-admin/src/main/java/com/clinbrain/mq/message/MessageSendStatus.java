package com.clinbrain.mq.message;

/**
 * Created by Liaopan on 2021-12-07.
 */
public enum MessageSendStatus {
    SUCCESS("发送成功",1), ERROR("发送失败",0);

    private String msg;
    private Integer code;

    MessageSendStatus(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
