package com.clinbrain.mq.model.custom;

import lombok.Data;
import java.util.Date;

@Data
public class UGroup {
    private Integer id;
    private String groupName;
    private String groupCode;
    private String groupType;
    private Integer enabled;
    private Date createTime;
    private Date updateTime;
}
