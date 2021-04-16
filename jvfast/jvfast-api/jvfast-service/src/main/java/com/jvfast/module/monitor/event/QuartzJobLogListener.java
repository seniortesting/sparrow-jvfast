package com.jvfast.module.monitor.event;

import com.jvfast.module.monitor.converter.QuartzJobLogConverter;
import com.jvfast.module.monitor.service.QuartzJobLogService;
import com.jvfast.quartz.entity.QuartzJobLog;
import com.jvfast.quartz.event.JobLogEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QuartzJobLogListener {

    private final QuartzJobLogService quartzJobLogService;

    /**
     * 监听日志事件
     *
     * @param event
     */
    @Async
    @Order
    @EventListener(JobLogEvent.class)
    public void saveLog(JobLogEvent event) {
        QuartzJobLog source = (QuartzJobLog) event.getSource();
        com.jvfast.module.monitor.model.entity.QuartzJobLog monitorLog = QuartzJobLogConverter.INSTANCE.convertJobLog(source);
        quartzJobLogService.save(monitorLog);
    }
}
