package com.autonavi.minimap.base.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLArcOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.base.overlay.ArcOverlayItem;

public class ArcOverlay<E extends ArcOverlayItem> extends BaseOverlayDelegate<GLArcOverlay, E> {
    public void onPause() {
    }

    public void onResume() {
    }

    public ArcOverlay(bty bty) {
        super(bty);
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLArcOverlay(this.mMapView.ad(), this.mMapView.c(), hashCode());
    }

    public void setShowTop(boolean z) {
        ((GLArcOverlay) this.mGLOverlay).setOverlayOnTop(true);
    }

    public void addItem(E e) {
        this.mItemList.add(e);
        ((GLArcOverlay) this.mGLOverlay).addArcOverlayItem(e.mX, e.mY, e.mRadius, getDefaultMarkerId(e), e.mBorder, e.mColor, e.mColorBorder);
    }

    public E getArcItem(int i) {
        try {
            int size = this.mItemList.size();
            if (size <= 0 || i < 0 || i >= size) {
                return null;
            }
            return (ArcOverlayItem) this.mItemList.get(i);
        } catch (Exception unused) {
            return null;
        }
    }

    public void setItemPosition(GeoPoint geoPoint) {
        if (geoPoint != null) {
            ((GLArcOverlay) this.mGLOverlay).setArcItemPosition(geoPoint.x, geoPoint.y, true);
        }
    }

    private int getDefaultMarkerId(E e) {
        if (e.mDefaultMarker != null) {
            return e.mDefaultMarker.mID;
        }
        return -999;
    }

    public Marker createMarker(int i, int i2) {
        return SimpleMarkerFactory.createMarker(i, i2, 0.0f, 0.0f, this.mMapView);
    }
}
