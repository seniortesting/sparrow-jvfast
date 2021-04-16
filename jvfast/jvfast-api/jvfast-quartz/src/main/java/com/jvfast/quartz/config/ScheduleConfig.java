package com.jvfast.quartz.config;

import org.quartz.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
public class ScheduleConfig {

    /***
     * 使用web接口进行处理任务
     *
     * 参考自动化配置 {@link  org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration}
     *
     * @see JobDetail
     * @see JobBuilder
     *
     *
     * @see ScheduleBuilder
     * @see DailyTimeIntervalScheduleBuilder
     * @see SimpleScheduleBuilder
     *
     * @see Trigger
     * @see TriggerBuilder
     *
     *
     * 这里声明两个bean，会在springboot启动的时候自动加上这个任务配置，用于启动任务
     * cron表达式:
     */
}
