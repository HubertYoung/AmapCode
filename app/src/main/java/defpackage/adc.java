package defpackage;

import com.amap.bundle.planhome.page.AjxPlanHomePage;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import java.util.List;

/* renamed from: adc reason: default package */
/* compiled from: AjxPlanHomePresenter */
public final class adc<Page extends AjxPlanHomePage> extends Ajx3PagePresenter implements acy {
    private Page a;
    private IRouteUI b;
    private atw c;

    private static boolean a(POI poi, POI poi2) {
        return (poi == null || poi2 == null) ? false : true;
    }

    public adc(Page page) {
        super(page);
        this.a = page;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        a();
    }

    public final void onResume() {
        super.onResume();
        ade.a().c = this;
        if (this.b == null) {
            this.b = ((axd) this.a.getContentView().getParent()).getRouteInputUI();
        }
        if (this.b.o()) {
            a();
        }
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onStart() {
        super.onStart();
    }

    public final void onStop() {
        super.onStop();
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final ON_BACK_TYPE onBackPressed() {
        if (this.a.backPressed()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public final void onNewIntent(PageBundle pageBundle) {
        this.a.a();
    }

    private void a() {
        b();
        a(ade.a().b(true), ade.a().c(), ade.a().d(true));
    }

    private static void b() {
        String str;
        String str2;
        RouteType b2 = adf.a().b();
        if (b2 == RouteType.AIRTICKET || b2 == RouteType.COACH || b2 == RouteType.TRAIN) {
            str2 = "出发城市";
            str = "到达城市";
        } else {
            str2 = "输入起点";
            str = "输入终点";
        }
        acq.a().a(new String[]{str2, str});
    }

    private void a(POI poi, List<POI> list, POI poi2) {
        if (a(poi, poi2)) {
            c();
        } else {
            b(poi, list, poi2);
        }
    }

    private void b(POI poi, List<POI> list, POI poi2) {
        RouteType b2 = adf.a().b();
        if (b2 == RouteType.AIRTICKET || b2 == RouteType.TRAIN) {
            acq.a().a(a(b2, ade.a().d, poi));
            acq.a().b(a(b2, ade.a().e, poi2));
        } else if (b2 == RouteType.COACH) {
            if (this.c == null) {
                this.c = (atw) a.a.a(atw.class);
            }
            this.c.d().a(b2, poi, poi2);
        } else {
            acq.a().a(poi);
            if (b2 == RouteType.CAR || b2 == RouteType.TRUCK) {
                acq.a().a(list);
            }
            acq.a().b(poi2);
        }
    }

    public final void onDataChange(POI poi, List list, POI poi2) {
        a(poi, list, poi2);
    }

    private void c() {
        adf a2 = adf.a();
        if (a2 != null) {
            switch (a2.b()) {
                case TRAIN:
                    d();
                    return;
                case AIRTICKET:
                    e();
                    return;
                case COACH:
                    f();
                    break;
            }
        }
    }

    private void d() {
        if (this.b != null) {
            bdo bdo = (bdo) a.a.a(bdo.class);
            if (bdo != null) {
                this.b.a(bdo.c(), RouteType.TRAIN, (PageBundle) null);
            }
        }
    }

    private void e() {
        if (this.b != null) {
            this.b.a(RouteType.AIRTICKET, (PageBundle) null);
        }
    }

    private void f() {
        if (this.b != null) {
            this.b.a(RouteType.COACH, (PageBundle) null);
        }
    }

    private static String a(RouteType routeType, RouteType routeType2, POI poi) {
        if (poi == null) {
            return "";
        }
        if (routeType != routeType2 || "我的位置".equals(poi.getName())) {
            return acu.a(poi);
        }
        return poi.getName();
    }
}
