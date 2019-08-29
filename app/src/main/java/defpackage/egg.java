package defpackage;

import com.autonavi.bundle.sharebike.api.IBikePage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.sharebike.page.ShareBikePage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;

/* renamed from: egg reason: default package */
/* compiled from: IBikePageImpl */
final class egg implements IBikePage {

    /* renamed from: egg$a */
    /* compiled from: IBikePageImpl */
    static class a {
        static egg a = new egg();
    }

    egg() {
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        switch (i) {
            case 1:
                return ShareRidingMapPage.class;
            case 2:
                return ShareRidingFinishPage.class;
            case 3:
                return ShareBikePage.class;
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
