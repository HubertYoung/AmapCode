package com.autonavi.minimap.route.run.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLNaviOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.AbstractNaviOverlay;

public class HelRunNaviOverlay extends AbstractNaviOverlay {
    public HelRunNaviOverlay(bty bty) {
        super(bty);
        resumeMarker();
    }

    public boolean clear() {
        clearAllMarker();
        this.mMapView.W();
        return super.clear();
    }

    private void clearAllMarker() {
        if (this.mGLOverlay != null) {
            ((GLNaviOverlay) this.mGLOverlay).SetNaviTexture(-1, -1, -1, -1, -1);
        }
    }

    public void resumeMarker() {
        createMarker(R.drawable.navi_run_locked, 4);
        createMarker(R.drawable.navi_map_flash, 4);
        updateNaviDirectionTexture();
    }

    private void updateNaviDirectionTexture() {
        ((GLNaviOverlay) this.mGLOverlay).SetNaviTexture(R.drawable.navi_run_locked, R.drawable.navi_map_flash, -1, -1, -1);
    }

    public void firstSetCarPosition(int i, int i2, int i3) {
        if (this.mGLOverlay != null) {
            ((GLNaviOverlay) this.mGLOverlay).setCarPosition(i, i2, i3);
        }
    }

    public void drawNaviLine_v3(GeoPoint geoPoint, int i, int i2) {
        this.mMapView.a((GLNaviOverlay) this.mGLOverlay, null, geoPoint, 0, i, -1, i2, geoPoint);
    }

    public void drawNaviLine_v2(GeoPoint geoPoint, int i) {
        this.mMapView.a((GLNaviOverlay) this.mGLOverlay, null, geoPoint, 0, i, -1, 0, geoPoint);
    }

    public void setNaviStateAsync(GLNaviOverlay gLNaviOverlay, amf amf, GeoPoint geoPoint, int i, int i2, int i3, int i4, GeoPoint geoPoint2) {
        if (this.mMapView != null) {
            this.mMapView.a(gLNaviOverlay, amf, geoPoint, i, i2, i3, i4, geoPoint2);
        }
    }

    public void updateCarPosition(int i, int i2, int i3) {
        setNaviStateAsync((GLNaviOverlay) this.mGLOverlay, null, new GeoPoint(i, i2), 0, i3, -1, (int) this.mMapView.I(), null);
    }
}
