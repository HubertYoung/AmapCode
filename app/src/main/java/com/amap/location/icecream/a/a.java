package com.amap.location.icecream.a;

import android.content.Context;
import android.text.TextUtils;
import com.amap.location.common.f.g;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* compiled from: FileUtils */
public class a {
    private static String a = "";
    private static String b = "";

    public static String a(Context context) {
        if (TextUtils.isEmpty(a)) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(context.getFilesDir().getAbsolutePath());
                sb.append("/icecream");
                a = sb.toString();
            } catch (Throwable unused) {
                a = "";
            }
        }
        return a;
    }

    public static String b(Context context) {
        if (TextUtils.isEmpty(b)) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(context.getFilesDir().getAbsolutePath());
                sb.append("/icecream/r");
                b = sb.toString();
            } catch (Throwable unused) {
                b = "";
            }
        }
        return b;
    }

    public static boolean a(File file, String str) {
        ByteArrayOutputStream byteArrayOutputStream;
        FileInputStream fileInputStream;
        if (file == null || TextUtils.isEmpty(str)) {
            return false;
        }
        FileInputStream fileInputStream2 = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(1024);
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
                g.a((Closeable) byteArrayOutputStream);
                g.a((Closeable) fileInputStream);
                throw th;
            }
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        boolean equals = b.a(byteArrayOutputStream.toByteArray()).equals(str);
                        g.a((Closeable) byteArrayOutputStream);
                        g.a((Closeable) fileInputStream);
                        return equals;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                g.a((Closeable) byteArrayOutputStream);
                g.a((Closeable) fileInputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
            fileInputStream = null;
            g.a((Closeable) byteArrayOutputStream);
            g.a((Closeable) fileInputStream);
            throw th;
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x0077 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.lang.String r7, java.lang.String r8) {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Exception -> 0x009c, all -> 0x0092 }
            r1.<init>(r7)     // Catch:{ Exception -> 0x009c, all -> 0x0092 }
            r7 = 256(0x100, float:3.59E-43)
            byte[] r7 = new byte[r7]     // Catch:{ Exception -> 0x009d, all -> 0x008f }
            java.util.zip.ZipInputStream r2 = new java.util.zip.ZipInputStream     // Catch:{ Exception -> 0x009d, all -> 0x008f }
            r2.<init>(r1)     // Catch:{ Exception -> 0x009d, all -> 0x008f }
        L_0x000f:
            java.util.zip.ZipEntry r0 = r2.getNextEntry()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            if (r0 == 0) goto L_0x0084
            java.lang.String r3 = r0.getName()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            java.lang.String r4 = "../"
            boolean r4 = r3.contains(r4)     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            if (r4 != 0) goto L_0x000f
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            r5.<init>()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            r5.append(r8)     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            java.lang.String r6 = java.io.File.separator     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            r5.append(r6)     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            r5.append(r3)     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            java.lang.String r3 = r5.toString()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            r4.<init>(r3)     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            java.io.File r5 = r4.getParentFile()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            java.lang.String r5 = r5.getPath()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            r3.<init>(r5)     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            boolean r0 = r0.isDirectory()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            if (r0 == 0) goto L_0x005a
            boolean r0 = r4.exists()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            if (r0 != 0) goto L_0x0056
            r4.mkdirs()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
        L_0x0056:
            r2.closeEntry()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            goto L_0x000f
        L_0x005a:
            boolean r0 = r3.exists()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            if (r0 != 0) goto L_0x0063
            r3.mkdirs()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
        L_0x0063:
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            r0.<init>(r4)     // Catch:{ Exception -> 0x008d, all -> 0x008b }
        L_0x0068:
            int r3 = r2.read(r7)     // Catch:{ Exception -> 0x0077, all -> 0x007b }
            r4 = -1
            if (r3 == r4) goto L_0x0074
            r4 = 0
            r0.write(r7, r4, r3)     // Catch:{ Exception -> 0x0077, all -> 0x007b }
            goto L_0x0068
        L_0x0074:
            r0.flush()     // Catch:{ Exception -> 0x0077, all -> 0x007b }
        L_0x0077:
            r0.close()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            goto L_0x0080
        L_0x007b:
            r7 = move-exception
            r0.close()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            throw r7     // Catch:{ Exception -> 0x008d, all -> 0x008b }
        L_0x0080:
            r2.closeEntry()     // Catch:{ Exception -> 0x008d, all -> 0x008b }
            goto L_0x000f
        L_0x0084:
            com.amap.location.common.f.g.a(r1)
            com.amap.location.common.f.g.a(r2)
            return
        L_0x008b:
            r7 = move-exception
            goto L_0x0095
        L_0x008d:
            r0 = r2
            goto L_0x009d
        L_0x008f:
            r7 = move-exception
            r2 = r0
            goto L_0x0095
        L_0x0092:
            r7 = move-exception
            r1 = r0
            r2 = r1
        L_0x0095:
            com.amap.location.common.f.g.a(r1)
            com.amap.location.common.f.g.a(r2)
            throw r7
        L_0x009c:
            r1 = r0
        L_0x009d:
            com.amap.location.common.f.g.a(r1)
            com.amap.location.common.f.g.a(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.icecream.a.a.a(java.lang.String, java.lang.String):void");
    }

    public static void b(String str, String str2) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                fileOutputStream = new FileOutputStream(str2);
            } catch (Exception unused) {
                g.a((Closeable) fileInputStream);
                g.a((Closeable) fileOutputStream2);
            }
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr, 0, 1024);
                    if (read != -1) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileOutputStream.flush();
                        return;
                    }
                }
            } catch (Exception unused2) {
                fileOutputStream2 = fileOutputStream;
                g.a((Closeable) fileInputStream);
                g.a((Closeable) fileOutputStream2);
            }
        } catch (Exception unused3) {
            fileInputStream = null;
            g.a((Closeable) fileInputStream);
            g.a((Closeable) fileOutputStream2);
        }
    }
}
