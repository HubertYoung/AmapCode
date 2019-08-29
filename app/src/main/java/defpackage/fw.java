package defpackage;

import java.util.List;

/* renamed from: fw reason: default package */
/* compiled from: FloatKeyframeAnimation */
public final class fw extends fz<Float> {
    public fw(List<fc<Float>> list) {
        super(list);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object a(fc fcVar, float f) {
        if (fcVar.a == null || fcVar.b == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        float floatValue = ((Float) fcVar.a).floatValue();
        return Float.valueOf(floatValue + (f * (((Float) fcVar.b).floatValue() - floatValue)));
    }
}
