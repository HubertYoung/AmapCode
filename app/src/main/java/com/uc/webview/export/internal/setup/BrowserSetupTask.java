package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.os.SystemClock;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.location.sdk.fusion.LocationParams;
import com.uc.webview.export.annotations.Interface;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.cyclone.UCHashMap;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.setup.UCSubSetupTask.a;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.f;
import com.uc.webview.export.utility.SetupTask;
import java.io.File;
import java.util.HashMap;

@Interface
/* compiled from: ProGuard */
public class BrowserSetupTask extends SetupTask {
    private static BrowserSetupTask a;
    private static final int[] j = {1008, 2008, 3004, 3007, 4007};
    private static final int[] k = {1003, 1006, 2001};
    /* access modifiers changed from: private */
    public t b;
    /* access modifiers changed from: private */
    public t c = null;
    /* access modifiers changed from: private */
    public Context d;
    /* access modifiers changed from: private */
    public File e = null;
    /* access modifiers changed from: private */
    public UCElapseTime f;
    /* access modifiers changed from: private */
    public long g;
    private ValueCallback<t> h;
    /* access modifiers changed from: private */
    public ValueCallback<t> i;
    /* access modifiers changed from: private */
    public final ValueCallback<t> l = new a(this);
    /* access modifiers changed from: private */
    public final ValueCallback<t> m = new d(this);

