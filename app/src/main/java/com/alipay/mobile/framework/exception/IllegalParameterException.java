package com.alipay.mobile.framework.exception;

public class IllegalParameterException extends MobileException {
    private int a;
    private String b;

    public IllegalParameterException(String msg) {
        super(msg);
        this.a = 0;
        this.b = msg;
    }

    public IllegalParameterException(Integer code, String msg) {
        super(format(code, msg));
        this.a = code.intValue();
        this.b = msg;
    }

    public String toString() {
        return "IllegalParameterException [mCode=" + this.a + ", mMsg=" + this.b + "]";
    }
}
