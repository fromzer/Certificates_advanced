package com.epam.esm.exception;

public class NoPaginationSpecifiedException extends RuntimeException {
    public NoPaginationSpecifiedException() {
        super();
    }

    public NoPaginationSpecifiedException(String message) {
        super(message);
    }

    public NoPaginationSpecifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPaginationSpecifiedException(Throwable cause) {
        super(cause);
    }

    protected NoPaginationSpecifiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
