package com.alipay.mobile.security.bio.exception;

public class InvalidCallException extends RuntimeException {
    public InvalidCallException(String str) {
        super("InvalidCallException:" + str);
    }
}
