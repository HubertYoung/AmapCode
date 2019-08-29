package com.alipay.mobile.monitor.util;

import java.io.File;

public class ZipUtils {
    private static final int BUFFER_LENGTH = 8192;

    public interface ZipFileHandler {
        String handleFileNameInZip(File file);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00af, code lost:
        r9 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b0, code lost:
        r3 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:?, code lost:
        r3.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:42:0x00a7 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:44:0x00aa */
    /* JADX WARNING: Missing exception handler attribute for start block: B:56:0x00be */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00af A[ExcHandler: all (th java.lang.Throwable), Splitter:B:42:0x00a7] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00dd A[SYNTHETIC, Splitter:B:76:0x00dd] */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x00e2 A[SYNTHETIC, Splitter:B:80:0x00e2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void zipFile(java.util.List<java.io.File> r9, java.lang.String r10, java.io.File r11, com.alipay.mobile.monitor.util.ZipUtils.ZipFileHandler r12) throws java.lang.Exception {
        /*
            if (r9 == 0) goto L_0x00ef
            boolean r0 = r9.isEmpty()
            if (r0 == 0) goto L_0x000a
            goto L_0x00ef
        L_0x000a:
            if (r10 == 0) goto L_0x00e6
            int r0 = r10.length()
            if (r0 != 0) goto L_0x0014
            goto L_0x00e6
        L_0x0014:
            java.io.File r0 = new java.io.File
            r0.<init>(r10)
            boolean r1 = r0.isDirectory()
            if (r1 == 0) goto L_0x0030
            java.lang.Exception r9 = new java.lang.Exception
            java.lang.String r11 = "zipFilePath is directory: "
            java.lang.String r10 = java.lang.String.valueOf(r10)
            java.lang.String r10 = r11.concat(r10)
            r9.<init>(r10)
            throw r9
        L_0x0030:
            java.io.File r0 = r0.getParentFile()     // Catch:{ Throwable -> 0x003f }
            if (r0 == 0) goto L_0x003f
            boolean r1 = r0.exists()     // Catch:{ Throwable -> 0x003f }
            if (r1 != 0) goto L_0x003f
            r0.mkdirs()     // Catch:{ Throwable -> 0x003f }
        L_0x003f:
            r0 = 0
            java.util.zip.ZipOutputStream r1 = new java.util.zip.ZipOutputStream     // Catch:{ Throwable -> 0x00d1, all -> 0x00cd }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x00d1, all -> 0x00cd }
            r2.<init>(r10)     // Catch:{ Throwable -> 0x00d1, all -> 0x00cd }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x00d1, all -> 0x00cd }
            r10 = 8192(0x2000, float:1.14794E-41)
            byte[] r10 = new byte[r10]     // Catch:{ Throwable -> 0x00c9, all -> 0x00c6 }
            r2 = 0
            r3 = r0
            r0 = 0
        L_0x0051:
            int r4 = r9.size()     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            if (r0 >= r4) goto L_0x00b9
            java.lang.Object r4 = r9.get(r0)     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            java.io.File r4 = (java.io.File) r4     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            if (r4 == 0) goto L_0x00b6
            boolean r5 = r4.exists()     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            if (r5 == 0) goto L_0x00b6
            boolean r5 = r4.isFile()     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            if (r5 == 0) goto L_0x00b6
            long r5 = r4.length()     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            r7 = 0
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 == 0) goto L_0x00b6
            java.lang.String r5 = getAbsFileName(r11, r4, r12)     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            java.util.zip.ZipEntry r6 = new java.util.zip.ZipEntry     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            r6.<init>(r5)     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            long r7 = r4.length()     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            r6.setSize(r7)     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            long r7 = r4.lastModified()     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            r6.setTime(r7)     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            r1.putNextEntry(r6)     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            java.io.BufferedInputStream r5 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            r6.<init>(r4)     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
            r5.<init>(r6)     // Catch:{ Throwable -> 0x00c4, all -> 0x00c2 }
        L_0x0099:
            int r3 = r5.read(r10)     // Catch:{ Throwable -> 0x00b2, all -> 0x00af }
            r4 = -1
            if (r3 == r4) goto L_0x00a4
            r1.write(r10, r2, r3)     // Catch:{ Throwable -> 0x00b2, all -> 0x00af }
            goto L_0x0099
        L_0x00a4:
            r5.close()     // Catch:{ Throwable -> 0x00a7, all -> 0x00af }
        L_0x00a7:
            r1.flush()     // Catch:{ Throwable -> 0x00aa, all -> 0x00af }
        L_0x00aa:
            r1.closeEntry()     // Catch:{ Throwable -> 0x00ad, all -> 0x00af }
        L_0x00ad:
            r3 = r5
            goto L_0x00b6
        L_0x00af:
            r9 = move-exception
            r3 = r5
            goto L_0x00db
        L_0x00b2:
            r9 = move-exception
            r0 = r1
            r3 = r5
            goto L_0x00d3
        L_0x00b6:
            int r0 = r0 + 1
            goto L_0x0051
        L_0x00b9:
            if (r3 == 0) goto L_0x00be
            r3.close()     // Catch:{ Throwable -> 0x00be }
        L_0x00be:
            r1.close()     // Catch:{ Throwable -> 0x00c1 }
        L_0x00c1:
            return
        L_0x00c2:
            r9 = move-exception
            goto L_0x00db
        L_0x00c4:
            r9 = move-exception
            goto L_0x00cb
        L_0x00c6:
            r9 = move-exception
            r3 = r0
            goto L_0x00db
        L_0x00c9:
            r9 = move-exception
            r3 = r0
        L_0x00cb:
            r0 = r1
            goto L_0x00d3
        L_0x00cd:
            r9 = move-exception
            r1 = r0
            r3 = r1
            goto L_0x00db
        L_0x00d1:
            r9 = move-exception
            r3 = r0
        L_0x00d3:
            java.lang.Exception r10 = new java.lang.Exception     // Catch:{ all -> 0x00d9 }
            r10.<init>(r9)     // Catch:{ all -> 0x00d9 }
            throw r10     // Catch:{ all -> 0x00d9 }
        L_0x00d9:
            r9 = move-exception
            r1 = r0
        L_0x00db:
            if (r3 == 0) goto L_0x00e0
            r3.close()     // Catch:{ Throwable -> 0x00e0 }
        L_0x00e0:
            if (r1 == 0) goto L_0x00e5
            r1.close()     // Catch:{ Throwable -> 0x00e5 }
        L_0x00e5:
            throw r9
        L_0x00e6:
            java.lang.Exception r9 = new java.lang.Exception
            java.lang.String r10 = "zipFilePath is none"
            r9.<init>(r10)
            throw r9
        L_0x00ef:
            java.lang.Exception r9 = new java.lang.Exception
            java.lang.String r10 = "willZipList is empty"
            r9.<init>(r10)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.ZipUtils.zipFile(java.util.List, java.lang.String, java.io.File, com.alipay.mobile.monitor.util.ZipUtils$ZipFileHandler):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0018  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0032 A[LOOP:0: B:13:0x001c->B:19:0x0032, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047 A[EDGE_INSN: B:21:0x0047->B:20:0x0047 ?: BREAK  
    EDGE_INSN: B:21:0x0047->B:20:0x0047 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getAbsFileName(java.io.File r2, java.io.File r3, com.alipay.mobile.monitor.util.ZipUtils.ZipFileHandler r4) {
        /*
            r0 = 0
            if (r3 != 0) goto L_0x0004
            return r0
        L_0x0004:
            if (r2 != 0) goto L_0x000a
            java.io.File r2 = r3.getParentFile()
        L_0x000a:
            if (r4 == 0) goto L_0x0011
            java.lang.String r4 = r4.handleFileNameInZip(r3)     // Catch:{ Throwable -> 0x0011 }
            goto L_0x0012
        L_0x0011:
            r4 = r0
        L_0x0012:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L_0x001c
            java.lang.String r4 = r3.getName()
        L_0x001c:
            java.io.File r3 = r3.getParentFile()
            if (r3 == 0) goto L_0x0047
            boolean r0 = r3.equals(r2)
            if (r0 != 0) goto L_0x0047
            java.lang.String r0 = r3.getName()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x0047
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            r0 = 47
            r1.append(r0)
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            goto L_0x001c
        L_0x0047:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.ZipUtils.getAbsFileName(java.io.File, java.io.File, com.alipay.mobile.monitor.util.ZipUtils$ZipFileHandler):java.lang.String");
    }

    public static byte[] gzipDataByString(String str) throws IllegalStateException {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            return gzipDataByBytes(bytes, 0, bytes.length);
        } catch (Throwable th) {
            throw new IllegalStateException(th);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(9:1|2|3|4|5|6|7|8|9) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0032 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x001a */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002f A[SYNTHETIC, Splitter:B:21:0x002f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] gzipDataByBytes(byte[] r3, int r4, int r5) throws java.lang.IllegalStateException {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r1 = 8192(0x2000, float:1.14794E-41)
            r0.<init>(r1)
            r1 = 0
            java.util.zip.GZIPOutputStream r2 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x0026 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x0026 }
            r2.write(r3, r4, r5)     // Catch:{ Throwable -> 0x0021, all -> 0x001e }
            r2.finish()     // Catch:{ Throwable -> 0x0021, all -> 0x001e }
            byte[] r3 = r0.toByteArray()     // Catch:{ Throwable -> 0x0021, all -> 0x001e }
            r2.close()     // Catch:{ Throwable -> 0x001a }
        L_0x001a:
            r0.close()     // Catch:{ Throwable -> 0x001d }
        L_0x001d:
            return r3
        L_0x001e:
            r3 = move-exception
            r1 = r2
            goto L_0x002d
        L_0x0021:
            r3 = move-exception
            r1 = r2
            goto L_0x0027
        L_0x0024:
            r3 = move-exception
            goto L_0x002d
        L_0x0026:
            r3 = move-exception
        L_0x0027:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0024 }
            r4.<init>(r3)     // Catch:{ all -> 0x0024 }
            throw r4     // Catch:{ all -> 0x0024 }
        L_0x002d:
            if (r1 == 0) goto L_0x0032
            r1.close()     // Catch:{ Throwable -> 0x0032 }
        L_0x0032:
            r0.close()     // Catch:{ Throwable -> 0x0035 }
        L_0x0035:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.ZipUtils.gzipDataByBytes(byte[], int, int):byte[]");
    }

