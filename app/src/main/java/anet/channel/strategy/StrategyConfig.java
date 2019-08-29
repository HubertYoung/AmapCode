package anet.channel.strategy;

import anet.channel.strategy.utils.SerialLruCache;
import java.io.Serializable;
import java.util.Map;

public class StrategyConfig implements Serializable {
    private static final long serialVersionUID = -7798500032935529499L;
    public SerialLruCache<String, String> a = null;
    public Map<String, String> b = null;
    transient StrategyInfoHolder c = null;

    StrategyConfig() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
        if ("No_Result".equals(r2) != false) goto L_0x003a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String a(java.lang.String r6) {
        /*
            r5 = this;
            boolean r0 = android.text.TextUtils.isEmpty(r6)
            r1 = 0
            if (r0 != 0) goto L_0x003e
            boolean r0 = defpackage.ci.c(r6)
            if (r0 != 0) goto L_0x000e
            goto L_0x003e
        L_0x000e:
            anet.channel.strategy.utils.SerialLruCache<java.lang.String, java.lang.String> r0 = r5.a
            monitor-enter(r0)
            anet.channel.strategy.utils.SerialLruCache<java.lang.String, java.lang.String> r2 = r5.a     // Catch:{ all -> 0x003b }
            java.lang.Object r2 = r2.get(r6)     // Catch:{ all -> 0x003b }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ all -> 0x003b }
            if (r2 != 0) goto L_0x0022
            anet.channel.strategy.utils.SerialLruCache<java.lang.String, java.lang.String> r3 = r5.a     // Catch:{ all -> 0x003b }
            java.lang.String r4 = "No_Result"
            r3.put(r6, r4)     // Catch:{ all -> 0x003b }
        L_0x0022:
            monitor-exit(r0)     // Catch:{ all -> 0x003b }
            if (r2 != 0) goto L_0x0030
            anet.channel.strategy.StrategyInfoHolder r0 = r5.c
            anet.channel.strategy.StrategyTable r0 = r0.b()
            r1 = 0
            r0.a(r6, r1)
            goto L_0x0039
        L_0x0030:
            java.lang.String r6 = "No_Result"
            boolean r6 = r6.equals(r2)
            if (r6 == 0) goto L_0x0039
            goto L_0x003a
        L_0x0039:
            r1 = r2
        L_0x003a:
            return r1
        L_0x003b:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003b }
            throw r6
        L_0x003e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.StrategyConfig.a(java.lang.String):java.lang.String");
    }
}
