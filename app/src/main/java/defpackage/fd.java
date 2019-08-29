package defpackage;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import java.util.ArrayList;
import java.util.List;

/* renamed from: fd reason: default package */
/* compiled from: BaseStrokeContent */
public abstract class fd implements fg, defpackage.fu.a {
    final Paint a = new Paint(1);
    private final PathMeasure b = new PathMeasure();
    private final Path c = new Path();
    private final Path d = new Path();
    private final RectF e = new RectF();
    private final ew f;
    private final List<a> g = new ArrayList();
    private final float[] h;
    private final fu<?, Float> i;
    private final fu<?, Integer> j;
    private final List<fu<?, Float>> k;
    @Nullable
    private final fu<?, Float> l;

    /* renamed from: fd$a */
    /* compiled from: BaseStrokeContent */
    static final class a {
        final List<fn> a;
        @Nullable
        final ft b;

        /* synthetic */ a(ft ftVar, byte b2) {
            this(ftVar);
        }

        private a(@Nullable ft ftVar) {
            this.a = new ArrayList();
            this.b = ftVar;
        }
    }

    fd(ew ewVar, hx hxVar, Cap cap, Join join, ha haVar, gy gyVar, List<gy> list, gy gyVar2) {
        this.f = ewVar;
        this.a.setStyle(Style.STROKE);
        this.a.setStrokeCap(cap);
        this.a.setStrokeJoin(join);
        this.j = haVar.a();
        this.i = gyVar.a();
        if (gyVar2 == null) {
            this.l = null;
        } else {
            this.l = gyVar2.a();
        }
        this.k = new ArrayList(list.size());
        this.h = new float[list.size()];
        for (int i2 = 0; i2 < list.size(); i2++) {
            this.k.add(list.get(i2).a());
        }
        hxVar.a(this.j);
        hxVar.a(this.i);
        for (int i3 = 0; i3 < this.k.size(); i3++) {
            hxVar.a(this.k.get(i3));
        }
        if (this.l != null) {
            hxVar.a(this.l);
        }
        this.j.a((defpackage.fu.a) this);
        this.i.a((defpackage.fu.a) this);
        for (int i4 = 0; i4 < list.size(); i4++) {
            this.k.get(i4).a((defpackage.fu.a) this);
        }
        if (this.l != null) {
            this.l.a((defpackage.fu.a) this);
        }
    }

    public final void a() {
        this.f.invalidateSelf();
    }

