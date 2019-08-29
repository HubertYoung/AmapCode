package com.alipay.mobile.common.cache.disk;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;

public class CacheException extends Exception {
    private ErrorCode a;
    private String b;

    public enum ErrorCode {
        WRITE_IO_ERROR(0),
        READ_IO_ERROR(1);
        
        private int a;

        private ErrorCode(int value) {
            this.a = value;
            if (Boolean.FALSE.booleanValue()) {
                Log.v("hackbyte ", ClassVerifier.class.toString());
            }
        }

        public final int getValue() {
            return this.a;
        }
    }

    public CacheException(String msg) {
        super(msg);
        this.a = ErrorCode.WRITE_IO_ERROR;
        this.b = msg;
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public CacheException(ErrorCode code, String msg) {
        super(a(code, msg));
        this.a = code;
        this.b = msg;
    }

    public ErrorCode getCode() {
        return this.a;
    }

    public String getMsg() {
        return this.b;
    }

    private static String a(ErrorCode code, String message) {
        StringBuilder str = new StringBuilder();
        str.append("Cache error");
        if (code != null) {
            str.append("[").append(code.getValue()).append("]");
        }
        str.append(" : ");
        if (message != null) {
            str.append(message);
        }
        return str.toString();
    }
}
