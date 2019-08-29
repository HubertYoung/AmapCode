package com.uc.webview.export.utility.download;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class h implements ValueCallback<DownloadTask> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ ValueCallback b;
    final /* synthetic */ ValueCallback c;
    final /* synthetic */ ValueCallback d;
    final /* synthetic */ UpdateTask e;

    h(UpdateTask updateTask, ValueCallback valueCallback, ValueCallback valueCallback2, ValueCallback valueCallback3, ValueCallback valueCallback4) {
        this.e = updateTask;
        this.a = valueCallback;
        this.b = valueCallback2;
        this.c = valueCallback3;
        this.d = valueCallback4;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(14:12|13|(1:15)(1:16)|17|(2:19|(1:21)(9:22|24|25|26|(4:28|29|30|(1:32))|33|34|(1:36)|37))|23|24|25|26|(0)|33|34|(0)|37) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:4|5|6|(1:8)|9|10|11) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0070 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x0086 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x003e */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0076 A[Catch:{ Throwable -> 0x0092, Throwable -> 0x00c3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008a A[Catch:{ Throwable -> 0x0091 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:25:0x0070=Splitter:B:25:0x0070, B:9:0x003e=Splitter:B:9:0x003e} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onReceiveValue(java.lang.Object r11) {
        /*
            r10 = this;
            com.uc.webview.export.utility.download.DownloadTask r11 = (com.uc.webview.export.utility.download.DownloadTask) r11
            r0 = 1
            com.uc.webview.export.utility.download.UpdateTask r1 = r10.e     // Catch:{ Throwable -> 0x0092 }
            long[] r1 = r1.c     // Catch:{ Throwable -> 0x0092 }
            long r2 = r11.getTotalSize()     // Catch:{ Throwable -> 0x0092 }
            r1[r0] = r2     // Catch:{ Throwable -> 0x0092 }
            com.uc.webview.export.utility.download.UpdateTask r1 = r10.e     // Catch:{ Throwable -> 0x0092 }
            long[] r1 = r1.c     // Catch:{ Throwable -> 0x0092 }
            r2 = 2
            long r3 = r11.getLastModified()     // Catch:{ Throwable -> 0x0092 }
            r1[r2] = r3     // Catch:{ Throwable -> 0x0092 }
            com.uc.webview.export.utility.download.UpdateTask r1 = r10.e     // Catch:{ Throwable -> 0x0092 }
            java.io.File r1 = r1.getUpdateDir()     // Catch:{ Throwable -> 0x0092 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r10.e     // Catch:{ Throwable -> 0x0092 }
            java.lang.String r2 = r2.h     // Catch:{ Throwable -> 0x0092 }
            boolean r2 = com.uc.webview.export.utility.download.UpdateTask.isFinished(r1, r2)     // Catch:{ Throwable -> 0x0092 }
            if (r2 == 0) goto L_0x0042
            java.lang.String r1 = "sdk_ucm_e"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)     // Catch:{ Throwable -> 0x0092 }
            android.webkit.ValueCallback r1 = r10.a     // Catch:{ Throwable -> 0x003e }
            if (r1 == 0) goto L_0x003e
            android.webkit.ValueCallback r1 = r10.a     // Catch:{ Throwable -> 0x003e }
            com.uc.webview.export.utility.download.UpdateTask r2 = r10.e     // Catch:{ Throwable -> 0x003e }
            r1.onReceiveValue(r2)     // Catch:{ Throwable -> 0x003e }
        L_0x003e:
            r11.stop()     // Catch:{ Throwable -> 0x0092 }
            return
        L_0x0042:
            long r2 = r11.getTotalSize()     // Catch:{ Exception -> 0x0070 }
            r4 = 0
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x004e
            r2 = r4
            goto L_0x0059
        L_0x004e:
            long r6 = r11.getCurrentSize()     // Catch:{ Exception -> 0x0070 }
            r8 = 10
            long r6 = r6 * r8
            long r6 = r6 / r2
            long r2 = r6 * r8
        L_0x0059:
            r6 = 100
            int r6 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r6 > 0) goto L_0x0066
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 >= 0) goto L_0x0064
            goto L_0x0066
        L_0x0064:
            int r2 = (int) r2     // Catch:{ Exception -> 0x0070 }
            goto L_0x0067
        L_0x0066:
            r2 = -1
        L_0x0067:
            java.lang.String r3 = "sdk_ucm_p"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Exception -> 0x0070 }
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r3, r2)     // Catch:{ Exception -> 0x0070 }
        L_0x0070:
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x0092 }
            if (r1 == 0) goto L_0x0086
            java.lang.String r1 = "sdk_ucm_f"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)     // Catch:{ Throwable -> 0x0092 }
            android.webkit.ValueCallback r11 = r10.b     // Catch:{ Throwable -> 0x0086 }
            if (r11 == 0) goto L_0x0086
            android.webkit.ValueCallback r11 = r10.b     // Catch:{ Throwable -> 0x0086 }
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.e     // Catch:{ Throwable -> 0x0086 }
            r11.onReceiveValue(r0)     // Catch:{ Throwable -> 0x0086 }
        L_0x0086:
            android.webkit.ValueCallback r11 = r10.c     // Catch:{ Throwable -> 0x0091 }
            if (r11 == 0) goto L_0x0091
            android.webkit.ValueCallback r11 = r10.c     // Catch:{ Throwable -> 0x0091 }
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.e     // Catch:{ Throwable -> 0x0091 }
            r11.onReceiveValue(r0)     // Catch:{ Throwable -> 0x0091 }
        L_0x0091:
            return
        L_0x0092:
            r1 = move-exception
            r11.stop()
            java.lang.String r11 = "sdk_ucm_en"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11)
            java.lang.Class r11 = r1.getClass()
            java.lang.String r11 = r11.getName()
            int r11 = r11.hashCode()
            java.lang.String r2 = "sdk_ucm_le"
            java.lang.String r11 = java.lang.String.valueOf(r11)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2, r11)
            com.uc.webview.export.utility.download.UpdateTask r11 = r10.e
            java.lang.Object[] r11 = r11.e
            r11[r0] = r1
            android.webkit.ValueCallback r11 = r10.d     // Catch:{ Throwable -> 0x00c3 }
            if (r11 == 0) goto L_0x00c3
            android.webkit.ValueCallback r11 = r10.d     // Catch:{ Throwable -> 0x00c3 }
            com.uc.webview.export.utility.download.UpdateTask r0 = r10.e     // Catch:{ Throwable -> 0x00c3 }
            r11.onReceiveValue(r0)     // Catch:{ Throwable -> 0x00c3 }
        L_0x00c3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.utility.download.h.onReceiveValue(java.lang.Object):void");
    }
}
