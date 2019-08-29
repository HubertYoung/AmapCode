package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import java.io.FileNotFoundException;

class PrintHelperKitkat {
    final Context a;
    Options b = null;
    int c = 2;
    int d = 2;
    int e = 1;
    /* access modifiers changed from: private */
    public final Object f = new Object();

    public interface OnPrintFinishCallback {
        void onFinish();
    }

    PrintHelperKitkat(Context context) {
        this.a = context;
    }

    /* access modifiers changed from: private */
    public Bitmap a(Uri uri) throws FileNotFoundException {
        Options options;
        if (uri == null || this.a == null) {
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }
        Options options2 = new Options();
        options2.inJustDecodeBounds = true;
        a(uri, options2);
        int i = options2.outWidth;
        int i2 = options2.outHeight;
        if (i <= 0 || i2 <= 0) {
            return null;
        }
        int max = Math.max(i, i2);
        int i3 = 1;
        while (max > 3500) {
            max >>>= 1;
            i3 <<= 1;
        }
        if (i3 <= 0 || Math.min(i, i2) / i3 <= 0) {
            return null;
        }
        synchronized (this.f) {
            this.b = new Options();
            this.b.inMutable = true;
            this.b.inSampleSize = i3;
            options = this.b;
        }
        try {
            Bitmap a2 = a(uri, options);
            synchronized (this.f) {
                this.b = null;
            }
            return a2;
        } catch (Throwable th) {
            synchronized (this.f) {
                this.b = null;
                throw th;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0022 A[SYNTHETIC, Splitter:B:17:0x0022] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.Bitmap a(android.net.Uri r3, android.graphics.BitmapFactory.Options r4) throws java.io.FileNotFoundException {
        /*
            r2 = this;
            if (r3 == 0) goto L_0x0026
            android.content.Context r0 = r2.a
            if (r0 != 0) goto L_0x0007
            goto L_0x0026
        L_0x0007:
            r0 = 0
            android.content.Context r1 = r2.a     // Catch:{ all -> 0x001f }
            android.content.ContentResolver r1 = r1.getContentResolver()     // Catch:{ all -> 0x001f }
            java.io.InputStream r3 = r1.openInputStream(r3)     // Catch:{ all -> 0x001f }
            android.graphics.Bitmap r4 = android.graphics.BitmapFactory.decodeStream(r3, r0, r4)     // Catch:{ all -> 0x001c }
            if (r3 == 0) goto L_0x001b
            r3.close()     // Catch:{ IOException -> 0x001b }
        L_0x001b:
            return r4
        L_0x001c:
            r4 = move-exception
            r0 = r3
            goto L_0x0020
        L_0x001f:
            r4 = move-exception
        L_0x0020:
            if (r0 == 0) goto L_0x0025
            r0.close()     // Catch:{ IOException -> 0x0025 }
        L_0x0025:
            throw r4
        L_0x0026:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "bad argument to loadBitmap"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.print.PrintHelperKitkat.a(android.net.Uri, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
    }

    static /* synthetic */ Bitmap a(Bitmap bitmap, int i) {
        if (i != 1) {
            return bitmap;
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        canvas.setBitmap(null);
        return createBitmap;
    }

    static /* synthetic */ Matrix a(int i, int i2, RectF rectF, int i3) {
        float f2;
        Matrix matrix = new Matrix();
        float f3 = (float) i;
        float width = rectF.width() / f3;
        if (i3 == 2) {
            f2 = Math.max(width, rectF.height() / ((float) i2));
        } else {
            f2 = Math.min(width, rectF.height() / ((float) i2));
        }
        matrix.postScale(f2, f2);
        matrix.postTranslate((rectF.width() - (f3 * f2)) / 2.0f, (rectF.height() - (((float) i2) * f2)) / 2.0f);
        return matrix;
    }
}
