package defpackage;

import com.autonavi.jni.ae.gmap.GLMapEngine;

/* renamed from: akt reason: default package */
/* compiled from: AMapView */
public final class akt {
    public int a = -1;
    public GLMapEngine b = null;
    public akq c = null;
    public akr d = new akr();
    public boolean e = false;
    public float f = 1.0f;
    public long g = 0;
    int h = 0;
    int i = 0;
    float j = 0.0f;
    long k = 0;
    final long l = 5000;

    /* access modifiers changed from: 0000 */
    public final void a(int i2, GLMapEngine gLMapEngine, akq akq) {
        this.a = i2;
        this.b = gLMapEngine;
        this.c = akq;
        this.d.a(this.a, this.b, this.c);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x009e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(int r11, int r12, int r13, int r14) {
        /*
            r10 = this;
            monitor-enter(r10)
            r0 = -1
            int r1 = r10.a     // Catch:{ all -> 0x009f }
            if (r0 == r1) goto L_0x009d
            com.autonavi.jni.ae.gmap.GLMapEngine r0 = r10.b     // Catch:{ all -> 0x009f }
            if (r0 == 0) goto L_0x009d
            akq r0 = r10.c     // Catch:{ all -> 0x009f }
            if (r0 != 0) goto L_0x0010
            goto L_0x009d
        L_0x0010:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x009f }
            java.lang.String r1 = "setMapModeAndStyleAndSwitch: "
            r0.<init>(r1)     // Catch:{ all -> 0x009f }
            int r1 = r10.a     // Catch:{ all -> 0x009f }
            r0.append(r1)     // Catch:{ all -> 0x009f }
            java.lang.String r1 = ", "
            r0.append(r1)     // Catch:{ all -> 0x009f }
            r0.append(r11)     // Catch:{ all -> 0x009f }
            java.lang.String r1 = ", "
            r0.append(r1)     // Catch:{ all -> 0x009f }
            r0.append(r12)     // Catch:{ all -> 0x009f }
            java.lang.String r1 = ", "
            r0.append(r1)     // Catch:{ all -> 0x009f }
            r0.append(r13)     // Catch:{ all -> 0x009f }
            java.lang.String r1 = ", "
            r0.append(r1)     // Catch:{ all -> 0x009f }
            r0.append(r14)     // Catch:{ all -> 0x009f }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x009f }
            defpackage.akq.b(r0)     // Catch:{ all -> 0x009f }
            java.lang.String r0 = ""
            com.autonavi.jni.ae.gmap.GLMapEngine r1 = r10.b     // Catch:{ all -> 0x009f }
            int r2 = r10.a     // Catch:{ all -> 0x009f }
            r6 = 0
            r9 = 0
            r3 = r11
            r4 = r12
            r5 = r13
            r7 = r0
            r8 = r14
            r1.SetMapModeAndStyle(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x009f }
            com.autonavi.jni.ae.gmap.GLMapEngine r1 = r10.b     // Catch:{ all -> 0x009f }
            int r2 = r10.a     // Catch:{ all -> 0x009f }
            r6 = 0
            r9 = 1
            r3 = r11
            r4 = r12
            r5 = r13
            r7 = r0
            r8 = r14
            r1.SetMapModeAndStyle(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x009f }
            r12 = 0
            r14 = 1
            if (r11 == r14) goto L_0x0076
            r11 = 4
            if (r13 == r11) goto L_0x0076
            r11 = 6
            if (r13 == r11) goto L_0x0076
            r11 = 12
            if (r13 == r11) goto L_0x0076
            r11 = 5
            if (r13 == r11) goto L_0x0076
            r11 = 15
            if (r13 != r11) goto L_0x0077
        L_0x0076:
            r14 = 0
        L_0x0077:
            com.autonavi.jni.ae.gmap.GLMapEngine r11 = r10.b     // Catch:{ all -> 0x009f }
            int r13 = r10.a     // Catch:{ all -> 0x009f }
            r0 = 11
            boolean r11 = r11.getSrvViewStateBoolValue(r13, r0)     // Catch:{ all -> 0x009f }
            if (r11 == 0) goto L_0x0084
            goto L_0x0085
        L_0x0084:
            r12 = r14
        L_0x0085:
            com.autonavi.jni.ae.gmap.GLMapEngine r11 = r10.b     // Catch:{ all -> 0x009f }
            int r13 = r10.a     // Catch:{ all -> 0x009f }
            com.autonavi.ae.gmap.utils.GLMapStaticValue$MapPreLoadType r14 = com.autonavi.ae.gmap.utils.GLMapStaticValue.MapPreLoadType.PreLoadAll     // Catch:{ all -> 0x009f }
            r11.setMapPreLoadEnable(r13, r14, r12)     // Catch:{ all -> 0x009f }
            com.autonavi.jni.ae.gmap.GLMapEngine r11 = r10.b     // Catch:{ all -> 0x009f }
            int r12 = r10.a     // Catch:{ all -> 0x009f }
            int r11 = r11.getBelongToRenderDeviceId(r12)     // Catch:{ all -> 0x009f }
            akq r12 = r10.c     // Catch:{ all -> 0x009f }
            r12.p(r11)     // Catch:{ all -> 0x009f }
            monitor-exit(r10)
            return
        L_0x009d:
            monitor-exit(r10)
            return
        L_0x009f:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.akt.a(int, int, int, int):void");
    }
}
