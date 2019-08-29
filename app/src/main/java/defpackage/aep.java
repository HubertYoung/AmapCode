package defpackage;

import android.graphics.Point;
import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.poi.CpData;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.external.PoiLocationInfo;
import com.autonavi.bundle.entity.infolite.external.ResponseHeaderModule;
import com.autonavi.common.model.GeoPoint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xidea.el.json.JSONEncoder;

/* renamed from: aep reason: default package */
/* compiled from: InfoliteOnlineParserForExternal */
public final class aep {
    public static boolean a(JSONObject jSONObject, aud aud) {
        if (jSONObject == null) {
            return false;
        }
        if (aud.c == null) {
            aud.c = new ResponseHeaderModule();
        }
        aud.c.version = jSONObject.optString("version");
        aud.c.result = jSONObject.optBoolean("result");
        aud.c.errorCode = jSONObject.optInt("code");
        aud.c.errorMessage = jSONObject.optString("message");
        aud.c.timeStamp = jSONObject.optLong("timestamp");
        if (aud.b == null) {
            aud.b = new auc();
        }
        JSONObject optJSONObject = jSONObject.optJSONObject("lqii");
        if (aud.b.a == null) {
            aud.b.a = new aub();
        }
        if (jSONObject.has("total") && jSONObject.opt("total") != null) {
            try {
                aud.b.e = jSONObject.optInt("total");
            } catch (NumberFormatException unused) {
            }
        }
        JSONArray jSONArray = null;
        if (optJSONObject != null) {
            aud.b.a.e = optJSONObject.optInt("self_navigation");
            aud.b.a.d = a(optJSONObject, (String) "querytype");
            JSONObject optJSONObject2 = optJSONObject.optJSONObject("recommend_info");
            if (optJSONObject2 != null) {
                JSONObject optJSONObject3 = optJSONObject2.optJSONObject("city");
                if (optJSONObject3 != null) {
                    aud.b.a.c = optJSONObject3.optString("current_adcode");
                }
            }
            String valueOf = String.valueOf(optJSONObject.optString("view_region"));
            if (!TextUtils.isEmpty(valueOf) && valueOf.indexOf(44) > 0) {
                String[] split = valueOf.split(",");
                if (split.length == 4) {
                    aud.b.a.b = new Double[4];
                    try {
                        aud.b.a.b[0] = Double.valueOf(Double.parseDouble(split[0]));
                        aud.b.a.b[1] = Double.valueOf(Double.parseDouble(split[1]));
                        aud.b.a.b[2] = Double.valueOf(Double.parseDouble(split[2]));
                        aud.b.a.b[3] = Double.valueOf(Double.parseDouble(split[3]));
                    } catch (Exception unused2) {
                        aud.b.a.b = null;
                    }
                }
            }
            aud.b.a.a = optJSONObject.optString("expand_range_tip");
        }
        if (!(jSONObject == null || optJSONObject == null)) {
            jSONArray = jSONObject.optJSONArray("poi_list");
            if (aud.b.d == null) {
                aud.b.d = new ArrayList<>();
                aud.b.c = new ArrayList<>();
                aud.b.b = new ArrayList<>();
            }
            int i = aud.b.a.d;
            if (i != 5) {
                switch (i) {
                    case 1:
                        if (aud.a == null) {
                            aud.a = new PoiLocationInfo();
                        }
                        a(aud, jSONObject);
                        break;
                    case 2:
                    case 3:
                        break;
                    default:
                        if (aud.b.d == null) {
                            aud.b.d = new ArrayList<>();
                            aud.b.c = new ArrayList<>();
                            aud.b.b = new ArrayList<>();
                        }
                        a(jSONObject, aud.b.d, jSONArray, aud.b.c, aud.b.b);
                        break;
                }
            }
            if (aud.b.d == null) {
                aud.b.d = new ArrayList<>();
                aud.b.c = new ArrayList<>();
                aud.b.b = new ArrayList<>();
            }
            a(jSONObject, aud.b.d, jSONArray, aud.b.c, aud.b.b);
        }
        if (jSONArray != null) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:218:0x0508, code lost:
        if (r1 > 0) goto L_0x0511;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x011f, code lost:
        if (1 == r10.getPoiChildrenInfo().childType) goto L_0x0121;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int a(org.json.JSONObject r21, java.util.ArrayList<com.autonavi.common.model.POI> r22, org.json.JSONArray r23, java.util.ArrayList<com.autonavi.bundle.entity.infolite.external.CitySuggestion> r24, java.util.ArrayList<java.lang.String> r25) {
        /*
            r1 = r21
            r2 = r24
            r3 = r25
            r4 = 0
            if (r1 != 0) goto L_0x000a
            return r4
        L_0x000a:
            java.lang.String r5 = "total"
            int r5 = r1.optInt(r5)
            if (r3 == 0) goto L_0x009c
            if (r2 != 0) goto L_0x0016
            goto L_0x009c
        L_0x0016:
            r25.clear()     // Catch:{ Exception -> 0x0097 }
            r24.clear()     // Catch:{ Exception -> 0x0097 }
            java.lang.String r6 = "suggestion"
            org.json.JSONObject r6 = r1.optJSONObject(r6)     // Catch:{ Exception -> 0x0097 }
            if (r6 != 0) goto L_0x0026
            goto L_0x009c
        L_0x0026:
            java.lang.String r7 = "keywords"
            org.json.JSONArray r7 = r6.optJSONArray(r7)     // Catch:{ Exception -> 0x0097 }
            if (r7 == 0) goto L_0x0045
            int r8 = r7.length()     // Catch:{ Exception -> 0x0097 }
            if (r8 <= 0) goto L_0x0045
            int r8 = r7.length()     // Catch:{ Exception -> 0x0097 }
            r9 = 0
        L_0x0039:
            if (r9 >= r8) goto L_0x0045
            java.lang.String r10 = r7.optString(r9)     // Catch:{ Exception -> 0x0097 }
            r3.add(r10)     // Catch:{ Exception -> 0x0097 }
            int r9 = r9 + 1
            goto L_0x0039
        L_0x0045:
            java.lang.String r3 = "regions"
            org.json.JSONArray r3 = r6.optJSONArray(r3)     // Catch:{ Exception -> 0x0097 }
            if (r3 == 0) goto L_0x009c
            int r6 = r3.length()     // Catch:{ Exception -> 0x0097 }
            if (r6 <= 0) goto L_0x009c
            r6 = 0
        L_0x0054:
            int r7 = r3.length()     // Catch:{ Exception -> 0x0097 }
            if (r6 >= r7) goto L_0x009c
            org.json.JSONObject r7 = r3.optJSONObject(r6)     // Catch:{ Exception -> 0x0097 }
            com.autonavi.bundle.entity.infolite.external.CitySuggestion r8 = new com.autonavi.bundle.entity.infolite.external.CitySuggestion     // Catch:{ Exception -> 0x0097 }
            r8.<init>()     // Catch:{ Exception -> 0x0097 }
            java.lang.String r9 = "name"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x0097 }
            r8.name = r9     // Catch:{ Exception -> 0x0097 }
            java.lang.String r9 = "ename"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x0097 }
            r8.ename = r9     // Catch:{ Exception -> 0x0097 }
            java.lang.String r9 = "adcode"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x0097 }
            r8.adcode = r9     // Catch:{ Exception -> 0x0097 }
            java.lang.String r9 = "areacode"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Exception -> 0x0097 }
            r8.citycode = r9     // Catch:{ Exception -> 0x0097 }
            java.lang.String r9 = "total"
            int r7 = r7.optInt(r9)     // Catch:{ Exception -> 0x0097 }
            r8.resultnum = r7     // Catch:{ Exception -> 0x0097 }
            r2.add(r8)     // Catch:{ Exception -> 0x0097 }
            int r6 = r6 + 1
            goto L_0x0054
        L_0x0091:
            r0 = move-exception
            r1 = r0
            r16 = r5
            goto L_0x063b
        L_0x0097:
            r0 = move-exception
            r2 = r0
            defpackage.kf.a(r2)     // Catch:{ JSONException -> 0x0091 }
        L_0x009c:
            if (r23 != 0) goto L_0x00a5
            java.lang.String r2 = "poi_list"
            org.json.JSONArray r1 = r1.optJSONArray(r2)     // Catch:{ JSONException -> 0x0091 }
            goto L_0x00a7
        L_0x00a5:
            r1 = r23
        L_0x00a7:
            if (r1 != 0) goto L_0x00aa
            return r5
        L_0x00aa:
            int r2 = r1.length()     // Catch:{ JSONException -> 0x0637 }
            if (r22 != 0) goto L_0x00b6
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ JSONException -> 0x0091 }
            r3.<init>(r2)     // Catch:{ JSONException -> 0x0091 }
            goto L_0x00b8
        L_0x00b6:
            r3 = r22
        L_0x00b8:
            r6 = 0
            r7 = 0
        L_0x00ba:
            r8 = 1
            if (r6 >= r2) goto L_0x0614
            org.json.JSONObject r9 = r1.getJSONObject(r6)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r10 = "item_type"
            java.lang.String r10 = r9.optString(r10)     // Catch:{ JSONException -> 0x0637 }
            boolean r10 = defpackage.bcy.a(r10)     // Catch:{ JSONException -> 0x0637 }
            if (r10 == 0) goto L_0x0603
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r10 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r10 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r10)     // Catch:{ JSONException -> 0x0637 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r10 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r10     // Catch:{ JSONException -> 0x0637 }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r11 = r10.getPoiChildrenInfo()     // Catch:{ JSONException -> 0x0637 }
            if (r11 != 0) goto L_0x00e3
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r11 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData     // Catch:{ JSONException -> 0x0091 }
            r11.<init>()     // Catch:{ JSONException -> 0x0091 }
            r10.setPoiChildrenInfo(r11)     // Catch:{ JSONException -> 0x0091 }
        L_0x00e3:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r11 = r10.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x0637 }
            if (r11 != 0) goto L_0x00f1
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r11 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData     // Catch:{ JSONException -> 0x0091 }
            r11.<init>()     // Catch:{ JSONException -> 0x0091 }
            r10.setIndoorPoiInfo(r11)     // Catch:{ JSONException -> 0x0091 }
        L_0x00f1:
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r11 = r10.getIDynamicRenderInfo()     // Catch:{ JSONException -> 0x0637 }
            if (r11 != 0) goto L_0x00ff
            com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData r11 = new com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData     // Catch:{ JSONException -> 0x0091 }
            r11.<init>()     // Catch:{ JSONException -> 0x0091 }
            r10.setIDynamicRenderInfo(r11)     // Catch:{ JSONException -> 0x0091 }
        L_0x00ff:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData r11 = r10.getPoiAutoNaviInfo()     // Catch:{ JSONException -> 0x0637 }
            if (r11 != 0) goto L_0x010d
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData r11 = new com.autonavi.minimap.search.model.searchpoi.searchpoitype.AutoNaviPoiData     // Catch:{ JSONException -> 0x0091 }
            r11.<init>()     // Catch:{ JSONException -> 0x0091 }
            r10.setPoiAutoNaviInfo(r11)     // Catch:{ JSONException -> 0x0091 }
        L_0x010d:
            b(r10, r9)     // Catch:{ JSONException -> 0x0637 }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r11 = r10.getPoiChildrenInfo()     // Catch:{ JSONException -> 0x0637 }
            int r11 = r11.childType     // Catch:{ JSONException -> 0x0637 }
            r12 = 2
            if (r12 == r11) goto L_0x0121
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r11 = r10.getPoiChildrenInfo()     // Catch:{ JSONException -> 0x0091 }
            int r11 = r11.childType     // Catch:{ JSONException -> 0x0091 }
            if (r8 != r11) goto L_0x0122
        L_0x0121:
            r7 = 1
        L_0x0122:
            java.lang.String r8 = "heat_map_flag"
            int r8 = r9.optInt(r8, r4)     // Catch:{ JSONException -> 0x0637 }
            r10.setHeatMapFlag(r8)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r8 = "id"
            java.lang.String r8 = r9.optString(r8)     // Catch:{ JSONException -> 0x0637 }
            r10.setId(r8)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r8 = "industry"
            java.lang.String r8 = r9.optString(r8)     // Catch:{ JSONException -> 0x0637 }
            r10.setIndustry(r8)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r8 = "aoi"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0637 }
            if (r8 == 0) goto L_0x0167
            java.lang.String r8 = "aoi"
            java.lang.String r8 = r9.getString(r8)     // Catch:{ JSONException -> 0x0091 }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ JSONException -> 0x0091 }
            if (r8 != 0) goto L_0x0167
            java.lang.String r8 = "aoi"
            java.lang.String r8 = r9.getString(r8)     // Catch:{ JSONException -> 0x0091 }
            java.util.ArrayList r8 = defpackage.aeq.a(r8)     // Catch:{ JSONException -> 0x0091 }
            java.util.HashMap r11 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0091 }
            java.lang.String r13 = "poi_polygon_bounds"
            r11.put(r13, r8)     // Catch:{ JSONException -> 0x0091 }
            r10.setRegions(r8)     // Catch:{ JSONException -> 0x0091 }
        L_0x0167:
            java.lang.String r8 = "name"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0637 }
            if (r8 == 0) goto L_0x0178
            java.lang.String r8 = "name"
            java.lang.String r8 = r9.optString(r8)     // Catch:{ JSONException -> 0x0091 }
            r10.setName(r8)     // Catch:{ JSONException -> 0x0091 }
        L_0x0178:
            java.util.HashMap r8 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r11 = "rating"
            java.lang.String r13 = "rating"
            r14 = 0
            java.lang.String r13 = r9.optString(r13, r14)     // Catch:{ JSONException -> 0x0637 }
            r8.put(r11, r13)     // Catch:{ JSONException -> 0x0637 }
            java.util.HashMap r8 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r11 = "averagecost"
            java.lang.String r13 = "averagecost"
            java.lang.String r13 = r9.optString(r13, r14)     // Catch:{ JSONException -> 0x0637 }
            r8.put(r11, r13)     // Catch:{ JSONException -> 0x0637 }
            java.util.HashMap r8 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r11 = "averagecostname"
            java.lang.String r13 = "averagecostname"
            java.lang.String r13 = r9.optString(r13, r14)     // Catch:{ JSONException -> 0x0637 }
            r8.put(r11, r13)     // Catch:{ JSONException -> 0x0637 }
            java.util.HashMap r8 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r11 = "businfo_station_status"
            java.lang.String r13 = "businfo_station_status"
            java.lang.String r13 = r9.optString(r13, r14)     // Catch:{ JSONException -> 0x0637 }
            r8.put(r11, r13)     // Catch:{ JSONException -> 0x0637 }
            java.util.HashMap r8 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r11 = "update_flag"
            java.lang.String r13 = "update_flag"
            java.lang.String r13 = r9.optString(r13, r14)     // Catch:{ JSONException -> 0x0637 }
            r8.put(r11, r13)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r8 = "typecode"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0637 }
            if (r8 == 0) goto L_0x01d5
            java.lang.String r8 = "typecode"
            java.lang.String r8 = r9.optString(r8)     // Catch:{ JSONException -> 0x0091 }
            r10.setType(r8)     // Catch:{ JSONException -> 0x0091 }
        L_0x01d5:
            java.lang.String r8 = "need_arrive_timecost"
            int r8 = r9.optInt(r8, r4)     // Catch:{ JSONException -> 0x0637 }
            r10.setNeedArriveTimeCost(r8)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r8 = "recommend_flag"
            int r8 = r9.optInt(r8, r4)     // Catch:{ JSONException -> 0x0637 }
            r10.setRecommendFlag(r8)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r8 = "reference_rlt_flag"
            int r8 = r9.optInt(r8, r4)     // Catch:{ JSONException -> 0x0637 }
            r10.setReferenceRltFlag(r8)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r8 = "sndt_fl_nona"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0637 }
            if (r8 == 0) goto L_0x0204
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r8 = r10.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x0091 }
            java.lang.String r11 = "sndt_fl_nona"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ JSONException -> 0x0091 }
            r8.sndtFloorName = r11     // Catch:{ JSONException -> 0x0091 }
        L_0x0204:
            java.lang.String r8 = "sndt_parentid"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0637 }
            if (r8 == 0) goto L_0x0218
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r8 = r10.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x0091 }
            java.lang.String r11 = "sndt_parentid"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ JSONException -> 0x0091 }
            r8.buildingPoiId = r11     // Catch:{ JSONException -> 0x0091 }
        L_0x0218:
            java.lang.String r8 = "f_nona"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0637 }
            if (r8 == 0) goto L_0x023a
            java.lang.String r8 = "f_nona"
            java.lang.String r8 = r9.optString(r8)     // Catch:{ JSONException -> 0x0091 }
            r10.setFnona(r8)     // Catch:{ JSONException -> 0x0091 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r8 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r8 = r10.as(r8)     // Catch:{ JSONException -> 0x0091 }
            com.amap.bundle.datamodel.FavoritePOI r8 = (com.amap.bundle.datamodel.FavoritePOI) r8     // Catch:{ JSONException -> 0x0091 }
            java.lang.String r11 = "f_nona"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ JSONException -> 0x0091 }
            r8.setFnona(r11)     // Catch:{ JSONException -> 0x0091 }
        L_0x023a:
            java.lang.String r8 = "sndt_fl_no"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0637 }
            if (r8 == 0) goto L_0x024e
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r8 = r10.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x0091 }
            java.lang.String r11 = "sndt_fl_no"
            int r11 = r9.optInt(r11, r4)     // Catch:{ JSONException -> 0x0091 }
            r8.floorNo = r11     // Catch:{ JSONException -> 0x0091 }
        L_0x024e:
            java.lang.String r8 = "sndt_parentid"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0637 }
            if (r8 == 0) goto L_0x0262
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.IndoorPoiData r8 = r10.getIndoorPoiInfo()     // Catch:{ JSONException -> 0x0091 }
            java.lang.String r11 = "sndt_parentid"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ JSONException -> 0x0091 }
            r8.parentId = r11     // Catch:{ JSONException -> 0x0091 }
        L_0x0262:
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r8 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r8 = r10.as(r8)     // Catch:{ JSONException -> 0x0637 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r8 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r8     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r11 = "display_icon_name_state"
            int r11 = r9.optInt(r11)     // Catch:{ JSONException -> 0x0637 }
            r8.setDisplayIconNameState(r11)     // Catch:{ JSONException -> 0x0637 }
            java.lang.String r8 = "longitude"
            r13 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r16 = r5
            double r4 = r9.optDouble(r8, r13)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r8 = "latitude"
            r17 = r1
            r18 = r2
            double r1 = r9.optDouble(r8, r13)     // Catch:{ JSONException -> 0x0635 }
            int r8 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r8 == 0) goto L_0x0296
            int r8 = (r1 > r13 ? 1 : (r1 == r13 ? 0 : -1))
            if (r8 == 0) goto L_0x0296
            com.autonavi.common.model.GeoPoint r8 = r10.getPoint()     // Catch:{ JSONException -> 0x0635 }
            r8.setLonLat(r4, r1)     // Catch:{ JSONException -> 0x0635 }
        L_0x0296:
            java.lang.String r1 = "display_x"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x02ba
            java.lang.String r1 = "display_x"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x02ba
            java.lang.String r1 = "display_x"
            double r1 = r9.optDouble(r1)     // Catch:{ Exception -> 0x02ba }
            java.lang.String r4 = "display_y"
            double r4 = r9.optDouble(r4)     // Catch:{ Exception -> 0x02ba }
            com.autonavi.common.model.GeoPoint r8 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x02ba }
            r8.<init>(r1, r4)     // Catch:{ Exception -> 0x02ba }
            r10.setDisplayPoint(r8)     // Catch:{ Exception -> 0x02ba }
        L_0x02ba:
            java.lang.String r1 = "parent"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x02dc
            java.lang.String r1 = "parent"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setParent(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r10.as(r1)     // Catch:{ JSONException -> 0x0635 }
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "parent"
            java.lang.String r2 = r9.optString(r2)     // Catch:{ JSONException -> 0x0635 }
            r1.setParent(r2)     // Catch:{ JSONException -> 0x0635 }
        L_0x02dc:
            java.lang.String r1 = "childType"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x02ff
            java.lang.String r1 = "childType"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setChildType(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r10.as(r1)     // Catch:{ JSONException -> 0x0635 }
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "childType"
            java.lang.String r2 = r9.optString(r2)     // Catch:{ JSONException -> 0x0635 }
            r1.setChildType(r2)     // Catch:{ JSONException -> 0x0635 }
            goto L_0x0321
        L_0x02ff:
            java.lang.String r1 = "childtype"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x0321
            java.lang.String r1 = "childtype"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setChildType(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r10.as(r1)     // Catch:{ JSONException -> 0x0635 }
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "childtype"
            java.lang.String r2 = r9.optString(r2)     // Catch:{ JSONException -> 0x0635 }
            r1.setChildType(r2)     // Catch:{ JSONException -> 0x0635 }
        L_0x0321:
            java.lang.String r1 = "towards_angle"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x0343
            java.lang.String r1 = "towards_angle"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setTowardsAngle(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r10.as(r1)     // Catch:{ JSONException -> 0x0635 }
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "towards_angle"
            java.lang.String r2 = r9.optString(r2)     // Catch:{ JSONException -> 0x0635 }
            r1.setTowardsAngle(r2)     // Catch:{ JSONException -> 0x0635 }
        L_0x0343:
            java.lang.String r1 = "end_poi_extension"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x0365
            java.lang.String r1 = "end_poi_extension"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setEndPoiExtension(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r10.as(r1)     // Catch:{ JSONException -> 0x0635 }
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "end_poi_extension"
            java.lang.String r2 = r9.optString(r2)     // Catch:{ JSONException -> 0x0635 }
            r1.setEndPoiExtension(r2)     // Catch:{ JSONException -> 0x0635 }
        L_0x0365:
            java.lang.String r1 = "transparent"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x0387
            java.lang.String r1 = "transparent"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setTransparent(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r10.as(r1)     // Catch:{ JSONException -> 0x0635 }
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "transparent"
            java.lang.String r2 = r9.optString(r2)     // Catch:{ JSONException -> 0x0635 }
            r1.setTransparent(r2)     // Catch:{ JSONException -> 0x0635 }
        L_0x0387:
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "tra_tag"
            java.lang.String r4 = "tra_tag"
            java.lang.String r4 = r9.optString(r4)     // Catch:{ JSONException -> 0x0635 }
            r1.put(r2, r4)     // Catch:{ JSONException -> 0x0635 }
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "tra_title"
            java.lang.String r4 = "tra_title"
            java.lang.String r4 = r9.optString(r4)     // Catch:{ JSONException -> 0x0635 }
            r1.put(r2, r4)     // Catch:{ JSONException -> 0x0635 }
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "tra_action"
            java.lang.String r4 = "tra_action"
            java.lang.String r4 = r9.optString(r4)     // Catch:{ JSONException -> 0x0635 }
            r1.put(r2, r4)     // Catch:{ JSONException -> 0x0635 }
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "tra_action_param"
            java.lang.String r4 = "tra_action_param"
            java.lang.String r4 = r9.optString(r4)     // Catch:{ JSONException -> 0x0635 }
            r1.put(r2, r4)     // Catch:{ JSONException -> 0x0635 }
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "ugc_title"
            java.lang.String r4 = "ugc_title"
            java.lang.String r4 = r9.optString(r4)     // Catch:{ JSONException -> 0x0635 }
            r1.put(r2, r4)     // Catch:{ JSONException -> 0x0635 }
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "ugc_thread_url"
            java.lang.String r4 = "ugc_thread_url"
            java.lang.String r4 = r9.optString(r4)     // Catch:{ JSONException -> 0x0635 }
            r1.put(r2, r4)     // Catch:{ JSONException -> 0x0635 }
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "ugc_plot_url"
            java.lang.String r4 = "ugc_plot_url"
            java.lang.String r4 = r9.optString(r4)     // Catch:{ JSONException -> 0x0635 }
            r1.put(r2, r4)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r1 = "citycode"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setCityCode(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r1 = "adcode"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setAdCode(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r1 = "s_duration"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            r10.setSketchDuration(r1)     // Catch:{ JSONException -> 0x0635 }
            c(r10, r9)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r1 = "address"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x048e
            java.lang.String r1 = r10.getType()     // Catch:{ JSONException -> 0x0635 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r2 != 0) goto L_0x042e
            int r2 = r1.length()     // Catch:{ JSONException -> 0x0635 }
            r4 = 5
            if (r2 <= r4) goto L_0x042e
            r2 = 4
            r4 = 0
            java.lang.String r1 = r1.substring(r4, r2)     // Catch:{ JSONException -> 0x0635 }
            goto L_0x042f
        L_0x042e:
            r4 = 0
        L_0x042f:
            java.lang.String r2 = "address"
            java.lang.String r2 = r9.optString(r2)     // Catch:{ JSONException -> 0x0635 }
            r10.setAddr(r2)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r5 = "1507"
            boolean r5 = r1.equals(r5)     // Catch:{ JSONException -> 0x0635 }
            if (r5 != 0) goto L_0x048f
            java.lang.String r5 = "1505"
            boolean r1 = r1.equals(r5)     // Catch:{ JSONException -> 0x0635 }
            if (r1 != 0) goto L_0x048f
            java.lang.String r1 = ""
            java.lang.String r5 = ""
            java.lang.String r8 = "districtname"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0635 }
            if (r8 == 0) goto L_0x045a
            java.lang.String r5 = "districtname"
            java.lang.String r5 = r9.optString(r5)     // Catch:{ JSONException -> 0x0635 }
        L_0x045a:
            java.lang.String r8 = "provincename"
            boolean r8 = r9.has(r8)     // Catch:{ JSONException -> 0x0635 }
            if (r8 == 0) goto L_0x0468
            java.lang.String r1 = "provincename"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
        L_0x0468:
            boolean r8 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r8 != 0) goto L_0x048a
            boolean r8 = android.text.TextUtils.isEmpty(r5)     // Catch:{ JSONException -> 0x0635 }
            if (r8 != 0) goto L_0x048a
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x0635 }
            r8.<init>()     // Catch:{ JSONException -> 0x0635 }
            r8.append(r1)     // Catch:{ JSONException -> 0x0635 }
            r8.append(r5)     // Catch:{ JSONException -> 0x0635 }
            r8.append(r2)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r1 = r8.toString()     // Catch:{ JSONException -> 0x0635 }
            r10.setAddr(r1)     // Catch:{ JSONException -> 0x0635 }
            goto L_0x048f
        L_0x048a:
            r10.setAddr(r2)     // Catch:{ JSONException -> 0x0635 }
            goto L_0x048f
        L_0x048e:
            r4 = 0
        L_0x048f:
            d(r10, r9)     // Catch:{ JSONException -> 0x0635 }
            e(r10, r9)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r1 = "stations"
            boolean r1 = r9.has(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x04ca
            java.lang.String r1 = "stations"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 != 0) goto L_0x04ca
            java.lang.String r1 = "stations"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r2 = "null"
            boolean r1 = r1.equals(r2)     // Catch:{ JSONException -> 0x0635 }
            if (r1 != 0) goto L_0x04ca
            java.lang.String r1 = "stations"
            org.json.JSONObject r1 = r9.getJSONObject(r1)     // Catch:{ JSONException -> 0x0635 }
            java.util.HashMap r2 = r10.getPoiExtra()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r5 = "stations"
            java.lang.String r1 = r1.toString()     // Catch:{ JSONException -> 0x0635 }
            r2.put(r5, r1)     // Catch:{ JSONException -> 0x0635 }
        L_0x04ca:
            com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ JSONException -> 0x0635 }
            com.autonavi.common.model.GeoPoint r1 = r1.getLatestPosition()     // Catch:{ JSONException -> 0x0635 }
            r2 = -100
            java.lang.String r5 = "distance"
            java.lang.String r5 = r9.optString(r5)     // Catch:{ Exception -> 0x050b }
            if (r5 == 0) goto L_0x04f1
            java.lang.String r8 = ""
            boolean r8 = r5.equals(r8)     // Catch:{ Exception -> 0x050b }
            if (r8 == 0) goto L_0x04e5
            goto L_0x04f1
        L_0x04e5:
            java.lang.Double r5 = java.lang.Double.valueOf(r5)     // Catch:{ Exception -> 0x050b }
            int r5 = r5.intValue()     // Catch:{ Exception -> 0x050b }
            if (r5 != 0) goto L_0x04f0
            goto L_0x04f1
        L_0x04f0:
            r2 = r5
        L_0x04f1:
            com.autonavi.common.model.GeoPoint r5 = r10.getPoint()     // Catch:{ Exception -> 0x050b }
            if (r2 > 0) goto L_0x0510
            if (r5 == 0) goto L_0x0510
            int r8 = r1.getAdCode()     // Catch:{ Exception -> 0x050b }
            int r11 = r5.getAdCode()     // Catch:{ Exception -> 0x050b }
            if (r8 == r11) goto L_0x0510
            float r1 = defpackage.bcz.a(r1, r5)     // Catch:{ Exception -> 0x050b }
            int r1 = (int) r1
            if (r1 <= 0) goto L_0x0510
            goto L_0x0511
        L_0x050b:
            r0 = move-exception
            r1 = r0
            defpackage.kf.a(r1)     // Catch:{ JSONException -> 0x0635 }
        L_0x0510:
            r1 = r2
        L_0x0511:
            r10.setDistance(r1)     // Catch:{ JSONException -> 0x0635 }
            f(r10, r9)     // Catch:{ JSONException -> 0x0635 }
            a(r10, r9)     // Catch:{ JSONException -> 0x0635 }
            r3.add(r10)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r1 = "correlation_list"
            org.json.JSONArray r1 = r9.optJSONArray(r1)     // Catch:{ JSONException -> 0x0635 }
            if (r1 == 0) goto L_0x0609
            int r2 = r1.length()     // Catch:{ JSONException -> 0x0635 }
            if (r2 <= 0) goto L_0x0609
            int r2 = r1.length()     // Catch:{ JSONException -> 0x0635 }
            aum r5 = new aum     // Catch:{ JSONException -> 0x0635 }
            r5.<init>()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r8 = r10.getId()     // Catch:{ JSONException -> 0x0635 }
            r5.a = r8     // Catch:{ JSONException -> 0x0635 }
            r8 = 0
        L_0x053b:
            if (r8 >= r2) goto L_0x05f7
            org.json.JSONObject r9 = r1.optJSONObject(r8)     // Catch:{ JSONException -> 0x0635 }
            if (r9 == 0) goto L_0x05ea
            aut r11 = new aut     // Catch:{ JSONException -> 0x0635 }
            r11.<init>()     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r13 = "mPoiId"
            java.lang.String r13 = r9.optString(r13)     // Catch:{ JSONException -> 0x0635 }
            r11.d = r13     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r13 = "mAnchor"
            boolean r13 = r9.has(r13)     // Catch:{ JSONException -> 0x0635 }
            if (r13 == 0) goto L_0x0591
            java.lang.String r13 = "mAnchor"
            java.lang.String r13 = r9.optString(r13)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r13 = r13.trim()     // Catch:{ JSONException -> 0x0635 }
            boolean r14 = android.text.TextUtils.isEmpty(r13)     // Catch:{ JSONException -> 0x0635 }
            if (r14 != 0) goto L_0x0591
            java.lang.String r14 = "0x"
            boolean r14 = r13.startsWith(r14)     // Catch:{ NumberFormatException -> 0x058c }
            if (r14 != 0) goto L_0x0578
            java.lang.String r14 = "0X"
            boolean r14 = r13.startsWith(r14)     // Catch:{ NumberFormatException -> 0x058c }
            if (r14 == 0) goto L_0x0591
        L_0x0578:
            java.lang.String r13 = r13.substring(r12)     // Catch:{ NumberFormatException -> 0x0586 }
            r14 = 16
            long r13 = java.lang.Long.parseLong(r13, r14)     // Catch:{ NumberFormatException -> 0x0586 }
            int r13 = (int) r13     // Catch:{ NumberFormatException -> 0x0586 }
            r11.e = r13     // Catch:{ NumberFormatException -> 0x0586 }
            goto L_0x0591
        L_0x0586:
            r0 = move-exception
            r13 = r0
            r13.printStackTrace()     // Catch:{ NumberFormatException -> 0x058c }
            goto L_0x0591
        L_0x058c:
            r0 = move-exception
            r13 = r0
            r13.printStackTrace()     // Catch:{ JSONException -> 0x0635 }
        L_0x0591:
            java.lang.String r13 = "mLabelName"
            java.lang.String r13 = r9.optString(r13)     // Catch:{ JSONException -> 0x0635 }
            r11.a = r13     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r13 = "mMainKey"
            int r13 = r9.optInt(r13)     // Catch:{ JSONException -> 0x0635 }
            r11.f = r13     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r13 = "mSubkey"
            int r13 = r9.optInt(r13)     // Catch:{ JSONException -> 0x0635 }
            r11.g = r13     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r13 = "mMaxzoom"
            r14 = 20
            int r13 = r9.optInt(r13, r14)     // Catch:{ JSONException -> 0x0635 }
            r11.i = r13     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r13 = "mMinzoom"
            int r13 = r9.optInt(r13)     // Catch:{ JSONException -> 0x0635 }
            r11.h = r13     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r13 = "mRank"
            java.lang.String r13 = r9.optString(r13)     // Catch:{ JSONException -> 0x0635 }
            float r13 = java.lang.Float.parseFloat(r13)     // Catch:{ JSONException -> 0x0635 }
            r11.j = r13     // Catch:{ JSONException -> 0x0635 }
            com.autonavi.common.model.GeoPoint r13 = new com.autonavi.common.model.GeoPoint     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r14 = "mLongitude"
            double r14 = r9.optDouble(r14)     // Catch:{ JSONException -> 0x0635 }
            java.lang.String r4 = "mLatitude"
            r19 = r1
            r20 = r2
            double r1 = r9.optDouble(r4)     // Catch:{ JSONException -> 0x0635 }
            r13.<init>(r14, r1)     // Catch:{ JSONException -> 0x0635 }
            int r1 = r13.x     // Catch:{ JSONException -> 0x0635 }
            r11.b = r1     // Catch:{ JSONException -> 0x0635 }
            int r1 = r13.y     // Catch:{ JSONException -> 0x0635 }
            r11.c = r1     // Catch:{ JSONException -> 0x0635 }
            java.util.List<aut> r1 = r5.b     // Catch:{ JSONException -> 0x0635 }
            r1.add(r11)     // Catch:{ JSONException -> 0x0635 }
            goto L_0x05ee
        L_0x05ea:
            r19 = r1
            r20 = r2
        L_0x05ee:
            int r8 = r8 + 1
            r1 = r19
            r2 = r20
            r4 = 0
            goto L_0x053b
        L_0x05f7:
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r1 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r1 = r10.as(r1)     // Catch:{ JSONException -> 0x0635 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r1 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r1     // Catch:{ JSONException -> 0x0635 }
            r1.setRecommendMode(r5)     // Catch:{ JSONException -> 0x0635 }
            goto L_0x0609
        L_0x0603:
            r17 = r1
            r18 = r2
            r16 = r5
        L_0x0609:
            int r6 = r6 + 1
            r5 = r16
            r1 = r17
            r2 = r18
            r4 = 0
            goto L_0x00ba
        L_0x0614:
            r16 = r5
            java.util.Iterator r1 = r3.iterator()     // Catch:{ JSONException -> 0x0635 }
        L_0x061a:
            boolean r2 = r1.hasNext()     // Catch:{ JSONException -> 0x0635 }
            if (r2 == 0) goto L_0x0634
            java.lang.Object r2 = r1.next()     // Catch:{ JSONException -> 0x0635 }
            com.autonavi.common.model.POI r2 = (com.autonavi.common.model.POI) r2     // Catch:{ JSONException -> 0x0635 }
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r3 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = r2.as(r3)     // Catch:{ JSONException -> 0x0635 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2     // Catch:{ JSONException -> 0x0635 }
            r3 = r7 ^ 1
            r2.setIsShowName(r3)     // Catch:{ JSONException -> 0x0635 }
            goto L_0x061a
        L_0x0634:
            return r16
        L_0x0635:
            r0 = move-exception
            goto L_0x063a
        L_0x0637:
            r0 = move-exception
            r16 = r5
        L_0x063a:
            r1 = r0
        L_0x063b:
            r1.printStackTrace()
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aep.a(org.json.JSONObject, java.util.ArrayList, org.json.JSONArray, java.util.ArrayList, java.util.ArrayList):int");
    }

    private static void b(SearchPoi searchPoi, JSONObject jSONObject) throws JSONException {
        JSONArray optJSONArray = jSONObject.optJSONArray("entrances");
        if (optJSONArray != null && optJSONArray.length() > 0) {
            ArrayList arrayList = new ArrayList(optJSONArray.length());
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                double optDouble = jSONObject2.optDouble("longitude", -1.0d);
                double optDouble2 = jSONObject2.optDouble("latitude", -1.0d);
                if (!(optDouble == -1.0d || optDouble2 == -1.0d)) {
                    Point a = bcz.a(optDouble2, optDouble);
                    arrayList.add(new GeoPoint(a.x, a.y));
                }
            }
            searchPoi.setEntranceList(arrayList);
        }
        JSONArray optJSONArray2 = jSONObject.optJSONArray("exits");
        if (optJSONArray2 != null && optJSONArray2.length() > 0) {
            ArrayList arrayList2 = new ArrayList(optJSONArray2.length());
            for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                JSONObject jSONObject3 = optJSONArray2.getJSONObject(i2);
                double optDouble3 = jSONObject3.optDouble("longitude", -1.0d);
                double optDouble4 = jSONObject3.optDouble("latitude", -1.0d);
                if (!(optDouble3 == -1.0d || optDouble4 == -1.0d)) {
                    Point a2 = bcz.a(optDouble4, optDouble3);
                    arrayList2.add(new GeoPoint(a2.x, a2.y));
                }
            }
            searchPoi.setExitList(arrayList2);
        }
        if (jSONObject.has("f_nona")) {
            searchPoi.getIndoorPoiInfo().floorName = jSONObject.optString("f_nona", "");
        }
        if (jSONObject.has("sndt_fl_no")) {
            searchPoi.getIndoorPoiInfo().floorNo = jSONObject.optInt("sndt_fl_no", 0);
        }
        if (jSONObject.has("sndt_parentid")) {
            searchPoi.getIndoorPoiInfo().parentId = jSONObject.optString("sndt_parentid", "");
        }
    }

    private static void c(SearchPoi searchPoi, JSONObject jSONObject) {
        try {
            if (jSONObject.has("parkinfo")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("parkinfo");
                Iterator<String> keys = jSONObject2.keys();
                if (keys != null) {
                    while (keys.hasNext()) {
                        String next = keys.next();
                        String string = jSONObject2.getString(next);
                        HashMap<String, Serializable> poiExtra = searchPoi.getPoiExtra();
                        StringBuilder sb = new StringBuilder();
                        sb.append("parkinfo");
                        sb.append("_");
                        sb.append(next);
                        poiExtra.put(sb.toString(), string);
                        if ("inout_info".equals(next)) {
                            JSONArray jSONArray = jSONObject2.getJSONArray("inout_info");
                            if (jSONArray != null) {
                                for (int i = 0; i < jSONArray.length(); i++) {
                                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                                    if (jSONObject3 != null) {
                                        String e = agd.e(jSONObject3, "keytype");
                                        if ("2".equals(e) || "0".equals(e)) {
                                            try {
                                                Point a = bcz.a(jSONObject3.getDouble(DictionaryKeys.CTRLXY_Y), jSONObject3.getDouble(DictionaryKeys.CTRLXY_X));
                                                if (searchPoi.getEntranceList() == null) {
                                                    searchPoi.setEntranceList(new ArrayList());
                                                }
                                                searchPoi.getEntranceList().add(new GeoPoint(a.x, a.y));
                                            } catch (Exception e2) {
                                                kf.a((Throwable) e2);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e3) {
            kf.a((Throwable) e3);
        }
    }

    private static void d(SearchPoi searchPoi, JSONObject jSONObject) throws JSONException {
        if (jSONObject.has("stations") && !TextUtils.isEmpty(jSONObject.optString("stations")) && !jSONObject.optString("stations").equals("null")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("stations");
            if (jSONObject2.has("businfo_line_keys")) {
                String[] split = jSONObject2.getString("businfo_line_keys").split(";|\\|");
                StringBuilder sb = new StringBuilder();
                HashMap hashMap = new HashMap();
                for (int i = 0; i < split.length; i++) {
                    if (!hashMap.containsKey(split[i])) {
                        hashMap.put(split[i], split[i]);
                        if (i != 0) {
                            sb.append("/");
                        }
                        sb.append(split[i]);
                    }
                }
                hashMap.clear();
                searchPoi.getPoiExtra().put("station_lines", sb.toString());
            }
            if (jSONObject2.has("businfo_lineids")) {
                searchPoi.getPoiExtra().put("businfo_lineids", (String) jSONObject2.get("businfo_lineids"));
            }
        }
    }

    /* JADX WARNING: type inference failed for: r4v0 */
    /* JADX WARNING: type inference failed for: r4v1, types: [java.util.Collection] */
    /* JADX WARNING: type inference failed for: r4v3, types: [java.util.Collection<? extends com.autonavi.common.model.POI>] */
    /* JADX WARNING: type inference failed for: r4v4, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r4v10, types: [java.util.List] */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r4v12 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r4v0
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], java.util.List, java.util.Collection<? extends com.autonavi.common.model.POI>]
      uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], java.util.Collection, java.util.List]
      mth insns count: 90
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void e(com.autonavi.bundle.entity.common.searchpoi.SearchPoi r7, org.json.JSONObject r8) throws org.json.JSONException {
        /*
            java.lang.String r0 = "child_stations"
            boolean r0 = r8.has(r0)
            if (r0 == 0) goto L_0x0129
            java.lang.String r0 = "child_stations"
            java.lang.String r0 = r8.optString(r0)
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x0129
            java.lang.String r0 = "child_stations"
            java.lang.String r0 = r8.optString(r0)
            java.lang.String r1 = "null"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x0129
            java.lang.String r0 = "child_stations"
            org.json.JSONArray r8 = r8.getJSONArray(r0)
            org.json.JSONArray r0 = new org.json.JSONArray
            r0.<init>()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 0
        L_0x0033:
            int r3 = r8.length()
            r4 = 0
            r5 = 1
            if (r2 >= r3) goto L_0x00ce
            org.json.JSONObject r3 = r8.getJSONObject(r2)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r6 = "businfo_lineids"
            java.lang.String r6 = r3.optString(r6)     // Catch:{ Exception -> 0x00c6 }
            r1.append(r6)     // Catch:{ Exception -> 0x00c6 }
            int r6 = r8.length()     // Catch:{ Exception -> 0x00c6 }
            int r6 = r6 - r5
            if (r2 >= r6) goto L_0x0054
            r5 = 59
            r1.append(r5)     // Catch:{ Exception -> 0x00c6 }
        L_0x0054:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r5 = r7.getPoiChildrenInfo()     // Catch:{ Exception -> 0x00c6 }
            if (r5 == 0) goto L_0x0062
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r4 = r7.getPoiChildrenInfo()     // Catch:{ Exception -> 0x00c6 }
            java.util.Collection<? extends com.autonavi.common.model.POI> r4 = r4.stationList     // Catch:{ Exception -> 0x00c6 }
            java.util.List r4 = (java.util.List) r4     // Catch:{ Exception -> 0x00c6 }
        L_0x0062:
            if (r4 == 0) goto L_0x00c2
            int r5 = r4.size()     // Catch:{ Exception -> 0x00c6 }
            int r6 = r2 + -1
            if (r5 < r6) goto L_0x00c2
            java.lang.Object r4 = r4.get(r2)     // Catch:{ Exception -> 0x00c6 }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r4 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r4     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "businfo_line_key"
            java.lang.String r6 = r4.getAddr()     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "x"
            com.autonavi.common.model.GeoPoint r6 = r4.getPoint()     // Catch:{ Exception -> 0x00c6 }
            int r6 = r6.x     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "y"
            com.autonavi.common.model.GeoPoint r6 = r4.getPoint()     // Catch:{ Exception -> 0x00c6 }
            int r6 = r6.y     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "name"
            java.lang.String r6 = r7.getName()     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "poiid2"
            java.lang.String r6 = r7.getId()     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "poiid"
            java.lang.String r6 = r4.getPoiId()     // Catch:{ Exception -> 0x00c6 }
            r3.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = r7.getId()     // Catch:{ Exception -> 0x00c6 }
            r4.setPoiId2(r5)     // Catch:{ Exception -> 0x00c6 }
            java.util.HashMap r4 = r4.getPoiExtra()     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "businfo_lineids"
            java.lang.String r6 = "businfo_lineids"
            java.lang.String r6 = r3.optString(r6)     // Catch:{ Exception -> 0x00c6 }
            r4.put(r5, r6)     // Catch:{ Exception -> 0x00c6 }
        L_0x00c2:
            r0.put(r2, r3)     // Catch:{ Exception -> 0x00c6 }
            goto L_0x00ca
        L_0x00c6:
            r3 = move-exception
            r3.printStackTrace()
        L_0x00ca:
            int r2 = r2 + 1
            goto L_0x0033
        L_0x00ce:
            java.lang.String r8 = r1.toString()
            boolean r1 = android.text.TextUtils.isEmpty(r8)
            if (r1 != 0) goto L_0x00e1
            java.util.HashMap r1 = r7.getPoiExtra()
            java.lang.String r2 = "businfo_lineids"
            r1.put(r2, r8)
        L_0x00e1:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r8 = r7.getPoiChildrenInfo()
            int r8 = r8.childType
            if (r8 != r5) goto L_0x0129
            java.util.HashMap r8 = r7.getPoiExtra()
            java.lang.String r1 = "child_stations"
            java.lang.String r2 = r0.toString()
            r8.put(r1, r2)
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r8 = r7.getPoiChildrenInfo()
            if (r8 == 0) goto L_0x0102
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r8 = r7.getPoiChildrenInfo()
            java.util.Collection<? extends com.autonavi.common.model.POI> r4 = r8.stationList
        L_0x0102:
            if (r4 == 0) goto L_0x0129
            java.util.Iterator r8 = r4.iterator()
        L_0x0108:
            boolean r1 = r8.hasNext()
            if (r1 == 0) goto L_0x0129
            java.lang.Object r1 = r8.next()
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r1 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r1
            java.util.HashMap r2 = r1.getPoiExtra()
            java.lang.String r3 = "child_stations"
            java.lang.String r4 = r0.toString()
            r2.put(r3, r4)
            java.lang.String r2 = r7.getName()
            r1.setName(r2)
            goto L_0x0108
        L_0x0129:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aep.e(com.autonavi.bundle.entity.common.searchpoi.SearchPoi, org.json.JSONObject):void");
    }

    private static void f(SearchPoi searchPoi, JSONObject jSONObject) {
        String optString = jSONObject.optString("cpdata");
        if (!TextUtils.isEmpty(optString)) {
            String[] split = optString.split("\\|");
            ArrayList arrayList = new ArrayList();
            for (String split2 : split) {
                String[] split3 = split2.split(";");
                if (split3.length > 1) {
                    CpData cpData = new CpData();
                    cpData.setCpid(split3[0]);
                    cpData.setSource(split3[1]);
                    arrayList.add(cpData);
                }
            }
            searchPoi.getPoiExtra().put("Cpdata", JSONEncoder.encode(arrayList));
            return;
        }
        searchPoi.getPoiExtra().remove("Cpdata");
    }

    public static void a(SearchPoi searchPoi, JSONObject jSONObject) {
        if (searchPoi != null && jSONObject != null) {
            searchPoi.setSketchDuration(jSONObject.optString("s_duration"));
            searchPoi.setShowSketchingMap(jSONObject.optString("show_sketching_map"));
            searchPoi.setSketchUrl(jSONObject.optString("sketch_url"));
            searchPoi.setShowEnvironmentalMap(jSONObject.optString("show_environmental_map"));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0044 A[Catch:{ Exception -> 0x00ed }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int a(defpackage.aud r11, org.json.JSONObject r12) {
        /*
            r0 = 0
            java.lang.String r1 = "locres"
            org.json.JSONObject r12 = r12.optJSONObject(r1)     // Catch:{ Exception -> 0x00ef }
            if (r12 != 0) goto L_0x000a
            return r0
        L_0x000a:
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r1 = r11.a     // Catch:{ Exception -> 0x00ef }
            if (r1 != 0) goto L_0x0015
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r1 = new com.autonavi.bundle.entity.infolite.external.PoiLocationInfo     // Catch:{ Exception -> 0x00ef }
            r1.<init>()     // Catch:{ Exception -> 0x00ef }
            r11.a = r1     // Catch:{ Exception -> 0x00ef }
        L_0x0015:
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r1 = r11.a     // Catch:{ Exception -> 0x00ef }
            java.lang.String r2 = "loctype"
            int r2 = r12.optInt(r2)     // Catch:{ Exception -> 0x00ef }
            r1.locationType = r2     // Catch:{ Exception -> 0x00ef }
            java.lang.String r1 = "total"
            boolean r1 = r12.has(r1)     // Catch:{ Exception -> 0x00ef }
            if (r1 == 0) goto L_0x003c
            java.lang.String r1 = "total"
            java.lang.String r1 = r12.getString(r1)     // Catch:{ Exception -> 0x00ef }
            if (r1 == 0) goto L_0x003c
            java.lang.String r2 = ""
            boolean r2 = r1.equals(r2)     // Catch:{ Exception -> 0x00ef }
            if (r2 != 0) goto L_0x003c
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ Exception -> 0x00ef }
            goto L_0x003d
        L_0x003c:
            r1 = 0
        L_0x003d:
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r2 = r11.a     // Catch:{ Exception -> 0x00ed }
            int r2 = r2.locationType     // Catch:{ Exception -> 0x00ed }
            r3 = 1
            if (r2 == r3) goto L_0x00ec
            if (r1 > 0) goto L_0x0048
            goto L_0x00ec
        L_0x0048:
            java.lang.String r2 = "poi_list"
            org.json.JSONArray r12 = r12.getJSONArray(r2)     // Catch:{ Exception -> 0x00ed }
            if (r12 == 0) goto L_0x00eb
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r2 = r11.a     // Catch:{ Exception -> 0x00ed }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.POIList     // Catch:{ Exception -> 0x00ed }
            if (r2 != 0) goto L_0x0060
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r2 = r11.a     // Catch:{ Exception -> 0x00ed }
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x00ed }
            r3.<init>()     // Catch:{ Exception -> 0x00ed }
            r2.POIList = r3     // Catch:{ Exception -> 0x00ed }
            goto L_0x0067
        L_0x0060:
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r2 = r11.a     // Catch:{ Exception -> 0x00ed }
            java.util.ArrayList<com.autonavi.common.model.POI> r2 = r2.POIList     // Catch:{ Exception -> 0x00ed }
            r2.clear()     // Catch:{ Exception -> 0x00ed }
        L_0x0067:
            int r2 = r12.length()     // Catch:{ Exception -> 0x00ed }
            if (r0 >= r2) goto L_0x00eb
            java.lang.Class<com.autonavi.bundle.entity.common.searchpoi.SearchPoi> r2 = com.autonavi.bundle.entity.common.searchpoi.SearchPoi.class
            com.autonavi.common.model.POI r2 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r2)     // Catch:{ Exception -> 0x00ed }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = (com.autonavi.bundle.entity.common.searchpoi.SearchPoi) r2     // Catch:{ Exception -> 0x00ed }
            java.lang.Object r3 = r12.get(r0)     // Catch:{ Exception -> 0x00ed }
            org.json.JSONObject r3 = (org.json.JSONObject) r3     // Catch:{ Exception -> 0x00ed }
            java.lang.String r4 = "longitude"
            r5 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            double r7 = r3.optDouble(r4, r5)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r4 = "latitude"
            double r9 = r3.optDouble(r4, r5)     // Catch:{ Exception -> 0x00ed }
            int r4 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r4 == 0) goto L_0x00e7
            int r4 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r4 == 0) goto L_0x00e7
            com.autonavi.common.model.GeoPoint r4 = r2.getPoint()     // Catch:{ Exception -> 0x00ed }
            r4.setLonLat(r7, r9)     // Catch:{ Exception -> 0x00ed }
            java.lang.String r4 = "address"
            boolean r4 = r3.has(r4)     // Catch:{ Exception -> 0x00ed }
            if (r4 == 0) goto L_0x00a9
            java.lang.String r4 = "address"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ Exception -> 0x00ed }
            r2.setAddr(r4)     // Catch:{ Exception -> 0x00ed }
        L_0x00a9:
            java.lang.String r4 = "name"
            boolean r4 = r3.has(r4)     // Catch:{ Exception -> 0x00ed }
            if (r4 == 0) goto L_0x00ba
            java.lang.String r4 = "name"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ Exception -> 0x00ed }
            r2.setName(r4)     // Catch:{ Exception -> 0x00ed }
        L_0x00ba:
            java.lang.String r4 = "adcode"
            boolean r4 = r3.has(r4)     // Catch:{ Exception -> 0x00ed }
            if (r4 == 0) goto L_0x00cb
            java.lang.String r4 = "adcode"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ Exception -> 0x00ed }
            r2.setAdCode(r4)     // Catch:{ Exception -> 0x00ed }
        L_0x00cb:
            java.lang.String r4 = "coords"
            boolean r4 = r3.has(r4)     // Catch:{ Exception -> 0x00ed }
            if (r4 == 0) goto L_0x00e0
            java.lang.String r4 = "coords"
            java.lang.String r3 = r3.getString(r4)     // Catch:{ Exception -> 0x00ed }
            java.util.ArrayList r3 = a(r3)     // Catch:{ Exception -> 0x00ed }
            r2.setRegions(r3)     // Catch:{ Exception -> 0x00ed }
        L_0x00e0:
            com.autonavi.bundle.entity.infolite.external.PoiLocationInfo r3 = r11.a     // Catch:{ Exception -> 0x00ed }
            java.util.ArrayList<com.autonavi.common.model.POI> r3 = r3.POIList     // Catch:{ Exception -> 0x00ed }
            r3.add(r2)     // Catch:{ Exception -> 0x00ed }
        L_0x00e7:
            int r0 = r0 + 1
            goto L_0x0067
        L_0x00eb:
            return r1
        L_0x00ec:
            return r1
        L_0x00ed:
            r11 = move-exception
            goto L_0x00f1
        L_0x00ef:
            r11 = move-exception
            r1 = 0
        L_0x00f1:
            defpackage.kf.a(r11)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aep.a(aud, org.json.JSONObject):int");
    }

    private static ArrayList<ArrayList<GeoPoint>> a(String str) {
        ArrayList<ArrayList<GeoPoint>> arrayList = new ArrayList<>();
        String[] split = str.split(AUScreenAdaptTool.PREFIX_ID);
        for (String split2 : split) {
            String[] split3 = split2.split(";");
            ArrayList arrayList2 = new ArrayList();
            int i = 0;
            while (true) {
                if (i >= split3.length) {
                    break;
                }
                try {
                    double d = 0.0d;
                    double doubleValue = !TextUtils.isEmpty(split3[i]) ? Double.valueOf(split3[i]).doubleValue() : 0.0d;
                    i++;
                    if (i >= split3.length) {
                        break;
                    }
                    if (!TextUtils.isEmpty(split3[i])) {
                        d = Double.valueOf(split3[i]).doubleValue();
                    }
                    GeoPoint geoPoint = new GeoPoint();
                    geoPoint.setLonLat(doubleValue, d);
                    arrayList2.add(geoPoint);
                    i++;
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }
            arrayList.add(arrayList2);
        }
        return arrayList;
    }

    private static int a(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return -1;
        }
        try {
            if (!jSONObject.has(str)) {
                return -1;
            }
            String string = jSONObject.getString(str);
            if (!TextUtils.isEmpty(string)) {
                return Integer.parseInt(string);
            }
            return -1;
        } catch (Exception e) {
            kf.a((Throwable) e);
            return -1;
        }
    }
}
