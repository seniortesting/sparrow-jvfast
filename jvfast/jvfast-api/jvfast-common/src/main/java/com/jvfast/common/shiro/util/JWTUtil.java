package com.jvfast.common.shiro.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.jvfast.common.util.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwsHeader;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    /**
     * 生成jwt token
     *
     * @param payloadClaims body数据
     * @return jwt token
     */
    public static String createToken(String issuer, String subject, String audience, String secret, Integer expiredHours, Map<String, Object> payloadClaims) {
        try {
            // 默认过期时间为1小时
            Date expiredAt = DateUtil.offsetHours(new Date(), expiredHours);
            if (payloadClaims == null || payloadClaims.isEmpty()) {
                payloadClaims = new HashMap<>();
            }
            Map<String, Object> headerClaims = new HashMap<>();
            Header header = new DefaultJwsHeader();
            header.setContentType(Header.CONTENT_TYPE);
            header.setType(Header.JWT_TYPE);
            headerClaims.putAll(header);

            // 构建token
            String token = Jwts.builder()
                    .setIssuer(issuer)
                    .setSubject(subject)
                    .setAudience(audience)

                    .setHeader(headerClaims)
                    .setClaims(payloadClaims)
                    .setId(IdUtil.fastSimpleUUID())

                    .setIssuedAt(new Date())
                    .setExpiration(expiredAt)
                    // 签名
                    .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                    .compact();
            return token;
        } catch (Exception e) {
            System.out.println("产生token异常");
        }
        return null;
    }

    /**
     * 验证jwt token
     * eyJjdHkiOiJjdHkiLCJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMTk2NzY1OTc5ODIyMDMwODUwIiwiZXhwIjoxNTc1MDM0MjQ2LCJpYXQiOjE1NzQxNzAyNDYsImp0aSI6IjMxZTExMDNhNjZkZDQxODM5ZTczOGI1ZGQzNTMzNGM0In0.y9rLxL-mFPH1kQLAT20a0UowLdzVmuuE4W7k7nirLhs
     * MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=
     *
     * @param token jwt token
     * @return Jws对象 payloadClaims
     */
    public static Claims parseToken(String token, String secret) {
        try {
            Claims jws = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
            return jws;
        } catch (Exception e) {
            e.printStackTrace();
            // throws UnsupportedJwtException, ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentExceptio
        }
        return null;
    }

    /**
     * 是否过期,防止被盗
     *
     * @param secret
     * @param token
     * @return
     */
    public static boolean isExpired(String token, String secret) {
        Claims verify = parseToken(token, secret);
        if (verify == null) {
            return true;
        }
        Date expiration = verify.getExpiration();
        return expiration.before(new Date());
    }

    /**
     * 获取token中的值,可以是header或者直接url参数:
     *
     * @return
     */
    public static String getTokenHeader(HttpServletRequest request, String tokenHeader) {
        if (request == null) {
            //  throw new IllegalArgumentException("获取tokenheader时HttpServletRequest为空");
            return null;
        }
        String token = request.getHeader(tokenHeader) != null ? request.getHeader(tokenHeader) : request.getHeader(tokenHeader.toLowerCase());
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter(tokenHeader);
        }
        return token;
    }
}
