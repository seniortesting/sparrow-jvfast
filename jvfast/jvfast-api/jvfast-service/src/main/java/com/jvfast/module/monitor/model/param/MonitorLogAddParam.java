package com.jvfast.module.monitor.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysLogAddParam对象", description = "添加日志表请求参数")
public class MonitorLogAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;

    @ApiModelProperty(value = "模块标题")
    private String businessTitle;

    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operationType;

    @ApiModelProperty(value = "方法名称")
    private String method;

    @ApiModelProperty(value = "请求URL")
    private String requestUrl;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "请求参数")
    private String requestParam;

    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    private Integer requestStatus;

    @ApiModelProperty(value = "返回参数")
    private String requestResult;

    @ApiModelProperty(value = "错误消息")
    private String requestErrorMsg;

    @ApiModelProperty(value = "操作时间")
    private Long requestTime;

    private String requestIp;

    @ApiModelProperty(value = "对应创建记录的人")
    private String createBy;

}
