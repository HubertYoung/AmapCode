package defpackage;

import android.graphics.PointF;
import java.util.Collections;

/* renamed from: gg reason: default package */
/* compiled from: SplitDimensionPathKeyframeAnimation */
public final class gg extends fu<PointF, PointF> {
    private final PointF d = new PointF();
    private final fu<Float, Float> e;
    private final fu<Float, Float> f;

    public gg(fu<Float, Float> fuVar, fu<Float, Float> fuVar2) {
        super(Collections.emptyList());
        this.e = fuVar;
        this.f = fuVar2;
    }

    public final void a(float f2) {
        this.e.a(f2);
        this.f.a(f2);
        this.d.set(((Float) this.e.a()).floatValue(), ((Float) this.f.a()).floatValue());
        for (int i = 0; i < this.a.size(); i++) {
            ((a) this.a.get(i)).a();
        }
    }

    /* access modifiers changed from: 0000 */
    public final /* bridge */ /* synthetic */ Object a(fc fcVar, float f2) {
        return this.d;
    }

    public final /* bridge */ /* synthetic */ Object a() {
        return this.d;
    }
}
