package com.autonavi.minimap.route.sharebike.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

public class ShareBikeShadowOverlay extends PointOverlay {
    public ShareBikeShadowOverlay(bty bty) {
        super(bty);
        this.mOverlayDefaultMarker = createMarker(R.drawable.sharebike_pop_shadow, 4);
    }

    public void drawShadow(GeoPoint geoPoint) {
        if (geoPoint != null) {
            addItem(new PointOverlayItem(geoPoint));
        }
    }
}
