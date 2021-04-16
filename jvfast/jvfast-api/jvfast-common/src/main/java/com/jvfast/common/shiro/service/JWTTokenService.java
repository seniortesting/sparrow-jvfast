package com.jvfast.common.shiro.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.jvfast.common.shiro.config.ShiroProperties;
import com.jvfast.common.shiro.util.JWTUtil;
import com.jvfast.common.util.ServletUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JWTTokenService {

    private final ShiroProperties shiroProperties;
    private static final String JWT_USERID_KEY = "uid";


    /**
     * jwt里面只存放对应的用户id信息,通过redis cache存放对应的用户id对应的权限等信息
     *
     * @return
     */
    public String createToken(String userId) {
        Assert.notNull(userId, "userId Should not empty");
        ShiroProperties.JWT jwt = shiroProperties.getJwt();
        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put(JWT_USERID_KEY, userId);

        String issuer = jwt.getIssuer();
        String subject = jwt.getSubject();
        String audience = jwt.getAudience();
        String secret = jwt.getSecretKey();
        Integer expiredHours = jwt.getExpiredHours();

        String jwtStr = JWTUtil.createToken(issuer, subject, audience, secret, expiredHours, payloadClaims);
        return jwtStr;
    }


    public Claims parseToken(String token) {
        String secret = getJWTProperties().getSecretKey();
        Claims claims = JWTUtil.parseToken(token, secret);
        return claims;
    }

    public boolean expired(String token) {
        String secret = getJWTProperties().getSecretKey();
        boolean expired = JWTUtil.isExpired(token, secret);
        return expired;
    }


    public ShiroProperties.JWT getJWTProperties() {
        ShiroProperties.JWT jwt = shiroProperties.getJwt();
        return jwt;
    }

    public String getToken() {
        String jwtToken = getToken(ServletUtil.getRequest());
        return jwtToken;
    }

    public String getToken(HttpServletRequest httpRequest) {
        ShiroProperties.JWT jwtProperties = getJWTProperties();
        String tokenHeader = jwtProperties.getTokenHeader();
        String jwtToken = JWTUtil.getTokenHeader(httpRequest, tokenHeader);
        return jwtToken;
    }

    public String getUserId() {
        String token = getToken(ServletUtil.getRequest());
        String userId = getUserId(token);
        return userId;
    }

    public String getUserId(HttpServletRequest httpRequest) {
        String token = getToken(httpRequest);
        String userId = getUserId(token);
        return userId;
    }

    public String getUserId(String token) {
        if (StrUtil.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 解析对应的用户信息
            if (claims != null) {
                String userId = (String) claims.get(JWT_USERID_KEY);
                return userId;
            }
        }
        return null;
    }
}
