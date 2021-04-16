package com.jvfast.module.monitor.model.vo;

import lombok.Data;
import org.quartz.JobDataMap;

import java.time.LocalDateTime;

@Data
public class ScheduleJobVo {

    private String jobName;

    private String description;

    private String jobClassName;

    private LocalDateTime nextFire;

    private LocalDateTime prevFire;

    private String cronExpression;

    private Boolean recovery;

    private String state;

    private String timezonId;

    private JobDataMap jobData;

}
