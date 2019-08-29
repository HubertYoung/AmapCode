package defpackage;

import android.graphics.Rect;
import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.bundle.searchcommon.overlay.SearchLineOverlay;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.overlay.MarkFocusOverlay;
import com.autonavi.map.search.overlay.NormalSearchPoiOverlay;
import com.autonavi.map.search.overlay.SearchCenterOverlay;
import com.autonavi.map.search.overlay.SearchChildOverlay;
import com.autonavi.map.search.overlay.SearchChildStationOverlay;
import com.autonavi.map.search.overlay.SearchGeoOverlay;
import com.autonavi.map.search.overlay.SearchPoiMarkOverlay;
import com.autonavi.map.search.overlay.SearchPoiOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.lang.ref.WeakReference;
import java.util.List;

/* renamed from: bzh reason: default package */
/* compiled from: SearchResultOverlayManager */
public abstract class bzh implements bbo, OnItemClickListener<PointOverlayItem> {
    float a = -1.0f;
    public SearchChildStationOverlay b;
    public SearchChildOverlay c;
    public SearchCenterOverlay d;
    public SearchGeoOverlay e;
    public SearchPoiOverlay f;
    SearchPoiMarkOverlay g;
    MarkFocusOverlay h;
    SearchLineOverlay i;
    public bbp j;
    bzc k;
    byz l;
    protected WeakReference<a> m;
    bty n;
    private btm o;

    /* renamed from: bzh$a */
    /* compiled from: SearchResultOverlayManager */
    public interface a {
        void a(BaseMapOverlay baseMapOverlay, PointOverlayItem pointOverlayItem);
    }

    public abstract void a(int i2);

    public abstract void a(int i2, int i3);

    public abstract void a(bxf bxf);

    public abstract void a(bzu bzu, boolean z, boolean z2);

    public abstract void a(cce cce, int i2, bxh bxh);

    public abstract void a(cce cce, int i2, POI poi, InfoliteResult infoliteResult);

    public abstract void a(cce cce, POI poi, InfoliteResult infoliteResult);

    public abstract void a(cce cce, POI poi, InfoliteResult infoliteResult, boolean z);

    public abstract void a(InfoliteResult infoliteResult);

    public abstract void a(InfoliteResult infoliteResult, cbn cbn, Rect rect);

    public abstract void a(POI poi, boolean z);

    public abstract void a(PointOverlayItem pointOverlayItem, String str);

    public abstract void a(List<POI> list);

    public abstract void a(boolean z);

    public abstract void a(boolean z, int i2);

    public abstract boolean a(POI poi);

    public abstract void b(int i2);

    public abstract void b(POI poi);

    public abstract void b(List<POI> list);

    public abstract void b(boolean z);

    public abstract void c(int i2);

    public abstract void c(POI poi);

    public abstract void g();

    public abstract PointOverlayItem h();

    public abstract int i();

    public abstract bbr j();

    public abstract List<bbr> k();

    public abstract bbr l();

    public abstract void m();

    public abstract boolean n();

    public abstract void o();

    public abstract void p();

    public abstract void q();

    public abstract void r();

    public abstract void s();

    public /* synthetic */ void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
        PointOverlayItem pointOverlayItem = (PointOverlayItem) obj;
        if (this.m != null && this.m.get() != null) {
            ((a) this.m.get()).a(baseMapOverlay, pointOverlayItem);
        }
    }

    bzh(bty bty) {
        this.n = bty;
        this.j = new bbp(this.n);
        this.k = new bzc(this.n);
        this.o = this.n.F();
        this.k.e = new defpackage.bzc.a() {
            public final void a(BaseMapOverlay baseMapOverlay, PointOverlayItem pointOverlayItem) {
                if (bzh.this.m != null && bzh.this.m.get() != null) {
                    ((a) bzh.this.m.get()).a(baseMapOverlay, pointOverlayItem);
                }
            }
        };
        b((BaseMapOverlay) this.j.a);
        b((BaseMapOverlay) this.j.b);
        b((BaseMapOverlay) this.k.a);
        b((BaseMapOverlay) this.k.b);
        b((BaseMapOverlay) this.k.c);
        this.g = new SearchPoiMarkOverlay(this.n);
        this.o.b((BaseMapOverlay) this.g);
        this.h = new MarkFocusOverlay(this.n);
        this.o.b((BaseMapOverlay) this.h);
        this.g.setCheckCover(true);
        this.g.showReversed(true);
        this.g.setOnItemClickListener(this);
        this.h.setOnItemClickListener(this);
        this.f = new NormalSearchPoiOverlay(this.n);
        this.f.showReversed(true);
        this.f.setOnItemClickListener(this);
        this.f.setFocusChangeCallback(this);
        this.o.b((BaseMapOverlay) this.f);
        this.c = new SearchChildOverlay(this.n);
        this.c.showReversed(true);
        this.c.setMaxCountShown(10);
        this.c.setOnItemClickListener(this);
        this.o.b((BaseMapOverlay) this.c);
        this.b = new SearchChildStationOverlay(this.n);
        this.b.showReversed(true);
        this.b.setOnItemClickListener(this);
        this.o.b((BaseMapOverlay) this.b);
        this.d = new SearchCenterOverlay(this.n);
        this.o.b((BaseMapOverlay) this.d);
        this.e = new SearchGeoOverlay(this.n);
        this.e.setOnItemClickListener(this);
        this.o.b((BaseMapOverlay) this.e);
        this.i = new SearchLineOverlay(this.n);
        this.o.b((BaseMapOverlay) this.i);
    }

    public final void a(a aVar) {
        this.m = new WeakReference<>(aVar);
    }

    public final void a() {
        if (this.c != null) {
            this.c.clearFocus();
        }
    }

    public final void b() {
        if (this.e != null) {
            this.e.setFocus(0, true);
        }
    }

    public final void c() {
        if (this.e != null) {
            this.e.clear();
        }
    }

    public final void a(bzx bzx) {
        if (this.l != null) {
            this.l.a(bzx);
        }
    }

    public final void a(PointOverlay pointOverlay) {
        if (pointOverlay instanceof SearchPoiOverlay) {
            this.c.clear();
            this.b.clear();
        }
    }

    public final void d() {
        if (this.b != null) {
            this.b.clear();
        }
        if (this.c != null) {
            this.c.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
        if (this.e != null) {
            this.e.clear();
        }
        if (this.j != null) {
            this.j.a();
        }
        if (this.k != null) {
            this.k.a();
        }
        if (this.l != null) {
            this.l.c();
        }
        if (this.f != null) {
            this.f.clear();
        }
        if (this.i != null) {
            this.i.clear();
        }
    }

    public final void e() {
        if (this.l != null) {
            this.l.d();
        }
        if (this.e != null) {
            this.e.clearFocus();
        }
        if (this.f != null) {
            this.f.clearFocus();
        }
        if (this.b != null) {
            this.b.clearFocus();
        }
        if (this.c != null) {
            this.c.clearFocus();
        }
        if (this.k != null) {
            this.k.a();
        }
        if (this.j != null) {
            this.j.a();
        }
    }

    public final void f() {
        e();
        t();
    }

    /* access modifiers changed from: 0000 */
    public final void t() {
        if (this.l != null) {
            this.l.d();
            this.l.b();
        }
    }

    private void b(BaseMapOverlay baseMapOverlay) {
        if (baseMapOverlay != null) {
            this.o.b(baseMapOverlay);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(BaseMapOverlay baseMapOverlay) {
        if (baseMapOverlay != null && this.o.a(baseMapOverlay)) {
            this.o.c(baseMapOverlay);
        }
    }
}
