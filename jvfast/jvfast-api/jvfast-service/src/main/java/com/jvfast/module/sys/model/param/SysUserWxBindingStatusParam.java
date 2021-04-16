package com.jvfast.module.sys.model.param;

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
public class SysUserWxBindingStatusParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "appId不能为空")
    private String appId;

    @NotBlank(message = "openId不能为空")
    private String openId;

}
