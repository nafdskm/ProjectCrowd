package com.skm.crowd.exception;

public class AdminAcctIsInUsedException extends RuntimeException {

    public AdminAcctIsInUsedException() {
    }

    public AdminAcctIsInUsedException(String message) {
        super(message);
    }

    public AdminAcctIsInUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminAcctIsInUsedException(Throwable cause) {
        super(cause);
    }

    public AdminAcctIsInUsedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
