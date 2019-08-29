package com.alipay.mobile.common.transport.ext;

public class ExtTransportException extends Throwable {
    public ExtTransportException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
