package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bli reason: default package */
/* compiled from: NoticeH5Action */
public class bli extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (!(a == null || jSONObject == null)) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("content", jSONObject.opt("params").toString());
                jSONObject2.put("_action", waVar.b);
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
