package defpackage;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import java.util.List;

/* renamed from: gc reason: default package */
/* compiled from: PathKeyframeAnimation */
public final class gc extends fz<PointF> {
    private final PointF d = new PointF();
    private final float[] e = new float[2];
    private gb f;
    private PathMeasure g;

    public gc(List<? extends fc<PointF>> list) {
        super(list);
    }

    public final /* synthetic */ Object a(fc fcVar, float f2) {
        gb gbVar = (gb) fcVar;
        Path path = gbVar.f;
        if (path == null) {
            return (PointF) fcVar.a;
        }
        if (this.f != gbVar) {
            this.g = new PathMeasure(path, false);
            this.f = gbVar;
        }
        this.g.getPosTan(f2 * this.g.getLength(), this.e, null);
        this.d.set(this.e[0], this.e[1]);
        return this.d;
    }
}
