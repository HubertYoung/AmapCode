package com.autonavi.minimap.agroup.overlay.manager;

import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;

public class EFAgroupOverlayManager$3 implements Callback<Status> {
    final /* synthetic */ cji a;

    public void error(Throwable th, boolean z) {
    }

    public EFAgroupOverlayManager$3(cji cji) {
        this.a = cji;
    }

    public void callback(Status status) {
        if (status == Status.ON_LOCATION_OK) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (this.a.r != latestPosition.getLongitude() || this.a.s != latestPosition.getLatitude()) {
                this.a.r = latestPosition.getLongitude();
                this.a.s = latestPosition.getLatitude();
                cji.e(this.a);
            }
        }
    }
}
