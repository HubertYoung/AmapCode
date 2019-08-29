package defpackage;

import android.graphics.PointF;

/* renamed from: cml reason: default package */
/* compiled from: CubicBezier */
public abstract class cml {
    private PointF a;
    private PointF b;
    private PointF c = new PointF();
    private PointF d = new PointF();
    private PointF e = new PointF();

    public final void a(float f, float f2, float f3, float f4) {
        this.a = new PointF(f, f2);
        this.b = new PointF(f3, f4);
    }

    public final void a(double d2, double d3, double d4, double d5) {
        a((float) d2, (float) d3, (float) d4, (float) d5);
    }

    public float a(float f) {
        float f2 = f;
        for (int i = 1; i < 14; i++) {
            this.e.x = this.a.x * 3.0f;
            this.d.x = ((this.b.x - this.a.x) * 3.0f) - this.e.x;
            this.c.x = (1.0f - this.e.x) - this.d.x;
            float f3 = ((this.e.x + ((this.d.x + (this.c.x * f2)) * f2)) * f2) - f;
            if (((double) Math.abs(f3)) < 0.001d) {
                break;
            }
            f2 -= f3 / (this.e.x + (((this.d.x * 2.0f) + ((this.c.x * 3.0f) * f2)) * f2));
        }
        this.e.y = this.a.y * 3.0f;
        this.d.y = ((this.b.y - this.a.y) * 3.0f) - this.e.y;
        this.c.y = (1.0f - this.e.y) - this.d.y;
        return f2 * (this.e.y + ((this.d.y + (this.c.y * f2)) * f2));
    }
}
