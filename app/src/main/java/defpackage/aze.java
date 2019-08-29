package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: aze reason: default package */
/* compiled from: GetRouteCommuteTypeAction */
public class aze extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", waVar.b);
                if (jSONObject == null) {
                    jSONObject2.put("value", "0");
                    a.callJs(waVar.a, jSONObject2.toString());
                    return;
                }
                String g = azi.g();
                if (TextUtils.isEmpty(g)) {
                    g = "0";
                }
                jSONObject2.put("value", g);
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
