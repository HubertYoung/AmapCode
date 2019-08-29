package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transportext.biz.shared.ExtTransportStrategy;
import com.alipay.mobile.common.transportext.biz.spdy.Address;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.OkResponseCache;
import com.alipay.mobile.common.transportext.biz.spdy.ResponseSource;
import com.alipay.mobile.common.transportext.biz.spdy.Route;
import com.alipay.mobile.common.transportext.biz.spdy.TunnelRequest;
import com.alipay.mobile.common.transportext.biz.spdy.http.DnsWrapper;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Dns;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Platform;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.sdk.util.h;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public class HttpEngine {
    private static final CacheResponse GATEWAY_TIMEOUT_RESPONSE = new GatewayTimeoutResponse();
    public static final int HTTP_CONTINUE = 100;
    private boolean automaticallyReleaseConnectionToPool;
    private CacheRequest cacheRequest;
    private CacheResponse cacheResponse;
    private InputStream cachedResponseBody;
    private ResponseHeaders cachedResponseHeaders;
    protected final OkHttpClient client;
    boolean connected;
    protected Connection connection;
    private boolean connectionReleased;
    protected final String method;
    protected final Policy policy;
    private OutputStream requestBodyOut;
    final RequestHeaders requestHeaders;
    private InputStream responseBodyIn;
    ResponseHeaders responseHeaders;
    private ResponseSource responseSource;
    private InputStream responseTransferIn;
    protected RouteSelector routeSelector;
    long sentRequestMillis = -1;
    private boolean transparentGzip;
    private Transport transport;
    final URI uri;

    class GatewayTimeoutResponse extends CacheResponse {
        public Map<String, List<String>> getHeaders() {
            Map result = new HashMap();
            result.put(null, Collections.singletonList("HTTP/1.1 504 Gateway Timeout"));
            return result;
        }

        public InputStream getBody() {
            return new ByteArrayInputStream(Util.EMPTY_BYTE_ARRAY);
        }
    }

    public HttpEngine(OkHttpClient client2, Policy policy2, String method2, RawHeaders requestHeaders2, Connection connection2, RetryableOutputStream requestBodyOut2) {
        this.client = client2;
        this.policy = policy2;
        this.method = method2;
        this.connection = connection2;
        this.requestBodyOut = requestBodyOut2;
        try {
            this.uri = Platform.get().toUriLenient(policy2.getURL());
            this.requestHeaders = new RequestHeaders(this.uri, new RawHeaders(requestHeaders2));
        } catch (URISyntaxException e) {
            throw new IOException(e.getMessage());
        }
    }

    public URI getUri() {
        return this.uri;
    }

    public final void sendRequest() {
        if (this.responseSource == null) {
            prepareRawRequestHeaders();
            initResponseSource();
            OkResponseCache responseCache = this.client.getOkResponseCache();
            if (this.policy.getUseCaches() && responseCache != null) {
                responseCache.trackResponse(this.responseSource);
            }
            if (this.requestHeaders.isOnlyIfCached() && this.responseSource.requiresConnection()) {
                if (this.responseSource == ResponseSource.CONDITIONAL_CACHE) {
                    Util.closeQuietly((Closeable) this.cachedResponseBody);
                }
                this.responseSource = ResponseSource.CACHE;
                this.cacheResponse = GATEWAY_TIMEOUT_RESPONSE;
                setResponse(new ResponseHeaders(this.uri, RawHeaders.fromMultimap(this.cacheResponse.getHeaders(), true)), this.cacheResponse.getBody());
            }
            if (this.responseSource.requiresConnection()) {
                sendSocketRequest();
            } else if (this.connection != null) {
                this.client.getConnectionPool().recycle(this.connection);
                this.connection = null;
            }
        }
    }

    private void initResponseSource() {
        this.responseSource = ResponseSource.NETWORK;
        if (this.policy.getUseCaches()) {
            OkResponseCache responseCache = this.client.getOkResponseCache();
            if (responseCache != null) {
                CacheResponse candidate = responseCache.get(this.uri, this.method, this.requestHeaders.getHeaders().toMultimap(false));
                if (candidate != null) {
                    Map responseHeadersMap = candidate.getHeaders();
                    this.cachedResponseBody = candidate.getBody();
                    if (!acceptCacheResponseType(candidate) || responseHeadersMap == null || this.cachedResponseBody == null) {
                        Util.closeQuietly((Closeable) this.cachedResponseBody);
                        return;
                    }
                    this.cachedResponseHeaders = new ResponseHeaders(this.uri, RawHeaders.fromMultimap(responseHeadersMap, true));
                    this.responseSource = this.cachedResponseHeaders.chooseResponseSource(System.currentTimeMillis(), this.requestHeaders);
                    if (this.responseSource == ResponseSource.CACHE) {
                        this.cacheResponse = candidate;
                        setResponse(this.cachedResponseHeaders, this.cachedResponseBody);
                    } else if (this.responseSource == ResponseSource.CONDITIONAL_CACHE) {
                        this.cacheResponse = candidate;
                    } else if (this.responseSource == ResponseSource.NETWORK) {
                        Util.closeQuietly((Closeable) this.cachedResponseBody);
                    } else {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    private void sendSocketRequest() {
        if (this.connection == null) {
            connect();
        }
        if (this.transport != null) {
            throw new IllegalStateException("\"transport != null\" is illegal state");
        }
        this.transport = (Transport) this.connection.newTransport(this);
        if (hasRequestBody() && this.requestBodyOut == null) {
            this.requestBodyOut = this.transport.createRequestBody();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public final void connect() {
        if (this.connection == null) {
            if (this.routeSelector == null) {
                String uriHost = this.uri.getHost();
                if (TextUtils.isEmpty(uriHost)) {
                    throw new UnknownHostException(this.uri.toString());
                }
                SSLSocketFactory sslSocketFactory = null;
                HostnameVerifier hostnameVerifier = null;
                if (this.uri.getScheme().equalsIgnoreCase("https")) {
                    sslSocketFactory = this.client.getSslSocketFactory();
                    hostnameVerifier = this.client.getHostnameVerifier();
                }
                this.routeSelector = new RouteSelector(new Address(uriHost, Util.getEffectivePort(this.uri), sslSocketFactory, hostnameVerifier, this.client.getAuthenticator(), this.client.getProxy(), this.client.getTransports()), this.uri, this.client.getProxySelector(), this.client.getConnectionPool(), getDnsClient(), this.client.getRoutesDatabase(), this.client.getNetContext());
            }
            this.client.getClientLock().lock();
            try {
                this.connection = this.routeSelector.next(this.method);
                logLastInetAddress();
                recordProxyAndHost();
                if (!isConnected()) {
                    if (MiscUtils.isDebugger(this.client.getContext())) {
                        InnerLogUtil.log4AtlsTest("New connection object hashcode=" + this.connection.hashCode());
                        LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "New connection " + this.connection.getRoute().toString());
                    }
                    ExtTransportStrategy.setUseSpdyShortReadTimeout(false);
                    this.connection.setContext(this.client.getContext());
                    this.connection.connect(this.client.getConnectTimeout(), this.client.getReadTimeout(), getTunnelConfig(), this.client.getNetContext());
                    this.client.getConnectionPool().maybeShare(this.connection);
                    this.client.getRoutesDatabase().connected(this.connection.getRoute());
                    LogCatUtil.info(InnerLogUtil.ALTS_TEST_LOG, "Spdy Connected");
                } else {
                    LogCatUtil.info(InnerLogUtil.ALTS_TEST_LOG, "GET SpdyConnection.");
                    this.connection.updateReadTimeout(this.client.getReadTimeout());
                }
                this.client.getClientLock().unlock();
                connected(this.connection);
                if (this.connection.getRoute().getProxy() != this.client.getProxy()) {
                    this.requestHeaders.getHeaders().setRequestLine(getRequestLine());
                }
            } catch (Throwable th) {
                this.client.getClientLock().unlock();
                throw th;
            }
        }
    }

    private boolean isConnected() {
        SpdyConnection spdyConnection = this.connection.getSpdyConnection();
        if (!this.connection.isConnected() || spdyConnection == null) {
            return false;
        }
        if (!spdyConnection.isShutdown()) {
            return true;
        }
        Util.closeQuietly((Closeable) this.connection);
        return false;
    }

    private void recordProxyAndHost() {
        try {
            Route route = this.connection.getRoute();
            Proxy proxy = route.getProxy();
            if (proxy == null || proxy.type() == Type.DIRECT) {
                recordIsProxy("F");
            } else {
                recordIsProxy("T");
            }
            recordTargetHost(route.getSocketAddress());
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) e);
        }
    }

    private void logLastInetAddress() {
        if (MiscUtils.isDebugger(this.client.getContext())) {
            try {
                InetSocketAddress socketAddress1 = this.connection.getRoute().getSocketAddress();
                if (socketAddress1 != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("{operationType:" + this.client.getNetContext().api + ",");
                    stringBuilder.append("InetAddress: " + socketAddress1.getAddress().toString() + ",");
                    stringBuilder.append(h.d);
                    LogCatUtil.debug("RouteSelector", stringBuilder.toString());
                    return;
                }
                LogCatUtil.debug("RouteSelector", "{operationType:" + this.client.getNetContext().api + ", InetAddress:null}");
            } catch (Exception e) {
                LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "logLastInetAddress exception : " + e.toString());
            }
        }
    }

    private Dns getDnsClient() {
        return new DnsWrapper(this.client);
    }

    /* access modifiers changed from: protected */
    public void connected(Connection connection2) {
        this.policy.setSelectedProxy(connection2.getRoute().getProxy());
        this.connected = true;
    }

    private void sentTimeItemDot() {
        try {
            this.client.getNetContext().getCurrentDataContainer().timeItemDot(RPCDataItems.SENT_TIME, this.sentRequestMillis);
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "sentTimeItemDot exception : " + e.toString());
        }
    }

    public void writingRequestHeaders() {
        if (this.sentRequestMillis != -1) {
            throw new IllegalStateException();
        }
        this.sentRequestMillis = System.currentTimeMillis();
        sentTimeItemDot();
    }

    private void setResponse(ResponseHeaders headers, InputStream body) {
        if (this.responseBodyIn != null) {
            throw new IllegalStateException();
        }
        this.responseHeaders = headers;
        if (body != null) {
            initContentStream(body);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean hasRequestBody() {
        return TextUtils.equals(this.method, "POST") || TextUtils.equals(this.method, RequestMethodConstants.PUT_METHOD) || TextUtils.equals(this.method, "PATCH");
    }

    public final OutputStream getRequestBody() {
        if (this.responseSource != null) {
            return this.requestBodyOut;
        }
        throw new IllegalStateException();
    }

    public final boolean hasResponse() {
        return this.responseHeaders != null;
    }

    public final RequestHeaders getRequestHeaders() {
        return this.requestHeaders;
    }

    public final ResponseHeaders getResponseHeaders() {
        if (this.responseHeaders != null) {
            return this.responseHeaders;
        }
        throw new IllegalStateException();
    }

    public final int getResponseCode() {
        if (this.responseHeaders != null) {
            return this.responseHeaders.getHeaders().getResponseCode();
        }
        throw new IllegalStateException();
    }

    public final InputStream getResponseBody() {
        if (this.responseHeaders != null) {
            return this.responseBodyIn;
        }
        throw new IllegalStateException();
    }

    public final CacheResponse getCacheResponse() {
        return this.cacheResponse;
    }

    public final Connection getConnection() {
        return this.connection;
    }

    /* access modifiers changed from: protected */
    public boolean acceptCacheResponseType(CacheResponse cacheResponse2) {
        return true;
    }

    private void maybeCache() {
        if (this.policy.getUseCaches()) {
            OkResponseCache responseCache = this.client.getOkResponseCache();
            if (responseCache != null) {
                HttpURLConnection connectionToCache = this.policy.getHttpConnectionToCache();
                if (!this.responseHeaders.isCacheable(this.requestHeaders)) {
                    responseCache.maybeRemove(connectionToCache.getRequestMethod(), this.uri);
                } else {
                    this.cacheRequest = responseCache.put(this.uri, connectionToCache);
                }
            }
        }
    }

    public final void automaticallyReleaseConnectionToPool() {
        this.automaticallyReleaseConnectionToPool = true;
        if (this.connection != null && this.connectionReleased) {
            this.client.getConnectionPool().recycle(this.connection);
            this.connection = null;
        }
    }

    public final void releaseConnection() {
        Util.closeQuietly((Closeable) this.connection);
        LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "HttpEngin. force release connection");
    }

    public final void release(boolean streamCanceled) {
        if (this.responseBodyIn == this.cachedResponseBody) {
            Util.closeQuietly((Closeable) this.responseBodyIn);
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "HttpEngin. close responseBodyIn");
        }
        if (!this.connectionReleased && this.connection != null) {
            this.connectionReleased = true;
            if (this.transport == null || !this.transport.makeReusable(streamCanceled, this.requestBodyOut, this.responseTransferIn)) {
                Util.closeQuietly((Closeable) this.connection);
                this.connection = null;
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "HttpEngin. no reusable");
            } else if (this.automaticallyReleaseConnectionToPool) {
                this.client.getConnectionPool().recycle(this.connection);
                this.connection = null;
                LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "HttpEngin. automatically=true, recycle");
            }
        }
    }

    private void initContentStream(InputStream transferStream) {
        this.responseTransferIn = transferStream;
        if (!this.transparentGzip || !this.responseHeaders.isContentEncodingGzip()) {
            this.responseBodyIn = transferStream;
            return;
        }
        this.responseHeaders.stripContentEncoding();
        this.responseHeaders.stripContentLength();
        this.responseBodyIn = new GZIPInputStream(transferStream);
    }

    public final boolean hasResponseBody() {
        int responseCode = this.responseHeaders.getHeaders().getResponseCode();
        if (TextUtils.equals(this.method, RequestMethodConstants.HEAD_METHOD)) {
            return false;
        }
        if ((responseCode < 100 || responseCode >= 200) && responseCode != 204 && responseCode != 304) {
            return true;
        }
        if (this.responseHeaders.getContentLength() != -1 || this.responseHeaders.isChunked()) {
            return true;
        }
        return false;
    }

    private void prepareRawRequestHeaders() {
        String spdyProxyUrlStr = this.requestHeaders.getHeaders().get("spdy-proxy-url");
        URL spdyProxyUrl = null;
        if (!TextUtils.isEmpty(spdyProxyUrlStr)) {
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "prepareRawRequestHeaders spdyProxyUrlStr=" + spdyProxyUrlStr);
            spdyProxyUrl = new URL(spdyProxyUrlStr);
            this.requestHeaders.getHeaders().setRequestLine(getRequestLine(spdyProxyUrl));
        } else {
            this.requestHeaders.getHeaders().setRequestLine(getRequestLine());
        }
        setUserAgent(spdyProxyUrlStr);
        if (this.requestHeaders.getHost() == null) {
            if (spdyProxyUrl != null) {
                this.requestHeaders.setHost(getOriginAddress(spdyProxyUrl));
            } else {
                this.requestHeaders.setHost(getOriginAddress(this.policy.getURL()));
            }
        }
        if ((this.connection == null || this.connection.getHttpMinorVersion() != 0) && this.requestHeaders.getConnection() == null) {
            this.requestHeaders.setConnection("Keep-Alive");
        }
        if (this.requestHeaders.getAcceptEncoding() == null) {
            this.transparentGzip = true;
            this.requestHeaders.setAcceptEncoding("gzip");
        }
        if (hasRequestBody() && this.requestHeaders.getContentType() == null) {
            this.requestHeaders.setContentType("application/x-www-form-urlencoded");
        }
        long ifModifiedSince = this.policy.getIfModifiedSince();
        if (ifModifiedSince != 0) {
            this.requestHeaders.setIfModifiedSince(new Date(ifModifiedSince));
        }
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            URI cookieURI = this.uri;
            if (spdyProxyUrl != null) {
                try {
                    cookieURI = spdyProxyUrl.toURI();
                } catch (URISyntaxException e) {
                    throw new IOException(e.getMessage(), e);
                }
            }
            this.requestHeaders.addCookies(cookieHandler.get(cookieURI, this.requestHeaders.getHeaders().toMultimap(false)));
        }
    }

    private void setUserAgent(String spdyProxyurl) {
        if (!TextUtils.isEmpty(spdyProxyurl)) {
            if (TextUtils.isEmpty(this.requestHeaders.getUserAgent())) {
                this.requestHeaders.setUserAgent("android");
            }
        } else if (TextUtils.isEmpty(this.requestHeaders.getUserAgent()) || !TextUtils.equals(this.requestHeaders.getUserAgent(), "android")) {
            this.requestHeaders.setUserAgent("android");
        }
    }

    /* access modifiers changed from: 0000 */
    public String getRequestLine() {
        return this.method + Token.SEPARATOR + requestString() + Token.SEPARATOR + ((this.connection == null || this.connection.getHttpMinorVersion() != 0) ? "HTTP/1.1" : "HTTP/1.0");
    }

    /* access modifiers changed from: 0000 */
    public String getRequestLine(URL url) {
        return this.method + Token.SEPARATOR + requestString(url) + Token.SEPARATOR + ((this.connection == null || this.connection.getHttpMinorVersion() != 0) ? "HTTP/1.1" : "HTTP/1.0");
    }

    private String requestString() {
        return requestString(this.policy.getURL());
    }

    private String requestString(URL url) {
        if (includeAuthorityInRequestLine()) {
            return url.toString();
        }
        return requestPath(url);
    }

    public static String requestPath(URL url) {
        String fileOnly = url.getFile();
        if (fileOnly == null) {
            return "/";
        }
        if (!fileOnly.startsWith("/")) {
            return "/" + fileOnly;
        }
        return fileOnly;
    }

    /* access modifiers changed from: protected */
    public boolean includeAuthorityInRequestLine() {
        if (this.connection == null) {
            return this.policy.usingProxy();
        }
        return this.connection.getRoute().getProxy().type() == Type.HTTP;
    }

    public static String getDefaultUserAgent() {
        String agent = System.getProperty("http.agent");
        return agent != null ? agent : "Java" + System.getProperty("java.version");
    }

    public static String getOriginAddress(URL url) {
        int port = url.getPort();
        String result = url.getHost();
        if (port <= 0 || port == Util.getDefaultPort(url.getProtocol())) {
            return result;
        }
        return result + ":" + port;
    }

    public final void readResponse() {
        if (hasResponse()) {
            this.responseHeaders.setResponseSource(this.responseSource);
        } else if (this.responseSource == null) {
            throw new IllegalStateException("readResponse() without sendRequest()");
        } else if (this.responseSource.requiresConnection()) {
            if (this.sentRequestMillis == -1) {
                if (this.requestBodyOut instanceof RetryableOutputStream) {
                    this.requestHeaders.setContentLength((long) ((RetryableOutputStream) this.requestBodyOut).contentLength());
                }
                this.transport.writeRequestHeaders();
            }
            if (this.requestBodyOut != null) {
                this.requestBodyOut.close();
                if (this.requestBodyOut instanceof RetryableOutputStream) {
                    this.transport.writeRequestBody((RetryableOutputStream) this.requestBodyOut);
                }
            }
            this.transport.flushRequest();
            this.client.getNetContext().getCurrentDataContainer().timeItemRelease(RPCDataItems.SENT_TIME);
            this.client.getNetContext().getCurrentDataContainer().timeItemDot(RPCDataItems.WAIT_TIME);
            this.responseHeaders = this.transport.readResponseHeaders();
            this.responseHeaders.setLocalTimestamps(this.sentRequestMillis, System.currentTimeMillis());
            this.responseHeaders.setResponseSource(this.responseSource);
            if (this.responseSource == ResponseSource.CONDITIONAL_CACHE) {
                if (this.cachedResponseHeaders.validate(this.responseHeaders)) {
                    release(false);
                    this.responseHeaders = this.cachedResponseHeaders.combine(this.responseHeaders);
                    OkResponseCache responseCache = this.client.getOkResponseCache();
                    responseCache.trackConditionalCacheHit();
                    responseCache.update(this.cacheResponse, this.policy.getHttpConnectionToCache());
                    initContentStream(this.cachedResponseBody);
                    return;
                }
                Util.closeQuietly((Closeable) this.cachedResponseBody);
            }
            if (hasResponseBody()) {
                maybeCache();
            }
            initContentStream(this.transport.getTransferStream(this.cacheRequest));
        }
    }

    /* access modifiers changed from: protected */
    public TunnelRequest getTunnelConfig() {
        return null;
    }

    public void receiveHeaders(RawHeaders headers) {
        CookieHandler cookieHandler = this.client.getCookieHandler();
        if (cookieHandler != null) {
            cookieHandler.put(this.uri, headers.toMultimap(true));
        }
    }

    public TransportContext getNetContext() {
        return this.client.getNetContext();
    }

    private void recordIsProxy(String flag) {
        try {
            this.client.getNetContext().getCurrentDataContainer().putDataItem(RPCDataItems.PROXY, flag);
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) e);
        }
    }

    private void recordTargetHost(InetSocketAddress result) {
        try {
            this.client.getNetContext().getCurrentDataContainer().putDataItem("TARGET_HOST", result.getAddress().getHostAddress() + ":" + result.getPort());
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) e);
        }
    }
}
