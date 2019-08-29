package com.amap.location.b.d;

/* compiled from: RssiManager */
public class a {
    private static com.amap.location.common.c.b a = new com.amap.location.common.c.b();

    /* renamed from: com.amap.location.b.d.a$a reason: collision with other inner class name */
    /* compiled from: RssiManager */
    public static class C0012a implements com.amap.location.common.c.a {
        private int a;
        private int b;
        private int c;

        C0012a(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }

        public long a() {
            return a.a(this.a, this.b);
        }

        public int b() {
            return this.c;
        }
    }

    /* compiled from: RssiManager */
    public static class b implements com.amap.location.common.c.a {
        private long a;
        private int b;

        b(long j, int i) {
            this.a = j;
            this.b = i;
        }

        public long a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }
    }

    public static long a(int i, int i2) {
        return (((long) i2) & 4294967295L) | ((((long) i) & 4294967295L) << 32);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0091, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void a(java.util.List<com.amap.location.b.c.c> r6) {
        /*
            java.lang.Class<com.amap.location.b.d.a> r0 = com.amap.location.b.d.a.class
            monitor-enter(r0)
            if (r6 == 0) goto L_0x0090
            boolean r1 = r6.isEmpty()     // Catch:{ all -> 0x008d }
            if (r1 == 0) goto L_0x000d
            goto L_0x0090
        L_0x000d:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x008d }
            int r2 = r6.size()     // Catch:{ all -> 0x008d }
            r1.<init>(r2)     // Catch:{ all -> 0x008d }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x008d }
        L_0x001a:
            boolean r2 = r6.hasNext()     // Catch:{ all -> 0x008d }
            if (r2 == 0) goto L_0x0086
            java.lang.Object r2 = r6.next()     // Catch:{ all -> 0x008d }
            com.amap.location.b.c.c r2 = (com.amap.location.b.c.c) r2     // Catch:{ all -> 0x008d }
            byte r3 = r2.a     // Catch:{ all -> 0x008d }
            r4 = 1
            if (r3 != r4) goto L_0x003e
            T r2 = r2.f     // Catch:{ all -> 0x008d }
            com.amap.location.b.c.h r2 = (com.amap.location.b.c.h) r2     // Catch:{ all -> 0x008d }
            com.amap.location.b.d.a$a r3 = new com.amap.location.b.d.a$a     // Catch:{ all -> 0x008d }
            int r4 = r2.c     // Catch:{ all -> 0x008d }
            int r5 = r2.d     // Catch:{ all -> 0x008d }
            int r2 = r2.e     // Catch:{ all -> 0x008d }
            r3.<init>(r4, r5, r2)     // Catch:{ all -> 0x008d }
            r1.add(r3)     // Catch:{ all -> 0x008d }
            goto L_0x001a
        L_0x003e:
            byte r3 = r2.a     // Catch:{ all -> 0x008d }
            r4 = 3
            if (r3 != r4) goto L_0x0056
            T r2 = r2.f     // Catch:{ all -> 0x008d }
            com.amap.location.b.c.i r2 = (com.amap.location.b.c.i) r2     // Catch:{ all -> 0x008d }
            com.amap.location.b.d.a$a r3 = new com.amap.location.b.d.a$a     // Catch:{ all -> 0x008d }
            int r4 = r2.c     // Catch:{ all -> 0x008d }
            int r5 = r2.d     // Catch:{ all -> 0x008d }
            int r2 = r2.f     // Catch:{ all -> 0x008d }
            r3.<init>(r4, r5, r2)     // Catch:{ all -> 0x008d }
            r1.add(r3)     // Catch:{ all -> 0x008d }
            goto L_0x001a
        L_0x0056:
            byte r3 = r2.a     // Catch:{ all -> 0x008d }
            r4 = 4
            if (r3 != r4) goto L_0x006e
            T r2 = r2.f     // Catch:{ all -> 0x008d }
            com.amap.location.b.c.k r2 = (com.amap.location.b.c.k) r2     // Catch:{ all -> 0x008d }
            com.amap.location.b.d.a$a r3 = new com.amap.location.b.d.a$a     // Catch:{ all -> 0x008d }
            int r4 = r2.c     // Catch:{ all -> 0x008d }
            int r5 = r2.d     // Catch:{ all -> 0x008d }
            int r2 = r2.f     // Catch:{ all -> 0x008d }
            r3.<init>(r4, r5, r2)     // Catch:{ all -> 0x008d }
            r1.add(r3)     // Catch:{ all -> 0x008d }
            goto L_0x001a
        L_0x006e:
            byte r3 = r2.a     // Catch:{ all -> 0x008d }
            r4 = 2
            if (r3 != r4) goto L_0x001a
            T r2 = r2.f     // Catch:{ all -> 0x008d }
            com.amap.location.b.c.a r2 = (com.amap.location.b.c.a) r2     // Catch:{ all -> 0x008d }
            com.amap.location.b.d.a$a r3 = new com.amap.location.b.d.a$a     // Catch:{ all -> 0x008d }
            int r4 = r2.b     // Catch:{ all -> 0x008d }
            int r5 = r2.c     // Catch:{ all -> 0x008d }
            int r2 = r2.f     // Catch:{ all -> 0x008d }
            r3.<init>(r4, r5, r2)     // Catch:{ all -> 0x008d }
            r1.add(r3)     // Catch:{ all -> 0x008d }
            goto L_0x001a
        L_0x0086:
            com.amap.location.common.c.b r6 = a     // Catch:{ all -> 0x008d }
            r6.a(r1)     // Catch:{ all -> 0x008d }
            monitor-exit(r0)
            return
        L_0x008d:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        L_0x0090:
            monitor-exit(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.b.d.a.a(java.util.List):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0041, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void b(java.util.List<android.net.wifi.ScanResult> r6) {
        /*
            java.lang.Class<com.amap.location.b.d.a> r0 = com.amap.location.b.d.a.class
            monitor-enter(r0)
            if (r6 == 0) goto L_0x0040
            boolean r1 = r6.isEmpty()     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x000c
            goto L_0x0040
        L_0x000c:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x003d }
            int r2 = r6.size()     // Catch:{ all -> 0x003d }
            r1.<init>(r2)     // Catch:{ all -> 0x003d }
            java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x003d }
        L_0x0019:
            boolean r2 = r6.hasNext()     // Catch:{ all -> 0x003d }
            if (r2 == 0) goto L_0x0036
            java.lang.Object r2 = r6.next()     // Catch:{ all -> 0x003d }
            android.net.wifi.ScanResult r2 = (android.net.wifi.ScanResult) r2     // Catch:{ all -> 0x003d }
            com.amap.location.b.d.a$b r3 = new com.amap.location.b.d.a$b     // Catch:{ all -> 0x003d }
            java.lang.String r4 = r2.BSSID     // Catch:{ all -> 0x003d }
            long r4 = com.amap.location.common.f.h.a(r4)     // Catch:{ all -> 0x003d }
            int r2 = r2.level     // Catch:{ all -> 0x003d }
            r3.<init>(r4, r2)     // Catch:{ all -> 0x003d }
            r1.add(r3)     // Catch:{ all -> 0x003d }
            goto L_0x0019
        L_0x0036:
            com.amap.location.common.c.b r6 = a     // Catch:{ all -> 0x003d }
            r6.b(r1)     // Catch:{ all -> 0x003d }
            monitor-exit(r0)
            return
        L_0x003d:
            r6 = move-exception
            monitor-exit(r0)
            throw r6
        L_0x0040:
            monitor-exit(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.b.d.a.b(java.util.List):void");
    }

    public static synchronized short a(long j) {
        short a2;
        synchronized (a.class) {
            try {
                a2 = a.a(j);
            }
        }
        return a2;
    }

    public static synchronized short b(long j) {
        short b2;
        synchronized (a.class) {
            try {
                b2 = a.b(j);
            }
        }
        return b2;
    }

    public static synchronized void a() {
        synchronized (a.class) {
            a.a();
        }
    }
}
