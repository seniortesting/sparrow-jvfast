package com.jvfast.module.monitor.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jvfast.common.exception.DaoExistException;
import com.jvfast.module.monitor.mapper.ScheduleJobMapper;
import com.jvfast.module.monitor.model.param.ScheduleJobParam;
import com.jvfast.module.monitor.model.param.ScheduleJobQueryPageParam;
import com.jvfast.module.monitor.model.vo.ScheduleJobVo;
import com.jvfast.module.monitor.service.MonitorScheduleJobService;
import com.jvfast.quartz.entity.QuartzJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MonitorScheduleJobServiceImpl implements MonitorScheduleJobService {

    private final Scheduler scheduler;
    private final ScheduleJobMapper jobMapper;
    private final List<QuartzJob> jobs;

    @Override
    public IPage<ScheduleJobVo> listJobs(ScheduleJobQueryPageParam scheduleJobQueryPageParam) {
        // 请求传递的分页参数
        Integer pageNo = scheduleJobQueryPageParam.getPageNo();
        Integer pageSize = scheduleJobQueryPageParam.getPageSize();
        String keyword = scheduleJobQueryPageParam.getKeyword();
        log.info("查询请求参数页码: {}, 大小: {},查询参数: {}", pageNo, pageSize, keyword);
        Page pagingData = new Page(pageNo, pageSize, true);
        // 查询分页结果
        IPage<ScheduleJobVo> jobListEntityIPage = jobMapper.getJobs(pagingData, scheduleJobQueryPageParam);
        List<ScheduleJobVo> records = jobListEntityIPage.getRecords();
        records.forEach(record -> {
            String jobName = record.getJobName();
            try {
                JobDetail jobDetail = scheduler.getJobDetail(new JobKey(jobName, jobName));
                if (null != jobDetail) {
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    record.setJobData(jobDataMap);
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        });
        jobListEntityIPage.setRecords(records);
        return jobListEntityIPage;
    }

    @Override
    public List<Map<String, String>> listAllImplementedJobs() {
        ArrayList<Map<String, String>> jobs = Lists.newArrayList();
        this.jobs.forEach(job -> {
            Map<String, String> jobClassMap = Maps.newHashMap();
            Class<? extends Job> jobClass = job.getClass();
            String value = jobClass.getName();
            String simpleName = jobClass.getSimpleName();
            jobClassMap.put("key", value);
            jobClassMap.put("value", simpleName);
            jobs.add(jobClassMap);
        });
        return jobs;
    }

    @Override
    public boolean addJob(ScheduleJobParam scheduleJob) {
        boolean addJobSuccess;
        String jobName = scheduleJob.getJobName();
        String jobClassName = scheduleJob.getJobClassName();
        String description = scheduleJob.getDescription();
        Boolean recovery = scheduleJob.getRecovery();
        String jobData = scheduleJob.getJobData();
        String cronExpression = scheduleJob.getCronExpression();
        Boolean isPause = scheduleJob.getIsPause();
        try {
            JobKey jobKey = getJobKey(jobName, jobName);
            boolean foundJobDetail = scheduler.checkExists(jobKey);
            if (!foundJobDetail) {
                // 启动调度器
                scheduler.start();
                Class<? extends Job> jobClass = getClass(jobClassName).getClass();
                JobDetailImpl jobDetail = (JobDetailImpl) JobBuilder.newJob(jobClass)
                        .withIdentity(jobName, jobName)
                        .withDescription(description)
                        // 如果一个job没有任何关联的trigger，是否持久化保存，此处不保存，自动删除
                        .storeDurably(false)
                        // 当任务运行过程非正常退出时（比如进程崩溃，机器断电，但不包括抛出异常这种情况），Quartz再次启动时，会重新运行一次这个任务实例
                        .requestRecovery(recovery != null ? recovery : false)
                        .build();
                if (StrUtil.isNotBlank(jobData)) {
                    String trimJobData = jobData.trim();
                    Map<String, String> validParams = Splitter.on("\n").withKeyValueSeparator("=").split(trimJobData);
                    JobDataMap jobDataMap = new JobDataMap(validParams);
                    jobDetail.setJobDataMap(jobDataMap);

                }
                if (StrUtil.isNotBlank(description)) {
                    jobDetail.setDescription(description);
                }

                cronExpression = cronExpression != null ? cronExpression : "";
                CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                        .inTimeZone(TimeZone.getDefault())
                        // 对应配置的调度中的misfireThreshold 参数，如果任务超过设置的时间没有执行，这次的任务不执行
                        .withMisfireHandlingInstructionDoNothing();

                CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName, jobName)
                        .withDescription(description)
                        .withSchedule(cronScheduleBuilder)
                        .forJob(jobDetail)
                        .build();

                //重置启动时间
                ((CronTriggerImpl) cronTrigger).setStartTime(new Date());
                //执行定时任务
                scheduler.scheduleJob(jobDetail, cronTrigger);
                // 暂停任务
                if (isPause != null && isPause) {
                    pauseJob(jobName);
                }
                addJobSuccess = true;

            } else {
                String errMsg = "当前任务已经存在，不能重复添加";
                throw new DaoExistException(errMsg);
            }
        } catch (Exception e) {
            log.error("创建任务异常:", e);
            addJobSuccess = false;
        }
        return addJobSuccess;
    }

    @Override
    public boolean updateJob(ScheduleJobParam scheduleJob) {
        boolean updateJobSuccess;
        String jobName = scheduleJob.getJobName();
        String description = scheduleJob.getDescription();
        Boolean recovery = scheduleJob.getRecovery();
        String jobData = scheduleJob.getJobData();
        String cronExpression = scheduleJob.getCronExpression();
        Boolean isPause = scheduleJob.getIsPause();
        try {
            TriggerKey triggerKey = getTriggerKey(jobName, jobName);
            JobKey jobKey = getJobKey(jobName, jobName);
            JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (jobDetail != null && cronTrigger != null) {
                jobDetail.setDescription(description);
                JobDataMap jobDataMap = null;
                if (StrUtil.isNotBlank(jobData)) {
                    String trimJobData = jobData.trim();
                    Map<String, String> updateParams = Splitter.on("\n").withKeyValueSeparator("=").split(trimJobData);
                    jobDataMap = new JobDataMap(updateParams);
                }
                jobDetail.setJobDataMap(jobDataMap);
                // 重新保存内容更新
                jobDetail.setDurability(false);
                jobDetail.setRequestsRecovery(recovery != null ? recovery : false);
                scheduler.addJob(jobDetail, true, true);
                // 重新设置任务定时器
                CronScheduleBuilder newCronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                        .inTimeZone(TimeZone.getDefault())
                        // 对应配置的调度中的misfireThreshold 参数，如果任务超过设置的时间没有执行，这次的任务不执行
                        .withMisfireHandlingInstructionDoNothing();
                cronTrigger = cronTrigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withDescription(description)
                        .withSchedule(newCronScheduleBuilder)
                        .forJob(jobDetail)
                        .build();


                scheduler.rescheduleJob(triggerKey, cronTrigger);
                if (isPause != null && isPause) {
                    pauseJob(jobName);
                }
                updateJobSuccess = true;
            } else {
                throw new DaoExistException("任务名称，触发器不存在");
            }
        } catch (Exception e) {
            updateJobSuccess = false;
            log.error("更新任务异常:", e);
        }
        return updateJobSuccess;
    }

    @Override
    public boolean deletejob(String jobName) {
        boolean deletedSuccess;
        try {
            JobKey jobKey = getJobKey(jobName, jobName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                TriggerKey triggerKey = getTriggerKey(jobName, jobName);
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                scheduler.deleteJob(jobKey);
            }
            deletedSuccess = true;
        } catch (Exception e) {
            deletedSuccess = false;
            log.error("删除任务异常", e);
        }
        return deletedSuccess;
    }

    @Override
    public boolean startJob(String jobName) {
        boolean triggerJobSuccess;
        try {
            JobKey jobKey = getJobKey(jobName, jobName);
            scheduler.triggerJob(jobKey);
            triggerJobSuccess = true;
        } catch (Exception e) {
            triggerJobSuccess = false;
            log.error("启动任务异常", e);
        }
        return triggerJobSuccess;
    }

    @Override
    public boolean resumeJob(String jobName) {
        boolean resumeJobSuccess;
        try {
            JobKey jobKey = getJobKey(jobName, jobName);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                scheduler.resumeJob(jobKey);
            }
            resumeJobSuccess = true;
        } catch (Exception e) {
            resumeJobSuccess = false;
            log.error("继续执行任务异常", e);
        }
        return resumeJobSuccess;
    }

    @Override
    public boolean pauseJob(String jobName) {
        boolean pauseJobSuccess;
        try {
            JobKey jobKey = getJobKey(jobName, jobName);
            scheduler.pauseJob(jobKey);
            pauseJobSuccess = true;
        } catch (Exception e) {
            pauseJobSuccess = false;
            log.error("暂停任务异常", e);
        }
        return pauseJobSuccess;
    }


    @Override
    public boolean stopJob(String jobName) {
        boolean stopSuccess;
        try {
            TriggerKey triggerKey = getTriggerKey(jobName, jobName);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger != null) {
                scheduler.unscheduleJob(triggerKey);
            }
            stopSuccess = true;
        } catch (Exception e) {
            stopSuccess = false;
            log.error("停止任务异常", e);
        }
        return stopSuccess;
    }


    /***
     *
     * 得到请求中传过来的job实例类名称
     *
     * @param: [classname]
     * @return: org.quartz.Job
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    private static Job getClass(String classname) throws Exception {
        Class<?> cls = Class.forName(classname);
        return (Job) cls.newInstance();

    }

    /***
     *
     * 得到对应的jobKey
     *
     * @param: [name, groupName]
     * @return: org.quartz.JobKey
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    private JobKey getJobKey(String name, String groupName) {
        if (StrUtil.isEmpty(name)) {
            return null;
        }
        return new JobKey(name, groupName);
    }

    /***
     *
     * 得到对应的triggerkey
     *
     * @param: [name, groupName]
     * @return: org.quartz.TriggerKey
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    private TriggerKey getTriggerKey(String name, String groupName) {
        if (StrUtil.isEmpty(name)) {
            return null;
        }
        return new TriggerKey(name, groupName);
    }

}
