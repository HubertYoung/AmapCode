package defpackage;

import com.autonavi.bundle.footnavi.api.IFootNaviPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.foot.page.AjxFootEndPage;
import com.autonavi.minimap.route.foot.page.AjxFootNaviPage;

/* renamed from: eci reason: default package */
/* compiled from: IFootNaviPageImpl */
public final class eci implements IFootNaviPage {

    /* renamed from: eci$a */
    /* compiled from: IFootNaviPageImpl */
    static class a {
        static eci a = new eci();
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        if (i == 1) {
            return AjxFootNaviPage.class;
        }
        if (i != 3) {
            return null;
        }
        return AjxFootEndPage.class;
    }

    public final void a(int i, PageBundle pageBundle) {
        Class<? extends AbstractBasePage> a2 = a(i);
        if (a2 != null) {
            AMapPageUtil.getPageContext().startPage(a2, pageBundle);
        }
    }

    public final boolean a(bid bid) {
        return bid instanceof AjxFootNaviPage;
    }

    public final boolean a(String str) {
        return AjxFootEndPage.class.getName().equals(str);
    }
}
