package com.alipay.mobile.common.transport.ext;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public interface ExtTransportClient {
    public static final int MODULE_CATEGORY_MRPC = 1;
    public static final int MODULE_CATEGORY_SPDY = 0;

    HttpResponse execute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext);

    int getModuleCategory();
}
