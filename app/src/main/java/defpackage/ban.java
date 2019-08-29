package defpackage;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;

/* renamed from: ban reason: default package */
/* compiled from: BusCommuteImpl */
public final class ban implements baq {
    private ayb a = new axz();

    public final void a(AbstractBaseMapPage abstractBaseMapPage) {
        azb.a("song---", "initBusCommute");
        this.a.a(abstractBaseMapPage);
    }

    public final void a(int i, String str) {
        StringBuilder sb = new StringBuilder("showCommuteTip location = ");
        sb.append(i);
        sb.append("  ,from = ");
        sb.append(str);
        azb.a("song---", sb.toString());
        this.a.a(i, str);
    }

    public final void a() {
        azb.a("song---", "destroy");
        this.a.g();
    }

    public final void b() {
        azb.a("song---", "onDefaultPageResume");
        this.a.d();
    }

    public final void c() {
        azb.a("song---", "onDefaultPagePause");
        this.a.e();
    }

    public final void d() {
        azb.a("song---", "onDefaultPageDestroy");
        this.a.f();
    }

    public final void e() {
        azb.a("song---", "onTipOrCQShow");
        this.a.h();
    }

    public final void f() {
        azb.a("song---", "onTipOrCQDismiss");
        this.a.i();
    }

    public final void g() {
        azb.a("song---", "onTrafficViewShow");
        this.a.h();
    }

    public final void h() {
        azb.a("song---", "onTrafficViewHide");
        this.a.i();
    }

    public final void a(GeoPoint geoPoint) {
        this.a.a(geoPoint);
    }

    public final void i() {
        this.a.j();
    }

    public final void a(boolean z) {
        this.a.a(z);
    }
}
