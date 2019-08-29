package defpackage;

import android.graphics.PointF;
import android.view.animation.Interpolator;

/* renamed from: buq reason: default package */
/* compiled from: BezierInterpolator */
public final class buq implements Interpolator {
    private int a;
    private final PointF b;
    private final PointF c;

    private static double a(double d, double d2, double d3) {
        double d4 = 1.0d - d;
        double d5 = d * d;
        double d6 = d4 * d4;
        return (d6 * d4 * 0.0d) + (d6 * 3.0d * d * d2) + (d4 * 3.0d * d5 * d3) + (d5 * d * 1.0d);
    }

    public buq() {
        this.a = 0;
        this.b = new PointF();
        this.c = new PointF();
        this.b.x = 0.2f;
        this.b.y = 0.8f;
        this.c.x = 0.4f;
        this.c.y = 1.0f;
    }

    public final float getInterpolation(float f) {
        int i = this.a;
        float f2 = f;
        while (true) {
            if (i >= 4096) {
                break;
            }
            f2 = (((float) i) * 1.0f) / 4096.0f;
            if (a((double) f2, (double) this.b.x, (double) this.c.x) >= ((double) f)) {
                this.a = i;
                break;
            }
            i++;
        }
        double a2 = a((double) f2, (double) this.b.y, (double) this.c.y);
        if (a2 > 0.999d) {
            a2 = 1.0d;
            this.a = 0;
        }
        return (float) a2;
    }
}
