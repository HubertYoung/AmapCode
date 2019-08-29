package com.alibaba.appmonitor.pool;

import java.util.HashMap;
import java.util.Map;

public class BalancedPool implements IPool {
    private static BalancedPool instance = new BalancedPool();
    private Map<Class<? extends Reusable>, ReuseItemPool<? extends Reusable>> reuseItemPools = new HashMap();

    public static BalancedPool getInstance() {
        return instance;
    }

    private BalancedPool() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x001a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T extends com.alibaba.appmonitor.pool.Reusable> T poll(java.lang.Class<T> r3, java.lang.Object... r4) {
        /*
            r2 = this;
            com.alibaba.appmonitor.pool.ReuseItemPool r0 = r2.getPool(r3)
            com.alibaba.appmonitor.pool.Reusable r0 = r0.poll()
            if (r0 != 0) goto L_0x0017
            java.lang.Object r3 = r3.newInstance()     // Catch:{ Exception -> 0x0011 }
            com.alibaba.appmonitor.pool.Reusable r3 = (com.alibaba.appmonitor.pool.Reusable) r3     // Catch:{ Exception -> 0x0011 }
            goto L_0x0018
        L_0x0011:
            r3 = move-exception
            com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder$ExceptionType r1 = com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder.ExceptionType.AP
            com.alibaba.analytics.core.selfmonitor.exception.ExceptionEventBuilder.log(r1, r3)
        L_0x0017:
            r3 = r0
        L_0x0018:
            if (r3 == 0) goto L_0x001d
            r3.fill(r4)
        L_0x001d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.appmonitor.pool.BalancedPool.poll(java.lang.Class, java.lang.Object[]):com.alibaba.appmonitor.pool.Reusable");
    }

    public <T extends Reusable> void offer(T t) {
        if (t != null) {
            getPool(t.getClass()).offer(t);
        }
    }

    private synchronized <T extends Reusable> ReuseItemPool<T> getPool(Class<T> cls) {
        ReuseItemPool<T> reuseItemPool;
        reuseItemPool = this.reuseItemPools.get(cls);
        if (reuseItemPool == null) {
            reuseItemPool = new ReuseItemPool<>();
            this.reuseItemPools.put(cls, reuseItemPool);
        }
        return reuseItemPool;
    }
}
