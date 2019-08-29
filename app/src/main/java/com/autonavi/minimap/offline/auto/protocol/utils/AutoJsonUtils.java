package com.autonavi.minimap.offline.auto.protocol.utils;

import android.text.TextUtils;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.minimap.offline.auto.model.nativeModel.AutoJsCity;
import com.autonavi.minimap.offline.auto.model.nativeModel.NativeCity;
import com.autonavi.minimap.offline.auto.model.nativeModel.NativeCity.DataBean;
import com.autonavi.minimap.offline.auto.model.nativeModel.NativeCity.DataBean.CityBean;
import com.autonavi.minimap.offline.auto.model.nativeModel.NativeCity.DataBean.CityBean.FilesBean;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AutoJsonUtils {
    public static final String JSON_ADCODE = "adcode";
    public static final String JSON_CITY = "city";
    public static final String JSON_DATA = "data";
    public static final String JSON_FILES = "files";
    public static final String JSON_ID = "ID";
    public static final String JSON_JIANPIN = "jianpin";
    public static final String JSON_MAP_SIZE = "map_size";
    public static final String JSON_MD5 = "md5";
    public static final String JSON_NAME = "name";
    public static final String JSON_PATH = "path";
    public static final String JSON_PINYIN = "pinyin";
    public static final String JSON_ROUTE_SIZE = "route_size";
    public static final String JSON_SIZE = "size";
    public static final String JSON_STATUS = "status";
    public static final String JSON_TYPE = "type";
    public static final String JSON_VERSION = "version";

    public static NativeCity parseJsonNativceCity(String str) {
        NativeCity nativeCity = new NativeCity();
        if (TextUtils.isEmpty(str)) {
            return nativeCity;
        }
        try {
            JSONArray optJSONArray = new JSONObject(str).optJSONArray("data");
            if (optJSONArray == null) {
                return null;
            }
            int length = optJSONArray.length();
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < length; i++) {
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                DataBean dataBean = new DataBean();
                dataBean.setJianpin(optJSONObject.optString(JSON_JIANPIN));
                dataBean.setName(optJSONObject.optString("name"));
                dataBean.setPinyin(optJSONObject.optString(JSON_PINYIN));
                dataBean.setAdcode(optJSONObject.optString(JSON_ADCODE));
                JSONArray jSONArray = optJSONObject.getJSONArray("city");
                int length2 = jSONArray.length();
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < length2; i2++) {
                    arrayList2.add(buildCity(jSONArray.optJSONObject(i2)));
                }
                dataBean.setCity(arrayList2);
                arrayList.add(dataBean);
            }
            nativeCity.setData(arrayList);
            return nativeCity;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static CityBean buildCity(JSONObject jSONObject) {
        CityBean cityBean = new CityBean();
        cityBean.setId(jSONObject.optString(JSON_ID));
        cityBean.setAdcode(jSONObject.optString(JSON_ADCODE));
        cityBean.setJianpin(jSONObject.optString(JSON_JIANPIN));
        cityBean.setName(jSONObject.optString("name"));
        cityBean.setPinyin(jSONObject.optString(JSON_PINYIN));
        cityBean.setRoute_size(jSONObject.optDouble(JSON_ROUTE_SIZE));
        cityBean.setMap_size(jSONObject.optDouble(JSON_MAP_SIZE));
        cityBean.setStatus(jSONObject.optInt("status"));
        JSONArray optJSONArray = jSONObject.optJSONArray(JSON_FILES);
        int length = optJSONArray.length();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < length; i++) {
            arrayList.add(buildFiles(optJSONArray.optJSONObject(i)));
        }
        cityBean.setFiles(arrayList);
        return cityBean;
    }

    private static FilesBean buildFiles(JSONObject jSONObject) {
        FilesBean filesBean = new FilesBean();
        filesBean.setSize(jSONObject.optDouble("size"));
        filesBean.setPath(jSONObject.optString("path"));
        filesBean.setType(jSONObject.optString("type"));
        filesBean.setVersion(jSONObject.optLong("version"));
        filesBean.setMd5(jSONObject.optString("md5"));
        return filesBean;
    }

    public static List<AutoJsCity.DataBean.CityBean> buildJsSendCities(String str) {
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                arrayList.add(JsonUtil.fromString(jSONArray.optJSONObject(i).toString(), AutoJsCity.DataBean.CityBean.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static double calculateCityFileSize(AutoJsCity.DataBean.CityBean cityBean) {
        double d = 0.0d;
        if (cityBean == null) {
            return 0.0d;
        }
        List<AutoJsCity.DataBean.CityBean.FilesBean> files = cityBean.getFiles();
        if (files != null) {
            for (AutoJsCity.DataBean.CityBean.FilesBean size : files) {
                d += size.getSize();
            }
        }
        return d;
    }
}
