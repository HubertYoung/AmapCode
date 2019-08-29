package com.autonavi.miniapp.plugin.lbs;

import android.content.Context;
import android.location.Location;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.behavor.Behavor;
import com.alipay.mobile.common.transport.monitor.RPCDataItems;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5plugin.H5LocationPlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Callback.ResponseCodeCallback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.minimap.R;
import com.autonavi.sdk.location.LocationInstrument;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class H5GetCurrentLocation {
    private static final List<String> CHINA_NAME;
    private static final int MAX_REQUEST_TYPE = 3;
    private static final String TAG = "H5GetCurrentLocation";
    private String action;
    private String bizType;
    private long cacheTime = 30;
    private H5BridgeContext h5BridgeContext;
    /* access modifiers changed from: private */
    public H5Event h5Event;
    private boolean isHighAccuracy = false;
    private boolean isNeedSpeed = false;
    private LocationListener locationListener;
    private SimpleLocationCache mSimpleLocationCache;
    private boolean needAddress;
    /* access modifiers changed from: private */
    public int requestType = 0;
    private long startTime;
    private long timeOut = 10000;

    static {
        ArrayList arrayList = new ArrayList();
        CHINA_NAME = arrayList;
        arrayList.add("中国");
        CHINA_NAME.add("中华人民共和国");
        CHINA_NAME.add("中國");
        CHINA_NAME.add("中華人民共和國");
        CHINA_NAME.add("China");
    }

    public H5GetCurrentLocation(H5Event h5Event2, H5BridgeContext h5BridgeContext2, LocationListener locationListener2, long j, SimpleLocationCache simpleLocationCache) {
        this.startTime = j;
        this.h5Event = h5Event2;
        this.h5BridgeContext = h5BridgeContext2;
        this.locationListener = locationListener2;
        this.mSimpleLocationCache = simpleLocationCache;
    }

    public void getCurrentLocation() {
        if (this.h5Event == null) {
            LoggerFactory.getTraceLogger().error((String) TAG, (String) "event = null");
            return;
        }
        H5Log.d(TAG, H5LocationPlugin.GET_LOCATION);
        initParam();
        getAmapLocation();
    }

    private void initParam() {
        this.action = this.h5Event.getAction();
        try {
            JSONObject param = this.h5Event.getParam();
            this.timeOut = (long) (H5Utils.getInt(param, (String) "timeout", 10) * 1000);
            boolean z = true;
            if ("getKBLocation".equals(this.action)) {
                this.cacheTime = (long) H5Utils.getInt(param, (String) "cacheTime", 180);
                if (this.cacheTime < 15) {
                    this.cacheTime = 180;
                }
                this.requestType = 1;
            } else {
                this.cacheTime = (long) H5Utils.getInt(param, (String) "cacheTimeout", 30);
                this.requestType = H5Utils.getInt(param, (String) H5Location.REQUEST_TYPE, 0);
            }
            if (this.requestType <= 0 || this.requestType >= 4) {
                z = false;
            }
            this.needAddress = z;
            this.isHighAccuracy = H5Utils.getBoolean(param, (String) "isHighAccuracy", false);
            this.isNeedSpeed = H5Utils.getBoolean(param, (String) "isNeedSpeed", false);
            this.bizType = H5Utils.getString(this.h5Event.getParam(), (String) "bizType", (String) null);
            if (TextUtils.isEmpty(this.bizType)) {
                this.bizType = new URL(((H5Page) this.h5Event.getTarget()).getUrl()).getHost();
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error((String) TAG, "initParam, th=".concat(String.valueOf(th)));
        }
    }

    private JSONObject tryToGetFromCache(int i, long j) {
        if (this.mSimpleLocationCache == null) {
            return null;
        }
        return this.mSimpleLocationCache.getCacheJsonObj(i, j);
    }

    /* access modifiers changed from: private */
    public void tryToSave2Cache(int i, JSONObject jSONObject) {
        if (this.mSimpleLocationCache != null) {
            this.mSimpleLocationCache.saveCache(i, jSONObject);
        }
    }

    private void getAmapLocation() {
        if (!kj.a((Context) this.h5Event.getActivity(), (String) "android.permission.ACCESS_FINE_LOCATION")) {
            invokeGetCurrentLocationError(11, this.h5Event.getActivity().getString(R.string.get_location_failed_permission));
        } else if (LocationInstrument.getInstance().getLatestPosition(5) == null) {
            invokeGetCurrentLocationError(13, this.h5Event.getActivity().getString(R.string.get_location_failed));
        } else {
            if ("getKBLocation".equals(this.action)) {
                for (int i = 1; i <= 3; i++) {
                    JSONObject tryToGetFromCache = tryToGetFromCache(i, this.cacheTime);
                    if (SimpleLocationCache.isValid(tryToGetFromCache)) {
                        invokeGetCurrentLocationResult(tryToGetFromCache);
                        return;
                    }
                }
            } else {
                JSONObject tryToGetFromCache2 = tryToGetFromCache(this.requestType, this.cacheTime);
                if (SimpleLocationCache.isValid(tryToGetFromCache2)) {
                    invokeGetCurrentLocationResult(tryToGetFromCache2);
                    return;
                }
            }
            final JSONObject jSONObject = new JSONObject();
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            jSONObject.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(latestLocation.getLatitude())));
            jSONObject.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(latestLocation.getLongitude())));
            jSONObject.put((String) CameraControllerManager.MY_POILOCATION_ACR, (Object) Float.valueOf(latestLocation.getAccuracy()));
            jSONObject.put((String) "horizontalAccuracy", (Object) Float.valueOf(latestLocation.getAccuracy()));
            if (this.requestType == 0) {
                invokeGetCurrentLocationResult(jSONObject);
                tryToSave2Cache(this.requestType, jSONObject);
                return;
            }
            MiniAppReverseGeocodeUtil.getMiniAppReverseGeocodeResult(new GeoPoint(latestLocation.getLongitude(), latestLocation.getLatitude()), new ResponseCodeCallback<MiniappReverseGeocodeResponser>() {
                public void onReseponseCode(int i) {
                    AMapLog.debug("infoservice.miniapp", "Tilll", "i:".concat(String.valueOf(i)));
                }

                public void callback(MiniappReverseGeocodeResponser miniappReverseGeocodeResponser) {
                    jSONObject.put((String) "city", (Object) miniappReverseGeocodeResponser.getCity());
                    jSONObject.put((String) "cityAdcode", (Object) AdcodeConverter.getMiniCityCode(miniappReverseGeocodeResponser.getDistrictadcode()));
                    jSONObject.put((String) "province", (Object) miniappReverseGeocodeResponser.getProvince());
                    jSONObject.put((String) "district", (Object) miniappReverseGeocodeResponser.getDistrict());
                    jSONObject.put((String) "districtAdcode", (Object) miniappReverseGeocodeResponser.getDistrictadcode());
                    jSONObject.put((String) "country", (Object) miniappReverseGeocodeResponser.getCountry());
                    jSONObject.put((String) "countryCode", (Object) "中国".equals(miniappReverseGeocodeResponser.getCountry()) ? "156" : "");
                    if (H5GetCurrentLocation.this.requestType == 1) {
                        H5GetCurrentLocation.this.invokeGetCurrentLocationResult(jSONObject);
                        H5GetCurrentLocation.this.tryToSave2Cache(H5GetCurrentLocation.this.requestType, jSONObject);
                        return;
                    }
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put((String) "number", (Object) miniappReverseGeocodeResponser.getStreetNumber());
                    jSONObject.put((String) "street", (Object) miniappReverseGeocodeResponser.getStreet());
                    jSONObject.put((String) "streetNumber", (Object) jSONObject);
                    ArrayList<POI> poiList = miniappReverseGeocodeResponser.getPoiList();
                    if (H5GetCurrentLocation.this.requestType == 2 || poiList == null || poiList.size() == 0) {
                        H5GetCurrentLocation.this.invokeGetCurrentLocationResult(jSONObject);
                        H5GetCurrentLocation.this.tryToSave2Cache(H5GetCurrentLocation.this.requestType, jSONObject);
                        return;
                    }
                    JSONArray jSONArray = new JSONArray();
                    for (int i = 1; i < poiList.size(); i++) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put((String) "address", (Object) poiList.get(i).getAddr());
                        jSONObject2.put((String) "name", (Object) poiList.get(i).getName());
                        jSONArray.add(jSONObject2);
                    }
                    jSONObject.put((String) "pois", (Object) jSONArray);
                    H5GetCurrentLocation.this.invokeGetCurrentLocationResult(jSONObject);
                    H5GetCurrentLocation.this.tryToSave2Cache(H5GetCurrentLocation.this.requestType, jSONObject);
                }

                public void error(Throwable th, boolean z) {
                    if (H5GetCurrentLocation.this.h5Event != null && H5GetCurrentLocation.this.h5Event.getActivity() != null) {
                        if (aaw.d(H5GetCurrentLocation.this.h5Event.getActivity().getApplicationContext()) == 0) {
                            H5GetCurrentLocation.this.invokeGetCurrentLocationError(12, H5GetCurrentLocation.this.h5Event.getActivity().getString(R.string.get_location_failed_no_network));
                        } else {
                            H5GetCurrentLocation.this.invokeGetCurrentLocationError(14, H5GetCurrentLocation.this.h5Event.getActivity().getString(R.string.get_location_failed_network_timeout));
                        }
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void invokeGetCurrentLocationResult(JSONObject jSONObject) {
        if (this.h5BridgeContext != null) {
            if ("getKBLocation".equals(this.action)) {
                this.h5BridgeContext.sendBridgeResult(trans2KBLocatonResult(jSONObject));
                return;
            }
            this.h5BridgeContext.sendBridgeResult(jSONObject);
        }
    }

    private JSONObject trans2KBLocatonResult(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        String string = jSONObject.getString("districtAdcode");
        jSONObject2.put((String) "adCode", (Object) string);
        jSONObject2.put((String) "cityCode", (Object) AdcodeConverter.getMiniCityCode(string));
        jSONObject2.put((String) "city", (Object) jSONObject.getString("city"));
        jSONObject2.put((String) "latitude", (Object) jSONObject.getString("latitude"));
        jSONObject2.put((String) "longitude", (Object) jSONObject.getString("longitude"));
        jSONObject2.put((String) "mainLand", (Object) Boolean.valueOf(isMainLand(jSONObject.getString("country"), string)));
        return jSONObject2;
    }

    private boolean isMainLand(String str, String str2) {
        return !TextUtils.isEmpty(str) && CHINA_NAME.contains(str) && !str2.startsWith("71") && !str2.startsWith("81") && !str2.startsWith("82");
    }

    /* access modifiers changed from: private */
    public void invokeGetCurrentLocationError(int i, String str) {
        if (this.h5BridgeContext != null) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "error", (Object) Integer.valueOf(i));
            jSONObject.put((String) "errorMessage", (Object) str);
            this.h5BridgeContext.sendBridgeResult(jSONObject);
            Behavor behavor = new Behavor();
            behavor.setSeedID("H5_LOCATION_FAILED");
            behavor.setUserCaseID("H5_LOCATION");
            behavor.setBehaviourPro(RPCDataItems.LBSINFO);
            behavor.setParam1(String.valueOf(i));
            LoggerFactory.getBehavorLogger().event(null, behavor);
        }
    }
}
