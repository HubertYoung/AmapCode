package com.autonavi.minimap.onekeycheck.exception;

public class OneKeyCheckException extends Exception {
    private int mErrorCode;

    public OneKeyCheckException(String str, int i) {
        super(str);
        this.mErrorCode = i;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String getException() {
        String message = getMessage();
        if (message == null) {
            message = "";
        }
        StringBuffer stringBuffer = new StringBuffer("error_code:");
        stringBuffer.append(this.mErrorCode);
        stringBuffer.append(",error_msg:");
        stringBuffer.append(message);
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}
