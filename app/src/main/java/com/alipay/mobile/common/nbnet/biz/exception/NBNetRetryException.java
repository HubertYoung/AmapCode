package com.alipay.mobile.common.nbnet.biz.exception;

import com.alipay.mobile.common.nbnet.api.NBNetException;

public final class NBNetRetryException extends NBNetException {
    public NBNetRetryException(String errorMessage, Throwable cause) {
        super(cause.getClass().getSimpleName() + ":" + errorMessage, cause);
    }

    public NBNetRetryException(int errorCode, String errorMessage) {
        super(errorMessage, errorCode);
    }

    public NBNetRetryException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, errorCode, cause);
    }
}
