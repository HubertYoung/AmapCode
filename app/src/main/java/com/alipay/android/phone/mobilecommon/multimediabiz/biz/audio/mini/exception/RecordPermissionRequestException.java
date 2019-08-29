package com.alipay.android.phone.mobilecommon.multimediabiz.biz.audio.mini.exception;

public class RecordPermissionRequestException extends RuntimeException {
    public String getMessage() {
        return "record permission interrupted exception";
    }
}
