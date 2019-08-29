package com.alipay.mobile.common.nbnet.biz.netlib;

import com.alipay.mobile.common.nbnet.api.NBNetContext;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;

public class NBNetDefaultConntionManager extends NBNetBasicConntionManager {
    private static NBNetConntionManager a;

    public static final NBNetConntionManager a() {
        if (a != null) {
            return a;
        }
        synchronized (NBNetConntionManager.class) {
            if (a != null) {
                NBNetConntionManager nBNetConntionManager = a;
                return nBNetConntionManager;
            }
            a = new NBNetDefaultConntionManager();
            return a;
        }
    }

    /* JADX INFO: finally extract failed */
    public final NBNetConnectionEntity a(NBNetReqConn nbNetReqConn, NBNetContext nbNetContext) {
        long startTime = System.currentTimeMillis();
        NBNetConnectionEntity nbNetConnectionEntity = null;
        String connMethod = "pool";
        try {
            a(nbNetReqConn);
            NBNetRoute nbNetRoute = b(nbNetReqConn);
            NBNetConnectionEntity nbNetConnectionEntity2 = a(nbNetReqConn, nbNetContext, nbNetRoute);
            if (nbNetConnectionEntity2 != null) {
                if (nbNetConnectionEntity2 != null) {
                    a(nbNetContext, startTime, nbNetConnectionEntity2.c().k(), connMethod, false);
                }
                return nbNetConnectionEntity2;
            }
            nbNetConnectionEntity = a(nbNetRoute, nbNetContext);
            a(nbNetContext, startTime, nbNetConnectionEntity.c().k(), "direct", false);
            return nbNetConnectionEntity;
        } catch (Throwable th) {
            Throwable th2 = th;
            if (nbNetConnectionEntity != null) {
                a(nbNetContext, startTime, nbNetConnectionEntity.c().k(), connMethod, false);
            }
            throw th2;
        }
    }

    public static NBNetConnectionEntity a(NBNetRoute nbNetRoute, NBNetContext nbNetContext) {
        NBNetConnection newConnect = NBNetDefaultConntionEngine.a().a(nbNetRoute, nbNetContext);
        newConnect.a(NBNetworkUtil.b());
        newConnect.a(false);
        NBNetConnectionPool.a().a(newConnect);
        NBNetLogCat.a((String) "NBNetConntionManager", "requestConnection. new connection, connected time : " + newConnect.g());
        return new NBNetConnectionEntity(newConnect, nbNetContext);
    }
}
