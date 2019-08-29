package com.autonavi.minimap.base.overlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLGpsOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;

public abstract class AbstractGpsOverlay<E> extends BaseOverlayDelegate<GLGpsOverlay, E> implements btt {
    public void onPause() {
    }

    public void onResume() {
    }

    public AbstractGpsOverlay(bty bty) {
        super(bty);
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLGpsOverlay(this.mMapView.ad(), this.mMapView.c(), hashCode());
    }

    public Marker createMarker(int i, int i2) {
        return createMarker(i, i2, 0.0f, 0.0f);
    }

    public Marker createMarker(int i, int i2, float f, float f2) {
        return SimpleMarkerFactory.createMarker(i, i2, f, f2, this.mMapView);
    }
}
