package com.alipay.deviceid.module.rpc.mrpc.core;

public class HttpException extends Exception {
    public static final int NETWORK_AUTH_ERROR = 8;
    public static final int NETWORK_CONNECTION_EXCEPTION = 3;
    public static final int NETWORK_DNS_ERROR = 9;
    public static final int NETWORK_IO_EXCEPTION = 6;
    public static final int NETWORK_SCHEDULE_ERROR = 7;
    public static final int NETWORK_SERVER_EXCEPTION = 5;
    public static final int NETWORK_SOCKET_EXCEPTION = 4;
    public static final int NETWORK_SSL_EXCEPTION = 2;
    public static final int NETWORK_UNAVAILABLE = 1;
    public static final int NETWORK_UNKNOWN_ERROR = 0;
    private static final long serialVersionUID = -6320569206365033676L;
    private int mCode;
    private String mMsg;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public HttpException(Integer num, String str) {
        // StringBuilder sb = new StringBuilder();
        // sb.append("Http Transport error");
        // if (num != null) {
            // sb.append("[");
            // sb.append(num);
            // sb.append("]");
        // }
        // sb.append(" : ");
        // if (str != null) {
            // sb.append(str);
        // }
        super(sb.toString());
        this.mCode = num.intValue();
        this.mMsg = str;
    }

    public HttpException(String str) {
        super(str);
        this.mCode = 0;
        this.mMsg = str;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getMsg() {
        return this.mMsg;
    }
}
