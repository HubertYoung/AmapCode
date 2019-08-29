package com.taobao.tao.remotebusiness.login;

public class LoginNotImplementException extends RuntimeException {
    public LoginNotImplementException() {
    }

    public LoginNotImplementException(String str) {
        super(str);
    }

    public LoginNotImplementException(String str, Throwable th) {
        super(str, th);
    }

    public LoginNotImplementException(Throwable th) {
        super(th);
    }
}
