package defpackage;

import org.json.JSONObject;

/* renamed from: doa reason: default package */
/* compiled from: ShowVoiceGuideAction */
public class doa extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (a() != null) {
            JSONObject optJSONObject = jSONObject.optJSONObject("poiInfo");
            if (optJSONObject != null) {
                kv.a(optJSONObject.toString());
            }
        }
    }
}
