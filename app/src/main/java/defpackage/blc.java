package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: blc reason: default package */
/* compiled from: GetWebDataAction */
public class blc extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put("data", bic.a((String) "webdata").get(jSONObject.optString("key")));
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
