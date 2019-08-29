package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: blq reason: default package */
/* compiled from: SetHomeAndCompanyAction */
public class blq extends vz {
    private static boolean a(double d) {
        return d >= -180.0d && d < 180.0d;
    }

    private static boolean b(double d) {
        return d >= -90.0d && d <= 90.0d;
    }

    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("_action", waVar.b);
                if (jSONObject == null) {
                    jSONObject2.put("value", false);
                    a.callJs(waVar.a, jSONObject2.toString());
                    return;
                }
                com com2 = (com) ank.a(com.class);
                if (com2 == null) {
                    jSONObject2.put("value", false);
                    a.callJs(waVar.a, jSONObject2.toString());
                    return;
                }
                cop b = com2.b(com2.a());
                if (b == null) {
                    jSONObject2.put("value", false);
                    a.callJs(waVar.a, jSONObject2.toString());
                    return;
                }
                String optString = jSONObject.optString("type");
                if ("home".equals(optString)) {
                    String optString2 = jSONObject.optString("pid");
                    String optString3 = jSONObject.optString("name");
                    double optDouble = jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON);
                    double optDouble2 = jSONObject.optDouble("lat");
                    if (TextUtils.isEmpty(optString3) || !a(optDouble) || !b(optDouble2)) {
                        jSONObject2.put("value", false);
                    } else {
                        b.f(a(optString2, optString3, optDouble, optDouble2));
                        jSONObject2.put("value", true);
                    }
                } else if ("company".equals(optString)) {
                    String optString4 = jSONObject.optString("pid");
                    String optString5 = jSONObject.optString("name");
                    double optDouble3 = jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON);
                    double optDouble4 = jSONObject.optDouble("lat");
                    if (TextUtils.isEmpty(optString5) || !a(optDouble3) || !b(optDouble4)) {
                        jSONObject2.put("value", false);
                    } else {
                        b.e(a(optString4, optString5, optDouble3, optDouble4));
                        a.callJs(waVar.a, "true");
                        jSONObject2.put("value", true);
                    }
                } else {
                    jSONObject2.put("value", false);
                }
                a.callJs(waVar.a, jSONObject2.toString());
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }

    private static POI a(String str, String str2, double d, double d2) {
        POI createPOI = POIFactory.createPOI();
        if (!TextUtils.isEmpty(str)) {
            createPOI.setPid(str);
        }
        if (!TextUtils.isEmpty(str2)) {
            createPOI.setName(str2);
        }
        createPOI.setPoint(new GeoPoint(d, d2));
        return createPOI;
    }
}
