package com.jvfast.module.sys.model.param;

import com.jvfast.notification.common.enums.NotificationTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-22
 */
@Data
@NoArgsConstructor
@ApiModel(value = "SysNotificationAddParam对象", description = "添加系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)请求参数")
public class SysNotificationAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "1,站内信;2.邮件;3.短信;4.微信")
    private NotificationTypeEnum notificationType;

    private String userId;

    private String title;

    private String content;

    private Integer readStatus;

    private boolean resultStatus;

}
