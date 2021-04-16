package com.jvfast.module.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jvfast.common.config.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@EqualsAndHashCode(callSuper = true)
@TableName("sys_notification")
@ApiModel(value = "SysNotification对象", description = "系统通知表(邮件,短信,微信公众号消息,微信小程序消息,app消息)")
public class SysNotification extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "记录唯一标识id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    private String userId;
    @ApiModelProperty(value = "1,站内信;2.邮件;3.短信;4.微信")
    private Integer notificationType;
    private String title;
    private String content;
    private Integer readStatus;
    private Boolean resultStatus;
}
