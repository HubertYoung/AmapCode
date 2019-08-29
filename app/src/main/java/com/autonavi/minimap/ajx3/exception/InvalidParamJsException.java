package com.autonavi.minimap.ajx3.exception;

public class InvalidParamJsException extends JsException {
    public InvalidParamJsException(String... strArr) {
        super(1, "invalid param", strArr);
    }
}
