package com.autonavi.miniapp.plugin.map.property;

import android.content.Context;
import com.alipay.mobile.h5container.api.H5Page;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.ComponentJsonObj;
import com.autonavi.miniapp.plugin.map.AMapH5EmbedMapView.MapJsonObj;
import com.autonavi.miniapp.plugin.map.AdapterTextureMapView;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MiscPropertyProcssor extends BasePropertyProcessor {

    public static class FloorSelectorPosition implements Serializable {
        public FloorSelectorPositionMargin margin = new FloorSelectorPositionMargin();
        public int position;
    }

    public static class FloorSelectorPositionMargin implements Serializable {
        public int bottom = 30;
        public int left = 10;
        public int right = 10;
    }

    public static class IncludePadding implements Serializable {
        public int bottom;
        public int left;
        public int right;
        public int top;
    }

    public static class MapSetting implements Serializable {
        public FloorSelectorPosition floorSelectorPosition;
        public Integer gestureEnable;
        public LogoPosition logoPosition;
        public Integer rotateGestureEnabled;
        public Integer scrollGestureEnabled;
        public Integer showCompass;
        public Integer showMapText;
        public Integer showScale;
        public Integer tiltGesturesEnabled;
        public Integer trafficEnabled;
        public Integer zoomGestureEnabled;
    }

    /* access modifiers changed from: protected */
    public void doClear() {
    }

    /* access modifiers changed from: protected */
    public void doDestroy() {
    }

    public MiscPropertyProcssor(WeakReference<Context> weakReference, WeakReference<H5Page> weakReference2, AdapterTextureMapView adapterTextureMapView) {
        super(weakReference, weakReference2, adapterTextureMapView);
    }

    /* access modifiers changed from: protected */
    public void doProcess(MapJsonObj mapJsonObj) {
        updateMapLocationAndScale(mapJsonObj.longitude, mapJsonObj.latitude, mapJsonObj.scale, mapJsonObj.showLocation != null ? mapJsonObj.showLocation.booleanValue() : -1, mapJsonObj.includePoints, mapJsonObj.includePadding);
        updateMapSetting(mapJsonObj.setting);
        updateMapDegrees(mapJsonObj.rotate, mapJsonObj.skew);
        this.mRealView.setIndoormapEnable(mapJsonObj.showIndoormap);
    }

    /* access modifiers changed from: protected */
    public void doProcess(ComponentJsonObj componentJsonObj) {
        updateMapLocationAndScale(componentJsonObj.longitude, componentJsonObj.latitude, componentJsonObj.scale, -1, componentJsonObj.includePoints, componentJsonObj.includePadding);
        updateMapSetting(componentJsonObj.setting);
        updateMapDegrees(componentJsonObj.rotate, componentJsonObj.skew);
    }

    private void updateMapLocationAndScale(double d, double d2, float f, int i, List<Point> list, IncludePadding includePadding) {
        StringBuilder sb = new StringBuilder("render latitude ");
        sb.append(d2);
        sb.append(" longitude ");
        sb.append(d);
        sb.append(" scale ");
        sb.append(f);
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
        if (i != -1) {
            this.mRealView.setLocationMarkerVisible(i == 1);
        }
        if (list == null || list.isEmpty()) {
            H5MapUtils.setMapScale(f, this.mRealView.getMap());
            H5MapUtils.setMapCenter(d2, d, this.mRealView.getMap());
        } else if (list.size() == 1) {
            H5MapUtils.setMapScale(f, this.mRealView.getMap());
            Point point = list.get(0);
            H5MapUtils.setMapCenter(point.latitude, point.longitude, this.mRealView.getMap());
        } else {
            setIncludePoints(list, includePadding);
        }
    }

    private void updateMapSetting(MapSetting mapSetting) {
        if (mapSetting == null) {
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, "map setting is null, skip.");
            return;
        }
        updateSetting4Guesture(mapSetting.gestureEnable);
        updateSetting4ShowScale(mapSetting.showScale);
        updateSetting4ShowCompass(mapSetting.showCompass);
        updateSetting4traffic(mapSetting.trafficEnabled);
        updateSetting4LogoPosition(mapSetting.logoPosition);
        updateSetting4MapText(mapSetting.showMapText);
        updateSetting4ScrollGesture(mapSetting.scrollGestureEnabled);
        updateSetting4ZoomGesture(mapSetting.zoomGestureEnabled);
        updateSetting4RotateGesture(mapSetting.rotateGestureEnabled);
        updateSetting4tiltGestures(mapSetting.tiltGesturesEnabled);
        if (!(mapSetting == null || mapSetting.floorSelectorPosition == null)) {
            this.mRealView.setIndoormapPosition(mapSetting.floorSelectorPosition.position, convertDp((double) mapSetting.floorSelectorPosition.margin.bottom), convertDp((double) mapSetting.floorSelectorPosition.margin.left), convertDp((double) mapSetting.floorSelectorPosition.margin.right));
        }
    }

    private void setIncludePoints(List<Point> list, IncludePadding includePadding) {
        int i;
        int i2;
        int i3;
        IncludePadding includePadding2 = includePadding;
        if (includePadding2 != null) {
            includePadding2.top = convertDp((double) includePadding2.top);
            includePadding2.bottom = convertDp((double) includePadding2.bottom);
            includePadding2.left = convertDp((double) includePadding2.left);
            includePadding2.right = convertDp((double) includePadding2.right);
        }
        ArrayList<GeoPoint> arrayList = new ArrayList<>();
        for (Point next : list) {
            arrayList.add(new GeoPoint(next.longitude, next.latitude));
        }
        int i4 = ((GeoPoint) arrayList.get(0)).x;
        int i5 = ((GeoPoint) arrayList.get(0)).y;
        int i6 = i4;
        int i7 = i5;
        for (GeoPoint geoPoint : arrayList) {
            i4 = Math.min(i4, geoPoint.x);
            i5 = Math.min(i5, geoPoint.y);
            i6 = Math.max(i6, geoPoint.x);
            i7 = Math.max(i7, geoPoint.y);
        }
        int al = this.mRealView.getMap().al();
        int am = this.mRealView.getMap().am();
        if (includePadding2 != null) {
            StringBuilder sb = new StringBuilder("setInculdePadding [left, top, right, bottom] = [");
            sb.append(includePadding2.left);
            sb.append(", ");
            sb.append(includePadding2.top);
            sb.append(", ");
            sb.append(includePadding2.right);
            sb.append(", ");
            sb.append(includePadding2.bottom);
            sb.append("]");
            AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb.toString());
            int i8 = al - (includePadding2.left + includePadding2.right);
            int i9 = am - (includePadding2.top + includePadding2.bottom);
            if (i8 > 0) {
                al = i8;
            } else {
                includePadding2.left = 0;
                includePadding2.right = 0;
            }
            if (i9 > 0) {
                am = i9;
            } else {
                includePadding2.top = 0;
                includePadding2.bottom = 0;
            }
            i2 = includePadding2.left;
            i3 = am;
            i = includePadding2.top;
        } else {
            i3 = am;
            i2 = 0;
            i = 0;
        }
        StringBuilder sb2 = new StringBuilder("setIncludePadding: width=");
        sb2.append(al);
        sb2.append(" height=");
        sb2.append(i3);
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb2.toString());
        akq b = akq.b();
        int j = this.mRealView.getMap().j();
        akq.b("calMapZoomLevelWithMapRect: ".concat(String.valueOf(j)));
        b.z(j);
        int i10 = i7;
        int i11 = i7;
        int i12 = al;
        float calMapZoomLevelWithMapRect = b.d.calMapZoomLevelWithMapRect(j, i4, i5, i6, i10, i2, i, i12, i3, 1.0f);
        float max = Math.max(Math.min(calMapZoomLevelWithMapRect, (float) this.mRealView.getMap().l()), (float) this.mRealView.getMap().m());
        StringBuilder sb3 = new StringBuilder("setInculdePadding zoomLevel=");
        sb3.append(max);
        sb3.append(" and caculateZoomLevel=");
        sb3.append(calMapZoomLevelWithMapRect);
        AMapLog.debug("infoservice.miniapp", AMapH5EmbedMapView.TAG, sb3.toString());
        setMapCenter((i4 + i6) / 2, (i5 + i11) / 2, includePadding2, max);
        H5MapUtils.setMapScale(max, this.mRealView.getMap());
        this.mRealView.getMap().e(0.0f);
    }

    private void setMapCenter(int i, int i2, IncludePadding includePadding, float f) {
        if (includePadding != null) {
            GeoPointHD geoPointHD = new GeoPointHD(cfg.a(this.mRealView.getMap(), new GeoPoint(i, i2), (includePadding.right - includePadding.left) / 2, (includePadding.bottom - includePadding.top) / 2, f, 0));
            H5MapUtils.setMapCenter(geoPointHD.getLatitude(), geoPointHD.getLongitude(), this.mRealView.getMap());
            return;
        }
        GeoPointHD geoPointHD2 = new GeoPointHD(i, i2);
        H5MapUtils.setMapCenter(geoPointHD2.getLatitude(), geoPointHD2.getLongitude(), this.mRealView.getMap());
    }

    private void updateMapDegrees(Float f, Float f2) {
        if (f != null) {
            this.mRealView.getMap().e(f.floatValue());
        }
        if (f2 != null) {
            this.mRealView.getMap().g(f2.floatValue());
        }
    }

    private void updateSetting4Guesture(Integer num) {
        if (num != null) {
            this.mRealView.setDisableGesture(num.intValue() == 0);
        }
    }

    private void updateSetting4ShowScale(Integer num) {
        if (num != null) {
            this.mRealView.setShowScaleView(num.intValue() != 0);
        }
    }

    private void updateSetting4ShowCompass(Integer num) {
        if (num != null) {
            this.mRealView.setShowCompass(num.intValue() != 0);
        }
    }

    private void updateSetting4tiltGestures(Integer num) {
        if (num != null) {
            this.mRealView.setEnableTiltGesture(num.intValue() != 0);
        }
    }

    private void updateSetting4traffic(Integer num) {
        if (num != null) {
            this.mRealView.getMap().b(num.intValue() != 0);
        }
    }

    private void updateSetting4LogoPosition(LogoPosition logoPosition) {
        if (logoPosition != null) {
            this.mRealView.setLogoPosition(convertDp((double) logoPosition.centerX), convertDp((double) logoPosition.centerY));
        }
    }

    private void updateSetting4MapText(Integer num) {
        if (num != null) {
            this.mRealView.getMap().i(num.intValue() != 0);
        }
    }

    private void updateSetting4ZoomGesture(Integer num) {
        if (num != null) {
            this.mRealView.setEnableZoomGesture(num.intValue() != 0);
        }
    }

    private void updateSetting4ScrollGesture(Integer num) {
        if (num != null) {
            this.mRealView.setEnableScrollGesture(num.intValue() != 0);
        }
    }

    private void updateSetting4RotateGesture(Integer num) {
        if (num != null) {
            this.mRealView.setEnableRotateGesture(num.intValue() != 0);
        }
    }
}
