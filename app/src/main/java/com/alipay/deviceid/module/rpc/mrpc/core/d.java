package com.alipay.deviceid.module.rpc.mrpc.core;

import org.apache.http.client.RedirectHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;

final class d extends DefaultHttpClient {
    final /* synthetic */ b a;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    d(b bVar, ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        // this.a = bVar;
        super(clientConnectionManager, httpParams);
    }

    /* access modifiers changed from: protected */
    public final ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy() {
        return new f(this);
    }

    /* access modifiers changed from: protected */
    public final HttpContext createHttpContext() {
        BasicHttpContext basicHttpContext = new BasicHttpContext();
        basicHttpContext.setAttribute("http.authscheme-registry", getAuthSchemes());
        basicHttpContext.setAttribute("http.cookiespec-registry", getCookieSpecs());
        basicHttpContext.setAttribute("http.auth.credentials-provider", getCredentialsProvider());
        return basicHttpContext;
    }

    /* access modifiers changed from: protected */
    public final BasicHttpProcessor createHttpProcessor() {
        BasicHttpProcessor createHttpProcessor = d.super.createHttpProcessor();
        createHttpProcessor.addRequestInterceptor(b.c);
        createHttpProcessor.addRequestInterceptor(new a(this.a, 0));
        return createHttpProcessor;
    }

    /* access modifiers changed from: protected */
    public final RedirectHandler createRedirectHandler() {
        return new e(this);
    }
}
