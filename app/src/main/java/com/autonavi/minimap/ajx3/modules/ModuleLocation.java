package com.autonavi.minimap.ajx3.modules;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.aosservice.request.AosGetRequest;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.aosservice.response.AosResponseCallback;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Callback;
import com.autonavi.common.SuperId;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.gdtaojin.camera.CameraControllerManager;
import com.autonavi.jni.ae.gmap.utils.GLMapUtil;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.route.common.view.RouteBanner;
import com.autonavi.sdk.location.LocationInstrument;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("location")
public final class ModuleLocation extends AbstractModule {
    public static final String MODULE_NAME = "location";
    public static final String MY_LOCATION_DES = "我的位置";
    private static String[] sProvinceCode = {"11", "12", "31", "50", "13", "14", "21", "22", "23", "32", RouteBanner.REQUEST_KEY_TRAIN, "34", "35", RouteBanner.REQUEST_KEY_RIDE, RouteBanner.REQUEST_KEY_COACH, SuperId.BIT_2_INDOOR_TAG_HOT, "42", "43", "44", "46", "51", "52", "53", "61", "62", SuperId.BIT_2_REALTIMEBUS_BUSSTATION_AUTO, "45", "15", "54", SuperId.BIT_2_REALTIMEBUS_BUSSTATION, SuperId.BIT_2_MAIN_BUSSTATION};
    private static String[] sProvinceNames = {"京", "津", "沪", "渝", "冀", "晋", "辽", "吉", "黑", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "琼", "川", "贵", "云", "陕", "甘", "青", "桂", "蒙", "藏", "宁", "新"};
    private CustomLocationChangeListener mCustomLocationChangeListener;
    /* access modifiers changed from: private */
    public boolean mGPSValide = false;
    public JsFunctionCallback mGPSWeakCallback;
    private JsFunctionCallback mGetLocationIndoorFloorIndexCallBack;
    private LocationChangeListener mLocationChangeListener;
    private ang<Status> originalLocationCallback = new ang<Status>() {
        public void onOriginalLocationChange(Status status) {
            if (status == Status.ON_LOCATION_GPS_FAIL_LOOP) {
                if (ModuleLocation.this.mGPSWeakCallback != null) {
                    ModuleLocation.this.mGPSWeakCallback.callback("{\"isGPSLost\":1}");
                }
                ModuleLocation.this.mGPSValide = false;
                return;
            }
            if (status == Status.ON_LOCATION_GPS_OK && !ModuleLocation.this.mGPSValide) {
                if (ModuleLocation.this.mGPSWeakCallback != null) {
                    ModuleLocation.this.mGPSWeakCallback.callback("{\"isGPSLost\":0}");
                }
                ModuleLocation.this.mGPSValide = true;
            }
        }
    };

    static class CustomLocationChangeListener implements Callback<Status> {
        private boolean mAndOr = true;
        private JsFunctionCallback mFailJsCallback;
        private WeakReference<ModuleLocation> mJsLocationService;
        private Location mLastCallbackLocation;
        private long mLastCallbackTimeStamp = 0;
        private float mMaxLength;
        private long mMaxTimeMs;
        private JsFunctionCallback mSuccessJsCallback;

        public void error(Throwable th, boolean z) {
        }

        public CustomLocationChangeListener(float f, long j, boolean z, ModuleLocation moduleLocation) {
            this.mJsLocationService = new WeakReference<>(moduleLocation);
            this.mMaxLength = f;
            this.mMaxTimeMs = j;
            this.mAndOr = z;
        }

        /* access modifiers changed from: 0000 */
        public void setSuccessJsRef(JsFunctionCallback jsFunctionCallback) {
            this.mSuccessJsCallback = jsFunctionCallback;
            ModuleLocation moduleLocation = (ModuleLocation) this.mJsLocationService.get();
            if (moduleLocation != null && jsFunctionCallback != null && LocationInstrument.getInstance().getLatestPosition(5) != null) {
                Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
                jsFunctionCallback.callback(moduleLocation.innerGetLocation(latestLocation));
                this.mLastCallbackLocation = latestLocation;
                this.mLastCallbackTimeStamp = SystemClock.elapsedRealtime();
            }
        }

