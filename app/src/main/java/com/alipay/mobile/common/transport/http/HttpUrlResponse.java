package com.alipay.mobile.common.transport.http;

import com.alipay.mobile.common.transport.Response;

public class HttpUrlResponse extends Response {
    private int a;
    private String b;
    private long c;
    private long d;
    private String e;
    private HttpUrlHeader f;

    public HttpUrlResponse(HttpUrlHeader header, int code, String msg, byte[] resData) {
        this.f = header;
        this.a = code;
        this.b = msg;
        this.mResData = resData;
    }

    public int getCode() {
        return this.a;
    }

    public String getMsg() {
        return this.b;
    }

    public String getCharset() {
        return this.e;
    }

    public void setCharset(String charset) {
        this.e = charset;
    }

    public long getCreateTime() {
        return this.c;
    }

    public void setCreateTime(long createTime) {
        this.c = createTime;
    }

    public long getPeriod() {
        return this.d;
    }

    public void setPeriod(long period) {
        this.d = period;
    }

    public HttpUrlHeader getHeader() {
        return this.f;
    }

    public void setHeader(HttpUrlHeader header) {
        this.f = header;
    }

    public boolean isSuccess() {
        return this.a == 200 || this.a == 304 || this.a == 206;
    }
}
