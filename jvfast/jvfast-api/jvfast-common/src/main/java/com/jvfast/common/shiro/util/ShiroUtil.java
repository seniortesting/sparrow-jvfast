package com.jvfast.common.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class ShiroUtil {

    /**
     * 获取shiro对应的subject对象
     *
     * @return
     */
    public static Subject getSubject() {
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    public static SecurityManager getSecurityManager() {
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        return securityManager;
    }
}
