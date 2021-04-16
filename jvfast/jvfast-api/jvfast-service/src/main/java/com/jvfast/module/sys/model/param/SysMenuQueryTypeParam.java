package com.jvfast.module.sys.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 菜单权限表 查询参数对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysMenuQueryPageParam对象", description = "查询菜单权限表分页请求参数")
public class SysMenuQueryTypeParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "对应的是否查询按钮权限标识不能为空")
    private Boolean filterButtonPermission;
}
