package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.alipay.mobile.common.transport.http.RequestMethodConstants;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Platform;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.mwallet.ErrorMsgHelper;
import com.alipay.mobile.common.transportext.biz.spdy.mwallet.NoRetrySpdyConnException;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketPermission;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.Permission;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLHandshakeException;

public class HttpURLConnectionImpl extends HttpURLConnection implements Policy {
    static final int HTTP_TEMP_REDIRECT = 307;
    private static final int MAX_REDIRECTS = 20;
    final OkHttpClient client;
    private long fixedContentLength = -1;
    protected HttpEngine httpEngine;
    protected IOException httpEngineFailure;
    private final RawHeaders rawRequestHeaders = new RawHeaders();
    private int redirectionCount;
    private int retryCount;
    private Proxy selectedProxy;

    enum Retry {
        NONE,
        SAME_CONNECTION,
        DIFFERENT_CONNECTION
    }

    public HttpURLConnectionImpl(URL url, OkHttpClient client2) {
        super(url);
        this.client = client2;
    }

    public final void connect() {
        initHttpEngine();
        do {
        } while (!execute(false));
    }

    public final void disconnect() {
        if (this.httpEngine != null) {
            if (this.httpEngine.hasResponse()) {
                Util.closeQuietly((Closeable) this.httpEngine.getResponseBody());
            }
            releaseConnection();
        }
    }

    public final InputStream getErrorStream() {
        try {
            HttpEngine response = getResponse();
            if (!response.hasResponseBody() || response.getResponseCode() < 400) {
                return null;
            }
            return response.getResponseBody();
        } catch (IOException e) {
            return null;
        }
    }

    public final String getHeaderField(int position) {
        try {
            return getResponse().getResponseHeaders().getHeaders().getValue(position);
        } catch (IOException e) {
            return null;
        }
    }

    public final String getHeaderField(String fieldName) {
        try {
            RawHeaders rawHeaders = getResponse().getResponseHeaders().getHeaders();
            return fieldName == null ? rawHeaders.getStatusLine() : rawHeaders.get(fieldName);
        } catch (IOException e) {
            return null;
        }
    }

    public final String getHeaderFieldKey(int position) {
        try {
            return getResponse().getResponseHeaders().getHeaders().getFieldName(position);
        } catch (IOException e) {
            return null;
        }
    }

    public final Map<String, List<String>> getHeaderFields() {
        try {
            return getResponse().getResponseHeaders().getHeaders().toMultimap(true);
        } catch (IOException e) {
            return Collections.emptyMap();
        }
    }

    public final Map<String, List<String>> getRequestProperties() {
        if (!this.connected) {
            return this.rawRequestHeaders.toMultimap(false);
        }
        throw new IllegalStateException("Cannot access request header fields after connection is set");
    }

