package com.uc.webview.export.business.setup;

import android.os.Bundle;
import android.webkit.ValueCallback;
import com.uc.webview.export.business.a;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.setup.t;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import com.uc.webview.export.utility.SetupTask;
import java.io.File;

/* compiled from: ProGuard */
public class o extends SetupTask {
    /* access modifiers changed from: private */
    public static final String a = "o";
    private static String c = "_odex_ready";
    /* access modifiers changed from: private */
    public a b = new a();

    static /* synthetic */ void a(o oVar, t tVar) {
        ValueCallback valueCallback = (ValueCallback) oVar.getOption(UCCore.OPTION_DECOMPRESS_AND_ODEX_CALLBACK);
        if (valueCallback != null) {
            Bundle bundle = new Bundle();
            bundle.putString("event", (String) tVar.invokeO(10009, new Object[0]));
            if (tVar.getException() != null) {
                bundle.putInt("errorCode", tVar.getException().errCode());
                bundle.putString("msg", tVar.getException().getMessage());
            }
            Log.d(a, "decompressAndODex bundle: ".concat(String.valueOf(bundle)));
            valueCallback.onReceiveValue(bundle);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x021c, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        r8.b.a(com.uc.webview.export.business.a.c.c);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0225, code lost:
        r0 = a;
        r1 = new java.lang.StringBuilder(".run stat: ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x022f, code lost:
        r1 = a;
        r2 = new java.lang.StringBuilder(".run stat: ");
        r2.append(r8.b.a);
        com.uc.webview.export.internal.utility.Log.d(r1, r2.toString());
        com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(com.uc.webview.export.internal.interfaces.IWaStat.BUSINESS_DECOMPRESS_AND_ODEX, java.lang.Long.toString(r8.b.a));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0253, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:48:0x021e */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r8 = this;
            com.uc.webview.export.business.a r0 = r8.b     // Catch:{ Throwable -> 0x021e }
            long r1 = com.uc.webview.export.business.a.c.a     // Catch:{ Throwable -> 0x021e }
            r0.a(r1)     // Catch:{ Throwable -> 0x021e }
            boolean r0 = com.uc.webview.export.internal.utility.j.e()     // Catch:{ Throwable -> 0x021e }
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0031
            java.lang.String r0 = "process_private_data_dir_suffix"
            java.lang.Object r0 = com.uc.webview.export.extension.UCCore.getGlobalOption(r0)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x021e }
            boolean r0 = com.uc.webview.export.internal.utility.j.a(r0)     // Catch:{ Throwable -> 0x021e }
            if (r0 == 0) goto L_0x0031
            java.lang.String r0 = "process_private_data_dir_suffix"
            java.lang.String r3 = "1"
            com.uc.webview.export.extension.UCCore.setGlobalOption(r0, r3)     // Catch:{ Throwable -> 0x021e }
            r0 = 10028(0x272c, float:1.4052E-41)
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x021e }
            android.content.Context r4 = r8.getContext()     // Catch:{ Throwable -> 0x021e }
            r3[r1] = r4     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.SDKFactory.invoke(r0, r3)     // Catch:{ Throwable -> 0x021e }
        L_0x0031:
            java.lang.String r0 = "process_private_data_dir_suffix"
            java.lang.Object r0 = com.uc.webview.export.extension.UCCore.getGlobalOption(r0)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x021e }
            if (r0 == 0) goto L_0x007d
            java.lang.String r3 = "0"
            boolean r3 = r3.equals(r0)     // Catch:{ Throwable -> 0x021e }
            if (r3 != 0) goto L_0x007d
            boolean r3 = com.uc.webview.export.internal.utility.j.e()     // Catch:{ Throwable -> 0x021e }
            if (r3 == 0) goto L_0x0051
            java.lang.String r3 = "1"
            boolean r0 = r3.equals(r0)     // Catch:{ Throwable -> 0x021e }
            if (r0 != 0) goto L_0x007d
        L_0x0051:
            com.uc.webview.export.business.a r0 = r8.b     // Catch:{ Throwable -> 0x021e }
            long r1 = com.uc.webview.export.business.a.c.d     // Catch:{ Throwable -> 0x021e }
            r0.a(r1)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = ".run stat: "
            r1.<init>(r2)
            com.uc.webview.export.business.a r2 = r8.b
            long r2 = r2.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            java.lang.String r0 = "bs_dec_od"
            com.uc.webview.export.business.a r1 = r8.b
            long r1 = r1.a
            java.lang.String r1 = java.lang.Long.toString(r1)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r0, r1)
            return
        L_0x007d:
            java.lang.String r0 = "ucmZipFile"
            java.lang.Object r0 = r8.getOption(r0)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r3 = "bo_dec_root_dir"
            java.lang.Object r3 = r8.getOption(r3)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x021e }
            java.lang.String r4 = a     // Catch:{ Throwable -> 0x021e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x021e }
            java.lang.String r6 = ".run decFilePath: "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x021e }
            r5.append(r0)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r6 = " decRootDirPath: "
            r5.append(r6)     // Catch:{ Throwable -> 0x021e }
            r5.append(r3)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.utility.Log.d(r4, r5)     // Catch:{ Throwable -> 0x021e }
            boolean r4 = com.uc.webview.export.internal.utility.j.a(r0)     // Catch:{ Throwable -> 0x021e }
            if (r4 != 0) goto L_0x01f0
            boolean r4 = com.uc.webview.export.internal.utility.j.a(r3)     // Catch:{ Throwable -> 0x021e }
            if (r4 == 0) goto L_0x00b7
            goto L_0x01f0
        L_0x00b7:
            boolean r4 = b(r3, r0)     // Catch:{ Throwable -> 0x021e }
            if (r4 == 0) goto L_0x00f0
            java.lang.String r0 = a     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "readyDecompressAndODex"
            com.uc.webview.export.internal.utility.Log.d(r0, r1)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.business.a r0 = r8.b     // Catch:{ Throwable -> 0x021e }
            long r1 = com.uc.webview.export.business.a.c.f     // Catch:{ Throwable -> 0x021e }
            r0.a(r1)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = ".run stat: "
            r1.<init>(r2)
            com.uc.webview.export.business.a r2 = r8.b
            long r2 = r2.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            java.lang.String r0 = "bs_dec_od"
            com.uc.webview.export.business.a r1 = r8.b
            long r1 = r1.a
            java.lang.String r1 = java.lang.Long.toString(r1)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r0, r1)
            return
        L_0x00f0:
            com.uc.webview.export.internal.setup.n r4 = new com.uc.webview.export.internal.setup.n     // Catch:{ Throwable -> 0x021e }
            r4.<init>()     // Catch:{ Throwable -> 0x021e }
            r5 = 10001(0x2711, float:1.4014E-41)
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x021e }
            r6[r1] = r8     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.UCAsyncTask r4 = r4.invoke(r5, r6)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r4 = (com.uc.webview.export.internal.setup.t) r4     // Catch:{ Throwable -> 0x021e }
            java.util.concurrent.ConcurrentHashMap r5 = r8.mCallbacks     // Catch:{ Throwable -> 0x021e }
            if (r5 == 0) goto L_0x0110
            r5 = 10002(0x2712, float:1.4016E-41)
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x021e }
            java.util.concurrent.ConcurrentHashMap r7 = r8.mCallbacks     // Catch:{ Throwable -> 0x021e }
            r6[r1] = r7     // Catch:{ Throwable -> 0x021e }
            r4.invoke(r5, r6)     // Catch:{ Throwable -> 0x021e }
        L_0x0110:
            java.lang.String r1 = "exception"
            com.uc.webview.export.business.setup.r r5 = new com.uc.webview.export.business.setup.r     // Catch:{ Throwable -> 0x021e }
            r5.<init>(r8)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r4.onEvent(r1, r5)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1     // Catch:{ Throwable -> 0x021e }
            java.lang.String r5 = "die"
            com.uc.webview.export.business.setup.q r6 = new com.uc.webview.export.business.setup.q     // Catch:{ Throwable -> 0x021e }
            r6.<init>(r8)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r1.onEvent(r5, r6)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1     // Catch:{ Throwable -> 0x021e }
            java.lang.String r5 = "setup"
            com.uc.webview.export.business.setup.p r6 = new com.uc.webview.export.business.setup.p     // Catch:{ Throwable -> 0x021e }
            r6.<init>(r8, r3, r0)     // Catch:{ Throwable -> 0x021e }
            r1.onEvent(r5, r6)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.business.a r0 = r8.b     // Catch:{ Throwable -> 0x021e }
            long r5 = com.uc.webview.export.business.a.c.b     // Catch:{ Throwable -> 0x021e }
            r0.a(r5)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r0 = "VERIFY_POLICY"
            java.lang.Object r0 = r8.getOption(r0)     // Catch:{ Throwable -> 0x021e }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "sc_vrfplc"
            java.lang.String r1 = com.uc.webview.export.extension.UCCore.getParam(r1)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r3 = "sc_vrfaha"
            boolean r3 = r3.equals(r1)     // Catch:{ Throwable -> 0x021e }
            r5 = -1073741697(0xffffffffc000007f, float:-2.0000303)
            if (r3 == 0) goto L_0x015e
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x021e }
            r0 = r0 | r5
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x021e }
            goto L_0x0173
        L_0x015e:
            java.lang.String r3 = "sc_vrfahs"
            boolean r1 = r3.equals(r1)     // Catch:{ Throwable -> 0x021e }
            if (r1 == 0) goto L_0x0173
            int r0 = r0.intValue()     // Catch:{ Throwable -> 0x021e }
            r0 = r0 | r5
            r1 = 2147483647(0x7fffffff, float:NaN)
            r0 = r0 & r1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x021e }
        L_0x0173:
            java.util.concurrent.ConcurrentHashMap r1 = r8.mOptions     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.BaseSetupTask r1 = r4.setOptions(r1)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r1 = (com.uc.webview.export.internal.setup.t) r1     // Catch:{ Throwable -> 0x021e }
            java.lang.String r3 = "VERIFY_POLICY"
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r1.setup(r3, r0)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "dexFilePath"
            r3 = 0
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.setup(r1, r3)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "soFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.setup(r1, r3)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "resFilePath"
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.setup(r1, r3)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "ucmCfgFile"
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.setup(r1, r3)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "ucmKrlDir"
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.setup(r1, r3)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "sdk_setup"
            boolean r5 = com.uc.webview.export.internal.utility.j.e()     // Catch:{ Throwable -> 0x021e }
            r2 = r2 ^ r5
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.BaseSetupTask r0 = r0.setup(r1, r2)     // Catch:{ Throwable -> 0x021e }
            com.uc.webview.export.internal.setup.t r0 = (com.uc.webview.export.internal.setup.t) r0     // Catch:{ Throwable -> 0x021e }
            java.lang.String r1 = "bo_enable_load_class"
            java.lang.Boolean r2 = java.lang.Boolean.FALSE     // Catch:{ Throwable -> 0x021e }
            r0.setup(r1, r2)     // Catch:{ Throwable -> 0x021e }
            r8.mCallbacks = r3     // Catch:{ Throwable -> 0x021e }
            r4.start()     // Catch:{ Throwable -> 0x021e }
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = ".run stat: "
            r1.<init>(r2)
        L_0x01d4:
            com.uc.webview.export.business.a r2 = r8.b
            long r2 = r2.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            java.lang.String r0 = "bs_dec_od"
            com.uc.webview.export.business.a r1 = r8.b
            long r1 = r1.a
            java.lang.String r1 = java.lang.Long.toString(r1)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r0, r1)
            return
        L_0x01f0:
            com.uc.webview.export.business.a r0 = r8.b     // Catch:{ Throwable -> 0x021e }
            long r1 = com.uc.webview.export.business.a.c.e     // Catch:{ Throwable -> 0x021e }
            r0.a(r1)     // Catch:{ Throwable -> 0x021e }
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = ".run stat: "
            r1.<init>(r2)
            com.uc.webview.export.business.a r2 = r8.b
            long r2 = r2.a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            java.lang.String r0 = "bs_dec_od"
            com.uc.webview.export.business.a r1 = r8.b
            long r1 = r1.a
            java.lang.String r1 = java.lang.Long.toString(r1)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r0, r1)
            return
        L_0x021c:
            r0 = move-exception
            goto L_0x022f
        L_0x021e:
            com.uc.webview.export.business.a r0 = r8.b     // Catch:{ all -> 0x021c }
            long r1 = com.uc.webview.export.business.a.c.c     // Catch:{ all -> 0x021c }
            r0.a(r1)     // Catch:{ all -> 0x021c }
            java.lang.String r0 = a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = ".run stat: "
            r1.<init>(r2)
            goto L_0x01d4
        L_0x022f:
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = ".run stat: "
            r2.<init>(r3)
            com.uc.webview.export.business.a r3 = r8.b
            long r3 = r3.a
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = "bs_dec_od"
            com.uc.webview.export.business.a r2 = r8.b
            long r2 = r2.a
            java.lang.String r2 = java.lang.Long.toString(r2)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.business.setup.o.run():void");
    }

    public static void a(String str, String str2) {
        try {
            String extractDirPath = UCCore.getExtractDirPath(str, str2);
            File file = new File(str2);
            String decompressSourceHash = UCCyclone.getDecompressSourceHash(str2, file.length(), file.lastModified(), false);
            StringBuilder sb = new StringBuilder();
            sb.append(decompressSourceHash);
            sb.append(c);
            new File(extractDirPath, sb.toString()).createNewFile();
        } catch (Exception unused) {
        }
    }

    public static boolean b(String str, String str2) {
        try {
            File file = new File(UCCore.getExtractDirPath(str, str2));
            if (!file.exists() || j.a(file, c, false) == null) {
                return false;
            }
            return true;
        } catch (Exception unused) {
        }
        return false;
    }
}
