package com.autonavi.bundle.amaphome.controller;

import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class MapHomeMapEventController {
    /* access modifiers changed from: private */
    public boolean hasMapInited = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean mIsRegisteredMainMapEventListener = false;
    private MainMapEventListener mMainMapEventListener = new awc() {
        public void onNoFeatureClick() {
            super.onNoFeatureClick();
        }

        private void logBlankClick() {
            bty mapView = MapHomeMapEventController.this.mPage.getMapView();
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            if (glGeoPoint2GeoPoint != null) {
                int j = mapView.j(false);
                int i = 2;
                if (j == 0) {
                    j = 1;
                } else if (j == 1) {
                    j = 2;
                } else if (j == 2) {
                    j = 3;
                }
                if (mapView.s()) {
                    i = 1;
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", j);
                    jSONObject.put("from", mapView.w());
                    jSONObject.put("lat", glGeoPoint2GeoPoint.getLatitude());
                    jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, glGeoPoint2GeoPoint.getLongitude());
                    jSONObject.put("status", i);
                    jSONObject.put("itemId", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_CLICK_BLANK_CONTENT, jSONObject);
                HashMap hashMap = new HashMap();
                hashMap.put("type", String.valueOf(j));
                StringBuilder sb = new StringBuilder();
                sb.append(mapView.w());
                hashMap.put("from", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(glGeoPoint2GeoPoint.getLatitude());
                hashMap.put("lat", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(glGeoPoint2GeoPoint.getLongitude());
                hashMap.put(LocationParams.PARA_FLP_AUTONAVI_LON, sb3.toString());
                hashMap.put("status", String.valueOf(i));
                hashMap.put("itemId", "1");
                kd.b("amap.P00001.0.B156", hashMap);
            }
        }

        public boolean onBlankClick() {
            logBlankClick();
            if (MapHomeMapEventController.this.mPage.f()) {
                MapHomeMapEventController.this.mPage.g();
            } else {
                MapHomeMapEventController.this.mPage.h();
            }
            return true;
        }

        public void onMapLevelChange(boolean z) {
            if (!MapHomeMapEventController.this.hasMapInited) {
                MapHomeMapEventController.this.hasMapInited = true;
            } else if (AMapPageUtil.getPagesStacks().size() == 1 && MapHomeMapEventController.this.mUserMapTouch) {
                MapHomeMapEventController.this.mPage.i();
            }
        }

        public void onMoveBegin(MotionEvent motionEvent) {
            super.onMoveBegin(motionEvent);
            MapHomeMapEventController.this.mPage.i();
        }

        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }

        public void onScaleRotateBegin(MotionEvent motionEvent) {
            super.onScaleRotateBegin(motionEvent);
        }

        public void onEngineActionGesture(alg alg) {
            logEngineActionGesture(alg.a);
        }

        public void onUserMapTouchEvent(MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                MapHomeMapEventController.this.mUserMapTouch = true;
            } else if (action == 1) {
                MapHomeMapEventController.this.mUserMapTouch = false;
            }
            if (MapHomeMapEventController.this.mPage != null) {
                MapHomeMapEventController.this.mPage.onMapTouchEvent(motionEvent);
            }
        }

        public void onMapRenderCompleted() {
            super.onMapRenderCompleted();
            la.a((int) MapHomeMapEventController.this.mPage.getMapView().an());
            cke.f();
            cke.b(MapHomeMapEventController.this.mPage.getMapView());
            if (bny.a) {
                MapHomeMapEventController.this.mPage.getMapView().f(0);
            }
        }

        private void logEngineActionGesture(int i) {
            bty mapView = MapHomeMapEventController.this.mPage.getMapView();
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            if (glGeoPoint2GeoPoint != null) {
                int j = mapView.j(false);
                int i2 = mapView.s() ? 1 : 2;
                HashMap hashMap = new HashMap();
                hashMap.put("type", String.valueOf(j));
                StringBuilder sb = new StringBuilder();
                sb.append(mapView.w());
                hashMap.put("from", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(glGeoPoint2GeoPoint.getLatitude());
                hashMap.put("lat", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(glGeoPoint2GeoPoint.getLongitude());
                hashMap.put(LocationParams.PARA_FLP_AUTONAVI_LON, sb3.toString());
                hashMap.put("status", String.valueOf(i));
                hashMap.put("isLogin", String.valueOf(i2));
                hashMap.put("itemId", "1");
                StringBuilder sb4 = new StringBuilder();
                sb4.append(mapView.J());
                hashMap.put("text", sb4.toString());
                kd.b("amap.P00001.0.B069", hashMap);
            }
        }
    };
    /* access modifiers changed from: private */
    public arn mPage;
    /* access modifiers changed from: private */
    public boolean mUserMapTouch = false;

    public MapHomeMapEventController(arn arn) {
        this.mPage = arn;
    }

    public void registerMainMapEventListener() {
        if (!this.mIsRegisteredMainMapEventListener) {
            this.mIsRegisteredMainMapEventListener = true;
            this.mPage.addMainMapEventListener(this.mMainMapEventListener);
        }
    }

    public void unregisterMainMapEventListener() {
        if (this.mIsRegisteredMainMapEventListener) {
            this.mIsRegisteredMainMapEventListener = false;
            this.mPage.removeMainMapEventListener(this.mMainMapEventListener);
        }
    }

    public void onPageStart() {
        this.mUserMapTouch = false;
        registerMainMapEventListener();
    }

    public void onPageStop() {
        this.mUserMapTouch = false;
        unregisterMainMapEventListener();
    }
}
