package com.alipay.mobile.common.nbnet.biz.exception;

public class NBNetAssertionException extends RuntimeException {
    public NBNetAssertionException() {
    }

    public NBNetAssertionException(String detailMessage) {
        super(detailMessage);
    }

    public NBNetAssertionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NBNetAssertionException(Throwable throwable) {
        super(throwable);
    }
}
