package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.transport.Route;
import com.alipay.mobile.common.nbnet.biz.util.NBNetCommonUtil;
import java.net.InetSocketAddress;
import java.net.Proxy;
import javax.net.ssl.SSLSocketFactory;

public class NBNetRoute extends Route {
    private final byte d;
    private final SSLSocketFactory e;
    private InetSocketAddress f;
    private boolean g;

    public NBNetRoute(byte transportType, String uriHost, int uriPort, Proxy proxy, SSLSocketFactory sslSocketFactory) {
        super(uriHost, uriPort, proxy);
        this.e = sslSocketFactory;
        this.d = transportType;
    }

    public final String a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public final SSLSocketFactory c() {
        return this.e;
    }

    public final Proxy d() {
        return this.c;
    }

    public final InetSocketAddress e() {
        return this.f;
    }

    public final void a(InetSocketAddress inetSocketAddress) {
        this.f = inetSocketAddress;
    }

    public final boolean a(NBNetRoute nbNetRoute) {
        if (nbNetRoute == null || !NBNetCommonUtil.a(this.a, nbNetRoute.a) || this.b != nbNetRoute.b || !NBNetCommonUtil.a((Object) c(), (Object) nbNetRoute.c())) {
            return false;
        }
        if (!NBNetworkUtil.a(d(), nbNetRoute.d())) {
            StringBuilder logBuilder = new StringBuilder("equalProxy is false. ");
            if (nbNetRoute.d() != null) {
                logBuilder.append("new proxy: " + nbNetRoute.d().toString());
            } else {
                logBuilder.append("new proxy: null");
            }
            NBNetLogCat.a((String) "NBNetRoute", logBuilder.toString());
        }
        if (this.d == nbNetRoute.d) {
            return true;
        }
        return false;
    }

    public final boolean f() {
        return this.g;
    }

    public final void g() {
        this.g = true;
    }
}
