package com.autonavi.minimap.offline.helper;

import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity;
import com.alipay.mobile.tinyappcommon.subpackage.TinyAppSubPackagePlugin;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.model.City;
import com.autonavi.minimap.offline.model.Province;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsAllCityInfoHelper {
    private static final String EMPTY_CITY_INFO = "";

    public final String convertAllCityInfoJOStr(List<Province> list) throws JSONException {
        if (list == null || list.isEmpty()) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (Province next : list) {
            if (next != null) {
                List<City> cityList = next.getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("adCode", next.getAdCode());
                    jSONObject.put("name", next.getName());
                    jSONObject.put(AutoJsonUtils.JSON_PINYIN, next.getPinyin());
                    jSONObject.put(AutoJsonUtils.JSON_JIANPIN, next.getJianpin());
                    jSONObject.put("mapSize", next.getMapSize());
                    jSONObject.put("routeSize", next.getRouteSize());
                    jSONObject.put("mapState", next.getMapState());
                    jSONObject.put("naviState", next.getNaviState());
                    jSONObject.put(TinyAppSubPackagePlugin.DOWNLOAD_STATUS, next.getDownloadStatus());
                    jSONObject.put("updateFlag", next.getUpdateFlag());
                    jSONObject.put("updateSize", next.getUpdateSize());
                    JSONArray jSONArray2 = new JSONArray();
                    for (City next2 : cityList) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("adCode", JsConvertUtils.checkAdCode(String.valueOf(next2.getAdCode())));
                        jSONObject2.put("provinceAdcode", next2.getProvinceAdcode());
                        jSONObject2.put("name", next2.getName());
                        jSONObject2.put(AutoJsonUtils.JSON_PINYIN, next2.getPinyin());
                        jSONObject2.put(AutoJsonUtils.JSON_JIANPIN, next2.getJianpin());
                        jSONObject2.put("mapSize", next2.getMapSize());
                        jSONObject2.put("routeSize", next2.getRouteSize());
                        jSONObject2.put("mapState", next2.getMapState());
                        jSONObject2.put("naviState", next2.getNaviState());
                        jSONObject2.put(TinyAppSubPackagePlugin.DOWNLOAD_STATUS, next2.getDownloadStatus());
                        jSONObject2.put("isCurrentCity", next2.getIsCurrentCity());
                        jSONObject2.put("updateFlag", next2.getUpdateFlag());
                        jSONObject2.put("updateSize", next2.getUpdateSize());
                        jSONObject2.put("mapUpdateFlag", next2.getMapUpdateFlag());
                        jSONObject2.put("routeUpdateFlag", next2.getRouteUpdateFlag());
                        jSONArray2.put(jSONObject2);
                    }
                    jSONObject.put(SelectCityActivity.EXTRA_PARAM_CITY_LIST, jSONArray2);
                    jSONArray.put(jSONObject);
                }
            }
        }
        if (jSONArray.length() == 0) {
            return "";
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("provinceList", jSONArray);
        return jSONObject3.toString();
    }

    public final String convertAllHotCityInfoJOStr(List<City> list) throws JSONException {
        if (list == null || list.isEmpty()) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (City next : list) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("adCode", JsConvertUtils.checkAdCode(String.valueOf(next.getAdCode())));
            jSONObject.put("provinceAdcode", next.getProvinceAdcode());
            jSONObject.put("name", next.getName());
            jSONObject.put(AutoJsonUtils.JSON_PINYIN, next.getPinyin());
            jSONObject.put(AutoJsonUtils.JSON_JIANPIN, next.getJianpin());
            jSONObject.put("mapSize", next.getMapSize());
            jSONObject.put("routeSize", next.getRouteSize());
            jSONObject.put("mapState", next.getMapState());
            jSONObject.put("naviState", next.getNaviState());
            jSONObject.put(TinyAppSubPackagePlugin.DOWNLOAD_STATUS, next.getDownloadStatus());
            jSONObject.put("isCurrentCity", next.getIsCurrentCity());
            jSONObject.put("updateFlag", next.getUpdateFlag());
            jSONObject.put("updateSize", next.getUpdateSize());
            jSONObject.put("mapUpdateFlag", next.getMapUpdateFlag());
            jSONObject.put("routeUpdateFlag", next.getRouteUpdateFlag());
            jSONArray.put(jSONObject);
        }
        if (jSONArray.length() == 0) {
            return "";
        }
        return jSONArray.toString();
    }
}
