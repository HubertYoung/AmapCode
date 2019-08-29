package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import org.json.JSONObject;

/* renamed from: cka reason: default package */
/* compiled from: ForeAndBackTimeRecorder */
public final class cka {
    private static long a;
    private static long b;
    private static long c;
    private static volatile c d;

    public static void a() {
        if (d == null) {
            AnonymousClass1 r0 = new c() {
                public final void a() {
                    cka.b();
                }

                public final void b() {
                    cka.c();
                }

                public final void c() {
                    cka.c();
                }
            };
            d = r0;
            drm.a((a) r0);
        }
    }

    static /* synthetic */ void b() {
        a = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("--onForeground: ");
        sb.append(a);
        AMapLog.d("ForeAndBackTimeRecorder", sb.toString());
    }

    static /* synthetic */ void c() {
        if (0 != a) {
            long currentTimeMillis = System.currentTimeMillis();
            b = currentTimeMillis;
            long j = currentTimeMillis - a;
            c = j;
            if (j >= 0) {
                StringBuilder sb = new StringBuilder("--onBackground:");
                sb.append(b);
                sb.append(" time is ");
                sb.append(c);
                AMapLog.d("ForeAndBackTimeRecorder", sb.toString());
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("used_time", String.valueOf(c));
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                LogManager.actionLogV2("P00429", "B001", jSONObject);
                a = 0;
                b = 0;
            }
        }
    }
}
