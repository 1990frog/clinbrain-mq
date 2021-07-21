package com.clinbrain.mq.model.custom.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ContactVo {
    private String contactName;
    private String nickname;
    private String contactType;
    private String contactValue;
}