    public final void a(List<fe> list, List<fe> list2) {
        a aVar = null;
        ft ftVar = null;
        for (int size = list.size() - 1; size >= 0; size--) {
            fe feVar = list.get(size);
            if (feVar instanceof ft) {
                ft ftVar2 = (ft) feVar;
                if (ftVar2.a == Type.Individually) {
                    ftVar = ftVar2;
                }
            }
        }
        if (ftVar != null) {
            ftVar.a(this);
        }
        for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
            fe feVar2 = list2.get(size2);
            if (feVar2 instanceof ft) {
                ft ftVar3 = (ft) feVar2;
                if (ftVar3.a == Type.Individually) {
                    if (aVar != null) {
                        this.g.add(aVar);
                    }
                    a aVar2 = new a(ftVar3, 0);
                    ftVar3.a(this);
                    aVar = aVar2;
                }
            }
            if (feVar2 instanceof fn) {
                if (aVar == null) {
                    aVar = new a(ftVar, 0);
                }
                aVar.a.add((fn) feVar2);
            }
        }
        if (aVar != null) {
            this.g.add(aVar);
        }
    }

    public void a(Canvas canvas, Matrix matrix, int i2) {
        eu.a("StrokeContent#draw");
        this.a.setAlpha((int) ((((((float) i2) / 255.0f) * ((float) ((Integer) this.j.a()).intValue())) / 100.0f) * 255.0f));
        this.a.setStrokeWidth(((Float) this.i.a()).floatValue() * ij.a(matrix));
        float f2 = 0.0f;
        if (this.a.getStrokeWidth() <= 0.0f) {
            eu.b("StrokeContent#draw");
            return;
        }
        eu.a("StrokeContent#applyDashPattern");
        if (this.k.isEmpty()) {
            eu.b("StrokeContent#applyDashPattern");
        } else {
            float a2 = ij.a(matrix);
            for (int i3 = 0; i3 < this.k.size(); i3++) {
                this.h[i3] = ((Float) this.k.get(i3).a()).floatValue();
                if (i3 % 2 == 0) {
                    if (this.h[i3] < 1.0f) {
                        this.h[i3] = 1.0f;
                    }
                } else if (this.h[i3] < 0.1f) {
                    this.h[i3] = 0.1f;
                }
                float[] fArr = this.h;
                fArr[i3] = fArr[i3] * a2;
            }
            if (this.l != null) {
                f2 = ((Float) this.l.a()).floatValue();
            }
            this.a.setPathEffect(new DashPathEffect(this.h, f2));
            eu.b("StrokeContent#applyDashPattern");
        }
        for (int i4 = 0; i4 < this.g.size(); i4++) {
            a aVar = this.g.get(i4);
            if (aVar.b != null) {
                a(canvas, aVar, matrix);
            } else {
                eu.a("StrokeContent#buildPath");
                this.c.reset();
                for (int size = aVar.a.size() - 1; size >= 0; size--) {
                    this.c.addPath(aVar.a.get(size).e(), matrix);
                }
                eu.b("StrokeContent#buildPath");
                eu.a("StrokeContent#drawPath");
                canvas.drawPath(this.c, this.a);
                eu.b("StrokeContent#drawPath");
            }
        }
        eu.b("StrokeContent#draw");
    }

    private void a(Canvas canvas, a aVar, Matrix matrix) {
        float f2;
        eu.a("StrokeContent#applyTrimPath");
        if (aVar.b == null) {
            eu.b("StrokeContent#applyTrimPath");
            return;
        }
        this.c.reset();
        for (int size = aVar.a.size() - 1; size >= 0; size--) {
            this.c.addPath(aVar.a.get(size).e(), matrix);
        }
        this.b.setPath(this.c, false);
        float length = this.b.getLength();
        while (this.b.nextContour()) {
            length += this.b.getLength();
        }
        float floatValue = (((Float) aVar.b.d.a()).floatValue() * length) / 360.0f;
        float floatValue2 = ((((Float) aVar.b.b.a()).floatValue() * length) / 100.0f) + floatValue;
        float floatValue3 = ((((Float) aVar.b.c.a()).floatValue() * length) / 100.0f) + floatValue;
        float f3 = 0.0f;
        for (int size2 = aVar.a.size() - 1; size2 >= 0; size2--) {
            this.d.set(aVar.a.get(size2).e());
            this.d.transform(matrix);
            this.b.setPath(this.d, false);
            float length2 = this.b.getLength();
            float f4 = 1.0f;
            if (floatValue3 > length) {
                float f5 = floatValue3 - length;
                if (f5 < f3 + length2 && f3 < f5) {
                    f2 = floatValue2 > length ? (floatValue2 - length) / length2 : 0.0f;
                    f4 = Math.min(f5 / length2, 1.0f);
                    ij.a(this.d, f2, f4, 0.0f);
                    canvas.drawPath(this.d, this.a);
                    f3 += length2;
                }
            }
            float f6 = f3 + length2;
            if (f6 >= floatValue2 && f3 <= floatValue3) {
                if (f6 > floatValue3 || floatValue2 >= f3) {
                    f2 = floatValue2 < f3 ? 0.0f : (floatValue2 - f3) / length2;
                    if (floatValue3 <= f6) {
                        f4 = (floatValue3 - f3) / length2;
                    }
                    ij.a(this.d, f2, f4, 0.0f);
                    canvas.drawPath(this.d, this.a);
                } else {
                    canvas.drawPath(this.d, this.a);
                }
            }
            f3 += length2;
        }
        eu.b("StrokeContent#applyTrimPath");
    }

    public final void a(RectF rectF, Matrix matrix) {
        eu.a("StrokeContent#getBounds");
        this.c.reset();
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            a aVar = this.g.get(i2);
            for (int i3 = 0; i3 < aVar.a.size(); i3++) {
                this.c.addPath(aVar.a.get(i3).e(), matrix);
            }
        }
        this.c.computeBounds(this.e, false);
        float floatValue = ((Float) this.i.a()).floatValue() / 2.0f;
        this.e.set(this.e.left - floatValue, this.e.top - floatValue, this.e.right + floatValue, this.e.bottom + floatValue);
        rectF.set(this.e);
        rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
        eu.b("StrokeContent#getBounds");
    }
}
