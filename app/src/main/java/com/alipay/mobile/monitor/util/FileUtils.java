package com.alipay.mobile.monitor.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import java.io.File;
import java.io.IOException;

public class FileUtils {
    private static final int BUFFER_LENGTH = 8192;
    private static final String TAG = "MonitorFileUtils";

    public enum PathType {
        PATH_TYPE_INNER,
        PATH_TYPE_EXTERNAL,
        PATH_TYPE_ALIPAY,
        PATH_TYPE_ANY;

        public static Result checkPathValid(Context context, File file, PathType pathType) {
            switch (pathType) {
                case PATH_TYPE_INNER:
                    return FileUtils.checkInnerStoragePathValid(context, file);
                case PATH_TYPE_EXTERNAL:
                    return FileUtils.checkExternalStoragePathValid(context, file);
                case PATH_TYPE_ALIPAY:
                    return FileUtils.checkAlipayPathValid(context, file);
                case PATH_TYPE_ANY:
                    if (FileUtils.checkInnerStoragePathValid(context, file).success) {
                        return new Result(true, 0, "");
                    }
                    if (FileUtils.checkExternalStoragePathValid(context, file).success) {
                        return new Result(true, 0, "");
                    }
                    if (FileUtils.checkAlipayPathValid(context, file).success) {
                        return new Result(true, 0, "");
                    }
                    StringBuilder sb = new StringBuilder("can not delete file in prohibit path, file path is ");
                    sb.append(file.getAbsolutePath());
                    sb.append(" not in Inner,External or Alipay");
                    return new Result(false, 3, sb.toString());
                default:
                    return new Result(false, 3, "invalid PathType");
            }
        }
    }

    public static class Result {
        public static final int ERROR_CODE_EXCEPTION = 5;
        public static final int ERROR_CODE_FILE_NOT_EXIST = 6;
        public static final int ERROR_CODE_INVALID_PATH = 3;
        public static final int ERROR_CODE_IS_DIRECTORY = 2;
        public static final int ERROR_CODE_PARAM_IS_NULL = 1;
        public static final int ERROR_CODE_SYSTEM_ERROR = 4;
        public static final int SUCCESS = 0;
        public int errCode;
        public String errMessage;
        public boolean success;

        public Result(boolean z, int i, String str) {
            this.success = z;
            this.errCode = i;
            this.errMessage = str;
        }
    }

    public static Result deleteFileOnly(Context context, File file, PathType pathType) {
        Result checkBeforeDelete = checkBeforeDelete(context, file, pathType);
        if (!checkBeforeDelete.success) {
            return checkBeforeDelete;
        }
        if (file.isDirectory()) {
            return new Result(false, 2, "can not delete a directory by using deleteFileOnly");
        }
        if (!deleteFile(file)) {
            return new Result(false, 4, "File.delete returns false");
        }
        StringBuilder sb = new StringBuilder("delete file ");
        sb.append(file.getAbsolutePath());
        sb.append(" success");
        return new Result(true, 0, sb.toString());
    }

    public static Result cleanDirectory(Context context, File file, PathType pathType) {
        return deleteOrCleanDirectory(context, file, false, pathType);
    }

    public static Result deleteDirectory(Context context, File file, PathType pathType) {
        return deleteOrCleanDirectory(context, file, true, pathType);
    }

