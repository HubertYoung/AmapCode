package defpackage;

import com.autonavi.jni.ae.gmap.GLMapEngine;

/* renamed from: akr reason: default package */
/* compiled from: AMapPosture */
public final class akr {
    public int a = -1;
    public GLMapEngine b = null;
    public akq c = null;

    /* access modifiers changed from: 0000 */
    public final void a(int i, GLMapEngine gLMapEngine, akq akq) {
        this.a = i;
        this.b = gLMapEngine;
        this.c = akq;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x004b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(int r3, int r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            r0 = -1
            int r1 = r2.a     // Catch:{ all -> 0x004c }
            if (r0 == r1) goto L_0x004a
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r2.b     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x004a
            akq r0 = r2.c     // Catch:{ all -> 0x004c }
            if (r0 != 0) goto L_0x000f
            goto L_0x004a
        L_0x000f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x004c }
            java.lang.String r1 = "setMapViewLeftTop: "
            r0.<init>(r1)     // Catch:{ all -> 0x004c }
            int r1 = r2.a     // Catch:{ all -> 0x004c }
            r0.append(r1)     // Catch:{ all -> 0x004c }
            java.lang.String r1 = ", "
            r0.append(r1)     // Catch:{ all -> 0x004c }
            r0.append(r3)     // Catch:{ all -> 0x004c }
            java.lang.String r1 = ", "
            r0.append(r1)     // Catch:{ all -> 0x004c }
            r0.append(r4)     // Catch:{ all -> 0x004c }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x004c }
            defpackage.akq.b(r0)     // Catch:{ all -> 0x004c }
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r2.b     // Catch:{ all -> 0x004c }
            int r1 = r2.a     // Catch:{ all -> 0x004c }
            float r3 = (float) r3     // Catch:{ all -> 0x004c }
            float r4 = (float) r4     // Catch:{ all -> 0x004c }
            r0.setMapLeftTop(r1, r3, r4)     // Catch:{ all -> 0x004c }
            com.autonavi.jni.ae.gmap.GLMapEngine r3 = r2.b     // Catch:{ all -> 0x004c }
            int r4 = r2.a     // Catch:{ all -> 0x004c }
            int r3 = r3.getBelongToRenderDeviceId(r4)     // Catch:{ all -> 0x004c }
            akq r4 = r2.c     // Catch:{ all -> 0x004c }
            r4.p(r3)     // Catch:{ all -> 0x004c }
            monitor-exit(r2)
            return
        L_0x004a:
            monitor-exit(r2)
            return
        L_0x004c:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.akr.a(int, int):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0049, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(float r3, float r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            r0 = -1
            int r1 = r2.a     // Catch:{ all -> 0x004a }
            if (r0 == r1) goto L_0x0048
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r2.b     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x0048
            akq r0 = r2.c     // Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x000f
            goto L_0x0048
        L_0x000f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x004a }
            java.lang.String r1 = "setMapViewLeftTopPercent: "
            r0.<init>(r1)     // Catch:{ all -> 0x004a }
            int r1 = r2.a     // Catch:{ all -> 0x004a }
            r0.append(r1)     // Catch:{ all -> 0x004a }
            java.lang.String r1 = ", "
            r0.append(r1)     // Catch:{ all -> 0x004a }
            r0.append(r3)     // Catch:{ all -> 0x004a }
            java.lang.String r1 = ", "
            r0.append(r1)     // Catch:{ all -> 0x004a }
            r0.append(r4)     // Catch:{ all -> 0x004a }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x004a }
            defpackage.akq.b(r0)     // Catch:{ all -> 0x004a }
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r2.b     // Catch:{ all -> 0x004a }
            int r1 = r2.a     // Catch:{ all -> 0x004a }
            r0.setMapLeftTopPercent(r1, r3, r4)     // Catch:{ all -> 0x004a }
            com.autonavi.jni.ae.gmap.GLMapEngine r3 = r2.b     // Catch:{ all -> 0x004a }
            int r4 = r2.a     // Catch:{ all -> 0x004a }
            int r3 = r3.getBelongToRenderDeviceId(r4)     // Catch:{ all -> 0x004a }
            akq r4 = r2.c     // Catch:{ all -> 0x004a }
            r4.p(r3)     // Catch:{ all -> 0x004a }
            monitor-exit(r2)
            return
        L_0x0048:
            monitor-exit(r2)
            return
        L_0x004a:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.akr.a(float, float):void");
    }
}
