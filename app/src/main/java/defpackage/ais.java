package defpackage;

import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ais reason: default package */
/* compiled from: VoiceMessageUtil */
public final class ais {
    public static String a(boolean z) {
        JSONObject a = a((String) "amap_available");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", z ? 1 : 0);
            a.put("data", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return a.toString();
    }

    public static String b(boolean z) {
        JSONObject a = a((String) "is_main_activity");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("status", z ^ true ? 1 : 0);
            a.put("data", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return a.toString();
    }

    private static JSONObject a(String str) {
        if (TextUtils.isEmpty(str)) {
            return new JSONObject();
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("function", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
