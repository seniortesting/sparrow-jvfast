package com.jvfast.common.shiro.matcher;

import com.jvfast.common.shiro.service.JWTTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

@RequiredArgsConstructor
@Slf4j
public class JWTCredentialsMatcher implements CredentialsMatcher {

    private final JWTTokenService JWTTokenService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        // 从 @see JWTRealm返回的simpleAuthorizationInfo进行对应token是否正确
        // 如果存在userId信息就标识token是合法的
        try {
            String token = info.getCredentials().toString();
            return null != JWTTokenService.getUserId(token);
        } catch (Exception e) {
            log.error("JWT Token CredentialsMatch Exception:" + e.getMessage(), e);
        }
        return false;
    }
}
