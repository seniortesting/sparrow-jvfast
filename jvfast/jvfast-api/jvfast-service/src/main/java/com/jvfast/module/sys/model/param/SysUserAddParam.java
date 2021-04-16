package com.jvfast.module.sys.model.param;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统用户信息表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-12-13
 */
@Data
@NoArgsConstructor
@ApiModel(value = "SysUserAddParam对象", description = "添加系统用户信息表请求参数")
public class SysUserAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户账号不能为空")
    private String userName;

    private String nickName;

    private String phone;

    private String passwd;

    @ApiModelProperty(value = "用户性别（1男 2女 3未知）")
    private Integer sex;

    private LocalDate birth;

    private String avatar;

    private String email;

    private String signature;

    private String address;

    private LocalDateTime lastLoginTime;

    private String lastLoginIp;

    @ApiModelProperty(value = "微信openid")
    private String wxOpenid;

    private Boolean status;

    // 用于更新角色
    private List<String> roleId = Lists.newArrayList();
    private Long deptId;
}
