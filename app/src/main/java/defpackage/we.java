package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: we reason: default package */
/* compiled from: RegisterCallbackAction */
public class we extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            a.setDefaultCallback(waVar.a);
        }
    }
}
