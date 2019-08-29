package com.uc.crashsdk.a;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: ProGuard */
public final class b {
    public static void a(File file, File file2, byte[] bArr) {
        FileInputStream fileInputStream = new FileInputStream(file);
        File parentFile = file2.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (file2.isDirectory()) {
            file2 = new File(file2, file.getName());
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        while (true) {
            try {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    fileOutputStream.close();
                    return;
                }
            } catch (IOException e) {
                throw e;
            } catch (Throwable th) {
                fileInputStream.close();
                fileOutputStream.close();
                throw th;
            }
        }
    }

    public static boolean a(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            if (list != null) {
                for (String file2 : list) {
                    if (!a(new File(file, file2))) {
                        return false;
                    }
                }
            }
        }
        return file.delete();
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x004a A[SYNTHETIC, Splitter:B:30:0x004a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String b(java.io.File r8) {
        /*
            r7 = 0
            java.lang.String r0 = ""
            boolean r1 = r8.exists()
            if (r1 != 0) goto L_0x000a
        L_0x0009:
            return r0
        L_0x000a:
            r3 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0055, all -> 0x0046 }
            r2.<init>(r8)     // Catch:{ Throwable -> 0x0055, all -> 0x0046 }
            r1 = 256(0x100, float:3.59E-43)
            byte[] r1 = new byte[r1]     // Catch:{ Throwable -> 0x0029 }
            java.lang.StringBuffer r3 = new java.lang.StringBuffer     // Catch:{ Throwable -> 0x0029 }
            r3.<init>()     // Catch:{ Throwable -> 0x0029 }
        L_0x0019:
            int r4 = r2.read(r1)     // Catch:{ Throwable -> 0x0029 }
            if (r4 <= 0) goto L_0x0039
            java.lang.String r5 = new java.lang.String     // Catch:{ Throwable -> 0x0029 }
            r6 = 0
            r5.<init>(r1, r6, r4)     // Catch:{ Throwable -> 0x0029 }
            r3.append(r5)     // Catch:{ Throwable -> 0x0029 }
            goto L_0x0019
        L_0x0029:
            r1 = move-exception
        L_0x002a:
            r3 = 0
            com.uc.crashsdk.a.a.a(r1, r3)     // Catch:{ all -> 0x0053 }
            if (r2 == 0) goto L_0x0009
            r2.close()     // Catch:{ Throwable -> 0x0034 }
            goto L_0x0009
        L_0x0034:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r7)
            goto L_0x0009
        L_0x0039:
            java.lang.String r0 = r3.toString()     // Catch:{ Throwable -> 0x0029 }
            r2.close()     // Catch:{ Throwable -> 0x0041 }
            goto L_0x0009
        L_0x0041:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r7)
            goto L_0x0009
        L_0x0046:
            r0 = move-exception
            r2 = r3
        L_0x0048:
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Throwable -> 0x004e }
        L_0x004d:
            throw r0
        L_0x004e:
            r1 = move-exception
            com.uc.crashsdk.a.a.a(r1, r7)
            goto L_0x004d
        L_0x0053:
            r0 = move-exception
            goto L_0x0048
        L_0x0055:
            r1 = move-exception
            r2 = r3
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.b.b(java.io.File):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0036 A[SYNTHETIC, Splitter:B:25:0x0036] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0045 A[SYNTHETIC, Splitter:B:33:0x0045] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r7, int r8, boolean r9) {
        /*
            r1 = 0
            r6 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x002c, all -> 0x0041 }
            r2.<init>(r7)     // Catch:{ Throwable -> 0x002c, all -> 0x0041 }
            byte[] r3 = new byte[r8]     // Catch:{ Throwable -> 0x0052 }
            int r4 = r2.read(r3)     // Catch:{ Throwable -> 0x0052 }
            if (r4 <= 0) goto L_0x0020
            java.lang.String r0 = new java.lang.String     // Catch:{ Throwable -> 0x0052 }
            r5 = 0
            r0.<init>(r3, r5, r4)     // Catch:{ Throwable -> 0x0052 }
            r2.close()     // Catch:{ Throwable -> 0x0019 }
        L_0x0018:
            return r0
        L_0x0019:
            r1 = move-exception
            if (r9 == 0) goto L_0x0018
            com.uc.crashsdk.a.a.a(r1, r6)
            goto L_0x0018
        L_0x0020:
            r2.close()     // Catch:{ Throwable -> 0x0025 }
        L_0x0023:
            r0 = r1
            goto L_0x0018
        L_0x0025:
            r0 = move-exception
            if (r9 == 0) goto L_0x0023
            com.uc.crashsdk.a.a.a(r0, r6)
            goto L_0x0023
        L_0x002c:
            r0 = move-exception
            r2 = r1
        L_0x002e:
            if (r9 == 0) goto L_0x0034
            r3 = 0
            com.uc.crashsdk.a.a.a(r0, r3)     // Catch:{ all -> 0x0050 }
        L_0x0034:
            if (r2 == 0) goto L_0x0023
            r2.close()     // Catch:{ Throwable -> 0x003a }
            goto L_0x0023
        L_0x003a:
            r0 = move-exception
            if (r9 == 0) goto L_0x0023
            com.uc.crashsdk.a.a.a(r0, r6)
            goto L_0x0023
        L_0x0041:
            r0 = move-exception
            r2 = r1
        L_0x0043:
            if (r2 == 0) goto L_0x0048
            r2.close()     // Catch:{ Throwable -> 0x0049 }
        L_0x0048:
            throw r0
        L_0x0049:
            r1 = move-exception
            if (r9 == 0) goto L_0x0048
            com.uc.crashsdk.a.a.a(r1, r6)
            goto L_0x0048
        L_0x0050:
            r0 = move-exception
            goto L_0x0043
        L_0x0052:
            r0 = move-exception
            goto L_0x002e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.b.a(java.io.File, int, boolean):java.lang.String");
    }

    /* JADX WARNING: type inference failed for: r2v0 */
    /* JADX WARNING: type inference failed for: r3v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v1, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r1v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r1v1 */
    /* JADX WARNING: type inference failed for: r3v4, types: [java.io.Closeable, java.io.FileReader, java.io.Reader] */
    /* JADX WARNING: type inference failed for: r1v2 */
    /* JADX WARNING: type inference failed for: r2v4 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r1v4 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 6 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList<java.lang.String> a(java.io.File r6, int r7) {
        /*
            r2 = 0
            r0 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x0029, all -> 0x0036 }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x0029, all -> 0x0036 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0048, all -> 0x003f }
            r5 = 512(0x200, float:7.175E-43)
            r1.<init>(r3, r5)     // Catch:{ Throwable -> 0x0048, all -> 0x003f }
        L_0x0013:
            java.lang.String r2 = r1.readLine()     // Catch:{ Throwable -> 0x004c, all -> 0x0041 }
            if (r2 == 0) goto L_0x0022
            r4.add(r2)     // Catch:{ Throwable -> 0x004c, all -> 0x0041 }
            int r0 = r0 + 1
            if (r7 <= 0) goto L_0x0013
            if (r0 < r7) goto L_0x0013
        L_0x0022:
            a(r3)
            a(r1)
        L_0x0028:
            return r4
        L_0x0029:
            r0 = move-exception
            r1 = r2
        L_0x002b:
            r3 = 0
            com.uc.crashsdk.a.a.a(r0, r3)     // Catch:{ all -> 0x0044 }
            a(r2)
            a(r1)
            goto L_0x0028
        L_0x0036:
            r0 = move-exception
            r3 = r2
        L_0x0038:
            a(r3)
            a(r2)
            throw r0
        L_0x003f:
            r0 = move-exception
            goto L_0x0038
        L_0x0041:
            r0 = move-exception
            r2 = r1
            goto L_0x0038
        L_0x0044:
            r0 = move-exception
            r3 = r2
            r2 = r1
            goto L_0x0038
        L_0x0048:
            r0 = move-exception
            r1 = r2
            r2 = r3
            goto L_0x002b
        L_0x004c:
            r0 = move-exception
            r2 = r3
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.b.a(java.io.File, int):java.util.ArrayList");
    }

    public static boolean a(File file, byte[] bArr) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(bArr);
                fileOutputStream.flush();
                a((Closeable) fileOutputStream);
                return true;
            } catch (Throwable th) {
                th = th;
                try {
                    a.a(th, false);
                    a((Closeable) fileOutputStream);
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    a((Closeable) fileOutputStream);
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            a((Closeable) fileOutputStream);
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0021 A[SYNTHETIC, Splitter:B:17:0x0021] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0030 A[SYNTHETIC, Splitter:B:25:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x003d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean a(java.io.File r4, java.lang.String r5) {
        /*
            r1 = 0
            r3 = 0
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ Throwable -> 0x0019, all -> 0x002c }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x0019, all -> 0x002c }
            r0 = 0
            int r3 = r5.length()     // Catch:{ Throwable -> 0x003b }
            r2.write(r5, r0, r3)     // Catch:{ Throwable -> 0x003b }
            r0 = 1
            r2.close()     // Catch:{ Throwable -> 0x0014 }
        L_0x0013:
            return r0
        L_0x0014:
            r2 = move-exception
            com.uc.crashsdk.a.a.a(r2, r1)
            goto L_0x0013
        L_0x0019:
            r0 = move-exception
            r2 = r3
        L_0x001b:
            r3 = 0
            com.uc.crashsdk.a.a.a(r0, r3)     // Catch:{ all -> 0x0039 }
            if (r2 == 0) goto L_0x003d
            r2.close()     // Catch:{ Throwable -> 0x0026 }
            r0 = r1
            goto L_0x0013
        L_0x0026:
            r0 = move-exception
            com.uc.crashsdk.a.a.a(r0, r1)
            r0 = r1
            goto L_0x0013
        L_0x002c:
            r0 = move-exception
            r2 = r3
        L_0x002e:
            if (r2 == 0) goto L_0x0033
            r2.close()     // Catch:{ Throwable -> 0x0034 }
        L_0x0033:
            throw r0
        L_0x0034:
            r2 = move-exception
            com.uc.crashsdk.a.a.a(r2, r1)
            goto L_0x0033
        L_0x0039:
            r0 = move-exception
            goto L_0x002e
        L_0x003b:
            r0 = move-exception
            goto L_0x001b
        L_0x003d:
            r0 = r1
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.crashsdk.a.b.a(java.io.File, java.lang.String):boolean");
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
                a.a(th, true);
            }
        }
    }
}
