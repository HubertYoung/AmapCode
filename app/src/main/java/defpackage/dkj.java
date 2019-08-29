package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dkj reason: default package */
/* compiled from: FaceAuthQueryResponse */
public final class dkj extends dkm {
    public dki a = new dki();

    public final void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                dki dki = new dki();
                if (optJSONObject != null) {
                    dki.a = optJSONObject.optInt("status");
                }
                this.a = dki;
            }
        }
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        dki dki = this.a;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("status", dki.a);
        json.put("data", jSONObject);
        return json;
    }
}
