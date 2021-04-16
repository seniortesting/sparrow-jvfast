package com.jvfast.wx.miniapp.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 公众号查询表 查询参数对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "MpQueryPageParam对象", description = "查询MpQueryPageParam表分页请求参数")
public class MaConfigQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;
}
