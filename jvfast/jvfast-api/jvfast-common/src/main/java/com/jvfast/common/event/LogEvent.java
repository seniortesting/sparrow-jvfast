package com.jvfast.common.event;

import com.jvfast.common.entity.BusinessLog;
import org.springframework.context.ApplicationEvent;

/**
 * @author Administrator
 */
public class LogEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public LogEvent(BusinessLog source) {
        super(source);
    }
}
