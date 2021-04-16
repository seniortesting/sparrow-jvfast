package com.jvfast.notification.wx.config;

import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.notification.wx.service.WxMaMsgService;
import com.jvfast.notification.wx.service.WxMpTemplateMsgService;
import com.jvfast.notification.wx.service.impl.WxMaMsgServiceImpl;
import com.jvfast.notification.wx.service.impl.WxMpTemplateMsgServiceImpl;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class WxConfig {

    private final WxMpService wxMpService;
    private final AsyncTask asyncTask;

    /**
     * 公众号消息接口
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public WxMpTemplateMsgService wxMpTemplateMsgService() {
        return new WxMpTemplateMsgServiceImpl(this.wxMpService, this.asyncTask);
    }

    /**
     * 小程序消息接口
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public WxMaMsgService wxMaMsgService() {
        return new WxMaMsgServiceImpl(this.asyncTask);
    }

}
