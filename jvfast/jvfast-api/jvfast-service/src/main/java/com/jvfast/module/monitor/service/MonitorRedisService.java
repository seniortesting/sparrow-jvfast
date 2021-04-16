package com.jvfast.module.monitor.service;

import com.jvfast.module.monitor.model.entity.RedisInfo;

import java.util.List;
import java.util.Map;

public interface MonitorRedisService {

    /**
     * 获取 redis 的详细信息
     *
     * @return List
     */
    List<RedisInfo> getRedisInfo();

    /**
     * 获取 redis key 数量
     *
     * @return Map
     */
    Map<String, Object> getKeysSize();

    /**
     * 获取 redis 内存信息
     *
     * @return Map
     */
    Map<String, Object> getMemoryInfo();

}
