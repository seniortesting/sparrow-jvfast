package com.jvfast.common.config.interceptor;


import cn.hutool.core.util.StrUtil;
import com.jvfast.common.annotation.IgnoreSecurity;
import com.jvfast.common.config.JVFastCommonProperties;
import com.jvfast.common.shiro.config.ShiroProperties;
import com.jvfast.common.shiro.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;

/**
 * @author Walter
 * @deprecated {@link com.jvfast.common.shiro.config.ShiroConfig}
 */
@Deprecated
@Slf4j
public class JWTInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JVFastCommonProperties jvFastCommonProperties;
    @Autowired
    private ShiroProperties shiroProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果访问的不是控制器
        JVFastCommonProperties.Encrypt encrypt = jvFastCommonProperties.getEncrypt();
        boolean enable = encrypt.getEnabled();
        if (!(handler instanceof HandlerMethod) || !enable) {
            return true;
        }
        // 是否带有{@see IgnoreSecurity}注解忽略不拦截
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
            if (method.getDeclaringClass().isAnnotationPresent(IgnoreSecurity.class)
                    || method.isAnnotationPresent(IgnoreSecurity.class)) {
                return true;
            }
        }
        // 验证token是否合法
        ShiroProperties.JWT jwt = shiroProperties.getJwt();
        String subject = jwt.getSubject();
        String audience = jwt.getAudience();
        String issuer = jwt.getIssuer();
        String secret = jwt.getSecretKey();
        Integer expiredHours = jwt.getExpiredHours();
        String tokenHeader = jwt.getTokenHeader();
        String refreshHeader = jwt.getTokenHeader();

        String token = JWTUtil.getTokenHeader(request, tokenHeader);
        if (StrUtil.isEmpty(token)) {
            throw new AccessDeniedException("认证信息不能为空");
        }
        try {
            JWTUtil.parseToken(token, secret);
        } catch (ExpiredJwtException e) {
            log.error("请求token: {} 已经过期,重新产生新的token", token);
            String newToken = JWTUtil.createToken(issuer, subject, audience, secret, expiredHours, null);
            response.setHeader(refreshHeader, newToken);
        } catch (MalformedJwtException e) {
            throw new AccessDeniedException("认证信息非法");
        } catch (SignatureException e) {
            throw new AccessDeniedException("认证信息非法");
        } catch (Exception e) {
            throw new AccessDeniedException("认证信息异常");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
