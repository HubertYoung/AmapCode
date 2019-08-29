package com.alipay.sdk.sys;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.data.c;
import com.ta.utdid2.device.UTDevice;
import java.io.File;

public final class b {
    private static b b;
    public Context a;

    private b() {
    }

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    private Context d() {
        return this.a;
    }

    public final void a(Context context) {
        this.a = context.getApplicationContext();
    }

    private static c e() {
        return c.a();
    }

    public static boolean b() {
        String[] strArr = {"/system/xbin/", "/system/bin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < 5) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(strArr[i]);
                sb.append("su");
                String sb2 = sb.toString();
                if (new File(sb2).exists()) {
                    String a2 = a(new String[]{"ls", "-l", sb2});
                    if (TextUtils.isEmpty(a2) || a2.indexOf(DictionaryKeys.ENV_ROOT) == a2.lastIndexOf(DictionaryKeys.ENV_ROOT)) {
                        return false;
                    }
                    return true;
                }
                i++;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0035, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0037, code lost:
        r2 = "";
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0031 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0010] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.lang.String[] r4) {
        /*
            java.lang.String r0 = ""
            r1 = 0
            java.lang.ProcessBuilder r2 = new java.lang.ProcessBuilder     // Catch:{ Throwable -> 0x003f, all -> 0x0039 }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x003f, all -> 0x0039 }
            r4 = 0
            r2.redirectErrorStream(r4)     // Catch:{ Throwable -> 0x003f, all -> 0x0039 }
            java.lang.Process r4 = r2.start()     // Catch:{ Throwable -> 0x003f, all -> 0x0039 }
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ Throwable -> 0x0037, all -> 0x0035 }
            java.io.OutputStream r2 = r4.getOutputStream()     // Catch:{ Throwable -> 0x0037, all -> 0x0035 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0037, all -> 0x0035 }
            java.io.DataInputStream r2 = new java.io.DataInputStream     // Catch:{ Throwable -> 0x0037, all -> 0x0035 }
            java.io.InputStream r3 = r4.getInputStream()     // Catch:{ Throwable -> 0x0037, all -> 0x0035 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0037, all -> 0x0035 }
            java.lang.String r2 = r2.readLine()     // Catch:{ Throwable -> 0x0037, all -> 0x0035 }
            java.lang.String r0 = "exit\n"
            r1.writeBytes(r0)     // Catch:{ Throwable -> 0x0031, all -> 0x0035 }
            r1.flush()     // Catch:{ Throwable -> 0x0031, all -> 0x0035 }
            r4.waitFor()     // Catch:{ Throwable -> 0x0031, all -> 0x0035 }
        L_0x0031:
            r4.destroy()     // Catch:{ Exception -> 0x0042 }
            goto L_0x0042
        L_0x0035:
            r0 = move-exception
            goto L_0x003b
        L_0x0037:
            r2 = r0
            goto L_0x0031
        L_0x0039:
            r0 = move-exception
            r4 = r1
        L_0x003b:
            r4.destroy()     // Catch:{ Exception -> 0x003e }
        L_0x003e:
            throw r0
        L_0x003f:
            r2 = r0
            r4 = r1
            goto L_0x0031
        L_0x0042:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.sys.b.a(java.lang.String[]):java.lang.String");
    }

    public final String c() {
        try {
            return UTDevice.getUtdid(this.a);
        } catch (Throwable th) {
            a.a((String) com.alipay.sdk.app.statistic.c.e, (String) com.alipay.sdk.app.statistic.c.k, th);
            return "";
        }
    }
}
