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
 * 文件上传表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_file_upload")
@ApiModel(value = "SysFileUpload对象", description = "文件上传表")
public class SysFileUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "文件上传者")
    private Long userId;

    @ApiModelProperty(value = "上传文件的token ID,用于分组多个文件")
    private String token;

    @ApiModelProperty(value = "文件访问url")
    private String url;

    @ApiModelProperty(value = "上传文件原始名称")
    private String originalName;

    @ApiModelProperty(value = "上传文件后的名称")
    private String fileName;

    @ApiModelProperty(value = "上传文件短路径")
    private String filePath;

    @ApiModelProperty(value = "最后一次定时任务执行状态(0=成功,1=失败)")
    private Integer lastBatchStatus;

    @ApiModelProperty(value = "最后一次定时任务执行时间")
    private LocalDateTime lastBatchTime;

    @ApiModelProperty(value = "对应该条记录是否可用，1可用，0不可用")
    @TableField(fill = FieldFill.INSERT)
    private Boolean status;

    @ApiModelProperty(value = "对应该条记录是否可用，1可用，0不可用")
    private String remark;

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
