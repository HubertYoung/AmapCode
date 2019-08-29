package com.alibaba.baichuan.android.trade.config.a;

import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class b {
    private static final String c = "b";
    private Map a = new HashMap();
    private Set b = new HashSet();

    public synchronized void a(a aVar) {
        Map a2 = aVar.a();
        String str = c;
        StringBuilder sb = new StringBuilder("config更新，config更新的参数值为:");
        String str2 = null;
        sb.append(a2 != null ? a2 : null);
        sb.append("   configMem当前值=");
        sb.append(this.a != null ? this.a : null);
        sb.append("   filter名单=");
        sb.append(this.b != null ? this.b.toString() : null);
        AlibcLogger.d(str, sb.toString());
        if (a2 != null) {
            for (String str3 : a2.keySet()) {
                for (Entry entry : ((Map) a2.get(str3)).entrySet()) {
                    if (!this.b.contains(entry.getKey()) || !str3.equals("albbTradeConfig")) {
                        a(str3, (String) entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        String str4 = c;
        StringBuilder sb2 = new StringBuilder("更新后configMemdo=");
        if (this.a != null) {
            str2 = this.a.toString();
        }
        sb2.append(str2);
        AlibcLogger.d(str4, sb2.toString());
    }

    public synchronized void a(String str) {
        this.b.add(str);
    }

    public synchronized void a(String str, String str2, Object obj) {
        Map map = (Map) this.a.get(str);
        if (map == null) {
            map = new HashMap();
            this.a.put(str, map);
        }
        map.put(str2, obj);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.lang.Object b(java.lang.String r2, java.lang.String r3, java.lang.Object r4) {
        /*
            r1 = this;
            monitor-enter(r1)
            java.util.Map r0 = r1.a     // Catch:{ all -> 0x0019 }
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x0019 }
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ all -> 0x0019 }
            if (r2 == 0) goto L_0x0017
            java.lang.Object r0 = r2.get(r3)     // Catch:{ all -> 0x0019 }
            if (r0 == 0) goto L_0x0017
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x0019 }
            monitor-exit(r1)
            return r2
        L_0x0017:
            monitor-exit(r1)
            return r4
        L_0x0019:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.baichuan.android.trade.config.a.b.b(java.lang.String, java.lang.String, java.lang.Object):java.lang.Object");
    }
}
