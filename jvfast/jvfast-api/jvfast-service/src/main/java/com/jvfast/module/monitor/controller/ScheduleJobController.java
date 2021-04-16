package com.jvfast.module.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.exception.DaoException;
import com.jvfast.module.monitor.model.param.ScheduleJobNameParam;
import com.jvfast.module.monitor.model.param.ScheduleJobParam;
import com.jvfast.module.monitor.model.param.ScheduleJobQueryPageParam;
import com.jvfast.module.monitor.model.vo.ScheduleJobVo;
import com.jvfast.module.monitor.service.MonitorScheduleJobService;
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
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Api(tags = "QuartzSchedule接口")
@RestController
@RequestMapping("/monitor/task")
public class ScheduleJobController {


    private final MonitorScheduleJobService scheduleJobService;

    @ApiOperation(value = "定时任务列表", notes = "所有设置的定时任务列表", response = Result.class)
    @PostMapping("/page")
    public Result<IPage<ScheduleJobVo>> pageJobResult(@RequestBody @Valid ScheduleJobQueryPageParam scheduleJobQueryPageParam) {
        IPage<ScheduleJobVo> jobListEntityPage = scheduleJobService.listJobs(scheduleJobQueryPageParam);
        return Result.success(jobListEntityPage);
    }

    @ApiOperation(value = "添加定时任务", notes = "添加详细的定时任务", response = Result.class)
    @PostMapping("/add")
    public Result save(@RequestBody @Valid ScheduleJobParam jobRequest) {
        boolean addJob = scheduleJobService.addJob(jobRequest);
        if (!addJob) {
            return Result.fail(ResultCode.DATABASE_ERROR, "保存定时任务失败");
        } else {
            return Result.success(jobRequest);
        }
    }

    @ApiOperation(value = "更新定时任务", notes = "更新任务详情", response = Result.class)
    @PostMapping("/update")
    public Result update(@RequestBody @Valid ScheduleJobParam jobRequest) {
        boolean updateJob = scheduleJobService.updateJob(jobRequest);
        if (!updateJob) {
            return Result.fail(ResultCode.DATABASE_ERROR, "更新定时任务失败");
        } else {
            return Result.success(jobRequest);
        }
    }

    @ApiOperation(value = "启动定时任务", notes = "启动任务", response = Result.class)
    @PostMapping("/start")
    public Result runNow(@RequestBody @Valid ScheduleJobNameParam jobNameParam) {
        String jobName = jobNameParam.getJobName();
        boolean runJob = scheduleJobService.startJob(jobName);
        if (!runJob) {
            return Result.fail(ResultCode.DATABASE_ERROR, "启动定时任务失败");
        } else {
            return Result.success(jobNameParam);
        }
    }

    @ApiOperation(value = "停止定时任务", notes = "停止定时任务", response = Result.class)
    @PostMapping("/stop")
    public Result stop(@RequestBody @Valid ScheduleJobNameParam jobNameParam) {
        String jobName = jobNameParam.getJobName();
        boolean resumeJob = scheduleJobService.stopJob(jobName);
        if (!resumeJob) {
            return Result.fail(ResultCode.DATABASE_ERROR, "停止定时任务失败");
        } else {
            return Result.success(jobNameParam);
        }
    }

    @ApiOperation(value = "暂停定时任务", notes = "暂停任务", response = Result.class)
    @PostMapping("/pause")
    public Result pause(@RequestBody @Valid ScheduleJobNameParam jobNameParam) {
        String jobName = jobNameParam.getJobName();
        boolean pauseJob = scheduleJobService.pauseJob(jobName);
        if (!pauseJob) {
            return Result.fail(ResultCode.DATABASE_ERROR, "暂停定时任务失败");
        } else {
            return Result.success(jobNameParam);
        }
    }

    @ApiOperation(value = "恢复重新启动已经停止的定时任务", notes = "恢复继续任务", response = Result.class)
    @PostMapping("/resume")
    public Result resume(@RequestBody @Valid ScheduleJobNameParam jobNameParam) {
        String jobName = jobNameParam.getJobName();
        boolean resumeJob = scheduleJobService.resumeJob(jobName);
        if (!resumeJob) {
            return Result.fail(ResultCode.DATABASE_ERROR, "恢复定时任务失败");
        } else {
            return Result.success(jobNameParam);
        }
    }

    @ApiOperation(value = "删除定时任务", notes = "删除任务", response = Result.class)
    @PostMapping("/del")
    public Result remove(@RequestBody @Valid ScheduleJobNameParam jobNameParam) {
        String jobName = jobNameParam.getJobName();
        boolean deletejob = scheduleJobService.deletejob(jobName);
        if (!deletejob) {
            throw new DaoException("删除失败");
        } else {
            return Result.success(jobNameParam);
        }
    }

    @ApiOperation(value = "可用定时任务列表", notes = "当前系统定时任务", response = Result.class)
    @PostMapping("/jobs")
    public Result listJobs() {
        List<Map<String, String>> jobs = scheduleJobService.listAllImplementedJobs();
        return Result.success(jobs);
    }

}
