package com.alipay.mobile.nebulacore.web;

public class ResourceInfo {
    public boolean mIsMainDoc;
    public String mMethod = "GET";
    public String mMimeType;
    public long mStart;
    public int mStatusCode;
    public String mUrl;

    ResourceInfo(long startTime, String method) {
        this.mMethod = method;
        this.mStart = startTime;
    }
}
