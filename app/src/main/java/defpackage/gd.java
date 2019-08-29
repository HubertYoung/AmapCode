package defpackage;

import android.graphics.PointF;
import java.util.List;

/* renamed from: gd reason: default package */
/* compiled from: PointKeyframeAnimation */
public final class gd extends fz<PointF> {
    private final PointF d = new PointF();

    public gd(List<fc<PointF>> list) {
        super(list);
    }

    public final /* synthetic */ Object a(fc fcVar, float f) {
        if (fcVar.a == null || fcVar.b == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        PointF pointF = (PointF) fcVar.a;
        PointF pointF2 = (PointF) fcVar.b;
        this.d.set(pointF.x + ((pointF2.x - pointF.x) * f), pointF.y + (f * (pointF2.y - pointF.y)));
        return this.d;
    }
}
