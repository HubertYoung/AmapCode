package com.alipay.mobile.framework.app;

public class AppLoadException extends RuntimeException {
    public AppLoadException() {
    }

    public AppLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppLoadException(String message) {
        super(message);
    }

    public AppLoadException(Throwable cause) {
        super(cause);
    }
}
