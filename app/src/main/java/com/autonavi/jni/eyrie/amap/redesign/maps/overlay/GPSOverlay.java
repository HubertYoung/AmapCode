package com.autonavi.jni.eyrie.amap.redesign.maps.overlay;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.BaseOverlay.OverlayType;

public class GPSOverlay extends BaseOverlay {
    protected GPSOverlay(BaseLayer baseLayer, String str) {
        super(baseLayer, str);
    }

    /* access modifiers changed from: protected */
    public void initOverlay() {
        this.mNativePtr = this.mLayer.createNativeOverlay(OverlayType.GPS, this.mName);
        this.mLayer.setGPSOverlay(this);
    }
}
