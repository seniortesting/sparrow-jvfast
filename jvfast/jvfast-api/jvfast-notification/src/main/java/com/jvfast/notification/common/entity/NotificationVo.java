package com.jvfast.notification.common.entity;

import com.jvfast.notification.common.enums.NotificationTypeEnum;
import lombok.Data;

@Data
public class NotificationVo {

    private String userId;
    private NotificationTypeEnum notificationType;
    private String title;
    private String content;
    private Boolean resultStatus;
    private Integer readStatus;
    private String remark;

}
