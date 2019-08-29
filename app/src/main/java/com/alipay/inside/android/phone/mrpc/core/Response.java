package com.alipay.inside.android.phone.mrpc.core;

public class Response {
    protected String mContentType;
    protected byte[] mResData;

    public byte[] getResData() {
        return this.mResData;
    }

    public void setResData(byte[] bArr) {
        this.mResData = bArr;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }
}
