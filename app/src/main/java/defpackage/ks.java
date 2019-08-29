package defpackage;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.autonavi.amap.app.AMapAppGlobal;

/* renamed from: ks reason: default package */
/* compiled from: RoundDrawableFactory */
public final class ks extends kr {
    protected int a = 0;
    protected int b;
    protected int c;
    private DisplayMetrics d;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h;

    public ks(float f2, DisplayMetrics displayMetrics) {
        this.d = displayMetrics;
        this.a = (int) TypedValue.applyDimension(1, f2, this.d);
    }

    private int a(int i, int i2, int i3) {
        return i + (this.h * 2) + Math.max(0, i2 + i3) + Math.max(0, i2 - i3);
    }

    private int a(int i) {
        return Math.max(0, this.e - i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ks ksVar = (ks) obj;
        return this.b == ksVar.b && this.h == ksVar.h && this.a == ksVar.a && this.c == ksVar.c && this.f == ksVar.f && this.g == ksVar.g && this.e == ksVar.e;
    }

    public final int hashCode() {
        return (((((((((((this.a * 31) + this.e) * 31) + this.f) * 31) + this.g) * 31) + this.h) * 31) + this.b) * 31) + this.c;
    }

    public final Bitmap a(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int a2 = a(this.f);
        int a3 = a(this.g);
        int i = this.h + a2;
        int i2 = this.h + a3;
        int i3 = AMapAppGlobal.getApplication().getResources().getDisplayMetrics().densityDpi;
        int a4 = a(bitmap.getScaledWidth(i3), this.e, this.f);
        int a5 = a(bitmap.getScaledWidth(i3), this.e, this.g);
        Config config = bitmap.getConfig();
        if (config == null) {
            config = Config.ARGB_8888;
        }
        Bitmap createBitmap = Bitmap.createBitmap(a4, a5, config);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(i, i2, i + width, i2 + height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(new RectF(rect), (float) this.a, (float) this.a, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, (float) i, (float) i2, paint);
        Paint paint2 = null;
        if (!(this.e == 0 && this.f == 0 && this.g == 0)) {
            paint2 = new Paint();
            paint2.setShadowLayer((float) this.e, (float) this.f, (float) this.g, this.c);
        }
        if (this.h > 0) {
            RectF rectF = new RectF((float) a2, (float) a3, (float) (a2 + width + (this.h * 2)), (float) (a3 + height + (this.h * 2)));
            paint2.setColor(this.b);
            canvas.drawRoundRect(rectF, (float) this.a, (float) this.a, paint2);
        }
        bitmap.recycle();
        return createBitmap;
    }
}
