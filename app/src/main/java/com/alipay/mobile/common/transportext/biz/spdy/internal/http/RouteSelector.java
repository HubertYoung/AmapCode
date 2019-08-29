package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.httpdns.DnsUtil;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.Address;
import com.alipay.mobile.common.transportext.biz.spdy.Connection;
import com.alipay.mobile.common.transportext.biz.spdy.ConnectionPool;
import com.alipay.mobile.common.transportext.biz.spdy.Route;
import com.alipay.mobile.common.transportext.biz.spdy.RouteDatabase;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Dns;
import com.alipay.mobile.common.transportext.biz.spdy.internal.Util;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public final class RouteSelector {
    private static final int TLS_MODE_COMPATIBLE = 0;
    private static final int TLS_MODE_MODERN = 1;
    private static final int TLS_MODE_NULL = -1;
    private final Address address;
    private final Dns dns;
    private boolean hasNextProxy;
    private InetSocketAddress lastInetSocketAddress;
    private Proxy lastProxy;
    private TransportContext netContext;
    private int nextSocketAddressIndex;
    private int nextTlsMode = -1;
    private final ConnectionPool pool;
    private final List<Route> postponedRoutes;
    private final ProxySelector proxySelector;
    private Iterator<Proxy> proxySelectorProxies;
    private final RouteDatabase routeDatabase;
    private String selectorID;
    private InetAddress[] socketAddresses;
    private int socketPort;
    private final URI uri;
    private Proxy userSpecifiedProxy;

    public RouteSelector(Address address2, URI uri2, ProxySelector proxySelector2, ConnectionPool pool2, Dns dns2, RouteDatabase routeDatabase2, TransportContext netContext2) {
        this.address = address2;
        this.uri = uri2;
        this.proxySelector = proxySelector2;
        this.pool = pool2;
        this.dns = dns2;
        this.routeDatabase = routeDatabase2;
        this.postponedRoutes = new LinkedList();
        this.selectorID = UUID.randomUUID().toString();
        this.netContext = netContext2;
        resetNextProxy(uri2, address2.getProxy());
    }

    public final boolean hasNext() {
        return hasNextTlsMode() || hasNextInetSocketAddress() || hasNextProxy() || hasNextPostponed();
    }

    public final Connection next(String method) {
        boolean modernTls = true;
        LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "当前pool中的连接数量：" + this.pool.getConnectionCount());
        while (true) {
            Connection pooled = this.pool.get(this.address);
            if (pooled == null) {
                if (this.pool.getConnectionCount() > 0) {
                    LogCatUtil.warn((String) InnerLogUtil.MWALLET_SPDY_TAG, "报警: 即将创建第大于1个的Connection对象。当前pool中的连接数量：" + this.pool.getConnectionCount());
                }
                if (!hasNextTlsMode()) {
                    if (!hasNextInetSocketAddress()) {
                        if (hasNextProxy()) {
                            this.lastProxy = nextProxy();
                            resetNextInetSocketAddress(this.lastProxy);
                        } else if (hasNextPostponed()) {
                            return new Connection(nextPostponed());
                        } else {
                            throw new NoSuchElementException();
                        }
                    }
                    this.lastInetSocketAddress = nextInetSocketAddress();
                    resetNextTlsMode();
                }
                if (nextTlsMode() != 1) {
                    modernTls = false;
                }
                Route route = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, modernTls);
                if (!this.routeDatabase.shouldPostpone(route)) {
                    return new Connection(route);
                }
                this.postponedRoutes.add(route);
                return next(method);
            } else if (TextUtils.equals(method, "GET") || pooled.isReadable()) {
                return pooled;
            } else {
                pooled.close();
            }
        }
    }

    public final void connectFailed(Connection connection, IOException failure) {
        Route failedRoute = connection.getRoute();
        if (!(failedRoute.getProxy().type() == Type.DIRECT || this.proxySelector == null)) {
            this.proxySelector.connectFailed(this.uri, failedRoute.getProxy().address(), failure);
        }
        this.routeDatabase.failed(failedRoute, failure);
    }

    private void resetNextProxy(URI uri2, Proxy proxy) {
        this.hasNextProxy = true;
        if (proxy == null) {
            List proxyList = this.proxySelector.select(uri2);
            if (proxyList != null) {
                this.proxySelectorProxies = proxyList.iterator();
            }
        } else if (proxy.type() == Proxy.NO_PROXY.type()) {
            this.userSpecifiedProxy = proxy;
        } else {
            List proxyList2 = new ArrayList();
            LogCatUtil.info(InnerLogUtil.MWALLET_SPDY_TAG, "RouteSelector First no proxy direct strategy");
            proxyList2.add(Proxy.NO_PROXY);
            proxyList2.add(proxy);
            this.proxySelectorProxies = proxyList2.iterator();
        }
    }

    private boolean hasNextProxy() {
        return this.hasNextProxy;
    }

    private Proxy nextProxy() {
        if (this.userSpecifiedProxy != null) {
            this.hasNextProxy = false;
            return this.userSpecifiedProxy;
        } else if (this.proxySelectorProxies != null && this.proxySelectorProxies.hasNext()) {
            return this.proxySelectorProxies.next();
        } else {
            this.hasNextProxy = false;
            return Proxy.NO_PROXY;
        }
    }

    private void resetNextInetSocketAddress(Proxy proxy) {
        String socketHost;
        this.socketAddresses = null;
        if (proxy.type() == Type.DIRECT) {
            socketHost = this.uri.getHost();
            this.socketPort = Util.getEffectivePort(this.uri);
        } else {
            SocketAddress proxyAddress = proxy.address();
            if (!(proxyAddress instanceof InetSocketAddress)) {
                throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass());
            }
            InetSocketAddress proxySocketAddress = (InetSocketAddress) proxyAddress;
            socketHost = proxySocketAddress.getHostName();
            this.socketPort = proxySocketAddress.getPort();
        }
        setSocketAddresses(socketHost);
        this.nextSocketAddressIndex = 0;
    }

    private void setSocketAddresses(String socketHost) {
        if (this.netContext != null) {
            this.netContext.getCurrentDataContainer().timeItemDot("DNS_TIME");
        }
        if (DnsUtil.isLogicIP(socketHost)) {
            this.socketAddresses = new InetAddress[]{InetAddress.getByAddress(DnsUtil.ipToBytesByReg(socketHost))};
        } else {
            this.socketAddresses = this.dns.getAllByName(socketHost);
        }
        if (this.netContext != null) {
            this.netContext.getCurrentDataContainer().timeItemRelease("DNS_TIME");
        }
    }

    private boolean hasNextInetSocketAddress() {
        return this.socketAddresses != null;
    }

    private InetSocketAddress nextInetSocketAddress() {
        InetAddress[] inetAddressArr = this.socketAddresses;
        int i = this.nextSocketAddressIndex;
        this.nextSocketAddressIndex = i + 1;
        InetSocketAddress result = new InetSocketAddress(inetAddressArr[i], this.socketPort);
        if (this.nextSocketAddressIndex == this.socketAddresses.length) {
            this.socketAddresses = null;
            this.nextSocketAddressIndex = 0;
        }
        return result;
    }

    private void resetNextTlsMode() {
        this.nextTlsMode = this.address.getSslSocketFactory() != null ? 1 : 0;
    }

    private boolean hasNextTlsMode() {
        return this.nextTlsMode != -1;
    }

    private int nextTlsMode() {
        if (this.nextTlsMode == 1) {
            this.nextTlsMode = 0;
            return 1;
        } else if (this.nextTlsMode == 0) {
            this.nextTlsMode = -1;
            return 0;
        } else {
            throw new AssertionError();
        }
    }

    private boolean hasNextPostponed() {
        return !this.postponedRoutes.isEmpty();
    }

    private Route nextPostponed() {
        return this.postponedRoutes.remove(0);
    }

    public final String getSelectorID() {
        return this.selectorID;
    }

    public final InetSocketAddress getLastInetSocketAddress() {
        return this.lastInetSocketAddress;
    }
}
