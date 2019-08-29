package com.autonavi.miniapp.plugin.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.mobile.beehive.imageedit.service.ImageEditService;
import com.alipay.mobile.framework.locale.LocaleHelper;
import com.alipay.mobile.h5container.api.H5Bridge;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.appcenter.api.H5ContentProvider.ResponseListen;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.view.IH5EmbedView;
import com.alipay.mobile.nebula.view.IH5EmbedViewJSCallback;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.drive.ajx.module.ModuleHeadunitImpl;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView.OnFloorChangeListener;
import com.autonavi.miniapp.plugin.map.action.BaseActionProcessor;
import com.autonavi.miniapp.plugin.map.action.ClearRouteActionProcessor;
import com.autonavi.miniapp.plugin.map.action.GestureEnableActionProcessor;
import com.autonavi.miniapp.plugin.map.action.GetCenterLocationActionProcessor;
import com.autonavi.miniapp.plugin.map.action.GetRegionActionProcessor;
import com.autonavi.miniapp.plugin.map.action.GetScaleProcessor;
import com.autonavi.miniapp.plugin.map.action.MapToScreenActionProcessor;
import com.autonavi.miniapp.plugin.map.action.MoveToLocationActionProcessor;
import com.autonavi.miniapp.plugin.map.action.RoutePlanActionProcessor;
import com.autonavi.miniapp.plugin.map.action.ScreenToMapActionProcessor;
import com.autonavi.miniapp.plugin.map.action.ShowCompassActionProcessor;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.miniapp.plugin.map.action.ShowScaleActionProcessor;
import com.autonavi.miniapp.plugin.map.action.ShowTileOverlayActionProcessor;
import com.autonavi.miniapp.plugin.map.action.TranslateMarkerActionProcessor;
import com.autonavi.miniapp.plugin.map.action.UpdateComponentsActionProcessor;
import com.autonavi.miniapp.plugin.map.action.UpdateIndoorMapActionProcessor;
import com.autonavi.miniapp.plugin.map.indoor.MiniAppFloorManager;
import com.autonavi.miniapp.plugin.map.property.BasePropertyProcessor;
import com.autonavi.miniapp.plugin.map.property.CirclesPropertyProcessor;
import com.autonavi.miniapp.plugin.map.property.CirclesPropertyProcessor.Circle;
import com.autonavi.miniapp.plugin.map.property.ControlPropertyProcessor;
import com.autonavi.miniapp.plugin.map.property.ControlPropertyProcessor.Control;
import com.autonavi.miniapp.plugin.map.property.GroundOverlaysPropertyProcessor;
import com.autonavi.miniapp.plugin.map.property.GroundOverlaysPropertyProcessor.GroundOverlay;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Command;
import com.autonavi.miniapp.plugin.map.property.MarkerPropertyProcessor.Marker;
import com.autonavi.miniapp.plugin.map.property.MiscPropertyProcssor;
import com.autonavi.miniapp.plugin.map.property.MiscPropertyProcssor.IncludePadding;
import com.autonavi.miniapp.plugin.map.property.MiscPropertyProcssor.MapSetting;
import com.autonavi.miniapp.plugin.map.property.Point;
import com.autonavi.miniapp.plugin.map.property.PolygonPropertyProcessor;
import com.autonavi.miniapp.plugin.map.property.PolygonPropertyProcessor.Polygon;
import com.autonavi.miniapp.plugin.map.property.PolylinePropertyProcessor;
import com.autonavi.miniapp.plugin.map.property.PolylinePropertyProcessor.Polyline;
import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteManager;
import com.autonavi.miniapp.plugin.map.util.ElementProvider;
import com.autonavi.miniapp.plugin.map.util.EmbedMapOfflineImageLoader;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class AMapH5EmbedMapView implements IH5EmbedView {
    public static final double INVALID_LATLON = -1000.0d;
    public static final float INVALID_SCALE = -1.0f;
    public static final double MIN_DIFF_ANGLE = 0.01d;
    public static final double MIN_DIFF_DEGREE = 0.01d;
    public static final double MIN_DIFF_LATLON = 1.0E-6d;
    public static final double MIN_DIFF_SCALE = 0.01d;
    public static final String TAG = "AMapH5EmbedMapView";
    /* access modifiers changed from: private */
    public boolean callOnPoiTap;
    private List<BaseActionProcessor> mActionProcessors = new ArrayList();
    private CirclesPropertyProcessor mCirclesProcessor;
    private ClearRouteActionProcessor mClearRouteActionProcessor;
    /* access modifiers changed from: private */
    public WeakReference<Context> mContext;
    private ControlPropertyProcessor mControlProcessor;
    /* access modifiers changed from: private */
    public ElementProvider mElementProvider = new ElementProvider();
    private GestureEnableActionProcessor mGestureEnableActionProcessor;
    private GetCenterLocationActionProcessor mGetCenterLocationActionProcessor;
    private GetRegionActionProcessor mGetRegionActionProcessor;
    private GetScaleProcessor mGetScaleProcessor;
    private GroundOverlaysPropertyProcessor mGroundOverlaysProcessor;
    private amk mMapListener = new SimpleMapListener() {
        /* access modifiers changed from: private */
        public boolean isCalledEnd = true;
        /* access modifiers changed from: private */
        public boolean isChanging = false;
        /* access modifiers changed from: private */
        public boolean isFrameDrawn = false;
        /* access modifiers changed from: private */
        public boolean isIgnoreDrawFrameCallback = false;
        /* access modifiers changed from: private */
        public boolean isInitialChange = true;
        /* access modifiers changed from: private */
        public boolean isTouching = false;
        float mLastCameraDegree;
        double mLastCenterLat = -1000.0d;
        double mLastCenterLon = -1000.0d;
        float mLastMapAngle;
        float mLastScale = -1.0f;

        public void onUserMapTouchEvent(int i, MotionEvent motionEvent) {
            boolean z = true;
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                z = false;
            }
            this.isTouching = z;
        }

        public boolean onSingleTapUp(int i, MotionEvent motionEvent) {
            H5Page h5Page = (H5Page) AMapH5EmbedMapView.this.mPage.get();
            if (h5Page == null || !AMapH5EmbedMapView.this.hasValidMapView()) {
                return false;
            }
            AMapH5EmbedMapView.this.mRealView.getPointOverlay().clearCalloutItem();
            H5Bridge bridge = h5Page.getBridge();
            if (bridge == null || (AMapH5EmbedMapView.this.callOnPoiTap && handlePoiClick(motionEvent, bridge))) {
                return false;
            }
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "onTapClick");
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (y < AMapH5EmbedMapView.this.mRealView.getMap().f().getWinSkyHeight()) {
                jSONObject2.put((String) "latitude", (Object) Double.valueOf(-180.0d));
                jSONObject2.put((String) "longitude", (Object) Double.valueOf(-180.0d));
                jSONObject2.put((String) "element", (Object) AMapH5EmbedMapView.this.mElementProvider.getElement());
                jSONObject.put((String) "data", (Object) jSONObject2);
                bridge.sendToWeb("nbcomponent.map.bindtap", jSONObject, null);
            } else {
                GeoPointHD geoPointHD = new GeoPointHD(AMapH5EmbedMapView.this.mRealView.getMap().c((int) x, (int) y));
                DPoint a = cfg.a((long) geoPointHD.x, (long) geoPointHD.y);
                jSONObject2.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(a.y)));
                jSONObject2.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(a.x)));
                jSONObject2.put((String) "element", (Object) AMapH5EmbedMapView.this.mElementProvider.getElement());
                jSONObject.put((String) "data", (Object) jSONObject2);
                bridge.sendToWeb("nbcomponent.map.bindtap", jSONObject, null);
            }
            return false;
        }

        private boolean handlePoiClick(MotionEvent motionEvent, H5Bridge h5Bridge) {
            ArrayList<als> d = AMapH5EmbedMapView.this.mRealView.getMap().d((int) motionEvent.getX(), (int) motionEvent.getY());
            if (d == null || d.isEmpty()) {
                return false;
            }
            als als = d.get(0);
            GeoPointHD geoPointHD = new GeoPointHD(als.e, als.f);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(geoPointHD.getLatitude())));
            jSONObject.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(geoPointHD.getLongitude())));
            jSONObject.put((String) "name", (Object) als.a);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put((String) "data", (Object) jSONObject);
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "bindpoitap, event: ".concat(String.valueOf(jSONObject2)));
            h5Bridge.sendToWeb("nbcomponent.map.bindpoitap", jSONObject2, null);
            return true;
        }

        public void onMapSizeChange(int i) {
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "onMapSizeChange: ".concat(String.valueOf(i)));
        }

        public void onMapLevelChange(int i, boolean z) {
            StringBuilder sb = new StringBuilder("onMapLevelChange: ");
            sb.append(i);
            sb.append(Token.SEPARATOR);
            sb.append(z);
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
        }

        public void afterDrawFrame(int i, GLMapState gLMapState) {
            if (AMapH5EmbedMapView.this.miniAppMapLock.tryLock()) {
                try {
                    if (!(AMapH5EmbedMapView.this.mRealView == null || AMapH5EmbedMapView.this.mRealView.getPointOverlay() == null)) {
                        AMapH5EmbedMapView.this.mRealView.getPointOverlay().renderFixedPoint();
                    }
                } finally {
                    AMapH5EmbedMapView.this.miniAppMapLock.unlock();
                }
            }
            AMapH5EmbedMapView.this.mainHandler.post(new Runnable() {
                public void run() {
                    if (AMapH5EmbedMapView.this.hasValidMapView() && !AnonymousClass2.this.isIgnoreDrawFrameCallback) {
                        boolean z = false;
                        if (AnonymousClass2.this.isFrameDrawn || AMapH5EmbedMapView.this.mapDataJsonObj == null) {
                            AnonymousClass2.this.isFrameDrawn = true;
                            boolean g = AMapH5EmbedMapView.this.mRealView.getMap().g();
                            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "region Change isAnimating".concat(String.valueOf(g)));
                            GeoPointHD geoPointHD = new GeoPointHD(AMapH5EmbedMapView.this.mRealView.getMap().n());
                            if (new BigDecimal(-1000.0d).compareTo(new BigDecimal(AnonymousClass2.this.mLastCenterLat)) == 0) {
                                AnonymousClass2.this.mLastCenterLat = geoPointHD.getLatitude();
                                AnonymousClass2.this.mLastCenterLon = geoPointHD.getLongitude();
                                AnonymousClass2.this.mLastScale = AMapH5EmbedMapView.this.mRealView.getMap().v();
                                AnonymousClass2.this.mLastCameraDegree = AMapH5EmbedMapView.this.mRealView.getMap().J();
                                AnonymousClass2.this.mLastMapAngle = AMapH5EmbedMapView.this.mRealView.getMap().I();
                                return;
                            }
                            boolean isLatLonEqual = H5MapUtils.isLatLonEqual(geoPointHD.getLatitude(), geoPointHD.getLongitude(), AnonymousClass2.this.mLastCenterLat, AnonymousClass2.this.mLastCenterLon);
                            boolean isScaleEqual = H5MapUtils.isScaleEqual(AMapH5EmbedMapView.this.mRealView.getMap().v(), AnonymousClass2.this.mLastScale);
                            boolean isAngleEqual = H5MapUtils.isAngleEqual(AMapH5EmbedMapView.this.mRealView.getMap().I(), AnonymousClass2.this.mLastMapAngle);
                            boolean isDegreeEqual = H5MapUtils.isDegreeEqual(AMapH5EmbedMapView.this.mRealView.getMap().J(), AnonymousClass2.this.mLastCameraDegree);
                            if (!isLatLonEqual || !isScaleEqual || !isAngleEqual || !isDegreeEqual) {
                                H5Page h5Page = (H5Page) AMapH5EmbedMapView.this.mPage.get();
                                if (h5Page == null) {
                                    AnonymousClass2.this.isChanging = true;
                                    return;
                                }
                                H5Bridge bridge = h5Page.getBridge();
                                if (bridge != null && !AnonymousClass2.this.isChanging) {
                                    if (!AnonymousClass2.this.isInitialChange) {
                                        JSONObject jSONObject = new JSONObject();
                                        JSONObject jSONObject2 = new JSONObject();
                                        jSONObject2.put((String) "regionChangedType", (Object) "begin");
                                        jSONObject2.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(geoPointHD.getLatitude())));
                                        jSONObject2.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(geoPointHD.getLongitude())));
                                        jSONObject2.put((String) WidgetType.SCALE, (Object) Double.valueOf(H5MapUtils.convertScale((double) AMapH5EmbedMapView.this.mRealView.getMap().v())));
                                        jSONObject2.put((String) "skew", (Object) Double.valueOf(H5MapUtils.convertDegree((double) AMapH5EmbedMapView.this.mRealView.getMap().J())));
                                        jSONObject2.put((String) ImageEditService.IN_EDIT_TYPE_ROTATE, (Object) Double.valueOf(H5MapUtils.convertAngle((double) AMapH5EmbedMapView.this.mRealView.getMap().I())));
                                        jSONObject2.put((String) "element", (Object) AMapH5EmbedMapView.this.mElementProvider.getElement());
                                        jSONObject.put((String) "data", (Object) jSONObject2);
                                        bridge.sendToWeb("nbcomponent.map.bindregionchange", jSONObject, null);
                                        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "region Change begin, data: ".concat(String.valueOf(jSONObject2)));
                                    } else {
                                        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "initial region Change begin ignored");
                                    }
                                    AnonymousClass2.this.isCalledEnd = false;
                                }
                                AnonymousClass2.this.isChanging = true;
                            } else if (!AnonymousClass2.this.isTouching && !AnonymousClass2.this.isCalledEnd && !g) {
                                AnonymousClass2.this.isChanging = false;
                                H5Page h5Page2 = (H5Page) AMapH5EmbedMapView.this.mPage.get();
                                if (h5Page2 != null) {
                                    H5Bridge bridge2 = h5Page2.getBridge();
                                    if (bridge2 != null) {
                                        if (!AnonymousClass2.this.isInitialChange) {
                                            JSONObject jSONObject3 = new JSONObject();
                                            JSONObject jSONObject4 = new JSONObject();
                                            jSONObject4.put((String) "regionChangedType", (Object) "end");
                                            jSONObject4.put((String) "latitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(geoPointHD.getLatitude())));
                                            jSONObject4.put((String) "longitude", (Object) Double.valueOf(H5MapUtils.convertLatLon(geoPointHD.getLongitude())));
                                            jSONObject4.put((String) WidgetType.SCALE, (Object) Double.valueOf(H5MapUtils.convertScale((double) AMapH5EmbedMapView.this.mRealView.getMap().v())));
                                            jSONObject4.put((String) "skew", (Object) Double.valueOf(H5MapUtils.convertDegree((double) AMapH5EmbedMapView.this.mRealView.getMap().J())));
                                            jSONObject4.put((String) ImageEditService.IN_EDIT_TYPE_ROTATE, (Object) Double.valueOf(H5MapUtils.convertAngle((double) AMapH5EmbedMapView.this.mRealView.getMap().I())));
                                            jSONObject4.put((String) "element", (Object) AMapH5EmbedMapView.this.mElementProvider.getElement());
                                            jSONObject3.put((String) "data", (Object) jSONObject4);
                                            bridge2.sendToWeb("nbcomponent.map.bindregionchange", jSONObject3, null);
                                            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "region Change end, data: ".concat(String.valueOf(jSONObject4)));
                                        } else {
                                            AnonymousClass2.this.isInitialChange = false;
                                            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "initial region Change end ignored, set isInitialChange = false");
                                        }
                                        AnonymousClass2.this.isCalledEnd = true;
                                    }
                                } else {
                                    return;
                                }
                            }
                            AMapH5EmbedMapView.this.mRealView.repaintCompass();
                            MarkerPropertyProcessor access$1600 = AMapH5EmbedMapView.this.mMarkerProcessor;
                            if (((int) AnonymousClass2.this.mLastScale) == AMapH5EmbedMapView.this.mRealView.getMap().w()) {
                                z = true;
                            }
                            access$1600.checkHideOnScale(z);
                            AnonymousClass2.this.mLastCenterLat = geoPointHD.getLatitude();
                            AnonymousClass2.this.mLastCenterLon = geoPointHD.getLongitude();
                            AnonymousClass2.this.mLastScale = AMapH5EmbedMapView.this.mRealView.getMap().v();
                            AnonymousClass2.this.mLastMapAngle = AMapH5EmbedMapView.this.mRealView.getMap().I();
                            AnonymousClass2.this.mLastCameraDegree = AMapH5EmbedMapView.this.mRealView.getMap().J();
                            return;
                        }
                        AnonymousClass2.this.isInitialChange = false;
                        AnonymousClass2.this.isFrameDrawn = true;
                        AnonymousClass2.this.isIgnoreDrawFrameCallback = true;
                        AMapH5EmbedMapView.this.mainHandler.postDelayed(new Runnable() {
                            public void run() {
                                AnonymousClass2.this.isIgnoreDrawFrameCallback = false;
                                if (AMapH5EmbedMapView.this.mRealView != null) {
                                    AMapH5EmbedMapView.this.mRealView.getMap().R();
                                }
                            }
                        }, 100);
                    }
                }
            });
        }
    };
    private MapToScreenActionProcessor mMapToScreenActionProcessor;
    /* access modifiers changed from: private */
    public MarkerPropertyProcessor mMarkerProcessor;
    private MiniAppShowRouteManager mMiniAppShowRouteManager;
    private MiscPropertyProcssor mMiscProcessor;
    private MoveToLocationActionProcessor mMoveToLocationActionProcessor;
    /* access modifiers changed from: private */
    public WeakReference<H5Page> mPage;
    private PolygonPropertyProcessor mPolygonProcessor;
    private PolylinePropertyProcessor mPolylineProcessor;
    /* access modifiers changed from: private */
    public Bitmap mPreSnapshot;
    private List<BasePropertyProcessor> mPropertyProcessors = new ArrayList();
    /* access modifiers changed from: private */
    public AdapterTextureMapView mRealView;
    private RoutePlanActionProcessor mRoutePlanActionProcessor;
    private ScreenToMapActionProcessor mScreenToMapActionProcessor;
    private String mSessionId;
    private ShowCompassActionProcessor mShowCompassActionProcessor;
    private ShowRouteActionProcessor mShowRouteActionProcessor;
    private ShowScaleActionProcessor mShowScaleActionProcessor;
    private ShowTileOverlayActionProcessor mShowTileOverlayActionProcessor;
    private TranslateMarkerActionProcessor mTranslateMarkerActionProcessor;
    private UpdateComponentsActionProcessor mUpdateComponentActionProcessor;
    private UpdateIndoorMapActionProcessor mUpdateIndoorMapActionProcessor;
    /* access modifiers changed from: private */
    public Handler mainHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public JSONObject mapDataJsonObj;
    /* access modifiers changed from: private */
    public final ReentrantLock miniAppMapLock = new ReentrantLock();
    private OnFloorChangeListener onFloorChangeListener = new OnFloorChangeListener() {
        private JSONObject lastNotifyJson;

        public void notifyStateChange(boolean z) {
            H5Page h5Page = (H5Page) AMapH5EmbedMapView.this.mPage.get();
            if (h5Page != null) {
                H5Bridge bridge = h5Page.getBridge();
                if (bridge != null) {
                    MiniAppFloorManager miniAppFloorManager = AMapH5EmbedMapView.this.mRealView.getMiniAppFloorManager();
                    String str = miniAppFloorManager.getIndoorBuilding() != null ? z ? ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_SHOW : LocaleHelper.SPKEY_CHANGE_FLAG : ModuleHeadunitImpl.HEADUNIT_BTN_EVENT_HIDE;
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put((String) "state", (Object) str);
                    ami lastIndoorBuilding = miniAppFloorManager.getLastIndoorBuilding();
                    JSONObject jSONObject2 = new JSONObject();
                    if (lastIndoorBuilding != null) {
                        jSONObject2.put((String) LocationInstrument.LOCATION_EXTRAS_KEY_POIID, (Object) lastIndoorBuilding.e);
                        jSONObject2.put((String) "activeFloorIndex", (Object) Integer.valueOf(lastIndoorBuilding.d));
                        int i = 0;
                        while (true) {
                            if (i >= lastIndoorBuilding.h.length) {
                                i = 0;
                                break;
                            } else if (lastIndoorBuilding.h[i] == lastIndoorBuilding.d) {
                                break;
                            } else {
                                i++;
                            }
                        }
                        jSONObject2.put((String) "activeFloorInfoIndex", (Object) Integer.valueOf(i));
                        JSONArray jSONArray = new JSONArray();
                        jSONArray.addAll(Arrays.asList(lastIndoorBuilding.i));
                        jSONObject2.put((String) "floorNames", (Object) jSONArray);
                        JSONArray jSONArray2 = new JSONArray();
                        for (int valueOf : lastIndoorBuilding.h) {
                            jSONArray2.add(Integer.valueOf(valueOf));
                        }
                        jSONObject2.put((String) "floorIndexes", (Object) jSONArray2);
                    }
                    jSONObject.put((String) "building", (Object) jSONObject2);
                    if (!jSONObject.equals(this.lastNotifyJson)) {
                        this.lastNotifyJson = (JSONObject) jSONObject.clone();
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put((String) "data", (Object) jSONObject);
                        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "indoor event: ".concat(String.valueOf(jSONObject3)));
                        bridge.sendToWeb("nbcomponent.map.bindindoormapstatechange", jSONObject3, null);
                    }
                }
            }
        }
    };

    public static class ComponentJsonObj implements Serializable {
        public Command command;
        @JSONField(name = "include-padding")
        public IncludePadding includePadding;
        @JSONField(name = "include-points")
        public List<Point> includePoints;
        public double latitude = -1000.0d;
        public double longitude = -1000.0d;
        public List<Marker> markers;
        public List<Polyline> polyline;
        public Float rotate;
        public float scale = -1.0f;
        public MapSetting setting;
        public Float skew;
    }

    public static class MapJsonObj implements Serializable {
        public List<Circle> circles;
        public List<Control> controls;
        public String element;
        @JSONField(name = "ground-overlays")
        public List<GroundOverlay> groundOverlays;
        @JSONField(name = "include-padding")
        public IncludePadding includePadding;
        @JSONField(name = "include-points")
        public List<Point> includePoints;
        public double latitude = -1000.0d;
        public double longitude = -1000.0d;
        public List<Marker> markers;
        public List<Polygon> polygon;
        public List<Polyline> polyline;
        public Float rotate;
        public float scale = -1.0f;
        public MapSetting setting;
        @JSONField(name = "show-indoormap")
        public Boolean showIndoormap;
        @JSONField(name = "show-location")
        public Boolean showLocation;
        public Float skew;
    }

    public void onRequestPermissionResult(int i, String[] strArr, int[] iArr) {
    }

    public void onCreate(Context context, H5Page h5Page) {
        StringBuilder sb = new StringBuilder("onCreate context:");
        sb.append(context);
        sb.append(", h5Page:");
        sb.append(h5Page);
        AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
        this.mContext = new WeakReference<>(context);
        this.mPage = new WeakReference<>(h5Page);
        this.mSessionId = h5Page.getSession().getId();
        EmbedMapOfflineImageLoader.getInstance().onMapCreate(this.mSessionId);
    }

    public View getView(int i, int i2, String str, String str2, Map<String, String> map) {
        this.miniAppMapLock.lock();
        try {
            StringBuilder sb = new StringBuilder("getView,width,height:");
            sb.append(i);
            sb.append("/");
            sb.append(i2);
            AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
            if (this.mRealView == null) {
                create(i, i2);
                H5Page h5Page = (H5Page) this.mPage.get();
                if (h5Page != null) {
                    this.mMiniAppShowRouteManager = new MiniAppShowRouteManager();
                    if (!(this.mRealView == null || this.mRealView.getMap() == null)) {
                        this.mMiniAppShowRouteManager.init((Context) this.mContext.get(), this.mRealView.getMap().j());
                    }
                    this.mRealView.setOnFloorChangeListener(this.onFloorChangeListener);
                    initProertyProcessors();
                    initActionProcessors();
                    String str3 = map.get("bindEvents");
                    if (str3 != null) {
                        this.callOnPoiTap = JSON.parseArray(str3).contains("poitap");
                    }
                    H5Bridge bridge = h5Page.getBridge();
                    if (bridge != null) {
                        bridge.sendToWeb("nbcomponent.map.afterrender", null, null);
                    }
                }
            }
            return this.mRealView;
        } finally {
            this.miniAppMapLock.unlock();
        }
    }

    private void initProertyProcessors() {
        this.mMarkerProcessor = new MarkerPropertyProcessor(this.mContext, this.mPage, this.mRealView, this.mElementProvider);
        this.mPropertyProcessors.add(this.mMarkerProcessor);
        this.mControlProcessor = new ControlPropertyProcessor(this.mContext, this.mPage, this.mRealView, this.mElementProvider);
        this.mPropertyProcessors.add(this.mControlProcessor);
        this.mPolylineProcessor = new PolylinePropertyProcessor(this.mContext, this.mPage, this.mRealView);
        this.mPropertyProcessors.add(this.mPolylineProcessor);
        this.mPolygonProcessor = new PolygonPropertyProcessor(this.mContext, this.mPage, this.mRealView);
        this.mPropertyProcessors.add(this.mPolygonProcessor);
        this.mCirclesProcessor = new CirclesPropertyProcessor(this.mContext, this.mPage, this.mRealView);
        this.mPropertyProcessors.add(this.mCirclesProcessor);
        this.mGroundOverlaysProcessor = new GroundOverlaysPropertyProcessor(this.mContext, this.mPage, this.mRealView);
        this.mPropertyProcessors.add(this.mGroundOverlaysProcessor);
        this.mMiscProcessor = new MiscPropertyProcssor(this.mContext, this.mPage, this.mRealView);
        this.mPropertyProcessors.add(this.mMiscProcessor);
    }

    private void initActionProcessors() {
        UpdateComponentsActionProcessor updateComponentsActionProcessor = new UpdateComponentsActionProcessor(this.mContext, this.mPage, this.mRealView, this.mMiscProcessor, this.mPolylineProcessor, this.mMarkerProcessor);
        this.mUpdateComponentActionProcessor = updateComponentsActionProcessor;
        this.mActionProcessors.add(this.mUpdateComponentActionProcessor);
        this.mGestureEnableActionProcessor = new GestureEnableActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mGestureEnableActionProcessor);
        this.mGetCenterLocationActionProcessor = new GetCenterLocationActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mGetCenterLocationActionProcessor);
        this.mMoveToLocationActionProcessor = new MoveToLocationActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mMoveToLocationActionProcessor);
        this.mShowCompassActionProcessor = new ShowCompassActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mShowCompassActionProcessor);
        this.mShowScaleActionProcessor = new ShowScaleActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mShowScaleActionProcessor);
        this.mShowTileOverlayActionProcessor = new ShowTileOverlayActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mShowTileOverlayActionProcessor);
        this.mUpdateIndoorMapActionProcessor = new UpdateIndoorMapActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mUpdateIndoorMapActionProcessor);
        this.mRoutePlanActionProcessor = new RoutePlanActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mRoutePlanActionProcessor);
        this.mShowRouteActionProcessor = new ShowRouteActionProcessor(this.mContext, this.mPage, this.mRealView, this.mMiniAppShowRouteManager);
        this.mActionProcessors.add(this.mShowRouteActionProcessor);
        this.mClearRouteActionProcessor = new ClearRouteActionProcessor(this.mContext, this.mPage, this.mRealView, this.mMiniAppShowRouteManager);
        this.mActionProcessors.add(this.mClearRouteActionProcessor);
        this.mGetScaleProcessor = new GetScaleProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mGetScaleProcessor);
        this.mTranslateMarkerActionProcessor = new TranslateMarkerActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mTranslateMarkerActionProcessor);
        this.mGetRegionActionProcessor = new GetRegionActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mGetRegionActionProcessor);
        this.mScreenToMapActionProcessor = new ScreenToMapActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mScreenToMapActionProcessor);
        this.mMapToScreenActionProcessor = new MapToScreenActionProcessor(this.mContext, this.mPage, this.mRealView);
        this.mActionProcessors.add(this.mMapToScreenActionProcessor);
    }

    private void create(int i, int i2) {
        AdapterTextureMapView adapterTextureMapView = new AdapterTextureMapView((Context) this.mContext.get());
        adapterTextureMapView.initMapView(i, i2);
        adapterTextureMapView.setMinimumWidth(i);
        adapterTextureMapView.setMinimumHeight(i2);
        adapterTextureMapView.onCreate(new Bundle());
        adapterTextureMapView.onResume();
        this.mRealView = adapterTextureMapView;
        this.mRealView.setMapListener(this.mMapListener);
    }

    public void onEmbedViewAttachedToWebView(int i, int i2, String str, String str2, Map<String, String> map) {
        this.miniAppMapLock.lock();
        try {
            StringBuilder sb = new StringBuilder("onEmbedViewAttachedToWebView,width,height:");
            sb.append(i);
            sb.append("/");
            sb.append(i2);
            AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
            StringBuilder sb2 = new StringBuilder("H5EmbedMapView onEmbedViewAttachedToWebView width ");
            sb2.append(i);
            sb2.append(" height ");
            sb2.append(i2);
            sb2.append(" viewId ");
            sb2.append(str);
            H5Log.d(TAG, sb2.toString());
            if (this.mRealView != null) {
                this.mRealView.onResume();
            }
        } finally {
            this.miniAppMapLock.unlock();
        }
    }

    public void onEmbedViewDetachedFromWebView(int i, int i2, String str, String str2, Map<String, String> map) {
        this.miniAppMapLock.lock();
        try {
            StringBuilder sb = new StringBuilder("onEmbedViewDetachedFromWebView,width,height:");
            sb.append(i);
            sb.append("/");
            sb.append(i2);
            AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
            StringBuilder sb2 = new StringBuilder("H5EmbedMapView onEmbedViewDetachedFromWebView width ");
            sb2.append(i);
            sb2.append(" height ");
            sb2.append(i2);
            sb2.append(" viewId ");
            sb2.append(str);
            H5Log.d(TAG, sb2.toString());
            if (this.mRealView != null) {
                this.mRealView.onPause();
                this.mRealView.removeMapListener(this.mMapListener);
            }
        } finally {
            this.miniAppMapLock.unlock();
        }
    }

    public void onEmbedViewDestory(int i, int i2, String str, String str2, Map<String, String> map) {
        this.miniAppMapLock.lock();
        try {
            StringBuilder sb = new StringBuilder("onEmbedViewDestory,width,height:");
            sb.append(i);
            sb.append("/");
            sb.append(i2);
            AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
            destroy(true);
        } finally {
            this.miniAppMapLock.unlock();
        }
    }

    public void onEmbedViewParamChanged(int i, int i2, String str, String str2, Map<String, String> map, String[] strArr, String[] strArr2) {
        StringBuilder sb = new StringBuilder("onEmbedViewParamChanged,width,height:");
        sb.append(i);
        sb.append("/");
        sb.append(i2);
        AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
    }

    public void onEmbedViewVisibilityChanged(int i, int i2, String str, String str2, Map<String, String> map, int i3) {
        StringBuilder sb = new StringBuilder("onEmbedViewVisibilityChanged,width,height:");
        sb.append(i);
        sb.append("/");
        sb.append(i2);
        AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
    }

    public void onWebViewResume() {
        AMapLog.debug("infoservice.miniapp", TAG, "onWebViewResume");
    }

    public void onWebViewPause() {
        AMapLog.debug("infoservice.miniapp", TAG, "onWebViewPause");
    }

    public void onWebViewDestory() {
        AMapLog.debug("infoservice.miniapp", TAG, "onWebViewDestory");
    }

    public void onReceivedMessage(String str, JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        this.miniAppMapLock.lock();
        try {
            Iterator<BaseActionProcessor> it = this.mActionProcessors.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                BaseActionProcessor next = it.next();
                if (TextUtils.equals(str, next.getAction())) {
                    next.process(jSONObject, h5BridgeContext);
                    break;
                }
            }
        } finally {
            this.miniAppMapLock.unlock();
        }
    }

    public void onReceivedRender(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        this.miniAppMapLock.lock();
        if (jSONObject == null) {
            try {
                AMapLog.warning("infoservice.miniapp", TAG, "onReceivedRender,data is null");
            } finally {
                this.miniAppMapLock.unlock();
            }
        } else {
            this.mapDataJsonObj = jSONObject;
            render(this.mapDataJsonObj);
            afterRender(h5BridgeContext);
            this.miniAppMapLock.unlock();
        }
    }

    private void afterRender(H5BridgeContext h5BridgeContext) {
        H5Page h5Page = (H5Page) this.mPage.get();
        if (h5Page != null) {
            H5Bridge bridge = h5Page.getBridge();
            if (bridge != null) {
                bridge.sendToWeb("nbcomponent.map.afterrender", null, null);
            }
        }
        h5BridgeContext.sendSuccess();
    }

    public void execJavaScript(String str, IH5EmbedViewJSCallback iH5EmbedViewJSCallback) {
        AMapLog.debug("infoservice.miniapp", TAG, "execJavaScript,s:".concat(String.valueOf(str)));
    }

    public void getComponentResourceDataWithUrl(String str, ResponseListen responseListen, H5Page h5Page) {
        AMapLog.debug("infoservice.miniapp", TAG, "getComponentResourceDataWithUrl,s:".concat(String.valueOf(str)));
    }

    public View getSpecialRestoreView(int i, int i2, String str, String str2, Map<String, String> map) {
        this.miniAppMapLock.lock();
        try {
            StringBuilder sb = new StringBuilder("getSpecialRestoreView,width,height:");
            sb.append(i);
            sb.append("/");
            sb.append(i2);
            AMapLog.debug("infoservice.miniapp", TAG, sb.toString());
            if (this.mRealView == null) {
                create(i, i2);
                this.mMiniAppShowRouteManager = new MiniAppShowRouteManager();
                bty map2 = this.mRealView.getMap();
                if (map2 != null) {
                    this.mMiniAppShowRouteManager.init((Context) this.mContext.get(), map2.j());
                }
                this.mRealView.setOnFloorChangeListener(this.onFloorChangeListener);
                initProertyProcessors();
                initActionProcessors();
                if (this.mapDataJsonObj != null) {
                    render(this.mapDataJsonObj);
                }
            } else {
                this.mRealView.onResume();
                this.mRealView.setMapListener(this.mMapListener);
            }
            return this.mRealView;
        } finally {
            this.miniAppMapLock.unlock();
        }
    }

    public Bitmap getSnapshot(int i, int i2, String str, String str2, Map<String, String> map) {
        AMapLog.debug("infoservice.miniapp", TAG, "triggerPreSnapshot onMapScreenShot");
        return this.mPreSnapshot;
    }

    public void triggerPreSnapshot() {
        if (this.mRealView != null) {
            this.mRealView.getMap().a(this.mRealView.getMap().al(), this.mRealView.getMap().am(), (a) new a() {
                public void onCallBack(Bitmap bitmap) {
                    AMapH5EmbedMapView.this.mPreSnapshot = bitmap;
                    LocalBroadcastManager instance = LocalBroadcastManager.getInstance((Context) AMapH5EmbedMapView.this.mContext.get());
                    Intent intent = new Intent();
                    intent.setAction("embedview.snapshot.complete");
                    instance.sendBroadcast(intent);
                    AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "triggerPreSnapshot onMapScreenShot");
                }
            });
        }
    }

    private void destroy(boolean z) {
        for (BasePropertyProcessor destroy : this.mPropertyProcessors) {
            destroy.destroy();
        }
        this.mPropertyProcessors.clear();
        for (BaseActionProcessor destroy2 : this.mActionProcessors) {
            destroy2.destroy();
        }
        this.mActionProcessors.clear();
        this.mMiniAppShowRouteManager.destroy();
        this.mMiniAppShowRouteManager = null;
        if (this.mRealView != null) {
            this.mRealView.removeMapListener(this.mMapListener);
            this.mRealView.onDestroy(z);
            this.mRealView = null;
        }
        this.mainHandler.removeCallbacksAndMessages(null);
        this.mapDataJsonObj = null;
        EmbedMapOfflineImageLoader.getInstance().onMapDestroy(this.mSessionId);
    }

    private void render(JSONObject jSONObject) {
        if (this.mRealView != null) {
            MapJsonObj mapJsonObj = null;
            if (jSONObject != null) {
                try {
                    mapJsonObj = (MapJsonObj) jSONObject.toJavaObject(MapJsonObj.class);
                } catch (Throwable th) {
                    AMapLog.debug("infoservice.miniapp", TAG, Log.getStackTraceString(th));
                }
            }
            if (mapJsonObj != null) {
                this.mElementProvider.setElement(mapJsonObj.element);
            }
            if (mapJsonObj != null) {
                for (BasePropertyProcessor process : this.mPropertyProcessors) {
                    process.process(mapJsonObj);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean hasValidMapView() {
        return this.mRealView != null;
    }
}
