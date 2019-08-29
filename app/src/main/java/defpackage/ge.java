package defpackage;

import java.util.List;

/* renamed from: ge reason: default package */
/* compiled from: ScaleKeyframeAnimation */
public final class ge extends fz<gw> {
    public ge(List<fc<gw>> list) {
        super(list);
    }

    public final /* synthetic */ Object a(fc fcVar, float f) {
        if (fcVar.a == null || fcVar.b == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        gw gwVar = (gw) fcVar.a;
        gw gwVar2 = (gw) fcVar.b;
        float f2 = gwVar.a;
        float f3 = gwVar.b;
        return new gw(f2 + ((gwVar2.a - f2) * f), f3 + (f * (gwVar2.b - f3)));
    }
}
