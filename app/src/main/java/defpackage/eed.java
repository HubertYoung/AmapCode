package defpackage;

import com.autonavi.bundle.ridenavi.api.IRideNaviPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.ride.dest.page.AjxRideEndPage;
import com.autonavi.minimap.route.ride.dest.page.AjxRideNaviPageNew;

/* renamed from: eed reason: default package */
/* compiled from: RideNaviPageImpl */
public final class eed implements IRideNaviPage {

    /* renamed from: eed$a */
    /* compiled from: RideNaviPageImpl */
    static class a {
        static eed a = new eed();
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        switch (i) {
            case 1:
                return AjxRideNaviPageNew.class;
            case 2:
                return AjxRideEndPage.class;
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

    public final boolean a(bid bid) {
        return bid instanceof AjxRideNaviPageNew;
    }

    public final boolean a(String str) {
        return AjxRideEndPage.class.getName().equals(str);
    }
}
