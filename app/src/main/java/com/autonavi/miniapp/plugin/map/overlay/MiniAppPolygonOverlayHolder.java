package com.autonavi.miniapp.plugin.map.overlay;

import android.text.TextUtils;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.jni.ae.gmap.utils.GLMapUtil;
import com.autonavi.miniapp.plugin.map.property.CirclesPropertyProcessor.Circle;
import com.autonavi.miniapp.plugin.map.property.Point;
import com.autonavi.miniapp.plugin.map.property.PolygonPropertyProcessor.Polygon;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureCacheManager;
import com.autonavi.miniapp.plugin.map.texture.MiniAppTextureFactory;
import com.autonavi.miniapp.plugin.map.util.H5MapUtils;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PolygonOverlayItem;
import com.autonavi.minimap.map.overlayholder.OverlayHolderImpl;
import java.util.ArrayList;
import java.util.List;

public class MiniAppPolygonOverlayHolder {
    private static final String TAG = "MiniAppPolygonOverlayHolder";
    private InnerLineOverlay mLineOverlay;
    private InnerLineOverlay mLineOverlayForCircle;
    private bty mMapView;
    private OverlayHolderImpl mOverlayHolder;
    private InnerPolygonOverlay mPolygonOverlay;
    private InnerPolygonOverlay mPolygonOverlayForCircle;
    private MiniAppTextureCacheManager mTextureCacheManager;

    class InnerLineOverlay extends BaseMiniAppLineOverlay {
        private InnerLineOverlay(bty bty) {
            super(bty);
        }
    }

    class InnerPolygonOverlay extends BasePolygonMiniAppOverlay {
        private InnerPolygonOverlay(bty bty) {
            super(bty);
        }
    }

    public MiniAppPolygonOverlayHolder(bty bty, OverlayHolderImpl overlayHolderImpl, MiniAppTextureCacheManager miniAppTextureCacheManager) {
        this.mMapView = bty;
        this.mOverlayHolder = overlayHolderImpl;
        this.mTextureCacheManager = miniAppTextureCacheManager;
        this.mPolygonOverlay = new InnerPolygonOverlay(bty);
        this.mLineOverlay = new InnerLineOverlay(bty);
        this.mPolygonOverlayForCircle = new InnerPolygonOverlay(bty);
        this.mLineOverlayForCircle = new InnerLineOverlay(bty);
    }

