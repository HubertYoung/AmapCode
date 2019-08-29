package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.webkit.ValueCallback;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.location.sdk.fusion.LocationParams;
import com.uc.webview.export.annotations.Reflection;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCSetupTask;
import com.uc.webview.export.internal.uc.startup.StartupTrace;
import com.uc.webview.export.internal.utility.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ProGuard */
public abstract class UCSetupTask<RETURN_TYPE extends UCSetupTask<RETURN_TYPE, CALLBACK_TYPE>, CALLBACK_TYPE extends UCSetupTask<RETURN_TYPE, CALLBACK_TYPE>> extends BaseSetupTask<RETURN_TYPE, CALLBACK_TYPE> {
    private static UCMRunningInfo b = null;
    private static UCSetupTask d = null;
    private static UCAsyncTask e = null;
    private static int f = 0;
    private static boolean g = false;
    protected static final List<UCSetupTask> sTotalSetupTasks = new ArrayList(2);
    private UCMRunningInfo a;
    private UCMRepairInfo c;
    /* access modifiers changed from: private */
    public String h = "";
    private a i;
    /* access modifiers changed from: private */
    public boolean j;

    /* compiled from: ProGuard */
    public class a {
        File a;
        File b;
        File c;
        public final ValueCallback<CALLBACK_TYPE> d = new cl(this);
        public final ValueCallback<CALLBACK_TYPE> e = new cm(this);

