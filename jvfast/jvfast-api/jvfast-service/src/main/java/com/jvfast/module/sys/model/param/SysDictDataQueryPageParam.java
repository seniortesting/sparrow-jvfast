package com.jvfast.module.sys.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统字典表 查询参数对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysDictQueryPageParam对象", description = "查询系统字典表分页请求参数")
public class SysDictDataQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;
    private Long dictTypeId;
}
