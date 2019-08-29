package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import org.json.JSONObject;

/* renamed from: bla reason: default package */
/* compiled from: GetSoftInputModeAction */
public class bla extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (!(a == null || waVar == null)) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                bid bid = a.mPageContext;
                if (bid instanceof AbstractBasePage) {
                    jSONObject2.put("value", ((AbstractBasePage) bid).getSoftInputMode());
                }
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
