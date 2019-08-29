package com.jiuyan.inimage.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.opengl.GLES10;
import android.os.Build.VERSION;
import android.view.View;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: CropUtil */
public class d {
    public static int a(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static int a(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static Bitmap a(Bitmap bitmap, float f) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        if (f < 0.0f) {
            matrix.setRotate(f);
        }
        if (f > 0.0f) {
            matrix.setRotate(f);
        }
        if (f == 0.0f) {
            return bitmap;
        }
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        } catch (OutOfMemoryError e) {
            return bitmap;
        }
    }

    public static long[] a() {
        try {
            Method method = Class.forName("android.os.Process").getMethod("readProcLines", new Class[]{String.class, String[].class, long[].class});
            String[] strArr = {"MemTotal:", "MemFree:"};
            long[] jArr = new long[strArr.length];
            jArr[0] = 30;
            jArr[1] = -30;
            Object[] objArr = {new String("/proc/meminfo"), strArr, jArr};
            if (method != null) {
                method.invoke(null, objArr);
                for (int i = 0; i < jArr.length; i++) {
                    jArr[i] = jArr[i] / 1024;
                }
            }
            if (jArr[0] != 30 || jArr[1] != -30) {
                return jArr;
            }
            jArr[0] = 0;
            jArr[1] = 0;
            return jArr;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
            return null;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return null;
        } catch (NoSuchMethodException e6) {
            e6.printStackTrace();
            return null;
        }
    }

    public static int b() {
        int[] iArr = new int[1];
        GLES10.glGetIntegerv(3379, iArr, 0);
        return iArr[0];
    }

    public static void a(View view, Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            view.postOnAnimation(runnable);
        } else {
            view.postDelayed(runnable, 16);
        }
    }

    public static boolean a(int i, int i2, int i3) {
        return Math.abs(i - i2) <= i3;
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
            }
        }
    }

    public static int a(File file) {
        if (file == null) {
            return 0;
        }
        try {
            switch (new ExifInterface(file.getAbsolutePath()).getAttributeInt("Orientation", 0)) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0089  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File a(android.content.ContentResolver r7, android.net.Uri r8) {
        /*
            r6 = 0
            if (r8 != 0) goto L_0x0005
            r0 = r6
        L_0x0004:
            return r0
        L_0x0005:
            java.lang.String r0 = "file"
            java.lang.String r1 = r8.getScheme()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x001b
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r8.getPath()
            r0.<init>(r1)
            goto L_0x0004
        L_0x001b:
            java.lang.String r0 = "content"
            java.lang.String r1 = r8.getScheme()
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x007b
            r0 = 2
            java.lang.String[] r2 = new java.lang.String[r0]
            r0 = 0
            java.lang.String r1 = "_data"
            r2[r0] = r1
            r0 = 1
            java.lang.String r1 = "_display_name"
            r2[r0] = r1
            r3 = 0
            r4 = 0
            r5 = 0
            r0 = r7
            r1 = r8
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5)     // Catch:{ SecurityException -> 0x007d, all -> 0x0085 }
            if (r1 == 0) goto L_0x0076
            boolean r0 = r1.moveToFirst()     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
            if (r0 == 0) goto L_0x0076
            java.lang.String r0 = r8.toString()     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
            java.lang.String r2 = "content://com.google.android.gallery3d"
            boolean r0 = r0.startsWith(r2)     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
            if (r0 == 0) goto L_0x006f
            java.lang.String r0 = "_display_name"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
        L_0x0057:
            r2 = -1
            if (r0 == r2) goto L_0x0076
            java.lang.String r2 = r1.getString(r0)     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
            if (r0 != 0) goto L_0x0076
            java.io.File r0 = new java.io.File     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
            r0.<init>(r2)     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
            if (r1 == 0) goto L_0x0004
            r1.close()
            goto L_0x0004
        L_0x006f:
            java.lang.String r0 = "_data"
            int r0 = r1.getColumnIndex(r0)     // Catch:{ SecurityException -> 0x008f, all -> 0x008d }
            goto L_0x0057
        L_0x0076:
            if (r1 == 0) goto L_0x007b
            r1.close()
        L_0x007b:
            r0 = r6
            goto L_0x0004
        L_0x007d:
            r0 = move-exception
            r0 = r6
        L_0x007f:
            if (r0 == 0) goto L_0x007b
            r0.close()
            goto L_0x007b
        L_0x0085:
            r0 = move-exception
            r1 = r6
        L_0x0087:
            if (r1 == 0) goto L_0x008c
            r1.close()
        L_0x008c:
            throw r0
        L_0x008d:
            r0 = move-exception
            goto L_0x0087
        L_0x008f:
            r0 = move-exception
            r0 = r1
            goto L_0x007f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jiuyan.inimage.util.d.a(android.content.ContentResolver, android.net.Uri):java.io.File");
    }
}
