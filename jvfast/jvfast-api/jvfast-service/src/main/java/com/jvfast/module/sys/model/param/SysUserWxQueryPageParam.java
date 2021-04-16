package com.jvfast.module.sys.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信用户 查询参数对象
 *
 * @author Walter Hu
 * @date 2020-02-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysUserWxQueryPageParam对象", description = "查询微信用户分页请求参数")
public class SysUserWxQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
