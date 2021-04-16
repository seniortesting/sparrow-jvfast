package com.jvfast.module.monitor.service;


import com.jvfast.module.monitor.model.vo.SystemInfoQueryVo;

public interface MonitorSystemInfoService {

    /**
     * 获取当前服务器信息
     *
     * @return
     */
    SystemInfoQueryVo getSystemInfo();
}
