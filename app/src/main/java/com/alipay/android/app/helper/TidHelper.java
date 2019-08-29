package com.alipay.android.app.helper;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.tid.c;
import com.alipay.sdk.util.a;
import org.json.JSONObject;

public class TidHelper {
    private static boolean a = false;

    public static Tid loadTID(Context context) {
        a(context);
        Tid a2 = a(c.a(context));
        if (a2 != null) {
            new StringBuilder("TidHelper:::loadTID > ").append(a2.toString());
        }
        return a2;
    }

    public static synchronized String getTIDValue(Context context) {
        synchronized (TidHelper.class) {
            Tid loadOrCreateTID = loadOrCreateTID(context);
            if (loadOrCreateTID != null) {
                if (!loadOrCreateTID.isEmpty()) {
                    String tid = loadOrCreateTID.getTid();
                    return tid;
                }
            }
            return "";
        }
    }

    public static boolean resetTID(Context context) throws Exception {
        Tid tid;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new Exception("不能在主线程中调用此方法");
        }
        a(context);
        clearTID(context);
        try {
            tid = b(context);
        } catch (Throwable unused) {
            tid = null;
        }
        return tid != null && !tid.isEmpty();
    }

    public static void clearTID(Context context) {
        c a2 = c.a(context);
        String.format("TidStorage::delete > %s，%s，%s，%s，%s", new Object[]{a2.j, a2.k, Long.valueOf(a2.l), a2.m, a2.n});
        a2.d();
    }

    public static String getIMEI(Context context) {
        a(context);
        return a.a(context).b();
    }

    public static String getIMSI(Context context) {
        a(context);
        return a.a(context).a();
    }

    public static String getVirtualImei(Context context) {
        a(context);
        com.alipay.sdk.data.c.a();
        return com.alipay.sdk.data.c.b();
    }

    public static String getVirtualImsi(Context context) {
        a(context);
        com.alipay.sdk.data.c.a();
        return com.alipay.sdk.data.c.c();
    }

    private static void a(Context context) {
        if (context != null) {
            b a2 = b.a();
            com.alipay.sdk.data.c.a();
            a2.a(context);
        }
    }

    private static Tid b(Context context) throws Exception {
        try {
            com.alipay.sdk.packet.b a2 = new com.alipay.sdk.packet.impl.c().a(context);
            if (a2 == null) {
                return null;
            }
            JSONObject jSONObject = new JSONObject(a2.b);
            c a3 = c.a(context);
            String optString = jSONObject.optString("tid");
            String string = jSONObject.getString("client_key");
            if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(string)) {
                a3.a(optString, string);
            }
            return a(a3);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Tid a(c cVar) {
        if (cVar == null || cVar.c()) {
            return null;
        }
        Tid tid = new Tid();
        tid.setTid(cVar.a());
        tid.setTidSeed(cVar.b());
        tid.setTimestamp(cVar.l);
        return tid;
    }

    public static Tid loadLocalTid(Context context) {
        if (c.a(context).c()) {
            return null;
        }
        Tid tid = new Tid();
        tid.setTid(c.a(context).a());
        tid.setTidSeed(c.a(context).b());
        tid.setTimestamp(c.a(context).l);
        return tid;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0046, code lost:
        return r5;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:8:0x0023 */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003f A[SYNTHETIC, Splitter:B:18:0x003f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.alipay.android.app.helper.Tid loadOrCreateTID(android.content.Context r5) {
        /*
            java.lang.Class<com.alipay.android.app.helper.TidHelper> r0 = com.alipay.android.app.helper.TidHelper.class
            monitor-enter(r0)
            if (r5 != 0) goto L_0x0023
            java.lang.String r1 = "tid"
            java.lang.String r2 = "tid_context_null"
            java.lang.String r3 = ""
            if (r5 == 0) goto L_0x0023
            com.alipay.sdk.app.statistic.c r4 = new com.alipay.sdk.app.statistic.c     // Catch:{ Throwable -> 0x0023 }
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0023 }
            r4.a(r1, r2, r3)     // Catch:{ Throwable -> 0x0023 }
            java.lang.String r1 = ""
            java.lang.String r1 = r4.a(r1)     // Catch:{ Throwable -> 0x0023 }
            com.alipay.sdk.app.statistic.a.a(r5, r1)     // Catch:{ Throwable -> 0x0023 }
            goto L_0x0023
        L_0x0021:
            r5 = move-exception
            goto L_0x0047
        L_0x0023:
            a(r5)     // Catch:{ all -> 0x0021 }
            com.alipay.android.app.helper.Tid r1 = loadTID(r5)     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x0032
            boolean r2 = r1.isEmpty()     // Catch:{ all -> 0x0021 }
            if (r2 == 0) goto L_0x0044
        L_0x0032:
            android.os.Looper r2 = android.os.Looper.myLooper()     // Catch:{ all -> 0x0021 }
            android.os.Looper r3 = android.os.Looper.getMainLooper()     // Catch:{ all -> 0x0021 }
            if (r2 != r3) goto L_0x003f
            r5 = 0
            monitor-exit(r0)
            return r5
        L_0x003f:
            com.alipay.android.app.helper.Tid r5 = b(r5)     // Catch:{ Throwable -> 0x0044 }
            goto L_0x0045
        L_0x0044:
            r5 = r1
        L_0x0045:
            monitor-exit(r0)
            return r5
        L_0x0047:
            monitor-exit(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.app.helper.TidHelper.loadOrCreateTID(android.content.Context):com.alipay.android.app.helper.Tid");
    }
}
