package com.jvfast.module.sys.model.param;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SysUserLoginParam {

    /**
     * 用户名密码和图形验证码方式登录
     */
    private String username;
    private String passwd;
    private String captchaId;
    /**
     * 手机号码登录+短信验证码登录
     */
    private String phone;

    /**
     * 输入的图形验证码或者短信验证码
     */
    private String code;

}
