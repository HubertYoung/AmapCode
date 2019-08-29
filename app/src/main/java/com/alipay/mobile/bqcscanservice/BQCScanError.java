package com.alipay.mobile.bqcscanservice;

public class BQCScanError {
    public String msg;
    public ErrorType type;

    public enum ErrorType {
        NoError,
        initEngineError,
        CameraOpenError,
        CameraPreviewError,
        ScanTypeNotSupport
    }

    public BQCScanError(ErrorType type2, String msg2) {
        this.type = type2;
        this.msg = msg2;
    }
}
