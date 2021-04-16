package com.jvfast.notification.wx.service;

import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

public interface WxMaMsgService {

    /**
     * <pre>
     * 发送客服消息
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/customerServiceMessage.send.html">发送客服消息</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     * </pre>
     */
    void sendKefuMsg(String appId, WxMaKefuMessage message);
    /**
     * 发送微信小程序模版消息
     */
    void sendTemplateMsg(String appId, WxMaTemplateMessage msg);
    /**
     * <pre>
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
     * </pre>
     * 发送订阅消息
     */
    void sendSubscribeMsg(String appId, WxMaSubscribeMessage subscribeMessage);


    /**
     * <pre>
     * 下发小程序和公众号统一的服务消息
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html">下发小程序和公众号统一的服务消息</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     * </pre>
     */
    void sendUniformMsg(String appId, WxMaUniformMessage uniformMessage);
}
