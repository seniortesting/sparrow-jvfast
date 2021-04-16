package com.jvfast.notification.sms.service;

import com.jvfast.common.util.SpringUtil;
import com.jvfast.notification.common.entity.NotificationVo;
import com.jvfast.notification.common.enums.NotificationTypeEnum;
import com.jvfast.notification.common.event.NotificationEvent;
import com.jvfast.notification.sms.entity.SmsRequest;
import com.jvfast.notification.sms.entity.SmsVerifyCodeRequest;

public interface SmsService {

    /**
     * 异步发送短信通知
     *
     * @param subject
     * @param content
     * @param to
     */
    default void publishSmsNotification(String to,String subject, String content,Boolean result, String error) {
        NotificationVo notificationVo = new NotificationVo();
        notificationVo.setTitle(subject);
        notificationVo.setContent(content);
        notificationVo.setUserId(to);
        notificationVo.setResultStatus(result);
        notificationVo.setRemark(error);
        notificationVo.setNotificationType(NotificationTypeEnum.SMS);
        SpringUtil.publishEvent(new NotificationEvent(notificationVo));
    }

    boolean sendVerifyCode(SmsVerifyCodeRequest smsVerifyCodeRequest);

    /**
     * 发送短信
     *
     * @param smsRequest
     * @return
     */
    boolean send(SmsRequest smsRequest);

    /**
     * 校验短信验证码
     *
     * @return
     */
    boolean checkVerifyCode(String phone, String code);
}
