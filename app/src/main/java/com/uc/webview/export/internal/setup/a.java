package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class a implements ValueCallback<t> {
    final /* synthetic */ BrowserSetupTask a;

    a(BrowserSetupTask browserSetupTask) {
        this.a = browserSetupTask;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(46:2|3|(3:5|(1:7)(1:8)|9)|10|11|12|(1:14)(1:15)|16|17|18|19|20|21|22|(1:24)(2:25|(1:27)(1:28))|31|32|(1:34)(1:35)|36|(1:38)(1:39)|40|(1:42)(1:43)|44|(1:46)(1:47)|48|49|51|52|53|54|55|56|(1:64)(1:63)|65|(3:67|(1:69)(1:70)|71)|(3:73|(1:75)(1:76)|77)|(1:79)|80|81|82|83|(1:85)|86|88|89|(3:91|92|93)) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x00b5 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x00c4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x01c0 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x01cd */
    /* JADX WARNING: Missing exception handler attribute for start block: B:80:0x0256 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:82:0x028a */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00da A[Catch:{ Throwable -> 0x00ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00dd A[Catch:{ Throwable -> 0x00ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x011e A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0121 A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x013f A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0142 A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0152 A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0155 A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x019e A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01a1 A[Catch:{ Throwable -> 0x01b5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01d9 A[Catch:{ Throwable -> 0x0256 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x01f8 A[Catch:{ Throwable -> 0x0256 }] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0219 A[Catch:{ Throwable -> 0x0256 }] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0235 A[Catch:{ Throwable -> 0x0256 }] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0292 A[Catch:{ Throwable -> 0x02cd }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x02dd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onReceiveValue(java.lang.Object r19) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            com.uc.webview.export.internal.setup.UCMRunningInfo r3 = r2.getLoadedUCM()
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L_0x031e
            com.uc.webview.export.internal.setup.UCMRunningInfo r2 = r2.getLoadedUCM()     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.setup.BrowserSetupTask r3 = r1.a     // Catch:{ Throwable -> 0x030f }
            r3.setLoadedUCM(r2)     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.setup.BrowserSetupTask r3 = r1.a     // Catch:{ Throwable -> 0x030f }
            r3.setTotalLoadedUCM(r2)     // Catch:{ Throwable -> 0x030f }
            int r3 = r2.loadType     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.SDKFactory.o = r3     // Catch:{ Throwable -> 0x030f }
            java.lang.String r3 = "d"
            java.lang.String r6 = "BrowserSetupTask"
            com.uc.webview.export.cyclone.UCLogger r3 = com.uc.webview.export.cyclone.UCLogger.create(r3, r6)     // Catch:{ Throwable -> 0x030f }
            r6 = 2
            if (r3 == 0) goto L_0x0065
            java.lang.String r7 = "mSuccessCB: dataDir is [%s] core type: [%d] isShareCore{%b}."
            r8 = 3
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.setup.UCMRunningInfo r9 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.setup.UCMPackageInfo r9 = r9.ucmPackageInfo     // Catch:{ Throwable -> 0x030f }
            if (r9 == 0) goto L_0x0041
            com.uc.webview.export.internal.setup.UCMRunningInfo r9 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.setup.UCMPackageInfo r9 = r9.ucmPackageInfo     // Catch:{ Throwable -> 0x030f }
            java.lang.String r9 = r9.dataDir     // Catch:{ Throwable -> 0x030f }
            goto L_0x0042
        L_0x0041:
            r9 = 0
        L_0x0042:
            r8[r5] = r9     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.setup.UCMRunningInfo r9 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x030f }
            int r9 = r9.coreType     // Catch:{ Throwable -> 0x030f }
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch:{ Throwable -> 0x030f }
            r8[r4] = r9     // Catch:{ Throwable -> 0x030f }
            com.uc.webview.export.internal.setup.UCMRunningInfo r9 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x030f }
            boolean r9 = r9.isShareCore     // Catch:{ Throwable -> 0x030f }
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)     // Catch:{ Throwable -> 0x030f }
            r8[r6] = r9     // Catch:{ Throwable -> 0x030f }
            java.lang.String r7 = java.lang.String.format(r7, r8)     // Catch:{ Throwable -> 0x030f }
            java.lang.Throwable[] r8 = new java.lang.Throwable[r5]     // Catch:{ Throwable -> 0x030f }
            r3.print(r7, r8)     // Catch:{ Throwable -> 0x030f }
        L_0x0065:
            r7 = 10001(0x2711, float:1.4014E-41)
            com.uc.webview.export.internal.setup.p r8 = new com.uc.webview.export.internal.setup.p     // Catch:{ Throwable -> 0x00b5 }
            r8.<init>()     // Catch:{ Throwable -> 0x00b5 }
            java.lang.Object[] r9 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.UCAsyncTask r10 = com.uc.webview.export.utility.SetupTask.getRoot()     // Catch:{ Throwable -> 0x00b5 }
            r9[r5] = r10     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.UCAsyncTask r8 = r8.invoke(r7, r9)     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.p r8 = (com.uc.webview.export.internal.setup.p) r8     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.BrowserSetupTask r9 = r1.a     // Catch:{ Throwable -> 0x00b5 }
            java.util.concurrent.ConcurrentHashMap r9 = r9.mOptions     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.UCSubSetupTask r8 = r8.setOptions(r9)     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.p r8 = (com.uc.webview.export.internal.setup.p) r8     // Catch:{ Throwable -> 0x00b5 }
            java.lang.String r9 = "del_dec_fil"
            com.uc.webview.export.internal.setup.BrowserSetupTask r10 = r1.a     // Catch:{ Throwable -> 0x00b5 }
            java.io.File r10 = r10.e     // Catch:{ Throwable -> 0x00b5 }
            if (r10 != 0) goto L_0x0090
            r10 = 1
            goto L_0x0091
        L_0x0090:
            r10 = 0
        L_0x0091:
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r10)     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.UCSubSetupTask r8 = r8.setup(r9, r10)     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.p r8 = (com.uc.webview.export.internal.setup.p) r8     // Catch:{ Throwable -> 0x00b5 }
            java.lang.String r9 = "del_upd_fil"
            java.lang.Boolean r10 = java.lang.Boolean.TRUE     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.UCSubSetupTask r8 = r8.setup(r9, r10)     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.p r8 = (com.uc.webview.export.internal.setup.p) r8     // Catch:{ Throwable -> 0x00b5 }
            java.lang.String r9 = "die"
            com.uc.webview.export.internal.setup.b r10 = new com.uc.webview.export.internal.setup.b     // Catch:{ Throwable -> 0x00b5 }
            r10.<init>(r1)     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.UCAsyncTask r8 = r8.onEvent(r9, r10)     // Catch:{ Throwable -> 0x00b5 }
            com.uc.webview.export.internal.setup.p r8 = (com.uc.webview.export.internal.setup.p) r8     // Catch:{ Throwable -> 0x00b5 }
            r8.start()     // Catch:{ Throwable -> 0x00b5 }
        L_0x00b5:
            com.uc.webview.export.internal.setup.BrowserSetupTask r8 = r1.a     // Catch:{ Throwable -> 0x00c4 }
            com.uc.webview.export.internal.setup.UCMRunningInfo r9 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x00c4 }
            int r9 = r9.coreType     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Throwable -> 0x00c4 }
            r8.callbackFinishStat(r9)     // Catch:{ Throwable -> 0x00c4 }
        L_0x00c4:
            com.uc.webview.export.internal.setup.BrowserSetupTask r8 = r1.a     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r9 = "setup_priority"
            java.lang.Object r8 = r8.getOption(r9)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.Integer r8 = (java.lang.Integer) r8     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.internal.setup.BrowserSetupTask r9 = r1.a     // Catch:{ Throwable -> 0x00ef }
            java.lang.String r10 = "dlChecker"
            java.lang.Object r9 = r9.getOption(r10)     // Catch:{ Throwable -> 0x00ef }
            java.util.concurrent.Callable r9 = (java.util.concurrent.Callable) r9     // Catch:{ Throwable -> 0x00ef }
            if (r9 != 0) goto L_0x00dd
            java.lang.String r9 = "N"
            goto L_0x00f1
        L_0x00dd:
            java.lang.Object r9 = r9.call()     // Catch:{ Throwable -> 0x00ef }
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch:{ Throwable -> 0x00ef }
            boolean r9 = r9.booleanValue()     // Catch:{ Throwable -> 0x00ef }
            if (r9 == 0) goto L_0x00ec
            java.lang.String r9 = "T"
            goto L_0x00f1
        L_0x00ec:
            java.lang.String r9 = "F"
            goto L_0x00f1
        L_0x00ef:
            java.lang.String r9 = "E"
        L_0x00f1:
            com.uc.webview.export.internal.setup.BrowserSetupTask r10 = r1.a     // Catch:{ Throwable -> 0x01b5 }
            android.util.Pair r11 = new android.util.Pair     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r12 = "sdk_stp_suc"
            com.uc.webview.export.cyclone.UCHashMap r13 = new com.uc.webview.export.cyclone.UCHashMap     // Catch:{ Throwable -> 0x01b5 }
            r13.<init>()     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r14 = "cnt"
            java.lang.String r15 = "1"
            com.uc.webview.export.cyclone.UCHashMap r13 = r13.set(r14, r15)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r14 = "code"
            com.uc.webview.export.internal.setup.UCMRunningInfo r15 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x01b5 }
            int r15 = r15.coreType     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r15 = java.lang.String.valueOf(r15)     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.cyclone.UCHashMap r13 = r13.set(r14, r15)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r14 = "dir"
            com.uc.webview.export.internal.setup.UCMRunningInfo r15 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.internal.setup.UCMPackageInfo r15 = r15.ucmPackageInfo     // Catch:{ Throwable -> 0x01b5 }
            if (r15 != 0) goto L_0x0121
            java.lang.String r15 = "null"
            goto L_0x0131
        L_0x0121:
            com.uc.webview.export.internal.setup.UCMRunningInfo r15 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.internal.setup.UCMPackageInfo r15 = r15.ucmPackageInfo     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.internal.setup.BrowserSetupTask r7 = r1.a     // Catch:{ Throwable -> 0x01b5 }
            android.content.Context r7 = r7.d     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r15 = r15.getDirAlias(r7)     // Catch:{ Throwable -> 0x01b5 }
        L_0x0131:
            com.uc.webview.export.cyclone.UCHashMap r7 = r13.set(r14, r15)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r13 = "old"
            com.uc.webview.export.internal.setup.UCMRunningInfo r14 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x01b5 }
            boolean r14 = r14.isOldExtraKernel     // Catch:{ Throwable -> 0x01b5 }
            if (r14 == 0) goto L_0x0142
            java.lang.String r14 = "T"
            goto L_0x0144
        L_0x0142:
            java.lang.String r14 = "F"
        L_0x0144:
            com.uc.webview.export.cyclone.UCHashMap r7 = r7.set(r13, r14)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r13 = "frun"
            com.uc.webview.export.internal.setup.UCMRunningInfo r14 = com.uc.webview.export.internal.setup.UCSetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x01b5 }
            boolean r14 = r14.isFirstTimeOdex     // Catch:{ Throwable -> 0x01b5 }
            if (r14 == 0) goto L_0x0155
            java.lang.String r14 = "T"
            goto L_0x0157
        L_0x0155:
            java.lang.String r14 = "F"
        L_0x0157:
            com.uc.webview.export.cyclone.UCHashMap r7 = r7.set(r13, r14)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r13 = "cpu_cnt"
            java.lang.String r14 = com.uc.webview.export.internal.utility.j.a()     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.cyclone.UCHashMap r7 = r7.set(r13, r14)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r13 = "cpu_freq"
            java.lang.String r14 = com.uc.webview.export.internal.utility.j.b()     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.cyclone.UCHashMap r7 = r7.set(r13, r14)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r13 = "cost_cpu"
            long r14 = android.os.SystemClock.currentThreadTimeMillis()     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.internal.setup.BrowserSetupTask r6 = r1.a     // Catch:{ Throwable -> 0x01b5 }
            long r16 = r6.g     // Catch:{ Throwable -> 0x01b5 }
            r6 = 0
            long r14 = r14 - r16
            java.lang.String r6 = java.lang.String.valueOf(r14)     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.cyclone.UCHashMap r6 = r7.set(r13, r6)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r7 = "cost"
            com.uc.webview.export.internal.setup.BrowserSetupTask r13 = r1.a     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.cyclone.UCElapseTime r13 = r13.f     // Catch:{ Throwable -> 0x01b5 }
            long r13 = r13.getMilis()     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r13 = java.lang.String.valueOf(r13)     // Catch:{ Throwable -> 0x01b5 }
            com.uc.webview.export.cyclone.UCHashMap r6 = r6.set(r7, r13)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r7 = "pri"
            if (r8 != 0) goto L_0x01a1
            java.lang.String r8 = "n"
            goto L_0x01a5
        L_0x01a1:
            java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Throwable -> 0x01b5 }
        L_0x01a5:
            com.uc.webview.export.cyclone.UCHashMap r6 = r6.set(r7, r8)     // Catch:{ Throwable -> 0x01b5 }
            java.lang.String r7 = "wifi"
            com.uc.webview.export.cyclone.UCHashMap r6 = r6.set(r7, r9)     // Catch:{ Throwable -> 0x01b5 }
            r11.<init>(r12, r6)     // Catch:{ Throwable -> 0x01b5 }
            r10.callbackStat(r11)     // Catch:{ Throwable -> 0x01b5 }
        L_0x01b5:
            r6 = 10041(0x2739, float:1.407E-41)
            java.lang.Object[] r7 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x01c0 }
            java.lang.ClassLoader r2 = r2.shellClassLoader     // Catch:{ Throwable -> 0x01c0 }
            r7[r5] = r2     // Catch:{ Throwable -> 0x01c0 }
            com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r6, r7)     // Catch:{ Throwable -> 0x01c0 }
        L_0x01c0:
            com.uc.webview.export.internal.setup.BrowserSetupTask r2 = r1.a     // Catch:{ Throwable -> 0x01cd }
            java.lang.String r6 = "load_share_core_host"
            java.lang.Object r2 = r2.getOption(r6)     // Catch:{ Throwable -> 0x01cd }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x01cd }
            com.uc.webview.export.internal.utility.g.a(r2)     // Catch:{ Throwable -> 0x01cd }
        L_0x01cd:
            com.uc.webview.export.internal.setup.BrowserSetupTask r2 = r1.a     // Catch:{ Throwable -> 0x0256 }
            java.lang.String r6 = "vmsize_saving"
            java.lang.Object r2 = r2.getOption(r6)     // Catch:{ Throwable -> 0x0256 }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ Throwable -> 0x0256 }
            if (r2 != 0) goto L_0x01e3
            double r6 = java.lang.Math.random()     // Catch:{ Throwable -> 0x0256 }
            r8 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 > 0) goto L_0x01eb
        L_0x01e3:
            if (r2 == 0) goto L_0x01ed
            boolean r2 = r2.booleanValue()     // Catch:{ Throwable -> 0x0256 }
            if (r2 == 0) goto L_0x01ed
        L_0x01eb:
            r2 = 1
            goto L_0x01ee
        L_0x01ed:
            r2 = 0
        L_0x01ee:
            java.lang.String r6 = "com.uc.crashsdk.export.CrashApi"
            java.lang.String r7 = "getInstance"
            java.lang.Object r6 = com.uc.webview.export.internal.utility.ReflectionUtil.invokeNoThrow(r6, r7)     // Catch:{ Throwable -> 0x0256 }
            if (r6 == 0) goto L_0x0217
            java.lang.String r7 = "addHeaderInfo"
            r8 = 2
            java.lang.Class[] r9 = new java.lang.Class[r8]     // Catch:{ Throwable -> 0x0256 }
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            r9[r5] = r10     // Catch:{ Throwable -> 0x0256 }
            java.lang.Class<java.lang.String> r10 = java.lang.String.class
            r9[r4] = r10     // Catch:{ Throwable -> 0x0256 }
            java.lang.Object[] r10 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x0256 }
            java.lang.String r8 = "vmsize_saving_enable"
            r10[r5] = r8     // Catch:{ Throwable -> 0x0256 }
            if (r2 == 0) goto L_0x0210
            java.lang.String r8 = "true"
            goto L_0x0212
        L_0x0210:
            java.lang.String r8 = "false"
        L_0x0212:
            r10[r4] = r8     // Catch:{ Throwable -> 0x0256 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invokeNoThrow(r6, r7, r9, r10)     // Catch:{ Throwable -> 0x0256 }
        L_0x0217:
            if (r3 == 0) goto L_0x0233
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0256 }
            java.lang.String r7 = "mSuccessCB: vmsize_saving_enable="
            r6.<init>(r7)     // Catch:{ Throwable -> 0x0256 }
            if (r2 == 0) goto L_0x0225
            java.lang.String r7 = "true"
            goto L_0x0227
        L_0x0225:
            java.lang.String r7 = "false"
        L_0x0227:
            r6.append(r7)     // Catch:{ Throwable -> 0x0256 }
            java.lang.String r6 = r6.toString()     // Catch:{ Throwable -> 0x0256 }
            java.lang.Throwable[] r7 = new java.lang.Throwable[r5]     // Catch:{ Throwable -> 0x0256 }
            r3.print(r6, r7)     // Catch:{ Throwable -> 0x0256 }
        L_0x0233:
            if (r2 == 0) goto L_0x0256
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = new com.uc.webview.export.internal.setup.UCAsyncTask     // Catch:{ Throwable -> 0x0256 }
            com.uc.webview.export.cyclone.UCVmsize r3 = new com.uc.webview.export.cyclone.UCVmsize     // Catch:{ Throwable -> 0x0256 }
            com.uc.webview.export.internal.setup.BrowserSetupTask r6 = r1.a     // Catch:{ Throwable -> 0x0256 }
            android.content.Context r6 = r6.d     // Catch:{ Throwable -> 0x0256 }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0256 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0256 }
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0256 }
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = com.uc.webview.export.utility.SetupTask.getRoot()     // Catch:{ Throwable -> 0x0256 }
            r3[r5] = r6     // Catch:{ Throwable -> 0x0256 }
            r6 = 10001(0x2711, float:1.4014E-41)
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r6, r3)     // Catch:{ Throwable -> 0x0256 }
            r2.start()     // Catch:{ Throwable -> 0x0256 }
        L_0x0256:
            com.uc.webview.export.internal.setup.ay r2 = new com.uc.webview.export.internal.setup.ay     // Catch:{ Throwable -> 0x028a }
            r2.<init>()     // Catch:{ Throwable -> 0x028a }
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x028a }
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = com.uc.webview.export.utility.SetupTask.getRoot()     // Catch:{ Throwable -> 0x028a }
            r3[r5] = r6     // Catch:{ Throwable -> 0x028a }
            r6 = 10001(0x2711, float:1.4014E-41)
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r6, r3)     // Catch:{ Throwable -> 0x028a }
            com.uc.webview.export.internal.setup.ay r2 = (com.uc.webview.export.internal.setup.ay) r2     // Catch:{ Throwable -> 0x028a }
            com.uc.webview.export.internal.setup.BrowserSetupTask r3 = r1.a     // Catch:{ Throwable -> 0x028a }
            java.util.concurrent.ConcurrentHashMap r3 = r3.mOptions     // Catch:{ Throwable -> 0x028a }
            com.uc.webview.export.internal.setup.UCSubSetupTask r2 = r2.setOptions(r3)     // Catch:{ Throwable -> 0x028a }
            com.uc.webview.export.internal.setup.ay r2 = (com.uc.webview.export.internal.setup.ay) r2     // Catch:{ Throwable -> 0x028a }
            java.lang.String r3 = "stat"
            com.uc.webview.export.internal.setup.UCSubSetupTask$a r6 = new com.uc.webview.export.internal.setup.UCSubSetupTask$a     // Catch:{ Throwable -> 0x028a }
            com.uc.webview.export.internal.setup.BrowserSetupTask r7 = r1.a     // Catch:{ Throwable -> 0x028a }
            r7.getClass()     // Catch:{ Throwable -> 0x028a }
            r6.<init>()     // Catch:{ Throwable -> 0x028a }
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.onEvent(r3, r6)     // Catch:{ Throwable -> 0x028a }
            com.uc.webview.export.internal.setup.ay r2 = (com.uc.webview.export.internal.setup.ay) r2     // Catch:{ Throwable -> 0x028a }
            r2.start()     // Catch:{ Throwable -> 0x028a }
        L_0x028a:
            com.uc.webview.export.internal.setup.BrowserSetupTask r2 = r1.a     // Catch:{ Throwable -> 0x02cd }
            com.uc.webview.export.internal.setup.t r2 = r2.c     // Catch:{ Throwable -> 0x02cd }
            if (r2 == 0) goto L_0x02cd
            com.uc.webview.export.internal.setup.BrowserSetupTask r2 = r1.a     // Catch:{ Throwable -> 0x02cd }
            com.uc.webview.export.internal.setup.t r2 = r2.c     // Catch:{ Throwable -> 0x02cd }
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x02cd }
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = com.uc.webview.export.internal.setup.UCSetupTask.getRoot()     // Catch:{ Throwable -> 0x02cd }
            r3[r5] = r6     // Catch:{ Throwable -> 0x02cd }
            r6 = 10001(0x2711, float:1.4014E-41)
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r6, r3)     // Catch:{ Throwable -> 0x02cd }
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2     // Catch:{ Throwable -> 0x02cd }
            r6 = 5000(0x1388, double:2.4703E-320)
            r2.start(r6)     // Catch:{ Throwable -> 0x02cd }
            com.uc.webview.export.internal.setup.BrowserSetupTask r2 = r1.a     // Catch:{ Throwable -> 0x02cd }
            r2.c = null     // Catch:{ Throwable -> 0x02cd }
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = new com.uc.webview.export.internal.setup.UCAsyncTask     // Catch:{ Throwable -> 0x02cd }
            com.uc.webview.export.cyclone.UCDex r3 = new com.uc.webview.export.cyclone.UCDex     // Catch:{ Throwable -> 0x02cd }
            r3.<init>()     // Catch:{ Throwable -> 0x02cd }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x02cd }
            java.lang.Object[] r3 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x02cd }
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = com.uc.webview.export.utility.SetupTask.getRoot()     // Catch:{ Throwable -> 0x02cd }
            r3[r5] = r6     // Catch:{ Throwable -> 0x02cd }
            r6 = 10001(0x2711, float:1.4014E-41)
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r6, r3)     // Catch:{ Throwable -> 0x02cd }
            r2.start()     // Catch:{ Throwable -> 0x02cd }
        L_0x02cd:
            r2 = 10064(0x2750, float:1.4103E-41)
            java.lang.Object[] r3 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x030f }
            java.lang.Object r2 = com.uc.webview.export.internal.SDKFactory.invoke(r2, r3)     // Catch:{ Throwable -> 0x030f }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ Throwable -> 0x030f }
            boolean r2 = r2.booleanValue()     // Catch:{ Throwable -> 0x030f }
            if (r2 == 0) goto L_0x033c
            java.lang.String r2 = "BrowserSetupTask"
            java.lang.String r3 = "CDInitTask new"
            com.uc.webview.export.internal.utility.Log.d(r2, r3)     // Catch:{ Throwable -> 0x033c }
            java.lang.String r2 = "com.uc.webview.export.cd.Utils"
            java.lang.String r3 = "createInitTaskForBrowserSetupTask"
            r6 = 2
            java.lang.Class[] r7 = new java.lang.Class[r6]     // Catch:{ Throwable -> 0x033c }
            java.lang.Class<java.lang.String> r8 = java.lang.String.class
            r7[r5] = r8     // Catch:{ Throwable -> 0x033c }
            java.lang.Class<com.uc.webview.export.internal.setup.BrowserSetupTask> r8 = com.uc.webview.export.internal.setup.BrowserSetupTask.class
            r7[r4] = r8     // Catch:{ Throwable -> 0x033c }
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x033c }
            java.lang.String r8 = "stat"
            r6[r5] = r8     // Catch:{ Throwable -> 0x033c }
            com.uc.webview.export.internal.setup.BrowserSetupTask r5 = r1.a     // Catch:{ Throwable -> 0x033c }
            r6[r4] = r5     // Catch:{ Throwable -> 0x033c }
            java.lang.Object r2 = com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r2, r3, r7, r6)     // Catch:{ Throwable -> 0x033c }
            com.uc.webview.export.internal.setup.UCSubSetupTask r2 = (com.uc.webview.export.internal.setup.UCSubSetupTask) r2     // Catch:{ Throwable -> 0x033c }
            com.uc.webview.export.internal.setup.BrowserSetupTask r3 = r1.a     // Catch:{ Throwable -> 0x033c }
            java.util.concurrent.ConcurrentHashMap r3 = r3.mOptions     // Catch:{ Throwable -> 0x033c }
            com.uc.webview.export.internal.setup.UCSubSetupTask r2 = r2.setOptions(r3)     // Catch:{ Throwable -> 0x033c }
            r2.start()     // Catch:{ Throwable -> 0x033c }
            goto L_0x033c
        L_0x030f:
            r0 = move-exception
            r2 = r0
            com.uc.webview.export.internal.setup.BrowserSetupTask r3 = r1.a
            com.uc.webview.export.internal.setup.UCSetupException r4 = new com.uc.webview.export.internal.setup.UCSetupException
            r5 = 4004(0xfa4, float:5.611E-42)
            r4.<init>(r5, r2)
            r3.setException(r4)
            goto L_0x033c
        L_0x031e:
            com.uc.webview.export.internal.setup.BrowserSetupTask r3 = r1.a
            com.uc.webview.export.internal.setup.UCSetupException r6 = new com.uc.webview.export.internal.setup.UCSetupException
            r7 = 4001(0xfa1, float:5.607E-42)
            java.lang.String r8 = "Task [%s] report success but no loaded UCM."
            java.lang.Object[] r4 = new java.lang.Object[r4]
            java.lang.Class r2 = r2.getClass()
            java.lang.String r2 = r2.getName()
            r4[r5] = r2
            java.lang.String r2 = java.lang.String.format(r8, r4)
            r6.<init>(r7, r2)
            r3.setException(r6)
        L_0x033c:
            r2 = 42
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r2)
            java.lang.String r2 = "BrowserCore.setup success"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEvent(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.a.onReceiveValue(java.lang.Object):void");
    }
}
