package defpackage;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.layer.Layer;

/* renamed from: ic reason: default package */
/* compiled from: SolidLayer */
public final class ic extends hx {
    private final RectF g = new RectF();
    private final Paint h = new Paint();
    private final Layer i;

    ic(ew ewVar, Layer layer) {
        super(ewVar, layer);
        this.i = layer;
        this.h.setAlpha(0);
        this.h.setStyle(Style.FILL);
        this.h.setColor(layer.l);
    }

    public final void b(Canvas canvas, Matrix matrix, int i2) {
        int alpha = Color.alpha(this.i.l);
        if (alpha != 0) {
            int intValue = (int) ((((float) i2) / 255.0f) * (((((float) alpha) / 255.0f) * ((float) ((Integer) this.f.e.a()).intValue())) / 100.0f) * 255.0f);
            this.h.setAlpha(intValue);
            if (intValue > 0) {
                a(matrix);
                canvas.drawRect(this.g, this.h);
            }
        }
    }

    public final void a(RectF rectF, Matrix matrix) {
        super.a(rectF, matrix);
        a(this.a);
        rectF.set(this.g);
    }

    private void a(Matrix matrix) {
        this.g.set(0.0f, 0.0f, (float) this.i.j, (float) this.i.k);
        matrix.mapRect(this.g);
    }

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        this.h.setColorFilter(colorFilter);
    }
}
