package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import org.json.JSONObject;

@Deprecated
/* renamed from: bkp reason: default package */
/* compiled from: BarHeightAction */
public class bkp extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put("titleBar", (double) AMapAppGlobal.getApplication().getResources().getDimension(R.dimen.title_bar_default_height));
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
            a.callJs(waVar.a, jSONObject2.toString());
        }
    }
}
