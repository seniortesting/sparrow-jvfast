package com.jvfast.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class NotificationConst {
    /**
     * 对应该项目对应的小程序appId和公众号appId,用于消息发送
     */
    public static final String APPID_MA = "wx0202487f59c1bc62";
    public static final String APPID_MP = "wx033237fec82ddfb0";

    /**
     * 微信公众号模板消息内容颜色
     */
    public static final String COLOR_RED = "#F50C05";
    public static final String COLOR_BLUE = "#1890ff";
    public static final String COLOR_YELLOW = "#fbbd08";
    public static final String COLOR_GREEN = "#39b54a";
    public static final String COLOR_BLACK = "#000000";

    /**
     * 一些默认的公众号的微信用户
     */
    @AllArgsConstructor
    @Getter
    public enum DefaultOpenIds {
        /**
         * 一些默认的公众号微信用户
         */
        WALTER(""),
        SHAUN("");

        private String openId;
    }

    /**
     * 邮件通知模板
     */
    public static final int EMAIL_VERIFYCODE_EXPIRED_HOURS = 24;
    public static final String EMAIL_FORGET_PASSWORD = "mail/forgetpwd.ftl";

    /**
     * 短信通知模板
     */
    public static final int VERIFY_CODE_LENGTH = 6;

    public static final String SMS_TEMPLATE_CODE_LOGIN = "SMS_174992747";
    public static final String SMS_TEMPLATE_CODE_REGISTER = "SMS_175495045";
    public static final String SMS_TEMPLATE_CODE_FREE_USE = "SMS_174988587";
    public static final String SMS_TEMPLATE_CODE_FORGET_PASSWORD = "SMS_174992753";
    public static final String SMS_TEMPLATE_CODE_BINDING_ACCOUNT = "SMS_192195642";
    /**
     * 微信公众号通知模板
     */
    public static final String MP_TEMPLATE_REPORT_COMPLETE = "IJR_maCynKsnafEWAkvr9Vg2bLEAmcKk_2RWJpUclaSw23W";
}
