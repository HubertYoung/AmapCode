package defpackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* renamed from: ahc reason: default package */
/* compiled from: ImageUtil */
public final class ahc {
    public static final boolean a = (VERSION.SDK_INT <= 10);
    private static final Config b = Config.ARGB_8888;
    private static int c;

    /* JADX WARNING: Can't wrap try/catch for region: R(2:19|20) */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        b((java.lang.Object) r3);
        r6 = a(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r1.inSampleSize++;
        b(r6);
        r7 = a(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        r2 = a(r7, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        b(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x006d, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x006e, code lost:
        r2 = r6;
        r6 = r7;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x003a */
    /* JADX WARNING: Missing exception handler attribute for start block: B:34:0x0055 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.Object a(android.content.Context r6, java.lang.Object r7) {
        /*
            r0 = 0
            android.graphics.BitmapFactory$Options r1 = new android.graphics.BitmapFactory$Options     // Catch:{ Throwable -> 0x0074 }
            r1.<init>()     // Catch:{ Throwable -> 0x0074 }
            android.graphics.Bitmap$Config r2 = b     // Catch:{ Throwable -> 0x0074 }
            r1.inPreferredConfig = r2     // Catch:{ Throwable -> 0x0074 }
            java.lang.Object r2 = a(r7)     // Catch:{ Throwable -> 0x0054, all -> 0x0051 }
            boolean r3 = r2 instanceof byte[]     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            if (r3 == 0) goto L_0x0017
            a(r6, r2, r1)     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            r6 = r2
            goto L_0x0041
        L_0x0017:
            boolean r3 = r2 instanceof java.io.InputStream     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            if (r3 == 0) goto L_0x0049
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            r4 = r2
            java.io.InputStream r4 = (java.io.InputStream) r4     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            r5 = 16384(0x4000, float:2.2959E-41)
            r3.<init>(r4, r5)     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            r4 = 1024(0x400, float:1.435E-42)
            r3.mark(r4)     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            a(r6, r3, r1)     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            int r6 = r1.outWidth     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            if (r6 > 0) goto L_0x0035
            b(r2)     // Catch:{ Throwable -> 0x0074 }
            return r0
        L_0x0035:
            r3.reset()     // Catch:{ IOException -> 0x003a }
            r6 = r3
            goto L_0x0041
        L_0x003a:
            b(r3)     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
            java.lang.Object r6 = a(r7)     // Catch:{ Throwable -> 0x004f, all -> 0x004d }
        L_0x0041:
            java.lang.Object r2 = a(r6, r1)     // Catch:{ Throwable -> 0x0055 }
            b(r6)     // Catch:{ Throwable -> 0x0074 }
            goto L_0x0069
        L_0x0049:
            b(r2)     // Catch:{ Throwable -> 0x0074 }
            return r0
        L_0x004d:
            r6 = move-exception
            goto L_0x0070
        L_0x004f:
            r6 = r2
            goto L_0x0055
        L_0x0051:
            r6 = move-exception
            r2 = r0
            goto L_0x0070
        L_0x0054:
            r6 = r0
        L_0x0055:
            int r2 = r1.inSampleSize     // Catch:{ all -> 0x006d }
            int r2 = r2 + 1
            r1.inSampleSize = r2     // Catch:{ all -> 0x006d }
            b(r6)     // Catch:{ all -> 0x006d }
            java.lang.Object r7 = a(r7)     // Catch:{ all -> 0x006d }
            java.lang.Object r2 = a(r7, r1)     // Catch:{ all -> 0x006a }
            b(r7)     // Catch:{ Throwable -> 0x0074 }
        L_0x0069:
            return r2
        L_0x006a:
            r6 = move-exception
            r2 = r7
            goto L_0x0070
        L_0x006d:
            r7 = move-exception
            r2 = r6
            r6 = r7
        L_0x0070:
            b(r2)     // Catch:{ Throwable -> 0x0074 }
            throw r6     // Catch:{ Throwable -> 0x0074 }
        L_0x0074:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ahc.a(android.content.Context, java.lang.Object):java.lang.Object");
    }

    private static Object a(Object obj) throws IOException {
        if (obj instanceof String) {
            return new FileInputStream((String) obj);
        }
        return obj instanceof File ? new FileInputStream((File) obj) : obj;
    }

    private static final Object a(Object obj, Options options) throws IllegalArgumentException {
        if (obj instanceof InputStream) {
            return BitmapFactory.decodeStream((InputStream) obj, null, options);
        }
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        }
        throw new IllegalArgumentException("byte[] and InputStream only!!");
    }

    private static final void b(Object obj) {
        try {
            if (obj instanceof InputStream) {
                InputStream inputStream = (InputStream) obj;
                inputStream.read();
                inputStream.close();
            }
        } catch (Exception unused) {
        }
    }

    private static int a(Context context) {
        if (c <= 0) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            c = Math.max(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        return c;
    }

    private static boolean a(Context context, Object obj, Options options) throws Exception {
        int i;
        float f;
        options.inJustDecodeBounds = true;
        try {
            if (options.inSampleSize <= 0) {
                a(obj, options);
                int i2 = options.outWidth;
                int i3 = options.outHeight;
                if (i2 * i3 < 40000) {
                    i = 0;
                } else {
                    int a2 = a(context);
                    int a3 = a(context);
                    if (i2 <= a2) {
                        if (i3 <= a3) {
                            f = 1.0f;
                            i = (int) Math.ceil((double) f);
                        }
                    }
                    f = Math.max(((float) i2) / ((float) a2), ((float) i3) / ((float) a3));
                    i = (int) Math.ceil((double) f);
                }
                options.inSampleSize = i;
            }
            return false;
        } finally {
            options.inJustDecodeBounds = false;
            options.inInputShareable = obj instanceof byte[];
            options.inPurgeable = true;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0035 A[SYNTHETIC, Splitter:B:16:0x0035] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0043 A[SYNTHETIC, Splitter:B:24:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.Bitmap d(java.lang.String r3) {
        /*
            r0 = 0
            byte[] r3 = defpackage.agv.a(r3)     // Catch:{ Exception -> 0x002e, all -> 0x002c }
            android.graphics.BitmapFactory$Options r1 = new android.graphics.BitmapFactory$Options     // Catch:{ Exception -> 0x002e, all -> 0x002c }
            r1.<init>()     // Catch:{ Exception -> 0x002e, all -> 0x002c }
            r2 = 1
            r1.inSampleSize = r2     // Catch:{ Exception -> 0x002e, all -> 0x002c }
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x002e, all -> 0x002c }
            r2.<init>(r3)     // Catch:{ Exception -> 0x002e, all -> 0x002c }
            java.lang.ref.SoftReference r3 = new java.lang.ref.SoftReference     // Catch:{ Exception -> 0x002a }
            android.graphics.Bitmap r1 = android.graphics.BitmapFactory.decodeStream(r2, r0, r1)     // Catch:{ Exception -> 0x002a }
            r3.<init>(r1)     // Catch:{ Exception -> 0x002a }
            java.lang.Object r3 = r3.get()     // Catch:{ Exception -> 0x002a }
            android.graphics.Bitmap r3 = (android.graphics.Bitmap) r3     // Catch:{ Exception -> 0x002a }
            r2.close()     // Catch:{ IOException -> 0x0025 }
            goto L_0x003e
        L_0x0025:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x003e
        L_0x002a:
            r3 = move-exception
            goto L_0x0030
        L_0x002c:
            r3 = move-exception
            goto L_0x0041
        L_0x002e:
            r3 = move-exception
            r2 = r0
        L_0x0030:
            r3.printStackTrace()     // Catch:{ all -> 0x003f }
            if (r2 == 0) goto L_0x003d
            r2.close()     // Catch:{ IOException -> 0x0039 }
            goto L_0x003d
        L_0x0039:
            r3 = move-exception
            r3.printStackTrace()
        L_0x003d:
            r3 = r0
        L_0x003e:
            return r3
        L_0x003f:
            r3 = move-exception
            r0 = r2
        L_0x0041:
            if (r0 == 0) goto L_0x004b
            r0.close()     // Catch:{ IOException -> 0x0047 }
            goto L_0x004b
        L_0x0047:
            r0 = move-exception
            r0.printStackTrace()
        L_0x004b:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ahc.d(java.lang.String):android.graphics.Bitmap");
    }

    @Deprecated
    public static Bitmap a(String str) {
        Bitmap bitmap;
        if (str.contains("http")) {
            return null;
        }
        if (str.contains("data:image/jpeg;base64")) {
            bitmap = d(str.replace("data:image/jpeg;base64,", ""));
        } else if (str.contains("data:image/png;base64")) {
            bitmap = d(str.replace("data:image/png;base64,", ""));
        } else {
            bitmap = d(str);
        }
        return bitmap;
    }

    public static Bitmap a(Context context, int i) {
        Drawable drawable = context.getResources().getDrawable(i);
        if (drawable == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static Bitmap a(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i) / ((float) width), ((float) i2) / ((float) height));
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Options b(String str) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return options;
    }

    public static Bitmap a(int i, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError unused) {
            return bitmap;
        }
    }

    public static int c(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt == 6) {
                return 90;
            }
            if (attributeInt != 8) {
                return 0;
            }
            return 270;
        } catch (IOException unused) {
            return 0;
        }
    }

    public static Bitmap a(Context context, String str) {
        return (Bitmap) a(context, (Object) str);
    }
}
