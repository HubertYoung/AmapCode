package com.alipay.mobile.common.transportext.biz.spdy;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transportext.biz.spdy.Response.Receiver;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpAuthenticator;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpURLConnectionImpl;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpsURLConnectionImpl;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.OkResponseCacheAdapter;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.SpdyRequestRetryHandler;
import com.alipay.mobile.common.transportext.biz.spdy.internal.tls.OkHostnameVerifier;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.ResponseCache;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public final class OkHttpClient implements URLStreamHandlerFactory {
    private static final List<String> DEFAULT_TRANSPORTS = Util.immutableList(Arrays.asList(new String[]{"spdy/3", "http/1.1"}));
    private OkAuthenticator authenticator;
    private Lock clientLock;
    private int connectTimeout;
    private ConnectionPool connectionPool;
    private Context context;
    private CookieHandler cookieHandler;
    private final Dispatcher dispatcher;
    private boolean followProtocolRedirects;
    private HostnameVerifier hostnameVerifier;
    private SpdyRequestRetryHandler mRequestRetryHandler;
    private int mStreamReadTimeout;
    private TransportContext netContext;
    private Proxy proxy;
    private ProxySelector proxySelector;
    private int readTimeout;
    private ResponseCache responseCache;
    private final RouteDatabase routeDatabase;
    private SSLSocketFactory sslSocketFactory;
    private List<String> transports;

    public OkHttpClient() {
        this.followProtocolRedirects = true;
        this.routeDatabase = new RouteDatabase();
        this.dispatcher = new Dispatcher();
        this.clientLock = new ReentrantLock(true);
    }

    private OkHttpClient(OkHttpClient copyFrom) {
        this.followProtocolRedirects = true;
        this.routeDatabase = copyFrom.routeDatabase;
        this.dispatcher = copyFrom.dispatcher;
        this.clientLock = copyFrom.clientLock;
        this.context = copyFrom.context;
    }

    public final Lock getClientLock() {
        return this.clientLock;
    }

    public final void setClientLock(Lock clientLock2) {
        this.clientLock = clientLock2;
    }

    public final void setConnectTimeout(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            this.connectTimeout = (int) millis;
        }
    }

    public final int getConnectTimeout() {
        return this.connectTimeout;
    }

    @Deprecated
    public final void setReadTimeout(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            this.readTimeout = (int) millis;
        }
    }

    public final void setStreamReadTimeout(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0");
        } else if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        } else {
            long millis = unit.toMillis(timeout);
            if (millis > 2147483647L) {
                throw new IllegalArgumentException("Timeout too large.");
            }
            this.mStreamReadTimeout = (int) millis;
        }
    }

    public final int getReadTimeout() {
        return this.readTimeout;
    }

    public final int getStreamReadTimeout() {
        return this.mStreamReadTimeout;
    }

    public final OkHttpClient setProxy(Proxy proxy2) {
        this.proxy = proxy2;
        return this;
    }

    public final Proxy getProxy() {
        return this.proxy;
    }

    public final OkHttpClient setProxySelector(ProxySelector proxySelector2) {
        this.proxySelector = proxySelector2;
        return this;
    }

    public final ProxySelector getProxySelector() {
        return this.proxySelector;
    }

    public final OkHttpClient setCookieHandler(CookieHandler cookieHandler2) {
        this.cookieHandler = cookieHandler2;
        return this;
    }

    public final CookieHandler getCookieHandler() {
        return this.cookieHandler;
    }

    public final OkHttpClient setResponseCache(ResponseCache responseCache2) {
        this.responseCache = responseCache2;
        return this;
    }

    public final ResponseCache getResponseCache() {
        return this.responseCache;
    }

    public final OkResponseCache getOkResponseCache() {
        if (this.responseCache instanceof HttpResponseCache) {
            return ((HttpResponseCache) this.responseCache).okResponseCache;
        }
        if (this.responseCache != null) {
            return new OkResponseCacheAdapter(this.responseCache);
        }
        return null;
    }

    public final OkHttpClient setSslSocketFactory(SSLSocketFactory sslSocketFactory2) {
        this.sslSocketFactory = sslSocketFactory2;
        return this;
    }

    public final SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public final OkHttpClient setHostnameVerifier(HostnameVerifier hostnameVerifier2) {
        this.hostnameVerifier = hostnameVerifier2;
        return this;
    }

    public final HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public final OkHttpClient setAuthenticator(OkAuthenticator authenticator2) {
        this.authenticator = authenticator2;
        return this;
    }

    public final OkAuthenticator getAuthenticator() {
        return this.authenticator;
    }

    public final OkHttpClient setConnectionPool(ConnectionPool connectionPool2) {
        this.connectionPool = connectionPool2;
        return this;
    }

    public final ConnectionPool getConnectionPool() {
        return this.connectionPool;
    }

    public final OkHttpClient setFollowProtocolRedirects(boolean followProtocolRedirects2) {
        this.followProtocolRedirects = followProtocolRedirects2;
        return this;
    }

    public final boolean getFollowProtocolRedirects() {
        return this.followProtocolRedirects;
    }

    public final RouteDatabase getRoutesDatabase() {
        return this.routeDatabase;
    }

    public final OkHttpClient setTransports(List<String> transports2) {
        List<String> transports3 = Util.immutableList(transports2);
        if (!transports3.contains("http/1.1")) {
            throw new IllegalArgumentException("transports doesn't contain http/1.1: " + transports3);
        } else if (transports3.contains(null)) {
            throw new IllegalArgumentException("transports must not contain null");
        } else if (transports3.contains("")) {
            throw new IllegalArgumentException("transports contains an empty string");
        } else {
            this.transports = transports3;
            return this;
        }
    }

    public final List<String> getTransports() {
        return this.transports;
    }

    public final void enqueue(Request request, Receiver responseReceiver, TransportContext netContext2) {
        this.dispatcher.enqueue(open(request.url(), null, netContext2), request, responseReceiver);
    }

    public final void cancel(Object tag) {
        this.dispatcher.cancel(tag);
    }

    public final HttpURLConnection open(URL url, TransportContext netContext2) {
        return open(url, this.proxy, netContext2);
    }

    /* access modifiers changed from: 0000 */
    public final HttpURLConnection open(URL url, Proxy proxy2, TransportContext netContext2) {
        String protocol = url.getProtocol();
        OkHttpClient copy = copyWithDefaults();
        copy.proxy = proxy2;
        copy.netContext = netContext2;
        if (TextUtils.equals(protocol, "http")) {
            return new HttpURLConnectionImpl(url, copy);
        }
        if (TextUtils.equals(protocol, "https")) {
            return new HttpsURLConnectionImpl(url, copy);
        }
        throw new IllegalArgumentException("Unexpected protocol: " + protocol);
    }

    private OkHttpClient copyWithDefaults() {
        SSLSocketFactory defaultSSLSocketFactory;
        ConnectionPool connectionPool2;
        OkHttpClient result = new OkHttpClient(this);
        result.proxy = this.proxy != null ? this.proxy : Proxy.NO_PROXY;
        result.proxySelector = this.proxySelector != null ? this.proxySelector : ProxySelector.getDefault();
        result.cookieHandler = this.cookieHandler != null ? this.cookieHandler : CookieHandler.getDefault();
        result.responseCache = this.responseCache != null ? this.responseCache : ResponseCache.getDefault();
        if (this.sslSocketFactory != null) {
            defaultSSLSocketFactory = this.sslSocketFactory;
        } else {
            defaultSSLSocketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
        }
        result.sslSocketFactory = defaultSSLSocketFactory;
        result.hostnameVerifier = this.hostnameVerifier != null ? this.hostnameVerifier : OkHostnameVerifier.INSTANCE;
        result.authenticator = this.authenticator != null ? this.authenticator : HttpAuthenticator.SYSTEM_DEFAULT;
        if (this.connectionPool != null) {
            connectionPool2 = this.connectionPool;
        } else {
            connectionPool2 = ConnectionPool.getDefault();
        }
        result.connectionPool = connectionPool2;
        result.followProtocolRedirects = this.followProtocolRedirects;
        result.transports = this.transports != null ? this.transports : DEFAULT_TRANSPORTS;
        result.connectTimeout = this.connectTimeout;
        result.readTimeout = this.readTimeout;
        result.mStreamReadTimeout = this.mStreamReadTimeout;
        result.setRequestRetryHandler(this.mRequestRetryHandler);
        return result;
    }

    public final URLStreamHandler createURLStreamHandler(final String protocol) {
        if (TextUtils.equals(protocol, "http") || TextUtils.equals(protocol, "https")) {
            return new URLStreamHandler() {
                /* access modifiers changed from: protected */
                public URLConnection openConnection(URL url) {
                    return OkHttpClient.this.open(url, null, null);
                }

                /* access modifiers changed from: protected */
                public URLConnection openConnection(URL url, Proxy proxy) {
                    return OkHttpClient.this.open(url, proxy, null);
                }

                /* access modifiers changed from: protected */
                public int getDefaultPort() {
                    if (TextUtils.equals(protocol, "http")) {
                        return 80;
                    }
                    if (TextUtils.equals(protocol, "https")) {
                        return 443;
                    }
                    throw new AssertionError();
                }
            };
        }
        return null;
    }

    public final Context getContext() {
        return this.context;
    }

    public final void setContext(Context context2) {
        this.context = context2;
    }

    public final void setNetContext(TransportContext netContext2) {
        this.netContext = netContext2;
    }

    public final TransportContext getNetContext() {
        return this.netContext;
    }

    public final void setRequestRetryHandler(SpdyRequestRetryHandler spdyRequestRetryHandler) {
        this.mRequestRetryHandler = spdyRequestRetryHandler;
    }

    public final SpdyRequestRetryHandler getRequestRetryHandler() {
        return this.mRequestRetryHandler;
    }
}
