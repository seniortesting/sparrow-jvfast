package com.jvfast.wx.mp.handler;

import com.jvfast.common.util.SpringUtil;
import com.jvfast.wx.common.converter.WxUserConverter;
import com.jvfast.wx.common.entity.WxUserDetail;
import com.jvfast.wx.common.event.WxUserInfoStoreEvent;
import com.jvfast.wx.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Slf4j
@Component
public class SubscribeHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) throws WxErrorException {
        log.info("新关注用户 OPENID: " + wxMessage.getFromUser() + "，事件：" + wxMessage.getEventKey());

        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = wxMpService.getUserService()
                    .userInfo(wxMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // 添加到本地数据库
                WxUserDetail wxUserDetail = WxUserConverter.INSTANCE.fromWxMpUser(userWxInfo);
                String appId = wxMpService.getWxMpConfigStorage().getAppId();
                wxUserDetail.setAppId(appId);
                SpringUtil.publishEvent(new WxUserInfoStoreEvent(wxUserDetail));
            }
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                log.info("该公众号没有获取用户信息权限！");
            }
        }


        WxMpXmlOutMessage responseResult = null;
        try {
            log.info("返回关注后自动回复信息");
            responseResult = this.handleSpecial(wxMpService, wxMessage);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        if (responseResult != null) {
            return responseResult;
        }

        try {
            return new TextBuilder().build("感谢关注!", wxMessage, wxMpService);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    protected WxMpXmlOutMessage handleSpecial(WxMpService wxMpService, WxMpXmlMessage wxMessage) throws Exception {
        log.info("特殊请求-新关注用户 OPENID: " + wxMessage.getFromUser());
        //TODO 拼接不同的公众号回复信息
        String appId = wxMpService.getWxMpConfigStorage().getAppId();
        String msg = "感谢关注,我们会定期推荐相关优质资讯信息，敬请期待！";
        log.info("关注公众号: {}自动回复信息是: {}", appId, msg);
        return new TextBuilder().build(msg, wxMessage, wxMpService);
    }

}
