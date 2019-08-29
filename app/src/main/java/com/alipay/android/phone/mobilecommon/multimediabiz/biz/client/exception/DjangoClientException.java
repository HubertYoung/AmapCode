package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception;

public class DjangoClientException extends Exception {
    private static final long serialVersionUID = 3509658503585778347L;

    public DjangoClientException() {
    }

    public DjangoClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public DjangoClientException(String message) {
        super(message);
    }

    public DjangoClientException(Throwable cause) {
        super(cause);
    }
}