        /* access modifiers changed from: 0000 */
        public void setFailJsRef(JsFunctionCallback jsFunctionCallback) {
            this.mFailJsCallback = jsFunctionCallback;
        }

        public void callback(Status status) {
            if (status != Status.ON_LOCATION_OK || this.mSuccessJsCallback == null) {
                if (status == Status.ON_LOCATION_FAIL && this.mFailJsCallback != null) {
                    this.mFailJsCallback.callback(new Object[0]);
                }
                return;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.mLastCallbackTimeStamp;
            if (!this.mAndOr) {
                ModuleLocation moduleLocation = (ModuleLocation) this.mJsLocationService.get();
                if (!(moduleLocation == null || this.mSuccessJsCallback == null)) {
                    Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
                    if (elapsedRealtime >= this.mMaxTimeMs) {
                        this.mSuccessJsCallback.callback(moduleLocation.innerGetLocation(latestLocation));
                        this.mLastCallbackLocation = latestLocation;
                        this.mLastCallbackTimeStamp = SystemClock.elapsedRealtime();
                    } else if (this.mLastCallbackLocation != null) {
                        if (cfe.a(new GeoPoint(this.mLastCallbackLocation.getLongitude(), this.mLastCallbackLocation.getLatitude()), new GeoPoint(latestLocation.getLongitude(), latestLocation.getLatitude())) >= this.mMaxLength) {
                            this.mSuccessJsCallback.callback(moduleLocation.innerGetLocation(latestLocation));
                            this.mLastCallbackLocation = latestLocation;
                            this.mLastCallbackTimeStamp = SystemClock.elapsedRealtime();
                        }
                    } else {
                        this.mSuccessJsCallback.callback(moduleLocation.innerGetLocation(latestLocation));
                        this.mLastCallbackLocation = latestLocation;
                        this.mLastCallbackTimeStamp = SystemClock.elapsedRealtime();
                    }
                }
            } else if (elapsedRealtime >= this.mMaxTimeMs) {
                ModuleLocation moduleLocation2 = (ModuleLocation) this.mJsLocationService.get();
                if (!(moduleLocation2 == null || this.mSuccessJsCallback == null)) {
                    Location latestLocation2 = LocationInstrument.getInstance().getLatestLocation();
                    if (this.mLastCallbackLocation == null) {
                        this.mSuccessJsCallback.callback(moduleLocation2.innerGetLocation(latestLocation2));
                        this.mLastCallbackLocation = latestLocation2;
                        this.mLastCallbackTimeStamp = SystemClock.elapsedRealtime();
                    } else if (cfe.a(new GeoPoint(this.mLastCallbackLocation.getLongitude(), this.mLastCallbackLocation.getLatitude()), new GeoPoint(latestLocation2.getLongitude(), latestLocation2.getLatitude())) >= this.mMaxLength) {
                        this.mSuccessJsCallback.callback(moduleLocation2.innerGetLocation(latestLocation2));
                        this.mLastCallbackLocation = latestLocation2;
                        this.mLastCallbackTimeStamp = SystemClock.elapsedRealtime();
                    }
                }
            }
        }
    }

    static class LocationChangeListener implements Callback<Status> {
        private WeakReference<JsFunctionCallback> mFailJsCallback;
        private WeakReference<ModuleLocation> mJsLocationService;
        private WeakReference<JsFunctionCallback> mSuccessJsCallback;

        public void error(Throwable th, boolean z) {
        }

        LocationChangeListener(ModuleLocation moduleLocation) {
            this.mJsLocationService = new WeakReference<>(moduleLocation);
        }

        /* access modifiers changed from: 0000 */
        public void setSuccessJsRef(JsFunctionCallback jsFunctionCallback) {
            this.mSuccessJsCallback = new WeakReference<>(jsFunctionCallback);
        }

