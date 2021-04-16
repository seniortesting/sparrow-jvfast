package com.jvfast.notification.wx.service.impl;

import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.notification.common.entity.NotificationVo;
import com.jvfast.notification.common.enums.NotificationTypeEnum;
import com.jvfast.notification.common.event.NotificationEvent;
import com.jvfast.notification.wx.service.WxMpTemplateMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * @author Walter
 */
@Slf4j
@RequiredArgsConstructor
public class WxMpTemplateMsgServiceImpl implements WxMpTemplateMsgService {
    private final WxMpService wxMpService;
    private final AsyncTask asyncTask;

    @Override
    public void sendTemplateMsg(String appId, WxMpTemplateMessage msg) {
        asyncTask.executeTask(() -> {
            if (!wxMpService.switchover(appId)) {
                String errorMsg = String.format("未找到对应appid=[%s]的配置，请核实！", appId);
                log.error(errorMsg);
                throw new BadRequestException(errorMsg);
            }
            if (msg.getMiniProgram() != null) {
                WxMpTemplateMessage.MiniProgram miniProgram = msg.getMiniProgram();
                String pagePath = miniProgram.getPagePath();
                if (pagePath != null && !pagePath.startsWith("pages")) {
                    String errorMsg = String.format("小程序{}路径配置出错，需要以pages开头！", appId);
                    log.error(errorMsg);
                    throw new BadRequestException(errorMsg);
                }
            }
            NotificationVo notificationVo = new NotificationVo();
            boolean resultStatus = false;
            String errorMsg = "";
            try {
                log.info("准备发送微信公众号模板消息: {}", msg.toJson());
                me.chanjar.weixin.mp.api.WxMpTemplateMsgService templateMsgService = wxMpService.getTemplateMsgService();
                errorMsg = templateMsgService.sendTemplateMsg(msg);
                resultStatus = true;
                log.info("发送微信公众号消息成功!, appId： {},发送给: {},", appId, msg.getToUser());
            } catch (WxErrorException e) {
                errorMsg = e.getError().getErrorMsg();
                resultStatus = false;
                log.error("发送微信公众号消息异常,错误信息: ", e);
            } catch (Exception e) {
                errorMsg = e.getMessage();
                resultStatus = false;
                log.error("发送微信公众号消息遇到未捕获的异常,错误信息: ", e);
            } finally {
                notificationVo.setTitle(msg.getTemplateId());
                notificationVo.setContent(msg.toJson());
                notificationVo.setUserId(msg.getToUser());
                notificationVo.setResultStatus(resultStatus);
                notificationVo.setRemark(errorMsg);
                notificationVo.setNotificationType(NotificationTypeEnum.WECHAT_OFFICIAL_ACCOUNT);
                log.info("准备存储此处消息内容: {}", notificationVo.toString());
                SpringUtil.publishEvent(new NotificationEvent(notificationVo));
            }
        });
    }
}
