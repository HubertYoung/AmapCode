package com.uc.webview.export.internal.setup;

import android.util.Pair;
import com.uc.webview.export.cyclone.UCHashMap;
import com.uc.webview.export.internal.interfaces.IWaStat;

/* compiled from: ProGuard */
public abstract class bl extends t {
    protected q d;

    /* access modifiers changed from: protected */
    public abstract q b();

    static /* synthetic */ void a(bl blVar, boolean z, int i) {
        try {
            blVar.callbackStat(new Pair(IWaStat.SETUP_TASK_ASYNC_VERIFY_FAILD, new UCHashMap().set("cnt", "1").set("result", String.valueOf(i)).set("enable", z ? "1" : "0")));
        } catch (Throwable unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0044, code lost:
        if (r5 != false) goto L_0x004d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r7 = this;
            com.uc.webview.export.internal.setup.bx r0 = new com.uc.webview.export.internal.setup.bx
            r0.<init>()
            monitor-enter(r0)
            com.uc.webview.export.internal.setup.da r1 = new com.uc.webview.export.internal.setup.da     // Catch:{ all -> 0x00ac }
            r1.<init>()     // Catch:{ all -> 0x00ac }
            java.util.concurrent.ConcurrentHashMap r2 = r7.mOptions     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.UCSubSetupTask r1 = r1.setOptions(r2)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.da r1 = (com.uc.webview.export.internal.setup.da) r1     // Catch:{ all -> 0x00ac }
            r1.b = r0     // Catch:{ all -> 0x00ac }
            java.util.concurrent.ConcurrentHashMap r2 = r1.mOptions     // Catch:{ all -> 0x00ac }
            java.lang.String r3 = "VERIFY_POLICY"
            java.lang.Object r2 = r2.get(r3)     // Catch:{ all -> 0x00ac }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ all -> 0x00ac }
            r1.c = r2     // Catch:{ all -> 0x00ac }
            java.lang.Integer r2 = r1.c     // Catch:{ all -> 0x00ac }
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x0046
            java.lang.Integer r2 = r1.c     // Catch:{ all -> 0x00ac }
            int r2 = r2.intValue()     // Catch:{ all -> 0x00ac }
            r2 = r2 & 16
            if (r2 == 0) goto L_0x0033
            r2 = 1
            goto L_0x0034
        L_0x0033:
            r2 = 0
        L_0x0034:
            java.lang.Integer r5 = r1.c     // Catch:{ all -> 0x00ac }
            int r5 = r5.intValue()     // Catch:{ all -> 0x00ac }
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = r5 & r6
            if (r5 == 0) goto L_0x0041
            r5 = 1
            goto L_0x0042
        L_0x0041:
            r5 = 0
        L_0x0042:
            if (r2 == 0) goto L_0x0046
            if (r5 != 0) goto L_0x004d
        L_0x0046:
            com.uc.webview.export.internal.setup.bx r2 = r1.b     // Catch:{ all -> 0x00ac }
            r5 = 7
            r6 = 0
            r2.a(r5, r6)     // Catch:{ all -> 0x00ac }
        L_0x004d:
            com.uc.webview.export.internal.setup.q r2 = r7.b()     // Catch:{ all -> 0x00ac }
            r5 = 10001(0x2711, float:1.4014E-41)
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x00ac }
            r4[r3] = r7     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r5, r4)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.q r2 = (com.uc.webview.export.internal.setup.q) r2     // Catch:{ all -> 0x00ac }
            java.util.concurrent.ConcurrentHashMap r3 = r7.mOptions     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.UCSubSetupTask r2 = r2.setOptions(r3)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.q r2 = (com.uc.webview.export.internal.setup.q) r2     // Catch:{ all -> 0x00ac }
            java.lang.String r3 = "verify_task"
            com.uc.webview.export.internal.setup.UCSubSetupTask r1 = r2.setup(r3, r1)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.q r1 = (com.uc.webview.export.internal.setup.q) r1     // Catch:{ all -> 0x00ac }
            java.lang.String r2 = "stat"
            com.uc.webview.export.internal.setup.UCSubSetupTask$a r3 = new com.uc.webview.export.internal.setup.UCSubSetupTask$a     // Catch:{ all -> 0x00ac }
            r3.<init>()     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.UCAsyncTask r1 = r1.onEvent(r2, r3)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.q r1 = (com.uc.webview.export.internal.setup.q) r1     // Catch:{ all -> 0x00ac }
            java.lang.String r2 = "exception"
            com.uc.webview.export.internal.setup.UCAsyncTask$b r3 = new com.uc.webview.export.internal.setup.UCAsyncTask$b     // Catch:{ all -> 0x00ac }
            r3.<init>()     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.UCAsyncTask r1 = r1.onEvent(r2, r3)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.q r1 = (com.uc.webview.export.internal.setup.q) r1     // Catch:{ all -> 0x00ac }
            java.lang.String r2 = "stop"
            com.uc.webview.export.internal.setup.UCAsyncTask$c r3 = new com.uc.webview.export.internal.setup.UCAsyncTask$c     // Catch:{ all -> 0x00ac }
            r3.<init>()     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.UCAsyncTask r1 = r1.onEvent(r2, r3)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.q r1 = (com.uc.webview.export.internal.setup.q) r1     // Catch:{ all -> 0x00ac }
            java.lang.String r2 = "success"
            com.uc.webview.export.internal.setup.bm r3 = new com.uc.webview.export.internal.setup.bm     // Catch:{ all -> 0x00ac }
            r3.<init>(r7, r0)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.UCAsyncTask r1 = r1.onEvent(r2, r3)     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.q r1 = (com.uc.webview.export.internal.setup.q) r1     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.UCAsyncTask r1 = r1.start()     // Catch:{ all -> 0x00ac }
            com.uc.webview.export.internal.setup.q r1 = (com.uc.webview.export.internal.setup.q) r1     // Catch:{ all -> 0x00ac }
            r7.d = r1     // Catch:{ all -> 0x00ac }
            monitor-exit(r0)     // Catch:{ all -> 0x00ac }
            return
        L_0x00ac:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ac }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.bl.run():void");
    }
}
