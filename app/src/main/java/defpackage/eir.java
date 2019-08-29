package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.train.page.ExtTrainPlanListPage;
import com.autonavi.minimap.route.train.page.TrainDataPage;
import com.autonavi.minimap.route.train.page.TrainPlanListPage;
import com.autonavi.minimap.route.train.page.TrainStationListPage;

@BundleInterface(bdo.class)
/* renamed from: eir reason: default package */
/* compiled from: TrainService */
public class eir extends esi implements bdo {
    public final Class<? extends bid> b() {
        return ExtTrainPlanListPage.class;
    }

    public final Class c() {
        return TrainPlanListPage.class;
    }

    public final void a(PageBundle pageBundle) {
        AMapPageUtil.getPageContext().startPage(TrainDataPage.class, pageBundle);
    }

    public final void a(AbstractBasePage abstractBasePage, int i) {
        TrainStationListPage.a(abstractBasePage, i);
    }

    public final boolean d() {
        return ejt.a().c();
    }

    public final bdn a() {
        return a.a;
    }
}
