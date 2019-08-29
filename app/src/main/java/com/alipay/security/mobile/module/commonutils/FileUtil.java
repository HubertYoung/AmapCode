package com.alipay.security.mobile.module.commonutils;

import java.io.File;

public class FileUtil {
    /* JADX WARNING: Removed duplicated region for block: B:15:0x001c A[SYNTHETIC, Splitter:B:15:0x001c] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0022 A[SYNTHETIC, Splitter:B:21:0x0022] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0026 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeFile(java.lang.String r3, java.lang.String r4) {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r3)
            r3 = 0
            java.io.FileWriter r1 = new java.io.FileWriter     // Catch:{ Exception -> 0x0020, all -> 0x0019 }
            r2 = 0
            r1.<init>(r0, r2)     // Catch:{ Exception -> 0x0020, all -> 0x0019 }
            r1.write(r4)     // Catch:{ Exception -> 0x0017, all -> 0x0013 }
            r1.close()     // Catch:{ IOException -> 0x0012 }
        L_0x0012:
            return
        L_0x0013:
            r3 = move-exception
            r4 = r3
            r3 = r1
            goto L_0x001a
        L_0x0017:
            r3 = r1
            goto L_0x0020
        L_0x0019:
            r4 = move-exception
        L_0x001a:
            if (r3 == 0) goto L_0x001f
            r3.close()     // Catch:{ IOException -> 0x001f }
        L_0x001f:
            throw r4
        L_0x0020:
            if (r3 == 0) goto L_0x0026
            r3.close()     // Catch:{ IOException -> 0x0025 }
        L_0x0025:
            return
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.commonutils.FileUtil.writeFile(java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003c, code lost:
        if (r4 != null) goto L_0x002d;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0037 A[SYNTHETIC, Splitter:B:16:0x0037] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFile(java.lang.String r4, java.lang.String r5) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            r2.<init>(r4, r5)     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            boolean r4 = r2.exists()     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            if (r4 != 0) goto L_0x0012
            return r1
        L_0x0012:
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            r3.<init>(r2)     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            java.lang.String r2 = "UTF-8"
            r5.<init>(r3, r2)     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x003b, all -> 0x0034 }
        L_0x0023:
            java.lang.String r5 = r4.readLine()     // Catch:{ IOException -> 0x003c, all -> 0x0031 }
            if (r5 == 0) goto L_0x002d
            r0.append(r5)     // Catch:{ IOException -> 0x003c, all -> 0x0031 }
            goto L_0x0023
        L_0x002d:
            r4.close()     // Catch:{ Throwable -> 0x003f }
            goto L_0x003f
        L_0x0031:
            r5 = move-exception
            r1 = r4
            goto L_0x0035
        L_0x0034:
            r5 = move-exception
        L_0x0035:
            if (r1 == 0) goto L_0x003a
            r1.close()     // Catch:{ Throwable -> 0x003a }
        L_0x003a:
            throw r5
        L_0x003b:
            r4 = r1
        L_0x003c:
            if (r4 == 0) goto L_0x003f
            goto L_0x002d
        L_0x003f:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.security.mobile.module.commonutils.FileUtil.readFile(java.lang.String, java.lang.String):java.lang.String");
    }

    public static boolean createFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean createDirs(String str) {
        File file = new File(str);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return false;
    }

    public static long getFileSize(String str, String str2) {
        File file = new File(str, str2);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    public static void deleteFile(File file) {
        if (file != null) {
            try {
                if (file.exists()) {
                    if (file.isFile()) {
                        file.delete();
                    } else if (file.isDirectory()) {
                        File[] listFiles = file.listFiles();
                        for (File deleteFile : listFiles) {
                            deleteFile(deleteFile);
                        }
                        file.delete();
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    public static boolean dirsExists(String str) {
        if (CommonUtils.isBlank(str)) {
            return false;
        }
        return new File(str).exists();
    }

    public static boolean fileExists(String str) {
        if (CommonUtils.isBlank(str)) {
            return false;
        }
        return new File(str).exists();
    }
}
