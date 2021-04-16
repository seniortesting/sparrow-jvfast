package com.jvfast.common.constant;

/**
 * @author Walter
 */
public class HttpConst {

    /**
     * 重复间隔时间，单位:秒 默认10秒
     * <p>
     * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     */
    public static final int REPEAT_SUBMIT_INTERVAL_TIME = 10;

}
