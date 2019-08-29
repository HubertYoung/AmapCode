package com.alipay.mobile.quinox.apkfile;

import android.content.Context;
import android.util.Printer;

public class ApkFileReader {
    public static final String ASSETS = "assets";
    public static final String LIB = "lib";
    private static final String TAG = "AssetsReader";
    private Printer msgPrinter;

    public ApkFileReader() {
    }

    public ApkFileReader(Printer printer) {
        this.msgPrinter = printer;
    }

    public boolean readAssets(Context context, String str, ApkFileInputStreamCallback apkFileInputStreamCallback) {
        return readAssets(context, str, true, apkFileInputStreamCallback);
    }

    public boolean readAssets(Context context, String str, boolean z, ApkFileInputStreamCallback apkFileInputStreamCallback) {
        return readAssets(context, str, z, apkFileInputStreamCallback, true);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0047 A[Catch:{ all -> 0x002a }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051 A[Catch:{ all -> 0x002a }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x007b A[Catch:{ all -> 0x002a }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ac  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean readAssets(android.content.Context r8, java.lang.String r9, boolean r10, com.alipay.mobile.quinox.apkfile.ApkFileInputStreamCallback r11, boolean r12) {
        /*
            r7 = this;
            java.lang.String r0 = "assets/"
            java.lang.String r1 = java.lang.String.valueOf(r9)
            java.lang.String r0 = r0.concat(r1)
            r1 = 0
            r2 = 0
            android.content.res.AssetManager r3 = r8.getAssets()     // Catch:{ Throwable -> 0x002d }
            java.io.InputStream r9 = r3.open(r9)     // Catch:{ Throwable -> 0x002d }
            if (r9 == 0) goto L_0x0025
            boolean r2 = r11.onInputStream(r9)     // Catch:{ Throwable -> 0x0020, all -> 0x001c }
            r1 = r2
            goto L_0x0025
        L_0x001c:
            r8 = move-exception
            r2 = r9
            goto L_0x00b1
        L_0x0020:
            r2 = move-exception
            r6 = r2
            r2 = r9
            r9 = r6
            goto L_0x002e
        L_0x0025:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r9)
            goto L_0x00aa
        L_0x002a:
            r8 = move-exception
            goto L_0x00b1
        L_0x002d:
            r9 = move-exception
        L_0x002e:
            boolean r3 = r9 instanceof java.io.FileNotFoundException     // Catch:{ all -> 0x002a }
            if (r3 == 0) goto L_0x003f
            if (r12 != 0) goto L_0x003f
            java.lang.String r8 = "AssetsReader"
            java.lang.String r10 = "Failed to read asset."
            com.alipay.mobile.quinox.utils.TraceLogger.w(r8, r10, r9)     // Catch:{ all -> 0x002a }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r2)
            return r1
        L_0x003f:
            android.content.pm.ApplicationInfo r12 = r8.getApplicationInfo()     // Catch:{ all -> 0x002a }
            java.lang.String r12 = r12.sourceDir     // Catch:{ all -> 0x002a }
            if (r10 == 0) goto L_0x0051
            java.io.File r10 = new java.io.File     // Catch:{ all -> 0x002a }
            r10.<init>(r12)     // Catch:{ all -> 0x002a }
            java.lang.String r10 = com.alipay.mobile.quinox.security.Md5Verifier.genFileMd5sum(r10)     // Catch:{ all -> 0x002a }
            goto L_0x0054
        L_0x0051:
            java.lang.String r10 = "skip_gen_md5"
        L_0x0054:
            java.lang.String r3 = "AssetsReader"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x002a }
            java.lang.String r5 = "Failed to read asset, "
            r4.<init>(r5)     // Catch:{ all -> 0x002a }
            r4.append(r12)     // Catch:{ all -> 0x002a }
            java.lang.String r5 = "/"
            r4.append(r5)     // Catch:{ all -> 0x002a }
            r4.append(r0)     // Catch:{ all -> 0x002a }
            java.lang.String r5 = ", md5="
            r4.append(r5)     // Catch:{ all -> 0x002a }
            r4.append(r10)     // Catch:{ all -> 0x002a }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x002a }
            com.alipay.mobile.quinox.utils.TraceLogger.w(r3, r4, r9)     // Catch:{ all -> 0x002a }
            android.util.Printer r3 = r7.msgPrinter     // Catch:{ all -> 0x002a }
            if (r3 == 0) goto L_0x00a7
            android.util.Printer r3 = r7.msgPrinter     // Catch:{ all -> 0x002a }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x002a }
            java.lang.String r5 = "Failed to read asset, "
            r4.<init>(r5)     // Catch:{ all -> 0x002a }
            r4.append(r12)     // Catch:{ all -> 0x002a }
            java.lang.String r12 = "/"
            r4.append(r12)     // Catch:{ all -> 0x002a }
            r4.append(r0)     // Catch:{ all -> 0x002a }
            java.lang.String r12 = ", md5="
            r4.append(r12)     // Catch:{ all -> 0x002a }
            r4.append(r10)     // Catch:{ all -> 0x002a }
            java.lang.String r10 = r4.toString()     // Catch:{ all -> 0x002a }
            r3.println(r10)     // Catch:{ all -> 0x002a }
            android.util.Printer r10 = r7.msgPrinter     // Catch:{ all -> 0x002a }
            java.lang.String r9 = android.util.Log.getStackTraceString(r9)     // Catch:{ all -> 0x002a }
            r10.println(r9)     // Catch:{ all -> 0x002a }
        L_0x00a7:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r2)
        L_0x00aa:
            if (r1 != 0) goto L_0x00b0
            boolean r1 = r7.read(r8, r0, r11)
        L_0x00b0:
            return r1
        L_0x00b1:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r2)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.apkfile.ApkFileReader.readAssets(android.content.Context, java.lang.String, boolean, com.alipay.mobile.quinox.apkfile.ApkFileInputStreamCallback, boolean):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0073 A[Catch:{ all -> 0x003c }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean read(android.content.Context r10, java.lang.String r11, com.alipay.mobile.quinox.apkfile.ApkFileInputStreamCallback r12) {
        /*
            r9 = this;
            com.alipay.mobile.quinox.apkfile.ApkFile r0 = com.alipay.mobile.quinox.apkfile.ApkFile.getInstance(r10)
            java.util.zip.ZipFile r1 = r0.getZipFile()
            r2 = 0
            if (r1 == 0) goto L_0x00a7
            r3 = 0
            java.util.zip.ZipEntry r4 = r1.getEntry(r11)     // Catch:{ Throwable -> 0x003e }
            if (r4 != 0) goto L_0x0023
            java.lang.String r1 = "AssetsReader"
            java.lang.String r4 = "Failed to get ZipEntry:"
            java.lang.String r5 = java.lang.String.valueOf(r11)     // Catch:{ Throwable -> 0x003e }
            java.lang.String r4 = r4.concat(r5)     // Catch:{ Throwable -> 0x003e }
            com.alipay.mobile.quinox.utils.TraceLogger.w(r1, r4)     // Catch:{ Throwable -> 0x003e }
            r1 = r3
            goto L_0x0038
        L_0x0023:
            java.io.InputStream r1 = r1.getInputStream(r4)     // Catch:{ Throwable -> 0x003e }
            if (r1 == 0) goto L_0x0038
            boolean r3 = r12.onInputStream(r1)     // Catch:{ Throwable -> 0x0033, all -> 0x002f }
            r2 = r3
            goto L_0x0038
        L_0x002f:
            r10 = move-exception
            r3 = r1
            goto L_0x00a3
        L_0x0033:
            r3 = move-exception
            r8 = r3
            r3 = r1
            r1 = r8
            goto L_0x003f
        L_0x0038:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r1)
            goto L_0x00a7
        L_0x003c:
            r10 = move-exception
            goto L_0x00a3
        L_0x003e:
            r1 = move-exception
        L_0x003f:
            java.lang.String r0 = r0.getApkFilePath()     // Catch:{ all -> 0x003c }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x003c }
            r4.<init>(r0)     // Catch:{ all -> 0x003c }
            java.lang.String r4 = com.alipay.mobile.quinox.security.Md5Verifier.genFileMd5sum(r4)     // Catch:{ all -> 0x003c }
            java.lang.String r5 = "AssetsReader"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x003c }
            java.lang.String r7 = "Failed to read apk, "
            r6.<init>(r7)     // Catch:{ all -> 0x003c }
            r6.append(r0)     // Catch:{ all -> 0x003c }
            java.lang.String r7 = "/"
            r6.append(r7)     // Catch:{ all -> 0x003c }
            r6.append(r11)     // Catch:{ all -> 0x003c }
            java.lang.String r7 = ", md5="
            r6.append(r7)     // Catch:{ all -> 0x003c }
            r6.append(r4)     // Catch:{ all -> 0x003c }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x003c }
            com.alipay.mobile.quinox.utils.TraceLogger.w(r5, r6, r1)     // Catch:{ all -> 0x003c }
            android.util.Printer r5 = r9.msgPrinter     // Catch:{ all -> 0x003c }
            if (r5 == 0) goto L_0x009f
            android.util.Printer r5 = r9.msgPrinter     // Catch:{ all -> 0x003c }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x003c }
            java.lang.String r7 = "Failed to read apk, "
            r6.<init>(r7)     // Catch:{ all -> 0x003c }
            r6.append(r0)     // Catch:{ all -> 0x003c }
            java.lang.String r0 = "/"
            r6.append(r0)     // Catch:{ all -> 0x003c }
            r6.append(r11)     // Catch:{ all -> 0x003c }
            java.lang.String r0 = ", md5="
            r6.append(r0)     // Catch:{ all -> 0x003c }
            r6.append(r4)     // Catch:{ all -> 0x003c }
            java.lang.String r0 = r6.toString()     // Catch:{ all -> 0x003c }
            r5.println(r0)     // Catch:{ all -> 0x003c }
            android.util.Printer r0 = r9.msgPrinter     // Catch:{ all -> 0x003c }
            java.lang.String r1 = android.util.Log.getStackTraceString(r1)     // Catch:{ all -> 0x003c }
            r0.println(r1)     // Catch:{ all -> 0x003c }
        L_0x009f:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r3)
            goto L_0x00a7
        L_0x00a3:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r3)
            throw r10
        L_0x00a7:
            if (r2 != 0) goto L_0x0128
            java.lang.ClassLoader r0 = r10.getClassLoader()
            java.io.InputStream r0 = r0.getResourceAsStream(r11)
            if (r0 == 0) goto L_0x0128
            boolean r12 = r12.onInputStream(r0)     // Catch:{ Throwable -> 0x00bd }
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r0)
            goto L_0x0129
        L_0x00bb:
            r10 = move-exception
            goto L_0x0124
        L_0x00bd:
            r12 = move-exception
            android.content.pm.ApplicationInfo r10 = r10.getApplicationInfo()     // Catch:{ all -> 0x00bb }
            java.lang.String r10 = r10.sourceDir     // Catch:{ all -> 0x00bb }
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x00bb }
            r1.<init>(r10)     // Catch:{ all -> 0x00bb }
            java.lang.String r1 = com.alipay.mobile.quinox.security.Md5Verifier.genFileMd5sum(r1)     // Catch:{ all -> 0x00bb }
            java.lang.String r3 = "AssetsReader"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bb }
            java.lang.String r5 = "Failed to load stream, "
            r4.<init>(r5)     // Catch:{ all -> 0x00bb }
            r4.append(r10)     // Catch:{ all -> 0x00bb }
            java.lang.String r5 = "/"
            r4.append(r5)     // Catch:{ all -> 0x00bb }
            r4.append(r11)     // Catch:{ all -> 0x00bb }
            java.lang.String r5 = ", md5="
            r4.append(r5)     // Catch:{ all -> 0x00bb }
            r4.append(r1)     // Catch:{ all -> 0x00bb }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x00bb }
            com.alipay.mobile.quinox.utils.TraceLogger.e(r3, r4, r12)     // Catch:{ all -> 0x00bb }
            android.util.Printer r3 = r9.msgPrinter     // Catch:{ all -> 0x00bb }
            if (r3 == 0) goto L_0x0120
            android.util.Printer r3 = r9.msgPrinter     // Catch:{ all -> 0x00bb }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00bb }
            java.lang.String r5 = "Failed to read stream, "
            r4.<init>(r5)     // Catch:{ all -> 0x00bb }
            r4.append(r10)     // Catch:{ all -> 0x00bb }
            java.lang.String r10 = "/"
            r4.append(r10)     // Catch:{ all -> 0x00bb }
            r4.append(r11)     // Catch:{ all -> 0x00bb }
            java.lang.String r10 = ", md5="
            r4.append(r10)     // Catch:{ all -> 0x00bb }
            r4.append(r1)     // Catch:{ all -> 0x00bb }
            java.lang.String r10 = r4.toString()     // Catch:{ all -> 0x00bb }
            r3.println(r10)     // Catch:{ all -> 0x00bb }
            android.util.Printer r10 = r9.msgPrinter     // Catch:{ all -> 0x00bb }
            java.lang.String r11 = android.util.Log.getStackTraceString(r12)     // Catch:{ all -> 0x00bb }
            r10.println(r11)     // Catch:{ all -> 0x00bb }
        L_0x0120:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r0)
            goto L_0x0128
        L_0x0124:
            com.alipay.mobile.quinox.utils.StreamUtil.closeSafely(r0)
            throw r10
        L_0x0128:
            r12 = r2
        L_0x0129:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.apkfile.ApkFileReader.read(android.content.Context, java.lang.String, com.alipay.mobile.quinox.apkfile.ApkFileInputStreamCallback):boolean");
    }

    public boolean readNativeCode(Context context, String str, String[] strArr, ApkFileInputStreamCallback apkFileInputStreamCallback) {
        boolean z = false;
        for (String append : strArr) {
            StringBuilder sb = new StringBuilder("lib/");
            sb.append(append);
            sb.append("/");
            sb.append(str);
            z = read(context, sb.toString(), apkFileInputStreamCallback);
            if (z) {
                break;
            }
        }
        return z;
    }
}
