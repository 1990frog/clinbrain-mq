package com.clinbrain.mq.common.enums;

/**
 * Created by Liaopan on 2021-07-14.
 * 定义所有的消息类型
 */
public enum MessagesGenreEnum {
    /**
     * 短信
     */
    SMS(100, "短信"),
    /**
     * 邮件
     */
    EMAIL(101, "邮件");

    final private int code;
    final private String description;

    MessagesGenreEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
