package com.jvfast.wx.mp.config;

import com.jvfast.common.exception.BusinessException;
import com.jvfast.wx.mp.handler.*;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Walter Hu
 */
@RequiredArgsConstructor
@Configuration
@ConditionalOnClass(WxMpService.class)
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
    private final WxMpProperties wxMpProperties;
    private final StringRedisTemplate stringRedisTemplate;

    private final LogHandler logHandler;
    private final NullHandler nullHandler;
    private final KfSessionHandler kfSessionHandler;
    private final StoreCheckNotifyHandler storeCheckNotifyHandler;
    private final LocationHandler locationHandler;
    private final MenuHandler menuHandler;
    private final MsgHandler msgHandler;
    private final UnsubscribeHandler unsubscribeHandler;
    private final SubscribeHandler subscribeHandler;
    private final ScanHandler scanHandler;

    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService() {
        List<WxMpProperties.MpConfig> configs = wxMpProperties.getConfigs();
        if (configs == null) {
            throw new BusinessException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
        }
        WxMpService service = new WxMpServiceImpl();
        if (!configs.isEmpty()) {
            service.setMultiConfigStorages(configs
                    .stream().map(a -> {
                        // 设置 WxMpDefaultConfigImpl
                        WxMpDefaultConfigImpl configStorage = new CustomWxMpRedisConfigImpl(stringRedisTemplate);

                        configStorage.setAppId(a.getAppId());
                        configStorage.setSecret(a.getSecret());
                        configStorage.setToken(a.getToken());
                        configStorage.setAesKey(a.getAesKey());

                        return configStorage;
                    }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
        }
        return service;
    }

    @Bean
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);
        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 接收客服会话管理事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION)
                .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION)
                .handler(this.kfSessionHandler).end();
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION)
                .handler(this.kfSessionHandler).end();

        // 门店审核事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(WxMpEventConstants.POI_CHECK_NOTIFY).handler(this.storeCheckNotifyHandler).end();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(EventType.CLICK).handler(this.menuHandler).end();

        // 点击菜单连接事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(EventType.VIEW).handler(this.nullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(EventType.SUBSCRIBE).handler(this.subscribeHandler).end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(EventType.UNSUBSCRIBE).handler(this.unsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(EventType.LOCATION).handler(this.locationHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(XmlMsgType.EVENT).event(EventType.SCAN).handler(this.scanHandler).end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(XmlMsgType.LOCATION).handler(this.locationHandler).end();
        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }
}
