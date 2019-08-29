package defpackage;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.content.ShapeStroke;

/* renamed from: fs reason: default package */
/* compiled from: StrokeContent */
public final class fs extends fd {
    private final String b;
    private final fu<Integer, Integer> c;

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        this.a.setColorFilter(colorFilter);
    }

    public final void a(Canvas canvas, Matrix matrix, int i) {
        this.a.setColor(((Integer) this.c.a()).intValue());
        super.a(canvas, matrix, i);
    }

    public final String b() {
        return this.b;
    }

    public fs(ew ewVar, hx hxVar, ShapeStroke shapeStroke) {
        super(ewVar, hxVar, shapeStroke.g.toPaintCap(), shapeStroke.h.toPaintJoin(), shapeStroke.e, shapeStroke.f, shapeStroke.c, shapeStroke.b);
        this.b = shapeStroke.a;
        this.c = shapeStroke.d.a();
        this.c.a((a) this);
        hxVar.a(this.c);
    }
}
