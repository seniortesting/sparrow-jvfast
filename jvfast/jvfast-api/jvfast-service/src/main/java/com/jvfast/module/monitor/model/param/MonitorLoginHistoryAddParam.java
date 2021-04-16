package com.jvfast.module.monitor.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统访问记录
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysLoginHistoryAddParam对象", description = "添加系统访问记录请求参数")
public class MonitorLoginHistoryAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账号")
    private String userName;

    @ApiModelProperty(value = "登录IP地址")
    private String ipAddress;

    @ApiModelProperty(value = "登录地点")
    private String loginLocation;

    @ApiModelProperty(value = "浏览器类型")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "登录状态（0成功 1失败）")
    private Integer loginStatus;

    @ApiModelProperty(value = "提示消息")
    private String loginMessage;

    @ApiModelProperty(value = "访问时间")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "对应创建记录的人")
    private String createBy;

}
