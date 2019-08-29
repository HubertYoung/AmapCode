package com.alipay.sdk.net;

import android.os.Build.VERSION;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HttpContext;

public final class b {
    public static final String a = "msp";
    static b b;
    final DefaultHttpClient c;

    private static b b() {
        return b;
    }

    private static void c() {
        b = null;
    }

    private b(HttpParams httpParams) {
        this.c = new DefaultHttpClient(httpParams);
    }

    private b(ClientConnectionManager clientConnectionManager, HttpParams httpParams) {
        this.c = new DefaultHttpClient(clientConnectionManager, httpParams);
    }

    public static b a() {
        if (b == null) {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
            HttpConnectionParams.setStaleCheckingEnabled(basicHttpParams, true);
            basicHttpParams.setBooleanParameter("http.protocol.expect-continue", false);
            ConnManagerParams.setMaxTotalConnections(basicHttpParams, 50);
            ConnManagerParams.setMaxConnectionsPerRoute(basicHttpParams, new ConnPerRouteBean(30));
            ConnManagerParams.setTimeout(basicHttpParams, 1000);
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 20000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 30000);
            HttpConnectionParams.setSocketBufferSize(basicHttpParams, 16384);
            HttpProtocolParams.setUseExpectContinue(basicHttpParams, false);
            HttpClientParams.setRedirecting(basicHttpParams, true);
            HttpClientParams.setAuthenticating(basicHttpParams, false);
            HttpProtocolParams.setUserAgent(basicHttpParams, a);
            try {
                SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
                socketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                Scheme scheme = new Scheme("https", socketFactory, 443);
                Scheme scheme2 = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(scheme);
                schemeRegistry.register(scheme2);
                b = new b(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
            } catch (Exception unused) {
                b = new b(basicHttpParams);
            }
        }
        return b;
    }

    private HttpParams f() {
        return this.c.getParams();
    }

    private ClientConnectionManager g() {
        return this.c.getConnectionManager();
    }

    public final HttpResponse a(HttpUriRequest httpUriRequest) throws Exception {
        try {
            return this.c.execute(httpUriRequest);
        } catch (Exception e) {
            throw e;
        }
    }

    private HttpResponse a(HttpUriRequest httpUriRequest, HttpContext httpContext) throws Exception {
        try {
            return this.c.execute(httpUriRequest, httpContext);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private HttpResponse a(HttpHost httpHost, HttpRequest httpRequest) throws Exception {
        try {
            return this.c.execute(httpHost, httpRequest);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private HttpResponse a(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws Exception {
        try {
            return this.c.execute(httpHost, httpRequest, httpContext);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private <T> T a(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler) throws Exception {
        try {
            return this.c.execute(httpUriRequest, responseHandler);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private <T> T a(HttpUriRequest httpUriRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws Exception {
        try {
            return this.c.execute(httpUriRequest, responseHandler, httpContext);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private <T> T a(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler) throws Exception {
        try {
            return this.c.execute(httpHost, httpRequest, responseHandler);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private <T> T a(HttpHost httpHost, HttpRequest httpRequest, ResponseHandler<? extends T> responseHandler, HttpContext httpContext) throws Exception {
        try {
            return this.c.execute(httpHost, httpRequest, responseHandler, httpContext);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private void d() {
        ClientConnectionManager connectionManager = this.c.getConnectionManager();
        if (connectionManager != null) {
            connectionManager.closeExpiredConnections();
            if (VERSION.SDK_INT >= 9) {
                connectionManager.closeIdleConnections(30, TimeUnit.MINUTES);
            }
        }
    }

    private void e() {
        ClientConnectionManager connectionManager = this.c.getConnectionManager();
        if (connectionManager != null) {
            connectionManager.shutdown();
            b = null;
        }
    }
}
