package com.jvfast.module.sys.model.param;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
 * @since 2019-11-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysUserUpdateParam对象", description = "修改系统用户信息表请求参数")
public class SysUserUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    @NotNull(message = "用户ID不能为空")
    private Long id;

    private String userName;

    private String nickName;

    private String phone;

    private String passwd;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
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

    private Integer version;

    // 个人资料更新
    private String token;
    // 用于更新角色
    private List<String> roleId = Lists.newArrayList();
    private Long deptId;

}
