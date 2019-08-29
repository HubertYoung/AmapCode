package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ctp reason: default package */
/* compiled from: GetPoiRangeAction */
public final class ctp extends vz {
    public wa a;
    public JSONObject b;

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        if (this.b == null) {
            this.a = waVar;
            return;
        }
        a(waVar, this.b);
        this.b = null;
    }

    public final void a(wa waVar, JSONObject jSONObject) {
        if (waVar != null && jSONObject != null) {
            JsAdapter a2 = a();
            if (a2 != null) {
                try {
                    jSONObject.put("_action", waVar.b);
                    a2.callJs(waVar.a, jSONObject.toString());
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }
        }
    }
}
