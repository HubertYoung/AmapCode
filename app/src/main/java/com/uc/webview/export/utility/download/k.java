package com.uc.webview.export.utility.download;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class k implements ValueCallback<DownloadTask> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ UpdateTask b;

    k(UpdateTask updateTask, ValueCallback valueCallback) {
        this.b = updateTask;
        this.a = valueCallback;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(21:0|1|2|(1:4)(1:5)|6|(2:8|(1:10)(10:11|13|(6:17|(1:19)(1:20)|21|(2:23|(1:25)(2:26|28))|27|28)|29|30|(1:32)(1:33)|34|(2:36|(1:38)(1:39))|40|(1:49)(2:44|46)))|12|13|17|(0)(0)|21|(0)|27|28|29|30|(0)(0)|34|(0)|40|(2:42|49)(1:48)) */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x008a */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006e A[Catch:{ Throwable -> 0x008a }] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0070 A[Catch:{ Throwable -> 0x008a }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0079 A[Catch:{ Throwable -> 0x008a }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0092 A[Catch:{ Throwable -> 0x00be }] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0094 A[Catch:{ Throwable -> 0x00be }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00a0 A[Catch:{ Throwable -> 0x00be }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00aa A[Catch:{ Throwable -> 0x00be }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onReceiveValue(java.lang.Object r15) {
        /*
            r14 = this;
            com.uc.webview.export.utility.download.DownloadTask r15 = (com.uc.webview.export.utility.download.DownloadTask) r15
            r0 = -1
            r1 = 100
            r3 = 0
            long r5 = r15.getTotalSize()     // Catch:{ Throwable -> 0x008a }
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            r8 = 10
            if (r7 != 0) goto L_0x0013
            r5 = r3
            goto L_0x001c
        L_0x0013:
            long r10 = r15.getCurrentSize()     // Catch:{ Throwable -> 0x008a }
            long r10 = r10 * r8
            long r10 = r10 / r5
            long r5 = r10 * r8
        L_0x001c:
            int r7 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r7 > 0) goto L_0x0027
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r7 >= 0) goto L_0x0025
            goto L_0x0027
        L_0x0025:
            int r5 = (int) r5     // Catch:{ Throwable -> 0x008a }
            goto L_0x0028
        L_0x0027:
            r5 = -1
        L_0x0028:
            com.uc.webview.export.utility.download.UpdateTask r6 = r14.b     // Catch:{ Throwable -> 0x008a }
            int r6 = r6.f     // Catch:{ Throwable -> 0x008a }
            if (r5 > r6) goto L_0x0034
            r6 = 100
            if (r5 != r6) goto L_0x008a
        L_0x0034:
            com.uc.webview.export.utility.download.UpdateTask r6 = r14.b     // Catch:{ Throwable -> 0x008a }
            com.uc.webview.export.utility.download.UpdateTask r7 = r14.b     // Catch:{ Throwable -> 0x008a }
            int r7 = r7.f     // Catch:{ Throwable -> 0x008a }
            int r7 = r7 + 10
            r6.f = r7     // Catch:{ Throwable -> 0x008a }
            java.lang.String r6 = "sdk_ucm_p"
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x008a }
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r6, r5)     // Catch:{ Throwable -> 0x008a }
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x008a }
            java.lang.String r6 = r15.getFilePath()     // Catch:{ Throwable -> 0x008a }
            r5.<init>(r6)     // Catch:{ Throwable -> 0x008a }
            long r6 = r5.getTotalSpace()     // Catch:{ Throwable -> 0x008a }
            long r10 = r5.getFreeSpace()     // Catch:{ Throwable -> 0x008a }
            r12 = 1048576(0x100000, double:5.180654E-318)
            long r12 = r10 / r12
            java.lang.String r5 = "sdk_ucm_dm"
            int r12 = (int) r12     // Catch:{ Throwable -> 0x008a }
            java.lang.String r12 = java.lang.String.valueOf(r12)     // Catch:{ Throwable -> 0x008a }
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r12)     // Catch:{ Throwable -> 0x008a }
            int r5 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r5 != 0) goto L_0x0070
            r5 = r3
            goto L_0x0075
        L_0x0070:
            long r10 = r10 * r8
            long r10 = r10 / r6
            long r5 = r10 * r8
        L_0x0075:
            int r7 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r7 > 0) goto L_0x0080
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r7 >= 0) goto L_0x007e
            goto L_0x0080
        L_0x007e:
            int r5 = (int) r5     // Catch:{ Throwable -> 0x008a }
            goto L_0x0081
        L_0x0080:
            r5 = -1
        L_0x0081:
            java.lang.String r6 = "sdk_ucm_dp"
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x008a }
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r6, r5)     // Catch:{ Throwable -> 0x008a }
        L_0x008a:
            long r5 = r15.getTotalSize()     // Catch:{ Throwable -> 0x00be }
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r7 != 0) goto L_0x0094
            r5 = r3
            goto L_0x009c
        L_0x0094:
            long r7 = r15.getCurrentSize()     // Catch:{ Throwable -> 0x00be }
            long r7 = r7 * r1
            long r5 = r7 / r5
        L_0x009c:
            int r15 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r15 > 0) goto L_0x00a6
            int r15 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r15 >= 0) goto L_0x00a5
            goto L_0x00a6
        L_0x00a5:
            int r0 = (int) r5     // Catch:{ Throwable -> 0x00be }
        L_0x00a6:
            android.webkit.ValueCallback r15 = r14.a     // Catch:{ Throwable -> 0x00be }
            if (r15 == 0) goto L_0x00be
            com.uc.webview.export.utility.download.UpdateTask r15 = r14.b     // Catch:{ Throwable -> 0x00be }
            int r15 = r15.g     // Catch:{ Throwable -> 0x00be }
            if (r0 <= r15) goto L_0x00be
            com.uc.webview.export.utility.download.UpdateTask r15 = r14.b     // Catch:{ Throwable -> 0x00be }
            r15.g = r0     // Catch:{ Throwable -> 0x00be }
            android.webkit.ValueCallback r15 = r14.a     // Catch:{ Throwable -> 0x00be }
            com.uc.webview.export.utility.download.UpdateTask r0 = r14.b     // Catch:{ Throwable -> 0x00be }
            r15.onReceiveValue(r0)     // Catch:{ Throwable -> 0x00be }
        L_0x00be:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.utility.download.k.onReceiveValue(java.lang.Object):void");
    }
}
