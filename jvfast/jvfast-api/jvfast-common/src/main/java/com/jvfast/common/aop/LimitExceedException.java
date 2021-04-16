package com.jvfast.common.aop;

import com.jvfast.common.exception.BusinessException;

/**
 * @author Walter
 */
public class LimitExceedException extends BusinessException {

    public LimitExceedException(String message) {
        super(message);
    }

    public LimitExceedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LimitExceedException(Throwable cause) {
        super(cause);
    }

    public LimitExceedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
