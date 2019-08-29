package com.alipay.mobile.antui.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public class AUFileUtils {
    public static String getCacheFileDocPath(Context context, String docName) {
        File cacheDir;
        if ("mounted".equals(Environment.getExternalStorageState())) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "/alipay/" + context.getApplicationInfo().packageName + "/" + docName);
        } else {
            cacheDir = context.getCacheDir();
        }
        if (cacheDir != null && !cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        if (cacheDir == null) {
            return "";
        }
        String cachePath = cacheDir.getAbsolutePath();
        if (!cachePath.endsWith(File.separator)) {
            return cachePath + File.separator;
        }
        return cachePath;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0037 A[SYNTHETIC, Splitter:B:16:0x0037] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0043 A[SYNTHETIC, Splitter:B:22:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getStringFromFile(java.lang.String r8) {
        /*
            java.io.File r3 = new java.io.File
            r3.<init>(r8)
            r1 = 0
            java.lang.String r6 = "mounted"
            java.lang.String r7 = android.os.Environment.getExternalStorageState()
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L_0x002a
            r4 = 0
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0031 }
            r5.<init>(r3)     // Catch:{ IOException -> 0x0031 }
            int r6 = r5.available()     // Catch:{ IOException -> 0x004f, all -> 0x004c }
            byte[] r0 = new byte[r6]     // Catch:{ IOException -> 0x004f, all -> 0x004c }
            r5.read(r0)     // Catch:{ IOException -> 0x004f, all -> 0x004c }
            java.lang.String r2 = new java.lang.String     // Catch:{ IOException -> 0x004f, all -> 0x004c }
            r2.<init>(r0)     // Catch:{ IOException -> 0x004f, all -> 0x004c }
            r5.close()     // Catch:{ IOException -> 0x002b }
            r1 = r2
        L_0x002a:
            return r1
        L_0x002b:
            r6 = move-exception
            r6.printStackTrace()
            r1 = r2
            goto L_0x002a
        L_0x0031:
            r6 = move-exception
        L_0x0032:
            r6.printStackTrace()     // Catch:{ all -> 0x0040 }
            if (r4 == 0) goto L_0x002a
            r4.close()     // Catch:{ IOException -> 0x003b }
            goto L_0x002a
        L_0x003b:
            r6 = move-exception
            r6.printStackTrace()
            goto L_0x002a
        L_0x0040:
            r6 = move-exception
        L_0x0041:
            if (r4 == 0) goto L_0x0046
            r4.close()     // Catch:{ IOException -> 0x0047 }
        L_0x0046:
            throw r6
        L_0x0047:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x0046
        L_0x004c:
            r6 = move-exception
            r4 = r5
            goto L_0x0041
        L_0x004f:
            r6 = move-exception
            r4 = r5
            goto L_0x0032
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.antui.utils.AUFileUtils.getStringFromFile(java.lang.String):java.lang.String");
    }
}
