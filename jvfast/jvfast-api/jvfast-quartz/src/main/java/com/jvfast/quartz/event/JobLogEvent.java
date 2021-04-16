package com.jvfast.quartz.event;

import com.jvfast.quartz.entity.QuartzJobLog;
import org.springframework.context.ApplicationEvent;

public class JobLogEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public JobLogEvent(QuartzJobLog source) {
        super(source);
    }
}
