package com.autonavi.miniapp.plugin.map.overlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLRasterOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLRasterOverlay.GLRasterOverlayParam;

public abstract class BaseRasterMiniAppOverlay extends BaseMiniAppOverlay<GLRasterOverlay, GLRasterOverlayParam> {
    public BaseRasterMiniAppOverlay(bty bty) {
        super(bty);
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    public void iniGLOverlay() {
        this.mGLOverlay = new GLRasterOverlay(this.mEngineID, akq.b(), hashCode());
    }

    public void addItem(GLRasterOverlayParam gLRasterOverlayParam) {
        if (gLRasterOverlayParam != null && gLRasterOverlayParam.mResourceID > 0 && this.mGLOverlay != null) {
            ((GLRasterOverlay) this.mGLOverlay).addRasterItemWithParam(gLRasterOverlayParam);
            if (this.mItemList != null) {
                this.mItemList.add(gLRasterOverlayParam);
            }
        }
    }

    public void setOverlayPriority(int i, int i2) {
        if (this.mGLOverlay != null) {
            ((GLRasterOverlay) this.mGLOverlay).setOverlayPriority(i, i2);
        }
    }
}
