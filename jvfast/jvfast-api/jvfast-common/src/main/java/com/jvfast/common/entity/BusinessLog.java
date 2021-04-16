package com.jvfast.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Data
@NoArgsConstructor
public class BusinessLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;

    @ApiModelProperty(value = "模块标题")
    private String businessTitle;

    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2前台操作 3手机端用户）")
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

    @ApiModelProperty(value = "对应该条记录是否可用，1可用，0不可用")
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
