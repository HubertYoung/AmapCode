package defpackage;

import com.amap.bundle.drive.result.driveresult.restrict.AjxRouteCarRestrictPage;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;

/* renamed from: ov reason: default package */
/* compiled from: AjxRouteCarRestricPresenter */
public final class ov<Page extends AjxRouteCarRestrictPage> extends Ajx3PagePresenter {
    protected Page a;

    public ov(Page page) {
        super(page);
        this.a = page;
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onResume() {
        super.onResume();
        Page page = this.a;
        bty mapView = page.getMapView();
        if (mapView != null) {
            if (page.a.a) {
                mapView.b(false);
            }
            mapView.a(0, mapView.ae(), 1);
            if (page.getSuspendManager() != null && page.getSuspendManager().d() != null) {
                page.getSuspendManager().d().f();
            }
        }
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
        Page page = this.a;
        bty mapView = page.getMapView();
        if (mapView != null) {
            if (page.a.a) {
                mapView.b(page.a.a);
            }
            mapView.a(page.a.d, page.a.c, page.a.b);
        }
        if (page.b != null) {
            page.b.setOnJsOpenCarSettingCallback(null);
        }
    }

    public final void onResult(int i, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i, resultType, pageBundle);
        Page page = this.a;
        if (page.b != null && i == 65536) {
            page.b.notifyCarSettingSuccess();
        }
    }
}
