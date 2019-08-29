package com.alipay.mobile.beehive.cityselect.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.beehive.cityselect.model.CityVO;
import com.alipay.mobile.beehive.cityselect.model.CityVOList;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.CitySelectHandler;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.IModifyCityCallBack;
import com.alipay.mobile.beehive.cityselect.service.CitySelectService.IProvinceCitySelectCallBack;
import com.alipay.mobile.beehive.cityselect.ui.ProvinceCityListActivity;
import com.alipay.mobile.beehive.cityselect.ui.SelectCityActivity;
import com.alipay.mobile.beehive.util.SpmUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.utils.LogCatUtil;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5CallBack;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Event.Error;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.rome.longlinkservice.LongLinkMsgConstants;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class H5PickCityPlugin extends H5SimplePlugin {
    private static final String API_SET_LOCATED_CITY = "setLocatedCity";
    public static final String GET_CITIES = "getCities";
    public static final String GET_CUSTOM_CITIES = "getCustomCities";
    public static final String GET_PROVINCE_CITYS = "beehive.getProvinceCitys";
    public static final int INVALID_CITY_ID = 13;
    public static final String TAG = "H5PickCityPlugin";

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(GET_CITIES);
        filter.addAction(GET_CUSTOM_CITIES);
        filter.addAction(GET_PROVINCE_CITYS);
        filter.addAction("setLocatedCity");
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        String action = event.getAction();
        if (GET_CITIES.equals(action)) {
            getCities(event, context);
        } else if (GET_CUSTOM_CITIES.equals(action)) {
            getCities(event, context);
        } else if (GET_PROVINCE_CITYS.equals(action)) {
            getProvinceCitys(event, context);
        } else if (!"setLocatedCity".equals(action)) {
            return false;
        } else {
            setCitySelectView(event, context);
        }
        return true;
    }

    private void getProvinceCitys(H5Event event, final H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        Bundle params = new Bundle();
        params.putString(ProvinceCityListActivity.KEY_FILTER_COUNTRY, param.getString(ProvinceCityListActivity.KEY_FILTER_COUNTRY));
        params.putString(ProvinceCityListActivity.KEY_COUNTRY, param.getString(ProvinceCityListActivity.KEY_COUNTRY));
        params.putString(ProvinceCityListActivity.KEY_PROVINCE, param.getString(ProvinceCityListActivity.KEY_PROVINCE));
        params.putString(ProvinceCityListActivity.KEY_CITY, param.getString(ProvinceCityListActivity.KEY_CITY));
        params.putString(ProvinceCityListActivity.KEY_HIDE_LOCATE_HEADER, param.getString(ProvinceCityListActivity.KEY_HIDE_LOCATE_HEADER));
        params.putString(ProvinceCityListActivity.KEY_PRE_DATA, param.getString(ProvinceCityListActivity.KEY_PRE_DATA));
        params.putString(ProvinceCityListActivity.KEY_USE_CHINESE, param.getString(ProvinceCityListActivity.KEY_USE_CHINESE));
        params.putString(ProvinceCityListActivity.KEY_RETURN_CODE, param.getString(ProvinceCityListActivity.KEY_RETURN_CODE));
        SpmUtils.addSourceByH5Event(event, params);
        ((CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(CitySelectService.class.getName())).callProvinceCitySelect(new IProvinceCitySelectCallBack() {
            public final void OnProvinceCitySelect(Intent result) {
                LoggerFactory.getTraceLogger().error((String) H5PickCityPlugin.TAG, "OnProvinceCitySelect:" + result.getStringExtra(ProvinceCityListActivity.KEY_COUNTRY));
                JSONObject object = new JSONObject();
                object.put((String) ProvinceCityListActivity.KEY_COUNTRY, (Object) result.getStringExtra(ProvinceCityListActivity.KEY_COUNTRY));
                object.put((String) ProvinceCityListActivity.KEY_PROVINCE, (Object) result.getStringExtra(ProvinceCityListActivity.KEY_PROVINCE));
                object.put((String) ProvinceCityListActivity.KEY_CITY, (Object) result.getStringExtra(ProvinceCityListActivity.KEY_CITY));
                object.put((String) ProvinceCityListActivity.KEY_COUNTRY_CODE, (Object) result.getStringExtra(ProvinceCityListActivity.KEY_COUNTRY_CODE));
                object.put((String) ProvinceCityListActivity.KEY_PROVINCE_CODE, (Object) result.getStringExtra(ProvinceCityListActivity.KEY_PROVINCE_CODE));
                object.put((String) ProvinceCityListActivity.KEY_CITY_CODE, (Object) result.getStringExtra(ProvinceCityListActivity.KEY_CITY_CODE));
                bridgeContext.sendBridgeResult(object);
            }
        }, params);
    }

    private void getCities(H5Event event, H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
            return;
        }
        Object current = param.get(SelectCityActivity.EXTRA_PARAM_CURRENT_CITY);
        String currentCityStr = null;
        String adCode = null;
        if (current instanceof String) {
            currentCityStr = (String) current;
            adCode = param.getString(AutoJsonUtils.JSON_ADCODE);
        } else if (current instanceof JSONObject) {
            JSONObject joCurrent = (JSONObject) current;
            currentCityStr = joCurrent.getString("name");
            adCode = joCurrent.getString(AutoJsonUtils.JSON_ADCODE);
        }
        CityVO currentCity = null;
        if (!(!(param.containsKey("showCurrentCity") ? param.getBoolean("showCurrentCity").booleanValue() : true) || currentCityStr == null || adCode == null)) {
            currentCity = new CityVO();
            currentCity.city = currentCityStr;
            currentCity.adCode = adCode;
        }
        startCitySelectService(bridgeContext, currentCity, param.getString("title"), convertCityVO(param.getJSONArray("customCities")), convertCityVO(param.getJSONArray("customHotCities")), convertCityVO(param.getJSONArray("customHistoryCities")), param.containsKey("needHotCity") ? param.getBoolean("needHotCity").booleanValue() : true, param.containsKey("isTinyApp") ? param.getBoolean("isTinyApp").booleanValue() : false, param, event);
    }

    private void setCitySelectView(H5Event event, final H5BridgeContext bridgeContext) {
        JSONObject param = event.getParam();
        if (param == null) {
            bridgeContext.sendError(event, Error.INVALID_PARAM);
        } else if (!param.containsKey("locatedCityName")) {
            bridgeContext.sendError(12, (String) "必填参数为空");
        } else {
            Object vk = param.get("locatedCityName");
            if (!(vk instanceof String)) {
                bridgeContext.sendError(11, (String) "参数类型错误");
            } else if (TextUtils.isEmpty((String) vk)) {
                bridgeContext.sendError(12, (String) "必填参数为空");
            } else {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString(LongLinkMsgConstants.LONGLINK_APPDATA, param.toJSONString());
                    bundle.putString("action", "setLocatedCity");
                    bundle.putString("serviceId", param.getString("locatedCityId"));
                    CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(CitySelectService.class.getName());
                    citySelectService.setModifyCallBack(new IModifyCityCallBack() {
                        public final void onModifySuccess(CityVO cityInfo) {
                            LoggerFactory.getTraceLogger().debug(H5PickCityPlugin.TAG, "modify located city success: " + cityInfo.city);
                            JSONObject object = new JSONObject();
                            object.put((String) "locatedCityName", (Object) cityInfo.city);
                            bridgeContext.sendBridgeResult(object);
                        }

                        public final void onModifyFailed(int msg) {
                            if (13 == msg) {
                                bridgeContext.sendError(13, (String) "locatedCityId不匹配");
                            } else {
                                bridgeContext.sendError(14, (String) "未知错误");
                            }
                        }
                    });
                    citySelectService.notifySetCitySelectView(bundle);
                } catch (Exception ex) {
                    LogCatUtil.warn((String) TAG, (Throwable) ex);
                }
            }
        }
    }

    private void startCitySelectService(final H5BridgeContext bridgeContext, CityVO currentCity, String title, CityVOList customCitiesVOList, CityVOList customHotCitiesVOList, CityVOList historyCityList, boolean needHotCity, boolean isTinyApp, JSONObject param, H5Event event) {
        CitySelectService citySelectService = (CitySelectService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().getExtServiceByInterface(CitySelectService.class.getName());
        Bundle bundle = new Bundle();
        if (!TextUtils.isEmpty(title)) {
            bundle.putString(SelectCityActivity.EXTRA_TITLE_NAME, title);
        }
        if (currentCity != null) {
            bundle.putParcelable(SelectCityActivity.EXTRA_PARAM_CURRENT_CITY, currentCity);
            bundle.putBoolean(SelectCityActivity.EXTRA_PARAM_CURRENTCITY_VISIABLE, true);
        } else {
            bundle.putBoolean(SelectCityActivity.EXTRA_PARAM_CURRENTCITY_VISIABLE, false);
        }
        if (customCitiesVOList.cityList != null && !customCitiesVOList.cityList.isEmpty()) {
            bundle.putParcelableArrayList(SelectCityActivity.EXTRA_PARAM_CITY_LIST, (ArrayList) customCitiesVOList.cityList);
        }
        if (customHotCitiesVOList.cityList != null && !customHotCitiesVOList.cityList.isEmpty() && needHotCity) {
            bundle.putParcelableArrayList(SelectCityActivity.EXTRA_PARAM_HOTCITY_LIST, (ArrayList) customHotCitiesVOList.cityList);
        }
        if (historyCityList.cityList != null && !historyCityList.cityList.isEmpty()) {
            bundle.putParcelableArrayList(SelectCityActivity.EXTRA_PARAM_HISTORY_CITY_LIST, (ArrayList) historyCityList.cityList);
            bundle.putBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_HISTORY_CITY, true);
        }
        bundle.putBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_HOT_CITY, needHotCity);
        bundle.putBoolean("isTinyApp", isTinyApp);
        for (Entry<String, Object> key : param.entrySet()) {
            String key2 = (String) key.getKey();
            char c = 65535;
            switch (key2.hashCode()) {
                case -1985631717:
                    if (key2.equals("setLocatedCity")) {
                        c = 13;
                        break;
                    }
                    break;
                case -1719591178:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_SECTION)) {
                        c = 6;
                        break;
                    }
                    break;
                case -1696469856:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_SHOW_LOCATED_CITY)) {
                        c = 12;
                        break;
                    }
                    break;
                case -1292671235:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKNAME_OVERSEA)) {
                        c = 8;
                        break;
                    }
                    break;
                case -681605518:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_SEARCHBARHINT)) {
                        c = 0;
                        break;
                    }
                    break;
                case -617455141:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_HOT_CITY_SECTION)) {
                        c = 4;
                        break;
                    }
                    break;
                case -502680135:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_HISTORYCITYTITLE)) {
                        c = 2;
                        break;
                    }
                    break;
                case -438115042:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKTITLE_OVERSEA)) {
                        c = 7;
                        break;
                    }
                    break;
                case 380637746:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_CITYSORT_DISABLE)) {
                        c = 11;
                        break;
                    }
                    break;
                case 506573044:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_HISTORY_CITY_SECTION)) {
                        c = 5;
                        break;
                    }
                    break;
                case 533702176:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_HOTCITYTITLE)) {
                        c = 1;
                        break;
                    }
                    break;
                case 1052013755:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_LOCATEDCITYTITLE)) {
                        c = 3;
                        break;
                    }
                    break;
                case 1141000747:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKSECTION_OVERSEA)) {
                        c = 10;
                        break;
                    }
                    break;
                case 1828273077:
                    if (key2.equals(SelectCityActivity.EXTRA_PARAM_RECOMMENDLINKURL_OVERSEA)) {
                        c = 9;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    bundle.putString(key2, param.getString(key2));
                    break;
                case 11:
                case 12:
                case 13:
                    bundle.putBoolean(key2, param.getBoolean(key2).booleanValue());
                    break;
            }
        }
        bundle.putBoolean(SelectCityActivity.EXTRA_PARAM_LOCATED_CITY_VISIBLE, bundle.getBoolean(SelectCityActivity.EXTRA_PARAM_SHOW_LOCATED_CITY));
        SpmUtils.addSourceByH5Event(event, bundle);
        final H5Event h5Event = event;
        citySelectService.callCitySelect((CitySelectHandler) new CitySelectHandler() {
            public final void onCitySelect(CityVO cityInfo, Activity activity) {
                LoggerFactory.getTraceLogger().info(H5PickCityPlugin.TAG, "OnCitySelect: city is " + cityInfo.city + " and adCode is " + cityInfo.adCode + ",latitude=" + cityInfo.latitude + ",longitude=" + cityInfo.longitude);
                bridgeContext.sendBridgeResult(H5PickCityPlugin.this.buildH5CitySelectResult(cityInfo, null, false));
            }

            public final void onLocateFinish(CityVO cityInfo, Activity activity, Bundle extParam) {
                LoggerFactory.getTraceLogger().info(H5PickCityPlugin.TAG, String.format("onLocateFinish: city=%s, adCode=%s, latitude=%s, longitude=%s, serviceId=", new Object[]{cityInfo.city, cityInfo.adCode, Double.valueOf(cityInfo.latitude), Double.valueOf(cityInfo.longitude)}));
                H5PickCityPlugin.this.sendMsgToH5(bridgeContext, h5Event, "locatedComplete", H5PickCityPlugin.this.buildH5CitySelectResult(cityInfo, extParam, true), null);
            }

            public final void onNothingSelected() {
            }
        }, bundle);
    }

    /* access modifiers changed from: private */
    public JSONObject buildH5CitySelectResult(CityVO cityInfo, Bundle extParam, boolean isLocateResult) {
        JSONObject object = new JSONObject();
        if (isLocateResult) {
            if (extParam != null) {
                object.put((String) "locatedCityId", (Object) extParam.getString("serviceId"));
            }
            object.put((String) "latitude", (Object) Double.valueOf(cityInfo.latitude));
            object.put((String) "longitude", (Object) Double.valueOf(cityInfo.longitude));
        } else {
            if (extParam != null) {
                for (String key : extParam.keySet()) {
                    object.put(key, extParam.get(key));
                }
            }
            object.put((String) "city", (Object) cityInfo.city);
            object.put((String) AutoJsonUtils.JSON_ADCODE, (Object) cityInfo.adCode);
            if (!(cityInfo.latitude == 0.0d || cityInfo.longitude == 0.0d)) {
                object.put((String) "latitude", (Object) Double.valueOf(cityInfo.latitude));
                object.put((String) "longitude", (Object) Double.valueOf(cityInfo.longitude));
            }
        }
        return object;
    }

    /* access modifiers changed from: private */
    public void sendMsgToH5(H5BridgeContext context, H5Event event, String msgName, JSONObject params, H5CallBack cb) {
        LoggerFactory.getTraceLogger().info(TAG, "sendMsgToH5 start send");
        H5Page page = event.getH5page();
        boolean useBrigeSend = false;
        if (page != null) {
            H5Bridge bridge = page.getBridge();
            if (bridge != null) {
                useBrigeSend = true;
                LoggerFactory.getTraceLogger().info(TAG, "bridge sendDataWarpToWeb start");
                bridge.sendDataWarpToWeb(msgName, params, cb);
            } else {
                LoggerFactory.getTraceLogger().info(TAG, "bridge is null");
            }
        }
        if (!useBrigeSend) {
            LoggerFactory.getTraceLogger().info(TAG, "bridgeContext sendToWeb:" + params);
            JSONObject wrapParams = new JSONObject();
            wrapParams.put((String) "data", (Object) params);
            context.sendToWeb(msgName, wrapParams, null);
        }
    }

    private CityVOList convertCityVO(JSONArray cities) {
        CityVOList list = new CityVOList();
        if (cities != null && !cities.isEmpty()) {
            List cityList = new ArrayList();
            for (int index = 0; index < cities.size(); index++) {
                Object obj = cities.get(index);
                if (obj instanceof JSONObject) {
                    JSONObject city = (JSONObject) obj;
                    CityVO cvo = new CityVO();
                    cvo.city = city.getString("name");
                    cvo.adCode = city.getString(AutoJsonUtils.JSON_ADCODE);
                    cvo.pinyin = city.getString(AutoJsonUtils.JSON_PINYIN);
                    cityList.add(cvo);
                }
            }
            list.cityList = cityList;
        }
        return list;
    }
}
