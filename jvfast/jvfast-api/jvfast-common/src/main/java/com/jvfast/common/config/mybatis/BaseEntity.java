package com.jvfast.common.config.mybatis;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description: mybatis的基础实体类
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-07 21:09
 **/
@Data
public class BaseEntity implements Serializable {

    @ApiModelProperty(value = "对应记录是否可用，1可用，0不可用")
    @TableField(fill = FieldFill.INSERT)
    private Boolean status;

    @ApiModelProperty(value = "对应记录备注")
    private String remark;

    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    @TableField(fill = FieldFill.INSERT)
    @Version
    private Integer version;

    @ApiModelProperty(value = "对应记录的创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "对应记录的创建者")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "对应记录的最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "对应记录的最后修改者")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
