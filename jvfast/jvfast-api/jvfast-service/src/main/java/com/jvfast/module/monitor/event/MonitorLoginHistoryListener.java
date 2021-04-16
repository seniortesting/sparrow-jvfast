package com.jvfast.module.monitor.event;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.jvfast.common.constant.HttpConst;
import com.jvfast.common.util.IPUtil;
import com.jvfast.common.util.ServletUtil;
import com.jvfast.module.monitor.model.entity.MonitorLoginHistory;
import com.jvfast.module.monitor.service.MonitorLoginHistoryService;
import com.jvfast.module.sys.event.LoginHistoryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class MonitorLoginHistoryListener {
    private final MonitorLoginHistoryService monitorLoginHistoryService;

    @Async
    @Order
    @EventListener(LoginHistoryEvent.class)
    public void saveLog(LoginHistoryEvent event) {
        MonitorLoginHistory monitorLoginHistory = (MonitorLoginHistory) event.getSource();
        String requestIp = IPUtil.getRequestIp();
        String userAgentValue = ServletUtil.getHeader(HttpConst.USER_AGENT_HEADER);
        UserAgent userAgent = UserAgentUtil.parse(userAgentValue);
        String browser = userAgent.getBrowser().getName();
        String os = userAgent.getOs().getName();

        String realIPAddress = IPUtil.getRealAddressByIP(requestIp);

        monitorLoginHistory.setIpAddress(requestIp);
        monitorLoginHistory.setLoginLocation(realIPAddress);
        monitorLoginHistory.setBrowser(browser);
        monitorLoginHistory.setOs(os);
        monitorLoginHistory.setLoginTime(LocalDateTime.now());
        monitorLoginHistoryService.save(monitorLoginHistory);
    }
}
