package com.alipay.mobile.common.nbnet.api;

import java.io.IOException;

public class NBNetException extends IOException {
    protected int errorCode = -1;

    public NBNetException(String detailMessage) {
        super(detailMessage);
    }

    public NBNetException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public NBNetException(String errorMessage, int errorCode2) {
        super(errorMessage);
        this.errorCode = errorCode2;
    }

    public NBNetException(String errorMessage, int errorCode2, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode2;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode2) {
        this.errorCode = errorCode2;
    }

    public String toString() {
        String msg = getLocalizedMessage();
        String name = getClass().getName();
        return msg == null ? name : name + ", msg: " + msg + ", code: " + this.errorCode;
    }
}
