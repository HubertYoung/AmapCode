package defpackage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.airbnb.lottie.model.layer.Layer;

/* renamed from: hz reason: default package */
/* compiled from: ImageLayer */
public final class hz extends hx {
    private final Paint g = new Paint(3);
    private final Rect h = new Rect();
    private final Rect i = new Rect();
    private final float j;

    hz(ew ewVar, Layer layer, float f) {
        super(ewVar, layer);
        this.j = f;
    }

    public final void b(@NonNull Canvas canvas, Matrix matrix, int i2) {
        Bitmap e = e();
        if (e != null) {
            this.g.setAlpha(i2);
            canvas.save();
            canvas.concat(matrix);
            this.h.set(0, 0, e.getWidth(), e.getHeight());
            this.i.set(0, 0, (int) (((float) e.getWidth()) * this.j), (int) (((float) e.getHeight()) * this.j));
            canvas.drawBitmap(e, this.h, this.i, this.g);
            canvas.restore();
        }
    }

    public final void a(RectF rectF, Matrix matrix) {
        super.a(rectF, matrix);
        Bitmap e = e();
        if (e != null) {
            rectF.set(rectF.left, rectF.top, Math.min(rectF.right, (float) e.getWidth()), Math.min(rectF.bottom, (float) e.getHeight()));
            this.a.mapRect(rectF);
        }
    }

    @Nullable
    private Bitmap e() {
        return this.b.a(this.c.g);
    }

    public final void a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter) {
        this.g.setColorFilter(colorFilter);
    }
}
