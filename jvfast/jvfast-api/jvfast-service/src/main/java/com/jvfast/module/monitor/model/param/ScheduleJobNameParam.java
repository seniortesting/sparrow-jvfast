package com.jvfast.module.monitor.model.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ScheduleJobNameParam {

    @NotEmpty(message = "定时任务名称不能为空")
    private String jobName;
}
