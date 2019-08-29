package defpackage;

import android.text.TextUtils;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.KeyValueStorage.WebStorage;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

/* renamed from: bma reason: default package */
/* compiled from: UserHomeAndCompanyAction */
public class bma extends vz {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            com com2 = (com) ank.a(com.class);
            if (com2 != null) {
                cop b = com2.b(com2.a());
                if (b != null) {
                    FavoritePOI c = b.c();
                    FavoritePOI d = b.d();
                    JSONObject optJSONObject = jSONObject.optJSONObject("params");
                    if (optJSONObject != null) {
                        JSONObject optJSONObject2 = optJSONObject.optJSONObject("home");
                        JSONObject optJSONObject3 = optJSONObject.optJSONObject("company");
                        WebStorage a2 = bic.a((String) "userHomeAndCompany");
                        a2.beginTransaction();
                        if (!(optJSONObject2 == null || c == null)) {
                            a2.set("homePoiId", c.getId());
                            a2.set("home", optJSONObject2.toString());
                        }
                        if (!(optJSONObject3 == null || d == null)) {
                            a2.set("companyPoiId", d.getId());
                            a2.set("company", optJSONObject3.toString());
                        }
                        a2.commit();
                        return;
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        jSONObject2.put("_action", waVar.b);
                        WebStorage a3 = bic.a((String) "userHomeAndCompany");
                        String str = a3.get("home");
                        if (!TextUtils.isEmpty(str)) {
                            if (c == null) {
                                a3.remove("homePoiId");
                                a3.remove("home");
                            } else if (a3.get("homePoiId").equals(c.getId())) {
                                jSONObject2.put("home", new JSONObject(str));
                            } else {
                                a3.remove("homePoiId");
                                a3.remove("home");
                            }
                        }
                        String str2 = a3.get("company");
                        if (!TextUtils.isEmpty(str2)) {
                            if (d == null) {
                                a3.remove("companyPoiId");
                                a3.remove("company");
                            } else if (a3.get("companyPoiId").equals(d.getId())) {
                                jSONObject2.put("company", new JSONObject(str2));
                            } else {
                                a3.remove("companyPoiId");
                                a3.remove("company");
                            }
                        }
                        if (c != null) {
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, c.getId());
                            jSONObject3.put("name", c.getName());
                            jSONObject3.put(DictionaryKeys.CTRLXY_X, c.getPoint().x);
                            jSONObject3.put(DictionaryKeys.CTRLXY_Y, c.getPoint().y);
                            jSONObject3.put(LocationParams.PARA_FLP_AUTONAVI_LON, c.getPoint().getLongitude());
                            jSONObject3.put("lat", c.getPoint().getLatitude());
                            jSONObject2.put("homePoi", jSONObject3);
                        }
                        if (d != null) {
                            JSONObject jSONObject4 = new JSONObject();
                            jSONObject4.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, d.getId());
                            jSONObject4.put("name", d.getName());
                            jSONObject4.put(DictionaryKeys.CTRLXY_X, d.getPoint().x);
                            jSONObject4.put(DictionaryKeys.CTRLXY_Y, d.getPoint().y);
                            jSONObject4.put(LocationParams.PARA_FLP_AUTONAVI_LON, d.getPoint().getLongitude());
                            jSONObject4.put("lat", d.getPoint().getLatitude());
                            jSONObject2.put("companyPoi", jSONObject4);
                        }
                        a.callJs(waVar.a, jSONObject2.toString());
                    } catch (Exception e) {
                        kf.a((Throwable) e);
                    }
                }
            }
        }
    }
}
