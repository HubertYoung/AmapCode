package defpackage;

import android.content.Context;
import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: bdq reason: default package */
/* compiled from: JsActionImmersive */
public class bdq extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        int a2 = (!euk.a() || a.mPageContext == null) ? 0 : euk.a((Context) a.mPageContext.getActivity());
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("height", a2);
            jSONObject2.put("_action", waVar.b);
            jSONObject2.put("support", euk.a());
            a.callJs(waVar.a, jSONObject2.toString());
        } catch (Exception unused) {
        }
    }
}
