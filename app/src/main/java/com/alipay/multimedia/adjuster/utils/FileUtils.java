package com.alipay.multimedia.adjuster.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    private static final Log logger = Log.getLogger((String) "FileUtils");

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
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.multimedia.adjuster.utils.FileUtils.copyToFile(java.io.InputStream, java.io.File):boolean");
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
        logger.d("safeCopyToFile tmpFile: " + tmpFile + ", dstFile: " + dstFile, new Object[0]);
        return copied2;
    }

    private static void deleteFile(File dstFile) {
        if (dstFile.exists() && dstFile.isFile()) {
            boolean deleted = dstFile.delete();
            if (!deleted) {
                logger.d("deleteFile file: " + dstFile + ", ret? " + deleted, new Object[0]);
                throw new IOException("delete dstFile failed!!");
            }
        }
    }

    private static File createTmpFile(File dstFile) {
        File tmpDstFile = new File(dstFile.getAbsolutePath() + "." + System.nanoTime());
        if (tmpDstFile.exists()) {
            boolean deleted = tmpDstFile.delete();
            if (!deleted) {
                logger.d("createTmpFile del exists file: " + tmpDstFile + ", ret: " + deleted, new Object[0]);
                throw new IOException("delete tmp file error!!!");
            }
        } else {
            tmpDstFile.getParentFile().mkdirs();
        }
        tmpDstFile.createNewFile();
        return tmpDstFile;
    }

    public static boolean delete(File file) {
        if (file == null) {
            return false;
        }
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        if (!file.isDirectory() || !file.exists()) {
            return false;
        }
        boolean delete = true;
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File f : files) {
                delete &= delete(f);
            }
        }
        return file.delete() & delete;
    }

    public static void scanPictureAsync(Context ctx, String picturePath) {
        if (ctx != null && !TextUtils.isEmpty(picturePath)) {
            try {
                Intent scanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                if (PathUtils.isContentUriPath(picturePath)) {
                    scanIntent.setData(Uri.parse(picturePath));
                } else {
                    scanIntent.setData(Uri.fromFile(new File(picturePath)));
                }
                ctx.sendBroadcast(scanIntent);
            } catch (Exception e) {
                logger.d("scanPictureAsync exp: " + e.toString(), new Object[0]);
            }
        }
    }
}