    public void addMiniAppPolygon(List<Polygon> list) {
        if (list != null) {
            for (Polygon next : list) {
                if (next.points != null && next.points.size() > 2) {
                    Point point = next.points.get(0);
                    Point point2 = next.points.get(next.points.size() - 1);
                    if (point.latitude == point2.latitude && point.longitude == point2.longitude) {
                        point2 = next.points.get(next.points.size() - 2);
                    }
                    Point point3 = new Point();
                    point3.longitude = (point.longitude + point2.longitude) / 2.0d;
                    point3.latitude = (point.latitude + point2.latitude) / 2.0d;
                    next.points.add(0, point3);
                    next.points.add(point3);
                }
                ArrayList arrayList = new ArrayList(next.points.size());
                for (Point next2 : next.points) {
                    arrayList.add(new GeoPoint(next2.longitude, next2.latitude));
                }
                this.mPolygonOverlay.addItem(new PolygonOverlayItem((GeoPoint[]) arrayList.toArray(new GeoPoint[arrayList.size()]), !TextUtils.isEmpty(next.fillColor) ? H5MapUtils.convertRGBAColor(next.fillColor) : 0));
                int convertRGBAColor = !TextUtils.isEmpty(next.color) ? H5MapUtils.convertRGBAColor(next.color) : 0;
                MiniAppLineOverlayItem miniAppLineOverlayItem = new MiniAppLineOverlayItem(9, (GeoPoint[]) arrayList.toArray(new GeoPoint[arrayList.size()]), (int) next.width);
                miniAppLineOverlayItem.setFillLineColor(convertRGBAColor);
                miniAppLineOverlayItem.setFillLineId(R.drawable.map_lr);
                if (((Integer) this.mTextureCacheManager.getTextureCache(this.mMapView, 0, Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f))) == null) {
                    MiniAppTextureFactory.createLineTexture(this.mMapView, miniAppLineOverlayItem.mLineType, miniAppLineOverlayItem.mLineProperty.f);
                    this.mTextureCacheManager.addTextureCache(this.mMapView, 0, Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f), Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f));
                }
                this.mLineOverlay.addItem(miniAppLineOverlayItem);
            }
        }
    }

    public void addMiniAppCircle(List<Circle> list) {
        if (list != null) {
            for (Circle next : list) {
                GeoPoint geoPoint = new GeoPoint(next.longitude, next.latitude);
                GeoPoint[] polylineCircle = getPolylineCircle(geoPoint.x, geoPoint.y, (float) next.radius);
                this.mPolygonOverlayForCircle.addItem(new PolygonOverlayItem(polylineCircle, !TextUtils.isEmpty(next.fillColor) ? H5MapUtils.convertRGBAColor(next.fillColor) : 0));
                int convertRGBAColor = !TextUtils.isEmpty(next.color) ? H5MapUtils.convertRGBAColor(next.color) : 0;
                MiniAppLineOverlayItem miniAppLineOverlayItem = new MiniAppLineOverlayItem(9, polylineCircle, (int) next.strokeWidth);
                miniAppLineOverlayItem.setFillLineColor(convertRGBAColor);
                miniAppLineOverlayItem.setFillLineId(R.drawable.map_lr);
                if (((Integer) this.mTextureCacheManager.getTextureCache(this.mMapView, 0, Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f))) == null) {
                    MiniAppTextureFactory.createLineTexture(this.mMapView, miniAppLineOverlayItem.mLineType, miniAppLineOverlayItem.mLineProperty.f);
                    this.mTextureCacheManager.addTextureCache(this.mMapView, 0, Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f), Integer.valueOf(miniAppLineOverlayItem.mLineProperty.f));
                }
                this.mLineOverlayForCircle.addItem(miniAppLineOverlayItem);
            }
        }
    }

    public void addOverlay() {
        this.mOverlayHolder.simpleOverlayHolder.addOverlay(this.mPolygonOverlay, true);
        this.mOverlayHolder.simpleOverlayHolder.addOverlay(this.mLineOverlay, true);
        this.mOverlayHolder.simpleOverlayHolder.addOverlay(this.mPolygonOverlayForCircle, true);
        this.mOverlayHolder.simpleOverlayHolder.addOverlay(this.mLineOverlayForCircle, true);
    }

    public void removeOverlay() {
        this.mOverlayHolder.simpleOverlayHolder.removeOverlay(this.mPolygonOverlay);
        this.mOverlayHolder.simpleOverlayHolder.removeOverlay(this.mLineOverlay);
        this.mOverlayHolder.simpleOverlayHolder.removeOverlay(this.mPolygonOverlayForCircle);
        this.mOverlayHolder.simpleOverlayHolder.removeOverlay(this.mLineOverlayForCircle);
    }

    public void clear() {
        if (this.mPolygonOverlay != null) {
            this.mPolygonOverlay.clear();
        }
        if (this.mLineOverlay != null) {
            this.mLineOverlay.clear();
        }
        if (this.mPolygonOverlayForCircle != null) {
            this.mPolygonOverlayForCircle.clear();
        }
        if (this.mLineOverlayForCircle != null) {
            this.mLineOverlayForCircle.clear();
        }
    }

    public void clearPolygon() {
        if (this.mPolygonOverlay != null) {
            this.mPolygonOverlay.clear();
        }
        if (this.mLineOverlay != null) {
            this.mLineOverlay.clear();
        }
    }

    public void clearCircle() {
        if (this.mPolygonOverlayForCircle != null) {
            this.mPolygonOverlayForCircle.clear();
        }
        if (this.mLineOverlayForCircle != null) {
            this.mLineOverlayForCircle.clear();
        }
    }

    private GeoPoint[] getPolylineCircle(int i, int i2, float f) {
        int meterToP20 = GLMapUtil.meterToP20(i, i2, f);
        GeoPoint[] geoPointArr = new GeoPoint[MessageCode.MSG_LBS_ERROR];
        for (int i3 = 0; i3 < 720; i3++) {
            double d = (double) meterToP20;
            double d2 = (((double) i3) * 360.0d) / 720.0d;
            geoPointArr[i3] = new GeoPoint((int) (((double) i) + (Math.cos(Math.toRadians(d2)) * d)), (int) (((double) i2) + (d * Math.sin(Math.toRadians(d2)))));
        }
        geoPointArr[720] = geoPointArr[0];
        return geoPointArr;
    }
}
