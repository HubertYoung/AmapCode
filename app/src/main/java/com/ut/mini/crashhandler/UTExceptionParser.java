package com.ut.mini.crashhandler;

public class UTExceptionParser {

    public static class UTExceptionItem {
        String mCrashDetail = null;
        boolean mCrashedByUT = false;
        String mExpName = null;
        String mMd5 = null;

        public String getExpName() {
            return this.mExpName;
        }

        public void setExpName(String str) {
            this.mExpName = str;
        }

        public String getMd5() {
            return this.mMd5;
        }

        public void setMd5(String str) {
            this.mMd5 = str;
        }

        public String getCrashDetail() {
            return this.mCrashDetail;
        }

        public void setCrashDetail(String str) {
            this.mCrashDetail = str;
        }

        public boolean isCrashedByUT() {
            return this.mCrashedByUT;
        }

        public void setmCrashedByUT(boolean z) {
            this.mCrashedByUT = z;
        }
    }

    /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0042 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.ut.mini.crashhandler.UTExceptionParser.UTExceptionItem parse(java.lang.Throwable r6) {
        /*
            if (r6 == 0) goto L_0x00a9
            com.ut.mini.crashhandler.UTExceptionParser$UTExceptionItem r0 = new com.ut.mini.crashhandler.UTExceptionParser$UTExceptionItem
            r0.<init>()
            java.lang.Throwable r1 = r6.getCause()
            if (r1 != 0) goto L_0x000e
            r1 = r6
        L_0x000e:
            if (r1 == 0) goto L_0x00a8
            java.lang.StackTraceElement[] r2 = r1.getStackTrace()
            int r3 = r2.length
            if (r3 <= 0) goto L_0x00a8
            r3 = 0
            r2 = r2[r3]
            if (r2 == 0) goto L_0x00a8
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = ""
            java.io.StringWriter r4 = new java.io.StringWriter
            r4.<init>()
            java.io.PrintWriter r5 = new java.io.PrintWriter
            r5.<init>(r4)
            r6.printStackTrace(r5)     // Catch:{ Exception -> 0x0042, all -> 0x003a }
            java.lang.String r6 = r4.toString()     // Catch:{ Exception -> 0x0042, all -> 0x003a }
            r5.close()     // Catch:{ Exception -> 0x0049 }
            r4.close()     // Catch:{ Exception -> 0x0049 }
            goto L_0x0049
        L_0x003a:
            r6 = move-exception
            r5.close()     // Catch:{ Exception -> 0x0041 }
            r4.close()     // Catch:{ Exception -> 0x0041 }
        L_0x0041:
            throw r6
        L_0x0042:
            r5.close()     // Catch:{ Exception -> 0x0048 }
            r4.close()     // Catch:{ Exception -> 0x0048 }
        L_0x0048:
            r6 = r2
        L_0x0049:
            java.lang.String r2 = "}:"
            int r2 = r1.indexOf(r2)
            if (r2 <= 0) goto L_0x005c
            int r2 = r2 + 2
            java.lang.String r1 = r1.substring(r2)
            java.lang.String r1 = r1.trim()
            goto L_0x0068
        L_0x005c:
            java.lang.String r2 = ":"
            int r2 = r1.indexOf(r2)
            if (r2 <= 0) goto L_0x0068
            java.lang.String r1 = r1.substring(r3, r2)
        L_0x0068:
            r0.setExpName(r1)
            boolean r1 = android.text.TextUtils.isEmpty(r6)
            if (r1 != 0) goto L_0x0079
            java.lang.String r1 = "\n"
            java.lang.String r2 = "++"
            java.lang.String r6 = r6.replaceAll(r1, r2)
        L_0x0079:
            r0.setCrashDetail(r6)
            byte[] r1 = r6.getBytes()
            java.lang.String r1 = com.alibaba.analytics.utils.MD5Utils.getMd5Hex(r1)
            r0.setMd5(r1)
            java.lang.String r1 = "com.taobao.statistic"
            boolean r1 = r6.contains(r1)
            if (r1 != 0) goto L_0x00a4
            java.lang.String r1 = "com.ut"
            boolean r1 = r6.contains(r1)
            if (r1 != 0) goto L_0x00a4
            java.lang.String r1 = "org.usertrack"
            boolean r6 = r6.contains(r1)
            if (r6 == 0) goto L_0x00a0
            goto L_0x00a4
        L_0x00a0:
            r0.setmCrashedByUT(r3)
            goto L_0x00a8
        L_0x00a4:
            r6 = 1
            r0.setmCrashedByUT(r6)
        L_0x00a8:
            return r0
        L_0x00a9:
            r6 = 0
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.crashhandler.UTExceptionParser.parse(java.lang.Throwable):com.ut.mini.crashhandler.UTExceptionParser$UTExceptionItem");
    }
}
