package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.bedstone.model.FrequentLocationInfo;
import com.autonavi.minimap.bundle.frequentlocation.util.FrequentLocationInfoEx;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: cyj reason: default package */
/* compiled from: FrequentLocationsUtil */
public final class cyj {
    public static String a(FrequentLocationInfo frequentLocationInfo) {
        try {
            JSONObject jSONObject = new JSONObject();
            agd.a(jSONObject, LocationInstrument.LOCATION_EXTRAS_KEY_POIID, frequentLocationInfo.poiid, "");
            agd.a(jSONObject, "name", frequentLocationInfo.name, "");
            agd.a(jSONObject, (String) DictionaryKeys.CTRLXY_X, frequentLocationInfo.x);
            agd.a(jSONObject, (String) DictionaryKeys.CTRLXY_Y, frequentLocationInfo.y);
            agd.a(jSONObject, "cityCode", frequentLocationInfo.cityCode, "");
            agd.a(jSONObject, "poiType", frequentLocationInfo.poiType, "");
            agd.a(jSONObject, "towardsAngle", frequentLocationInfo.towardsAngle, "");
            agd.a(jSONObject, "parent", frequentLocationInfo.parent, "");
            agd.a(jSONObject, "floor", frequentLocationInfo.floor, "");
            agd.a(jSONObject, "childType", frequentLocationInfo.childType, "");
            agd.a(jSONObject, "fnona", frequentLocationInfo.fnona, "");
            agd.a(jSONObject, "endPoiExtension", frequentLocationInfo.endPoiExtension, "");
            agd.a(jSONObject, H5Param.LONG_TRANSPARENT, frequentLocationInfo.transparent, "");
            ArrayList<GeoPoint> arrayList = frequentLocationInfo.entranceList;
            if (arrayList != null && arrayList.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    GeoPoint geoPoint = arrayList.get(i);
                    jSONObject2.put("mEntranceX", geoPoint.x);
                    jSONObject2.put("mEntranceY", geoPoint.y);
                    jSONArray.put(i, jSONObject2);
                }
                agd.a(jSONObject, (String) "entranceList", jSONArray.toString());
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FrequentLocationInfo a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            FrequentLocationInfo frequentLocationInfo = new FrequentLocationInfo();
            frequentLocationInfo.poiid = agd.e(jSONObject, LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
            frequentLocationInfo.name = agd.e(jSONObject, "name");
            frequentLocationInfo.x = agd.a(jSONObject, DictionaryKeys.CTRLXY_X);
            frequentLocationInfo.y = agd.a(jSONObject, DictionaryKeys.CTRLXY_Y);
            frequentLocationInfo.cityCode = agd.e(jSONObject, "cityCode");
            frequentLocationInfo.poiType = agd.e(jSONObject, "poiType");
            frequentLocationInfo.towardsAngle = agd.e(jSONObject, "towardsAngle");
            frequentLocationInfo.parent = agd.e(jSONObject, "parent");
            frequentLocationInfo.floor = agd.e(jSONObject, "floor");
            frequentLocationInfo.childType = agd.e(jSONObject, "childType");
            frequentLocationInfo.fnona = agd.e(jSONObject, "fnona");
            frequentLocationInfo.endPoiExtension = agd.e(jSONObject, "endPoiExtension");
            frequentLocationInfo.transparent = agd.e(jSONObject, H5Param.LONG_TRANSPARENT);
            try {
                String e = agd.e(jSONObject, "entranceList");
                if (TextUtils.isEmpty(e)) {
                    return frequentLocationInfo;
                }
                JSONArray jSONArray = new JSONArray(e);
                frequentLocationInfo.entranceList = new ArrayList<>();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    if (jSONObject2 != null && jSONObject2.has("mEntranceX") && jSONObject2.has("mEntranceY")) {
                        frequentLocationInfo.entranceList.add(new GeoPoint(jSONObject2.optInt("mEntranceX"), jSONObject2.optInt("mEntranceY")));
                    }
                }
                return frequentLocationInfo;
            } catch (JSONException e2) {
                e2.printStackTrace();
                if (frequentLocationInfo.entranceList == null) {
                    return frequentLocationInfo;
                }
                frequentLocationInfo.entranceList.clear();
                return frequentLocationInfo;
            }
        } catch (JSONException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static String a(FrequentLocationInfoEx frequentLocationInfoEx) {
        try {
            JSONObject jSONObject = new JSONObject();
            agd.a(jSONObject, LocationInstrument.LOCATION_EXTRAS_KEY_POIID, frequentLocationInfoEx.poiid, "");
            agd.a(jSONObject, "name", frequentLocationInfoEx.name, "");
            jSONObject.put(DictionaryKeys.CTRLXY_X, frequentLocationInfoEx.x);
            jSONObject.put(DictionaryKeys.CTRLXY_Y, frequentLocationInfoEx.y);
            agd.a(jSONObject, "city_code", frequentLocationInfoEx.cityCode, "");
            agd.a(jSONObject, "poiType", frequentLocationInfoEx.poiType, "");
            agd.a(jSONObject, "towards_angle", frequentLocationInfoEx.towardsAngle, "");
            agd.a(jSONObject, "parent", frequentLocationInfoEx.parent, "");
            agd.a(jSONObject, "floor", frequentLocationInfoEx.floor, "");
            agd.a(jSONObject, "childType", frequentLocationInfoEx.childType, "");
            agd.a(jSONObject, "f_nona", frequentLocationInfoEx.fnona, "");
            agd.a(jSONObject, "end_poi_extension", frequentLocationInfoEx.endPoiExtension, "");
            agd.a(jSONObject, H5Param.LONG_TRANSPARENT, frequentLocationInfoEx.transparent, "");
            agd.a(jSONObject, "frequent_remark", frequentLocationInfoEx.frequentRemark, "");
            ArrayList arrayList = frequentLocationInfoEx.entranceList;
            if (arrayList != null && arrayList.size() > 0) {
                JSONArray jSONArray = new JSONArray();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    GeoPoint geoPoint = (GeoPoint) arrayList.get(i);
                    jSONObject2.put(DictionaryKeys.CTRLXY_X, geoPoint.x);
                    jSONObject2.put(DictionaryKeys.CTRLXY_Y, geoPoint.y);
                    jSONArray.put(i, jSONObject2);
                }
                jSONObject.put("entranceList", jSONArray);
            }
            return jSONObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FrequentLocationInfoEx b(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            FrequentLocationInfoEx frequentLocationInfoEx = new FrequentLocationInfoEx();
            frequentLocationInfoEx.poiid = agd.e(jSONObject, LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
            frequentLocationInfoEx.name = agd.e(jSONObject, "name");
            frequentLocationInfoEx.x = jSONObject.optInt(DictionaryKeys.CTRLXY_X, 0);
            frequentLocationInfoEx.y = jSONObject.optInt(DictionaryKeys.CTRLXY_Y, 0);
            frequentLocationInfoEx.cityCode = agd.e(jSONObject, "city_code");
            frequentLocationInfoEx.poiType = agd.e(jSONObject, "poiType");
            frequentLocationInfoEx.towardsAngle = agd.e(jSONObject, "towards_angle");
            frequentLocationInfoEx.parent = agd.e(jSONObject, "parent");
            frequentLocationInfoEx.floor = agd.e(jSONObject, "floor");
            frequentLocationInfoEx.childType = agd.e(jSONObject, "childType");
            frequentLocationInfoEx.fnona = agd.e(jSONObject, "f_nona");
            frequentLocationInfoEx.endPoiExtension = agd.e(jSONObject, "end_poi_extension");
            frequentLocationInfoEx.transparent = agd.e(jSONObject, H5Param.LONG_TRANSPARENT);
            frequentLocationInfoEx.frequentRemark = agd.e(jSONObject, "frequent_remark");
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("entranceList");
                if (jSONArray == null) {
                    return frequentLocationInfoEx;
                }
                frequentLocationInfoEx.entranceList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    if (jSONObject2 != null && jSONObject2.has(DictionaryKeys.CTRLXY_X) && jSONObject2.has(DictionaryKeys.CTRLXY_Y)) {
                        frequentLocationInfoEx.entranceList.add(new GeoPoint(jSONObject2.optInt(DictionaryKeys.CTRLXY_X), jSONObject2.optInt(DictionaryKeys.CTRLXY_Y)));
                    }
                }
                return frequentLocationInfoEx;
            } catch (JSONException e) {
                e.printStackTrace();
                if (frequentLocationInfoEx.entranceList == null) {
                    return frequentLocationInfoEx;
                }
                frequentLocationInfoEx.entranceList.clear();
                return frequentLocationInfoEx;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
