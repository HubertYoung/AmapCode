package com.alipay.mobile.common.nbnet.biz.netlib;

import android.support.annotation.NonNull;
import com.alipay.mobile.common.nbnet.api.NBNetContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NBNetCompositeConntionEngine implements NBNetConntionEngine {
    private static NBNetCompositeConntionEngine a;
    private Map<String, NBNetCompositeConntionEngineUnit> b = new ConcurrentHashMap(2);
    private NBNetConntionCallBack c;

    public static final NBNetConntionEngine a() {
        if (a != null) {
            return a;
        }
        synchronized (NBNetConntionEngine.class) {
            if (a != null) {
                NBNetCompositeConntionEngine nBNetCompositeConntionEngine = a;
                return nBNetCompositeConntionEngine;
            }
            a = new NBNetCompositeConntionEngine();
            return a;
        }
    }

    public final NBNetConnection a(NBNetRoute nbNetRoute, NBNetContext nbNetContext) {
        b(nbNetRoute.a()).a();
        return null;
    }

    @NonNull
    private NBNetCompositeConntionEngineUnit b(String uriHost) {
        NBNetCompositeConntionEngineUnit nbNetCompositeConntionEngineUnit = this.b.get(uriHost);
        if (nbNetCompositeConntionEngineUnit != null) {
            return nbNetCompositeConntionEngineUnit;
        }
        synchronized (this) {
            NBNetCompositeConntionEngineUnit nbNetCompositeConntionEngineUnit2 = this.b.get(uriHost);
            if (nbNetCompositeConntionEngineUnit2 != null) {
                return nbNetCompositeConntionEngineUnit2;
            }
            NBNetCompositeConntionEngineUnit nbNetCompositeConntionEngineUnit22 = new NBNetCompositeConntionEngineUnit(uriHost, this.c);
            this.b.put(uriHost, nbNetCompositeConntionEngineUnit22);
            return nbNetCompositeConntionEngineUnit22;
        }
    }

    public final void a(NBNetConntionCallBack nbNetConntionCallBack) {
        this.c = nbNetConntionCallBack;
    }

    public final void a(String hostName) {
        b(hostName).b();
    }
}
