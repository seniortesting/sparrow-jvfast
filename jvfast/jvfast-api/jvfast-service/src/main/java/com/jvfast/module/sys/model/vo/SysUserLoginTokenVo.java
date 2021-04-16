package com.jvfast.module.sys.model.vo;

import com.jvfast.common.shiro.model.RedisLoginSysUserVo;
import lombok.Data;

/**
 * 前台返回的用户信息
 */
@Data
public class SysUserLoginTokenVo {

    private String token;
    private RedisLoginSysUserVo user;
}
