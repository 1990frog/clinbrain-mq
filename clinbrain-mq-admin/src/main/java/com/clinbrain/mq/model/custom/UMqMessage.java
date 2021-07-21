package com.clinbrain.mq.model.custom;

import lombok.Data;

import java.util.Date;

@Data
public class UMqMessage {
    private Long id;
    private String messageGenre;
    private String  status;
    private Integer  retry;
    private String  delay;
    private String  assignTo;
    private String  content;
    private String  originalData;
    private String  log;
    private Long  templateId;
    private String expired;
    private Date  createTime;
    private Date  updateTime;
}
