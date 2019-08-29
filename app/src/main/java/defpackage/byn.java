package defpackage;

import android.text.TextUtils;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.jsadapter.JsAdapter;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.search.data.DateEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: byn reason: default package */
/* compiled from: GetPoiInfoAction */
public class byn extends bkq {
    public final void a(JSONObject jSONObject, wa waVar) {
        JsAdapter a = a();
        if (a != null) {
            PageBundle bundle = a.getBundle();
            if (bundle != null) {
                POI poi = (POI) bundle.getObject("POI");
                GeoPoint geoPoint = null;
                bty mapView = DoNotUseTool.getMapView();
                if (mapView != null) {
                    geoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
                }
                if (poi != null) {
                    String a2 = kv.a(poi);
                    JSONObject jSONObject2 = new JSONObject();
                    try {
                        if (a().getBundle().containsKey("poi_search_result")) {
                            InfoliteResult infoliteResult = (InfoliteResult) a().getBundle().getObject("poi_search_result");
                            if (infoliteResult != null && bcy.c(infoliteResult) && infoliteResult.searchInfo.x) {
                                jSONObject2.put("general_flag", infoliteResult.searchInfo.w);
                            }
                        }
                        JSONObject jSONObject3 = new JSONObject(a2);
                        String string = a.getBundle().getString("rank_list_more");
                        if (!TextUtils.isEmpty(string)) {
                            jSONObject2.put("ranklistmore", string);
                        }
                        if (a.getBundle().getBoolean("isGPSPoint")) {
                            jSONObject2.put("_action", "setMyLocation");
                        } else if (a.getBundle().getBoolean("isGeoCode")) {
                            jSONObject2.put("_action", "setMapPoint");
                        } else {
                            if (poi.getId() != null) {
                                if (poi.getId().length() > 0) {
                                    jSONObject2.put("_action", "setPoiInfo");
                                    jSONObject2.put("showIndoorMap", poi.getPoiExtra().get("showIndoorMap"));
                                }
                            }
                            jSONObject2.put("_action", "setMapPoint");
                            if (poi.getPoiExtra().containsKey("SrcType")) {
                                String str = (String) poi.getPoiExtra().get("SrcType");
                                if (!TextUtils.isEmpty(str) && "nativepoi".equals(str)) {
                                    jSONObject2.put("_action", "setPoiInfo");
                                    jSONObject2.put("showIndoorMap", poi.getPoiExtra().get("showIndoorMap"));
                                }
                            }
                        }
                        jSONObject2.put("source", a.getBundle().getString("fromSource"));
                        jSONObject2.put("poiInfo", jSONObject3);
                        JSONObject jSONObject4 = new JSONObject();
                        new cei();
                        if (cei.b()) {
                            jSONObject4.put(WidgetType.GPS, 1);
                        } else {
                            jSONObject4.put(WidgetType.GPS, 0);
                        }
                        if (cei.a()) {
                            jSONObject4.put("wifi", 1);
                        } else {
                            jSONObject4.put("wifi", 0);
                        }
                        jSONObject2.put("precision", jSONObject4);
                        if (a.getBundle().getBoolean("isFromIndoorMap")) {
                            jSONObject2.put(IndoorLocationProvider.NAME, 1);
                        }
                        new JSONObject();
                        if (a != null && (a instanceof DateEntity)) {
                            jSONObject2.put("isHourlyRoom", a.isHourlyRoom);
                        }
                        if (geoPoint != null) {
                            jSONObject2.put("view_x", String.valueOf(geoPoint.getLongitude()));
                            jSONObject2.put("view_y", String.valueOf(geoPoint.getLatitude()));
                        }
                        jSONObject2.put("CURRENT_BUS_ALIAS", bic.a((String) "poi_info").get("CURRENT_BUS_ALIAS"));
                        String str2 = (String) poi.getPoiExtra().get("child_stations");
                        if (!TextUtils.isEmpty(str2)) {
                            jSONObject2.put("child_stations", new JSONArray(str2));
                        }
                        if (!TextUtils.isEmpty(poi.getIndustry())) {
                            jSONObject2.put("industry", poi.getIndustry());
                        }
                        if (poi.getPoiExtra().containsKey("poiinfo")) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(poi.getPoiExtra().get("poiinfo"));
                            jSONObject2.put("srcData", new JSONObject(sb.toString()));
                        }
                        a.callJs(waVar.a, jSONObject2.toString());
                    } catch (JSONException e) {
                        kf.a((Throwable) e);
                    }
                }
            }
        }
    }
}
