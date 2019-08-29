package defpackage;

import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: fu reason: default package */
/* compiled from: BaseKeyframeAnimation */
public abstract class fu<K, A> {
    final List<a> a = new ArrayList();
    public boolean b = false;
    public float c = 0.0f;
    private final List<? extends fc<K>> d;
    @Nullable
    private fc<K> e;

    /* renamed from: fu$a */
    /* compiled from: BaseKeyframeAnimation */
    public interface a {
        void a();
    }

    /* access modifiers changed from: 0000 */
    public abstract A a(fc<K> fcVar, float f);

    fu(List<? extends fc<K>> list) {
        this.d = list;
    }

    public void a(a aVar) {
        this.a.add(aVar);
    }

    public void a(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (f < c()) {
            f = c();
        } else if (f > d()) {
            f = d();
        }
        if (f != this.c) {
            this.c = f;
            for (int i = 0; i < this.a.size(); i++) {
                this.a.get(i).a();
            }
        }
    }

    private fc<K> b() {
        if (this.d.isEmpty()) {
            throw new IllegalStateException("There are no keyframes");
        } else if (this.e != null && this.e.a(this.c)) {
            return this.e;
        } else {
            fc<K> fcVar = (fc) this.d.get(this.d.size() - 1);
            if (this.c < fcVar.a()) {
                for (int size = this.d.size() - 1; size >= 0; size--) {
                    fcVar = (fc) this.d.get(size);
                    if (fcVar.a(this.c)) {
                        break;
                    }
                }
            }
            this.e = fcVar;
            return fcVar;
        }
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    private float c() {
        if (this.d.isEmpty()) {
            return 0.0f;
        }
        return ((fc) this.d.get(0)).a();
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    private float d() {
        if (this.d.isEmpty()) {
            return 1.0f;
        }
        return ((fc) this.d.get(this.d.size() - 1)).b();
    }

    public A a() {
        fc b2 = b();
        float f = 0.0f;
        if (!this.b) {
            fc b3 = b();
            if (!(b3.c == null)) {
                f = b3.c.getInterpolation((this.c - b3.a()) / (b3.b() - b3.a()));
            }
        }
        return a(b2, f);
    }
}
