package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.jsadapter.JsAdapter.a;
import org.json.JSONObject;

/* renamed from: blm reason: default package */
/* compiled from: RegistRightButtonNewAction */
public class blm extends vz {
    public final void a(final JSONObject jSONObject, final wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            a.setActionConfigurable(new a() {
                public final String a() {
                    return jSONObject.optString("buttonText");
                }

                public final boolean b() {
                    JSONObject optJSONObject = jSONObject.optJSONObject("function");
                    if (optJSONObject == null || waVar == null) {
                        return false;
                    }
                    blm.this.a().send(new String[]{optJSONObject.toString(), waVar.a});
                    return true;
                }
            });
        }
    }
}