    static /* synthetic */ void a(BrowserSetupTask browserSetupTask, t tVar, UCMRepairInfo uCMRepairInfo) {
        StringBuilder sb = new StringBuilder("BrowserSetupTask - shell repair kernel repairDir: ");
        sb.append(uCMRepairInfo.repairDir);
        sb.append(", verifyPolicy: ");
        sb.append(uCMRepairInfo.verifyPolicy);
        sb.append(", repairResult: ");
        sb.append(uCMRepairInfo.repairResult);
        Log.w("UCAsyncTask", sb.toString());
        StringBuffer b2 = b("shell_repair_kernel", tVar);
        if (b2 != null) {
            try {
                StringBuilder sb2 = new StringBuilder("repair verifyPolicy: ");
                sb2.append(uCMRepairInfo.verifyPolicy);
                b2.append(sb2.toString());
                b2.append("\n");
                StringBuilder sb3 = new StringBuilder("repair result: ");
                sb3.append(uCMRepairInfo.repairResult);
                b2.append(sb3.toString());
                b2.append("\n");
                StringBuilder sb4 = new StringBuilder("repair ucm dir: ");
                sb4.append(uCMRepairInfo.repairDir);
                b2.append(sb4.toString());
                b2.append("\n");
            } catch (Exception unused) {
            }
        }
        f.a(b2, "kernel");
        browserSetupTask.callbackStat(new Pair(IWaStat.SETUP_REPAIR, new UCHashMap().set("cnt", "1").set("err", String.valueOf(uCMRepairInfo.repairResult)).set("code", String.valueOf(uCMRepairInfo.verifyPolicy)).set("dir", uCMRepairInfo.repairDir)));
        if (uCMRepairInfo.repairResult == -1) {
            ((t) ((t) ((t) ((t) ((t) new ae().invoke(10001, browserSetupTask)).setOptions(browserSetupTask.b.mOptions)).onEvent((String) "stat", (ValueCallback<CALLBACK_TYPE>) new a<CALLBACK_TYPE>())).onEvent((String) "success", browserSetupTask.l)).onEvent((String) LogCategory.CATEGORY_EXCEPTION, browserSetupTask.m)).start();
        } else if (uCMRepairInfo.repairResult == 0 || uCMRepairInfo.repairResult == 1) {
            Integer num = (Integer) browserSetupTask.mOptions.get(UCCore.OPTION_VERIFY_POLICY);
            if (!(num == null || (num.intValue() & UCCore.VERIFY_POLICY_QUICK) == 0)) {
                num = Integer.valueOf(num.intValue() & -1073741825);
            }
            ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) new bw().invoke(10001, browserSetupTask)).setOptions(browserSetupTask.b.mOptions)).onEvent((String) "stat", (ValueCallback<CALLBACK_TYPE>) new a<CALLBACK_TYPE>())).onEvent((String) "success", browserSetupTask.l)).onEvent((String) LogCategory.CATEGORY_EXCEPTION, browserSetupTask.m)).setup((String) UCCore.OPTION_VERIFY_POLICY, (Object) num)).setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) uCMRepairInfo.repairDir)).start();
        } else {
            browserSetupTask.m.onReceiveValue(tVar);
        }
    }

    static /* synthetic */ void i(BrowserSetupTask browserSetupTask) {
        Context context = (Context) browserSetupTask.getOption(UCCore.OPTION_CONTEXT);
        Boolean bool = Boolean.TRUE;
        try {
            File file = (File) UCMPackageInfo.invoke(10003, context);
            StringBuilder sb = new StringBuilder();
            sb.append(file.getAbsolutePath());
            sb.append(String.format("_bad_%s", new Object[]{Long.valueOf(SystemClock.uptimeMillis())}));
            File file2 = new File(sb.toString());
            file.renameTo(file2);
            File[] listFiles = file2.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                if (bool.booleanValue() || listFiles.length >= 2) {
                    File[] listFiles2 = file2.listFiles();
                    if (listFiles2 != null) {
                        for (File recursiveDelete : listFiles2) {
                            UCCyclone.recursiveDelete(recursiveDelete, false, null);
                        }
                    }
                    file2.delete();
                }
            }
        } catch (Throwable unused) {
        }
    }

    public boolean isNeedRestartError(int i2) {
        for (int i3 : j) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public boolean isNoDiskSpaceError(int i2) {
        for (int i3 : k) {
            if (i3 == i2) {
                return true;
            }
        }
        return false;
    }

    public static synchronized BrowserSetupTask getInstance() {
        BrowserSetupTask browserSetupTask;
        synchronized (BrowserSetupTask.class) {
            if (a == null) {
                a = new BrowserSetupTask();
            }
            browserSetupTask = a;
        }
        return browserSetupTask;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(19:0|1|2|3|4|5|(1:8)|9|(1:11)(1:12)|13|(1:15)(1:16)|17|(1:19)(1:20)|21|(1:23)(1:24)|25|(1:27)(1:28)|29|(1:(2:35|36)(6:37|(1:39)(1:40)|41|(2:43|(2:45|46))|47|48))(2:32|33)) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0064 */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x00e0  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x00e3  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x00fc  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0117  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0132  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x01cf  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x01d2  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0242  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x02a5  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x00c5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r10 = this;
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            r0 = 3
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r0)
            com.uc.webview.export.cyclone.UCElapseTime r0 = new com.uc.webview.export.cyclone.UCElapseTime
            r0.<init>()
            r10.f = r0
            long r0 = android.os.SystemClock.currentThreadTimeMillis()
            r10.g = r0
            r10.setupGlobalOnce()
            java.lang.String r0 = "CONTEXT"
            java.lang.Object r0 = r10.getOption(r0)
            android.content.Context r0 = (android.content.Context) r0
            r10.d = r0
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.String r2 = "stat"
            r3 = 0
            r1[r3] = r2
            r2 = 10007(0x2717, float:1.4023E-41)
            java.lang.Object r1 = r10.invokeO(r2, r1)
            android.webkit.ValueCallback r1 = (android.webkit.ValueCallback) r1
            java.lang.String r2 = "stat"
            com.uc.webview.export.internal.setup.e r4 = new com.uc.webview.export.internal.setup.e
            r4.<init>(r10, r1)
            r10.onEvent(r2, r4)
            r1 = 10001(0x2711, float:1.4014E-41)
            com.uc.webview.export.internal.setup.z r2 = new com.uc.webview.export.internal.setup.z     // Catch:{ Throwable -> 0x0064 }
            r2.<init>()     // Catch:{ Throwable -> 0x0064 }
            com.uc.webview.export.cyclone.UCCyclone.statCallback = r2     // Catch:{ Throwable -> 0x0064 }
            com.uc.webview.export.internal.setup.z r2 = (com.uc.webview.export.internal.setup.z) r2     // Catch:{ Throwable -> 0x0064 }
            java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0064 }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = com.uc.webview.export.utility.SetupTask.getRoot()     // Catch:{ Throwable -> 0x0064 }
            r4[r3] = r5     // Catch:{ Throwable -> 0x0064 }
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r1, r4)     // Catch:{ Throwable -> 0x0064 }
            com.uc.webview.export.internal.setup.z r2 = (com.uc.webview.export.internal.setup.z) r2     // Catch:{ Throwable -> 0x0064 }
            java.lang.String r4 = "stat"
            com.uc.webview.export.internal.setup.UCSubSetupTask$a r5 = new com.uc.webview.export.internal.setup.UCSubSetupTask$a     // Catch:{ Throwable -> 0x0064 }
            r5.<init>()     // Catch:{ Throwable -> 0x0064 }
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.onEvent(r4, r5)     // Catch:{ Throwable -> 0x0064 }
            com.uc.webview.export.internal.setup.z r2 = (com.uc.webview.export.internal.setup.z) r2     // Catch:{ Throwable -> 0x0064 }
            r2.start()     // Catch:{ Throwable -> 0x0064 }
        L_0x0064:
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = new com.uc.webview.export.internal.setup.UCAsyncTask     // Catch:{ Throwable -> 0x007f }
            com.uc.webview.export.cyclone.UCDex r4 = new com.uc.webview.export.cyclone.UCDex     // Catch:{ Throwable -> 0x007f }
            r4.<init>()     // Catch:{ Throwable -> 0x007f }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x007f }
            java.lang.Object[] r4 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x007f }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = com.uc.webview.export.utility.SetupTask.getRoot()     // Catch:{ Throwable -> 0x007f }
            r4[r3] = r5     // Catch:{ Throwable -> 0x007f }
            com.uc.webview.export.internal.setup.UCAsyncTask r2 = r2.invoke(r1, r4)     // Catch:{ Throwable -> 0x007f }
            r4 = 5000(0x1388, double:2.4703E-320)
            r2.start(r4)     // Catch:{ Throwable -> 0x007f }
        L_0x007f:
            com.uc.webview.export.internal.setup.UCAsyncTask$a r2 = new com.uc.webview.export.internal.setup.UCAsyncTask$a
            r2.<init>()
            r10.i = r2
            com.uc.webview.export.internal.setup.UCSubSetupTask$a r2 = new com.uc.webview.export.internal.setup.UCSubSetupTask$a
            r2.<init>()
            r10.h = r2
            java.lang.String r2 = "soFilePath"
            r4 = 0
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r10.setup(r2, r4)
            com.uc.webview.export.utility.SetupTask r2 = (com.uc.webview.export.utility.SetupTask) r2
            java.lang.String r5 = "resFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r5, r4)
            com.uc.webview.export.utility.SetupTask r2 = (com.uc.webview.export.utility.SetupTask) r2
            java.lang.String r5 = "ucmUpdUrl"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r5, r4)
            com.uc.webview.export.utility.SetupTask r2 = (com.uc.webview.export.utility.SetupTask) r2
            java.lang.String r5 = "ucmCfgFile"
            com.uc.webview.export.internal.setup.BaseSetupTask r2 = r2.setup(r5, r4)
            com.uc.webview.export.utility.SetupTask r2 = (com.uc.webview.export.utility.SetupTask) r2
            java.lang.String r5 = "ucmKrlDir"
            r2.setup(r5, r4)
            java.lang.String r2 = "ucmZipFile"
            java.lang.Object r2 = r10.getOption(r2)
            java.lang.String r2 = (java.lang.String) r2
            boolean r5 = com.uc.webview.export.internal.utility.j.a(r2)
            if (r5 != 0) goto L_0x00cc
            java.io.File r5 = new java.io.File
            r5.<init>(r2)
            r10.e = r5
        L_0x00cc:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r5 = "ucmLibDir"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.String r5 = (java.lang.String) r5
            boolean r5 = com.uc.webview.export.internal.utility.j.a(r5)
            if (r5 == 0) goto L_0x00e3
            java.lang.String r5 = "LIB:N"
            goto L_0x00e5
        L_0x00e3:
            java.lang.String r5 = "LIB:Y"
        L_0x00e5:
            r2.append(r5)
            java.lang.String r5 = ","
            r2.append(r5)
            java.lang.String r5 = "ucmZipDir"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.String r5 = (java.lang.String) r5
            boolean r5 = com.uc.webview.export.internal.utility.j.a(r5)
            if (r5 == 0) goto L_0x00ff
            java.lang.String r5 = "ZIP:N"
            goto L_0x0101
        L_0x00ff:
            java.lang.String r5 = "ZIP:Y"
        L_0x0101:
            r2.append(r5)
            java.lang.String r5 = ","
            r2.append(r5)
            java.lang.String r5 = "init_setup_thread"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = com.uc.webview.export.internal.utility.j.b(r5)
            if (r5 == 0) goto L_0x011a
            java.lang.String r5 = "IIST:F"
            goto L_0x011c
        L_0x011a:
            java.lang.String r5 = "IIST:T"
        L_0x011c:
            r2.append(r5)
            java.lang.String r5 = ","
            r2.append(r5)
            java.lang.String r5 = "AC"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = com.uc.webview.export.internal.utility.j.b(r5)
            if (r5 == 0) goto L_0x0135
            java.lang.String r5 = "HA:F"
            goto L_0x0137
        L_0x0135:
            java.lang.String r5 = "HA:T"
        L_0x0137:
            r2.append(r5)
            java.lang.String r5 = ",MP:"
            r2.append(r5)
            java.lang.String r5 = "WEBVIEW_MULTI_PROCESS"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.Integer r5 = (java.lang.Integer) r5
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r2.append(r5)
            java.lang.String r5 = ",MP_FT:"
            r2.append(r5)
            java.lang.String r5 = "WEBVIEW_MULTI_PROCESS_FALLBACK_TIMEOUT"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.Integer r5 = (java.lang.Integer) r5
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r2.append(r5)
            java.lang.String r5 = ",VP:"
            r2.append(r5)
            java.lang.String r5 = "VERIFY_POLICY"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.Integer r5 = (java.lang.Integer) r5
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r2.append(r5)
            java.lang.String r5 = ",WP:"
            r2.append(r5)
            java.lang.String r5 = "WEBVIEW_POLICY"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.Integer r5 = (java.lang.Integer) r5
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r2.append(r5)
            java.lang.String r5 = ",CD_LD:"
            r2.append(r5)
            java.lang.Object[] r5 = new java.lang.Object[r0]
            java.lang.String r6 = "load"
            r5[r3] = r6
            r6 = 10005(0x2715, float:1.402E-41)
            java.lang.Object r5 = com.uc.webview.export.internal.SDKFactory.invoke(r6, r5)
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r2.append(r5)
            java.lang.String r5 = ",CD_SOEK:"
            r2.append(r5)
            java.lang.Object[] r5 = new java.lang.Object[r0]
            java.lang.String r7 = "skip_old_extra_kernel"
            r5[r3] = r7
            java.lang.Object r5 = com.uc.webview.export.internal.SDKFactory.invoke(r6, r5)
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r2.append(r5)
            java.lang.String r5 = ",PT:"
            r2.append(r5)
            int r5 = com.uc.webview.export.Build.PACK_TYPE
            java.lang.String r5 = java.lang.String.valueOf(r5)
            r2.append(r5)
            java.lang.String r5 = ",KF:"
            r2.append(r5)
            java.io.File r5 = r10.e
            if (r5 != 0) goto L_0x01d2
            java.lang.String r5 = "N"
            goto L_0x01d4
        L_0x01d2:
            java.lang.String r5 = "Y"
        L_0x01d4:
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            android.util.Pair r5 = new android.util.Pair
            java.lang.String r6 = "sdk_stp"
            com.uc.webview.export.cyclone.UCHashMap r7 = new com.uc.webview.export.cyclone.UCHashMap
            r7.<init>()
            java.lang.String r8 = "cnt"
            java.lang.String r9 = "1"
            com.uc.webview.export.cyclone.UCHashMap r7 = r7.set(r8, r9)
            java.lang.String r8 = "data"
            com.uc.webview.export.cyclone.UCHashMap r2 = r7.set(r8, r2)
            java.lang.String r7 = "cpu_cnt"
            java.lang.String r8 = com.uc.webview.export.internal.utility.j.a()
            com.uc.webview.export.cyclone.UCHashMap r2 = r2.set(r7, r8)
            java.lang.String r7 = "cpu_freq"
            java.lang.String r8 = com.uc.webview.export.internal.utility.j.b()
            com.uc.webview.export.cyclone.UCHashMap r2 = r2.set(r7, r8)
            r5.<init>(r6, r2)
            r10.callbackStat(r5)
            r2 = 10011(0x271b, float:1.4028E-41)
            java.lang.Object[] r5 = new java.lang.Object[r3]
            java.lang.Object r2 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r2, r5)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            java.lang.String r5 = "ucmLibDir"
            java.lang.Object r5 = r10.getOption(r5)
            java.lang.String r5 = (java.lang.String) r5
            boolean r6 = com.uc.webview.export.internal.utility.j.a(r5)
            if (r6 == 0) goto L_0x0240
            if (r2 != 0) goto L_0x0240
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r2 = 3009(0xbc1, float:4.217E-42)
            java.lang.String r4 = "Option [%s] expected."
            java.lang.Object[] r0 = new java.lang.Object[r0]
            java.lang.String r5 = "ucmLibDir"
            r0[r3] = r5
            java.lang.String r0 = java.lang.String.format(r4, r0)
            r1.<init>(r2, r0)
            throw r1
        L_0x0240:
            if (r2 == 0) goto L_0x02a5
            com.uc.webview.export.internal.setup.bu r2 = new com.uc.webview.export.internal.setup.bu
            r2.<init>()
            java.lang.Object[] r0 = new java.lang.Object[r0]
            r0[r3] = r10
            com.uc.webview.export.internal.setup.UCAsyncTask r0 = r2.invoke(r1, r0)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.util.concurrent.ConcurrentHashMap r1 = r10.mOptions
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.setOptions(r1)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.lang.String r1 = "setup"
            android.webkit.ValueCallback<com.uc.webview.export.internal.setup.t> r3 = r10.i
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.onEvent(r1, r3)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.lang.String r1 = "load"
            android.webkit.ValueCallback<com.uc.webview.export.internal.setup.t> r3 = r10.i
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.onEvent(r1, r3)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.lang.String r1 = "init"
            android.webkit.ValueCallback<com.uc.webview.export.internal.setup.t> r3 = r10.i
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.onEvent(r1, r3)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.lang.String r1 = "switch"
            android.webkit.ValueCallback<com.uc.webview.export.internal.setup.t> r3 = r10.i
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.onEvent(r1, r3)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.lang.String r1 = "stat"
            android.webkit.ValueCallback<com.uc.webview.export.internal.setup.t> r3 = r10.h
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.onEvent(r1, r3)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.lang.String r1 = "success"
            android.webkit.ValueCallback<com.uc.webview.export.internal.setup.t> r3 = r10.l
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.onEvent(r1, r3)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.lang.String r1 = "exception"
            android.webkit.ValueCallback<com.uc.webview.export.internal.setup.t> r3 = r10.m
            r0.onEvent(r1, r3)
            r2.start()
            java.lang.String r0 = "Thick SDK"
            com.uc.webview.export.internal.SDKFactory.a(r0)
            return
        L_0x02a5:
            java.lang.String r2 = "dexFilePath"
            java.lang.Object r2 = r10.getOption(r2)
            java.lang.String r2 = (java.lang.String) r2
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r2)
            if (r2 != 0) goto L_0x02c5
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "dexFilePath"
            java.lang.Object r2 = r10.getOption(r2)
            java.lang.String r2 = (java.lang.String) r2
            r1.<init>(r2)
            com.uc.webview.export.internal.setup.t r1 = r10.a(r1, r4)
            goto L_0x032e
        L_0x02c5:
            com.uc.webview.export.internal.setup.UCAsyncTask$a r2 = new com.uc.webview.export.internal.setup.UCAsyncTask$a
            r2.<init>()
            com.uc.webview.export.internal.setup.bw r6 = new com.uc.webview.export.internal.setup.bw
            r6.<init>()
            java.lang.Object[] r7 = new java.lang.Object[r0]
            r7[r3] = r10
            com.uc.webview.export.internal.setup.UCAsyncTask r1 = r6.invoke(r1, r7)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.util.concurrent.ConcurrentHashMap r6 = r10.mOptions
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.setOptions(r6)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r6 = "ucmZipDir"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.setup(r6, r4)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r6 = "stat"
            com.uc.webview.export.internal.setup.UCSubSetupTask$a r7 = new com.uc.webview.export.internal.setup.UCSubSetupTask$a
            r7.<init>()
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r6, r7)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r6 = "success"
            android.webkit.ValueCallback<com.uc.webview.export.internal.setup.t> r7 = r10.l
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r6, r7)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r6 = "setup"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r6, r2)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r6 = "load"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r6, r2)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r6 = "init"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r6, r2)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r6 = "switch"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r6, r2)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r2 = "exception"
            com.uc.webview.export.internal.setup.i r6 = new com.uc.webview.export.internal.setup.i
            r6.<init>(r10)
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r2, r6)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
        L_0x032e:
            r10.b = r1
            java.lang.String r1 = "UCAsyncTask"
            java.lang.String r2 = "BrowserSetupTask shell setup UCM_LIB_DIR: "
            java.lang.String r5 = java.lang.String.valueOf(r5)
            java.lang.String r2 = r2.concat(r5)
            com.uc.webview.export.internal.utility.Log.w(r1, r2)
            java.io.File r1 = r10.e
            if (r1 == 0) goto L_0x03ac
            com.uc.webview.export.internal.setup.n r1 = new com.uc.webview.export.internal.setup.n
            r1.<init>()
            java.util.concurrent.ConcurrentHashMap r2 = r10.mOptions
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.setOptions(r2)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r2 = "ucmLibDir"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.setup(r2, r4)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r2 = "ucmZipDir"
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.setup(r2, r4)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r2 = "ucmZipFile"
            java.io.File r4 = r10.e
            java.lang.String r4 = r4.getAbsolutePath()
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.setup(r2, r4)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r2 = "stat"
            com.uc.webview.export.internal.setup.UCSubSetupTask$a r4 = new com.uc.webview.export.internal.setup.UCSubSetupTask$a
            r4.<init>()
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r2, r4)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            java.lang.String r2 = "setup"
            com.uc.webview.export.internal.setup.f r4 = new com.uc.webview.export.internal.setup.f
            r4.<init>(r10)
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r2, r4)
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1
            r10.c = r1
            r1 = 10003(0x2713, float:1.4017E-41)
            java.lang.Object[] r0 = new java.lang.Object[r0]
            android.content.Context r2 = r10.d
            r0[r3] = r2
            java.lang.Object r0 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r1, r0)
            java.io.File r0 = (java.io.File) r0
            java.lang.String[] r1 = r0.list()
            int r1 = r1.length
            if (r1 <= 0) goto L_0x03ac
            com.uc.webview.export.internal.setup.t r1 = r10.b
            com.uc.webview.export.internal.setup.t r0 = r10.a(r0, r1)
            r0.start()
            return
        L_0x03ac:
            com.uc.webview.export.internal.setup.t r0 = r10.b
            r0.start()
            java.lang.String r0 = "BrowserSetupTask.run"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r0)
            r0 = 4
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.BrowserSetupTask.run():void");
    }

    private t a(File file, t tVar) {
        t tVar2 = (t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) new bw().invoke(10001, this)).setOptions(this.mOptions)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) null)).setup((String) UCCore.OPTION_UCM_ZIP_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) null)).setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) file.getAbsolutePath())).setup((String) "chkDecFinish", (Object) Boolean.TRUE)).onEvent((String) "stat", (ValueCallback<CALLBACK_TYPE>) new a<CALLBACK_TYPE>())).onEvent((String) "success", this.l)).onEvent((String) "setup", this.i)).onEvent((String) "crash_none", (ValueCallback<CALLBACK_TYPE>) null)).onEvent((String) "crash_seen", (ValueCallback<CALLBACK_TYPE>) null)).onEvent((String) "crash_repeat", (ValueCallback<CALLBACK_TYPE>) new h<CALLBACK_TYPE>(this, file))).onEvent((String) LogCategory.CATEGORY_EXCEPTION, (ValueCallback<CALLBACK_TYPE>) new g<CALLBACK_TYPE>(this, tVar));
        ((t) tVar2.onEvent((String) H5PageData.KEY_UC_START, tVar2.getSetupCrashImproverInst(file.getAbsolutePath()).d)).onEvent((String) LocationParams.PARA_COMMON_DIE, tVar2.getSetupCrashImproverInst(file.getAbsolutePath()).e);
        return tVar2;
    }

    public void startDecompressSetupTask(Context context, String str, ValueCallback<Pair<String, HashMap<String, String>>> valueCallback) {
        ((at) ((at) ((at) new at().setup((String) UCCore.OPTION_CONTEXT, (Object) context)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) str)).setup((String) "stat", (Object) valueCallback)).start();
    }

    private static StringBuffer b(String str, t tVar) {
        if (tVar == null || tVar.getException() == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append("action:");
            stringBuffer.append(str);
            stringBuffer.append("\n");
            stringBuffer.append("error code: ");
            stringBuffer.append(tVar.getException().errCode());
            stringBuffer.append("\n");
            stringBuffer.append("class name: ");
            stringBuffer.append(tVar.getException().getRootCause().getClass().getName());
            stringBuffer.append("\n");
            stringBuffer.append("message: ");
            stringBuffer.append(tVar.getException().getRootCause().getMessage());
            stringBuffer.append("\n");
            stringBuffer.append("kernel file hash: ");
            stringBuffer.append(SetupTask.sFirstUCM);
            stringBuffer.append("\n");
            stringBuffer.append("root stack trace");
            stringBuffer.append(android.util.Log.getStackTraceString(tVar.getException().getRootCause()));
            stringBuffer.append("\n");
        } catch (Exception unused) {
        }
        return stringBuffer;
    }
}
