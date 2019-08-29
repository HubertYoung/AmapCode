package com.ali.user.mobile.util;

public class CommentHelper {
    private static String a;

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004e A[SYNTHETIC, Splitter:B:26:0x004e] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0057 A[SYNTHETIC, Splitter:B:32:0x0057] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.lang.String r5) {
        /*
            java.lang.String r0 = a
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x000b
            java.lang.String r5 = a
            return r5
        L_0x000b:
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            long r1 = r0.length()
            int r5 = (int) r1
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x0045, all -> 0x0042 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x0045, all -> 0x0042 }
            r0 = 102400(0x19000, float:1.43493E-40)
            int r0 = java.lang.Math.min(r5, r0)     // Catch:{ Exception -> 0x0040 }
            byte[] r0 = new byte[r0]     // Catch:{ Exception -> 0x0040 }
            int r3 = r0.length     // Catch:{ Exception -> 0x0040 }
            int r5 = r5 - r3
            long r3 = (long) r5     // Catch:{ Exception -> 0x0040 }
            r2.skip(r3)     // Catch:{ Exception -> 0x0040 }
            int r5 = r2.read(r0)     // Catch:{ Exception -> 0x0040 }
            if (r5 <= 0) goto L_0x0035
            java.lang.String r5 = a(r0, r5)     // Catch:{ Exception -> 0x0040 }
            r1 = r5
        L_0x0035:
            r2.close()     // Catch:{ IOException -> 0x0039 }
            goto L_0x0051
        L_0x0039:
            r5 = move-exception
            java.lang.String r0 = "CommentHelper"
            com.ali.user.mobile.log.AliUserLog.b(r0, r5)
            goto L_0x0051
        L_0x0040:
            r5 = move-exception
            goto L_0x0047
        L_0x0042:
            r5 = move-exception
            r2 = r1
            goto L_0x0055
        L_0x0045:
            r5 = move-exception
            r2 = r1
        L_0x0047:
            java.lang.String r0 = "CommentHelper"
            com.ali.user.mobile.log.AliUserLog.b(r0, r5)     // Catch:{ all -> 0x0054 }
            if (r2 == 0) goto L_0x0051
            r2.close()     // Catch:{ IOException -> 0x0039 }
        L_0x0051:
            a = r1
            return r1
        L_0x0054:
            r5 = move-exception
        L_0x0055:
            if (r2 == 0) goto L_0x0061
            r2.close()     // Catch:{ IOException -> 0x005b }
            goto L_0x0061
        L_0x005b:
            r0 = move-exception
            java.lang.String r1 = "CommentHelper"
            com.ali.user.mobile.log.AliUserLog.b(r1, r0)
        L_0x0061:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.util.CommentHelper.a(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r0v2, types: [byte, int] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=byte, code=int, for r1v3, types: [byte, int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(byte[] r6, int r7) {
        /*
            r0 = 4
            byte[] r1 = new byte[r0]
            r1 = {80, 75, 5, 6} // fill-array
            int r2 = r6.length
            int r7 = java.lang.Math.min(r2, r7)
            int r7 = r7 + -22
        L_0x000d:
            if (r7 < 0) goto L_0x0042
            r2 = 0
            r3 = 0
        L_0x0011:
            if (r3 >= r0) goto L_0x001f
            int r4 = r7 + r3
            byte r4 = r6[r4]
            byte r5 = r1[r3]
            if (r4 == r5) goto L_0x001c
            goto L_0x0020
        L_0x001c:
            int r3 = r3 + 1
            goto L_0x0011
        L_0x001f:
            r2 = 1
        L_0x0020:
            if (r2 == 0) goto L_0x003f
            int r0 = r7 + 20
            byte r0 = r6[r0]
            int r1 = r7 + 21
            byte r1 = r6[r1]
            if (r0 < 0) goto L_0x002d
            goto L_0x002f
        L_0x002d:
            int r0 = r0 + 256
        L_0x002f:
            if (r1 < 0) goto L_0x0032
            goto L_0x0034
        L_0x0032:
            int r1 = r1 + 256
        L_0x0034:
            int r1 = r1 * 256
            int r0 = r0 + r1
            java.lang.String r1 = new java.lang.String
            int r7 = r7 + 22
            r1.<init>(r6, r7, r0)
            goto L_0x0043
        L_0x003f:
            int r7 = r7 + -1
            goto L_0x000d
        L_0x0042:
            r1 = 0
        L_0x0043:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ali.user.mobile.util.CommentHelper.a(byte[], int):java.lang.String");
    }
}
