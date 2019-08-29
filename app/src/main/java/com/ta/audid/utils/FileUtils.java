package com.ta.audid.utils;

import android.text.TextUtils;
import java.io.File;

public class FileUtils {
    public static void isDirExist(String str) {
        if (!TextUtils.isEmpty(str)) {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0045 A[SYNTHETIC, Splitter:B:25:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0056 A[SYNTHETIC, Splitter:B:32:0x0056] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFile(java.lang.String r5) {
        /*
            r0 = 0
            r1 = 0
            java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x003b }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x003b }
            r3.<init>(r5)     // Catch:{ Exception -> 0x003b }
            r2.<init>(r3)     // Catch:{ Exception -> 0x003b }
            r5 = 100
            char[] r5 = new char[r5]     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            java.lang.String r3 = ""
            r1.<init>(r3)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
        L_0x0017:
            int r3 = r2.read(r5)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r4 = -1
            if (r3 == r4) goto L_0x0022
            r1.append(r5, r0, r3)     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            goto L_0x0017
        L_0x0022:
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x0036, all -> 0x0033 }
            r2.close()     // Catch:{ Exception -> 0x002a }
            goto L_0x0032
        L_0x002a:
            r1 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.ta.audid.utils.UtdidLogger.se(r2, r1, r0)
        L_0x0032:
            return r5
        L_0x0033:
            r5 = move-exception
            r1 = r2
            goto L_0x0054
        L_0x0036:
            r5 = move-exception
            r1 = r2
            goto L_0x003c
        L_0x0039:
            r5 = move-exception
            goto L_0x0054
        L_0x003b:
            r5 = move-exception
        L_0x003c:
            java.lang.String r2 = ""
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ all -> 0x0039 }
            com.ta.audid.utils.UtdidLogger.se(r2, r5, r3)     // Catch:{ all -> 0x0039 }
            if (r1 == 0) goto L_0x0051
            r1.close()     // Catch:{ Exception -> 0x0049 }
            goto L_0x0051
        L_0x0049:
            r5 = move-exception
            java.lang.String r1 = ""
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.ta.audid.utils.UtdidLogger.se(r1, r5, r0)
        L_0x0051:
            java.lang.String r5 = ""
            return r5
        L_0x0054:
            if (r1 == 0) goto L_0x0062
            r1.close()     // Catch:{ Exception -> 0x005a }
            goto L_0x0062
        L_0x005a:
            r1 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r0 = new java.lang.Object[r0]
            com.ta.audid.utils.UtdidLogger.se(r2, r1, r0)
        L_0x0062:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.utils.FileUtils.readFile(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0043 A[SYNTHETIC, Splitter:B:29:0x0043] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0051 A[SYNTHETIC, Splitter:B:34:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0063 A[SYNTHETIC, Splitter:B:42:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0071 A[SYNTHETIC, Splitter:B:47:0x0071] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readFileLine(java.lang.String r6) {
        /*
            r0 = 0
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x0038, all -> 0x0035 }
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0033 }
            r6.<init>(r2)     // Catch:{ Exception -> 0x0033 }
            java.lang.String r0 = r6.readLine()     // Catch:{ Exception -> 0x002e, all -> 0x0029 }
            r6.close()     // Catch:{ Exception -> 0x0014 }
            goto L_0x001c
        L_0x0014:
            r6 = move-exception
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r3, r6, r4)
        L_0x001c:
            r2.close()     // Catch:{ Exception -> 0x0020 }
            goto L_0x0028
        L_0x0020:
            r6 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r2, r6, r1)
        L_0x0028:
            return r0
        L_0x0029:
            r0 = move-exception
            r5 = r0
            r0 = r6
            r6 = r5
            goto L_0x0061
        L_0x002e:
            r0 = move-exception
            r5 = r0
            r0 = r6
            r6 = r5
            goto L_0x003a
        L_0x0033:
            r6 = move-exception
            goto L_0x003a
        L_0x0035:
            r6 = move-exception
            r2 = r0
            goto L_0x0061
        L_0x0038:
            r6 = move-exception
            r2 = r0
        L_0x003a:
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r1]     // Catch:{ all -> 0x0060 }
            com.ta.audid.utils.UtdidLogger.se(r3, r6, r4)     // Catch:{ all -> 0x0060 }
            if (r0 == 0) goto L_0x004f
            r0.close()     // Catch:{ Exception -> 0x0047 }
            goto L_0x004f
        L_0x0047:
            r6 = move-exception
            java.lang.String r0 = ""
            java.lang.Object[] r3 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r0, r6, r3)
        L_0x004f:
            if (r2 == 0) goto L_0x005d
            r2.close()     // Catch:{ Exception -> 0x0055 }
            goto L_0x005d
        L_0x0055:
            r6 = move-exception
            java.lang.String r0 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r0, r6, r1)
        L_0x005d:
            java.lang.String r6 = ""
            return r6
        L_0x0060:
            r6 = move-exception
        L_0x0061:
            if (r0 == 0) goto L_0x006f
            r0.close()     // Catch:{ Exception -> 0x0067 }
            goto L_0x006f
        L_0x0067:
            r0 = move-exception
            java.lang.String r3 = ""
            java.lang.Object[] r4 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r3, r0, r4)
        L_0x006f:
            if (r2 == 0) goto L_0x007d
            r2.close()     // Catch:{ Exception -> 0x0075 }
            goto L_0x007d
        L_0x0075:
            r0 = move-exception
            java.lang.String r2 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r2, r0, r1)
        L_0x007d:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.utils.FileUtils.readFileLine(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0047 A[SYNTHETIC, Splitter:B:30:0x0047] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0055 A[SYNTHETIC, Splitter:B:35:0x0055] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0065 A[SYNTHETIC, Splitter:B:42:0x0065] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0073 A[SYNTHETIC, Splitter:B:47:0x0073] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean saveFile(java.lang.String r4, java.lang.String r5) {
        /*
            r0 = 0
            r1 = 0
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ Exception -> 0x003c, all -> 0x0039 }
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x003c, all -> 0x0039 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x003c, all -> 0x0039 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x003c, all -> 0x0039 }
            java.io.BufferedWriter r4 = new java.io.BufferedWriter     // Catch:{ Exception -> 0x0037 }
            r4.<init>(r2)     // Catch:{ Exception -> 0x0037 }
            r4.write(r5)     // Catch:{ Exception -> 0x0034, all -> 0x0031 }
            r4.flush()     // Catch:{ Exception -> 0x0034, all -> 0x0031 }
            r4.close()     // Catch:{ Exception -> 0x001b }
            goto L_0x0023
        L_0x001b:
            r4 = move-exception
            java.lang.String r5 = ""
            java.lang.Object[] r0 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r5, r4, r0)
        L_0x0023:
            r2.close()     // Catch:{ Exception -> 0x0027 }
            goto L_0x002f
        L_0x0027:
            r4 = move-exception
            java.lang.String r5 = ""
            java.lang.Object[] r0 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r5, r4, r0)
        L_0x002f:
            r4 = 1
            return r4
        L_0x0031:
            r5 = move-exception
            r0 = r4
            goto L_0x0063
        L_0x0034:
            r5 = move-exception
            r0 = r4
            goto L_0x003e
        L_0x0037:
            r5 = move-exception
            goto L_0x003e
        L_0x0039:
            r5 = move-exception
            r2 = r0
            goto L_0x0063
        L_0x003c:
            r5 = move-exception
            r2 = r0
        L_0x003e:
            java.lang.String r4 = ""
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x0062 }
            com.ta.audid.utils.UtdidLogger.se(r4, r5, r3)     // Catch:{ all -> 0x0062 }
            if (r0 == 0) goto L_0x0053
            r0.close()     // Catch:{ Exception -> 0x004b }
            goto L_0x0053
        L_0x004b:
            r4 = move-exception
            java.lang.String r5 = ""
            java.lang.Object[] r0 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r5, r4, r0)
        L_0x0053:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ Exception -> 0x0059 }
            goto L_0x0061
        L_0x0059:
            r4 = move-exception
            java.lang.String r5 = ""
            java.lang.Object[] r0 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r5, r4, r0)
        L_0x0061:
            return r1
        L_0x0062:
            r5 = move-exception
        L_0x0063:
            if (r0 == 0) goto L_0x0071
            r0.close()     // Catch:{ Exception -> 0x0069 }
            goto L_0x0071
        L_0x0069:
            r4 = move-exception
            java.lang.String r0 = ""
            java.lang.Object[] r3 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r0, r4, r3)
        L_0x0071:
            if (r2 == 0) goto L_0x007f
            r2.close()     // Catch:{ Exception -> 0x0077 }
            goto L_0x007f
        L_0x0077:
            r4 = move-exception
            java.lang.String r0 = ""
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.ta.audid.utils.UtdidLogger.se(r0, r4, r1)
        L_0x007f:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.audid.utils.FileUtils.saveFile(java.lang.String, java.lang.String):boolean");
    }
}
