package defpackage;

import com.autonavi.bundle.healthyrun.api.IHRunPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.run.page.RouteFootRunMapPage;
import com.autonavi.minimap.route.run.page.RunFinishMapPage;
import com.autonavi.minimap.route.run.page.RunningHistoryPage;

/* renamed from: eff reason: default package */
/* compiled from: IHRunPageImpl */
final class eff implements IHRunPage {

    /* renamed from: eff$a */
    /* compiled from: IHRunPageImpl */
    static class a {
        static eff a = new eff();
    }

    eff() {
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        switch (i) {
            case 1:
                return RouteFootRunMapPage.class;
            case 2:
                return RunFinishMapPage.class;
            case 3:
                return RunningHistoryPage.class;
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
