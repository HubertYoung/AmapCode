package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: bls reason: default package */
/* compiled from: SetWebLongpressEnableAction */
public class bls extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            a.mBaseWebView.a(jSONObject.optBoolean("enable"));
        }
    }
}
