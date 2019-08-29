package com.alipay.mobile.common.logging.util;

import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtil {
    public static boolean isCanUseSdCard() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Throwable e) {
            Log.e("LogFileUtil", "isCanUseSdCard: " + e);
            return false;
        }
    }

    public static String getSDPath() {
        String str = null;
        if (!isCanUseSdCard()) {
            return str;
        }
        try {
            return Environment.getExternalStorageDirectory().getPath();
        } catch (Throwable t) {
            Log.e("LogFileUtil", "getSDPath", t);
            return str;
        }
    }

    public static boolean isSDcardAvailableSpace(long space) {
        String dirPath = getSDPath();
        if (dirPath == null) {
            return false;
        }
        try {
            StatFs statFs = new StatFs(dirPath);
            if (space < (((long) statFs.getAvailableBlocks()) - 4) * ((long) statFs.getBlockSize())) {
                return true;
            }
            return false;
        } catch (Throwable t) {
            Log.e("LogFileUtil", "isSDcardAvailableSpace", t);
            return false;
        }
    }

    public static boolean isAppAvailableSpace(long space) {
        File dirFile = Environment.getDataDirectory();
        if (dirFile == null) {
            return false;
        }
        try {
            StatFs statFs = new StatFs(dirFile.getPath());
            if (space < ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) {
                return true;
            }
            return false;
        } catch (Throwable t) {
            Log.e("LogFileUtil", "isAppAvailableSpace", t);
            return false;
        }
    }

    public static void deleteFileNotDir(File file) {
        if (file != null) {
            try {
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            } catch (Throwable t) {
                Log.e("LogFileUtil", "deleteFileNotDir: " + file.getAbsolutePath(), t);
            }
        }
    }

    public static void deleteFileByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            deleteFileNotDir(new File(path));
        }
    }

    public static String getTraceFile() {
        Object value = null;
        try {
            value = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"dalvik.vm.stack-trace-file"});
        } catch (Throwable e) {
            Log.e("LogFileUtil", "getTraceFile", e);
        }
        if (value == null) {
            return "/data/anr/traces.txt";
        }
        return value.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0056 A[SYNTHETIC, Splitter:B:32:0x0056] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readAssetFile(android.content.Context r11, java.lang.String r12) {
        /*
            r8 = 0
            if (r11 == 0) goto L_0x0009
            boolean r9 = android.text.TextUtils.isEmpty(r12)
            if (r9 == 0) goto L_0x000a
        L_0x0009:
            return r8
        L_0x000a:
            r3 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            android.content.res.Resources r6 = r11.getResources()     // Catch:{ Throwable -> 0x005f }
            if (r6 == 0) goto L_0x0009
            android.content.res.AssetManager r9 = r6.getAssets()     // Catch:{ Throwable -> 0x005f }
            java.io.InputStream r3 = r9.open(r12)     // Catch:{ Throwable -> 0x005f }
            java.io.BufferedInputStream r4 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x005f }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x005f }
            r9 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r9]     // Catch:{ Throwable -> 0x0038, all -> 0x005c }
        L_0x0027:
            int r5 = r4.read(r0)     // Catch:{ Throwable -> 0x0038, all -> 0x005c }
            r9 = -1
            if (r5 == r9) goto L_0x0049
            java.lang.String r7 = new java.lang.String     // Catch:{ Throwable -> 0x0038, all -> 0x005c }
            r9 = 0
            r7.<init>(r0, r9, r5)     // Catch:{ Throwable -> 0x0038, all -> 0x005c }
            r1.append(r7)     // Catch:{ Throwable -> 0x0038, all -> 0x005c }
            goto L_0x0027
        L_0x0038:
            r2 = move-exception
            r3 = r4
        L_0x003a:
            java.lang.String r9 = "LogFileUtil"
            java.lang.String r10 = "readAssetFile"
            android.util.Log.e(r9, r10, r2)     // Catch:{ all -> 0x0053 }
            if (r3 == 0) goto L_0x0009
            r3.close()     // Catch:{ Throwable -> 0x0047 }
            goto L_0x0009
        L_0x0047:
            r9 = move-exception
            goto L_0x0009
        L_0x0049:
            java.lang.String r8 = r1.toString()     // Catch:{ Throwable -> 0x0038, all -> 0x005c }
            r4.close()     // Catch:{ Throwable -> 0x0051 }
            goto L_0x0009
        L_0x0051:
            r9 = move-exception
            goto L_0x0009
        L_0x0053:
            r8 = move-exception
        L_0x0054:
            if (r3 == 0) goto L_0x0059
            r3.close()     // Catch:{ Throwable -> 0x005a }
        L_0x0059:
            throw r8
        L_0x005a:
            r9 = move-exception
            goto L_0x0059
        L_0x005c:
            r8 = move-exception
            r3 = r4
            goto L_0x0054
        L_0x005f:
            r2 = move-exception
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.FileUtil.readAssetFile(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0024 A[SYNTHETIC, Splitter:B:14:0x0024] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFile(java.io.File r6) {
        /*
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x001a }
            r3.<init>(r6)     // Catch:{ Throwable -> 0x001a }
            int r4 = r3.available()     // Catch:{ Throwable -> 0x002f, all -> 0x002c }
            byte[] r0 = new byte[r4]     // Catch:{ Throwable -> 0x002f, all -> 0x002c }
            r3.read(r0)     // Catch:{ Throwable -> 0x002f, all -> 0x002c }
            java.lang.String r4 = new java.lang.String     // Catch:{ Throwable -> 0x002f, all -> 0x002c }
            java.lang.String r5 = "UTF-8"
            r4.<init>(r0, r5)     // Catch:{ Throwable -> 0x002f, all -> 0x002c }
            r3.close()     // Catch:{ Throwable -> 0x0028 }
        L_0x0019:
            return r4
        L_0x001a:
            r1 = move-exception
        L_0x001b:
            java.io.IOException r4 = new java.io.IOException     // Catch:{ all -> 0x0021 }
            r4.<init>(r1)     // Catch:{ all -> 0x0021 }
            throw r4     // Catch:{ all -> 0x0021 }
        L_0x0021:
            r4 = move-exception
        L_0x0022:
            if (r2 == 0) goto L_0x0027
            r2.close()     // Catch:{ Throwable -> 0x002a }
        L_0x0027:
            throw r4
        L_0x0028:
            r5 = move-exception
            goto L_0x0019
        L_0x002a:
            r5 = move-exception
            goto L_0x0027
        L_0x002c:
            r4 = move-exception
            r2 = r3
            goto L_0x0022
        L_0x002f:
            r1 = move-exception
            r2 = r3
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.FileUtil.readFile(java.io.File):java.lang.String");
    }

    public static byte[] toByteArray(File f) {
        if (f == null || !f.exists()) {
            throw new IOException();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            BufferedInputStream in2 = new BufferedInputStream(new FileInputStream(f));
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int len = in2.read(buffer, 0, 1024);
                    if (-1 == len) {
                        break;
                    }
                    bos.write(buffer, 0, len);
                }
                byte[] byteArray = bos.toByteArray();
                try {
                    in2.close();
                } catch (Throwable th) {
                }
                try {
                    bos.close();
                } catch (Throwable th2) {
                }
                return byteArray;
            } catch (Throwable th3) {
                th = th3;
                in = in2;
                if (in != null) {
                    try {
                        in.close();
                    } catch (Throwable th4) {
                    }
                }
                try {
                    bos.close();
                } catch (Throwable th5) {
                }
                throw th;
            }
        } catch (Throwable th6) {
            th = th6;
            try {
                throw th;
            } catch (Throwable th7) {
                th = th7;
            }
        }
    }

    public static String readFileStringFully(File file) {
        byte[] data = readFileByteFully(file);
        if (data == null) {
            return null;
        }
        try {
            return new String(data);
        } catch (Throwable t) {
            throw new IOException(t);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x005c A[SYNTHETIC, Splitter:B:31:0x005c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readFileByteFully(java.io.File r12) {
        /*
            r4 = 0
            if (r12 == 0) goto L_0x0015
            boolean r8 = r12.isDirectory()     // Catch:{ Throwable -> 0x0052 }
            if (r8 != 0) goto L_0x0015
            boolean r8 = r12.isFile()     // Catch:{ Throwable -> 0x0052 }
            if (r8 == 0) goto L_0x0015
            boolean r8 = r12.exists()     // Catch:{ Throwable -> 0x0052 }
            if (r8 != 0) goto L_0x0017
        L_0x0015:
            r2 = 0
        L_0x0016:
            return r2
        L_0x0017:
            long r8 = r12.length()     // Catch:{ Throwable -> 0x0052 }
            r10 = 0
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 != 0) goto L_0x0025
            r8 = 0
            byte[] r2 = new byte[r8]     // Catch:{ Throwable -> 0x0052 }
            goto L_0x0016
        L_0x0025:
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0052 }
            r5.<init>(r12)     // Catch:{ Throwable -> 0x0052 }
            int r1 = r5.available()     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            r3 = 0
            byte[] r2 = new byte[r1]     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
        L_0x0031:
            int r8 = r2.length     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            int r8 = r8 - r3
            int r0 = r5.read(r2, r3, r8)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            if (r0 <= 0) goto L_0x004d
            int r3 = r3 + r0
            int r1 = r5.available()     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            int r8 = r2.length     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            int r8 = r8 - r3
            if (r1 <= r8) goto L_0x0031
            int r8 = r3 + r1
            byte[] r7 = new byte[r8]     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            r8 = 0
            r9 = 0
            java.lang.System.arraycopy(r2, r8, r7, r9, r3)     // Catch:{ Throwable -> 0x0067, all -> 0x0064 }
            r2 = r7
            goto L_0x0031
        L_0x004d:
            r5.close()     // Catch:{ Throwable -> 0x0060 }
        L_0x0050:
            r4 = r5
            goto L_0x0016
        L_0x0052:
            r6 = move-exception
        L_0x0053:
            java.io.IOException r8 = new java.io.IOException     // Catch:{ all -> 0x0059 }
            r8.<init>(r6)     // Catch:{ all -> 0x0059 }
            throw r8     // Catch:{ all -> 0x0059 }
        L_0x0059:
            r8 = move-exception
        L_0x005a:
            if (r4 == 0) goto L_0x005f
            r4.close()     // Catch:{ Throwable -> 0x0062 }
        L_0x005f:
            throw r8
        L_0x0060:
            r8 = move-exception
            goto L_0x0050
        L_0x0062:
            r9 = move-exception
            goto L_0x005f
        L_0x0064:
            r8 = move-exception
            r4 = r5
            goto L_0x005a
        L_0x0067:
            r6 = move-exception
            r4 = r5
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.FileUtil.readFileByteFully(java.io.File):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x002b A[SYNTHETIC, Splitter:B:16:0x002b] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeFile(java.io.File r5, byte[] r6, boolean r7) {
        /*
            r1 = 0
            java.io.File r3 = r5.getParentFile()     // Catch:{ Throwable -> 0x0021 }
            boolean r3 = r3.exists()     // Catch:{ Throwable -> 0x0021 }
            if (r3 != 0) goto L_0x0012
            java.io.File r3 = r5.getParentFile()     // Catch:{ Throwable -> 0x0021 }
            r3.mkdirs()     // Catch:{ Throwable -> 0x0021 }
        L_0x0012:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0021 }
            r2.<init>(r5, r7)     // Catch:{ Throwable -> 0x0021 }
            r2.write(r6)     // Catch:{ Throwable -> 0x0036, all -> 0x0033 }
            r2.flush()     // Catch:{ Throwable -> 0x0036, all -> 0x0033 }
            r2.close()     // Catch:{ Throwable -> 0x002f }
        L_0x0020:
            return
        L_0x0021:
            r0 = move-exception
        L_0x0022:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x0028 }
            r3.<init>(r0)     // Catch:{ all -> 0x0028 }
            throw r3     // Catch:{ all -> 0x0028 }
        L_0x0028:
            r3 = move-exception
        L_0x0029:
            if (r1 == 0) goto L_0x002e
            r1.close()     // Catch:{ Throwable -> 0x0031 }
        L_0x002e:
            throw r3
        L_0x002f:
            r3 = move-exception
            goto L_0x0020
        L_0x0031:
            r4 = move-exception
            goto L_0x002e
        L_0x0033:
            r3 = move-exception
            r1 = r2
            goto L_0x0029
        L_0x0036:
            r0 = move-exception
            r1 = r2
            goto L_0x0022
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.FileUtil.writeFile(java.io.File, byte[], boolean):void");
    }

    public static void writeFile(File file, String content, boolean isAppend) {
        try {
            writeFile(file, content.getBytes("UTF-8"), isAppend);
        } catch (Throwable e) {
            throw new IOException(e);
        }
    }

    public static void moveFile(File src, File dst) {
        try {
            if (!src.renameTo(dst)) {
                copyFile(src, dst);
                src.delete();
            }
            if (src.exists() || !dst.exists()) {
                throw new Exception("move file fail");
            }
        } catch (Throwable e) {
            throw new IOException(e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0039 A[SYNTHETIC, Splitter:B:17:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003e A[SYNTHETIC, Splitter:B:20:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0043 A[SYNTHETIC, Splitter:B:23:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0048 A[SYNTHETIC, Splitter:B:26:0x0048] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFile(java.io.File r11, java.io.File r12) {
        /*
            r7 = 0
            r0 = 0
            r9 = 0
            r5 = 0
            java.io.FileInputStream r8 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0074 }
            r8.<init>(r11)     // Catch:{ Throwable -> 0x0074 }
            java.nio.channels.FileChannel r0 = r8.getChannel()     // Catch:{ Throwable -> 0x0076, all -> 0x006d }
            java.io.FileOutputStream r10 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0076, all -> 0x006d }
            r10.<init>(r12)     // Catch:{ Throwable -> 0x0076, all -> 0x006d }
            java.nio.channels.FileChannel r5 = r10.getChannel()     // Catch:{ Throwable -> 0x002d, all -> 0x0070 }
            r1 = 0
            long r3 = r0.size()     // Catch:{ Throwable -> 0x002d, all -> 0x0070 }
            r0.transferTo(r1, r3, r5)     // Catch:{ Throwable -> 0x002d, all -> 0x0070 }
            boolean r1 = r12.exists()     // Catch:{ Throwable -> 0x002d, all -> 0x0070 }
            if (r1 != 0) goto L_0x004c
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ Throwable -> 0x002d, all -> 0x0070 }
            java.lang.String r2 = "copy file fail"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x002d, all -> 0x0070 }
            throw r1     // Catch:{ Throwable -> 0x002d, all -> 0x0070 }
        L_0x002d:
            r6 = move-exception
            r9 = r10
            r7 = r8
        L_0x0030:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0036 }
            r1.<init>(r6)     // Catch:{ all -> 0x0036 }
            throw r1     // Catch:{ all -> 0x0036 }
        L_0x0036:
            r1 = move-exception
        L_0x0037:
            if (r7 == 0) goto L_0x003c
            r7.close()     // Catch:{ Throwable -> 0x0065 }
        L_0x003c:
            if (r0 == 0) goto L_0x0041
            r0.close()     // Catch:{ Throwable -> 0x0067 }
        L_0x0041:
            if (r9 == 0) goto L_0x0046
            r9.close()     // Catch:{ Throwable -> 0x0069 }
        L_0x0046:
            if (r5 == 0) goto L_0x004b
            r5.close()     // Catch:{ Throwable -> 0x006b }
        L_0x004b:
            throw r1
        L_0x004c:
            r8.close()     // Catch:{ Throwable -> 0x005d }
        L_0x004f:
            if (r0 == 0) goto L_0x0054
            r0.close()     // Catch:{ Throwable -> 0x005f }
        L_0x0054:
            r10.close()     // Catch:{ Throwable -> 0x0061 }
        L_0x0057:
            if (r5 == 0) goto L_0x005c
            r5.close()     // Catch:{ Throwable -> 0x0063 }
        L_0x005c:
            return
        L_0x005d:
            r1 = move-exception
            goto L_0x004f
        L_0x005f:
            r1 = move-exception
            goto L_0x0054
        L_0x0061:
            r1 = move-exception
            goto L_0x0057
        L_0x0063:
            r1 = move-exception
            goto L_0x005c
        L_0x0065:
            r2 = move-exception
            goto L_0x003c
        L_0x0067:
            r2 = move-exception
            goto L_0x0041
        L_0x0069:
            r2 = move-exception
            goto L_0x0046
        L_0x006b:
            r2 = move-exception
            goto L_0x004b
        L_0x006d:
            r1 = move-exception
            r7 = r8
            goto L_0x0037
        L_0x0070:
            r1 = move-exception
            r9 = r10
            r7 = r8
            goto L_0x0037
        L_0x0074:
            r6 = move-exception
            goto L_0x0030
        L_0x0076:
            r6 = move-exception
            r7 = r8
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.FileUtil.copyFile(java.io.File, java.io.File):void");
    }

    public static long getFolderSize(File dir) {
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
                        size += getFolderSize(file);
                    }
                }
            }
            return size;
        } catch (Throwable t) {
            Log.w("LogFileUtil", dir.getAbsolutePath(), t);
            return 0;
        }
    }
}
