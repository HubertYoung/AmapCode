package defpackage;

import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dkh reason: default package */
/* compiled from: FaceAuthInitResponse */
public final class dkh extends dkm {
    public dkg a = new dkg();

    public final void fromJson(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            super.fromJson(jSONObject);
            JSONObject optJSONObject = jSONObject.optJSONObject("data");
            if (optJSONObject != null) {
                dkg dkg = new dkg();
                if (optJSONObject != null) {
                    dkg.a = optJSONObject.optString("zim_id");
                    dkg.b = optJSONObject.optString("sub_code");
                    dkg.c = optJSONObject.optString("sub_msg");
                }
                this.a = dkg;
            }
        }
    }

    public final JSONObject toJson() throws JSONException {
        JSONObject json = super.toJson();
        dkg dkg = this.a;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("zim_id", dkg.a);
        jSONObject.put("sub_code", dkg.b);
        jSONObject.put("sub_msg", dkg.c);
        json.put("data", jSONObject);
        return json;
    }
}
