package com.jvfast.module.sys.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 检查用户是否存在请求
 */
@Data
public class SysUserNameParam {

    @NotEmpty(message = "用户名不能为空")
    private String username;
}
