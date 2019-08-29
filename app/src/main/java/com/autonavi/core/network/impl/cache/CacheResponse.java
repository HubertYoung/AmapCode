package com.autonavi.core.network.impl.cache;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CacheResponse implements bpa, Serializable {
    private long mContentLength;
    private Map<String, List<String>> mHeaders;
    private byte[] mResponseBody;
    private int mStatusCode;

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public long getContentLength() {
        return this.mContentLength;
    }

    public String getHeader(String str) {
        return bps.b(this.mHeaders, str);
    }

    public Map<String, List<String>> getHeaders() {
        return this.mHeaders;
    }

    public InputStream getBodyInputStream() {
        if (this.mResponseBody == null) {
            return null;
        }
        return new ByteArrayInputStream(this.mResponseBody);
    }
}
