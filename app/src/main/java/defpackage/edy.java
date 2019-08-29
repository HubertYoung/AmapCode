package defpackage;

import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.ride.page.RouteFootRideMapPage;

@Router({"healthyRide"})
/* renamed from: edy reason: default package */
/* compiled from: HealthyRideRouter */
public class edy extends esk {
    public boolean start(ese ese) {
        if (Boolean.parseBoolean(ehs.b("share_bike_riding_status_id"))) {
            bdf bdf = (bdf) a.a.a(bdf.class);
            if (bdf != null) {
                bdf.b().a(1, null);
            }
        } else {
            startPage(RouteFootRideMapPage.class, (PageBundle) null);
        }
        return true;
    }
}
