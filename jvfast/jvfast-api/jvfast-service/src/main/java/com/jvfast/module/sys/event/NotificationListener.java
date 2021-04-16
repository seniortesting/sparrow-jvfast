package com.jvfast.module.sys.event;

import com.jvfast.module.sys.converter.SysNotificationConverter;
import com.jvfast.module.sys.model.entity.SysNotification;
import com.jvfast.module.sys.service.SysNotificationService;
import com.jvfast.notification.common.entity.NotificationVo;
import com.jvfast.notification.common.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Walter
 */
@RequiredArgsConstructor
@Component
public class NotificationListener {
    private final SysNotificationService sysNotificationService;

    /**
     * 保存通知信息到数据库
     * @param event
     */
    @Async
    @Order
    @EventListener(NotificationEvent.class)
    public void saveLog(NotificationEvent event) {
        NotificationVo source = (NotificationVo) event.getSource();
        SysNotification sysNotification = SysNotificationConverter.INSTANCE.convertNotification(source);
        sysNotificationService.save(sysNotification);
    }
}
