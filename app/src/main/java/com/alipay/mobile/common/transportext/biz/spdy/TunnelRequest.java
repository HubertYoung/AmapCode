package com.alipay.mobile.common.transportext.biz.spdy;

import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.RawHeaders;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;

public final class TunnelRequest {
    final String host;
    final int port;
    final String proxyAuthorization;
    final String userAgent;

    public TunnelRequest(String host2, int port2, String userAgent2, String proxyAuthorization2) {
        if (host2 == null) {
            throw new NullPointerException("host == null");
        } else if (userAgent2 == null) {
            throw new NullPointerException("userAgent == null");
        } else {
            this.host = host2;
            this.port = port2;
            this.userAgent = userAgent2;
            this.proxyAuthorization = proxyAuthorization2;
        }
    }

    /* access modifiers changed from: 0000 */
    public final RawHeaders getRequestHeaders() {
        RawHeaders result = new RawHeaders();
        result.setRequestLine("CONNECT " + this.host + ":" + this.port + " HTTP/1.1");
        result.set("Host", this.port == Util.getDefaultPort("https") ? this.host : this.host + ":" + this.port);
        result.set(H5AppHttpRequest.HEADER_UA, this.userAgent);
        if (this.proxyAuthorization != null) {
            result.set("Proxy-Authorization", this.proxyAuthorization);
        }
        result.set("Proxy-Connection", "Keep-Alive");
        return result;
    }
}
