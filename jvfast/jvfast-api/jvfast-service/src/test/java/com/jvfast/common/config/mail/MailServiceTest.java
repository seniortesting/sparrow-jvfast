package com.jvfast.common.config.mail;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Maps;
import com.jvfast.common.constant.NotificationConst;
import com.jvfast.notification.mail.entity.MailVo;
import com.jvfast.notification.mail.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailServiceTest {

    @Autowired
    private MailService MailService;

    @Test
    public void sendEmail() {
        MailVo MailVo = new MailVo();
        MailVo.setTo("1725641479@qq.com");
        MailVo.setSubject("测试邮箱");
        MailVo.setTemplate(NotificationConst.EMAIL_FORGET_PASSWORD);
        HashMap<String, Object> model = Maps.newHashMap();
        model.put("name", "walter");
        model.put("date", DateUtil.now());
        MailVo.setModel(model);
        MailService.sendEmail(MailVo);
    }
}
