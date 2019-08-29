package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: sb reason: default package */
/* compiled from: VoiceUtils */
public final class sb {
    public static JSONObject a(String str, int i, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("methodID", str);
            jSONObject2.put("token", i);
            if (jSONObject == null) {
                jSONObject = new JSONObject();
            }
            jSONObject.put("from", 1);
            jSONObject2.put("jsonParam", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject2;
    }

    public static JSONObject b(String str, int i, JSONObject jSONObject) {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            return aia.a(str, i, jSONObject);
        }
        return null;
    }
}
