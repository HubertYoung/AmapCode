package com.autonavi.minimap.route.sharebike.overlay;

import com.autonavi.minimap.route.common.overlay.AbstractRouteLineOverlay;

public class RouteShareBikeLineOverlay extends AbstractRouteLineOverlay {
    public int getArrowLineItemType() {
        return 6;
    }

    public RouteShareBikeLineOverlay(bty bty) {
        super(bty, 106);
    }

    public RouteShareBikeLineOverlay(int i, bty bty) {
        super(i, bty, 106);
    }
}
