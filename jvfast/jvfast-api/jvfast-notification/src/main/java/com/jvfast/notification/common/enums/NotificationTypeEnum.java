package com.jvfast.notification.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationTypeEnum {

    /**
     * 发送通知的方式
     */
    NOTICE(1, "站内信"),
    EMAIL(2, "邮件"),
    SMS(3, "短信"),
    WECHAT_OFFICIAL_ACCOUNT(4, "微信公众号"),
    WECHAT_MINIPROGRAM_MESSAGE(5, "微信小程序消息"),
    APP_MESSAGE(6, "APP消息");

    private Integer code;
    private String desc;

    NotificationTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @JsonCreator
    public static NotificationTypeEnum getByCode(Integer code) {
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if (notificationTypeEnum.code.equals(code)) {
                return notificationTypeEnum;
            }
        }
        return null;
    }

    @JsonValue
    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
