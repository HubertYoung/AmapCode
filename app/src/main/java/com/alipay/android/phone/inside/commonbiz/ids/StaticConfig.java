package com.alipay.android.phone.inside.commonbiz.ids;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import java.util.Map;

public class StaticConfig {
    static boolean a = false;
    static Map<String, String> b = null;
    static InsideChannel c = null;
    static String d = "insideModel";
    static String e = "productName";
    static String f = "productVersion";
    static String g = "productID";
    static String h = "innerVersion";
    static String i = "channel";
    static String j = "insideChannel";
    static String k = "barcodeDegrade";
    static String l = "sgAuthCode";

    public static String a() {
        String a2 = a(l);
        return TextUtils.isEmpty(a2) ? "" : a2;
    }

    public static String b() {
        return a(d);
    }

    public static String c() {
        return a(f);
    }

    public static String d() {
        return a(g);
    }

    public static String e() {
        return a(i);
    }

    public static String f() {
        return a(h);
    }

    public static InsideChannel g() {
        if (c == null) {
            c = InsideChannel.getChannel(a(j));
        }
        return c;
    }

    public static String a(String str) {
        return h().get(str);
    }

    public static Map<String, String> h() {
        a((Context) LauncherApplication.a());
        return b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0077 A[SYNTHETIC, Splitter:B:36:0x0077] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x008d A[SYNTHETIC, Splitter:B:49:0x008d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void a(android.content.Context r7) {
        /*
            java.lang.Class<com.alipay.android.phone.inside.commonbiz.ids.StaticConfig> r0 = com.alipay.android.phone.inside.commonbiz.ids.StaticConfig.class
            monitor-enter(r0)
            boolean r1 = a     // Catch:{ all -> 0x009c }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)
            return
        L_0x0009:
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ all -> 0x009c }
            r1.<init>()     // Catch:{ all -> 0x009c }
            b = r1     // Catch:{ all -> 0x009c }
            android.content.res.Resources r7 = r7.getResources()     // Catch:{ all -> 0x009c }
            r1 = 0
            android.content.res.AssetManager r7 = r7.getAssets()     // Catch:{ Throwable -> 0x0066, all -> 0x0061 }
            java.lang.String r2 = "alipay_inside_channel.config"
            java.io.InputStream r7 = r7.open(r2)     // Catch:{ Throwable -> 0x0066, all -> 0x0061 }
            java.util.Properties r1 = new java.util.Properties     // Catch:{ Throwable -> 0x005f }
            r1.<init>()     // Catch:{ Throwable -> 0x005f }
            r1.load(r7)     // Catch:{ Throwable -> 0x005f }
            java.util.Set r2 = r1.keySet()     // Catch:{ Throwable -> 0x005f }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ Throwable -> 0x005f }
        L_0x002f:
            boolean r3 = r2.hasNext()     // Catch:{ Throwable -> 0x005f }
            if (r3 == 0) goto L_0x0049
            java.lang.Object r3 = r2.next()     // Catch:{ Throwable -> 0x005f }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x005f }
            java.util.Map<java.lang.String, java.lang.String> r4 = b     // Catch:{ Throwable -> 0x005f }
            java.lang.String r5 = ""
            java.lang.String r5 = r1.getProperty(r3, r5)     // Catch:{ Throwable -> 0x005f }
            r4.put(r3, r5)     // Catch:{ Throwable -> 0x005f }
            goto L_0x002f
        L_0x0049:
            r1 = 1
            a = r1     // Catch:{ Throwable -> 0x005f }
            if (r7 == 0) goto L_0x0088
            r7.close()     // Catch:{ Throwable -> 0x0053 }
            monitor-exit(r0)
            return
        L_0x0053:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x009c }
            java.lang.String r2 = "inside"
            r1.c(r2, r7)     // Catch:{ all -> 0x009c }
            monitor-exit(r0)
            return
        L_0x005f:
            r1 = move-exception
            goto L_0x006a
        L_0x0061:
            r7 = move-exception
            r6 = r1
            r1 = r7
            r7 = r6
            goto L_0x008b
        L_0x0066:
            r7 = move-exception
            r6 = r1
            r1 = r7
            r7 = r6
        L_0x006a:
            com.alipay.android.phone.inside.log.api.ex.ExceptionLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.e()     // Catch:{ all -> 0x008a }
            java.lang.String r3 = "commonbiz"
            java.lang.String r4 = "LoadConfigFileEx"
            r2.a(r3, r4, r1)     // Catch:{ all -> 0x008a }
            if (r7 == 0) goto L_0x0088
            r7.close()     // Catch:{ Throwable -> 0x007c }
            monitor-exit(r0)
            return
        L_0x007c:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x009c }
            java.lang.String r2 = "inside"
            r1.c(r2, r7)     // Catch:{ all -> 0x009c }
            monitor-exit(r0)
            return
        L_0x0088:
            monitor-exit(r0)
            return
        L_0x008a:
            r1 = move-exception
        L_0x008b:
            if (r7 == 0) goto L_0x009b
            r7.close()     // Catch:{ Throwable -> 0x0091 }
            goto L_0x009b
        L_0x0091:
            r7 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x009c }
            java.lang.String r3 = "inside"
            r2.c(r3, r7)     // Catch:{ all -> 0x009c }
        L_0x009b:
            throw r1     // Catch:{ all -> 0x009c }
        L_0x009c:
            r7 = move-exception
            monitor-exit(r0)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.commonbiz.ids.StaticConfig.a(android.content.Context):void");
    }

    public static boolean i() {
        return g() != InsideChannel.Tao;
    }

    public static boolean j() {
        return g() == InsideChannel.Alipay;
    }

    public static boolean k() {
        return g() == InsideChannel.Tao;
    }

    public static boolean l() {
        return g() == InsideChannel.Merchant;
    }
}
