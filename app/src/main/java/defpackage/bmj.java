package defpackage;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.server.aos.serverkey;
import org.json.JSONObject;

/* renamed from: bmj reason: default package */
/* compiled from: xxDecodeAction */
public class bmj extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            String amapDecode = serverkey.amapDecode(jSONObject.optString("text"));
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(LogItem.MM_C24_K4_DECODE_ERR, amapDecode);
                jSONObject2.put("result", jSONObject3);
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
