package com.alipay.mobile.common.nbnet.biz.transport;

import java.net.Proxy;

public class Route {
    protected final String a;
    protected final int b;
    protected final Proxy c;

    public Route(String uriHost, int uriPort, Proxy proxy) {
        this.a = uriHost;
        this.b = uriPort;
        this.c = proxy;
    }

    public String a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public Proxy d() {
        return this.c;
    }
}
