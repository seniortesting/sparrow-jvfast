package com.jvfast.module.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jvfast.module.monitor.model.param.ScheduleJobParam;
import com.jvfast.module.monitor.model.param.ScheduleJobQueryPageParam;
import com.jvfast.module.monitor.model.vo.ScheduleJobVo;

import java.util.List;
import java.util.Map;

public interface MonitorScheduleJobService {

    /***
     *
     * 列出所有已经配置的定时任务
     *
     * @param: [pageNum, PageSize]
     * @return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<JobListEntity>
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    IPage<ScheduleJobVo> listJobs(ScheduleJobQueryPageParam scheduleJobQueryPageParam);

    /***
     *
     * 列出所有已经可用但是没有配置定时器的任务
     *
     * @param: []
     * @return: java.util.Map<java.lang.String, java.lang.String>
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    List<Map<String, String>> listAllImplementedJobs();

    /***
     *
     * 添加任务
     *
     * @param: [jobClassName, jobGroupName, cronExpression, params]
     * @return: boolean
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    boolean addJob(ScheduleJobParam scheduleJobParam);

    /***
     *
     * 更新任务
     *
     * @param: [jobClassName, jobGroupName, cronExpression, params]
     * @return: boolean
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    boolean updateJob(ScheduleJobParam scheduleJobParam);

    /***
     *
     * 删除任务
     *
     * @param: [jobClassName, jobGroupName]
     * @return: boolean
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    boolean deletejob(String jobName);
    /***
     *
     * 立即运行任务
     *
     * @param: [jobName]
     * @return: boolean
     * @author: Walter Hu
     * @date: 2019/5/25 0025
     */
    boolean startJob(String jobName);

    /***
     *
     * 暂停任务
     *
     * @param: [jobClassName, jobGroupName]
     * @return: boolean
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    boolean pauseJob(String jobName);

    /***
     *
     * 恢复任务
     *
     * @param: [jobClassName, jobGroupName]
     * @return: boolean
     * @author: Walter Hu
     * @date: 2019/5/24 0024
     */
    boolean resumeJob(String jobName);

    /***
     *
     *停止任务
     *
     *
     * @param: [jobName]
     * @return: boolean
     * @author: Walter Hu
     * @date: 2019/5/25 0025
     */
    boolean stopJob(String jobName);

}
