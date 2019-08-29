package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: cmc reason: default package */
/* compiled from: LocationDelegate */
class cmc implements f {
    cmc() {
    }

    public final GeoPoint a() {
        return LocationInstrument.getInstance().getLatestPosition();
    }

    public final String b() {
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        lj ljVar = null;
        if (latestPosition != null) {
            try {
                li a = li.a();
                if (a != null) {
                    ljVar = a.b(latestPosition.getLongitude(), latestPosition.getLatitude());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ljVar == null ? "" : ljVar.b;
    }
}
