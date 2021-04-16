package com.jvfast.common.util;

import cn.hutool.core.date.DateTime;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateUtil {

    /**
     * 计算两个时间间隔秒数
     *
     * @param first
     * @param second
     * @return
     */
    public static long betweenSeconds(LocalDateTime first, LocalDateTime second) {
        long seconds = ChronoUnit.SECONDS.between(first, second);
        return seconds;
    }

    public static LocalDateTime plusDays(LocalDateTime dateTime, long days) {
        return dateTime.plusDays(days);
    }

    public static LocalDateTime plusHours(LocalDateTime dateTime, long hours) {
        return dateTime.plusHours(hours);
    }

    public static LocalDateTime plusSeconds(LocalDateTime dateTime, long seconds) {
        return dateTime.plusSeconds(seconds);
    }

    public static DateTime offsetSeconds(Date date, int offset) {
        return cn.hutool.core.date.DateUtil.offsetSecond(date, offset);
    }

    public static DateTime offsetHours(Date date, int offset) {
        return cn.hutool.core.date.DateUtil.offsetHour(date, offset);
    }
}
