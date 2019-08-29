package defpackage;

import com.amap.bundle.logs.AMapLog;
import org.json.JSONObject;

/* renamed from: cha reason: default package */
/* compiled from: CloudUtil */
public final class cha {
    public static int a() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(lo.a().a((String) "splashscreen"));
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder(" get \"show_splash_in_days\" in \"splashscreen\" clound config Throwable：");
            sb.append(th.getMessage());
            AMapLog.error("basemap.splashscreen", "render", sb.toString());
            th.printStackTrace();
            jSONObject = null;
        }
        int i = 2;
        if (jSONObject == null) {
            return 2;
        }
        int optInt = jSONObject.optInt("show_splash_in_days");
        if (optInt > 0) {
            i = optInt;
        }
        return i;
    }

    public static long b() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(lo.a().a((String) "splashscreen"));
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder(" get \"splash_all_time\" in \"splashscreen\" clound config Throwable：");
            sb.append(th.getMessage());
            AMapLog.error("basemap.splashscreen", "render", sb.toString());
            th.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            return 8000;
        }
        long optInt = ((long) jSONObject.optInt("splash_all_time")) * 1000;
        if (optInt <= 0) {
            optInt = 8000;
        }
        return optInt;
    }

    public static int c() {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(lo.a().a((String) "splashscreen"));
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder(" get \"back_switching_time\" in \"splashscreen\" clound config Throwable：");
            sb.append(th.getMessage());
            AMapLog.error("basemap.splashscreen", "render", sb.toString());
            th.printStackTrace();
            jSONObject = null;
        }
        int i = 24;
        if (jSONObject == null) {
            return 24;
        }
        int optInt = jSONObject.optInt("back_switching_time");
        if (optInt > 0) {
            i = optInt;
        }
        return i;
    }
}
