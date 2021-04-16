package com.jvfast.module.monitor.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ScheduleJobParam {

    @NotEmpty(message = "定时任务名称不能为空")
    private String jobName;
    @NotEmpty(message = "定时任务对应的任务类名不能为空")
    private String jobClassName;
    @NotEmpty(message = "定时任务的cron表达式不能为空")
    private String cronExpression = "";
    private String jobData;

    private Boolean recovery;

    private Boolean isPause = false;

    private String description;
}
