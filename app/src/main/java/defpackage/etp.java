package defpackage;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/* renamed from: etp reason: default package */
/* compiled from: IgnoreHostProxySelector */
final class etp extends ProxySelector {
    private static final List<Proxy> a = Arrays.asList(new Proxy[]{Proxy.NO_PROXY});
    private final ProxySelector b;
    private final String c;
    private final int d;

    private etp(ProxySelector proxySelector, String str, int i) {
        this.b = (ProxySelector) etr.a(proxySelector);
        this.c = (String) etr.a(str);
        this.d = i;
    }

    static void a(String str, int i) {
        ProxySelector.setDefault(new etp(ProxySelector.getDefault(), str, i));
    }

    public final List<Proxy> select(URI uri) {
        return this.c.equals(uri.getHost()) && this.d == uri.getPort() ? a : this.b.select(uri);
    }

    public final void connectFailed(URI uri, SocketAddress socketAddress, IOException iOException) {
        this.b.connectFailed(uri, socketAddress, iOException);
    }
}