        /* access modifiers changed from: 0000 */
        public void setFailJsRef(JsFunctionCallback jsFunctionCallback) {
            this.mFailJsCallback = new WeakReference<>(jsFunctionCallback);
        }

        public void callback(Status status) {
            if (status != Status.ON_LOCATION_OK || this.mSuccessJsCallback == null) {
                if (status == Status.ON_LOCATION_FAIL && this.mFailJsCallback != null) {
                    JsFunctionCallback jsFunctionCallback = (JsFunctionCallback) this.mFailJsCallback.get();
                    if (jsFunctionCallback != null) {
                        jsFunctionCallback.callback(new Object[0]);
                    }
                }
                return;
            }
            JsFunctionCallback jsFunctionCallback2 = (JsFunctionCallback) this.mSuccessJsCallback.get();
            ModuleLocation moduleLocation = (ModuleLocation) this.mJsLocationService.get();
            if (!(moduleLocation == null || jsFunctionCallback2 == null)) {
                jsFunctionCallback2.callback(moduleLocation.innerGetLocation(LocationInstrument.getInstance().getLatestLocation()));
            }
        }
    }

    private boolean isValidCoordinate(double d, double d2) {
        return d <= 90.0d && d >= -90.0d && d2 <= 180.0d && d2 >= -180.0d;
    }

    public ModuleLocation(IAjxContext iAjxContext) {
        super(iAjxContext);
    }

