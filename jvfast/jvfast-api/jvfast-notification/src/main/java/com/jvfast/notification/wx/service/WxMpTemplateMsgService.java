package com.jvfast.notification.wx.service;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

public interface WxMpTemplateMsgService {

    /**
     * 发送微信公众号模版消息
     */
    void sendTemplateMsg(String appId, WxMpTemplateMessage msg);
}
