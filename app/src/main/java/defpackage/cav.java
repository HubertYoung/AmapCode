package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import org.json.JSONObject;

/* renamed from: cav reason: default package */
/* compiled from: BaseExtendWebViewPresenter */
public class cav extends ajf {
    public void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        JsAdapter b = this.a.b();
        if (i == 1001 && resultType == ResultType.OK && pageBundle != null) {
            JSONObject jSONObject = (JSONObject) pageBundle.getObject("data");
            if (jSONObject != null) {
                b.callJs("callback", jSONObject.toString());
            }
        }
        if (i == 1002 && pageBundle != null) {
            JSONObject jSONObject2 = (JSONObject) pageBundle.getObject("data");
            if (jSONObject2 != null) {
                b.callJs("callback", jSONObject2.toString());
            }
        }
        if (i == 1003 && pageBundle != null) {
            JSONObject jSONObject3 = (JSONObject) pageBundle.getObject("data");
            if (jSONObject3 != null) {
                b.callJs("callback", jSONObject3.toString());
            }
        }
    }
}
