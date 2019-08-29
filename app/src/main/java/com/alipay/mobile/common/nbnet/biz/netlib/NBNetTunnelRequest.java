package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.transport.http.HeaderMap;
import com.alipay.mobile.nebula.appcenter.openapi.H5AppHttpRequest;

public class NBNetTunnelRequest {
    final String a;
    final int b;
    final String c;

    public NBNetTunnelRequest(String host, int port, String userAgent) {
        if (host == null) {
            throw new IllegalArgumentException("host == null");
        }
        this.a = host;
        this.b = port;
        this.c = userAgent;
    }

    public final HeaderMap<String, String> a() {
        HeaderMap result = new HeaderMap();
        result.put("Host", this.a + ":" + this.b);
        result.put(H5AppHttpRequest.HEADER_UA, this.c);
        result.put("Proxy-Connection", "Keep-Alive");
        NBNetLogCat.f("TunnelRequest", result.toString());
        return result;
    }

    public final String b() {
        return "CONNECT " + this.a + ":" + this.b + " HTTP/1.1";
    }
}
