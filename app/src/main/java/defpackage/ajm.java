package defpackage;

import android.app.Application;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.provider.H5UcInitProvider;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.uc.webview.export.Build;
import com.uc.webview.export.Build.Version;
import com.uc.webview.export.WebView;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.extension.UCSettings;

/* renamed from: ajm reason: default package */
/* compiled from: UCInitializer */
public class ajm {
    private static final boolean a = bno.a;
    private static volatile boolean b = false;
    private static volatile boolean c = false;
    /* access modifiers changed from: private */
    public static volatile boolean d = false;

    /* renamed from: ajm$a */
    /* compiled from: UCInitializer */
    public static class a extends Handler {
        private static volatile a a;

        public static a a() {
            if (a == null) {
                synchronized (a.class) {
                    if (a == null) {
                        a = new a();
                    }
                }
            }
            return a;
        }

        private a() {
            super(Looper.getMainLooper());
        }
    }

    public static boolean a() {
        return a(-20);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x01e2 A[Catch:{ Throwable -> 0x01d8 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(int r10) {
        /*
            boolean r0 = b
            if (r0 != 0) goto L_0x0200
            java.lang.Class<ajm> r0 = defpackage.ajm.class
            monitor-enter(r0)
            boolean r1 = b     // Catch:{ all -> 0x01fd }
            if (r1 != 0) goto L_0x01fb
            r1 = 1
            b = r1     // Catch:{ all -> 0x01fd }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01fd }
            boolean r4 = a     // Catch:{ all -> 0x01fd }
            com.uc.webview.export.extension.UCCore.setPrintLog(r4)     // Catch:{ all -> 0x01fd }
            r4 = 0
            defpackage.ajl.a()     // Catch:{ Throwable -> 0x01d8 }
            com.amap.bundle.mapstorage.MapSharePreference r5 = new com.amap.bundle.mapstorage.MapSharePreference     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "uc_options"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "OPTION_USE_SYSTEM_WEBVIEW"
            int r7 = android.os.Build.VERSION.SDK_INT     // Catch:{ Throwable -> 0x01d8 }
            r8 = 19
            if (r7 >= r8) goto L_0x002c
            r7 = 1
            goto L_0x002d
        L_0x002c:
            r7 = 0
        L_0x002d:
            boolean r5 = r5.getBooleanValue(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "CONTEXT"
            android.app.Application r7 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r6 = com.uc.webview.export.extension.UCCore.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r7 = "MULTI_CORE_TYPE"
            java.lang.Boolean r8 = java.lang.Boolean.TRUE     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r6 = r6.setup(r7, r8)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r6 = (com.uc.webview.export.utility.SetupTask) r6     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r7 = "SYSTEM_WEBVIEW"
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r6.setup(r7, r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "loadPolicy"
            java.lang.String r7 = "SPECIFIED_ONLY"
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "AC"
            java.lang.Boolean r7 = java.lang.Boolean.TRUE     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "log_conf"
            boolean r7 = a     // Catch:{ Throwable -> 0x01d8 }
            r8 = 5
            java.lang.Object[] r8 = new java.lang.Object[r8]     // Catch:{ Throwable -> 0x01d8 }
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r7)     // Catch:{ Throwable -> 0x01d8 }
            r8[r4] = r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.Boolean r7 = java.lang.Boolean.FALSE     // Catch:{ Throwable -> 0x01d8 }
            r8[r1] = r7     // Catch:{ Throwable -> 0x01d8 }
            r7 = 2
            com.amap.bundle.webview.uc.UCInitializer$13 r9 = new com.amap.bundle.webview.uc.UCInitializer$13     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>()     // Catch:{ Throwable -> 0x01d8 }
            r8[r7] = r9     // Catch:{ Throwable -> 0x01d8 }
            r7 = 3
            java.lang.String r9 = "[all]"
            r8[r7] = r9     // Catch:{ Throwable -> 0x01d8 }
            r7 = 4
            java.lang.String r9 = "[all]"
            r8[r7] = r9     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r8)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "provided_keys"
            java.lang.String r7 = g()     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "VIDEO_AC"
            java.lang.Boolean r7 = java.lang.Boolean.FALSE     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "VERIFY_POLICY"
            r7 = 31
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "WEBVIEW_POLICY"
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "delete_core"
            r7 = 63
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "ucmZipFile"
            boolean r7 = defpackage.bno.a     // Catch:{ Throwable -> 0x01d8 }
            if (r7 == 0) goto L_0x00fb
            java.io.File r7 = new java.io.File     // Catch:{ Throwable -> 0x01d8 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d8 }
            r8.<init>()     // Catch:{ Throwable -> 0x01d8 }
            java.io.File r9 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r9 = r9.getAbsolutePath()     // Catch:{ Throwable -> 0x01d8 }
            r8.append(r9)     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r9 = "/amap/libkernelu4_ri_7z_uc.so"
            r8.append(r9)     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x01d8 }
            r7.<init>(r8)     // Catch:{ Throwable -> 0x01d8 }
            boolean r8 = r7.exists()     // Catch:{ Throwable -> 0x01d8 }
            if (r8 == 0) goto L_0x00fb
            java.lang.String r7 = r7.getAbsolutePath()     // Catch:{ Throwable -> 0x01d8 }
            goto L_0x0116
        L_0x00fb:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01d8 }
            r7.<init>()     // Catch:{ Throwable -> 0x01d8 }
            android.app.Application r8 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Throwable -> 0x01d8 }
            android.content.pm.ApplicationInfo r8 = r8.getApplicationInfo()     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = r8.nativeLibraryDir     // Catch:{ Throwable -> 0x01d8 }
            r7.append(r8)     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "/libkernelu4_7z_uc.so"
            r7.append(r8)     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x01d8 }
        L_0x0116:
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "init_setup_thread"
            java.lang.Boolean r7 = java.lang.Boolean.TRUE     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.setup(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "setup_priority"
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r10 = r5.setup(r6, r10)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r10 = (com.uc.webview.export.utility.SetupTask) r10     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r5 = "ucPlayer"
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r10 = r10.setup(r5, r6)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r10 = (com.uc.webview.export.utility.SetupTask) r10     // Catch:{ Throwable -> 0x01d8 }
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r7 = "success"
            com.amap.bundle.webview.uc.UCInitializer$11 r8 = new com.amap.bundle.webview.uc.UCInitializer$11     // Catch:{ Throwable -> 0x01d8 }
            r8.<init>(r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r7 = r10.onEvent(r7, r8)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r7 = (com.uc.webview.export.utility.SetupTask) r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "setup"
            com.amap.bundle.webview.uc.UCInitializer$10 r9 = new com.amap.bundle.webview.uc.UCInitializer$10     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>(r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r7 = r7.onEvent(r8, r9)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r7 = (com.uc.webview.export.utility.SetupTask) r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "load"
            com.amap.bundle.webview.uc.UCInitializer$9 r9 = new com.amap.bundle.webview.uc.UCInitializer$9     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>(r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r7 = r7.onEvent(r8, r9)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r7 = (com.uc.webview.export.utility.SetupTask) r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "init"
            com.amap.bundle.webview.uc.UCInitializer$8 r9 = new com.amap.bundle.webview.uc.UCInitializer$8     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>(r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r7 = r7.onEvent(r8, r9)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r7 = (com.uc.webview.export.utility.SetupTask) r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "switch"
            com.amap.bundle.webview.uc.UCInitializer$7 r9 = new com.amap.bundle.webview.uc.UCInitializer$7     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>()     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r7 = r7.onEvent(r8, r9)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r7 = (com.uc.webview.export.utility.SetupTask) r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "pause"
            com.amap.bundle.webview.uc.UCInitializer$6 r9 = new com.amap.bundle.webview.uc.UCInitializer$6     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>(r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r7 = r7.onEvent(r8, r9)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r7 = (com.uc.webview.export.utility.SetupTask) r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "resume"
            com.amap.bundle.webview.uc.UCInitializer$5 r9 = new com.amap.bundle.webview.uc.UCInitializer$5     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>(r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r7 = r7.onEvent(r8, r9)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r7 = (com.uc.webview.export.utility.SetupTask) r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "exception"
            com.amap.bundle.webview.uc.UCInitializer$4 r9 = new com.amap.bundle.webview.uc.UCInitializer$4     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>()     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r7 = r7.onEvent(r8, r9)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r7 = (com.uc.webview.export.utility.SetupTask) r7     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r8 = "initStarted"
            com.amap.bundle.webview.uc.UCInitializer$3 r9 = new com.amap.bundle.webview.uc.UCInitializer$3     // Catch:{ Throwable -> 0x01d8 }
            r9.<init>(r5)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r7.onEvent(r8, r9)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "updateProgress"
            com.amap.bundle.webview.uc.UCInitializer$2 r7 = new com.amap.bundle.webview.uc.UCInitializer$2     // Catch:{ Throwable -> 0x01d8 }
            r7.<init>()     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.onEvent(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            java.lang.String r6 = "downloadException"
            com.amap.bundle.webview.uc.UCInitializer$1 r7 = new com.amap.bundle.webview.uc.UCInitializer$1     // Catch:{ Throwable -> 0x01d8 }
            r7.<init>()     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.internal.setup.BaseSetupTask r5 = r5.onEvent(r6, r7)     // Catch:{ Throwable -> 0x01d8 }
            com.uc.webview.export.utility.SetupTask r5 = (com.uc.webview.export.utility.SetupTask) r5     // Catch:{ Throwable -> 0x01d8 }
            r5.setAsDefault()     // Catch:{ Throwable -> 0x01d8 }
            r10.start()     // Catch:{ Throwable -> 0x01d8 }
            c = r1     // Catch:{ Throwable -> 0x01d8 }
            goto L_0x01de
        L_0x01d8:
            r10 = move-exception
            c = r4     // Catch:{ all -> 0x01fd }
            r10.printStackTrace()     // Catch:{ all -> 0x01fd }
        L_0x01de:
            boolean r10 = defpackage.bno.a     // Catch:{ all -> 0x01fd }
            if (r10 == 0) goto L_0x01fb
            java.lang.String r10 = "UCCore.init"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01fd }
            java.lang.String r4 = "init uc sdk sync:"
            r1.<init>(r4)     // Catch:{ all -> 0x01fd }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01fd }
            r6 = 0
            long r4 = r4 - r2
            r1.append(r4)     // Catch:{ all -> 0x01fd }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01fd }
            com.amap.bundle.logs.AMapLog.d(r10, r1)     // Catch:{ all -> 0x01fd }
        L_0x01fb:
            monitor-exit(r0)     // Catch:{ all -> 0x01fd }
            goto L_0x0200
        L_0x01fd:
            r10 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x01fd }
            throw r10
        L_0x0200:
            boolean r10 = c
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ajm.a(int):boolean");
    }

    public static void b() {
        long currentTimeMillis = System.currentTimeMillis();
        H5Service h5Service = (H5Service) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(H5Service.class.getName());
        if (h5Service != null) {
            h5Service.getProviderManager().setProvider(H5UcInitProvider.class.getName(), new ajk());
        }
        StringBuilder sb = new StringBuilder("inject nebula init provider:");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        AMapLog.d("UCCore.init", sb.toString());
    }

    public static boolean c() {
        return d;
    }

    private static String g() {
        Application application = AMapAppGlobal.getApplication();
        try {
            return application.getPackageManager().getApplicationInfo(application.getPackageName(), 128).metaData.getString("UCSDKAppKey");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static /* synthetic */ void e() {
        AMapLog.d("UCCore.init", "setup global settings!");
        UCCore.setThirdNetwork(null, null);
        UCCore.setNetLogger(null);
        UCCore.setStatDataCallback(null);
        UCCore.setGlobalOption(UCCore.ADAPTER_BUILD_VERSOPM, defpackage.ahp.a.a().a);
        UCSettings.setEnableUCVideoViewFullscreen(true);
        UCSettings.setEnableCustomErrorPage(true);
        UCSettings.setEnableAdblock(false);
        UCSettings.setGlobalEnableUCProxy(false);
        UCSettings.setEnableDispatcher(false);
        UCSettings.setForceUserScalable(2);
        UCSettings.setEnableAllResourceCallBack(true);
        UCSettings.setEnableMediaCache(true);
        UCSettings.setGlobalBoolValue("OPEN_CACHE_LOG", a);
        UCSettings.setGlobalBoolValue("DISABLE_PREFER_CACHE", true);
        UCSettings.setPageCacheCapacity(0);
        a.a().post(new Runnable() {
            public final void run() {
                if (bno.a) {
                    WebView.setWebContentsDebuggingEnabled(true);
                    return;
                }
                ajl.a();
                boolean b = ajl.b();
                if (b) {
                    WebView.setWebContentsDebuggingEnabled(b);
                }
            }
        });
    }

    public static /* synthetic */ void f() {
        bmp.a((String) "UC.Build.TIME", Build.TIME);
        bmp.a((String) "UC.Build.Version.NAME", Version.NAME);
    }
}
