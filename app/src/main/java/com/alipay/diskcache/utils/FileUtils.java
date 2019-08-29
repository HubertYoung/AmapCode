package com.alipay.diskcache.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.alipay.diskcache.impl.BaseDiskCache;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    private static String TAG = "FileUtils";

    private static void deleteFile(File dstFile) {
        if (dstFile.exists() && dstFile.isFile() && !dstFile.delete()) {
            throw new IOException("delete dstFile failed!!");
        }
    }

    private static File createTmpFile(File dstFile) {
        File tmpDstFile = new File(dstFile.getAbsolutePath() + "." + System.nanoTime());
        if (tmpDstFile.exists()) {
            boolean deleted = tmpDstFile.delete();
            LogHelper.d(TAG, "createTmpFile del exists file: " + tmpDstFile + ", ret: " + deleted);
            if (!deleted) {
                throw new IOException("delete tmp file error!!!");
            }
        } else {
            tmpDstFile.getParentFile().mkdirs();
        }
        tmpDstFile.createNewFile();
        return tmpDstFile;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean cpFile(java.io.File r3, java.io.File r4) {
        /*
            java.io.FileInputStream r0 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0012 }
            r0.<init>(r3)     // Catch:{ Throwable -> 0x0012 }
            boolean r1 = safeCopyToFile(r0, r4)     // Catch:{ all -> 0x000d }
            r0.close()     // Catch:{ Throwable -> 0x0012 }
        L_0x000c:
            return r1
        L_0x000d:
            r2 = move-exception
            r0.close()     // Catch:{ Throwable -> 0x0012 }
            throw r2     // Catch:{ Throwable -> 0x0012 }
        L_0x0012:
            r2 = move-exception
            r1 = 0
            goto L_0x000c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.diskcache.utils.FileUtils.cpFile(java.io.File, java.io.File):boolean");
    }

    public static boolean safeCopyToFile(InputStream in, File dstFile) {
        if (dstFile == null) {
            return false;
        }
        deleteFile(dstFile);
        File tmpFile = createTmpFile(dstFile);
        boolean copied = copyToFile(in, tmpFile);
        if (!copied) {
            return copied;
        }
        deleteFile(dstFile);
        boolean copied2 = tmpFile.renameTo(dstFile);
        LogHelper.i(TAG, "safeCopyToFile tmpFile: " + tmpFile + ", dstFile: " + dstFile);
        return copied2;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean copyToFile(java.io.InputStream r5, java.io.File r6) {
        /*
            r3 = 0
            deleteFile(r6)     // Catch:{ IOException -> 0x0020 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0020 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x0020 }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r0 = new byte[r4]     // Catch:{ all -> 0x0018 }
        L_0x000d:
            int r1 = r5.read(r0)     // Catch:{ all -> 0x0018 }
            if (r1 < 0) goto L_0x0022
            r4 = 0
            r2.write(r0, r4, r1)     // Catch:{ all -> 0x0018 }
            goto L_0x000d
        L_0x0018:
            r4 = move-exception
            r2.flush()     // Catch:{ IOException -> 0x0020 }
            r2.close()     // Catch:{ IOException -> 0x0020 }
            throw r4     // Catch:{ IOException -> 0x0020 }
        L_0x0020:
            r4 = move-exception
        L_0x0021:
            return r3
        L_0x0022:
            r2.flush()     // Catch:{ IOException -> 0x0020 }
            r2.close()     // Catch:{ IOException -> 0x0020 }
            r3 = 1
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.diskcache.utils.FileUtils.copyToFile(java.io.InputStream, java.io.File):boolean");
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

    private static StatFs getStatFs(Context context) {
        if (context == null) {
            return null;
        }
        try {
            String root = getSDPath();
            if (!TextUtils.isEmpty(root)) {
                return new StatFs(root);
            }
            return null;
        } catch (Throwable t) {
            LogHelper.d(BaseDiskCache.TAG, "getStatFs error" + t.getMessage());
            return null;
        }
    }

    private static String getSDPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    public static long getSdAvailableSizeBytes(Context context) {
        StatFs sf = getStatFs(context);
        if (sf == null) {
            return 2147483647L;
        }
        return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
    }
}
