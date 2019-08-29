package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dnp reason: default package */
/* compiled from: DownloadUrlAction */
public class dnp extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String optString = jSONObject.optString("url");
            String optString2 = jSONObject.optString("poiname", "");
            if (jSONObject.optInt("event") == 1) {
                drj.a();
                drj.b(optString);
                return;
            }
            if (jSONObject.optInt("event") == 0) {
                if (!aaw.c(AMapAppGlobal.getApplication())) {
                    ToastHelper.showToast(AMapAppGlobal.getApplication().getText(R.string.ic_net_error_tipinfo));
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("_action", waVar.b);
                        jSONObject2.put("status", "0");
                        jSONObject2.put("url", optString);
                        jSONObject2.put("downSize", "0");
                        jSONObject2.put("totalSize", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    a.callJs(waVar.a, jSONObject2.toString());
                    return;
                }
                drj a2 = drj.a();
                a2.g = optString2;
                String a3 = drj.a(optString);
                b bVar = new b(a2.a, a3, optString);
                bjg bjg = new bjg(a3);
                bjg.setUrl(optString);
                bjh.a().a(bjg, (bjf) bVar);
                drj.c.put(optString, a3);
            }
        }
    }
}
