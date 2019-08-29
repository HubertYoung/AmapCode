package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.webkit.ValueCallback;
import com.uc.webview.export.utility.download.UpdateTask;
import java.util.concurrent.Callable;

/* compiled from: ProGuard */
final class cp implements ValueCallback<UpdateTask> {
    final /* synthetic */ Context a;
    final /* synthetic */ Callable b;
    final /* synthetic */ cn c;

    cp(cn cnVar, Context context, Callable callable) {
        this.c = cnVar;
        this.a = context;
        this.b = callable;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|(2:6|(1:8))|10|(1:12)|13|14|15|(2:21|22)(2:19|20)) */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006b, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0075, code lost:
        throw new java.lang.RuntimeException(r6.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0032, code lost:
        if (r3.getAbsolutePath().startsWith(r6.getAbsolutePath()) == false) goto L_0x0034;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0044 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onReceiveValue(java.lang.Object r6) {
        /*
            r5 = this;
            r6 = 10002(0x2712, float:1.4016E-41)
            r0 = 1
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0044 }
            r3 = 0
            android.content.Context r4 = r5.a     // Catch:{ Throwable -> 0x0044 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x0044 }
            java.lang.Object r6 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r6, r2)     // Catch:{ Throwable -> 0x0044 }
            java.io.File r6 = (java.io.File) r6     // Catch:{ Throwable -> 0x0044 }
            com.uc.webview.export.internal.setup.UCMRunningInfo r2 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x0044 }
            if (r2 == 0) goto L_0x0034
            com.uc.webview.export.internal.setup.UCMPackageInfo r3 = r2.ucmPackageInfo     // Catch:{ Throwable -> 0x0044 }
            if (r3 == 0) goto L_0x0034
            com.uc.webview.export.internal.setup.UCMPackageInfo r2 = r2.ucmPackageInfo     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r2 = r2.dataDir     // Catch:{ Throwable -> 0x0044 }
            if (r2 == 0) goto L_0x0034
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0044 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r2 = r3.getAbsolutePath()     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r4 = r6.getAbsolutePath()     // Catch:{ Throwable -> 0x0044 }
            boolean r2 = r2.startsWith(r4)     // Catch:{ Throwable -> 0x0044 }
            if (r2 != 0) goto L_0x0035
        L_0x0034:
            r3 = r1
        L_0x0035:
            if (r3 != 0) goto L_0x0041
            com.uc.webview.export.internal.setup.cn r2 = r5.c     // Catch:{ Throwable -> 0x0044 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r2.a     // Catch:{ Throwable -> 0x0044 }
            java.io.File r3 = r2.getUpdateDir()     // Catch:{ Throwable -> 0x0044 }
        L_0x0041:
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r6, r0, r3)     // Catch:{ Throwable -> 0x0044 }
        L_0x0044:
            java.util.concurrent.Callable r6 = r5.b     // Catch:{ Exception -> 0x006b }
            if (r6 == 0) goto L_0x005e
            java.util.concurrent.Callable r6 = r5.b     // Catch:{ Exception -> 0x006b }
            java.lang.Object r6 = r6.call()     // Catch:{ Exception -> 0x006b }
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ Exception -> 0x006b }
            boolean r6 = r6.booleanValue()     // Catch:{ Exception -> 0x006b }
            if (r6 != 0) goto L_0x005e
            java.lang.RuntimeException r6 = new java.lang.RuntimeException     // Catch:{ Exception -> 0x006b }
            java.lang.String r0 = "Update should be in wifi network."
            r6.<init>(r0)     // Catch:{ Exception -> 0x006b }
            throw r6     // Catch:{ Exception -> 0x006b }
        L_0x005e:
            com.uc.webview.export.internal.setup.cn r6 = r5.c     // Catch:{ Exception -> 0x006b }
            android.util.Pair r0 = new android.util.Pair     // Catch:{ Exception -> 0x006b }
            java.lang.String r2 = "sdk_ucm_wifi"
            r0.<init>(r2, r1)     // Catch:{ Exception -> 0x006b }
            r6.callbackStat(r0)     // Catch:{ Exception -> 0x006b }
            return
        L_0x006b:
            r6 = move-exception
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r6 = r6.getMessage()
            r0.<init>(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.cp.onReceiveValue(java.lang.Object):void");
    }
}
