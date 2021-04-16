package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysMenu;
import com.jvfast.module.sys.model.param.SysMenuAddParam;
import com.jvfast.module.sys.model.param.SysMenuQueryPageParam;
import com.jvfast.module.sys.model.param.SysMenuQueryTypeParam;
import com.jvfast.module.sys.model.param.SysMenuUpdateParam;
import com.jvfast.module.sys.model.vo.SysMenuQueryVo;
import com.jvfast.module.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysMenu接口")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {
    private final SysMenuService sysMenuService;


    /**
     * 添加菜单权限表
     */
    @Log(title = "新增菜单", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysMenu对象", notes = "添加菜单权限表", response = Result.class)
    public Result<Boolean> addSysMenu(@Valid @RequestBody SysMenuAddParam sysMenuAddParam) {
        boolean flag = sysMenuService.saveSysMenu(sysMenuAddParam);
        return Result.status(flag);
    }

    /**
     * 修改菜单权限表
     */
    @Log(title = "修改菜单", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysMenu对象", notes = "修改菜单权限表", response = Result.class)
    public Result<Boolean> updateSysMenu(@Valid @RequestBody SysMenuUpdateParam sysMenuUpdateParam) {
        boolean flag = sysMenuService.updateSysMenuById(sysMenuUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除菜单权限表
     */
    @Log(title = "删除菜单", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysMenu对象", notes = "删除菜单权限表", response = Result.class)
    public Result<Boolean> deleteSysMenu(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysMenuService.removeSysMenuById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除菜单表
     */
    @Log(title = "批量删除菜单", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysMenu对象", notes = "批量删除菜单表", response = Result.class)
    public Result<Boolean> deleteSysMenus(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysMenuService.removeSysMenusById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取菜单权限表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysMenuVo对象详情", notes = "菜单权限表", response = SysMenu.class)
    public Result<SysMenu> getSysMenu(@Valid @RequestBody IdParam idParam) {
        SysMenu sysMenu = sysMenuService.getSysMenuById(idParam);
        return Result.success(sysMenu);
    }

    /**
     * SysMenu查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysMenu列表", notes = "SysMenu列表", response = SysMenu.class)
    public Result<List<SysMenuQueryVo>> getSysMenuList(@Valid @RequestBody SysMenuQueryTypeParam sysMenuQueryTypeParam) {
        List<SysMenuQueryVo> sysMenuList = sysMenuService.listSysMenuQueryVo(sysMenuQueryTypeParam);
        return Result.success(sysMenuList);
    }


    /**
     * 菜单权限表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysMenuQueryVo分页列表", notes = "菜单权限表分页列表", response = SysMenuQueryVo.class)
    public Result<IPage<SysMenuQueryVo>> getSysMenuPageList(@Valid @RequestBody(required = false) SysMenuQueryPageParam sysMenuQueryPageParam) throws DataAccessException {
        IPage<SysMenuQueryVo> paging = sysMenuService.pageSysMenuQueryVo(sysMenuQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
    /**
     * 查看/编辑/查询对应角色所拥有的菜单列表
     *
     * @param idParam
     * @return com.jvfast.common.api.Result<java.util.List < com.jvfast.module.sys.model.vo.SysMenuQueryVo>>
     * @author Walter Hu
     * @date 2019/12/24
     * @since 1.8
     */
    @PostMapping("/role")
    @ApiOperation(value = "根据角色ID获取菜单列表信息", notes = "获取角色的所有菜单信息", response = Result.class)
    public Result<List<SysMenuQueryVo>> getRoleMenus(@RequestBody @Valid IdParam idParam) {
        List<SysMenuQueryVo> sysMenuQueryVos = sysMenuService.listMenusByRoleId(idParam.getId());
        return Result.success(sysMenuQueryVos);
    }

    /**
     * 查询树结构
     *
     * @param idParam
     * @return com.jvfast.common.api.Result<java.util.List < com.jvfast.module.sys.model.vo.SysMenuQueryVo>>
     * @author Walter Hu
     * @date 2020/1/8
     * @since 1.8
     * @deprecated 采用前端进行递归获取对应的子菜单
     */

    @PostMapping("/child")
    @ApiOperation(value = "根据顶级菜单获取子菜单列表信息", notes = "获取顶级菜单的所有子菜单信息", response = Result.class)
    public Result<List<SysMenuQueryVo>> getMenuTreeByMenuId(@RequestBody @Valid IdParam idParam) {
        List<SysMenuQueryVo> sysMenuQueryVos = sysMenuService.listMenuTreeByMenuId(idParam);
        return Result.success(sysMenuQueryVos);
    }
}
