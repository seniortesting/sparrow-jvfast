package com.jvfast.module.sys.model.param;

import com.jvfast.common.param.QueryParam;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 建议反馈表 查询参数对象
 * </p>
 *
 * @author Walter Hu
 * @date 2020-01-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysFeedbackQueryPageParam对象", description = "查询建议反馈表分页请求参数")
public class SysFeedbackQueryPageParam extends QueryParam {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String detail;
}
