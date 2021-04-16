package com.jvfast.common.config.captcha;

import com.jvfast.common.config.JVFastCommonProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(JVFastCommonProperties.class)
public class CaptchaConfig {

    @Bean
    public Captcha defaultKaptcha(JVFastCommonProperties jvFastCommonProperties) {
        JVFastCommonProperties.Captcha captcha = jvFastCommonProperties.getCaptcha();
        EasyCaptcha easyCaptcha = new EasyCaptcha(captcha.getWidth(), captcha.getHeight());
        return easyCaptcha;
    }
}
