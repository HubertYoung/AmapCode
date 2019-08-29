package com.alipay.mobile.common.patch.dir;

import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Flater {
    private static final String a = Flater.class.getSimpleName();
    private static final boolean b = (File.separatorChar != '/');
    private static final Charset c = (Charset.isSupported("US-ASCII") ? Charset.forName("US-ASCII") : Charset.defaultCharset());

    public Flater() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static boolean packFolderToFile(String folderPath, String flatPath) {
        if (folderPath == null || folderPath.length() == 0 || flatPath == null || flatPath.length() == 0) {
            return false;
        }
        File folderDir = new File(folderPath);
        File flatFile = new File(flatPath);
        FileOutputStream fileOutputStream = null;
        try {
            if (!folderDir.isDirectory() || flatFile.isDirectory()) {
                a((Closeable) null);
                return false;
            }
            List itemPaths = new ArrayList();
            a(folderDir, itemPaths, folderDir.getCanonicalPath().length() + 1);
            Collections.sort(itemPaths, new Comparator<String>() {
                {
                    if (Boolean.FALSE.booleanValue()) {
                        Log.v("hackbyte ", ClassVerifier.class.toString());
                    }
                }

                public final int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            flatFile.getParentFile().mkdirs();
            FileOutputStream fileOutputStream2 = new FileOutputStream(flatFile, false);
            int i = 0;
            while (i < itemPaths.size()) {
                try {
                    String itemPath = (String) itemPaths.get(i);
                    File itemFile = new File(folderDir, itemPath);
                    StringBuffer buffer = new StringBuffer(itemPath);
                    if (itemFile.isFile()) {
                        long len = itemFile.length();
                        buffer.append(0);
                        buffer.append(len);
                    } else {
                        buffer.append('/');
                    }
                    buffer.append(0);
                    fileOutputStream2.write(a(buffer.toString()));
                    if (itemFile.isFile()) {
                        a(itemFile, (OutputStream) fileOutputStream2);
                    }
                    i++;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    a((Closeable) fileOutputStream);
                    throw th;
                }
            }
            a((Closeable) fileOutputStream2);
            return true;
        } catch (Throwable th2) {
            e = th2;
            LoggerFactory.getTraceLogger().error(a, "packFolderToFile", e);
            a((Closeable) fileOutputStream);
            return false;
        }
    }

    private static void a(File folder, List<String> paths, int cutLen) {
        File[] listFiles;
        for (File sub : folder.listFiles()) {
            try {
                String path = sub.getCanonicalPath().substring(cutLen);
                if (b) {
                    path = path.replace(File.separatorChar, '/');
                }
                paths.add(path);
                if (sub.isDirectory()) {
                    a(sub, paths, cutLen);
                }
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error(a, "packFolderToStream", e);
            }
        }
    }

    private static void a(File file, OutputStream output) {
        InputStream input = null;
        try {
            InputStream input2 = new FileInputStream(file);
            try {
                byte[] data = new byte[1024];
                while (true) {
                    int len = input2.read(data);
                    if (len != -1) {
                        output.write(data, 0, len);
                    } else {
                        a((Closeable) input2);
                        FileInputStream fileInputStream = input2;
                        return;
                    }
                }
            } catch (Throwable th) {
                th = th;
                input = input2;
                a((Closeable) input);
                throw th;
            }
        } catch (Throwable th2) {
            e = th2;
            LoggerFactory.getTraceLogger().error(a, "loadFileToStream", e);
            a((Closeable) input);
        }
    }

    private static byte[] a(String str) {
        return str.getBytes(c);
    }

    private static String a(byte[] data, int length) {
        return new String(data, 0, length, c);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ce, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().error(a, "unpackFileToFolder", r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x00e0, code lost:
        r15 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0114, code lost:
        r20 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0115, code lost:
        r15 = r18;
        r12 = r13;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0114 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:29:0x0073] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean unpackFileToFolder(java.lang.String r23, java.lang.String r24) {
        /*
            if (r23 == 0) goto L_0x0010
            int r20 = r23.length()
            if (r20 == 0) goto L_0x0010
            if (r24 == 0) goto L_0x0010
            int r20 = r24.length()
            if (r20 != 0) goto L_0x0013
        L_0x0010:
            r20 = 0
        L_0x0012:
            return r20
        L_0x0013:
            java.io.File r9 = new java.io.File
            r0 = r23
            r9.<init>(r0)
            java.io.File r10 = new java.io.File
            r0 = r24
            r10.<init>(r0)
            r12 = 0
            r15 = 0
            boolean r20 = r9.isFile()     // Catch:{ Throwable -> 0x00ed }
            if (r20 == 0) goto L_0x002f
            boolean r20 = r10.isFile()     // Catch:{ Throwable -> 0x00ed }
            if (r20 == 0) goto L_0x003c
        L_0x002f:
            r20 = 0
            a(r20)
            r20 = 0
            a(r20)
            r20 = 0
            goto L_0x0012
        L_0x003c:
            boolean r20 = r10.isDirectory()     // Catch:{ Throwable -> 0x00ed }
            if (r20 == 0) goto L_0x0055
            boolean r20 = a(r10)     // Catch:{ Throwable -> 0x00ed }
            if (r20 != 0) goto L_0x0055
            r20 = 0
            a(r20)
            r20 = 0
            a(r20)
            r20 = 0
            goto L_0x0012
        L_0x0055:
            r10.mkdirs()     // Catch:{ Throwable -> 0x00ed }
            java.io.FileInputStream r13 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00ed }
            r13.<init>(r9)     // Catch:{ Throwable -> 0x00ed }
            r20 = 1024(0x400, float:1.435E-42)
            r0 = r20
            byte[] r6 = new byte[r0]     // Catch:{ Throwable -> 0x0119, all -> 0x0111 }
            r14 = 1
            r5 = 0
            r16 = 0
            r18 = r15
        L_0x0069:
            r20 = 0
            int r20 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1))
            if (r20 <= 0) goto L_0x007d
            r0 = r18
            r1 = r16
            a(r13, r0, r1, r6)     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            a(r18)     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r14 = 1
            r16 = 0
            goto L_0x0069
        L_0x007d:
            int r20 = r13.read()     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r0 = r20
            byte r4 = (byte) r0     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            if (r4 < 0) goto L_0x00e3
            if (r4 == 0) goto L_0x008d
            r6[r5] = r4     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            int r5 = r5 + 1
            goto L_0x0069
        L_0x008d:
            java.lang.String r19 = a(r6, r5)     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            int r20 = r19.length()     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            if (r20 == 0) goto L_0x0069
            if (r14 == 0) goto L_0x00c7
            int r11 = r5 + -1
            java.io.File r8 = new java.io.File     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r0 = r19
            r8.<init>(r10, r0)     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r0 = r19
            char r20 = r0.charAt(r11)     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r21 = 47
            r0 = r20
            r1 = r21
            if (r0 != r1) goto L_0x00b9
            r8.mkdirs()     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r15 = r18
        L_0x00b5:
            r5 = 0
            r18 = r15
            goto L_0x0069
        L_0x00b9:
            java.io.File r20 = r8.getParentFile()     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r20.mkdirs()     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            java.io.FileOutputStream r15 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r15.<init>(r8)     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r14 = 0
            goto L_0x00b5
        L_0x00c7:
            long r16 = java.lang.Long.parseLong(r19)     // Catch:{ Throwable -> 0x00ce, all -> 0x0114 }
            r15 = r18
            goto L_0x00b5
        L_0x00ce:
            r7 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r20 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            java.lang.String r21 = a     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            java.lang.String r22 = "unpackFileToFolder"
            r0 = r20
            r1 = r21
            r2 = r22
            r0.error(r1, r2, r7)     // Catch:{ Throwable -> 0x011c, all -> 0x0114 }
            r15 = r18
            goto L_0x00b5
        L_0x00e3:
            a(r13)
            a(r18)
            r20 = 1
            goto L_0x0012
        L_0x00ed:
            r7 = move-exception
        L_0x00ee:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r20 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0109 }
            java.lang.String r21 = a     // Catch:{ all -> 0x0109 }
            java.lang.String r22 = "unpackFileToFolder"
            r0 = r20
            r1 = r21
            r2 = r22
            r0.error(r1, r2, r7)     // Catch:{ all -> 0x0109 }
            a(r12)
            a(r15)
            r20 = 0
            goto L_0x0012
        L_0x0109:
            r20 = move-exception
        L_0x010a:
            a(r12)
            a(r15)
            throw r20
        L_0x0111:
            r20 = move-exception
            r12 = r13
            goto L_0x010a
        L_0x0114:
            r20 = move-exception
            r15 = r18
            r12 = r13
            goto L_0x010a
        L_0x0119:
            r7 = move-exception
            r12 = r13
            goto L_0x00ee
        L_0x011c:
            r7 = move-exception
            r15 = r18
            r12 = r13
            goto L_0x00ee
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.patch.dir.Flater.unpackFileToFolder(java.lang.String, java.lang.String):boolean");
    }

    private static boolean a(File file) {
        try {
            if (file.isDirectory()) {
                for (File a2 : file.listFiles()) {
                    a(a2);
                }
            }
            return file.delete();
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error(a, "deleteFile", e);
            return false;
        }
    }

    private static void a(InputStream input, OutputStream output, long length, byte[] buf) {
        int count = (int) (length / 1024);
        int extra = (int) (length % 1024);
        int i = 0;
        while (i < count) {
            try {
                input.read(buf);
                output.write(buf);
                i++;
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error(a, "readByteToStream", e);
                return;
            }
        }
        if (extra > 0) {
            input.read(buf, 0, extra);
            output.write(buf, 0, extra);
        }
    }

    private static void a(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Throwable e) {
                LoggerFactory.getTraceLogger().error(a, "closeStream", e);
            }
        }
    }
}
