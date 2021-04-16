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

import java.time.LocalDateTime;

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
@EqualsAndHashCode(callSuper = true)
@TableName("sys_feedback")
@ApiModel(value = "SysFeedback对象", description = "建议反馈表")
public class SysFeedback extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "记录唯一标识id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    private String screenshot;

    private String reason;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
