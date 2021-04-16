package com.jvfast.common.shiro.filter;

import cn.hutool.core.util.StrUtil;
import com.jvfast.common.api.Result;
import com.jvfast.common.api.ResultCode;
import com.jvfast.common.shiro.config.ShiroProperties;
import com.jvfast.common.shiro.constant.SecurityMessageConst;
import com.jvfast.common.shiro.entity.JWTToken;
import com.jvfast.common.shiro.service.JWTRedisService;
import com.jvfast.common.shiro.service.JWTTokenService;
import com.jvfast.common.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

/**
 * 参考对应的 see BasicHttpAuthenticationFilter
 */
@Slf4j
public class JWTFilter extends AuthenticatingFilter {

    private JWTRedisService loginRedisService;
    private JWTTokenService JWTTokenService;

    public JWTFilter(JWTTokenService JWTTokenService, JWTRedisService loginRedisService) {
        this.JWTTokenService = JWTTokenService;
        this.loginRedisService = loginRedisService;
    }

    /**
     * 返回true则继续，返回false则会调用onAccessDenied()。这里在不通过时，还调用了isPermissive()方法
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (IllegalStateException e) {
            // 如果调用createToken出错
            log.error("调用createToken出错,无法继续操作", e);
        } catch (Exception e) {
            log.error("isAccessAllowed出现以上异常,无法继续操作", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // 构建对应的AuthenticationToken
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        String requestURI = httpServletRequest.getRequestURI();
        String token = JWTTokenService.getToken(httpServletRequest);
        if (StrUtil.isBlank(token)) {
            throw new AuthenticationException(StrUtil.format(SecurityMessageConst.MSG_TOKEN_EMPTY, requestURI));
        }
        if (JWTTokenService.expired(token)) {
            // 可能是过期或者是非法篡改的token
            log.error("验证token内容已过期, token: {}", token);
            throw new AuthenticationException(StrUtil.format(SecurityMessageConst.MSG_TOKEN_EXPIRED, requestURI));
        }
        // 检查redis中是否存在该token, 如果设置为单个用户token登陆，则先在redis中判断token是否存在
        boolean redisExpired = loginRedisService.exists(token);
        if (!redisExpired) {
            log.error("验证redis的token已经过期, token: {}", token);
            throw new AuthenticationException(StrUtil.format(SecurityMessageConst.MSG_TOKEN_NOT_IN_REDIS, requestURI));
        }

        String userId = JWTTokenService.getUserId(token);
        ShiroProperties.JWT jwtProperties = JWTTokenService.getJWTProperties();
        String secret = jwtProperties.getSecretKey();
        Integer expiredHours = jwtProperties.getExpiredHours();
        JWTToken jwtToken = JWTToken.build(token, userId, secret, expiredHours);
        return jwtToken;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //每次登陆成功重新刷新token的缓存时间
        JWTToken jwtToken = (JWTToken) token;
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        loginRedisService.refreshToken(jwtToken, httpServletResponse);
        return true;
    }

    /**
     * 如果调用shiro的login认证失败，会回调这个方法，这里我们什么都不做，因为逻辑放到了onAccessDenied（）中。
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.error("Validate token fail, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }

    /**
     * isAccessAllowed（）方法中返回false,则会进入这个方法
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(servletResponse);
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        // 返回401
        String requestURI = httpServletRequest.getRequestURI();
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        Result responseResult = Result.fail(ResultCode.UN_AUTHORIZED, SecurityMessageConst.UNAUTHORIZED, null, requestURI);
        ServletUtil.printJSONStr(httpServletResponse, responseResult);
        // 过滤链终止
        return false;
    }


    /**
     * description 验证当前用户是否属于mappedValue任意一个角色
     *
     * @param subject     1
     * @param mappedValue 2
     * @return boolean
     */
    private boolean checkRoles(Subject subject, Object mappedValue) {
        String[] rolesArray = (String[]) mappedValue;
        return rolesArray == null || rolesArray.length == 0 || Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }
}
