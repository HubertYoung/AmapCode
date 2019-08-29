package com.alipay.mobile.common.transportext.biz.spdy;

import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public interface OkResponseCache {
    CacheResponse get(URI uri, String str, Map<String, List<String>> map);

    void maybeRemove(String str, URI uri);

    CacheRequest put(URI uri, URLConnection uRLConnection);

    void trackConditionalCacheHit();

    void trackResponse(ResponseSource responseSource);

    void update(CacheResponse cacheResponse, HttpURLConnection httpURLConnection);
}
