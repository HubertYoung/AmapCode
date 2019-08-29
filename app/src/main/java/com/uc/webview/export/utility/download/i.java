package com.uc.webview.export.utility.download;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class i implements ValueCallback<DownloadTask> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ ValueCallback b;
    final /* synthetic */ UpdateTask c;

    i(UpdateTask updateTask, ValueCallback valueCallback, ValueCallback valueCallback2) {
        this.c = updateTask;
        this.a = valueCallback;
        this.b = valueCallback2;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:18|(1:20)(1:21)|22|23|24|(1:26)|27|28|(1:30)|31) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x00d3 */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00db A[Catch:{ Throwable -> 0x00ec }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onReceiveValue(java.lang.Object r11) {
        /*
            r10 = this;
            com.uc.webview.export.utility.download.DownloadTask r11 = (com.uc.webview.export.utility.download.DownloadTask) r11
            java.lang.Throwable r0 = r11.getException()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0040
            java.lang.Class r0 = r0.getClass()
            java.lang.String r0 = r0.getName()
            int r0 = r0.hashCode()
            java.lang.String r3 = "sdk_ucm_le"
            java.lang.String r4 = java.lang.String.valueOf(r0)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r3, r4)
            com.uc.webview.export.utility.download.UpdateTask r3 = r10.c     // Catch:{ Throwable -> 0x0040 }
            android.webkit.ValueCallback r3 = r3.i     // Catch:{ Throwable -> 0x0040 }
            if (r3 == 0) goto L_0x0040
            com.uc.webview.export.utility.download.UpdateTask r3 = r10.c     // Catch:{ Throwable -> 0x0040 }
            android.webkit.ValueCallback r3 = r3.i     // Catch:{ Throwable -> 0x0040 }
            r4 = 2
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0040 }
            r5 = 7
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ Throwable -> 0x0040 }
            r4[r1] = r5     // Catch:{ Throwable -> 0x0040 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0040 }
            r4[r2] = r0     // Catch:{ Throwable -> 0x0040 }
            r3.onReceiveValue(r4)     // Catch:{ Throwable -> 0x0040 }
        L_0x0040:
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.c
            long[] r0 = r0.c
            r3 = 3
            r4 = r0[r3]
            com.uc.webview.export.utility.download.UpdateTask r6 = r10.c
            long[] r6 = r6.c
            r7 = 4
            r8 = r6[r7]
            long r4 = r4 + r8
            r0[r3] = r4
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.c
            long[] r0 = r0.c
            r3 = r0[r3]
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.c
            long[] r0 = r0.c
            r5 = 5
            r5 = r0[r5]
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x009b
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.c
            java.lang.Object[] r0 = r0.e
            java.lang.Throwable r1 = r11.getException()
            r0[r2] = r1
            android.webkit.ValueCallback r0 = r10.a     // Catch:{ Throwable -> 0x0081 }
            if (r0 == 0) goto L_0x0081
            android.webkit.ValueCallback r0 = r10.a     // Catch:{ Throwable -> 0x0081 }
            com.uc.webview.export.utility.download.UpdateTask r1 = r10.c     // Catch:{ Throwable -> 0x0081 }
            r0.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0081 }
        L_0x0081:
            android.os.Handler r0 = new android.os.Handler
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            r0.<init>(r1)
            com.uc.webview.export.utility.download.j r1 = new com.uc.webview.export.utility.download.j
            r1.<init>(r10, r11)
            com.uc.webview.export.utility.download.UpdateTask r11 = r10.c
            long[] r11 = r11.c
            r2 = r11[r7]
            r0.postDelayed(r1, r2)
            return
        L_0x009b:
            java.lang.String r0 = "sdk_ucm_e1"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r0)
            java.lang.Throwable r0 = r11.getException()
            if (r0 == 0) goto L_0x00af
            java.lang.Throwable r11 = r11.getException()
            java.lang.String r11 = r11.getMessage()
            goto L_0x00b1
        L_0x00af:
            java.lang.String r11 = ""
        L_0x00b1:
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.c
            java.lang.Object[] r0 = r0.e
            java.lang.RuntimeException r3 = new java.lang.RuntimeException
            java.lang.String r4 = "Download aborted because of up to 10080 retries. Last exception is: "
            java.lang.String r11 = java.lang.String.valueOf(r11)
            java.lang.String r11 = r4.concat(r11)
            r3.<init>(r11)
            r0[r2] = r3
            android.webkit.ValueCallback r11 = r10.b     // Catch:{ Throwable -> 0x00d3 }
            if (r11 == 0) goto L_0x00d3
            android.webkit.ValueCallback r11 = r10.b     // Catch:{ Throwable -> 0x00d3 }
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.c     // Catch:{ Throwable -> 0x00d3 }
            r11.onReceiveValue(r0)     // Catch:{ Throwable -> 0x00d3 }
        L_0x00d3:
            com.uc.webview.export.utility.download.UpdateTask r11 = r10.c     // Catch:{ Throwable -> 0x00ec }
            android.webkit.ValueCallback r11 = r11.i     // Catch:{ Throwable -> 0x00ec }
            if (r11 == 0) goto L_0x00ec
            com.uc.webview.export.utility.download.UpdateTask r11 = r10.c     // Catch:{ Throwable -> 0x00ec }
            android.webkit.ValueCallback r11 = r11.i     // Catch:{ Throwable -> 0x00ec }
            java.lang.Object[] r0 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00ec }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x00ec }
            r0[r1] = r2     // Catch:{ Throwable -> 0x00ec }
            r11.onReceiveValue(r0)     // Catch:{ Throwable -> 0x00ec }
        L_0x00ec:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.utility.download.i.onReceiveValue(java.lang.Object):void");
    }
}
