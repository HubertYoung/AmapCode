package com.autonavi.miniapp.plugin.map.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.gmap.gloverlay.GLPolygonOverlay;
import com.autonavi.minimap.base.overlay.PolygonOverlayItem;

public abstract class BasePolygonMiniAppOverlay extends BaseMiniAppOverlay<GLPolygonOverlay, PolygonOverlayItem> {
    public BasePolygonMiniAppOverlay(bty bty) {
        super(bty);
    }

    public void iniGLOverlay() {
        this.mGLOverlay = new GLPolygonOverlay(this.mEngineID, akq.b(), hashCode());
    }

    public void addItem(PolygonOverlayItem polygonOverlayItem) {
        if (polygonOverlayItem != null && polygonOverlayItem.points.length > 1) {
            this.mItemList.add(polygonOverlayItem);
            ((GLPolygonOverlay) this.mGLOverlay).addItem(GeoPoint.geoPoints2GlGeoPoints(polygonOverlayItem.points), polygonOverlayItem.color);
        }
    }
}
