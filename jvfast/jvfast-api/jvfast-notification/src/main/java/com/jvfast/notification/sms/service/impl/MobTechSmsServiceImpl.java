package com.jvfast.notification.sms.service.impl;

import com.jvfast.notification.sms.config.SmsProperties;
import com.jvfast.notification.sms.entity.SmsRequest;
import com.jvfast.notification.sms.entity.SmsVerifyCodeRequest;
import com.jvfast.notification.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
public class MobTechSmsServiceImpl implements SmsService {
    private SmsProperties smsProperties;

    public MobTechSmsServiceImpl(SmsProperties smsProperties) {
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

    @PostConstruct
    private void init() {
    }
}
