package defpackage;

import android.net.Uri;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bld reason: default package */
/* compiled from: JsAuthorizeWhiteListUpdateAction */
public class bld extends vz {
    private wa a;
    private a b = new a() {
        public final void a(int i, String str) {
            JsAdapter a2 = bld.this.a();
            if (a2 != null) {
                bld.a(bld.this, a2, i, str);
            }
        }
    };

    public final void a(JSONObject jSONObject, wa waVar) {
        String str;
        this.a = waVar;
        JsAdapter a2 = a();
        if (a2 != null) {
            try {
                str = Uri.parse(a2.mBaseWebView.c()).getHost();
                try {
                    wi.a(str, jSONObject.optString("needVersion"), this.b);
                } catch (Exception e) {
                    e = e;
                    StringBuilder sb = new StringBuilder("jsaction call updateWhiteList error: ");
                    sb.append(e.getMessage());
                    sb.append(", host = ");
                    sb.append(str);
                    AMapLog.e("jsauth", sb.toString());
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e = e2;
                str = "";
                StringBuilder sb2 = new StringBuilder("jsaction call updateWhiteList error: ");
                sb2.append(e.getMessage());
                sb2.append(", host = ");
                sb2.append(str);
                AMapLog.e("jsauth", sb2.toString());
                e.printStackTrace();
            }
        }
    }

    static /* synthetic */ void a(bld bld, JsAdapter jsAdapter, int i, String str) {
        if (bld.a != null) {
            StringBuilder sb = new StringBuilder("notifyJs code = ");
            sb.append(i);
            sb.append(", newVersion = ");
            sb.append(str);
            AMapLog.i("jsauth", sb.toString());
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("result", i);
                jSONObject.put("newVersion", str);
                jSONObject.put("_action", bld.a.b);
                jsAdapter.callJs(bld.a.a, jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
