package com.alipay.mobile.common.nativecrash;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CrashCombineUtils {
    public static final int DEFAULT_MAX_INFO_LEN = 1048576;
    public static final String JNI_SUFFIX = "jni.log";
    public static final String TOMB = "CrashSDK";
    private static final FlatComparator a = new FlatComparator();
    public static long crashTime = 0;

    public class FlatComparator implements Comparator<File> {
        public int compare(File file1, File file2) {
            return (file1.lastModified()).compareTo(file2.lastModified());
        }
    }

    public static String getLatestTombAndDelOld(Context context) {
        if (context == null) {
            Log.w("CrashCombineUtils", "getLatestTombAndDelOld, context is null");
            return null;
        }
        File parent = null;
        try {
            parent = new File(context.getApplicationInfo().dataDir + File.separator + NativeCrashHandler.FILE_PATH);
        } catch (Exception e) {
        }
        if (parent == null) {
            Log.w("CrashCombineUtils", "getLatestTombAndDelOld, parent is null");
            return null;
        }
        File[] files = parent.listFiles();
        List sortFiles = new ArrayList();
        for (File sub : files) {
            sortFiles.add(sub);
        }
        Collections.sort(sortFiles, a);
        File latestTombFile = null;
        for (int i = 0; i < sortFiles.size(); i++) {
            File file = (File) sortFiles.get(i);
            if (file != null && file.getName() != null && file.getName().startsWith(TOMB) && file.getName().endsWith(JNI_SUFFIX)) {
                latestTombFile = file;
            }
        }
        if (latestTombFile == null) {
            return null;
        }
        Log.w("CrashCombineUtils", "getLatestTombAndDelOld, latestTombFile = " + latestTombFile.getName());
        String UserTrackReport = UserTrackReport(latestTombFile.getAbsolutePath(), null);
        a(latestTombFile);
        deleteFileByPath(latestTombFile.getAbsolutePath());
        return UserTrackReport;
    }

    private static void a(File file) {
        try {
            crashTime = file.lastModified();
        } catch (Exception e) {
        }
    }

    public static long getCrashTime() {
        return crashTime;
    }

    public static String UserTrackReport(String path, String stack) {
        if (path == null) {
            return stack;
        }
        File infoFile = new File(path);
        if (!infoFile.exists() || !infoFile.isFile()) {
            return stack;
        }
        byte[] bytes = b(infoFile);
        Log.i("MiniDump", "read: " + bytes.length + " org: " + infoFile.length());
        if (bytes.length <= 0) {
            return "error: none byte, logType:" + stack;
        }
        try {
            return new String(bytes, "UTF-8");
        } catch (Throwable th) {
            return "error: byte to string, logType:" + stack;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0035 A[SYNTHETIC, Splitter:B:18:0x0035] */
    /* JADX WARNING: Removed duplicated region for block: B:25:? A[Catch:{ IOException -> 0x003b }, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static byte[] b(java.io.File r10) {
        /*
            r6 = 1048576(0x100000, float:1.469368E-39)
            r9 = 0
            long r7 = r10.length()
            int r4 = (int) r7
            if (r4 <= r6) goto L_0x000b
            r4 = r6
        L_0x000b:
            byte[] r0 = new byte[r4]
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0030 }
            r3.<init>(r10)     // Catch:{ IOException -> 0x0030 }
            r6 = 0
            int r5 = r3.read(r0, r6, r4)     // Catch:{ IOException -> 0x003b }
            r3.close()     // Catch:{ IOException -> 0x003b }
            if (r5 <= 0) goto L_0x0029
            if (r5 >= r4) goto L_0x0029
            byte[] r1 = new byte[r5]     // Catch:{ IOException -> 0x003b }
            r6 = 0
            r7 = 0
            java.lang.System.arraycopy(r0, r6, r1, r7, r5)     // Catch:{ IOException -> 0x003b }
            r0 = r1
            r2 = r3
        L_0x0028:
            return r0
        L_0x0029:
            if (r5 == r4) goto L_0x002e
            r6 = 0
            byte[] r0 = new byte[r6]     // Catch:{ IOException -> 0x003b }
        L_0x002e:
            r2 = r3
            goto L_0x0028
        L_0x0030:
            r6 = move-exception
        L_0x0031:
            byte[] r0 = new byte[r9]
            if (r2 == 0) goto L_0x0028
            r2.close()     // Catch:{ IOException -> 0x0039 }
            goto L_0x0028
        L_0x0039:
            r6 = move-exception
            goto L_0x0028
        L_0x003b:
            r6 = move-exception
            r2 = r3
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.nativecrash.CrashCombineUtils.b(java.io.File):byte[]");
    }

    public static void deleteFileByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            try {
                File file = new File(path);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            } catch (Exception e) {
            }
        }
    }
}
