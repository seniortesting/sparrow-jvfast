package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysUserWx;
import com.jvfast.module.sys.model.param.*;
import com.jvfast.module.sys.model.vo.SysUserWxQueryVo;
import com.jvfast.module.sys.service.SysUserWxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 微信用户 前端控制器
 *
 * @author Walter Hu
 * @since 2020-02-16
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysUserWx接口")
@RestController
@RequestMapping("/sys/wx")
public class SysUserWxController {

    private final SysUserWxService sysUserWxService;


    /**
     * 添加微信用户
     */
    @Log(title = "添加SysUserWx", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysUserWx对象", notes = "添加微信用户", response = Result.class)
    public Result<Boolean> addSysUserWx(@Valid @RequestBody SysUserWxAddParam sysUserWxAddParam) {
        boolean flag = sysUserWxService.saveSysUserWx(sysUserWxAddParam);
        return Result.status(flag);
    }

    /**
     * 修改微信用户
     */
    @Log(title = "修改SysUserWx", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysUserWx对象", notes = "修改微信用户", response = Result.class)
    public Result<Boolean> updateSysUserWx(@Valid @RequestBody SysUserWxUpdateParam sysUserWxUpdateParam) {
        boolean flag = sysUserWxService.updateSysUserWxById(sysUserWxUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除微信用户
     */
    @Log(title = "删除SysUserWx", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysUserWx对象", notes = "删除微信用户", response = Result.class)
    public Result<Boolean> deleteSysUserWx(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysUserWxService.removeSysUserWxById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除微信用户
     */
    @Log(title = "批量删除SysUserWx", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysUserWx对象", notes = "批量删除微信用户", response = Result.class)
    public Result<Boolean> deleteSysUserWxs(@RequestBody IdBatchParam IdBatchParam) {
        boolean flag = sysUserWxService.removeSysUserWxByIds(IdBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取微信用户
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysUserWxVo对象详情", notes = "微信用户", response = SysUserWx.class)
    public Result<SysUserWx> getSysUserWx(@Valid @RequestBody IdParam idParam) {
        SysUserWx sysUserWx = sysUserWxService.getSysUserWxById(idParam);
        return Result.success(sysUserWx);
    }

    /**
     * SysUserWx查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysUserWx列表", notes = "SysUserWx列表", response = SysUserWx.class)
    public Result<List<SysUserWxQueryVo>> listSysUserWx() {
        List<SysUserWxQueryVo> sysUserWxList = sysUserWxService.listSysUserWxQueryVo();
        return Result.success(sysUserWxList);
    }

    /**
     * 微信用户分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysUserWxQueryVo分页列表", notes = "微信用户分页列表", response = SysUserWxQueryVo.class)
    public Result<IPage<SysUserWxQueryVo>> pageSysUserWx(@Valid @RequestBody(required = false) SysUserWxQueryPageParam sysUserWxQueryPageParam) {
        IPage<SysUserWxQueryVo> paging = sysUserWxService.pageSysUserWxQueryVo(sysUserWxQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
    @PostMapping("/openid")
    @ApiOperation(value = "获取SysUserWxQueryVo通过openid", notes = "微信用户分页列表", response = SysUserWxQueryVo.class)
    public Result<SysUserWxQueryVo> getUserIdByOpenId(@Valid @RequestBody SysUserWxOpenIdQueryParam sysUserWxOpenIdQueryParam) {
        SysUserWxQueryVo userWxByOpenId = sysUserWxService.getUserWxByOpenId(sysUserWxOpenIdQueryParam);
        if (!Objects.isNull(userWxByOpenId)) {
            return Result.success(userWxByOpenId);
        } else {
            return Result.fail(ResultCode.NOT_FOUND);
        }
    }

    @PostMapping("/binding")
    @ApiOperation(value = "获取SysUserWxQueryVo通过openid", notes = "微信用户分页列表", response = SysUserWxQueryVo.class)
    public Result<Boolean> bindingSysUser(@Valid @RequestBody SysUserWxBindingParam sysUserWxBindingParam) {
        Boolean bindWpUser = sysUserWxService.bindWpUser(sysUserWxBindingParam);
        return Result.status(bindWpUser);
    }

    @PostMapping("/status")
    @ApiOperation(value = "获取SysUserWxQueryVo通过openid", notes = "微信用户分页列表", response = SysUserWxQueryVo.class)
    public Result<SysUserWxQueryVo> getBindingStatus(@Valid @RequestBody SysUserWxBindingStatusParam sysUserWxBindingStatusParam) {
        SysUserWxQueryVo userWxByOpenId = sysUserWxService.getBindStatus(sysUserWxBindingStatusParam);
        if (!Objects.isNull(userWxByOpenId)) {
            return Result.success(userWxByOpenId);
        } else {
            return Result.fail(ResultCode.NOT_FOUND);
        }
    }

}
