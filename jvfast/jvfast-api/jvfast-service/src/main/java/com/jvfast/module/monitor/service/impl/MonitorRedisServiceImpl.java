package com.jvfast.module.monitor.service.impl;

import cn.hutool.core.util.StrUtil;
import com.jvfast.module.monitor.model.entity.RedisInfo;
import com.jvfast.module.monitor.service.MonitorRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Redis 监控信息获取
 */
@Slf4j
@Service
public class MonitorRedisServiceImpl implements MonitorRedisService {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * Redis详细信息
     */
    @Override
    public List<RedisInfo> getRedisInfo() {
        Properties info = redisConnectionFactory.getConnection().info();
        List<RedisInfo> infoList = new ArrayList<>();
        RedisInfo redisInfo = null;
        for (Map.Entry<Object, Object> entry : info.entrySet()) {
            redisInfo = new RedisInfo();
            redisInfo.setKey(StrUtil.toString(entry.getKey()));
            redisInfo.setValue(StrUtil.toString(entry.getValue()));
            infoList.add(redisInfo);
        }
        return infoList;
    }

    @Override
    public Map<String, Object> getKeysSize() {
        Long dbSize = redisConnectionFactory.getConnection().dbSize();
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", System.currentTimeMillis());
        map.put("dbSize", dbSize);

        log.info("--getKeysSize--: " + map.toString());
        return map;
    }

    @Override
    public Map<String, Object> getMemoryInfo() {
        Map<String, Object> map = null;
        Properties info = redisConnectionFactory.getConnection().info();
        for (Map.Entry<Object, Object> entry : info.entrySet()) {
            String key = StrUtil.toString(entry.getKey());
            if ("used_memory".equals(key)) {
                map = new HashMap<>();
                map.put("used_memory", entry.getValue());
                map.put("create_time", System.currentTimeMillis());
            }
        }
        log.info("--getMemoryInfo--: " + map.toString());
        return map;
    }
}
