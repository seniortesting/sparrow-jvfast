package com.jvfast.notification.mail.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.common.exception.BusinessException;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.common.util.TemplateUtil;
import com.jvfast.notification.common.entity.NotificationVo;
import com.jvfast.notification.common.enums.NotificationTypeEnum;
import com.jvfast.notification.common.event.NotificationEvent;
import com.jvfast.notification.mail.entity.MailVo;
import com.jvfast.notification.mail.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author Walter
 */
@Slf4j
public class MailServiceImpl implements MailService {
    private ConfigurableApplicationContext configurableApplicationContext;
    private JavaMailSenderImpl mailSender;

    private Configuration configuration;

    private AsyncTask asyncTask;

    public MailServiceImpl(ConfigurableApplicationContext configurableApplicationContext, JavaMailSenderImpl mailSender, Configuration configuration, AsyncTask asyncTask) {
        this.configurableApplicationContext = configurableApplicationContext;
        this.mailSender = mailSender;
        this.configuration = configuration;
        this.asyncTask = asyncTask;
    }

    /**
     * 发送邮件
     *
     * @param MailVo
     * @return
     */
    @Override
    public void sendEmail(MailVo MailVo) {
        this.asyncTask.executeTask(() -> {
            try {
                //1.检测邮件
                checkMail(MailVo);
                //2.发送邮件
                sendMimeMail(MailVo);
                //3.保存邮件
                MailVo.setStatus(true);
            } catch (BusinessException e) {
                //打印错误信息
                log.error("发送邮件失败:", e);
                MailVo.setStatus(false);
                MailVo.setError(e.getMessage());
            }finally {
                saveMail(MailVo);
            }
        });
    }

    /**
     * 工具方法，发送邮件
     *
     * @param mailVo
     */
    private void sendMimeMail(MailVo mailVo) {
        try {
            String from = getMailSendFrom();
            String mailSendFromPersonal = getMailSendFromPersonal();

            String mailFrom = mailVo.getFrom();
            String to = mailVo.getTo();
            String cc = mailVo.getCc();
            String bcc = mailVo.getBcc();
            String subject = mailVo.getSubject();
            String content = mailVo.getContent();
            Date sentDate = mailVo.getSentDate();
            String emailTemplate = mailVo.getTemplate();
            Map<String, Object> model = mailVo.getModel();
            mailFrom = StrUtil.isEmpty(mailFrom) ? from : mailFrom;
            String fromPersonal = StrUtil.isNotEmpty(mailSendFromPersonal) ? mailSendFromPersonal : this.configurableApplicationContext.getEnvironment().getProperty("spring.application.name");
            sentDate = Objects.isNull(sentDate) ? new Date() : sentDate;
            mailVo.setFrom(mailFrom);
            mailVo.setSentDate(sentDate);
            if (!Objects.isNull(emailTemplate)) {
                Template template = this.configuration.getTemplate(emailTemplate);
                content = TemplateUtil.processTemplate(template, model);
                mailVo.setContent(content);
            }
            //true表示支持复杂类型
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(mailFrom, fromPersonal);
            messageHelper.setTo(to.split(","));
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            messageHelper.setSentDate(sentDate);
            //抄送
            if (!StrUtil.isEmpty(mailVo.getCc())) {
                messageHelper.setCc(cc.split(","));
            }
            //密送
            if (!StrUtil.isEmpty(mailVo.getBcc())) {
                messageHelper.setBcc(bcc.split(","));
            }
            // 是否有附件
//            messageHelper.addAttachment();
            // 发送,主要是构造： MimeMessage
            this.mailSender.send(messageHelper.getMimeMessage());
        } catch (Exception e) {
            //发送失败
            throw new BusinessException(e);
        }
    }

    /**
     * 持久化保存邮件内容
     *
     * @param MailVo
     * @return
     */
    private void saveMail(MailVo MailVo) {
        String to = MailVo.getTo();
        String subject = MailVo.getSubject();
        String content = MailVo.getContent();
        Boolean status = MailVo.getStatus();
        String error = MailVo.getError();
        NotificationVo notificationVo = new NotificationVo();
        notificationVo.setTitle(subject);
        notificationVo.setContent(content);
        notificationVo.setUserId(to);
        notificationVo.setResultStatus(status);
        notificationVo.setRemark(error);
        notificationVo.setNotificationType(NotificationTypeEnum.EMAIL);
        SpringUtil.publishEvent(new NotificationEvent(notificationVo));
    }

    /**
     * 检查邮件设置
     *
     * @param MailVo
     */
    private void checkMail(MailVo MailVo) {
        if (StrUtil.isEmpty(MailVo.getTo())) {
            throw new BadRequestException("邮件收信人不能为空");
        }
        if (StrUtil.isEmpty(MailVo.getSubject())) {
            throw new BadRequestException("邮件主题不能为空");
        }
        if (StrUtil.isEmpty(MailVo.getContent()) && StrUtil.isEmpty(MailVo.getTemplate())) {
            throw new BadRequestException("邮件内容不能为空");
        }
    }

    private String getMailSendFrom() {
        return this.mailSender.getJavaMailProperties().getProperty("mail.smtp.from");
    }

    private String getMailSendFromPersonal() {
        return this.mailSender.getJavaMailProperties().getProperty("mail.smtp.from-personal");
    }
}
