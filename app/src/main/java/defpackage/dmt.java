package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dmt reason: default package */
/* compiled from: InitPaymentAction */
public class dmt extends bkq {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String str = a.phone;
            String str2 = a.request;
            String str3 = a.result;
            if (!TextUtils.isEmpty(str3)) {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("_action", waVar.b);
                    jSONObject2.put("phoneNumber", str);
                    jSONObject2.put("checkResults", str3);
                    jSONObject2.put("requestData", str2);
                    a.callJs(waVar.a, jSONObject2.toString());
                } catch (JSONException e) {
                    kf.a((Throwable) e);
                }
            }
        }
    }
}
