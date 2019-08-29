package com.alipay.mobile.framework.exception;

public class StartActivityRecord extends RuntimeException {
    public StartActivityRecord(String msg) {
        super(msg + " (take easy, this is just record the activity's caller stack, will not cause crash)");
    }
}
