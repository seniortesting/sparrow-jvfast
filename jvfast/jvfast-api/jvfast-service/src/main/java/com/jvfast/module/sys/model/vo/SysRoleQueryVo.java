package com.jvfast.module.sys.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色信息表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "AuthRoleQueryVo对象", description = "查询角色信息表返回结果对象")
public class SysRoleQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色权限字符串")
    private String roleCode;

    @ApiModelProperty(value = "显示顺序")
    private Integer roleOrder;

    @ApiModelProperty(value = "数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private Integer dataScope;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "对应该条记录是否可用，0可用，1不可用")
    private Boolean status;

    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    private Integer version;

    @ApiModelProperty(value = "对应记录的最后修改时间")
    private LocalDateTime updateTime;
}
