package com.jvfast.common.config.async;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class AsyncTask {

    private final int OPERATE_DELAY_TIME = 10;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;


    /**
     * 异步执行任务
     *
     * @param task
     */
    public void executeTask(Runnable task) {
        threadPoolTaskExecutor.submit(task);
    }

    /**
     * 异步延迟执行任务,每隔10分钟执行一次任务
     * 发送短信
     * 记录日志
     * 用于后台记录日志,或者其他的静默任务
     *
     * @param task 任务
     */
    public void executeSchedule(Runnable task) {
        threadPoolTaskScheduler.scheduleWithFixedDelay(task, Duration.ofSeconds(OPERATE_DELAY_TIME));
    }

}
