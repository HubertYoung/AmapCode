package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* renamed from: fi reason: default package */
/* compiled from: FillContent */
public final class fi implements fg, a {
    private final Path a = new Path();
    private final Paint b = new Paint(1);
    private final String c;
    private final List<fn> d = new ArrayList();
    private final fu<Integer, Integer> e;
    private final fu<Integer, Integer> f;
    private final ew g;

    public fi(ew ewVar, hx hxVar, hu huVar) {
        this.c = huVar.b;
        this.g = ewVar;
        if (huVar.c == null || huVar.d == null) {
            this.e = null;
            this.f = null;
            return;
        }
        this.a.setFillType(huVar.a);
        this.e = huVar.c.a();
        this.e.a((a) this);
        hxVar.a(this.e);
        this.f = huVar.d.a();
        this.f.a((a) this);
        hxVar.a(this.f);
    }

    public final void a() {
        this.g.invalidateSelf();
    }

    public final void a(List<fe> list, List<fe> list2) {
        for (int i = 0; i < list2.size(); i++) {
            fe feVar = list2.get(i);
            if (feVar instanceof fn) {
                this.d.add((fn) feVar);
            }
        }
    }

    public final String b() {
        return this.c;
    }

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        this.b.setColorFilter(colorFilter);
    }

    public final void a(Canvas canvas, Matrix matrix, int i) {
        eu.a("FillContent#draw");
        this.b.setColor(((Integer) this.e.a()).intValue());
        this.b.setAlpha((int) ((((((float) i) / 255.0f) * ((float) ((Integer) this.f.a()).intValue())) / 100.0f) * 255.0f));
        this.a.reset();
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            this.a.addPath(this.d.get(i2).e(), matrix);
        }
        canvas.drawPath(this.a, this.b);
        eu.b("FillContent#draw");
    }

    public final void a(RectF rectF, Matrix matrix) {
        this.a.reset();
        for (int i = 0; i < this.d.size(); i++) {
            this.a.addPath(this.d.get(i).e(), matrix);
        }
        this.a.computeBounds(rectF, false);
        rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
    }
}
