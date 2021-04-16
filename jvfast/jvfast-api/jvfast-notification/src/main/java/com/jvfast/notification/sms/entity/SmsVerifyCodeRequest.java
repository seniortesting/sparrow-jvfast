package com.jvfast.notification.sms.entity;

import lombok.Data;

import java.util.List;

@Data
public class SmsVerifyCodeRequest {

    private String templateCode;
    private String verifyCode;
    private List<String> phones;
}
