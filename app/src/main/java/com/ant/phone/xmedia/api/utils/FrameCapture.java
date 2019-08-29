package com.ant.phone.xmedia.api.utils;

import com.alipay.alipaylogger.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FrameCapture {
    private byte[] a;

    /* JADX WARNING: Removed duplicated region for block: B:30:0x00fa A[SYNTHETIC, Splitter:B:30:0x00fa] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ff A[SYNTHETIC, Splitter:B:33:0x00ff] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0108 A[SYNTHETIC, Splitter:B:38:0x0108] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x010d A[SYNTHETIC, Splitter:B:41:0x010d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(byte[] r21, int r22, int r23, int r24) {
        /*
            r20 = this;
            r0 = r20
            byte[] r4 = r0.a
            if (r4 != 0) goto L_0x0012
            int r4 = r22 * r23
            int r4 = r4 * 12
            int r4 = r4 / 8
            byte[] r4 = new byte[r4]
            r0 = r20
            r0.a = r4
        L_0x0012:
            long r18 = java.lang.System.currentTimeMillis()
            r4 = 90
            r0 = r24
            if (r0 != r4) goto L_0x00c3
            r0 = r20
            byte[] r4 = r0.a
            r0 = r21
            r1 = r22
            r2 = r23
            com.ant.phone.xmedia.api.utils.VideoHelper.b(r0, r4, r1, r2)
        L_0x0029:
            android.graphics.YuvImage r3 = new android.graphics.YuvImage
            r0 = r20
            byte[] r4 = r0.a
            r5 = 17
            r8 = 0
            r6 = r23
            r7 = r22
            r3.<init>(r4, r5, r6, r7, r8)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.io.File r5 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r5 = r5.getAbsolutePath()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = java.io.File.separator
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = "xmedia_"
            java.lang.StringBuilder r4 = r4.append(r5)
            long r5 = java.lang.System.currentTimeMillis()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r5 = ".jpg"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r16 = r4.toString()
            r13 = 0
            r10 = 0
            java.io.ByteArrayOutputStream r11 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x00f0 }
            r11.<init>()     // Catch:{ Exception -> 0x00f0 }
            java.io.FileOutputStream r14 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0120, all -> 0x0119 }
            r0 = r16
            r14.<init>(r0)     // Catch:{ Exception -> 0x0120, all -> 0x0119 }
            android.graphics.Rect r4 = new android.graphics.Rect     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            r5 = 0
            r6 = 0
            r0 = r23
            r1 = r22
            r4.<init>(r5, r6, r0, r1)     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            r5 = 100
            r3.compressToJpeg(r4, r5, r11)     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            byte[] r15 = r11.toByteArray()     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            r4 = 0
            int r5 = r15.length     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            android.graphics.Bitmap r9 = android.graphics.BitmapFactory.decodeByteArray(r15, r4, r5)     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            android.graphics.Bitmap$CompressFormat r4 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            r5 = 100
            r9.compress(r4, r5, r14)     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            r9.recycle()     // Catch:{ Exception -> 0x0123, all -> 0x011c }
            r14.close()     // Catch:{ IOException -> 0x0111 }
        L_0x009d:
            r11.close()     // Catch:{ IOException -> 0x00ec }
            r10 = r11
            r13 = r14
        L_0x00a2:
            java.lang.String r4 = "FrameCapture"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "saveFrame took "
            r5.<init>(r6)
            long r6 = java.lang.System.currentTimeMillis()
            long r6 = r6 - r18
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "ms"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r5 = r5.toString()
            com.alipay.alipaylogger.Log.d(r4, r5)
            return
        L_0x00c3:
            if (r24 != 0) goto L_0x00dd
            r4 = 0
            r0 = r20
            byte[] r5 = r0.a
            r6 = 0
            r0 = r20
            byte[] r7 = r0.a
            int r7 = r7.length
            r0 = r21
            java.lang.System.arraycopy(r0, r4, r5, r6, r7)
            r17 = r22
            r22 = r23
            r23 = r17
            goto L_0x0029
        L_0x00dd:
            r0 = r20
            byte[] r4 = r0.a
            r0 = r21
            r1 = r22
            r2 = r23
            com.ant.phone.xmedia.api.utils.VideoHelper.a(r0, r4, r1, r2)
            goto L_0x0029
        L_0x00ec:
            r4 = move-exception
            r10 = r11
            r13 = r14
            goto L_0x00a2
        L_0x00f0:
            r12 = move-exception
        L_0x00f1:
            java.lang.String r4 = "FrameCapture"
            java.lang.String r5 = "saveFrame err"
            com.alipay.alipaylogger.Log.e(r4, r5, r12)     // Catch:{ all -> 0x0105 }
            if (r13 == 0) goto L_0x00fd
            r13.close()     // Catch:{ IOException -> 0x0113 }
        L_0x00fd:
            if (r10 == 0) goto L_0x00a2
            r10.close()     // Catch:{ IOException -> 0x0103 }
            goto L_0x00a2
        L_0x0103:
            r4 = move-exception
            goto L_0x00a2
        L_0x0105:
            r4 = move-exception
        L_0x0106:
            if (r13 == 0) goto L_0x010b
            r13.close()     // Catch:{ IOException -> 0x0115 }
        L_0x010b:
            if (r10 == 0) goto L_0x0110
            r10.close()     // Catch:{ IOException -> 0x0117 }
        L_0x0110:
            throw r4
        L_0x0111:
            r4 = move-exception
            goto L_0x009d
        L_0x0113:
            r4 = move-exception
            goto L_0x00fd
        L_0x0115:
            r5 = move-exception
            goto L_0x010b
        L_0x0117:
            r5 = move-exception
            goto L_0x0110
        L_0x0119:
            r4 = move-exception
            r10 = r11
            goto L_0x0106
        L_0x011c:
            r4 = move-exception
            r10 = r11
            r13 = r14
            goto L_0x0106
        L_0x0120:
            r12 = move-exception
            r10 = r11
            goto L_0x00f1
        L_0x0123:
            r12 = move-exception
            r10 = r11
            r13 = r14
            goto L_0x00f1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ant.phone.xmedia.api.utils.FrameCapture.a(byte[], int, int, int):void");
    }

    public static String a(String path, byte[] content) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path), true);
            fos.write(content);
            fos.close();
        } catch (IOException e) {
            Log.e("FrameCapture", "saveContent exp:", e);
        }
        return path;
    }
}
