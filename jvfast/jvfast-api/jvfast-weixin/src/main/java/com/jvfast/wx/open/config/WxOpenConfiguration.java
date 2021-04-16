package com.jvfast.wx.open.config;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(WxOpenProperties.class)
public class WxOpenConfiguration {

    private final RedisTemplate redisTemplate;
    private final WxOpenProperties wxOpenProperties;

    @Bean
    public WxOpenService wxOpenService() {
        WxOpenServiceImpl wxOpenService = new WxOpenServiceImpl();
        WxOpenRedisStorageImpl wxOpenRedisStorage = new WxOpenRedisStorageImpl(redisTemplate);
        if (StrUtil.isNotEmpty(wxOpenProperties.getAppId())
                && StrUtil.isNotEmpty(wxOpenProperties.getSecret())
                && StrUtil.isNotEmpty(wxOpenProperties.getToken())
                && StrUtil.isNotEmpty(wxOpenProperties.getAesKey())) {
            wxOpenRedisStorage.setComponentAppId(wxOpenProperties.getAppId());
            wxOpenRedisStorage.setComponentAppSecret(wxOpenProperties.getSecret());
            wxOpenRedisStorage.setComponentToken(wxOpenProperties.getToken());
            wxOpenRedisStorage.setComponentAesKey(wxOpenProperties.getAesKey());
        }
        wxOpenService.setWxOpenConfigStorage(wxOpenRedisStorage);
        return wxOpenService;
    }

    @Bean
    public WxOpenMessageRouter wxOpenMessageRouter(WxOpenService wxOpenService) {
        WxOpenMessageRouter wxOpenMessageRouter = new WxOpenMessageRouter(wxOpenService);
        wxOpenMessageRouter.rule().handler((wxMpXmlMessage, map, wxMpService, wxSessionManager) -> {
            log.info("\n接收到 {} 公众号请求消息，内容：{}", wxMpService.getWxMpConfigStorage().getAppId(), wxMpXmlMessage);
            return null;
        }).next();
        return wxOpenMessageRouter;
    }
}
