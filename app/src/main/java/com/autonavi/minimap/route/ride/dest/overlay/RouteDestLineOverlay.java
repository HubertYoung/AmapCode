package com.autonavi.minimap.route.ride.dest.overlay;

import com.autonavi.minimap.route.common.overlay.AbstractRouteLineOverlay;

public class RouteDestLineOverlay extends AbstractRouteLineOverlay {
    public int getRouteType() {
        return 103;
    }

    public RouteDestLineOverlay(bty bty) {
        super(bty, 103);
    }

    public RouteDestLineOverlay(int i, bty bty) {
        super(i, bty, 103);
    }
}
