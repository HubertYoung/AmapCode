package defpackage;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.h5container.api.H5PageData;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.drivecommon.request.RouteCarParamUrlWrapper;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.bundle.carownerservice.ajx.ModuleCarOwner;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import com.taobao.accs.common.Constants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: nv reason: default package */
/* compiled from: EtripRequestParamUrlBuilder */
public final class nv {
    private static Pattern a = Pattern.compile("[0-9]*");

    private static JSONObject a(int i, POI poi, POI poi2) {
        ta taVar = new ta(poi, poi2, CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN);
        taVar.z = false;
        taVar.i = (float) i;
        taVar.y = 0;
        taVar.v = false;
        taVar.C = "planend_record";
        if (TextUtils.isEmpty(taVar.e)) {
            taVar.e = DriveUtil.getLastRoutingChoice();
        }
        taVar.h = true;
        if (taVar.d == null) {
            taVar.d = "plan";
        }
        RouteCarParamUrlWrapper a2 = ou.a(0, taVar);
        JSONObject jSONObject = new JSONObject();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(a2.angle_fittingdir);
            jSONObject.put("angle_fittingdir", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a2.sloc_speed);
            jSONObject.put("sloc_speed", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a2.fromX);
            jSONObject.put("fromX", sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a2.angle_matchingdir);
            jSONObject.put("angle_matchingdir", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a2.sdk_version);
            jSONObject.put("sdk_version", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(a2.end_types);
            jSONObject.put("end_types", sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a2.end_poiid);
            jSONObject.put("end_poiid", sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a2.end_poi_extension);
            jSONObject.put("end_poi_extension", sb8.toString());
            StringBuilder sb9 = new StringBuilder();
            sb9.append(a2.sloc_precision);
            jSONObject.put("sloc_precision", sb9.toString());
            StringBuilder sb10 = new StringBuilder();
            sb10.append(a2.gps_cre);
            jSONObject.put("gps_cre", sb10.toString());
            StringBuilder sb11 = new StringBuilder();
            sb11.append(a2.angle_sigtype);
            jSONObject.put("angle_sigtype", sb11.toString());
            StringBuilder sb12 = new StringBuilder();
            sb12.append(a2.invoker);
            jSONObject.put("invoker", sb12.toString());
            StringBuilder sb13 = new StringBuilder();
            sb13.append(a2.use_truck_engine);
            jSONObject.put("use_truck_engine", sb13.toString());
            StringBuilder sb14 = new StringBuilder();
            sb14.append(a2.usepoiquery);
            jSONObject.put("usepoiquery", sb14.toString());
            StringBuilder sb15 = new StringBuilder();
            sb15.append(a2.end_name);
            jSONObject.put("end_name", sb15.toString());
            StringBuilder sb16 = new StringBuilder();
            sb16.append(a2.soundtype);
            jSONObject.put("soundtype", sb16.toString());
            StringBuilder sb17 = new StringBuilder();
            sb17.append(a2.start_types);
            jSONObject.put("start_types", sb17.toString());
            StringBuilder sb18 = new StringBuilder();
            sb18.append(a2.contentoptions);
            jSONObject.put("contentoptions", sb18.toString());
            StringBuilder sb19 = new StringBuilder();
            sb19.append(a2.v_axis);
            jSONObject.put("v_axis", sb19.toString());
            StringBuilder sb20 = new StringBuilder();
            sb20.append(a2.toX);
            jSONObject.put("toX", sb20.toString());
            StringBuilder sb21 = new StringBuilder();
            sb21.append(a2.angle_radius);
            jSONObject.put("angle_radius", sb21.toString());
            StringBuilder sb22 = new StringBuilder();
            sb22.append(a2.credibility);
            jSONObject.put("credibility", sb22.toString());
            StringBuilder sb23 = new StringBuilder();
            sb23.append(a2.route_version);
            jSONObject.put("route_version", sb23.toString());
            StringBuilder sb24 = new StringBuilder();
            sb24.append(a2.toY);
            jSONObject.put("toY", sb24.toString());
            StringBuilder sb25 = new StringBuilder();
            sb25.append(a2.fromY);
            jSONObject.put("fromY", sb25.toString());
            StringBuilder sb26 = new StringBuilder();
            sb26.append(a2.v_weight);
            jSONObject.put("v_weight", sb26.toString());
            StringBuilder sb27 = new StringBuilder();
            sb27.append(a2.v_width);
            jSONObject.put("v_width", sb27.toString());
            StringBuilder sb28 = new StringBuilder();
            sb28.append(a2.v_length);
            jSONObject.put("v_length", sb28.toString());
            StringBuilder sb29 = new StringBuilder();
            sb29.append(a2.v_height);
            jSONObject.put("v_height", sb29.toString());
            StringBuilder sb30 = new StringBuilder();
            sb30.append(a2.policy2);
            jSONObject.put("policy2", sb30.toString());
            StringBuilder sb31 = new StringBuilder();
            sb31.append(a2.v_type);
            jSONObject.put("v_type", sb31.toString());
            StringBuilder sb32 = new StringBuilder();
            sb32.append(a2.refresh);
            jSONObject.put("refresh", sb32.toString());
            StringBuilder sb33 = new StringBuilder();
            sb33.append(a2.angle_comp);
            jSONObject.put("angle_comp", sb33.toString());
            StringBuilder sb34 = new StringBuilder();
            sb34.append(a2.v_load);
            jSONObject.put("v_load", sb34.toString());
            StringBuilder sb35 = new StringBuilder();
            sb35.append(a2.angle_gps);
            jSONObject.put("angle_gps", sb35.toString());
            StringBuilder sb36 = new StringBuilder();
            sb36.append(a2.angle_type);
            jSONObject.put("angle_type", sb36.toString());
            StringBuilder sb37 = new StringBuilder();
            sb37.append(a2.playstyle);
            jSONObject.put("playstyle", sb37.toString());
            StringBuilder sb38 = new StringBuilder();
            sb38.append(a2.v_size);
            jSONObject.put("v_size", sb38.toString());
            StringBuilder sb39 = new StringBuilder();
            sb39.append(a2.threeD);
            jSONObject.put("threeD", sb39.toString());
            StringBuilder sb40 = new StringBuilder();
            sb40.append(a2.angle);
            jSONObject.put("angle", sb40.toString());
            StringBuilder sb41 = new StringBuilder();
            sb41.append(a2.fitting_cre);
            jSONObject.put("fitting_cre", sb41.toString());
            StringBuilder sb42 = new StringBuilder();
            sb42.append(a2.carplate);
            jSONObject.put("carplate", sb42.toString());
            StringBuilder sb43 = new StringBuilder();
            sb43.append(a2.end_typecode);
            jSONObject.put("end_typecode", sb43.toString());
            StringBuilder sb44 = new StringBuilder();
            sb44.append(a2.start_typecode);
            jSONObject.put("start_typecode", sb44.toString());
            StringBuilder sb45 = new StringBuilder();
            sb45.append(a2.superid);
            jSONObject.put("superid", sb45.toString());
            StringBuilder sb46 = new StringBuilder();
            sb46.append(a2.frompage);
            jSONObject.put("frompage", sb46.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new StringBuilder("buildCarParam: ").append(jSONObject.toString());
        return jSONObject;
    }

    private static JSONObject a(POI poi, POI poi2) {
        JSONObject jSONObject = new JSONObject();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(poi2.getPoint().getLatitude());
            jSONObject.put("end_y", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(poi2.getPoint().getLongitude());
            jSONObject.put("end_x", sb2.toString());
            if (TextUtils.equals("我的位置", poi2.getName())) {
                jSONObject.put("end_name", "");
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(poi2.getName());
                jSONObject.put("end_name", sb3.toString());
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(poi.getPoint().getLatitude());
            jSONObject.put("start_y", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(poi.getPoint().getLongitude());
            jSONObject.put("start_x", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(poi.getName());
            jSONObject.put("start_name", sb6.toString());
            if (TextUtils.equals("我的位置", poi.getName())) {
                jSONObject.put("start_name", "");
            } else {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(poi2.getName());
                jSONObject.put("start_name", sb7.toString());
            }
            jSONObject.put("highway_cost", "0");
            jSONObject.put("scenario", "2");
            jSONObject.put(Constants.KEY_MODE, "simple");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static JSONObject b(POI poi, POI poi2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", "0");
            jSONObject.put("eta", "1");
            jSONObject.put("group", "1");
            jSONObject.put("server_ver", "0");
            jSONObject.put("humanize", "0");
            jSONObject.put("isindoor", "1");
            jSONObject.put("ver", "3");
            jSONObject.put("req_num", "5");
            if (poi != null) {
                if (TextUtils.equals("我的位置", poi.getName())) {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                    if (latestPosition != null) {
                        poi.setPoint(latestPosition);
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append(poi.getPoint().getLongitude());
                jSONObject.put("x1", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(poi.getPoint().getLatitude());
                jSONObject.put("y1", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(poi.getId());
                jSONObject.put("poiid1", sb3.toString());
                jSONObject.put("poiext1", TextUtils.isEmpty(poi.getEndPoiExtension()) ? "" : poi.getEndPoiExtension());
                jSONObject.put("poitype1", TextUtils.isEmpty(poi.getType()) ? "" : poi.getType());
                if (a(poi.getId())) {
                    jSONObject.put("precision1", "3");
                } else {
                    if (TextUtils.equals("我的位置", poi.getName())) {
                        jSONObject.put("precision1", "1");
                    } else if (TextUtils.equals(DriveUtil.MAP_PLACE_DES, poi.getName())) {
                        jSONObject.put("precision1", "2");
                    } else {
                        jSONObject.put("precision1", "4");
                    }
                    jSONObject.put("precision1", "");
                }
                StringBuilder sb4 = new StringBuilder();
                sb4.append(poi.getAdCode());
                jSONObject.put("ad1", sb4.toString());
                if (poi.getAdCode() == null) {
                    jSONObject.put("ad1", "");
                }
            }
            if (poi2 != null) {
                if (TextUtils.equals("我的位置", poi2.getName())) {
                    GeoPoint latestPosition2 = LocationInstrument.getInstance().getLatestPosition();
                    if (latestPosition2 != null) {
                        poi2.setPoint(latestPosition2);
                    }
                }
                StringBuilder sb5 = new StringBuilder();
                sb5.append(poi2.getPoint().getLongitude());
                jSONObject.put("x2", sb5.toString());
                StringBuilder sb6 = new StringBuilder();
                sb6.append(poi2.getPoint().getLatitude());
                jSONObject.put("y2", sb6.toString());
                StringBuilder sb7 = new StringBuilder();
                sb7.append(poi2.getId());
                jSONObject.put("poiid2", sb7.toString());
                jSONObject.put("poiext2", TextUtils.isEmpty(poi2.getEndPoiExtension()) ? "" : poi2.getEndPoiExtension());
                jSONObject.put("poitype2", TextUtils.isEmpty(poi2.getType()) ? "" : poi2.getType());
                if (a(poi2.getId())) {
                    jSONObject.put("precision2", "3");
                } else {
                    if (TextUtils.equals("我的位置", poi2.getName())) {
                        jSONObject.put("precision2", "3");
                    } else if (TextUtils.equals(DriveUtil.MAP_PLACE_DES, poi2.getName())) {
                        jSONObject.put("precision2", "2");
                    } else {
                        jSONObject.put("precision2", "4");
                    }
                    jSONObject.put("precision2", "");
                }
                StringBuilder sb8 = new StringBuilder();
                sb8.append(poi2.getAdCode());
                jSONObject.put("ad2", sb8.toString());
                if (poi2.getAdCode() == null) {
                    jSONObject.put("ad2", "");
                }
            }
            if (TextUtils.equals("0", "0")) {
                jSONObject.put(FunctionSupportConfiger.TAXI_TAG, "1");
            }
            Calendar instance = Calendar.getInstance();
            if (instance != null) {
                StringBuilder sb9 = new StringBuilder();
                sb9.append(instance.get(1));
                sb9.append("-");
                sb9.append(instance.get(2) + 1);
                sb9.append("-");
                sb9.append(instance.get(5));
                String sb10 = sb9.toString();
                StringBuilder sb11 = new StringBuilder();
                sb11.append(instance.get(11));
                sb11.append("-");
                sb11.append(instance.get(12));
                String sb12 = sb11.toString();
                jSONObject.put("date", String.valueOf(sb10));
                jSONObject.put("time", String.valueOf(sb12));
            } else {
                jSONObject.put(FunctionSupportConfiger.TAXI_TAG, "1");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private static JSONObject c(POI poi, POI poi2) {
        int i;
        TextUtils.equals("我的位置", poi.getName());
        TextUtils.equals("我的位置", poi2.getName());
        StringBuilder sb = new StringBuilder();
        try {
            int i2 = 2;
            if (a(poi.getId())) {
                sb.append(poi.getId());
                sb.append(",");
                i = 3;
            } else {
                sb.append(poi.getPoint().getLongitude());
                sb.append(",");
                sb.append(poi.getPoint().getLatitude());
                sb.append(",");
                if (TextUtils.equals("我的位置", poi.getName())) {
                    i = 1;
                } else {
                    if (!DriveUtil.MAP_PLACE_DES.equals(poi.getName())) {
                        if (!"地图选定位置".equals(poi.getName())) {
                            i = 4;
                        }
                    }
                    i = 2;
                }
            }
            if (a(poi2.getId())) {
                sb.append(poi2.getId());
                sb.append(",");
                i2 = 3;
            } else {
                sb.append(poi2.getPoint().getLongitude());
                sb.append(",");
                sb.append(poi2.getPoint().getLatitude());
                sb.append(",");
                if (TextUtils.equals("我的位置", poi2.getName())) {
                    i2 = 1;
                } else if (!TextUtils.equals(DriveUtil.MAP_PLACE_DES, poi2.getName())) {
                    if (!TextUtils.equals("地图选定位置", poi2.getName())) {
                        i2 = 4;
                    }
                }
            }
            sb.append(i);
            sb.append(",");
            sb.append(i2);
            sb.append(",0,0,100000");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("lv", "4.1");
            bax bax = (bax) a.a.a(bax.class);
            if (bax == null) {
                return null;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(bax.e());
            jSONObject.put("sdk_version", sb2.toString());
            jSONObject.put("isindoor", "0");
            jSONObject.put("maxLength", "1000000");
            jSONObject.put(FunctionSupportConfiger.TAXI_TAG, "0");
            jSONObject.put("req_num", "1");
            jSONObject.put(ModuleCarOwner.KEY_VEHICLE, "1");
            jSONObject.put("request_mole", "YES");
            JSONObject jSONObject2 = new JSONObject();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(poi.getPoint().getLongitude());
            jSONObject2.put(DictionaryKeys.CTRLXY_X, sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append(poi.getPoint().getLatitude());
            jSONObject2.put(DictionaryKeys.CTRLXY_Y, sb4.toString());
            jSONObject2.put("id", poi.getId());
            jSONObject2.put("precision", i);
            jSONObject2.put("pid", poi.getPid());
            jSONObject2.put("floor", poi.getIndoorFloorNoName());
            jSONObject2.put("type", poi.getType() != null ? poi.getType() : "");
            jSONObject2.put("name", poi.getName() != null ? poi.getName() : "");
            jSONObject2.put(ProcessInfo.ALIAS_EXT, poi.getEndPoiExtension() != null ? poi.getEndPoiExtension() : "");
            ArrayList<GeoPoint> entranceList = poi.getEntranceList();
            if (entranceList == null || entranceList.isEmpty()) {
                jSONObject2.put("x_entr", "");
                jSONObject2.put("y_entr", "");
            } else {
                GeoPoint geoPoint = entranceList.get(0);
                jSONObject2.put("x_entr", String.valueOf(geoPoint.getLongitude()));
                jSONObject2.put("y_entr", String.valueOf(geoPoint.getLatitude()));
            }
            ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
            if (iSearchPoiData != null) {
                jSONObject2.put("p_rel", iSearchPoiData.getChildType() != null ? iSearchPoiData.getChildType() : "");
                jSONObject2.put("angle", iSearchPoiData.getTowardsAngle() != null ? iSearchPoiData.getTowardsAngle() : "");
            } else {
                jSONObject2.put("p_rel", "");
                jSONObject2.put("angle", "");
            }
            jSONObject.put(H5PageData.KEY_UC_START, jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(poi2.getPoint().getLongitude());
            jSONObject3.put(DictionaryKeys.CTRLXY_X, sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(poi2.getPoint().getLatitude());
            jSONObject3.put(DictionaryKeys.CTRLXY_Y, sb6.toString());
            jSONObject3.put("id", poi2.getId());
            jSONObject3.put("precision", i2);
            jSONObject3.put("pid", poi2.getPid());
            jSONObject3.put("floor", poi2.getIndoorFloorNoName());
            jSONObject3.put("type", poi2.getType() != null ? poi2.getType() : "");
            jSONObject3.put("name", poi2.getName() != null ? poi2.getName() : "");
            jSONObject3.put(ProcessInfo.ALIAS_EXT, poi2.getEndPoiExtension() != null ? poi2.getEndPoiExtension() : "");
            ArrayList<GeoPoint> entranceList2 = poi2.getEntranceList();
            if (entranceList2 == null || entranceList2.isEmpty()) {
                jSONObject3.put("x_entr", "");
                jSONObject3.put("y_entr", "");
            } else {
                GeoPoint geoPoint2 = entranceList2.get(0);
                jSONObject3.put("x_entr", String.valueOf(geoPoint2.getLongitude()));
                jSONObject3.put("y_entr", String.valueOf(geoPoint2.getLatitude()));
            }
            ISearchPoiData iSearchPoiData2 = (ISearchPoiData) poi2.as(ISearchPoiData.class);
            if (iSearchPoiData2 != null) {
                jSONObject3.put("p_rel", iSearchPoiData2.getChildType() != null ? iSearchPoiData2.getChildType() : "");
                jSONObject3.put("angle", iSearchPoiData2.getTowardsAngle() != null ? iSearchPoiData2.getTowardsAngle() : "");
            } else {
                jSONObject3.put("p_rel", "");
                jSONObject3.put("angle", "");
            }
            jSONObject.put("end", jSONObject3);
            return jSONObject;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return null;
        }
    }

    private static JSONObject d(POI poi, POI poi2) {
        int i;
        if (TextUtils.equals("我的位置", poi.getName())) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation == null || !latestLocation.getProvider().equals(IndoorLocationProvider.NAME)) {
                if (latestPosition != null) {
                    poi.setPoint(latestPosition);
                }
                poi.setPid("");
                poi.setInoorFloorNoName("");
            } else {
                Bundle extras = latestLocation.getExtras();
                if (extras != null) {
                    String string = extras.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                    String string2 = extras.getString("floor");
                    if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                        if (latestPosition != null) {
                            poi.setPoint(latestPosition);
                        }
                        poi.setPid(string);
                        poi.setInoorFloorNoName(string2);
                    }
                }
            }
        }
        if (TextUtils.equals(poi2.getName(), "我的位置")) {
            GeoPoint latestPosition2 = LocationInstrument.getInstance().getLatestPosition();
            Location latestLocation2 = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation2 == null || !TextUtils.equals(latestLocation2.getProvider(), IndoorLocationProvider.NAME)) {
                if (latestPosition2 != null) {
                    poi2.setPoint(latestPosition2);
                }
                poi2.setPid("");
                poi2.setInoorFloorNoName("");
            } else {
                Bundle extras2 = latestLocation2.getExtras();
                if (extras2 != null) {
                    String string3 = extras2.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                    String string4 = extras2.getString("floor");
                    if (!TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string4)) {
                        if (latestPosition2 != null) {
                            poi2.setPoint(latestPosition2);
                        }
                        poi2.setPid(string3);
                        poi2.setInoorFloorNoName(string4);
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        try {
            int i2 = 2;
            if (a(poi.getId())) {
                sb.append(poi.getId());
                sb.append(",");
                i = 3;
            } else {
                sb.append(poi.getPoint().getLongitude());
                sb.append(",");
                sb.append(poi.getPoint().getLatitude());
                sb.append(",");
                if (TextUtils.equals("我的位置", poi.getName())) {
                    i = 1;
                } else {
                    if (!TextUtils.equals(DriveUtil.MAP_PLACE_DES, poi.getName())) {
                        if (!TextUtils.equals("地图选定位置", poi.getName())) {
                            i = 4;
                        }
                    }
                    i = 2;
                }
            }
            if (a(poi2.getId())) {
                sb.append(poi2.getId());
                sb.append(",");
                i2 = 3;
            } else {
                sb.append(poi2.getPoint().getLongitude());
                sb.append(",");
                sb.append(poi2.getPoint().getLatitude());
                sb.append(",");
                if (TextUtils.equals("我的位置", poi2.getName())) {
                    i2 = 1;
                } else if (!DriveUtil.MAP_PLACE_DES.equals(poi2.getName())) {
                    if (!"地图选定位置".equals(poi2.getName())) {
                        i2 = 4;
                    }
                }
            }
            sb.append(i);
            sb.append(",");
            sb.append(i2);
            sb.append(",0,0,100000");
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("lv", "4.1");
            jSONObject.put("isindoor", "1");
            jSONObject.put("maxLength", "100000");
            jSONObject.put(FunctionSupportConfiger.TAXI_TAG, "0");
            jSONObject.put("req_num", "1");
            JSONObject jSONObject2 = new JSONObject();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(poi.getPoint().getLongitude());
            jSONObject2.put(DictionaryKeys.CTRLXY_X, sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(poi.getPoint().getLatitude());
            jSONObject2.put(DictionaryKeys.CTRLXY_Y, sb3.toString());
            jSONObject2.put("id", poi.getId());
            jSONObject2.put("precision", i);
            jSONObject2.put("pid", poi.getPid());
            jSONObject2.put("floor", poi.getIndoorFloorNoName());
            jSONObject.put(H5PageData.KEY_UC_START, jSONObject2);
            JSONObject jSONObject3 = new JSONObject();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(poi2.getPoint().getLongitude());
            jSONObject3.put(DictionaryKeys.CTRLXY_X, sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(poi2.getPoint().getLatitude());
            jSONObject3.put(DictionaryKeys.CTRLXY_Y, sb5.toString());
            jSONObject3.put("id", poi2.getId());
            jSONObject3.put("precision", i2);
            jSONObject3.put("pid", poi2.getPid());
            jSONObject3.put("floor", poi2.getIndoorFloorNoName());
            jSONObject.put("end", jSONObject3);
            return jSONObject;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return null;
        }
    }

    private static boolean a(String str) {
        if (!TextUtils.isEmpty(str) && !a.matcher(str).matches()) {
            return true;
        }
        return false;
    }

    public static final String a(int i, String str, POI poi, POI poi2) {
        JSONObject b = b(poi, poi2);
        JSONObject d = d(poi, poi2);
        JSONObject c = c(poi, poi2);
        JSONObject a2 = a(i, poi, poi2);
        JSONObject a3 = a(poi, poi2);
        JSONObject jSONObject = new JSONObject();
        try {
            if (!TextUtils.isEmpty(str)) {
                if (str.contains(ShowRouteActionProcessor.SEARCH_TYPE_WALK)) {
                    jSONObject.put(ShowRouteActionProcessor.SEARCH_TYPE_WALK, d);
                }
                if (str.contains("bicycle")) {
                    jSONObject.put("bicycle", c);
                }
                if (str.contains(FunctionSupportConfiger.TAXI_TAG)) {
                    jSONObject.put(FunctionSupportConfiger.TAXI_TAG, a3);
                }
                if (str.contains(ShowRouteActionProcessor.SEARCH_TYPE_BUS)) {
                    jSONObject.put(ShowRouteActionProcessor.SEARCH_TYPE_BUS, b);
                }
                if (str.contains("car")) {
                    jSONObject.put("car", a2);
                }
                return jSONObject.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
