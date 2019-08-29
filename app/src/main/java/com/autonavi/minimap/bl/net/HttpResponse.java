package com.autonavi.minimap.bl.net;

import java.util.HashMap;
import java.util.Map;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class HttpResponse {
    public static final int ERROR_CACHE_NO_RESULT = 5;
    public static final int ERROR_CONNECT_TIMEOUT = 2;
    public static final int ERROR_NO_NET = 0;
    public static final int ERROR_RECV_FAILED = 4;
    public static final int ERROR_SEND_FAILED = 3;
    public static final int ERROR_TIMEOUT = 1;
    public static final int ERROR_UNKNOWN = 6;
    private IHttpBuffer mBuffer;
    private int mErrorCode = -1;
    private Map<String, String> mHeaders = new HashMap();
    private boolean mIsFromCache;
    private HttpRequest mRequest;
    private int mRequestID;
    private int mStatusCode;
    private String mUrl;

    public void setUrl(String str) {
        this.mUrl = str;
    }

    public void setStatusCode(int i) {
        this.mStatusCode = i;
    }

    public void setErrorCode(int i) {
        this.mErrorCode = i;
    }

    public void addHeader(String str, String str2) {
        this.mHeaders.put(str, str2);
    }

    public void setBody(IHttpBuffer iHttpBuffer) {
        this.mBuffer = iHttpBuffer;
    }

    public void setRequest(HttpRequest httpRequest) {
        this.mRequest = httpRequest;
    }

    public void setRequestID(int i) {
        this.mRequestID = i;
    }

    public void setIsFromCache(boolean z) {
        this.mIsFromCache = z;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public int getErrorCode() {
        return this.mErrorCode;
    }

    public String[] getHeaderKeys() {
        return (String[]) this.mHeaders.keySet().toArray(new String[this.mHeaders.keySet().size()]);
    }

    public String getHeader(String str) {
        return this.mHeaders.get(str);
    }

    public IHttpBuffer getBody() {
        return this.mBuffer;
    }

    public int getRequestID() {
        return this.mRequestID;
    }

    public HttpRequest getRequest() {
        return this.mRequest;
    }

    public boolean isFromCache() {
        return this.mIsFromCache;
    }
}
