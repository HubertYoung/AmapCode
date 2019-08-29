package defpackage;

import java.util.List;

/* renamed from: fy reason: default package */
/* compiled from: IntegerKeyframeAnimation */
public final class fy extends fz<Integer> {
    public fy(List<fc<Integer>> list) {
        super(list);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object a(fc fcVar, float f) {
        if (fcVar.a == null || fcVar.b == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        int intValue = ((Integer) fcVar.a).intValue();
        return Integer.valueOf((int) (((float) intValue) + (f * ((float) (((Integer) fcVar.b).intValue() - intValue)))));
    }
}
