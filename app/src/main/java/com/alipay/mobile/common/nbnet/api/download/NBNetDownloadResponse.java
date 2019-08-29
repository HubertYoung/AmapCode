package com.alipay.mobile.common.nbnet.api.download;

public class NBNetDownloadResponse {
    private String a;
    private int b = -1;
    private String c;
    private long d;
    private Throwable e;

    public boolean isSuccess() {
        return this.b == 0;
    }

    public String getTraceId() {
        return this.a;
    }

    public void setTraceId(String traceId) {
        this.a = traceId;
    }

    public int getErrorCode() {
        return this.b;
    }

    public void setErrorCode(int errorCode) {
        this.b = errorCode;
    }

    public String getErrorMsg() {
        return this.c;
    }

    public void setErrorMsg(String errorMsg) {
        this.c = errorMsg;
    }

    public long getDataLength() {
        return this.d;
    }

    public void setDataLength(long dataLength) {
        this.d = dataLength;
    }

    public Throwable getException() {
        return this.e;
    }

    public void setException(Throwable exception) {
        this.e = exception;
    }

    public String toString() {
        return "traceId='" + this.a + ", errorCode=" + this.b + ", errorMsg='" + this.c + ", dataLength=" + this.d;
    }
}
