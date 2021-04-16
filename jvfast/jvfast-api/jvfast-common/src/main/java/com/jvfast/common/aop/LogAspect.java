package com.jvfast.common.aop;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jvfast.common.config.async.AsyncTask;
import com.jvfast.common.entity.BusinessLog;
import com.jvfast.common.enums.BusinessTypeEnum;
import com.jvfast.common.enums.OperatorTypeEnum;
import com.jvfast.common.event.LogEvent;
import com.jvfast.common.util.IPUtil;
import com.jvfast.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * @description: 记录日志到logstash的切面
 * @project: vrpanoservice
 * @author: Walter Hu
 * @create: 2019-01-17 14:50
 **/
@Slf4j
@Aspect
@Component
@Order
public class LogAspect {
    private static final NamedThreadLocal<Long> elapseTime = new NamedThreadLocal<Long>("elapseTime");

    private static final int MAX_CHARACTERS = 2000;

    @Autowired
    private AsyncTask asyncTask;


    @Pointcut("@annotation(com.jvfast.common.annotation.Log)")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        elapseTime.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(value = "logPointCut()", returning = "data")
    public void doAfterReturning(JoinPoint joinPoint, Object data) {
        handleLog(joinPoint, null, data);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    public void handleLog(final JoinPoint joinPoint, final Exception e, Object result) {
        //获取当前请求对象
        long endTime = System.currentTimeMillis();
        long seconds = (endTime - elapseTime.get());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        BusinessLog businessLogEntity = new BusinessLog();
        businessLogEntity.setStatus(e != null ? Boolean.FALSE : Boolean.TRUE);
        businessLogEntity.setRequestStatus(e != null ? 0 : 1);
        businessLogEntity.setRequestErrorMsg(!Objects.isNull(e) && !Objects.isNull(e.getMessage()) && e.getMessage().length() > MAX_CHARACTERS ? StrUtil.sub(e.getMessage(), 0, MAX_CHARACTERS) : (e == null ? "" : e.getMessage()));

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = className + "." + method.getName() + "()";

        com.jvfast.common.annotation.Log logAnnotation = null;
        if (method != null) {
            logAnnotation = method.getAnnotation(com.jvfast.common.annotation.Log.class);
            if (logAnnotation == null) {
                return;
            }
        }
        // *========数据库日志=========*//
        String title = logAnnotation.title();
        BusinessTypeEnum businessType = logAnnotation.businessType();
        OperatorTypeEnum operatorType = logAnnotation.operatorType();

        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String contentType = request.getContentType();
        String requestIp = IPUtil.getRequestIp();

        // 判断控制器方法参数中是否有RequestBody注解
        String bodyResult = result != null ? JSONUtil.toJsonStr(result) : "";
        Annotation[][] annotations = method.getParameterAnnotations();
        boolean isRequestBody = isRequestBody(annotations);
        Object requestParamJson = getRequestParamJsonString(joinPoint, request, requestMethod, contentType, isRequestBody);
        String requestParam = requestParamJson != null ? JSONUtil.toJsonStr(requestParamJson) : "";

        businessLogEntity.setBusinessTitle(title);
        businessLogEntity.setBusinessType(businessType.ordinal());
        businessLogEntity.setOperationType(operatorType.ordinal());

        businessLogEntity.setRequestUrl(requestURI);
        businessLogEntity.setMethod(methodName);
        businessLogEntity.setRequestMethod(requestMethod);
        businessLogEntity.setRequestParam(requestParam != null && requestParam.length() > MAX_CHARACTERS ? requestParam.substring(0, MAX_CHARACTERS) : requestParam);
        businessLogEntity.setRequestResult(bodyResult != null && bodyResult.length() > MAX_CHARACTERS ? bodyResult.substring(0, MAX_CHARACTERS) : bodyResult);
        businessLogEntity.setRequestTime(seconds);
        businessLogEntity.setRequestIp(requestIp);
//
        // 异步保存数据
        SpringUtil.publishEvent(new LogEvent(businessLogEntity));
//        asyncTask.executeTask(AsyncTaskFactory.recordBusinessLog(sysLog));
    }

    /**
     * 判断控制器方法参数中是否有RequestBody注解
     *
     * @param annotations
     * @return
     */
    private boolean isRequestBody(Annotation[][] annotations) {
        boolean isRequestBody = false;
        for (Annotation[] annotationArray : annotations) {
            for (Annotation annotation : annotationArray) {
                if (annotation instanceof RequestBody) {
                    isRequestBody = true;
                }
            }
        }
        return isRequestBody;
    }

    /**
     * 获取请求参数JSON字符串
     *
     * @param joinPoint
     * @param request
     * @param requestMethod
     * @param contentType
     * @param isRequestBody
     */
    private Object getRequestParamJsonString(JoinPoint joinPoint, HttpServletRequest request, String requestMethod, String contentType, boolean isRequestBody) {
        /**
         * 判断请求内容类型
         * 通常有3中请求内容类型
         * 1.发送get请求时,contentType为null
         * 2.发送post请求时,contentType为application/x-www-form-urlencoded
         * 3.发送post json请求,contentType为application/json
         * 4.发送post json请求并有RequestBody注解,contentType为application/json
         */
        Object paramObject = null;
        int requestType = 0;
        if (HttpMethod.GET.name().equalsIgnoreCase(requestMethod)) {
            requestType = 1;
        } else if (HttpMethod.POST.name().equalsIgnoreCase(requestMethod)) {
            if (contentType == null) {
                requestType = 5;
            } else if (contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
                requestType = 6;
            } else if (contentType.startsWith(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                requestType = 2;
            } else if (contentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {
                if (isRequestBody) {
                    requestType = 4;
                } else {
                    requestType = 3;
                }
            }
        }

        // 1,2,3中类型时,获取getParameterMap中所有的值,处理后序列化成JSON字符串
        if (requestType == 1 || requestType == 2 || requestType == 3 || requestType == 5 || requestType == 6) {
            Map<String, String[]> paramsMap = request.getParameterMap();
            paramObject = getJsonForParamMap(paramsMap);
        } else if (requestType == 4) { // POST,application/json,RequestBody的类型,简单判断,然后序列化成JSON字符串
            Object[] args = joinPoint.getArgs();
            paramObject = argsArrayToJsonString(args);
        }

        return paramObject;
    }

    /**
     * 请求参数拼装
     *
     * @param args
     * @return
     */
    private Object argsArrayToJsonString(Object[] args) {
        if (args == null) {
            return null;
        }
        if (args.length == 1) {
            return args[0];
        } else {
            return args[0];
        }
    }


    /**
     * 获取参数Map的JSON字符串
     *
     * @param paramsMap
     * @return
     */
    public JSONObject getJsonForParamMap(Map<String, String[]> paramsMap) {
        int paramSize = paramsMap.size();
        if (paramsMap == null || paramSize == 0) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String[]> kv : paramsMap.entrySet()) {
            String key = kv.getKey();
            String[] values = kv.getValue();
            if (values == null) { // 没有值
                jsonObject.put(key, null);
            } else if (values.length == 1) { // 一个值
                jsonObject.put(key, values[0]);
            } else { // 多个值
                jsonObject.put(key, values);
            }
        }
        return jsonObject;
    }


}
