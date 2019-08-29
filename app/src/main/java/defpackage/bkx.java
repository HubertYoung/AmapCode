package defpackage;

import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bkx reason: default package */
/* compiled from: GetHomeAndCompanyAction */
public class bkx extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            String optString = jSONObject.optString("type");
            try {
                jSONObject2.put("_action", waVar.b);
                if (jSONObject == null) {
                    a.callJs(waVar.a, jSONObject2.toString());
                    return;
                }
                com com2 = (com) ank.a(com.class);
                if (com2 == null) {
                    a.callJs(waVar.a, jSONObject2.toString());
                    return;
                }
                cop b = com2.b(com2.a());
                if (b == null) {
                    a.callJs(waVar.a, jSONObject2.toString());
                    return;
                }
                if ("home".equals(optString)) {
                    FavoritePOI c = b.c();
                    if (c != null) {
                        jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, c.getId());
                        jSONObject2.put("name", c.getName());
                        jSONObject2.put(DictionaryKeys.CTRLXY_X, c.getPoint().x);
                        jSONObject2.put(DictionaryKeys.CTRLXY_Y, c.getPoint().y);
                        jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, c.getPoint().getLongitude());
                        jSONObject2.put("lat", c.getPoint().getLatitude());
                    }
                } else if ("company".equals(optString)) {
                    FavoritePOI d = b.d();
                    if (d != null) {
                        jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, d.getId());
                        jSONObject2.put("name", d.getName());
                        jSONObject2.put(DictionaryKeys.CTRLXY_X, d.getPoint().x);
                        jSONObject2.put(DictionaryKeys.CTRLXY_Y, d.getPoint().y);
                        jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, d.getPoint().getLongitude());
                        jSONObject2.put("lat", d.getPoint().getLatitude());
                    }
                }
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
