package com.alipay.mobile.security.bio.service.local;

public class NotImplementedException extends RuntimeException {
    public NotImplementedException() {
    }

    public NotImplementedException(String str) {
        super(str);
    }

    public NotImplementedException(String str, Throwable th) {
        super(str, th);
    }

    public NotImplementedException(Throwable th) {
        super(th);
    }
}
