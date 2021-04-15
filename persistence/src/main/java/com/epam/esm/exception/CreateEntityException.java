package com.epam.esm.exception;

public class CreateEntityException extends RuntimeException {
    public CreateEntityException() {
        super();
    }

    public CreateEntityException(String message) {
        super(message);
    }

    public CreateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateEntityException(Throwable cause) {
        super(cause);
    }

    protected CreateEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
