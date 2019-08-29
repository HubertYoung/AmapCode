package com.alipay.inside.android.phone.mrpc.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;

public class HttpUrlRequest extends Request {
    private String mContentType;
    private ArrayList<Header> mHeaders;
    private byte[] mReqData;
    private boolean mResetCookie;
    private Map<String, String> mTags;
    private String mUrl;
    private String requestMethod = "POST";

    public HttpUrlRequest(String str) {
        this.mUrl = str;
        this.mHeaders = new ArrayList<>();
        this.mTags = new HashMap();
        this.mContentType = "application/x-www-form-urlencoded";
    }

    public HttpUrlRequest(String str, byte[] bArr, ArrayList<Header> arrayList, HashMap<String, String> hashMap) {
        this.mUrl = str;
        this.mReqData = bArr;
        this.mHeaders = arrayList;
        this.mTags = hashMap;
        this.mContentType = "application/x-www-form-urlencoded";
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String setUrl(String str) {
        this.mUrl = str;
        return str;
    }

    public byte[] getReqData() {
        return this.mReqData;
    }

    public void setReqData(byte[] bArr) {
        this.mReqData = bArr;
    }

    public String getContentType() {
        return this.mContentType;
    }

    public void setContentType(String str) {
        this.mContentType = str;
    }

    public void setHeaders(ArrayList<Header> arrayList) {
        this.mHeaders = arrayList;
    }

    public void addHeader(Header header) {
        this.mHeaders.add(header);
    }

    public ArrayList<Header> getHeaders() {
        return this.mHeaders;
    }

    public void setTags(Map<String, String> map) {
        this.mTags = map;
    }

    public void addTags(String str, String str2) {
        if (this.mTags == null) {
            this.mTags = new HashMap();
        }
        this.mTags.put(str, str2);
    }

    public String getTag(String str) {
        if (this.mTags == null) {
            return null;
        }
        return this.mTags.get(str);
    }

    public boolean isResetCookie() {
        return this.mResetCookie;
    }

    public void setResetCookie(boolean z) {
        this.mResetCookie = z;
    }

    public String getKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(getUrl());
        sb.append(Integer.toHexString(getReqData().hashCode()));
        return sb.toString();
    }

    public String toString() {
        return String.format("Url : %s,HttpHeader: %s", new Object[]{getUrl(), getHeaders()});
    }

    public int hashCode() {
        return (((this.mTags == null || !this.mTags.containsKey("id")) ? 1 : this.mTags.get("id").hashCode() + 31) * 31) + (this.mUrl == null ? 0 : this.mUrl.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HttpUrlRequest httpUrlRequest = (HttpUrlRequest) obj;
        if (this.mReqData == null) {
            if (httpUrlRequest.mReqData != null) {
                return false;
            }
        } else if (!this.mReqData.equals(httpUrlRequest.mReqData)) {
            return false;
        }
        if (this.mUrl == null) {
            if (httpUrlRequest.mUrl != null) {
                return false;
            }
        } else if (!this.mUrl.equals(httpUrlRequest.mUrl)) {
            return false;
        }
        return true;
    }

    public void setRequestMethod(String str) {
        this.requestMethod = str;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }
}
