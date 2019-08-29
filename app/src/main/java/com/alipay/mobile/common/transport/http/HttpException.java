package com.alipay.mobile.common.transport.http;

import java.io.IOException;

public class HttpException extends IOException {
    public static final int DOWNLOAD_CACHE_DIR_CREATE_ERR = 17;
    public static final int DOWNLOAD_CACHE_FILE_READ_ERR = 20;
    public static final int DOWNLOAD_CACHE_SPACE_NOT_ENOUGH_ERR = 14;
    public static final int DOWNLOAD_COPY_FAILURE_ERR = 16;
    public static final int DOWNLOAD_FILE_CHECK_ERR = 22;
    public static final int DOWNLOAD_SD_WRITE_ERR = 21;
    public static final int DOWNLOAD_TARGET_DIR_CREATE_ERR = 18;
    public static final int DOWNLOAD_TARGET_SPACE_NOT_ENOUGH_ERR = 15;
    public static final int DOWNLOAD_TOO_MANY_REQUESTS = 429;
    public static final int DOWNLOAD_URL_BLACK_SET_ERR = 19;
    public static final int LOGIN_REFRESH_ERR = 50;
    public static final int NETWORK_AUTH_ERROR = 8;
    public static final int NETWORK_CANCEL_ERROR = 13;
    public static final int NETWORK_CONNECTION_EXCEPTION = 3;
    public static final int NETWORK_DNS_ERROR = 9;
    public static final int NETWORK_IO_EXCEPTION = 6;
    public static final int NETWORK_SCHEDULE_ERROR = 7;
    public static final int NETWORK_SERVER_EXCEPTION = 5;
    public static final int NETWORK_SOCKET_EXCEPTION = 4;
    public static final int NETWORK_SSL_EXCEPTION = 2;
    public static final int NETWORK_TRAFIC_BEYOND_LIMIT = 11;
    public static final int NETWORK_UNAVAILABLE = 1;
    public static final int NETWORK_UNKNOWN_ERROR = 0;
    public static final int REQ_INTERCEPTOR_ERR = 51;
    public static final int URL_ERROR = 10;
    protected int mCode;
    protected String mMsg;

    public HttpException(String msg) {
        super(msg);
        this.mCode = 0;
        this.mMsg = msg;
    }

    public HttpException(String msg, Throwable throwable) {
        super(msg, throwable);
        this.mCode = 0;
        this.mMsg = msg;
    }

    public HttpException(Integer code, String msg) {
        super(format(code, msg));
        this.mCode = code.intValue();
        this.mMsg = msg;
    }

    public HttpException(Integer code, String msg, Throwable throwable) {
        super(format(code, msg), throwable);
        this.mCode = code.intValue();
        this.mMsg = msg;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getMsg() {
        return this.mMsg;
    }

    public void setMsg(String msg) {
        this.mMsg = msg;
    }

    protected static String format(Integer code, String message) {
        StringBuilder str = new StringBuilder();
        str.append("Http Transport error");
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
