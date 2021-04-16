package com.jvfast.module.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.monitor.model.entity.MonitorLoginHistory;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryAddParam;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryQueryPageParam;
import com.jvfast.module.monitor.model.param.MonitorLoginHistoryUpdateParam;
import com.jvfast.module.monitor.model.vo.MonitorLoginHistoryQueryVo;
import com.jvfast.module.monitor.service.MonitorLoginHistoryService;
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
 * 系统访问记录 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-12-17
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "MonitorLoginHistory接口")
@RestController
@RequestMapping("/monitor/history")
public class MonitorLoginHistoryController {

    private final MonitorLoginHistoryService monitorLoginHistoryService;

    /**
     * 添加系统访问记录
     */
    @Log(title = "添加登录日志", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加登录日志对象", notes = "添加系统访问记录", response = Result.class)
    public Result<Boolean> addMonitorLoginHistory(@Valid @RequestBody MonitorLoginHistoryAddParam monitorLoginHistoryAddParam) {
        boolean flag = monitorLoginHistoryService.saveMonitorLoginHistory(monitorLoginHistoryAddParam);
        return Result.status(flag);
    }

    /**
     * 修改系统访问记录
     */
    @Log(title = "修改登录日志", businessType = BusinessTypeEnum.UPDATE)
    @PostMapping("/update")
    @ApiOperation(value = "修改登录日志对象", notes = "修改系统访问记录", response = Result.class)
    public Result<Boolean> updateMonitorLoginHistory(@Valid @RequestBody MonitorLoginHistoryUpdateParam monitorLoginHistoryUpdateParam) {
        boolean flag = monitorLoginHistoryService.updateMonitorLoginHistoryById(monitorLoginHistoryUpdateParam);
        return Result.status(flag);
    }

    /**
     * 删除系统访问记录
     */
    @Log(title = "删除登录日志", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除登录日志对象", notes = "删除系统访问记录", response = Result.class)
    public Result<Boolean> deleteMonitorLoginHistory(@Valid @RequestBody IdParam idParam) {
        boolean flag = monitorLoginHistoryService.removeMonitorLoginHistoryById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除系统访问记录
     */
    @Log(title = "批量删除登录日志", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除登录日志对象", notes = "批量删除系统访问记录", response = Result.class)
    public Result<Boolean> deleteMonitorLoginHistorys(@RequestBody IdBatchParam IdBatchParam) {
        boolean flag = monitorLoginHistoryService.removeMonitorLoginHistoryByIds(IdBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取系统访问记录
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取登录日志对象详情", notes = "系统访问记录", response = MonitorLoginHistory.class)
    public Result<MonitorLoginHistory> getMonitorLoginHistory(@Valid @RequestBody IdParam idParam) {
        MonitorLoginHistory monitorLoginHistory = monitorLoginHistoryService.getMonitorLoginHistoryById(idParam);
        return Result.success(monitorLoginHistory);
    }

    /**
     * MonitorLoginHistory查询列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "获取登录日志列表", notes = "登录日志列表", response = MonitorLoginHistory.class)
    public Result<List<MonitorLoginHistoryQueryVo>> getMonitorLoginHistoryList() {
        List<MonitorLoginHistoryQueryVo> monitorLoginHistoryList = monitorLoginHistoryService.listMonitorLoginHistoryQueryVo();
        return Result.success(monitorLoginHistoryList);
    }

    /**
     * 系统访问记录分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取登录日志分页列表", notes = "系统访问记录分页列表", response = MonitorLoginHistoryQueryVo.class)
    public Result<IPage<MonitorLoginHistoryQueryVo>> getMonitorLoginHistoryPageList(@Valid @RequestBody(required = false) MonitorLoginHistoryQueryPageParam monitorLoginHistoryQueryPageParam) {
        IPage<MonitorLoginHistoryQueryVo> paging = monitorLoginHistoryService.pageMonitorLoginHistoryQueryVo(monitorLoginHistoryQueryPageParam);
        return Result.success(paging);
    }
    /***************************** 以下为扩展接口 ******************************************************/
}
