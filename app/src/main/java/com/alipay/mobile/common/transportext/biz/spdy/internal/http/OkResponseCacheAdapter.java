package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.OkResponseCache;
import com.alipay.mobile.common.transportext.biz.spdy.ResponseSource;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public final class OkResponseCacheAdapter implements OkResponseCache {
    private final ResponseCache responseCache;

    public OkResponseCacheAdapter(ResponseCache responseCache2) {
        this.responseCache = responseCache2;
    }

    public final CacheResponse get(URI uri, String requestMethod, Map<String, List<String>> requestHeaders) {
        return this.responseCache.get(uri, requestMethod, requestHeaders);
    }

    public final CacheRequest put(URI uri, URLConnection urlConnection) {
        return this.responseCache.put(uri, urlConnection);
    }

    public final void maybeRemove(String requestMethod, URI uri) {
    }

    public final void update(CacheResponse conditionalCacheHit, HttpURLConnection connection) {
    }

    public final void trackConditionalCacheHit() {
    }

    public final void trackResponse(ResponseSource source) {
    }
}
