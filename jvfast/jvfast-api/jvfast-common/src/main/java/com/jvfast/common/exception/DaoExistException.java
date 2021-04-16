package com.jvfast.common.exception;

/**
 * @author Walter
 */
public class DaoExistException extends DaoException {

    public DaoExistException(String message) {
        super(message);
    }

    public DaoExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoExistException(Throwable cause) {
        super(cause);
    }

    public DaoExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
