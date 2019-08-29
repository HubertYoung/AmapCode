package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.antui.basic.AUCardOptionView;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.search.data.DateEntity;
import com.autonavi.minimap.ajx3.Ajx3Page.AjxPageResultExecutor;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.basemap.errorback.inter.IBusErrorReportRemind;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.minimap.shortcutbadger.shortcutbadger.impl.NewHtcHomeBadger;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bxw reason: default package */
/* compiled from: SearchPoiDetailDataHelper */
public final class bxw implements AjxPageResultExecutor {
    public String a;
    public JSONObject b;
    private POI c;
    private PageBundle d;

    public bxw(POI poi) {
        this.c = poi;
    }

    public final void a(AbstractBaseMapPage abstractBaseMapPage, PageBundle pageBundle) {
        this.d = pageBundle;
        if (pageBundle == null) {
            pageBundle = new PageBundle();
            pageBundle.putString("fromSource", "default");
        }
        POI poi = (POI) pageBundle.getObject("POI");
        if (poi != null) {
            this.c = poi;
        } else {
            pageBundle.putObject("POI", this.c);
        }
        if (pageBundle.getInt(IndoorLocationProvider.NAME, 0) == 1) {
            pageBundle.putBoolean("isFromIndoorMap", true);
        }
        this.c.getPoiExtra().put("is_gpspoint", Boolean.valueOf(GpsPOI.class.isInstance(this.c)));
        a(this.c, pageBundle);
        if (pageBundle.getString("fromSource") == null || !pageBundle.getString("fromSource").equals("life_hotel")) {
            bkq.a(new DateEntity());
        } else {
            a(pageBundle);
        }
        if (this.c != null) {
            String type = this.c.getType() == null ? "" : this.c.getType();
            if (type.equals("150500") || type.equals("150600") || type.equals("150700")) {
                IBusErrorReportRemind iBusErrorReportRemind = (IBusErrorReportRemind) ank.a(IBusErrorReportRemind.class);
                if (iBusErrorReportRemind != null) {
                    iBusErrorReportRemind.handlePageOnResume(abstractBaseMapPage.getActivity(), -1);
                }
            }
        }
    }

    private static void a(PageBundle pageBundle) {
        boolean z = pageBundle.getBoolean("key_is_hour_room");
        DateEntity dateEntity = new DateEntity();
        if (z) {
            dateEntity.isHourlyRoom = "1";
        }
        bkq.a(dateEntity);
    }

    private void a(POI poi, PageBundle pageBundle) {
        if (pageBundle == null) {
            new PageBundle().putString("fromSource", "default");
        }
        FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
        com com2 = (com) ank.a(com.class);
        if (com2 != null) {
            cop b2 = com2.b(com2.a());
            if (b2 != null) {
                FavoritePOI d2 = b2.d(poi);
                if (d2 != null) {
                    favoritePOI.setCustomName(((FavoritePOI) d2.as(FavoritePOI.class)).getCustomName());
                    favoritePOI.setSaved(true);
                } else {
                    favoritePOI.setSaved(false);
                }
            }
        }
        this.d.putObject("POI", favoritePOI);
    }