    private static Result deleteOrCleanDirectory(Context context, File file, boolean z, PathType pathType) {
        Result checkBeforeDelete = checkBeforeDelete(context, file, pathType);
        if (!checkBeforeDelete.success) {
            return checkBeforeDelete;
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return new Result(false, 4, "dir listFiles returns null maybe do not have permission");
        }
        if (listFiles.length != 0 || !z) {
            for (File file2 : listFiles) {
                if (file2.isFile()) {
                    deleteFile(file2);
                } else {
                    deleteOrCleanDirectory(context, file2, z, pathType);
                }
            }
            if (!z) {
                File[] listFiles2 = file.listFiles();
                if (listFiles2.length == 0) {
                    StringBuilder sb = new StringBuilder("delete dir ");
                    sb.append(file.getAbsolutePath());
                    sb.append(" success");
                    return new Result(true, 0, sb.toString());
                }
                for (File isFile : listFiles2) {
                    if (isFile.isFile()) {
                        return new Result(false, 4, "file.delete returns false");
                    }
                }
                StringBuilder sb2 = new StringBuilder("delete dir ");
                sb2.append(file.getAbsolutePath());
                sb2.append(" success");
                return new Result(true, 0, sb2.toString());
            } else if (file.delete()) {
                StringBuilder sb3 = new StringBuilder("delete dir ");
                sb3.append(file.getAbsolutePath());
                sb3.append(" success");
                return new Result(true, 0, sb3.toString());
            } else {
                StringBuilder sb4 = new StringBuilder("delete dir ");
                sb4.append(file.getAbsolutePath());
                sb4.append(" fail");
                return new Result(false, 4, sb4.toString());
            }
        } else if (!deleteFile(file)) {
            return new Result(false, 4, "File.delete returns false");
        } else {
            StringBuilder sb5 = new StringBuilder("delete dir ");
            sb5.append(file.getAbsolutePath());
            sb5.append(" success");
            return new Result(true, 0, sb5.toString());
        }
    }

    /* access modifiers changed from: private */
    public static Result checkInnerStoragePathValid(Context context, File file) {
        String absolutePath = file.getAbsolutePath();
        String parent = context.getCacheDir().getParent();
        if (!absolutePath.contains(parent)) {
            StringBuilder sb = new StringBuilder("can not delete file in prohibit path, specified path = ");
            sb.append(parent);
            sb.append(", but file path = ");
            sb.append(absolutePath);
            return new Result(false, 3, sb.toString());
        } else if (absolutePath.equals(parent)) {
            return new Result(false, 3, "can not delete inner root dir ".concat(String.valueOf(parent)));
        } else {
            return new Result(true, 0, "");
        }
    }

