package com.jvfast.common.shiro.model;

import com.google.common.collect.Sets;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 登录需要返回的用户信息,此信息参考 @see SysUser
 */
@Data
public class RedisLoginSysUserVo {

    private Long id;

    private String userName;

    private String nickName;

    private String phone;

    private Integer sex;

    private LocalDate birth;

    private String avatar;

    private String email;

    private String signature;

    private String address;

    private LocalDateTime lastLoginTime;

    private String lastLoginIp;

    private String wxOpenid;

    private Boolean status;


    private Set<String> roles = Sets.newHashSet();
    private Set<String> permissions = Sets.newHashSet();
}
