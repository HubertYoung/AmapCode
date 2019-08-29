package com.amap.bundle.schoolbus.page;

import com.autonavi.minimap.ajx3.Ajx3Page;

public class SchoolBusAjx3Page extends Ajx3Page {
    public void recoveryMapState() {
        super.recoveryMapState();
        bty mapView = getMapManager().getMapView();
        bqx bqx = (bqx) ank.a(bqx.class);
        if (bqx != null) {
            bqx.a(this.mStoreMapStateParam.mTrafficState, mapView, getContext());
        }
    }
}
