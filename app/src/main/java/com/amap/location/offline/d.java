package com.amap.location.offline;

import android.content.Context;
import android.support.annotation.NonNull;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.FPS;
import com.amap.location.offline.a.a;
import com.amap.location.offline.e.b;

/* compiled from: OfflineManager */
public class d {
    private static volatile d a;
    private c b;
    private b c;
    private Context d;
    private a e;
    private e f;
    private com.amap.location.offline.b.a g;

    public static d a() {
        if (a == null) {
            synchronized (d.class) {
                try {
                    if (a == null) {
                        a = new d();
                    }
                }
            }
        }
        return a;
    }

    private d() {
    }

    public synchronized void a(@NonNull Context context, @NonNull c cVar, @NonNull a aVar) {
        if (this.b == null) {
            if (cVar != null) {
                this.b = cVar;
            } else {
                this.b = new c();
            }
        }
        if (this.c == null) {
            this.c = new b();
            if (aVar != null) {
                this.c.a = aVar;
            }
        }
        if (this.e == null) {
            this.e = new a(context, this.b, this.c);
            this.e.a();
        }
        if (this.f == null) {
            this.d = context.getApplicationContext();
            com.amap.location.offline.d.a.a(this.d, this.b, this.c);
            this.f = new e(context, this.b, this.c);
            if (!this.f.a(this.d.getPackageName()) && this.g == null) {
                d();
            }
        }
    }

    public synchronized void b() {
        c cVar = this.b;
        this.b = null;
        this.c = null;
        this.f = null;
        if (this.e != null) {
            this.e.b();
            this.e = null;
        }
        if (this.g != null) {
            this.g.b();
            this.g = null;
        }
        com.amap.location.offline.d.a.a(cVar);
    }

    public synchronized void a(c cVar) {
        if (cVar != null) {
            if (this.f != null) {
                this.b = cVar;
                this.f.a(this.b);
                if (this.g != null) {
                    this.g.a(this.b);
                }
            }
        }
    }

    public synchronized boolean c() {
        return (this.f == null || this.b == null || !this.b.k || this.c == null || !this.c.a()) ? false : true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0014 A[Catch:{ all -> 0x000f }] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0021 A[Catch:{ all -> 0x000f }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0051 A[Catch:{ all -> 0x000f }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0058 A[Catch:{ all -> 0x000f }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.amap.location.common.model.AmapLoc a(@android.support.annotation.NonNull com.amap.location.common.model.FPS r4, boolean r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            r0 = 0
            if (r5 == 0) goto L_0x0011
            com.amap.location.offline.b r1 = r3.c     // Catch:{ all -> 0x000f }
            if (r1 == 0) goto L_0x0011
            com.amap.location.offline.b r1 = r3.c     // Catch:{ all -> 0x000f }
            int r1 = r1.e()     // Catch:{ all -> 0x000f }
            goto L_0x0012
        L_0x000f:
            r4 = move-exception
            goto L_0x0060
        L_0x0011:
            r1 = 0
        L_0x0012:
            if (r5 == 0) goto L_0x0021
            r2 = 100033(0x186c1, float:1.40176E-40)
            com.amap.location.offline.d.a.a(r2)     // Catch:{ all -> 0x000f }
            r2 = 100081(0x186f1, float:1.40243E-40)
            com.amap.location.offline.d.a.a(r2)     // Catch:{ all -> 0x000f }
            goto L_0x002d
        L_0x0021:
            r2 = 100034(0x186c2, float:1.40177E-40)
            com.amap.location.offline.d.a.a(r2)     // Catch:{ all -> 0x000f }
            r2 = 100082(0x186f2, float:1.40245E-40)
            com.amap.location.offline.d.a.a(r2)     // Catch:{ all -> 0x000f }
        L_0x002d:
            android.content.Context r2 = r3.d     // Catch:{ all -> 0x000f }
            java.lang.String r2 = r2.getPackageName()     // Catch:{ all -> 0x000f }
            com.amap.location.common.model.AmapLoc r4 = r3.a(r4, r1, r0, r2)     // Catch:{ all -> 0x000f }
            if (r4 == 0) goto L_0x004f
            boolean r0 = r4.isLocationCorrect()     // Catch:{ all -> 0x000f }
            if (r0 == 0) goto L_0x004f
            if (r5 == 0) goto L_0x0048
            r5 = 100083(0x186f3, float:1.40246E-40)
            com.amap.location.offline.d.a.a(r5)     // Catch:{ all -> 0x000f }
            goto L_0x005e
        L_0x0048:
            r5 = 100084(0x186f4, float:1.40248E-40)
            com.amap.location.offline.d.a.a(r5)     // Catch:{ all -> 0x000f }
            goto L_0x005e
        L_0x004f:
            if (r5 == 0) goto L_0x0058
            r5 = 100085(0x186f5, float:1.40249E-40)
            com.amap.location.offline.d.a.a(r5)     // Catch:{ all -> 0x000f }
            goto L_0x005e
        L_0x0058:
            r5 = 100086(0x186f6, float:1.4025E-40)
            com.amap.location.offline.d.a.a(r5)     // Catch:{ all -> 0x000f }
        L_0x005e:
            monitor-exit(r3)
            return r4
        L_0x0060:
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.d.a(com.amap.location.common.model.FPS, boolean):com.amap.location.common.model.AmapLoc");
    }

    /* access modifiers changed from: protected */
    public synchronized AmapLoc a(@NonNull FPS fps, int i, boolean z, String str) {
        if (!c()) {
            return null;
        }
        if (this.g == null) {
            b.a a2 = this.f.a(fps, i, str);
            if (a2.a) {
                return a2.b;
            }
            d();
        }
        return this.g.a(fps, i, z);
    }

    public synchronized void a(@NonNull FPS fps) {
        a(fps, 0, true, this.d.getPackageName());
    }

    public synchronized void a(@NonNull FPS fps, AmapLoc amapLoc) {
        a(fps, amapLoc, this.d.getPackageName());
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0019, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(@android.support.annotation.NonNull com.amap.location.common.model.FPS r2, com.amap.location.common.model.AmapLoc r3, java.lang.String r4) {
        /*
            r1 = this;
            monitor-enter(r1)
            boolean r0 = r1.c()     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x0009
            monitor-exit(r1)
            return
        L_0x0009:
            com.amap.location.offline.b.a r0 = r1.g     // Catch:{ all -> 0x0021 }
            if (r0 != 0) goto L_0x001a
            com.amap.location.offline.e r0 = r1.f     // Catch:{ all -> 0x0021 }
            boolean r2 = r0.a(r2, r3, r4)     // Catch:{ all -> 0x0021 }
            if (r2 != 0) goto L_0x0018
            r1.d()     // Catch:{ all -> 0x0021 }
        L_0x0018:
            monitor-exit(r1)
            return
        L_0x001a:
            com.amap.location.offline.b.a r4 = r1.g     // Catch:{ all -> 0x0021 }
            r4.a(r2, r3)     // Catch:{ all -> 0x0021 }
            monitor-exit(r1)
            return
        L_0x0021:
            r2 = move-exception
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.offline.d.a(com.amap.location.common.model.FPS, com.amap.location.common.model.AmapLoc, java.lang.String):void");
    }

    private void d() {
        this.g = new com.amap.location.offline.b.a(this.d, this.b, this.c);
        this.g.a();
    }
}
