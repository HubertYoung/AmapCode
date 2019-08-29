package com.alipay.sdk.app;

public class H5AuthActivity extends H5PayActivity {
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0009 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r2 = this;
            java.lang.Object r0 = com.alipay.sdk.app.AuthTask.a
            monitor-enter(r0)
            r0.notify()     // Catch:{ Exception -> 0x0009 }
            goto L_0x0009
        L_0x0007:
            r1 = move-exception
            goto L_0x000b
        L_0x0009:
            monitor-exit(r0)     // Catch:{ all -> 0x0007 }
            return
        L_0x000b:
            monitor-exit(r0)     // Catch:{ all -> 0x0007 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.H5AuthActivity.a():void");
    }
}
