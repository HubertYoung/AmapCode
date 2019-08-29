package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: bkw reason: default package */
/* compiled from: GetFeatureListAction */
public class bkw extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("nearbyStore", 1);
                jSONObject2.put("featrueList", jSONObject3);
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
