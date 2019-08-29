package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dmo reason: default package */
/* compiled from: GetCarInfoListAction */
public class dmo extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put("status", 0);
                String str = null;
                ato ato = (ato) a.a.a(ato.class);
                if (ato != null) {
                    str = ato.a().c(-1);
                }
                if (TextUtils.isEmpty(str)) {
                    jSONObject2.put("data", new JSONArray());
                } else {
                    jSONObject2.put("data", new JSONArray(str));
                }
            } catch (JSONException e) {
                kf.a((Throwable) e);
                a.callJs(waVar.a, jSONObject2.toString());
            }
            a.callJs(waVar.a, jSONObject2.toString());
        }
    }
}
