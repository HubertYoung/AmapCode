package com.alipay.inside.android.phone.mrpc.core;

public class HttpUrlResponse extends Response {
    private String mCharset;
    private int mCode;
    private long mCreateTime;
    private HttpUrlHeader mHeader;
    private String mMsg;
    private long mPeriod;

    public HttpUrlResponse(HttpUrlHeader httpUrlHeader, int i, String str, byte[] bArr) {
        this.mHeader = httpUrlHeader;
        this.mCode = i;
        this.mMsg = str;
        this.mResData = bArr;
    }

    public int getCode() {
        return this.mCode;
    }

    public String getMsg() {
        return this.mMsg;
    }

    public String getCharset() {
        return this.mCharset;
    }

    public void setCharset(String str) {
        this.mCharset = str;
    }

    public long getCreateTime() {
        return this.mCreateTime;
    }

    public void setCreateTime(long j) {
        this.mCreateTime = j;
    }

    public long getPeriod() {
        return this.mPeriod;
    }

    public void setPeriod(long j) {
        this.mPeriod = j;
    }

    public HttpUrlHeader getHeader() {
        return this.mHeader;
    }

    public void setHeader(HttpUrlHeader httpUrlHeader) {
        this.mHeader = httpUrlHeader;
    }
}
