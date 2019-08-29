package com.alipay.mobile.common.nbnet.biz.exception;

import com.alipay.mobile.common.nbnet.api.NBNetException;

public class NBNetServerException extends NBNetException {
    public NBNetServerException(int errorCode, String detailMessage) {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public NBNetServerException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public boolean isRecoverable() {
        return (1000 == this.errorCode || 1007 == this.errorCode || 1006 == this.errorCode) ? false : true;
    }
}