    @AjxMethod(invokeMode = "sync", value = "getCoordinateFromG20")
    public final Object getCoordinateFromG20(int i, int i2) {
        double[] pixelToLonLat = GLMapUtil.pixelToLonLat(i, i2, 20);
        if (pixelToLonLat == null || pixelToLonLat.length < 2 || !isValidCoordinate(pixelToLonLat[1], pixelToLonLat[0])) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("longitude", pixelToLonLat[0]);
            jSONObject.put("latitude", pixelToLonLat[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    @AjxMethod(invokeMode = "sync", value = "getG20FromCoordinate")
    public final Object getG20FromCoordinate(double d, double d2) {
        if (!isValidCoordinate(d, d2)) {
            return null;
        }
        int[] lonLatToPixel = GLMapUtil.lonLatToPixel(d2, d, 20);
        JSONObject jSONObject = new JSONObject();
        if (lonLatToPixel != null) {
            try {
                if (lonLatToPixel.length > 1) {
                    jSONObject.put(DictionaryKeys.CTRLXY_X, lonLatToPixel[0]);
                    jSONObject.put(DictionaryKeys.CTRLXY_Y, lonLatToPixel[1]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    @AjxMethod(invokeMode = "sync", value = "getG20PerMeterWithCoordinate")
    public final float getG20PerMeterWithCoordinate(double d, double d2) {
        if (d > 90.0d || d < -90.0d || d2 > 180.0d || d2 < -180.0d) {
            return -1.0f;
        }
        GeoPoint geoPoint = new GeoPoint(d2, d);
        GeoPoint geoPoint2 = new GeoPoint();
        geoPoint2.x = geoPoint.x + 1000;
        geoPoint2.y = geoPoint.y;
        DPoint a = cfg.a((long) geoPoint.x, (long) geoPoint.y);
        DPoint a2 = cfg.a((long) geoPoint2.x, (long) geoPoint2.y);
        float[] fArr = new float[1];
        Location.distanceBetween(a.y, a.x, a2.y, a2.x, fArr);
        return 1000.0f / fArr[0];
    }

    @AjxMethod(invokeMode = "sync", value = "getDistanceBetweenCoordinates")
    public final float getDistanceBetweenCoordinates(double d, double d2, double d3, double d4) {
        if (isValidCoordinate(d, d2)) {
            double d5 = d3;
            double d6 = d4;
            if (isValidCoordinate(d5, d6)) {
                float[] fArr = new float[1];
                Location.distanceBetween(d, d2, d5, d6, fArr);
                return fArr[0];
            }
        }
        return -1.0f;
    }

    @AjxMethod(invokeMode = "sync", value = "getDistanceBetweenG20Points")
    public final float getDistanceBetweenG20Points(int i, int i2, int i3, int i4) {
        GeoPoint geoPoint = new GeoPoint(i, i2);
        GeoPoint geoPoint2 = new GeoPoint(i3, i4);
        if (!isValidCoordinate(geoPoint.getLatitude(), geoPoint.getLongitude()) || !isValidCoordinate(geoPoint2.getLatitude(), geoPoint2.getLongitude())) {
            return -1.0f;
        }
        return cfe.a(geoPoint, geoPoint2);
    }

    @AjxMethod("getProvinceAbbreviation")
    public final void getProvinceAbbreviation(JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(getProvinceName(getProvinceCode()));
    }

    private String getProvinceName(String str) {
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        int length = sProvinceCode.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (sProvinceCode[i].equals(str)) {
                str2 = sProvinceNames[i];
                break;
            } else {
                i++;
            }
        }
        return str2;
    }

    /* access modifiers changed from: private */
    public String innerGetLocation(Location location) {
        double d;
        JSONObject jSONObject = new JSONObject();
        GeoPoint geoPoint = new GeoPoint(location.getLongitude(), location.getLatitude());
        try {
            jSONObject.put("latitude", String.format(Locale.ENGLISH, "%.6f", new Object[]{Double.valueOf(geoPoint.getLatitude())}));
            jSONObject.put("longitude", String.format(Locale.ENGLISH, "%.6f", new Object[]{Double.valueOf(geoPoint.getLongitude())}));
            jSONObject.put(DictionaryKeys.CTRLXY_X, geoPoint.x);
            jSONObject.put(DictionaryKeys.CTRLXY_Y, geoPoint.y);
            jSONObject.put("altitude", location.getAltitude());
            jSONObject.put(CameraControllerManager.MY_POILOCATION_ACR, (double) location.getAccuracy());
            jSONObject.put("speed", (double) location.getSpeed());
            jSONObject.put("timestamp", location.getTime());
            jSONObject.put("course", Math.ceil((double) location.getBearing()));
            jSONObject.put("provider", location.getProvider());
            if (location.getProvider().equals(IndoorLocationProvider.NAME)) {
                Bundle extras = location.getExtras();
                if (extras != null) {
                    String string = extras.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                    String string2 = extras.getString("floor");
                    jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, string);
                    jSONObject.put("floor", string2);
                }
            }
            Bundle extras2 = location.getExtras();
            if (extras2 != null) {
                extras2.getSerializable(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_POS);
                jSONObject.put("angle", (double) ((float) extras2.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_ROAD_COURSE, -1.0d)));
                jSONObject.put("sloc_speed", (double) location.getSpeed());
                jSONObject.put("angle_type", String.valueOf(extras2.getInt(LocationInstrument.LOCATION_EXTRAS_KEY_COURSE_TYPE, -1)));
                jSONObject.put("angle_gps", String.valueOf(extras2.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_GPS_COURSE, -1.0d)));
                jSONObject.put("angle_comp", String.valueOf(extras2.getDouble(LocationInstrument.LOCATION_EXTRAS_KEY_COMPASS_COURSE, -1.0d)));
                jSONObject.put("angle_radius", String.valueOf(extras2.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_ERROR_DIST, -1.0f)));
                jSONObject.put("angle_sigtype", String.valueOf(extras2.getInt(LocationInstrument.LOCATION_EXTRAS_KEY_MATCH_POS_TYPE, 0)));
                jSONObject.put("gps_cre", String.valueOf(extras2.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_GPS_COURE_ACC, 0.0f)));
                jSONObject.put("angle_fittingdir", String.valueOf(extras2.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_FITTING_COURSE, -1.0f)));
                jSONObject.put("fitting_cre", String.valueOf(extras2.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_FITTING_COURSE_ACC, 0.0f)));
                jSONObject.put("angle_matchingdir", String.valueOf(extras2.getFloat(LocationInstrument.LOCATION_EXTRAS_KEY_ROAD_COURSE, -1.0f)));
            }
            jSONObject.put("sloc_precision", String.valueOf(location.getAccuracy()));
            if (LocationInstrument.getInstance().getLocInfo() == null) {
                d = 0.0d;
            } else {
                d = (double) ((float) LocationInstrument.getInstance().getLocInfo().courseAcc);
            }
            jSONObject.put("credibility", d);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    @AjxMethod("locationEnable")
    public final void locationEnable(JsFunctionCallback jsFunctionCallback) {
        boolean z = true;
        Object[] objArr = new Object[1];
        if (LocationInstrument.getInstance().getLatestPosition(1) == null) {
            z = false;
        }
        objArr[0] = Boolean.valueOf(z);
        jsFunctionCallback.callback(objArr);
    }

    @AjxMethod("gpsEnable")
    public final void gpsEnable(JsFunctionCallback jsFunctionCallback) {
        boolean z = true;
        Object[] objArr = new Object[1];
        if (LocationInstrument.getInstance().getLatestPosition(1) == null) {
            z = false;
        }
        objArr[0] = Boolean.valueOf(z);
        jsFunctionCallback.callback(objArr);
    }

    @AjxMethod("getGpsSwitchState")
    @Deprecated
    public final void getGPSOpenState(JsFunctionCallback jsFunctionCallback) {
        boolean checkHasGps = checkHasGps(getNativeContext());
        if (checkHasGps && agf.a()) {
            checkHasGps = agf.a(getNativeContext());
        }
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(checkHasGps));
        }
    }

