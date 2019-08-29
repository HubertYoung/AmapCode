package com.autonavi.minimap.bundle.qrscan.data;

public interface IScanResult {
    public static final int ERROR_CODE_OK = 1000;
    public static final int ERROR_TYPE_HTTP = 101;
    public static final int ERROR_TYPE_NO_ERROR = 100;
    public static final int ERROR_TYPE_PLATFORM = 102;

    int getErrorCode();

    int getErrorType();

    String getText();
}
