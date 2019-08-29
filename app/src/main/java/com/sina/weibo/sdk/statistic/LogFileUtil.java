package com.sina.weibo.sdk.statistic;

import android.os.Environment;
import android.text.TextUtils;
import com.sina.weibo.sdk.utils.MD5;
import java.io.File;

class LogFileUtil {
    public static final String ANALYTICS_FILE_NAME = "app_logs";
    private static final String ANALYTICS_FILE_SUFFIX = ".txt";
    private static final String SDCARD_WEIBO_ANALYTICS_DIR = "/sina/weibo/.applogs/";

    LogFileUtil() {
    }

    public static String getAppLogs(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return readStringFromFile(str);
    }

    public static String getAppLogPath(String str) {
        String str2 = "";
        if (LogReport.getPackageName() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(MD5.hexdigest(LogReport.getPackageName()));
            sb.append("/");
            str2 = sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getSDPath());
        sb2.append(SDCARD_WEIBO_ANALYTICS_DIR);
        sb2.append(str2);
        sb2.append(str);
        sb2.append(ANALYTICS_FILE_SUFFIX);
        return sb2.toString();
    }

    private static String getSDPath() {
        File externalStorageDirectory = Environment.getExternalStorageState().equals("mounted") ? Environment.getExternalStorageDirectory() : null;
        if (externalStorageDirectory != null) {
            return externalStorageDirectory.toString();
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0050, code lost:
        if (r4 != null) goto L_0x0052;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x005a, code lost:
        if (r4 != null) goto L_0x0052;
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0064 A[SYNTHETIC, Splitter:B:38:0x0064] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:31:0x0057=Splitter:B:31:0x0057, B:25:0x004d=Splitter:B:25:0x004d} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String readStringFromFile(java.lang.String r4) {
        /*
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L_0x0009
            java.lang.String r4 = ""
            return r4
        L_0x0009:
            java.io.File r0 = new java.io.File
            r0.<init>(r4)
            boolean r4 = r0.isFile()
            if (r4 == 0) goto L_0x0068
            boolean r4 = r0.exists()
            if (r4 != 0) goto L_0x001b
            goto L_0x0068
        L_0x001b:
            r4 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            long r2 = r0.length()
            int r2 = (int) r2
            r1.<init>(r2)
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ IOException -> 0x0056, OutOfMemoryError -> 0x004c }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ IOException -> 0x0056, OutOfMemoryError -> 0x004c }
            r3.<init>(r0)     // Catch:{ IOException -> 0x0056, OutOfMemoryError -> 0x004c }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0056, OutOfMemoryError -> 0x004c }
        L_0x0030:
            java.lang.String r4 = r2.readLine()     // Catch:{ IOException -> 0x0046, OutOfMemoryError -> 0x0042, all -> 0x003e }
            if (r4 == 0) goto L_0x003a
            r1.append(r4)     // Catch:{ IOException -> 0x0046, OutOfMemoryError -> 0x0042, all -> 0x003e }
            goto L_0x0030
        L_0x003a:
            r2.close()     // Catch:{ IOException -> 0x005d }
            goto L_0x005d
        L_0x003e:
            r4 = move-exception
            r0 = r4
            r4 = r2
            goto L_0x0062
        L_0x0042:
            r4 = move-exception
            r0 = r4
            r4 = r2
            goto L_0x004d
        L_0x0046:
            r4 = move-exception
            r0 = r4
            r4 = r2
            goto L_0x0057
        L_0x004a:
            r0 = move-exception
            goto L_0x0062
        L_0x004c:
            r0 = move-exception
        L_0x004d:
            r0.printStackTrace()     // Catch:{ all -> 0x004a }
            if (r4 == 0) goto L_0x005d
        L_0x0052:
            r4.close()     // Catch:{ IOException -> 0x005d }
            goto L_0x005d
        L_0x0056:
            r0 = move-exception
        L_0x0057:
            r0.printStackTrace()     // Catch:{ all -> 0x004a }
            if (r4 == 0) goto L_0x005d
            goto L_0x0052
        L_0x005d:
            java.lang.String r4 = r1.toString()
            return r4
        L_0x0062:
            if (r4 == 0) goto L_0x0067
            r4.close()     // Catch:{ IOException -> 0x0067 }
        L_0x0067:
            throw r0
        L_0x0068:
            java.lang.String r4 = ""
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.statistic.LogFileUtil.readStringFromFile(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00c8, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00af A[SYNTHETIC, Splitter:B:53:0x00af] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00ba A[SYNTHETIC, Splitter:B:61:0x00ba] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00c5 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void writeToFile(java.lang.String r8, java.lang.String r9, boolean r10) {
        /*
            java.lang.Class<com.sina.weibo.sdk.statistic.LogFileUtil> r0 = com.sina.weibo.sdk.statistic.LogFileUtil.class
            monitor-enter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r8)     // Catch:{ all -> 0x00c9 }
            if (r1 == 0) goto L_0x000b
            monitor-exit(r0)
            return
        L_0x000b:
            java.lang.String r1 = "WBAgent"
            java.lang.String r2 = "filePath:"
            java.lang.String r3 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x00c9 }
            java.lang.String r2 = r2.concat(r3)     // Catch:{ all -> 0x00c9 }
            com.sina.weibo.sdk.utils.LogUtil.i(r1, r2)     // Catch:{ all -> 0x00c9 }
            if (r9 == 0) goto L_0x00c7
            int r1 = r9.length()     // Catch:{ all -> 0x00c9 }
            if (r1 != 0) goto L_0x0024
            goto L_0x00c7
        L_0x0024:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c9 }
            r1.<init>(r9)     // Catch:{ all -> 0x00c9 }
            r9 = 0
            char r2 = r1.charAt(r9)     // Catch:{ all -> 0x00c9 }
            r3 = 91
            r4 = 1
            if (r2 != r3) goto L_0x0038
            java.lang.String r2 = ""
            r1.replace(r9, r4, r2)     // Catch:{ all -> 0x00c9 }
        L_0x0038:
            int r2 = r1.length()     // Catch:{ all -> 0x00c9 }
            int r2 = r2 - r4
            char r2 = r1.charAt(r2)     // Catch:{ all -> 0x00c9 }
            r3 = 44
            if (r2 == r3) goto L_0x0053
            int r2 = r1.length()     // Catch:{ all -> 0x00c9 }
            int r2 = r2 - r4
            int r3 = r1.length()     // Catch:{ all -> 0x00c9 }
            java.lang.String r4 = ","
            r1.replace(r2, r3, r4)     // Catch:{ all -> 0x00c9 }
        L_0x0053:
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x00c9 }
            r2.<init>(r8)     // Catch:{ all -> 0x00c9 }
            r8 = 0
            java.io.File r3 = r2.getParentFile()     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            boolean r4 = r3.exists()     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            if (r4 != 0) goto L_0x0066
            r3.mkdirs()     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
        L_0x0066:
            boolean r3 = r2.exists()     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            if (r3 != 0) goto L_0x0070
            r2.createNewFile()     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            goto L_0x008c
        L_0x0070:
            long r3 = r2.lastModified()     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            r5 = 0
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x008c
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            long r5 = r2.lastModified()     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            r7 = 0
            long r3 = r3 - r5
            r5 = 86400000(0x5265c00, double:4.2687272E-316)
            int r3 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r3 <= 0) goto L_0x008c
            goto L_0x008d
        L_0x008c:
            r9 = r10
        L_0x008d:
            java.io.FileWriter r10 = new java.io.FileWriter     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            r10.<init>(r2, r9)     // Catch:{ IOException -> 0x00b8, all -> 0x00ac }
            java.lang.String r8 = r1.toString()     // Catch:{ IOException -> 0x00aa, all -> 0x00a7 }
            r10.write(r8)     // Catch:{ IOException -> 0x00aa, all -> 0x00a7 }
            r10.flush()     // Catch:{ IOException -> 0x00aa, all -> 0x00a7 }
            r10.close()     // Catch:{ IOException -> 0x00a1 }
            monitor-exit(r0)
            return
        L_0x00a1:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x00c9 }
            monitor-exit(r0)
            return
        L_0x00a7:
            r9 = move-exception
            r8 = r10
            goto L_0x00ad
        L_0x00aa:
            r8 = r10
            goto L_0x00b8
        L_0x00ac:
            r9 = move-exception
        L_0x00ad:
            if (r8 == 0) goto L_0x00b7
            r8.close()     // Catch:{ IOException -> 0x00b3 }
            goto L_0x00b7
        L_0x00b3:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x00c9 }
        L_0x00b7:
            throw r9     // Catch:{ all -> 0x00c9 }
        L_0x00b8:
            if (r8 == 0) goto L_0x00c5
            r8.close()     // Catch:{ IOException -> 0x00bf }
            monitor-exit(r0)
            return
        L_0x00bf:
            r8 = move-exception
            r8.printStackTrace()     // Catch:{ all -> 0x00c9 }
            monitor-exit(r0)
            return
        L_0x00c5:
            monitor-exit(r0)
            return
        L_0x00c7:
            monitor-exit(r0)
            return
        L_0x00c9:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sina.weibo.sdk.statistic.LogFileUtil.writeToFile(java.lang.String, java.lang.String, boolean):void");
    }

    public static boolean delete(String str) {
        File file = new File(str);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        file.delete();
        return true;
    }
}
