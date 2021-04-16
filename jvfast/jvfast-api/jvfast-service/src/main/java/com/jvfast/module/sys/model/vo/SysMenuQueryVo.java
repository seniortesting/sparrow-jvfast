package com.jvfast.module.sys.model.vo;

import com.jvfast.common.entity.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 菜单权限表 查询结果对象
 * </p>
 *
 * @author Walter Hu
 * @date 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysMenuQueryVo对象", description = "查询菜单权限表返回结果对象")
public class SysMenuQueryVo extends Tree {

    @ApiModelProperty(value = "资源类型,1=后台菜单,2=普通页面")
    private Integer menuType;

    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @ApiModelProperty(value = "显示顺序")
    private Integer menuOrder;

    @ApiModelProperty(value = "组件路径")
    private String componentName;

    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private Boolean hidden;

    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private Boolean external;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    private String remark;

    @ApiModelProperty(value = "对应该条记录是否可用，1可用，0不可用")
    private Boolean status;

    @ApiModelProperty(value = "对应记录的修订版本号,乐观锁")
    private Integer version;

    private LocalDateTime updateTime;
}
