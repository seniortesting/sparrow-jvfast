package com.jvfast.module.demo.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 测试表(无用)分页查询参数对象
 *
 * @author Walter Hu
 * @date 2020-06-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "DemoTestQueryPageParam分页查询对象", description = "查询测试表(无用)分页请求参数")
public class DemoTestQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
