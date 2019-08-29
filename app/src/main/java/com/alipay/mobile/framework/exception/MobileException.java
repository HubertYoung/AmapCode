package com.alipay.mobile.framework.exception;

public abstract class MobileException extends RuntimeException {
    private int a;
    private String b;

    public MobileException(String msg) {
        super(msg);
        this.a = 0;
        this.b = msg;
    }

    public MobileException(Integer code, String msg) {
        super(format(code, msg));
        this.a = code.intValue();
        this.b = msg;
    }

    public MobileException(Integer code, Throwable cause) {
        super(cause);
        this.a = code.intValue();
    }

    public int getCode() {
        return this.a;
    }

    public String getMsg() {
        return this.b;
    }

    protected static String format(Integer code, String message) {
        StringBuilder str = new StringBuilder();
        str.append("MobileException: ");
        if (code != null) {
            str.append("[").append(code).append("]");
        }
        str.append(" : ");
        if (message != null) {
            str.append(message);
        }
        return str.toString();
    }
}
