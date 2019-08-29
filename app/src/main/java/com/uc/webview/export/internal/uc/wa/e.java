package com.uc.webview.export.internal.uc.wa;

/* compiled from: ProGuard */
final class e extends Thread {
    final /* synthetic */ a a;

    e(a aVar) {
        this.a = aVar;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0091, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0110 A[Catch:{ Exception -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0124 A[Catch:{ Exception -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0133 A[SYNTHETIC, Splitter:B:71:0x0133] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0153 A[Catch:{ Exception -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x016e A[Catch:{ Exception -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x012e A[EDGE_INSN: B:94:0x012e->B:68:0x012e ?: BREAK  
    EDGE_INSN: B:94:0x012e->B:68:0x012e ?: BREAK  
    EDGE_INSN: B:94:0x012e->B:68:0x012e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x012e A[EDGE_INSN: B:94:0x012e->B:68:0x012e ?: BREAK  
    EDGE_INSN: B:94:0x012e->B:68:0x012e ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r13 = this;
            com.uc.webview.export.internal.uc.wa.a r0 = r13.a     // Catch:{ Throwable -> 0x0181 }
            r0.e()     // Catch:{ Throwable -> 0x0181 }
            com.uc.webview.export.internal.uc.wa.a r0 = r13.a     // Catch:{ Throwable -> 0x0181 }
            monitor-enter(r0)     // Catch:{ Throwable -> 0x0181 }
            com.uc.webview.export.internal.uc.wa.a r1 = r13.a     // Catch:{ all -> 0x017e }
            android.content.Context r1 = r1.k     // Catch:{ all -> 0x017e }
            java.lang.String r2 = "UC_WA_STAT"
            r3 = 4
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r3)     // Catch:{ all -> 0x017e }
            java.lang.String r2 = com.uc.webview.export.internal.uc.wa.a.h()     // Catch:{ all -> 0x017e }
            r3 = 0
            long r5 = r1.getLong(r2, r3)     // Catch:{ all -> 0x017e }
            boolean r2 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ all -> 0x017e }
            if (r2 == 0) goto L_0x0046
            java.lang.String r2 = "SDKWaStat"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x017e }
            java.lang.String r8 = "==handlUpload==last upload time:"
            r7.<init>(r8)     // Catch:{ all -> 0x017e }
            java.text.SimpleDateFormat r8 = new java.text.SimpleDateFormat     // Catch:{ all -> 0x017e }
            java.lang.String r9 = "yyyy-MM-dd HH:mm:ss"
            r8.<init>(r9)     // Catch:{ all -> 0x017e }
            java.util.Date r9 = new java.util.Date     // Catch:{ all -> 0x017e }
            r9.<init>(r5)     // Catch:{ all -> 0x017e }
            java.lang.String r8 = r8.format(r9)     // Catch:{ all -> 0x017e }
            r7.append(r8)     // Catch:{ all -> 0x017e }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x017e }
            com.uc.webview.export.internal.utility.Log.d(r2, r7)     // Catch:{ all -> 0x017e }
        L_0x0046:
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x017e }
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 == 0) goto L_0x0092
            long r3 = r7 - r5
            r9 = 43200000(0x2932e00, double:2.1343636E-316)
            int r3 = (r3 > r9 ? 1 : (r3 == r9 ? 0 : -1))
            if (r3 >= 0) goto L_0x0092
            java.util.Calendar r3 = java.util.Calendar.getInstance()     // Catch:{ all -> 0x017e }
            r3.setTimeInMillis(r5)     // Catch:{ all -> 0x017e }
            r4 = 11
            int r5 = r3.get(r4)     // Catch:{ all -> 0x017e }
            r3.setTimeInMillis(r7)     // Catch:{ all -> 0x017e }
            int r3 = r3.get(r4)     // Catch:{ all -> 0x017e }
            r4 = 12
            if (r5 < 0) goto L_0x0073
            if (r5 >= r4) goto L_0x0073
            if (r3 >= r4) goto L_0x0079
        L_0x0073:
            if (r5 < r4) goto L_0x0085
            if (r3 < 0) goto L_0x0085
            if (r3 >= r4) goto L_0x0085
        L_0x0079:
            boolean r3 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ all -> 0x017e }
            if (r3 == 0) goto L_0x0092
            java.lang.String r3 = "SDKWaStat"
            java.lang.String r4 = "跨0点或12点"
            com.uc.webview.export.internal.utility.Log.d(r3, r4)     // Catch:{ all -> 0x017e }
            goto L_0x0092
        L_0x0085:
            boolean r1 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ all -> 0x017e }
            if (r1 == 0) goto L_0x0090
            java.lang.String r1 = "SDKWaStat"
            java.lang.String r2 = "时间间隔小于12小时,不上传"
            com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ all -> 0x017e }
        L_0x0090:
            monitor-exit(r0)     // Catch:{ all -> 0x017e }
            return
        L_0x0092:
            r3 = 0
            java.lang.String[] r3 = new java.lang.String[]{r3}     // Catch:{ all -> 0x017e }
            r4 = 0
            if (r2 == 0) goto L_0x0150
            com.uc.webview.export.internal.uc.wa.a r5 = r13.a     // Catch:{ all -> 0x017e }
            java.lang.String r1 = r5.a(r1)     // Catch:{ all -> 0x017e }
            android.webkit.ValueCallback<java.lang.String> r5 = com.uc.webview.export.internal.SDKFactory.z     // Catch:{ all -> 0x017e }
            r6 = 1
            if (r5 == 0) goto L_0x00b7
            com.uc.webview.export.internal.uc.wa.a r5 = r13.a     // Catch:{ all -> 0x017e }
            android.content.Context r5 = r5.k     // Catch:{ all -> 0x017e }
            java.lang.String r5 = r5.getPackageName()     // Catch:{ all -> 0x017e }
            java.lang.String r9 = "com.taobao.taobao"
            boolean r5 = r5.equals(r9)     // Catch:{ all -> 0x017e }
            if (r5 != 0) goto L_0x012e
        L_0x00b7:
            com.uc.webview.export.internal.uc.wa.a r5 = r13.a     // Catch:{ all -> 0x017e }
            byte[] r5 = com.uc.webview.export.internal.uc.wa.a.a(r5, r3)     // Catch:{ all -> 0x017e }
            if (r5 != 0) goto L_0x00c1
            monitor-exit(r0)     // Catch:{ all -> 0x017e }
            return
        L_0x00c1:
            android.webkit.ValueCallback<java.lang.String> r9 = com.uc.webview.export.internal.SDKFactory.A     // Catch:{ all -> 0x017e }
            if (r9 == 0) goto L_0x00da
            android.webkit.ValueCallback<java.lang.String> r9 = com.uc.webview.export.internal.SDKFactory.A     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r10 = new java.lang.String     // Catch:{ Exception -> 0x00d2 }
            java.lang.String r11 = "UTF-8"
            r10.<init>(r5, r11)     // Catch:{ Exception -> 0x00d2 }
            r9.onReceiveValue(r10)     // Catch:{ Exception -> 0x00d2 }
            goto L_0x00da
        L_0x00d2:
            r9 = move-exception
            java.lang.String r10 = "SDKWaStat"
            java.lang.String r11 = "byte 转 String异常!"
            com.uc.webview.export.internal.utility.Log.d(r10, r11, r9)     // Catch:{ all -> 0x017e }
        L_0x00da:
            byte[] r9 = com.uc.webview.export.internal.uc.wa.f.a(r5)     // Catch:{ Exception -> 0x00fd }
            boolean r5 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ Exception -> 0x00f9 }
            if (r5 == 0) goto L_0x00f6
            java.lang.String r5 = "SDKWaStat"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f9 }
            java.lang.String r11 = "getUploadData encrypt:"
            r10.<init>(r11)     // Catch:{ Exception -> 0x00f9 }
            int r11 = r9.length     // Catch:{ Exception -> 0x00f9 }
            r10.append(r11)     // Catch:{ Exception -> 0x00f9 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x00f9 }
            com.uc.webview.export.internal.utility.Log.d(r5, r10)     // Catch:{ Exception -> 0x00f9 }
        L_0x00f6:
            r10 = r9
            r9 = 1
            goto L_0x0108
        L_0x00f9:
            r5 = move-exception
            r10 = r9
            r9 = 1
            goto L_0x0101
        L_0x00fd:
            r9 = move-exception
            r10 = r5
            r5 = r9
            r9 = 0
        L_0x0101:
            java.lang.String r11 = "SDKWaStat"
            java.lang.String r12 = "data encrypt error:"
            com.uc.webview.export.internal.utility.Log.e(r11, r12, r5)     // Catch:{ all -> 0x017e }
        L_0x0108:
            java.lang.String r1 = com.uc.webview.export.internal.uc.wa.a.a(r1, r9)     // Catch:{ all -> 0x017e }
            boolean r5 = com.uc.webview.export.utility.Utils.sWAPrintLog     // Catch:{ all -> 0x017e }
            if (r5 == 0) goto L_0x011f
            java.lang.String r5 = "SDKWaStat"
            java.lang.String r9 = "request url:"
            java.lang.String r11 = java.lang.String.valueOf(r1)     // Catch:{ all -> 0x017e }
            java.lang.String r9 = r9.concat(r11)     // Catch:{ all -> 0x017e }
            com.uc.webview.export.internal.utility.Log.d(r5, r9)     // Catch:{ all -> 0x017e }
        L_0x011f:
            r5 = 3
        L_0x0120:
            int r9 = r5 + -1
            if (r5 <= 0) goto L_0x012e
            boolean r5 = com.uc.webview.export.internal.uc.wa.a.b(r1, r10)     // Catch:{ all -> 0x017e }
            if (r5 == 0) goto L_0x012c
            r1 = 1
            goto L_0x012f
        L_0x012c:
            r5 = r9
            goto L_0x0120
        L_0x012e:
            r1 = 0
        L_0x012f:
            android.webkit.ValueCallback<java.lang.String> r5 = com.uc.webview.export.internal.SDKFactory.z     // Catch:{ all -> 0x017e }
            if (r5 == 0) goto L_0x0151
            com.uc.webview.export.internal.uc.wa.a r5 = r13.a     // Catch:{ Exception -> 0x0147 }
            java.lang.String r5 = r5.a(r3)     // Catch:{ Exception -> 0x0147 }
            if (r5 == 0) goto L_0x0151
            java.lang.String r9 = "SDKWaStat"
            com.uc.webview.export.internal.utility.Log.i(r9, r5)     // Catch:{ Exception -> 0x0147 }
            android.webkit.ValueCallback<java.lang.String> r9 = com.uc.webview.export.internal.SDKFactory.z     // Catch:{ Exception -> 0x0147 }
            r9.onReceiveValue(r5)     // Catch:{ Exception -> 0x0147 }
            r1 = 1
            goto L_0x0151
        L_0x0147:
            r5 = move-exception
            java.lang.String r6 = "SDKWaStat"
            java.lang.String r9 = "第三方上传数据出错!"
            com.uc.webview.export.internal.utility.Log.d(r6, r9, r5)     // Catch:{ all -> 0x017e }
            goto L_0x0151
        L_0x0150:
            r1 = 0
        L_0x0151:
            if (r1 == 0) goto L_0x016c
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x017e }
            com.uc.webview.export.internal.uc.wa.a r5 = r13.a     // Catch:{ all -> 0x017e }
            java.lang.String r5 = r5.f()     // Catch:{ all -> 0x017e }
            java.lang.String r6 = com.uc.webview.export.internal.uc.wa.a.g()     // Catch:{ all -> 0x017e }
            r1.<init>(r5, r6)     // Catch:{ all -> 0x017e }
            r1.delete()     // Catch:{ all -> 0x017e }
            com.uc.webview.export.internal.uc.wa.a r1 = r13.a     // Catch:{ all -> 0x017e }
            r5 = r3[r4]     // Catch:{ all -> 0x017e }
            com.uc.webview.export.internal.uc.wa.a.a(r1, r7, r5)     // Catch:{ all -> 0x017e }
        L_0x016c:
            if (r2 != 0) goto L_0x017c
            java.lang.String r1 = "SDKWaStat"
            java.lang.String r2 = "首次不上传数据"
            com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ all -> 0x017e }
            com.uc.webview.export.internal.uc.wa.a r1 = r13.a     // Catch:{ all -> 0x017e }
            r2 = r3[r4]     // Catch:{ all -> 0x017e }
            com.uc.webview.export.internal.uc.wa.a.a(r1, r7, r2)     // Catch:{ all -> 0x017e }
        L_0x017c:
            monitor-exit(r0)     // Catch:{ all -> 0x017e }
            return
        L_0x017e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x017e }
            throw r1     // Catch:{ Throwable -> 0x0181 }
        L_0x0181:
            r0 = move-exception
            java.lang.String r1 = "SDKWaStat"
            java.lang.String r2 = "handlUpload"
            com.uc.webview.export.internal.utility.Log.i(r1, r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.uc.wa.e.run():void");
    }
}
