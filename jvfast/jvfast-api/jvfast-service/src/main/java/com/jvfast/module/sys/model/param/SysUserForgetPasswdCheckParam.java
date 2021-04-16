package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统用户信息表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-04
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysUserRegisterParam对象", description = "添加系统用户信息表请求参数")
public class SysUserForgetPasswdCheckParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户手机号码")
    private String phone;

    @ApiModelProperty(value = "邮箱验证激活码或者短信验证码")
    private String code;

}
