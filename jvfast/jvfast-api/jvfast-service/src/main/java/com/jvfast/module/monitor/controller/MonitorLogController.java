package com.jvfast.module.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.annotation.Log;
import com.jvfast.common.api.Result;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.param.IdBatchParam;
import com.jvfast.common.param.IdParam;
import com.jvfast.module.monitor.model.entity.MonitorLog;
import com.jvfast.module.monitor.model.param.MonitorLogAddParam;
import com.jvfast.module.monitor.model.param.MonitorLogQueryPageParam;
import com.jvfast.module.monitor.model.vo.MonitorLogQueryVo;
import com.jvfast.module.monitor.service.MonitorLogService;
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
 * 日志表 前端控制器
 * </p>
 *
 * @author Walter Hu
 * @since 2019-11-18
 */
@RequiredArgsConstructor
@Slf4j
@Api(tags = "SysLog接口")
@RestController
@RequestMapping("/monitor/log")
public class MonitorLogController {
    private final MonitorLogService monitorLogService;

    /**
     * 添加系统日志表
     */
    @Log(title = "新增日志", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add")
    @ApiOperation(value = "添加SysLog对象", notes = "添加系统日志表", response = Result.class)
    public Result<Boolean> addSysLog(@Valid @RequestBody MonitorLogAddParam sysLogAddParam) {
        boolean flag = monitorLogService.saveMonitorLog(sysLogAddParam);
        return Result.status(flag);
    }

    /**
     * 添加系统日志表
     */
    @Log(title = "批量新增日志", businessType = BusinessTypeEnum.ADD)
    @PostMapping("/add/batch")
    @ApiOperation(value = "批量添加SysLog对象", notes = "添加系统日志表", response = Result.class)
    public Result<Boolean> addSysLogBatch(@Valid @RequestBody List<MonitorLogAddParam> sysLogAddParam) {
        boolean flag = monitorLogService.saveMonitorLogBatch(sysLogAddParam);
        return Result.status(flag);
    }

    /**
     * 删除系统日志表
     */
    @Log(title = "删除日志", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/del")
    @ApiOperation(value = "删除SysLog对象", notes = "删除系统日志表", response = Result.class)
    public Result<Boolean> deleteSysLog(@Valid @RequestBody IdParam idParam) {
        boolean flag = monitorLogService.removeMonitorLogById(idParam);
        return Result.status(flag);
    }

    /**
     * 批量删除日志表
     */
    @Log(title = "批量删除日志", businessType = BusinessTypeEnum.REMOVE)
    @PostMapping("/delbatch")
    @ApiOperation(value = "批量删除SysLog对象", notes = "批量删除日志表", response = Result.class)
    public Result<Boolean> deleteSysLogs(@Valid @RequestBody IdBatchParam idBatchParam) {
        boolean flag = monitorLogService.removeMonitorLogByIds(idBatchParam);
        return Result.status(flag);
    }

    /**
     * 获取系统操作记录
     */
    @PostMapping("/id")
    @ApiOperation(value = "根据id获取MonitorLog对象详情", notes = "操作日志", response = MonitorLog.class)
    public Result<MonitorLog> getMonitorLog(@Valid @RequestBody IdParam idParam) {
        MonitorLog monitorLog = monitorLogService.getMonitorLogById(idParam);
        return Result.success(monitorLog);
    }

    /**
     * 日志表分页列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "获取SysLogQueryVo分页列表", notes = "日志表分页列表", response = MonitorLogQueryVo.class)
    public Result<IPage<MonitorLogQueryVo>> getSysLogPageList(@Valid @RequestBody(required = false) MonitorLogQueryPageParam sysLogQueryPageParam) throws Exception {
        IPage<MonitorLogQueryVo> paging = monitorLogService.pageMonitorLogQueryVo(sysLogQueryPageParam);
        return Result.success(paging);
    }

    /***************************** 以下为扩展接口 ******************************************************/
}
