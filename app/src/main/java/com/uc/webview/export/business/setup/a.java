package com.uc.webview.export.business.setup;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCElapseTime;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.setup.BaseSetupTask;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.setup.UCSetupException;
import com.uc.webview.export.internal.setup.da;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import com.uc.webview.export.utility.SetupTask;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: ProGuard */
public class a extends SetupTask {
    /* access modifiers changed from: private */
    public static final String a = "a";
    /* access modifiers changed from: private */
    public com.uc.webview.export.business.a b = new com.uc.webview.export.business.a();
    /* access modifiers changed from: private */
    public com.uc.webview.export.business.a c = new com.uc.webview.export.business.a();
    /* access modifiers changed from: private */
    public com.uc.webview.export.business.a d = new com.uc.webview.export.business.a();
    /* access modifiers changed from: private */
    public com.uc.webview.export.business.a e = new com.uc.webview.export.business.a();
    /* access modifiers changed from: private */
    public com.uc.webview.export.business.a f = new com.uc.webview.export.business.a();
    /* access modifiers changed from: private */
    public C0069a g;
    /* access modifiers changed from: private */
    public ValueCallback<BaseSetupTask> h = new h(this);
    /* access modifiers changed from: private */
    public ValueCallback<BaseSetupTask> i = new i(this);
    /* access modifiers changed from: private */
    public ValueCallback<BaseSetupTask> j = new j(this);
    /* access modifiers changed from: private */
    public ValueCallback<BaseSetupTask> k = new k(this);
    private Map<String, Pair<ValueCallback<BaseSetupTask>, ValueCallback<BaseSetupTask>>> l = new l(this);
    private Map<String, String> m = new m(this);

    /* renamed from: com.uc.webview.export.business.setup.a$a reason: collision with other inner class name */
    /* compiled from: ProGuard */
    class C0069a {
        UCElapseTime a;
        String b;
        String c;
        String d;
        String e;
        String f;
        String g;

        private C0069a() {
            this.a = new UCElapseTime();
        }

        /* synthetic */ C0069a(a aVar, byte b2) {
            this();
        }
    }

    static /* synthetic */ void a(a aVar, String str, BaseSetupTask baseSetupTask) {
        for (Entry next : aVar.l.entrySet()) {
            if (str.equals(next.getKey())) {
                ValueCallback valueCallback = (ValueCallback) ((Pair) next.getValue()).first;
                if (valueCallback != null) {
                    try {
                        valueCallback.onReceiveValue(baseSetupTask != 0 ? baseSetupTask : aVar);
                    } catch (Throwable th) {
                        String str2 = a;
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(" callback");
                        Log.d(str2, sb.toString(), th);
                    }
                }
            }
        }
    }

    static /* synthetic */ void c(a aVar, String str) {
        g gVar = new g(aVar, str);
        WaStat.statAKV(new Pair(IWaStat.BUSINESS_ELAPSE_KEY, gVar));
        Log.d(a, "elapseStatMaps: ".concat(String.valueOf(gVar)));
    }

    static /* synthetic */ void j(a aVar) {
        aVar.c.a(aVar.b.a);
        b bVar = new b(aVar);
        for (Entry entry : bVar.entrySet()) {
            WaStat.stat((String) entry.getKey(), (String) entry.getValue());
        }
        Log.d(a, "processStatMaps: ".concat(String.valueOf(bVar)));
    }

    private void b() {
        for (Entry next : this.l.entrySet()) {
            ValueCallback valueCallback = (ValueCallback) invokeO(10007, next.getKey());
            if (valueCallback != null) {
                next.setValue(new Pair(valueCallback, ((Pair) next.getValue()).second));
            }
        }
    }

