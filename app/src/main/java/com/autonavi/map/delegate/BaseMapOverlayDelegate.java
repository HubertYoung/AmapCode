package com.autonavi.map.delegate;

import android.content.Context;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay;
import java.util.Map;

public class BaseMapOverlayDelegate<T extends GLOverlay, E> extends BaseMapOverlay<T, E> {
    public bty mMapView;

    public void addItem(E e) {
    }

    public void iniGLOverlay() {
    }

    public void resumeMarker() {
    }

    public BaseMapOverlayDelegate(Context context, bty bty) {
        super(bty.ad(), context, bty.c());
    }

    /* access modifiers changed from: protected */
    public void initMapViewDelegate() {
        this.mMapView = (bty) ((Map) this.mMapView.K).get(Integer.valueOf(this.mEngineID));
    }
}
