package com.alipay.deviceid.module.rpc.mrpc.core;

import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

final class f implements ConnectionKeepAliveStrategy {
    final /* synthetic */ d a;

    f(d dVar) {
        this.a = dVar;
    }

    public final long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        return 180000;
    }
}
