package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.PageBundle;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: blb reason: default package */
/* compiled from: GetTransparentParamsAction */
public class blb extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            String str = "";
            if (a.mPageContext != null) {
                PageBundle arguments = a.mPageContext.getArguments();
                if (arguments != null) {
                    str = arguments.getString(H5Param.LONG_TRANSPARENT);
                }
            }
            try {
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put(H5Param.LONG_TRANSPARENT, str);
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
