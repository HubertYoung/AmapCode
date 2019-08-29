package com.alipay.mobile.common.nbnet.biz.exception;

import com.alipay.mobile.common.nbnet.api.NBNetException;

public final class NBNetVerifyException extends NBNetException {
    public NBNetVerifyException(String detailMessage) {
        super(detailMessage, -4);
    }

    public NBNetVerifyException(String detailMessage, Throwable cause) {
        super(detailMessage, -4, cause);
    }
}
