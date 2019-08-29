package defpackage;

import android.content.Context;
import android.view.KeyEvent;
import com.amap.bundle.drive.navi.motornavi.page.AjxRouteMotorNaviPage;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;

/* renamed from: ny reason: default package */
/* compiled from: AjxRouteMotorNaviPresenter */
public final class ny<Page extends AjxRouteMotorNaviPage> extends Ajx3PagePresenter {
    public ny(Page page) {
        super(page);
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
