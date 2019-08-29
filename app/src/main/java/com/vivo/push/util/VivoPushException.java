package com.vivo.push.util;

public class VivoPushException extends Exception {
    public static final int REASON_CODE_ACCESS = 10000;
    private int mReasonCode;

    public VivoPushException(String str) {
        this(10000, str);
    }

    public VivoPushException(int i, String str) {
        super(str);
        this.mReasonCode = i;
    }

    public int getCode() {
        return this.mReasonCode;
    }
}
