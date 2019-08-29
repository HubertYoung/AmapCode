package defpackage;

import com.autonavi.common.model.POI;

/* renamed from: dzf reason: default package */
/* compiled from: ICoachDBMgrImpl */
public final class dzf implements atv {

    /* renamed from: dzf$a */
    /* compiled from: ICoachDBMgrImpl */
    static class a {
        static dzf a = new dzf();
    }

    public final void a() {
        dzi.a().a = true;
    }

    public final void b() {
        dzi.a().b = true;
    }

    public final void a(POI poi, POI poi2) {
        dzi a2 = dzi.a();
        if (a2.a && a2.b && a2.c) {
            ahm.c(new Runnable(poi, poi2) {
                final /* synthetic */ POI a;
                final /* synthetic */ POI b;

                {
                    this.a = r2;
                    this.b = r3;
                }

                public final void run() {
                    ebm.a(this.a, this.b);
                }
            });
        }
    }

    public final void c() {
        dzi.a();
        dzi.b();
    }
}
