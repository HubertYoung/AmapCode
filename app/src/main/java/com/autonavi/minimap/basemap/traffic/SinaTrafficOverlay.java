package com.autonavi.minimap.basemap.traffic;

import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlay;

public class SinaTrafficOverlay extends PointOverlay {
    public SinaTrafficOverlay(bty bty) {
        super(bty);
        resumeMarker();
    }

    public void resumeMarker() {
        super.resumeMarker();
        this.mOverlayDefaultMarker = createMarker(R.drawable.b_poi, 5);
    }

    public void setItem(POIOverlayItem pOIOverlayItem) {
        if (this.mItemList.size() == 0) {
            addItem(pOIOverlayItem);
            return;
        }
        clear();
        addItem(pOIOverlayItem);
    }
}
