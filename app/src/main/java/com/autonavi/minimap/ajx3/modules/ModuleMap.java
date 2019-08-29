package com.autonavi.minimap.ajx3.modules;

import android.graphics.Rect;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@AjxModule("map")
public final class ModuleMap extends AbstractModule {
    private static final String DAY_NIGHT_MODE = "NaviModeSet";
    private static final int DAY_NIGHT_MODE_AUTO = 16;
    private static final int DAY_NIGHT_MODE_DAY = 17;
    private static final int DAY_NIGHT_MODE_NIGHT = 18;
    public static final String MODULE_NAME = "map";
    private static final String TRACKING_MODE_FOLLOW = "follow";
    private static final String TRACKING_MODE_FOLLOW_WITH_HEADING = "followWithHeading";
    private static final String TRACKING_MODE_NONE = "none";
    private bei listener = new bei() {
        public void onFloorWidgetVisibleChanged(boolean z, boolean z2) {
            super.onFloorWidgetVisibleChanged(z, z2);
            if (ModuleMap.this.mIndoorVisibilityChangeListener != null) {
                bec bec = (bec) ank.a(bec.class);
                ami ami = null;
                if (bec != null) {
                    ami = bec.a().a();
                }
                ModuleMap.this.mIndoorVisibilityChangeListener.callback(Boolean.valueOf(z), ModuleMap.this.transferToAJXBuildingInfo(ami));
            }
        }
    };
    private final float mDensity;
    /* access modifiers changed from: private */
    public JsFunctionCallback mIndoorVisibilityChangeListener;
    private JsFunctionCallback mJsMoveFinishedCallback;

    public ModuleMap(IAjxContext iAjxContext) {
        super(iAjxContext);
        this.mDensity = iAjxContext.getNativeContext().getResources().getDisplayMetrics().density;
    }

    @AjxMethod("moveFinished")
    @Deprecated
    public final void moveFinished(JsFunctionCallback jsFunctionCallback) {
        this.mJsMoveFinishedCallback = jsFunctionCallback;
    }

