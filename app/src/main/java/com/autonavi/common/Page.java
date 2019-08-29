package com.autonavi.common;

public interface Page {
    public static final int INVALID_REQUEST_CODE = -1;

    public enum ON_BACK_TYPE {
        TYPE_NORMAL,
        TYPE_FINISH,
        TYPE_IGNORE
    }

    public enum ResultType {
        NONE,
        OK,
        CANCEL
    }
}
