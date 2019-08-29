package com.alipay.mobile.map.exception;

public class GeocodeException extends Exception {
    private static final long serialVersionUID = 1;

    public GeocodeException() {
    }

    public GeocodeException(String str, Throwable th) {
        super(str, th);
    }

    public GeocodeException(String str) {
        super(str);
    }

    public GeocodeException(Throwable th) {
        super(th);
    }
}
