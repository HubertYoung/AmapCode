package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.server.aos.serverkey;
import org.json.JSONObject;

/* renamed from: bmk reason: default package */
/* compiled from: xxEncodeAction */
public class bmk extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String amapEncode = serverkey.amapEncode(jSONObject.optString("text"));
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("in", amapEncode);
                jSONObject3.put("ent", 2);
                jSONObject2.put("result", jSONObject3);
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
