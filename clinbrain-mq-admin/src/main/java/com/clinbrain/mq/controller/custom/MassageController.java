package com.clinbrain.mq.controller.custom;

import com.alibaba.fastjson.JSON;
import com.clinbrain.mq.common.domain.AjaxResult;
import com.clinbrain.mq.common.message.EmailMessage;
import com.clinbrain.mq.service.MassageService;
import com.clinbrain.mq.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class MassageController {

    @Autowired
    private MassageService massageService;

    @PostMapping("/email")
    public Object sendEmail(@RequestBody EmailMessage emailMessage){
        log.info("[{}]-接收到消息:[{}]", LocalDateTime.now().toString(), JSON.toJSONString(emailMessage));
        if("fail".equals(massageService.sendEmail(emailMessage,Optional.empty()))){
            return AjaxResult.error();
        }else{
            return AjaxResult.success();
        }
    }

    /**
     * @description
     *
     * @author hexun
     * @param files 邮件附件列表
     * @param emailMessage 邮件具体内容
     * @return 操作响应
     */
    @PostMapping("/email/attach")
    public Object upload(@RequestParam("file")MultipartFile[] files,
                         @RequestPart EmailMessage emailMessage){
        log.info("[{}]-接收发送附件邮件消息:[{}]", LocalDateTime.now().toString(), JSON.toJSONString(emailMessage));
        // 1，上传文件,上传失败返回错误信息
        List<String> filePaths = new ArrayList<>(files.length);
        for (MultipartFile file : files){
            if(!FileUtil.upload(file)){
                return AjaxResult.error(500,"文件"+file.getOriginalFilename()+"上传失败,邮件将不会发送");
            }
            filePaths.add(FileUtil.uploadPath() + file.getOriginalFilename());
        }
        // 2,文件上传成功后开始发送邮件
        if("fail".equals(massageService.sendEmail(emailMessage, Optional.ofNullable(filePaths)))){
            return AjaxResult.error();
        }else{
            return AjaxResult.success();
        }
    }

    public void restTemplateUploadFile(){
        final String url = "http://localhost:8801/email/attach";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        // 文件
        FileSystemResource resource = new FileSystemResource("C:\\Users\\41475\\Desktop\\1111.png");
        FileSystemResource resource1 = new FileSystemResource("C:\\Users\\41475\\Desktop\\rest client请求.txt");
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", resource);
        form.add("file", resource1);

        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setTitle("测试邮件标题");
        // 其它属性填充
        form.add("emailMessage", emailMessage);

        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        AjaxResult s = restTemplate.postForObject(url, files, AjaxResult.class);
        System.out.println(s.get("code"));
    }
}