    /* access modifiers changed from: private */
    public static Result checkAlipayPathValid(Context context, File file) {
        try {
            String absolutePath = new File(new File(Environment.getExternalStorageDirectory(), BehavorReporter.PROVIDE_BY_ALIPAY), context.getPackageName()).getAbsolutePath();
            String absolutePath2 = file.getAbsolutePath();
            if (!absolutePath2.contains(absolutePath)) {
                StringBuilder sb = new StringBuilder("can not delete file in prohibit path, specified path = ");
                sb.append(absolutePath);
                sb.append(", but file path = ");
                sb.append(absolutePath2);
                return new Result(false, 3, sb.toString());
            } else if (absolutePath2.equals(absolutePath)) {
                return new Result(false, 3, "can not delete alipay root dir ".concat(String.valueOf(absolutePath)));
            } else {
                return new Result(true, 0, "");
            }
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder("checkAlipayPathValid throws exception! ");
            sb2.append(th.getMessage());
            return new Result(false, 5, sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public static Result checkExternalStoragePathValid(Context context, File file) {
        try {
            String parent = context.getExternalCacheDir().getParent();
            String absolutePath = file.getAbsolutePath();
            if (!absolutePath.contains(parent)) {
                StringBuilder sb = new StringBuilder("can not delete file in prohibit path, specified path = ");
                sb.append(parent);
                sb.append(", but file path = ");
                sb.append(absolutePath);
                return new Result(false, 3, sb.toString());
            } else if (absolutePath.equals(parent)) {
                return new Result(false, 3, "can not delete external root dir ".concat(String.valueOf(parent)));
            } else {
                return new Result(true, 0, "");
            }
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder("checkExternalStoragePathValid throws exception! ");
            sb2.append(th.getMessage());
            return new Result(false, 5, sb2.toString());
        }
    }

    private static Result checkBeforeDelete(Context context, File file, PathType pathType) {
        if (context == null) {
            return new Result(false, 1, "context is null");
        }
        if (file == null) {
            return new Result(false, 1, "file is null");
        }
        String absolutePath = file.getAbsolutePath();
        if (TextUtils.isEmpty(absolutePath)) {
            return new Result(false, 1, "param file getAbsolutePath returns null");
        }
        if (!file.exists()) {
            StringBuilder sb = new StringBuilder();
            sb.append(absolutePath);
            sb.append(" do not exist");
            return new Result(false, 6, sb.toString());
        } else if (!absolutePath.contains(context.getPackageName())) {
            StringBuilder sb2 = new StringBuilder("file path ");
            sb2.append(absolutePath);
            sb2.append(" do not contains package name");
            return new Result(false, 3, sb2.toString());
        } else if (absolutePath.contains("/..") || absolutePath.contains("../")) {
            StringBuilder sb3 = new StringBuilder("file path");
            sb3.append(absolutePath);
            sb3.append("can not contains \"../\" or \"/..\"");
            return new Result(false, 3, sb3.toString());
        } else {
            Result checkPathValid = PathType.checkPathValid(context, file, pathType);
            if (!checkPathValid.success) {
                return checkPathValid;
            }
            return new Result(true, 0, "");
        }
    }

    private static boolean deleteFile(File file) {
        if (file.delete()) {
            StringBuilder sb = new StringBuilder("delete ");
            sb.append(file.getAbsolutePath());
            sb.append(" success!");
            return true;
        }
        StringBuilder sb2 = new StringBuilder("delete ");
        sb2.append(file.getAbsolutePath());
        sb2.append(" fail!");
        return false;
    }

    public static boolean isCanUseSdCard() {
        try {
            return "mounted".equals(Environment.getExternalStorageState());
        } catch (Throwable th) {
            new StringBuilder("isCanUseSdCard: ").append(th);
            return false;
        }
    }

    public static String getSDPath() {
        if (!isCanUseSdCard()) {
            return null;
        }
        try {
            return Environment.getExternalStorageDirectory().getPath();
        } catch (Throwable unused) {
            return null;
        }
    }

    public static boolean isSDcardAvailableSpace(long j) {
        String sDPath = getSDPath();
        if (sDPath == null) {
            return false;
        }
        try {
            StatFs statFs = new StatFs(sDPath);
            if (j < (((long) statFs.getAvailableBlocks()) - 4) * ((long) statFs.getBlockSize())) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static boolean isAppAvailableSpace(long j) {
        File dataDirectory = Environment.getDataDirectory();
        if (dataDirectory == null) {
            return false;
        }
        try {
            StatFs statFs = new StatFs(dataDirectory.getPath());
            if (j < ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static void deleteFileNotDir(File file) {
        if (file != null) {
            try {
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            } catch (Throwable unused) {
                new StringBuilder("deleteFileNotDir: ").append(file.getAbsolutePath());
            }
        }
    }

    public static void deleteFileByPath(String str) {
        if (!TextUtils.isEmpty(str)) {
            deleteFileNotDir(new File(str));
        }
    }

    public static String getTraceFile() {
        Object obj = null;
        try {
            obj = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"dalvik.vm.stack-trace-file"});
        } catch (Throwable unused) {
        }
        if (obj == null) {
            return "/data/anr/traces.txt";
        }
        return obj.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x004d A[SYNTHETIC, Splitter:B:31:0x004d] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0054 A[SYNTHETIC, Splitter:B:39:0x0054] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readAssetFile(android.content.Context r6, java.lang.String r7) {
        /*
            r0 = 0
            if (r6 == 0) goto L_0x0058
            boolean r1 = android.text.TextUtils.isEmpty(r7)
            if (r1 == 0) goto L_0x000a
            goto L_0x0058
        L_0x000a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            android.content.res.Resources r6 = r6.getResources()     // Catch:{ Throwable -> 0x0051, all -> 0x0049 }
            if (r6 != 0) goto L_0x0016
            return r0
        L_0x0016:
            android.content.res.AssetManager r6 = r6.getAssets()     // Catch:{ Throwable -> 0x0051, all -> 0x0049 }
            java.io.InputStream r6 = r6.open(r7)     // Catch:{ Throwable -> 0x0051, all -> 0x0049 }
            java.io.BufferedInputStream r7 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0052, all -> 0x0044 }
            r7.<init>(r6)     // Catch:{ Throwable -> 0x0052, all -> 0x0044 }
            r6 = 8192(0x2000, float:1.14794E-41)
            byte[] r6 = new byte[r6]     // Catch:{ Throwable -> 0x0042, all -> 0x0040 }
        L_0x0027:
            int r2 = r7.read(r6)     // Catch:{ Throwable -> 0x0042, all -> 0x0040 }
            r3 = -1
            if (r2 == r3) goto L_0x0038
            java.lang.String r3 = new java.lang.String     // Catch:{ Throwable -> 0x0042, all -> 0x0040 }
            r4 = 0
            r3.<init>(r6, r4, r2)     // Catch:{ Throwable -> 0x0042, all -> 0x0040 }
            r1.append(r3)     // Catch:{ Throwable -> 0x0042, all -> 0x0040 }
            goto L_0x0027
        L_0x0038:
            java.lang.String r6 = r1.toString()     // Catch:{ Throwable -> 0x0042, all -> 0x0040 }
            r7.close()     // Catch:{ Throwable -> 0x003f }
        L_0x003f:
            return r6
        L_0x0040:
            r6 = move-exception
            goto L_0x004b
        L_0x0042:
            r6 = r7
            goto L_0x0052
        L_0x0044:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
            goto L_0x004b
        L_0x0049:
            r6 = move-exception
            r7 = r0
        L_0x004b:
            if (r7 == 0) goto L_0x0050
            r7.close()     // Catch:{ Throwable -> 0x0050 }
        L_0x0050:
            throw r6
        L_0x0051:
            r6 = r0
        L_0x0052:
            if (r6 == 0) goto L_0x0057
            r6.close()     // Catch:{ Throwable -> 0x0057 }
        L_0x0057:
            return r0
        L_0x0058:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.FileUtils.readAssetFile(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0040 A[SYNTHETIC, Splitter:B:26:0x0040] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFile(java.io.File r6) throws java.io.IOException {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0037 }
            r2.<init>(r6)     // Catch:{ Throwable -> 0x0037 }
            java.io.BufferedInputStream r1 = new java.io.BufferedInputStream     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            r1.<init>(r2)     // Catch:{ Throwable -> 0x0032, all -> 0x002f }
            r6 = 8192(0x2000, float:1.14794E-41)
            byte[] r6 = new byte[r6]     // Catch:{ Throwable -> 0x0037 }
        L_0x0014:
            int r2 = r1.read(r6)     // Catch:{ Throwable -> 0x0037 }
            r3 = -1
            if (r2 == r3) goto L_0x0027
            java.lang.String r3 = new java.lang.String     // Catch:{ Throwable -> 0x0037 }
            r4 = 0
            java.lang.String r5 = "UTF-8"
            r3.<init>(r6, r4, r2, r5)     // Catch:{ Throwable -> 0x0037 }
            r0.append(r3)     // Catch:{ Throwable -> 0x0037 }
            goto L_0x0014
        L_0x0027:
            java.lang.String r6 = r0.toString()     // Catch:{ Throwable -> 0x0037 }
            r1.close()     // Catch:{ Throwable -> 0x002e }
        L_0x002e:
            return r6
        L_0x002f:
            r6 = move-exception
            r1 = r2
            goto L_0x003e
        L_0x0032:
            r6 = move-exception
            r1 = r2
            goto L_0x0038
        L_0x0035:
            r6 = move-exception
            goto L_0x003e
        L_0x0037:
            r6 = move-exception
        L_0x0038:
            java.io.IOException r0 = new java.io.IOException     // Catch:{ all -> 0x0035 }
            r0.<init>(r6)     // Catch:{ all -> 0x0035 }
            throw r0     // Catch:{ all -> 0x0035 }
        L_0x003e:
            if (r1 == 0) goto L_0x0043
            r1.close()     // Catch:{ Throwable -> 0x0043 }
        L_0x0043:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.FileUtils.readFile(java.io.File):java.lang.String");
    }

    public static String readFileStringFully(File file) throws IOException {
        byte[] readFileByteFully = readFileByteFully(file);
        if (readFileByteFully == null) {
            return null;
        }
        try {
            return new String(readFileByteFully);
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x005e A[SYNTHETIC, Splitter:B:37:0x005e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] readFileByteFully(java.io.File r5) throws java.io.IOException {
        /*
            r0 = 0
            if (r5 == 0) goto L_0x0062
            boolean r1 = r5.isDirectory()     // Catch:{ Throwable -> 0x0055 }
            if (r1 != 0) goto L_0x0062
            boolean r1 = r5.isFile()     // Catch:{ Throwable -> 0x0055 }
            if (r1 == 0) goto L_0x0062
            boolean r1 = r5.exists()     // Catch:{ Throwable -> 0x0055 }
            if (r1 != 0) goto L_0x0016
            goto L_0x0062
        L_0x0016:
            long r1 = r5.length()     // Catch:{ Throwable -> 0x0055 }
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            r2 = 0
            if (r1 != 0) goto L_0x0024
            byte[] r5 = new byte[r2]     // Catch:{ Throwable -> 0x0055 }
            return r5
        L_0x0024:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0055 }
            r1.<init>(r5)     // Catch:{ Throwable -> 0x0055 }
            int r5 = r1.available()     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            byte[] r5 = new byte[r5]     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            r0 = 0
        L_0x0030:
            int r3 = r5.length     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            int r3 = r3 - r0
            int r3 = r1.read(r5, r0, r3)     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            if (r3 <= 0) goto L_0x0049
            int r0 = r0 + r3
            int r3 = r1.available()     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            int r4 = r5.length     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            int r4 = r4 - r0
            if (r3 <= r4) goto L_0x0030
            int r3 = r3 + r0
            byte[] r3 = new byte[r3]     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            java.lang.System.arraycopy(r5, r2, r3, r2, r0)     // Catch:{ Throwable -> 0x0050, all -> 0x004d }
            r5 = r3
            goto L_0x0030
        L_0x0049:
            r1.close()     // Catch:{ Throwable -> 0x004c }
        L_0x004c:
            return r5
        L_0x004d:
            r5 = move-exception
            r0 = r1
            goto L_0x005c
        L_0x0050:
            r5 = move-exception
            r0 = r1
            goto L_0x0056
        L_0x0053:
            r5 = move-exception
            goto L_0x005c
        L_0x0055:
            r5 = move-exception
        L_0x0056:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0053 }
            r1.<init>(r5)     // Catch:{ all -> 0x0053 }
            throw r1     // Catch:{ all -> 0x0053 }
        L_0x005c:
            if (r0 == 0) goto L_0x0061
            r0.close()     // Catch:{ Throwable -> 0x0061 }
        L_0x0061:
            throw r5
        L_0x0062:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.FileUtils.readFileByteFully(java.io.File):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0039 A[SYNTHETIC, Splitter:B:23:0x0039] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeFile(java.io.File r2, byte[] r3, boolean r4) throws java.io.IOException {
        /*
            r0 = 0
            java.io.File r1 = r2.getParentFile()     // Catch:{ Throwable -> 0x0030 }
            boolean r1 = r1.exists()     // Catch:{ Throwable -> 0x0030 }
            if (r1 != 0) goto L_0x0012
            java.io.File r1 = r2.getParentFile()     // Catch:{ Throwable -> 0x0030 }
            r1.mkdirs()     // Catch:{ Throwable -> 0x0030 }
        L_0x0012:
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0030 }
            r1.<init>(r2, r4)     // Catch:{ Throwable -> 0x0030 }
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            r2 = 8192(0x2000, float:1.14794E-41)
            r0.<init>(r1, r2)     // Catch:{ Throwable -> 0x002b, all -> 0x0028 }
            r0.write(r3)     // Catch:{ Throwable -> 0x0030 }
            r0.flush()     // Catch:{ Throwable -> 0x0030 }
            r0.close()     // Catch:{ Throwable -> 0x0027 }
        L_0x0027:
            return
        L_0x0028:
            r2 = move-exception
            r0 = r1
            goto L_0x0037
        L_0x002b:
            r2 = move-exception
            r0 = r1
            goto L_0x0031
        L_0x002e:
            r2 = move-exception
            goto L_0x0037
        L_0x0030:
            r2 = move-exception
        L_0x0031:
            java.io.IOException r3 = new java.io.IOException     // Catch:{ all -> 0x002e }
            r3.<init>(r2)     // Catch:{ all -> 0x002e }
            throw r3     // Catch:{ all -> 0x002e }
        L_0x0037:
            if (r0 == 0) goto L_0x003c
            r0.close()     // Catch:{ Throwable -> 0x003c }
        L_0x003c:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.FileUtils.writeFile(java.io.File, byte[], boolean):void");
    }

    public static void writeFile(File file, String str, boolean z) throws IOException {
        try {
            writeFile(file, str.getBytes("UTF-8"), z);
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    public static void moveFile(File file, File file2) throws IOException {
        try {
            if (!file.renameTo(file2)) {
                copyFile(file, file2);
                file.delete();
            }
            if (!file.exists()) {
                if (file2.exists()) {
                    return;
                }
            }
            throw new Exception("move file fail");
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    /* JADX WARNING: type inference failed for: r9v0, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r8v0, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r9v1 */
    /* JADX WARNING: type inference failed for: r8v1 */
    /* JADX WARNING: type inference failed for: r8v2 */
    /* JADX WARNING: type inference failed for: r9v2 */
    /* JADX WARNING: type inference failed for: r8v3 */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r8v4 */
    /* JADX WARNING: type inference failed for: r9v4 */
    /* JADX WARNING: type inference failed for: r8v5 */
    /* JADX WARNING: type inference failed for: r8v6 */
    /* JADX WARNING: type inference failed for: r9v5 */
    /* JADX WARNING: type inference failed for: r8v7 */
    /* JADX WARNING: type inference failed for: r8v8 */
    /* JADX WARNING: type inference failed for: r8v9 */
    /* JADX WARNING: type inference failed for: r8v10 */
    /* JADX WARNING: type inference failed for: r8v11, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r9v6 */
    /* JADX WARNING: type inference failed for: r9v7 */
    /* JADX WARNING: type inference failed for: r9v8, types: [java.nio.channels.FileChannel] */
    /* JADX WARNING: type inference failed for: r7v0, types: [java.nio.channels.WritableByteChannel] */
    /* JADX WARNING: type inference failed for: r9v9 */
    /* JADX WARNING: type inference failed for: r8v12 */
    /* JADX WARNING: type inference failed for: r8v13 */
    /* JADX WARNING: type inference failed for: r9v10 */
    /* JADX WARNING: type inference failed for: r8v14 */
    /* JADX WARNING: type inference failed for: r8v15 */
    /* JADX WARNING: type inference failed for: r8v16 */
    /* JADX WARNING: type inference failed for: r8v17 */
    /* JADX WARNING: type inference failed for: r8v18 */
    /* JADX WARNING: type inference failed for: r8v19 */
    /* JADX WARNING: type inference failed for: r9v11 */
    /* JADX WARNING: type inference failed for: r9v12 */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0034 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r9v1
      assigns: []
      uses: []
      mth insns count: 75
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0039 A[SYNTHETIC, Splitter:B:25:0x0039] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x003d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x006c A[SYNTHETIC, Splitter:B:57:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0071 A[SYNTHETIC, Splitter:B:61:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0076 A[SYNTHETIC, Splitter:B:65:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x007b A[SYNTHETIC, Splitter:B:69:0x007b] */
    /* JADX WARNING: Unknown variable types count: 13 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void copyFile(java.io.File r10, java.io.File r11) throws java.io.IOException {
        /*
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x005e, all -> 0x0058 }
            r1.<init>(r10)     // Catch:{ Throwable -> 0x005e, all -> 0x0058 }
            java.nio.channels.FileChannel r10 = r1.getChannel()     // Catch:{ Throwable -> 0x0052, all -> 0x004e }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x004b, all -> 0x0048 }
            r8.<init>(r11)     // Catch:{ Throwable -> 0x004b, all -> 0x0048 }
            java.nio.channels.FileChannel r9 = r8.getChannel()     // Catch:{ Throwable -> 0x0045, all -> 0x0042 }
            r3 = 0
            long r5 = r10.size()     // Catch:{ Throwable -> 0x0040, all -> 0x003e }
            r2 = r10
            r7 = r9
            r2.transferTo(r3, r5, r7)     // Catch:{ Throwable -> 0x0040, all -> 0x003e }
            boolean r11 = r11.exists()     // Catch:{ Throwable -> 0x0040, all -> 0x003e }
            if (r11 != 0) goto L_0x002c
            java.lang.RuntimeException r11 = new java.lang.RuntimeException     // Catch:{ Throwable -> 0x0040, all -> 0x003e }
            java.lang.String r0 = "copy file fail"
            r11.<init>(r0)     // Catch:{ Throwable -> 0x0040, all -> 0x003e }
            throw r11     // Catch:{ Throwable -> 0x0040, all -> 0x003e }
        L_0x002c:
            r1.close()     // Catch:{ Throwable -> 0x002f }
        L_0x002f:
            if (r10 == 0) goto L_0x0034
            r10.close()     // Catch:{ Throwable -> 0x0034 }
        L_0x0034:
            r8.close()     // Catch:{ Throwable -> 0x0037 }
        L_0x0037:
            if (r9 == 0) goto L_0x003d
            r9.close()     // Catch:{ Throwable -> 0x003c }
        L_0x003c:
            return
        L_0x003d:
            return
        L_0x003e:
            r11 = move-exception
            goto L_0x006a
        L_0x0040:
            r11 = move-exception
            goto L_0x0056
        L_0x0042:
            r11 = move-exception
            r9 = r0
            goto L_0x006a
        L_0x0045:
            r11 = move-exception
            r9 = r0
            goto L_0x0056
        L_0x0048:
            r11 = move-exception
            r8 = r0
            goto L_0x005c
        L_0x004b:
            r11 = move-exception
            r8 = r0
            goto L_0x0055
        L_0x004e:
            r11 = move-exception
            r10 = r0
            r8 = r10
            goto L_0x005c
        L_0x0052:
            r11 = move-exception
            r10 = r0
            r8 = r10
        L_0x0055:
            r9 = r8
        L_0x0056:
            r0 = r1
            goto L_0x0062
        L_0x0058:
            r11 = move-exception
            r10 = r0
            r1 = r10
            r8 = r1
        L_0x005c:
            r9 = r8
            goto L_0x006a
        L_0x005e:
            r11 = move-exception
            r10 = r0
            r8 = r10
            r9 = r8
        L_0x0062:
            java.io.IOException r1 = new java.io.IOException     // Catch:{ all -> 0x0068 }
            r1.<init>(r11)     // Catch:{ all -> 0x0068 }
            throw r1     // Catch:{ all -> 0x0068 }
        L_0x0068:
            r11 = move-exception
            r1 = r0
        L_0x006a:
            if (r1 == 0) goto L_0x006f
            r1.close()     // Catch:{ Throwable -> 0x006f }
        L_0x006f:
            if (r10 == 0) goto L_0x0074
            r10.close()     // Catch:{ Throwable -> 0x0074 }
        L_0x0074:
            if (r8 == 0) goto L_0x0079
            r8.close()     // Catch:{ Throwable -> 0x0079 }
        L_0x0079:
            if (r9 == 0) goto L_0x007e
            r9.close()     // Catch:{ Throwable -> 0x007e }
        L_0x007e:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.util.FileUtils.copyFile(java.io.File, java.io.File):void");
    }

    public static long getFolderSize(File file) {
        if (file == null || !file.exists() || !file.isDirectory()) {
            return 0;
        }
        try {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                if (listFiles.length != 0) {
                    long j = 0;
                    for (File file2 : listFiles) {
                        if (file2 != null && file2.exists()) {
                            if (file2.isFile()) {
                                j += file2.length();
                            } else {
                                j += getFolderSize(file2);
                            }
                        }
                    }
                    return j;
                }
            }
            return 0;
        } catch (Throwable unused) {
            file.getAbsolutePath();
            return 0;
        }
    }
}
