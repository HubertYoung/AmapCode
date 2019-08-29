package com.autonavi.minimap.base.overlay;

import android.graphics.Bitmap;
import com.autonavi.jni.ae.gmap.gloverlay.GLRasterOverlay.GLRasterOverlayParam;

public class RasterOverlayItem {
    private Bitmap bitmap;
    private int mResId;
    private GLRasterOverlayParam param;

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap2) {
        this.bitmap = bitmap2;
    }

    public RasterOverlayItem(GLRasterOverlayParam gLRasterOverlayParam, int i) {
        this.param = gLRasterOverlayParam;
        this.mResId = i;
    }

    public void setParam(GLRasterOverlayParam gLRasterOverlayParam) {
        this.param = gLRasterOverlayParam;
    }

    public void setResId(int i) {
        this.mResId = i;
    }

    public GLRasterOverlayParam getParam() {
        return this.param;
    }

    public int getResId() {
        return this.mResId;
    }
}
