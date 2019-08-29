package com.amap.bundle.aosservice.response;

import com.autonavi.core.network.inter.response.ResponseException;

public class AosResponseException extends ResponseException {
    public AosResponseException(String str) {
        super(str);
    }

    public AosResponseException(ResponseException responseException) {
        super(responseException.getMessage());
        this.errorCode = responseException.errorCode;
        this.exception = responseException.exception;
        this.isCallbackError = responseException.isCallbackError;
        this.response = responseException.response;
    }
}
