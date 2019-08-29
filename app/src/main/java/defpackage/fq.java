package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/* renamed from: fq reason: default package */
/* compiled from: RepeaterContent */
public final class fq implements fg, fl, fn, a {
    private final Matrix a = new Matrix();
    private final Path b = new Path();
    private final ew c;
    private final hx d;
    private final String e;
    private final fu<Float, Float> f;
    private final fu<Float, Float> g;
    private final gj h;
    private ff i;

    public fq(ew ewVar, hx hxVar, hs hsVar) {
        this.c = ewVar;
        this.d = hxVar;
        this.e = hsVar.a;
        this.f = hsVar.b.a();
        hxVar.a(this.f);
        this.f.a((a) this);
        this.g = hsVar.c.a();
        hxVar.a(this.g);
        this.g.a((a) this);
        this.h = hsVar.d.a();
        this.h.a(hxVar);
        this.h.a((a) this);
    }

    public final void a(ListIterator<fe> listIterator) {
        if (this.i == null) {
            while (listIterator.hasPrevious()) {
                if (listIterator.previous() == this) {
                    break;
                }
            }
            ArrayList arrayList = new ArrayList();
            while (listIterator.hasPrevious()) {
                arrayList.add(listIterator.previous());
                listIterator.remove();
            }
            Collections.reverse(arrayList);
            ff ffVar = new ff(this.c, this.d, "Repeater", arrayList, null);
            this.i = ffVar;
        }
    }

    public final String b() {
        return this.e;
    }

    public final void a(List<fe> list, List<fe> list2) {
        this.i.a(list, list2);
    }

    public final Path e() {
        Path e2 = this.i.e();
        this.b.reset();
        float floatValue = ((Float) this.f.a()).floatValue();
        float floatValue2 = ((Float) this.g.a()).floatValue();
        for (int i2 = ((int) floatValue) - 1; i2 >= 0; i2--) {
            this.a.set(this.h.a(((float) i2) + floatValue2));
            this.b.addPath(e2, this.a);
        }
        return this.b;
    }

    public final void a(Canvas canvas, Matrix matrix, int i2) {
        float floatValue = ((Float) this.f.a()).floatValue();
        float floatValue2 = ((Float) this.g.a()).floatValue();
        float floatValue3 = ((Float) this.h.f.a()).floatValue() / 100.0f;
        float floatValue4 = ((Float) this.h.g.a()).floatValue() / 100.0f;
        for (int i3 = ((int) floatValue) - 1; i3 >= 0; i3--) {
            this.a.set(matrix);
            float f2 = (float) i3;
            this.a.preConcat(this.h.a(f2 + floatValue2));
            this.i.a(canvas, this.a, (int) (((float) i2) * (((f2 / floatValue) * (floatValue4 - floatValue3)) + floatValue3)));
        }
    }

    public final void a(RectF rectF, Matrix matrix) {
        this.i.a(rectF, matrix);
    }

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        this.i.a(str, str2, colorFilter);
    }

    public final void a() {
        this.c.invalidateSelf();
    }
}
