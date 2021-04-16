package com.jvfast.common.config.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class AsyncTaskConfig {

    /***
     *
     * 对于中配置的默认线程池进行自定义
     *
     * 所以查看对应的 {link TaskExecutor}发现，可以使用如下方法进行异步操作：
     * ThreadPoolTaskExecutor
     * AsyncTaskExecutor
     *
     * @see org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration
     * @param: []
     * @return: org.springframework.boot.task.TaskExecutorCustomizer
     * @author: Walter Hu
     * @date: 2019/5/31 0031
     */
    @Bean
    public TaskExecutorCustomizer taskExecutorCustomizer() {
        return (taskExecutor) -> {
            //rejection-policy：当pool已经达到max size的时候，如何处理新任务
            //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
            //对拒绝task的处理策略
            ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
            taskExecutor.setRejectedExecutionHandler(callerRunsPolicy);
        };
    }

    /**
     * 必须要加上: @EnableScheduling
     *
     * @return
     * @see org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration
     */
    @Bean
    public TaskSchedulerCustomizer taskSchedulerCustomizer() {
        return (taskScheduler) -> {
            ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
            taskScheduler.setDaemon(true);
            taskScheduler.setRejectedExecutionHandler(callerRunsPolicy);
        };
    }
}
