package defpackage;

import com.autonavi.bundle.ugc.api.IUGCPage;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.ugc.page.BusNaviReviewPage;

/* renamed from: ekb reason: default package */
/* compiled from: IUGCPageImpl */
final class ekb implements IUGCPage {

    /* renamed from: ekb$a */
    /* compiled from: IUGCPageImpl */
    static class a {
        static ekb a = new ekb();
    }

    ekb() {
    }

    public final Class<? extends AbstractBasePage> a() {
        return BusNaviReviewPage.class;
    }
}
