package defpackage;

import android.graphics.PointF;

/* renamed from: hf reason: default package */
/* compiled from: AnimatableSplitDimensionPathValue */
public final class hf implements hj<PointF, PointF> {
    private final gy a;
    private final gy b;

    hf(gy gyVar, gy gyVar2) {
        this.a = gyVar;
        this.b = gyVar2;
    }

    public final fu<PointF, PointF> a() {
        return new gg(this.a.a(), this.b.a());
    }
}
