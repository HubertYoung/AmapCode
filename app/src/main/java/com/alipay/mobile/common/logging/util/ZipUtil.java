package com.alipay.mobile.common.logging.util;

import android.text.TextUtils;
import java.io.File;

public class ZipUtil {

    public interface ZipFileHandler {
        String handleFileNameInZip(File file);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        r5.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
        r13.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00fd, code lost:
        r15 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00fe, code lost:
        r13 = r14;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d0 A[SYNTHETIC, Splitter:B:50:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00d5 A[SYNTHETIC, Splitter:B:53:0x00d5] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00fd A[ExcHandler: all (th java.lang.Throwable), PHI: r5 
      PHI: (r5v3 'is' java.io.InputStream) = (r5v0 'is' java.io.InputStream), (r5v0 'is' java.io.InputStream), (r5v10 'is' java.io.InputStream), (r5v10 'is' java.io.InputStream), (r5v10 'is' java.io.InputStream), (r5v10 'is' java.io.InputStream), (r5v10 'is' java.io.InputStream), (r5v10 'is' java.io.InputStream), (r5v10 'is' java.io.InputStream) binds: [B:24:0x0065, B:25:?, B:39:0x00b9, B:56:0x00d9, B:58:0x00dc, B:60:0x00df, B:61:?, B:59:?, B:57:?] A[DONT_GENERATE, DONT_INLINE], Splitter:B:39:0x00b9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void zipFile(java.util.List<java.io.File> r19, java.lang.String r20, java.io.File r21, com.alipay.mobile.common.logging.util.ZipUtil.ZipFileHandler r22) {
        /*
            if (r19 == 0) goto L_0x0008
            boolean r15 = r19.isEmpty()
            if (r15 == 0) goto L_0x0010
        L_0x0008:
            java.lang.Exception r15 = new java.lang.Exception
            java.lang.String r16 = "willZipList is empty"
            r15.<init>(r16)
            throw r15
        L_0x0010:
            if (r20 == 0) goto L_0x0018
            int r15 = r20.length()
            if (r15 != 0) goto L_0x0020
        L_0x0018:
            java.lang.Exception r15 = new java.lang.Exception
            java.lang.String r16 = "zipFilePath is none"
            r15.<init>(r16)
            throw r15
        L_0x0020:
            java.io.File r10 = new java.io.File
            r0 = r20
            r10.<init>(r0)
            boolean r15 = r10.isDirectory()
            if (r15 == 0) goto L_0x0046
            java.lang.Exception r15 = new java.lang.Exception
            java.lang.StringBuilder r16 = new java.lang.StringBuilder
            java.lang.String r17 = "zipFilePath is directory: "
            r16.<init>(r17)
            r0 = r16
            r1 = r20
            java.lang.StringBuilder r16 = r0.append(r1)
            java.lang.String r16 = r16.toString()
            r15.<init>(r16)
            throw r15
        L_0x0046:
            java.io.File r9 = r10.getParentFile()     // Catch:{ Throwable -> 0x010a }
            if (r9 == 0) goto L_0x0055
            boolean r15 = r9.exists()     // Catch:{ Throwable -> 0x010a }
            if (r15 != 0) goto L_0x0055
            r9.mkdirs()     // Catch:{ Throwable -> 0x010a }
        L_0x0055:
            r13 = 0
            r5 = 0
            java.util.zip.ZipOutputStream r14 = new java.util.zip.ZipOutputStream     // Catch:{ Throwable -> 0x0104 }
            java.io.FileOutputStream r15 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0104 }
            r0 = r20
            r15.<init>(r0)     // Catch:{ Throwable -> 0x0104 }
            r14.<init>(r15)     // Catch:{ Throwable -> 0x0104 }
            r15 = 8192(0x2000, float:1.14794E-41)
            byte[] r2 = new byte[r15]     // Catch:{ Throwable -> 0x00c5, all -> 0x00fd }
            r4 = 0
            r6 = r5
        L_0x0069:
            int r15 = r19.size()     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            if (r4 >= r15) goto L_0x00e6
            r0 = r19
            java.lang.Object r3 = r0.get(r4)     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            java.io.File r3 = (java.io.File) r3     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            if (r3 == 0) goto L_0x010d
            boolean r15 = r3.exists()     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            if (r15 == 0) goto L_0x010d
            boolean r15 = r3.isFile()     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            if (r15 == 0) goto L_0x010d
            long r15 = r3.length()     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            r17 = 0
            int r15 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r15 == 0) goto L_0x010d
            r0 = r21
            r1 = r22
            java.lang.String r12 = a(r0, r3, r1)     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            java.util.zip.ZipEntry r11 = new java.util.zip.ZipEntry     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            r11.<init>(r12)     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            long r15 = r3.length()     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            r0 = r15
            r11.setSize(r0)     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            long r15 = r3.lastModified()     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            r0 = r15
            r11.setTime(r0)     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            r14.putNextEntry(r11)     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            java.io.FileInputStream r15 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            r15.<init>(r3)     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
            r5.<init>(r15)     // Catch:{ Throwable -> 0x0106, all -> 0x0100 }
        L_0x00b9:
            int r7 = r5.read(r2)     // Catch:{ Throwable -> 0x00c5, all -> 0x00fd }
            r15 = -1
            if (r7 == r15) goto L_0x00d9
            r15 = 0
            r14.write(r2, r15, r7)     // Catch:{ Throwable -> 0x00c5, all -> 0x00fd }
            goto L_0x00b9
        L_0x00c5:
            r8 = move-exception
            r13 = r14
        L_0x00c7:
            java.lang.Exception r15 = new java.lang.Exception     // Catch:{ all -> 0x00cd }
            r15.<init>(r8)     // Catch:{ all -> 0x00cd }
            throw r15     // Catch:{ all -> 0x00cd }
        L_0x00cd:
            r15 = move-exception
        L_0x00ce:
            if (r5 == 0) goto L_0x00d3
            r5.close()     // Catch:{ Throwable -> 0x00f9 }
        L_0x00d3:
            if (r13 == 0) goto L_0x00d8
            r13.close()     // Catch:{ Throwable -> 0x00fb }
        L_0x00d8:
            throw r15
        L_0x00d9:
            r5.close()     // Catch:{ Throwable -> 0x00ef, all -> 0x00fd }
        L_0x00dc:
            r14.flush()     // Catch:{ Throwable -> 0x00f1, all -> 0x00fd }
        L_0x00df:
            r14.closeEntry()     // Catch:{ Throwable -> 0x00f3, all -> 0x00fd }
        L_0x00e2:
            int r4 = r4 + 1
            r6 = r5
            goto L_0x0069
        L_0x00e6:
            if (r6 == 0) goto L_0x00eb
            r6.close()     // Catch:{ Throwable -> 0x00f5 }
        L_0x00eb:
            r14.close()     // Catch:{ Throwable -> 0x00f7 }
        L_0x00ee:
            return
        L_0x00ef:
            r15 = move-exception
            goto L_0x00dc
        L_0x00f1:
            r15 = move-exception
            goto L_0x00df
        L_0x00f3:
            r15 = move-exception
            goto L_0x00e2
        L_0x00f5:
            r15 = move-exception
            goto L_0x00eb
        L_0x00f7:
            r15 = move-exception
            goto L_0x00ee
        L_0x00f9:
            r16 = move-exception
            goto L_0x00d3
        L_0x00fb:
            r16 = move-exception
            goto L_0x00d8
        L_0x00fd:
            r15 = move-exception
            r13 = r14
            goto L_0x00ce
        L_0x0100:
            r15 = move-exception
            r5 = r6
            r13 = r14
            goto L_0x00ce
        L_0x0104:
            r8 = move-exception
            goto L_0x00c7
        L_0x0106:
            r8 = move-exception
            r5 = r6
            r13 = r14
            goto L_0x00c7
        L_0x010a:
            r15 = move-exception
            goto L_0x0055
        L_0x010d:
            r5 = r6
            goto L_0x00e2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.ZipUtil.zipFile(java.util.List, java.lang.String, java.io.File, com.alipay.mobile.common.logging.util.ZipUtil$ZipFileHandler):void");
    }

    private static String a(File baseOnDir, File targetFile, ZipFileHandler handler) {
        if (targetFile == null) {
            return null;
        }
        if (baseOnDir == null) {
            baseOnDir = targetFile.getParentFile();
        }
        String absFileName = null;
        if (handler != null) {
            try {
                absFileName = handler.handleFileNameInZip(targetFile);
            } catch (Throwable th) {
            }
        }
        if (TextUtils.isEmpty(absFileName)) {
            absFileName = targetFile.getName();
        }
        while (true) {
            targetFile = targetFile.getParentFile();
            if (targetFile == null || targetFile.equals(baseOnDir)) {
                return absFileName;
            }
            String targetName = targetFile.getName();
            if (TextUtils.isEmpty(targetName)) {
                return absFileName;
            }
            absFileName = targetName + '/' + absFileName;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0025 A[SYNTHETIC, Splitter:B:16:0x0025] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x003e A[SYNTHETIC, Splitter:B:30:0x003e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] unCompressGzip(java.io.InputStream r7) {
        /*
            if (r7 != 0) goto L_0x0004
            r5 = 0
        L_0x0003:
            return r5
        L_0x0004:
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream
            r2.<init>()
            r3 = 0
            java.util.zip.GZIPInputStream r4 = new java.util.zip.GZIPInputStream     // Catch:{ Throwable -> 0x004f, all -> 0x0038 }
            r4.<init>(r7)     // Catch:{ Throwable -> 0x004f, all -> 0x0038 }
            r5 = 256(0x100, float:3.59E-43)
            byte[] r0 = new byte[r5]     // Catch:{ Throwable -> 0x001e, all -> 0x004c }
        L_0x0013:
            int r1 = r4.read(r0)     // Catch:{ Throwable -> 0x001e, all -> 0x004c }
            if (r1 < 0) goto L_0x002d
            r5 = 0
            r2.write(r0, r5, r1)     // Catch:{ Throwable -> 0x001e, all -> 0x004c }
            goto L_0x0013
        L_0x001e:
            r5 = move-exception
            r3 = r4
        L_0x0020:
            r2.close()     // Catch:{ Throwable -> 0x0044 }
        L_0x0023:
            if (r3 == 0) goto L_0x0028
            r3.close()     // Catch:{ Throwable -> 0x0046 }
        L_0x0028:
            byte[] r5 = r2.toByteArray()
            goto L_0x0003
        L_0x002d:
            r2.close()     // Catch:{ Throwable -> 0x0042 }
        L_0x0030:
            r4.close()     // Catch:{ Throwable -> 0x0035 }
            r3 = r4
            goto L_0x0028
        L_0x0035:
            r5 = move-exception
            r3 = r4
            goto L_0x0028
        L_0x0038:
            r5 = move-exception
        L_0x0039:
            r2.close()     // Catch:{ Throwable -> 0x0048 }
        L_0x003c:
            if (r3 == 0) goto L_0x0041
            r3.close()     // Catch:{ Throwable -> 0x004a }
        L_0x0041:
            throw r5
        L_0x0042:
            r5 = move-exception
            goto L_0x0030
        L_0x0044:
            r5 = move-exception
            goto L_0x0023
        L_0x0046:
            r5 = move-exception
            goto L_0x0028
        L_0x0048:
            r6 = move-exception
            goto L_0x003c
        L_0x004a:
            r6 = move-exception
            goto L_0x0041
        L_0x004c:
            r5 = move-exception
            r3 = r4
            goto L_0x0039
        L_0x004f:
            r5 = move-exception
            goto L_0x0020
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.ZipUtil.unCompressGzip(java.io.InputStream):byte[]");
    }
}
