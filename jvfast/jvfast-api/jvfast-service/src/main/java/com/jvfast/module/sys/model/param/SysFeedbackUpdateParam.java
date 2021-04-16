package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 建议反馈表
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-09
 */
@Data
@NoArgsConstructor
@ApiModel(value = "SysFeedbackUpdateParam对象", description = "修改建议反馈表请求参数")
public class SysFeedbackUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    private Long userId;
    private Integer feedbackType;
    private String name;
    private String email;

    @ApiModelProperty(value = "提交的建议单的状态,1000=pending,1001已解决1003未解决")
    private Integer feedbackStatus;

    @ApiModelProperty(value = "反馈标题")
    private String title;

    @ApiModelProperty(value = "反馈详情")
    private String detail;

    private List<String> screenshot;

    private String reason;

    private LocalDateTime updateTime = LocalDateTime.now();

}
