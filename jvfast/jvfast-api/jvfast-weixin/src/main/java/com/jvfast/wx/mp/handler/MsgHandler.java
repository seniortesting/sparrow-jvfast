package com.jvfast.wx.mp.handler;


import cn.hutool.core.util.StrUtil;
import com.jvfast.wx.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang
 */
@Component
@Slf4j
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) {
        if (!wxMpXmlMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }
//        boolean autoReplyed = weixinMsgService.wxReplyMsg(false,wxMessage.getFromUser(), textContent);
        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StrUtil.startWithAny(wxMpXmlMessage.getContent(), "你好", "客服")
                    && wxMpService.getKefuService().kfOnlineList()
                    .getKfOnlineList().size() > 0) {
                // 如果有客服
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                        .fromUser(wxMpXmlMessage.getToUser())
                        .toUser(wxMpXmlMessage.getFromUser()).build();
            } else if (StrUtil.equals(wxMpXmlMessage.getContent(), MenuHandler.MENU_KEY_KEYWORD)) {
                String msg = "点击链接: https://pingbook.top 获取更多资讯";
                return new TextBuilder().build(msg, wxMpXmlMessage, wxMpService);
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        String appId = wxMpService.getWxMpConfigStorage().getAppId();
        String msg = "感谢关注,我们会定期推荐相关优质资讯信息，敬请期待！";
        // TODO 拼接不同的公众号的不同消息回复
        log.info("默认公众号: {}消息提醒回复是: {}", appId, msg);
        return new TextBuilder().build(msg, wxMpXmlMessage, wxMpService);

    }

}
