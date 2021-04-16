package com.jvfast.common.util;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;

import java.util.regex.Pattern;

public class RegUtil {

    public static final Pattern CHINA_PHONE = PatternPool.MOBILE;
    public static final Pattern CHINA_ID_CARD = PatternPool.CITIZEN_ID;
    public static final Pattern EMAIL = PatternPool.EMAIL;


    public static boolean match(Pattern pattern, String value) {
        boolean match = ReUtil.isMatch(pattern, value);
        return match;
    }
}
