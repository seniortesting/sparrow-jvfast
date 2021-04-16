package com.jvfast.notification.common.event;

import com.jvfast.notification.common.entity.NotificationVo;
import org.springframework.context.ApplicationEvent;

/**
 * @author Walter Hu
 * 接受通知
 */
public class NotificationEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public NotificationEvent(NotificationVo source) {
        super(source);
    }
}
