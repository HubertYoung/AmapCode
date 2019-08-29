package defpackage;

import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.miniapp.monitor.biz.logmonitor.util.upload.UploadConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bku reason: default package */
/* compiled from: GetExtraUrlAction */
public class bku extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put(LocationParams.PARA_COMMON_DIC, NetworkParam.getDic());
                jSONObject2.put(LocationParams.PARA_COMMON_DIV, NetworkParam.getDiv());
                jSONObject2.put(LocationParams.PARA_COMMON_DIBV, NetworkParam.getDibv());
                jSONObject2.put(LocationParams.PARA_COMMON_DIP, NetworkParam.getDip());
                jSONObject2.put(LocationParams.PARA_COMMON_DIU, NetworkParam.getDiu());
                jSONObject2.put(LocationParams.PARA_COMMON_ADIU, NetworkParam.getAdiu());
                jSONObject2.put("session_id", NetworkParam.getSession());
                jSONObject2.put("step_id", NetworkParam.getStepId());
                jSONObject2.put(LocationParams.PARA_COMMON_CIFA, NetworkParam.getCifa());
                jSONObject2.put("tid", NetworkParam.getTaobaoID());
                jSONObject2.put("pson", bim.aa().k((String) UploadConstants.STATUS_PUSH_RECEIVED) ? 1 : 0);
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
