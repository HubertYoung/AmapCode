package com.uc.webview.export.internal.setup;

/* compiled from: ProGuard */
public final class u extends UCSubSetupTask<u, u> {
    private String a = null;

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0085 A[Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ec A[SYNTHETIC, Splitter:B:31:0x00ec] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x019d A[Catch:{ Throwable -> 0x01b2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01a6 A[Catch:{ Throwable -> 0x01b2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01b4  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x01b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r12 = this;
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            r0 = 11
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r0)
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            java.lang.ClassLoader r0 = r12.mCL
            com.uc.webview.export.internal.SDKFactory.c = r0
            com.uc.webview.export.internal.uc.CoreFactory.updateLazy()
            java.lang.String r0 = "LibraryTask.setUpEnv"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r0)
            java.util.concurrent.ConcurrentHashMap r0 = r12.mOptions
            java.lang.String r1 = "CONTEXT"
            java.lang.Object r0 = r0.get(r1)
            android.content.Context r0 = (android.content.Context) r0
            com.uc.webview.export.internal.setup.UCMPackageInfo r1 = r12.mUCM
            java.lang.String r1 = r1.soDirPath
            java.lang.String r2 = r12.a
            if (r2 != 0) goto L_0x002f
            com.uc.webview.export.internal.setup.UCMPackageInfo r2 = r12.mUCM
            java.lang.String r2 = r2.mainLibrary
            r12.a = r2
        L_0x002f:
            r2 = 0
            com.uc.webview.export.internal.setup.UCMPackageInfo r3 = r12.mUCM     // Catch:{ Throwable -> 0x0047 }
            android.util.Pair<java.lang.String, java.lang.String> r3 = r3.coreImplModule     // Catch:{ Throwable -> 0x0047 }
            java.lang.Object r3 = r3.first     // Catch:{ Throwable -> 0x0047 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x0047 }
            com.uc.webview.export.internal.setup.UCMPackageInfo r4 = r12.mUCM     // Catch:{ Throwable -> 0x0045 }
            android.util.Pair<java.lang.String, java.lang.String> r4 = r4.coreImplModule     // Catch:{ Throwable -> 0x0045 }
            java.lang.Object r4 = r4.second     // Catch:{ Throwable -> 0x0045 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x0045 }
            com.uc.webview.export.internal.setup.UCMPackageInfo r5 = r12.mUCM     // Catch:{ Throwable -> 0x0049 }
            java.lang.String r5 = r5.resDirPath     // Catch:{ Throwable -> 0x0049 }
            goto L_0x004a
        L_0x0045:
            r4 = r2
            goto L_0x0049
        L_0x0047:
            r3 = r2
            r4 = r3
        L_0x0049:
            r5 = r2
        L_0x004a:
            java.lang.String r6 = "4"
            com.uc.webview.export.cyclone.UCElapseTime r7 = new com.uc.webview.export.cyclone.UCElapseTime
            r7.<init>()
            java.lang.String r8 = "PRIVATE_DATA_DIRECTORY_SUFFIX"
            java.lang.Object r8 = r12.getOption(r8)
            java.lang.String r8 = (java.lang.String) r8
            java.util.HashMap r9 = new java.util.HashMap
            r9.<init>()
            java.lang.String r10 = "ucm_dex_path"
            r9.put(r10, r3)
            java.lang.String r3 = "ucm_odex_path"
            r9.put(r3, r4)
            java.lang.String r3 = "ucm_private_data_dir_suffix"
            r9.put(r3, r8)
            java.lang.String r3 = "ucm_paks_resource_dir"
            r9.put(r3, r5)
            r3 = 40
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r3)
            r3 = 3007(0xbbf, float:4.214E-42)
            r4 = 0
            boolean r5 = com.uc.webview.export.internal.utility.j.a(r1)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            r8 = 1
            if (r5 != 0) goto L_0x00df
            java.io.File r5 = new java.io.File     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            r5.<init>(r1)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            boolean r10 = r5.isDirectory()     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            if (r10 != 0) goto L_0x00a2
            com.uc.webview.export.internal.setup.UCSetupException r5 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            r10 = 3006(0xbbe, float:4.212E-42)
            java.lang.String r11 = "Directory expected for LibraryTask but [%s] given."
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            r8[r4] = r1     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r1 = java.lang.String.format(r11, r8)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            r5.<init>(r10, r1)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            throw r5     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
        L_0x00a2:
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r1 = r5.getAbsolutePath()     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r5 = "ucm_corelib_path"
            r9.put(r5, r1)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r1 = "ucm_multi_process"
            int r5 = com.uc.webview.export.internal.SDKFactory.u     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            r9.put(r1, r5)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r1 = "ucm_multi_process_fallback_timeout"
            int r5 = com.uc.webview.export.internal.SDKFactory.v     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            r9.put(r1, r5)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r1 = "ucm_multi_process_enable_service_speedup"
            boolean r5 = com.uc.webview.export.internal.SDKFactory.w     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            r9.put(r1, r5)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            com.uc.webview.export.internal.uc.CoreFactory.initUCMobileWebkitCoreSoEnv(r0, r9)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r1 = "CoreFactory.initUCMobileWebkitCoreSoEnv1"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r1)     // Catch:{ UCSetupException -> 0x00e9, Throwable -> 0x00e1 }
            java.lang.String r1 = "1"
            r6 = r1
            r4 = 1
        L_0x00df:
            r1 = r2
            goto L_0x00ea
        L_0x00e1:
            r1 = move-exception
            com.uc.webview.export.internal.setup.UCSetupException r5 = new com.uc.webview.export.internal.setup.UCSetupException
            r5.<init>(r3, r1)
            r1 = r5
            goto L_0x00ea
        L_0x00e9:
            r1 = move-exception
        L_0x00ea:
            if (r4 != 0) goto L_0x0161
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.io.File r4 = new java.io.File     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            android.content.pm.ApplicationInfo r5 = r0.getApplicationInfo()     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r5 = r5.nativeLibraryDir     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            r4.<init>(r5)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.io.File r5 = new java.io.File     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r10 = "lib"
            r8.<init>(r10)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r10 = r12.a     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            r8.append(r10)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r10 = ".so"
            r8.append(r10)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r8 = r8.toString()     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            r5.<init>(r4, r8)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            boolean r5 = r5.exists()     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            if (r5 == 0) goto L_0x014e
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r5 = "ucm_corelib_path"
            r9.put(r5, r4)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r4 = "ucm_multi_process"
            int r5 = com.uc.webview.export.internal.SDKFactory.u     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            r9.put(r4, r5)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r4 = "ucm_multi_process_fallback_timeout"
            int r5 = com.uc.webview.export.internal.SDKFactory.v     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            r9.put(r4, r5)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r4 = "ucm_multi_process_enable_service_speedup"
            boolean r5 = com.uc.webview.export.internal.SDKFactory.w     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            r9.put(r4, r5)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            com.uc.webview.export.internal.uc.CoreFactory.initUCMobileWebkitCoreSoEnv(r0, r9)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            java.lang.String r0 = "2"
            r6 = r0
        L_0x014e:
            java.lang.String r0 = "CoreFactory.initUCMobileWebkitCoreSoEnv2"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r0)     // Catch:{ UCSetupException -> 0x015d, Throwable -> 0x0154 }
            goto L_0x0161
        L_0x0154:
            r0 = move-exception
            if (r1 != 0) goto L_0x0161
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r1.<init>(r3, r0)
            goto L_0x0161
        L_0x015d:
            r0 = move-exception
            if (r1 != 0) goto L_0x0161
            r1 = r0
        L_0x0161:
            r0 = 41
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r0)
            android.util.Pair r0 = new android.util.Pair     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r3 = "sdk_lib"
            com.uc.webview.export.cyclone.UCHashMap r4 = new com.uc.webview.export.cyclone.UCHashMap     // Catch:{ Throwable -> 0x01b2 }
            r4.<init>()     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r5 = "cnt"
            java.lang.String r8 = "1"
            com.uc.webview.export.cyclone.UCHashMap r4 = r4.set(r5, r8)     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r5 = "code"
            com.uc.webview.export.cyclone.UCHashMap r4 = r4.set(r5, r6)     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r5 = "cost_cpu"
            long r8 = r7.getMilisCpu()     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r6 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x01b2 }
            com.uc.webview.export.cyclone.UCHashMap r4 = r4.set(r5, r6)     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r5 = "cost"
            long r6 = r7.getMilis()     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x01b2 }
            com.uc.webview.export.cyclone.UCHashMap r4 = r4.set(r5, r6)     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r5 = "data"
            if (r1 == 0) goto L_0x01a6
            int r6 = r1.errCode()     // Catch:{ Throwable -> 0x01b2 }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x01b2 }
            goto L_0x01a8
        L_0x01a6:
            java.lang.String r6 = ""
        L_0x01a8:
            com.uc.webview.export.cyclone.UCHashMap r4 = r4.set(r5, r6)     // Catch:{ Throwable -> 0x01b2 }
            r0.<init>(r3, r4)     // Catch:{ Throwable -> 0x01b2 }
            r12.callbackStat(r0)     // Catch:{ Throwable -> 0x01b2 }
        L_0x01b2:
            if (r1 == 0) goto L_0x01b5
            throw r1
        L_0x01b5:
            android.util.Pair r0 = new android.util.Pair
            java.lang.String r1 = "sdk_stp_l"
            r0.<init>(r1, r2)
            r12.callbackStat(r0)
            r0 = 12
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r0)
            java.lang.String r0 = "LibraryTask.run"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.u.run():void");
    }
}
