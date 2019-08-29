package com.alipay.mobile.common.nbnet.biz.exception;

import com.alipay.mobile.common.nbnet.api.NBNetException;

public final class NBNetFuseException extends NBNetException {
    public NBNetFuseException(String detailMessage) {
        super(detailMessage, -7);
    }

    public NBNetFuseException(String detailMessage, Throwable cause) {
        super(detailMessage, -7, cause);
    }
}
