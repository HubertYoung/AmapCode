package com.alipay.mobile.common.nbnet.biz.netlib;

import android.support.annotation.Nullable;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.biz.log.MonitorLogUtil;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.util.NBNetConfigUtil;
import javax.net.ssl.SSLSocketFactory;

public class NBNetBasicConntionManager implements NBNetConntionManager {
    public NBNetConnectionEntity a(NBNetReqConn nbNetReqConn, NBNetContext nbNetContext) {
        a(nbNetReqConn);
        return null;
    }

    protected static void a(NBNetReqConn nbNetReqConn) {
        if (nbNetReqConn.e == 1) {
            int downloadServerPort = NBNetConfigUtil.r();
            if (downloadServerPort != -1) {
                nbNetReqConn.b = downloadServerPort;
                return;
            }
            return;
        }
        int uploadServerPort = NBNetConfigUtil.q();
        if (uploadServerPort != -1) {
            nbNetReqConn.b = uploadServerPort;
        }
    }

    private static SSLSocketFactory c(NBNetReqConn nbNetReqConn) {
        if (!nbNetReqConn.c) {
            return null;
        }
        nbNetReqConn.b = 443;
        return (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

    protected static NBNetRoute b(NBNetReqConn nbNetReqConn) {
        return new NBNetRoute(nbNetReqConn.e, nbNetReqConn.a, nbNetReqConn.b == -1 ? 443 : nbNetReqConn.b, NBNetworkUtil.a(), c(nbNetReqConn));
    }

    @Nullable
    protected static NBNetConnectionEntity a(NBNetReqConn nbNetReqConn, NBNetContext nbNetContext, NBNetRoute nbNetRoute) {
        if (!nbNetReqConn.d) {
            return null;
        }
        long startWaitConnTime = System.currentTimeMillis();
        NBNetConnectionEntity connectionFromPool = a(nbNetContext, nbNetRoute);
        if (connectionFromPool == null) {
            return connectionFromPool;
        }
        a(nbNetContext, startWaitConnTime, connectionFromPool.c().k(), "pool", false);
        return connectionFromPool;
    }

    private static NBNetConnectionEntity a(NBNetContext nbNetContext, NBNetRoute nbNetRoute) {
        NBNetConnection nbNetConnection = NBNetConnectionPool.a().a(nbNetRoute);
        if (nbNetConnection == null || !nbNetConnection.d()) {
            return null;
        }
        NBNetLogCat.a((String) "NBNetConntionManager", "requestConnection. reuse connection, connected time : " + nbNetConnection.g());
        a(nbNetContext, nbNetConnection);
        NBNetConnectionEntity nBNetConnectionEntity = new NBNetConnectionEntity(nbNetConnection, nbNetContext);
        nbNetConnection.a(NBNetworkUtil.b());
        return nBNetConnectionEntity;
    }

    private static void a(NBNetContext nbNetContext, NBNetConnection nbNetConnection) {
        if (nbNetConnection.h().f()) {
            MonitorLogUtil.c(nbNetContext, true);
        } else {
            MonitorLogUtil.c(nbNetContext, false);
        }
    }

    protected static void a(NBNetContext nbNetContext, long startWaitConnTime, String hostAndPort, String connMethod, boolean isTriedComp) {
        MonitorLogUtil.f(nbNetContext, hostAndPort);
        MonitorLogUtil.g(nbNetContext, connMethod);
        MonitorLogUtil.l(nbNetContext, System.currentTimeMillis() - startWaitConnTime);
        MonitorLogUtil.b(nbNetContext, isTriedComp);
    }
}
