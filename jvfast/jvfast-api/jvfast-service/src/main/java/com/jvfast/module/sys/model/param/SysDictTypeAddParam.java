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
 * 系统字典类型表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysDictTypeAddParam对象", description = "添加系统字典类型表请求参数")
public class SysDictTypeAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Boolean status;

    @ApiModelProperty(value = "备注")
    private String remark;

}
