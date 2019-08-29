package defpackage;

import android.content.Context;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.suspend.refactor.scenic.ISketchScenic;
import com.autonavi.map.suspend.refactor.scenic.ISmartScenicController;

/* renamed from: daj reason: default package */
/* compiled from: SuspendManagerImpl */
public class daj implements cdb {
    /* access modifiers changed from: 0000 */
    public cdz a;
    ceu b = new ceu() {
    };
    /* access modifiers changed from: private */
    public Context c;
    /* access modifiers changed from: private */
    public MapManager d;
    /* access modifiers changed from: private */
    public cdn e;
    /* access modifiers changed from: private */
    public MapCustomizeManager f;
    private cdg g;
    private cej h;
    private ces i;
    private ISmartScenicController j;
    private ISketchScenic k;
    private cdc l = new cdc() {
        public final Context a() {
            return daj.this.c;
        }

        public final MapManager b() {
            return daj.this.d;
        }

        public final cdn c() {
            return daj.this.e;
        }

        public final cdz d() {
            return daj.this.a;
        }

        public final MapCustomizeManager e() {
            return daj.this.f;
        }
    };

    public final void a(Context context, MapManager mapManager) {
        this.c = context;
        this.d = mapManager;
        this.a = new cdz(this.l);
        this.e = new cdn(this.l);
        this.g = new cdg(this.l);
        this.h = new cej(this.l);
        this.i = new ces(this.l);
        this.d.addAllMapEventListener(new bts() {
            public final void a() {
                cdd.n().g();
            }

            public final void b() {
                daj.this.a.f();
            }

            public final void c() {
                daj.this.a.f();
            }
        });
    }

    @Deprecated
    public final Context a() {
        return this.c;
    }

    @Deprecated
    public final MapCustomizeManager b() {
        return this.f;
    }

    public final cdn c() {
        return this.e;
    }

    public final cdz d() {
        return this.a;
    }

    public final cdg e() {
        return this.g;
    }

    public final ces f() {
        return this.i;
    }

    public final cej g() {
        return this.h;
    }

    public final ISmartScenicController h() {
        return this.j;
    }

    public final ISketchScenic i() {
        return this.k;
    }

    @Deprecated
    public final void a(MapCustomizeManager mapCustomizeManager) {
        this.f = mapCustomizeManager;
    }

    public final void a(ISmartScenicController iSmartScenicController) {
        if (iSmartScenicController != null && this.j == null) {
            this.j = iSmartScenicController;
        }
    }

    public final void a(ISketchScenic iSketchScenic) {
        this.k = iSketchScenic;
    }
}
