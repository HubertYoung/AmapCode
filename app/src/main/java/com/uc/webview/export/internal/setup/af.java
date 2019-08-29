package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.location.sdk.fusion.LocationParams;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.setup.UCSubSetupTask.a;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import com.uc.webview.export.utility.SetupTask;
import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;

/* compiled from: ProGuard */
public class af extends SetupTask {
    /* access modifiers changed from: private */
    public static Stack<UCSetupTask> b = new Stack<>();
    public t a;
    /* access modifiers changed from: private */
    public t c;
    /* access modifiers changed from: private */
    public t d;
    /* access modifiers changed from: private */
    public Context e;
    /* access modifiers changed from: private */
    public UCElapseTime f;
    /* access modifiers changed from: private */
    public UCSetupException g;
    /* access modifiers changed from: private */
    public UCSetupTask h;
    private ValueCallback<t> i;
    private ValueCallback<t> j;
    /* access modifiers changed from: private */
    public boolean k = false;
    /* access modifiers changed from: private */
    public List<bl> l;
    /* access modifiers changed from: private */
    public final ValueCallback<t> m = new ag(this);
    /* access modifiers changed from: private */
    public final ValueCallback<t> n = new aj(this);
    private final ValueCallback<t> o = new ak(this);
    private final ValueCallback<t> p = new ao(this);
    /* access modifiers changed from: private */
    public final ValueCallback<t> q = new as(this);

