package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 公司表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2020-01-19
 */
@Data
@ApiModel(value = "SysCompanyQueryVo对象", description = "查询公司表返回结果对象")
public class SysCompanyQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "简要描述")
    private String description;
}
