package com.alipay.multimedia.io;

import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    public static boolean safeCopyToFile(File src, File dst) {
        BufferedInputStream in = null;
        try {
            BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(src));
            try {
                boolean safeCopyToFile = safeCopyToFile((InputStream) in2, dst);
                IOUtils.closeQuietly(in2);
                return safeCopyToFile;
            } catch (Throwable th) {
                th = th;
                in = in2;
                IOUtils.closeQuietly(in);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            IOUtils.closeQuietly(in);
            throw th;
        }
    }

    public static boolean safeCopyToFile(byte[] data, File dstFile) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try {
            return safeCopyToFile((InputStream) in, dstFile);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static boolean safeCopyToFile(InputStream in, File dstFile) {
        if (dstFile == null) {
            return false;
        }
        a(dstFile);
        File tmpFile = b(dstFile);
        boolean copied = copyToFile(in, tmpFile);
        if (!copied) {
            return copied;
        }
        a(dstFile);
        return tmpFile.renameTo(dstFile);
    }

    private static void a(File dstFile) {
        if (dstFile.exists() && dstFile.isFile() && !dstFile.delete()) {
            throw new IOException("delete dstFile failed!!");
        }
    }

    private static File b(File dstFile) {
        File tmpDstFile = new File(dstFile.getAbsolutePath() + "." + System.nanoTime());
        if (!tmpDstFile.exists()) {
            tmpDstFile.getParentFile().mkdirs();
        } else if (!tmpDstFile.delete()) {
            throw new IOException("delete tmp file error!!!");
        }
        tmpDstFile.createNewFile();
        return tmpDstFile;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:14:0x0023=Splitter:B:14:0x0023, B:21:0x0033=Splitter:B:21:0x0033} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyToFile(java.io.InputStream r6, java.io.File r7) {
        /*
            r3 = 0
            a(r7)     // Catch:{ IOException -> 0x0027 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0027 }
            r2.<init>(r7)     // Catch:{ IOException -> 0x0027 }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r4]     // Catch:{ all -> 0x0018 }
        L_0x000d:
            int r1 = r6.read(r0)     // Catch:{ all -> 0x0018 }
            if (r1 < 0) goto L_0x0029
            r4 = 0
            r2.write(r0, r4, r1)     // Catch:{ all -> 0x0018 }
            goto L_0x000d
        L_0x0018:
            r4 = move-exception
            r2.flush()     // Catch:{ IOException -> 0x0027 }
            java.io.FileDescriptor r5 = r2.getFD()     // Catch:{ IOException -> 0x003d }
            r5.sync()     // Catch:{ IOException -> 0x003d }
        L_0x0023:
            r2.close()     // Catch:{ IOException -> 0x0027 }
            throw r4     // Catch:{ IOException -> 0x0027 }
        L_0x0027:
            r4 = move-exception
        L_0x0028:
            return r3
        L_0x0029:
            r2.flush()     // Catch:{ IOException -> 0x0027 }
            java.io.FileDescriptor r4 = r2.getFD()     // Catch:{ IOException -> 0x0038 }
            r4.sync()     // Catch:{ IOException -> 0x0038 }
        L_0x0033:
            r2.close()     // Catch:{ IOException -> 0x0027 }
            r3 = 1
            goto L_0x0028
        L_0x0038:
            r4 = move-exception
            r2.close()     // Catch:{ IOException -> 0x0027 }
            goto L_0x0033
        L_0x003d:
            r5 = move-exception
            r2.close()     // Catch:{ IOException -> 0x0027 }
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.io.FileUtils.copyToFile(java.io.InputStream, java.io.File):boolean");
    }

    public static boolean checkFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            return checkFile(new File(path));
        }
        return false;
    }

    public static boolean checkFile(File file) {
        return file != null && file.exists() && file.isFile() && file.length() > 0;
    }

    public static long fileSize(String path) {
        if (!TextUtils.isEmpty(path)) {
            return new File(path).length();
        }
        return 0;
    }

    public static long fileSize(File path) {
        if (path != null) {
            return path.length();
        }
        return 0;
    }
}
