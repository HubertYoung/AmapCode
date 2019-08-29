package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception;

public class RecordIllegalStateException extends RuntimeException {
    public String getMessage() {
        return "record sequence error";
    }
}
