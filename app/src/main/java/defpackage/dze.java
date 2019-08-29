package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.route.coach.page.CoachResultListPage;
import com.autonavi.minimap.route.coach.page.CoachStationListPage;

@BundleInterface(atw.class)
/* renamed from: dze reason: default package */
/* compiled from: CoachService */
public class dze extends esi implements atw {
    public final Class b() {
        return CoachResultListPage.class;
    }

    public final void a(AbstractBasePage abstractBasePage) {
        CoachStationListPage.a(abstractBasePage);
    }

    public final void a(AbstractBasePage abstractBasePage, String str) {
        CoachStationListPage.a(abstractBasePage, str);
    }

    public final atv a() {
        return a.a;
    }

    public final aty c() {
        return a.a;
    }

    public final atx d() {
        if (dzg.a == null) {
            dzg.a = new dzg();
        }
        return dzg.a;
    }
}
