package com.jvfast.common.aop;

import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.config.redis.RedisClient;
import com.jvfast.common.util.IPUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 浏览记录拦截器
 *
 * @author Walter Hu
 * @date 2019/12/9
 * @since 1.8
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@Order
@Component
public class PageViewAspect {

    private final RedisClient redisClient;
    private final AsyncTask asyncTask;

    @Pointcut("@annotation(com.jvfast.common.annotation.PageView)")
    public void pageViewJointCut() {
    }

    @Around("@annotation(com.jvfast.common.annotation.PageView)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Parameter[] parameters = method.getParameters();
        String pageId = String.valueOf(parameters[0]);

        Object result = null;
        try {
            result = pjp.proceed();
            // 异步保存对应的结果到redis中
            String requestIp = IPUtil.getRequestIp();
            String redisKey = pageId + ":" + requestIp;
            asyncTask.executeTask(() -> {

            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


        return result;
    }
}
