package defpackage;

import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.uitemplate.ajx.ModuleMapWidget;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: wd reason: default package */
/* compiled from: GetCloudConfigAction */
public class wd extends vz {
    private static final String[] a = {"paas_", ModuleMapWidget.AJX_PRE_TAG};

    public final void a(JSONObject jSONObject, wa waVar) throws JSONException {
        JsAdapter a2 = a();
        if (a2 != null) {
            String str = "";
            String str2 = "";
            String optString = jSONObject.optString("key");
            AMapLog.info("paas.jsadapter", "GetCloudConfigAction key: ", optString);
            boolean z = false;
            if (!TextUtils.isEmpty(optString)) {
                String[] strArr = a;
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    } else if (optString.startsWith(strArr[i])) {
                        z = true;
                        break;
                    } else {
                        i++;
                    }
                }
            }
            if (z) {
                str = lo.a().a(optString);
            } else {
                str2 = TextUtils.isEmpty(optString) ? "key should not be empty" : "key should start with paas_ or ajx_";
            }
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("_action", waVar.b);
                jSONObject2.put("value", str);
                jSONObject2.put("errMsg", str2);
                String jSONObject3 = jSONObject2.toString();
                a2.callJs(waVar.a, jSONObject3);
                AMapLog.info("paas.jsadapter", "GetCloudConfigAction callback: ", jSONObject3);
            } catch (JSONException e) {
                AMapLog.warning("paas.jsadapter", "GetCloudConfigAction", e.toString());
            }
        }
    }
}
