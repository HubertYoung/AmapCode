package defpackage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/* renamed from: bmu reason: default package */
/* compiled from: IOUtil */
public final class bmu {
    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

    public static String a(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        StringBuilder sb = new StringBuilder();
        char[] cArr = new char[1024];
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (read < 0) {
                return sb.toString();
            }
            sb.append(cArr, 0, read);
        }
    }

    public static void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = bufferedInputStream.read(bArr);
            if (read != -1) {
                bufferedOutputStream.write(bArr, 0, read);
            } else {
                bufferedOutputStream.flush();
                return;
            }
        }
    }

    public static boolean a(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File a : listFiles) {
                if (!a(a)) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x002c A[SYNTHETIC, Splitter:B:21:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0030 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0033 A[SYNTHETIC, Splitter:B:27:0x0033] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.lang.String r3, java.io.File r4, boolean r5) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r3)
            if (r0 != 0) goto L_0x0037
            if (r4 != 0) goto L_0x0009
            goto L_0x0037
        L_0x0009:
            r0 = 0
            java.io.PrintWriter r1 = new java.io.PrintWriter     // Catch:{ Throwable -> 0x0026 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0026 }
            r2.<init>(r4, r5)     // Catch:{ Throwable -> 0x0026 }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0026 }
            r1.print(r3)     // Catch:{ Throwable -> 0x0021, all -> 0x001e }
            r1.flush()     // Catch:{ Throwable -> 0x0021, all -> 0x001e }
            r1.close()     // Catch:{ Throwable -> 0x001d }
        L_0x001d:
            return
        L_0x001e:
            r3 = move-exception
            r0 = r1
            goto L_0x0031
        L_0x0021:
            r3 = move-exception
            r0 = r1
            goto L_0x0027
        L_0x0024:
            r3 = move-exception
            goto L_0x0031
        L_0x0026:
            r3 = move-exception
        L_0x0027:
            r3.printStackTrace()     // Catch:{ all -> 0x0024 }
            if (r0 == 0) goto L_0x0030
            r0.close()     // Catch:{ Throwable -> 0x002f }
        L_0x002f:
            return
        L_0x0030:
            return
        L_0x0031:
            if (r0 == 0) goto L_0x0036
            r0.close()     // Catch:{ Throwable -> 0x0036 }
        L_0x0036:
            throw r3
        L_0x0037:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bmu.a(java.lang.String, java.io.File, boolean):void");
    }

    public static String b(File file) {
        FileInputStream fileInputStream = null;
        try {
            FileInputStream fileInputStream2 = new FileInputStream(file);
            try {
                String a = a((InputStream) fileInputStream2);
                a((Closeable) fileInputStream2);
                return a;
            } catch (Throwable th) {
                th = th;
                fileInputStream = fileInputStream2;
                a((Closeable) fileInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            th.printStackTrace();
            a((Closeable) fileInputStream);
            return "";
        }
    }
}
