package com.amap.location.icecream;

/* compiled from: IcecreamCart */
public class b {
    private static volatile b a;
    private volatile boolean b;
    private g c;

    public static b a() {
        if (a == null) {
            synchronized (b.class) {
                try {
                    if (a == null) {
                        a = new b();
                    }
                }
            }
        }
        return a;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0022, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(android.content.Context r2, org.json.JSONObject r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            if (r2 == 0) goto L_0x0021
            if (r3 != 0) goto L_0x0006
            goto L_0x0021
        L_0x0006:
            boolean r0 = r1.b     // Catch:{ all -> 0x001e }
            if (r0 != 0) goto L_0x001c
            com.amap.location.icecream.a r0 = com.amap.location.icecream.a.a(r2)     // Catch:{ all -> 0x001e }
            com.amap.location.icecream.IcecreamBaseLibFactory.setIcecreamBaseLib(r0)     // Catch:{ all -> 0x001e }
            com.amap.location.icecream.d.a(r2)     // Catch:{ all -> 0x001e }
            com.amap.location.icecream.g r0 = r1.c     // Catch:{ all -> 0x001e }
            r0.a(r2, r3)     // Catch:{ all -> 0x001e }
            r2 = 1
            r1.b = r2     // Catch:{ all -> 0x001e }
        L_0x001c:
            monitor-exit(r1)
            return
        L_0x001e:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        L_0x0021:
            monitor-exit(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.b.a(android.content.Context, org.json.JSONObject):void");
    }

    public synchronized void b() {
        if (this.b) {
            this.c.a();
            this.b = false;
        }
    }

    public synchronized int a(int i) {
        if (!this.b) {
            return -1;
        }
        return this.c.a(i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.b     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x000e
            if (r2 != 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            com.amap.location.icecream.g r0 = r1.c     // Catch:{ all -> 0x0010 }
            r0.a(r2)     // Catch:{ all -> 0x0010 }
        L_0x000e:
            monitor-exit(r1)
            return
        L_0x0010:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.b.a(java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(android.location.Location r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.b     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x000e
            if (r2 != 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            com.amap.location.icecream.g r0 = r1.c     // Catch:{ all -> 0x0010 }
            r0.a(r2)     // Catch:{ all -> 0x0010 }
        L_0x000e:
            monitor-exit(r1)
            return
        L_0x0010:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.b.a(android.location.Location):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x000f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(java.lang.String r2) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.b     // Catch:{ all -> 0x0010 }
            if (r0 == 0) goto L_0x000e
            if (r2 != 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            com.amap.location.icecream.g r0 = r1.c     // Catch:{ all -> 0x0010 }
            r0.b(r2)     // Catch:{ all -> 0x0010 }
        L_0x000e:
            monitor-exit(r1)
            return
        L_0x0010:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.b.b(java.lang.String):void");
    }

    private b() {
        this.b = false;
        this.c = null;
        this.c = new g();
    }
}
