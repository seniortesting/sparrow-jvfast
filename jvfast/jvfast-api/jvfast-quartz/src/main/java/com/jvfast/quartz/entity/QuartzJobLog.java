package com.jvfast.quartz.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * Quartz任务的执行记录
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuartzJobLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    private String jobName;

    private String jobGroup;

    private String jobParams;

    @ApiModelProperty(value = "定时任务标识")
    private String cronExpression;

    private String exceptionDetail;

    @ApiModelProperty(value = "对应该条记录是否可用，0可用，1不可用")
    private Boolean status;

    @ApiModelProperty(value = "备注")
    private String remark;

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
