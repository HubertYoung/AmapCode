package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.support.annotation.FloatRange;

/* renamed from: fht reason: default package */
/* compiled from: CornerRadiusTransform */
public final class fht implements fhu {
    public float a;
    private Shader b;
    private final RectF c = new RectF();

    public fht(@FloatRange(from = 0.0d) float f) {
        float max = Math.max(0.0f, f);
        if (max != this.a) {
            this.a = max;
            this.b = null;
        }
    }

    public final void a(Rect rect) {
        this.c.set(rect);
        this.b = null;
    }

    public final void a(Canvas canvas, Paint paint, Bitmap bitmap) {
        if (this.a == 0.0f) {
            canvas.drawBitmap(bitmap, null, this.c, paint);
            return;
        }
        if (this.b == null) {
            TileMode tileMode = TileMode.CLAMP;
            this.b = new BitmapShader(bitmap, tileMode, tileMode);
            Matrix matrix = new Matrix();
            matrix.setTranslate(this.c.left, this.c.top);
            matrix.preScale(this.c.width() / ((float) bitmap.getWidth()), this.c.height() / ((float) bitmap.getHeight()));
            this.b.setLocalMatrix(matrix);
        }
        paint.setShader(this.b);
        canvas.drawRoundRect(this.c, this.a, this.a, paint);
    }
}
