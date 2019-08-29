package defpackage;

import android.graphics.Path;
import android.graphics.PointF;

/* renamed from: ii reason: default package */
/* compiled from: MiscUtils */
public final class ii {
    public static PointF a(PointF pointF, PointF pointF2) {
        return new PointF(pointF.x + pointF2.x, pointF.y + pointF2.y);
    }

    public static void a(ht htVar, Path path) {
        path.reset();
        PointF pointF = htVar.b;
        path.moveTo(pointF.x, pointF.y);
        PointF pointF2 = new PointF(pointF.x, pointF.y);
        for (int i = 0; i < htVar.a.size(); i++) {
            go goVar = htVar.a.get(i);
            PointF pointF3 = goVar.a;
            PointF pointF4 = goVar.b;
            PointF pointF5 = goVar.c;
            if (!pointF3.equals(pointF2) || !pointF4.equals(pointF5)) {
                path.cubicTo(pointF3.x, pointF3.y, pointF4.x, pointF4.y, pointF5.x, pointF5.y);
            } else {
                path.lineTo(pointF5.x, pointF5.y);
            }
            pointF2.set(pointF5.x, pointF5.y);
        }
        if (htVar.c) {
            path.close();
        }
    }

    public static float a(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f3, f));
    }

    public static int a(float f, float f2) {
        int i = (int) f;
        int i2 = (int) f2;
        int i3 = i / i2;
        if ((i ^ i2) < 0 && i3 * i2 != i) {
            i3--;
        }
        return i - (i3 * i2);
    }
}
