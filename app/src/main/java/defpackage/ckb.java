package defpackage;

import android.app.Application;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.util.AjxALCLog;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* renamed from: ckb reason: default package */
/* compiled from: LaunchMonitor */
public final class ckb {
    private static long a = 0;
    private static List<String> b = new ArrayList();
    private static boolean c = true;
    private static final StringBuilder d = new StringBuilder();

    public static void a(Application application) {
        if (ahs.a(application)) {
            a = System.currentTimeMillis();
        }
    }

    public static void a() {
        Ajx.getInstance().getMemoryStorage("basemap.amap-launch").setItem("Amap.Launch.Time", Long.valueOf(a));
    }

    public static void a(String str) {
        if (c) {
            StringBuilder sb = d;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(" : ");
            sb2.append(System.currentTimeMillis() - a);
            sb.append(sb2.toString());
            d.append(" #-># ");
        }
    }

    public static void b() {
        if (c) {
            long currentTimeMillis = System.currentTimeMillis() - a;
            String sb = d.toString();
            if (!TextUtils.isEmpty(sb)) {
                d.delete(0, sb.length());
                AMapLog.info("basemap.amap-launch", AjxALCLog.TAG_PERFORMANCE, sb);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("result", sb);
                    jSONObject.put("totalTime", currentTimeMillis);
                    LogManager.actionLogV2("P00001", "B293", jSONObject);
                } catch (Exception unused) {
                }
            }
        }
    }

    public static void c() {
        c = false;
    }
}
