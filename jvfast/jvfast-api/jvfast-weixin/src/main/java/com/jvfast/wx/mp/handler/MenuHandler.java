package com.jvfast.wx.mp.handler;

import cn.hutool.core.util.StrUtil;
import com.jvfast.wx.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
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
public class MenuHandler extends AbstractHandler {
    private static final String MENU_KEY_HELP = "KEY_HELP";
    public static final String MENU_KEY_KEYWORD = "?";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager wxSessionManager) {
        // 如果是自定义的菜单，则不响应时间
        if (WxConsts.EventType.VIEW.equals(wxMessage.getEvent())) {
            return null;
        }
        if (wxMessage.getEventKey().equals(MENU_KEY_HELP)) {
            String msg = "获取帮助，您可发送“"+MENU_KEY_KEYWORD+"”到该公众号查询相关信息。";
            return new TextBuilder().build(msg, wxMessage, wxMpService);
        }
//        weixinMsgService.wxReplyMsg(true,wxMessage.getFromUser(), wxMessage.getEventKey());
        return WxMpXmlOutMessage.TEXT().content("感谢关注,我们会定期推荐相关优质资讯信息，敬请期待！")
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
    }


}
