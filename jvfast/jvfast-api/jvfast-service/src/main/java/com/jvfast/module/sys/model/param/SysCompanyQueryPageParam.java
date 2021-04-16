package com.jvfast.module.sys.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 公司表 查询参数对象
 * </p>
 *
 * @author Walter Hu
 * @date 2020-01-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CvrCompanyQueryPageParam对象", description = "查询公司表分页请求参数")
public class SysCompanyQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    private String name;
}
