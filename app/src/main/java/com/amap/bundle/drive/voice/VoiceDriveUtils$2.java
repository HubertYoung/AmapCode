package com.amap.bundle.drive.voice;

import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;

public class VoiceDriveUtils$2 implements Callback<GeoPoint> {
    final /* synthetic */ rz a;

    public VoiceDriveUtils$2(rz rzVar) {
        this.a = rzVar;
    }

    public final void callback(GeoPoint geoPoint) {
        if (geoPoint != null && this.a != null) {
            rz rzVar = this.a;
            LocationInstrument.getInstance().getLatestPosition();
            rzVar.a();
        }
    }

    public final void error(Throwable th, boolean z) {
        if (this.a != null) {
            this.a.b();
        }
    }
}
