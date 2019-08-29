package com.alipay.mobile.h5container.api;

public class H5PageImage {
    public static String TAG = "H5PageImage";
    public String appId;
    public long costTime;
    public String etag;
    public String size;
    public int statusCode;
    public String url;
    public String version;

    public H5PageImage() {
        this.url = "";
        this.size = "";
        this.statusCode = 0;
        this.costTime = 0;
        this.etag = "";
    }

    public H5PageImage(String url2, String size2, int statusCode2, long costTime2, String appId2, String version2, String etag2) {
        this.url = url2;
        this.size = size2;
        this.statusCode = statusCode2;
        this.costTime = costTime2;
        this.appId = appId2;
        this.version = version2;
        this.etag = etag2;
    }

    public String toString() {
        return "H5PageImage{url='" + this.url + '\'' + ", size='" + this.size + '\'' + ", statusCode=" + this.statusCode + ", costTime=" + this.costTime + '}';
    }
}
