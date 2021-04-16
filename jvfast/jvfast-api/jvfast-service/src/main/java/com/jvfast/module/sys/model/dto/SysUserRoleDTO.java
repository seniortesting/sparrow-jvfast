package com.jvfast.module.sys.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-14 17:47
 **/
@Data
public class SysUserRoleDTO implements Serializable {

    private Long id;
    private Long roleId;
    private String roleName;
    private String roleCode;
}
