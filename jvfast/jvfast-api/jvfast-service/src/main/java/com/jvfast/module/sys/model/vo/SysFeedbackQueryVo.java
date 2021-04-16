package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 建议反馈表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2020-01-09
 */
@Data
@ApiModel(value = "SysFeedbackQueryVo对象", description = "查询建议反馈表返回结果对象")
public class SysFeedbackQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    private Long userName;
    private Integer feedbackType;
    private String name;
    private String email;

    @ApiModelProperty(value = "提交的建议单的状态,1000=pending,1001已解决1003未解决")
    private Integer feedbackStatus;

    @ApiModelProperty(value = "反馈标题")
    private String title;

    @ApiModelProperty(value = "反馈详情")
    private String detail;

    private String screenshot;

    private String reason;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer version;
}
