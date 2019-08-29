package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import android.text.TextUtils;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.ErrorCode;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyConnection;
import com.alipay.mobile.common.transportext.biz.spdy.internal.spdy.SpdyStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.URISyntaxException;
import java.net.URL;

public final class SpdyTransport implements Transport {
    private final HttpEngine httpEngine;
    private final SpdyConnection spdyConnection;
    private SpdyStream stream;

    public SpdyTransport(HttpEngine httpEngine2, SpdyConnection spdyConnection2) {
        this.httpEngine = httpEngine2;
        this.spdyConnection = spdyConnection2;
    }

    public final OutputStream createRequestBody() {
        long fixedContentLength = this.httpEngine.policy.getFixedContentLength();
        if (fixedContentLength != -1) {
            this.httpEngine.requestHeaders.setContentLength(fixedContentLength);
        }
        writeRequestHeaders();
        return this.stream.getOutputStream();
    }

    public final void writeRequestHeaders() {
        if (this.stream == null) {
            this.httpEngine.writingRequestHeaders();
            RawHeaders requestHeaders = this.httpEngine.requestHeaders.getHeaders();
            String version = this.httpEngine.connection.getHttpMinorVersion() == 1 ? "HTTP/1.1" : "HTTP/1.0";
            String spdyProxyUrlStr = requestHeaders.get("spdy-proxy-url");
            if (!TextUtils.isEmpty(spdyProxyUrlStr)) {
                URL url = new URL(spdyProxyUrlStr);
                try {
                    requestHeaders.addSpdyRequestHeaders(this.httpEngine.method, HttpEngine.requestPath(url), version, HttpEngine.getOriginAddress(url), url.toURI().getScheme());
                } catch (URISyntaxException e) {
                    throw new IOException(e.getMessage(), e);
                }
            } else {
                URL url2 = this.httpEngine.policy.getURL();
                requestHeaders.addSpdyRequestHeaders(this.httpEngine.method, HttpEngine.requestPath(url2), version, HttpEngine.getOriginAddress(url2), this.httpEngine.uri.getScheme());
            }
            this.stream = this.spdyConnection.newStream(requestHeaders.toNameValueBlock(), this.httpEngine.hasRequestBody(), true, this.httpEngine.getNetContext());
            this.stream.setReadTimeout((long) this.httpEngine.client.getStreamReadTimeout());
        }
    }

    public final void writeRequestBody(RetryableOutputStream requestBody) {
        throw new UnsupportedOperationException();
    }

    public final void flushRequest() {
        if (this.stream != null) {
            this.stream.getOutputStream().close();
        }
    }

    public final ResponseHeaders readResponseHeaders() {
        RawHeaders rawHeaders = RawHeaders.fromNameValueBlock(this.stream.getResponseHeaders());
        this.httpEngine.receiveHeaders(rawHeaders);
        ResponseHeaders headers = new ResponseHeaders(this.httpEngine.uri, rawHeaders);
        headers.setTransport("spdy/3");
        return headers;
    }

    public final InputStream getTransferStream(CacheRequest cacheRequest) {
        return new UnknownLengthHttpInputStream(this.stream.getInputStream(), cacheRequest, this.httpEngine);
    }

    public final boolean makeReusable(boolean streamCanceled, OutputStream requestBodyOut, InputStream responseBodyIn) {
        if (!streamCanceled) {
            return true;
        }
        if (this.stream == null) {
            return false;
        }
        this.stream.closeLater(ErrorCode.CANCEL);
        return true;
    }
}
