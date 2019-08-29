package defpackage;

import com.autonavi.bundle.rideresult.api.IRideResultPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.ride.dest.page.AjxRideBrowserPage;
import com.autonavi.minimap.route.ride.dest.page.AjxRideMapPage;

/* renamed from: eef reason: default package */
/* compiled from: RideResultPageImpl */
public final class eef implements IRideResultPage {

    /* renamed from: eef$a */
    /* compiled from: RideResultPageImpl */
    static class a {
        static eef a = new eef();
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        switch (i) {
            case 1:
                return AjxRideMapPage.class;
            case 2:
                return AjxRideBrowserPage.class;
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
