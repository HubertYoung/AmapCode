package defpackage;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.amap.app.AMapAppGlobal;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bkt reason: default package */
/* compiled from: GetAjxStorageItemAction */
public class bkt extends vz {
    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a = a();
        if (a != null && a.mPageContext != null) {
            String optString = jSONObject.optString("key");
            String optString2 = jSONObject.optString("namespace");
            if (!TextUtils.isEmpty(optString2) && !TextUtils.isEmpty(optString)) {
                SharedPreferences sharedPreferences = AMapAppGlobal.getApplication().getSharedPreferences(optString2, 0);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put("value", sharedPreferences.getString(optString, ""));
                a.callJs(waVar.a, jSONObject2.toString());
            }
        }
    }
}
