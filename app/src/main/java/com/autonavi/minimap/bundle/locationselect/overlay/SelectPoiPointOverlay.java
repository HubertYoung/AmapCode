package com.autonavi.minimap.bundle.locationselect.overlay;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

public class SelectPoiPointOverlay extends PointOverlay<PointOverlayItem> {
    public SelectPoiPointOverlay(bty bty) {
        super(bty);
    }

    public void addPoiPointItem(POI poi, int i) {
        if (poi != null) {
            PointOverlayItem pointOverlayItem = new PointOverlayItem(poi.getPoint());
            pointOverlayItem.mDefaultMarker = createMarker(i, 5);
            addItem(pointOverlayItem);
        }
    }
}
