package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

/* renamed from: kt reason: default package */
/* compiled from: SquareDrawableFactory */
public final class kt extends kr {
    protected int a;
    protected int b;
    private int c;
    private int d;

    public kt() {
        this.a = 0;
        this.a = 0;
    }

    public final Bitmap a(Bitmap bitmap) {
        Bitmap a2 = a(bitmap, this.a);
        bitmap.recycle();
        int i = this.b;
        int i2 = this.d;
        int i3 = this.c;
        if (a2 == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(a2.getWidth(), a2.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, a2.getWidth(), a2.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        paint.setColor(-1);
        paint.setStyle(Style.FILL);
        canvas.drawARGB(0, 0, 0, 0);
        float f = (float) i2;
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(a2, rect, rect, paint);
        paint.setColor(i);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth((float) i3);
        canvas.drawRoundRect(rectF, f, f, paint);
        a2.recycle();
        return createBitmap;
    }

    public final void a(int i) {
        this.c = i;
        this.d = 0;
        this.b = -1907998;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        kt ktVar = (kt) obj;
        return this.a == ktVar.a && this.b == ktVar.b && this.c == ktVar.c && this.d == ktVar.d;
    }

    public final int hashCode() {
        return (((((this.a * 31) + this.b) * 31) + this.c) * 31) + this.d;
    }

    private static Bitmap a(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        if (i != 0) {
            matrix.postRotate((float) i);
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        try {
            return Bitmap.createBitmap(bitmap, (width - Math.min(width, height)) / 2, (height - Math.min(width, height)) / 2, Math.min(width, height), Math.min(width, height), matrix, true);
        } catch (Exception unused) {
            return null;
        }
    }
}
