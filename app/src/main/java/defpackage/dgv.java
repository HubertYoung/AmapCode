package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dgv reason: default package */
/* compiled from: DriveUserReport */
public final class dgv {
    public static JSONObject a(String str, String str2, String str3, String str4) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("touchType", str);
            jSONObject.put("nameType", str2);
            jSONObject.put("enterType", str3);
            jSONObject.put("netType", str4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
