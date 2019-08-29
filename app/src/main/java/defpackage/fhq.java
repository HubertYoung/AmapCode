package defpackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import java.io.Closeable;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* renamed from: fhq reason: default package */
/* compiled from: ReLinker */
public class fhq {
    /* access modifiers changed from: private */
    public static final String a = System.mapLibraryName("gif");

    private fhq() {
    }

    @SuppressLint({"UnsafeDynamicallyLoadedCode"})
    public static void a(Context context) {
        synchronized (fhq.class) {
            System.load(b(context).getAbsolutePath());
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        a((java.io.Closeable) r1);
        a((java.io.Closeable) r6);
        r4.setReadable(true, false);
        r4.setExecutable(true, false);
        r4.setWritable(true);
     */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b3 A[Catch:{ IOException -> 0x00b0, all -> 0x009c, all -> 0x00bc }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ca A[SYNTHETIC, Splitter:B:52:0x00ca] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.io.File b(android.content.Context r9) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = a
            r0.append(r1)
            java.lang.String r1 = "1.0"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.io.File r1 = new java.io.File
            java.lang.String r2 = "lib"
            r3 = 0
            java.io.File r2 = r9.getDir(r2, r3)
            r1.<init>(r2, r0)
            boolean r2 = r1.isFile()
            if (r2 == 0) goto L_0x0026
            return r1
        L_0x0026:
            java.io.File r2 = new java.io.File
            java.io.File r4 = r9.getCacheDir()
            r2.<init>(r4, r0)
            boolean r0 = r2.isFile()
            if (r0 == 0) goto L_0x0036
            return r2
        L_0x0036:
            java.lang.String r0 = "pl_droidsonroids_gif_surface"
            java.lang.String r0 = java.lang.System.mapLibraryName(r0)
            fhq$1 r4 = new fhq$1
            r4.<init>(r0)
            a(r1, r4)
            a(r2, r4)
            android.content.pm.ApplicationInfo r9 = r9.getApplicationInfo()
            java.io.File r0 = new java.io.File
            java.lang.String r9 = r9.sourceDir
            r0.<init>(r9)
            r9 = 0
            java.util.zip.ZipFile r0 = a(r0)     // Catch:{ all -> 0x00c4 }
            r4 = r1
            r1 = 0
        L_0x0059:
            int r5 = r1 + 1
            r6 = 5
            if (r1 >= r6) goto L_0x00be
            java.util.zip.ZipEntry r1 = a(r0)     // Catch:{ all -> 0x00bc }
            if (r1 != 0) goto L_0x007f
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00bc }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bc }
            java.lang.String r2 = "Library "
            r1.<init>(r2)     // Catch:{ all -> 0x00bc }
            java.lang.String r2 = a     // Catch:{ all -> 0x00bc }
            r1.append(r2)     // Catch:{ all -> 0x00bc }
            java.lang.String r2 = " for supported ABIs not found in APK file"
            r1.append(r2)     // Catch:{ all -> 0x00bc }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00bc }
            r9.<init>(r1)     // Catch:{ all -> 0x00bc }
            throw r9     // Catch:{ all -> 0x00bc }
        L_0x007f:
            java.io.InputStream r1 = r0.getInputStream(r1)     // Catch:{ IOException -> 0x00ae, all -> 0x00a4 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00a2, all -> 0x00a0 }
            r6.<init>(r4)     // Catch:{ IOException -> 0x00a2, all -> 0x00a0 }
            a(r1, r6)     // Catch:{ IOException -> 0x00b0, all -> 0x009c }
            a(r1)     // Catch:{ all -> 0x00bc }
            a(r6)     // Catch:{ all -> 0x00bc }
            r9 = 1
            r4.setReadable(r9, r3)     // Catch:{ all -> 0x00bc }
            r4.setExecutable(r9, r3)     // Catch:{ all -> 0x00bc }
            r4.setWritable(r9)     // Catch:{ all -> 0x00bc }
            goto L_0x00be
        L_0x009c:
            r9 = move-exception
            r2 = r9
            r9 = r6
            goto L_0x00a7
        L_0x00a0:
            r2 = move-exception
            goto L_0x00a7
        L_0x00a2:
            r6 = r9
            goto L_0x00b0
        L_0x00a4:
            r1 = move-exception
            r2 = r1
            r1 = r9
        L_0x00a7:
            a(r1)     // Catch:{ all -> 0x00bc }
            a(r9)     // Catch:{ all -> 0x00bc }
            throw r2     // Catch:{ all -> 0x00bc }
        L_0x00ae:
            r1 = r9
            r6 = r1
        L_0x00b0:
            r7 = 2
            if (r5 <= r7) goto L_0x00b4
            r4 = r2
        L_0x00b4:
            a(r1)     // Catch:{ all -> 0x00bc }
            a(r6)     // Catch:{ all -> 0x00bc }
            r1 = r5
            goto L_0x0059
        L_0x00bc:
            r9 = move-exception
            goto L_0x00c8
        L_0x00be:
            if (r0 == 0) goto L_0x00c3
            r0.close()     // Catch:{ IOException -> 0x00c3 }
        L_0x00c3:
            return r4
        L_0x00c4:
            r0 = move-exception
            r8 = r0
            r0 = r9
            r9 = r8
        L_0x00c8:
            if (r0 == 0) goto L_0x00cd
            r0.close()     // Catch:{ IOException -> 0x00cd }
        L_0x00cd:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fhq.b(android.content.Context):java.io.File");
    }

    private static ZipFile a(File file) {
        ZipFile zipFile;
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (i >= 5) {
                zipFile = null;
                break;
            }
            try {
                zipFile = new ZipFile(file, 1);
                break;
            } catch (IOException unused) {
                i = i2;
            }
        }
        if (zipFile != null) {
            return zipFile;
        }
        StringBuilder sb = new StringBuilder("Could not open APK file: ");
        sb.append(file.getAbsolutePath());
        throw new IllegalStateException(sb.toString());
    }

    private static void a(File file, FilenameFilter filenameFilter) {
        File[] listFiles = file.getParentFile().listFiles(filenameFilter);
        if (listFiles != null) {
            for (File delete : listFiles) {
                delete.delete();
            }
        }
    }

    private static void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    private static ZipEntry a(ZipFile zipFile) {
        for (String append : VERSION.SDK_INT >= 21 ? Build.SUPPORTED_ABIS : new String[]{Build.CPU_ABI, Build.CPU_ABI2}) {
            StringBuilder sb = new StringBuilder("lib/");
            sb.append(append);
            sb.append("/");
            sb.append(a);
            ZipEntry entry = zipFile.getEntry(sb.toString());
            if (entry != null) {
                return entry;
            }
        }
        return null;
    }
}
