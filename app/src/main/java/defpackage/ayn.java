package defpackage;

import android.location.Location;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.sdk.location.LocationInstrument;

/* renamed from: ayn reason: default package */
/* compiled from: NaviAddressWrapper */
public final class ayn {
    private NaviAddress a;

    public ayn(NaviAddress naviAddress) {
        this.a = naviAddress;
    }

    public final int a() {
        if (d()) {
            return 2;
        }
        switch (ayq.a(e(), this.a.home.getHome(), this.a.company.getCompany())) {
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                return 2;
        }
    }

    public final boolean b() {
        return (this.a == null || this.a.home == null || this.a.home.source != 0) ? false : true;
    }

    public final boolean c() {
        return (this.a == null || this.a.company == null || this.a.company.source != 0) ? false : true;
    }

    public final boolean d() {
        return this.a == null || this.a.home == null || this.a.company == null;
    }

    private static GeoPoint e() {
        Location latestLocation = LocationInstrument.getInstance().getLatestLocation();
        return new GeoPoint(latestLocation.getLongitude(), latestLocation.getLatitude());
    }
}
