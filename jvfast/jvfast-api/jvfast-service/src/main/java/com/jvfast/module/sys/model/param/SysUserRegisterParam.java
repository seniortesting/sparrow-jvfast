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
 * @since 2019-11-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysUserAddParam对象", description = "添加系统用户信息表请求参数")
public class SysUserRegisterParam implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "登录用户名")
    private String username;
    @ApiModelProperty(value = "登录密码")
    private String passwd;
    @ApiModelProperty(value = "干扰图形验证码的sessionId")
    private String captchaId;

    @ApiModelProperty(value = "干扰图形验证码或者手机验证码")
    private String code;

    private String device_info;
}
