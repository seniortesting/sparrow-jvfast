package com.jvfast.wx.common.event;

import com.jvfast.wx.common.entity.WxUserDetail;
import org.springframework.context.ApplicationEvent;

/**
 * @author Walter
 */
public class WxUserInfoStoreEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public WxUserInfoStoreEvent(WxUserDetail source) {
        super(source);
    }
}
