package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;

interface Transport {
    OutputStream createRequestBody();

    void flushRequest();

    InputStream getTransferStream(CacheRequest cacheRequest);

    boolean makeReusable(boolean z, OutputStream outputStream, InputStream inputStream);

    ResponseHeaders readResponseHeaders();

    void writeRequestBody(RetryableOutputStream retryableOutputStream);

    void writeRequestHeaders();
}
