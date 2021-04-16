package com.jvfast.notification.wx.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUniformMessage;
import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.notification.common.entity.NotificationVo;
import com.jvfast.notification.common.enums.NotificationTypeEnum;
import com.jvfast.notification.common.event.NotificationEvent;
import com.jvfast.notification.wx.service.WxMaMsgService;
import com.jvfast.wx.miniapp.config.WxMaConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;

@Slf4j
@RequiredArgsConstructor
public class WxMaMsgServiceImpl implements WxMaMsgService {

    private final AsyncTask asyncTask;

    @Override
    public void sendKefuMsg(String appId, WxMaKefuMessage message) {
        asyncTask.executeTask(() -> {
            boolean resultStatus = false;
            String errorMsg = "";
            try {
                log.info("发送小程序客服消息，消息内容是: {}",message.toJson());
                WxMaService wxMaService = WxMaConfiguration.getMaService(appId);
                cn.binarywang.wx.miniapp.api.WxMaMsgService msgService = wxMaService.getMsgService();
                resultStatus = msgService.sendKefuMsg(message);
                log.info("发送小程序消息成功!, appId： {},发送给: {},", appId, message.getToUser());
            } catch (Exception e) {
                errorMsg = e.getMessage();
                resultStatus = false;
                log.error("发送小程序消息异常,错误信息: ",e );
            } finally {
                saveMaMessage(message.getText().getContent(),
                        message.toJson(),
                        message.getToUser(),
                        resultStatus, errorMsg);
            }
        });
    }

    @Override
    public void sendTemplateMsg(String appId, WxMaTemplateMessage msg) {
        asyncTask.executeTask(() -> {
            boolean resultStatus = false;
            String errorMsg = "";
            try {
                log.info("发送小程序模板消息，消息内容是: {}",msg.toJson());
                WxMaService wxMaService = WxMaConfiguration.getMaService(appId);
                cn.binarywang.wx.miniapp.api.WxMaMsgService msgService = wxMaService.getMsgService();
                msgService.sendTemplateMsg(msg);
                log.info("发送小程序模板消息成功，消息内容是: {}",msg.toJson());
                resultStatus = true;
            } catch (Exception e) {
                errorMsg = e.getMessage();
                resultStatus = false;
                log.error("发送小程序模板消息失败，错误信息是: ",e);
            } finally {
                saveMaMessage(msg.getTemplateId(),
                        msg.toJson(),
                        msg.getToUser(),
                        resultStatus, errorMsg);
            }
        });
    }

    @Override
    public void sendSubscribeMsg(String appId, WxMaSubscribeMessage subscribeMessage) {
        asyncTask.executeTask(() -> {
            boolean resultStatus = false;
            String errorMsg = "";
            try {
                log.info("发送小程序订阅消息，消息内容是: {}",subscribeMessage.toJson());
                WxMaService wxMaService = WxMaConfiguration.getMaService(appId);
                cn.binarywang.wx.miniapp.api.WxMaMsgService msgService = wxMaService.getMsgService();
                msgService.sendSubscribeMsg(subscribeMessage);
                resultStatus = true;
                log.info("发送小程序订阅消息成功，消息内容是: {}",subscribeMessage.toJson());
            } catch (Exception e) {
                errorMsg = e.getMessage();
                resultStatus = false;
                log.error("发送小程序订阅消息异常，错误信息是: ",e);
            } finally {
                saveMaMessage(subscribeMessage.getTemplateId(),
                        subscribeMessage.toJson(),
                        subscribeMessage.getToUser(),
                        resultStatus, errorMsg);
            }
        });
    }

    @Override
    public void sendUniformMsg(String appId, WxMaUniformMessage uniformMessage) {
        asyncTask.executeTask(() -> {
            boolean resultStatus = false;
            String errorMsg = "";
            try {
                log.info("发送小程序公众号统一消息，消息内容是: {}",uniformMessage.toJson());
                WxMaService wxMaService = WxMaConfiguration.getMaService(appId);
                cn.binarywang.wx.miniapp.api.WxMaMsgService msgService = wxMaService.getMsgService();
                msgService.sendUniformMsg(uniformMessage);
                resultStatus = true;
            } catch (Exception e) {
                errorMsg = e.getMessage();
                resultStatus = false;
                log.error("发送小程序公众号统一消息未捕获的异常,异常信息是: ",e);
            } finally {
                saveMaMessage(uniformMessage.getTemplateId(),
                        uniformMessage.toJson(),
                        uniformMessage.getToUser(),
                        resultStatus, errorMsg);
            }
        });
    }

    /**
     * 保存发送的消息
     *
     * @param title
     * @param content
     * @param resultStatus
     * @param errorMsg
     */
    private void saveMaMessage(String title, String content, String userId, boolean resultStatus, String errorMsg) {
        NotificationVo notificationVo = new NotificationVo();
        notificationVo.setTitle(title);
        notificationVo.setContent(content);
        notificationVo.setUserId(userId);
        notificationVo.setResultStatus(resultStatus);
        notificationVo.setRemark(errorMsg);
        notificationVo.setNotificationType(NotificationTypeEnum.WECHAT_MINIPROGRAM_MESSAGE);
        log.info("保存微信小程序发送的消息，消息内容是: {}",notificationVo.toString());
        SpringUtil.publishEvent(new NotificationEvent(notificationVo));
    }
}