    static /* synthetic */ void a(af afVar, UCMRunningInfo uCMRunningInfo) {
        afVar.setLoadedUCM(uCMRunningInfo);
        afVar.setTotalLoadedUCM(uCMRunningInfo);
        SDKFactory.o = uCMRunningInfo.loadType;
        StringBuilder sb = new StringBuilder("initLoadUcm coreType: ");
        sb.append(SDKFactory.o);
        sb.append(", isShareCore");
        sb.append(uCMRunningInfo.isShareCore);
        Log.d("SdkSetupTask", sb.toString());
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(7:0|1|(1:3)|4|5|6|(3:8|9|11)) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x001a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void e(com.uc.webview.export.internal.setup.af r4) {
        /*
            com.uc.webview.export.internal.setup.UCMRunningInfo r0 = getTotalLoadedUCM()     // Catch:{ Throwable -> 0x001a }
            int r0 = r0.coreType     // Catch:{ Throwable -> 0x001a }
            r1 = 2
            if (r0 == r1) goto L_0x001a
            r0 = 10041(0x2739, float:1.407E-41)
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x001a }
            r2 = 0
            com.uc.webview.export.internal.setup.UCMRunningInfo r3 = getTotalLoadedUCM()     // Catch:{ Throwable -> 0x001a }
            java.lang.ClassLoader r3 = r3.shellClassLoader     // Catch:{ Throwable -> 0x001a }
            r1[r2] = r3     // Catch:{ Throwable -> 0x001a }
            com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r0, r1)     // Catch:{ Throwable -> 0x001a }
        L_0x001a:
            com.uc.webview.export.internal.setup.UCMRunningInfo r0 = getTotalLoadedUCM()     // Catch:{ Throwable -> 0x002f }
            com.uc.webview.export.internal.setup.UCMPackageInfo r0 = r0.ucmPackageInfo     // Catch:{ Throwable -> 0x002f }
            java.lang.String r1 = "loadPolicy"
            java.lang.Object r1 = r4.getOption(r1)     // Catch:{ Throwable -> 0x002f }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x002f }
            java.lang.String r0 = com.uc.webview.export.internal.SDKFactory.a(r0, r1)     // Catch:{ Throwable -> 0x002f }
            com.uc.webview.export.internal.SDKFactory.a(r0)     // Catch:{ Throwable -> 0x002f }
        L_0x002f:
            java.lang.String r0 = "load_share_core_host"
            java.lang.Object r4 = r4.getOption(r0)     // Catch:{ Throwable -> 0x003a }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Throwable -> 0x003a }
            com.uc.webview.export.internal.utility.g.a(r4)     // Catch:{ Throwable -> 0x003a }
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.af.e(com.uc.webview.export.internal.setup.af):void");
    }

    static /* synthetic */ void i(af afVar) {
        try {
            if (j.a(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_HOST_UPD_SETUP_TASK_WAIT_MILIS)) && afVar.d != null) {
                WaStat.stat((String) IWaStat.SHARE_CORE_DELAY_DECOMPRESS_START_PV);
                afVar.d.start(2000);
                afVar.d = null;
            }
        } catch (Throwable unused) {
        }
    }

    static /* synthetic */ void j(af afVar) {
        try {
            getTotalLoadedUCM();
            WaStat.stat((String) IWaStat.SHARE_CORE_COPY_TASK_NEW_PV);
            boolean z = true;
            bk bkVar = (bk) ((bk) new bk().invoke(10001, SetupTask.getRoot())).setOptions(afVar.mOptions);
            if (getTotalLoadedUCM().coreType != 2) {
                z = false;
            }
            ((bk) ((bk) bkVar.setup("o_st_dhcs", Boolean.valueOf(z))).onEvent("stat", new a())).start(5000);
        } catch (Throwable unused) {
        }
    }

    static /* synthetic */ t p(af afVar) {
        WaStat.stat((String) IWaStat.SHARE_CORE_CREATE_DELAY_SEARE_CORE_FILE_TASK_PV);
        t a2 = afVar.a((t) new bf(), (String) "ShareCoreSearchCoreFileTask");
        a2.invoke(10001, UCSetupTask.getRoot());
        return a2;
    }

    public final void a(String str, Callable<Boolean> callable) {
        t tVar = (t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) new cn().invoke(10001, UCSetupTask.getRoot())).setOptions(this.mOptions)).setup((String) UCCore.OPTION_UCM_ZIP_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) null)).setup((String) UCCore.OPTION_USE_SDK_SETUP, (Object) Boolean.TRUE)).setup((String) "chkMultiCore", (Object) Boolean.TRUE)).onEvent((String) "stat", this.i != null ? this.i : new a<>())).onEvent((String) FunctionSupportConfiger.SWITCH_TAG, this.q)).onEvent((String) "downloadException", (ValueCallback<CALLBACK_TYPE>) new an<CALLBACK_TYPE>(this))).onEvent((String) "downloadFileDelete", (ValueCallback<CALLBACK_TYPE>) new am<CALLBACK_TYPE>(this, str))).onEvent((String) "updateProgress", (ValueCallback<CALLBACK_TYPE>) new al<CALLBACK_TYPE>(this));
        this.a = tVar;
        this.c = tVar;
        if (callable != null) {
            this.c.setup((String) UCCore.OPTION_DOWNLOAD_CHECKER, (Object) callable);
        }
        if (!j.a(str)) {
            this.c.setup((String) UCCore.OPTION_UCM_UPD_URL, (Object) str);
        }
        if (CDParamKeys.CD_VALUE_LOAD_POLICY_SHARE_CORE.equals(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_LOAD_POLICY))) {
            this.c.onEvent((String) "shareCoreEvt", this.p);
        }
    }

    /* access modifiers changed from: private */
    public t b() {
        WaStat.stat((String) IWaStat.SHARE_CORE_NEW_SC_TASK);
        return (t) a((t) new be(), (String) "ShareCoreSdcardSetupTask").setup((String) "scst_flag", (Object) Boolean.TRUE);
    }

    private void a(t tVar) {
        WaStat.stat((String) IWaStat.SHARE_CORE_FAULT_TOLERANCE_TASK);
        t a2 = (!j.a(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_KRL_DIR)) || !j.a(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_BAK_ZIP_FPATH))) ? a((t) new bc(), (String) "ShareCoreFaultToleranceTask") : null;
        if (a2 != null) {
            b.push(a2);
            tVar.onEvent(be.c, (ValueCallback<CALLBACK_TYPE>) new ar<CALLBACK_TYPE>(this));
        }
    }

    private t d() {
        t tVar;
        StartupTrace.a();
        WaStat.stat((String) IWaStat.SHARE_CORE_NEW_DEF_TASK);
        String str = (String) this.mOptions.get(UCCore.OPTION_DEX_FILE_PATH);
        if (!j.a(str)) {
            tVar = (t) ((t) ((t) a((t) new bw(), str).setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) str)).setup((String) UCCore.OPTION_SO_FILE_PATH, getOption(UCCore.OPTION_SO_FILE_PATH))).setup((String) UCCore.OPTION_RES_FILE_PATH, getOption(UCCore.OPTION_RES_FILE_PATH));
        } else {
            String str2 = (String) this.mOptions.get(UCCore.OPTION_UCM_ZIP_FILE);
            if (!j.a(str2)) {
                tVar = (t) a((t) new n(), str2).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) str2);
            } else {
                String str3 = (String) this.mOptions.get(UCCore.OPTION_UCM_LIB_DIR);
                if (!j.a(str3)) {
                    Boolean bool = (Boolean) this.mOptions.get(UCCore.OPTION_FORBID_GEN_REPAIR_DIR);
                    if (bool == null || !bool.booleanValue()) {
                        b.push(a((t) new ae(), "RepairSetupTask_".concat(String.valueOf(str3))).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) str3));
                    }
                    tVar = (t) a((t) new bw(), str3).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) str3);
                } else {
                    String str4 = (String) this.mOptions.get(UCCore.OPTION_UCM_KRL_DIR);
                    if (!j.a(str4)) {
                        tVar = (t) a((t) new bw(), str4).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) str4);
                    } else {
                        String str5 = (String) this.mOptions.get(UCCore.OPTION_UCM_CFG_FILE);
                        tVar = !j.a(str5) ? (t) a((t) new bw(), str5).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) str5) : null;
                    }
                }
            }
        }
        if (!j.a((String) getOption(UCCore.OPTION_UCM_UPD_URL))) {
            a((String) getOption(UCCore.OPTION_UCM_UPD_URL), (Callable<Boolean>) null);
            try {
                File file = (File) UCMPackageInfo.invoke(10002, this.e);
                if (file.list().length > 0) {
                    WaStat.stat((String) IWaStat.SHARE_CORE_NEW_UPD_TASK);
                    if (tVar != null) {
                        b.push(tVar);
                    }
                    String absolutePath = file.getAbsolutePath();
                    return (t) ((t) a((t) new bw(), absolutePath).setup((String) "chkDecFinish", (Object) Boolean.TRUE)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) absolutePath);
                }
            } catch (Exception e2) {
                Log.d("SdkSetupTask", "UCMPackageInfo.getUpdateRoot exception: ".concat(String.valueOf(e2)));
            }
        }
        StartupTrace.traceEventEnd("SdkSetupTask.initSpecifiedTask");
        return tVar;
    }

    private t b(t tVar) {
        ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) tVar.invoke(10001, this)).setOptions(this.mOptions)).onEvent((String) "setup", this.j)).onEvent((String) UCCore.OPTION_LOAD_KERNEL_TYPE, this.j)).onEvent((String) "init", this.j)).onEvent((String) FunctionSupportConfiger.SWITCH_TAG, this.j)).onEvent((String) "stat", this.i)).onEvent((String) "success", this.m)).onEvent((String) LogCategory.CATEGORY_EXCEPTION, this.n);
        return tVar;
    }

    private t a(t tVar, String str) {
        ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) ((t) b(tVar).setup((String) UCCore.OPTION_DEX_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_SO_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_RES_FILE_PATH, (Object) null)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) null)).setup((String) UCCore.OPTION_UCM_LIB_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_KRL_DIR, (Object) null)).setup((String) UCCore.OPTION_UCM_CFG_FILE, (Object) null)).onEvent((String) H5PageData.KEY_UC_START, tVar.getSetupCrashImproverInst(str).d)).onEvent((String) LocationParams.PARA_COMMON_DIE, tVar.getSetupCrashImproverInst(str).e)).onEvent((String) "crash_none", (ValueCallback<CALLBACK_TYPE>) null)).onEvent((String) "crash_seen", (ValueCallback<CALLBACK_TYPE>) null)).onEvent((String) "crash_repeat", this.o);
        return tVar;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:0|1|2|3|4|5|(1:8)(2:9|(1:11)(4:12|(1:14)|15|(3:17|(1:20)|21)(2:22|(1:24)(1:(1:26)(2:29|30)))))|27|28) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0088 */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x00cd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r7 = this;
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            r0 = 5
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r0)
            com.uc.webview.export.cyclone.UCElapseTime r0 = new com.uc.webview.export.cyclone.UCElapseTime
            r0.<init>()
            r7.f = r0
            java.lang.String r0 = "ucmZipDir"
            r1 = 0
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r7.setup(r0, r1)
            com.uc.webview.export.utility.SetupTask r0 = (com.uc.webview.export.utility.SetupTask) r0
            java.lang.String r2 = "sdk_setup"
            java.lang.Boolean r3 = java.lang.Boolean.TRUE
            r0.setup(r2, r3)
            r7.setupGlobalOnce()
            java.lang.String r0 = "CONTEXT"
            java.lang.Object r0 = r7.getOption(r0)
            android.content.Context r0 = (android.content.Context) r0
            r7.e = r0
            r0 = 0
            java.lang.Object[] r2 = new java.lang.Object[r0]
            r3 = 10011(0x271b, float:1.4028E-41)
            java.lang.Object r2 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r3, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            r7.k = r2
            r2 = 1
            java.lang.Object[] r3 = new java.lang.Object[r2]
            java.lang.String r4 = "stat"
            r3[r0] = r4
            r4 = 10007(0x2717, float:1.4023E-41)
            java.lang.Object r3 = r7.invokeO(r4, r3)
            android.webkit.ValueCallback r3 = (android.webkit.ValueCallback) r3
            java.lang.String r4 = "stat"
            com.uc.webview.export.internal.setup.ai r5 = new com.uc.webview.export.internal.setup.ai
            r5.<init>(r7, r3)
            r7.onEvent(r4, r5)
            android.util.Pair r3 = new android.util.Pair
            java.lang.String r4 = "sdk_stp"
            r3.<init>(r4, r1)
            r7.callbackStat(r3)
            r3 = 10001(0x2711, float:1.4014E-41)
            com.uc.webview.export.internal.setup.z r4 = new com.uc.webview.export.internal.setup.z     // Catch:{ Throwable -> 0x0088 }
            r4.<init>()     // Catch:{ Throwable -> 0x0088 }
            com.uc.webview.export.cyclone.UCCyclone.statCallback = r4     // Catch:{ Throwable -> 0x0088 }
            com.uc.webview.export.internal.setup.z r4 = (com.uc.webview.export.internal.setup.z) r4     // Catch:{ Throwable -> 0x0088 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0088 }
            com.uc.webview.export.internal.setup.UCAsyncTask r6 = com.uc.webview.export.utility.SetupTask.getRoot()     // Catch:{ Throwable -> 0x0088 }
            r5[r0] = r6     // Catch:{ Throwable -> 0x0088 }
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r4.invoke(r3, r5)     // Catch:{ Throwable -> 0x0088 }
            com.uc.webview.export.internal.setup.z r4 = (com.uc.webview.export.internal.setup.z) r4     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r5 = "stat"
            com.uc.webview.export.internal.setup.UCSubSetupTask$a r6 = new com.uc.webview.export.internal.setup.UCSubSetupTask$a     // Catch:{ Throwable -> 0x0088 }
            r6.<init>()     // Catch:{ Throwable -> 0x0088 }
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r4.onEvent(r5, r6)     // Catch:{ Throwable -> 0x0088 }
            com.uc.webview.export.internal.setup.z r4 = (com.uc.webview.export.internal.setup.z) r4     // Catch:{ Throwable -> 0x0088 }
            r4.start()     // Catch:{ Throwable -> 0x0088 }
        L_0x0088:
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = new com.uc.webview.export.internal.setup.UCAsyncTask     // Catch:{ Throwable -> 0x00a3 }
            com.uc.webview.export.cyclone.UCDex r5 = new com.uc.webview.export.cyclone.UCDex     // Catch:{ Throwable -> 0x00a3 }
            r5.<init>()     // Catch:{ Throwable -> 0x00a3 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00a3 }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x00a3 }
            com.uc.webview.export.internal.setup.UCAsyncTask r5 = com.uc.webview.export.utility.SetupTask.getRoot()     // Catch:{ Throwable -> 0x00a3 }
            r2[r0] = r5     // Catch:{ Throwable -> 0x00a3 }
            com.uc.webview.export.internal.setup.UCAsyncTask r0 = r4.invoke(r3, r2)     // Catch:{ Throwable -> 0x00a3 }
            r2 = 5000(0x1388, double:2.4703E-320)
            r0.start(r2)     // Catch:{ Throwable -> 0x00a3 }
        L_0x00a3:
            com.uc.webview.export.internal.setup.UCAsyncTask$a r0 = new com.uc.webview.export.internal.setup.UCAsyncTask$a
            r0.<init>()
            r7.j = r0
            com.uc.webview.export.internal.setup.UCSubSetupTask$a r0 = new com.uc.webview.export.internal.setup.UCSubSetupTask$a
            r0.<init>()
            r7.i = r0
            com.uc.webview.export.internal.setup.bs r0 = new com.uc.webview.export.internal.setup.bs
            r0.<init>()
            com.uc.webview.export.internal.setup.t r0 = r7.b(r0)
            java.util.concurrent.ConcurrentHashMap r2 = r7.mOptions
            java.lang.String r3 = "SYSTEM_WEBVIEW"
            java.lang.Boolean r2 = com.uc.webview.export.internal.utility.j.a(r2, r3)
            boolean r2 = com.uc.webview.export.internal.utility.j.b(r2)
            if (r2 != 0) goto L_0x00cd
            r0.start()
            goto L_0x0178
        L_0x00cd:
            java.util.Stack<com.uc.webview.export.internal.setup.UCSetupTask> r2 = b
            r2.push(r0)
            boolean r0 = r7.k
            if (r0 == 0) goto L_0x0116
            java.util.concurrent.ConcurrentHashMap r0 = r7.mOptions
            java.lang.String r1 = "soFilePath"
            java.lang.Object r0 = r0.get(r1)
            java.lang.String r0 = (java.lang.String) r0
            java.util.concurrent.ConcurrentHashMap r1 = r7.mOptions
            java.lang.String r2 = "resFilePath"
            java.lang.Object r1 = r1.get(r2)
            java.lang.String r1 = (java.lang.String) r1
            com.uc.webview.export.internal.setup.bu r2 = new com.uc.webview.export.internal.setup.bu
            r2.<init>()
            java.lang.String r3 = "ThickSetupTask_"
            java.lang.String r4 = java.lang.String.valueOf(r0)
            java.lang.String r3 = r3.concat(r4)
            com.uc.webview.export.internal.setup.t r2 = r7.a(r2, r3)
            java.lang.String r3 = "soFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r2.setup(r3, r0)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            java.lang.String r2 = "resFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.setup(r2, r1)
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0
            r0.start()
            java.lang.String r0 = "Thick SDK"
            com.uc.webview.export.internal.SDKFactory.a(r0)
            goto L_0x0178
        L_0x0116:
            com.uc.webview.export.internal.setup.t r0 = r7.d()
            java.lang.String r2 = "sc_ldpl"
            java.lang.String r2 = com.uc.webview.export.extension.UCCore.getParam(r2)
            java.lang.String r3 = "sc_lshco"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x012c
            com.uc.webview.export.internal.setup.t r1 = r7.b()
        L_0x012c:
            java.lang.String r2 = "SdkSetupTask"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "mUpdateTask: "
            r3.<init>(r4)
            com.uc.webview.export.internal.setup.t r4 = r7.c
            r3.append(r4)
            java.lang.String r4 = " shareCoreTask: "
            r3.append(r4)
            r3.append(r1)
            java.lang.String r3 = r3.toString()
            com.uc.webview.export.internal.utility.Log.d(r2, r3)
            if (r0 == 0) goto L_0x015d
            com.uc.webview.export.internal.setup.t r2 = r7.c
            if (r2 == 0) goto L_0x0159
            if (r1 == 0) goto L_0x0159
            r7.a(r1)
            java.util.Stack<com.uc.webview.export.internal.setup.UCSetupTask> r2 = b
            r2.push(r1)
        L_0x0159:
            r0.start()
            goto L_0x0178
        L_0x015d:
            com.uc.webview.export.internal.setup.t r0 = r7.c
            if (r0 == 0) goto L_0x0170
            com.uc.webview.export.internal.setup.bw r0 = new com.uc.webview.export.internal.setup.bw
            r0.<init>()
            java.lang.String r1 = ""
            com.uc.webview.export.internal.setup.t r0 = r7.a(r0, r1)
            r0.start()
            goto L_0x0178
        L_0x0170:
            if (r1 == 0) goto L_0x0182
            r7.a(r1)
            r1.start()
        L_0x0178:
            r0 = 6
            com.uc.webview.export.internal.uc.startup.StartupStats.a(r0)
            java.lang.String r0 = "SdkSetupTask.run"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r0)
            return
        L_0x0182:
            com.uc.webview.export.internal.setup.UCSetupException r0 = new com.uc.webview.export.internal.setup.UCSetupException
            r1 = 3017(0xbc9, float:4.228E-42)
            java.lang.String r2 = "At least 1 of OPTION_DEX_FILE_PATH|OPTION_UCM_LIB_DIR|OPTION_UCM_KRL_DIR|OPTION_UCM_CFG_FILE|OPTION_UCM_UPD_URL and CD_KEY_SHARE_CORE_CLIENT_LOAD_POLICY should be given."
            r0.<init>(r1, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.af.run():void");
    }
}
