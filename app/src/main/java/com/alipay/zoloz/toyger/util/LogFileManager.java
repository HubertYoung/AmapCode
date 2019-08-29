package com.alipay.zoloz.toyger.util;

import java.io.File;
import java.io.FilenameFilter;

public final class LogFileManager {
    private static final String FILE_NAME_FORMAT = "%s_%04d_%s.bin";
    private static int counter = 0;

    static class a implements FilenameFilter {
        a() {
        }

        public final boolean accept(File file, String str) {
            return str.endsWith(".bin");
        }
    }

    private LogFileManager() {
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean saveFile(java.lang.String r12, byte[] r13) {
        /*
            r2 = 1
            r0 = 0
            java.util.Locale r1 = java.util.Locale.US     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r3 = "%s_%04d_%s.bin"
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x00bc }
            r5 = 0
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r6 = com.alipay.mobile.security.bio.utils.DateUtil.getDateFormat(r6)     // Catch:{ Throwable -> 0x00bc }
            r4[r5] = r6     // Catch:{ Throwable -> 0x00bc }
            r5 = 1
            int r6 = counter     // Catch:{ Throwable -> 0x00bc }
            int r6 = r6 / 2
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x00bc }
            r4[r5] = r6     // Catch:{ Throwable -> 0x00bc }
            r5 = 2
            r4[r5] = r12     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r3 = java.lang.String.format(r1, r3, r4)     // Catch:{ Throwable -> 0x00bc }
            java.io.File r4 = new java.io.File     // Catch:{ Throwable -> 0x00bc }
            java.io.File r1 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r5 = "Download"
            r4.<init>(r1, r5)     // Catch:{ Throwable -> 0x00bc }
            boolean r1 = r4.exists()     // Catch:{ Throwable -> 0x00bc }
            if (r1 == 0) goto L_0x00b7
            int r1 = counter     // Catch:{ Throwable -> 0x00bc }
            if (r1 != 0) goto L_0x00c1
            com.alipay.zoloz.toyger.util.LogFileManager$a r1 = new com.alipay.zoloz.toyger.util.LogFileManager$a     // Catch:{ Throwable -> 0x00b1 }
            r1.<init>()     // Catch:{ Throwable -> 0x00b1 }
            java.io.File[] r5 = r4.listFiles(r1)     // Catch:{ Throwable -> 0x00b1 }
            if (r5 == 0) goto L_0x0082
            int r1 = r5.length     // Catch:{ Throwable -> 0x00b1 }
            if (r1 <= 0) goto L_0x0082
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00b1 }
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.DAYS     // Catch:{ Throwable -> 0x00b1 }
            r8 = 3
            long r8 = r1.toMillis(r8)     // Catch:{ Throwable -> 0x00b1 }
            long r6 = r6 - r8
            int r8 = r5.length     // Catch:{ Throwable -> 0x00b1 }
            r1 = r0
        L_0x0058:
            if (r1 >= r8) goto L_0x0082
            r9 = r5[r1]     // Catch:{ Throwable -> 0x00b1 }
            long r10 = r9.lastModified()     // Catch:{ Throwable -> 0x00b1 }
            int r10 = (r10 > r6 ? 1 : (r10 == r6 ? 0 : -1))
            if (r10 >= 0) goto L_0x007f
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r11 = "Delete File : "
            r10.<init>(r11)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.StringBuilder r10 = r10.append(r9)     // Catch:{ Throwable -> 0x00b1 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x00b1 }
            com.alipay.mobile.security.bio.utils.BioLog.i(r10)     // Catch:{ Throwable -> 0x00b1 }
            boolean r10 = r9.delete()     // Catch:{ Throwable -> 0x00b1 }
            if (r10 == 0) goto L_0x007f
            r9.deleteOnExit()     // Catch:{ Throwable -> 0x00b1 }
        L_0x007f:
            int r1 = r1 + 1
            goto L_0x0058
        L_0x0082:
            r1 = r2
        L_0x0083:
            if (r1 == 0) goto L_0x00b0
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x00bc }
            r1.<init>(r4, r3)     // Catch:{ Throwable -> 0x00bc }
            boolean r0 = com.alipay.mobile.security.bio.utils.FileUtil.save(r1, r13)     // Catch:{ Throwable -> 0x00bc }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r3 = "Save file : "
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00bc }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r2 = ", bRet="
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00bc }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ Throwable -> 0x00bc }
            java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x00bc }
            com.alipay.mobile.security.bio.utils.BioLog.d(r1)     // Catch:{ Throwable -> 0x00bc }
            int r1 = counter     // Catch:{ Throwable -> 0x00bc }
            int r1 = r1 + 1
            counter = r1     // Catch:{ Throwable -> 0x00bc }
        L_0x00b0:
            return r0
        L_0x00b1:
            r1 = move-exception
            com.alipay.mobile.security.bio.utils.BioLog.w(r1)     // Catch:{ Throwable -> 0x00bc }
            r1 = r2
            goto L_0x0083
        L_0x00b7:
            boolean r1 = r4.mkdirs()     // Catch:{ Throwable -> 0x00bc }
            goto L_0x0083
        L_0x00bc:
            r1 = move-exception
            com.alipay.mobile.security.bio.utils.BioLog.w(r1)
            goto L_0x00b0
        L_0x00c1:
            r1 = r2
            goto L_0x0083
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.zoloz.toyger.util.LogFileManager.saveFile(java.lang.String, byte[]):boolean");
    }
}
