package com.amap.location.common.c;

import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.ArrayMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: RssiInfoManager */
public class b {
    private Map<Long, a> a;
    private Map<Long, a> b;
    private Map<Long, a> c;
    private Map<Long, a> d;
    private Object e = new Object();
    private Object f = new Object();

    /* compiled from: RssiInfoManager */
    static class a {
        int a;
        long b;
        boolean c;

        private a() {
        }
    }

    public b() {
        if (VERSION.SDK_INT >= 19) {
            this.a = new ArrayMap();
            this.b = new ArrayMap();
            this.c = new ArrayMap();
            this.d = new ArrayMap();
            return;
        }
        this.a = new HashMap();
        this.b = new HashMap();
        this.c = new HashMap();
        this.d = new HashMap();
    }

    public void a() {
        synchronized (this.e) {
            this.a.clear();
        }
        synchronized (this.f) {
            this.c.clear();
        }
    }

    public void a(List<a> list) {
        if (list != null && !list.isEmpty()) {
            synchronized (this.e) {
                a(list, this.a, this.b);
                Map<Long, a> map = this.a;
                this.a = this.b;
                this.b = map;
                this.b.clear();
            }
        }
    }

    public short a(long j) {
        return a(this.a, j);
    }

    public void b(List<a> list) {
        if (list != null && !list.isEmpty()) {
            synchronized (this.f) {
                a(list, this.c, this.d);
                Map<Long, a> map = this.c;
                this.c = this.d;
                this.d = map;
                this.d.clear();
            }
        }
    }

    public short b(long j) {
        return a(this.c, j);
    }

    private void a(List<a> list, Map<Long, a> map, Map<Long, a> map2) {
        long b2 = b();
        if (map.isEmpty()) {
            for (a next : list) {
                a aVar = new a();
                aVar.a = next.b();
                aVar.b = b2;
                aVar.c = false;
                map2.put(Long.valueOf(next.a()), aVar);
            }
            return;
        }
        for (a next2 : list) {
            long a2 = next2.a();
            a aVar2 = map.get(Long.valueOf(a2));
            if (aVar2 == null) {
                aVar2 = new a();
                aVar2.a = next2.b();
                aVar2.b = b2;
                aVar2.c = true;
            } else if (aVar2.a != next2.b()) {
                aVar2.a = next2.b();
                aVar2.b = b2;
                aVar2.c = true;
            }
            map2.put(Long.valueOf(a2), aVar2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0031, code lost:
        return r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private short a(java.util.Map<java.lang.Long, com.amap.location.common.c.b.a> r9, long r10) {
        /*
            r8 = this;
            monitor-enter(r9)
            java.lang.Long r10 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x0032 }
            java.lang.Object r10 = r9.get(r10)     // Catch:{ all -> 0x0032 }
            com.amap.location.common.c.b$a r10 = (com.amap.location.common.c.b.a) r10     // Catch:{ all -> 0x0032 }
            if (r10 != 0) goto L_0x0010
            r10 = 0
            monitor-exit(r9)     // Catch:{ all -> 0x0032 }
            return r10
        L_0x0010:
            r0 = 1
            r2 = 32767(0x7fff, double:1.6189E-319)
            long r4 = b()     // Catch:{ all -> 0x0032 }
            long r6 = r10.b     // Catch:{ all -> 0x0032 }
            r11 = 0
            long r4 = r4 - r6
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r6
            long r2 = java.lang.Math.min(r2, r4)     // Catch:{ all -> 0x0032 }
            long r0 = java.lang.Math.max(r0, r2)     // Catch:{ all -> 0x0032 }
            int r11 = (int) r0     // Catch:{ all -> 0x0032 }
            short r11 = (short) r11     // Catch:{ all -> 0x0032 }
            boolean r10 = r10.c     // Catch:{ all -> 0x0032 }
            if (r10 == 0) goto L_0x002e
            goto L_0x0030
        L_0x002e:
            int r10 = -r11
            short r11 = (short) r10     // Catch:{ all -> 0x0032 }
        L_0x0030:
            monitor-exit(r9)     // Catch:{ all -> 0x0032 }
            return r11
        L_0x0032:
            r10 = move-exception
            monitor-exit(r9)     // Catch:{ all -> 0x0032 }
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.common.c.b.a(java.util.Map, long):short");
    }

    private static long b() {
        return SystemClock.elapsedRealtime();
    }
}
