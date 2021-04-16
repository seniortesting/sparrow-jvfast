package com.jvfast.notification.sms.entity;

import lombok.Data;

@Data
public class SmsResponse {
    private String Message;
    private String RequestId;
    private String BizId;
    private String Code;
}
