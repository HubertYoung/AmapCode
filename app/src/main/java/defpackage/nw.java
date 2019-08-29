package defpackage;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import com.amap.bundle.drive.navi.drivenavi.normal.page.AjxRouteCarNaviPage;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ResultType;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;

/* renamed from: nw reason: default package */
/* compiled from: AjxRouteCarNaviPresenter */
public final class nw extends mu<AjxRouteCarNaviPage> {
    private boolean b = true;

    public nw(AjxRouteCarNaviPage ajxRouteCarNaviPage) {
        super(ajxRouteCarNaviPage);
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
        if (this.b) {
            anf.a(2, 0);
            this.b = false;
        }
    }

    public final void onDestroy() {
        anf.a(0, -1);
        AMapPageFramework.removePageStateListener((bid) this.mPage);
        super.onDestroy();
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 24 && !ro.f()) {
            rw.a(AMapAppGlobal.getApplication()).b();
            tv.a((Context) AMapAppGlobal.getApplication()).a(false);
            return true;
        } else if (i != 25 || ro.f()) {
            return super.onKeyDown(i, keyEvent);
        } else {
            rw.a(AMapAppGlobal.getApplication()).c();
            tv.a((Context) AMapAppGlobal.getApplication()).b(false);
            return true;
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            ResultType resultType = ResultType.OK;
        } else {
            ResultType resultType2 = ResultType.CANCEL;
        }
    }
}
