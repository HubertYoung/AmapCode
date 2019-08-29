package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.statist.StrategyStatObject;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anet.channel.status.NetworkStatusHelper.a;
import anet.channel.strategy.utils.SerialLruCache;
import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class StrategyInfoHolder implements a {
    Map<String, StrategyTable> a = new LruStrategyMap();
    public StrategyConfig b = null;
    public final bs c = new bs();
    private final StrategyTable d = new StrategyTable("Unknown");
    private final Set<String> e = new HashSet();
    private volatile String f = "";

    static class LruStrategyMap extends SerialLruCache<String, StrategyTable> {
        private static final long serialVersionUID = 1866478394612290927L;

        public LruStrategyMap() {
            super(3);
        }

        public boolean entryRemoved(final Entry<String, StrategyTable> entry) {
            ch.a(new Runnable() {
                public final void run() {
                    StrategyTable strategyTable = (StrategyTable) entry.getValue();
                    if (strategyTable.e) {
                        StrategyStatObject strategyStatObject = new StrategyStatObject(1);
                        strategyStatObject.writeStrategyFileId = strategyTable.a;
                        bx.a((Serializable) entry.getValue(), strategyTable.a, strategyStatObject);
                        strategyTable.e = false;
                    }
                }
            });
            return true;
        }
    }

    public StrategyInfoHolder() {
        try {
            NetworkStatusHelper.a((a) this);
            this.f = b(NetworkStatusHelper.a());
            cl.b("awcn.StrategyInfoHolder", "restore", null, new Object[0]);
            final String str = this.f;
            if (!TextUtils.isEmpty(str)) {
                a(str, true);
            }
            this.b = (StrategyConfig) bx.a("StrategyConfig", null);
            ch.a(new Runnable() {
                public final void run() {
                    try {
                        cl.b("awcn.StrategyInfoHolder", "start loading strategy files", null, new Object[0]);
                        long currentTimeMillis = System.currentTimeMillis();
                        File[] b2 = bx.b();
                        if (b2 != null) {
                            int i = 0;
                            for (int i2 = 0; i2 < b2.length && i < 2; i2++) {
                                File file = b2[i2];
                                if (!file.isDirectory()) {
                                    String name = file.getName();
                                    if (!name.equals(str) && !name.startsWith("StrategyConfig")) {
                                        StrategyInfoHolder.this.a(name, false);
                                        i++;
                                    }
                                }
                            }
                            cl.b("awcn.StrategyInfoHolder", "end loading strategy files", null, "total cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis));
                        }
                    } catch (Exception unused) {
                    }
                }
            });
        } catch (Throwable unused) {
        } finally {
            c();
        }
    }

    private void c() {
        for (Entry<String, StrategyTable> value : this.a.entrySet()) {
            ((StrategyTable) value.getValue()).a();
        }
        if (this.b == null) {
            this.b = new StrategyConfig();
        }
        StrategyConfig strategyConfig = this.b;
        if (strategyConfig.a == null) {
            strategyConfig.a = new SerialLruCache<>(256);
        }
        if (strategyConfig.b == null) {
            strategyConfig.b = new ConcurrentHashMap();
        }
        this.b.c = this;
    }

    /* JADX INFO: used method not loaded: z.a(anet.channel.statist.StatObject):null, types can be incorrect */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001c, code lost:
        r2 = (anet.channel.strategy.StrategyTable) defpackage.bx.a(r7, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0022, code lost:
        if (r2 == null) goto L_0x0036;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0024, code lost:
        r2.a();
        r3 = r6.a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0029, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r6.a.put(r2.a, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0031, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0036, code lost:
        r3 = r6.e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0038, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r6.e.remove(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003e, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x003f, code lost:
        if (r8 == false) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0041, code lost:
        if (r2 == null) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0043, code lost:
        r1 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0044, code lost:
        r0.isSucceed = r1;
        defpackage.x.a().a((anet.channel.statist.StatObject) r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x004d, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0011, code lost:
        r0 = null;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0013, code lost:
        if (r8 == false) goto L_0x001c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0015, code lost:
        r0 = new anet.channel.statist.StrategyStatObject(0);
        r0.readStrategyFileId = r7;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.lang.String r7, boolean r8) {
        /*
            r6 = this;
            java.util.Set<java.lang.String> r0 = r6.e
            monitor-enter(r0)
            java.util.Set<java.lang.String> r1 = r6.e     // Catch:{ all -> 0x0053 }
            boolean r1 = r1.contains(r7)     // Catch:{ all -> 0x0053 }
            if (r1 != 0) goto L_0x0051
            java.util.Set<java.lang.String> r1 = r6.e     // Catch:{ all -> 0x0053 }
            r1.add(r7)     // Catch:{ all -> 0x0053 }
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            r0 = 0
            r1 = 0
            if (r8 == 0) goto L_0x001c
            anet.channel.statist.StrategyStatObject r0 = new anet.channel.statist.StrategyStatObject
            r0.<init>(r1)
            r0.readStrategyFileId = r7
        L_0x001c:
            java.lang.Object r2 = defpackage.bx.a(r7, r0)
            anet.channel.strategy.StrategyTable r2 = (anet.channel.strategy.StrategyTable) r2
            if (r2 == 0) goto L_0x0036
            r2.a()
            java.util.Map<java.lang.String, anet.channel.strategy.StrategyTable> r3 = r6.a
            monitor-enter(r3)
            java.util.Map<java.lang.String, anet.channel.strategy.StrategyTable> r4 = r6.a     // Catch:{ all -> 0x0033 }
            java.lang.String r5 = r2.a     // Catch:{ all -> 0x0033 }
            r4.put(r5, r2)     // Catch:{ all -> 0x0033 }
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            goto L_0x0036
        L_0x0033:
            r7 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            throw r7
        L_0x0036:
            java.util.Set<java.lang.String> r3 = r6.e
            monitor-enter(r3)
            java.util.Set<java.lang.String> r4 = r6.e     // Catch:{ all -> 0x004e }
            r4.remove(r7)     // Catch:{ all -> 0x004e }
            monitor-exit(r3)     // Catch:{ all -> 0x004e }
            if (r8 == 0) goto L_0x004d
            if (r2 == 0) goto L_0x0044
            r1 = 1
        L_0x0044:
            r0.isSucceed = r1
            z r7 = defpackage.x.a()
            r7.a(r0)
        L_0x004d:
            return
        L_0x004e:
            r7 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x004e }
            throw r7
        L_0x0051:
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            return
        L_0x0053:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0053 }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.strategy.StrategyInfoHolder.a(java.lang.String, boolean):void");
    }

    public final void a() {
        synchronized (this) {
            for (StrategyTable next : this.a.values()) {
                if (next.e) {
                    StrategyStatObject strategyStatObject = new StrategyStatObject(1);
                    strategyStatObject.writeStrategyFileId = next.a;
                    bx.a(next, next.a, strategyStatObject);
                    next.e = false;
                }
            }
            bx.a(this.b, "StrategyConfig", null);
        }
    }

    public final StrategyTable b() {
        StrategyTable strategyTable = this.d;
        String str = this.f;
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.a) {
                try {
                    strategyTable = this.a.get(str);
                    if (strategyTable == null) {
                        strategyTable = new StrategyTable(str);
                        this.a.put(str, strategyTable);
                    }
                }
            }
        }
        return strategyTable;
    }

    private static String b(NetworkStatus networkStatus) {
        if (networkStatus.isWifi()) {
            String b2 = cz.b(NetworkStatusHelper.g());
            if (TextUtils.isEmpty(b2)) {
                b2 = "";
            }
            return "WIFI$".concat(String.valueOf(b2));
        } else if (!networkStatus.isMobile()) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(networkStatus.getType());
            sb.append("$");
            sb.append(NetworkStatusHelper.c());
            return sb.toString();
        }
    }

    public final void a(NetworkStatus networkStatus) {
        this.f = b(networkStatus);
        final String str = this.f;
        if (!TextUtils.isEmpty(str)) {
            synchronized (this.a) {
                if (!this.a.containsKey(str)) {
                    ch.a(new Runnable() {
                        public final void run() {
                            StrategyInfoHolder.this.a(str, true);
                        }
                    });
                }
            }
        }
    }
}
