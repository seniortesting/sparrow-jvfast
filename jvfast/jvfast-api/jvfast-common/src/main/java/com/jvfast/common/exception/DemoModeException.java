package com.jvfast.common.exception;

/**
 * @description:
 * @project: jvfast
 * @author: Walter Hu
 * @create: 2019-12-09 20:54
 **/
public class DemoModeException extends RuntimeException {


    public DemoModeException() {
    }

    public DemoModeException(String message) {
        super(message);
    }

    public DemoModeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DemoModeException(Throwable cause) {
        super(cause);
    }

    public DemoModeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
