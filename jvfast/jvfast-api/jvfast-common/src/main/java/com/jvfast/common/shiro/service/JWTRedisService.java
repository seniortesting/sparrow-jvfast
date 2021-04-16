package com.jvfast.common.shiro.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpStatus;
import com.jvfast.common.config.redis.RedisClient;
import com.jvfast.common.shiro.config.ShiroProperties;
import com.jvfast.common.shiro.constant.SecurityMessageConst;
import com.jvfast.common.shiro.entity.JWTToken;
import com.jvfast.common.shiro.model.RedisLoginSysUserVo;
import com.jvfast.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class JWTRedisService {


    String LOGIN_TOKEN_CACHE_KEY = "login:token:{}";
    private final JWTTokenService JWTTokenService;
    private final RedisClient redisCache;
    private final ShiroProperties shiroProperties;

    /**
     * key-value: 有过期时间-->token过期时间
     * 1. tokenMd5:jwtTokenRedisVo
     * 2. username:loginSysUserRedisVo
     * 3. username:salt
     * hash: 没有过期时间，统计在线的用户信息
     * username:num
     */
    public void cacheLoginInfo(JWTToken jwtToken, RedisLoginSysUserVo redisLoginSysUser) {
        if (jwtToken == null) {
            throw new IllegalArgumentException("jwtToken不能为空");
        }
        if (redisLoginSysUser == null) {
            throw new IllegalArgumentException("loginSysUserVo不能为空");
        }

        String token = jwtToken.getToken();
        String tokenMd5 = DigestUtil.md5Hex(token);
        // Redis过期时间与JwtToken过期时间一致
        Duration expireDuration = Duration.ofHours(jwtToken.getExpiredHours());
        String cacheKey = StrUtil.format(LOGIN_TOKEN_CACHE_KEY, tokenMd5);
        redisCache.setValue(cacheKey, redisLoginSysUser, expireDuration);
    }

    /**
     * key-value: 有过期时间-->token过期时间
     * 1. tokenMd5:jwtTokenRedisVo
     * 2. username:loginSysUserRedisVo
     * 3. username:salt
     * hash: 没有过期时间，统计在线的用户信息
     * username:num
     */
    public void cacheLoginInfo(String token, RedisLoginSysUserVo redisLoginSysUser) {
        if (token == null) {
            throw new IllegalArgumentException("jwtToken不能为空");
        }
        if (redisLoginSysUser == null) {
            throw new IllegalArgumentException("loginSysUserVo不能为空");
        }

        String tokenMd5 = DigestUtil.md5Hex(token);
        // Redis过期时间与JwtToken过期时间一致
        Integer expiredHours = shiroProperties.getJwt().getExpiredHours();
        Duration expireDuration = Duration.ofHours(expiredHours);
        String cacheKey = StrUtil.format(LOGIN_TOKEN_CACHE_KEY, tokenMd5);
        redisCache.setValue(cacheKey, redisLoginSysUser, expireDuration);
    }

    /**
     * 刷新
     *
     * @param jwtToken
     * @param httpServletResponse
     */
    public void refreshToken(JWTToken jwtToken, HttpServletResponse httpServletResponse) {
        if (jwtToken == null) {
            return;
        }
        String token = jwtToken.getToken();
        if (StrUtil.isBlank(token)) {
            return;
        }
        // 如果(当前时间+倒计时) > 过期时间，则刷新token
        Date expireDate = jwtToken.getExpireDate();
        Integer refreshCountDownHours = shiroProperties.getJwt().getRefreshCountDownHours();
        boolean refresh = DateUtil.offsetHours(new Date(), refreshCountDownHours).after(expireDate);
        if (!refresh) {
            return;
        }
        // 如果token继续发往后台，则提示，此token已失效，请使用新token，不在返回新token，返回状态码：461
        // 如果Redis缓存中没有，JwtToken没有过期，则说明，已经刷新token
        boolean exists = exists(token);
        if (!exists) {
            log.error("刷新缓存失败,redis不存在该缓存信息,token: {}", token);
            httpServletResponse.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            throw new AuthenticationException(StrUtil.format(SecurityMessageConst.MSG_TOKEN_NOT_IN_REDIS, token));
        }
        String userId = jwtToken.getUserId();
        String newToken = JWTTokenService.createToken(userId);
        // 生成新JwtToken对象
        ShiroProperties.JWT jwtProperties = JWTTokenService.getJWTProperties();
        String secret = jwtProperties.getSecretKey();
        Integer expiredHours = jwtProperties.getExpiredHours();
        String tokenHeader = jwtProperties.getTokenHeader();
        JWTToken newJWTToken = JWTToken.build(newToken, userId, secret, expiredHours);
        // 更新redis缓存
        refreshLoginInfo(token, newJWTToken);
        // 设置响应头
        // 刷新token
        httpServletResponse.setStatus(HttpStatus.HTTP_CREATED);
        httpServletResponse.setHeader(tokenHeader, newToken);
    }

    /**
     * 刷新登陆信息
     *
     * @param oldToken
     * @param newJWTToken
     */
    private void refreshLoginInfo(String oldToken, JWTToken newJWTToken) {
        String tokenMd5 = DigestUtil.md5Hex(oldToken);
        String cacheKey = StrUtil.format(LOGIN_TOKEN_CACHE_KEY, tokenMd5);
        RedisLoginSysUserVo redisLoginSysUser = redisCache.getValue(cacheKey);
        if (null != redisLoginSysUser) {
            // 删除之前的token信息
            deleteLoginInfo(oldToken);
            // 缓存登陆信息
            cacheLoginInfo(newJWTToken, redisLoginSysUser);
        }
    }

    public RedisLoginSysUserVo getLoginSysUserRedisVo(String token) {
        if (StrUtil.isBlank(token)) {
            throw new IllegalArgumentException("token不能为空");
        }
        String tokenMd5 = DigestUtil.md5Hex(token);
        String cacheKey = StrUtil.format(LOGIN_TOKEN_CACHE_KEY, tokenMd5);
        RedisLoginSysUserVo redisLoginSysUser = redisCache.getValue(cacheKey);

        return redisLoginSysUser;
    }

    /**
     * 判断token在redis中是否存在
     *
     * @param token
     * @return
     */
    public boolean exists(String token) {
        if (token == null) {
            throw new IllegalArgumentException("token不能为空");
        }
        String tokenMd5 = DigestUtil.md5Hex(token);
        String cacheKey = StrUtil.format(LOGIN_TOKEN_CACHE_KEY, tokenMd5);
        Object cacheToken = redisCache.getValue(cacheKey);
        return null != cacheToken;
    }

    /**
     * 删除用户所有登陆缓存
     *
     * @param token
     */
    public void deleteLoginInfo(String token) {
        String tokenMd5 = DigestUtil.md5Hex(token);
        String cacheKey = StrUtil.format(LOGIN_TOKEN_CACHE_KEY, tokenMd5);
        redisCache.deleteKey(cacheKey);
    }
}
