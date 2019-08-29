package com.alipay.mobile.common.patch;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.android.hackbyte.ClassVerifier;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import java.io.File;

public class PatchUtils {
    public static final String ExtDataTunnel = "ExtDataTunnel";
    public static final String PATCH = "patch";
    private static final String[] a = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", SuperId.BIT_1_RQBXY, SuperId.BIT_1_NEARBY_SEARCH, "d", "e", "f"};

    public PatchUtils() {
        if (Boolean.FALSE.booleanValue()) {
            Log.v("hackbyte ", ClassVerifier.class.toString());
        }
    }

    public static String getStoragePath(Context context) {
        if (context == null) {
            return null;
        }
        File externalFilesDir = context.getExternalFilesDir("patch");
        if (externalFilesDir == null) {
            String sdcard = getSDPath();
            if (TextUtils.isEmpty(sdcard)) {
                return null;
            }
            externalFilesDir = new File(sdcard + File.separator + ExtDataTunnel + File.separator + AutoJsonUtils.JSON_FILES + File.separator + "patch");
        }
        String dir = externalFilesDir.getAbsolutePath();
        LoggerFactory.getTraceLogger().debug("PatchUtils", "getAbsolutePath = " + dir);
        if (externalFilesDir.exists() || externalFilesDir.mkdirs()) {
            return dir;
        }
        LoggerFactory.getTraceLogger().debug("PatchUtils", "path not exist " + dir);
        return null;
    }

    public static String getPatchFile(Context context, String patchUrl) {
        String dir = getStoragePath(context);
        if (TextUtils.isEmpty(dir) || TextUtils.isEmpty(patchUrl)) {
            LoggerFactory.getTraceLogger().debug("PatchUtils", "Patch dir is null or patchurl is null");
            return null;
        }
        String filePath = dir + File.separator + patchUrl.hashCode() + ".patch";
        File f = new File(filePath);
        if (f.getParentFile() == null) {
            LoggerFactory.getTraceLogger().debug("PatchUtils", "Patch file can not creat");
            return null;
        } else if (f.exists() || f.getParentFile().mkdirs() || f.getParentFile().exists()) {
            return filePath;
        } else {
            LoggerFactory.getTraceLogger().debug("PatchUtils", "Patch file can not creat");
            return null;
        }
    }

