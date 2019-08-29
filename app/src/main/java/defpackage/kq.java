package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

/* renamed from: kq reason: default package */
/* compiled from: CircleDrawableFactory */
public final class kq extends kr {
    protected int a;
    protected Mode b;

    public kq() {
        this.a = 0;
        this.b = Mode.SRC_ATOP;
        this.a = 50;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        kq kqVar = (kq) obj;
        return this.a == kqVar.a && this.b.equals(kqVar.b);
    }

    public final int hashCode() {
        return (this.a * 31) + this.b.hashCode();
    }

    public final Bitmap a(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Config config = bitmap.getConfig();
        if (config == null) {
            config = Config.ARGB_8888;
        }
        if (this.a == 0) {
            this.a = width > height ? height / 2 : width / 2;
        }
        Bitmap createBitmap = Bitmap.createBitmap(this.a * 2, this.a * 2, config);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        new Rect(0, 0, width, this.a * 2);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-1);
        canvas.drawCircle((float) this.a, (float) this.a, (float) this.a, paint);
        paint.setXfermode(new PorterDuffXfermode(this.b));
        canvas.drawBitmap(bitmap, (float) (this.a - (width / 2)), (float) (this.a - (height / 2)), paint);
        bitmap.recycle();
        return createBitmap;
    }
}
