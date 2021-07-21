package com.clinbrain.mq.model.custom.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UMsgTemplate {
    private Long id;
    private String templateCode;
    private String templateGenre;
    private String templateContent;

}
