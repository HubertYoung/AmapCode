package com.huawei.android.pushselfshow.utils.c;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.huawei.android.pushagent.a.a.c;

public class a {
    public Bitmap a(Context context, Bitmap bitmap, float f, float f2) {
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(f / ((float) width), f2 / ((float) height));
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            if (createBitmap != null) {
                c.a("PushSelfShowLog", "reScaleBitmap success");
                return createBitmap;
            }
        } catch (Exception e) {
            c.c("PushSelfShowLog", "reScaleBitmap fail ,error ：".concat(String.valueOf(e)), e);
        }
        return bitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:45:0x0113 A[SYNTHETIC, Splitter:B:45:0x0113] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x014c A[SYNTHETIC, Splitter:B:61:0x014c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.graphics.Bitmap a(android.content.Context r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r2 = "image"
            r1.<init>(r2)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r1.append(r2)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r2 = com.huawei.android.pushselfshow.utils.b.b.a(r8)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            boolean r4 = r3.exists()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            if (r4 != 0) goto L_0x0056
            java.lang.String r4 = "PushSelfShowLog"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r6 = "mkdir: "
            r5.<init>(r6)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r6 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r5.append(r6)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r5 = r5.toString()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            com.huawei.android.pushagent.a.a.c.a(r4, r5)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            boolean r4 = r3.mkdirs()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            if (r4 != 0) goto L_0x0056
            java.lang.String r4 = "PushSelfShowLog"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r6 = "file mkdir failed ,path is "
            r5.<init>(r6)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r3 = r3.getPath()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r5.append(r3)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r3 = r5.toString()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            com.huawei.android.pushagent.a.a.c.a(r4, r3)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
        L_0x0056:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r3.<init>()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r3.append(r2)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r2 = java.io.File.separator     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r3.append(r2)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r3.append(r1)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.String r3 = "try to download image to "
            java.lang.String r4 = java.lang.String.valueOf(r1)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.lang.String r3 = r3.concat(r4)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            com.huawei.android.pushagent.a.a.c.a(r2, r3)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            com.huawei.android.pushselfshow.utils.b.b r2 = new com.huawei.android.pushselfshow.utils.b.b     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r2.<init>()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            boolean r8 = r2.b(r8, r9, r1)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            if (r8 == 0) goto L_0x00b5
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.String r9 = "download successed"
            com.huawei.android.pushagent.a.a.c.a(r8, r9)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            android.graphics.BitmapFactory$Options r8 = new android.graphics.BitmapFactory$Options     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r8.<init>()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r9 = 0
            r8.inDither = r9     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r9 = 1
            r8.inPurgeable = r9     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r8.inSampleSize = r9     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            android.graphics.Bitmap$Config r9 = android.graphics.Bitmap.Config.RGB_565     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r8.inPreferredConfig = r9     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.io.File r9 = new java.io.File     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r9.<init>(r1)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00b2, all -> 0x00af }
            r1.<init>(r9)     // Catch:{ Exception -> 0x00b2, all -> 0x00af }
            android.graphics.Bitmap r8 = android.graphics.BitmapFactory.decodeStream(r1, r0, r8)     // Catch:{ Exception -> 0x00ad }
            r0 = r1
            goto L_0x00be
        L_0x00ad:
            r8 = move-exception
            goto L_0x00fa
        L_0x00af:
            r8 = move-exception
            goto L_0x014a
        L_0x00b2:
            r8 = move-exception
            r1 = r0
            goto L_0x00fa
        L_0x00b5:
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.String r9 = "download failed"
            com.huawei.android.pushagent.a.a.c.a(r8, r9)     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            r8 = r0
            r9 = r8
        L_0x00be:
            if (r0 == 0) goto L_0x00c6
            r0.close()     // Catch:{ Exception -> 0x00c4 }
            goto L_0x00c6
        L_0x00c4:
            r9 = move-exception
            goto L_0x00dc
        L_0x00c6:
            if (r9 == 0) goto L_0x0147
            boolean r0 = r9.isFile()     // Catch:{ Exception -> 0x00c4 }
            if (r0 == 0) goto L_0x0147
            boolean r9 = r9.delete()     // Catch:{ Exception -> 0x00c4 }
            if (r9 == 0) goto L_0x0147
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.String r0 = "image delete success"
            com.huawei.android.pushagent.a.a.c.a(r9, r0)     // Catch:{ Exception -> 0x00c4 }
            return r8
        L_0x00dc:
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "is.close() error"
            r1.<init>(r2)
            java.lang.String r2 = r9.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.huawei.android.pushagent.a.a.c.c(r0, r1, r9)
            return r8
        L_0x00f4:
            r8 = move-exception
            r9 = r0
            goto L_0x014a
        L_0x00f7:
            r8 = move-exception
            r9 = r0
            r1 = r9
        L_0x00fa:
            java.lang.String r2 = "PushSelfShowLog"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0148 }
            java.lang.String r4 = "getRemoteImage  failed  ,errorinfo is "
            r3.<init>(r4)     // Catch:{ all -> 0x0148 }
            java.lang.String r4 = r8.toString()     // Catch:{ all -> 0x0148 }
            r3.append(r4)     // Catch:{ all -> 0x0148 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0148 }
            com.huawei.android.pushagent.a.a.c.c(r2, r3, r8)     // Catch:{ all -> 0x0148 }
            if (r1 == 0) goto L_0x0119
            r1.close()     // Catch:{ Exception -> 0x0117 }
            goto L_0x0119
        L_0x0117:
            r8 = move-exception
            goto L_0x012f
        L_0x0119:
            if (r9 == 0) goto L_0x0146
            boolean r8 = r9.isFile()     // Catch:{ Exception -> 0x0117 }
            if (r8 == 0) goto L_0x0146
            boolean r8 = r9.delete()     // Catch:{ Exception -> 0x0117 }
            if (r8 == 0) goto L_0x0146
            java.lang.String r8 = "PushSelfShowLog"
            java.lang.String r9 = "image delete success"
            com.huawei.android.pushagent.a.a.c.a(r8, r9)     // Catch:{ Exception -> 0x0117 }
            goto L_0x0146
        L_0x012f:
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "is.close() error"
            r1.<init>(r2)
            java.lang.String r2 = r8.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.huawei.android.pushagent.a.a.c.c(r9, r1, r8)
        L_0x0146:
            r8 = r0
        L_0x0147:
            return r8
        L_0x0148:
            r8 = move-exception
            r0 = r1
        L_0x014a:
            if (r0 == 0) goto L_0x0152
            r0.close()     // Catch:{ Exception -> 0x0150 }
            goto L_0x0152
        L_0x0150:
            r9 = move-exception
            goto L_0x0168
        L_0x0152:
            if (r9 == 0) goto L_0x017f
            boolean r0 = r9.isFile()     // Catch:{ Exception -> 0x0150 }
            if (r0 == 0) goto L_0x017f
            boolean r9 = r9.delete()     // Catch:{ Exception -> 0x0150 }
            if (r9 == 0) goto L_0x017f
            java.lang.String r9 = "PushSelfShowLog"
            java.lang.String r0 = "image delete success"
            com.huawei.android.pushagent.a.a.c.a(r9, r0)     // Catch:{ Exception -> 0x0150 }
            goto L_0x017f
        L_0x0168:
            java.lang.String r0 = "PushSelfShowLog"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "is.close() error"
            r1.<init>(r2)
            java.lang.String r2 = r9.toString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.huawei.android.pushagent.a.a.c.c(r0, r1, r9)
        L_0x017f:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.utils.c.a.a(android.content.Context, java.lang.String):android.graphics.Bitmap");
    }
}
