package com.ali.user.mobile.authlogin.exception;

public class PreAuthLoginException extends AliAuthBaseException {
    private static final long serialVersionUID = -4201851410425432384L;

    public PreAuthLoginException(String str) {
        super(str);
    }

    public PreAuthLoginException(String str, Throwable th) {
        super(str, th);
    }

    public PreAuthLoginException(Throwable th) {
        super(th);
    }
}
