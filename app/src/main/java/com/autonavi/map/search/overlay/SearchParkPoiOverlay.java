package com.autonavi.map.search.overlay;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;

public class SearchParkPoiOverlay extends PointOverlay<POIOverlayItem> {
    private static final long serialVersionUID = -6665944711902275150L;

    public SearchParkPoiOverlay(bty bty) {
        super(bty);
        setMoveToFocus(false);
    }

    public void addPOI(POI poi, int i) {
        if (poi != null) {
            POIOverlayItem pOIOverlayItem = new POIOverlayItem(poi);
            pOIOverlayItem.mDefaultMarker = createMarker(i, 5);
            addItem(pOIOverlayItem);
        }
    }
}
