package defpackage;

import java.util.List;

/* renamed from: fx reason: default package */
/* compiled from: GradientColorKeyframeAnimation */
public final class fx extends fz<ho> {
    private final ho d;

    public fx(List<? extends fc<ho>> list) {
        super(list);
        int i = 0;
        ho hoVar = (ho) ((fc) list.get(0)).a;
        i = hoVar != null ? hoVar.b.length : i;
        this.d = new ho(new float[i], new int[i]);
    }

    /* access modifiers changed from: 0000 */
    public final /* synthetic */ Object a(fc fcVar, float f) {
        ho hoVar = this.d;
        ho hoVar2 = (ho) fcVar.a;
        ho hoVar3 = (ho) fcVar.b;
        if (hoVar2.b.length != hoVar3.b.length) {
            StringBuilder sb = new StringBuilder("Cannot interpolate between gradients. Lengths vary (");
            sb.append(hoVar2.b.length);
            sb.append(" vs ");
            sb.append(hoVar3.b.length);
            sb.append(")");
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i = 0; i < hoVar2.b.length; i++) {
            float[] fArr = hoVar.a;
            float f2 = hoVar2.a[i];
            fArr[i] = f2 + ((hoVar3.a[i] - f2) * f);
            hoVar.b[i] = ie.a(f, hoVar2.b[i], hoVar3.b[i]);
        }
        return this.d;
    }
}
