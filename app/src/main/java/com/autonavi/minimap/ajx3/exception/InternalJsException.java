package com.autonavi.minimap.ajx3.exception;

public class InternalJsException extends JsException {
    public InternalJsException(String... strArr) {
        super(2, "internal error", strArr);
    }
}
