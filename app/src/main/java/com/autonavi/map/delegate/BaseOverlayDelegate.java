package com.autonavi.map.delegate;

import com.autonavi.ae.gmap.gloverlay.BaseOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay;
import java.util.Map;

public class BaseOverlayDelegate<T extends GLOverlay, E> extends BaseOverlay<T, E> {
    public bty mMapView;

    public void addItem(E e) {
    }

    public void iniGLOverlay() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public BaseOverlayDelegate(int i, bty bty) {
        super(i, bty.c());
    }

    public BaseOverlayDelegate(bty bty) {
        super(bty.j(), bty.c());
    }

    /* access modifiers changed from: protected */
    public void initMapViewDelegate() {
        this.mMapView = (bty) ((Map) this.mMapView.K).get(Integer.valueOf(this.mEngineID));
    }
}
