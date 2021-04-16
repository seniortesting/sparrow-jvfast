package com.jvfast.quartz.entity;

import cn.hutool.json.JSONUtil;
import com.jvfast.common.util.SpringUtil;
import com.jvfast.quartz.event.JobLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @description: 定时任务类, 所有的定时任务执行需要继承本类
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-11-21 20:38
 **/
@Slf4j
public abstract class QuartzJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        // 记录任务执行日志
        QuartzJobLog quartzJobLog = new QuartzJobLog();
        try {
            run(context);
            quartzJobLog.setStatus(true);
        } catch (Exception e) {
            quartzJobLog.setExceptionDetail(e.getMessage());
            quartzJobLog.setStatus(false);
        } finally {
            // 异步记录定时任务的执行日志
            Trigger trigger = context.getTrigger();
            JobDetail jobDetail = context.getJobDetail();
            JobKey jobDetailKey = jobDetail.getKey();
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            String jobParams = jobDataMap.isEmpty() ? "" : JSONUtil.toJsonStr(jobDataMap);
            quartzJobLog.setJobName(jobDetailKey.getName());
            quartzJobLog.setJobGroup(jobDetailKey.getGroup());
            quartzJobLog.setJobParams(jobParams);
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                quartzJobLog.setCronExpression(cronTrigger.getCronExpression());
            }
            SpringUtil.publishEvent(new JobLogEvent(quartzJobLog));
        }

    }

    protected abstract void run(JobExecutionContext context) throws JobExecutionException;
}
