package com.jvfast.notification.sms.config;

import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.config.redis.RedisClient;
import com.jvfast.notification.sms.service.SmsService;
import com.jvfast.notification.sms.service.impl.ALiYunSmsServiceImpl;
import com.jvfast.notification.sms.service.impl.MobTechSmsServiceImpl;
import com.jvfast.notification.sms.service.impl.NoOpSmsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsConfig {

    private final SmsProperties smsProperties;
    private final RedisClient redisClient;
    private final AsyncTask asyncTask;

    @Bean
    @ConditionalOnMissingBean
    public SmsService smsService() {
        SmsType type = this.smsProperties.getType();
        if (type.equals(SmsType.ALIYUN)) {
            return new ALiYunSmsServiceImpl(this.smsProperties, this.redisClient, this.asyncTask);
        } else if (type.equals(SmsType.MOBTECH)) {
            return new MobTechSmsServiceImpl(this.smsProperties);
        }
        return new NoOpSmsServiceImpl(this.smsProperties);
    }
}
