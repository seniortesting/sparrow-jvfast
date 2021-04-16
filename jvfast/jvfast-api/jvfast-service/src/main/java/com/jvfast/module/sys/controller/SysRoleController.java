package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysRole;
import com.jvfast.module.sys.model.param.SysRoleAddParam;
import com.jvfast.module.sys.model.param.SysRoleQueryPageParam;
import com.jvfast.module.sys.model.param.SysRoleUpdateParam;
import com.jvfast.module.sys.model.vo.SysRoleQueryVo;
import com.jvfast.module.sys.service.SysRoleService;
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
 * 角色信息表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-22
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysRole接口")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    /**
     * 添加角色信息表
     */
    @Log(title = "新增角色", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysRole对象", notes = "添加角色信息表", response = Result.class)
    public Result<Boolean> addSysRole(@Valid @RequestBody SysRoleAddParam sysRoleAddParam) {
        boolean flag = sysRoleService.saveSysRole(sysRoleAddParam);
        return Result.status(flag);
    }

    /**
     * 修改角色信息表
     */
    @Log(title = "修改角色", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysRole对象", notes = "修改角色信息表", response = Result.class)
    public Result<Boolean> updateSysRole(@Valid @RequestBody SysRoleUpdateParam sysRoleUpdateParam) {
        boolean flag = sysRoleService.updateSysRoleById(sysRoleUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除角色信息表
     */
    @Log(title = "删除角色", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysRole对象", notes = "删除角色信息表", response = Result.class)
    public Result<Boolean> deleteSysRole(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysRoleService.removeSysRoleById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除角色表
     */
    @Log(title = "批量删除角色", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysRole对象", notes = "批量删除角色表", response = Result.class)
    public Result<Boolean> deleteSysRoles(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysRoleService.removeSysRolesById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取角色信息表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysRoleVo对象详情", notes = "角色信息表", response = SysRole.class)
    public Result<SysRole> getSysRole(@Valid @RequestBody IdParam idParam) {
        SysRole sysRole = sysRoleService.getSysRoleById(idParam);
        return Result.success(sysRole);
    }

    /**
     * SysRole查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysRole列表", notes = "SysRole列表", response = SysRole.class)
    public Result<List<SysRoleQueryVo>> getSysRoleList() {
        List<SysRoleQueryVo> sysRoleList = sysRoleService.listSysRoleQueryVo();
        return Result.success(sysRoleList);
    }


    /**
     * 角色信息表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysRoleQueryVo分页列表", notes = "角色信息表分页列表", response = SysRoleQueryVo.class)
    public Result<IPage<SysRoleQueryVo>> getSysRolePageList(@Valid @RequestBody(required = false) SysRoleQueryPageParam sysRoleQueryPageParam) throws DataAccessException {
        IPage<SysRoleQueryVo> paging = sysRoleService.pageSysRoleQueryVo(sysRoleQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
