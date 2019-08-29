package defpackage;

import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.common.PageBundle;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.route.subway.inter.model.SubWayStation;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: eih reason: default package */
/* compiled from: OpenSubwaySearchAction */
public class eih extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            HashMap hashMap = new HashMap();
            ArrayList arrayList = new ArrayList();
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("subway");
                if (jSONObject2 != null) {
                    JSONArray jSONArray = jSONObject2.getJSONArray("lines");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        eig eig = new eig();
                        JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                        eig.a = jSONObject3.getString("ls");
                        eig.b = jSONObject3.getString("ln");
                        eig.c = jSONObject3.getString("kn");
                        eig.d = jSONObject3.getString("cl");
                        eig.e = jSONObject3.getInt("su");
                        hashMap.put(eig.a, eig);
                    }
                    JSONArray jSONArray2 = jSONObject2.getJSONArray("stations");
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        SubWayStation subWayStation = new SubWayStation();
                        JSONObject jSONObject4 = jSONArray2.getJSONObject(i2);
                        subWayStation.a = jSONObject4.getString("si");
                        subWayStation.b = jSONObject4.getString(SuperId.BIT_1_MAIN_BUSSTATION);
                        subWayStation.c = jSONObject4.getString("sp");
                        subWayStation.e = jSONObject4.getInt("su");
                        subWayStation.d = jSONObject4.getString(UploadQueueMgr.MSGTYPE_REALTIME);
                        arrayList.add(subWayStation);
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putString("callback", waVar.a);
                    pageBundle.putString("_action", waVar.b);
                    pageBundle.putObject("lines", hashMap);
                    pageBundle.putObject("stations", arrayList);
                    a.mPageContext.startPageForResult((String) "amap.search.action.subwaysearch", pageBundle, 1);
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }
}
