package com.jvfast.module.sys.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-14 17:49
 **/
@Data
public class SysUserDeptDTO implements Serializable {

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

    private String wxOpenid;

    private Boolean status;

    private Integer version;

    private LocalDateTime updateTime;

    private Long deptId;
    private String deptName;


}
