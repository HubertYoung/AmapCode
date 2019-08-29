package com.autonavi.miniapp.plugin.map.overlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLLineOverlay;

public abstract class BaseMiniAppLineOverlay extends BaseMiniAppOverlay<GLLineOverlay, MiniAppLineOverlayItem> {
    public BaseMiniAppLineOverlay(bty bty) {
        super(bty);
    }

    public void iniGLOverlay() {
        this.mGLOverlay = new GLLineOverlay(this.mEngineID, akq.b(), hashCode());
    }

    public void addItem(MiniAppLineOverlayItem miniAppLineOverlayItem) {
        if (miniAppLineOverlayItem != null && miniAppLineOverlayItem.mPoints.length > 0) {
            ((GLLineOverlay) this.mGLOverlay).addLineItem(miniAppLineOverlayItem.mLineProperty);
            this.mItemList.add(miniAppLineOverlayItem);
        }
    }
}
