package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HeaderParser.CacheControlHandler;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class RequestHeaders {
    private String acceptEncoding;
    private String connection;
    private long contentLength = -1;
    private String contentType;
    private boolean hasAuthorization;
    private final RawHeaders headers;
    private String host;
    private String ifModifiedSince;
    private String ifNoneMatch;
    /* access modifiers changed from: private */
    public int maxAgeSeconds = -1;
    /* access modifiers changed from: private */
    public int maxStaleSeconds = -1;
    /* access modifiers changed from: private */
    public int minFreshSeconds = -1;
    /* access modifiers changed from: private */
    public boolean noCache;
    /* access modifiers changed from: private */
    public boolean onlyIfCached;
    private String proxyAuthorization;
    private String transferEncoding;
    private final URI uri;
    private String userAgent;

    public RequestHeaders(URI uri2, RawHeaders headers2) {
        this.uri = uri2;
        this.headers = headers2;
        CacheControlHandler handler = new CacheControlHandler() {
            public void handle(String directive, String parameter) {
                if ("no-cache".equalsIgnoreCase(directive)) {
                    RequestHeaders.this.noCache = true;
                } else if ("max-age".equalsIgnoreCase(directive)) {
                    RequestHeaders.this.maxAgeSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("max-stale".equalsIgnoreCase(directive)) {
                    RequestHeaders.this.maxStaleSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("min-fresh".equalsIgnoreCase(directive)) {
                    RequestHeaders.this.minFreshSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("only-if-cached".equalsIgnoreCase(directive)) {
                    RequestHeaders.this.onlyIfCached = true;
                }
            }
        };
        for (int i = 0; i < headers2.length(); i++) {
            String fieldName = headers2.getFieldName(i);
            String value = headers2.getValue(i);
            if ("Cache-Control".equalsIgnoreCase(fieldName)) {
                HeaderParser.parseCacheControl(value, handler);
            } else if ("Pragma".equalsIgnoreCase(fieldName)) {
                if ("no-cache".equalsIgnoreCase(value)) {
                    this.noCache = true;
                }
            } else if ("If-None-Match".equalsIgnoreCase(fieldName)) {
                this.ifNoneMatch = value;
            } else if ("If-Modified-Since".equalsIgnoreCase(fieldName)) {
                this.ifModifiedSince = value;
            } else if ("Authorization".equalsIgnoreCase(fieldName)) {
                this.hasAuthorization = true;
            } else if ("Content-Length".equalsIgnoreCase(fieldName)) {
                try {
                    this.contentLength = (long) Integer.parseInt(value);
                } catch (NumberFormatException ignored) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "parseInt(" + value + ") exception : " + ignored.toString());
                }
            } else if ("Transfer-Encoding".equalsIgnoreCase(fieldName)) {
                this.transferEncoding = value;
            } else if (H5AppHttpRequest.HEADER_UA.equalsIgnoreCase(fieldName)) {
                LoggerFactory.getTraceLogger().debug(InnerLogUtil.MWALLET_SPDY_TAG, "初始httpEngine,设置UA：" + fieldName);
                this.userAgent = value;
            } else if ("Host".equalsIgnoreCase(fieldName)) {
                this.host = value;
            } else if (H5AppHttpRequest.HEADER_CONNECTION.equalsIgnoreCase(fieldName)) {
                this.connection = value;
            } else if ("Accept-Encoding".equalsIgnoreCase(fieldName)) {
                this.acceptEncoding = value;
            } else if ("Content-Type".equalsIgnoreCase(fieldName)) {
                this.contentType = value;
            } else if ("Proxy-Authorization".equalsIgnoreCase(fieldName)) {
                this.proxyAuthorization = value;
            }
        }
    }

    public final boolean isChunked() {
        return "chunked".equalsIgnoreCase(this.transferEncoding);
    }

    public final boolean hasConnectionClose() {
        return DataflowMonitorModel.METHOD_NAME_CLOSE.equalsIgnoreCase(this.connection);
    }

    public final URI getUri() {
        return this.uri;
    }

    public final RawHeaders getHeaders() {
        return this.headers;
    }

    public final boolean isNoCache() {
        return this.noCache;
    }

    public final int getMaxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public final int getMaxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public final int getMinFreshSeconds() {
        return this.minFreshSeconds;
    }

    public final boolean isOnlyIfCached() {
        return this.onlyIfCached;
    }

    public final boolean hasAuthorization() {
        return this.hasAuthorization;
    }

    public final long getContentLength() {
        return this.contentLength;
    }

    public final String getTransferEncoding() {
        return this.transferEncoding;
    }

    public final String getUserAgent() {
        return this.userAgent;
    }

    public final String getHost() {
        return this.host;
    }

    public final String getConnection() {
        return this.connection;
    }

    public final String getAcceptEncoding() {
        return this.acceptEncoding;
    }

    public final String getContentType() {
        return this.contentType;
    }

    public final String getIfModifiedSince() {
        return this.ifModifiedSince;
    }

    public final String getIfNoneMatch() {
        return this.ifNoneMatch;
    }

    public final String getProxyAuthorization() {
        return this.proxyAuthorization;
    }

    public final void setChunked() {
        if (this.transferEncoding != null) {
            this.headers.removeAll("Transfer-Encoding");
        }
        this.headers.add("Transfer-Encoding", "chunked");
        this.transferEncoding = "chunked";
    }

    public final void setContentLength(long contentLength2) {
        if (this.contentLength != -1) {
            this.headers.removeAll("Content-Length");
        }
        this.headers.add("Content-Length", Long.toString(contentLength2));
        this.contentLength = contentLength2;
    }

    public final void removeContentLength() {
        if (this.contentLength != -1) {
            this.headers.removeAll("Content-Length");
            this.contentLength = -1;
        }
    }

    public final void setUserAgent(String userAgent2) {
        if (this.userAgent != null) {
            this.headers.removeAll(H5AppHttpRequest.HEADER_UA);
        }
        this.headers.add(H5AppHttpRequest.HEADER_UA, userAgent2);
        this.userAgent = userAgent2;
    }

    public final void setHost(String host2) {
        if (this.host != null) {
            this.headers.removeAll("Host");
        }
        this.headers.add("Host", host2);
        this.host = host2;
    }

    public final void setConnection(String connection2) {
        if (this.connection != null) {
            this.headers.removeAll(H5AppHttpRequest.HEADER_CONNECTION);
        }
        this.headers.add(H5AppHttpRequest.HEADER_CONNECTION, connection2);
        this.connection = connection2;
    }

    public final void setAcceptEncoding(String acceptEncoding2) {
        if (this.acceptEncoding != null) {
            this.headers.removeAll("Accept-Encoding");
        }
        this.headers.add("Accept-Encoding", acceptEncoding2);
        this.acceptEncoding = acceptEncoding2;
    }

    public final void setContentType(String contentType2) {
        if (this.contentType != null) {
            this.headers.removeAll("Content-Type");
        }
        this.headers.add("Content-Type", contentType2);
        this.contentType = contentType2;
    }

    public final void setIfModifiedSince(Date date) {
        if (this.ifModifiedSince != null) {
            this.headers.removeAll("If-Modified-Since");
        }
        String formattedDate = HttpDate.format(date);
        this.headers.add("If-Modified-Since", formattedDate);
        this.ifModifiedSince = formattedDate;
    }

    public final void setIfNoneMatch(String ifNoneMatch2) {
        if (this.ifNoneMatch != null) {
            this.headers.removeAll("If-None-Match");
        }
        this.headers.add("If-None-Match", ifNoneMatch2);
        this.ifNoneMatch = ifNoneMatch2;
    }

    public final boolean hasConditions() {
        return (this.ifModifiedSince == null && this.ifNoneMatch == null) ? false : true;
    }

    public final void addCookies(Map<String, List<String>> allCookieHeaders) {
        for (Entry entry : allCookieHeaders.entrySet()) {
            String key = (String) entry.getKey();
            if (("Cookie".equalsIgnoreCase(key) || "Cookie2".equalsIgnoreCase(key)) && !((List) entry.getValue()).isEmpty()) {
                this.headers.add(key, buildCookieHeader((List) entry.getValue()));
            }
        }
    }

    private String buildCookieHeader(List<String> cookies) {
        if (cookies.size() == 1) {
            return cookies.get(0);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cookies.size(); i++) {
            if (i > 0) {
                sb.append("; ");
            }
            sb.append(cookies.get(i));
        }
        return sb.toString();
    }
}
