package com.ali.user.mobile.authlogin.exception;

public class ParamNullException extends AliAuthBaseException {
    private static final long serialVersionUID = -8404557976711945417L;

    public ParamNullException(String str) {
        super(str);
    }

    public ParamNullException(String str, Throwable th) {
        super(str, th);
    }

    public ParamNullException(Throwable th) {
        super(th);
    }
}
