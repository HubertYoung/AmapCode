package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewGroup;
import com.autonavi.common.model.POI;
import com.autonavi.map.search.manager.PoiDetailAjxLayerHandler.b;
import java.util.LinkedList;
import java.util.List;

/* renamed from: cbl reason: default package */
/* compiled from: PoiDetailInnerPageManager */
public final class cbl {
    public List<a> a = new LinkedList();
    private Handler b = new Handler(Looper.getMainLooper());
    private cbk c = new cbk(this.a);

    /* renamed from: cbl$a */
    /* compiled from: PoiDetailInnerPageManager */
    public static class a implements bxj {
        public int n = 0;
        public int o;
        public POI p;
        public View q;
        public boolean r = true;
        public bxf s;
        public bxw t;
        public cbl u;
        public ViewGroup v;
        public boolean w;

        @UiThread
        @CallSuper
        public void a(boolean z) {
        }

        @UiThread
        @CallSuper
        public void b() {
        }

        public final boolean c() {
            return this.q != null && this.q.getVisibility() == 0 && this.r;
        }

        public boolean a() {
            if (this.u.c() == 2) {
                this.u.a(this.v);
                return true;
            } else if (this.u.c() <= 2) {
                return false;
            } else {
                this.u.a(this.v, this);
                return true;
            }
        }
    }

    public final void a(a aVar, ViewGroup viewGroup) {
        boolean z;
        int i = aVar.n;
        if (i != 1) {
            switch (i) {
                case 3:
                    this.c.a(viewGroup);
                    break;
                case 4:
                    this.c.b(viewGroup);
                    break;
                case 5:
                    this.c.c(viewGroup);
                    break;
                case 6:
                    this.c.b(aVar, viewGroup);
                    break;
                case 7:
                    this.c.a(viewGroup);
                    break;
            }
            z = false;
        } else {
            z = this.c.a(aVar, viewGroup);
        }
        if (!z) {
            a a2 = a();
            if (!(a2 == null || a2.q == null)) {
                a2.q.setVisibility(8);
            }
        }
        this.c.d(viewGroup);
        if (aVar.q != null) {
            aVar.q.setVisibility(0);
        }
        if (!z) {
            aVar.u = this;
            aVar.v = viewGroup;
            this.a.add(aVar);
        }
        if (z || aVar.n == 7) {
            aVar.a(true);
        }
    }

    @Nullable
    public final a a(final ViewGroup viewGroup) {
        final a a2 = a();
        if (a2 == null) {
            return null;
        }
        this.a.remove(a2);
        this.b.postDelayed(new Runnable() {
            public final void run() {
                viewGroup.removeView(a2.q);
                a2.b();
            }
        }, 50);
        a a3 = a();
        if (a3 == null) {
            return a3;
        }
        if (a3.q != null && a3.r) {
            a3.q.setVisibility(0);
        }
        a3.a(false);
        return a3;
    }

    @Nullable
    public final a a() {
        if (this.a.isEmpty()) {
            return null;
        }
        return this.a.get(this.a.size() - 1);
    }

    @Nullable
    public final a b() {
        for (a next : this.a) {
            if (next.o == 0) {
                return next;
            }
        }
        return null;
    }

    public final int c() {
        return this.a.size();
    }

    public final boolean a(ViewGroup viewGroup, a aVar) {
        if (aVar == null) {
            return false;
        }
        this.a.remove(aVar);
        if (!(viewGroup == null || aVar.q == null)) {
            viewGroup.removeView(aVar.q);
        }
        aVar.b();
        return true;
    }

    public final void b(ViewGroup viewGroup) {
        this.c.e(viewGroup);
    }

    public final void d() {
        for (a next : this.a) {
            if (next instanceof b) {
                b bVar = (b) next;
                if (bVar.f != null) {
                    bVar.f.a();
                }
            }
        }
    }
}
