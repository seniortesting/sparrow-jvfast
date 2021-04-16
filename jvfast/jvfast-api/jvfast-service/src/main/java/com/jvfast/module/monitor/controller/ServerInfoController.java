package com.jvfast.module.monitor.controller;

import com.jvfast.common.api.Result;
import com.jvfast.module.monitor.model.entity.RedisInfo;
import com.jvfast.module.monitor.model.vo.SystemInfoQueryVo;
import com.jvfast.module.monitor.service.MonitorRedisService;
import com.jvfast.module.monitor.service.MonitorSystemInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "ServerInfo服务器指标接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitor/server")
public class ServerInfoController {

    private final MonitorSystemInfoService systemInfoService;
    private final MonitorRedisService redisService;


    @PostMapping("/info")
    @ApiOperation(value = "获取服务器相关性能指标信息", notes = "获取服务器信息")
    public Result getServerInfo() {
        SystemInfoQueryVo systemInfo = systemInfoService.getSystemInfo();
        return Result.success(systemInfo);
    }

    /**
     * Redis详细信息
     *
     * @return
     */
    @PostMapping("/redis/info")
    public Result<?> getRedisInfo() {
        List<RedisInfo> infoList = this.redisService.getRedisInfo();
        return Result.success(infoList);
    }

    @PostMapping("/redis/keys")
    public Result<Map<String, Object>> getKeysSize() {
        Map<String, Object> keysSize = redisService.getKeysSize();
        return Result.success(keysSize);
    }

    @PostMapping("/redis/memory")
    public Result<Map<String, Object>> getMemoryInfo() {
        Map<String, Object> memoryInfo = redisService.getMemoryInfo();
        return Result.success(memoryInfo);
    }
}
