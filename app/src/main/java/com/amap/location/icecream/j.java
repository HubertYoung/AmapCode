package com.amap.location.icecream;

import android.support.annotation.NonNull;

/* compiled from: IcecreamSupplier */
public class j {
    private static volatile j c;
    /* access modifiers changed from: private */
    public boy a = new boy();
    /* access modifiers changed from: private */
    public Object b = new Object();

    /* compiled from: IcecreamSupplier */
    static abstract class a {
        private volatile boolean a = false;

        public abstract void a(int i, int i2, Throwable th);

        a() {
        }

        /* access modifiers changed from: private */
        public void a() {
            this.a = true;
        }

        /* access modifiers changed from: private */
        public boolean b() {
            return this.a;
        }
    }

    protected static j a() {
        if (c == null) {
            synchronized (j.class) {
                try {
                    if (c == null) {
                        c = new j();
                    }
                }
            }
        }
        return c;
    }

    private j() {
    }

    /* access modifiers changed from: protected */
    public void a(@NonNull final bph bph, @NonNull final String str, @NonNull final a aVar) {
        new Thread() {
            /* JADX WARNING: Removed duplicated region for block: B:140:0x013b A[SYNTHETIC] */
            /* JADX WARNING: Removed duplicated region for block: B:153:0x0154 A[SYNTHETIC] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r12 = this;
                    r0 = 0
                    r1 = 3
                    r2 = 0
                    com.amap.location.icecream.j$a r3 = r4     // Catch:{ Throwable -> 0x014b, all -> 0x0130 }
                    boolean r3 = r3.b()     // Catch:{ Throwable -> 0x014b, all -> 0x0130 }
                    if (r3 == 0) goto L_0x0022
                    com.amap.location.icecream.j r3 = com.amap.location.icecream.j.this
                    java.lang.Object r3 = r3.b
                    monitor-enter(r3)
                    defpackage.bow.a(r2)     // Catch:{ all -> 0x001f }
                    monitor-exit(r3)     // Catch:{ all -> 0x001f }
                    defpackage.bow.a(r2)
                    com.amap.location.icecream.j$a r3 = r4
                    r3.a(r1, r0, r2)
                    return
                L_0x001f:
                    r0 = move-exception
                    monitor-exit(r3)     // Catch:{ all -> 0x001f }
                    throw r0
                L_0x0022:
                    com.amap.location.icecream.j r3 = com.amap.location.icecream.j.this     // Catch:{ Throwable -> 0x014b, all -> 0x0130 }
                    boy r3 = r3.a     // Catch:{ Throwable -> 0x014b, all -> 0x0130 }
                    bph r4 = r2     // Catch:{ Throwable -> 0x014b, all -> 0x0130 }
                    java.lang.Class<com.autonavi.core.network.inter.response.InputStreamResponse> r5 = com.autonavi.core.network.inter.response.InputStreamResponse.class
                    bpk r3 = r3.a(r4, r5)     // Catch:{ Throwable -> 0x014b, all -> 0x0130 }
                    com.autonavi.core.network.inter.response.InputStreamResponse r3 = (com.autonavi.core.network.inter.response.InputStreamResponse) r3     // Catch:{ Throwable -> 0x014b, all -> 0x0130 }
                    if (r3 != 0) goto L_0x004c
                    com.amap.location.icecream.j r1 = com.amap.location.icecream.j.this
                    java.lang.Object r4 = r1.b
                    monitor-enter(r4)
                    defpackage.bow.a(r2)     // Catch:{ all -> 0x0049 }
                    monitor-exit(r4)     // Catch:{ all -> 0x0049 }
                    defpackage.bow.a(r2)
                    com.amap.location.icecream.j$a r1 = r4
                    r3 = 4
                    r1.a(r3, r0, r2)
                    return
                L_0x0049:
                    r0 = move-exception
                    monitor-exit(r4)     // Catch:{ all -> 0x0049 }
                    throw r0
                L_0x004c:
                    int r4 = r3.getStatusCode()     // Catch:{ Throwable -> 0x014b, all -> 0x0130 }
                    r5 = 400(0x190, float:5.6E-43)
                    if (r4 < r5) goto L_0x006c
                    com.amap.location.icecream.j r0 = com.amap.location.icecream.j.this
                    java.lang.Object r5 = r0.b
                    monitor-enter(r5)
                    defpackage.bow.a(r2)     // Catch:{ all -> 0x0069 }
                    monitor-exit(r5)     // Catch:{ all -> 0x0069 }
                    defpackage.bow.a(r2)
                    com.amap.location.icecream.j$a r0 = r4
                    r1 = 6
                    r0.a(r1, r4, r2)
                    return
                L_0x0069:
                    r0 = move-exception
                    monitor-exit(r5)     // Catch:{ all -> 0x0069 }
                    throw r0
                L_0x006c:
                    java.io.InputStream r3 = r3.getBodyInputStream()     // Catch:{ Throwable -> 0x012b, all -> 0x0126 }
                    if (r3 != 0) goto L_0x008a
                    com.amap.location.icecream.j r0 = com.amap.location.icecream.j.this
                    java.lang.Object r5 = r0.b
                    monitor-enter(r5)
                    defpackage.bow.a(r2)     // Catch:{ all -> 0x0087 }
                    monitor-exit(r5)     // Catch:{ all -> 0x0087 }
                    defpackage.bow.a(r3)
                    com.amap.location.icecream.j$a r0 = r4
                    r1 = 5
                    r0.a(r1, r4, r2)
                    return
                L_0x0087:
                    r0 = move-exception
                    monitor-exit(r5)     // Catch:{ all -> 0x0087 }
                    throw r0
                L_0x008a:
                    com.amap.location.icecream.j r5 = com.amap.location.icecream.j.this     // Catch:{ Throwable -> 0x0123, all -> 0x011d }
                    java.lang.Object r5 = r5.b     // Catch:{ Throwable -> 0x0123, all -> 0x011d }
                    monitor-enter(r5)     // Catch:{ Throwable -> 0x0123, all -> 0x011d }
                    r6 = 2
                    java.io.File r7 = new java.io.File     // Catch:{ all -> 0x0110 }
                    java.lang.String r8 = r3     // Catch:{ all -> 0x0110 }
                    r7.<init>(r8)     // Catch:{ all -> 0x0110 }
                    boolean r8 = r7.exists()     // Catch:{ all -> 0x0110 }
                    if (r8 == 0) goto L_0x00bb
                    monitor-exit(r5)     // Catch:{ all -> 0x00b7 }
                    com.amap.location.icecream.j r0 = com.amap.location.icecream.j.this
                    java.lang.Object r0 = r0.b
                    monitor-enter(r0)
                    defpackage.bow.a(r2)     // Catch:{ all -> 0x00b4 }
                    monitor-exit(r0)     // Catch:{ all -> 0x00b4 }
                    defpackage.bow.a(r3)
                    com.amap.location.icecream.j$a r0 = r4
                    r0.a(r6, r4, r2)
                    return
                L_0x00b4:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x00b4 }
                    throw r1
                L_0x00b7:
                    r0 = move-exception
                    r6 = r2
                    r1 = 2
                    goto L_0x0112
                L_0x00bb:
                    java.io.BufferedOutputStream r6 = new java.io.BufferedOutputStream     // Catch:{ all -> 0x0110 }
                    java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x0110 }
                    r9 = 1
                    r8.<init>(r7, r9)     // Catch:{ all -> 0x0110 }
                    r6.<init>(r8)     // Catch:{ all -> 0x0110 }
                    r7 = 4096(0x1000, float:5.74E-42)
                    byte[] r7 = new byte[r7]     // Catch:{ all -> 0x011b }
                L_0x00ca:
                    int r8 = r3.read(r7)     // Catch:{ all -> 0x011b }
                    r10 = -1
                    if (r8 == r10) goto L_0x00f5
                    com.amap.location.icecream.j$a r10 = r4     // Catch:{ all -> 0x011b }
                    boolean r10 = r10.b()     // Catch:{ all -> 0x011b }
                    if (r10 == 0) goto L_0x00f1
                    monitor-exit(r5)     // Catch:{ all -> 0x011b }
                    com.amap.location.icecream.j r0 = com.amap.location.icecream.j.this
                    java.lang.Object r0 = r0.b
                    monitor-enter(r0)
                    defpackage.bow.a(r6)     // Catch:{ all -> 0x00ee }
                    monitor-exit(r0)     // Catch:{ all -> 0x00ee }
                    defpackage.bow.a(r3)
                    com.amap.location.icecream.j$a r0 = r4
                    r0.a(r1, r4, r2)
                    return
                L_0x00ee:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x00ee }
                    throw r1
                L_0x00f1:
                    r6.write(r7, r0, r8)     // Catch:{ all -> 0x011b }
                    goto L_0x00ca
                L_0x00f5:
                    r6.flush()     // Catch:{ all -> 0x011b }
                    monitor-exit(r5)     // Catch:{ all -> 0x011b }
                    com.amap.location.icecream.j r0 = com.amap.location.icecream.j.this
                    java.lang.Object r0 = r0.b
                    monitor-enter(r0)
                    defpackage.bow.a(r6)     // Catch:{ all -> 0x010d }
                    monitor-exit(r0)     // Catch:{ all -> 0x010d }
                    defpackage.bow.a(r3)
                    com.amap.location.icecream.j$a r0 = r4
                    r0.a(r9, r4, r2)
                    return
                L_0x010d:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x010d }
                    throw r1
                L_0x0110:
                    r0 = move-exception
                    r6 = r2
                L_0x0112:
                    monitor-exit(r5)     // Catch:{ all -> 0x011b }
                    throw r0     // Catch:{ Throwable -> 0x0116, all -> 0x0114 }
                L_0x0114:
                    r0 = move-exception
                    goto L_0x011f
                L_0x0116:
                    r0 = move-exception
                    r1 = r0
                    r0 = r4
                    r2 = r6
                    goto L_0x014d
                L_0x011b:
                    r0 = move-exception
                    goto L_0x0112
                L_0x011d:
                    r0 = move-exception
                    r6 = r2
                L_0x011f:
                    r11 = r3
                    r3 = r0
                    r0 = r11
                    goto L_0x0134
                L_0x0123:
                    r0 = move-exception
                    r1 = r0
                    goto L_0x012e
                L_0x0126:
                    r0 = move-exception
                    r3 = r0
                    r0 = r2
                    r6 = r0
                    goto L_0x0134
                L_0x012b:
                    r0 = move-exception
                    r1 = r0
                    r3 = r2
                L_0x012e:
                    r0 = r4
                    goto L_0x014d
                L_0x0130:
                    r3 = move-exception
                    r0 = r2
                    r6 = r0
                    r4 = 0
                L_0x0134:
                    com.amap.location.icecream.j r5 = com.amap.location.icecream.j.this
                    java.lang.Object r5 = r5.b
                    monitor-enter(r5)
                    defpackage.bow.a(r6)     // Catch:{ all -> 0x0148 }
                    monitor-exit(r5)     // Catch:{ all -> 0x0148 }
                    defpackage.bow.a(r0)
                    com.amap.location.icecream.j$a r0 = r4
                    r0.a(r1, r4, r2)
                    throw r3
                L_0x0148:
                    r0 = move-exception
                    monitor-exit(r5)     // Catch:{ all -> 0x0148 }
                    throw r0
                L_0x014b:
                    r1 = move-exception
                    r3 = r2
                L_0x014d:
                    com.amap.location.icecream.j r4 = com.amap.location.icecream.j.this
                    java.lang.Object r4 = r4.b
                    monitor-enter(r4)
                    defpackage.bow.a(r2)     // Catch:{ all -> 0x0162 }
                    monitor-exit(r4)     // Catch:{ all -> 0x0162 }
                    defpackage.bow.a(r3)
                    com.amap.location.icecream.j$a r2 = r4
                    r3 = 7
                    r2.a(r3, r0, r1)
                    return
                L_0x0162:
                    r0 = move-exception
                    monitor-exit(r4)     // Catch:{ all -> 0x0162 }
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.j.AnonymousClass1.run():void");
            }
        }.start();
    }

    /* access modifiers changed from: protected */
    public void a(a aVar) {
        if (aVar != null) {
            aVar.a();
        }
    }
}
