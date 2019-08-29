package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.js.action.LifeEntity;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dns reason: default package */
/* compiled from: LifeServiceCallBackAction */
public class dns extends bkq {
    public final void a(JSONObject jSONObject, wa waVar) {
        if (a() != null) {
            try {
                if ("openCouponDetail".equals(jSONObject.optString("subAciton"))) {
                    LifeEntity lifeEntity = a;
                    JsAdapter a = a();
                    if (a != null) {
                        String str = lifeEntity.jsonStr;
                        if (!TextUtils.isEmpty(str)) {
                            try {
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("_action", waVar.b);
                                jSONObject2.put("json", str);
                                a.callJs(waVar.a, jSONObject2.toString());
                            } catch (JSONException e) {
                                kf.a((Throwable) e);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
    }
}
