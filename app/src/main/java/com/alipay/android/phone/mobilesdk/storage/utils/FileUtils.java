package com.alipay.android.phone.mobilesdk.storage.utils;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.storage.file.BaseFile;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {
    private static final String TAG = "FileUtils";

    public static boolean IsCanUseSdCard() {
        try {
            return Environment.getExternalStorageState().equals("mounted");
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
            return false;
        }
    }

    public static String getSDPath() {
        if (IsCanUseSdCard()) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    public static boolean isSDcardAvailableSpace(long space) {
        if (!IsCanUseSdCard()) {
            return false;
        }
        try {
            StatFs statFs = new StatFs(new File(Environment.getExternalStorageDirectory().getPath()).getPath());
            if (space < ((long) statFs.getBlockSize()) * (((long) statFs.getAvailableBlocks()) - 4)) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) TAG, e);
            return false;
        }
    }

    public static void deleteFileByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
    }

    public static String readFile2String(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
            return null;
        }
    }

    public static String getTraceFile() {
        Object value = null;
        try {
            value = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"dalvik.vm.stack-trace-file"});
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
        }
        if (value != null) {
            return value.toString();
        }
        return "/data/anr/traces.txt";
    }

    public static boolean moveFile(File srcFile, BaseFile destFile) {
        boolean z = false;
        if (srcFile == null || destFile == null || !srcFile.exists() || !srcFile.isFile()) {
            return z;
        }
        try {
            return srcFile.renameTo(destFile);
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) TAG, (Throwable) e);
            return z;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0023  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFile(java.io.BufferedInputStream r5, java.io.File r6) {
        /*
            r1 = 0
            boolean r4 = r6.exists()     // Catch:{ Exception -> 0x0039, all -> 0x002f }
            if (r4 != 0) goto L_0x000a
            r6.createNewFile()     // Catch:{ Exception -> 0x0039, all -> 0x002f }
        L_0x000a:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0039, all -> 0x002f }
            r4 = 0
            r2.<init>(r6, r4)     // Catch:{ Exception -> 0x0039, all -> 0x002f }
            r4 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r4]     // Catch:{ Exception -> 0x001f, all -> 0x0036 }
        L_0x0014:
            int r3 = r5.read(r0)     // Catch:{ Exception -> 0x001f, all -> 0x0036 }
            if (r3 <= 0) goto L_0x0027
            r4 = 0
            r2.write(r0, r4, r3)     // Catch:{ Exception -> 0x001f, all -> 0x0036 }
            goto L_0x0014
        L_0x001f:
            r4 = move-exception
            r1 = r2
        L_0x0021:
            if (r1 == 0) goto L_0x0026
            r1.close()
        L_0x0026:
            return
        L_0x0027:
            r2.flush()     // Catch:{ Exception -> 0x001f, all -> 0x0036 }
            r2.close()
            r1 = r2
            goto L_0x0026
        L_0x002f:
            r4 = move-exception
        L_0x0030:
            if (r1 == 0) goto L_0x0035
            r1.close()
        L_0x0035:
            throw r4
        L_0x0036:
            r4 = move-exception
            r1 = r2
            goto L_0x0030
        L_0x0039:
            r4 = move-exception
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.storage.utils.FileUtils.copyFile(java.io.BufferedInputStream, java.io.File):void");
    }
}
