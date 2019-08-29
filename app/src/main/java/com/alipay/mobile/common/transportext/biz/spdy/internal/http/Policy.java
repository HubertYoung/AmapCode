package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public interface Policy {
    int getChunkLength();

    long getFixedContentLength();

    HttpURLConnection getHttpConnectionToCache();

    long getIfModifiedSince();

    URL getURL();

    boolean getUseCaches();

    void setSelectedProxy(Proxy proxy);

    boolean usingProxy();
}
