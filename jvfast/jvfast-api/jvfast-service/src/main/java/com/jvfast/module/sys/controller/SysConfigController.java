package com.jvfast.module.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.sys.model.entity.SysConfig;
import com.jvfast.module.sys.model.param.SysConfigAddParam;
import com.jvfast.module.sys.model.param.SysConfigQueryPageParam;
import com.jvfast.module.sys.model.param.SysConfigUpdateParam;
import com.jvfast.module.sys.model.vo.SysConfigQueryVo;
import com.jvfast.module.sys.service.SysConfigService;
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

/**
 * <p>
 * 参数配置表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysConfig接口")
@RestController
@RequestMapping("/sys/config")
public class SysConfigController {
    private final SysConfigService sysConfigService;


    /**
     * 添加参数配置表
     */
    @Log(title = "新增配置参数", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysConfig对象", notes = "添加参数配置表", response = Result.class)
    public Result<Boolean> addSysConfig(@Valid @RequestBody SysConfigAddParam sysConfigAddParam) {
        boolean flag = sysConfigService.saveSysConfig(sysConfigAddParam);
        return Result.status(flag);
    }

    /**
     * 修改参数配置表
     */
    @Log(title = "修改配置参数", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改SysConfig对象", notes = "修改参数配置表", response = Result.class)
    public Result<Boolean> updateSysConfig(@Valid @RequestBody SysConfigUpdateParam sysConfigUpdateParam) {
        boolean flag = sysConfigService.updateSysConfigById(sysConfigUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除参数配置表
     */
    @Log(title = "删除配置参数", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysConfig对象", notes = "删除参数配置表", response = Result.class)
    public Result<Boolean> deleteSysConfig(@Valid @RequestBody IdParam idParam) {
        boolean flag = sysConfigService.removeSysConfigById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除配置参数表
     */
    @Log(title = "批量删除配置参数", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysConfig对象", notes = "批量删除配置参数表", response = Result.class)
    public Result<Boolean> deleteSysConfigs(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = sysConfigService.removeSysConfigsById(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取参数配置表
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取SysConfigVo对象详情", notes = "参数配置表", response = SysConfig.class)
    public Result<SysConfig> getSysConfig(@Valid @RequestBody IdParam idParam) {
        SysConfig sysConfig = sysConfigService.getSysConfigById(idParam);
        return Result.success(sysConfig);
    }

    /**
     * SysConfig查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取SysConfig列表", notes = "SysConfig列表", response = SysConfig.class)
    public Result<List<SysConfigQueryVo>> getSysConfigList() {
        List<SysConfigQueryVo> sysConfigList = sysConfigService.listSysConfigQueryVo();
        return Result.success(sysConfigList);
    }


    /**
     * 参数配置表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysConfigQueryVo分页列表", notes = "参数配置表分页列表", response = SysConfigQueryVo.class)
    public Result<IPage<SysConfigQueryVo>> getSysConfigPageList(@Valid @RequestBody(required = false) SysConfigQueryPageParam sysConfigQueryPageParam) {
        IPage<SysConfigQueryVo> paging = sysConfigService.pageSysConfigQueryVo(sysConfigQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
