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
@ApiModel(value = "SysFeedbackAddParam对象", description = "添加建议反馈表请求参数")
public class SysFeedbackAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer feedbackType;
    private String name;
    private String email;

    @ApiModelProperty(value = "userId")
    private Long userId;
    @ApiModelProperty(value = "提交的建议单的状态,1000=pending,1001已解决1003未解决")
    private Integer feedbackStatus;

    @ApiModelProperty(value = "反馈标题")
    private String title;

    @ApiModelProperty(value = "反馈详情")
    private String detail;

    private List<String> screenshot;

    private LocalDateTime createTime=LocalDateTime.now();

}
