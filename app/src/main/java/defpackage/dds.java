package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.drivecommon.inter.NetConstant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dds reason: default package */
/* compiled from: ToolsBoxParser */
public final class dds {
    public static ddt a(JSONObject jSONObject) {
        JSONArray jSONArray;
        ddt ddt = new ddt();
        if (jSONObject != null) {
            ddt.b = jSONObject.optString("code", "");
            ddt.c = jSONObject.optString("resule", "");
            ddt.d = jSONObject.optString("timestamp", "");
            ddt.e = jSONObject.optString("message", "");
            ddt.f = jSONObject.optString("version", "");
            ddt.g = jSONObject.optString("md5", "");
            JSONArray jSONArray2 = null;
            JSONObject optJSONObject = jSONObject.optJSONObject(NetConstant.KEY_USER_APPLIED_NAVI_LIST);
            try {
                if (optJSONObject.has("conf_list")) {
                    jSONArray = optJSONObject.getJSONArray("conf_list");
                } else if (optJSONObject.has("wealth_list")) {
                    jSONArray = optJSONObject.getJSONArray("wealth_list");
                } else {
                    if (optJSONObject.has("mypage")) {
                        jSONArray2 = optJSONObject.getJSONArray("mypage");
                    }
                    jSONArray = jSONArray2;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jSONArray != null) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject optJSONObject2 = jSONArray.optJSONObject(i);
                    int optInt = optJSONObject2.optInt("id", 0);
                    String optString = optJSONObject2.optString("name", "");
                    String optString2 = optJSONObject2.optString(H5Param.MENU_ICON, "");
                    int optInt2 = optJSONObject2.optInt("position", 0);
                    int optInt3 = optJSONObject2.optInt("type", 0);
                    int optInt4 = optJSONObject2.optInt("is_new", 0);
                    String optString3 = optJSONObject2.optString("label", "");
                    JSONObject optJSONObject3 = optJSONObject2.optJSONObject("action");
                    int optInt5 = optJSONObject3.optInt("action_type", 0);
                    String optString4 = optJSONObject3.optString("url", "");
                    ddr ddr = new ddr();
                    ddr.a = optInt;
                    ddr.b = optString;
                    ddr.c = optString3;
                    ddr.d = optString2;
                    ddr.e = optInt2;
                    ddr.f = optInt3;
                    ddr.g = optInt4;
                    a aVar = new a();
                    aVar.a = optInt5;
                    aVar.b = optString4;
                    ddr.h = aVar;
                    ddt.a.add(ddr);
                }
            }
        }
        return ddt;
    }
}
