package defpackage;

import android.app.Application;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: dte reason: default package */
/* compiled from: GalleryImageUtil */
public final class dte {
    public static Bitmap a(Uri uri, int i) {
        Application application = AMapAppGlobal.getApplication();
        if (application == null) {
            return null;
        }
        ContentResolver contentResolver = application.getContentResolver();
        if (contentResolver == null) {
            return null;
        }
        try {
            InputStream openInputStream = contentResolver.openInputStream(uri);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(openInputStream, null, options);
            openInputStream.close();
            if (options.outWidth != -1) {
                if (options.outHeight != -1) {
                    AtomicReference atomicReference = new AtomicReference(Integer.valueOf(0));
                    AtomicReference atomicReference2 = new AtomicReference(Integer.valueOf(0));
                    a(options, i, atomicReference, atomicReference2);
                    options.inSampleSize = a(options, ((Integer) atomicReference.get()).intValue(), ((Integer) atomicReference2.get()).intValue());
                    options.inJustDecodeBounds = false;
                    InputStream openInputStream2 = contentResolver.openInputStream(uri);
                    Bitmap decodeStream = BitmapFactory.decodeStream(openInputStream2, null, options);
                    openInputStream2.close();
                    return decodeStream;
                }
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    public static Bitmap a(String str, int i) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        AtomicReference atomicReference = new AtomicReference(Integer.valueOf(0));
        AtomicReference atomicReference2 = new AtomicReference(Integer.valueOf(0));
        a(options, i, atomicReference, atomicReference2);
        options.inSampleSize = a(options, ((Integer) atomicReference.get()).intValue(), ((Integer) atomicReference2.get()).intValue());
        options.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        if (decodeFile == null) {
            return decodeFile;
        }
        int c = ahc.c(str);
        if (c == 0) {
            return decodeFile;
        }
        return ahc.a(c, decodeFile);
    }

    private static int a(Options options, int i, int i2) {
        int i3 = 1;
        if (i == 0 || i2 == 0) {
            return 1;
        }
        int i4 = options.outWidth;
        int i5 = options.outHeight;
        boolean z = b.a;
        if (i4 > i || i5 > i2) {
            int round = Math.round(((float) i4) / ((float) i));
            int round2 = Math.round(((float) i5) / ((float) i2));
            i3 = round < round2 ? round : round2;
        }
        return i3;
    }

    public static void a(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0071 A[SYNTHETIC, Splitter:B:32:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0080 A[SYNTHETIC, Splitter:B:39:0x0080] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r3, android.graphics.Bitmap r4, java.lang.String r5) {
        /*
            if (r4 == 0) goto L_0x0089
            boolean r0 = r4.isRecycled()
            if (r0 != 0) goto L_0x0089
            boolean r0 = android.text.TextUtils.isEmpty(r5)
            if (r0 == 0) goto L_0x0010
            goto L_0x0089
        L_0x0010:
            java.io.File r0 = new java.io.File
            r0.<init>(r3)
            java.io.File r3 = new java.io.File
            java.lang.String r0 = r0.getAbsolutePath()
            r3.<init>(r0)
            boolean r0 = r3.exists()
            if (r0 != 0) goto L_0x0027
            r3.mkdirs()
        L_0x0027:
            java.io.File r0 = new java.io.File
            r0.<init>(r3, r5)
            boolean r3 = r0.exists()
            if (r3 == 0) goto L_0x0037
            java.lang.String r3 = r0.getAbsolutePath()
            return r3
        L_0x0037:
            r0.createNewFile()     // Catch:{ IOException -> 0x003b }
            goto L_0x003f
        L_0x003b:
            r3 = move-exception
            r3.printStackTrace()
        L_0x003f:
            java.lang.String r3 = r0.getAbsolutePath()
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream
            r5.<init>()
            android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG
            r2 = 100
            r4.compress(r1, r2, r5)
            r4 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x006b }
            r1.<init>(r3)     // Catch:{ Exception -> 0x006b }
            byte[] r3 = r5.toByteArray()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            r1.write(r3)     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            r1.flush()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            r1.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x0079
        L_0x0063:
            r3 = move-exception
            r4 = r1
            goto L_0x007e
        L_0x0066:
            r3 = move-exception
            r4 = r1
            goto L_0x006c
        L_0x0069:
            r3 = move-exception
            goto L_0x007e
        L_0x006b:
            r3 = move-exception
        L_0x006c:
            r3.printStackTrace()     // Catch:{ all -> 0x0069 }
            if (r4 == 0) goto L_0x0079
            r4.close()     // Catch:{ IOException -> 0x0075 }
            goto L_0x0079
        L_0x0075:
            r3 = move-exception
            r3.printStackTrace()
        L_0x0079:
            java.lang.String r3 = r0.getAbsolutePath()
            return r3
        L_0x007e:
            if (r4 == 0) goto L_0x0088
            r4.close()     // Catch:{ IOException -> 0x0084 }
            goto L_0x0088
        L_0x0084:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0088:
            throw r3
        L_0x0089:
            java.lang.String r3 = ""
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dte.a(java.lang.String, android.graphics.Bitmap, java.lang.String):java.lang.String");
    }

    private static void a(Options options, int i, AtomicReference<Integer> atomicReference, AtomicReference<Integer> atomicReference2) {
        atomicReference.set(Integer.valueOf(0));
        atomicReference2.set(Integer.valueOf(0));
        int i2 = options.outWidth;
        int i3 = options.outHeight;
        if (i3 > i2) {
            atomicReference.set(Integer.valueOf(Math.round(((float) i2) / (((float) i3) / ((float) i)))));
            atomicReference2.set(Integer.valueOf(i));
            return;
        }
        atomicReference.set(Integer.valueOf(i));
        atomicReference2.set(Integer.valueOf(Math.round(((float) i3) / (((float) i2) / ((float) i)))));
    }

    public static Bitmap a(Bitmap bitmap, int i) {
        if (bitmap == null || i <= 0) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width > i || height > i) {
            int min = (Math.min(width, height) * i) / Math.max(width, height);
            int i2 = width > height ? i : min;
            if (width > height) {
                i = min;
            }
            try {
                bitmap = Bitmap.createScaledBitmap(bitmap, i2, i, true);
            } catch (Exception unused) {
            }
        }
        return bitmap;
    }

    public static String a(String str) {
        String name = new File(str).getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(name.substring(0, lastIndexOf));
        sb.append("_temp");
        sb.append(name.substring(lastIndexOf));
        return sb.toString();
    }

    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return "";
        }
        String[] split = str.split(",");
        String[] split2 = split[0].split("/");
        double parseDouble = Double.parseDouble(split2[0].trim()) / Double.parseDouble(split2[1].trim());
        String[] split3 = split[1].split("/");
        String[] split4 = split[2].split("/");
        double parseDouble2 = parseDouble + ((Double.parseDouble(split3[0].trim()) / Double.parseDouble(split3[1].trim())) / 60.0d) + ((Double.parseDouble(split4[0].trim()) / Double.parseDouble(split4[1].trim())) / 3600.0d);
        if (!str2.equals("S") && !str2.equals("W")) {
            return String.valueOf(parseDouble2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(-parseDouble2);
        return sb.toString();
    }
}
