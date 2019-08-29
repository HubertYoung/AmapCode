package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dmy reason: default package */
/* compiled from: OpenHotelDetailAction */
public class dmy extends bkq {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String str = a.oid;
            String str2 = a.type;
            String str3 = a.srcType;
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("_action", waVar.b);
                    jSONObject2.put("type", str2);
                    jSONObject2.put("src_type", str3);
                    jSONObject2.put("oid", str);
                    a.callJs(waVar.a, jSONObject2.toString());
                } catch (JSONException e) {
                    kf.a((Throwable) e);
                }
            }
        }
    }
}
