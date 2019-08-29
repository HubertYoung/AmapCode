package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: dmr reason: default package */
/* compiled from: GetJSONStringAction */
public class dmr extends bkq {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String optString = jSONObject.optString("flag");
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put("jsonStr", a.jsonStr);
                if ("order".equals(optString)) {
                    a.callJs(waVar.a, jSONObject2.toString());
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
