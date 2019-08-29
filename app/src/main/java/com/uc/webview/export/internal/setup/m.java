package com.uc.webview.export.internal.setup;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.webkit.ValueCallback;
import com.alipay.mobile.quinox.bundle.IBundleOperator;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCLogger;
import com.uc.webview.export.internal.utility.j;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* compiled from: ProGuard */
public final class m implements Runnable {
    private ValueCallback<Bundle> a = null;
    private File b;
    private File c;
    private File d;
    private Context e;
    private UCLogger f = UCLogger.create("d", "CreateCoreZipTask");

    public m(Context context, ValueCallback<Bundle> valueCallback) {
        this.e = context;
        this.a = valueCallback;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00a1, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void run() {
        /*
            r7 = this;
            monitor-enter(r7)
            com.uc.webview.export.internal.setup.UCMRunningInfo r0 = com.uc.webview.export.utility.SetupTask.getTotalLoadedUCM()     // Catch:{ all -> 0x00a2 }
            if (r0 == 0) goto L_0x00a0
            int r1 = r0.coreType     // Catch:{ all -> 0x00a2 }
            r2 = 2
            if (r1 != r2) goto L_0x000e
            goto L_0x00a0
        L_0x000e:
            com.uc.webview.export.internal.setup.UCMPackageInfo r0 = r0.ucmPackageInfo     // Catch:{ all -> 0x00a2 }
            r1 = 10001(0x2711, float:1.4014E-41)
            r3 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0084 }
            android.content.Context r4 = r7.e     // Catch:{ Throwable -> 0x0084 }
            r2[r3] = r4     // Catch:{ Throwable -> 0x0084 }
            java.lang.String r4 = "cmodule"
            r5 = 1
            r2[r5] = r4     // Catch:{ Throwable -> 0x0084 }
            java.lang.Object r1 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r1, r2)     // Catch:{ Throwable -> 0x0084 }
            java.io.File r1 = (java.io.File) r1     // Catch:{ Throwable -> 0x0084 }
            r7.b = r1     // Catch:{ Throwable -> 0x0084 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0084 }
            java.io.File r2 = r7.b     // Catch:{ Throwable -> 0x0084 }
            java.lang.String r4 = "zip"
            r1.<init>(r2, r4)     // Catch:{ Throwable -> 0x0084 }
            r7.d = r1     // Catch:{ Throwable -> 0x0084 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0084 }
            java.io.File r2 = r7.b     // Catch:{ Throwable -> 0x0084 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0084 }
            java.lang.String r6 = "uccore_module"
            r4.<init>(r6)     // Catch:{ Throwable -> 0x0084 }
            java.lang.String r6 = com.uc.webview.export.Build.CORE_VERSION     // Catch:{ Throwable -> 0x0084 }
            r4.append(r6)     // Catch:{ Throwable -> 0x0084 }
            java.lang.String r6 = ".apk"
            r4.append(r6)     // Catch:{ Throwable -> 0x0084 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0084 }
            r1.<init>(r2, r4)     // Catch:{ Throwable -> 0x0084 }
            r7.c = r1     // Catch:{ Throwable -> 0x0084 }
            java.io.File r1 = r7.d     // Catch:{ Throwable -> 0x0084 }
            boolean r1 = r7.a(r1)     // Catch:{ Throwable -> 0x0084 }
            if (r1 == 0) goto L_0x0078
            boolean r0 = r7.a(r0)     // Catch:{ Throwable -> 0x0084 }
            if (r0 == 0) goto L_0x0078
            java.io.File r0 = r7.d     // Catch:{ Throwable -> 0x0084 }
            java.io.File r1 = r7.c     // Catch:{ Throwable -> 0x0084 }
            boolean r0 = r7.a(r0, r1)     // Catch:{ Throwable -> 0x0084 }
            if (r0 == 0) goto L_0x0078
            android.webkit.ValueCallback<android.os.Bundle> r0 = r7.a     // Catch:{ Throwable -> 0x0084 }
            java.io.File r1 = r7.c     // Catch:{ Throwable -> 0x0084 }
            java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ Throwable -> 0x0084 }
            android.os.Bundle r1 = a(r5, r1)     // Catch:{ Throwable -> 0x0084 }
            r0.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0084 }
            monitor-exit(r7)
            return
        L_0x0078:
            android.webkit.ValueCallback<android.os.Bundle> r0 = r7.a     // Catch:{ Throwable -> 0x0084 }
            r1 = 0
            android.os.Bundle r1 = a(r3, r1)     // Catch:{ Throwable -> 0x0084 }
            r0.onReceiveValue(r1)     // Catch:{ Throwable -> 0x0084 }
            monitor-exit(r7)
            return
        L_0x0084:
            r0 = move-exception
            com.uc.webview.export.cyclone.UCLogger r1 = r7.f     // Catch:{ all -> 0x00a2 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a2 }
            java.lang.String r4 = "execption: CreateCoreZipTask :"
            r2.<init>(r4)     // Catch:{ all -> 0x00a2 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x00a2 }
            r2.append(r0)     // Catch:{ all -> 0x00a2 }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x00a2 }
            java.lang.Throwable[] r2 = new java.lang.Throwable[r3]     // Catch:{ all -> 0x00a2 }
            r1.print(r0, r2)     // Catch:{ all -> 0x00a2 }
            monitor-exit(r7)
            return
        L_0x00a0:
            monitor-exit(r7)
            return
        L_0x00a2:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.m.run():void");
    }

    private boolean a(UCMPackageInfo uCMPackageInfo) {
        String[] a2;
        try {
            UCCyclone.expectCreateDirFile(this.d);
            File expectCreateDirFile = UCCyclone.expectCreateDirFile(new File(this.d, "lib"));
            File expectCreateDirFile2 = UCCyclone.expectCreateDirFile(new File(this.d, "assets"));
            if (!a(uCMPackageInfo.coreImplModule)) {
                this.f.print("fail: core.jar is Missing", new Throwable[0]);
                return false;
            } else if (!a(uCMPackageInfo.sdkShellModule)) {
                this.f.print("fail: sdk_shell.jar is Missing", new Throwable[0]);
                return false;
            } else {
                for (String str : j.a(uCMPackageInfo.mSdkShellClassLoader)) {
                    if (!a(uCMPackageInfo.soDirPath, str, expectCreateDirFile)) {
                        UCLogger uCLogger = this.f;
                        StringBuilder sb = new StringBuilder("fail:");
                        sb.append(str);
                        sb.append(" is Missing");
                        uCLogger.print(sb.toString(), new Throwable[0]);
                        return false;
                    }
                }
                if (a(uCMPackageInfo, expectCreateDirFile2)) {
                    return true;
                }
                this.f.print("fail: copyRes Fail", new Throwable[0]);
                return false;
            }
        } catch (Throwable th) {
            UCLogger uCLogger2 = this.f;
            StringBuilder sb2 = new StringBuilder("execption:");
            sb2.append(th.getMessage());
            uCLogger2.print(sb2.toString(), new Throwable[0]);
            return false;
        }
    }

    private boolean a(Pair<String, String> pair) {
        if (pair != null) {
            try {
                if (pair.first != null) {
                    File file = new File(this.d, "tmp");
                    b(new File((String) pair.first), file);
                    File file2 = new File(file, IBundleOperator.CLASSES_DEX);
                    if (file2.exists()) {
                        File file3 = new File(this.d, new File((String) pair.first).getName().replace("jar", "dex"));
                        j.a(file2, file3, file3, true);
                        a(file);
                        return true;
                    }
                    UCLogger uCLogger = this.f;
                    StringBuilder sb = new StringBuilder("execption: copyJar:");
                    sb.append(file2.getAbsolutePath());
                    sb.append(" not exist.");
                    uCLogger.print(sb.toString(), new Throwable[0]);
                }
            } catch (Throwable th) {
                UCLogger uCLogger2 = this.f;
                StringBuilder sb2 = new StringBuilder("execption: copyJar:");
                sb2.append(th.getMessage());
                uCLogger2.print(sb2.toString(), new Throwable[0]);
            }
        }
        return false;
    }

    private boolean a(String str, String str2, File file) {
        try {
            File file2 = new File(str, str2);
            File file3 = new File(file, str2);
            j.a(file2, file3, file3, true);
            return true;
        } catch (Throwable th) {
            UCLogger uCLogger = this.f;
            StringBuilder sb = new StringBuilder("fail: copySo:");
            sb.append(th.getMessage());
            uCLogger.print(sb.toString(), new Throwable[0]);
            return false;
        }
    }

    private boolean a(UCMPackageInfo uCMPackageInfo, File file) {
        if (uCMPackageInfo.resDirPath == null) {
            return false;
        }
        try {
            c(new File(uCMPackageInfo.resDirPath), file);
        } catch (IOException e2) {
            UCLogger uCLogger = this.f;
            StringBuilder sb = new StringBuilder("fail: copyRes:");
            sb.append(e2.getMessage());
            uCLogger.print(sb.toString(), new Throwable[0]);
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x006e A[SYNTHETIC, Splitter:B:29:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0080 A[SYNTHETIC, Splitter:B:35:0x0080] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.io.File r5, java.io.File r6) throws java.io.IOException {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
            java.util.zip.ZipOutputStream r2 = new java.util.zip.ZipOutputStream     // Catch:{ IOException -> 0x0052 }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0052 }
            r3.<init>(r6)     // Catch:{ IOException -> 0x0052 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0052 }
            boolean r6 = r5.isFile()     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            if (r6 == 0) goto L_0x0018
            java.lang.String r6 = ""
            r4.a(r2, r5, r6)     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            goto L_0x002a
        L_0x0018:
            java.io.File[] r5 = r5.listFiles()     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            r6 = 0
        L_0x001d:
            int r1 = r5.length     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            if (r6 >= r1) goto L_0x002a
            r1 = r5[r6]     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            java.lang.String r3 = ""
            r4.a(r2, r1, r3)     // Catch:{ IOException -> 0x004d, all -> 0x004a }
            int r6 = r6 + 1
            goto L_0x001d
        L_0x002a:
            r2.close()     // Catch:{ IOException -> 0x002f }
            r5 = 1
            return r5
        L_0x002f:
            r5 = move-exception
            com.uc.webview.export.cyclone.UCLogger r6 = r4.f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "fail: zip in close:"
            r1.<init>(r2)
        L_0x0039:
            java.lang.String r5 = r5.getMessage()
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            java.lang.Throwable[] r1 = new java.lang.Throwable[r0]
            r6.print(r5, r1)
            return r0
        L_0x004a:
            r5 = move-exception
            r1 = r2
            goto L_0x007e
        L_0x004d:
            r5 = move-exception
            r1 = r2
            goto L_0x0053
        L_0x0050:
            r5 = move-exception
            goto L_0x007e
        L_0x0052:
            r5 = move-exception
        L_0x0053:
            com.uc.webview.export.cyclone.UCLogger r6 = r4.f     // Catch:{ all -> 0x0050 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0050 }
            java.lang.String r3 = "fail: zip:"
            r2.<init>(r3)     // Catch:{ all -> 0x0050 }
            java.lang.String r5 = r5.getMessage()     // Catch:{ all -> 0x0050 }
            r2.append(r5)     // Catch:{ all -> 0x0050 }
            java.lang.String r5 = r2.toString()     // Catch:{ all -> 0x0050 }
            java.lang.Throwable[] r2 = new java.lang.Throwable[r0]     // Catch:{ all -> 0x0050 }
            r6.print(r5, r2)     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x007d
            r1.close()     // Catch:{ IOException -> 0x0072 }
            goto L_0x007d
        L_0x0072:
            r5 = move-exception
            com.uc.webview.export.cyclone.UCLogger r6 = r4.f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "fail: zip in close:"
            r1.<init>(r2)
            goto L_0x0039
        L_0x007d:
            return r0
        L_0x007e:
            if (r1 == 0) goto L_0x008f
            r1.close()     // Catch:{ IOException -> 0x0084 }
            goto L_0x008f
        L_0x0084:
            r5 = move-exception
            com.uc.webview.export.cyclone.UCLogger r6 = r4.f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "fail: zip in close:"
            r1.<init>(r2)
            goto L_0x0039
        L_0x008f:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.m.a(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ab A[SYNTHETIC, Splitter:B:36:0x00ab] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.util.zip.ZipOutputStream r8, java.io.File r9, java.lang.String r10) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            boolean r2 = r9.isDirectory()     // Catch:{ IOException -> 0x008e }
            if (r2 != 0) goto L_0x0042
            r2 = 4096(0x1000, float:5.74E-42)
            byte[] r2 = new byte[r2]     // Catch:{ IOException -> 0x008e }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x008e }
            r3.<init>(r9)     // Catch:{ IOException -> 0x008e }
            java.util.zip.ZipEntry r1 = new java.util.zip.ZipEntry     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r4.<init>()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r4.append(r10)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            java.lang.String r9 = r9.getName()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r4.append(r9)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            java.lang.String r9 = r4.toString()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r1.<init>(r9)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r8.putNextEntry(r1)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
        L_0x002c:
            int r9 = r3.read(r2)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r10 = -1
            if (r9 == r10) goto L_0x0037
            r8.write(r2, r0, r9)     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            goto L_0x002c
        L_0x0037:
            r8.closeEntry()     // Catch:{ IOException -> 0x003f, all -> 0x003c }
            r1 = r3
            goto L_0x006a
        L_0x003c:
            r8 = move-exception
            r1 = r3
            goto L_0x00a9
        L_0x003f:
            r8 = move-exception
            r1 = r3
            goto L_0x008f
        L_0x0042:
            java.io.File[] r2 = r9.listFiles()     // Catch:{ IOException -> 0x008e }
            r3 = 0
        L_0x0047:
            int r4 = r2.length     // Catch:{ IOException -> 0x008e }
            if (r3 >= r4) goto L_0x006a
            r4 = r2[r3]     // Catch:{ IOException -> 0x008e }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x008e }
            r5.<init>()     // Catch:{ IOException -> 0x008e }
            r5.append(r10)     // Catch:{ IOException -> 0x008e }
            java.lang.String r6 = r9.getName()     // Catch:{ IOException -> 0x008e }
            r5.append(r6)     // Catch:{ IOException -> 0x008e }
            java.lang.String r6 = "/"
            r5.append(r6)     // Catch:{ IOException -> 0x008e }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x008e }
            r7.a(r8, r4, r5)     // Catch:{ IOException -> 0x008e }
            int r3 = r3 + 1
            goto L_0x0047
        L_0x006a:
            if (r1 == 0) goto L_0x008b
            r1.close()     // Catch:{ IOException -> 0x0070 }
            return
        L_0x0070:
            r8 = move-exception
            com.uc.webview.export.cyclone.UCLogger r9 = r7.f
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r1 = "fail: zipFileOrDirectory in close:"
            r10.<init>(r1)
            java.lang.String r1 = r8.getMessage()
            r10.append(r1)
            java.lang.String r10 = r10.toString()
            java.lang.Throwable[] r0 = new java.lang.Throwable[r0]
            r9.print(r10, r0)
            throw r8
        L_0x008b:
            return
        L_0x008c:
            r8 = move-exception
            goto L_0x00a9
        L_0x008e:
            r8 = move-exception
        L_0x008f:
            com.uc.webview.export.cyclone.UCLogger r9 = r7.f     // Catch:{ all -> 0x008c }
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x008c }
            java.lang.String r2 = "fail: zipFileOrDirectory:"
            r10.<init>(r2)     // Catch:{ all -> 0x008c }
            java.lang.String r2 = r8.getMessage()     // Catch:{ all -> 0x008c }
            r10.append(r2)     // Catch:{ all -> 0x008c }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x008c }
            java.lang.Throwable[] r2 = new java.lang.Throwable[r0]     // Catch:{ all -> 0x008c }
            r9.print(r10, r2)     // Catch:{ all -> 0x008c }
            throw r8     // Catch:{ all -> 0x008c }
        L_0x00a9:
            if (r1 == 0) goto L_0x00ca
            r1.close()     // Catch:{ IOException -> 0x00af }
            goto L_0x00ca
        L_0x00af:
            r8 = move-exception
            com.uc.webview.export.cyclone.UCLogger r9 = r7.f
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r1 = "fail: zipFileOrDirectory in close:"
            r10.<init>(r1)
            java.lang.String r1 = r8.getMessage()
            r10.append(r1)
            java.lang.String r10 = r10.toString()
            java.lang.Throwable[] r0 = new java.lang.Throwable[r0]
            r9.print(r10, r0)
            throw r8
        L_0x00ca:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.m.a(java.util.zip.ZipOutputStream, java.io.File, java.lang.String):void");
    }

    private static void b(File file, File file2) throws IOException {
        FileOutputStream fileOutputStream;
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    String name = nextEntry.getName();
                    if (name.contains("../")) {
                        throw new IOException("unsecurity zipfile!");
                    }
                    File file3 = new File(file2, name);
                    File parentFile = nextEntry.isDirectory() ? file3 : file3.getParentFile();
                    if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                        StringBuilder sb = new StringBuilder("Failed to ensure directory: ");
                        sb.append(parentFile.getAbsolutePath());
                        throw new FileNotFoundException(sb.toString());
                    } else if (!nextEntry.isDirectory()) {
                        fileOutputStream = new FileOutputStream(file3);
                        while (true) {
                            int read = zipInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.close();
                    }
                } else {
                    zipInputStream.close();
                    return;
                }
            }
        } catch (Throwable th) {
            zipInputStream.close();
            throw th;
        }
    }

    private void c(File file, File file2) throws IOException {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdirs();
            }
            String[] list = file.list();
            for (int i = 0; i < list.length; i++) {
                c(new File(file, list[i]), new File(file2, list[i]));
            }
            return;
        }
        j.a(file, file2, file2, true);
    }

    private boolean a(File file) {
        if (!file.exists()) {
            return true;
        }
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (listFiles[i].isDirectory()) {
                a(listFiles[i]);
            } else {
                listFiles[i].delete();
            }
        }
        return file.delete();
    }

    private static Bundle a(boolean z, String str) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("success", z);
        bundle.putString("zippath", str);
        return bundle;
    }
}
