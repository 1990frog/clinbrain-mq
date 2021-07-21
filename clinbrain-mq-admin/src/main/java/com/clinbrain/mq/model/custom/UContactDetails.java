package com.clinbrain.mq.model.custom;

import lombok.Data;
import java.util.Date;

@Data
public class UContactDetails {
    private Integer id;
    private Integer contactId;
    private String contactType;
    private String contactValue;
    private Integer isDefault;
    private String checkDefault;
    private Date createTime;
    private Date updateTime;
}
