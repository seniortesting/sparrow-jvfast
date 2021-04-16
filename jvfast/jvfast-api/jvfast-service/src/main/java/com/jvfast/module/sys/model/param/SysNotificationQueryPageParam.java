package com.jvfast.module.sys.model.param;

import com.jvfast.common.param.QueryParam;
import com.jvfast.notification.common.enums.NotificationTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息) 查询参数对象
 * </p>
 *
 * @author Walter Hu
 * @date 2020-01-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysNotificationQueryPageParam对象", description = "查询系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)分页请求参数")
public class SysNotificationQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    private NotificationTypeEnum type;
    private String userId;
    private Integer readStatus;
    private String userName;
}
