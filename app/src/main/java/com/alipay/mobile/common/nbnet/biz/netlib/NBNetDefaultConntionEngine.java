package com.alipay.mobile.common.nbnet.biz.netlib;

import android.support.annotation.Nullable;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import com.alipay.mobile.common.transport.TransportStrategy;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.iprank.AlipayDNSHelper;
import com.alipay.mobile.common.transport.utils.TransportContextThreadLocalUtils;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;

public class NBNetDefaultConntionEngine implements NBNetConntionEngine {
    private static NBNetDefaultConntionEngine a;
    private boolean b = false;

    public static final NBNetConntionEngine a() {
        if (a != null) {
            return a;
        }
        synchronized (NBNetConntionEngine.class) {
            try {
                if (a != null) {
                    NBNetDefaultConntionEngine nBNetDefaultConntionEngine = a;
                    return nBNetDefaultConntionEngine;
                }
                a = new NBNetDefaultConntionEngine();
                return a;
            }
        }
    }

    public final NBNetConnection a(NBNetRoute nbNetRoute, NBNetContext nbNetContext) {
        InetAddress[] inetAddresses;
        int i;
        if (b()) {
            return a(nbNetRoute, nbNetContext, nbNetRoute.d());
        }
        TransportContext transportContext = new TransportContext();
        TransportStrategy.fillCurrentReqInfo(true, "nbnet", transportContext);
        TransportContextThreadLocalUtils.setValue(transportContext);
        try {
            inetAddresses = b(nbNetRoute, nbNetContext);
            i = 0;
            while (true) {
                MonitorLogUtil.c(nbNetContext, false);
                NBNetConnection a2 = a(nbNetRoute, nbNetContext, inetAddresses);
                a(nbNetRoute, i);
                return a2;
            }
        } catch (IOException e) {
            if (i > 2) {
                throw e;
            }
            Proxy proxy = nbNetRoute.d();
            if (proxy != Proxy.NO_PROXY) {
                return a(nbNetRoute, nbNetContext, proxy);
            }
            if (TransportContextThreadLocalUtils.isFromHttpDns()) {
                InetAddress[] diffIps = NBNetDnsHelper.b(nbNetRoute.a(), inetAddresses);
                if (diffIps != null) {
                    inetAddresses = diffIps;
                    NBNetLogCat.a((String) "NBNetConntionEngine", "Retry connection use iprank. ip count:" + inetAddresses.length);
                } else {
                    throw e;
                }
            } else {
                if (!TransportContextThreadLocalUtils.isFromIpRank()) {
                    break;
                }
                InetAddress[] diffIps2 = NBNetDnsHelper.a(nbNetRoute.a(), inetAddresses);
                if (diffIps2 == null) {
                    break;
                }
                inetAddresses = diffIps2;
                NBNetLogCat.a((String) "NBNetConntionEngine", "Retry connection use system. ip count:" + inetAddresses.length);
                throw e;
            }
            i++;
        } finally {
            TransportContextThreadLocalUtils.setValue(null);
        }
        throw e;
    }

    private static void a(NBNetRoute nbNetRoute, int retryCount) {
        if (retryCount <= 0) {
            return;
        }
        if (TransportContextThreadLocalUtils.isFromIpRank()) {
            NBNetDnsHelper.b(nbNetRoute.a());
        } else if (TransportContextThreadLocalUtils.isFromLocalDns()) {
            AlipayDNSHelper.getInstance().removeIpsInIpRank(nbNetRoute.a());
        }
    }

    @Nullable
    private static NBNetConnection a(NBNetRoute nbNetRoute, NBNetContext nbNetContext, InetAddress[] inetAddresses) {
        int maxRetryCount = Math.max(5, inetAddresses.length);
        int i = 0;
        int indexAddr = 0;
        while (i < maxRetryCount) {
            try {
                if (indexAddr >= inetAddresses.length) {
                    indexAddr = 0;
                }
                return a(nbNetRoute, nbNetContext, inetAddresses[indexAddr]);
            } catch (IOException e) {
                NBNetLogCat.d("NBNetConntionEngine", "Connect fail. retryCount:" + i + "/" + maxRetryCount + ", exception:" + e.toString());
                if (i + 1 == maxRetryCount) {
                    throw e;
                }
                i++;
                indexAddr++;
            }
        }
        return null;
    }

    private static NBNetConnection a(NBNetRoute nbNetRoute, NBNetContext nbNetContext, InetAddress inetAddress) {
        return a(nbNetRoute, nbNetContext, new InetSocketAddress(inetAddress, nbNetRoute.b()));
    }

    private static NBNetConnection a(NBNetRoute nbNetRoute, NBNetContext nbNetContext, InetSocketAddress inetSocketAddress) {
        nbNetRoute.a(inetSocketAddress);
        NBNetConnection nbNetConnection = new NBNetConnection(nbNetRoute);
        nbNetConnection.a(NBNetConfigUtil.a(), NBNetConfigUtil.b(), nbNetContext);
        return nbNetConnection;
    }

    private static InetAddress[] b(NBNetRoute nbNetRoute, NBNetContext nbNetContext) {
        long startTime = System.currentTimeMillis();
        try {
            return NBNetDnsHelper.a(nbNetRoute);
        } finally {
            MonitorLogUtil.c(nbNetContext, System.currentTimeMillis() - startTime);
        }
    }

    private static NBNetConnection a(NBNetRoute nbNetRoute, NBNetContext nbNetContext, Proxy proxy) {
        if (nbNetRoute.d() == Proxy.NO_PROXY) {
            throw new UnsupportedOperationException("connectByProxy. NO_PROXY");
        }
        nbNetRoute.g();
        SocketAddress proxyAddress = proxy.address();
        if (!(proxyAddress instanceof InetSocketAddress)) {
            throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + proxyAddress.getClass());
        }
        InetSocketAddress proxyInetAddress = (InetSocketAddress) proxyAddress;
        InetSocketAddress socketAddress = new InetSocketAddress(NBNetDnsHelper.a(proxyInetAddress.getHostName())[0], proxyInetAddress.getPort());
        MonitorLogUtil.c(nbNetContext, true);
        return a(nbNetRoute, nbNetContext, socketAddress);
    }

    private boolean b() {
        return this.b;
    }
}
