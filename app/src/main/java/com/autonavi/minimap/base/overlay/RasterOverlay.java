package com.autonavi.minimap.base.overlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLRasterOverlay;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.base.overlay.RasterOverlayItem;

public class RasterOverlay<E extends RasterOverlayItem> extends BaseOverlayDelegate<GLRasterOverlay, Object> {
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_AFTER_BACKGROUND = 10;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_AFTER_BUILDING = 80;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_AFTER_LABEL = 120;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_AFTER_ROAD = 40;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_BEFORE_BACKGROUND = 0;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_BEFORE_BUILDING = 60;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_BEFORE_LABEL = 100;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_BEFORE_ROAD = 20;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_RENDER_MAPOVER = 140;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_TOP = 1000;
    protected static final int OVERLAY_DRAWLAYER_PRIORITY_WIDGET = 65535;

    public RasterOverlay(int i, bty bty) {
        super(i, bty);
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    public void iniGLOverlay() {
        initMapViewDelegate();
        this.mGLOverlay = new GLRasterOverlay(this.mEngineID, this.mMapView.c(), hashCode());
    }

    private void createResTexture(RasterOverlayItem rasterOverlayItem) {
        if (rasterOverlayItem != null && rasterOverlayItem.getResId() > 0) {
            if (rasterOverlayItem.getBitmap() != null) {
                SimpleMarkerFactory.createRasterTexure(this.mMapView, rasterOverlayItem.getResId(), rasterOverlayItem.getBitmap());
            } else {
                SimpleMarkerFactory.createRasterTexure(this.mMapView, rasterOverlayItem.getResId());
            }
        }
    }

    public long addItem(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return 0;
        }
        long addRasterItem = ((GLRasterOverlay) this.mGLOverlay).addRasterItem(bArr);
        if (this.mItemList != null) {
            this.mItemList.add(Long.valueOf(addRasterItem));
        }
        return 0;
    }

    public long addItem(RasterOverlayItem rasterOverlayItem) {
        if (rasterOverlayItem == null || rasterOverlayItem.getResId() <= 0 || this.mGLOverlay == null) {
            return 0;
        }
        createResTexture(rasterOverlayItem);
        long addRasterItemWithParam = ((GLRasterOverlay) this.mGLOverlay).addRasterItemWithParam(rasterOverlayItem.getParam());
        if (this.mItemList != null) {
            this.mItemList.add(Long.valueOf(addRasterItemWithParam));
        }
        return addRasterItemWithParam;
    }

    public void removeItem(long j) {
        if (this.mGLOverlay != null) {
            ((GLRasterOverlay) this.mGLOverlay).removeRasterItem(j);
        }
    }

    public void setOverlayPriority(int i, int i2) {
        if (this.mGLOverlay != null) {
            ((GLRasterOverlay) this.mGLOverlay).setOverlayPriority(i, i2);
        }
    }
}
