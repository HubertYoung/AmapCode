package defpackage;

import com.autonavi.common.model.POI;
import java.util.List;

/* renamed from: pf reason: default package */
/* compiled from: CalcRouteData */
public final class pf {
    public POI a;
    public POI b;
    public List<POI> c;
    public POI d;
    public int e;
    public boolean f;
    public boolean g;
    public boolean h;
    public String i;
    public boolean j;

    public pf(qb qbVar) {
        this(qbVar, 0);
    }

    private pf(qb qbVar, byte b2) {
        this.c = null;
        this.d = null;
        this.e = 0;
        this.f = false;
        this.g = false;
        this.h = false;
        this.j = false;
        this.a = qbVar.n();
        this.b = qbVar.o();
        this.c = qbVar.t();
        this.e = 0;
    }
}
