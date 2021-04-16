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
 * 岗位表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysUserPostAddParam对象", description = "添加岗位表请求参数")
public class SysUserPostAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long postId;

    @ApiModelProperty(value = "备注")
    private String remark;

}