    private void a(BaseSetupTask baseSetupTask) {
        for (Entry next : this.l.entrySet()) {
            String str = (String) next.getKey();
            Iterator<Entry<String, String>> it = this.m.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Entry next2 = it.next();
                if (((String) next2.getKey()).equals(str)) {
                    str = (String) next2.getValue();
                    break;
                }
            }
            baseSetupTask.onEvent(str, (ValueCallback) ((Pair) next.getValue()).second);
        }
    }

    private SetupTask d() {
        if (!j.a((Boolean) getOption(UCCore.OPTION_UCMOBILE_INIT))) {
            return UCCore.setup(UCCore.OPTION_CONTEXT, this.mOptions.get(UCCore.OPTION_CONTEXT));
        }
        try {
            return (SetupTask) UCCyclone.invoke(null, Class.forName("com.uc.webview.browser.BrowserCore"), "setup", new Class[]{String.class, Object.class}, new Object[]{UCCore.OPTION_CONTEXT, this.mOptions.get(UCCore.OPTION_CONTEXT)});
        } catch (Exception e2) {
            throw new UCSetupException((Throwable) e2);
        }
    }

    private void a(Map<String, Object> map) {
        String str;
        String str2;
        try {
            ValueCallback valueCallback = (ValueCallback) getOption(UCCore.OPTION_START_INIT_UC_CORE);
            if (valueCallback != null) {
                Bundle bundle = new Bundle();
                for (Entry next : map.entrySet()) {
                    if (next.getValue() instanceof String) {
                        str = (String) next.getKey();
                        str2 = (String) next.getValue();
                    } else {
                        str = (String) next.getKey();
                        str2 = next.getValue() == null ? "null" : next.getValue().toString();
                    }
                    bundle.putString(str, str2);
                }
                valueCallback.onReceiveValue(bundle);
            }
        } catch (Throwable th) {
            Log.d(a, "init core callback", th);
        }
        Log.d(a, "initCore options: ".concat(String.valueOf(map)));
        b();
        SetupTask d2 = d();
        ((SetupTask) ((SetupTask) ((SetupTask) ((SetupTask) d2.setOptions(this.mOptions)).invoke(10001, this)).invoke(10002, this.mCallbacks)).setup((String) UCCore.OPTION_DECOMPRESS_ROOT_DIR, this.mOptions.get(UCCore.OPTION_BUSINESS_DECOMPRESS_ROOT_PATH))).setAsDefault();
        a((BaseSetupTask) d2);
        for (Entry next2 : map.entrySet()) {
            d2.setup((String) next2.getKey(), next2.getValue());
        }
        d2.start();
    }

    /* access modifiers changed from: private */
    public String e() {
        File file = new File(UCCore.getExtractDirPath((String) this.mOptions.get(UCCore.OPTION_BUSINESS_DECOMPRESS_ROOT_PATH), (String) this.mOptions.get(UCCore.OPTION_NEW_UCM_ZIP_FILE)));
        if (!file.exists()) {
            file = new File(UCCore.getExtractDirPath(((File) UCMPackageInfo.invoke(10003, this.mOptions.get(UCCore.OPTION_CONTEXT))).getAbsolutePath(), (String) this.mOptions.get(UCCore.OPTION_NEW_UCM_ZIP_FILE)));
        }
        return file.getAbsolutePath();
    }

    private void f() {
        a((Map<String, Object>) new n<String,Object>(this));
    }

    private void g() {
        a((Map<String, Object>) new d<String,Object>(this));
    }

    private void h() {
        a((Map<String, Object>) new e<String,Object>(this));
    }

    private void i() {
        a((Map<String, Object>) new f<String,Object>(this));
    }

    private static final int[] a(String str) {
        if (str != null) {
            String[] split = str.split("\\.");
            if (split.length > 3) {
                return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3])};
            }
        }
        return null;
    }

    private static final boolean a(String str, String str2, String str3, String str4) {
        try {
            Log.d(a, "sdk版本:".concat(String.valueOf(str)));
            Log.d(a, "sdk支持的最小内核版本:".concat(String.valueOf(str2)));
            Log.d(a, "内核版本:".concat(String.valueOf(str3)));
            Log.d(a, "内核支持的最小sdk版本:".concat(String.valueOf(str4)));
            int[] a2 = a(str3);
            int[] a3 = a(str2);
            if (a2 != null) {
                if (a3 != null) {
                    if (a2[0] >= a3[0]) {
                        if (a2[0] == a3[0]) {
                            if (a2[1] >= a3[1]) {
                                if (a2[1] == a3[1]) {
                                    if (a2[2] >= a3[2]) {
                                        if (a2[2] == a3[2] && a2[3] < a3[3]) {
                                        }
                                    }
                                }
                            }
                        }
                        int[] a4 = a(str);
                        int[] a5 = a(str4);
                        if (a4 != null) {
                            if (a5 != null) {
                                if (a4[0] >= a5[0]) {
                                    if (a4[0] == a5[0]) {
                                        if (a4[1] >= a5[1]) {
                                            if (a4[1] == a5[1]) {
                                                if (a4[2] >= a5[2]) {
                                                    if (a4[2] == a5[2] && a4[3] < a5[3]) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    return true;
                                }
                                Log.d(a, "最小SDK版本不通过");
                                return false;
                            }
                        }
                        return false;
                    }
                    Log.d(a, "最小内核版本不通过");
                    return false;
                }
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:139:0x0260  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x0276  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long a(java.io.File r7, java.lang.String[] r8) {
        /*
            r0 = 0
            int r1 = r8.length     // Catch:{ Throwable -> 0x0245 }
            if (r1 > 0) goto L_0x0026
            java.lang.String r7 = "so file array is empty."
            long r0 = com.uc.webview.export.business.a.C0068a.j     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x001d
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x001d:
            return r0
        L_0x001e:
            r8 = move-exception
            r0 = r7
            goto L_0x0270
        L_0x0022:
            r8 = move-exception
            r0 = r7
            goto L_0x0246
        L_0x0026:
            boolean r1 = com.uc.webview.export.internal.utility.j.b(r7, r7)     // Catch:{ Throwable -> 0x0245 }
            if (r1 != 0) goto L_0x0046
            java.lang.String r7 = "root dir modifyFilePermissionsDirFromTo failure."
            long r0 = com.uc.webview.export.business.a.C0068a.k     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x0045
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x0045:
            return r0
        L_0x0046:
            java.lang.String r1 = "core.jar"
            java.lang.String r2 = "sdk_shell.jar"
            java.lang.String[] r1 = new java.lang.String[]{r1, r2}     // Catch:{ Throwable -> 0x0245 }
            r2 = 0
            r3 = 0
        L_0x0050:
            r4 = 2
            if (r3 >= r4) goto L_0x0097
            r4 = r1[r3]     // Catch:{ Throwable -> 0x0245 }
            java.io.File r5 = new java.io.File     // Catch:{ Throwable -> 0x0245 }
            r5.<init>(r7, r4)     // Catch:{ Throwable -> 0x0245 }
            boolean r4 = r5.exists()     // Catch:{ Throwable -> 0x0245 }
            if (r4 == 0) goto L_0x006a
            boolean r4 = com.uc.webview.export.internal.utility.j.a(r5)     // Catch:{ Throwable -> 0x0245 }
            if (r4 != 0) goto L_0x0067
            goto L_0x006a
        L_0x0067:
            int r3 = r3 + 1
            goto L_0x0050
        L_0x006a:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0245 }
            r7.<init>()     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = r5.getName()     // Catch:{ Throwable -> 0x0245 }
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = " not exists or setReadable failure."
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0245 }
            long r0 = com.uc.webview.export.business.a.C0068a.l     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x0096
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x0096:
            return r0
        L_0x0097:
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r3 = "assets"
            r1.<init>(r7, r3)     // Catch:{ Throwable -> 0x0245 }
            boolean r3 = com.uc.webview.export.internal.utility.j.b(r1, r7)     // Catch:{ Throwable -> 0x0245 }
            if (r3 != 0) goto L_0x00be
            java.lang.String r7 = "resource dir modifyFilePermissionsDirFromTo failure."
            long r0 = com.uc.webview.export.business.a.C0068a.m     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x00bd
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x00bd:
            return r0
        L_0x00be:
            r3 = 10035(0x2733, float:1.4062E-41)
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x0245 }
            r4[r2] = r1     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r1 = "paks"
            r5 = 1
            r4[r5] = r1     // Catch:{ Throwable -> 0x0245 }
            java.lang.Object r1 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r3, r4)     // Catch:{ Throwable -> 0x0245 }
            java.io.File r1 = (java.io.File) r1     // Catch:{ Throwable -> 0x0245 }
            java.io.File[] r1 = r1.listFiles()     // Catch:{ Throwable -> 0x0245 }
            int r3 = r1.length     // Catch:{ Throwable -> 0x0245 }
            r4 = 0
        L_0x00d5:
            if (r4 >= r3) goto L_0x0116
            r5 = r1[r4]     // Catch:{ Throwable -> 0x0245 }
            boolean r6 = r5.exists()     // Catch:{ Throwable -> 0x0245 }
            if (r6 == 0) goto L_0x00e9
            boolean r6 = com.uc.webview.export.internal.utility.j.a(r5)     // Catch:{ Throwable -> 0x0245 }
            if (r6 != 0) goto L_0x00e6
            goto L_0x00e9
        L_0x00e6:
            int r4 = r4 + 1
            goto L_0x00d5
        L_0x00e9:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0245 }
            r7.<init>()     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = r5.getName()     // Catch:{ Throwable -> 0x0245 }
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = " not exists or setReadable failure."
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0245 }
            long r0 = com.uc.webview.export.business.a.C0068a.n     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x0115
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x0115:
            return r0
        L_0x0116:
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0245 }
            r3 = r8[r2]     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r3 = com.uc.webview.export.internal.utility.j.a(r7, r3)     // Catch:{ Throwable -> 0x0245 }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0245 }
            java.io.File r1 = r1.getParentFile()     // Catch:{ Throwable -> 0x0245 }
            boolean r7 = com.uc.webview.export.internal.utility.j.b(r1, r7)     // Catch:{ Throwable -> 0x0245 }
            if (r7 != 0) goto L_0x0145
            java.lang.String r7 = "so dir modifyFilePermissionsDirFromTo failure."
            long r0 = com.uc.webview.export.business.a.C0068a.o     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x0144
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x0144:
            return r0
        L_0x0145:
            boolean r7 = r1.exists()     // Catch:{ Throwable -> 0x0245 }
            if (r7 == 0) goto L_0x0216
            boolean r7 = com.uc.webview.export.internal.utility.j.a(r1)     // Catch:{ Throwable -> 0x0245 }
            if (r7 != 0) goto L_0x0153
            goto L_0x0216
        L_0x0153:
            int r7 = r8.length     // Catch:{ Throwable -> 0x0245 }
        L_0x0154:
            if (r2 >= r7) goto L_0x01fa
            r3 = r8[r2]     // Catch:{ Throwable -> 0x0245 }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x0245 }
            r4.<init>(r1, r3)     // Catch:{ Throwable -> 0x0245 }
            boolean r3 = r4.exists()     // Catch:{ Throwable -> 0x0245 }
            if (r3 != 0) goto L_0x0190
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0245 }
            r7.<init>()     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = r4.getName()     // Catch:{ Throwable -> 0x0245 }
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = " not exists."
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0245 }
            long r0 = com.uc.webview.export.business.a.C0068a.q     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x018f
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x018f:
            return r0
        L_0x0190:
            boolean r3 = com.uc.webview.export.internal.utility.j.b(r4)     // Catch:{ Throwable -> 0x0245 }
            if (r3 != 0) goto L_0x01c3
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0245 }
            r7.<init>()     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = r4.getName()     // Catch:{ Throwable -> 0x0245 }
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = " setExecutable failure."
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0245 }
            long r0 = com.uc.webview.export.business.a.C0068a.r     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x01c2
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x01c2:
            return r0
        L_0x01c3:
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r4)     // Catch:{ Throwable -> 0x0245 }
            if (r3 != 0) goto L_0x01f6
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0245 }
            r7.<init>()     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = r4.getName()     // Catch:{ Throwable -> 0x0245 }
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = " setReadable failure."
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0245 }
            long r0 = com.uc.webview.export.business.a.C0068a.s     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x01f5
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x01f5:
            return r0
        L_0x01f6:
            int r2 = r2 + 1
            goto L_0x0154
        L_0x01fa:
            boolean r7 = com.uc.webview.export.internal.utility.j.a(r0)
            if (r7 != 0) goto L_0x0213
            java.lang.String r7 = a
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r1 = ".checkFilesExistsAndPermissions failure, because "
            r8.<init>(r1)
            r8.append(r0)
            java.lang.String r8 = r8.toString()
            com.uc.webview.export.internal.utility.Log.d(r7, r8)
        L_0x0213:
            r7 = 0
            return r7
        L_0x0216:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0245 }
            r7.<init>()     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = r1.getName()     // Catch:{ Throwable -> 0x0245 }
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r8 = " not exists or setReadable failure."
            r7.append(r8)     // Catch:{ Throwable -> 0x0245 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x0245 }
            long r0 = com.uc.webview.export.business.a.C0068a.p     // Catch:{ Throwable -> 0x0022 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x0242
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x0242:
            return r0
        L_0x0243:
            r8 = move-exception
            goto L_0x0270
        L_0x0245:
            r8 = move-exception
        L_0x0246:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x0243 }
            java.lang.String r1 = "exception "
            r7.<init>(r1)     // Catch:{ all -> 0x0243 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x0243 }
            r7.append(r8)     // Catch:{ all -> 0x0243 }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x0243 }
            long r0 = com.uc.webview.export.business.a.C0068a.t     // Catch:{ all -> 0x001e }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r8 != 0) goto L_0x026f
            java.lang.String r8 = a
            java.lang.String r2 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r7 = java.lang.String.valueOf(r7)
            java.lang.String r7 = r2.concat(r7)
            com.uc.webview.export.internal.utility.Log.d(r8, r7)
        L_0x026f:
            return r0
        L_0x0270:
            boolean r7 = com.uc.webview.export.internal.utility.j.a(r0)
            if (r7 != 0) goto L_0x0285
            java.lang.String r7 = a
            java.lang.String r1 = ".checkFilesExistsAndPermissions failure, because "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r1.concat(r0)
            com.uc.webview.export.internal.utility.Log.d(r7, r0)
        L_0x0285:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.business.setup.a.a(java.io.File, java.lang.String[]):long");
    }

    private static long a(File file, DexClassLoader dexClassLoader) {
        return a(file, j.a((ClassLoader) dexClassLoader));
    }

    private DexClassLoader b(String str) {
        Context context = (Context) getOption(UCCore.OPTION_CONTEXT);
        File file = new File(str);
        try {
            String absolutePath = new File(file, UCMPackageInfo.SDK_SHELL_DEX_FILE).getAbsolutePath();
            Integer num = (Integer) this.mOptions.get(UCCore.OPTION_VERIFY_POLICY);
            if (!(num == null || (num.intValue() & 1) == 0)) {
                da.a(context, num, absolutePath);
            }
            return new DexClassLoader(absolutePath, UCCore.getODexDirPath(context, file.getAbsolutePath()), "", a.class.getClassLoader());
        } catch (Throwable unused) {
            Log.d(a, "create sdk_shell dexLoader failure!");
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:82:0x0159  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0172  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long c(java.lang.String r8) {
        /*
            r7 = this;
            java.lang.String r0 = a
            java.lang.String r1 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r2 = java.lang.String.valueOf(r8)
            java.lang.String r1 = r1.concat(r2)
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x013b }
            r1.<init>(r8)     // Catch:{ Throwable -> 0x013b }
            boolean r2 = r1.exists()     // Catch:{ Throwable -> 0x013b }
            if (r2 != 0) goto L_0x0046
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x013b }
            java.lang.String r2 = "check new core files, "
            r1.<init>(r2)     // Catch:{ Throwable -> 0x013b }
            r1.append(r8)     // Catch:{ Throwable -> 0x013b }
            java.lang.String r8 = " not exists!"
            r1.append(r8)     // Catch:{ Throwable -> 0x013b }
            java.lang.String r8 = r1.toString()     // Catch:{ Throwable -> 0x013b }
            long r0 = com.uc.webview.export.business.a.C0068a.d     // Catch:{ Throwable -> 0x006c, all -> 0x0066 }
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r8)
            if (r2 != 0) goto L_0x0045
            java.lang.String r2 = a
            java.lang.String r3 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r8 = java.lang.String.valueOf(r8)
            java.lang.String r8 = r3.concat(r8)
            com.uc.webview.export.internal.utility.Log.d(r2, r8)
        L_0x0045:
            return r0
        L_0x0046:
            dalvik.system.DexClassLoader r8 = r7.b(r8)     // Catch:{ Throwable -> 0x013b }
            if (r8 != 0) goto L_0x0072
            java.lang.String r8 = "check old core files, create sdk_shell dexLoader failure!"
            long r0 = com.uc.webview.export.business.a.C0068a.e     // Catch:{ Throwable -> 0x006c, all -> 0x0066 }
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r8)
            if (r2 != 0) goto L_0x0065
            java.lang.String r2 = a
            java.lang.String r3 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r8 = java.lang.String.valueOf(r8)
            java.lang.String r8 = r3.concat(r8)
            com.uc.webview.export.internal.utility.Log.d(r2, r8)
        L_0x0065:
            return r0
        L_0x0066:
            r0 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L_0x016c
        L_0x006c:
            r0 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L_0x013c
        L_0x0072:
            java.lang.String r2 = com.uc.webview.export.internal.utility.j.b(r8)     // Catch:{ Throwable -> 0x013b }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r2)     // Catch:{ Throwable -> 0x013b }
            if (r3 == 0) goto L_0x0096
            java.lang.String r8 = "check old core files, get core version failure!"
            long r0 = com.uc.webview.export.business.a.C0068a.f     // Catch:{ Throwable -> 0x006c, all -> 0x0066 }
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r8)
            if (r2 != 0) goto L_0x0095
            java.lang.String r2 = a
            java.lang.String r3 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r8 = java.lang.String.valueOf(r8)
            java.lang.String r8 = r3.concat(r8)
            com.uc.webview.export.internal.utility.Log.d(r2, r8)
        L_0x0095:
            return r0
        L_0x0096:
            java.lang.String r3 = "bo_prom_sp_v_c_i"
            java.lang.Object r3 = r7.getOption(r3)     // Catch:{ Throwable -> 0x013b }
            com.uc.webview.export.extension.UCCore$Callable r3 = (com.uc.webview.export.extension.UCCore.Callable) r3     // Catch:{ Throwable -> 0x013b }
            if (r3 == 0) goto L_0x011f
            java.lang.Object r3 = r3.call(r2)     // Catch:{ Throwable -> 0x013b }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ Throwable -> 0x013b }
            boolean r3 = r3.booleanValue()     // Catch:{ Throwable -> 0x013b }
            if (r3 != 0) goto L_0x00ad
            goto L_0x011f
        L_0x00ad:
            java.lang.String r3 = com.uc.webview.export.internal.utility.j.c(r8)     // Catch:{ Throwable -> 0x013b }
            java.lang.String r4 = com.uc.webview.export.Build.Version.NAME     // Catch:{ Throwable -> 0x013b }
            java.lang.String r5 = com.uc.webview.export.Build.Version.SUPPORT_U4_MIN     // Catch:{ Throwable -> 0x013b }
            boolean r2 = a(r4, r5, r2, r3)     // Catch:{ Throwable -> 0x013b }
            if (r2 != 0) goto L_0x00d5
            java.lang.String r8 = "check old core files, version compatible failure!"
            long r0 = com.uc.webview.export.business.a.C0068a.h     // Catch:{ Throwable -> 0x006c, all -> 0x0066 }
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r8)
            if (r2 != 0) goto L_0x00d4
            java.lang.String r2 = a
            java.lang.String r3 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r8 = java.lang.String.valueOf(r8)
            java.lang.String r8 = r3.concat(r8)
            com.uc.webview.export.internal.utility.Log.d(r2, r8)
        L_0x00d4:
            return r0
        L_0x00d5:
            java.lang.String r2 = "bo_skip_io_dc"
            java.lang.Object r2 = r7.getOption(r2)     // Catch:{ Throwable -> 0x013b }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ Throwable -> 0x013b }
            boolean r2 = com.uc.webview.export.internal.utility.j.b(r2)     // Catch:{ Throwable -> 0x013b }
            r3 = 0
            if (r2 == 0) goto L_0x0105
            long r1 = a(r1, r8)     // Catch:{ Throwable -> 0x013b }
            int r8 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r8 == 0) goto L_0x0105
            java.lang.String r8 = "check old core files, file exists and permission failure!"
            boolean r0 = com.uc.webview.export.internal.utility.j.a(r8)
            if (r0 != 0) goto L_0x0104
            java.lang.String r0 = a
            java.lang.String r3 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r8 = java.lang.String.valueOf(r8)
            java.lang.String r8 = r3.concat(r8)
            com.uc.webview.export.internal.utility.Log.d(r0, r8)
        L_0x0104:
            return r1
        L_0x0105:
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r0)
            if (r8 != 0) goto L_0x011e
            java.lang.String r8 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = ".checkCoreCompatibleAndFileExistsPermissions "
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r8, r0)
        L_0x011e:
            return r3
        L_0x011f:
            java.lang.String r8 = "check callable permission failure!"
            long r0 = com.uc.webview.export.business.a.C0068a.g     // Catch:{ Throwable -> 0x006c, all -> 0x0066 }
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r8)
            if (r2 != 0) goto L_0x0138
            java.lang.String r2 = a
            java.lang.String r3 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r8 = java.lang.String.valueOf(r8)
            java.lang.String r8 = r3.concat(r8)
            com.uc.webview.export.internal.utility.Log.d(r2, r8)
        L_0x0138:
            return r0
        L_0x0139:
            r8 = move-exception
            goto L_0x016c
        L_0x013b:
            r8 = move-exception
        L_0x013c:
            java.lang.String r1 = "check old core files exception!"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0169 }
            r0.<init>()     // Catch:{ all -> 0x0169 }
            r0.append(r1)     // Catch:{ all -> 0x0169 }
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x0169 }
            r0.append(r8)     // Catch:{ all -> 0x0169 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0169 }
            long r1 = com.uc.webview.export.business.a.C0068a.i     // Catch:{ all -> 0x0139 }
            boolean r8 = com.uc.webview.export.internal.utility.j.a(r0)
            if (r8 != 0) goto L_0x0168
            java.lang.String r8 = a
            java.lang.String r3 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r3.concat(r0)
            com.uc.webview.export.internal.utility.Log.d(r8, r0)
        L_0x0168:
            return r1
        L_0x0169:
            r0 = move-exception
            r8 = r0
            r0 = r1
        L_0x016c:
            boolean r1 = com.uc.webview.export.internal.utility.j.a(r0)
            if (r1 != 0) goto L_0x0181
            java.lang.String r1 = a
            java.lang.String r2 = ".checkCoreCompatibleAndFileExistsPermissions "
            java.lang.String r0 = java.lang.String.valueOf(r0)
            java.lang.String r0 = r2.concat(r0)
            com.uc.webview.export.internal.utility.Log.d(r1, r0)
        L_0x0181:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.business.setup.a.c(java.lang.String):long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00a0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long d(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0073 }
            r1.<init>(r7)     // Catch:{ Throwable -> 0x0073 }
            boolean r2 = r1.exists()     // Catch:{ Throwable -> 0x0073 }
            if (r2 != 0) goto L_0x002d
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r2 = "check new core files, "
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0073 }
            r1.append(r7)     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r7 = " not exists!"
            r1.append(r7)     // Catch:{ Throwable -> 0x0073 }
            java.lang.String r7 = r1.toString()     // Catch:{ Throwable -> 0x0073 }
            long r0 = com.uc.webview.export.business.a.C0068a.d     // Catch:{ Throwable -> 0x0048, all -> 0x0043 }
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r2 != 0) goto L_0x002c
            java.lang.String r2 = a
            com.uc.webview.export.internal.utility.Log.d(r2, r7)
        L_0x002c:
            return r0
        L_0x002d:
            dalvik.system.DexClassLoader r7 = r6.b(r7)     // Catch:{ Throwable -> 0x0073 }
            if (r7 != 0) goto L_0x004d
            java.lang.String r7 = "check new core files, create sdk_shell dexLoader failure!"
            long r0 = com.uc.webview.export.business.a.C0068a.e     // Catch:{ Throwable -> 0x0048, all -> 0x0043 }
            boolean r2 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r2 != 0) goto L_0x0042
            java.lang.String r2 = a
            com.uc.webview.export.internal.utility.Log.d(r2, r7)
        L_0x0042:
            return r0
        L_0x0043:
            r0 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x009a
        L_0x0048:
            r0 = move-exception
            r5 = r0
            r0 = r7
            r7 = r5
            goto L_0x0074
        L_0x004d:
            long r1 = a(r1, r7)     // Catch:{ Throwable -> 0x0073 }
            r3 = 0
            int r7 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r7 == 0) goto L_0x0065
            java.lang.String r7 = "check new core files, file exists and permission failure!"
            boolean r0 = com.uc.webview.export.internal.utility.j.a(r7)
            if (r0 != 0) goto L_0x0064
            java.lang.String r0 = a
            com.uc.webview.export.internal.utility.Log.d(r0, r7)
        L_0x0064:
            return r1
        L_0x0065:
            boolean r7 = com.uc.webview.export.internal.utility.j.a(r0)
            if (r7 != 0) goto L_0x0070
            java.lang.String r7 = a
            com.uc.webview.export.internal.utility.Log.d(r7, r0)
        L_0x0070:
            return r3
        L_0x0071:
            r7 = move-exception
            goto L_0x009a
        L_0x0073:
            r7 = move-exception
        L_0x0074:
            java.lang.String r1 = "check new core files exception!"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0097 }
            r0.<init>()     // Catch:{ all -> 0x0097 }
            r0.append(r1)     // Catch:{ all -> 0x0097 }
            java.lang.String r7 = r7.getMessage()     // Catch:{ all -> 0x0097 }
            r0.append(r7)     // Catch:{ all -> 0x0097 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0097 }
            long r1 = com.uc.webview.export.business.a.C0068a.i     // Catch:{ all -> 0x0071 }
            boolean r7 = com.uc.webview.export.internal.utility.j.a(r0)
            if (r7 != 0) goto L_0x0096
            java.lang.String r7 = a
            com.uc.webview.export.internal.utility.Log.d(r7, r0)
        L_0x0096:
            return r1
        L_0x0097:
            r0 = move-exception
            r7 = r0
            r0 = r1
        L_0x009a:
            boolean r1 = com.uc.webview.export.internal.utility.j.a(r0)
            if (r1 != 0) goto L_0x00a5
            java.lang.String r1 = a
            com.uc.webview.export.internal.utility.Log.d(r1, r0)
        L_0x00a5:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.business.setup.a.d(java.lang.String):long");
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x0121 A[Catch:{ UCSetupException -> 0x0268, all -> 0x0370, UCSetupException -> 0x0316 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0186 A[SYNTHETIC, Splitter:B:46:0x0186] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r9 = this;
            java.lang.String r0 = a
            java.lang.String r1 = ".run begin."
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            com.uc.webview.export.business.setup.a$a r0 = new com.uc.webview.export.business.setup.a$a
            r1 = 0
            r0.<init>(r9, r1)
            r9.g = r0
            com.uc.webview.export.business.a r0 = r9.b     // Catch:{ all -> 0x0370 }
            long r2 = com.uc.webview.export.business.a.d.a     // Catch:{ all -> 0x0370 }
            r0.a(r2)     // Catch:{ all -> 0x0370 }
            r0 = 7001(0x1b59, float:9.81E-42)
            r2 = 0
            com.uc.webview.export.business.a r3 = r9.d     // Catch:{ UCSetupException -> 0x0268 }
            long r4 = com.uc.webview.export.business.a.b.a     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "CONTEXT"
            java.lang.Object r3 = r9.getOption(r3)     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 == 0) goto L_0x0037
            com.uc.webview.export.business.a r3 = r9.d     // Catch:{ UCSetupException -> 0x0268 }
            long r4 = com.uc.webview.export.business.a.b.e     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "OPTION_CONTEXT is null."
            goto L_0x00c9
        L_0x0037:
            java.util.concurrent.ConcurrentHashMap r3 = r9.mOptions     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r4 = "bo_new_ucm_zf"
            java.lang.Object r3 = r3.get(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 == 0) goto L_0x0052
            com.uc.webview.export.business.a r3 = r9.d     // Catch:{ UCSetupException -> 0x0268 }
            long r4 = com.uc.webview.export.business.a.b.b     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "OPTION_NEW_UCM_ZIP_FILE is empty."
            goto L_0x00c9
        L_0x0052:
            java.util.concurrent.ConcurrentHashMap r3 = r9.mOptions     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r4 = "bo_dec_r_p"
            java.lang.Object r3 = r3.get(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 == 0) goto L_0x006c
            com.uc.webview.export.business.a r3 = r9.d     // Catch:{ UCSetupException -> 0x0268 }
            long r4 = com.uc.webview.export.business.a.b.c     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "OPTION_BUSINESS_DECOMPRESS_ROOT_PATH is empty."
            goto L_0x00c9
        L_0x006c:
            java.util.concurrent.ConcurrentHashMap r3 = r9.mOptions     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r4 = "bo_f_u_dec_r_p"
            java.lang.Object r3 = r3.get(r4)     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 == 0) goto L_0x0084
            com.uc.webview.export.business.a r3 = r9.d     // Catch:{ UCSetupException -> 0x0268 }
            long r4 = com.uc.webview.export.business.a.b.d     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "OPTION_FORCE_USE_BUSINESS_DECOMPRESS_ROOT_PATH is null."
            goto L_0x00c9
        L_0x0084:
            java.util.concurrent.ConcurrentHashMap r3 = r9.mOptions     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r4 = "bo_old_dex_dp"
            java.lang.Object r3 = r3.get(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 != 0) goto L_0x00aa
            java.lang.String r3 = "bo_prom_sp_v_c_i"
            java.lang.Object r3 = r9.getOption(r3)     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 == 0) goto L_0x00aa
            com.uc.webview.export.business.a r3 = r9.d     // Catch:{ UCSetupException -> 0x0268 }
            long r4 = com.uc.webview.export.business.a.b.f     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "OPTION_OLD_DEX_DIR_PATH not empty but OPTION_PROMISE_SPECIAL_VERSION_CORE_INIT is null."
            goto L_0x00c9
        L_0x00aa:
            java.lang.String r3 = "bo_ucm_init"
            java.lang.Object r3 = r9.getOption(r3)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 == 0) goto L_0x00c8
            boolean r3 = com.uc.webview.export.internal.utility.j.e()     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 != 0) goto L_0x00c8
            com.uc.webview.export.business.a r3 = r9.d     // Catch:{ UCSetupException -> 0x0268 }
            long r4 = com.uc.webview.export.business.a.b.g     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "OPTION_UCMOBILE_INIT is true but Class.forName(\"com.uc.webview.browser.BrowserCore\") exception."
            goto L_0x00c9
        L_0x00c8:
            r3 = r2
        L_0x00c9:
            boolean r4 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r4 != 0) goto L_0x00d5
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ UCSetupException -> 0x0268 }
            r1.<init>(r0, r3)     // Catch:{ UCSetupException -> 0x0268 }
            throw r1     // Catch:{ UCSetupException -> 0x0268 }
        L_0x00d5:
            com.uc.webview.export.business.a r3 = r9.e     // Catch:{ UCSetupException -> 0x0268 }
            long r4 = com.uc.webview.export.business.a.C0068a.a     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "bo_new_ucm_zf"
            java.lang.Object r3 = r9.getOption(r3)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r4 = "bo_dec_r_p"
            java.lang.Object r4 = r9.getOption(r4)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.business.setup.o.b(r4, r3)     // Catch:{ UCSetupException -> 0x0268 }
            r4 = 0
            r6 = 1
            if (r3 == 0) goto L_0x011e
            com.uc.webview.export.business.a r3 = r9.e     // Catch:{ UCSetupException -> 0x0268 }
            long r7 = com.uc.webview.export.business.a.C0068a.c     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r7)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = "bo_skip_io_dc"
            java.lang.Object r3 = r9.getOption(r3)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ UCSetupException -> 0x0268 }
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            if (r3 == 0) goto L_0x010c
        L_0x010a:
            r3 = 1
            goto L_0x011f
        L_0x010c:
            java.lang.String r3 = r9.e()     // Catch:{ UCSetupException -> 0x0268 }
            long r7 = r9.d(r3)     // Catch:{ UCSetupException -> 0x0268 }
            int r3 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r3 != 0) goto L_0x0119
            goto L_0x010a
        L_0x0119:
            com.uc.webview.export.business.a r3 = r9.e     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r7)     // Catch:{ UCSetupException -> 0x0268 }
        L_0x011e:
            r3 = 0
        L_0x011f:
            if (r3 == 0) goto L_0x0186
            java.lang.String r1 = a     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = ".run readyDecompressAndODex && checkNewCoreFileExistsAndPermissions."
            com.uc.webview.export.internal.utility.Log.d(r1, r3)     // Catch:{ UCSetupException -> 0x0268 }
            com.uc.webview.export.business.setup.c r1 = new com.uc.webview.export.business.setup.c     // Catch:{ UCSetupException -> 0x0268 }
            r1.<init>(r9)     // Catch:{ UCSetupException -> 0x0268 }
            r9.a(r1)     // Catch:{ UCSetupException -> 0x0268 }
            com.uc.webview.export.business.a r1 = r9.b     // Catch:{ UCSetupException -> 0x0268 }
            long r3 = com.uc.webview.export.business.a.d.b     // Catch:{ UCSetupException -> 0x0268 }
            r1.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            com.uc.webview.export.business.setup.a$a r0 = r9.g
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r1 = r1.a
            long r1 = r1.getMilis()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.b = r1
            com.uc.webview.export.business.setup.a$a r0 = r9.g
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r1 = r1.a
            long r1 = r1.getMilisCpu()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.c = r1
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "mInitStat："
            r1.<init>(r2)
            com.uc.webview.export.business.a r2 = r9.b
            long r2 = r2.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "checkMillis："
            r1.<init>(r2)
            com.uc.webview.export.business.setup.a$a r2 = r9.g
            java.lang.String r2 = r2.b
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            return
        L_0x0186:
            com.uc.webview.export.business.a r3 = r9.f     // Catch:{ UCSetupException -> 0x0268 }
            long r7 = com.uc.webview.export.business.a.C0068a.b     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r7)     // Catch:{ UCSetupException -> 0x0268 }
            java.util.concurrent.ConcurrentHashMap r3 = r9.mOptions     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r7 = "bo_old_dex_dp"
            java.lang.Object r3 = r3.get(r7)     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ UCSetupException -> 0x0268 }
            long r7 = r9.c(r3)     // Catch:{ UCSetupException -> 0x0268 }
            int r3 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r3 != 0) goto L_0x01a1
            r1 = 1
            goto L_0x01a6
        L_0x01a1:
            com.uc.webview.export.business.a r3 = r9.f     // Catch:{ UCSetupException -> 0x0268 }
            r3.a(r7)     // Catch:{ UCSetupException -> 0x0268 }
        L_0x01a6:
            if (r1 == 0) goto L_0x0208
            java.lang.String r1 = a     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = ".run checkOldCoreCompatibleAndFileExistsPermissions."
            com.uc.webview.export.internal.utility.Log.d(r1, r3)     // Catch:{ UCSetupException -> 0x0268 }
            r9.g()     // Catch:{ UCSetupException -> 0x0268 }
            com.uc.webview.export.business.a r1 = r9.b     // Catch:{ UCSetupException -> 0x0268 }
            long r3 = com.uc.webview.export.business.a.d.c     // Catch:{ UCSetupException -> 0x0268 }
            r1.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            com.uc.webview.export.business.setup.a$a r0 = r9.g
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r1 = r1.a
            long r1 = r1.getMilis()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.b = r1
            com.uc.webview.export.business.setup.a$a r0 = r9.g
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r1 = r1.a
            long r1 = r1.getMilisCpu()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.c = r1
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "mInitStat："
            r1.<init>(r2)
            com.uc.webview.export.business.a r2 = r9.b
            long r2 = r2.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "checkMillis："
            r1.<init>(r2)
            com.uc.webview.export.business.setup.a$a r2 = r9.g
            java.lang.String r2 = r2.b
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            return
        L_0x0208:
            java.lang.String r1 = a     // Catch:{ UCSetupException -> 0x0268 }
            java.lang.String r3 = ".run initNewCoreByZipFile."
            com.uc.webview.export.internal.utility.Log.d(r1, r3)     // Catch:{ UCSetupException -> 0x0268 }
            r9.f()     // Catch:{ UCSetupException -> 0x0268 }
            com.uc.webview.export.business.a r1 = r9.b     // Catch:{ UCSetupException -> 0x0268 }
            long r3 = com.uc.webview.export.business.a.d.d     // Catch:{ UCSetupException -> 0x0268 }
            r1.a(r3)     // Catch:{ UCSetupException -> 0x0268 }
            com.uc.webview.export.business.setup.a$a r0 = r9.g
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r1 = r1.a
            long r1 = r1.getMilis()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.b = r1
            com.uc.webview.export.business.setup.a$a r0 = r9.g
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r1 = r1.a
            long r1 = r1.getMilisCpu()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.c = r1
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "mInitStat："
            r1.<init>(r2)
            com.uc.webview.export.business.a r2 = r9.b
            long r2 = r2.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "checkMillis："
            r1.<init>(r2)
            com.uc.webview.export.business.setup.a$a r2 = r9.g
            java.lang.String r2 = r2.b
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            return
        L_0x0268:
            r1 = move-exception
            java.lang.String r3 = a     // Catch:{ all -> 0x0370 }
            java.lang.String r4 = "checkInputConditions failure message: "
            com.uc.webview.export.internal.utility.Log.d(r3, r4, r1)     // Catch:{ all -> 0x0370 }
            int r3 = r1.errCode()     // Catch:{ all -> 0x0370 }
            if (r3 == r0) goto L_0x0277
            throw r1     // Catch:{ all -> 0x0370 }
        L_0x0277:
            java.lang.String r0 = "CONTEXT"
            java.lang.Object r0 = r9.getOption(r0)     // Catch:{ UCSetupException -> 0x0316 }
            boolean r0 = com.uc.webview.export.internal.utility.j.a(r0)     // Catch:{ UCSetupException -> 0x0316 }
            if (r0 == 0) goto L_0x028d
            com.uc.webview.export.business.a r0 = r9.d     // Catch:{ UCSetupException -> 0x0316 }
            long r1 = com.uc.webview.export.business.a.b.e     // Catch:{ UCSetupException -> 0x0316 }
            r0.a(r1)     // Catch:{ UCSetupException -> 0x0316 }
            java.lang.String r2 = "OPTION_CONTEXT is null."
            goto L_0x02f6
        L_0x028d:
            java.lang.String r0 = "sc_ldpl"
            java.lang.String r0 = com.uc.webview.export.extension.UCCore.getParam(r0)     // Catch:{ UCSetupException -> 0x0316 }
            java.lang.String r1 = "sc_lshco"
            boolean r0 = r1.equals(r0)     // Catch:{ UCSetupException -> 0x0316 }
            if (r0 != 0) goto L_0x02a5
            com.uc.webview.export.business.a r0 = r9.d     // Catch:{ UCSetupException -> 0x0316 }
            long r1 = com.uc.webview.export.business.a.b.h     // Catch:{ UCSetupException -> 0x0316 }
            r0.a(r1)     // Catch:{ UCSetupException -> 0x0316 }
            java.lang.String r2 = "shareCoreLoadPolicy not equals sc_lshco"
            goto L_0x02f6
        L_0x02a5:
            java.lang.String r0 = "sc_ta_fp"
            java.lang.String r0 = com.uc.webview.export.extension.UCCore.getParam(r0)     // Catch:{ UCSetupException -> 0x0316 }
            boolean r0 = com.uc.webview.export.internal.utility.j.a(r0)     // Catch:{ UCSetupException -> 0x0316 }
            if (r0 == 0) goto L_0x02bb
            com.uc.webview.export.business.a r0 = r9.d     // Catch:{ UCSetupException -> 0x0316 }
            long r1 = com.uc.webview.export.business.a.b.i     // Catch:{ UCSetupException -> 0x0316 }
            r0.a(r1)     // Catch:{ UCSetupException -> 0x0316 }
            java.lang.String r2 = "CD_KEY_SHARE_CORE_COMMONALITY_TARGET_FPATH is empty."
            goto L_0x02f6
        L_0x02bb:
            java.lang.String r0 = "sc_pkgl"
            java.lang.String r0 = com.uc.webview.export.extension.UCCore.getParam(r0)     // Catch:{ UCSetupException -> 0x0316 }
            boolean r0 = com.uc.webview.export.internal.utility.j.a(r0)     // Catch:{ UCSetupException -> 0x0316 }
            if (r0 == 0) goto L_0x02d1
            com.uc.webview.export.business.a r0 = r9.d     // Catch:{ UCSetupException -> 0x0316 }
            long r1 = com.uc.webview.export.business.a.b.j     // Catch:{ UCSetupException -> 0x0316 }
            r0.a(r1)     // Catch:{ UCSetupException -> 0x0316 }
            java.lang.String r2 = "CD_KEY_SHARE_CORE_CLIENT_SPECIAL_HOST_PKG_NAME_LIST is empty."
            goto L_0x02f6
        L_0x02d1:
            java.lang.String r0 = "sc_taucmv"
            java.lang.String r0 = com.uc.webview.export.extension.UCCore.getParam(r0)     // Catch:{ UCSetupException -> 0x0316 }
            boolean r0 = com.uc.webview.export.internal.utility.j.a(r0)     // Catch:{ UCSetupException -> 0x0316 }
            if (r0 == 0) goto L_0x02e7
            com.uc.webview.export.business.a r0 = r9.d     // Catch:{ UCSetupException -> 0x0316 }
            long r1 = com.uc.webview.export.business.a.b.k     // Catch:{ UCSetupException -> 0x0316 }
            r0.a(r1)     // Catch:{ UCSetupException -> 0x0316 }
            java.lang.String r2 = "CD_KEY_SHARE_CORE_CLIENT_UCM_VERSIONS is empty."
            goto L_0x02f6
        L_0x02e7:
            boolean r0 = com.uc.webview.export.internal.utility.g.b()     // Catch:{ UCSetupException -> 0x0316 }
            if (r0 != 0) goto L_0x02f6
            com.uc.webview.export.business.a r0 = r9.d     // Catch:{ UCSetupException -> 0x0316 }
            long r1 = com.uc.webview.export.business.a.b.l     // Catch:{ UCSetupException -> 0x0316 }
            r0.a(r1)     // Catch:{ UCSetupException -> 0x0316 }
            java.lang.String r2 = "Sdcard配置及权限校验失败."
        L_0x02f6:
            boolean r0 = com.uc.webview.export.internal.utility.j.a(r2)     // Catch:{ UCSetupException -> 0x0316 }
            if (r0 != 0) goto L_0x0304
            com.uc.webview.export.internal.setup.UCSetupException r0 = new com.uc.webview.export.internal.setup.UCSetupException     // Catch:{ UCSetupException -> 0x0316 }
            r1 = 7003(0x1b5b, float:9.813E-42)
            r0.<init>(r1, r2)     // Catch:{ UCSetupException -> 0x0316 }
            throw r0     // Catch:{ UCSetupException -> 0x0316 }
        L_0x0304:
            java.lang.String r0 = a     // Catch:{ UCSetupException -> 0x0316 }
            java.lang.String r1 = ".run initShareCore."
            com.uc.webview.export.internal.utility.Log.d(r0, r1)     // Catch:{ UCSetupException -> 0x0316 }
            r9.h()     // Catch:{ UCSetupException -> 0x0316 }
            com.uc.webview.export.business.a r0 = r9.b     // Catch:{ UCSetupException -> 0x0316 }
            long r1 = com.uc.webview.export.business.a.d.j     // Catch:{ UCSetupException -> 0x0316 }
            r0.a(r1)     // Catch:{ UCSetupException -> 0x0316 }
            goto L_0x0321
        L_0x0316:
            r0 = move-exception
            java.lang.String r1 = a     // Catch:{ all -> 0x0370 }
            java.lang.String r2 = "checkShareCore failure message: "
            com.uc.webview.export.internal.utility.Log.d(r1, r2, r0)     // Catch:{ all -> 0x0370 }
            r9.i()     // Catch:{ all -> 0x0370 }
        L_0x0321:
            com.uc.webview.export.business.setup.a$a r0 = r9.g
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r1 = r1.a
            long r1 = r1.getMilis()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.b = r1
            com.uc.webview.export.business.setup.a$a r0 = r9.g
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r1 = r1.a
            long r1 = r1.getMilisCpu()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.c = r1
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "mInitStat："
            r1.<init>(r2)
            com.uc.webview.export.business.a r2 = r9.b
            long r2 = r2.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "checkMillis："
            r1.<init>(r2)
            com.uc.webview.export.business.setup.a$a r2 = r9.g
            java.lang.String r2 = r2.b
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            return
        L_0x0370:
            r0 = move-exception
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.business.setup.a$a r2 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r2 = r2.a
            long r2 = r2.getMilis()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1.b = r2
            com.uc.webview.export.business.setup.a$a r1 = r9.g
            com.uc.webview.export.business.setup.a$a r2 = r9.g
            com.uc.webview.export.cyclone.UCElapseTime r2 = r2.a
            long r2 = r2.getMilisCpu()
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1.c = r2
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "mInitStat："
            r2.<init>(r3)
            com.uc.webview.export.business.a r3 = r9.b
            long r3 = r3.a
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "checkMillis："
            r2.<init>(r3)
            com.uc.webview.export.business.setup.a$a r3 = r9.g
            java.lang.String r3 = r3.b
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.business.setup.a.run():void");
    }
}
