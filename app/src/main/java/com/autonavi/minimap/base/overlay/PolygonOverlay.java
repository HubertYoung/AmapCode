package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLPolygonOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.base.overlay.PolygonOverlayItem;
import java.util.ArrayList;

public abstract class PolygonOverlay<E extends PolygonOverlayItem> extends BaseOverlayDelegate<GLPolygonOverlay, E> {
    public PolygonOverlay(bty bty) {
        super(bty);
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLPolygonOverlay(this.mMapView.ad(), this.mMapView.c(), hashCode());
    }

    public void addItem(PolygonOverlayItem polygonOverlayItem) {
        if (polygonOverlayItem != null && polygonOverlayItem.points.length > 1) {
            this.mItemList.add(polygonOverlayItem);
            ((GLPolygonOverlay) this.mGLOverlay).addItem(GeoPoint.geoPoints2GlGeoPoints(polygonOverlayItem.points), polygonOverlayItem.color);
        }
    }

    public void addPolygonItem(PolygonOverlayItem polygonOverlayItem) {
        addItem(polygonOverlayItem);
    }

    public void addPolygon(int[] iArr, int[] iArr2, int i) {
        GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            geoPointArr[i2] = new GeoPoint(i2, i2);
            geoPointArr[i2].x = iArr[i2];
            geoPointArr[i2].y = iArr2[i2];
        }
        addPolygonItem(new PolygonOverlayItem(geoPointArr, i));
    }

    public void addPolygon(ArrayList<GeoPoint> arrayList, int i) {
        GeoPoint[] geoPointArr = new GeoPoint[arrayList.size()];
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            geoPointArr[i2] = arrayList.get(i2);
        }
        addPolygonItem(new PolygonOverlayItem(geoPointArr, i));
    }

    public PolygonOverlayItem getPolygonItem(int i) {
        return (PolygonOverlayItem) getItem(i);
    }
}