    public final InputStream getInputStream() {
        if (!this.doInput) {
            throw new ProtocolException(ErrorMsgHelper.getMessage(ErrorMsgHelper.PROTOCOL_NOT_SUPPORT_INPUT));
        }
        HttpEngine response = getResponse();
        if (getResponseCode() >= 400) {
            throw new FileNotFoundException(this.url.toString());
        }
        InputStream result = response.getResponseBody();
        if (result != null) {
            return result;
        }
        throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.NO_RESPONSE_BODY_EXISTS), new Object[]{Integer.valueOf(getResponseCode())}));
    }

    public final OutputStream getOutputStream() {
        connect();
        OutputStream out = this.httpEngine.getRequestBody();
        if (out == null) {
            throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.METHOD_NO_SUPPORT_REQUEST_BODY), new Object[]{this.method}));
        } else if (!this.httpEngine.hasResponse()) {
            return out;
        } else {
            throw new ProtocolException(ErrorMsgHelper.getMessage(ErrorMsgHelper.WRITE_REQ_BODY_AFTER_RESP));
        }
    }

    public final Permission getPermission() {
        String hostName = getURL().getHost();
        int hostPort = Util.getEffectivePort(getURL());
        if (usingProxy()) {
            InetSocketAddress proxyAddress = (InetSocketAddress) this.client.getProxy().address();
            hostName = proxyAddress.getHostName();
            hostPort = proxyAddress.getPort();
        }
        return new SocketPermission(hostName + ":" + hostPort, "connect, resolve");
    }

    public final String getRequestProperty(String field) {
        if (field == null) {
            return null;
        }
        return this.rawRequestHeaders.get(field);
    }

    public void setConnectTimeout(int timeoutMillis) {
        this.client.setConnectTimeout((long) timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public int getConnectTimeout() {
        return this.client.getConnectTimeout();
    }

    public void setReadTimeout(int timeoutMillis) {
        this.client.setReadTimeout((long) timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public int getReadTimeout() {
        return this.client.getReadTimeout();
    }

    private void initHttpEngine() {
        if (this.httpEngineFailure != null) {
            throw this.httpEngineFailure;
        } else if (this.httpEngine == null) {
            this.connected = true;
            try {
                if (this.doOutput) {
                    if (TextUtils.equals(this.method, "GET")) {
                        this.method = "POST";
                    } else if (!TextUtils.equals(this.method, "POST") && !TextUtils.equals(this.method, RequestMethodConstants.PUT_METHOD) && !TextUtils.equals(this.method, "PATCH")) {
                        throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.NOT_SUPPORT_HTTP_METHOD), new Object[]{this.method}));
                    }
                }
                this.httpEngine = newHttpEngine(this.method, this.rawRequestHeaders, null, null);
            } catch (IOException e) {
                this.httpEngineFailure = e;
                throw e;
            }
        }
    }

    public HttpURLConnection getHttpConnectionToCache() {
        return this;
    }

    private HttpEngine newHttpEngine(String method, RawHeaders requestHeaders, Connection connection, RetryableOutputStream requestBody) {
        if (TextUtils.equals(this.url.getProtocol(), "http")) {
            return new HttpEngine(this.client, this, method, requestHeaders, connection, requestBody);
        }
        if (TextUtils.equals(this.url.getProtocol(), "https")) {
            return new HttpsEngine(this.client, this, method, requestHeaders, connection, requestBody);
        }
        throw new AssertionError();
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 133 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine getResponse() {
        /*
            r7 = this;
            r7.initHttpEngine()
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
            boolean r4 = r4.hasResponse()
            if (r4 == 0) goto L_0x0039
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
        L_0x000d:
            return r4
        L_0x000e:
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpURLConnectionImpl$Retry r4 = com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpURLConnectionImpl.Retry.DIFFERENT_CONNECTION
            if (r2 != r4) goto L_0x0017
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
            r4.automaticallyReleaseConnectionToPool()
        L_0x0017:
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
            r5 = 0
            r4.release(r5)
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.RawHeaders r5 = r7.rawRequestHeaders
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
            com.alipay.mobile.common.transportext.biz.spdy.Connection r6 = r4.getConnection()
            r4 = r0
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.RetryableOutputStream r4 = (com.alipay.mobile.common.transportext.biz.spdy.internal.http.RetryableOutputStream) r4
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.newHttpEngine(r3, r5, r6, r4)
            r7.httpEngine = r4
            if (r0 != 0) goto L_0x0039
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.RequestHeaders r4 = r4.getRequestHeaders()
            r4.removeContentLength()
        L_0x0039:
            r4 = 1
            boolean r4 = r7.execute(r4)
            if (r4 == 0) goto L_0x0039
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpURLConnectionImpl$Retry r2 = r7.processResponseHeaders()
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpURLConnectionImpl$Retry r4 = com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpURLConnectionImpl.Retry.NONE
            if (r2 != r4) goto L_0x0050
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
            r4.automaticallyReleaseConnectionToPool()
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
            goto L_0x000d
        L_0x0050:
            java.lang.String r3 = r7.method
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r4 = r7.httpEngine
            java.io.OutputStream r0 = r4.getRequestBody()
            int r1 = r7.getResponseCode()
            r4 = 300(0x12c, float:4.2E-43)
            if (r1 == r4) goto L_0x006c
            r4 = 301(0x12d, float:4.22E-43)
            if (r1 == r4) goto L_0x006c
            r4 = 302(0x12e, float:4.23E-43)
            if (r1 == r4) goto L_0x006c
            r4 = 303(0x12f, float:4.25E-43)
            if (r1 != r4) goto L_0x006f
        L_0x006c:
            java.lang.String r3 = "GET"
            r0 = 0
        L_0x006f:
            if (r0 == 0) goto L_0x000e
            boolean r4 = r0 instanceof com.alipay.mobile.common.transportext.biz.spdy.internal.http.RetryableOutputStream
            if (r4 != 0) goto L_0x000e
            java.net.HttpRetryException r4 = new java.net.HttpRetryException
            java.lang.String r5 = "Cannot retry streamed HTTP body"
            com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine r6 = r7.httpEngine
            int r6 = r6.getResponseCode()
            r4.<init>(r5, r6)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpURLConnectionImpl.getResponse():com.alipay.mobile.common.transportext.biz.spdy.internal.http.HttpEngine");
    }

    private boolean execute(boolean readResponse) {
        try {
            this.httpEngine.sendRequest();
            if (readResponse) {
                this.httpEngine.readResponse();
            }
            return true;
        } catch (IOException e) {
            LogCatUtil.error((String) InnerLogUtil.MWALLET_SPDY_TAG, (Throwable) e);
            if (handleFailure(e, readResponse)) {
                return false;
            }
            throw e;
        }
    }

    private boolean handleFailure(IOException e, boolean readResponse) {
        boolean canRetryRequestBody;
        if (!readResponse || "get".equalsIgnoreCase(getRequestMethod())) {
            RouteSelector routeSelector = this.httpEngine.routeSelector;
            if (!(routeSelector == null || this.httpEngine.connection == null)) {
                routeSelector.connectFailed(this.httpEngine.connection, e);
            }
            OutputStream requestBody = this.httpEngine.getRequestBody();
            if (requestBody == null || (requestBody instanceof RetryableOutputStream)) {
                canRetryRequestBody = true;
            } else {
                canRetryRequestBody = false;
            }
            boolean defaultRetry = true;
            if ((routeSelector == null && this.httpEngine.connection == null) || ((routeSelector != null && !routeSelector.hasNext()) || !isRecoverable(e) || !canRetryRequestBody)) {
                this.httpEngineFailure = e;
                defaultRetry = false;
            }
            boolean isCustRetry = false;
            SpdyRequestRetryHandler requestRetryHandler = this.client.getRequestRetryHandler();
            if (requestRetryHandler != null) {
                isCustRetry = requestRetryHandler.retryRequest(this.httpEngineFailure, this.retryCount, defaultRetry, this.client, this);
                if (!isCustRetry) {
                    releaseConnection(e);
                    return false;
                }
            }
            if (isCustRetry || defaultRetry) {
                this.retryCount++;
                releaseConnection(e);
                this.httpEngine = newHttpEngine(this.method, this.rawRequestHeaders, null, cast2RetryableOutputStream(requestBody));
                this.httpEngine.routeSelector = routeSelector;
                if (isCustRetry && !defaultRetry) {
                    this.httpEngine.routeSelector = null;
                }
                return true;
            }
            releaseConnection(e);
            return false;
        }
        LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "handleFailure no retry, return false.");
        releaseConnection(e);
        return false;
    }

    private void releaseConnection(IOException e) {
        releaseConnection();
        if (e instanceof SocketTimeoutException) {
            forceReleaseConnection();
        }
    }

    private void releaseConnection() {
        try {
            this.httpEngine.release(true);
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "releaseConnection exception : " + e.toString());
        }
    }

    private void forceReleaseConnection() {
        try {
            this.httpEngine.releaseConnection();
        } catch (Exception e) {
            LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "forceReleaseConnection exception : " + e.toString());
        }
    }

    private RetryableOutputStream cast2RetryableOutputStream(OutputStream requestBody) {
        if (requestBody == null || !(requestBody instanceof RetryableOutputStream)) {
            return null;
        }
        return (RetryableOutputStream) requestBody;
    }

    private boolean isRecoverable(IOException e) {
        boolean sslFailure;
        if (!(e instanceof SSLHandshakeException) || !(e.getCause() instanceof CertificateException)) {
            sslFailure = false;
        } else {
            sslFailure = true;
        }
        boolean protocolFailure = e instanceof ProtocolException;
        boolean notRetryError = e instanceof NoRetrySpdyConnException;
        if (sslFailure || protocolFailure || notRetryError) {
            return false;
        }
        return true;
    }

    public HttpEngine getHttpEngine() {
        return this.httpEngine;
    }

    private Retry processResponseHeaders() {
        boolean samePort = true;
        if (!TextUtils.isEmpty(this.httpEngine.getRequestHeaders().getHeaders().get("spdy-proxy-url"))) {
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "processResponseHeaders hash spdyProxyUrl. no retry!");
            return Retry.NONE;
        }
        Proxy selectedProxy2 = this.httpEngine.connection != null ? this.httpEngine.connection.getRoute().getProxy() : this.client.getProxy();
        int responseCode = getResponseCode();
        switch (responseCode) {
            case 300:
            case 301:
            case 302:
            case 303:
            case 307:
                if (!getInstanceFollowRedirects()) {
                    return Retry.NONE;
                }
                int i = this.redirectionCount + 1;
                this.redirectionCount = i;
                if (i > 20) {
                    throw new ProtocolException(String.format(ErrorMsgHelper.getMessage(ErrorMsgHelper.TOO_MANY_REDIRECTS), new Object[]{Integer.valueOf(this.redirectionCount)}));
                } else if (responseCode == 307 && !TextUtils.equals(this.method, "GET") && !TextUtils.equals(this.method, RequestMethodConstants.HEAD_METHOD)) {
                    return Retry.NONE;
                } else {
                    String location = getHeaderField((String) "Location");
                    if (location == null) {
                        return Retry.NONE;
                    }
                    URL previousUrl = this.url;
                    this.url = new URL(previousUrl, location);
                    if (!TextUtils.equals(this.url.getProtocol(), "https") && !TextUtils.equals(this.url.getProtocol(), "http")) {
                        return Retry.NONE;
                    }
                    boolean sameProtocol = TextUtils.equals(previousUrl.getProtocol(), this.url.getProtocol());
                    if (!sameProtocol && !this.client.getFollowProtocolRedirects()) {
                        return Retry.NONE;
                    }
                    boolean sameHost = TextUtils.equals(previousUrl.getHost(), this.url.getHost());
                    if (Util.getEffectivePort(previousUrl) != Util.getEffectivePort(this.url)) {
                        samePort = false;
                    }
                    if (!sameHost || !samePort || !sameProtocol) {
                        return Retry.DIFFERENT_CONNECTION;
                    }
                    return Retry.SAME_CONNECTION;
                }
            case 401:
                break;
            case 407:
                if (selectedProxy2.type() != Type.HTTP) {
                    throw new ProtocolException(ErrorMsgHelper.getMessage(ErrorMsgHelper.HTTP_PROXY_AUTH_407));
                }
                break;
            default:
                return Retry.NONE;
        }
        return HttpAuthenticator.processAuthHeader(this.client.getAuthenticator(), getResponseCode(), this.httpEngine.getResponseHeaders().getHeaders(), this.rawRequestHeaders, selectedProxy2, this.url) ? Retry.SAME_CONNECTION : Retry.NONE;
    }

    public final long getFixedContentLength() {
        return this.fixedContentLength;
    }

    public final int getChunkLength() {
        return this.chunkLength;
    }

    public final boolean usingProxy() {
        if (this.selectedProxy != null) {
            return isValidNonDirectProxy(this.selectedProxy);
        }
        return isValidNonDirectProxy(this.client.getProxy());
    }

    private static boolean isValidNonDirectProxy(Proxy proxy) {
        return (proxy == null || proxy.type() == Type.DIRECT) ? false : true;
    }

    public String getResponseMessage() {
        return getResponse().getResponseHeaders().getHeaders().getResponseMessage();
    }

    public final int getResponseCode() {
        return getResponse().getResponseCode();
    }

    public final void setRequestProperty(String field, String newValue) {
        if (this.connected) {
            throw new IllegalStateException("Cannot set request property after connection is made");
        } else if (field == null) {
            throw new NullPointerException("field == null");
        } else if (newValue == null) {
            Platform.get().logW("Ignoring header " + field + " because its value was null.");
        } else if (TextUtils.equals("X-Android-Transports", field)) {
            setTransports(newValue, false);
        } else {
            this.rawRequestHeaders.set(field, newValue);
        }
    }

    public final void addRequestProperty(String field, String value) {
        if (this.connected) {
            throw new IllegalStateException("Cannot add request property after connection is made");
        } else if (field == null) {
            throw new NullPointerException("field == null");
        } else if (value == null) {
            Platform.get().logW("Ignoring header " + field + " because its value was null.");
        } else if (TextUtils.equals("X-Android-Transports", field)) {
            setTransports(value, true);
        } else {
            this.rawRequestHeaders.add(field, value);
        }
    }

    private void setTransports(String transportsString, boolean append) {
        List transportsList = new ArrayList();
        if (append) {
            transportsList.addAll(this.client.getTransports());
        }
        for (String transport : transportsString.split(",", -1)) {
            transportsList.add(transport);
        }
        this.client.setTransports(transportsList);
    }

    public void setFixedLengthStreamingMode(int contentLength) {
        setFixedLengthStreamingMode((long) contentLength);
    }

    @SuppressLint({"Override"})
    public void setFixedLengthStreamingMode(long contentLength) {
        if (this.connected) {
            throw new IllegalStateException("Already connected");
        } else if (this.chunkLength > 0) {
            throw new IllegalStateException("Already in chunked mode");
        } else if (contentLength < 0) {
            throw new IllegalArgumentException("contentLength < 0");
        } else {
            this.fixedContentLength = contentLength;
            this.fixedContentLength = (int) Math.min(contentLength, 2147483647L);
        }
    }

    public final void setSelectedProxy(Proxy proxy) {
        this.selectedProxy = proxy;
    }
}
