package defpackage;

import java.util.List;

/* renamed from: fv reason: default package */
/* compiled from: ColorKeyframeAnimation */
public final class fv extends fz<Integer> {
    public fv(List<fc<Integer>> list) {
        super(list);
    }

    public final /* synthetic */ Object a(fc fcVar, float f) {
        if (fcVar.a != null && fcVar.b != null) {
            return Integer.valueOf(ie.a(f, ((Integer) fcVar.a).intValue(), ((Integer) fcVar.b).intValue()));
        }
        throw new IllegalStateException("Missing values for keyframe.");
    }
}
