package com.jvfast.common.util;

import org.junit.Test;

public class EncryptUtilTest {

    @Test
    public void encryptPasswd() {
        String passwd = "123456";
        String encryptPasswd = EncryptUtil.encryptPasswd(EncryptUtil.md5(passwd));
        System.out.println(encryptPasswd);
    }
}
