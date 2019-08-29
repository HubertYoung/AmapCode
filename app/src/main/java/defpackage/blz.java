package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: blz reason: default package */
/* compiled from: TriggerJSAction */
public class blz extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            a.setTriggerFunction(jSONObject.optString("function"));
        }
    }
}