    public static String ungzipDataToString(byte[] bArr, int i, int i2) throws IllegalStateException {
        try {
            return new String(ungzipDataToBytes(bArr, i, i2), "UTF-8");
        } catch (Throwable th) {
            throw new IllegalStateException(th);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:3|4|(2:5|(1:7)(1:35))|8|9|10|11|12|13|14|15) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:25|(0)|29|30|31|32|33) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0026 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0029 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0044 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0047 */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0041 A[SYNTHETIC, Splitter:B:27:0x0041] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] ungzipDataToBytes(byte[] r4, int r5, int r6) throws java.lang.IllegalStateException {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r1 = 8192(0x2000, float:1.14794E-41)
            r0.<init>(r1)
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream
            r2.<init>(r4, r5, r6)
            r4 = 0
            java.util.zip.GZIPInputStream r5 = new java.util.zip.GZIPInputStream     // Catch:{ Throwable -> 0x0034, all -> 0x002f }
            r5.<init>(r2)     // Catch:{ Throwable -> 0x0034, all -> 0x002f }
            byte[] r4 = new byte[r1]     // Catch:{ Throwable -> 0x002d }
        L_0x0014:
            int r6 = r5.read(r4)     // Catch:{ Throwable -> 0x002d }
            if (r6 < 0) goto L_0x001f
            r1 = 0
            r0.write(r4, r1, r6)     // Catch:{ Throwable -> 0x002d }
            goto L_0x0014
        L_0x001f:
            byte[] r4 = r0.toByteArray()     // Catch:{ Throwable -> 0x002d }
            r5.close()     // Catch:{ Throwable -> 0x0026 }
        L_0x0026:
            r2.close()     // Catch:{ Throwable -> 0x0029 }
        L_0x0029:
            r0.close()     // Catch:{ Throwable -> 0x002c }
        L_0x002c:
            return r4
        L_0x002d:
            r4 = move-exception
            goto L_0x0038
        L_0x002f:
            r5 = move-exception
            r3 = r5
            r5 = r4
            r4 = r3
            goto L_0x003f
        L_0x0034:
            r5 = move-exception
            r3 = r5
            r5 = r4
            r4 = r3
        L_0x0038:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException     // Catch:{ all -> 0x003e }
            r6.<init>(r4)     // Catch:{ all -> 0x003e }
            throw r6     // Catch:{ all -> 0x003e }
        L_0x003e:
            r4 = move-exception
        L_0x003f:
            if (r5 == 0) goto L_0x0044
            r5.close()     // Catch:{ Throwable -> 0x0044 }
        L_0x0044:
            r2.close()     // Catch:{ Throwable -> 0x0047 }
        L_0x0047:
            r0.close()     // Catch:{ Throwable -> 0x004a }
        L_0x004a:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.ZipUtils.ungzipDataToBytes(byte[], int, int):byte[]");
    }
}
