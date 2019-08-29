package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.antui.basic.AUCardOptionView;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bnx reason: default package */
/* compiled from: POIUtil */
public final class bnx {
    public static String a(FavoritePOI favoritePOI) {
        JSONObject jSONObject = new JSONObject();
        try {
            String customName = favoritePOI.getCustomName();
            if (customName == null || customName.equals(favoritePOI.getName())) {
                customName = "";
            }
            jSONObject.put("name", customName);
            jSONObject.put("address", "");
            jSONObject.put("telephone", "");
            jSONObject.put(AUCardOptionView.TYPE_COMMENT, "");
            jSONObject.put("custom_comment", AMapAppGlobal.getApplication().getString(R.string.custom));
            return jSONObject.toString();
        } catch (JSONException unused) {
            return null;
        }
    }

    public static boolean a(POI poi, POI poi2) {
        if (poi == null || poi2 == null) {
            return false;
        }
        String id = poi.getId();
        String id2 = poi2.getId();
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(id2) && id.equalsIgnoreCase(id2)) {
            return true;
        }
        String string = AMapAppGlobal.getApplication().getString(R.string.LocationMe);
        if (string.equals(poi.getName()) && string.equals(poi2.getName())) {
            return true;
        }
        if (poi.getPoint().x == poi2.getPoint().x && poi.getPoint().y == poi2.getPoint().y && TextUtils.equals(poi.getName(), poi2.getName())) {
            return true;
        }
        return false;
    }

    public static boolean a(POI poi) {
        if (poi == null) {
            return false;
        }
        GeoPoint point = poi.getPoint();
        return (point != null && point.x != 0 && point.y != 0) && (TextUtils.isEmpty(poi.getName()) ^ true);
    }

    public static JSONObject b(POI poi) {
        if (poi == null) {
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
            jSONObject.put("parentID", poi.getPid());
            HashMap<String, Serializable> poiExtra2 = poi.getPoiExtra();
            if (!(poiExtra2 == null || poiExtra2.get("IATA_CODE") == null)) {
                jSONObject.put("IATA_CODE", poi.getPoiExtra().get("IATA_CODE").toString());
            }
            if (!(poiExtra2 == null || poiExtra2.get("businfo_lineids") == null)) {
                jSONObject.put("businfo_lineids", poi.getPoiExtra().get("businfo_lineids"));
            }
            if (TextUtils.isEmpty(poi.getCityCode())) {
                StringBuilder sb = new StringBuilder();
                sb.append(poi.getPoint().getAdCode());
                poi.setCityCode(sb.toString());
            }
            jSONObject.put("cityCode", poi.getCityCode());
            jSONObject.put(AutoJsonUtils.JSON_ADCODE, poi.getAdCode());
            if (!TextUtils.isEmpty(poi.getIndustry())) {
                jSONObject.put("industry", poi.getIndustry());
            }
            ArrayList<GeoPoint> entranceList = poi.getEntranceList();
            if (entranceList != null && !entranceList.isEmpty()) {
                JSONArray jSONArray = new JSONArray();
                for (GeoPoint next : entranceList) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put(DictionaryKeys.CTRLXY_X, next.x);
                    jSONObject2.put(DictionaryKeys.CTRLXY_Y, next.y);
                    jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, next.getLongitude());
                    jSONObject2.put("lat", next.getLatitude());
                    jSONArray.put(jSONObject2);
                }
                jSONObject.put("entranceList", jSONArray);
            }
            ArrayList<GeoPoint> exitList = poi.getExitList();
            if (exitList != null && !exitList.isEmpty()) {
                JSONArray jSONArray2 = new JSONArray();
                for (GeoPoint next2 : exitList) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put(DictionaryKeys.CTRLXY_X, next2.x);
                    jSONObject3.put(DictionaryKeys.CTRLXY_Y, next2.y);
                    jSONObject3.put(LocationParams.PARA_FLP_AUTONAVI_LON, next2.getLongitude());
                    jSONObject3.put("lat", next2.getLatitude());
                    jSONArray2.put(jSONObject3);
                }
                jSONObject.put("exitList", jSONArray2);
            }
            jSONObject.put("end_poi_extension", poi.getEndPoiExtension());
            jSONObject.put(H5Param.LONG_TRANSPARENT, poi.getTransparent());
            FavoritePOI favoritePOI = (FavoritePOI) poi.as(FavoritePOI.class);
            ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
            if (!TextUtils.isEmpty(favoritePOI.getTowardsAngle())) {
                jSONObject.put("towards_angle", favoritePOI.getTowardsAngle());
            } else if (!TextUtils.isEmpty(iSearchPoiData.getTowardsAngle())) {
                jSONObject.put("towards_angle", iSearchPoiData.getTowardsAngle());
            }
            if (!TextUtils.isEmpty(favoritePOI.getParent())) {
                jSONObject.put("parent", favoritePOI.getParent());
            } else if (!TextUtils.isEmpty(iSearchPoiData.getParent())) {
                jSONObject.put("parent", iSearchPoiData.getParent());
            }
            if (!TextUtils.isEmpty(favoritePOI.getChildType())) {
                jSONObject.put("childType", favoritePOI.getChildType());
            } else if (!TextUtils.isEmpty(iSearchPoiData.getChildType())) {
                jSONObject.put("childType", iSearchPoiData.getChildType());
            }
            if (!TextUtils.isEmpty(favoritePOI.getFnona())) {
                jSONObject.put("f_nona", favoritePOI.getFnona());
            } else if (!TextUtils.isEmpty(iSearchPoiData.getFnona())) {
                jSONObject.put("f_nona", iSearchPoiData.getFnona());
            }
            if (!TextUtils.isEmpty(favoritePOI.getIndoorFloorNoName())) {
                jSONObject.put("floor", favoritePOI.getIndoorFloorNoName());
            } else if (!TextUtils.isEmpty(iSearchPoiData.getIndoorFloorNoName())) {
                jSONObject.put("floor", iSearchPoiData.getIndoorFloorNoName());
            }
            return jSONObject;
        } catch (JSONException e) {
            AMapLog.e("poi", String.valueOf(e));
            return null;
        }
    }

    public static POI a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        POI createPOI = POIFactory.createPOI();
        try {
            JSONObject jSONObject = new JSONObject(str);
            createPOI.setId(jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            createPOI.setName(jSONObject.optString("name"));
            createPOI.setAddr(jSONObject.optString("address"));
            createPOI.setPhone(jSONObject.optString("phoneNumbers"));
            createPOI.setType(jSONObject.optString("new_type"));
            createPOI.setInoorFloorNoName(jSONObject.optString("floor"));
            createPOI.setPid(jSONObject.optString("parentID"));
            if (jSONObject.has("is_gpspoint")) {
                createPOI.getPoiExtra().put("is_gpspoint", Boolean.valueOf(jSONObject.optBoolean("is_gpspoint")));
            }
            if (jSONObject.has("IATA_CODE")) {
                createPOI.getPoiExtra().put("IATA_CODE", jSONObject.optString("IATA_CODE"));
            }
            int optInt = jSONObject.optInt(DictionaryKeys.CTRLXY_X);
            int optInt2 = jSONObject.optInt(DictionaryKeys.CTRLXY_Y);
            double optDouble = jSONObject.has(LocationParams.PARA_FLP_AUTONAVI_LON) ? jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON) : 0.0d;
            if (jSONObject.has("longitude")) {
                optDouble = jSONObject.optDouble("longitude");
            }
            double optDouble2 = jSONObject.has("lat") ? jSONObject.optDouble("lat") : 0.0d;
            if (jSONObject.has("latitude")) {
                optDouble2 = jSONObject.optDouble("latitude");
            }
            GeoPoint point = createPOI.getPoint();
            if (optInt != 0 && optInt2 != 0) {
                if (point == null) {
                    point = new GeoPoint();
                    createPOI.setPoint(point);
                }
                point.x = optInt;
                point.y = optInt2;
            } else if (!(optDouble2 == 0.0d || optDouble == 0.0d)) {
                if (point == null) {
                    point = new GeoPoint();
                    createPOI.setPoint(point);
                }
                point.setLonLat(optDouble, optDouble2);
            }
            if (jSONObject.has("entranceList")) {
                ArrayList arrayList = new ArrayList();
                JSONArray optJSONArray = jSONObject.optJSONArray("entranceList");
                if (optJSONArray != null && optJSONArray.length() > 0) {
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                        if (optJSONObject != null) {
                            arrayList.add(new GeoPoint(optJSONObject.optInt(DictionaryKeys.CTRLXY_X, 0), optJSONObject.optInt(DictionaryKeys.CTRLXY_Y, 0)));
                        }
                    }
                }
                createPOI.setEntranceList(arrayList);
            }
            if (jSONObject.has("exitList")) {
                ArrayList arrayList2 = new ArrayList();
                JSONArray optJSONArray2 = jSONObject.optJSONArray("exitList");
                if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                    for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                        JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                        if (optJSONObject2 != null) {
                            arrayList2.add(new GeoPoint(optJSONObject2.optInt(DictionaryKeys.CTRLXY_X, 0), optJSONObject2.optInt(DictionaryKeys.CTRLXY_Y, 0)));
                        }
                    }
                }
                createPOI.setExitList(arrayList2);
            }
            if (jSONObject.has("businfo_lineids")) {
                createPOI.getPoiExtra().put("businfo_lineids", jSONObject.optString("businfo_lineids"));
            }
            createPOI.setCityCode(jSONObject.optString("cityCode"));
            createPOI.setAdCode(jSONObject.optString(AutoJsonUtils.JSON_ADCODE));
            createPOI.setIndustry(jSONObject.optString("industry"));
            createPOI.setEndPoiExtension(jSONObject.optString("end_poi_extension"));
            createPOI.setTransparent(jSONObject.optString(H5Param.LONG_TRANSPARENT));
            FavoritePOI favoritePOI = (FavoritePOI) createPOI.as(FavoritePOI.class);
            favoritePOI.setTowardsAngle(jSONObject.optString("towards_angle"));
            favoritePOI.setParent(jSONObject.optString("parent"));
            if (jSONObject.has("childType")) {
                favoritePOI.setChildType(jSONObject.optString("childType"));
            } else if (jSONObject.has("childtype")) {
                favoritePOI.setChildType(jSONObject.optString("childtype"));
            }
            favoritePOI.setFnona(jSONObject.optString("f_nona"));
            ISearchPoiData iSearchPoiData = (ISearchPoiData) createPOI.as(ISearchPoiData.class);
            iSearchPoiData.setTowardsAngle(jSONObject.optString("towards_angle"));
            iSearchPoiData.setParent(jSONObject.optString("parent"));
            if (jSONObject.has("childType")) {
                iSearchPoiData.setChildType(jSONObject.optString("childType"));
            } else if (jSONObject.has("childtype")) {
                iSearchPoiData.setChildType(jSONObject.optString("childtype"));
            }
            iSearchPoiData.setFnona(jSONObject.optString("f_nona"));
        } catch (JSONException e) {
            kf.a((Throwable) e);
        }
        return createPOI;
    }
}
