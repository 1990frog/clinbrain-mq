package com.clinbrain.mq.util;

import com.clinbrain.mq.model.custom.EmailSendEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @description  发送不同类型邮件工具，所有邮件均需要指定邮件模板
 *
 * @author hexun
 * @date 13:52 2021/8/27
 *
 */
@Component
public class EmailHandUtil {

    private final JavaMailSender javaMailSender;

    public EmailHandUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 文本邮件
     */
    public void sendSimpleEmail(EmailSendEntity entity) throws Exception{
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject(entity.getTitle());
        msg.setFrom(entity.getFrom());
        msg.setTo(String.join(",",entity.getTo()));      // 设置邮件接收者，可以有多个接收者
        msg.setSentDate(new Date());                              // 设置邮件发送日期
        msg.setText(entity.getContent());                         // 设置邮件的正文
        javaMailSender.send(msg);
    }

    /**
     * HTML邮件,图片显示使用BASE64编码字符创
     */
    public void sendHtmlEmail(EmailSendEntity entity) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(String.join(",",entity.getTo()));
        helper.setFrom(entity.getFrom());
        helper.setSubject(entity.getTitle());
        helper.setText(entity.getContent(), true);
        javaMailSender.send(message);
    }

    /**
     * 发送混合邮件。图片使用base64处理，通过模板指定参数顺序
     */
    public void sendMixedEmail(EmailSendEntity entity, Optional<List<String>> attachPaths) throws Exception{
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject(entity.getTitle());
        message.setFrom(new InternetAddress(entity.getFrom()));
        entity.getTo().forEach(to ->{
            try {
                message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        message.setSentDate(new Date());

        // 添加附件，可以添加多个
        //一个Multipart对象包含一个或多个MimeBodyPart对象(文字/图片/文件)，组成邮件正文
        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart text = new MimeBodyPart();
        text.setContent(entity.getContent(),"text/html;charset=UTF-8");
        if(attachPaths.isPresent()){
            MimeBodyPart attachment = null;
            DataHandler dataHandler = null;
            for(String path : attachPaths.get()){
                attachment = new MimeBodyPart();
                dataHandler = new DataHandler(new FileDataSource(path));
                attachment.setDataHandler(dataHandler);
                attachment.setFileName(MimeUtility.encodeText(dataHandler.getName()));
                multipart.addBodyPart(attachment);
            }
        }

        multipart.setSubType("mixed");
        message.setContent(multipart);
        message.saveChanges();
        javaMailSender.send(message);
    }

    /**
     * 带附件的HTML邮件样例
     */
    private void sendHtmlAttachEmail(EmailSendEntity entity){
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setSubject(entity.getTitle());
            message.setFrom(new InternetAddress(entity.getFrom()));
            entity.getTo().forEach(to ->{
                try {
                    message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            message.setSentDate(new Date());

            //一个Multipart对象包含一个或多个MimeBodyPart对象(文字/图片/文件)，组成邮件正文
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart text = new MimeBodyPart();
            text.setContent("图片文字描述，样式与内容自定义<br/><img src='data:image/gif;base64,xxxxxxxxxxxxxxx'/>","text/html;charset=UTF-8");

            // 添加附件，可以添加多个
            // FileUtil.uploadPath() 获取文件保存目录，读取指定文件
            MimeBodyPart attachment = new MimeBodyPart();
            DataHandler dataHandler3 = new DataHandler(new FileDataSource("C:\\Users\\41475\\Desktop\\各种信息.xlsx"));
            attachment.setDataHandler(dataHandler3);
            attachment.setFileName(MimeUtility.encodeText(dataHandler3.getName()));

            multipart.addBodyPart(text);
            multipart.addBodyPart(attachment);
            multipart.setSubType("mixed");
            message.setContent(multipart);
            message.saveChanges();
            javaMailSender.send(message);
        }catch (Exception e){
            e.getMessage();
        }
    }
}
