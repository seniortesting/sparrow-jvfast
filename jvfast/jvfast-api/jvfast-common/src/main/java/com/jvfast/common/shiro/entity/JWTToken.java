package com.jvfast.common.shiro.entity;

import com.jvfast.common.shiro.util.JWTUtil;
import io.jsonwebtoken.Claims;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.shiro.authc.AuthenticationToken;

import java.util.Date;

@Data
@Accessors(chain = true)
public class JWTToken implements AuthenticationToken {

    /**
     * 登陆token
     */
    private String token;

    private String userId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 多长时间过期，默认一小时
     */
    private long expiredHours;
    /**
     * 过期日期
     */
    private Date expireDate;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public static JWTToken build(String token, String userId, String secret, long expiredHours) {
        Claims claims = JWTUtil.parseToken(token, secret);
        if (null != claims) {
            Date issuedAt = claims.getIssuedAt();
            Date expiration = claims.getExpiration();
            return new JWTToken()
                    .setCreateDate(issuedAt)
                    .setExpireDate(expiration)
                    .setExpiredHours(expiredHours)
                    .setToken(token)
                    .setUserId(userId);
        }
        return null;
    }
}
