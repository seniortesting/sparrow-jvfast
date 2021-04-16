package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *
 * 微信用户
 *
 * @author Walter Hu
 * @since 2020-02-03
 */
@Data
@NoArgsConstructor
@ApiModel(value = "SysUserWxOpenIdQueryParam对象", description = "添加微信用户请求参数")
public class SysUserWxOpenIdQueryParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "openId不能为空")
    private String openId;
}