    public final void onCenterPointChanged(@NonNull GeoPoint geoPoint, @NonNull GeoPoint geoPoint2, int i, boolean z) {
        if (this.mJsMoveFinishedCallback != null) {
            this.mJsMoveFinishedCallback.callback(Double.valueOf(geoPoint.getLongitude()), Double.valueOf(geoPoint.getLatitude()), Double.valueOf(geoPoint2.getLongitude()), Double.valueOf(geoPoint2.getLatitude()), Integer.valueOf(i), Boolean.valueOf(z));
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getGeoObj")
    @Deprecated
    public final String getGeoObj() {
        StringBuilder sb = new StringBuilder();
        bty mapView = DoNotUseTool.getMapManager().getMapView();
        if (mapView == null) {
            AMapLog.w("ModuleMap", "getGeoObj error, can not obtain mapView!");
            return sb.toString();
        }
        Rect H = mapView.H();
        DPoint a = cfg.a((long) H.left, (long) H.top);
        DPoint a2 = cfg.a((long) H.right, (long) H.bottom);
        sb.append(a.x);
        sb.append(";");
        sb.append(a.y);
        sb.append(";");
        sb.append(a2.x);
        sb.append(";");
        sb.append(a2.y);
        return sb.toString();
    }

    @AjxMethod("distanceOfLocation")
    @Deprecated
    public final void getDistance(double d, double d2, double d3, double d4, JsFunctionCallback jsFunctionCallback) {
        float[] fArr = new float[1];
        Location.distanceBetween(d2, d, d4, d3, fArr);
        jsFunctionCallback.callback(Float.valueOf(fArr[0]));
    }

    @AjxMethod(invokeMode = "sync", value = "distanceOfLocationSync")
    @Deprecated
    public final float getDistanceSync(double d, double d2, double d3, double d4) {
        float[] fArr = new float[1];
        Location.distanceBetween(d2, d, d4, d3, fArr);
        return fArr[0];
    }

    @AjxMethod("saveTrafficStateSetting")
    public final void saveTrafficStateSetting(boolean z) {
        new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("traffic", z);
    }

    @AjxMethod("setMapModeAndStyle")
    @Deprecated
    public final void setMapModeAndStyle(int i, int i2, int i3) {
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager != null && mapManager.getMapView() != null) {
            mapManager.getMapView().a(i, i2, i3);
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getTrafficStateSetting")
    public final boolean getTrafficStateSetting() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
    }

    @AjxMethod("saveMapModeSetting")
    @Deprecated
    public final void saveMapModeSetting(int i) {
        bim.aa().a((String) "101", i);
    }

    @AjxMethod(invokeMode = "sync", value = "getMapModeSetting")
    @Deprecated
    public final int getMapModeSetting() {
        return bim.aa().l((String) "101");
    }

    @AjxMethod("saveMapTimeSetting")
    public final void saveMapTimeSetting(int i) {
        MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
        switch (i) {
            case 0:
                mapSharePreference.putIntValue("NaviModeSet", 17);
                return;
            case 1:
                mapSharePreference.putIntValue("NaviModeSet", 18);
                break;
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getMapTimeSetting")
    public final int getMapTimeSetting() {
        return new MapSharePreference(SharePreferenceName.SharedPreferences).getIntValue("NaviModeSet", 16);
    }

    @AjxMethod("scaleFactor")
    @Deprecated
    public final void scaleFactor(JsFunctionCallback jsFunctionCallback) {
        jsFunctionCallback.callback(Float.valueOf(this.mDensity));
    }

    @AjxMethod(invokeMode = "sync", value = "getCenter")
    @Deprecated
    public final String getCenter() {
        MapManager mapManager = DoNotUseTool.getMapManager();
        if (mapManager == null || mapManager.getMapView() == null) {
            return null;
        }
        GeoPoint o = mapManager.getMapView().o();
        StringBuilder sb = new StringBuilder();
        sb.append(o.getLongitude());
        sb.append(",");
        sb.append(o.getLatitude());
        return sb.toString();
    }

    @AjxMethod("disableGpsFollow")
    public final void disableGpsFollow() {
        cde suspendManager = DoNotUseTool.getSuspendManager();
        if (suspendManager != null && suspendManager.d() != null) {
            suspendManager.d().a(false);
            suspendManager.d().f();
        }
    }

    @AjxMethod("changeToNormalMapMode")
    public final void changeToNormalMapMode() {
        cde suspendManager = DoNotUseTool.getSuspendManager();
        if (suspendManager != null && suspendManager.d() != null) {
            suspendManager.d().f();
            suspendManager.d().e();
        }
    }

    @AjxMethod("setRenderFps")
    @Deprecated
    public final void setRenderFps(int i) {
        if (i >= 0 && i <= 60) {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (!(mapManager == null || mapManager.getMapView() == null)) {
                mapManager.getMapView().c(i);
            }
        }
    }

    @AjxMethod("screenShotForMap")
    @Deprecated
    public final void screenShotForMap(final JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager == null) {
                jsFunctionCallback.callback("");
                return;
            }
            cfc.a().a(mapManager, (a) new a() {
                public void onPrepare() {
                }

                public void onFailure() {
                    jsFunctionCallback.callback("");
                }

                public void onScreenShotFinish(String str) {
                    if (str == null) {
                        str = "";
                    }
                    jsFunctionCallback.callback(str);
                }
            });
        }
    }

    @AjxMethod("setGpsOverlayVisibility")
    @Deprecated
    public final void setGpsOverlayVisibility(boolean z) {
        try {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (!(mapManager == null || mapManager.getOverlayManager() == null)) {
                mapManager.getOverlayManager().getGpsLayer().setVisible(z);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getGpsOverlayVisibility")
    @Deprecated
    public final boolean getGpsOverlayVisibility() {
        try {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (!(mapManager == null || mapManager.getOverlayManager() == null)) {
                return mapManager.getOverlayManager().getGpsLayer().d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @AjxMethod("setGestureCenterType")
    @Deprecated
    public final void setGestureCenterType(String str) {
        if (DoNotUseTool.getMapManager() != null) {
            bty mapView = DoNotUseTool.getMapManager().getMapView();
            if (mapView != null) {
                mapView.g(mapView.j(), "1".equals(str) ? 1 : 0);
            }
        }
    }

    @AjxMethod("setGpsTrackingMode")
    public final void setGpsTrackingMode(String str) {
        cdx gpsLayer = getGpsLayer();
        if (gpsLayer != null) {
            if ("none".equals(str)) {
                cde suspendManager = DoNotUseTool.getSuspendManager();
                if (!(suspendManager == null || suspendManager.d() == null)) {
                    suspendManager.d().a(false);
                    suspendManager.d().f();
                }
            } else if (TRACKING_MODE_FOLLOW.equals(str)) {
                gpsLayer.a(0);
                gpsLayer.a(true);
            } else {
                if (TRACKING_MODE_FOLLOW_WITH_HEADING.equals(str)) {
                    gpsLayer.a(1);
                    gpsLayer.a(true);
                }
            }
        }
    }

    @AjxMethod(invokeMode = "sync", value = "getGpsTrackingMode")
    public final String getGpsTrackingMode() {
        cdx gpsLayer = getGpsLayer();
        if (gpsLayer == null) {
            return null;
        }
        int i = gpsLayer.b;
        if (!(gpsLayer.c == 1)) {
            return "none";
        }
        if (i == 1) {
            return TRACKING_MODE_FOLLOW_WITH_HEADING;
        }
        if (i == 0) {
            return TRACKING_MODE_FOLLOW;
        }
        return null;
    }

    @AjxMethod("getActivatedIndoorBuildingInfo")
    public final void getActivatedIndoorBuildingInfo(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            bec bec = (bec) ank.a(bec.class);
            if (bec != null) {
                jsFunctionCallback.callback(transferToAJXBuildingInfo(bec.a().a()));
            }
        }
    }

    /* access modifiers changed from: private */
    public JSONObject transferToAJXBuildingInfo(ami ami) {
        if (ami == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name_cn", ami.a);
            jSONObject.put("name_en", ami.b);
            jSONObject.put("activeFloorName", ami.c);
            jSONObject.put("activeFloorIndex", ami.d);
            jSONObject.put(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, ami.e);
            jSONObject.put("building_type", ami.f);
            jSONObject.put("numberOfFloor", ami.g);
            jSONObject.put("numberOfParkingFloor", ami.k);
            if (ami.h != null) {
                jSONObject.put("floorIndexs", new JSONArray(ami.h));
            }
            if (ami.i != null) {
                jSONObject.put("floorNames", new JSONArray(Arrays.asList(ami.i)));
            }
            if (ami.j != null) {
                jSONObject.put("floor_nonas", new JSONArray(Arrays.asList(ami.j)));
            }
            if (ami.l != null) {
                jSONObject.put("parkingFloorIndexs", new JSONArray(ami.l));
            }
        } catch (JSONException unused) {
        }
        return jSONObject;
    }

    @AjxMethod("activateIndoorBuildingFloor")
    public final void activateIndoorBuildingFloor(String str, int i, String str2) {
        bec bec = (bec) ank.a(bec.class);
        if (bec != null) {
            bea a = bec.a();
            if (a != null) {
                ami a2 = a.a();
                if (a2 != null && TextUtils.equals(a2.e, str)) {
                    int[] iArr = a2.h;
                    if (iArr != null) {
                        for (int i2 : iArr) {
                            if (i2 == i) {
                                a.a(str, i, str2);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @AjxMethod("setIndoorBuildingVisibilityChangeListener")
    public final void setIndoorBuildingVisibilityChangeListener(JsFunctionCallback jsFunctionCallback) {
        if (jsFunctionCallback != null) {
            this.mIndoorVisibilityChangeListener = jsFunctionCallback;
            bec bec = (bec) ank.a(bec.class);
            if (bec != null) {
                bea a = bec.a();
                if (a != null) {
                    a.a(this.listener);
                }
            }
        }
    }

    @AjxMethod("removeIndoorBuildingVisibilityChangeListener")
    public final void removeIndoorBuildingVisibilityChangeListener() {
        this.mIndoorVisibilityChangeListener = null;
        bec bec = (bec) ank.a(bec.class);
        if (bec != null) {
            bea a = bec.a();
            if (a != null) {
                a.b(this.listener);
            }
        }
    }

    @Nullable
    private cdx getGpsLayer() {
        xp xpVar = (xp) a.a.a(xp.class);
        if (xpVar != null && (xpVar.a() instanceof cdx)) {
            return (cdx) xpVar.a();
        }
        return null;
    }
}
