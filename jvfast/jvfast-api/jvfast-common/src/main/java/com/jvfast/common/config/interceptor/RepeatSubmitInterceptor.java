package com.jvfast.common.config.interceptor;

import com.jvfast.common.annotation.RepeatSubmit;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.config.jackson.JacksonUtil;
import com.jvfast.common.constant.HttpConst;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Walter
 * @deprecated 采用 {@link com.jvfast.common.annotation.Limit}
 */
@Deprecated
public class RepeatSubmitInterceptor extends HandlerInterceptorAdapter {

    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";

    public final String SESSION_REPEAT_KEY = "repeatData";


    /**
     * @param request
     * @param response
     * @param handler
     * @return true: 按照默认处理,false: 退出请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request)) {
                    String errorMsg = "不允许重复提交，请稍后再试";
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().print(Result.fail(ResultCode.LIMIT_EXCEED, errorMsg));
                    return false;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    public boolean isRepeatSubmit(HttpServletRequest request) {

        // 本次参数及系统时间
        String nowParams = JacksonUtil.toStr(request.getParameterMap());
        Map<String, Object> nowDataMap = new HashMap<String, Object>();
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

        // 请求地址（作为存放session的key值）
        String url = request.getRequestURI();

        HttpSession session = request.getSession();
        Object sessionObj = session.getAttribute(SESSION_REPEAT_KEY);
        if (sessionObj != null) {
            Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
            if (sessionMap.containsKey(url)) {
                Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
                    return true;
                }
            }
        }
        Map<String, Object> sessionMap = new HashMap<String, Object>();
        sessionMap.put(url, nowDataMap);
        session.setAttribute(SESSION_REPEAT_KEY, sessionMap);
        return false;
    }


    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        return (time1 - time2) < (HttpConst.REPEAT_SUBMIT_INTERVAL_TIME * 1000);
    }
}
