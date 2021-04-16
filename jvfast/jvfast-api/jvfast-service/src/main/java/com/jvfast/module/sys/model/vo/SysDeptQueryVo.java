package com.jvfast.module.sys.model.vo;

import com.jvfast.common.entity.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-22
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysDeptQueryVo对象", description = "查询部门表返回结果对象")
public class SysDeptQueryVo extends Tree {
    private static final long serialVersionUID = 1L;

    private String deptName;

    private Integer deptOrder;

    @ApiModelProperty(value = "对应该条记录是否可用，0可用，1不可用")
    private Boolean status;

    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    private Integer version;
}