    public static boolean checkFileInMd5(String md5, File file) {
        if (md5 == null || file == null || !file.exists()) {
            return false;
        }
        LoggerFactory.getTraceLogger().debug("PatchUtils", "begin get file MD5");
        long start = System.currentTimeMillis();
        String targetFileMd5 = genFileMd5sum(file);
        LoggerFactory.getTraceLogger().debug("PatchUtils", "end,time:" + (System.currentTimeMillis() - start));
        if (md5.equalsIgnoreCase(targetFileMd5)) {
            LoggerFactory.getTraceLogger().debug("PatchUtils", "checkFileInMd5 success");
            return true;
        }
        LoggerFactory.getTraceLogger().debug("PatchUtils", "check Md5 = false file:" + file + "currentFile Md5:" + targetFileMd5 + ",targetMd5:" + md5);
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x005b A[SYNTHETIC, Splitter:B:31:0x005b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String genFileMd5sum(java.io.File r9) {
        /*
            r6 = 0
            if (r9 == 0) goto L_0x0009
            boolean r7 = r9.exists()
            if (r7 != 0) goto L_0x000a
        L_0x0009:
            return r6
        L_0x000a:
            r2 = 0
            r7 = 1024(0x400, float:1.435E-42)
            byte[] r0 = new byte[r7]
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x006d }
            r3.<init>(r9)     // Catch:{ Exception -> 0x006d }
            java.lang.String r7 = "MD5"
            java.security.MessageDigest r4 = java.security.MessageDigest.getInstance(r7)     // Catch:{ Exception -> 0x0025, all -> 0x006a }
        L_0x001a:
            int r5 = r3.read(r0)     // Catch:{ Exception -> 0x0025, all -> 0x006a }
            if (r5 <= 0) goto L_0x0041
            r7 = 0
            r4.update(r0, r7, r5)     // Catch:{ Exception -> 0x0025, all -> 0x006a }
            goto L_0x001a
        L_0x0025:
            r1 = move-exception
            r2 = r3
        L_0x0027:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0058 }
            java.lang.String r8 = "PatchUtils"
            r7.warn(r8, r1)     // Catch:{ all -> 0x0058 }
            if (r2 == 0) goto L_0x0009
            r2.close()     // Catch:{ IOException -> 0x0036 }
            goto L_0x0009
        L_0x0036:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "PatchUtils"
            r7.warn(r8, r1)
            goto L_0x0009
        L_0x0041:
            byte[] r7 = r4.digest()     // Catch:{ Exception -> 0x0025, all -> 0x006a }
            java.lang.String r6 = a(r7)     // Catch:{ Exception -> 0x0025, all -> 0x006a }
            r3.close()     // Catch:{ IOException -> 0x004d }
            goto L_0x0009
        L_0x004d:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "PatchUtils"
            r7.warn(r8, r1)
            goto L_0x0009
        L_0x0058:
            r6 = move-exception
        L_0x0059:
            if (r2 == 0) goto L_0x005e
            r2.close()     // Catch:{ IOException -> 0x005f }
        L_0x005e:
            throw r6
        L_0x005f:
            r1 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r7 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r8 = "PatchUtils"
            r7.warn(r8, r1)
            goto L_0x005e
        L_0x006a:
            r6 = move-exception
            r2 = r3
            goto L_0x0059
        L_0x006d:
            r1 = move-exception
            goto L_0x0027
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.patch.PatchUtils.genFileMd5sum(java.io.File):java.lang.String");
    }

    private static String a(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (byte a2 : b) {
            resultSb.append(a(a2));
        }
        return resultSb.toString();
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r5v0, types: [byte, int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(int r5) {
        /*
            r2 = r5
            if (r5 >= 0) goto L_0x0005
            int r2 = r2 + 256
        L_0x0005:
            int r0 = r2 / 16
            int r1 = r2 % 16
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String[] r4 = a
            r4 = r4[r0]
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String[] r4 = a
            r4 = r4[r1]
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.patch.PatchUtils.a(byte):java.lang.String");
    }

    public static boolean checkPathAndStorage(Context context, String dir) {
        if (!IsCanUseSdCard()) {
            return false;
        }
        return true;
    }

    public static boolean isFileExists(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        if (file.isDirectory() || !file.exists()) {
            return false;
        }
        return true;
    }

    public static boolean creatFileDir(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        try {
            File file = new File(path);
            boolean isDelSuccess = true;
            if (file.exists()) {
                isDelSuccess = file.delete();
            }
            if (!isDelSuccess) {
                LoggerFactory.getTraceLogger().warn((String) "PatchUtils", (String) "file del fail! when creatFileDir");
                return false;
            } else if (file.isDirectory()) {
                LoggerFactory.getTraceLogger().warn((String) "PatchUtils", (String) "param error,path is isDirectory");
                return false;
            } else {
                File parentFile = file.getParentFile();
                if (parentFile == null) {
                    LoggerFactory.getTraceLogger().warn((String) "PatchUtils", (String) "can not find parent dir!");
                    return false;
                } else if (parentFile.exists()) {
                    return true;
                } else {
                    if (parentFile.mkdirs()) {
                        return true;
                    }
                    LoggerFactory.getTraceLogger().warn((String) "PatchUtils", (String) "can not mkdirs!");
                    return false;
                }
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().warn((String) "PatchUtils", (Throwable) e);
            return false;
        }
    }

    public static void deleteFileByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            LoggerFactory.getTraceLogger().debug("PatchUtils", "deleteFileByPath = " + path);
            try {
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                LoggerFactory.getTraceLogger().warn((String) "PatchUtils", (Throwable) e);
            }
        }
    }

    public static boolean IsCanUseSdCard() {
        try {
            return Environment.getExternalStorageState().equals("mounted");
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isSDcardAvailableSpace(long space) {
        if (!IsCanUseSdCard()) {
            return false;
        }
        StatFs statFs = new StatFs(new File(Environment.getExternalStorageDirectory().getPath()).getPath());
        long availableSpare = ((long) statFs.getBlockSize()) * (((long) statFs.getAvailableBlocks()) - 4);
        LoggerFactory.getTraceLogger().debug("PatchUtils", "availableSpare = " + availableSpare);
        if (space < availableSpare) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0051 A[SYNTHETIC, Splitter:B:17:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0059 A[SYNTHETIC, Splitter:B:22:0x0059] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long getFileSizes(java.io.File r11) {
        /*
            r4 = 0
            r1 = 0
            if (r11 == 0) goto L_0x0054
            boolean r3 = r11.exists()     // Catch:{ Exception -> 0x0045 }
            if (r3 == 0) goto L_0x0054
            boolean r3 = r11.isDirectory()     // Catch:{ Exception -> 0x0045 }
            if (r3 != 0) goto L_0x0054
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0045 }
            r2.<init>(r11)     // Catch:{ Exception -> 0x0045 }
            int r3 = r2.available()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            long r4 = (long) r3     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.lang.String r8 = "PatchUtils"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            r9.<init>()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.lang.String r10 = r11.getName()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.lang.String r10 = "file length = "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.lang.StringBuilder r9 = r9.append(r4)     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            java.lang.String r9 = r9.toString()     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            r3.debug(r8, r9)     // Catch:{ Exception -> 0x0066, all -> 0x0063 }
            r2.close()     // Catch:{ Exception -> 0x005d }
        L_0x0042:
            r1 = r2
            r6 = r4
        L_0x0044:
            return r6
        L_0x0045:
            r0 = move-exception
        L_0x0046:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r3 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0056 }
            java.lang.String r8 = "PatchUtils"
            r3.warn(r8, r0)     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x0054
            r1.close()     // Catch:{ Exception -> 0x005f }
        L_0x0054:
            r6 = r4
            goto L_0x0044
        L_0x0056:
            r3 = move-exception
        L_0x0057:
            if (r1 == 0) goto L_0x005c
            r1.close()     // Catch:{ Exception -> 0x0061 }
        L_0x005c:
            throw r3
        L_0x005d:
            r3 = move-exception
            goto L_0x0042
        L_0x005f:
            r3 = move-exception
            goto L_0x0054
        L_0x0061:
            r8 = move-exception
            goto L_0x005c
        L_0x0063:
            r3 = move-exception
            r1 = r2
            goto L_0x0057
        L_0x0066:
            r0 = move-exception
            r1 = r2
            goto L_0x0046
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.patch.PatchUtils.getFileSizes(java.io.File):long");
    }

    public static String getSDPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    public static boolean isEnoughSpaceDoPatch(File patchFile, File oldFile) {
        boolean z = false;
        long patchLength = getFileSizes(patchFile);
        if (patchLength == 0) {
            LoggerFactory.getTraceLogger().error((String) "PatchUtils", (String) "patchLength is 0");
            return z;
        }
        long oldLength = getFileSizes(oldFile);
        if (oldLength == 0) {
            LoggerFactory.getTraceLogger().warn((String) "PatchUtils", (String) "oldFileLength is 0");
            return z;
        }
        try {
            return isSDcardAvailableSpace(patchLength + oldLength);
        } catch (Exception e) {
            return z;
        }
    }
}
