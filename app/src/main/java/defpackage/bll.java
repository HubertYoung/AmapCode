package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.jsadapter.JsAdapter.a;
import org.json.JSONObject;

/* renamed from: bll reason: default package */
/* compiled from: RegistRightButtonAction */
public class bll extends vz {
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
                    String[] strArr = {optJSONObject.toString(), waVar.a};
                    JsAdapter a2 = bll.this.a();
                    if (a2 != null) {
                        a2.send(strArr);
                    }
                    return true;
                }
            });
        }
    }
}
