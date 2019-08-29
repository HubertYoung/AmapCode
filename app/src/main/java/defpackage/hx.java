package defpackage;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.CallSuper;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.Mask.MaskMode;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.Layer.MatteType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* renamed from: hx reason: default package */
/* compiled from: BaseLayer */
public abstract class hx implements fg, a {
    final Matrix a;
    final ew b;
    final Layer c;
    @Nullable
    hx d;
    @Nullable
    hx e;
    final gj f;
    private final Path g = new Path();
    private final Matrix h = new Matrix();
    private final Paint i;
    private final Paint j;
    private final Paint k;
    private final Paint l;
    private final Paint m;
    private final RectF n;
    private final RectF o;
    private final RectF p;
    private final RectF q;
    private final String r;
    @Nullable
    private ga s;
    private List<hx> t;
    private final List<fu<?, ?>> u;
    private boolean v;

    public void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
    }

    public final void a(List<fe> list, List<fe> list2) {
    }

    /* access modifiers changed from: 0000 */
    public abstract void b(Canvas canvas, Matrix matrix, int i2);

    hx(ew ewVar, Layer layer) {
        boolean z = true;
        this.i = new Paint(1);
        this.j = new Paint(1);
        this.k = new Paint(1);
        this.l = new Paint(1);
        this.m = new Paint();
        this.n = new RectF();
        this.o = new RectF();
        this.p = new RectF();
        this.q = new RectF();
        this.a = new Matrix();
        this.u = new ArrayList();
        this.v = true;
        this.b = ewVar;
        this.c = layer;
        StringBuilder sb = new StringBuilder();
        sb.append(layer.c);
        sb.append("#draw");
        this.r = sb.toString();
        this.m.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.j.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        this.k.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        if (layer.u == MatteType.Invert) {
            this.l.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        } else {
            this.l.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        }
        this.f = layer.i.a();
        this.f.a((a) this);
        if (layer.h != null && !layer.h.isEmpty()) {
            this.s = new ga(layer.h);
            for (fu next : this.s.a) {
                a(next);
                next.a((a) this);
            }
            for (fu next2 : this.s.b) {
                a(next2);
                next2.a((a) this);
            }
        }
        if (!this.c.t.isEmpty()) {
            final fw fwVar = new fw(this.c.t);
            fwVar.b = true;
            fwVar.a((a) new a() {
                public final void a() {
                    hx.this.a(((Float) fwVar.a()).floatValue() == 1.0f);
                }
            });
            a(((Float) fwVar.a()).floatValue() != 1.0f ? false : z);
            a((fu<?, ?>) fwVar);
            return;
        }
        a(true);
    }

    public final boolean c() {
        return this.d != null;
    }

    public final void a(fu<?, ?> fuVar) {
        if (!(fuVar instanceof gh)) {
            this.u.add(fuVar);
        }
    }

    @CallSuper
    public void a(RectF rectF, Matrix matrix) {
        this.a.set(matrix);
        this.a.preConcat(this.f.a());
    }

    @SuppressLint({"WrongConstant"})
    public final void a(Canvas canvas, Matrix matrix, int i2) {
        eu.a(this.r);
        if (!this.v) {
            eu.b(this.r);
            return;
        }
        if (this.t == null) {
            if (this.e == null) {
                this.t = Collections.emptyList();
            } else {
                this.t = new ArrayList();
                for (hx hxVar = this.e; hxVar != null; hxVar = hxVar.e) {
                    this.t.add(hxVar);
                }
            }
        }
        eu.a("Layer#parentMatrix");
        this.h.reset();
        this.h.set(matrix);
        for (int size = this.t.size() - 1; size >= 0; size--) {
            this.h.preConcat(this.t.get(size).f.a());
        }
        eu.b("Layer#parentMatrix");
        int intValue = (int) ((((((float) i2) / 255.0f) * ((float) ((Integer) this.f.e.a()).intValue())) / 100.0f) * 255.0f);
        if (c() || d()) {
            eu.a("Layer#computeBounds");
            this.n.set(0.0f, 0.0f, 0.0f, 0.0f);
            a(this.n, this.h);
            RectF rectF = this.n;
            Matrix matrix2 = this.h;
            if (c() && this.c.u != MatteType.Invert) {
                this.d.a(this.p, matrix2);
                rectF.set(Math.max(rectF.left, this.p.left), Math.max(rectF.top, this.p.top), Math.min(rectF.right, this.p.right), Math.min(rectF.bottom, this.p.bottom));
            }
            this.h.preConcat(this.f.a());
            b(this.n, this.h);
            this.n.set(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
            eu.b("Layer#computeBounds");
            eu.a("Layer#saveLayer");
            canvas.saveLayer(this.n, this.i, 31);
            eu.b("Layer#saveLayer");
            a(canvas);
            eu.a("Layer#drawLayer");
            b(canvas, this.h, intValue);
            eu.b("Layer#drawLayer");
            if (d()) {
                Matrix matrix3 = this.h;
                a(canvas, matrix3, MaskMode.MaskModeAdd);
                a(canvas, matrix3, MaskMode.MaskModeSubtract);
            }
            if (c()) {
                eu.a("Layer#drawMatte");
                eu.a("Layer#saveLayer");
                canvas.saveLayer(this.n, this.l, 31);
                eu.b("Layer#saveLayer");
                a(canvas);
                this.d.a(canvas, matrix, intValue);
                eu.a("Layer#restoreLayer");
                canvas.restore();
                eu.b("Layer#restoreLayer");
                eu.b("Layer#drawMatte");
            }
            eu.a("Layer#restoreLayer");
            canvas.restore();
            eu.b("Layer#restoreLayer");
            b(eu.b(this.r));
            return;
        }
        this.h.preConcat(this.f.a());
        eu.a("Layer#drawLayer");
        b(canvas, this.h, intValue);
        eu.b("Layer#drawLayer");
        b(eu.b(this.r));
    }

    private void b(float f2) {
        this.b.a.g.a(this.c.c, f2);
    }

    private void a(Canvas canvas) {
        eu.a("Layer#clearLayer");
        canvas.drawRect(this.n.left - 1.0f, this.n.top - 1.0f, this.n.right + 1.0f, this.n.bottom + 1.0f, this.m);
        eu.b("Layer#clearLayer");
    }

    private void b(RectF rectF, Matrix matrix) {
        this.o.set(0.0f, 0.0f, 0.0f, 0.0f);
        if (d()) {
            int size = this.s.c.size();
            int i2 = 0;
            while (i2 < size) {
                this.g.set((Path) this.s.a.get(i2).a());
                this.g.transform(matrix);
                switch (this.s.c.get(i2).a) {
                    case MaskModeSubtract:
                        return;
                    case MaskModeIntersect:
                        return;
                    case MaskModeUnknown:
                        return;
                    default:
                        this.g.computeBounds(this.q, false);
                        if (i2 == 0) {
                            this.o.set(this.q);
                        } else {
                            this.o.set(Math.min(this.o.left, this.q.left), Math.min(this.o.top, this.q.top), Math.max(this.o.right, this.q.right), Math.max(this.o.bottom, this.q.bottom));
                        }
                        i2++;
                }
            }
            rectF.set(Math.max(rectF.left, this.o.left), Math.max(rectF.top, this.o.top), Math.min(rectF.right, this.o.right), Math.min(rectF.bottom, this.o.bottom));
        }
    }

    @SuppressLint({"WrongConstant"})
    private void a(Canvas canvas, Matrix matrix, MaskMode maskMode) {
        Paint paint;
        boolean z;
        if (maskMode == MaskMode.MaskModeSubtract) {
            paint = this.k;
        } else {
            paint = this.j;
        }
        int size = this.s.c.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                z = false;
                break;
            } else if (this.s.c.get(i2).a == maskMode) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            eu.a("Layer#drawMask");
            eu.a("Layer#saveLayer");
            canvas.saveLayer(this.n, paint, 31);
            eu.b("Layer#saveLayer");
            a(canvas);
            for (int i3 = 0; i3 < size; i3++) {
                if (this.s.c.get(i3).a == maskMode) {
                    this.g.set((Path) this.s.a.get(i3).a());
                    this.g.transform(matrix);
                    int alpha = this.i.getAlpha();
                    this.i.setAlpha((int) (((float) ((Integer) this.s.b.get(i3).a()).intValue()) * 2.55f));
                    canvas.drawPath(this.g, this.i);
                    this.i.setAlpha(alpha);
                }
            }
            eu.a("Layer#restoreLayer");
            canvas.restore();
            eu.b("Layer#restoreLayer");
            eu.b("Layer#drawMask");
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean d() {
        return this.s != null && !this.s.a.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean z) {
        if (z != this.v) {
            this.v = z;
            this.b.invalidateSelf();
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        gj gjVar = this.f;
        gjVar.a.a(f2);
        gjVar.b.a(f2);
        gjVar.c.a(f2);
        gjVar.d.a(f2);
        gjVar.e.a(f2);
        if (gjVar.f != null) {
            gjVar.f.a(f2);
        }
        if (gjVar.g != null) {
            gjVar.g.a(f2);
        }
        if (this.c.m != 0.0f) {
            f2 /= this.c.m;
        }
        if (this.d != null) {
            this.d.a(this.d.c.m * f2);
        }
        for (int i2 = 0; i2 < this.u.size(); i2++) {
            this.u.get(i2).a(f2);
        }
    }

    public final String b() {
        return this.c.c;
    }

    public final void a() {
        this.b.invalidateSelf();
    }
}
