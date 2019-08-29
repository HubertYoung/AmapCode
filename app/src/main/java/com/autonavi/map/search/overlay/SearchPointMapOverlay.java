package com.autonavi.map.search.overlay;

import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

public class SearchPointMapOverlay extends PointOverlay<PointOverlayItem> {
    private static final long serialVersionUID = 5080943270369895323L;

    public SearchPointMapOverlay(bty bty) {
        super(bty);
    }

    public void addPointMapItem(POI poi) {
        if (poi != null) {
            PointOverlayItem pointOverlayItem = new PointOverlayItem(poi.getPoint());
            pointOverlayItem.mDefaultMarker = createMarker(R.drawable.b_poi, 5);
            addItem(pointOverlayItem);
        }
    }
}
