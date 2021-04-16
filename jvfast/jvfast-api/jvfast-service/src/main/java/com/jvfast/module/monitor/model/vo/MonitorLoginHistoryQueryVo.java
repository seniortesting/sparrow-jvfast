package com.jvfast.module.monitor.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统访问记录 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysLoginHistoryQueryVo对象", description = "查询系统访问记录返回结果对象")
public class MonitorLoginHistoryQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

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

    @ApiModelProperty(value = "对应该条记录是否可用，0可用，1不可用")
    private Boolean status;

    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    private Integer version;

    @ApiModelProperty(value = "对应记录的创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "对应创建记录的人")
    private String createBy;

    @ApiModelProperty(value = "对应记录的最后修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "对应最后修改记录的人")
    private String updateBy;
}
