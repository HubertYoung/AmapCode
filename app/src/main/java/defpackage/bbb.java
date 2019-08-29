package defpackage;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.minimap.ajx3.Ajx3Page;
import java.lang.ref.WeakReference;
import java.util.Iterator;

/* renamed from: bbb reason: default package */
/* compiled from: SearchScenicMapStateManager */
public final class bbb {
    public bty a;
    public Context b;
    public ccv c;
    public ccy d;
    public View e;
    public boolean f = false;
    public boolean g = false;
    private WeakReference<Ajx3Page> h;
    private int i = 0;
    private a j;
    private a k;

    /* renamed from: bbb$a */
    /* compiled from: SearchScenicMapStateManager */
    static class a {
        public boolean a;
        public boolean b;
        public boolean c;
        public boolean d;
        public int e;
        public float f;
        public float g;
        public float h;

        private a() {
            this.a = false;
            this.b = false;
            this.c = false;
            this.d = false;
            this.e = 0;
            this.f = 0.0f;
            this.g = 0.0f;
            this.h = 0.0f;
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public bbb(Ajx3Page ajx3Page) {
        this.a = ajx3Page.getMapView();
        this.b = ajx3Page.getContext();
        this.c = new ccv(ajx3Page.getContext());
        this.d = ajx3Page.getSuspendWidgetHelper();
        this.j = new a(0);
        this.k = new a(0);
        this.h = new WeakReference<>(ajx3Page);
    }

    public final void a() {
        this.a.b(false);
        this.a.g(false);
        if (this.h.get() != null) {
            c();
            this.a.a(this.a.j(false), this.a.l(false), 17);
            this.a.r(true);
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            if (awo != null) {
                Iterator<LayerItem> it = awo.i().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    LayerItem next = it.next();
                    if (next.getLayer_id() == 610000) {
                        awo.a(next.getData());
                        awo.b(610000);
                        break;
                    }
                }
            }
            this.a.e(true);
            this.a.d(true);
        }
    }

    public final void b() {
        a aVar = this.j;
        aVar.a = this.a.s();
        aVar.d = this.a.D();
        aVar.e = this.a.o(false);
        aVar.f = this.a.v();
        aVar.c = this.a.A();
        aVar.b = this.a.C();
        aVar.g = (float) this.a.l();
        aVar.h = (float) this.a.m();
        c();
    }

    private void c() {
        cde suspendManager = ((Ajx3Page) this.h.get()).getSuspendManager();
        if (suspendManager != null) {
            cdz d2 = suspendManager.d();
            if (d2 != null) {
                d2.f();
                d2.a(false);
            }
        }
    }

    public final void a(boolean z) {
        a aVar = this.j;
        this.a.r(false);
        this.a.b(aVar.a);
        this.a.g(aVar.d);
        this.a.d(aVar.f);
        this.a.h(aVar.g);
        this.a.i(aVar.h);
        this.a.a(new alp(0.0d, 0.0d), new alp(0.0d, 0.0d));
        this.a.a(this.a.j(false), this.a.l(false), aVar.e);
        if (aVar.b) {
            this.a.e(true);
        } else {
            this.a.B();
        }
        if (aVar.c) {
            this.a.d(true);
        } else {
            this.a.z();
        }
        if (z) {
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            if (awo != null) {
                awo.a(610000);
            }
        }
    }
}
