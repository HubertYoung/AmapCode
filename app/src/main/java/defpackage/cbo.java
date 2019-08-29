package defpackage;

import com.autonavi.map.core.MapManager;
import proguard.annotation.Keep;

/* renamed from: cbo reason: default package */
/* compiled from: SearchMapSnapshot */
public final class cbo {
    public cbn a;
    public boolean b;
    private final cde c;
    private final MapManager d;
    private final bxh e;

    public cbo(cde cde, MapManager mapManager, bxh bxh) {
        this.c = cde;
        this.d = mapManager;
        this.e = bxh;
    }

    public final void a() {
        if (!this.b) {
            this.b = true;
            this.a = new cbn();
            bty mapView = this.d.getMapView();
            if (mapView != null) {
                this.a.a.a = mapView.v();
                this.a.a.b = mapView.I();
                this.a.a.c = mapView.J();
                this.a.a.d = mapView.n();
            }
            cdz d2 = this.c.d();
            if (d2 != null) {
                this.a.a.e = d2.b();
            }
            if (bcy.a(this.e.b)) {
                this.a.b.a = this.e.e.a;
                this.a.b.b = this.e.b.searchInfo.o.b;
                this.a.b.c = this.e.b.searchInfo.o.c;
            }
        }
    }

    @Keep
    private void onEventMainThread(cbm cbm) {
        a();
    }

    public final void b() {
        this.a = null;
        this.b = false;
    }
}
