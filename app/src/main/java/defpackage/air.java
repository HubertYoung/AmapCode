package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: air reason: default package */
/* compiled from: VoiceCommandJSONUtil */
public final class air {
    public static JSONObject a(String str, int i, JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("methodID", str);
            jSONObject2.put("token", i);
            if (jSONObject == null) {
                jSONObject = new JSONObject();
            }
            jSONObject.put("from", 0);
            jSONObject2.put("jsonParam", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject2;
    }
}
