package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ble reason: default package */
/* compiled from: JsCallBackAction */
public class ble extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put("params", jSONObject.opt("params"));
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
