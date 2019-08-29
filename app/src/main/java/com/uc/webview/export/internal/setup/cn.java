package com.uc.webview.export.internal.setup;

import com.uc.webview.export.utility.download.UpdateTask;

/* compiled from: ProGuard */
public final class cn extends t {
    public UpdateTask a = null;
    /* access modifiers changed from: 0000 */
    public String b;
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public boolean d = false;
    private boolean e = false;

    /* JADX WARNING: Code restructure failed: missing block: B:132:0x02c9, code lost:
        if (((java.lang.Integer) r2.first).intValue() == 1) goto L_0x0270;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x02cb, code lost:
        r3 = ((java.lang.Integer) r2.first).intValue();
        r2 = r2.second;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:?, code lost:
        r6 = getTotalLoadedUCM();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x02df, code lost:
        if (r6 == null) goto L_0x0308;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x02e4, code lost:
        if (r6.coreType != 2) goto L_0x02ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x02e8, code lost:
        if (com.uc.webview.export.internal.SDKFactory.l != false) goto L_0x0308;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:?, code lost:
        com.uc.webview.export.internal.utility.Log.d("UpdateSetupTask", ".shareCoreWaitTimeout UCCore had initialized.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x02fa, code lost:
        if (((java.lang.Integer) r2.first).intValue() == 1) goto L_0x0270;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x02fc, code lost:
        r3 = ((java.lang.Integer) r2.first).intValue();
        r2 = r2.second;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:?, code lost:
        new java.lang.Thread(new com.uc.webview.export.internal.setup.cq(r1, r3, r5, r2)).start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x0327, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x0328, code lost:
        r3 = r0;
        r16 = r14;
     */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x0339 A[SYNTHETIC, Splitter:B:171:0x0339] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0125 A[SYNTHETIC, Splitter:B:46:0x0125] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r24 = this;
            r1 = r24
            java.lang.String r2 = "CONTEXT"
            java.lang.Object r2 = r1.getOption(r2)
            android.content.Context r2 = (android.content.Context) r2
            java.lang.String r3 = "ucmUpdUrl"
            java.lang.Object r3 = r1.getOption(r3)
            r5 = r3
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r3 = "chkMultiCore"
            java.lang.Object r3 = r1.getOption(r3)
            r11 = r3
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            java.lang.String r3 = "dlChecker"
            java.lang.Object r3 = r1.getOption(r3)
            r12 = r3
            java.util.concurrent.Callable r12 = (java.util.concurrent.Callable) r12
            java.lang.String r3 = "i"
            java.lang.String r4 = "UpdateSetupTask"
            int r3 = com.uc.webview.export.cyclone.UCLogger.createToken(r3, r4)
            com.uc.webview.export.internal.setup.UCMRunningInfo r4 = getTotalLoadedUCM()
            r13 = 2
            r14 = 0
            r15 = 1
            if (r4 == 0) goto L_0x007c
            boolean r6 = r4.isShareCore
            if (r6 == 0) goto L_0x0041
            int r4 = r4.coreType
            if (r4 != r13) goto L_0x003f
            goto L_0x0041
        L_0x003f:
            r4 = 0
            goto L_0x0042
        L_0x0041:
            r4 = 1
        L_0x0042:
            if (r4 != 0) goto L_0x0065
            java.lang.String r6 = "sc_udst"
            java.lang.String r6 = com.uc.webview.export.extension.UCCore.getParam(r6)
            java.lang.String r7 = "UpdateSetupTask"
            java.lang.String r8 = "stileUpdate : "
            java.lang.String r9 = java.lang.String.valueOf(r6)
            java.lang.String r8 = r8.concat(r9)
            com.uc.webview.export.internal.utility.Log.d(r7, r8)
            boolean r6 = com.uc.webview.export.internal.utility.j.b(r6)
            if (r6 == 0) goto L_0x0065
            java.lang.String r4 = "csc_usl"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r4)
            r4 = 1
        L_0x0065:
            java.lang.String r6 = "UpdateSetupTask"
            java.lang.String r7 = "stile update task : "
            java.lang.String r8 = java.lang.String.valueOf(r4)
            java.lang.String r7 = r7.concat(r8)
            com.uc.webview.export.internal.utility.Log.d(r6, r7)
            if (r4 != 0) goto L_0x007c
            java.lang.String r2 = "csc_usp"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r2)
            return
        L_0x007c:
            boolean r4 = com.uc.webview.export.internal.utility.j.a(r5)
            if (r4 == 0) goto L_0x0096
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 3014(0xbc6, float:4.224E-42)
            java.lang.String r4 = "Option [%s] expected."
            java.lang.Object[] r5 = new java.lang.Object[r15]
            java.lang.String r6 = "ucmUpdUrl"
            r5[r14] = r6
            java.lang.String r4 = java.lang.String.format(r4, r5)
            r2.<init>(r3, r4)
            throw r2
        L_0x0096:
            com.uc.webview.export.internal.setup.bx r10 = new com.uc.webview.export.internal.setup.bx
            r10.<init>()
            com.uc.webview.export.internal.setup.bx r9 = new com.uc.webview.export.internal.setup.bx
            r9.<init>()
            java.lang.String r4 = "updWait"
            java.lang.Object r4 = r1.getOption(r4)
            if (r4 != 0) goto L_0x00ac
            r6 = 7200000(0x6ddd00, double:3.5572727E-317)
            goto L_0x00ca
        L_0x00ac:
            boolean r6 = r4 instanceof java.lang.Long
            if (r6 == 0) goto L_0x00b7
            java.lang.Long r4 = (java.lang.Long) r4
            long r6 = r4.longValue()
            goto L_0x00ca
        L_0x00b7:
            boolean r6 = r4 instanceof java.lang.Integer
            if (r6 == 0) goto L_0x00c2
            java.lang.Integer r4 = (java.lang.Integer) r4
            long r6 = r4.longValue()
            goto L_0x00ca
        L_0x00c2:
            java.lang.String r4 = java.lang.String.valueOf(r4)
            long r6 = java.lang.Long.parseLong(r4)
        L_0x00ca:
            java.lang.Long r8 = java.lang.Long.valueOf(r6)
            long r6 = r8.longValue()
            r13 = 600000(0x927c0, double:2.964394E-318)
            long r6 = java.lang.Math.min(r6, r13)
            java.lang.String r4 = "sc_ustwm"
            java.lang.String r4 = com.uc.webview.export.extension.UCCore.getParam(r4)     // Catch:{ Exception -> 0x00fe }
            boolean r13 = com.uc.webview.export.internal.utility.j.a(r4)     // Catch:{ Exception -> 0x00fe }
            if (r13 != 0) goto L_0x00fa
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x00fe }
            long r13 = r4.longValue()     // Catch:{ Exception -> 0x00fe }
            r17 = r6
            long r6 = r8.longValue()     // Catch:{ Exception -> 0x00f8 }
            long r6 = java.lang.Math.min(r13, r6)     // Catch:{ Exception -> 0x00f8 }
            goto L_0x00fc
        L_0x00f8:
            r0 = move-exception
            goto L_0x0101
        L_0x00fa:
            r17 = r6
        L_0x00fc:
            r13 = r6
            goto L_0x010b
        L_0x00fe:
            r0 = move-exception
            r17 = r6
        L_0x0101:
            r4 = r0
            java.lang.String r6 = "UpdateSetupTask"
            java.lang.String r7 = "Long.valueOf(String) exceptin."
            com.uc.webview.export.internal.utility.Log.d(r6, r7, r4)
            r13 = r17
        L_0x010b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r6 = "run:update from ["
            r4.<init>(r6)
            r4.append(r5)
            java.lang.String r6 = "]"
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            r6 = 0
            java.lang.Throwable[] r7 = new java.lang.Throwable[r6]
            com.uc.webview.export.cyclone.UCLogger.print(r3, r4, r7)
            monitor-enter(r10)
            java.lang.Object[] r3 = new java.lang.Object[r15]     // Catch:{ all -> 0x04bb }
            r3[r6] = r2     // Catch:{ all -> 0x04bb }
            r7 = 10002(0x2712, float:1.4016E-41)
            java.lang.Object r3 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r7, r3)     // Catch:{ all -> 0x04bb }
            java.io.File r3 = (java.io.File) r3     // Catch:{ all -> 0x04bb }
            java.lang.String r4 = "dwnRetryWait"
            java.lang.Object r4 = r1.getOption(r4)     // Catch:{ all -> 0x04bb }
            if (r4 != 0) goto L_0x013e
            r19 = r8
            r17 = 0
            goto L_0x016c
        L_0x013e:
            boolean r6 = r4 instanceof java.lang.Long     // Catch:{ all -> 0x04bb }
            if (r6 == 0) goto L_0x0152
            java.lang.Long r4 = (java.lang.Long) r4     // Catch:{ all -> 0x014d }
            long r17 = r4.longValue()     // Catch:{ all -> 0x014d }
        L_0x0148:
            r19 = r8
            r7 = r17
            goto L_0x0166
        L_0x014d:
            r0 = move-exception
            r2 = r0
            r5 = r10
            goto L_0x04be
        L_0x0152:
            boolean r6 = r4 instanceof java.lang.Integer     // Catch:{ all -> 0x04bb }
            if (r6 == 0) goto L_0x015d
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ all -> 0x014d }
            long r17 = r4.longValue()     // Catch:{ all -> 0x014d }
            goto L_0x0148
        L_0x015d:
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x04bb }
            long r17 = java.lang.Long.parseLong(r4)     // Catch:{ all -> 0x04bb }
            goto L_0x0148
        L_0x0166:
            java.lang.Long r4 = java.lang.Long.valueOf(r7)     // Catch:{ all -> 0x04bb }
            r17 = r4
        L_0x016c:
            java.lang.String r4 = "dwnRetryMaxWait"
            java.lang.Object r4 = r1.getOption(r4)     // Catch:{ all -> 0x04bb }
            if (r4 != 0) goto L_0x0177
            r18 = 0
            goto L_0x019b
        L_0x0177:
            boolean r6 = r4 instanceof java.lang.Long     // Catch:{ all -> 0x04bb }
            if (r6 == 0) goto L_0x0182
            java.lang.Long r4 = (java.lang.Long) r4     // Catch:{ all -> 0x014d }
            long r6 = r4.longValue()     // Catch:{ all -> 0x014d }
            goto L_0x0195
        L_0x0182:
            boolean r6 = r4 instanceof java.lang.Integer     // Catch:{ all -> 0x04bb }
            if (r6 == 0) goto L_0x018d
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ all -> 0x014d }
            long r6 = r4.longValue()     // Catch:{ all -> 0x014d }
            goto L_0x0195
        L_0x018d:
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x04bb }
            long r6 = java.lang.Long.parseLong(r4)     // Catch:{ all -> 0x04bb }
        L_0x0195:
            java.lang.Long r4 = java.lang.Long.valueOf(r6)     // Catch:{ all -> 0x04bb }
            r18 = r4
        L_0x019b:
            com.uc.webview.export.utility.download.UpdateTask r8 = new com.uc.webview.export.utility.download.UpdateTask     // Catch:{ all -> 0x04bb }
            java.lang.String r6 = r3.getAbsolutePath()     // Catch:{ all -> 0x04bb }
            java.lang.String r7 = "core.jar"
            com.uc.webview.export.internal.utility.i$b r4 = new com.uc.webview.export.internal.utility.i$b     // Catch:{ all -> 0x04bb }
            java.lang.String r3 = "ut_cvsv"
            r4.<init>(r3)     // Catch:{ all -> 0x04bb }
            r3 = r8
            r20 = r4
            r4 = r2
            r15 = r8
            r21 = r19
            r8 = r20
            r22 = r11
            r11 = r9
            r9 = r17
            r23 = r10
            r10 = r18
            r3.<init>(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x04b7 }
            r1.a = r15     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r3 = r1.a     // Catch:{ all -> 0x04b7 }
            java.lang.String r4 = "check"
            com.uc.webview.export.internal.setup.cp r5 = new com.uc.webview.export.internal.setup.cp     // Catch:{ all -> 0x04b7 }
            r5.<init>(r1, r2, r12)     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r3.onEvent(r4, r5)     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "exception"
            com.uc.webview.export.internal.setup.cy r4 = new com.uc.webview.export.internal.setup.cy     // Catch:{ all -> 0x04b7 }
            r4.<init>(r1)     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r2.onEvent(r3, r4)     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "downloadException"
            com.uc.webview.export.internal.setup.cx r4 = new com.uc.webview.export.internal.setup.cx     // Catch:{ all -> 0x04b7 }
            r4.<init>(r1)     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r2.onEvent(r3, r4)     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "success"
            com.uc.webview.export.internal.setup.cw r4 = new com.uc.webview.export.internal.setup.cw     // Catch:{ all -> 0x04b7 }
            r4.<init>(r1, r11)     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r2.onEvent(r3, r4)     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "failed"
            com.uc.webview.export.internal.setup.cv r4 = new com.uc.webview.export.internal.setup.cv     // Catch:{ all -> 0x04b7 }
            r4.<init>(r1, r11)     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r2.onEvent(r3, r4)     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "exists"
            com.uc.webview.export.internal.setup.cu r4 = new com.uc.webview.export.internal.setup.cu     // Catch:{ all -> 0x04b7 }
            r4.<init>(r1, r11)     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r2.onEvent(r3, r4)     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "deleteDownFile"
            com.uc.webview.export.internal.setup.ct r4 = new com.uc.webview.export.internal.setup.ct     // Catch:{ all -> 0x04b7 }
            r4.<init>(r1)     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r2.onEvent(r3, r4)     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "progress"
            com.uc.webview.export.internal.setup.cs r4 = new com.uc.webview.export.internal.setup.cs     // Catch:{ all -> 0x04b7 }
            r4.<init>(r1)     // Catch:{ all -> 0x04b7 }
            com.uc.webview.export.utility.download.UpdateTask r2 = r2.onEvent(r3, r4)     // Catch:{ all -> 0x04b7 }
            r2.start()     // Catch:{ all -> 0x04b7 }
            android.util.Pair r2 = r11.a(r13)     // Catch:{ all -> 0x04b7 }
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04b7 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04b7 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04b7 }
            r4 = 4
            if (r3 == 0) goto L_0x0352
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04b7 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04b7 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04b7 }
            if (r3 == r4) goto L_0x0352
            java.lang.String r3 = "UpdateSetupTask"
            java.lang.String r5 = ".shareCoreWaitTimeout"
            com.uc.webview.export.internal.utility.Log.d(r3, r5)     // Catch:{ all -> 0x04b7 }
            java.lang.String r3 = "sc_ldpl"
            java.lang.String r3 = com.uc.webview.export.extension.UCCore.getParam(r3)     // Catch:{ all -> 0x0331 }
            java.lang.String r5 = "sc_lshco"
            boolean r3 = r5.equals(r3)     // Catch:{ all -> 0x0331 }
            if (r3 != 0) goto L_0x027c
            java.lang.String r3 = "UpdateSetupTask"
            java.lang.String r5 = ".shareCoreWaitTimeout !CDKeys.CD_VALUE_LOAD_POLICY_SHARE_CORE.equals(shareCoreLoadPolicy)."
            com.uc.webview.export.internal.utility.Log.d(r3, r5)     // Catch:{ all -> 0x0274 }
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04b7 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04b7 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04b7 }
            r5 = 1
            if (r3 == r5) goto L_0x026e
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04b7 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04b7 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04b7 }
            java.lang.Object r2 = r2.second     // Catch:{ all -> 0x04b7 }
            r5 = r23
        L_0x026a:
            r5.a(r3, r2)     // Catch:{ all -> 0x04c0 }
            goto L_0x0270
        L_0x026e:
            r5 = r23
        L_0x0270:
            r3 = r21
            goto L_0x0317
        L_0x0274:
            r0 = move-exception
            r5 = r23
        L_0x0277:
            r3 = r0
            r16 = 1
            goto L_0x0337
        L_0x027c:
            r5 = r23
            r3 = 10007(0x2717, float:1.4023E-41)
            r6 = 1
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch:{ all -> 0x032f }
            java.lang.String r6 = "shareCoreEvt"
            r8 = 0
            r7[r8] = r6     // Catch:{ all -> 0x032f }
            java.lang.Object r3 = r1.invokeO(r3, r7)     // Catch:{ all -> 0x032f }
            android.webkit.ValueCallback r3 = (android.webkit.ValueCallback) r3     // Catch:{ all -> 0x032f }
            if (r3 != 0) goto L_0x02af
            java.lang.String r3 = "UpdateSetupTask"
            java.lang.String r6 = ".shareCoreWaitTimeout dlShareCoreCB == null."
            com.uc.webview.export.internal.utility.Log.d(r3, r6)     // Catch:{ all -> 0x02ad }
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04c0 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04c0 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04c0 }
            r6 = 1
            if (r3 == r6) goto L_0x0270
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04c0 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04c0 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04c0 }
            java.lang.Object r2 = r2.second     // Catch:{ all -> 0x04c0 }
            goto L_0x026a
        L_0x02ad:
            r0 = move-exception
            goto L_0x0277
        L_0x02af:
            monitor-enter(r24)     // Catch:{ all -> 0x032f }
            boolean r6 = r1.c     // Catch:{ all -> 0x0322 }
            if (r6 != 0) goto L_0x02da
            boolean r6 = r1.d     // Catch:{ all -> 0x0322 }
            if (r6 != 0) goto L_0x02da
            java.lang.String r3 = "UpdateSetupTask"
            java.lang.String r6 = ".shareCoreWaitTimeout !mHasExcepted && !mHasFailed"
            com.uc.webview.export.internal.utility.Log.d(r3, r6)     // Catch:{ all -> 0x02d6 }
            monitor-exit(r24)     // Catch:{ all -> 0x02d6 }
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04c0 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04c0 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04c0 }
            r6 = 1
            if (r3 == r6) goto L_0x0270
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04c0 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04c0 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04c0 }
            java.lang.Object r2 = r2.second     // Catch:{ all -> 0x04c0 }
            goto L_0x026a
        L_0x02d6:
            r0 = move-exception
            r3 = r0
            r14 = 1
            goto L_0x0325
        L_0x02da:
            monitor-exit(r24)     // Catch:{ all -> 0x0322 }
            com.uc.webview.export.internal.setup.UCMRunningInfo r6 = getTotalLoadedUCM()     // Catch:{ all -> 0x032f }
            if (r6 == 0) goto L_0x0308
            int r6 = r6.coreType     // Catch:{ all -> 0x032f }
            r7 = 2
            if (r6 != r7) goto L_0x02ea
            boolean r6 = com.uc.webview.export.internal.SDKFactory.l     // Catch:{ all -> 0x032f }
            if (r6 != 0) goto L_0x0308
        L_0x02ea:
            java.lang.String r3 = "UpdateSetupTask"
            java.lang.String r6 = ".shareCoreWaitTimeout UCCore had initialized."
            com.uc.webview.export.internal.utility.Log.d(r3, r6)     // Catch:{ all -> 0x02ad }
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04c0 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04c0 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04c0 }
            r6 = 1
            if (r3 == r6) goto L_0x0270
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x04c0 }
            java.lang.Integer r3 = (java.lang.Integer) r3     // Catch:{ all -> 0x04c0 }
            int r3 = r3.intValue()     // Catch:{ all -> 0x04c0 }
            java.lang.Object r2 = r2.second     // Catch:{ all -> 0x04c0 }
            goto L_0x026a
        L_0x0308:
            java.lang.Thread r6 = new java.lang.Thread     // Catch:{ all -> 0x032f }
            com.uc.webview.export.internal.setup.cq r7 = new com.uc.webview.export.internal.setup.cq     // Catch:{ all -> 0x032f }
            r7.<init>(r1, r3, r5, r2)     // Catch:{ all -> 0x032f }
            r6.<init>(r7)     // Catch:{ all -> 0x032f }
            r6.start()     // Catch:{ all -> 0x032f }
            goto L_0x0270
        L_0x0317:
            long r6 = r3.longValue()     // Catch:{ all -> 0x04c0 }
            r2 = 0
            long r6 = r6 - r13
            android.util.Pair r2 = r5.a(r6)     // Catch:{ all -> 0x04c0 }
            goto L_0x0356
        L_0x0322:
            r0 = move-exception
            r3 = r0
            r14 = 0
        L_0x0325:
            monitor-exit(r24)     // Catch:{ all -> 0x032c }
            throw r3     // Catch:{ all -> 0x0327 }
        L_0x0327:
            r0 = move-exception
            r3 = r0
            r16 = r14
            goto L_0x0337
        L_0x032c:
            r0 = move-exception
            r3 = r0
            goto L_0x0325
        L_0x032f:
            r0 = move-exception
            goto L_0x0334
        L_0x0331:
            r0 = move-exception
            r5 = r23
        L_0x0334:
            r3 = r0
            r16 = 0
        L_0x0337:
            if (r16 == 0) goto L_0x0351
            java.lang.Object r4 = r2.first     // Catch:{ all -> 0x04c0 }
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ all -> 0x04c0 }
            int r4 = r4.intValue()     // Catch:{ all -> 0x04c0 }
            r6 = 1
            if (r4 == r6) goto L_0x0351
            java.lang.Object r4 = r2.first     // Catch:{ all -> 0x04c0 }
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch:{ all -> 0x04c0 }
            int r4 = r4.intValue()     // Catch:{ all -> 0x04c0 }
            java.lang.Object r2 = r2.second     // Catch:{ all -> 0x04c0 }
            r5.a(r4, r2)     // Catch:{ all -> 0x04c0 }
        L_0x0351:
            throw r3     // Catch:{ all -> 0x04c0 }
        L_0x0352:
            r3 = r21
            r5 = r23
        L_0x0356:
            monitor-exit(r5)     // Catch:{ all -> 0x04c0 }
            java.lang.String r5 = "UpdateSetupTask"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "retult.first: "
            r6.<init>(r7)
            java.lang.Object r7 = r2.first
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            com.uc.webview.export.internal.utility.Log.d(r5, r6)
            java.lang.Object r5 = r2.first
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            r6 = 1
            if (r5 != r6) goto L_0x0399
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r4 = 4010(0xfaa, float:5.619E-42)
            java.lang.String r5 = "Thread [%s] waiting for update is up to [%s] milis."
            r7 = 2
            java.lang.Object[] r7 = new java.lang.Object[r7]
            java.lang.Thread r8 = java.lang.Thread.currentThread()
            java.lang.String r8 = r8.getName()
            r9 = 0
            r7[r9] = r8
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r7[r6] = r3
            java.lang.String r3 = java.lang.String.format(r5, r7)
            r2.<init>(r4, r3)
            throw r2
        L_0x0399:
            java.lang.Object r3 = r2.first
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            r5 = 3
            if (r3 != r5) goto L_0x03b0
            com.uc.webview.export.internal.setup.UCSetupException r3 = new com.uc.webview.export.internal.setup.UCSetupException
            r4 = 4019(0xfb3, float:5.632E-42)
            java.lang.Object r2 = r2.second
            java.lang.Exception r2 = (java.lang.Exception) r2
            r3.<init>(r4, r2)
            throw r3
        L_0x03b0:
            java.lang.Object r3 = r2.first
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            r5 = 8
            if (r3 != r5) goto L_0x03d8
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 4030(0xfbe, float:5.647E-42)
            java.lang.String r4 = "Thread [%s] waiting timeout for share core task."
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.Thread r6 = java.lang.Thread.currentThread()
            java.lang.String r6 = r6.getName()
            r7 = 0
            r5[r7] = r6
            java.lang.String r4 = java.lang.String.format(r4, r5)
            r2.<init>(r3, r4)
            throw r2
        L_0x03d8:
            java.lang.Object r3 = r2.first
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            if (r3 != 0) goto L_0x03e4
            r3 = 1
            goto L_0x03e5
        L_0x03e4:
            r3 = 0
        L_0x03e5:
            if (r3 != 0) goto L_0x03f4
            java.lang.Object r2 = r2.first
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            if (r2 != r4) goto L_0x03f2
            goto L_0x03f4
        L_0x03f2:
            r2 = 0
            goto L_0x03f5
        L_0x03f4:
            r2 = 1
        L_0x03f5:
            com.uc.webview.export.utility.download.UpdateTask r4 = r1.a
            java.io.File r4 = r4.getUpdateDir()
            if (r2 == 0) goto L_0x04af
            if (r3 != 0) goto L_0x0405
            com.uc.webview.export.internal.setup.UCMRunningInfo r2 = com.uc.webview.export.utility.SetupTask.getTotalLoadedUCM()
            if (r2 != 0) goto L_0x04af
        L_0x0405:
            java.lang.String r2 = "UpdateSetupTask"
            java.lang.String r3 = "new ThinSetupTask."
            com.uc.webview.export.internal.utility.Log.d(r2, r3)
            com.uc.webview.export.internal.setup.bw r2 = new com.uc.webview.export.internal.setup.bw
            r2.<init>()
            r3 = 10001(0x2711, float:1.4014E-41)
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]
            r7 = 0
            r6[r7] = r1
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r3, r6)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.util.concurrent.ConcurrentHashMap r3 = r1.mOptions
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setOptions(r3)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.Object[] r3 = new java.lang.Object[r5]
            java.util.concurrent.ConcurrentHashMap r5 = r1.mCallbacks
            r3[r7] = r5
            r5 = 10002(0x2712, float:1.4016E-41)
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r5, r3)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "dexFilePath"
            r5 = 0
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r3, r5)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "soFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r3, r5)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "resFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r3, r5)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "ucmCfgFile"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r3, r5)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "ucmLibDir"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r3, r5)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "ucmZipDir"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r3, r5)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "ucmZipFile"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r3, r5)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "ucmKrlDir"
            java.lang.String r4 = r4.getAbsolutePath()
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r3, r4)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "switch"
            com.uc.webview.export.internal.setup.cr r4 = new com.uc.webview.export.internal.setup.cr
            r4.<init>(r1)
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.onEvent(r3, r4)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "stop"
            com.uc.webview.export.internal.setup.UCAsyncTask$c r4 = new com.uc.webview.export.internal.setup.UCAsyncTask$c
            r4.<init>()
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.onEvent(r3, r4)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            java.lang.String r3 = "setup"
            r4 = r22
            boolean r4 = com.uc.webview.export.internal.utility.j.b(r4)
            if (r4 == 0) goto L_0x049f
            goto L_0x04a5
        L_0x049f:
            com.uc.webview.export.internal.setup.co r6 = new com.uc.webview.export.internal.setup.co
            r6.<init>(r1)
            r5 = r6
        L_0x04a5:
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.onEvent(r3, r5)
            com.uc.webview.export.internal.setup.t r2 = (com.uc.webview.export.internal.setup.t) r2
            r2.start()
            return
        L_0x04af:
            java.lang.String r2 = "UpdateSetupTask"
            java.lang.String r3 = "else, need not new ThinSetupTask."
            com.uc.webview.export.internal.utility.Log.d(r2, r3)
            return
        L_0x04b7:
            r0 = move-exception
            r5 = r23
            goto L_0x04bd
        L_0x04bb:
            r0 = move-exception
            r5 = r10
        L_0x04bd:
            r2 = r0
        L_0x04be:
            monitor-exit(r5)     // Catch:{ all -> 0x04c0 }
            throw r2
        L_0x04c0:
            r0 = move-exception
            goto L_0x04bd
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.cn.run():void");
    }
}
