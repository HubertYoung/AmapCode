package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.FavoritePOI;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: kv reason: default package */
/* compiled from: POIHelper */
public final class kv {
    public static POI a(String str) {
        POI createPOI = POIFactory.createPOI();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(RpcConstant.BASE)) {
                jSONObject = jSONObject.optJSONObject(RpcConstant.BASE);
            }
            if (jSONObject.has(LocationInstrument.LOCATION_EXTRAS_KEY_POIID)) {
                createPOI.setId(jSONObject.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID));
            }
            if (jSONObject.has("name")) {
                createPOI.setName(jSONObject.getString("name"));
            }
            if (jSONObject.has("address")) {
                createPOI.setAddr(jSONObject.getString("address"));
            }
            if (jSONObject.has("phoneNumbers")) {
                createPOI.setPhone(jSONObject.getString("phoneNumbers"));
            }
            createPOI.setType(jSONObject.optString("new_type"));
            if (jSONObject.has("is_gpspoint")) {
                createPOI.getPoiExtra().put("is_gpspoint", Boolean.valueOf(jSONObject.optBoolean("is_gpspoint")));
            }
            if (jSONObject.has("end_poi_extension")) {
                createPOI.setEndPoiExtension(jSONObject.getString("end_poi_extension"));
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
            if (jSONObject.has("cityCode")) {
                createPOI.setCityCode(jSONObject.getString("cityCode"));
            }
            FavoritePOI favoritePOI = (FavoritePOI) createPOI.as(FavoritePOI.class);
            if (jSONObject.has("parent")) {
                favoritePOI.setParent(jSONObject.optString("parent"));
            }
            if (jSONObject.has("childtype")) {
                favoritePOI.setChildType(jSONObject.optString("childtype"));
            }
            if (jSONObject.has("towards_angle")) {
                favoritePOI.setTowardsAngle(jSONObject.optString("towards_angle"));
            }
            if (jSONObject.has("f_nona")) {
                favoritePOI.setFnona(jSONObject.optString("f_nona"));
            }
            if (jSONObject.has("sndt_fl_nona")) {
                favoritePOI.setSndtFlNona(jSONObject.optString("sndt_fl_nona"));
            }
            String optString = jSONObject.optString("navi_lng");
            String optString2 = jSONObject.optString("navi_lat");
            if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2)) {
                ArrayList arrayList = new ArrayList(1);
                try {
                    arrayList.add(new GeoPoint(Double.parseDouble(optString), Double.parseDouble(optString2)));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                createPOI.setEntranceList(arrayList);
            }
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        return createPOI;
    }

    public static String a(POI poi) {
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
            return jSONObject.toString();
        } catch (JSONException unused) {
            return null;
        }
    }
}
