package com.jvfast.module.sys.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel(value = "SysMenu对象", description = "菜单权限表")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long pid;

    @ApiModelProperty(value = "资源类型,1=后台菜单,2=普通页面")
    private Integer menuType;

    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "显示顺序")
    private Integer menuOrder;

    @ApiModelProperty(value = "组件路径")
    @TableField(updateStrategy = FieldStrategy.NOT_NULL)
    private String componentName;

    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private Boolean hidden;

    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private Boolean external;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    private String remark;

    @ApiModelProperty(value = "对应该条记录是否可用，1可用，0不可用")
    @TableField(fill = FieldFill.INSERT)
    private Boolean status;

    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    @TableField(fill = FieldFill.INSERT)
    @Version
    private Integer version;

    @ApiModelProperty(value = "对应记录的创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "对应创建记录的人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "对应记录的最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "对应最后修改记录的人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;


}
