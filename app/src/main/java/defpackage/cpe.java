package defpackage;

import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sync.beans.GirfFavoritePoint;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cpe reason: default package */
/* compiled from: SavePoiJsonUtils */
public class cpe implements coo {
    public final JSONObject a(POI poi) {
        if (poi == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
        agd.a(jSONObject, LocationInstrument.LOCATION_EXTRAS_KEY_POIID, poi.getId(), "");
        agd.a(jSONObject, "name", poi.getName(), "");
        agd.a(jSONObject, "address", poi.getAddr(), "");
        agd.a(jSONObject, "phone_numbers", poi.getPhone(), "");
        agd.a(jSONObject, GirfFavoritePoint.JSON_FIELD_POI_CUSTOM_NAME, favoritePOI.getCustomName(), "");
        agd.a(jSONObject, GirfFavoritePoint.JSON_FIELD_POI_COMMON_NAME, favoritePOI.getCommonName(), "");
        agd.a(jSONObject, "city_code", poi.getCityCode(), "");
        agd.a(jSONObject, "city_name", poi.getCityName(), "");
        agd.a(jSONObject, (String) GirfFavoritePoint.JSON_FIELD_POI_POINT_X, poi.getPoint().x);
        agd.a(jSONObject, (String) GirfFavoritePoint.JSON_FIELD_POI_POINT_Y, poi.getPoint().y);
        agd.a(jSONObject, "end_poi_extension", poi.getEndPoiExtension(), "");
        agd.a(jSONObject, H5Param.LONG_TRANSPARENT, poi.getTransparent(), "");
        agd.a(jSONObject, "tag", favoritePOI.getTag(), "");
        agd.a(jSONObject, "poi_type", favoritePOI.getType(), "");
        agd.a(jSONObject, GirfFavoritePoint.JSON_FIELD_POI_NEW_TYPE, favoritePOI.getNewType(), "");
        agd.a(jSONObject, GirfFavoritePoint.JSON_FIELD_POI_CLASSIFICATION, favoritePOI.getClassification(), "");
        agd.a(jSONObject, GirfFavoritePoint.JSON_FIELD_POI_TOP_TIME, favoritePOI.getTopTime(), "");
        agd.a(jSONObject, "parent", favoritePOI.getParent(), "");
        agd.a(jSONObject, "childType", favoritePOI.getChildType(), "");
        agd.a(jSONObject, "towards_angle", favoritePOI.getTowardsAngle(), "");
        agd.a(jSONObject, "sndt_fl_nona", favoritePOI.getSndtFlNona(), "");
        agd.a(jSONObject, "f_nona", favoritePOI.getFnona(), "");
        try {
            ArrayList<GeoPoint> entranceList = favoritePOI.getEntranceList();
            if (entranceList != null) {
                JSONArray jSONArray = new JSONArray();
                Iterator<GeoPoint> it = entranceList.iterator();
                while (it.hasNext()) {
                    GeoPoint next = it.next();
                    jSONArray.put(next.x);
                    jSONArray.put(next.y);
                }
                jSONObject.put("entrance_list", jSONArray);
            }
            if (favoritePOI.getExitList() != null) {
                JSONArray jSONArray2 = new JSONArray();
                Iterator<GeoPoint> it2 = entranceList.iterator();
                while (it2.hasNext()) {
                    GeoPoint next2 = it2.next();
                    jSONArray2.put(next2.x);
                    jSONArray2.put(next2.y);
                }
                jSONObject.put("exit_list", jSONArray2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x013d A[Catch:{ Exception -> 0x01e1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x014c A[Catch:{ Exception -> 0x01e1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x015b A[Catch:{ Exception -> 0x01e1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x016b A[Catch:{ Exception -> 0x01e1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0176 A[Catch:{ Exception -> 0x01e1 }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01ab A[Catch:{ Exception -> 0x01e1 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.autonavi.common.model.POI a(org.json.JSONObject r12) {
        /*
            r11 = this;
            com.autonavi.common.model.POI r0 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ Exception -> 0x01e1 }
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r0.as(r1)     // Catch:{ Exception -> 0x01e1 }
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "poiid"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r0.setId(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "name"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r0.setName(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "address"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r0.setAddr(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "phone_numbers"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r0.setPhone(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "city_code"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r0.setCityCode(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "city_name"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r0.setCityName(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "point_x"
            int r2 = defpackage.agd.a(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r3 = "point_y"
            int r3 = defpackage.agd.a(r12, r3)     // Catch:{ Exception -> 0x01e1 }
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x01e1 }
            r4.<init>(r2, r3)     // Catch:{ Exception -> 0x01e1 }
            r0.setPoint(r4)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "end_poi_extension"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r0.setEndPoiExtension(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "transparent"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r0.setTransparent(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "custom_name"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r1.setCustomName(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "common_name"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r1.setCommonName(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "towards_angle"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r1.setTowardsAngle(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "parent"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r1.setParent(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "childType"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r3 == 0) goto L_0x00a0
            java.lang.String r2 = "childtype"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
        L_0x00a0:
            r1.setChildType(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "sndt_fl_nona"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r1.setSndtFlNona(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "f_nona"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            r1.setFnona(r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r2 = "tag"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            java.lang.String r3 = "poi_type"
            java.lang.String r3 = defpackage.agd.e(r12, r3)     // Catch:{ Exception -> 0x01e1 }
            boolean r4 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x01e1 }
            if (r4 != 0) goto L_0x00cb
            r1.setType(r3)     // Catch:{ Exception -> 0x01e1 }
        L_0x00cb:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01e1 }
            r4 = 0
            r5 = 1
            if (r3 == 0) goto L_0x0137
            java.lang.String r3 = "tags"
            java.lang.String r3 = defpackage.agd.e(r12, r3)     // Catch:{ Exception -> 0x01e1 }
            boolean r6 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x01e1 }
            if (r6 != 0) goto L_0x0137
            org.json.JSONArray r6 = new org.json.JSONArray     // Catch:{ JSONException -> 0x012f }
            r6.<init>(r3)     // Catch:{ JSONException -> 0x012f }
            r3 = r2
            r2 = 0
        L_0x00e7:
            int r7 = r6.length()     // Catch:{ JSONException -> 0x012d }
            if (r2 >= r7) goto L_0x010a
            org.json.JSONObject r7 = r6.getJSONObject(r2)     // Catch:{ JSONException -> 0x012d }
            java.lang.String r8 = "originTag"
            java.lang.String r7 = defpackage.agd.e(r7, r8)     // Catch:{ JSONException -> 0x012d }
            boolean r3 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x0107 }
            if (r3 != 0) goto L_0x0103
            r1.setTag(r7)     // Catch:{ JSONException -> 0x0107 }
            r3 = r7
            r2 = 1
            goto L_0x010b
        L_0x0103:
            int r2 = r2 + 1
            r3 = r7
            goto L_0x00e7
        L_0x0107:
            r2 = move-exception
            r3 = r7
            goto L_0x0133
        L_0x010a:
            r2 = 0
        L_0x010b:
            if (r2 != 0) goto L_0x0136
            r2 = 0
        L_0x010e:
            int r7 = r6.length()     // Catch:{ JSONException -> 0x012d }
            if (r2 >= r7) goto L_0x0136
            org.json.JSONObject r7 = r6.getJSONObject(r2)     // Catch:{ JSONException -> 0x012d }
            java.lang.String r8 = "customTag"
            java.lang.String r7 = defpackage.agd.e(r7, r8)     // Catch:{ JSONException -> 0x012d }
            boolean r3 = android.text.TextUtils.isEmpty(r7)     // Catch:{ JSONException -> 0x0107 }
            if (r3 != 0) goto L_0x0129
            r1.setTag(r7)     // Catch:{ JSONException -> 0x0107 }
            r2 = r7
            goto L_0x0137
        L_0x0129:
            int r2 = r2 + 1
            r3 = r7
            goto L_0x010e
        L_0x012d:
            r2 = move-exception
            goto L_0x0133
        L_0x012f:
            r3 = move-exception
            r10 = r3
            r3 = r2
            r2 = r10
        L_0x0133:
            r2.printStackTrace()     // Catch:{ Exception -> 0x01e1 }
        L_0x0136:
            r2 = r3
        L_0x0137:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r3 != 0) goto L_0x0140
            r1.setTag(r2)     // Catch:{ Exception -> 0x01e1 }
        L_0x0140:
            java.lang.String r2 = "newType"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r3 != 0) goto L_0x014f
            r1.setNewType(r2)     // Catch:{ Exception -> 0x01e1 }
        L_0x014f:
            java.lang.String r2 = "classification"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r3 != 0) goto L_0x015e
            r1.setClassification(r2)     // Catch:{ Exception -> 0x01e1 }
        L_0x015e:
            java.lang.String r2 = "top_time"
            java.lang.String r2 = defpackage.agd.e(r12, r2)     // Catch:{ Exception -> 0x01e1 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r3 != 0) goto L_0x016e
            r1.setTopTime(r2)     // Catch:{ Exception -> 0x01e1 }
        L_0x016e:
            java.lang.String r2 = "entrance_list"
            boolean r2 = r12.has(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r2 == 0) goto L_0x01a3
            java.lang.String r2 = "entrance_list"
            org.json.JSONArray r2 = r12.optJSONArray(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r2 == 0) goto L_0x01a3
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x01e1 }
            r3.<init>()     // Catch:{ Exception -> 0x01e1 }
            r6 = 0
        L_0x0184:
            int r7 = r2.length()     // Catch:{ Exception -> 0x01e1 }
            int r7 = r7 - r5
            if (r6 >= r7) goto L_0x01a0
            int r7 = r2.getInt(r6)     // Catch:{ Exception -> 0x01e1 }
            int r8 = r6 + 1
            int r8 = r2.getInt(r8)     // Catch:{ Exception -> 0x01e1 }
            com.autonavi.common.model.GeoPoint r9 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x01e1 }
            r9.<init>(r7, r8)     // Catch:{ Exception -> 0x01e1 }
            r3.add(r9)     // Catch:{ Exception -> 0x01e1 }
            int r6 = r6 + 2
            goto L_0x0184
        L_0x01a0:
            r1.setEntranceList(r3)     // Catch:{ Exception -> 0x01e1 }
        L_0x01a3:
            java.lang.String r2 = "exit_list"
            boolean r2 = r12.has(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r2 == 0) goto L_0x01d7
            java.lang.String r2 = "exit_list"
            org.json.JSONArray r2 = r12.optJSONArray(r2)     // Catch:{ Exception -> 0x01e1 }
            if (r2 == 0) goto L_0x01d7
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch:{ Exception -> 0x01e1 }
            r3.<init>()     // Catch:{ Exception -> 0x01e1 }
        L_0x01b8:
            int r6 = r2.length()     // Catch:{ Exception -> 0x01e1 }
            int r6 = r6 - r5
            if (r4 >= r6) goto L_0x01d4
            int r6 = r2.getInt(r4)     // Catch:{ Exception -> 0x01e1 }
            int r7 = r4 + 1
            int r7 = r2.getInt(r7)     // Catch:{ Exception -> 0x01e1 }
            com.autonavi.common.model.GeoPoint r8 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x01e1 }
            r8.<init>(r6, r7)     // Catch:{ Exception -> 0x01e1 }
            r3.add(r8)     // Catch:{ Exception -> 0x01e1 }
            int r4 = r4 + 2
            goto L_0x01b8
        L_0x01d4:
            r1.setExitList(r3)     // Catch:{ Exception -> 0x01e1 }
        L_0x01d7:
            java.lang.String r1 = "cpdata"
            java.lang.String r12 = defpackage.agd.e(r12, r1)     // Catch:{ Exception -> 0x01e1 }
            defpackage.cpj.a(r12, r0)     // Catch:{ Exception -> 0x01e1 }
            return r0
        L_0x01e1:
            r12 = move-exception
            r12.printStackTrace()
            r12 = 0
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cpe.a(org.json.JSONObject):com.autonavi.common.model.POI");
    }
}
