package com.epam.esm.exception;

public class EntityRetrievalException extends RuntimeException {
    public EntityRetrievalException() {
        super();
    }

    public EntityRetrievalException(String message) {
        super(message);
    }

    public EntityRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityRetrievalException(Throwable cause) {
        super(cause);
    }

    protected EntityRetrievalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
