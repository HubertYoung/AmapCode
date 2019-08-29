package defpackage;

import com.autonavi.ae.bl.map.IMapPageConstant;
import java.util.Random;
import java.util.TreeSet;

/* renamed from: ab reason: default package */
/* compiled from: ByteArrayPool */
public final class ab {
    private final TreeSet<aa> a = new TreeSet<>();
    private final aa b = aa.a(0);
    private final Random c = new Random();
    private long d = 0;

    /* renamed from: ab$a */
    /* compiled from: ByteArrayPool */
    public static class a {
        public static ab a = new ab();
    }

    public final synchronized void a(aa aaVar) {
        aa aaVar2;
        if (aaVar.b < 524288) {
            this.d += (long) aaVar.b;
            this.a.add(aaVar);
            while (this.d > IMapPageConstant.BL_MAP_FLAG_MAP_STATE_IS_SHOW_BUILD_TEXTURE) {
                if (this.c.nextBoolean()) {
                    aaVar2 = this.a.pollFirst();
                } else {
                    aaVar2 = this.a.pollLast();
                }
                this.d -= (long) aaVar2.b;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0038, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized defpackage.aa a(int r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 524288(0x80000, float:7.34684E-40)
            if (r6 < r0) goto L_0x000d
            aa r6 = defpackage.aa.a(r6)     // Catch:{ all -> 0x000b }
            monitor-exit(r5)
            return r6
        L_0x000b:
            r6 = move-exception
            goto L_0x0039
        L_0x000d:
            aa r0 = r5.b     // Catch:{ all -> 0x000b }
            r0.b = r6     // Catch:{ all -> 0x000b }
            java.util.TreeSet<aa> r0 = r5.a     // Catch:{ all -> 0x000b }
            aa r1 = r5.b     // Catch:{ all -> 0x000b }
            java.lang.Object r0 = r0.ceiling(r1)     // Catch:{ all -> 0x000b }
            aa r0 = (defpackage.aa) r0     // Catch:{ all -> 0x000b }
            if (r0 != 0) goto L_0x0022
            aa r0 = defpackage.aa.a(r6)     // Catch:{ all -> 0x000b }
            goto L_0x0037
        L_0x0022:
            byte[] r6 = r0.a     // Catch:{ all -> 0x000b }
            r1 = 0
            java.util.Arrays.fill(r6, r1)     // Catch:{ all -> 0x000b }
            r0.c = r1     // Catch:{ all -> 0x000b }
            java.util.TreeSet<aa> r6 = r5.a     // Catch:{ all -> 0x000b }
            r6.remove(r0)     // Catch:{ all -> 0x000b }
            long r1 = r5.d     // Catch:{ all -> 0x000b }
            int r6 = r0.b     // Catch:{ all -> 0x000b }
            long r3 = (long) r6     // Catch:{ all -> 0x000b }
            long r1 = r1 - r3
            r5.d = r1     // Catch:{ all -> 0x000b }
        L_0x0037:
            monitor-exit(r5)
            return r0
        L_0x0039:
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ab.a(int):aa");
    }
}
