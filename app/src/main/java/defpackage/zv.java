package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: zv reason: default package */
/* compiled from: SceneModel */
public final class zv {
    public String a;
    public long b;
    public long c;
    public boolean d;

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.a);
            jSONObject.put("upLink", this.b);
            jSONObject.put("downLink", this.c);
            jSONObject.put("isAccs", this.d ? "1" : "0");
        } catch (JSONException unused) {
        }
        return jSONObject;
    }
}
