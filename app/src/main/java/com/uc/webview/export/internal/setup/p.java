package com.uc.webview.export.internal.setup;

/* compiled from: ProGuard */
public final class p extends UCSubSetupTask<p, p> {
    /* JADX WARNING: Can't wrap try/catch for region: R(38:5|6|7|8|10|11|12|13|(2:17|(1:19)(23:20|22|23|24|(1:29)(1:28)|30|31|32|(2:43|(3:45|(1:47)|112))|48|(2:54|(4:56|(1:58)(1:59)|60|(2:71|(3:73|(2:75|76)|113))))|77|79|80|(1:82)(1:83)|84|85|87|88|(2:99|(2:101|(2:103|104)))|105|106|107))|21|22|23|24|(1:26)|29|30|31|32|34|36|43|(0)|48|50|54|(0)|77|(4:79|80|(0)(0)|84)|85|87|88|90|92|99|(0)|105|106|107) */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01de, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:105:0x01da */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0044 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0075 */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01c6 A[Catch:{ Throwable -> 0x01da }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0056 A[Catch:{ Throwable -> 0x0075 }] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0057 A[Catch:{ Throwable -> 0x0075 }] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0079 A[Catch:{ Throwable -> 0x01de }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c1 A[Catch:{ Throwable -> 0x0177 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x011e A[Catch:{ Throwable -> 0x0177 }] */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0185 A[Catch:{ Throwable -> 0x0192 }] */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x0187 A[Catch:{ Throwable -> 0x0192 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r17 = this;
            r0 = r17
            com.uc.webview.export.internal.setup.UCMRunningInfo r1 = com.uc.webview.export.utility.SetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x01df }
            int r2 = r1.coreType     // Catch:{ Throwable -> 0x01df }
            r3 = 2
            if (r2 != r3) goto L_0x000c
            return
        L_0x000c:
            java.lang.String r2 = "CONTEXT"
            java.lang.Object r2 = r0.getOption(r2)     // Catch:{ Throwable -> 0x01df }
            android.content.Context r2 = (android.content.Context) r2     // Catch:{ Throwable -> 0x01df }
            java.lang.String r4 = "del_dec_fil"
            java.lang.Object r4 = r0.getOption(r4)     // Catch:{ Throwable -> 0x01df }
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ Throwable -> 0x01df }
            boolean r4 = com.uc.webview.export.internal.utility.j.b(r4)     // Catch:{ Throwable -> 0x01df }
            r5 = 1
            r4 = r4 ^ r5
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)     // Catch:{ Throwable -> 0x01df }
            java.lang.String r6 = "del_upd_fil"
            java.lang.Object r6 = r0.getOption(r6)     // Catch:{ Throwable -> 0x01df }
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ Throwable -> 0x01df }
            boolean r6 = com.uc.webview.export.internal.utility.j.b(r6)     // Catch:{ Throwable -> 0x01df }
            r6 = r6 ^ r5
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Throwable -> 0x01df }
            com.uc.webview.export.cyclone.UCCyclone.deleteUnusedFiles(r2)     // Catch:{ Throwable -> 0x003a }
        L_0x003a:
            r7 = 10039(0x2737, float:1.4068E-41)
            r8 = 0
            java.lang.Object[] r9 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0044 }
            r9[r8] = r2     // Catch:{ Throwable -> 0x0044 }
            com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r7, r9)     // Catch:{ Throwable -> 0x0044 }
        L_0x0044:
            com.uc.webview.export.internal.setup.UCMPackageInfo r9 = r1.ucmPackageInfo     // Catch:{ Throwable -> 0x0075 }
            if (r9 == 0) goto L_0x0065
            com.uc.webview.export.internal.setup.UCMPackageInfo r9 = r1.ucmPackageInfo     // Catch:{ Throwable -> 0x0075 }
            android.util.Pair<java.lang.String, java.lang.String> r9 = r9.coreImplModule     // Catch:{ Throwable -> 0x0075 }
            if (r9 == 0) goto L_0x0065
            com.uc.webview.export.internal.setup.UCMPackageInfo r9 = r1.ucmPackageInfo     // Catch:{ Throwable -> 0x0075 }
            android.util.Pair<java.lang.String, java.lang.String> r9 = r9.coreImplModule     // Catch:{ Throwable -> 0x0075 }
            java.lang.Object r9 = r9.second     // Catch:{ Throwable -> 0x0075 }
            if (r9 != 0) goto L_0x0057
            goto L_0x0065
        L_0x0057:
            java.io.File r9 = new java.io.File     // Catch:{ Throwable -> 0x0075 }
            com.uc.webview.export.internal.setup.UCMPackageInfo r10 = r1.ucmPackageInfo     // Catch:{ Throwable -> 0x0075 }
            android.util.Pair<java.lang.String, java.lang.String> r10 = r10.coreImplModule     // Catch:{ Throwable -> 0x0075 }
            java.lang.Object r10 = r10.second     // Catch:{ Throwable -> 0x0075 }
            java.lang.String r10 = (java.lang.String) r10     // Catch:{ Throwable -> 0x0075 }
            r9.<init>(r10)     // Catch:{ Throwable -> 0x0075 }
            goto L_0x0066
        L_0x0065:
            r9 = 0
        L_0x0066:
            r10 = 10004(0x2714, float:1.4019E-41)
            java.lang.Object[] r11 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0075 }
            r11[r8] = r2     // Catch:{ Throwable -> 0x0075 }
            java.lang.Object r10 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r10, r11)     // Catch:{ Throwable -> 0x0075 }
            java.io.File r10 = (java.io.File) r10     // Catch:{ Throwable -> 0x0075 }
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r10, r5, r9)     // Catch:{ Throwable -> 0x0075 }
        L_0x0075:
            com.uc.webview.export.internal.setup.UCMPackageInfo r9 = r1.ucmPackageInfo     // Catch:{ Throwable -> 0x01de }
            if (r9 == 0) goto L_0x0089
            com.uc.webview.export.internal.setup.UCMPackageInfo r9 = r1.ucmPackageInfo     // Catch:{ Throwable -> 0x01de }
            java.lang.String r9 = r9.dataDir     // Catch:{ Throwable -> 0x01de }
            if (r9 == 0) goto L_0x0089
            java.io.File r9 = new java.io.File     // Catch:{ Throwable -> 0x01de }
            com.uc.webview.export.internal.setup.UCMPackageInfo r1 = r1.ucmPackageInfo     // Catch:{ Throwable -> 0x01de }
            java.lang.String r1 = r1.dataDir     // Catch:{ Throwable -> 0x01de }
            r9.<init>(r1)     // Catch:{ Throwable -> 0x01de }
            goto L_0x008a
        L_0x0089:
            r9 = 0
        L_0x008a:
            r1 = 10003(0x2713, float:1.4017E-41)
            r10 = 3
            r11 = 10044(0x273c, float:1.4075E-41)
            java.lang.Object[] r12 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0177 }
            r12[r8] = r2     // Catch:{ Throwable -> 0x0177 }
            java.lang.Object r1 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r1, r12)     // Catch:{ Throwable -> 0x0177 }
            java.io.File r1 = (java.io.File) r1     // Catch:{ Throwable -> 0x0177 }
            java.io.File[] r12 = r1.listFiles()     // Catch:{ Throwable -> 0x0177 }
            if (r12 == 0) goto L_0x00d5
            int r13 = r12.length     // Catch:{ Throwable -> 0x0177 }
            if (r13 <= 0) goto L_0x00d5
            boolean r13 = r4.booleanValue()     // Catch:{ Throwable -> 0x0177 }
            if (r13 != 0) goto L_0x00bb
            int r12 = r12.length     // Catch:{ Throwable -> 0x0177 }
            if (r12 >= r3) goto L_0x00bb
            if (r9 == 0) goto L_0x00d5
            java.lang.String r12 = r9.getAbsolutePath()     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r13 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x0177 }
            boolean r12 = r12.startsWith(r13)     // Catch:{ Throwable -> 0x0177 }
            if (r12 == 0) goto L_0x00d5
        L_0x00bb:
            java.io.File[] r12 = r1.listFiles()     // Catch:{ Throwable -> 0x0177 }
            if (r12 == 0) goto L_0x00d5
            int r13 = r12.length     // Catch:{ Throwable -> 0x0177 }
            r14 = 0
        L_0x00c3:
            if (r14 >= r13) goto L_0x00d5
            r15 = r12[r14]     // Catch:{ Throwable -> 0x0177 }
            java.lang.Object[] r7 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0177 }
            r7[r8] = r2     // Catch:{ Throwable -> 0x0177 }
            r7[r5] = r15     // Catch:{ Throwable -> 0x0177 }
            r7[r3] = r9     // Catch:{ Throwable -> 0x0177 }
            com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r11, r7)     // Catch:{ Throwable -> 0x0177 }
            int r14 = r14 + 1
            goto L_0x00c3
        L_0x00d5:
            java.lang.String r7 = "bo_dex_old_dex_dir"
            java.lang.Object r7 = r0.getOption(r7)     // Catch:{ Throwable -> 0x0177 }
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch:{ Throwable -> 0x0177 }
            boolean r7 = com.uc.webview.export.internal.utility.j.a(r7)     // Catch:{ Throwable -> 0x0177 }
            if (r7 == 0) goto L_0x0177
            java.lang.String r7 = "bit_by_new_dex_dir"
            java.lang.String r12 = "bo_init_type"
            java.lang.Object r12 = r0.getOption(r12)     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x0177 }
            boolean r7 = r7.equals(r12)     // Catch:{ Throwable -> 0x0177 }
            if (r7 != 0) goto L_0x0103
            java.lang.String r7 = "bit_by_new_zip_file"
            java.lang.String r12 = "bo_init_type"
            java.lang.Object r12 = r0.getOption(r12)     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x0177 }
            boolean r7 = r7.equals(r12)     // Catch:{ Throwable -> 0x0177 }
            if (r7 == 0) goto L_0x0177
        L_0x0103:
            java.io.File r7 = new java.io.File     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r12 = "bo_old_dex_dp"
            java.lang.Object r12 = r0.getOption(r12)     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x0177 }
            r7.<init>(r12)     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r12 = r7.getAbsolutePath()     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x0177 }
            boolean r1 = r12.startsWith(r1)     // Catch:{ Throwable -> 0x0177 }
            if (r1 != 0) goto L_0x0177
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r12 = "bo_dec_root_dir"
            java.lang.Object r12 = r0.getOption(r12)     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x0177 }
            r1.<init>(r12)     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r12 = r7.getAbsolutePath()     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r13 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x0177 }
            boolean r12 = r12.startsWith(r13)     // Catch:{ Throwable -> 0x0177 }
            if (r12 == 0) goto L_0x013a
            goto L_0x013b
        L_0x013a:
            r1 = r7
        L_0x013b:
            java.io.File[] r7 = r1.listFiles()     // Catch:{ Throwable -> 0x0177 }
            if (r7 == 0) goto L_0x0177
            int r12 = r7.length     // Catch:{ Throwable -> 0x0177 }
            if (r12 <= 0) goto L_0x0177
            boolean r4 = r4.booleanValue()     // Catch:{ Throwable -> 0x0177 }
            if (r4 != 0) goto L_0x015d
            int r4 = r7.length     // Catch:{ Throwable -> 0x0177 }
            if (r4 >= r3) goto L_0x015d
            if (r9 == 0) goto L_0x0177
            java.lang.String r4 = r9.getAbsolutePath()     // Catch:{ Throwable -> 0x0177 }
            java.lang.String r7 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x0177 }
            boolean r4 = r4.startsWith(r7)     // Catch:{ Throwable -> 0x0177 }
            if (r4 == 0) goto L_0x0177
        L_0x015d:
            java.io.File[] r1 = r1.listFiles()     // Catch:{ Throwable -> 0x0177 }
            if (r1 == 0) goto L_0x0177
            int r4 = r1.length     // Catch:{ Throwable -> 0x0177 }
            r7 = 0
        L_0x0165:
            if (r7 >= r4) goto L_0x0177
            r12 = r1[r7]     // Catch:{ Throwable -> 0x0177 }
            java.lang.Object[] r13 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x0177 }
            r13[r8] = r2     // Catch:{ Throwable -> 0x0177 }
            r13[r5] = r12     // Catch:{ Throwable -> 0x0177 }
            r13[r3] = r9     // Catch:{ Throwable -> 0x0177 }
            com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r11, r13)     // Catch:{ Throwable -> 0x0177 }
            int r7 = r7 + 1
            goto L_0x0165
        L_0x0177:
            r1 = 10006(0x2716, float:1.4021E-41)
            java.lang.Object[] r4 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0192 }
            r4[r8] = r2     // Catch:{ Throwable -> 0x0192 }
            java.lang.Object r1 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r1, r4)     // Catch:{ Throwable -> 0x0192 }
            java.io.File r1 = (java.io.File) r1     // Catch:{ Throwable -> 0x0192 }
            if (r9 != 0) goto L_0x0187
            r7 = 0
            goto L_0x018f
        L_0x0187:
            java.io.File r4 = r9.getParentFile()     // Catch:{ Throwable -> 0x0192 }
            java.io.File r7 = r4.getParentFile()     // Catch:{ Throwable -> 0x0192 }
        L_0x018f:
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r1, r5, r7)     // Catch:{ Throwable -> 0x0192 }
        L_0x0192:
            r1 = 10002(0x2712, float:1.4016E-41)
            java.lang.Object[] r4 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x01da }
            r4[r8] = r2     // Catch:{ Throwable -> 0x01da }
            java.lang.Object r1 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r1, r4)     // Catch:{ Throwable -> 0x01da }
            java.io.File r1 = (java.io.File) r1     // Catch:{ Throwable -> 0x01da }
            java.io.File[] r4 = r1.listFiles()     // Catch:{ Throwable -> 0x01da }
            if (r4 == 0) goto L_0x01da
            int r7 = r4.length     // Catch:{ Throwable -> 0x01da }
            if (r7 <= 0) goto L_0x01da
            boolean r6 = r6.booleanValue()     // Catch:{ Throwable -> 0x01da }
            if (r6 != 0) goto L_0x01c0
            int r4 = r4.length     // Catch:{ Throwable -> 0x01da }
            if (r4 >= r3) goto L_0x01c0
            if (r9 == 0) goto L_0x01da
            java.lang.String r4 = r9.getAbsolutePath()     // Catch:{ Throwable -> 0x01da }
            java.lang.String r6 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x01da }
            boolean r4 = r4.startsWith(r6)     // Catch:{ Throwable -> 0x01da }
            if (r4 == 0) goto L_0x01da
        L_0x01c0:
            java.io.File[] r1 = r1.listFiles()     // Catch:{ Throwable -> 0x01da }
            if (r1 == 0) goto L_0x01da
            int r4 = r1.length     // Catch:{ Throwable -> 0x01da }
            r6 = 0
        L_0x01c8:
            if (r6 >= r4) goto L_0x01da
            r7 = r1[r6]     // Catch:{ Throwable -> 0x01da }
            java.lang.Object[] r12 = new java.lang.Object[r10]     // Catch:{ Throwable -> 0x01da }
            r12[r8] = r2     // Catch:{ Throwable -> 0x01da }
            r12[r5] = r7     // Catch:{ Throwable -> 0x01da }
            r12[r3] = r9     // Catch:{ Throwable -> 0x01da }
            com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r11, r12)     // Catch:{ Throwable -> 0x01da }
            int r6 = r6 + 1
            goto L_0x01c8
        L_0x01da:
            com.uc.webview.export.internal.setup.l.a(r2)     // Catch:{ Throwable -> 0x01de }
            return
        L_0x01de:
            return
        L_0x01df:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.p.run():void");
    }
}