    @AjxMethod("isGpsOn")
    public final void isGpsOn(JsFunctionCallback jsFunctionCallback) {
        boolean checkHasGps = checkHasGps(getNativeContext());
        if (checkHasGps && agf.a()) {
            checkHasGps = agf.a(getNativeContext());
        }
        if (jsFunctionCallback != null) {
            jsFunctionCallback.callback(Boolean.valueOf(checkHasGps));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "isGpsOnSync")
    public final boolean isGpsOnSync() {
        boolean checkHasGps = checkHasGps(getNativeContext());
        return (!checkHasGps || !agf.a()) ? checkHasGps : agf.a(getNativeContext());
    }

    @AjxMethod("openGpsSetting")
    public final void openGpsSetting() {
        Context nativeContext = getNativeContext();
        if (nativeContext != null) {
            try {
                Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                intent.setFlags(268435456);
                nativeContext.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                if (agf.a()) {
                    try {
                        Intent intent2 = new Intent("miui.intent.action.APP_PERM_EDITOR");
                        intent2.setFlags(MapCustomizeManager.VIEW_SEARCH_ALONG);
                        intent2.putExtra("extra_pkgname", "com.autonavi.minimap");
                        nativeContext.startActivity(intent2);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        ToastHelper.showToast(nativeContext.getString(R.string.msg_open_setting_failed));
                    }
                } else {
                    ToastHelper.showLongToast(getNativeContext().getString(R.string.msg_open_setting_failed));
                }
            }
        }
    }

    @AjxMethod("setLocationFailed")
    public final void setLocationFailed(JsFunctionCallback jsFunctionCallback) {
        if (this.mLocationChangeListener == null) {
            this.mLocationChangeListener = new LocationChangeListener(this);
            LocationInstrument.getInstance().addStatusCallback(this.mLocationChangeListener, null);
        }
        this.mLocationChangeListener.setFailJsRef(jsFunctionCallback);
    }

    @AjxMethod("setLocationChanged")
    public final void setLocationChanged(JsFunctionCallback jsFunctionCallback) {
        if (this.mLocationChangeListener == null) {
            this.mLocationChangeListener = new LocationChangeListener(this);
            LocationInstrument.getInstance().addStatusCallback(this.mLocationChangeListener, null);
        }
        this.mLocationChangeListener.setSuccessJsRef(jsFunctionCallback);
    }

    @AjxMethod("setCustomLocationChanged")
    public final void setCustomLocationChanged(float f, long j, boolean z, JsFunctionCallback jsFunctionCallback) {
        if (this.mCustomLocationChangeListener == null) {
            CustomLocationChangeListener customLocationChangeListener = new CustomLocationChangeListener(f, j, z, this);
            this.mCustomLocationChangeListener = customLocationChangeListener;
            LocationInstrument.getInstance().addStatusCallback(this.mCustomLocationChangeListener, null);
        }
        this.mCustomLocationChangeListener.setSuccessJsRef(jsFunctionCallback);
    }

    @AjxMethod("getCityInfoByAdcode")
    public final void getCityInfoByAdcode(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            try {
                invokeCityInfo(li.a().a((int) Long.parseLong(str)), jsFunctionCallback);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod("getCityInfoByCoordinate")
    public final void getCityByCoordinate(double d, double d2, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            invokeCityInfo(li.a().b(d, d2), jsFunctionCallback);
        }
    }

    @AjxMethod("getLocationCityInfo")
    public final void getLocationCityInfo(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            invokeCityInfo(li.a().b(LocationInstrument.getInstance().getLatestPosition().getLongitude(), LocationInstrument.getInstance().getLatestPosition().getLatitude()), jsFunctionCallback);
        }
    }

    @AjxMethod("getMapCenterCityInfo")
    public final void getMapCenterCityInfo(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager == null) {
                jsFunctionCallback.callback(new Object[0]);
            } else {
                invokeCityInfo(AppManager.getInstance().getMapCenterCityInfo(mapManager.getMapView()), jsFunctionCallback);
            }
        }
    }

