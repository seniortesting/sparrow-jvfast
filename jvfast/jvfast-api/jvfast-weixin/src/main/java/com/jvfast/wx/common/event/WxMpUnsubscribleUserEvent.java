package com.jvfast.wx.common.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Walter
 */
public class WxMpUnsubscribleUserEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param openId the object on which the event initially occurred (never {@code null})
     */
    public WxMpUnsubscribleUserEvent(String openId) {
        super(openId);
    }
}
