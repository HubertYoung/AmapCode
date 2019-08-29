package com.autonavi.bundle.uitemplate.mapwidget.widget.search;

import android.graphics.Color;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.parse.WeatherRestrict;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.controller.AppManager;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

public class NearBySearchWidgetPresenter extends BaseMapWidgetPresenter<NearBySearchMapWidget> {
    private final int MIN_INTERVAL_TIME = 500;
    protected StringBuffer mContextDes;
    private bea mFloorWidgetController;
    private bei mFloorWidgetListener = new bei() {
        public void onIndoorChanged(boolean z, ami ami) {
            NearBySearchWidgetPresenter.this.updateVisibility();
        }
    };
    private long mLastTime;
    private String mNearbyDataCache = "";

    public void initialize(NearBySearchMapWidget nearBySearchMapWidget) {
        super.initialize(nearBySearchMapWidget);
        bec bec = (bec) ank.a(bec.class);
        if (bec != null) {
            this.mFloorWidgetController = bec.a();
            if (this.mFloorWidgetController != null) {
                this.mFloorWidgetController.a(this.mFloorWidgetListener);
            }
        }
        updateVisibility();
    }

    public void unInitialize() {
        if (this.mFloorWidgetController != null) {
            this.mFloorWidgetController.b(this.mFloorWidgetListener);
        }
    }

    public void internalClickListener(View view) {
        if (System.currentTimeMillis() - this.mLastTime >= 500) {
            this.mLastTime = System.currentTimeMillis();
            String str = "";
            INearbyEventDelegate iNearbyEventDelegate = (INearbyEventDelegate) getEventDelegate();
            if (iNearbyEventDelegate != null) {
                str = iNearbyEventDelegate.getFeedGrayAjxPath();
            }
            int zoomLevel = getZoomLevel();
            bty mapView = getMapView();
            if (mapView != null) {
                JSONObject generateNewFeedData = generateNewFeedData(zoomLevel, mapView.H(), mapView.n());
                PageBundle pageBundle = getPageBundle(str, getLogVersionStateParam(), generateNewFeedData);
                if (iNearbyEventDelegate != null) {
                    iNearbyEventDelegate.startFeedAjx3Page(AMapPageUtil.getPageContext(), pageBundle);
                }
                updateLog(generateNewFeedData);
            }
        }
    }

