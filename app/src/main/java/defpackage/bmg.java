package defpackage;

import com.alibaba.fastjson.JSON;
import com.alipay.mobile.security.bio.api.BioDetector;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.vui.entity.VSysStateResultMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bmg reason: default package */
/* compiled from: RegisterVUISceneIdAndCommand */
public class bmg extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            try {
                JSONArray optJSONArray = jSONObject.optJSONArray("supportCmds");
                long optLong = jSONObject.optLong(BioDetector.EXT_KEY_SCENE_ID_BUNDLE, 0);
                if (optJSONArray != null) {
                    List<String> parseArray = JSON.parseArray(optJSONArray.toString(), String.class);
                    if (parseArray != null) {
                        bid bid = a.mPageContext;
                        if (bid instanceof ajb) {
                            ((ajb) bid).a(optLong, parseArray);
                        }
                        AMapLog.d("WebViewH5", "sceneId: ".concat(String.valueOf(optLong)));
                        StringBuilder sb = new StringBuilder("vuiCmdList: ");
                        sb.append(String.valueOf(optJSONArray));
                        AMapLog.d("WebViewH5", sb.toString());
                        bfo bfo = (bfo) a.a.a(bfo.class);
                        if (bfo != null) {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("_action", waVar.b);
                            jSONObject2.put("isVUIAvailable", bfo.e());
                            VSysStateResultMap b = bfo.b();
                            JSONObject jSONObject3 = new JSONObject();
                            for (String str : b.keySet()) {
                                boolean z = false;
                                if ("isVUICardVisible".equals(str)) {
                                    if (b.get(str).intValue() == 1) {
                                        z = true;
                                    }
                                    jSONObject2.put("isVUICardVisible", z);
                                } else {
                                    if (b.get(str).intValue() == 1) {
                                        z = true;
                                    }
                                    jSONObject3.put(str, z);
                                }
                            }
                            jSONObject3.put("isVUISwitchOn", bfo.a());
                            jSONObject2.put("VUIRelatedState", jSONObject3);
                            a.callJs(waVar.a, jSONObject2.toString());
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
