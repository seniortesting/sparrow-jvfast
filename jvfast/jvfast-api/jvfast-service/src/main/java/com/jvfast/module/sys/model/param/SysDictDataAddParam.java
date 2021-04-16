package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysDictAddParam对象", description = "添加系统字典表请求参数")
public class SysDictDataAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dictTypeId;

    private String dictLabel;

    private String dictValue;

    private Integer dictOrder;

    private Boolean status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
