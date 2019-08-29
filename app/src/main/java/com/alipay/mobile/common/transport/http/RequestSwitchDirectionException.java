package com.alipay.mobile.common.transport.http;

class RequestSwitchDirectionException extends RuntimeException {
    public RequestSwitchDirectionException() {
    }

    public RequestSwitchDirectionException(String detailMessage) {
        super(detailMessage);
    }

    public RequestSwitchDirectionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RequestSwitchDirectionException(Throwable throwable) {
        super(throwable);
    }
}
