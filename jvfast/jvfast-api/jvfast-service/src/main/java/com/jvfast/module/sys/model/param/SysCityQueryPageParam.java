package com.jvfast.module.sys.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md 查询参数对象
 *
 * @author Walter Hu
 * @date 2020-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysCityQueryPageParam对象", description = "查询国家城市数据库：https://github.com/xiangyuecn/AreaCity-JsSpider-StatsGov/blob/master/README.md分页请求参数")
public class SysCityQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
