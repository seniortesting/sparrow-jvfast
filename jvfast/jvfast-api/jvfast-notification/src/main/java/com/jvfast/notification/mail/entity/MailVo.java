package com.jvfast.notification.mail.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Map;

/**
 * @author Administrator
 */
@Data
public class MailVo {
    //邮件发送人
    private String from;
    //邮件接收人（多个邮箱则用逗号","隔开）
    @NotEmpty(message = "邮件接收人不能为空")
    private String to;
    //抄送（多个邮箱则用逗号","隔开）
    private String cc;
    //密送（多个邮箱则用逗号","隔开）
    private String bcc;
    //邮件主题
    @NotEmpty(message = "邮件主题不能为空")
    private String subject;
    //邮件内容
    private String template;
    private Map<String, Object> model;
    private String content;
    //发送时间
    private Date sentDate;
    //状态
    private Boolean status;
    //报错信息
    private String error;

}
