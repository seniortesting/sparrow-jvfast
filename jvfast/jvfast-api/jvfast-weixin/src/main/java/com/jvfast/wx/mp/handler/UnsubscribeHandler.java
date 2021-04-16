package com.jvfast.wx.mp.handler;

import com.jvfast.common.util.SpringUtil;
import com.jvfast.wx.common.event.WxMpUnsubscribleUserEvent;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Slf4j
@Component
public class UnsubscribeHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) {
        String openId = wxMessage.getFromUser();
        log.info("取消关注用户 OPENID: " + openId);
        SpringUtil.publishEvent(new WxMpUnsubscribleUserEvent(openId));
        return null;
    }

}
