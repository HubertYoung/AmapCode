package defpackage;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: cke reason: default package */
/* compiled from: XingPanAppStartRecordLog */
public class cke {
    private static final String a = "cke";
    /* access modifiers changed from: private */
    public static String b = "";
    /* access modifiers changed from: private */
    public static String c = "";
    /* access modifiers changed from: private */
    public static List<Long> d = new ArrayList(5);
    /* access modifiers changed from: private */
    public static long e;
    /* access modifiers changed from: private */
    public static long f;
    /* access modifiers changed from: private */
    public static long g;
    /* access modifiers changed from: private */
    public static long h;
    /* access modifiers changed from: private */
    public static long i;
    /* access modifiers changed from: private */
    public static long j;
    private static long k;

    public static void a(boolean z) {
        b = z ? "1" : "0";
    }

    public static void a(String str) {
        if (TextUtils.isEmpty(c)) {
            c = str;
        }
    }

    public static void c() {
        emv.g();
        g = System.currentTimeMillis();
    }

    public static void d() {
        emv.h();
        h = System.currentTimeMillis();
    }

    public static void e() {
        emv.i();
        i = System.currentTimeMillis();
    }

    public static void f() {
        emv.j();
        j = System.currentTimeMillis();
    }

    public static boolean g() {
        return f > 0;
    }

    public static void b(final bty bty) {
        if (kj.a) {
            if (f - e > 120000 || f == 120000 || h == 0 || i == 0 || g == 120000 || j - e < 0 || j - f < 0 || j - g < 0) {
                h();
            } else {
                aho.a(new Runnable() {
                    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0093, code lost:
                        if (r6 != null) goto L_0x0095;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0095, code lost:
                        r6.f(0);
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:17:0x009a, code lost:
                        return;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:23:0x00ad, code lost:
                        if (r6 == null) goto L_0x00b0;
                     */
                    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00b0, code lost:
                        return;
                     */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void run() {
                        /*
                            r7 = this;
                            org.json.JSONObject r0 = new org.json.JSONObject
                            r0.<init>()
                            r1 = 0
                            java.lang.String r2 = defpackage.cke.c     // Catch:{ JSONException -> 0x009d }
                            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ JSONException -> 0x009d }
                            if (r2 == 0) goto L_0x0015
                            java.lang.String r2 = "0"
                            defpackage.cke.c = r2     // Catch:{ JSONException -> 0x009d }
                        L_0x0015:
                            java.lang.String r2 = "from"
                            java.lang.String r3 = defpackage.cke.b     // Catch:{ JSONException -> 0x009d }
                            r0.put(r2, r3)     // Catch:{ JSONException -> 0x009d }
                            java.lang.String r2 = "type"
                            java.lang.String r3 = defpackage.cke.c     // Catch:{ JSONException -> 0x009d }
                            r0.put(r2, r3)     // Catch:{ JSONException -> 0x009d }
                            java.util.List r2 = defpackage.cke.d     // Catch:{ JSONException -> 0x009d }
                            int r2 = r2.size()     // Catch:{ JSONException -> 0x009d }
                            if (r2 <= 0) goto L_0x0051
                            java.util.List r2 = defpackage.cke.d     // Catch:{ JSONException -> 0x009d }
                            java.lang.Object r2 = r2.get(r1)     // Catch:{ JSONException -> 0x009d }
                            java.lang.Long r2 = (java.lang.Long) r2     // Catch:{ JSONException -> 0x009d }
                            long r2 = r2.longValue()     // Catch:{ JSONException -> 0x009d }
                            java.lang.String r4 = "app_startup"
                            r5 = 0
                            int r5 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
                            if (r5 <= 0) goto L_0x0049
                            goto L_0x004d
                        L_0x0049:
                            long r2 = defpackage.cke.e     // Catch:{ JSONException -> 0x009d }
                        L_0x004d:
                            r0.put(r4, r2)     // Catch:{ JSONException -> 0x009d }
                            goto L_0x005a
                        L_0x0051:
                            java.lang.String r2 = "app_startup"
                            long r3 = defpackage.cke.e     // Catch:{ JSONException -> 0x009d }
                            r0.put(r2, r3)     // Catch:{ JSONException -> 0x009d }
                        L_0x005a:
                            java.lang.String r2 = "app_mainmap"
                            long r3 = defpackage.cke.f     // Catch:{ JSONException -> 0x009d }
                            r0.put(r2, r3)     // Catch:{ JSONException -> 0x009d }
                            java.lang.String r2 = "controls_completed"
                            long r3 = defpackage.cke.g     // Catch:{ JSONException -> 0x009d }
                            r0.put(r2, r3)     // Catch:{ JSONException -> 0x009d }
                            java.lang.String r2 = "map_firstframe"
                            long r3 = defpackage.cke.h     // Catch:{ JSONException -> 0x009d }
                            r0.put(r2, r3)     // Catch:{ JSONException -> 0x009d }
                            java.lang.String r2 = "text"
                            long r3 = defpackage.cke.i     // Catch:{ JSONException -> 0x009d }
                            r0.put(r2, r3)     // Catch:{ JSONException -> 0x009d }
                            java.lang.String r2 = "map_completed"
                            long r3 = defpackage.cke.j     // Catch:{ JSONException -> 0x009d }
                            r0.put(r2, r3)     // Catch:{ JSONException -> 0x009d }
                            java.lang.String r2 = "P00001"
                            java.lang.String r3 = "B239"
                            com.amap.bundle.statistics.LogManager.actionLogV2(r2, r3, r0)
                            defpackage.cke.h()
                            bty r0 = r6
                            if (r0 == 0) goto L_0x00b0
                        L_0x0095:
                            bty r0 = r6
                            r0.f(r1)
                            return
                        L_0x009b:
                            r2 = move-exception
                            goto L_0x00b1
                        L_0x009d:
                            r2 = move-exception
                            r2.printStackTrace()     // Catch:{ all -> 0x009b }
                            java.lang.String r2 = "P00001"
                            java.lang.String r3 = "B239"
                            com.amap.bundle.statistics.LogManager.actionLogV2(r2, r3, r0)
                            defpackage.cke.h()
                            bty r0 = r6
                            if (r0 == 0) goto L_0x00b0
                            goto L_0x0095
                        L_0x00b0:
                            return
                        L_0x00b1:
                            java.lang.String r3 = "P00001"
                            java.lang.String r4 = "B239"
                            com.amap.bundle.statistics.LogManager.actionLogV2(r3, r4, r0)
                            defpackage.cke.h()
                            bty r0 = r6
                            if (r0 == 0) goto L_0x00c4
                            bty r0 = r6
                            r0.f(r1)
                        L_0x00c4:
                            throw r2
                        */
                        throw new UnsupportedOperationException("Method not decompiled: defpackage.cke.AnonymousClass1.run():void");
                    }
                });
            }
        }
    }

    public static void h() {
        b = "";
        c = "";
        d.clear();
        k = 0;
        e = 0;
        f = 0;
        g = 0;
        h = 0;
        i = 0;
        j = 0;
    }

    public static void a() {
        k = System.currentTimeMillis();
        d.add(Long.valueOf(System.currentTimeMillis()));
    }

    public static void b() {
        e = System.currentTimeMillis();
    }

    public static void a(bty bty) {
        long currentTimeMillis = System.currentTimeMillis();
        f = currentTimeMillis;
        emv.a(String.valueOf(currentTimeMillis - k));
        emv.f();
        if (bty != null) {
            bty.f(1);
        }
    }
}
