package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.network.request.param.NetworkParam;
import com.autonavi.server.aos.serverkey;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dmp reason: default package */
/* compiled from: GetDeviceParamStringAction */
public class dmp extends vz {
    private static boolean a(int i, int i2) {
        return ((~i) & i2) == 0;
    }

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        if (jSONObject != null && waVar != null) {
            int optInt = jSONObject.optInt("params");
            String optString = jSONObject.optString("delimiting");
            boolean optBoolean = jSONObject.optBoolean("encypt");
            StringBuilder sb = new StringBuilder();
            if (a(optInt, 1)) {
                sb.append(NetworkParam.getDiv());
                sb.append(optString);
            }
            if (a(optInt, 2)) {
                sb.append(NetworkParam.getDip());
                sb.append(optString);
            }
            if (a(optInt, 4)) {
                sb.append(NetworkParam.getDic());
                sb.append(optString);
            }
            if (a(optInt, 8)) {
                sb.append(NetworkParam.getDiu());
                sb.append(optString);
            }
            if (a(optInt, 16)) {
                sb.append(NetworkParam.getAdiu());
                sb.append(optString);
            }
            if (a(optInt, 32)) {
                sb.append(NetworkParam.getTaobaoID());
                sb.append(optString);
            }
            if (a(optInt, 64)) {
                sb.append(optString);
            }
            if (TextUtils.isEmpty(sb)) {
                a(waVar, (String) "");
                return;
            }
            if (!TextUtils.isEmpty(optString)) {
                sb.delete(sb.lastIndexOf(optString), sb.length());
            }
            String sb2 = sb.toString();
            if (optBoolean) {
                sb2 = serverkey.amapEncodeV2(sb2);
            }
            a(waVar, sb2);
        }
    }

    private void a(wa waVar, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("value", str);
        jSONObject.put("_action", waVar.b);
        JsAdapter a = a();
        if (a != null) {
            a.callJs(waVar.a, jSONObject.toString());
        }
    }
}
