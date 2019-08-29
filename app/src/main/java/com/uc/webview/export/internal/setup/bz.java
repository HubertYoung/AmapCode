package com.uc.webview.export.internal.setup;

import android.os.Handler;
import android.os.Looper;

/* compiled from: ProGuard */
final class bz extends Handler {
    final /* synthetic */ by a;
    private UCAsyncTask b = null;

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    bz(by byVar, Looper looper) {
        // this.a = byVar;
        super(looper);
    }

    private static void a(UCAsyncTask uCAsyncTask) {
        if (uCAsyncTask != null) {
            synchronized (uCAsyncTask.d) {
                uCAsyncTask.mPercent = (int) ((((float) UCAsyncTask.e(uCAsyncTask)) * 100.0f) / ((float) ((Integer) uCAsyncTask.invokeO(UCAsyncTask.getTaskCount, new Object[0])).intValue()));
            }
            uCAsyncTask.callback("progress");
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:14|15|16|17) */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x010b, code lost:
        r0 = r8;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0036 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:66:0x00ca */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x0158  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x0164  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0179 A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x001d A[Catch:{ Throwable -> 0x00a4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0049 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0055 A[SYNTHETIC, Splitter:B:27:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x00d4 A[Catch:{ Throwable -> 0x00d7 }] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00df A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void dispatchMessage(android.os.Message r11) {
        /*
            r10 = this;
            r0 = 0
            java.lang.Boolean r1 = com.uc.webview.export.internal.setup.UCAsyncTask.n     // Catch:{ Throwable -> 0x0011 }
            boolean r1 = r1.booleanValue()     // Catch:{ Throwable -> 0x0011 }
            if (r1 == 0) goto L_0x0011
            com.uc.webview.export.cyclone.UCElapseTime r1 = new com.uc.webview.export.cyclone.UCElapseTime     // Catch:{ Throwable -> 0x0011 }
            r1.<init>()     // Catch:{ Throwable -> 0x0011 }
            goto L_0x0012
        L_0x0011:
            r1 = r0
        L_0x0012:
            r2 = 10012(0x271c, float:1.403E-41)
            r3 = 0
            java.lang.Runnable r4 = r11.getCallback()     // Catch:{ Throwable -> 0x00a4 }
            boolean r5 = r4 instanceof com.uc.webview.export.internal.setup.UCAsyncTask     // Catch:{ Throwable -> 0x00a4 }
            if (r5 == 0) goto L_0x0042
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = (com.uc.webview.export.internal.setup.UCAsyncTask) r4     // Catch:{ Throwable -> 0x00a4 }
            r10.b = r4     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b     // Catch:{ Throwable -> 0x00a4 }
            long r4 = r4.l     // Catch:{ Throwable -> 0x00a4 }
            r6 = 0
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 <= 0) goto L_0x003b
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b     // Catch:{ Throwable -> 0x0036 }
            long r4 = r4.l     // Catch:{ Throwable -> 0x0036 }
            java.lang.Thread.sleep(r4)     // Catch:{ Throwable -> 0x0036 }
        L_0x0036:
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b     // Catch:{ Throwable -> 0x00a4 }
            r4.l = 0     // Catch:{ Throwable -> 0x00a4 }
        L_0x003b:
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b     // Catch:{ Throwable -> 0x00a4 }
            java.lang.String r5 = "start"
            r4.callback(r5)     // Catch:{ Throwable -> 0x00a4 }
        L_0x0042:
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.bx r4 = r4.h     // Catch:{ Throwable -> 0x00a4 }
            monitor-enter(r4)     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x00a1 }
            boolean r5 = r5.g     // Catch:{ all -> 0x00a1 }
            if (r5 == 0) goto L_0x0052
            r11 = r0
        L_0x0052:
            monitor-exit(r4)     // Catch:{ all -> 0x00a1 }
            if (r11 == 0) goto L_0x0090
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.by r5 = r10.a     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r5.a     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.bx r5 = r5.h     // Catch:{ Throwable -> 0x00a4 }
            monitor-enter(r5)     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.by r6 = r10.a     // Catch:{ all -> 0x008d }
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = r6.a     // Catch:{ all -> 0x008d }
            boolean r6 = r6.f     // Catch:{ all -> 0x008d }
            if (r6 == 0) goto L_0x008b
            com.uc.webview.export.internal.setup.by r6 = r10.a     // Catch:{ all -> 0x008d }
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = r6.a     // Catch:{ all -> 0x008d }
            r6.f = false     // Catch:{ all -> 0x008d }
            java.lang.String r6 = "pause"
            r4.callback(r6)     // Catch:{ all -> 0x008d }
            com.uc.webview.export.internal.setup.by r6 = r10.a     // Catch:{ all -> 0x008d }
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = r6.a     // Catch:{ all -> 0x008d }
            com.uc.webview.export.internal.setup.bx r6 = r6.h     // Catch:{ all -> 0x008d }
            r7 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r6.a(r7)     // Catch:{ all -> 0x008d }
            java.lang.String r6 = "resume"
            r4.callback(r6)     // Catch:{ all -> 0x008d }
        L_0x008b:
            monitor-exit(r5)     // Catch:{ all -> 0x008d }
            goto L_0x0090
        L_0x008d:
            r11 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x008d }
            throw r11     // Catch:{ Throwable -> 0x00a4 }
        L_0x0090:
            if (r11 == 0) goto L_0x00ca
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.UCSetupException r4 = r4.mException     // Catch:{ Throwable -> 0x00a4 }
            if (r4 != 0) goto L_0x00ca
            super.dispatchMessage(r11)     // Catch:{ Throwable -> 0x00a4 }
            com.uc.webview.export.internal.setup.UCAsyncTask r11 = r10.b     // Catch:{ Throwable -> 0x00a4 }
            a(r11)     // Catch:{ Throwable -> 0x00a4 }
            goto L_0x00ca
        L_0x00a1:
            r11 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00a1 }
            throw r11     // Catch:{ Throwable -> 0x00a4 }
        L_0x00a4:
            r11 = move-exception
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b
            boolean r5 = r11 instanceof com.uc.webview.export.internal.setup.UCSetupException
            if (r5 == 0) goto L_0x00ae
            com.uc.webview.export.internal.setup.UCSetupException r11 = (com.uc.webview.export.internal.setup.UCSetupException) r11
            goto L_0x00b4
        L_0x00ae:
            com.uc.webview.export.internal.setup.UCSetupException r5 = new com.uc.webview.export.internal.setup.UCSetupException
            r5.<init>(r11)
            r11 = r5
        L_0x00b4:
            r4.setException(r11)
            com.uc.webview.export.internal.setup.UCAsyncTask r11 = r10.b     // Catch:{ Throwable -> 0x00ca }
            java.lang.Object r11 = r11.d     // Catch:{ Throwable -> 0x00ca }
            monitor-enter(r11)     // Catch:{ Throwable -> 0x00ca }
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b     // Catch:{ all -> 0x00c7 }
            java.lang.Object[] r5 = new java.lang.Object[r3]     // Catch:{ all -> 0x00c7 }
            r4.invoke(r2, r5)     // Catch:{ all -> 0x00c7 }
            monitor-exit(r11)     // Catch:{ all -> 0x00c7 }
            goto L_0x00ca
        L_0x00c7:
            r4 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x00c7 }
            throw r4     // Catch:{ Throwable -> 0x00ca }
        L_0x00ca:
            java.lang.Boolean r11 = com.uc.webview.export.internal.setup.UCAsyncTask.n     // Catch:{ Throwable -> 0x00d7 }
            boolean r11 = r11.booleanValue()     // Catch:{ Throwable -> 0x00d7 }
            if (r11 == 0) goto L_0x00d7
            com.uc.webview.export.internal.setup.UCAsyncTask r11 = r10.b     // Catch:{ Throwable -> 0x00d7 }
            goto L_0x00d8
        L_0x00d7:
            r11 = r0
        L_0x00d8:
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r10.b
            java.lang.Object r4 = r4.d
            monitor-enter(r4)
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            com.uc.webview.export.internal.setup.UCSetupException r5 = r5.mException     // Catch:{ all -> 0x01b9 }
            r6 = 1
            if (r5 == 0) goto L_0x00e8
            r5 = 1
            goto L_0x00e9
        L_0x00e8:
            r5 = 0
        L_0x00e9:
            com.uc.webview.export.internal.setup.UCAsyncTask r7 = r10.b     // Catch:{ all -> 0x01b9 }
            boolean r7 = r7.g     // Catch:{ all -> 0x01b9 }
            if (r5 != 0) goto L_0x00f3
            if (r7 == 0) goto L_0x00fa
        L_0x00f3:
            com.uc.webview.export.internal.setup.UCAsyncTask r8 = r10.b     // Catch:{ all -> 0x01b9 }
            java.lang.Object[] r9 = new java.lang.Object[r3]     // Catch:{ all -> 0x01b9 }
            r8.invoke(r2, r9)     // Catch:{ all -> 0x01b9 }
        L_0x00fa:
            com.uc.webview.export.internal.setup.UCAsyncTask r8 = r10.b     // Catch:{ all -> 0x01b9 }
            java.util.concurrent.ConcurrentLinkedQueue r8 = r8.b     // Catch:{ all -> 0x01b9 }
            if (r8 == 0) goto L_0x011b
            java.lang.Object r8 = r8.poll()     // Catch:{ Throwable -> 0x010f }
            java.lang.Runnable r8 = (java.lang.Runnable) r8     // Catch:{ Throwable -> 0x010f }
            if (r8 == 0) goto L_0x010d
            monitor-exit(r4)     // Catch:{ all -> 0x01b9 }
            r0 = r8
            goto L_0x0156
        L_0x010d:
            r0 = r8
            goto L_0x011b
        L_0x010f:
            r5 = move-exception
            com.uc.webview.export.internal.setup.UCAsyncTask r8 = r10.b     // Catch:{ all -> 0x01b9 }
            com.uc.webview.export.internal.setup.UCSetupException r9 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ all -> 0x01b9 }
            r9.<init>(r5)     // Catch:{ all -> 0x01b9 }
            r8.setException(r9)     // Catch:{ all -> 0x01b9 }
            r5 = 1
        L_0x011b:
            if (r7 == 0) goto L_0x0125
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            java.lang.String r6 = "stop"
            r5.callback(r6)     // Catch:{ all -> 0x01b9 }
            goto L_0x013d
        L_0x0125:
            if (r5 == 0) goto L_0x012f
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            java.lang.String r6 = "exception"
            r5.callback(r6)     // Catch:{ all -> 0x01b9 }
            goto L_0x0136
        L_0x012f:
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            java.lang.String r6 = "success"
            r5.callback(r6)     // Catch:{ all -> 0x01b9 }
        L_0x0136:
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            java.lang.String r6 = "gone"
            r5.callback(r6)     // Catch:{ all -> 0x01b9 }
        L_0x013d:
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            java.lang.String r6 = "die"
            r5.callback(r6)     // Catch:{ all -> 0x01b9 }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r5.a     // Catch:{ all -> 0x01b9 }
            r10.b = r5     // Catch:{ all -> 0x01b9 }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            a(r5)     // Catch:{ all -> 0x01b9 }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = r10.b     // Catch:{ all -> 0x01b9 }
            if (r5 != 0) goto L_0x01b6
            monitor-exit(r4)     // Catch:{ all -> 0x01b9 }
        L_0x0156:
            if (r0 == 0) goto L_0x0164
            com.uc.webview.export.internal.setup.by r2 = r10.a
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.a
            android.os.Handler r2 = r2.j
            r2.post(r0)
            goto L_0x016f
        L_0x0164:
            com.uc.webview.export.internal.setup.by r0 = r10.a
            com.uc.webview.export.internal.setup.UCAsyncTask r0 = r0.a
            r2 = 10008(0x2718, float:1.4024E-41)
            java.lang.Object[] r3 = new java.lang.Object[r3]
            r0.invokeO(r2, r3)
        L_0x016f:
            java.lang.Boolean r0 = com.uc.webview.export.internal.setup.UCAsyncTask.n     // Catch:{ Throwable -> 0x01b5 }
            boolean r0 = r0.booleanValue()     // Catch:{ Throwable -> 0x01b5 }
            if (r0 == 0) goto L_0x01b4
            com.uc.webview.export.internal.setup.by r0 = r10.a     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.internal.setup.UCAsyncTask r0 = r0.a     // Catch:{ Throwable -> 0x01b5 }
            java.util.Vector r0 = r0.o     // Catch:{ Throwable -> 0x01b5 }
            android.util.Pair r2 = new android.util.Pair     // Catch:{ Throwable -> 0x01b5 }
            if (r11 != 0) goto L_0x0188
            java.lang.String r11 = "null"
            goto L_0x0190
        L_0x0188:
            java.lang.Class r11 = r11.getClass()     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r11 = r11.getSimpleName()     // Catch:{ Throwable -> 0x01b5 }
        L_0x0190:
            android.util.Pair r3 = new android.util.Pair     // Catch:{ Throwable -> 0x01b5 }
            long r4 = r1.getMilis()     // Catch:{ Throwable -> 0x01b5 }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x01b5 }
            long r5 = r1.getMilisCpu()     // Catch:{ Throwable -> 0x01b5 }
            java.lang.Long r1 = java.lang.Long.valueOf(r5)     // Catch:{ Throwable -> 0x01b5 }
            r3.<init>(r4, r1)     // Catch:{ Throwable -> 0x01b5 }
            r2.<init>(r11, r3)     // Catch:{ Throwable -> 0x01b5 }
            r0.add(r2)     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.internal.setup.by r11 = r10.a     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.internal.setup.UCAsyncTask r11 = r11.a     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r0 = "cost"
            r11.callback(r0)     // Catch:{ Throwable -> 0x01b5 }
        L_0x01b4:
            return
        L_0x01b5:
            return
        L_0x01b6:
            monitor-exit(r4)     // Catch:{ all -> 0x01b9 }
            goto L_0x00d8
        L_0x01b9:
            r11 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x01b9 }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.bz.dispatchMessage(android.os.Message):void");
    }
}
