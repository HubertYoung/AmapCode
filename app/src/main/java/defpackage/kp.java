package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/* renamed from: kp reason: default package */
/* compiled from: BitmapDecodeUtil */
public final class kp {
    public static Bitmap a(String str, int i, int i2) {
        if (str == null || !new File(str).exists()) {
            return null;
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = a(options, i, i2);
        options.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        int c = ahc.c(str);
        Matrix matrix = new Matrix();
        int width = decodeFile.getWidth();
        int height = decodeFile.getHeight();
        matrix.setRotate((float) c);
        try {
            return Bitmap.createBitmap(decodeFile, 0, 0, width, height, matrix, true);
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }

    public static int a(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 == 2448 && i4 == 3264) {
            return 4;
        }
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 > i2 && i7 / i5 > i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public static Bitmap a(String str, Options options, int i, int i2) throws FileNotFoundException {
        File file = new File(str);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        if (options != null) {
            Rect rect = new Rect(0, 0, i, i2);
            int width = rect.width();
            int height = rect.height();
            options.inSampleSize = b(options, width > height ? width : height, width * height);
            options.inJustDecodeBounds = false;
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(fileInputStream, null, options);
        try {
            fileInputStream.close();
        } catch (IOException unused) {
        }
        return decodeStream;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0037, code lost:
        if (r10 == -1) goto L_0x0039;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003e A[LOOP:0: B:15:0x003e->B:16:0x0040, LOOP_START, PHI: r9 
      PHI: (r9v4 int) = (r9v2 int), (r9v5 int) binds: [B:14:0x003c, B:16:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int b(android.graphics.BitmapFactory.Options r9, int r10, int r11) {
        /*
            int r0 = r9.outWidth
            double r0 = (double) r0
            int r9 = r9.outHeight
            double r2 = (double) r9
            r9 = 1
            r4 = -1
            if (r11 != r4) goto L_0x000c
            r5 = 1
            goto L_0x0019
        L_0x000c:
            double r5 = r0 * r2
            double r7 = (double) r11
            double r5 = r5 / r7
            double r5 = java.lang.Math.sqrt(r5)
            double r5 = java.lang.Math.ceil(r5)
            int r5 = (int) r5
        L_0x0019:
            if (r10 != r4) goto L_0x001e
            r0 = 128(0x80, float:1.794E-43)
            goto L_0x002e
        L_0x001e:
            double r6 = (double) r10
            double r0 = r0 / r6
            double r0 = java.lang.Math.floor(r0)
            double r2 = r2 / r6
            double r2 = java.lang.Math.floor(r2)
            double r0 = java.lang.Math.min(r0, r2)
            int r0 = (int) r0
        L_0x002e:
            if (r0 >= r5) goto L_0x0031
            goto L_0x0039
        L_0x0031:
            if (r11 != r4) goto L_0x0037
            if (r10 != r4) goto L_0x0037
            r0 = 1
            goto L_0x003a
        L_0x0037:
            if (r10 != r4) goto L_0x003a
        L_0x0039:
            r0 = r5
        L_0x003a:
            r10 = 8
            if (r0 > r10) goto L_0x0043
        L_0x003e:
            if (r9 >= r0) goto L_0x0048
            int r9 = r9 << 1
            goto L_0x003e
        L_0x0043:
            int r0 = r0 + 7
            int r0 = r0 / r10
            int r9 = r0 * 8
        L_0x0048:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.kp.b(android.graphics.BitmapFactory$Options, int, int):int");
    }
}
