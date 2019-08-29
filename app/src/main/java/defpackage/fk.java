package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import com.airbnb.lottie.model.content.GradientType;

/* renamed from: fk reason: default package */
/* compiled from: GradientStrokeContent */
public final class fk extends fd {
    private final String b;
    private final LongSparseArray<LinearGradient> c = new LongSparseArray<>();
    private final LongSparseArray<RadialGradient> d = new LongSparseArray<>();
    private final RectF e = new RectF();
    private final GradientType f;
    private final int g;
    private final fu<ho, ho> h;
    private final fu<PointF, PointF> i;
    private final fu<PointF, PointF> j;

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
    }

    public final void a(Canvas canvas, Matrix matrix, int i2) {
        a(this.e, matrix);
        if (this.f == GradientType.Linear) {
            Paint paint = this.a;
            long c2 = (long) c();
            LinearGradient linearGradient = (LinearGradient) this.c.get(c2);
            if (linearGradient == null) {
                PointF pointF = (PointF) this.i.a();
                PointF pointF2 = (PointF) this.j.a();
                ho hoVar = (ho) this.h.a();
                LinearGradient linearGradient2 = new LinearGradient((float) ((int) (this.e.left + (this.e.width() / 2.0f) + pointF.x)), (float) ((int) (this.e.top + (this.e.height() / 2.0f) + pointF.y)), (float) ((int) (this.e.left + (this.e.width() / 2.0f) + pointF2.x)), (float) ((int) (this.e.top + (this.e.height() / 2.0f) + pointF2.y)), hoVar.b, hoVar.a, TileMode.CLAMP);
                this.c.put(c2, linearGradient2);
                linearGradient = linearGradient2;
            }
            paint.setShader(linearGradient);
        } else {
            Paint paint2 = this.a;
            long c3 = (long) c();
            RadialGradient radialGradient = (RadialGradient) this.d.get(c3);
            if (radialGradient == null) {
                PointF pointF3 = (PointF) this.i.a();
                PointF pointF4 = (PointF) this.j.a();
                ho hoVar2 = (ho) this.h.a();
                int[] iArr = hoVar2.b;
                float[] fArr = hoVar2.a;
                int width = (int) (this.e.left + (this.e.width() / 2.0f) + pointF3.x);
                int height = (int) (this.e.top + (this.e.height() / 2.0f) + pointF3.y);
                RadialGradient radialGradient2 = new RadialGradient((float) width, (float) height, (float) Math.hypot((double) (((int) ((this.e.left + (this.e.width() / 2.0f)) + pointF4.x)) - width), (double) (((int) ((this.e.top + (this.e.height() / 2.0f)) + pointF4.y)) - height)), iArr, fArr, TileMode.CLAMP);
                this.d.put(c3, radialGradient2);
                radialGradient = radialGradient2;
            }
            paint2.setShader(radialGradient);
        }
        super.a(canvas, matrix, i2);
    }

    public final String b() {
        return this.b;
    }

    private int c() {
        int round = Math.round(this.i.c * ((float) this.g));
        int round2 = Math.round(this.j.c * ((float) this.g));
        int round3 = Math.round(this.h.c * ((float) this.g));
        int i2 = round != 0 ? round * 527 : 17;
        if (round2 != 0) {
            i2 = i2 * 31 * round2;
        }
        return round3 != 0 ? i2 * 31 * round3 : i2;
    }

    public fk(ew ewVar, hx hxVar, hq hqVar) {
        super(ewVar, hxVar, hqVar.h.toPaintCap(), hqVar.i.toPaintJoin(), hqVar.d, hqVar.g, hqVar.j, hqVar.k);
        this.b = hqVar.a;
        this.f = hqVar.b;
        this.g = (int) (ewVar.a.a() / 32);
        this.h = hqVar.c.a();
        this.h.a((a) this);
        hxVar.a(this.h);
        this.i = hqVar.e.a();
        this.i.a((a) this);
        hxVar.a(this.i);
        this.j = hqVar.f.a();
        this.j.a((a) this);
        hxVar.a(this.j);
    }
}
