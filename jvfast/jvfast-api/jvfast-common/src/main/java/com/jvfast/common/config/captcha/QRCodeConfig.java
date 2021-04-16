package com.jvfast.common.config.captcha;

import com.jvfast.common.config.JVFastCommonProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(JVFastCommonProperties.class)
public class QRCodeConfig {

    @Bean
    public QRCode defaultQRCode(JVFastCommonProperties jvFastCommonProperties) {
        JVFastCommonProperties.QRCode qrcode = jvFastCommonProperties.getQrcode();
        HutoolQRCode hutoolQRCode = new HutoolQRCode(qrcode.getWidth(), qrcode.getHeight());
        return hutoolQRCode;
    }
}
