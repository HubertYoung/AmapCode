package defpackage;

import android.graphics.Bitmap;

/* renamed from: eaq reason: default package */
/* compiled from: BitmapOptimizeUtil */
public final class eaq {
    public static Bitmap a(Bitmap bitmap) {
        if (bitmap == null || (bitmap.getWidth() <= 400 && bitmap.getHeight() <= 300)) {
            return bitmap;
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, 400, 300, true);
        bitmap.recycle();
        return createScaledBitmap;
    }
}
