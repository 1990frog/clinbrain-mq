package com.clinbrain.mq.model.custom;

import lombok.Data;

import java.util.List;

@Data
public class EmailSendEntity {
    private String from;                    // 邮件发送人
    private List<String> to;                // 邮件接收人
    private String title;                   // 邮件标题
    private List<String> pictures;          // 图片列表
    private List<String> attachments;       // 附件列表
    private String content;                 // 邮件正文
}
