package com.jvfast.notification.mail.config;

import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.notification.mail.service.MailService;
import com.jvfast.notification.mail.service.impl.MailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件配置服务
 * @author Walter
 */
@RequiredArgsConstructor
@Configuration
public class MailConfig {

    private final ConfigurableApplicationContext configurableApplicationContext;
    private final JavaMailSenderImpl mailSender;

    private final freemarker.template.Configuration configuration;

    private final AsyncTask asyncTask;

    @Bean
    public MailService mailService() {
        return new MailServiceImpl(configurableApplicationContext, mailSender, configuration, asyncTask);
    }
}
