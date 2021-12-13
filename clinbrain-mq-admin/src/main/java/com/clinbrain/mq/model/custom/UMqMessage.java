package com.clinbrain.mq.model.custom;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@ToString
@Builder
public class UMqMessage implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "")
    private Long id;

    @ApiModelProperty(value = "消息类型， sms, email ...")
    private String messageGenre;

    @ApiModelProperty(value = "消息状态，准备发送，发送失败，发送成功")
    private String status;

    @ApiModelProperty(value = " > 0 重试次数")
    private Integer retry;

    @ApiModelProperty(value = "延迟时间点")
    private String delay;

    @ApiModelProperty(value = "指定人")
    private String assignTo;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "发送内容")
    private String content;

    @ApiModelProperty(value = "引用模板")
    private Long templateId;

    @ApiModelProperty(value = "追踪标识")
    private String traceId;

    @ApiModelProperty(value = "原始信息")
    private String originalData;

    @ApiModelProperty(value = "日志记录信息")
    private String log;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "标识过期")
    private String expired;

    @ApiModelProperty(value = "附件列表。多个附近以||分割（双竖线）")
    private String attachPaths;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id =  id;
    }
    @JsonProperty("messageGenre")
    public String getMessageGenre() {
        return messageGenre;
    }

    public void setMessageGenre(String messageGenre) {
        this.messageGenre =  messageGenre;
    }
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status =  status;
    }
    @JsonProperty("retry")
    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry =  retry;
    }
    @JsonProperty("delay")
    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay =  delay;
    }
    @JsonProperty("assignTo")
    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo =  assignTo;
    }
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title =  title;
    }
    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content =  content;
    }
    @JsonProperty("templateId")
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId =  templateId;
    }
    @JsonProperty("traceId")
    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId =  traceId;
    }
    @JsonProperty("originalData")
    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData =  originalData;
    }
    @JsonProperty("log")
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log =  log;
    }
    @JsonProperty("createTime")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime =  createTime;
    }
    @JsonProperty("updateTime")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime =  updateTime;
    }
    @JsonProperty("expired")
    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired =  expired;
    }
    @JsonProperty("attachPaths")
    public String getAttachPaths() {
        return attachPaths;
    }

    public void setAttachPaths(String attachPaths) {
        this.attachPaths =  attachPaths;
    }


    public UMqMessage(Long id,String messageGenre,String status,Integer retry,String delay,String assignTo,String title,String content,Long templateId,String traceId,String originalData,String log,Date createTime,Date updateTime,String expired,String attachPaths) {

        this.id = id;

        this.messageGenre = messageGenre;

        this.status = status;

        this.retry = retry;

        this.delay = delay;

        this.assignTo = assignTo;

        this.title = title;

        this.content = content;

        this.templateId = templateId;

        this.traceId = traceId;

        this.originalData = originalData;

        this.log = log;

        this.createTime = createTime;

        this.updateTime = updateTime;

        this.expired = expired;

        this.attachPaths = attachPaths;

    }

    public UMqMessage() {
        super();
    }

    public String dateToStringConvert(Date date) {
        if(date!=null) {
            return DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        }
        return "";
    }
}
