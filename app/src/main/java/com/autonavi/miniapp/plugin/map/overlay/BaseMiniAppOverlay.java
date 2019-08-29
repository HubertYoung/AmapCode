package com.autonavi.miniapp.plugin.map.overlay;

import com.autonavi.ae.gmap.gloverlay.BaseOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlay;

public abstract class BaseMiniAppOverlay<T extends GLOverlay, E> extends BaseOverlay<T, E> {
    public bty mMapView;

    public void onPause() {
    }

    public void onResume() {
    }

    public BaseMiniAppOverlay(bty bty) {
        super(bty.j(), bty.c());
        this.mMapView = bty;
    }

    public final int generateMarkerId(int i) {
        throw new RuntimeException("miniapp must use it's own texture id management!");
    }
}
