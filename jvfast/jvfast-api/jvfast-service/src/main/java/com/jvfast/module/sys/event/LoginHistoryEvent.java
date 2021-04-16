package com.jvfast.module.sys.event;

import com.jvfast.module.monitor.model.entity.MonitorLoginHistory;
import org.springframework.context.ApplicationEvent;

public class LoginHistoryEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LoginHistoryEvent(MonitorLoginHistory source) {
        super(source);
    }
}
