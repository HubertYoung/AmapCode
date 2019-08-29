package com.autonavi.minimap.offline.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity;
import com.alipay.mobile.tinyappcommon.subpackage.TinyAppSubPackagePlugin;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.model.DownloadCity;
import com.autonavi.minimap.offline.model.DownloadProvince;
import java.util.List;

public class JsAllDownloadCityHelper {
    private static final String EMPTY_STR = "";

    private JsAllDownloadCityHelper() {
    }

    public static String convertDownloadAllCityJOStr(List<DownloadProvince> list) throws JSONException {
        if (list == null || list.isEmpty()) {
            return "";
        }
        JSONArray jSONArray = new JSONArray();
        for (DownloadProvince next : list) {
            List<DownloadCity> cityList = next.getCityList();
            if (cityList != null && !cityList.isEmpty()) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put((String) "code", (Object) Integer.valueOf(next.getCode()));
                jSONObject.put((String) "adCode", (Object) next.getAdCode());
                jSONObject.put((String) "name", (Object) next.getName());
                jSONObject.put((String) AutoJsonUtils.JSON_PINYIN, (Object) next.getPinyin());
                jSONObject.put((String) AutoJsonUtils.JSON_JIANPIN, (Object) next.getJianpin());
                jSONObject.put((String) "mapSize", (Object) Long.valueOf(next.getMapSize()));
                jSONObject.put((String) "routeSize", (Object) Long.valueOf(next.getRouteSize()));
                jSONObject.put((String) "mapState", (Object) Integer.valueOf(next.getMapState()));
                jSONObject.put((String) "naviState", (Object) Integer.valueOf(next.getNaviState()));
                jSONObject.put((String) TinyAppSubPackagePlugin.DOWNLOAD_STATUS, (Object) Integer.valueOf(next.getDownloadStatus()));
                jSONObject.put((String) "updateFlag", (Object) next.getUpdateFlag());
                jSONObject.put((String) "updateSize", (Object) next.getUpdateSize());
                jSONObject.put((String) "mapUpdateFlag", (Object) next.getMapUpdateFlag());
                jSONObject.put((String) "routeUpdateFlag", (Object) next.getRouteUpdateFlag());
                JSONArray jSONArray2 = new JSONArray();
                for (DownloadCity next2 : cityList) {
                    if (next2 != null) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put((String) "adCode", (Object) next2.getAdCode());
                        jSONObject2.put((String) "name", (Object) next2.getName());
                        jSONObject2.put((String) AutoJsonUtils.JSON_PINYIN, (Object) next2.getPinyin());
                        jSONObject2.put((String) AutoJsonUtils.JSON_JIANPIN, (Object) next2.getJianpin());
                        jSONObject2.put((String) "mapSize", (Object) Long.valueOf(next2.getMapSize()));
                        jSONObject2.put((String) "routeSize", (Object) Long.valueOf(next2.getRouteSize()));
                        jSONObject2.put((String) "mapState", (Object) Integer.valueOf(next2.getMapState()));
                        jSONObject2.put((String) "naviState", (Object) Integer.valueOf(next2.getNaviState()));
                        jSONObject2.put((String) "isCurrentCity", (Object) next2.getIsCurrentCity());
                        jSONObject2.put((String) TinyAppSubPackagePlugin.DOWNLOAD_STATUS, (Object) Integer.valueOf(next2.getDownloadStatus()));
                        jSONObject2.put((String) "downloadedMapSize", (Object) Long.valueOf(next2.getDownloadedMapSize()));
                        jSONObject2.put((String) "downloadedRouteSize", (Object) Long.valueOf(next2.getDownloadedRouteSize()));
                        jSONObject2.put((String) "downloadType", (Object) Integer.valueOf(next2.getDownloadType()));
                        jSONObject2.put((String) "downloadingProgress", (Object) Double.valueOf(next2.getDownloadingProgress()));
                        jSONObject2.put((String) "updateFlag", (Object) next2.getUpdateFlag());
                        jSONObject2.put((String) "updateSize", (Object) next2.getUpdateSize());
                        jSONObject2.put((String) "mapUpdateFlag", (Object) next2.getMapUpdateFlag());
                        jSONObject2.put((String) "routeUpdateFlag", (Object) next2.getRouteUpdateFlag());
                        jSONArray2.add(jSONObject2);
                    }
                }
                if (!jSONArray2.isEmpty()) {
                    jSONObject.put((String) SelectCityActivity.EXTRA_PARAM_CITY_LIST, (Object) jSONArray2);
                }
                jSONArray.add(jSONObject);
            }
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put((String) "provinceList", (Object) jSONArray);
        return jSONObject3.toString();
    }
}
