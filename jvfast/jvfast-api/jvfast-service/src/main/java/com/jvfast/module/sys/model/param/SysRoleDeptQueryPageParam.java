package com.jvfast.module.sys.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色部门表 查询参数对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysRoleDeptQueryPageParam对象", description = "查询角色部门表分页请求参数")
public class SysRoleDeptQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
