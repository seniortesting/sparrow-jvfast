package com.jvfast.common.shiro.realm;

import com.jvfast.common.shiro.constant.SecurityMessageConst;
import com.jvfast.common.shiro.entity.JWTToken;
import com.jvfast.common.shiro.model.RedisLoginSysUserVo;
import com.jvfast.common.shiro.service.JWTRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

@Slf4j
@RequiredArgsConstructor
public class JWTRealm extends AuthorizingRealm {

    private final JWTRedisService loginRedisService;

    /**
     * 此Realm只对JWTToken进行处理
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return null != token && token instanceof JWTToken;
    }

    /**
     * 用户调用Subject.login后对应的请求转发到这里的shiro处理
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo();
        if (!(authenticationToken instanceof JWTToken)) {
            return null;
        }
        if (null == authenticationToken.getPrincipal() || null == authenticationToken.getCredentials()) {
            throw new AuthenticationException(SecurityMessageConst.MSG_ACCOUNT_INFO_EMPTY);
        }
        // 将subject.login对应的值转换过来
        JWTToken jwtToken = (JWTToken) authenticationToken;
        String token = jwtToken.getToken();

        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(jwtToken, getName());
        simpleAuthenticationInfo.setPrincipals(principalCollection);
        simpleAuthenticationInfo.setCredentials(token);
        return simpleAuthenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 角色和权限信息
        log.debug("doGetAuthorizationInfo principalCollection...");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 设置角色/权限信息
        JWTToken jwtToken = (JWTToken) principals.getPrimaryPrincipal();
        String token = jwtToken.getToken();
        RedisLoginSysUserVo loginSysUserRedisVo = loginRedisService.getLoginSysUserRedisVo(token);
        // 赋予角色, 用户角色表
        simpleAuthorizationInfo.setRoles(loginSysUserRedisVo.getRoles());
        // 赋予权限, 菜单资源表,角色表,用户表
        simpleAuthorizationInfo.setStringPermissions(loginSysUserRedisVo.getPermissions());
        return simpleAuthorizationInfo;
    }
}
