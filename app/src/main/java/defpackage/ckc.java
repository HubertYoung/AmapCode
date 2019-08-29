package defpackage;

import com.amap.bundle.statistics.LogManager;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ckc reason: default package */
/* compiled from: RecordingStartTime */
public final class ckc {
    @SuppressFBWarnings({"MS_CANNOT_BE_FINAL"})
    public static long a = 0;
    private static String b = "RecordingStartTime";
    private static long c;
    private static long d;

    public static long a() {
        return c;
    }

    public static void a(String str, long j) {
        if (kj.a && j <= 300000 && j > 0) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str);
                jSONObject.put("time", String.valueOf(j));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B200", jSONObject);
        }
    }

    public static void b(String str, long j) {
        if (kj.a) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str);
                jSONObject.put("time", String.valueOf(j));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00000", "B000", jSONObject);
        }
    }

    public static void b() {
        a = 0;
        c = 0;
        d = 0;
    }
}
