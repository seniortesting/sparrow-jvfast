package com.jvfast.notification.sms.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SmsRequest {

    private List<String> phones;
    private String templateCode;
    private Map<String, String> params;
}
