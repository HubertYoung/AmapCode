package com.alipay.mobile.common.nbnet.biz.exception;

import com.alipay.mobile.common.nbnet.api.NBNetException;

public final class NBNetProtocolException extends NBNetException {
    public NBNetProtocolException(String detailMessage) {
        super(detailMessage, -3);
    }

    public NBNetProtocolException(String detailMessage, Throwable cause) {
        super(detailMessage, -3, cause);
    }
}
