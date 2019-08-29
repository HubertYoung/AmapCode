package defpackage;

import android.content.Context;
import android.view.ViewGroup;
import com.autonavi.map.core.view.MapLayerDrawerView.a;

/* renamed from: aqw reason: default package */
/* compiled from: MapHomeMapLayerDrawerController */
public final class aqw {
    arn a;
    private Object b = new Object();
    private volatile f c;

    public aqw(arn arn) {
        this.a = arn;
    }

    public final boolean a() {
        if (this.c == null) {
            synchronized (this.b) {
                if (this.c == null) {
                    this.c = new bsd(this.a.getSuspendManager(), this.a.getMapManager(), new a() {
                        public final Context a() {
                            return aqw.this.a.getSuspendManager().a();
                        }

                        public final ViewGroup b() {
                            return aqw.this.a.j();
                        }
                    });
                }
            }
        }
        if (this.c.f()) {
            return false;
        }
        this.c.c();
        return true;
    }

    public final boolean b() {
        if (this.c == null || !this.c.f()) {
            return false;
        }
        this.c.d();
        return true;
    }

    public final boolean c() {
        return this.c != null && this.c.f();
    }
}
