package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统用户信息表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysUserQueryVo对象", description = "查询系统用户信息表返回结果对象")
public class SysUserQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    private String userName;

    private String nickName;

    private String phone;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private Integer sex;

    private LocalDate birth;

    private String avatar;

    private String email;

    private String signature;

    private String address;

    @ApiModelProperty(value = "微信openid")
    private String wxOpenid;

    @ApiModelProperty(value = "对应该条记录是否可用，0可用，1不可用")
    private Boolean status;

    @ApiModelProperty(value = "对应记录的最后修改时间")
    private LocalDateTime updateTime;

    private Long deptId;
    private String deptName;
}
