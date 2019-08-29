package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONObject;

/* renamed from: dne reason: default package */
/* compiled from: OpenSchemeAction */
public class dne extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null && jSONObject != null) {
            String optString = jSONObject.optString("url");
            if (!TextUtils.isEmpty(optString)) {
                a.mPageContext.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(optString)));
            }
        }
    }
}
