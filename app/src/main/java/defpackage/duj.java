package defpackage;

import com.autonavi.bundle.busline.api.IBusLinePage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.bus.busline.page.BusLineDetailPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineResultPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineSearchPage;
import com.autonavi.minimap.route.bus.busline.page.BusLineStationMapPage;

/* renamed from: duj reason: default package */
/* compiled from: IBusLinePageImpl */
public final class duj implements IBusLinePage {

    /* renamed from: duj$a */
    /* compiled from: IBusLinePageImpl */
    static class a {
        static duj a = new duj();
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        switch (i) {
            case 1:
                return BusLineDetailPage.class;
            case 2:
                return BusLineResultPage.class;
            case 3:
                return BusLineStationMapPage.class;
            case 4:
                return BusLineSearchPage.class;
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
