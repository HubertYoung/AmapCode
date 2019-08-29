package com.amap.bundle.environmentmap.overlay;

import android.content.Context;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLRasterOverlay;

public class EnvironmentGLRasterOverlay extends BaseMapOverlay<GLRasterOverlay, Object> {
    public void addItem(Object obj) {
    }

    public void resumeMarker() {
    }

    public EnvironmentGLRasterOverlay(int i, Context context, akq akq) {
        super(i, context, akq);
    }

    public void iniGLOverlay() {
        this.mGLOverlay = new GLRasterOverlay(this.mEngineID, this.mMapView, hashCode());
    }
}
