package com.uc.webview.export.utility.download;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class d implements Runnable {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ String b;
    final /* synthetic */ DownloadTask c;
    final /* synthetic */ ValueCallback d;
    final /* synthetic */ ValueCallback e;
    final /* synthetic */ ValueCallback f;
    final /* synthetic */ ValueCallback g;
    final /* synthetic */ ValueCallback h;
    final /* synthetic */ UpdateTask i;

    d(UpdateTask updateTask, ValueCallback valueCallback, String str, DownloadTask downloadTask, ValueCallback valueCallback2, ValueCallback valueCallback3, ValueCallback valueCallback4, ValueCallback valueCallback5, ValueCallback valueCallback6) {
        this.i = updateTask;
        this.a = valueCallback;
        this.b = str;
        this.c = downloadTask;
        this.d = valueCallback2;
        this.e = valueCallback3;
        this.f = valueCallback4;
        this.g = valueCallback5;
        this.h = valueCallback6;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(24:11|12|(2:14|(5:16|17|18|(1:20)|21)(3:24|25|(1:27)))|28|29|30|31|32|(1:34)|37|38|39|40|(1:42)|43|44|(1:46)(1:47)|48|49|(1:51)|52|53|(1:55)|56) */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x014e, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0029 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x0093 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00b3 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x00fd */
    /* JADX WARNING: Missing exception handler attribute for start block: B:52:0x0142 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:63:0x017d */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0037 A[Catch:{ Exception -> 0x00af, Throwable -> 0x017d, Throwable -> 0x017e }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00a7 A[Catch:{ Throwable -> 0x00b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00f6 A[Catch:{ Throwable -> 0x00fd }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0122 A[Catch:{ Exception -> 0x00af, Throwable -> 0x017d, Throwable -> 0x017e }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x012d A[Catch:{ Exception -> 0x00af, Throwable -> 0x017d, Throwable -> 0x017e }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x013b A[Catch:{ Throwable -> 0x0142 }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0146 A[Catch:{ Throwable -> 0x014e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r19 = this;
            r1 = r19
            android.webkit.ValueCallback r2 = r1.a     // Catch:{ Throwable -> 0x000d }
            if (r2 == 0) goto L_0x000d
            android.webkit.ValueCallback r2 = r1.a     // Catch:{ Throwable -> 0x000d }
            com.uc.webview.export.utility.download.UpdateTask r3 = r1.i     // Catch:{ Throwable -> 0x000d }
            r2.onReceiveValue(r3)     // Catch:{ Throwable -> 0x000d }
        L_0x000d:
            r2 = 0
            r3 = 1
            com.uc.webview.export.utility.download.UpdateTask r4 = r1.i     // Catch:{ Throwable -> 0x0029 }
            android.webkit.ValueCallback r4 = r4.i     // Catch:{ Throwable -> 0x0029 }
            if (r4 == 0) goto L_0x0029
            com.uc.webview.export.utility.download.UpdateTask r4 = r1.i     // Catch:{ Throwable -> 0x0029 }
            android.webkit.ValueCallback r4 = r4.i     // Catch:{ Throwable -> 0x0029 }
            java.lang.Object[] r5 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x0029 }
            r6 = 3
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x0029 }
            r5[r2] = r6     // Catch:{ Throwable -> 0x0029 }
            r4.onReceiveValue(r5)     // Catch:{ Throwable -> 0x0029 }
        L_0x0029:
            java.lang.String r4 = r1.b     // Catch:{ Throwable -> 0x017e }
            java.lang.String r4 = r4.toLowerCase()     // Catch:{ Throwable -> 0x017e }
            java.lang.String r5 = ".7z"
            boolean r4 = r4.endsWith(r5)     // Catch:{ Throwable -> 0x017e }
            if (r4 != 0) goto L_0x0093
            com.uc.webview.export.utility.download.DownloadTask r4 = r1.c     // Catch:{ Throwable -> 0x017e }
            java.lang.String r5 = r4.getFilePath()     // Catch:{ Throwable -> 0x017e }
            com.uc.webview.export.utility.download.UpdateTask r4 = r1.i     // Catch:{ Throwable -> 0x017e }
            java.lang.Object[] r4 = r4.e     // Catch:{ Throwable -> 0x017e }
            r4 = r4[r2]     // Catch:{ Throwable -> 0x017e }
            r6 = r4
            android.content.Context r6 = (android.content.Context) r6     // Catch:{ Throwable -> 0x017e }
            com.uc.webview.export.utility.download.UpdateTask r4 = r1.i     // Catch:{ Throwable -> 0x017e }
            java.lang.Object[] r4 = r4.e     // Catch:{ Throwable -> 0x017e }
            r4 = r4[r2]     // Catch:{ Throwable -> 0x017e }
            r7 = r4
            android.content.Context r7 = (android.content.Context) r7     // Catch:{ Throwable -> 0x017e }
            java.lang.String r8 = "com.UCMobile"
            com.uc.webview.export.utility.download.UpdateTask r4 = r1.i     // Catch:{ Throwable -> 0x017e }
            android.webkit.ValueCallback r9 = r4.i     // Catch:{ Throwable -> 0x017e }
            r10 = 0
            boolean r4 = com.uc.webview.export.internal.utility.i.a(r5, r6, r7, r8, r9, r10)     // Catch:{ Throwable -> 0x017e }
            if (r4 != 0) goto L_0x0088
            java.lang.String r2 = "sdk_dec7z_ls"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)     // Catch:{ Throwable -> 0x017e }
            com.uc.webview.export.utility.download.DownloadTask r2 = r1.c     // Catch:{ Throwable -> 0x017e }
            r2.delete()     // Catch:{ Throwable -> 0x017e }
            com.uc.webview.export.utility.download.UpdateTask r2 = r1.i     // Catch:{ Throwable -> 0x017e }
            java.lang.Object[] r2 = r2.e     // Catch:{ Throwable -> 0x017e }
            java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ Throwable -> 0x017e }
            java.lang.String r5 = "Archive verify failed."
            r4.<init>(r5)     // Catch:{ Throwable -> 0x017e }
            r2[r3] = r4     // Catch:{ Throwable -> 0x017e }
            android.webkit.ValueCallback r2 = r1.d     // Catch:{ Throwable -> 0x0087 }
            if (r2 == 0) goto L_0x0086
            android.webkit.ValueCallback r2 = r1.d     // Catch:{ Throwable -> 0x0087 }
            com.uc.webview.export.utility.download.UpdateTask r3 = r1.i     // Catch:{ Throwable -> 0x0087 }
            r2.onReceiveValue(r3)     // Catch:{ Throwable -> 0x0087 }
        L_0x0086:
            return
        L_0x0087:
            return
        L_0x0088:
            android.webkit.ValueCallback r4 = r1.a     // Catch:{ Throwable -> 0x0093 }
            if (r4 == 0) goto L_0x0093
            android.webkit.ValueCallback r4 = r1.a     // Catch:{ Throwable -> 0x0093 }
            com.uc.webview.export.utility.download.UpdateTask r5 = r1.i     // Catch:{ Throwable -> 0x0093 }
            r4.onReceiveValue(r5)     // Catch:{ Throwable -> 0x0093 }
        L_0x0093:
            com.uc.webview.export.utility.download.UpdateTask r4 = r1.i     // Catch:{ Throwable -> 0x017e }
            java.io.File r4 = r4.getUpdateDir()     // Catch:{ Throwable -> 0x017e }
            com.uc.webview.export.cyclone.UCCyclone.expectCreateDirFile(r4)     // Catch:{ Throwable -> 0x017e }
            new java.io.File(r4, com.uc.webview.export.utility.download.UpdateTask.STOP_FLG_FILE_NAME).delete()     // Catch:{ Throwable -> 0x017e }
            com.uc.webview.export.utility.download.UpdateTask.a(r4, r2)     // Catch:{ Throwable -> 0x017e }
            r15 = 2
            android.webkit.ValueCallback r5 = r1.e     // Catch:{ Throwable -> 0x00b3 }
            if (r5 == 0) goto L_0x00b3
            android.webkit.ValueCallback r5 = r1.e     // Catch:{ Throwable -> 0x00b3 }
            com.uc.webview.export.utility.download.UpdateTask r6 = r1.i     // Catch:{ Throwable -> 0x00b3 }
            r5.onReceiveValue(r6)     // Catch:{ Throwable -> 0x00b3 }
            goto L_0x00b3
        L_0x00af:
            r0 = move-exception
            r4 = r0
            goto L_0x014f
        L_0x00b3:
            com.uc.webview.export.utility.download.UpdateTask r5 = r1.i     // Catch:{ Exception -> 0x00af }
            java.lang.Object[] r5 = r5.e     // Catch:{ Exception -> 0x00af }
            r5 = r5[r2]     // Catch:{ Exception -> 0x00af }
            android.content.Context r5 = (android.content.Context) r5     // Catch:{ Exception -> 0x00af }
            java.lang.String r6 = r1.b     // Catch:{ Exception -> 0x00af }
            java.lang.String r7 = ".7z"
            boolean r6 = r6.endsWith(r7)     // Catch:{ Exception -> 0x00af }
            java.lang.String r7 = r1.b     // Catch:{ Exception -> 0x00af }
            com.uc.webview.export.utility.download.UpdateTask r8 = r1.i     // Catch:{ Exception -> 0x00af }
            long[] r8 = r8.c     // Catch:{ Exception -> 0x00af }
            r9 = r8[r3]     // Catch:{ Exception -> 0x00af }
            com.uc.webview.export.utility.download.UpdateTask r8 = r1.i     // Catch:{ Exception -> 0x00af }
            long[] r8 = r8.c     // Catch:{ Exception -> 0x00af }
            r11 = r8[r15]     // Catch:{ Exception -> 0x00af }
            java.io.File r13 = new java.io.File     // Catch:{ Exception -> 0x00af }
            com.uc.webview.export.utility.download.DownloadTask r8 = r1.c     // Catch:{ Exception -> 0x00af }
            java.lang.String r8 = r8.getFilePath()     // Catch:{ Exception -> 0x00af }
            r13.<init>(r8)     // Catch:{ Exception -> 0x00af }
            r14 = 0
            r16 = 0
            int r17 = com.uc.webview.export.cyclone.UCCyclone.DecFileOrign.Update     // Catch:{ Exception -> 0x00af }
            r8 = r9
            r10 = r11
            r12 = r13
            r13 = r4
            r15 = r16
            r16 = r17
            com.uc.webview.export.cyclone.UCCyclone.decompressIfNeeded(r5, r6, r7, r8, r10, r12, r13, r14, r15, r16)     // Catch:{ Exception -> 0x00af }
            android.webkit.ValueCallback r5 = r1.f     // Catch:{ Throwable -> 0x00fd }
            if (r5 == 0) goto L_0x00fd
            android.webkit.ValueCallback r5 = r1.f     // Catch:{ Throwable -> 0x00fd }
            com.uc.webview.export.utility.download.UpdateTask r6 = r1.i     // Catch:{ Throwable -> 0x00fd }
            r5.onReceiveValue(r6)     // Catch:{ Throwable -> 0x00fd }
        L_0x00fd:
            com.uc.webview.export.utility.download.UpdateTask.a(r4, r3)     // Catch:{ Throwable -> 0x017e }
            r5 = 10002(0x2712, float:1.4016E-41)
            java.lang.Object[] r6 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x017e }
            com.uc.webview.export.utility.download.UpdateTask r7 = r1.i     // Catch:{ Throwable -> 0x017e }
            java.lang.Object[] r7 = r7.e     // Catch:{ Throwable -> 0x017e }
            r7 = r7[r2]     // Catch:{ Throwable -> 0x017e }
            r6[r2] = r7     // Catch:{ Throwable -> 0x017e }
            java.lang.Object r2 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r5, r6)     // Catch:{ Throwable -> 0x017e }
            java.io.File r2 = (java.io.File) r2     // Catch:{ Throwable -> 0x017e }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x017e }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ Throwable -> 0x017e }
            boolean r2 = r4.contains(r2)     // Catch:{ Throwable -> 0x017e }
            if (r2 == 0) goto L_0x012d
            com.uc.webview.export.utility.download.DownloadTask r2 = r1.c     // Catch:{ Throwable -> 0x017e }
            r2.delete(r3)     // Catch:{ Throwable -> 0x017e }
            java.lang.String r2 = "sdk_ucm_s"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)     // Catch:{ Throwable -> 0x017e }
            goto L_0x0137
        L_0x012d:
            com.uc.webview.export.utility.download.DownloadTask r2 = r1.c     // Catch:{ Throwable -> 0x017e }
            r2.delete()     // Catch:{ Throwable -> 0x017e }
            java.lang.String r2 = "sdk_ucm_so"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)     // Catch:{ Throwable -> 0x017e }
        L_0x0137:
            android.webkit.ValueCallback r2 = r1.a     // Catch:{ Throwable -> 0x0142 }
            if (r2 == 0) goto L_0x0142
            android.webkit.ValueCallback r2 = r1.a     // Catch:{ Throwable -> 0x0142 }
            com.uc.webview.export.utility.download.UpdateTask r3 = r1.i     // Catch:{ Throwable -> 0x0142 }
            r2.onReceiveValue(r3)     // Catch:{ Throwable -> 0x0142 }
        L_0x0142:
            android.webkit.ValueCallback r2 = r1.g     // Catch:{ Throwable -> 0x014e }
            if (r2 == 0) goto L_0x014d
            android.webkit.ValueCallback r2 = r1.g     // Catch:{ Throwable -> 0x014e }
            com.uc.webview.export.utility.download.UpdateTask r3 = r1.i     // Catch:{ Throwable -> 0x014e }
            r2.onReceiveValue(r3)     // Catch:{ Throwable -> 0x014e }
        L_0x014d:
            return
        L_0x014e:
            return
        L_0x014f:
            com.uc.webview.export.utility.download.UpdateTask r5 = r1.i     // Catch:{ Throwable -> 0x017d }
            android.webkit.ValueCallback r5 = r5.i     // Catch:{ Throwable -> 0x017d }
            if (r5 == 0) goto L_0x017d
            com.uc.webview.export.utility.download.UpdateTask r5 = r1.i     // Catch:{ Throwable -> 0x017d }
            android.webkit.ValueCallback r5 = r5.i     // Catch:{ Throwable -> 0x017d }
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x017d }
            r7 = 9
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x017d }
            r6[r2] = r7     // Catch:{ Throwable -> 0x017d }
            java.lang.Class r2 = r4.getClass()     // Catch:{ Throwable -> 0x017d }
            java.lang.String r2 = r2.getName()     // Catch:{ Throwable -> 0x017d }
            int r2 = r2.hashCode()     // Catch:{ Throwable -> 0x017d }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x017d }
            r6[r3] = r2     // Catch:{ Throwable -> 0x017d }
            r5.onReceiveValue(r6)     // Catch:{ Throwable -> 0x017d }
        L_0x017d:
            throw r4     // Catch:{ Throwable -> 0x017e }
        L_0x017e:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "sdk_dec7z"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r4)
            java.lang.Class r4 = r2.getClass()
            java.lang.String r4 = r4.getName()
            int r4 = r4.hashCode()
            java.lang.String r5 = "sdk_ucm_le"
            java.lang.String r4 = java.lang.String.valueOf(r4)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r5, r4)
            com.uc.webview.export.utility.download.UpdateTask r4 = r1.i
            java.lang.Object[] r4 = r4.e
            r4[r3] = r2
            android.webkit.ValueCallback r2 = r1.h     // Catch:{ Throwable -> 0x01ae }
            if (r2 == 0) goto L_0x01ad
            android.webkit.ValueCallback r2 = r1.h     // Catch:{ Throwable -> 0x01ae }
            com.uc.webview.export.utility.download.UpdateTask r3 = r1.i     // Catch:{ Throwable -> 0x01ae }
            r2.onReceiveValue(r3)     // Catch:{ Throwable -> 0x01ae }
        L_0x01ad:
            return
        L_0x01ae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.utility.download.d.run():void");
    }
}
