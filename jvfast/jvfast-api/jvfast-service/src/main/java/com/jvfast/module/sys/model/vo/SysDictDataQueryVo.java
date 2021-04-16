package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统字典表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-22
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysDictQueryVo对象", description = "查询系统字典表返回结果对象")
public class SysDictDataQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    private Long dictTypeId;

    private String dictLabel;

    private String dictValue;

    private Integer dictOrder;

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
