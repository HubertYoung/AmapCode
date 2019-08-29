package com.ali.user.mobile.authlogin.exception;

public class AliAuthBaseException extends Exception {
    private static final long serialVersionUID = 3573597909040537457L;

    public AliAuthBaseException(String str) {
        super(str);
    }

    public AliAuthBaseException(String str, Throwable th) {
        super(str, th);
    }

    public AliAuthBaseException(Throwable th) {
        super(th);
    }
}