    private void invokeCityInfo(lj ljVar, JsFunctionCallback jsFunctionCallback) {
        if (ljVar == null) {
            jsFunctionCallback.callback(new Object[0]);
            return;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("cityPinyin", ljVar.b);
            jSONObject.put("identity", ljVar.j);
            jSONObject.put("cityCoordX", String.valueOf(ljVar.f));
            jSONObject.put("cityName", ljVar.a);
            jSONObject.put("province", ljVar.e);
            jSONObject.put("cityInitLetters", ljVar.c);
            jSONObject.put("cityCode", ljVar.i);
            jSONObject.put(H5PermissionManager.level, String.valueOf(ljVar.h));
            jSONObject.put("cityCoordY", String.valueOf(ljVar.g));
            jSONObject.put("cityInitLetter", ljVar.c != null ? Character.valueOf(ljVar.c.charAt(0)) : null);
            jSONObject.put("adCode", ljVar.j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsFunctionCallback.callback(jSONObject);
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        if (this.mLocationChangeListener != null) {
            LocationInstrument.getInstance().removeStatusCallback(this.mLocationChangeListener);
        }
        if (this.mCustomLocationChangeListener != null) {
            LocationInstrument.getInstance().removeStatusCallback(this.mCustomLocationChangeListener);
        }
        super.finalize();
    }

    public final void destroy() {
        if (this.mLocationChangeListener != null) {
            LocationInstrument.getInstance().removeStatusCallback(this.mLocationChangeListener);
            this.mLocationChangeListener = null;
        }
        if (this.mCustomLocationChangeListener != null) {
            LocationInstrument.getInstance().removeStatusCallback(this.mCustomLocationChangeListener);
            this.mCustomLocationChangeListener = null;
        }
    }

    public final void onModuleDestroy() {
        super.onModuleDestroy();
        destroy();
    }

    private boolean checkHasGps(Context context) {
        boolean z;
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (locationManager == null) {
            return false;
        }
        try {
            List<String> allProviders = locationManager.getAllProviders();
            z = allProviders != null ? allProviders.contains(WidgetType.GPS) : false;
            if (z && VERSION.SDK_INT >= 19) {
                int i = Secure.getInt(context.getContentResolver(), "location_mode", 0);
                if (i == 0) {
                    return false;
                }
                if (!(i == 3 || i == 1)) {
                    return false;
                }
            }
            if (!locationManager.isProviderEnabled(WidgetType.GPS)) {
                return false;
            }
        } catch (Exception unused) {
            z = false;
        }
        return z;
    }

    @AjxMethod("getGeoInfoByGPS")
    public final void getGeoInfoByGPS(String str, JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                GeoPoint geoPoint = new GeoPoint(jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON), jSONObject.optDouble("lat"));
                ReverseCallback reverseCallback = new ReverseCallback(jsFunctionCallback);
                ReverseGeocodeWrapper reverseGeocodeWrapper = new ReverseGeocodeWrapper();
                reverseGeocodeWrapper.lat = String.valueOf(geoPoint.getLatitude());
                reverseGeocodeWrapper.lon = String.valueOf(geoPoint.getLongitude());
                AosGetRequest a = aax.a(reverseGeocodeWrapper);
                yq.a();
                yq.a((AosRequest) a, (AosResponseCallback<T>) reverseCallback);
            } catch (JSONException unused) {
                jsFunctionCallback.callback("");
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "isLocated")
    public final boolean isLocationSuccess() {
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        if (latestLocation == null || latestLocation.getLatitude() == 0.0d || latestLocation.getLongitude() == 0.0d) {
            return false;
        }
        return true;
    }

    @AjxMethod("getLocationIndoorFloorIndex")
    public final void getLocationIndoorFloorIndex(String str, JsFunctionCallback jsFunctionCallback) {
        this.mGetLocationIndoorFloorIndexCallBack = jsFunctionCallback;
        if (!TextUtils.isEmpty(str)) {
            try {
                caculatelocationIndoorIndex(new JSONObject(str).optString("startName"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @AjxMethod("isGPSLost")
    public final void isGPSLost(JsFunctionCallback jsFunctionCallback) {
        this.mGPSWeakCallback = jsFunctionCallback;
        if (this.mGPSWeakCallback != null) {
            LocationInstrument.getInstance().addOriginalLocation(this.originalLocationCallback);
        } else {
            LocationInstrument.getInstance().removeOriginalLocation(this.originalLocationCallback);
        }
    }

    public final void setLocationIndoorFloorForAJX(String str) {
        if (this.mGetLocationIndoorFloorIndexCallBack != null) {
            this.mGetLocationIndoorFloorIndexCallBack.callback(str);
        }
    }

    private void caculatelocationIndoorIndex(String str) {
        JSONObject jSONObject = new JSONObject();
        if (str.equals("我的位置")) {
            Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
            if (latestLocation != null && latestLocation.getProvider().equals(IndoorLocationProvider.NAME)) {
                Bundle extras = latestLocation.getExtras();
                if (extras != null) {
                    String string = extras.getString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
                    String string2 = extras.getString("floor");
                    if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                        try {
                            jSONObject.put("pid", string);
                            jSONObject.put("locFloor", string2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        setLocationIndoorFloorForAJX(jSONObject.toString());
    }

    @AjxMethod(invokeMode = "sync", value = "getLatestLocations")
    public final String getLatestLocations() {
        JSONArray jSONArray = new JSONArray();
        dfl[] recentGPS = getRecentGPS(30, 4);
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
        if (recentGPS != null) {
            try {
                if (recentGPS.length > 0 && recentGPS[recentGPS.length - 1] != null) {
                    for (dfl dfl : recentGPS) {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("latitude", dfl.b);
                        jSONObject.put("longitude", dfl.a);
                        jSONObject.put(DictionaryKeys.CTRLXY_X, dfl.a);
                        jSONObject.put(DictionaryKeys.CTRLXY_Y, dfl.b);
                        int i = -1;
                        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
                        if (latestLocation != null) {
                            i = (int) latestLocation.getAccuracy();
                        }
                        jSONObject.put(CameraControllerManager.MY_POILOCATION_ACR, i);
                        jSONObject.put("speed", dfl.c);
                        Calendar instance = Calendar.getInstance();
                        instance.set(dfl.e, dfl.f, dfl.g, dfl.h, dfl.i, dfl.j);
                        jSONObject.put("timestamp", instance.getTimeInMillis());
                        jSONArray.put(jSONObject);
                    }
                    StringBuilder sb = new StringBuilder("daihq    getLatestLocationWithMaxCount    ");
                    sb.append(jSONArray.toString());
                    AMapLog.i("ModuleLocation", sb.toString());
                    return jSONArray.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONObject jSONObject2 = new JSONObject();
        if (latestPosition != null) {
            jSONObject2.put("latitude", String.format(Locale.ENGLISH, "%.6f", new Object[]{Double.valueOf(latestPosition.getLatitude())}));
            jSONObject2.put("longitude", String.format(Locale.ENGLISH, "%.6f", new Object[]{Double.valueOf(latestPosition.getLongitude())}));
            jSONObject2.put(DictionaryKeys.CTRLXY_X, latestPosition.x);
            jSONObject2.put(DictionaryKeys.CTRLXY_Y, latestPosition.y);
        }
        jSONObject2.put("timestamp", Calendar.getInstance().getTimeInMillis());
        jSONArray.put(jSONObject2);
        StringBuilder sb2 = new StringBuilder("daihq    getLatestLocationWithMaxCount    ");
        sb2.append(jSONArray.toString());
        AMapLog.i("ModuleLocation", sb2.toString());
        return jSONArray.toString();
    }

    @AjxMethod("getAdcode")
    public final void getAdcode(JsFunctionCallback jsFunctionCallback) {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        StringBuilder sb = new StringBuilder();
        sb.append(latestPosition.getAdCode());
        jsFunctionCallback.callback(sb.toString());
    }

    @AjxMethod("getCityCode")
    public final void getCityCode(JsFunctionCallback jsFunctionCallback) {
        String str;
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        li a = li.a();
        if (a != null) {
            lj b = a.b(latestPosition.x, latestPosition.y);
            if (b != null) {
                str = b.i;
                jsFunctionCallback.callback(str);
            }
        }
        str = null;
        jsFunctionCallback.callback(str);
    }

    @AjxMethod("getLocation")
    public final void getLocation(JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(innerGetLocation(LocationInstrument.getInstance().getLatestLocation()));
    }

    @AjxMethod("getLatestLocation")
    public final void getLatestLocation(JsFunctionCallback jsFunctionCallback) {
        if (LocationInstrument.getInstance().getCacheLatestPosition() != null) {
            jsFunctionCallback.callback(innerGetLocation(LocationInstrument.getInstance().getLatestLocation()));
            return;
        }
        jsFunctionCallback.callback("");
    }

    private String getProvinceCode() {
        String valueOf = String.valueOf((long) LocationInstrument.getInstance().getLatestPosition().getAdCode());
        return (TextUtils.isEmpty(valueOf) || valueOf.length() < 2) ? "" : valueOf.substring(0, 2);
    }

    @AjxMethod(invokeMode = "sync", value = "syncGetLatestLocation")
    public final String syncGetLatestLocation() {
        return LocationInstrument.getInstance().getCacheLatestPosition() != null ? innerGetLocation(LocationInstrument.getInstance().getLatestLocation()) : "";
    }

    private dfl[] getRecentGPS(int i, int i2) {
        try {
            List<Location> latestGpsLocations = LocationInstrument.getInstance().getLatestGpsLocations();
            if (latestGpsLocations != null && latestGpsLocations.size() > 0) {
                dfl[] dflArr = new dfl[latestGpsLocations.size()];
                for (int i3 = 0; i3 < latestGpsLocations.size(); i3++) {
                    Location location = latestGpsLocations.get(i3);
                    dflArr[i3] = new dfl();
                    dflArr[i3].b = location.getLatitude();
                    dflArr[i3].a = location.getLongitude();
                    dflArr[i3].c = (double) location.getSpeed();
                    dflArr[i3].d = location.getBearing();
                    Calendar instance = Calendar.getInstance();
                    instance.setTimeInMillis(location.getTime());
                    dflArr[i3].e = instance.get(1);
                    dflArr[i3].f = instance.get(2) + 1;
                    dflArr[i3].g = instance.get(5);
                    dflArr[i3].h = instance.get(11);
                    dflArr[i3].i = instance.get(12);
                    dflArr[i3].j = instance.get(13);
                }
                return dflArr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
