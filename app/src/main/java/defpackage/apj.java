package defpackage;

import com.autonavi.bundle.airticket.api.IAirTicketPage;
import com.autonavi.bundle.airticket.page.AjxAirTicketResultPage;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.ajx3.Ajx3Page;

/* renamed from: apj reason: default package */
/* compiled from: IAirTicketPageImpl */
public final class apj implements IAirTicketPage {

    /* renamed from: apj$a */
    /* compiled from: IAirTicketPageImpl */
    static class a {
        static apj a = new apj();
    }

    public final Class<? extends AbstractBasePage> a(int i) {
        switch (i) {
            case 0:
                return AjxAirTicketResultPage.class;
            case 1:
                return Ajx3Page.class;
            default:
                return null;
        }
    }

    public final void a(PageBundle pageBundle, int i) {
        Class<? extends AbstractBasePage> a2 = a(1);
        if (a2 != null) {
            AMapPageUtil.getPageContext().startPageForResult(a2, pageBundle, i);
        }
    }
}