        /* JADX WARNING: Can't wrap try/catch for region: R(4:6|7|8|9) */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0063, code lost:
            if (r1 != false) goto L_0x0067;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x003a */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0069  */
        /* JADX WARNING: Removed duplicated region for block: B:26:0x008c  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x008f  */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x0098  */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x009b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static /* synthetic */ void a(com.uc.webview.export.internal.setup.UCSetupTask.a r5) {
            /*
                java.io.File r0 = r5.a
                boolean r0 = r0.exists()
                java.io.File r1 = r5.c
                boolean r1 = r1.exists()
                java.io.File r2 = r5.b
                boolean r2 = r2.exists()
                r3 = 1
                if (r2 == 0) goto L_0x0061
                if (r0 == 0) goto L_0x0067
                if (r1 == 0) goto L_0x0067
                java.io.File r1 = r5.b
                long r1 = r1.lastModified()
                java.io.File r3 = r5.c
                long r3 = r3.lastModified()
                long r1 = java.lang.Math.max(r1, r3)
                long r3 = java.lang.System.currentTimeMillis()
                long r3 = r3 - r1
                r1 = 86400000(0x5265c00, double:4.2687272E-316)
                int r1 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
                if (r1 <= 0) goto L_0x0040
                java.io.File r1 = r5.b     // Catch:{ Throwable -> 0x003a }
                r1.delete()     // Catch:{ Throwable -> 0x003a }
            L_0x003a:
                java.io.File r1 = r5.a     // Catch:{ Throwable -> 0x0066 }
                r1.delete()     // Catch:{ Throwable -> 0x0066 }
                goto L_0x0066
            L_0x0040:
                com.uc.webview.export.internal.setup.UCSetupTask r0 = com.uc.webview.export.internal.setup.UCSetupTask.this
                java.lang.String r1 = "2"
                r0.h = r1
                com.uc.webview.export.internal.setup.UCSetupTask r0 = com.uc.webview.export.internal.setup.UCSetupTask.this
                java.lang.String r1 = "disable_multi_unknown_crash"
                java.lang.Object r0 = r0.getOption(r1)
                java.lang.Boolean r0 = (java.lang.Boolean) r0
                if (r0 == 0) goto L_0x0059
                boolean r0 = r0.booleanValue()
                if (r0 != 0) goto L_0x0060
            L_0x0059:
                com.uc.webview.export.internal.setup.UCSetupTask r5 = com.uc.webview.export.internal.setup.UCSetupTask.this
                java.lang.String r0 = "crash_repeat"
                r5.callback(r0)
            L_0x0060:
                return
            L_0x0061:
                if (r0 == 0) goto L_0x0066
                if (r1 == 0) goto L_0x0066
                goto L_0x0067
            L_0x0066:
                r3 = 0
            L_0x0067:
                if (r0 == 0) goto L_0x0088
                com.uc.webview.export.internal.setup.UCSetupTask r0 = com.uc.webview.export.internal.setup.UCSetupTask.this
                java.util.concurrent.ConcurrentHashMap r0 = r0.mOptions
                java.lang.String r1 = "VERIFY_POLICY"
                java.lang.Object r0 = r0.get(r1)
                java.lang.Integer r0 = (java.lang.Integer) r0
                if (r0 == 0) goto L_0x0088
                com.uc.webview.export.internal.setup.UCSetupTask r1 = com.uc.webview.export.internal.setup.UCSetupTask.this
                java.lang.String r2 = "VERIFY_POLICY"
                int r0 = r0.intValue()
                r0 = r0 | 16
                java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
                r1.setup(r2, r0)
            L_0x0088:
                com.uc.webview.export.internal.setup.UCSetupTask r0 = com.uc.webview.export.internal.setup.UCSetupTask.this
                if (r3 == 0) goto L_0x008f
                java.lang.String r1 = "1"
                goto L_0x0091
            L_0x008f:
                java.lang.String r1 = "0"
            L_0x0091:
                r0.h = r1
                com.uc.webview.export.internal.setup.UCSetupTask r5 = com.uc.webview.export.internal.setup.UCSetupTask.this
                if (r3 == 0) goto L_0x009b
                java.lang.String r0 = "crash_seen"
                goto L_0x009d
            L_0x009b:
                java.lang.String r0 = "crash_none"
            L_0x009d:
                r5.callback(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.UCSetupTask.a.a(com.uc.webview.export.internal.setup.UCSetupTask$a):void");
        }

        a(String str) {
            if (this.a == null) {
                File file = (File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, (File) UCMPackageInfo.invoke(10005, (Context) UCSetupTask.this.mOptions.get(UCCore.OPTION_CONTEXT)), (String) UCMPackageInfo.invoke(10012, str));
                StringBuilder sb = new StringBuilder("SetupCrashImprover<init> UCSetupt.class: ");
                sb.append(UCSetupTask.this.getClass());
                Log.d("UCSetupTask", sb.toString());
                StringBuilder sb2 = new StringBuilder("SetupCrashImprover<init> flgDirFile.path: ");
                sb2.append(file.getAbsolutePath());
                Log.d("UCSetupTask", sb2.toString());
                this.a = new File(file, "b36ce8d879e33bc88f717f74617ea05a");
                this.b = new File(file, "bd89426940609c9ae14e5ae90827201b");
                this.c = new File(file, "51bfcd9dd2f1379936c4fbb3558a6e67");
            }
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:2:0x0005 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a() {
            /*
                r1 = this;
                java.io.File r0 = r1.a     // Catch:{ Throwable -> 0x0005 }
                r0.delete()     // Catch:{ Throwable -> 0x0005 }
            L_0x0005:
                java.io.File r0 = r1.c     // Catch:{ Throwable -> 0x000b }
                r0.delete()     // Catch:{ Throwable -> 0x000b }
                return
            L_0x000b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.UCSetupTask.a.a():void");
        }
    }

    /* access modifiers changed from: protected */
    public a getSetupCrashImproverInst(String str) {
        if (this.i == null) {
            this.i = new a(str);
        }
        return this.i;
    }

    /* access modifiers changed from: protected */
    public void resetCrashFlag() {
        if (this.i != null) {
            this.i.a();
        }
    }

    /* access modifiers changed from: protected */
    public final String getCrashCode() {
        return this.h;
    }

    public static synchronized UCAsyncTask getRoot() {
        UCAsyncTask uCAsyncTask;
        synchronized (UCSetupTask.class) {
            try {
                if (e == null) {
                    e = new cj(Integer.valueOf(f)).onEvent(H5PageData.KEY_UC_START, new ci()).onEvent(LocationParams.PARA_COMMON_DIE, new ch());
                }
                uCAsyncTask = e;
            }
        }
        return uCAsyncTask;
    }

    /* access modifiers changed from: protected */
    public final void setTotalLoadedUCM(UCMRunningInfo uCMRunningInfo) {
        b = uCMRunningInfo;
    }

    public static UCMRunningInfo getTotalLoadedUCM() {
        return b;
    }

    @Reflection
    public static Class<?> classForName(String str) throws ClassNotFoundException {
        ClassLoader classLoader;
        UCMRunningInfo totalLoadedUCM = getTotalLoadedUCM();
        if (totalLoadedUCM == null) {
            classLoader = null;
        } else {
            classLoader = totalLoadedUCM.classLoader;
        }
        if (classLoader == null) {
            return Class.forName(str);
        }
        return Class.forName(str, true, classLoader);
    }

    public static boolean isSetupThread() {
        return ((Boolean) getRoot().invokeO(UCAsyncTask.inThread, new Object[0])).booleanValue();
    }

    public static void resumeAll() {
        synchronized (sTotalSetupTasks) {
            for (int i2 = 0; i2 < sTotalSetupTasks.size(); i2++) {
                sTotalSetupTasks.get(i2).resume();
            }
        }
    }

    public UCSetupTask() {
        synchronized (sTotalSetupTasks) {
            sTotalSetupTasks.add(this);
        }
    }

    public synchronized RETURN_TYPE start() {
        StartupTrace.traceEvent("UCSetupTask.start");
        if (invokeO(10005, new Object[0]) == null) {
            Integer num = (Integer) this.mOptions.get(UCCore.OPTION_SETUP_THREAD_PRIORITY);
            UCLogger create = UCLogger.create("d", "UCSetupTask");
            if (create != null) {
                create.print("start: setup_priority=".concat(String.valueOf(num)), new Throwable[0]);
            }
            if (num != null) {
                setRootTaskPriority(num.intValue());
            }
            UCAsyncTask root = getRoot();
            invoke(10001, root);
            RETURN_TYPE return_type = (UCSetupTask) super.start();
            root.start();
            return return_type;
        }
        return (UCSetupTask) super.start();
    }

    /* access modifiers changed from: private */
    public void a(boolean z, boolean z2) {
        StringBuilder sb = new StringBuilder("setPringLogBaseOnConfig overrideConfig: ");
        sb.append(z);
        sb.append(" enable: ");
        sb.append(z2);
        Log.d("UCSetupTask", sb.toString());
        Object[] objArr = (Object[]) this.mOptions.get(UCCore.OPTION_LOG_CONFIG);
        if (objArr != null || z) {
            if (objArr == null) {
                objArr = new Object[]{Boolean.valueOf(z2), Boolean.TRUE, null, "[all]", "[all]"};
            }
            if (z) {
                objArr[0] = Boolean.valueOf(z2);
            }
            if (objArr.length == 5) {
                SDKFactory.invoke(10048, objArr[0], objArr);
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0307  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0318  */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0326  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x0337  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0349  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x035a  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x036a  */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x037b  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x038b  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x039c  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x03af  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x03c0  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x03d2  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x03e3  */
    /* JADX WARNING: Removed duplicated region for block: B:141:0x03f4  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0405  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0419  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x042a  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x043c  */
    /* JADX WARNING: Removed duplicated region for block: B:153:0x044d  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x045d  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x046e  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x017c  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x01a1  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x023f  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0250  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0260  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x0271  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0281  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0292  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x02a2  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x02b3  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x02c3  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x02d4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setupGlobalOnce() {
        /*
            r13 = this;
            java.lang.String r0 = "UCSetupTask"
            java.lang.String r1 = "setupGlobalOnce"
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            boolean r0 = g
            if (r0 == 0) goto L_0x000c
            return
        L_0x000c:
            r0 = 1
            g = r0
            com.uc.webview.export.internal.uc.startup.StartupTrace.a()
            java.util.concurrent.ConcurrentHashMap r1 = r13.mOptions
            java.lang.String r2 = "CONTEXT"
            java.lang.Object r1 = r1.get(r2)
            android.content.Context r1 = (android.content.Context) r1
            android.content.Context r1 = r1.getApplicationContext()
            java.lang.String r2 = "CONTEXT"
            r13.setup(r2, r1)
            java.lang.String r2 = "PRIVATE_DATA_DIRECTORY_SUFFIX"
            java.lang.Object r2 = r13.getOption(r2)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.String r3 = "process_private_data_dir_suffix"
            boolean r4 = com.uc.webview.export.internal.utility.j.a(r2)
            if (r4 == 0) goto L_0x0037
            java.lang.String r2 = "0"
        L_0x0037:
            com.uc.webview.export.extension.UCCore.setGlobalOption(r3, r2)
            r8 = 0
            java.lang.Object[] r2 = new java.lang.Object[r8]
            r9 = 10064(0x2750, float:1.4103E-41)
            java.lang.Object r2 = com.uc.webview.export.internal.SDKFactory.invoke(r9, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x005c
            java.lang.String r2 = "com.uc.webview.export.cd.Utils"
            java.lang.String r3 = "initializeCDPreferences"
            java.lang.Class[] r4 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x005c }
            java.lang.Class<android.content.Context> r5 = android.content.Context.class
            r4[r8] = r5     // Catch:{ Exception -> 0x005c }
            java.lang.Object[] r5 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x005c }
            r5[r8] = r1     // Catch:{ Exception -> 0x005c }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r2, r3, r4, r5)     // Catch:{ Exception -> 0x005c }
        L_0x005c:
            boolean r2 = com.uc.webview.export.internal.utility.Log.sPrintLog
            r10 = 0
            r11 = 2
            if (r2 != 0) goto L_0x00ed
            java.io.File r12 = new java.io.File     // Catch:{ Throwable -> 0x00ed }
            java.io.File r2 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Throwable -> 0x00ed }
            java.io.File r2 = r2.getAbsoluteFile()     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r3 = "uclogconfig.apk"
            r12.<init>(r2, r3)     // Catch:{ Throwable -> 0x00ed }
            boolean r2 = r12.exists()     // Catch:{ Throwable -> 0x00ed }
            if (r2 == 0) goto L_0x00ed
            boolean r2 = r12.isFile()     // Catch:{ Throwable -> 0x00ed }
            if (r2 == 0) goto L_0x00ed
            long r2 = r12.length()     // Catch:{ Throwable -> 0x00ed }
            r4 = 3000(0xbb8, double:1.482E-320)
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 > 0) goto L_0x00ed
            java.lang.String r2 = r12.getAbsolutePath()     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r5 = "com.UCMobile"
            r6 = 0
            r7 = 0
            r3 = r1
            r4 = r1
            boolean r2 = com.uc.webview.export.internal.utility.i.a(r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x00ed }
            if (r2 == 0) goto L_0x00ed
            com.uc.webview.export.cyclone.UCLoader r2 = new com.uc.webview.export.cyclone.UCLoader     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r3 = r12.getAbsolutePath()     // Catch:{ Throwable -> 0x00ed }
            java.io.File r4 = r1.getCacheDir()     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r4 = r4.getAbsolutePath()     // Catch:{ Throwable -> 0x00ed }
            java.lang.Class<com.uc.webview.export.internal.setup.UCSetupTask> r5 = com.uc.webview.export.internal.setup.UCSetupTask.class
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch:{ Throwable -> 0x00ed }
            r2.<init>(r3, r4, r10, r5)     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r3 = "com.uc.webview.uclogconfig.UCSDKLogConfig"
            java.lang.Class r2 = r2.loadClass(r3)     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r3 = "getEnabledDate"
            java.lang.Object r2 = com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r2, r3, r10, r10)     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x00ed }
            java.text.SimpleDateFormat r3 = new java.text.SimpleDateFormat     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r4 = "yyyy-MM-dd"
            java.util.Locale r5 = java.util.Locale.CHINA     // Catch:{ Throwable -> 0x00ed }
            r3.<init>(r4, r5)     // Catch:{ Throwable -> 0x00ed }
            java.util.Date r4 = new java.util.Date     // Catch:{ Throwable -> 0x00ed }
            r4.<init>()     // Catch:{ Throwable -> 0x00ed }
            java.lang.String r3 = r3.format(r4)     // Catch:{ Throwable -> 0x00ed }
            if (r2 == 0) goto L_0x00ed
            int r4 = r2.length()     // Catch:{ Throwable -> 0x00ed }
            r5 = 8
            if (r4 < r5) goto L_0x00ed
            if (r3 == 0) goto L_0x00ed
            int r4 = r3.length()     // Catch:{ Throwable -> 0x00ed }
            if (r4 < r5) goto L_0x00ed
            boolean r2 = r2.startsWith(r3)     // Catch:{ Throwable -> 0x00ed }
            if (r2 == 0) goto L_0x00ed
            com.uc.webview.export.extension.UCCore.setPrintLog(r0)     // Catch:{ Throwable -> 0x00ed }
            goto L_0x0168
        L_0x00ed:
            r13.j = r8
            java.lang.Object[] r2 = new java.lang.Object[r8]
            java.lang.Object r2 = com.uc.webview.export.internal.SDKFactory.invoke(r9, r2)
            java.lang.Boolean r2 = (java.lang.Boolean) r2
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x0159
            java.lang.String r2 = "com.uc.webview.export.cd.Utils"
            java.lang.String r3 = "createBusinessTemplate"
            r4 = 4
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ Exception -> 0x0159 }
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r8] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r0] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.Class<java.lang.Object> r6 = java.lang.Object.class
            r5[r11] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.Class<android.webkit.ValueCallback> r6 = android.webkit.ValueCallback.class
            r7 = 3
            r5[r7] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0159 }
            java.lang.String r6 = "print_log"
            r4[r8] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.String r6 = "true"
            r4[r0] = r6     // Catch:{ Exception -> 0x0159 }
            r4[r11] = r10     // Catch:{ Exception -> 0x0159 }
            com.uc.webview.export.internal.setup.ck r6 = new com.uc.webview.export.internal.setup.ck     // Catch:{ Exception -> 0x0159 }
            r6.<init>(r13)     // Catch:{ Exception -> 0x0159 }
            r4[r7] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.Object r2 = com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r2, r3, r5, r4)     // Catch:{ Exception -> 0x0159 }
            java.lang.String r3 = "com.uc.webview.export.cd.Utils"
            java.lang.String r4 = "addKeyListener"
            java.lang.Class[] r5 = new java.lang.Class[r11]     // Catch:{ Exception -> 0x0159 }
            java.lang.Class<java.lang.String> r6 = java.lang.String.class
            r5[r8] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.Class<android.webkit.ValueCallback> r6 = android.webkit.ValueCallback.class
            r5[r0] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.Object[] r6 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0159 }
            java.lang.String r7 = "print_log"
            r6[r8] = r7     // Catch:{ Exception -> 0x0159 }
            r6[r0] = r2     // Catch:{ Exception -> 0x0159 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r3, r4, r5, r6)     // Catch:{ Exception -> 0x0159 }
            java.lang.String r3 = "com.uc.webview.export.cd.Utils"
            java.lang.String r4 = "setupBusinessMathod"
            java.lang.Class[] r5 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x0159 }
            java.lang.Class r6 = r2.getClass()     // Catch:{ Exception -> 0x0159 }
            r5[r8] = r6     // Catch:{ Exception -> 0x0159 }
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0159 }
            r6[r8] = r2     // Catch:{ Exception -> 0x0159 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r3, r4, r5, r6)     // Catch:{ Exception -> 0x0159 }
        L_0x0159:
            boolean r2 = r13.j
            if (r2 != 0) goto L_0x0164
            boolean r2 = com.uc.webview.export.internal.utility.Log.sPrintLog
            if (r2 != 0) goto L_0x0164
            r13.a(r8, r0)
        L_0x0164:
            boolean r2 = com.uc.webview.export.internal.utility.Log.sPrintLog
            com.uc.webview.export.cyclone.UCCyclone.enableDebugLog = r2
        L_0x0168:
            java.lang.String r2 = "d"
            java.lang.String r3 = "UCSetupTask"
            com.uc.webview.export.cyclone.UCLogger r2 = com.uc.webview.export.cyclone.UCLogger.create(r2, r3)
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "log_conf"
            java.lang.Object r3 = r3.get(r4)
            java.lang.Object[] r3 = (java.lang.Object[]) r3
            if (r2 == 0) goto L_0x0193
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "setupGlobalOnce: log_conf="
            r4.<init>(r5)
            java.lang.String r3 = java.util.Arrays.toString(r3)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            java.lang.Throwable[] r4 = new java.lang.Throwable[r8]
            r2.print(r3, r4)
        L_0x0193:
            java.lang.Object[] r3 = new java.lang.Object[r8]
            java.lang.Object r3 = com.uc.webview.export.internal.SDKFactory.invoke(r9, r3)
            java.lang.Boolean r3 = (java.lang.Boolean) r3
            boolean r3 = r3.booleanValue()
            if (r3 == 0) goto L_0x022a
            java.lang.String r3 = "UCSetupTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "new_url setupNewUrl　OPTION_UCM_UPD_URL: "
            r4.<init>(r5)
            java.util.concurrent.ConcurrentHashMap r5 = r13.mOptions
            java.lang.String r6 = "ucmUpdUrl"
            java.lang.Object r5 = r5.get(r6)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.uc.webview.export.internal.utility.Log.d(r3, r4)
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "ucmUpdUrl"
            java.lang.Object r3 = r3.get(r4)
            if (r3 == 0) goto L_0x01e4
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "ucmUpdUrl"
            java.lang.Object r3 = r3.get(r4)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "com.uc.webview.export.cd.Utils"
            java.lang.String r5 = "setupBusinessTemplateForUrl"
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x01e4 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r6[r8] = r7     // Catch:{ Exception -> 0x01e4 }
            java.lang.Object[] r7 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x01e4 }
            r7[r8] = r3     // Catch:{ Exception -> 0x01e4 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r4, r5, r6, r7)     // Catch:{ Exception -> 0x01e4 }
        L_0x01e4:
            java.lang.String r3 = "com.uc.webview.export.cd.Utils"
            java.lang.String r4 = "setupSetupTask"
            java.lang.Class[] r5 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x01f5 }
            java.lang.Class<com.uc.webview.export.internal.setup.UCSetupTask> r6 = com.uc.webview.export.internal.setup.UCSetupTask.class
            r5[r8] = r6     // Catch:{ Exception -> 0x01f5 }
            java.lang.Object[] r6 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x01f5 }
            r6[r8] = r13     // Catch:{ Exception -> 0x01f5 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r3, r4, r5, r6)     // Catch:{ Exception -> 0x01f5 }
        L_0x01f5:
            java.lang.String r3 = "com.uc.webview.export.cd.Utils"
            java.lang.String r4 = "setupVMSizeSavingSample"
            java.lang.Class[] r5 = new java.lang.Class[r11]     // Catch:{ Exception -> 0x020c }
            java.lang.Class<com.uc.webview.export.internal.setup.UCSetupTask> r6 = com.uc.webview.export.internal.setup.UCSetupTask.class
            r5[r8] = r6     // Catch:{ Exception -> 0x020c }
            java.lang.Class<com.uc.webview.export.cyclone.UCLogger> r6 = com.uc.webview.export.cyclone.UCLogger.class
            r5[r0] = r6     // Catch:{ Exception -> 0x020c }
            java.lang.Object[] r6 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x020c }
            r6[r8] = r13     // Catch:{ Exception -> 0x020c }
            r6[r0] = r2     // Catch:{ Exception -> 0x020c }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r3, r4, r5, r6)     // Catch:{ Exception -> 0x020c }
        L_0x020c:
            java.lang.String r3 = "com.uc.webview.export.cd.Utils"
            java.lang.String r4 = "setupVMSizeSavingBlackList"
            java.lang.Class[] r5 = new java.lang.Class[r11]     // Catch:{ Exception -> 0x0223 }
            java.lang.Class<com.uc.webview.export.internal.setup.UCSetupTask> r6 = com.uc.webview.export.internal.setup.UCSetupTask.class
            r5[r8] = r6     // Catch:{ Exception -> 0x0223 }
            java.lang.Class<com.uc.webview.export.cyclone.UCLogger> r6 = com.uc.webview.export.cyclone.UCLogger.class
            r5[r0] = r6     // Catch:{ Exception -> 0x0223 }
            java.lang.Object[] r6 = new java.lang.Object[r11]     // Catch:{ Exception -> 0x0223 }
            r6[r8] = r13     // Catch:{ Exception -> 0x0223 }
            r6[r0] = r2     // Catch:{ Exception -> 0x0223 }
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r3, r4, r5, r6)     // Catch:{ Exception -> 0x0223 }
        L_0x0223:
            java.lang.String r3 = "com.uc.webview.export.cd.Utils"
            java.lang.String r4 = "setCDParamLegacy"
            com.uc.webview.export.internal.utility.ReflectionUtil.invoke(r3, r4)     // Catch:{ Exception -> 0x022a }
        L_0x022a:
            r3 = 10028(0x272c, float:1.4052E-41)
            java.lang.Object[] r4 = new java.lang.Object[r0]
            r4[r8] = r1
            com.uc.webview.export.internal.SDKFactory.invoke(r3, r4)
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "WEBVIEW_POLICY"
            java.lang.Object r3 = r3.get(r4)
            java.lang.Integer r3 = (java.lang.Integer) r3
            if (r2 == 0) goto L_0x024e
            java.lang.String r4 = "setupGlobalOnce: WEBVIEW_POLICY="
            java.lang.String r5 = java.lang.String.valueOf(r3)
            java.lang.String r4 = r4.concat(r5)
            java.lang.Throwable[] r5 = new java.lang.Throwable[r8]
            r2.print(r4, r5)
        L_0x024e:
            if (r3 == 0) goto L_0x0256
            int r3 = r3.intValue()
            com.uc.webview.export.internal.SDKFactory.k = r3
        L_0x0256:
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "sdk_setup"
            java.lang.Boolean r3 = com.uc.webview.export.internal.utility.j.a(r3, r4)
            if (r2 == 0) goto L_0x026f
            java.lang.String r4 = "setupGlobalOnce: sdk_setup="
            java.lang.String r5 = java.lang.String.valueOf(r3)
            java.lang.String r4 = r4.concat(r5)
            java.lang.Throwable[] r5 = new java.lang.Throwable[r8]
            r2.print(r4, r5)
        L_0x026f:
            if (r3 == 0) goto L_0x0277
            boolean r3 = r3.booleanValue()
            com.uc.webview.export.internal.SDKFactory.n = r3
        L_0x0277:
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "MULTI_CORE_TYPE"
            java.lang.Boolean r3 = com.uc.webview.export.internal.utility.j.a(r3, r4)
            if (r2 == 0) goto L_0x0290
            java.lang.String r4 = "setupGlobalOnce: MULTI_CORE_TYPE="
            java.lang.String r5 = java.lang.String.valueOf(r3)
            java.lang.String r4 = r4.concat(r5)
            java.lang.Throwable[] r5 = new java.lang.Throwable[r8]
            r2.print(r4, r5)
        L_0x0290:
            if (r3 == 0) goto L_0x0298
            boolean r3 = r3.booleanValue()
            com.uc.webview.export.internal.SDKFactory.l = r3
        L_0x0298:
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "AC"
            java.lang.Boolean r3 = com.uc.webview.export.internal.utility.j.a(r3, r4)
            if (r2 == 0) goto L_0x02b1
            java.lang.String r4 = "setupGlobalOnce: AC="
            java.lang.String r5 = java.lang.String.valueOf(r3)
            java.lang.String r4 = r4.concat(r5)
            java.lang.Throwable[] r5 = new java.lang.Throwable[r8]
            r2.print(r4, r5)
        L_0x02b1:
            if (r3 == 0) goto L_0x02b9
            boolean r3 = r3.booleanValue()
            com.uc.webview.export.internal.SDKFactory.i = r3
        L_0x02b9:
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "VIDEO_AC"
            java.lang.Boolean r3 = com.uc.webview.export.internal.utility.j.a(r3, r4)
            if (r2 == 0) goto L_0x02d2
            java.lang.String r4 = "setupGlobalOnce: VIDEO_AC="
            java.lang.String r5 = java.lang.String.valueOf(r3)
            java.lang.String r4 = r4.concat(r5)
            java.lang.Throwable[] r5 = new java.lang.Throwable[r8]
            r2.print(r4, r5)
        L_0x02d2:
            if (r3 == 0) goto L_0x02e5
            r4 = 10043(0x273b, float:1.4073E-41)
            java.lang.Object[] r5 = new java.lang.Object[r0]
            boolean r3 = r3.booleanValue()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r5[r8] = r3
            com.uc.webview.export.internal.SDKFactory.invoke(r4, r5)
        L_0x02e5:
            java.util.concurrent.ConcurrentHashMap r3 = r13.mOptions
            java.lang.String r4 = "grant_all_builds"
            java.lang.Boolean r3 = com.uc.webview.export.internal.utility.j.a(r3, r4)
            java.lang.String r1 = r1.getPackageName()
            java.lang.String r4 = "com.ucsdk.cts"
            boolean r1 = r1.equals(r4)
            if (r1 != 0) goto L_0x0304
            if (r3 == 0) goto L_0x0302
            boolean r1 = r3.booleanValue()
            if (r1 == 0) goto L_0x0302
            goto L_0x0304
        L_0x0302:
            r1 = 0
            goto L_0x0305
        L_0x0304:
            r1 = 1
        L_0x0305:
            if (r2 == 0) goto L_0x0316
            java.lang.String r3 = "setupGlobalOnce: grant_all_builds="
            java.lang.String r4 = java.lang.String.valueOf(r1)
            java.lang.String r3 = r3.concat(r4)
            java.lang.Throwable[] r4 = new java.lang.Throwable[r8]
            r2.print(r3, r4)
        L_0x0316:
            if (r1 == 0) goto L_0x031a
            com.uc.webview.export.internal.SDKFactory.s = r0
        L_0x031a:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "conn_to"
            java.lang.Object r0 = r0.get(r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r2 == 0) goto L_0x0335
            java.lang.String r1 = "setupGlobalOnce: conn_to="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x0335:
            if (r0 == 0) goto L_0x033d
            int r0 = r0.intValue()
            com.uc.webview.export.internal.utility.j.a = r0
        L_0x033d:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "read_to"
            java.lang.Object r0 = r0.get(r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r2 == 0) goto L_0x0358
            java.lang.String r1 = "setupGlobalOnce: read_to="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x0358:
            if (r0 == 0) goto L_0x0360
            int r0 = r0.intValue()
            com.uc.webview.export.internal.utility.j.b = r0
        L_0x0360:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "exact_old"
            java.lang.Boolean r0 = com.uc.webview.export.internal.utility.j.a(r0, r1)
            if (r2 == 0) goto L_0x0379
            java.lang.String r1 = "setupGlobalOnce: exact_old="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x0379:
            if (r0 == 0) goto L_0x0381
            boolean r0 = r0.booleanValue()
            com.uc.webview.export.internal.SDKFactory.m = r0
        L_0x0381:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "exact_mod"
            java.lang.Boolean r0 = com.uc.webview.export.internal.utility.j.a(r0, r1)
            if (r2 == 0) goto L_0x039a
            java.lang.String r1 = "setupGlobalOnce: exact_mod="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x039a:
            if (r0 == 0) goto L_0x03a2
            boolean r0 = r0.booleanValue()
            com.uc.webview.export.internal.utility.j.c = r0
        L_0x03a2:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "wait_fallback_sys"
            java.lang.Object r0 = r0.get(r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r2 == 0) goto L_0x03be
            java.lang.String r1 = "setupGlobalOnce: wait_fallback_sys="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x03be:
            if (r0 == 0) goto L_0x03c7
            int r0 = r0.intValue()
            long r0 = (long) r0
            com.uc.webview.export.internal.SDKFactory.j = r0
        L_0x03c7:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "ucPlayerRoot"
            java.lang.Object r0 = r0.get(r1)
            if (r2 == 0) goto L_0x03e1
            java.lang.String r1 = "setupGlobalOnce: ucPlayerRoot="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x03e1:
            if (r0 == 0) goto L_0x03e9
            java.lang.String r0 = r0.toString()
            com.uc.webview.export.internal.SDKFactory.x = r0
        L_0x03e9:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "ucPlayer"
            java.lang.Object r0 = r0.get(r1)
            if (r2 == 0) goto L_0x0403
            java.lang.String r1 = "setupGlobalOnce: ucPlayer="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x0403:
            if (r0 == 0) goto L_0x040d
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            com.uc.webview.export.internal.SDKFactory.t = r0
        L_0x040d:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "WEBVIEW_MULTI_PROCESS"
            java.lang.Object r0 = r0.get(r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r2 == 0) goto L_0x0428
            java.lang.String r1 = "setupGlobalOnce: WEBVIEW_MULTI_PROCESS="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x0428:
            if (r0 == 0) goto L_0x0430
            int r0 = r0.intValue()
            com.uc.webview.export.internal.SDKFactory.u = r0
        L_0x0430:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "WEBVIEW_MULTI_PROCESS_FALLBACK_TIMEOUT"
            java.lang.Object r0 = r0.get(r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            if (r2 == 0) goto L_0x044b
            java.lang.String r1 = "setupGlobalOnce: WEBVIEW_MULTI_PROCESS_FALLBACK_TIMEOUT="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x044b:
            if (r0 == 0) goto L_0x0453
            int r0 = r0.intValue()
            com.uc.webview.export.internal.SDKFactory.v = r0
        L_0x0453:
            java.util.concurrent.ConcurrentHashMap r0 = r13.mOptions
            java.lang.String r1 = "WEBVIEW_MULTI_PROCESS_ENABLE_SERVICE_SPEEDUP"
            java.lang.Object r0 = r0.get(r1)
            if (r2 == 0) goto L_0x046c
            java.lang.String r1 = "setupGlobalOnce: WEBVIEW_MULTI_PROCESS_ENABLE_SERVICE_SPEEDUP="
            java.lang.String r3 = java.lang.String.valueOf(r0)
            java.lang.String r1 = r1.concat(r3)
            java.lang.Throwable[] r3 = new java.lang.Throwable[r8]
            r2.print(r1, r3)
        L_0x046c:
            if (r0 == 0) goto L_0x0476
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            com.uc.webview.export.internal.SDKFactory.w = r0
        L_0x0476:
            java.lang.String r0 = "UCSetupTask.setupGlobalOnce"
            com.uc.webview.export.internal.uc.startup.StartupTrace.traceEventEnd(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.UCSetupTask.setupGlobalOnce():void");
    }

    /* access modifiers changed from: protected */
    public final void setLoadedUCM(UCMRunningInfo uCMRunningInfo) {
        this.a = uCMRunningInfo;
    }

    @Reflection
    public final UCMRepairInfo getRepairInfo() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public final void setRepairInfo(UCMRepairInfo uCMRepairInfo) {
        this.c = uCMRepairInfo;
    }

    @Reflection
    public final UCMRunningInfo getLoadedUCM() {
        return this.a;
    }

    protected static void setRootTaskPriority(int i2) {
        f = i2;
    }

    public static UCSetupTask getDefault() {
        return d;
    }

    /* access modifiers changed from: protected */
    public void setDefault(UCSetupTask uCSetupTask) {
        d = uCSetupTask;
    }
}
