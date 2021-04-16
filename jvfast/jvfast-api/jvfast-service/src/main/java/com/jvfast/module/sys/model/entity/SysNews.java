package com.jvfast.module.sys.model.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * 消息新闻表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_news")
@ApiModel(value = "SysNews对象", description = "消息新闻表")
public class SysNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private String title;

    private String content;

    private String fromSource;

    private String originalUrl;

    private Long viewCount;

    private Long commentCount;

    @ApiModelProperty(value = "对应该条记录是否可用，1可用，0不可用")
    @TableField(fill = FieldFill.INSERT)
    private Boolean status;

    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    @TableField(fill = FieldFill.INSERT)
    @Version
    private Integer version;

    @ApiModelProperty(value = "对应记录的创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "对应创建记录的人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "对应记录的最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "对应最后修改记录的人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;


}