    public final String a(PageBundle pageBundle, AbstractBaseMapPage abstractBaseMapPage, boolean z, boolean z2) {
        String str;
        a(abstractBaseMapPage, pageBundle);
        POI poi = (POI) pageBundle.getObject("POI");
        if (poi != null) {
            try {
                JSONObject a2 = a(pageBundle, abstractBaseMapPage, poi);
                JSONObject jSONObject = new JSONObject();
                String string = pageBundle.getString("point_type");
                if (string != null) {
                    jSONObject.put("pointType", string);
                }
                String string2 = pageBundle.getString("toggle");
                if (string2 != null) {
                    jSONObject.put("toggle", string2);
                }
                String string3 = pageBundle.getString("is_whole");
                if (poi.getPoiExtra() != null && Integer.valueOf(1).equals(poi.getPoiExtra().get("isFromBusRadar"))) {
                    string3 = "1";
                }
                if (string3 != null) {
                    jSONObject.put("isWhole", string3);
                }
                jSONObject.put("new_detail_switch", ((JSONObject) pageBundle.getObject("key_tip_poi")).optString("new_detail_switch", "0"));
                String string4 = pageBundle.getString(H5Param.LONG_TRANSPARENT);
                if (string4 != null) {
                    jSONObject.put(H5Param.LONG_TRANSPARENT, string4);
                }
                jSONObject.put("fromScheme", z2 ? "1" : "0");
                jSONObject.put("isFullState", z);
                jSONObject.put("clientData", a2);
                jSONObject.put("forbiddenDragDown", pageBundle.getBoolean("key_forbidden_dragdown", false));
                abstractBaseMapPage.getContext().getSharedPreferences("_ajx3_", 0).edit().putString("poiClientData", jSONObject.toString()).apply();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("from", pageBundle.getInt("key_tip_from", 2));
                jSONObject2.put("request_type", pageBundle.getInt("key_tip_request_type", 0));
                jSONObject2.put("poi", pageBundle.getObject("key_tip_poi"));
                jSONObject2.put("tip_type", pageBundle.getInt("key_tip_type", 0));
                jSONObject2.put("is_city_card", pageBundle.getInt("key_is_city_card", 0));
                jSONObject2.put("longitude", pageBundle.getString("key_centerpoint_longitude", ""));
                jSONObject2.put("latitude", pageBundle.getString("key_centerpoint_latitude", ""));
                jSONObject2.put("superid", pageBundle.getString("key_centerpoint_superid", ""));
                jSONObject2.put("gsid", pageBundle.getString("key_gsid", ""));
                jSONObject2.put("cate", pageBundle.getString("key_queryIntentCate", ""));
                jSONObject2.put("is_search_idq", pageBundle.getInt("is_search_idq", 0));
                jSONObject2.put("data_source", pageBundle.getInt("key_tip_data_source", 0));
                jSONObject2.put("search_header", (double) pageBundle.getFloat("key_search_headerb", 0.0f));
                jSONObject2.put("subway_ids", pageBundle.getString("key_subway_activeid", ""));
                jSONObject2.put("titleName", pageBundle.getString("titleName", ""));
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("detail", jSONObject);
                jSONObject3.put("tip", jSONObject2);
                str = jSONObject3.toString();
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
            AMapLog.e("info", str);
            return str;
        }
        str = "";
        AMapLog.e("info", str);
        return str;
    }

    @NonNull
    public final JSONObject a(PageBundle pageBundle, AbstractBaseMapPage abstractBaseMapPage, POI poi) throws JSONException {
        String b2 = b(poi);
        JSONObject jSONObject = new JSONObject();
        bty mapView = abstractBaseMapPage.getMapManager().getMapView();
        GeoPoint glGeoPoint2GeoPoint = mapView != null ? GeoPoint.glGeoPoint2GeoPoint(mapView.n()) : null;
        if (pageBundle.containsKey("poi_search_result")) {
            InfoliteResult infoliteResult = (InfoliteResult) pageBundle.getObject("poi_search_result");
            if (infoliteResult != null && bcy.c(infoliteResult) && infoliteResult.searchInfo.x) {
                jSONObject.put("general_flag", infoliteResult.searchInfo.w);
            }
        }
        JSONObject jSONObject2 = new JSONObject(b2);
        if (pageBundle.getBoolean("isGPSPoint")) {
            jSONObject.put("_action", "setMyLocation");
        } else if (pageBundle.getBoolean("isGeoCode")) {
            jSONObject.put("_action", "setMapPoint");
        } else if (poi.getId() == null || poi.getId().length() <= 0) {
            jSONObject.put("_action", "setMapPoint");
            if (poi.getPoiExtra().containsKey("SrcType")) {
                String str = (String) poi.getPoiExtra().get("SrcType");
                if (!TextUtils.isEmpty(str) && "nativepoi".equals(str)) {
                    jSONObject.put("_action", "setPoiInfo");
                    jSONObject.put("showIndoorMap", poi.getPoiExtra().get("showIndoorMap"));
                }
            }
        } else {
            jSONObject.put("_action", "setPoiInfo");
            jSONObject.put("showIndoorMap", poi.getPoiExtra().get("showIndoorMap"));
        }
        jSONObject.put("source", pageBundle.getString("fromSource"));
        jSONObject.put("poiInfo", jSONObject2);
        JSONObject jSONObject3 = new JSONObject();
        new cei();
        if (cei.b()) {
            jSONObject3.put(WidgetType.GPS, 1);
        } else {
            jSONObject3.put(WidgetType.GPS, 0);
        }
        if (cei.a()) {
            jSONObject3.put("wifi", 1);
        } else {
            jSONObject3.put("wifi", 0);
        }
        jSONObject.put("precision", jSONObject3);
        if (pageBundle.getBoolean("isFromIndoorMap")) {
            jSONObject.put(IndoorLocationProvider.NAME, 1);
        }
        new JSONObject();
        if (pageBundle.getBoolean("key_is_hour_room")) {
            jSONObject.put("isHourlyRoom", "1");
        }
        if (glGeoPoint2GeoPoint != null) {
            jSONObject.put("view_x", String.valueOf(glGeoPoint2GeoPoint.getLongitude()));
            jSONObject.put("view_y", String.valueOf(glGeoPoint2GeoPoint.getLatitude()));
        }
        jSONObject.put("CURRENT_BUS_ALIAS", bic.a((String) "poi_info").get("CURRENT_BUS_ALIAS"));
        String str2 = (String) poi.getPoiExtra().get("child_stations");
        if (!TextUtils.isEmpty(str2)) {
            jSONObject.put("child_stations", new JSONArray(str2));
        }
        if (!TextUtils.isEmpty(poi.getIndustry())) {
            jSONObject.put("industry", poi.getIndustry());
        }
        if (poi.getPoiExtra().containsKey("poiinfo")) {
            StringBuilder sb = new StringBuilder();
            sb.append(poi.getPoiExtra().get("poiinfo"));
            jSONObject.put("srcData", new JSONObject(sb.toString()));
        }
        Map templateDataMap = ((SearchPoi) this.c.as(SearchPoi.class)).getTemplateDataMap();
        if (templateDataMap != null && !templateDataMap.isEmpty()) {
            PoiLayoutTemplate poiLayoutTemplate = (PoiLayoutTemplate) templateDataMap.get(Integer.valueOf(2009));
            if (!(poiLayoutTemplate == null || poiLayoutTemplate.getValue() == null || poiLayoutTemplate.getValue().equals(poi.getAddr()))) {
                jSONObject.put("dist2landmark", poiLayoutTemplate.getValue());
            }
        }
        return jSONObject;
    }

    private String b(POI poi) {
        if (poi.getPoint() == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
            if (poiExtra != null && poiExtra.containsKey("is_gpspoint")) {
                jSONObject.put("is_gpspoint", (Boolean) poiExtra.get("is_gpspoint"));
            }
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId());
            jSONObject.put("name", poi.getName());
            jSONObject.put("address", poi.getAddr());
            jSONObject.put("phoneNumbers", poi.getPhone());
            jSONObject.put("new_type", poi.getType());
            jSONObject.put(DictionaryKeys.CTRLXY_X, poi.getPoint().x);
            jSONObject.put(DictionaryKeys.CTRLXY_Y, poi.getPoint().y);
            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, poi.getPoint().getLongitude());
            jSONObject.put("lat", poi.getPoint().getLatitude());
            jSONObject.put("end_poi_extension", poi.getEndPoiExtension());
            jSONObject.put(H5Param.LONG_TRANSPARENT, poi.getTransparent());
            SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
            ArrayList<GeoPoint> entranceList = searchPoi.getEntranceList();
            if (entranceList != null && !entranceList.isEmpty()) {
                GeoPoint geoPoint = entranceList.get(0);
                jSONObject.put("navi_lng", geoPoint.getLongitude());
                jSONObject.put("navi_lat", geoPoint.getLatitude());
            }
            if (!TextUtils.isEmpty(searchPoi.getTowardsAngle())) {
                jSONObject.put("towards_angle", searchPoi.getTowardsAngle());
            }
            if (!TextUtils.isEmpty(searchPoi.getParent())) {
                jSONObject.put("parent", searchPoi.getParent());
            }
            if (!TextUtils.isEmpty(searchPoi.getChildType())) {
                jSONObject.put("childType", searchPoi.getChildType());
            }
            if (!TextUtils.isEmpty(searchPoi.getFnona())) {
                jSONObject.put("f_nona", searchPoi.getFnona());
            }
            if (poi.getPoiExtra().get("businfo_lineids") != null) {
                jSONObject.put("businfo_lineids", poi.getPoiExtra().get("businfo_lineids"));
            }
            if (TextUtils.isEmpty(poi.getCityCode())) {
                StringBuilder sb = new StringBuilder();
                sb.append(poi.getPoint().getAdCode());
                poi.setCityCode(sb.toString());
            }
            jSONObject.put("cityCode", poi.getCityCode());
            if (!TextUtils.isEmpty(poi.getIndustry())) {
                jSONObject.put("industry", poi.getIndustry());
            }
            if (this.b != null) {
                jSONObject.put("indicator", this.b);
            }
            return jSONObject.toString();
        } catch (JSONException unused) {
            return null;
        }
    }

    public static PageBundle a(POI poi) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("POI", poi);
        pageBundle.putBoolean("isGeoCode", GeocodePOI.class.isInstance(poi));
        pageBundle.putBoolean("isGPSPoint", GpsPOI.class.isInstance(poi));
        pageBundle.putBoolean(Constant.KEY_IS_BACK, true);
        pageBundle.putString("fromSource", "mainmap");
        return pageBundle;
    }

    public final void onFragmentResult(AbstractBasePage abstractBasePage, int i, ResultType resultType, PageBundle pageBundle, JsAdapter jsAdapter) {
        if (pageBundle != null) {
            this.a = (String) pageBundle.get(ModuleHistory.AJX_BACK_RETURN_DATA_KEY);
        } else {
            this.a = null;
        }
        if (i == 1000) {
            JSONObject jSONObject = new JSONObject();
            try {
                FavoritePOI favoritePOI = (FavoritePOI) this.c.as(FavoritePOI.class);
                jSONObject.put("_action", "setFavoriteMark");
                jSONObject.put("status", favoritePOI.isSaved());
                jSONObject.put("favInfo", new JSONObject(bnx.a(favoritePOI)));
                jsAdapter.callJs("callback", jSONObject.toString());
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        } else if (i == 1) {
            a(pageBundle, jsAdapter);
        } else {
            if ((i == 2 || i == 20484 || i == 20485) && pageBundle != null) {
                try {
                    int i2 = pageBundle.getInt("PHOTO_UPLOAD_STATUS");
                    if (i2 > 0) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("_action", pageBundle.getString("PHOTO_UPLOAD_ACTION"));
                        jSONObject2.put(NewHtcHomeBadger.COUNT, pageBundle.getInt("PHOTO_UPLOAD_COUNT"));
                        jSONObject2.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, pageBundle.getString("PHOTO_UPLOAD_POIID"));
                        jSONObject2.put("status", i2);
                        Object obj = pageBundle.get("PHOTO_UPLOAD_CALLBACK");
                        if (obj instanceof wa) {
                            wa waVar = (wa) obj;
                            jSONObject2.put("_action", waVar.b);
                            jsAdapter.callJs(waVar.a, jSONObject2.toString());
                        }
                        String string = pageBundle.getString("PHOTO_UPLOAD_LINK");
                        if (!TextUtils.isEmpty(string)) {
                            caf caf = (caf) a.a.a(caf.class);
                            if (caf != null) {
                                caf.a(abstractBasePage, string);
                            }
                        }
                    }
                } catch (JSONException e2) {
                    kf.a((Throwable) e2);
                }
            }
        }
    }

    public static void a(PageBundle pageBundle, JsAdapter jsAdapter) {
        if (pageBundle != null && jsAdapter != null) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("id", pageBundle.getString("COMMENT_PUBLISH_ID", ""));
                jSONObject.put("desc", pageBundle.getString("EDIT_COMMENT_CONTENT"));
                jSONObject.put("score", pageBundle.getInt("EDIT_COMMENT_RATING"));
                jSONObject.put("picCount", pageBundle.getInt("EDIT_COMMENT_PICCOUNT"));
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(AUCardOptionView.TYPE_COMMENT, jSONObject);
                jSONObject2.put(TrafficUtil.POIID, pageBundle.getString("EDIT_COMMENT_POI_ID"));
                jSONObject2.put("status", pageBundle.getInt("COMMENT_PUBLISH_STATUS"));
                Object obj = pageBundle.get("EDIT_COMMENT_CALLBACK");
                if (obj instanceof wa) {
                    wa waVar = (wa) obj;
                    jSONObject2.put("_action", waVar.b);
                    jsAdapter.callJs(waVar.a, jSONObject2.toString());
                }
            } catch (JSONException e) {
                kf.a((Throwable) e);
            }
        }
    }
}
