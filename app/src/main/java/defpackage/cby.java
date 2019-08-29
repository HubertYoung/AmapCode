package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import java.io.File;
import java.io.IOException;

/* renamed from: cby reason: default package */
/* compiled from: AlbumCommonUtil */
public final class cby {
    public static final String a;
    public static final String b;
    public static final String c;

    static {
        StringBuilder sb = new StringBuilder("autonavi");
        sb.append(File.separator);
        sb.append("saved");
        sb.append(File.separator);
        a = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb2.append(File.separator);
        sb2.append(a);
        b = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb3.append(File.separator);
        sb3.append("autonavi");
        sb3.append(File.separator);
        sb3.append("realscene");
        sb3.append(File.separator);
        c = sb3.toString();
    }

    private static Bitmap a(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = 960;
        if (width > 960 || height > 960) {
            int min = (Math.min(width, height) * 960) / Math.max(width, height);
            int i2 = width > height ? 960 : min;
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

    private static void b(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public static int b(String str) {
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
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Bitmap a(int i, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static String a(Bitmap bitmap, String str) {
        return a(bitmap, str, Long.MAX_VALUE);
    }

    private static String a(Bitmap bitmap, String str, long j) {
        if (bitmap == null || bitmap.isRecycled() || TextUtils.isEmpty(str)) {
            return "";
        }
        File file = new File(new File(c).getAbsolutePath());
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, str);
        if (file2.exists()) {
            return file2.getAbsolutePath();
        }
        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        b(bitmap, file2.getAbsolutePath(), j);
        return file2.getAbsolutePath();
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x003b A[SYNTHETIC, Splitter:B:19:0x003b] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x004c A[SYNTHETIC, Splitter:B:27:0x004c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long b(android.graphics.Bitmap r4, java.lang.String r5, long r6) {
        /*
            r0 = 100
        L_0x0002:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            android.graphics.Bitmap$CompressFormat r2 = android.graphics.Bitmap.CompressFormat.JPEG
            r4.compress(r2, r0, r1)
            int r0 = r0 + -5
            int r2 = r1.size()
            long r2 = (long) r2
            int r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r2 <= 0) goto L_0x0019
            if (r0 > 0) goto L_0x0002
        L_0x0019:
            r4 = 0
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0033, all -> 0x002f }
            r6.<init>(r5)     // Catch:{ Exception -> 0x0033, all -> 0x002f }
            byte[] r4 = r1.toByteArray()     // Catch:{ Exception -> 0x002d }
            r6.write(r4)     // Catch:{ Exception -> 0x002d }
            r6.flush()     // Catch:{ Exception -> 0x002d }
            r6.close()     // Catch:{ IOException -> 0x003f }
            goto L_0x0043
        L_0x002d:
            r4 = move-exception
            goto L_0x0036
        L_0x002f:
            r5 = move-exception
            r6 = r4
            r4 = r5
            goto L_0x004a
        L_0x0033:
            r5 = move-exception
            r6 = r4
            r4 = r5
        L_0x0036:
            r4.printStackTrace()     // Catch:{ all -> 0x0049 }
            if (r6 == 0) goto L_0x0043
            r6.close()     // Catch:{ IOException -> 0x003f }
            goto L_0x0043
        L_0x003f:
            r4 = move-exception
            r4.printStackTrace()
        L_0x0043:
            int r4 = r1.size()
            long r4 = (long) r4
            return r4
        L_0x0049:
            r4 = move-exception
        L_0x004a:
            if (r6 == 0) goto L_0x0054
            r6.close()     // Catch:{ IOException -> 0x0050 }
            goto L_0x0054
        L_0x0050:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0054:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cby.b(android.graphics.Bitmap, java.lang.String, long):long");
    }

    @Nullable
    public static String a(String str) {
        String str2;
        int i;
        String str3;
        try {
            Options options = new Options();
            int i2 = 1;
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            int i3 = options.outWidth;
            int i4 = options.outHeight;
            if (i4 > i3) {
                i = Math.round(((float) i3) / (((float) i4) / 960.0f));
            } else {
                i = Math.round(((float) i4) / (((float) i3) / 960.0f));
            }
            if (i != 0) {
                int i5 = options.outWidth;
                int i6 = options.outHeight;
                if (i5 > i || i6 > 960) {
                    i2 = Math.round(((float) i5) / ((float) i));
                    int round = Math.round(((float) i6) / 960.0f);
                    if (i2 >= round) {
                        i2 = round;
                    }
                }
            }
            options.inSampleSize = i2;
            options.inJustDecodeBounds = false;
            Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
            if (decodeFile == null) {
                return null;
            }
            Bitmap a2 = a(decodeFile);
            if (a2 != decodeFile) {
                b(decodeFile);
            }
            int b2 = b(str);
            if (a2 == null) {
                return null;
            }
            Bitmap a3 = a(b2, a2);
            if (a3 != a2) {
                b(a2);
            }
            String name = new File(str).getName();
            int lastIndexOf = name.lastIndexOf(".");
            if (lastIndexOf != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(name.substring(0, lastIndexOf));
                sb.append("_");
                sb.append(agy.a(str));
                sb.append("_temp");
                sb.append(name.substring(lastIndexOf));
                str3 = sb.toString();
            } else {
                str3 = null;
            }
            str2 = a(a3, str3, 153600);
            return str2;
        } catch (Exception e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(e.getMessage());
            AMapLog.i("wtf", sb2.toString());
            str2 = null;
            return str2;
        } catch (OutOfMemoryError unused) {
            str2 = null;
            return str2;
        }
    }
}
