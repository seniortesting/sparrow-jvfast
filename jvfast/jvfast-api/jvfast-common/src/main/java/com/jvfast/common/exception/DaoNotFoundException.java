package com.jvfast.common.exception;

public class DaoNotFoundException extends DaoException {

    public DaoNotFoundException(String message) {
        super(message);
    }

    public DaoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoNotFoundException(Throwable cause) {
        super(cause);
    }

    public DaoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
