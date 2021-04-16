package com.jvfast.common.util;

import com.jvfast.common.constant.NotificationConst;
import com.jvfast.notification.wx.service.WxMpTemplateMsgService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Slf4j
public class NotificationUtil {

    /**
     * 发送报告完成微信通知
     *
     * @param openId
     * @param title
     * @param proposer
     * @param time
     * @param department
     * @param content
     * @param remark
     * @param miniPath
     */
    public static void sendMpReportNotification(String openId,
                                                String miniPath,
                                                String title,
                                                String proposer,
                                                String time,
                                                String department,
                                                String content,
                                                String remark) {
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
                .templateId(NotificationConst.MP_TEMPLATE_REPORT_COMPLETE)
                .toUser(openId)
                .miniProgram(new WxMpTemplateMessage.MiniProgram(NotificationConst.APPID_MA, miniPath, false))
                .build();
        // 添加模板数据
        wxMpTemplateMessage
                .addData(new WxMpTemplateData("first", title, NotificationConst.COLOR_GREEN))
                .addData(new WxMpTemplateData("keyword1", proposer, NotificationConst.COLOR_BLACK))
                .addData(new WxMpTemplateData("keyword2", time, NotificationConst.COLOR_BLACK))
                .addData(new WxMpTemplateData("remark", content, NotificationConst.COLOR_BLACK));
        log.info("准备发送公众号消息到用户openId: {},消息内容是: {}", openId, wxMpTemplateMessage.toJson());
        mpTemplateNotification(wxMpTemplateMessage);
    }

    private static void mpTemplateNotification(WxMpTemplateMessage wxMpTemplateMessage) {
        WxMpTemplateMsgService wxMpTemplateMsgService = SpringUtil.getBean(WxMpTemplateMsgService.class);
        wxMpTemplateMsgService.sendTemplateMsg(NotificationConst.APPID_MP, wxMpTemplateMessage);
    }

}
