package defpackage;

import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: aqt reason: default package */
/* compiled from: LuBanHotWordResult */
public final class aqt {
    public String a;
    public String b;
    public String c;
    public String d;

    public final boolean a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("words");
        if (optJSONArray == null) {
            return false;
        }
        int length = optJSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                this.a = optJSONObject.optString("name");
                this.b = optJSONObject.optString("redirect_value");
                this.c = optJSONObject.optString("color_main");
                this.d = optJSONObject.optString("color_search");
            }
        }
        return true;
    }
}
