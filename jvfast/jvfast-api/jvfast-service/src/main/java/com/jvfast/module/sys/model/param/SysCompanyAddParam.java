package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 公司表
 * </p>
 *
 * @author Walter Hu
 * @since 2020-01-19
 */
@Data
@NoArgsConstructor
@ApiModel(value="CvrCompanyAddParam对象", description="添加公司表请求参数")
public class SysCompanyAddParam implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "简要描述")
    private String description;

}
