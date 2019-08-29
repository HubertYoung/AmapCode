package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: zr reason: default package */
/* compiled from: InfoCollecttor */
public final class zr {
    Map<String, String> a;
    Map<String, String> b;
    Map<String, String> c;
    private Map<String, String> d;

    @NonNull
    private JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("request", a(this.a));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jSONObject.put(ModuleLongLinkService.CALLBACK_KEY_RESPONSE, a(this.b));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        try {
            jSONObject.put("details", a(this.c));
        } catch (JSONException e3) {
            e3.printStackTrace();
        }
        try {
            jSONObject.put("extra", a(this.d));
        } catch (JSONException e4) {
            e4.printStackTrace();
        }
        return jSONObject;
    }

    public final String toString() {
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(a());
        return jSONArray.toString();
    }

    @NonNull
    private static JSONObject a(Map<String, String> map) {
        JSONObject jSONObject = new JSONObject();
        if (map != null && map.size() > 0) {
            for (Entry next : map.entrySet()) {
                if (next != null) {
                    try {
                        jSONObject.put((String) next.getKey(), next.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jSONObject;
    }
}
