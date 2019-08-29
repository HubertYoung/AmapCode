package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SecureCacheResponse;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public final class HttpsURLConnectionImpl extends HttpsURLConnection {
    private final HttpUrlConnectionDelegate delegate;

    final class HttpUrlConnectionDelegate extends HttpURLConnectionImpl {
        private HttpUrlConnectionDelegate(URL url, OkHttpClient client) {
            super(url, client);
        }

        public final HttpURLConnection getHttpConnectionToCache() {
            return HttpsURLConnectionImpl.this;
        }

        public final SecureCacheResponse getSecureCacheResponse() {
            if (this.httpEngine instanceof HttpsEngine) {
                return (SecureCacheResponse) this.httpEngine.getCacheResponse();
            }
            return null;
        }
    }

    public HttpsURLConnectionImpl(URL url, OkHttpClient client) {
        super(url);
        this.delegate = new HttpUrlConnectionDelegate(url, client);
    }

    public final String getCipherSuite() {
        SecureCacheResponse cacheResponse = this.delegate.getSecureCacheResponse();
        if (cacheResponse != null) {
            return cacheResponse.getCipherSuite();
        }
        SSLSocket sslSocket = getSslSocket();
        if (sslSocket != null) {
            return sslSocket.getSession().getCipherSuite();
        }
        return null;
    }

    public final Certificate[] getLocalCertificates() {
        SecureCacheResponse cacheResponse = this.delegate.getSecureCacheResponse();
        if (cacheResponse != null) {
            List result = cacheResponse.getLocalCertificateChain();
            if (result != null) {
                return (Certificate[]) result.toArray(new Certificate[result.size()]);
            }
            return null;
        }
        SSLSocket sslSocket = getSslSocket();
        if (sslSocket != null) {
            return sslSocket.getSession().getLocalCertificates();
        }
        return null;
    }

    public final Certificate[] getServerCertificates() {
        SecureCacheResponse cacheResponse = this.delegate.getSecureCacheResponse();
        if (cacheResponse != null) {
            List result = cacheResponse.getServerCertificateChain();
            if (result != null) {
                return (Certificate[]) result.toArray(new Certificate[result.size()]);
            }
            return null;
        }
        SSLSocket sslSocket = getSslSocket();
        if (sslSocket != null) {
            return sslSocket.getSession().getPeerCertificates();
        }
        return null;
    }

    public final Principal getPeerPrincipal() {
        SecureCacheResponse cacheResponse = this.delegate.getSecureCacheResponse();
        if (cacheResponse != null) {
            return cacheResponse.getPeerPrincipal();
        }
        SSLSocket sslSocket = getSslSocket();
        if (sslSocket != null) {
            return sslSocket.getSession().getPeerPrincipal();
        }
        return null;
    }

    public final Principal getLocalPrincipal() {
        SecureCacheResponse cacheResponse = this.delegate.getSecureCacheResponse();
        if (cacheResponse != null) {
            return cacheResponse.getLocalPrincipal();
        }
        SSLSocket sslSocket = getSslSocket();
        if (sslSocket != null) {
            return sslSocket.getSession().getLocalPrincipal();
        }
        return null;
    }

    public final HttpEngine getHttpEngine() {
        return this.delegate.getHttpEngine();
    }

    private SSLSocket getSslSocket() {
        if (this.delegate.httpEngine == null || !this.delegate.httpEngine.connected) {
            throw new IllegalStateException("Connection has not yet been established");
        } else if (this.delegate.httpEngine instanceof HttpsEngine) {
            return ((HttpsEngine) this.delegate.httpEngine).getSslSocket();
        } else {
            return null;
        }
    }

    public final void disconnect() {
        this.delegate.disconnect();
    }

    public final InputStream getErrorStream() {
        return this.delegate.getErrorStream();
    }

    public final String getRequestMethod() {
        return this.delegate.getRequestMethod();
    }

    public final int getResponseCode() {
        return this.delegate.getResponseCode();
    }

    public final String getResponseMessage() {
        return this.delegate.getResponseMessage();
    }

    public final void setRequestMethod(String method) {
        this.delegate.setRequestMethod(method);
    }

    public final boolean usingProxy() {
        return this.delegate.usingProxy();
    }

    public final boolean getInstanceFollowRedirects() {
        return this.delegate.getInstanceFollowRedirects();
    }

    public final void setInstanceFollowRedirects(boolean followRedirects) {
        this.delegate.setInstanceFollowRedirects(followRedirects);
    }

    public final void connect() {
        this.connected = true;
        this.delegate.connect();
    }

    public final boolean getAllowUserInteraction() {
        return this.delegate.getAllowUserInteraction();
    }

    public final Object getContent() {
        return this.delegate.getContent();
    }

    public final Object getContent(Class[] types) {
        return this.delegate.getContent(types);
    }

    public final String getContentEncoding() {
        return this.delegate.getContentEncoding();
    }

    public final int getContentLength() {
        return this.delegate.getContentLength();
    }

    public final String getContentType() {
        return this.delegate.getContentType();
    }

    public final long getDate() {
        return this.delegate.getDate();
    }

    public final boolean getDefaultUseCaches() {
        return this.delegate.getDefaultUseCaches();
    }

    public final boolean getDoInput() {
        return this.delegate.getDoInput();
    }

    public final boolean getDoOutput() {
        return this.delegate.getDoOutput();
    }

    public final long getExpiration() {
        return this.delegate.getExpiration();
    }

    public final String getHeaderField(int pos) {
        return this.delegate.getHeaderField(pos);
    }

    public final Map<String, List<String>> getHeaderFields() {
        return this.delegate.getHeaderFields();
    }

    public final Map<String, List<String>> getRequestProperties() {
        return this.delegate.getRequestProperties();
    }

    public final void addRequestProperty(String field, String newValue) {
        this.delegate.addRequestProperty(field, newValue);
    }

    public final String getHeaderField(String key) {
        return this.delegate.getHeaderField(key);
    }

    public final long getHeaderFieldDate(String field, long defaultValue) {
        return this.delegate.getHeaderFieldDate(field, defaultValue);
    }

    public final int getHeaderFieldInt(String field, int defaultValue) {
        return this.delegate.getHeaderFieldInt(field, defaultValue);
    }

    public final String getHeaderFieldKey(int position) {
        return this.delegate.getHeaderFieldKey(position);
    }

    public final long getIfModifiedSince() {
        return this.delegate.getIfModifiedSince();
    }

    public final InputStream getInputStream() {
        return this.delegate.getInputStream();
    }

    public final long getLastModified() {
        return this.delegate.getLastModified();
    }

    public final OutputStream getOutputStream() {
        return this.delegate.getOutputStream();
    }

    public final Permission getPermission() {
        return this.delegate.getPermission();
    }

    public final String getRequestProperty(String field) {
        return this.delegate.getRequestProperty(field);
    }

    public final URL getURL() {
        return this.delegate.getURL();
    }

    public final boolean getUseCaches() {
        return this.delegate.getUseCaches();
    }

    public final void setAllowUserInteraction(boolean newValue) {
        this.delegate.setAllowUserInteraction(newValue);
    }

    public final void setDefaultUseCaches(boolean newValue) {
        this.delegate.setDefaultUseCaches(newValue);
    }

    public final void setDoInput(boolean newValue) {
        this.delegate.setDoInput(newValue);
    }

    public final void setDoOutput(boolean newValue) {
        this.delegate.setDoOutput(newValue);
    }

    public final void setIfModifiedSince(long newValue) {
        this.delegate.setIfModifiedSince(newValue);
    }

    public final void setRequestProperty(String field, String newValue) {
        this.delegate.setRequestProperty(field, newValue);
    }

    public final void setUseCaches(boolean newValue) {
        this.delegate.setUseCaches(newValue);
    }

    public final void setConnectTimeout(int timeoutMillis) {
        this.delegate.setConnectTimeout(timeoutMillis);
    }

    public final int getConnectTimeout() {
        return this.delegate.getConnectTimeout();
    }

    public final void setReadTimeout(int timeoutMillis) {
        this.delegate.setReadTimeout(timeoutMillis);
    }

    public final int getReadTimeout() {
        return this.delegate.getReadTimeout();
    }

    public final String toString() {
        return this.delegate.toString();
    }

    public final void setFixedLengthStreamingMode(int contentLength) {
        this.delegate.setFixedLengthStreamingMode(contentLength);
    }

    public final void setChunkedStreamingMode(int chunkLength) {
        this.delegate.setChunkedStreamingMode(chunkLength);
    }

    public final void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.delegate.client.setHostnameVerifier(hostnameVerifier);
    }

    public final HostnameVerifier getHostnameVerifier() {
        return this.delegate.client.getHostnameVerifier();
    }

    public final void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.delegate.client.setSslSocketFactory(sslSocketFactory);
    }

    public final SSLSocketFactory getSSLSocketFactory() {
        return this.delegate.client.getSslSocketFactory();
    }
}
