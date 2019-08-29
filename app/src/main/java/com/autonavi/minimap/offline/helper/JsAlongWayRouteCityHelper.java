package com.autonavi.minimap.offline.helper;

import android.text.TextUtils;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.autonavi.minimap.offline.util.JsNativeFacade;
import com.autonavi.minimap.offline.utils.OfflineUtil;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsAlongWayRouteCityHelper {
    private static final String EMPTY_JSON_STR = "";

    private JsAlongWayRouteCityHelper() {
    }

    public static JSONObject convertSuccessJO(CityInfo[] cityInfoArr, String str) throws JSONException {
        if (cityInfoArr == null || cityInfoArr.length == 0 || TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (CityInfo cityInfo : cityInfoArr) {
            if (cityInfo != null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("cityName", cityInfo.name);
                jSONObject2.put("adCode", String.valueOf(cityInfo.adcode));
                jSONArray.put(jSONObject2);
            }
        }
        if (jSONArray.length() == 0) {
            return null;
        }
        jSONObject.put("code", str);
        jSONObject.put("routeCitySizeTips", getRouteCitiesSizeTips(cityInfoArr));
        jSONObject.put("alongWayRouteCity", jSONArray);
        return jSONObject;
    }

    public static String convertErrorJoStr(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("code", str);
        return jSONObject.toString();
    }

    public static JSONObject convertRouteBundleJO(CityInfo[] cityInfoArr, String str) throws JSONException {
        if (cityInfoArr == null || cityInfoArr.length == 0 || TextUtils.isEmpty(str)) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("startEndCity", convertStartEndJA(cityInfoArr));
        jSONObject.put("routeCity", convertSuccessJO(cityInfoArr, "1"));
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("code", str);
        jSONObject2.put("data", jSONObject);
        return jSONObject2;
    }

    private static JSONArray convertStartEndJA(CityInfo[] cityInfoArr) throws JSONException {
        if (cityInfoArr == null) {
            return null;
        }
        int length = cityInfoArr.length;
        if (length < 2) {
            return null;
        }
        CityInfo cityInfo = cityInfoArr[0];
        CityInfo cityInfo2 = cityInfoArr[length - 1];
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("adCode", String.valueOf(cityInfo.adcode));
        jSONObject.put("from", "1");
        jSONObject.put("name", cityInfo.name);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("adCode", String.valueOf(cityInfo2.adcode));
        jSONObject2.put("from", "2");
        jSONObject2.put("name", cityInfo2.name);
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(jSONObject);
        jSONArray.put(jSONObject2);
        return jSONArray;
    }

    private static String getRouteCitiesSizeTips(CityInfo[] cityInfoArr) {
        long dataSize = JsNativeFacade.getInstance().getDataSize(cityInfoArr);
        if (dataSize <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder("途经以下城市，地图+导航共");
        sb.append(OfflineUtil.getPercentValue((double) OfflineUtil.get2Num((float) dataSize)));
        sb.append(DiskFormatter.MB);
        return sb.toString();
    }
}
