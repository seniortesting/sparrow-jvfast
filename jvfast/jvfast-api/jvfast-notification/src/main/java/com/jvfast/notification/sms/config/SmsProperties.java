package com.jvfast.notification.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@Data
@ConfigurationProperties(prefix = "jvfast.sms")
public class SmsProperties {
    private SmsType type;
    private int expiredSeconds;
    private ALiYun aliyun;
    private MobTech mob;

    @Data
    public static class ALiYun {
        @NotBlank(message = "阿里云accessKeyId不能为空")
        private String accessKeyId;
        @NotBlank(message = "阿里云accessKeySecret不能为空")
        private String accessKeySecret;
        private String signName;
    }

    @Data
    public static class MobTech {

    }
}
