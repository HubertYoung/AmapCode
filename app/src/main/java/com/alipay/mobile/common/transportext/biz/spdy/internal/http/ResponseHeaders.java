package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.DataflowMonitorModel;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transportext.biz.spdy.ResponseSource;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Platform;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.HeaderParser.CacheControlHandler;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.autonavi.minimap.offline.auto.protocol.request.AutoDownloadLogRequest;
import java.net.URI;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public final class ResponseHeaders {
    private static final String RECEIVED_MILLIS = (Platform.get().getPrefix() + "-Received-Millis");
    static final String RESPONSE_SOURCE = (Platform.get().getPrefix() + "-Response-Source");
    static final String SELECTED_TRANSPORT = (Platform.get().getPrefix() + "-Selected-Transport");
    private static final String SENT_MILLIS = (Platform.get().getPrefix() + "-Sent-Millis");
    private int ageSeconds = -1;
    private String connection;
    private String contentEncoding;
    private int contentLength = -1;
    private String etag;
    private Date expires;
    private final RawHeaders headers;
    /* access modifiers changed from: private */
    public boolean isPublic;
    private Date lastModified;
    /* access modifiers changed from: private */
    public int maxAgeSeconds = -1;
    /* access modifiers changed from: private */
    public boolean mustRevalidate;
    /* access modifiers changed from: private */
    public boolean noCache;
    /* access modifiers changed from: private */
    public boolean noStore;
    private long receivedResponseMillis;
    /* access modifiers changed from: private */
    public int sMaxAgeSeconds = -1;
    private long sentRequestMillis;
    private Date servedDate;
    private String transferEncoding;
    private final URI uri;
    private Set<String> varyFields = Collections.emptySet();

    public ResponseHeaders(URI uri2, RawHeaders headers2) {
        this.uri = uri2;
        this.headers = headers2;
        CacheControlHandler handler = new CacheControlHandler() {
            public void handle(String directive, String parameter) {
                if ("no-cache".equalsIgnoreCase(directive)) {
                    ResponseHeaders.this.noCache = true;
                } else if ("no-store".equalsIgnoreCase(directive)) {
                    ResponseHeaders.this.noStore = true;
                } else if ("max-age".equalsIgnoreCase(directive)) {
                    ResponseHeaders.this.maxAgeSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("s-maxage".equalsIgnoreCase(directive)) {
                    ResponseHeaders.this.sMaxAgeSeconds = HeaderParser.parseSeconds(parameter);
                } else if ("public".equalsIgnoreCase(directive)) {
                    ResponseHeaders.this.isPublic = true;
                } else if ("must-revalidate".equalsIgnoreCase(directive)) {
                    ResponseHeaders.this.mustRevalidate = true;
                }
            }
        };
        for (int i = 0; i < headers2.length(); i++) {
            String fieldName = headers2.getFieldName(i);
            String value = headers2.getValue(i);
            if ("Cache-Control".equalsIgnoreCase(fieldName)) {
                HeaderParser.parseCacheControl(value, handler);
            } else if (AutoDownloadLogRequest.AUTO_KEY_DATE.equalsIgnoreCase(fieldName)) {
                this.servedDate = HttpDate.parse(value);
            } else if ("Expires".equalsIgnoreCase(fieldName)) {
                this.expires = HttpDate.parse(value);
            } else if ("Last-Modified".equalsIgnoreCase(fieldName)) {
                this.lastModified = HttpDate.parse(value);
            } else if ("ETag".equalsIgnoreCase(fieldName)) {
                this.etag = value;
            } else if ("Pragma".equalsIgnoreCase(fieldName)) {
                if ("no-cache".equalsIgnoreCase(value)) {
                    this.noCache = true;
                }
            } else if ("Age".equalsIgnoreCase(fieldName)) {
                this.ageSeconds = HeaderParser.parseSeconds(value);
            } else if ("Vary".equalsIgnoreCase(fieldName)) {
                if (this.varyFields.isEmpty()) {
                    this.varyFields = new TreeSet(String.CASE_INSENSITIVE_ORDER);
                }
                for (String varyField : value.split(",")) {
                    this.varyFields.add(varyField.trim());
                }
            } else if (TransportConstants.KEY_X_CONTENT_ENCODING.equalsIgnoreCase(fieldName)) {
                this.contentEncoding = value;
            } else if ("Transfer-Encoding".equalsIgnoreCase(fieldName)) {
                this.transferEncoding = value;
            } else if ("Content-Length".equalsIgnoreCase(fieldName)) {
                try {
                    this.contentLength = Integer.parseInt(value);
                } catch (NumberFormatException ignored) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "parseContentLength(" + value + ") exception : " + ignored.toString());
                }
            } else if (H5AppHttpRequest.HEADER_CONNECTION.equalsIgnoreCase(fieldName)) {
                this.connection = value;
            } else if (SENT_MILLIS.equalsIgnoreCase(fieldName)) {
                this.sentRequestMillis = Long.parseLong(value);
            } else if (RECEIVED_MILLIS.equalsIgnoreCase(fieldName)) {
                this.receivedResponseMillis = Long.parseLong(value);
            }
        }
    }

    public final boolean isContentEncodingGzip() {
        return "gzip".equalsIgnoreCase(this.contentEncoding);
    }

    public final void stripContentEncoding() {
        this.contentEncoding = null;
        this.headers.removeAll(TransportConstants.KEY_X_CONTENT_ENCODING);
    }

    public final void stripContentLength() {
        this.contentLength = -1;
        this.headers.removeAll("Content-Length");
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

    public final Date getServedDate() {
        return this.servedDate;
    }

    public final Date getLastModified() {
        return this.lastModified;
    }

    public final Date getExpires() {
        return this.expires;
    }

    public final boolean isNoCache() {
        return this.noCache;
    }

    public final boolean isNoStore() {
        return this.noStore;
    }

    public final int getMaxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public final int getSMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public final boolean isPublic() {
        return this.isPublic;
    }

    public final boolean isMustRevalidate() {
        return this.mustRevalidate;
    }

    public final String getEtag() {
        return this.etag;
    }

    public final Set<String> getVaryFields() {
        return this.varyFields;
    }

    public final String getContentEncoding() {
        return this.contentEncoding;
    }

    public final int getContentLength() {
        return this.contentLength;
    }

    public final String getConnection() {
        return this.connection;
    }

    public final void setLocalTimestamps(long sentRequestMillis2, long receivedResponseMillis2) {
        this.sentRequestMillis = sentRequestMillis2;
        this.headers.add(SENT_MILLIS, Long.toString(sentRequestMillis2));
        this.receivedResponseMillis = receivedResponseMillis2;
        this.headers.add(RECEIVED_MILLIS, Long.toString(receivedResponseMillis2));
    }

    public final void setResponseSource(ResponseSource responseSource) {
        this.headers.set(RESPONSE_SOURCE, responseSource.toString() + Token.SEPARATOR + this.headers.getResponseCode());
    }

    public final void setTransport(String transport) {
        this.headers.set(SELECTED_TRANSPORT, transport);
    }

    private long computeAge(long nowMillis) {
        long receivedAge;
        long apparentReceivedAge = 0;
        if (this.servedDate != null) {
            apparentReceivedAge = Math.max(0, this.receivedResponseMillis - this.servedDate.getTime());
        }
        if (this.ageSeconds != -1) {
            receivedAge = Math.max(apparentReceivedAge, TimeUnit.SECONDS.toMillis((long) this.ageSeconds));
        } else {
            receivedAge = apparentReceivedAge;
        }
        return receivedAge + (this.receivedResponseMillis - this.sentRequestMillis) + (nowMillis - this.receivedResponseMillis);
    }

    private long computeFreshnessLifetime() {
        if (this.maxAgeSeconds != -1) {
            return TimeUnit.SECONDS.toMillis((long) this.maxAgeSeconds);
        }
        if (this.expires != null) {
            long delta = this.expires.getTime() - (this.servedDate != null ? this.servedDate.getTime() : this.receivedResponseMillis);
            if (delta <= 0) {
                return 0;
            }
            return delta;
        } else if (this.lastModified == null || this.uri.getRawQuery() != null) {
            return 0;
        } else {
            long delta2 = (this.servedDate != null ? this.servedDate.getTime() : this.sentRequestMillis) - this.lastModified.getTime();
            if (delta2 > 0) {
                return delta2 / 10;
            }
            return 0;
        }
    }

    private boolean isFreshnessLifetimeHeuristic() {
        return this.maxAgeSeconds == -1 && this.expires == null;
    }

    public final boolean isCacheable(RequestHeaders request) {
        int responseCode = this.headers.getResponseCode();
        if (responseCode != 200 && responseCode != 203 && responseCode != 300 && responseCode != 301 && responseCode != 410) {
            return false;
        }
        if ((!request.hasAuthorization() || this.isPublic || this.mustRevalidate || this.sMaxAgeSeconds != -1) && !this.noStore) {
            return true;
        }
        return false;
    }

    public final boolean hasVaryAll() {
        return this.varyFields.contains("*");
    }

    public final boolean varyMatches(Map<String, List<String>> cachedRequest, Map<String, List<String>> newRequest) {
        for (String field : this.varyFields) {
            if (!Util.equal(cachedRequest.get(field), newRequest.get(field))) {
                return false;
            }
        }
        return true;
    }

    public final ResponseSource chooseResponseSource(long nowMillis, RequestHeaders request) {
        if (!isCacheable(request)) {
            return ResponseSource.NETWORK;
        }
        if (request.isNoCache() || request.hasConditions()) {
            return ResponseSource.NETWORK;
        }
        long ageMillis = computeAge(nowMillis);
        long freshMillis = computeFreshnessLifetime();
        if (request.getMaxAgeSeconds() != -1) {
            freshMillis = Math.min(freshMillis, TimeUnit.SECONDS.toMillis((long) request.getMaxAgeSeconds()));
        }
        long minFreshMillis = 0;
        if (request.getMinFreshSeconds() != -1) {
            minFreshMillis = TimeUnit.SECONDS.toMillis((long) request.getMinFreshSeconds());
        }
        long maxStaleMillis = 0;
        if (!this.mustRevalidate && request.getMaxStaleSeconds() != -1) {
            maxStaleMillis = TimeUnit.SECONDS.toMillis((long) request.getMaxStaleSeconds());
        }
        if (this.noCache || ageMillis + minFreshMillis >= freshMillis + maxStaleMillis) {
            if (this.lastModified != null) {
                request.setIfModifiedSince(this.lastModified);
            } else if (this.servedDate != null) {
                request.setIfModifiedSince(this.servedDate);
            }
            if (this.etag != null) {
                request.setIfNoneMatch(this.etag);
            }
            if (request.hasConditions()) {
                return ResponseSource.CONDITIONAL_CACHE;
            }
            return ResponseSource.NETWORK;
        }
        if (ageMillis + minFreshMillis >= freshMillis) {
            this.headers.add("Warning", "110 HttpURLConnection \"Response is stale\"");
        }
        if (ageMillis > 86400000 && isFreshnessLifetimeHeuristic()) {
            this.headers.add("Warning", "113 HttpURLConnection \"Heuristic expiration\"");
        }
        return ResponseSource.CACHE;
    }

    public final boolean validate(ResponseHeaders networkResponse) {
        if (networkResponse.headers.getResponseCode() == 304) {
            return true;
        }
        if (this.lastModified == null || networkResponse.lastModified == null || networkResponse.lastModified.getTime() >= this.lastModified.getTime()) {
            return false;
        }
        return true;
    }

    public final ResponseHeaders combine(ResponseHeaders network) {
        RawHeaders result = new RawHeaders();
        result.setStatusLine(this.headers.getStatusLine());
        for (int i = 0; i < this.headers.length(); i++) {
            String fieldName = this.headers.getFieldName(i);
            String value = this.headers.getValue(i);
            if ((!TextUtils.equals("Warning", fieldName) || value == null || !value.startsWith("1")) && (!isEndToEnd(fieldName) || network.headers.get(fieldName) == null)) {
                result.add(fieldName, value);
            }
        }
        for (int i2 = 0; i2 < network.headers.length(); i2++) {
            String fieldName2 = network.headers.getFieldName(i2);
            if (isEndToEnd(fieldName2)) {
                result.add(fieldName2, network.headers.getValue(i2));
            }
        }
        return new ResponseHeaders(this.uri, result);
    }

    private static boolean isEndToEnd(String fieldName) {
        return !H5AppHttpRequest.HEADER_CONNECTION.equalsIgnoreCase(fieldName) && !"Keep-Alive".equalsIgnoreCase(fieldName) && !"Proxy-Authenticate".equalsIgnoreCase(fieldName) && !"Proxy-Authorization".equalsIgnoreCase(fieldName) && !"TE".equalsIgnoreCase(fieldName) && !"Trailers".equalsIgnoreCase(fieldName) && !"Transfer-Encoding".equalsIgnoreCase(fieldName) && !"Upgrade".equalsIgnoreCase(fieldName);
    }
}
