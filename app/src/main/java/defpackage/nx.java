package defpackage;

import android.content.Context;
import android.view.KeyEvent;
import com.amap.bundle.drive.navi.drivenavi.simulate.page.AjxRouteCarNaviSimulatePage;
import com.autonavi.amap.app.AMapAppGlobal;

/* renamed from: nx reason: default package */
/* compiled from: AjxRouteCarNaviSimulatePresenter */
public final class nx extends mu<AjxRouteCarNaviSimulatePage> {
    public nx(AjxRouteCarNaviSimulatePage ajxRouteCarNaviSimulatePage) {
        super(ajxRouteCarNaviSimulatePage);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 24 && !ro.f()) {
            rj.a().c();
            tv.a((Context) AMapAppGlobal.getApplication()).a(false);
            return true;
        } else if (i != 25 || ro.f()) {
            return super.onKeyDown(i, keyEvent);
        } else {
            rj.a().d();
            tv.a((Context) AMapAppGlobal.getApplication()).b(false);
            return true;
        }
    }
}
