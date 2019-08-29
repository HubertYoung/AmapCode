package com.uc.webview.export.internal.setup;

import java.util.List;

/* compiled from: ProGuard */
public final class o extends UCSubSetupTask<o, o> {
    private List<bl> a;

    public o(List<bl> list) {
        this.a = list;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x01ce */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0277  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x027c  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0282  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x02b6 A[SYNTHETIC, Splitter:B:123:0x02b6] */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x03f8 A[Catch:{ Throwable -> 0x0410 }] */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x03fc A[Catch:{ Throwable -> 0x0410 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01d6 A[Catch:{ Throwable -> 0x0232 }] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x023f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r26 = this;
            r1 = r26
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.String r3 = "======deleteSo====="
            com.uc.webview.export.internal.utility.Log.d(r2, r3)
            java.lang.String r2 = "CONTEXT"
            java.lang.Object r2 = r1.getOption(r2)
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r3 = "libWebCore_UC.so"
            java.lang.String r4 = "libV8_UC.so"
            java.lang.String r5 = "libandroid_uc_40.so"
            java.lang.String r6 = "libandroid_uc_41.so"
            java.lang.String r7 = "libandroid_uc_42.so"
            java.lang.String r8 = "libandroid_uc_43.so"
            java.lang.String r9 = "libandroid_uc_44.so"
            java.lang.String r10 = "libandroid_uc_50.so"
            java.lang.String r11 = "libskia_neon_uc.so"
            java.lang.String r12 = "libwebviewuc.so"
            java.lang.String r13 = "libimagehelper.so"
            java.lang.String r14 = "libvinit.so"
            java.lang.String r15 = "libInitHelper_UC.so"
            java.lang.String r16 = "libcrashsdk.so"
            java.lang.String[] r3 = new java.lang.String[]{r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16}
            java.util.List<com.uc.webview.export.internal.setup.bl> r4 = r1.a
            java.util.Iterator r4 = r4.iterator()
            r5 = 0
            r6 = 0
        L_0x0039:
            boolean r7 = r4.hasNext()
            if (r7 == 0) goto L_0x0422
            java.lang.Object r7 = r4.next()
            com.uc.webview.export.internal.setup.bl r7 = (com.uc.webview.export.internal.setup.bl) r7
            boolean r8 = r7 instanceof com.uc.webview.export.internal.setup.bu
            r10 = 1
            if (r8 == 0) goto L_0x0064
            com.uc.webview.export.internal.setup.q r8 = r7.d
            if (r8 != 0) goto L_0x0064
            java.util.concurrent.ConcurrentHashMap r8 = r1.mOptions
            java.lang.String r11 = "soFilePath"
            java.lang.Object r8 = r8.get(r11)
            java.lang.String r8 = (java.lang.String) r8
            java.util.concurrent.ConcurrentHashMap r11 = r1.mOptions
            java.lang.String r12 = "resFilePath"
            java.lang.Object r11 = r11.get(r12)
            java.lang.String r11 = (java.lang.String) r11
            r12 = 1
            goto L_0x0067
        L_0x0064:
            r8 = 0
            r11 = 0
            r12 = 0
        L_0x0067:
            com.uc.webview.export.internal.setup.q r13 = r7.d
            if (r13 == 0) goto L_0x0084
            com.uc.webview.export.internal.setup.q r13 = r7.d
            com.uc.webview.export.internal.setup.UCMPackageInfo r13 = r13.mUCM
            if (r13 == 0) goto L_0x0084
            com.uc.webview.export.internal.setup.q r8 = r7.d
            com.uc.webview.export.internal.setup.UCMPackageInfo r8 = r8.mUCM
            java.lang.String r11 = r8.dataDir
            java.lang.String r13 = r8.soDirPath
            java.lang.String r8 = r8.resDirPath
            com.uc.webview.export.internal.setup.q r14 = r7.d
            com.uc.webview.export.internal.setup.UCMPackageInfo r14 = r14.mUCM
            com.uc.webview.export.internal.setup.q r7 = r7.d
            java.lang.ClassLoader r7 = r7.mShellCL
            goto L_0x0089
        L_0x0084:
            r13 = r8
            r8 = r11
            r7 = 0
            r11 = 0
            r14 = 0
        L_0x0089:
            if (r13 == 0) goto L_0x0098
            android.content.pm.ApplicationInfo r15 = r2.getApplicationInfo()
            java.lang.String r15 = r15.nativeLibraryDir
            boolean r15 = r13.equals(r15)
            if (r15 == 0) goto L_0x0098
            r13 = 0
        L_0x0098:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r11)
            java.lang.String r9 = "_"
            r15.append(r9)
            r15.append(r13)
            java.lang.String r9 = "_"
            r15.append(r9)
            r15.append(r8)
            java.lang.String r8 = r15.toString()
            java.lang.String r8 = com.uc.webview.export.cyclone.UCCyclone.getSourceHash(r8)
            r15 = 2
            java.lang.Object[] r9 = new java.lang.Object[r15]
            r15 = 10005(0x2715, float:1.402E-41)
            r20 = r3
            java.lang.Object[] r3 = new java.lang.Object[r10]
            r3[r5] = r2
            java.lang.Object r3 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r15, r3)
            r9[r5] = r3
            java.lang.String r3 = "delcore"
            r9[r10] = r3
            r3 = 10035(0x2733, float:1.4062E-41)
            java.lang.Object r3 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r3, r9)
            java.io.File r3 = (java.io.File) r3
            java.io.File r9 = new java.io.File
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r8)
            java.lang.String r5 = "_1"
            r15.append(r5)
            java.lang.String r5 = r15.toString()
            r9.<init>(r3, r5)
            java.io.File r5 = new java.io.File
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r8)
            java.lang.String r10 = "_2"
            r15.append(r10)
            java.lang.String r10 = r15.toString()
            r5.<init>(r3, r10)
            java.io.File r10 = new java.io.File
            java.lang.StringBuilder r15 = new java.lang.StringBuilder
            r15.<init>()
            r15.append(r8)
            java.lang.String r8 = "_3"
            r15.append(r8)
            java.lang.String r8 = r15.toString()
            r10.<init>(r3, r8)
            boolean r8 = r10.exists()
            if (r8 == 0) goto L_0x0126
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.String r3 = "Skip delete UC files (over 3 times)."
            com.uc.webview.export.internal.utility.Log.d(r2, r3)
            goto L_0x0422
        L_0x0126:
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r11)
            if (r8 != 0) goto L_0x017f
            r8 = 10001(0x2711, float:1.4014E-41)
            r21 = r4
            r15 = 1
            java.lang.Object[] r4 = new java.lang.Object[r15]
            r15 = 0
            r4[r15] = r2
            java.lang.Object r4 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r8, r4)
            java.io.File r4 = (java.io.File) r4
            java.lang.String r4 = r4.getAbsolutePath()
            boolean r4 = r11.startsWith(r4)
            if (r4 == 0) goto L_0x0181
            java.io.File r3 = new java.io.File
            r3.<init>(r11)
            boolean r4 = r3.exists()
            if (r4 == 0) goto L_0x017e
            com.uc.webview.export.internal.setup.UCMRunningInfo r4 = com.uc.webview.export.utility.SetupTask.getTotalLoadedUCM()
            com.uc.webview.export.internal.setup.UCMPackageInfo r5 = r4.ucmPackageInfo
            if (r5 == 0) goto L_0x016b
            com.uc.webview.export.internal.setup.UCMPackageInfo r5 = r4.ucmPackageInfo
            java.lang.String r5 = r5.dataDir
            if (r5 == 0) goto L_0x016b
            java.io.File r9 = new java.io.File
            com.uc.webview.export.internal.setup.UCMPackageInfo r4 = r4.ucmPackageInfo
            java.lang.String r4 = r4.dataDir
            r9.<init>(r4)
            r17 = r9
            goto L_0x016d
        L_0x016b:
            r17 = 0
        L_0x016d:
            r4 = 10044(0x273c, float:1.4075E-41)
            r5 = 3
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r6 = 0
            r5[r6] = r2
            r2 = 1
            r5[r2] = r3
            r2 = 2
            r5[r2] = r17
            com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r4, r5)
        L_0x017e:
            return
        L_0x017f:
            r21 = r4
        L_0x0181:
            boolean r4 = com.uc.webview.export.internal.utility.j.a(r13)
            if (r4 != 0) goto L_0x02b1
            java.io.File r4 = new java.io.File
            r4.<init>(r13)
            java.io.File r4 = r4.getParentFile()
            r15 = 0
            java.io.File r6 = new java.io.File     // Catch:{ Throwable -> 0x01ce }
            java.lang.String r8 = "2e67cdbeb4ec133dcc8204d930aa7145"
            r6.<init>(r4, r8)     // Catch:{ Throwable -> 0x01ce }
            java.io.File r8 = new java.io.File     // Catch:{ Throwable -> 0x01ce }
            java.lang.String r11 = "299772b0fd1634653ae3c31f366de3f8"
            r8.<init>(r4, r11)     // Catch:{ Throwable -> 0x01ce }
            boolean r11 = r6.exists()     // Catch:{ Throwable -> 0x01ce }
            if (r11 == 0) goto L_0x01b7
            boolean r11 = r6.isFile()     // Catch:{ Throwable -> 0x01ce }
            if (r11 == 0) goto L_0x01b7
            long r18 = r6.length()     // Catch:{ Throwable -> 0x01ce }
            int r11 = (r18 > r15 ? 1 : (r18 == r15 ? 0 : -1))
            if (r11 != 0) goto L_0x01b7
            r6.delete()     // Catch:{ Throwable -> 0x01ce }
        L_0x01b7:
            boolean r6 = r8.exists()     // Catch:{ Throwable -> 0x01ce }
            if (r6 == 0) goto L_0x01ce
            boolean r6 = r8.isFile()     // Catch:{ Throwable -> 0x01ce }
            if (r6 == 0) goto L_0x01ce
            long r18 = r8.length()     // Catch:{ Throwable -> 0x01ce }
            int r6 = (r18 > r15 ? 1 : (r18 == r15 ? 0 : -1))
            if (r6 != 0) goto L_0x01ce
            r8.delete()     // Catch:{ Throwable -> 0x01ce }
        L_0x01ce:
            java.io.File[] r4 = r4.listFiles()     // Catch:{ Throwable -> 0x0232 }
            int r6 = r4.length     // Catch:{ Throwable -> 0x0232 }
            r8 = 0
        L_0x01d4:
            if (r8 >= r6) goto L_0x022f
            r11 = r4[r8]     // Catch:{ Throwable -> 0x0232 }
            java.lang.String r15 = r11.getName()     // Catch:{ Throwable -> 0x0232 }
            r22 = r2
            java.io.File r2 = r11.getParentFile()     // Catch:{ Throwable -> 0x022d }
            r23 = r4
            java.io.File r4 = r2.getParentFile()     // Catch:{ Throwable -> 0x022d }
            r24 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x022d }
            r6.<init>()     // Catch:{ Throwable -> 0x022d }
            java.lang.String r4 = r4.getName()     // Catch:{ Throwable -> 0x022d }
            r6.append(r4)     // Catch:{ Throwable -> 0x022d }
            java.lang.String r4 = "_"
            r6.append(r4)     // Catch:{ Throwable -> 0x022d }
            java.lang.String r2 = r2.getName()     // Catch:{ Throwable -> 0x022d }
            r6.append(r2)     // Catch:{ Throwable -> 0x022d }
            java.lang.String r2 = r6.toString()     // Catch:{ Throwable -> 0x022d }
            boolean r2 = r15.startsWith(r2)     // Catch:{ Throwable -> 0x022d }
            if (r2 == 0) goto L_0x0220
            boolean r2 = r11.isFile()     // Catch:{ Throwable -> 0x022d }
            if (r2 == 0) goto L_0x0220
            long r15 = r11.length()     // Catch:{ Throwable -> 0x022d }
            r18 = 0
            int r2 = (r15 > r18 ? 1 : (r15 == r18 ? 0 : -1))
            if (r2 != 0) goto L_0x0222
            r11.delete()     // Catch:{ Throwable -> 0x022d }
            goto L_0x0222
        L_0x0220:
            r18 = 0
        L_0x0222:
            int r8 = r8 + 1
            r15 = r18
            r2 = r22
            r4 = r23
            r6 = r24
            goto L_0x01d4
        L_0x022d:
            r0 = move-exception
            goto L_0x0235
        L_0x022f:
            r22 = r2
            goto L_0x023d
        L_0x0232:
            r0 = move-exception
            r22 = r2
        L_0x0235:
            r2 = r0
            java.lang.String r4 = "DeleteCoreTask"
            java.lang.String r6 = "delete flag:"
            com.uc.webview.export.internal.utility.Log.w(r4, r6, r2)
        L_0x023d:
            if (r7 == 0) goto L_0x0277
            java.lang.String r2 = "com.uc.webview.browser.shell.NativeLibraries"
            r15 = 1
            java.lang.Class r2 = java.lang.Class.forName(r2, r15, r7)     // Catch:{ Throwable -> 0x0274 }
            if (r2 == 0) goto L_0x0259
            java.lang.String r4 = "LIBRARIES"
            java.lang.reflect.Field r2 = r2.getDeclaredField(r4)     // Catch:{ Throwable -> 0x0274 }
            r2.setAccessible(r15)     // Catch:{ Throwable -> 0x0274 }
            r4 = 0
            java.lang.Object r2 = r2.get(r4)     // Catch:{ Throwable -> 0x0275 }
            java.lang.String[][] r2 = (java.lang.String[][]) r2     // Catch:{ Throwable -> 0x0275 }
            goto L_0x025b
        L_0x0259:
            r4 = 0
            r2 = r4
        L_0x025b:
            if (r2 == 0) goto L_0x0275
            int r6 = r2.length     // Catch:{ Throwable -> 0x0275 }
            if (r6 <= 0) goto L_0x0275
            int r6 = r2.length     // Catch:{ Throwable -> 0x0275 }
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ Throwable -> 0x0275 }
            r4 = 0
        L_0x0264:
            int r7 = r6.length     // Catch:{ Throwable -> 0x0271 }
            if (r4 >= r7) goto L_0x0271
            r7 = r2[r4]     // Catch:{ Throwable -> 0x0271 }
            r8 = 0
            r7 = r7[r8]     // Catch:{ Throwable -> 0x0272 }
            r6[r4] = r7     // Catch:{ Throwable -> 0x0272 }
            int r4 = r4 + 1
            goto L_0x0264
        L_0x0271:
            r8 = 0
        L_0x0272:
            r4 = r6
            goto L_0x027a
        L_0x0274:
            r4 = 0
        L_0x0275:
            r8 = 0
            goto L_0x027a
        L_0x0277:
            r4 = 0
            r8 = 0
            r15 = 1
        L_0x027a:
            if (r4 != 0) goto L_0x027e
            r4 = r20
        L_0x027e:
            int r2 = r4.length
            r6 = 0
        L_0x0280:
            if (r6 >= r2) goto L_0x02af
            r7 = r4[r6]
            java.io.File r11 = new java.io.File     // Catch:{ Throwable -> 0x02a2 }
            r11.<init>(r13, r7)     // Catch:{ Throwable -> 0x02a2 }
            boolean r7 = r11.exists()     // Catch:{ Throwable -> 0x02a2 }
            if (r7 == 0) goto L_0x0292
            r11.delete()     // Catch:{ Throwable -> 0x02a2 }
        L_0x0292:
            java.lang.String r7 = "DeleteCoreTask"
            java.lang.String r8 = "deleteSo:"
            java.lang.String r11 = java.lang.String.valueOf(r11)     // Catch:{ Throwable -> 0x02a2 }
            java.lang.String r8 = r8.concat(r11)     // Catch:{ Throwable -> 0x02a2 }
            com.uc.webview.export.internal.utility.Log.d(r7, r8)     // Catch:{ Throwable -> 0x02a2 }
            goto L_0x02ab
        L_0x02a2:
            r0 = move-exception
            r7 = r0
            java.lang.String r8 = "DeleteCoreTask"
            java.lang.String r11 = "deleteSo:"
            com.uc.webview.export.internal.utility.Log.w(r8, r11, r7)
        L_0x02ab:
            int r6 = r6 + 1
            r8 = 0
            goto L_0x0280
        L_0x02af:
            r6 = 1
            goto L_0x02b4
        L_0x02b1:
            r22 = r2
            r15 = 1
        L_0x02b4:
            if (r12 != 0) goto L_0x03e3
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x02de }
            android.util.Pair<java.lang.String, java.lang.String> r4 = r14.coreImplModule     // Catch:{ Throwable -> 0x02de }
            java.lang.Object r4 = r4.first     // Catch:{ Throwable -> 0x02de }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x02de }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x02de }
            r2.delete()     // Catch:{ Throwable -> 0x02de }
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02de }
            java.lang.String r6 = "delete dex:"
            r4.<init>(r6)     // Catch:{ Throwable -> 0x02de }
            android.util.Pair<java.lang.String, java.lang.String> r6 = r14.coreImplModule     // Catch:{ Throwable -> 0x02de }
            java.lang.Object r6 = r6.first     // Catch:{ Throwable -> 0x02de }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x02de }
            r4.append(r6)     // Catch:{ Throwable -> 0x02de }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x02de }
            com.uc.webview.export.internal.utility.Log.d(r2, r4)     // Catch:{ Throwable -> 0x02de }
            goto L_0x02e7
        L_0x02de:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "DeleteCoreTask"
            java.lang.String r6 = "deleteSo:"
            com.uc.webview.export.internal.utility.Log.w(r4, r6, r2)
        L_0x02e7:
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x030f }
            android.util.Pair<java.lang.String, java.lang.String> r4 = r14.browserIFModule     // Catch:{ Throwable -> 0x030f }
            java.lang.Object r4 = r4.first     // Catch:{ Throwable -> 0x030f }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x030f }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x030f }
            r2.delete()     // Catch:{ Throwable -> 0x030f }
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x030f }
            java.lang.String r6 = "delete dex:"
            r4.<init>(r6)     // Catch:{ Throwable -> 0x030f }
            android.util.Pair<java.lang.String, java.lang.String> r6 = r14.browserIFModule     // Catch:{ Throwable -> 0x030f }
            java.lang.Object r6 = r6.first     // Catch:{ Throwable -> 0x030f }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x030f }
            r4.append(r6)     // Catch:{ Throwable -> 0x030f }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.utility.Log.d(r2, r4)     // Catch:{ Throwable -> 0x030f }
            goto L_0x0318
        L_0x030f:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "DeleteCoreTask"
            java.lang.String r6 = "deleteSo:"
            com.uc.webview.export.internal.utility.Log.w(r4, r6, r2)
        L_0x0318:
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0340 }
            android.util.Pair<java.lang.String, java.lang.String> r4 = r14.sdkShellModule     // Catch:{ Throwable -> 0x0340 }
            java.lang.Object r4 = r4.first     // Catch:{ Throwable -> 0x0340 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0340 }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0340 }
            r2.delete()     // Catch:{ Throwable -> 0x0340 }
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0340 }
            java.lang.String r6 = "delete dex:"
            r4.<init>(r6)     // Catch:{ Throwable -> 0x0340 }
            android.util.Pair<java.lang.String, java.lang.String> r6 = r14.sdkShellModule     // Catch:{ Throwable -> 0x0340 }
            java.lang.Object r6 = r6.first     // Catch:{ Throwable -> 0x0340 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x0340 }
            r4.append(r6)     // Catch:{ Throwable -> 0x0340 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0340 }
            com.uc.webview.export.internal.utility.Log.d(r2, r4)     // Catch:{ Throwable -> 0x0340 }
            goto L_0x0349
        L_0x0340:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "DeleteCoreTask"
            java.lang.String r6 = "deleteSo:"
            com.uc.webview.export.internal.utility.Log.w(r4, r6, r2)
        L_0x0349:
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0373 }
            android.util.Pair<java.lang.String, java.lang.String> r4 = r14.coreImplModule     // Catch:{ Throwable -> 0x0373 }
            java.lang.Object r4 = r4.second     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r6 = "dex.dex"
            r2.<init>(r4, r6)     // Catch:{ Throwable -> 0x0373 }
            r2.delete()     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r6 = "delete odex:"
            r4.<init>(r6)     // Catch:{ Throwable -> 0x0373 }
            android.util.Pair<java.lang.String, java.lang.String> r6 = r14.coreImplModule     // Catch:{ Throwable -> 0x0373 }
            java.lang.Object r6 = r6.first     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x0373 }
            r4.append(r6)     // Catch:{ Throwable -> 0x0373 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0373 }
            com.uc.webview.export.internal.utility.Log.d(r2, r4)     // Catch:{ Throwable -> 0x0373 }
            goto L_0x037c
        L_0x0373:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "DeleteCoreTask"
            java.lang.String r6 = "deleteSo:"
            com.uc.webview.export.internal.utility.Log.w(r4, r6, r2)
        L_0x037c:
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x03a6 }
            android.util.Pair<java.lang.String, java.lang.String> r4 = r14.browserIFModule     // Catch:{ Throwable -> 0x03a6 }
            java.lang.Object r4 = r4.second     // Catch:{ Throwable -> 0x03a6 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x03a6 }
            java.lang.String r6 = "browser_if.dex"
            r2.<init>(r4, r6)     // Catch:{ Throwable -> 0x03a6 }
            r2.delete()     // Catch:{ Throwable -> 0x03a6 }
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x03a6 }
            java.lang.String r6 = "delete odex:"
            r4.<init>(r6)     // Catch:{ Throwable -> 0x03a6 }
            android.util.Pair<java.lang.String, java.lang.String> r6 = r14.browserIFModule     // Catch:{ Throwable -> 0x03a6 }
            java.lang.Object r6 = r6.first     // Catch:{ Throwable -> 0x03a6 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x03a6 }
            r4.append(r6)     // Catch:{ Throwable -> 0x03a6 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x03a6 }
            com.uc.webview.export.internal.utility.Log.d(r2, r4)     // Catch:{ Throwable -> 0x03a6 }
            goto L_0x03af
        L_0x03a6:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "DeleteCoreTask"
            java.lang.String r6 = "deleteSo:"
            com.uc.webview.export.internal.utility.Log.w(r4, r6, r2)
        L_0x03af:
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x03d9 }
            android.util.Pair<java.lang.String, java.lang.String> r4 = r14.sdkShellModule     // Catch:{ Throwable -> 0x03d9 }
            java.lang.Object r4 = r4.second     // Catch:{ Throwable -> 0x03d9 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x03d9 }
            java.lang.String r6 = "sdk_shell.dex"
            r2.<init>(r4, r6)     // Catch:{ Throwable -> 0x03d9 }
            r2.delete()     // Catch:{ Throwable -> 0x03d9 }
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x03d9 }
            java.lang.String r6 = "delete odex:"
            r4.<init>(r6)     // Catch:{ Throwable -> 0x03d9 }
            android.util.Pair<java.lang.String, java.lang.String> r6 = r14.sdkShellModule     // Catch:{ Throwable -> 0x03d9 }
            java.lang.Object r6 = r6.first     // Catch:{ Throwable -> 0x03d9 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Throwable -> 0x03d9 }
            r4.append(r6)     // Catch:{ Throwable -> 0x03d9 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x03d9 }
            com.uc.webview.export.internal.utility.Log.d(r2, r4)     // Catch:{ Throwable -> 0x03d9 }
            goto L_0x03e2
        L_0x03d9:
            r0 = move-exception
            r2 = r0
            java.lang.String r4 = "DeleteCoreTask"
            java.lang.String r6 = "deleteSo:"
            com.uc.webview.export.internal.utility.Log.w(r4, r6, r2)
        L_0x03e2:
            r6 = 1
        L_0x03e3:
            java.lang.String r2 = "DeleteCoreTask"
            java.lang.String r4 = "deleteCoreFlagDir:"
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ Throwable -> 0x0410 }
            java.lang.String r3 = r4.concat(r3)     // Catch:{ Throwable -> 0x0410 }
            com.uc.webview.export.internal.utility.Log.d(r2, r3)     // Catch:{ Throwable -> 0x0410 }
            boolean r2 = r9.exists()     // Catch:{ Throwable -> 0x0410 }
            if (r2 != 0) goto L_0x03fc
            r9.createNewFile()     // Catch:{ Throwable -> 0x0410 }
            goto L_0x0419
        L_0x03fc:
            boolean r2 = r5.exists()     // Catch:{ Throwable -> 0x0410 }
            if (r2 != 0) goto L_0x0406
            r5.createNewFile()     // Catch:{ Throwable -> 0x0410 }
            goto L_0x0419
        L_0x0406:
            boolean r2 = r10.exists()     // Catch:{ Throwable -> 0x0410 }
            if (r2 != 0) goto L_0x0419
            r10.createNewFile()     // Catch:{ Throwable -> 0x0410 }
            goto L_0x0419
        L_0x0410:
            r0 = move-exception
            r2 = r0
            java.lang.String r3 = "DeleteCoreTask"
            java.lang.String r4 = "deleteCoreFlag:"
            com.uc.webview.export.internal.utility.Log.w(r3, r4, r2)
        L_0x0419:
            r3 = r20
            r4 = r21
            r2 = r22
            r5 = 0
            goto L_0x0039
        L_0x0422:
            java.util.List<com.uc.webview.export.internal.setup.bl> r2 = r1.a
            r2.clear()
            if (r6 == 0) goto L_0x042e
            java.lang.String r2 = "sdk_stp_dcc"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)
        L_0x042e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.o.run():void");
    }
}
