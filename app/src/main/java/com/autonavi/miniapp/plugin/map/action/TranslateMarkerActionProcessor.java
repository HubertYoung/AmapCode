package com.autonavi.miniapp.plugin.map.action;

import android.content.Context;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.AnimationStatusType;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OverlayAnimationEvent;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay.OverlayAnimationListener;
import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.MarkerAnimator;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlay;
import com.autonavi.miniapp.plugin.map.overlay.MiniAppPointOverlayItem;
import com.autonavi.minimap.map.DPoint;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TranslateMarkerActionProcessor extends BaseActionProcessor {
    private static final int DES_LENGTH = 2;
    private static final String KEY_AUTOROTATE = "autoRotate";
    private static final String KEY_DESTINATION = "destination";
    private static final String KEY_MARKERID = "markerId";
    private static final String KEY_ROTATE = "rotate";
    /* access modifiers changed from: private */
    public static ConcurrentHashMap<Long, MiniAppPointOverlayItem> sItemCache = new ConcurrentHashMap<>();
    public static Map<Long, String> sTranslateMarkerAnimStatusMap = Collections.synchronizedMap(new HashMap());

    public static class TranslateMarkerConfig implements Serializable {
        public boolean autoRotate;
        public Destination destination;
        public double duration = 1000.0d;
        public Object markerId;
        public float rotate = 0.0f;

        public static class Destination {
            public double latitude = -1.0d;
            public double longitude = -1.0d;

            public String toString() {
                StringBuilder sb = new StringBuilder("destination{lat=");
                sb.append(this.latitude);
                sb.append(", lng=");
                sb.append(this.longitude);
                sb.append('}');
                return sb.toString();
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("TranslateMarkerConfig{markerId='");
            sb.append(this.markerId);
            sb.append('\'');
            sb.append(", destination.lat=");
            sb.append(this.destination.latitude);
            sb.append('\'');
            sb.append(", destination.lng=");
            sb.append(this.destination.longitude);
            sb.append('\'');
            sb.append(", autoRotate=");
            sb.append(this.autoRotate);
            sb.append('\'');
            sb.append(", rotate='");
            sb.append(this.rotate);
            sb.append('\'');
            sb.append(", duration='");
            sb.append(this.duration);
            sb.append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public TranslateMarkerActionProcessor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super("translateMarker", weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(JSONObject jSONObject, H5BridgeContext h5BridgeContext) {
        JSONObject jSONObject2 = jSONObject;
        if (this.mRealView != null) {
            MiniAppPointOverlay pointOverlay = this.mRealView.getPointOverlay();
            if (pointOverlay != null) {
                if (!jSONObject2.containsKey(KEY_MARKERID) || !jSONObject2.containsKey(KEY_DESTINATION)) {
                    return;
                }
                if (!jSONObject2.containsKey(KEY_AUTOROTATE)) {
                    jSONObject2.put((String) KEY_AUTOROTATE, (Object) Boolean.TRUE);
                }
                if (!jSONObject2.containsKey("rotate")) {
                    jSONObject2.put((String) "rotate", (Object) Integer.valueOf(0));
                }
                try {
                    if (jSONObject2.getBoolean(KEY_AUTOROTATE) == null) {
                        jSONObject2.put((String) KEY_AUTOROTATE, (Object) Boolean.TRUE);
                    }
                } catch (Exception unused) {
                    jSONObject2.put((String) KEY_AUTOROTATE, (Object) Boolean.TRUE);
                }
                try {
                    if (jSONObject2.getFloat("rotate") == null) {
                        jSONObject2.put((String) "rotate", (Object) Integer.valueOf(0));
                    }
                } catch (Exception unused2) {
                    jSONObject2.put((String) "rotate", (Object) Integer.valueOf(0));
                }
                try {
                    JSONObject jSONObject3 = jSONObject2.getJSONObject(KEY_DESTINATION);
                    if (jSONObject3.getFloat("longitude") != null && jSONObject3.getFloat("latitude") != null) {
                        TranslateMarkerConfig translateMarkerConfig = (TranslateMarkerConfig) jSONObject2.toJavaObject(TranslateMarkerConfig.class);
                        if (translateMarkerConfig != null) {
                            BigDecimal valueOf = BigDecimal.valueOf(-1);
                            BigDecimal valueOf2 = BigDecimal.valueOf(translateMarkerConfig.destination.longitude);
                            BigDecimal valueOf3 = BigDecimal.valueOf(translateMarkerConfig.destination.latitude);
                            boolean z = valueOf.compareTo(valueOf2) == 0;
                            boolean z2 = valueOf.compareTo(valueOf3) == 0;
                            if (translateMarkerConfig.destination == null || (!z && !z2)) {
                                MiniAppPointOverlayItem findMarker = new MarkerAnimator(pointOverlay).findMarker(translateMarkerConfig.markerId);
                                if (findMarker != null) {
                                    GeoPointHD[] geoPointHDArr = {new GeoPointHD(findMarker.mGeoPoint), new GeoPointHD(translateMarkerConfig.destination.longitude, translateMarkerConfig.destination.latitude)};
                                    BigDecimal valueOf4 = BigDecimal.valueOf(findMarker.mMiniAppMarker.longitude);
                                    BigDecimal valueOf5 = BigDecimal.valueOf(findMarker.mMiniAppMarker.latitude);
                                    boolean z3 = valueOf4.compareTo(valueOf2) == 0;
                                    boolean z4 = valueOf5.compareTo(valueOf3) == 0;
                                    if (!z3 || !z4) {
                                        GLPointOverlay gLPointOverlay = (GLPointOverlay) pointOverlay.getGLOverlay();
                                        if (gLPointOverlay != null) {
                                            float f = !translateMarkerConfig.autoRotate ? translateMarkerConfig.rotate : 0.0f;
                                            long j = findMarker.mItemId;
                                            final String string = jSONObject2.getString("element");
                                            sItemCache.put(Long.valueOf(j), findMarker);
                                            final long j2 = j;
                                            final GLPointOverlay gLPointOverlay2 = gLPointOverlay;
                                            AnonymousClass1 r7 = r0;
                                            final H5BridgeContext h5BridgeContext2 = h5BridgeContext;
                                            AnonymousClass1 r0 = new OverlayAnimationListener() {
                                                public void onProcessOverlayAnimationEvent(OverlayAnimationEvent overlayAnimationEvent) {
                                                    if (overlayAnimationEvent != null) {
                                                        String simpleName = TranslateMarkerActionProcessor.class.getSimpleName();
                                                        StringBuilder sb = new StringBuilder("onProcessOverlayAnimationEvent itemId=");
                                                        sb.append(overlayAnimationEvent.mAnimationObject);
                                                        AMapLog.debug("infoservice.miniapp", simpleName, sb.toString());
                                                    }
                                                    if (!(overlayAnimationEvent == null || overlayAnimationEvent.mStatus == null)) {
                                                        String name = overlayAnimationEvent.mStatus.name();
                                                        if (AnimationStatusType.AnimationStatusTypeStart.name().equals(name)) {
                                                            TranslateMarkerActionProcessor.sTranslateMarkerAnimStatusMap.put(Long.valueOf(j2), name);
                                                        }
                                                        if (!AnimationStatusType.AnimationStatusTypeStart.name().equals(name) && !AnimationStatusType.AnimationStatusTypeDoing.name().equals(name)) {
                                                            TranslateMarkerActionProcessor.sTranslateMarkerAnimStatusMap.remove(Long.valueOf(j2));
                                                        }
                                                    }
                                                    MiniAppPointOverlayItem miniAppPointOverlayItem = (MiniAppPointOverlayItem) TranslateMarkerActionProcessor.sItemCache.get(Long.valueOf(overlayAnimationEvent.mAnimationObject));
                                                    if ((miniAppPointOverlayItem != null && overlayAnimationEvent.mStatus == AnimationStatusType.AnimationStatusTypeNormalEnd) || overlayAnimationEvent.mStatus == AnimationStatusType.AnimationStatusTypeForceEnd) {
                                                        String simpleName2 = TranslateMarkerActionProcessor.class.getSimpleName();
                                                        StringBuilder sb2 = new StringBuilder("onProcessOverlayAnimationEvent process itemId=");
                                                        sb2.append(overlayAnimationEvent.mAnimationObject);
                                                        AMapLog.debug("infoservice.miniapp", simpleName2, sb2.toString());
                                                        GLGeoPoint itemPosition = gLPointOverlay2.getItemPosition(overlayAnimationEvent.mAnimationObject);
                                                        if (!(itemPosition == null || miniAppPointOverlayItem == null || miniAppPointOverlayItem.mMiniAppMarker == null)) {
                                                            DPoint a = cfg.a((long) itemPosition.x, (long) itemPosition.y);
                                                            miniAppPointOverlayItem.mMiniAppMarker.latitude = a.y;
                                                            miniAppPointOverlayItem.mMiniAppMarker.longitude = a.x;
                                                            miniAppPointOverlayItem.mGeoPoint.x = itemPosition.x;
                                                            miniAppPointOverlayItem.mGeoPoint.y = itemPosition.y;
                                                        }
                                                        JSONObject jSONObject = new JSONObject();
                                                        JSONObject jSONObject2 = new JSONObject();
                                                        jSONObject2.put((String) "element", (Object) string);
                                                        if (!(miniAppPointOverlayItem == null || miniAppPointOverlayItem.mMiniAppMarker == null)) {
                                                            jSONObject2.put((String) "translateMarkerId", miniAppPointOverlayItem.mMiniAppMarker.id);
                                                        }
                                                        jSONObject.put((String) "data", (Object) jSONObject2);
                                                        h5BridgeContext2.sendToWeb("nbcomponent.map.animationEnd", jSONObject, null);
                                                        if (TranslateMarkerActionProcessor.sItemCache != null && miniAppPointOverlayItem != null) {
                                                            TranslateMarkerActionProcessor.sItemCache.remove(Long.valueOf(miniAppPointOverlayItem.mItemId));
                                                        }
                                                    }
                                                }
                                            };
                                            gLPointOverlay.setAnimationListener(r7);
                                            pointOverlay.clearCalloutItem(Long.valueOf(j));
                                            gLPointOverlay.addMoveAnimationPointItem(findMarker.mItemId, geoPointHDArr, f, translateMarkerConfig.autoRotate, translateMarkerConfig.duration);
                                            this.mRealView.refreshRender();
                                        }
                                        return;
                                    }
                                    return;
                                }
                                AMapLog.debug("infoservice.miniapp", TranslateMarkerActionProcessor.class.getSimpleName(), "setItemCache fail item is null, itemId=".concat(String.valueOf(findMarker)));
                            }
                        }
                    }
                } catch (Exception unused3) {
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
        sTranslateMarkerAnimStatusMap.clear();
        super.doDestroy();
    }

    public boolean shouldForceRefresh() {
        return super.shouldForceRefresh();
    }
}
