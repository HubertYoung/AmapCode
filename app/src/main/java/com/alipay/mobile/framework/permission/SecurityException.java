package com.alipay.mobile.framework.permission;

public class SecurityException extends Exception {
    public SecurityException(SecurityException se) {
        super(se);
    }
}
