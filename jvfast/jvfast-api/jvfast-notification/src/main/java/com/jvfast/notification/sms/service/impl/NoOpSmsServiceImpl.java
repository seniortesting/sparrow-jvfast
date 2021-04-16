package com.jvfast.notification.sms.service.impl;

import com.jvfast.notification.sms.config.SmsProperties;
import com.jvfast.notification.sms.entity.SmsRequest;
import com.jvfast.notification.sms.entity.SmsVerifyCodeRequest;
import com.jvfast.notification.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
public class NoOpSmsServiceImpl implements SmsService {
    private SmsProperties smsProperties;

    public NoOpSmsServiceImpl(SmsProperties smsProperties) {
        this.smsProperties = smsProperties;
    }

    @Override
    public boolean sendVerifyCode(SmsVerifyCodeRequest smsVerifyCodeRequest) {
        return false;
    }

    @Override
    public boolean send(SmsRequest smsRequest) {
        return false;
    }

    @Override
    public boolean checkVerifyCode(String phone, String code) {
        return false;
    }
}
