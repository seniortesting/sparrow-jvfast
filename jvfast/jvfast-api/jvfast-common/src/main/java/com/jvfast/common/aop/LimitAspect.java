package com.jvfast.common.aop;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableList;
import com.jvfast.common.annotation.Limit;
import com.jvfast.common.exception.BadRequestException;
import com.jvfast.common.exception.BusinessException;
import com.jvfast.common.util.IPUtil;
import com.jvfast.common.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 功能描述： redis拦截调用的次数
 *
 * @Author: Walter Hu
 * @Date: 2018/12/29 0029
 */
@Slf4j
@Aspect
@Order(2)
@Configuration
public class LimitAspect {

    private final RedisTemplate<String, Serializable> limitRedisTemplate;


    @Autowired
    public LimitAspect(RedisTemplate<String, Serializable> limitRedisTemplate) {
        this.limitRedisTemplate = limitRedisTemplate;
    }


    @Around("execution(public * *(..)) &&@annotation(com.jvfast.common.annotation.Limit))")
    public Object interceptor(ProceedingJoinPoint pjp) {
        HttpServletRequest request = ServletUtil.getRequest();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        Limit.LimitType limitType = limitAnnotation.limitType();
        String name = limitAnnotation.name();
        String key;
        int limitPeriod = limitAnnotation.seconds();
        int limitCount = limitAnnotation.count();
        switch (limitType) {
            case IP:
                key = IPUtil.getRequestIp();
                break;
            case CUSTOM:
                key = method.getName();
                break;
            default:
                key = method.getName().toLowerCase();
        }
        ImmutableList<String> keys = ImmutableList.of(StrUtil.join(limitAnnotation.prefix(), "_", key, "_", request.getRequestURI().replaceAll("/", "_")));
        try {
            String luaScript = buildLuaScript();
            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
            Number count = limitRedisTemplate.execute(redisScript, keys, limitCount, limitPeriod);
            log.info("Access try count is {} for name={} and key = {}", count, name, key);
            if (count != null && count.intValue() <= limitCount) {
                return pjp.proceed();
            } else {
                // 锁定账号，不能使用
                String errMsg = StrUtil.isNotEmpty(name) ? name : "";
                throw new BadRequestException(errMsg + "请求次数超限,请" + limitPeriod + "秒后重试");
            }
        } catch (Throwable e) {
            log.error("check limit error", e);
            if (e instanceof BadRequestException) {
                throw new BadRequestException(e.getMessage());
            } else {
                throw new BusinessException(e);
            }
        }


    }

    /**
     * 限流 脚本
     *
     * @return lua脚本
     */
    public String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }
}