    public static int getZoomLevel() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            bty f = iMainMapService.f();
            if (f != null) {
                return f.w();
            }
        }
        return -1;
    }

    public static PageBundle getPageBundle(String str, JSONObject jSONObject, JSONObject jSONObject2) {
        String str2;
        logGoNearby(jSONObject);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", str);
        pageBundle.putInt(Ajx3Page.PAGE_LOADING_TYPE, 1);
        if (jSONObject2 == null) {
            str2 = "";
        } else {
            str2 = jSONObject2.toString();
        }
        pageBundle.putString("jsData", str2);
        return pageBundle;
    }

    public static void updateLog(JSONObject jSONObject) {
        try {
            jSONObject.put("type", 1);
            LogManager.actionLogV2("P00386", "B002", jSONObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void logGoNearby(JSONObject jSONObject) {
        LogManager.actionLogV2("P00001", "B007", jSONObject);
    }

    public static JSONObject generateNewFeedData(int i, Rect rect, GLGeoPoint gLGeoPoint) {
        JSONObject jSONObject = new JSONObject();
        try {
            StringBuffer stringBuffer = new StringBuffer();
            if (rect != null) {
                DPoint a = bey.a((long) rect.left, (long) rect.top);
                DPoint a2 = bey.a((long) rect.right, (long) rect.bottom);
                stringBuffer.append(a.x);
                stringBuffer.append(MergeUtil.SEPARATOR_KV);
                stringBuffer.append(a.y);
                stringBuffer.append(MergeUtil.SEPARATOR_KV);
                stringBuffer.append(a2.x);
                stringBuffer.append(MergeUtil.SEPARATOR_KV);
                stringBuffer.append(a2.y);
            }
            jSONObject.put("geoobj", stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            jSONObject.put("user_loc", AppManager.getInstance().getUserLocInfo());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (i >= 0) {
            try {
                jSONObject.put(H5PermissionManager.level, String.valueOf(i));
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        GeoPoint geoPoint = new GeoPoint(gLGeoPoint);
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        double latitude = geoPoint.getLatitude();
        double longitude = geoPoint.getLongitude();
        double latitude2 = latestPosition.getLatitude();
        double longitude2 = latestPosition.getLongitude();
        double d = 0.0d;
        boolean z = true;
        if (longitude > 0.0d && latitude > 0.0d && longitude2 > 0.0d && latitude2 > 0.0d) {
            float[] fArr = new float[1];
            beu.a(longitude, latitude, longitude2, latitude2, fArr);
            d = (double) fArr[0];
        }
        if (d <= 1000.0d) {
            z = false;
        }
        try {
            jSONObject.put("is_show_switchloc", z);
        } catch (JSONException e4) {
            e4.printStackTrace();
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(geoPoint.getLongitude());
            sb.append(",");
            sb.append(geoPoint.getLatitude());
            jSONObject.put("mapCenter", sb.toString());
        } catch (JSONException e5) {
            e5.printStackTrace();
        }
        return jSONObject;
    }

    public void responseData(String str) {
        if (str != null || "".equals(this.mNearbyDataCache)) {
            this.mNearbyDataCache = str;
        } else {
            str = this.mNearbyDataCache;
        }
        if (this.mBindWidget != null) {
            freshState(WeatherRestrict.createInstance(str));
        }
    }

    public void freshState(WeatherRestrict weatherRestrict) {
        int i;
        this.mContextDes = new StringBuffer("探索附近");
        if (this.mBindWidget != null) {
            if (weatherRestrict == null) {
                ((NearBySearchMapWidget) this.mBindWidget).setBottomRegionVisibility(8);
                ((NearBySearchMapWidget) this.mBindWidget).getContentView().setContentDescription(this.mContextDes.toString());
                return;
            }
            boolean z = false;
            ((NearBySearchMapWidget) this.mBindWidget).setBottomRegionVisibility(0);
            if (weatherRestrict.isHasTrafficRestrict()) {
                ((NearBySearchMapWidget) this.mBindWidget).setRestrictLabelVisibility(0);
                StringBuilder sb = new StringBuilder();
                String str = null;
                if (weatherRestrict.isTodayNotRestrict()) {
                    sb.append("今日不限行");
                    this.mContextDes.append("今日不限行");
                } else {
                    str = weatherRestrict.plate_no.replace(",", "");
                    sb.append(str);
                    sb.append("限行");
                    StringBuffer stringBuffer = this.mContextDes;
                    stringBuffer.append(str);
                    stringBuffer.append("限行");
                }
                if (str == null) {
                    i = 0;
                } else {
                    i = str.length();
                }
                if (i > 3 && i <= 5) {
                    z = true;
                }
                if (!weatherRestrict.isHasWeatherState() || z) {
                    ((NearBySearchMapWidget) this.mBindWidget).setWeatherContainerVisibility(8);
                } else {
                    setWeatherTemperature(weatherRestrict.image_url, weatherRestrict.temperature);
                    StringBuffer stringBuffer2 = this.mContextDes;
                    stringBuffer2.append(weatherRestrict.temperature);
                    stringBuffer2.append("度");
                }
                dealWithPlateNo(sb.toString(), str, 1.3f);
                ((NearBySearchMapWidget) this.mBindWidget).getContentView().setContentDescription(this.mContextDes.toString());
                return;
            }
            ((NearBySearchMapWidget) this.mBindWidget).setRestrictLabelVisibility(8);
            if (weatherRestrict.isHasWeatherState()) {
                ((NearBySearchMapWidget) this.mBindWidget).setWeatherIcon(weatherRestrict.image_url);
                z = true;
            }
            if (!weatherRestrict.isHaveTempHighAndLow() || !z) {
                ((NearBySearchMapWidget) this.mBindWidget).setBottomRegionVisibility(8);
                return;
            }
            ((NearBySearchMapWidget) this.mBindWidget).setWeatherLabel(weatherRestrict.getTempHighAndLow());
            this.mContextDes.append(weatherRestrict.getTempHighAndLow());
            ((NearBySearchMapWidget) this.mBindWidget).getContentView().setContentDescription(this.mContextDes.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void setWeatherTemperature(String str, String str2) {
        ((NearBySearchMapWidget) this.mBindWidget).setWeatherContainerVisibility(0);
        ((NearBySearchMapWidget) this.mBindWidget).setWeatherIcon(str);
        StringBuilder sb = new StringBuilder(str2);
        sb.append("°");
        ((NearBySearchMapWidget) this.mBindWidget).setWeatherLabel(sb);
    }

    /* access modifiers changed from: protected */
    public void dealWithPlateNo(String str, String str2, float f) {
        int indexOf = TextUtils.isEmpty(str2) ? -1 : str.indexOf(str2);
        if (-1 != indexOf) {
            ((NearBySearchMapWidget) this.mBindWidget).setRestrictLabel(doPlateNo(str, indexOf, str2.length(), f));
        } else {
            ((NearBySearchMapWidget) this.mBindWidget).setRestrictLabel(str);
        }
    }

    private SpannableString doPlateNo(CharSequence charSequence, int i, int i2, float f) {
        SpannableString spannableString = new SpannableString(charSequence);
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = i + i3;
            int i5 = i4 + 1;
            spannableString.setSpan(new RelativeSizeSpan(f), i4, i5, 33);
            spannableString.setSpan(new bex(Color.parseColor("#888db4"), false), i4, i5, 33);
        }
        return spannableString;
    }

    public static bty getMapView() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            return iMainMapService.g();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void updateVisibility() {
        if (getWidget() != null && ((NearBySearchMapWidget) getWidget()).getWidgetProperty().isLoadNewWidgetStyle() && this.mFloorWidgetController != null) {
            ((NearBySearchMapWidget) getWidget()).getContentView().setVisibility(this.mFloorWidgetController.b() ? 8 : 0);
        }
    }
}
