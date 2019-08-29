package defpackage;

import com.autonavi.bundle.healthyride.api.IHRidePage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.ride.page.RideFinishMapPage;
import com.autonavi.minimap.route.ride.page.RideHistoryPage;
import com.autonavi.minimap.route.ride.page.RouteFootRideMapPage;

/* renamed from: eea reason: default package */
/* compiled from: IHRidePageImpl */
public final class eea implements IHRidePage {

    /* renamed from: eea$a */
    /* compiled from: IHRidePageImpl */
    static class a {
        static eea a = new eea();
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        switch (i) {
            case 1:
                return RouteFootRideMapPage.class;
            case 2:
                return RideFinishMapPage.class;
            case 3:
                return RideHistoryPage.class;
            default:
                return null;
        }
    }

    public final void a(int i, PageBundle pageBundle) {
        Class<? extends AbstractBasePage> a2 = a(i);
        if (a2 != null) {
            AMapPageUtil.getPageContext().startPage(a2, pageBundle);
        }
    }
}
