package com.alipay.mobile.common.transport;

public class Response {
    protected String mContentType;
    protected byte[] mResData;

    public byte[] getResData() {
        return this.mResData;
    }

    public void setResData(byte[] resData) {
        this.mResData = resData;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String contentType) {
        this.mContentType = contentType;
    }
}
