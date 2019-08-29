package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bks reason: default package */
/* compiled from: CommercialSubscribeAction */
public class bks extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        boolean z;
        JsAdapter a = a();
        if (a != null) {
            try {
                z = jSONObject.getBoolean("flag");
            } catch (JSONException e) {
                kf.a((Throwable) e);
                z = false;
            }
            a.getBundle().putBoolean("mBSubScribe", z);
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", "_commercialSubscribe");
                jSONObject2.put("message", "success");
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
    }
}
