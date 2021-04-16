package com.jvfast.module.ignore.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * maomiav.com 查询参数对象
 *
 * @author Walter Hu
 * @date 2020-03-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PornMaomiQueryPageParam对象", description = "查询maomiav.com分页请求参数")
public class PornMaomiQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;
    private String category;
}
