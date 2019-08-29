package com.alipay.mobile.common.helper;

import android.util.Log;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ZipHelper {
    static final String TAG = "ZipHelper";

    /* JADX WARNING: Removed duplicated region for block: B:30:0x006c A[SYNTHETIC, Splitter:B:30:0x006c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean unZip(java.io.InputStream r12, java.lang.String r13) {
        /*
            r6 = 1
            r8 = 0
            r10 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r10]
            java.util.zip.ZipInputStream r9 = new java.util.zip.ZipInputStream     // Catch:{ Exception -> 0x0097 }
            java.io.BufferedInputStream r10 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0097 }
            r10.<init>(r12)     // Catch:{ Exception -> 0x0097 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x0097 }
        L_0x0010:
            java.util.zip.ZipEntry r7 = r9.getNextEntry()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            if (r7 == 0) goto L_0x0077
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            r10.<init>()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            java.lang.StringBuilder r10 = r10.append(r13)     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            java.lang.String r11 = r7.getName()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            java.lang.StringBuilder r10 = r10.append(r11)     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            java.lang.String r10 = r10.toString()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            r2.<init>(r10)     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            boolean r10 = r7.isDirectory()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            if (r10 == 0) goto L_0x004b
            r2.mkdirs()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            goto L_0x0010
        L_0x003a:
            r1 = move-exception
            r8 = r9
        L_0x003c:
            java.lang.String r10 = "ZipHelper"
            android.util.Log.w(r10, r1)     // Catch:{ all -> 0x0095 }
            r6 = 0
            if (r8 == 0) goto L_0x004a
            r8.closeEntry()     // Catch:{ IOException -> 0x0087 }
            r8.close()     // Catch:{ IOException -> 0x0087 }
        L_0x004a:
            return r6
        L_0x004b:
            java.io.File r4 = r2.getParentFile()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            boolean r10 = r4.exists()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            if (r10 != 0) goto L_0x0058
            r4.mkdirs()     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
        L_0x0058:
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
        L_0x005d:
            int r5 = r9.read(r0)     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            if (r5 <= 0) goto L_0x0073
            r10 = 0
            r3.write(r0, r10, r5)     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            goto L_0x005d
        L_0x0068:
            r10 = move-exception
            r8 = r9
        L_0x006a:
            if (r8 == 0) goto L_0x0072
            r8.closeEntry()     // Catch:{ IOException -> 0x008e }
            r8.close()     // Catch:{ IOException -> 0x008e }
        L_0x0072:
            throw r10
        L_0x0073:
            close(r3)     // Catch:{ Exception -> 0x003a, all -> 0x0068 }
            goto L_0x0010
        L_0x0077:
            r9.closeEntry()     // Catch:{ IOException -> 0x007f }
            r9.close()     // Catch:{ IOException -> 0x007f }
            r8 = r9
            goto L_0x004a
        L_0x007f:
            r1 = move-exception
            java.lang.String r10 = "ZipHelper"
            android.util.Log.w(r10, r1)
            r8 = r9
            goto L_0x004a
        L_0x0087:
            r1 = move-exception
            java.lang.String r10 = "ZipHelper"
            android.util.Log.w(r10, r1)
            goto L_0x004a
        L_0x008e:
            r1 = move-exception
            java.lang.String r11 = "ZipHelper"
            android.util.Log.w(r11, r1)
            goto L_0x0072
        L_0x0095:
            r10 = move-exception
            goto L_0x006a
        L_0x0097:
            r1 = move-exception
            goto L_0x003c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.helper.ZipHelper.unZip(java.io.InputStream, java.lang.String):boolean");
    }

    static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.w(TAG, e);
            }
        }
    }

    public static boolean unZip(String unZipfileName, String destPath) {
        boolean unZipState = false;
        try {
            return unZip((InputStream) new FileInputStream(unZipfileName), destPath);
        } catch (FileNotFoundException e) {
            Log.w(TAG, e);
            return unZipState;
        }
    }
}
