package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.TunnelRequest;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import java.net.CacheResponse;
import java.net.SecureCacheResponse;
import java.net.URL;
import javax.net.ssl.SSLSocket;

public final class HttpsEngine extends HttpEngine {
    private SSLSocket sslSocket = null;

    public HttpsEngine(OkHttpClient client, Policy policy, String method, RawHeaders requestHeaders, Connection connection, RetryableOutputStream requestBody) {
        super(client, policy, method, requestHeaders, connection, requestBody);
    }

    /* access modifiers changed from: protected */
    public final void connected(Connection connection) {
        super.connected(connection);
    }

    /* access modifiers changed from: protected */
    public final boolean acceptCacheResponseType(CacheResponse cacheResponse) {
        return cacheResponse instanceof SecureCacheResponse;
    }

    /* access modifiers changed from: protected */
    public final boolean includeAuthorityInRequestLine() {
        return false;
    }

    public final SSLSocket getSslSocket() {
        return this.sslSocket;
    }

    /* access modifiers changed from: protected */
    public final TunnelRequest getTunnelConfig() {
        String userAgent = this.requestHeaders.getUserAgent();
        if (userAgent == null) {
            userAgent = getDefaultUserAgent();
        }
        URL url = this.policy.getURL();
        return new TunnelRequest(url.getHost(), Util.getEffectivePort(url), userAgent, this.requestHeaders.getProxyAuthorization());
    }
}
