package com.alipay.deviceid.module.rpc.mrpc.core;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

final class e extends DefaultRedirectHandler {
    int a;
    final /* synthetic */ d b;

    e(d dVar) {
        this.b = dVar;
    }

    public final boolean isRedirectRequested(HttpResponse httpResponse, HttpContext httpContext) {
        this.a++;
        boolean isRedirectRequested = e.super.isRedirectRequested(httpResponse, httpContext);
        if (!isRedirectRequested && this.a < 5) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 301 || statusCode == 302) {
                return true;
            }
        }
        return isRedirectRequested;
    }
}
