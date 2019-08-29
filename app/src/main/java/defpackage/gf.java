package defpackage;

import android.graphics.Path;
import android.graphics.PointF;
import java.util.List;

/* renamed from: gf reason: default package */
/* compiled from: ShapeKeyframeAnimation */
public final class gf extends fu<ht, Path> {
    private final ht d = new ht();
    private final Path e = new Path();

    public gf(List<fc<ht>> list) {
        super(list);
    }

    public final /* synthetic */ Object a(fc fcVar, float f) {
        ht htVar = (ht) fcVar.a;
        ht htVar2 = (ht) fcVar.b;
        ht htVar3 = this.d;
        if (htVar3.b == null) {
            htVar3.b = new PointF();
        }
        htVar3.c = htVar.c || htVar2.c;
        if (htVar3.a.isEmpty() || htVar3.a.size() == htVar.a.size() || htVar3.a.size() == htVar2.a.size()) {
            if (htVar3.a.isEmpty()) {
                for (int size = htVar.a.size() - 1; size >= 0; size--) {
                    htVar3.a.add(new go());
                }
            }
            PointF pointF = htVar.b;
            PointF pointF2 = htVar2.b;
            float f2 = pointF.x;
            float f3 = f2 + ((pointF2.x - f2) * f);
            float f4 = pointF.y;
            float f5 = f4 + ((pointF2.y - f4) * f);
            if (htVar3.b == null) {
                htVar3.b = new PointF();
            }
            htVar3.b.set(f3, f5);
            for (int size2 = htVar3.a.size() - 1; size2 >= 0; size2--) {
                go goVar = htVar.a.get(size2);
                go goVar2 = htVar2.a.get(size2);
                PointF pointF3 = goVar.a;
                PointF pointF4 = goVar.b;
                PointF pointF5 = goVar.c;
                PointF pointF6 = goVar2.a;
                PointF pointF7 = goVar2.b;
                PointF pointF8 = goVar2.c;
                float f6 = pointF3.x;
                float f7 = pointF3.y;
                htVar3.a.get(size2).a.set(f6 + ((pointF6.x - f6) * f), f7 + ((pointF6.y - f7) * f));
                float f8 = pointF4.x;
                float f9 = pointF4.y;
                htVar3.a.get(size2).b.set(f8 + ((pointF7.x - f8) * f), f9 + ((pointF7.y - f9) * f));
                float f10 = pointF5.x;
                float f11 = pointF5.y;
                htVar3.a.get(size2).c.set(f10 + ((pointF8.x - f10) * f), f11 + ((pointF8.y - f11) * f));
            }
            ii.a(this.d, this.e);
            return this.e;
        }
        StringBuilder sb = new StringBuilder("Curves must have the same number of control points. This: ");
        sb.append(htVar3.a.size());
        sb.append("\tShape 1: ");
        sb.append(htVar.a.size());
        sb.append("\tShape 2: ");
        sb.append(htVar2.a.size());
        throw new IllegalStateException(sb.toString());
    }
}
