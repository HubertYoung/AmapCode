package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.util.Pair;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.uc.startup.StartupStats;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

/* compiled from: ProGuard */
public class bv extends q {
    private static final String d = "bv";

    public void run() {
        List list;
        UCMPackageInfo uCMPackageInfo;
        String str;
        Iterator it;
        String str2;
        StartupTrace.a();
        StartupStats.a(7);
        Integer num = (Integer) this.mOptions.get(UCCore.OPTION_VERIFY_POLICY);
        if (num != null) {
            StartupStats.a(16, String.valueOf(num));
        }
        Context context = (Context) this.mOptions.get(UCCore.OPTION_CONTEXT);
        boolean z = SDKFactory.s;
        UCSetupException uCSetupException = null;
        UCElapseTime uCElapseTime = new UCElapseTime();
        StartupTrace.a();
        if (bd.a(invokeO(10005, new Object[0]))) {
            list = UCMPackageInfo.listFromSharedApps(context, this.mOptions);
        } else {
            list = UCMPackageInfo.a(context, this.mOptions);
        }
        StartupTrace.traceEventEnd("ThinEnvTask.run.isLocationShareCoreTask");
        StringBuilder sb = new StringBuilder("UCMPackageInfo listUCMS:");
        sb.append(String.valueOf(uCElapseTime.getMilis()));
        Log.e("ThinEnvTask", sb.toString());
        if (list.size() > 0) {
            boolean z2 = !j.b((Boolean) this.mOptions.get("chkDecFinish"));
            boolean z3 = !j.b((Boolean) this.mOptions.get(UCCore.OPTION_USE_SDK_SETUP));
            String str3 = (String) this.mOptions.get("core_ver_excludes");
            String str4 = (String) this.mOptions.get("sdk_ver_excludes");
            String param = UCCore.getParam("core_ver_excludes");
            String param2 = UCCore.getParam("sdk_ver_excludes");
            ClassLoader classLoader = bv.class.getClassLoader();
            Iterator it2 = list.iterator();
            UCSetupException uCSetupException2 = null;
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                uCMPackageInfo = (UCMPackageInfo) it2.next();
                try {
                    if (!j.a(param2) || !j.a(param)) {
                        Iterator it3 = it2;
                        a(context, uCMPackageInfo, z2, z3, z, num, param, param2, CDParamKeys.CD_VALUE_STRING_SPLITER, classLoader);
                    } else {
                        it = it2;
                        str = param2;
                        r10 = ",";
                        str2 = param;
                        try {
                            a(context, uCMPackageInfo, z2, z3, z, num, str3, str4, ",", classLoader);
                            break;
                        } catch (UCSetupException e) {
                            e = e;
                            uCSetupException2 = e;
                            param = str2;
                            it2 = it;
                            param2 = str;
                        } catch (Throwable th) {
                            th = th;
                            uCSetupException2 = new UCSetupException((int) amb.GL_MARKER_LINE_ARROW_DOT, th);
                            param = str2;
                            it2 = it;
                            param2 = str;
                        }
                    }
                } catch (UCSetupException e2) {
                    e = e2;
                    it = it2;
                    str = param2;
                    str2 = param;
                    uCSetupException2 = e;
                    param = str2;
                    it2 = it;
                    param2 = str;
                } catch (Throwable th2) {
                    th = th2;
                    it = it2;
                    str = param2;
                    str2 = param;
                    uCSetupException2 = new UCSetupException((int) amb.GL_MARKER_LINE_ARROW_DOT, th);
                    param = str2;
                    it2 = it;
                    param2 = str;
                }
                param = str2;
                it2 = it;
                param2 = str;
            }
            Iterator it32 = it2;
            a(context, uCMPackageInfo, z2, z3, z, num, param, param2, CDParamKeys.CD_VALUE_STRING_SPLITER, classLoader);
            uCSetupException = uCSetupException2;
        }
        if (this.mCL == null || this.mUCM == null) {
            if (uCSetupException == null) {
                uCSetupException = new UCSetupException(3004, (String) "UCM packages not found.");
            }
            throw uCSetupException;
        }
        StartupStats.a(8);
        StartupTrace.traceEventEnd("ThinEnvTask.run");
    }

    public static boolean a(Context context, String str, String str2) {
        File file = (File) UCMPackageInfo.invoke(10003, context);
        if (str.startsWith(file.getAbsolutePath())) {
            File file2 = new File(str2);
            if (!str.startsWith(new File(new File(file, UCCyclone.getSourceHash(file2.getAbsolutePath())), UCCyclone.getSourceHash(file2.length(), file2.lastModified())).getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }

    private boolean a(Context context, UCMPackageInfo uCMPackageInfo) {
        String str = (String) getOption(UCCore.OPTION_UCM_ZIP_FILE);
        if (!j.a(uCMPackageInfo.dataDir)) {
            if (!j.a(str)) {
                return a(context, uCMPackageInfo.dataDir, str);
            }
            String str2 = (String) getOption(UCCore.OPTION_UCM_UPD_URL);
            if (!j.a(str2)) {
                File file = (File) UCMPackageInfo.invoke(10002, context);
                if (uCMPackageInfo.dataDir.startsWith(file.getAbsolutePath())) {
                    File file2 = new File(file, UCCyclone.getSourceHash(str2));
                    if (!uCMPackageInfo.dataDir.startsWith(file2.getAbsolutePath())) {
                        return true;
                    }
                    if (!SDKFactory.m) {
                        return false;
                    }
                    try {
                        if (!((Boolean) ((Callable) getOption(UCCore.OPTION_DOWNLOAD_CHECKER)).call()).booleanValue()) {
                            return false;
                        }
                        Pair<Long, Long> a = j.a(str2, (URL) null);
                        if (!uCMPackageInfo.dataDir.startsWith(new File(file2, UCCyclone.getSourceHash(((Long) a.first).longValue(), ((Long) a.second).longValue())).getAbsolutePath())) {
                            return true;
                        }
                    } catch (Throwable unused) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x0336  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x0330  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.content.Context r72, com.uc.webview.export.internal.setup.UCMPackageInfo r73, boolean r74, boolean r75, boolean r76, java.lang.Integer r77, java.lang.String r78, java.lang.String r79, java.lang.String r80, java.lang.ClassLoader r81) {
        /*
            r71 = this;
            r10 = r71
            r1 = r72
            r2 = r73
            r11 = r77
            r3 = r80
            r4 = r81
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            r5 = 9
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r5)
            r10.mUCM = r2
            com.uc.webview.export.cyclone.UCElapseTime r5 = new com.uc.webview.export.cyclone.UCElapseTime
            r5.<init>()
            r12 = 0
            r6 = 1
            if (r74 == 0) goto L_0x0040
            java.io.File r7 = new java.io.File
            java.lang.String r8 = r2.dataDir
            r7.<init>(r8)
            boolean r7 = com.uc.webview.export.cyclone.UCCyclone.isDecompressFinished(r7)
            if (r7 != 0) goto L_0x0040
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 2007(0x7d7, float:2.812E-42)
            java.lang.String r4 = "Package [%s] decompress not finished."
            java.lang.Object[] r5 = new java.lang.Object[r6]
            java.lang.String r2 = r2.dataDir
            r5[r12] = r2
            java.lang.String r2 = java.lang.String.format(r4, r5)
            r1.<init>(r3, r2)
            throw r1
        L_0x0040:
            long r13 = r5.getMilis()
            com.uc.webview.export.cyclone.UCElapseTime r5 = new com.uc.webview.export.cyclone.UCElapseTime
            r5.<init>()
            java.lang.String r7 = "load"
            java.lang.Object r7 = r10.getOption(r7)
            java.lang.String r7 = (java.lang.String) r7
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            r9 = 2
            if (r8 != 0) goto L_0x009b
            java.lang.String r8 = r2.coreCode
            boolean r8 = r8.equals(r7)
            if (r8 != 0) goto L_0x009b
            java.lang.String r1 = "u3"
            boolean r1 = r1.equals(r7)
            if (r1 != 0) goto L_0x0085
            java.lang.String r1 = "u4"
            boolean r1 = r1.equals(r7)
            if (r1 == 0) goto L_0x0073
            goto L_0x0085
        L_0x0073:
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r2 = 3008(0xbc0, float:4.215E-42)
            java.lang.String r3 = "UCM param load value [%s] unknown."
            java.lang.Object[] r4 = new java.lang.Object[r6]
            r4[r12] = r7
            java.lang.String r3 = java.lang.String.format(r3, r4)
            r1.<init>(r2, r3)
            throw r1
        L_0x0085:
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 4002(0xfa2, float:5.608E-42)
            java.lang.String r4 = "UCM with core code [%s] is excluded by param load [%s]."
            java.lang.Object[] r5 = new java.lang.Object[r9]
            java.lang.String r2 = r2.coreCode
            r5[r12] = r2
            r5[r6] = r7
            java.lang.String r2 = java.lang.String.format(r4, r5)
            r1.<init>(r3, r2)
            throw r1
        L_0x009b:
            long r7 = r5.getMilis()
            boolean r5 = r71.a(r72, r73)
            android.util.Pair<java.lang.String, java.lang.String> r9 = r2.coreImplModule
            java.lang.Object r9 = r9.first
            java.lang.String r9 = (java.lang.String) r9
            android.util.Pair<java.lang.String, java.lang.String> r12 = r2.coreImplModule
            java.lang.Object r12 = r12.second
            java.lang.String r12 = (java.lang.String) r12
            java.io.File r9 = com.uc.webview.export.cyclone.UCCyclone.optimizedFileFor(r9, r12)
            boolean r9 = r9.exists()
            r9 = r9 ^ r6
            r12 = 18
            if (r9 == 0) goto L_0x00c1
            java.lang.String r17 = "1"
        L_0x00be:
            r6 = r17
            goto L_0x00c4
        L_0x00c1:
            java.lang.String r17 = "0"
            goto L_0x00be
        L_0x00c4:
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r12, r6)
            com.uc.webview.export.cyclone.UCElapseTime r6 = new com.uc.webview.export.cyclone.UCElapseTime
            r6.<init>()
            if (r5 == 0) goto L_0x0104
            r12 = r75 ^ 1
            r18 = r7
            java.util.concurrent.ConcurrentHashMap r7 = r10.mOptions
            java.lang.String r8 = "skip_old_extra_kernel"
            java.lang.Boolean r7 = com.uc.webview.export.internal.utility.j.a(r7, r8)
            if (r7 == 0) goto L_0x00e0
            boolean r12 = r7.booleanValue()
        L_0x00e0:
            if (r12 == 0) goto L_0x0106
            int r1 = r10.c
            java.lang.String r3 = "checkParamSkipOldKernel:true"
            r4 = 0
            java.lang.Throwable[] r5 = new java.lang.Throwable[r4]
            com.uc.webview.export.cyclone.UCLogger.print(r1, r3, r5)
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 4006(0xfa6, float:5.614E-42)
            java.lang.String r5 = "UCM [%s] is excluded by param skip_old_extra_kernel value [%s]."
            r6 = 2
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.lang.String r2 = r2.dataDir
            r6[r4] = r2
            r2 = 1
            r6[r2] = r7
            java.lang.String r2 = java.lang.String.format(r5, r6)
            r1.<init>(r3, r2)
            throw r1
        L_0x0104:
            r18 = r7
        L_0x0106:
            long r6 = r6.getMilis()
            com.uc.webview.export.internal.setup.UCMPackageInfo r8 = com.uc.webview.export.utility.SetupTask.sFirstUCM
            if (r8 != 0) goto L_0x0110
            com.uc.webview.export.utility.SetupTask.sFirstUCM = r2
        L_0x0110:
            com.uc.webview.export.cyclone.UCElapseTime r8 = new com.uc.webview.export.cyclone.UCElapseTime
            r8.<init>()
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            r20 = 0
            if (r11 == 0) goto L_0x013f
            int r12 = r77.intValue()
            r17 = 1
            r12 = r12 & 1
            if (r12 == 0) goto L_0x013f
            android.util.Pair<java.lang.String, java.lang.String> r8 = r2.sdkShellModule
            java.lang.Object r8 = r8.first
            java.lang.String r8 = (java.lang.String) r8
            com.uc.webview.export.cyclone.UCElapseTime r8 = com.uc.webview.export.internal.setup.da.a(r1, r11, r8)
            long r22 = r8.getMilisCpu()
            long r22 = r22 + r20
            long r24 = r8.getMilis()
            long r24 = r24 + r20
            r26 = r13
            goto L_0x0145
        L_0x013f:
            r26 = r13
            r22 = r20
            r24 = r22
        L_0x0145:
            long r12 = r8.getMilis()
            r8 = 32
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r8, r12)
            java.lang.String r14 = "ThinEnvTask.tryEnv check sdk_shell.jar signature"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r14)
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            r14 = 23
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r14)
            com.uc.webview.export.cyclone.UCElapseTime r14 = new com.uc.webview.export.cyclone.UCElapseTime
            r14.<init>()
            com.uc.webview.export.cyclone.UCLoader r8 = new com.uc.webview.export.cyclone.UCLoader
            r28 = r6
            android.util.Pair<java.lang.String, java.lang.String> r6 = r2.sdkShellModule
            java.lang.Object r6 = r6.first
            java.lang.String r6 = (java.lang.String) r6
            android.util.Pair<java.lang.String, java.lang.String> r7 = r2.sdkShellModule
            java.lang.Object r7 = r7.second
            java.lang.String r7 = (java.lang.String) r7
            r30 = r12
            java.lang.String r12 = r2.soDirPath
            r8.<init>(r6, r7, r12, r4)
            long r12 = r14.getMilis()
            r6 = 24
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r6)
            java.lang.String r6 = "ThinEnvTask.tryEnv load sdk_shell.jar"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r6)
            long r6 = r14.getMilisCpu()
            long r6 = r6 + r20
            long r32 = r14.getMilis()
            long r32 = r32 + r20
            r2.mSdkShellClassLoader = r8
            r10.mShellCL = r8
            if (r75 == 0) goto L_0x0355
            com.uc.webview.export.cyclone.UCElapseTime r14 = new com.uc.webview.export.cyclone.UCElapseTime
            r14.<init>()
            if (r76 != 0) goto L_0x01f6
            r34 = r12
            java.lang.String r12 = "com.uc.webview.browser.shell.Build"
            r13 = 0
            java.lang.Class r12 = java.lang.Class.forName(r12, r13, r8)     // Catch:{ Exception -> 0x01ec }
            java.lang.String r13 = "TYPE"
            java.lang.reflect.Field r12 = r12.getField(r13)     // Catch:{ Exception -> 0x01ec }
            r13 = 0
            java.lang.Object r12 = r12.get(r13)     // Catch:{ Exception -> 0x01ec }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ Exception -> 0x01ec }
            boolean r13 = r2.isSpecified     // Catch:{ Exception -> 0x01ec }
            if (r13 != 0) goto L_0x01e9
            java.lang.String r13 = r12.toLowerCase()     // Catch:{ Exception -> 0x01ec }
            r36 = r9
            java.lang.String r9 = "ucrelease"
            boolean r9 = r13.startsWith(r9)     // Catch:{ Exception -> 0x01ec }
            if (r9 != 0) goto L_0x01fa
            java.lang.String r9 = r12.toLowerCase()     // Catch:{ Exception -> 0x01ec }
            java.lang.String r13 = "ucpatch"
            boolean r9 = r9.startsWith(r13)     // Catch:{ Exception -> 0x01ec }
            if (r9 != 0) goto L_0x01fa
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ Exception -> 0x01ec }
            r2 = 4011(0xfab, float:5.62E-42)
            java.lang.String r3 = "ucrelease or ucpatch is expected but get [%s] to shared UCM."
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x01ec }
            r5 = 0
            r4[r5] = r12     // Catch:{ Exception -> 0x01ec }
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ Exception -> 0x01ec }
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x01ec }
            throw r1     // Catch:{ Exception -> 0x01ec }
        L_0x01e9:
            r36 = r9
            goto L_0x01fa
        L_0x01ec:
            r0 = move-exception
            r1 = r0
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 4012(0xfac, float:5.622E-42)
            r2.<init>(r3, r1)
            throw r2
        L_0x01f6:
            r36 = r9
            r34 = r12
        L_0x01fa:
            long r12 = r14.getMilis()
            r9 = 28
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r9, r12)
            com.uc.webview.export.cyclone.UCElapseTime r9 = new com.uc.webview.export.cyclone.UCElapseTime
            r9.<init>()
            java.lang.String r14 = "com.uc.webview.browser.shell.Build"
            r37 = r12
            java.lang.String r12 = "CORE_VERSION"
            r13 = r78
            a(r13, r8, r14, r12, r3)
            java.lang.String r12 = "com.uc.webview.browser.shell.Build$Version"
            java.lang.String r13 = "NAME"
            r14 = r79
            a(r14, r8, r12, r13, r3)
            long r20 = r9.getMilis()
            com.uc.webview.export.cyclone.UCElapseTime r3 = new com.uc.webview.export.cyclone.UCElapseTime
            r3.<init>()
            java.util.concurrent.ConcurrentHashMap r9 = r10.mOptions
            java.lang.String r12 = "scst_flag"
            java.lang.Object r9 = r9.get(r12)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = com.uc.webview.export.internal.utility.j.a(r9)
            if (r9 == 0) goto L_0x0244
            java.lang.String r9 = "sc_taucmv"
            java.lang.String r9 = com.uc.webview.export.extension.UCCore.getParam(r9)
            java.lang.String r12 = "com.uc.webview.browser.shell.Build$Version"
            java.lang.String r13 = "NAME"
            java.lang.String r14 = "\\^\\^"
            b(r9, r8, r12, r13, r14)
        L_0x0244:
            long r12 = r3.getMilis()
            r3 = 29
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r3, r12)
            com.uc.webview.export.cyclone.UCElapseTime r3 = new com.uc.webview.export.cyclone.UCElapseTime
            r3.<init>()
            java.lang.String r9 = "com.uc.webview.browser.shell.SdkAuthentication"
            java.lang.Class r9 = r8.loadClass(r9)     // Catch:{ ClassNotFoundException -> 0x034b }
            r14 = 3
            r39 = r12
            java.lang.Class[] r12 = new java.lang.Class[r14]
            java.lang.Class<android.content.Context> r13 = android.content.Context.class
            r16 = 0
            r12[r16] = r13
            java.lang.Class<com.uc.webview.export.internal.utility.UCMPackageInfo> r13 = com.uc.webview.export.internal.utility.UCMPackageInfo.class
            r17 = 1
            r12[r17] = r13
            java.lang.Class<java.util.HashMap> r13 = java.util.HashMap.class
            r15 = 2
            r12[r15] = r13
            java.lang.String r13 = "tryLoadUCCore"
            java.lang.reflect.Method r12 = r9.getMethod(r13, r12)     // Catch:{ NoSuchMethodException -> 0x0297 }
            java.util.HashMap r13 = new java.util.HashMap     // Catch:{ NoSuchMethodException -> 0x0297 }
            java.util.concurrent.ConcurrentHashMap r14 = r10.mOptions     // Catch:{ NoSuchMethodException -> 0x0297 }
            int r14 = r14.size()     // Catch:{ NoSuchMethodException -> 0x0297 }
            r13.<init>(r14)     // Catch:{ NoSuchMethodException -> 0x0297 }
            java.util.concurrent.ConcurrentHashMap r14 = r10.mOptions     // Catch:{ NoSuchMethodException -> 0x0297 }
            r13.putAll(r14)     // Catch:{ NoSuchMethodException -> 0x0297 }
            r14 = 3
            java.lang.Object[] r14 = new java.lang.Object[r14]     // Catch:{ NoSuchMethodException -> 0x0297 }
            r16 = 0
            r14[r16] = r1     // Catch:{ NoSuchMethodException -> 0x0297 }
            r17 = 1
            r14[r17] = r2     // Catch:{ NoSuchMethodException -> 0x0297 }
            r15 = 2
            r14[r15] = r13     // Catch:{ NoSuchMethodException -> 0x0297 }
            r13 = 0
            r17 = 1
            goto L_0x02b5
        L_0x0297:
            r12 = 2
            java.lang.Class[] r13 = new java.lang.Class[r12]
            java.lang.Class<android.content.Context> r14 = android.content.Context.class
            r16 = 0
            r13[r16] = r14
            java.lang.Class<com.uc.webview.export.internal.utility.UCMPackageInfo> r14 = com.uc.webview.export.internal.utility.UCMPackageInfo.class
            r17 = 1
            r13[r17] = r14
            java.lang.String r14 = "tryLoadUCCore"
            java.lang.reflect.Method r13 = r9.getMethod(r14, r13)     // Catch:{ NoSuchMethodException -> 0x0341 }
            java.lang.Object[] r14 = new java.lang.Object[r12]     // Catch:{ NoSuchMethodException -> 0x0341 }
            r14[r16] = r1     // Catch:{ NoSuchMethodException -> 0x0341 }
            r14[r17] = r2     // Catch:{ NoSuchMethodException -> 0x0341 }
            r12 = r13
            r13 = 0
        L_0x02b5:
            java.lang.Object r9 = com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r13, r9, r12, r14)     // Catch:{ UCKnownException -> 0x033e, Throwable -> 0x0310 }
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch:{ UCKnownException -> 0x033e, Throwable -> 0x0310 }
            boolean r9 = com.uc.webview.export.internal.utility.j.b(r9)     // Catch:{ UCKnownException -> 0x033e, Throwable -> 0x0310 }
            r9 = r9 ^ 1
            if (r9 != 0) goto L_0x02ce
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ UCKnownException -> 0x033e, Throwable -> 0x0310 }
            r2 = 4017(0xfb1, float:5.629E-42)
            java.lang.String r3 = "tryLoadUCCore return false."
            r1.<init>(r2, r3)     // Catch:{ UCKnownException -> 0x033e, Throwable -> 0x0310 }
            throw r1     // Catch:{ UCKnownException -> 0x033e, Throwable -> 0x0310 }
        L_0x02ce:
            long r12 = r3.getMilis()
            r3 = 30
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r3, r12)
            com.uc.webview.export.cyclone.UCElapseTime r3 = new com.uc.webview.export.cyclone.UCElapseTime
            r3.<init>()
            if (r11 == 0) goto L_0x02fc
            int r9 = r77.intValue()
            r14 = 2
            r9 = r9 & r14
            if (r9 == 0) goto L_0x02fc
            android.util.Pair<java.lang.String, java.lang.String> r3 = r2.browserIFModule
            java.lang.Object r3 = r3.first
            java.lang.String r3 = (java.lang.String) r3
            com.uc.webview.export.cyclone.UCElapseTime r3 = com.uc.webview.export.internal.setup.da.a(r1, r11, r3)
            long r14 = r3.getMilisCpu()
            long r22 = r22 + r14
            long r14 = r3.getMilis()
            long r24 = r24 + r14
        L_0x02fc:
            r41 = r12
            long r12 = r3.getMilis()
            r3 = 33
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r3, r12)
            r45 = r20
            r43 = r37
            r47 = r39
            r49 = r41
            goto L_0x0363
        L_0x0310:
            r0 = move-exception
            r1 = r0
            java.lang.String r2 = r1.getMessage()
            if (r2 == 0) goto L_0x032b
            java.lang.String r3 = "9"
            int r3 = r2.indexOf(r3)
            if (r3 != 0) goto L_0x032b
            int r4 = r3 + 4
            java.lang.String r2 = r2.substring(r3, r4)     // Catch:{ Exception -> 0x032b }
            int r12 = com.uc.webview.export.internal.utility.j.c(r2)     // Catch:{ Exception -> 0x032b }
            goto L_0x032c
        L_0x032b:
            r12 = 0
        L_0x032c:
            r2 = 9000(0x2328, float:1.2612E-41)
            if (r12 < r2) goto L_0x0336
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r2.<init>(r12, r1)
            throw r2
        L_0x0336:
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 4016(0xfb0, float:5.628E-42)
            r2.<init>(r3, r1)
            throw r2
        L_0x033e:
            r0 = move-exception
            r1 = r0
            throw r1
        L_0x0341:
            r0 = move-exception
            r1 = r0
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 4015(0xfaf, float:5.626E-42)
            r2.<init>(r3, r1)
            throw r2
        L_0x034b:
            r0 = move-exception
            r1 = r0
            com.uc.webview.export.internal.setup.UCSetupException r2 = new com.uc.webview.export.internal.setup.UCSetupException
            r3 = 4014(0xfae, float:5.625E-42)
            r2.<init>(r3, r1)
            throw r2
        L_0x0355:
            r36 = r9
            r34 = r12
            r12 = r20
            r43 = r12
            r45 = r43
            r47 = r45
            r49 = r47
        L_0x0363:
            com.uc.webview.export.cyclone.UCElapseTime r3 = new com.uc.webview.export.cyclone.UCElapseTime
            r3.<init>()
            if (r11 == 0) goto L_0x038a
            int r9 = r77.intValue()
            r9 = r9 & 8
            if (r9 == 0) goto L_0x038a
            com.uc.webview.export.cyclone.UCElapseTime r3 = new com.uc.webview.export.cyclone.UCElapseTime
            r3.<init>()
            int r9 = r77.intValue()
            r10.a(r2, r1, r8, r9)
            long r14 = r3.getMilisCpu()
            long r22 = r22 + r14
            long r14 = r3.getMilis()
            long r24 = r24 + r14
        L_0x038a:
            r51 = r12
            long r12 = r3.getMilis()
            r3 = 35
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r3, r12)
            com.uc.webview.export.cyclone.UCElapseTime r3 = new com.uc.webview.export.cyclone.UCElapseTime
            r3.<init>()
            if (r11 == 0) goto L_0x03bd
            int r9 = r77.intValue()
            r14 = 32
            r9 = r9 & r14
            if (r9 == 0) goto L_0x03bd
            com.uc.webview.export.cyclone.UCElapseTime r3 = new com.uc.webview.export.cyclone.UCElapseTime
            r3.<init>()
            int r9 = r77.intValue()
            r10.b(r2, r1, r8, r9)
            long r8 = r3.getMilisCpu()
            long r22 = r22 + r8
            long r8 = r3.getMilis()
            long r24 = r24 + r8
        L_0x03bd:
            long r8 = r3.getMilis()
            r3 = 31
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r3, r8)
            com.uc.webview.export.cyclone.UCElapseTime r3 = new com.uc.webview.export.cyclone.UCElapseTime
            r3.<init>()
            if (r11 == 0) goto L_0x03eb
            int r14 = r77.intValue()
            r14 = r14 & 4
            if (r14 == 0) goto L_0x03eb
            android.util.Pair<java.lang.String, java.lang.String> r3 = r2.coreImplModule
            java.lang.Object r3 = r3.first
            java.lang.String r3 = (java.lang.String) r3
            com.uc.webview.export.cyclone.UCElapseTime r3 = com.uc.webview.export.internal.setup.da.a(r1, r11, r3)
            long r14 = r3.getMilisCpu()
            long r22 = r22 + r14
            long r14 = r3.getMilis()
            long r24 = r24 + r14
        L_0x03eb:
            r55 = r12
            r53 = r24
            long r12 = r3.getMilis()
            r1 = 34
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r1, r12)
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            r1 = 26
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r1)
            com.uc.webview.export.cyclone.UCElapseTime r1 = new com.uc.webview.export.cyclone.UCElapseTime
            r1.<init>()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            if (r75 == 0) goto L_0x042c
            android.util.Pair<java.lang.String, java.lang.String> r14 = r2.browserIFModule
            java.lang.Object r14 = r14.first
            if (r14 == 0) goto L_0x042c
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            r57 = r8
            android.util.Pair<java.lang.String, java.lang.String> r8 = r2.browserIFModule
            java.lang.Object r8 = r8.first
            java.lang.String r8 = (java.lang.String) r8
            r14.append(r8)
            java.lang.String r8 = ":"
            r14.append(r8)
            java.lang.String r8 = r14.toString()
            goto L_0x0430
        L_0x042c:
            r57 = r8
            java.lang.String r8 = ""
        L_0x0430:
            r3.append(r8)
            android.util.Pair<java.lang.String, java.lang.String> r8 = r2.coreImplModule
            java.lang.Object r8 = r8.first
            java.lang.String r8 = (java.lang.String) r8
            r3.append(r8)
            java.lang.String r3 = r3.toString()
            com.uc.webview.export.cyclone.UCLoader r8 = new com.uc.webview.export.cyclone.UCLoader
            android.util.Pair<java.lang.String, java.lang.String> r9 = r2.coreImplModule
            java.lang.Object r9 = r9.second
            java.lang.String r9 = (java.lang.String) r9
            java.lang.String r14 = r2.soDirPath
            r8.<init>(r3, r9, r14, r4)
            long r3 = r1.getMilis()
            long r14 = r1.getMilisCpu()
            long r6 = r6 + r14
            long r14 = r1.getMilis()
            r59 = r12
            long r12 = r32 + r14
            r1 = 27
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r1)
            java.lang.String r1 = "ThinEnvTask.tryEnv load core.jar"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r1)
            r2.mCoreClassLoader = r8
            r10.mCL = r8
            r10.a = r5
            r1 = r36
            r10.b = r1
            java.lang.String r2 = "sdk_vrf"
            boolean r5 = r10.b
            if (r75 == 0) goto L_0x047c
            java.lang.String r1 = "sdk"
        L_0x047a:
            r8 = r1
            goto L_0x0480
        L_0x047c:
            java.lang.String r1 = "ucm"
            goto L_0x047a
        L_0x0480:
            r1 = r10
            r61 = r3
            r3 = r5
            r4 = r11
            r5 = r8
            r65 = r6
            r8 = r18
            r63 = r28
            r6 = r53
            r67 = r8
            r69 = r57
            r8 = r22
            r1.a(r2, r3, r4, r5, r6, r8)
            java.lang.String r2 = "sdk_opt"
            boolean r3 = r10.b
            r1 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)
            if (r75 == 0) goto L_0x04a6
            java.lang.String r1 = "sdk"
        L_0x04a4:
            r5 = r1
            goto L_0x04aa
        L_0x04a6:
            java.lang.String r1 = "ucm"
            goto L_0x04a4
        L_0x04aa:
            r1 = r10
            r6 = r12
            r8 = r65
            r1.a(r2, r3, r4, r5, r6, r8)
            android.util.Pair r1 = new android.util.Pair     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r2 = "sdk_vrf_detail"
            com.uc.webview.export.cyclone.UCHashMap r3 = new com.uc.webview.export.cyclone.UCHashMap     // Catch:{ Throwable -> 0x05bb }
            r3.<init>()     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cnt"
            java.lang.String r5 = "1"
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "id"
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "code"
            java.lang.String r5 = java.lang.String.valueOf(r77)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "frun"
            boolean r5 = r10.b     // Catch:{ Throwable -> 0x05bb }
            if (r5 == 0) goto L_0x04e3
            java.lang.String r5 = "T"
            goto L_0x04e5
        L_0x04e3:
            java.lang.String r5 = "F"
        L_0x04e5:
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "data"
            if (r75 == 0) goto L_0x04f0
            java.lang.String r5 = "sdk"
            goto L_0x04f3
        L_0x04f0:
            java.lang.String r5 = "ucm"
        L_0x04f3:
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cost"
            java.lang.String r5 = java.lang.String.valueOf(r12)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cost_cpu"
            r6 = r65
            java.lang.String r5 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "lcj"
            r5 = r61
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "lsj"
            r5 = r34
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cc"
            r5 = r26
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cp"
            r5 = r67
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "csok"
            r5 = r63
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "csg1"
            r5 = r30
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "csg2"
            r5 = r51
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "csg3"
            r5 = r59
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cbt"
            r5 = r43
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cve"
            r5 = r45
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cvi"
            r5 = r47
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "ccp"
            r5 = r49
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "csah"
            r5 = r55
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            java.lang.String r4 = "cpah"
            r5 = r69
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ Throwable -> 0x05bb }
            com.uc.webview.export.cyclone.UCHashMap r3 = r3.set(r4, r5)     // Catch:{ Throwable -> 0x05bb }
            r1.<init>(r2, r3)     // Catch:{ Throwable -> 0x05bb }
            r10.callbackStat(r1)     // Catch:{ Throwable -> 0x05bb }
        L_0x05bb:
            java.lang.String r1 = "ThinEnvTask"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "tryEnv Time: VERIFY:"
            r2.<init>(r3)
            r3 = r53
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r2.append(r3)
            java.lang.String r3 = " DEXOPT:"
            r2.append(r3)
            java.lang.String r3 = java.lang.String.valueOf(r12)
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.e(r1, r2)
            android.util.Pair r1 = new android.util.Pair
            java.lang.String r2 = "sdk_stp_s"
            r3 = 0
            r1.<init>(r2, r3)
            r10.callbackStat(r1)
            r1 = 10
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r1)
            java.lang.String r1 = "ThinEnvTask.tryEnv"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.bv.a(android.content.Context, com.uc.webview.export.internal.setup.UCMPackageInfo, boolean, boolean, boolean, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.ClassLoader):void");
    }
}
