package defpackage;

import com.autonavi.bundle.footresult.api.IFootResultPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.foot.page.AjxFootBrowserPage;
import com.autonavi.minimap.route.foot.page.AjxFootMapPage;

/* renamed from: ecj reason: default package */
/* compiled from: IFootResultPageImpl */
final class ecj implements IFootResultPage {

    /* renamed from: ecj$a */
    /* compiled from: IFootResultPageImpl */
    static class a {
        static ecj a = new ecj();
    }

    ecj() {
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        if (i == 1) {
            return AjxFootMapPage.class;
        }
        if (i != 3) {
            return null;
        }
        return AjxFootBrowserPage.class;
    }

    public final void a(int i, PageBundle pageBundle) {
        Class<? extends AbstractBasePage> a2 = a(i);
        if (a2 != null) {
            AMapPageUtil.getPageContext().startPage(a2, pageBundle);
        }
    }
}
