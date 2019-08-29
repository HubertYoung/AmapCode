package com.uc.webview.export.internal.setup;

import android.util.Pair;
import com.uc.webview.export.cyclone.UCElapseTime;

/* compiled from: ProGuard */
public final class bx {
    boolean a = false;
    private Pair<Integer, Object> b = new Pair<>(Integer.valueOf(-1), null);

    /* JADX WARNING: Can't wrap try/catch for region: R(6:1|2|3|4|5|6) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x000f */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r2, java.lang.Object r3) {
        /*
            r1 = this;
            monitor-enter(r1)
            android.util.Pair r0 = new android.util.Pair     // Catch:{ all -> 0x0011 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x0011 }
            r0.<init>(r2, r3)     // Catch:{ all -> 0x0011 }
            r1.b = r0     // Catch:{ all -> 0x0011 }
            r1.notify()     // Catch:{ Exception -> 0x000f }
        L_0x000f:
            monitor-exit(r1)     // Catch:{ all -> 0x0011 }
            return
        L_0x0011:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0011 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.bx.a(int, java.lang.Object):void");
    }

    public final Pair<Integer, Object> a(long j) {
        UCElapseTime uCElapseTime = new UCElapseTime();
        synchronized (this) {
            if (((Integer) this.b.first).intValue() != -1) {
                Pair<Integer, Object> pair = this.b;
                return pair;
            }
            long j2 = 0;
            int i = (j > 0 ? 1 : (j == 0 ? 0 : -1));
            if (i > 0) {
                j2 = 100;
            }
            this.a = true;
            while (true) {
                if (uCElapseTime.getMilis() >= j) {
                    if (i > 0) {
                        this.a = false;
                        return new Pair<>(Integer.valueOf(1), null);
                    }
                }
                try {
                    wait(Math.max(j2, j - uCElapseTime.getMilis()));
                    if (((Integer) this.b.first).intValue() != -1) {
                        this.a = false;
                        Pair<Integer, Object> pair2 = this.b;
                        return pair2;
                    }
                } catch (InterruptedException unused) {
                }
            }
        }
    }
}
