package com.alipay.mobile.security.faceauth;

import com.autonavi.minimap.onekeycheck.module.UploadDataResult;

public enum InvokeType {
    NETWORK("network"),
    SERVER_FAIL("server_fail"),
    INTERRUPT("interrupt"),
    TIMEOUT("timeout"),
    NORMAL("normal"),
    FAIL(UploadDataResult.FAIL_MSG),
    MONITOR("monitor"),
    LIVENESS_FAILED("liveness_failed");
    
    public String value;

    private InvokeType(String str) {
        this.value = str;
    }
}
