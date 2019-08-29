package com.autonavi.minimap.offline.helper;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.autonavi.minimap.offline.OfflineNativeSdk;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoUtils;
import com.autonavi.minimap.offline.model.City;
import com.autonavi.minimap.offline.model.Province;
import com.autonavi.minimap.offline.util.JsCommonUtils;
import com.autonavi.minimap.offline.util.JsConvertUtils;
import com.autonavi.minimap.offline.util.JsSortUtils;
import com.autonavi.minimap.offline.utils.CityHelper;
import com.autonavi.minimap.offlinesdk.ICityManager;
import com.autonavi.minimap.offlinesdk.model.CityInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsAlongWayAllCityHelper {
    private static final String EMPTY_JSON_STR = "";
    private static final String HOT_CITY_SECTION_NAME = "热门城市";

    private JsAlongWayAllCityHelper() {
    }

    public static String convertNativeCityInfoInProvince(String str) {
        if (OfflineNativeSdk.getInstance().getCityManager() == null) {
            return "";
        }
        List<Province> convertProvince = JsConvertUtils.convertProvince();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (convertProvince == null) {
            return "";
        }
        if (convertProvince != null) {
            for (Province next : convertProvince) {
                if (TextUtils.isEmpty(str) || "[]".equals(str)) {
                    arrayList2.add(next);
                }
                if (next.getCityList() != null) {
                    if (TextUtils.isEmpty(str) || "[]".equals(str)) {
                        arrayList.addAll(next.getCityList());
                    } else if (str.contains(next.getAdCode())) {
                        arrayList.addAll(next.getCityList());
                    }
                }
            }
        }
        List<City> sortOfPinyin = JsSortUtils.sortOfPinyin((List<City>) arrayList);
        return !sortOfPinyin.isEmpty() ? convertAlongWayProvinceCity(arrayList2, sortOfPinyin) : "";
    }

    public static String convertNativeCityInfo() {
        ICityManager cityManager = OfflineNativeSdk.getInstance().getCityManager();
        if (cityManager == null) {
            return "";
        }
        CityInfo[] allCities = cityManager.getAllCities();
        if (allCities == null) {
            return "";
        }
        CityInfo[] sortOfPinyin = JsSortUtils.sortOfPinyin(allCities);
        ArrayList arrayList = new ArrayList();
        for (CityInfo convertCity : sortOfPinyin) {
            arrayList.add(JsConvertUtils.convertCity(convertCity, false));
        }
        return !arrayList.isEmpty() ? convertAlongWayCity(arrayList) : "";
    }

    public static String convertAlongWayCity(List<City> list) {
        if (list == null) {
            return "";
        }
        try {
            JSONArray convertCityInfoToJA = convertCityInfoToJA(JsCommonUtils.cleanNullData(list));
            if (convertCityInfoToJA.length() == 0) {
                return "";
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("alongWayCityList", convertCityInfoToJA);
                return jSONObject.toString();
            } catch (JSONException unused) {
                return "";
            }
        } catch (JSONException unused2) {
            return "";
        }
    }

    public static String convertAlongWayProvinceCity(List<Province> list, List<City> list2) {
        if (list2 == null && list == null) {
            return "";
        }
        try {
            JSONArray convertCityInfoToJAExceptHot = convertCityInfoToJAExceptHot(JsCommonUtils.cleanNullData(list2));
            JSONArray convertProinceInfoToJAWithZhixia = convertProinceInfoToJAWithZhixia(list);
            if (convertCityInfoToJAExceptHot.length() == 0 && convertProinceInfoToJAWithZhixia.length() == 0) {
                return "";
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("cities", convertCityInfoToJAExceptHot);
                if (!(list == null || list.size() == 0)) {
                    jSONObject.put("provinces", convertProinceInfoToJAWithZhixia);
                }
                return jSONObject.toString();
            } catch (JSONException unused) {
                return "";
            }
        } catch (JSONException unused2) {
            return "";
        }
    }

    private static JSONObject convertCityInfoToJO(City city) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cityName", city.getName());
            jSONObject.put("adCode", JsConvertUtils.checkAdCode(String.valueOf(city.getAdCode())));
            jSONObject.put(AutoJsonUtils.JSON_JIANPIN, city.getJianpin());
            jSONObject.put(AutoJsonUtils.JSON_PINYIN, city.getPinyin());
            return jSONObject;
        } catch (JSONException unused) {
            return null;
        }
    }

    private static JSONArray convertCityInfoToJA(List<City> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray2 = new JSONArray();
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        int size = list.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            City city = list.get(i);
            if (TextUtils.isEmpty(city.getPinyin()) || (CityHelper.isProvince((long) city.getAdCode()) && !CityHelper.isMunicipalitiesCity((long) city.getAdCode()) && !CityHelper.isSpecialCity((long) city.getAdCode()))) {
                i = i2;
            } else {
                JSONObject jSONObject2 = new JSONObject();
                String substring = city.getPinyin().substring(0, 1);
                jSONObject2.put("sectionName", substring.toUpperCase());
                JSONArray jSONArray3 = new JSONArray();
                while (true) {
                    if (i2 <= size) {
                        if (!TextUtils.isEmpty(city.getPinyin()) && TextUtils.equals(substring, city.getPinyin().substring(0, 1))) {
                            if (!CityHelper.isProvince((long) city.getAdCode()) || CityHelper.isSpecialCity((long) city.getAdCode()) || CityHelper.isMunicipalitiesCity((long) city.getAdCode())) {
                                JSONObject convertCityInfoToJO = convertCityInfoToJO(city);
                                if (convertCityInfoToJO != null) {
                                    jSONArray3.put(convertCityInfoToJO(city));
                                    if (isHotCity(city)) {
                                        jSONArray2.put(convertCityInfoToJO);
                                    }
                                }
                            }
                            if (i2 >= size) {
                                break;
                            }
                            City city2 = list.get(i2);
                            i2++;
                            city = city2;
                        } else {
                            i2--;
                        }
                    } else {
                        break;
                    }
                }
                i = i2;
                if (jSONArray3.length() > 0) {
                    jSONObject2.put("isHotCity", false);
                    jSONObject2.put("cityData", jSONArray3);
                    arrayList.add(jSONObject2);
                }
            }
        }
        if (jSONArray2.length() > 0) {
            jSONObject.put("sectionName", HOT_CITY_SECTION_NAME);
            jSONObject.put("isHotCity", true);
            jSONObject.put("cityData", jSONArray2);
            arrayList.add(0, jSONObject);
        }
        for (JSONObject put : arrayList) {
            jSONArray.put(put);
        }
        return jSONArray;
    }

    private static JSONArray convertProinceInfoToJAWithZhixia(List<Province> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        if (list != null && list.size() > 0) {
            for (Province next : list) {
                if (next != null && !AutoUtils.CITY_GANGAO_GROUP_ADCOD.equals(next.getAdCode())) {
                    if (AutoUtils.CITY_QGGYT_GROUP_ADCODE.equals(next.getAdCode())) {
                        List<City> cityList = next.getCityList();
                        if (cityList != null && cityList.size() > 0) {
                            for (City next2 : cityList) {
                                JSONObject jSONObject = new JSONObject();
                                if (!(next2 == null || next2.getAdCode() == 0)) {
                                    jSONObject.put("adCode", next2.getAdCode());
                                    jSONObject.put("name", next2.getName());
                                    jSONObject.put(AutoJsonUtils.JSON_JIANPIN, next2.getJianpin());
                                    jSONObject.put(AutoJsonUtils.JSON_PINYIN, next2.getPinyin());
                                    jSONArray.put(jSONObject);
                                }
                            }
                        }
                    } else {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("adCode", next.getAdCode());
                        jSONObject2.put("name", next.getName());
                        jSONObject2.put(AutoJsonUtils.JSON_JIANPIN, next.getJianpin());
                        jSONObject2.put(AutoJsonUtils.JSON_PINYIN, next.getPinyin());
                        jSONArray.put(jSONObject2);
                    }
                }
            }
        }
        return jSONArray;
    }

    private static JSONArray convertCityInfoToJAExceptHot(List<City> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        ArrayList<JSONObject> arrayList = new ArrayList<>();
        int size = list.size();
        int i = 0;
        while (i < size) {
            int i2 = i + 1;
            City city = list.get(i);
            if (TextUtils.isEmpty(city.getPinyin()) || (CityHelper.isProvince((long) city.getAdCode()) && !CityHelper.isMunicipalitiesCity((long) city.getAdCode()) && !CityHelper.isSpecialCity((long) city.getAdCode()))) {
                i = i2;
            } else {
                JSONObject jSONObject = new JSONObject();
                String substring = city.getPinyin().substring(0, 1);
                jSONObject.put("sectionName", substring.toUpperCase());
                JSONArray jSONArray2 = new JSONArray();
                while (true) {
                    if (i2 <= size) {
                        if (!TextUtils.isEmpty(city.getPinyin()) && TextUtils.equals(substring, city.getPinyin().substring(0, 1))) {
                            if ((!CityHelper.isProvince((long) city.getAdCode()) || CityHelper.isSpecialCity((long) city.getAdCode()) || CityHelper.isMunicipalitiesCity((long) city.getAdCode())) && convertCityInfoToJO(city) != null) {
                                jSONArray2.put(convertCityInfoToJO(city));
                            }
                            if (i2 >= size) {
                                break;
                            }
                            City city2 = list.get(i2);
                            i2++;
                            city = city2;
                        } else {
                            i2--;
                        }
                    } else {
                        break;
                    }
                }
                i2--;
                i = i2;
                if (jSONArray2.length() > 0) {
                    jSONObject.put("isHotCity", false);
                    jSONObject.put("cityData", jSONArray2);
                    arrayList.add(jSONObject);
                }
            }
        }
        for (JSONObject put : arrayList) {
            jSONArray.put(put);
        }
        return jSONArray;
    }

    private static boolean isHotCity(@NonNull City city) {
        return JsConvertUtils.POPULAR_ADCODE_LIST.contains(Integer.valueOf(city.getAdCode()));
    }

    private static boolean isZhixiaCity(@NonNull City city) {
        return JsConvertUtils.ZHIXIA_ADCODE_LIST.contains(Integer.valueOf(city.getAdCode()));
    }
}
