package com.jvfast.module.monitor.event;

import com.jvfast.common.entity.BusinessLog;
import com.jvfast.common.event.LogEvent;
import com.jvfast.module.monitor.converter.MonitorLogConverter;
import com.jvfast.module.monitor.model.entity.MonitorLog;
import com.jvfast.module.monitor.service.MonitorLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MonitorLogListener {

    private final MonitorLogService monitorLogService;

    /**
     * 监听日志事件
     *
     * @param event
     */
    @Async
    @Order
    @EventListener(LogEvent.class)
    public void saveLog(LogEvent event) {
        BusinessLog source = (BusinessLog) event.getSource();
        MonitorLog monitorLog = MonitorLogConverter.INSTANCE.convertLogToMonitorLog(source);
        monitorLogService.save(monitorLog);
    }
}
