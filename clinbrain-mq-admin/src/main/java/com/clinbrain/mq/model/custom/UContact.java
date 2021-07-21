package com.clinbrain.mq.model.custom;

import lombok.Data;
import java.util.Date;

@Data
public class UContact {
    private Integer id;
    private String contactName;
    private String nickname;
    private String sex;
    private Integer enabled;
    private Date createTime;
    private Date updateTime;
}
