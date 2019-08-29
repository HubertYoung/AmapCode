package com.alipay.mobile.common.share;

public class ShareException extends RuntimeException {
    public static final int APP_UNINSTALL = 40501;
    public static final int AUTH_ERROR = 1002;
    public static final int UNKNOWN_ERROR = 1003;
    public static final int USER_CANCEL = 1001;
    private static final long serialVersionUID = 475022994858770424L;
    private int statusCode = -1;

    public ShareException(String msg) {
        super(msg);
    }

    public ShareException(Exception cause) {
        super(cause);
    }

    public ShareException(String msg, int statusCode2) {
        super(msg);
        this.statusCode = statusCode2;
    }

    public ShareException(String msg, Exception cause) {
        super(msg, cause);
    }

    public ShareException(String msg, Exception cause, int statusCode2) {
        super(msg, cause);
        this.statusCode = statusCode2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public ShareException() {
    }

    public ShareException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ShareException(Throwable throwable) {
        super(throwable);
    }

    public ShareException(int statusCode2) {
        this.statusCode = statusCode2;
    }

    public void setStatusCode(int statusCode2) {
        this.statusCode = statusCode2;
    }
}
