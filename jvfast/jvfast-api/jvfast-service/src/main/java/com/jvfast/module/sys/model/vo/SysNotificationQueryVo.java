package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息) 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2020-01-22
 */
@Data
@ApiModel(value = "SysNotificationQueryVo对象", description = "查询系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)返回结果对象")
public class SysNotificationQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;
    private String userId;
    private String userName;

    @ApiModelProperty(value = "1,站内信;2.邮件;3.短信;4.微信")
    private Integer notificationType;

    private String title;

    private String content;

    private Boolean resultStatus;

    private Integer readStatus;

    private Boolean status;

    private String remark;

    private LocalDateTime updateTime;
}
