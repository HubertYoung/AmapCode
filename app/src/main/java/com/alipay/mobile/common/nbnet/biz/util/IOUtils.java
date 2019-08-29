package com.alipay.mobile.common.nbnet.biz.util;

import com.alipay.mobile.common.nbnet.api.NBNetException;
import com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat;
import com.alipay.mobile.common.nbnet.biz.qoe.NetworkQoeManagerFactory;
import com.alipay.mobile.common.transport.utils.FileUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Future;

public class IOUtils {
    private static final String a = IOUtils.class.getSimpleName();

    public static void a(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                NBNetLogCat.b(a, "", e);
            }
        }
    }

    public static void a(File src, File dst) {
        if (!c(src, dst) || src.length() != dst.length()) {
            NBNetLogCat.a(a, (String) "Use copyTransferToFile fail, Continue to use transferStream retry.");
            if (dst.exists()) {
                dst.delete();
            }
            FileOutputStream out = new FileOutputStream(dst);
            FileInputStream in = new FileInputStream(src);
            try {
                a((InputStream) in, (OutputStream) out);
            } finally {
                a((Closeable) in);
                a((Closeable) out);
            }
        }
    }

    public static void a(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[40096];
            long startTime = System.currentTimeMillis();
            while (true) {
                int bytesRead = in.read(buffer);
                if (bytesRead >= 0) {
                    NetworkQoeManagerFactory.a().a(startTime);
                    out.write(buffer, 0, bytesRead);
                    startTime = System.currentTimeMillis();
                } else {
                    return;
                }
            }
        } finally {
            out.flush();
        }
    }

    public static long a(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return 0;
        }
        try {
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                return 0;
            }
            long size = 0;
            for (File file : files) {
                if (file != null && file.exists()) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += a(file);
                    }
                }
            }
            return size;
        } catch (Throwable t) {
            NBNetLogCat.b(a, t);
            return 0;
        }
    }

    public static void a(int arrayLength, int offset, int count) {
        if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
            throw new ArrayIndexOutOfBoundsException("length=" + arrayLength + "; regionStart=" + offset + "; regionLength=" + count);
        }
    }

    public static final int[] b(int totalLength, int offset, int count) {
        int count2;
        if (totalLength - offset >= count || offset >= totalLength || count <= 0 || count > totalLength) {
            return null;
        }
        if (offset < count) {
            count2 = count - offset;
        } else if (offset > count) {
            count2 = offset - count;
        } else {
            count2 = totalLength - count;
        }
        return new int[]{offset, count2};
    }

    public static final boolean a(Future<?> writeFutureTask) {
        boolean z = true;
        if (writeFutureTask == null || writeFutureTask.isCancelled() || writeFutureTask.isDone()) {
            return z;
        }
        try {
            NBNetLogCat.a(a, (String) "cancel writeFutureTask");
            return writeFutureTask.cancel(true);
        } catch (Throwable th) {
            return z;
        }
    }

    public static final String a(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf(46);
            if (dot >= 0 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    public static final boolean b(File cacheFile, File targetPath) {
        if (!FileUtils.checkFileDirWRPermissions(cacheFile)) {
            throw new NBNetException("CachePath can't rw, path: " + cacheFile.getAbsolutePath(), -18);
        } else if (FileUtils.checkFileDirWRPermissions(targetPath)) {
            return true;
        } else {
            throw new NBNetException("TargetPath can't rw, path: " + cacheFile.getAbsolutePath(), -18);
        }
    }

    public static void b(File saveFile) {
        if (!saveFile.exists()) {
            try {
                if (!saveFile.createNewFile()) {
                    NBNetLogCat.d(a, "createNewFile. Create new file fail, path: " + saveFile.getAbsolutePath());
                }
            } catch (Throwable e) {
                throw new NBNetException("createNewFile. Create new file exception, path: " + saveFile.getAbsolutePath(), -21, e);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0070 A[SYNTHETIC, Splitter:B:32:0x0070] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0075 A[SYNTHETIC, Splitter:B:35:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00e5 A[SYNTHETIC, Splitter:B:48:0x00e5] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00ea A[SYNTHETIC, Splitter:B:51:0x00ea] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean c(java.io.File r12, java.io.File r13) {
        /*
            boolean r1 = r13.exists()
            if (r1 == 0) goto L_0x004e
            r13.delete()
        L_0x0009:
            r7 = 0
            r9 = 0
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0129 }
            r8.<init>(r12)     // Catch:{ Exception -> 0x0129 }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x012c, all -> 0x0122 }
            r10.<init>(r13)     // Catch:{ Exception -> 0x012c, all -> 0x0122 }
            java.nio.channels.FileChannel r5 = r10.getChannel()     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            java.nio.channels.FileChannel r0 = r8.getChannel()     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            r1 = 0
            long r3 = r0.size()     // Catch:{ all -> 0x005c }
            r0.transferTo(r1, r3, r5)     // Catch:{ all -> 0x005c }
            r5.close()     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            r0.close()     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            java.lang.String r1 = a     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            java.lang.String r3 = "copyTransferToFile finish.  src: "
            r2.<init>(r3)     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            java.lang.String r3 = r12.getAbsolutePath()     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            com.alipay.mobile.common.nbnet.biz.log.NBNetLogCat.a(r1, r2)     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            r10.close()     // Catch:{ Exception -> 0x007a }
        L_0x0047:
            r8.close()     // Catch:{ Exception -> 0x0094 }
        L_0x004a:
            r1 = 1
            r9 = r10
            r7 = r8
        L_0x004d:
            return r1
        L_0x004e:
            java.io.File r11 = r13.getParentFile()
            boolean r1 = r11.exists()
            if (r1 != 0) goto L_0x0009
            r11.mkdirs()
            goto L_0x0009
        L_0x005c:
            r1 = move-exception
            r5.close()     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            r0.close()     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
            throw r1     // Catch:{ Exception -> 0x0064, all -> 0x0125 }
        L_0x0064:
            r6 = move-exception
            r9 = r10
            r7 = r8
        L_0x0067:
            java.lang.String r1 = a     // Catch:{ all -> 0x00e2 }
            java.lang.String r2 = "copy file error!"
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r1, r2, r6)     // Catch:{ all -> 0x00e2 }
            if (r9 == 0) goto L_0x0073
            r9.close()     // Catch:{ Exception -> 0x00ae }
        L_0x0073:
            if (r7 == 0) goto L_0x0078
            r7.close()     // Catch:{ Exception -> 0x00c8 }
        L_0x0078:
            r1 = 0
            goto L_0x004d
        L_0x007a:
            r6 = move-exception
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "outputStream.close exception. "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x0047
        L_0x0094:
            r6 = move-exception
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "inputStream.close exception. "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x004a
        L_0x00ae:
            r6 = move-exception
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "outputStream.close exception. "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x0073
        L_0x00c8:
            r6 = move-exception
            java.lang.String r1 = a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "inputStream.close exception. "
            r2.<init>(r3)
            java.lang.String r3 = r6.toString()
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r1, r2)
            goto L_0x0078
        L_0x00e2:
            r1 = move-exception
        L_0x00e3:
            if (r9 == 0) goto L_0x00e8
            r9.close()     // Catch:{ Exception -> 0x00ee }
        L_0x00e8:
            if (r7 == 0) goto L_0x00ed
            r7.close()     // Catch:{ Exception -> 0x0108 }
        L_0x00ed:
            throw r1
        L_0x00ee:
            r6 = move-exception
            java.lang.String r2 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "outputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r6.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)
            goto L_0x00e8
        L_0x0108:
            r6 = move-exception
            java.lang.String r2 = a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "inputStream.close exception. "
            r3.<init>(r4)
            java.lang.String r4 = r6.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.warn(r2, r3)
            goto L_0x00ed
        L_0x0122:
            r1 = move-exception
            r7 = r8
            goto L_0x00e3
        L_0x0125:
            r1 = move-exception
            r9 = r10
            r7 = r8
            goto L_0x00e3
        L_0x0129:
            r6 = move-exception
            goto L_0x0067
        L_0x012c:
            r6 = move-exception
            r7 = r8
            goto L_0x0067
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nbnet.biz.util.IOUtils.c(java.io.File, java.io.File):boolean");
    }
}
