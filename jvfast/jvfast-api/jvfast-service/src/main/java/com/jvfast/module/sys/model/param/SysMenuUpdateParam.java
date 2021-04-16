package com.jvfast.module.sys.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@ApiModel(value = "SysMenuUpdateParam对象", description = "修改菜单权限表请求参数")
public class SysMenuUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "记录唯一标识id")
    @NotNull(message = "ID不能为空")
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
    private String componentName;

    @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
    private Boolean hidden;

    @ApiModelProperty(value = "是否为外链（0是 1否）")
    private Boolean external;

    @ApiModelProperty(value = "外链地址")
    private String externalUrl;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    private String remark;

    private Boolean status;
}
