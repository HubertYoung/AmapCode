package com.alipay.mobile.nebulaappcenter.dbhelp;

/* compiled from: H5DBCompatHelper */
final class a {
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0074, code lost:
        com.alipay.mobile.nebula.util.H5Log.d("H5DBCompatHelper", "add for 12 -> 13");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x007f, code lost:
        if (com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.f() == false) goto L_0x00c8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0081, code lost:
        com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, "ALTER table nebula_app_install add column user_id TEXT default " + com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate.a() + ";");
        com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, "ALTER table nebula_app_info_table add column user_id TEXT default " + com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate.a() + ";");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x00b9, code lost:
        com.alipay.mobile.nebula.util.H5Log.d("H5DBCompatHelper", "add for 13 -> 14");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        com.j256.ormlite.table.TableUtils.createTable(r7, com.alipay.mobile.nebulaappcenter.dbbean.H5UrlAppMapBean.class);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x00c8, code lost:
        com.alipay.mobile.nebula.util.H5Log.d("H5DBCompatHelper", "not login, just clear table and recreate");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x00d1, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00d2, code lost:
        com.alipay.mobile.nebula.util.H5Log.e((java.lang.String) "H5DBCompatHelper", "createTable error: " + r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0050, code lost:
        com.alipay.mobile.nebula.util.H5Log.d("H5DBCompatHelper", "add for 9 -> 10");
        com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, "ALTER table nebula_app_info_table add column nbl_id TEXT;");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x005c, code lost:
        com.alipay.mobile.nebula.util.H5Log.d("H5DBCompatHelper", "add for 10 -> 11");
        com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, "ALTER table nebula_app_info_table add column slogan TEXT;");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0068, code lost:
        com.alipay.mobile.nebula.util.H5Log.d("H5DBCompatHelper", "add for 11 -> 12");
        com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, "ALTER table nebula_app_info_table add column unavailable_reason TEXT;");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a(com.alibaba.sqlcrypto.sqlite.SQLiteDatabase r6, com.j256.ormlite.support.ConnectionSource r7, int r8, int r9) {
        /*
            r2 = 0
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "onVersionChange, oldVersion:"
            r4.<init>(r5)
            java.lang.StringBuilder r4 = r4.append(r8)
            java.lang.String r5 = ",newVersion:"
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r9)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            java.lang.Class<com.alipay.mobile.nebula.provider.H5ConfigProvider> r3 = com.alipay.mobile.nebula.provider.H5ConfigProvider.class
            java.lang.String r3 = r3.getName()
            java.lang.Object r1 = com.alipay.mobile.nebula.util.H5Utils.getProvider(r3)
            com.alipay.mobile.nebula.provider.H5ConfigProvider r1 = (com.alipay.mobile.nebula.provider.H5ConfigProvider) r1
            java.lang.String r3 = "NO"
            java.lang.String r4 = "h5_upgrade_compat_db"
            java.lang.String r4 = r1.getConfig(r4)
            boolean r3 = r3.equalsIgnoreCase(r4)
            if (r3 == 0) goto L_0x003a
        L_0x0039:
            return r2
        L_0x003a:
            if (r9 <= r8) goto L_0x0039
            r3 = 8
            if (r8 < r3) goto L_0x0039
            switch(r8) {
                case 8: goto L_0x0044;
                case 9: goto L_0x0050;
                case 10: goto L_0x005c;
                case 11: goto L_0x0068;
                case 12: goto L_0x0074;
                case 13: goto L_0x00b9;
                default: goto L_0x0043;
            }
        L_0x0043:
            goto L_0x0039
        L_0x0044:
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.String r4 = "add for 8 -> 9"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            java.lang.String r3 = "ALTER table nebula_app_install add column installPath TEXT;"
            com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, r3)
        L_0x0050:
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.String r4 = "add for 9 -> 10"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            java.lang.String r3 = "ALTER table nebula_app_info_table add column nbl_id TEXT;"
            com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, r3)
        L_0x005c:
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.String r4 = "add for 10 -> 11"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            java.lang.String r3 = "ALTER table nebula_app_info_table add column slogan TEXT;"
            com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, r3)
        L_0x0068:
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.String r4 = "add for 11 -> 12"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            java.lang.String r3 = "ALTER table nebula_app_info_table add column unavailable_reason TEXT;"
            com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, r3)
        L_0x0074:
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.String r4 = "add for 12 -> 13"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            boolean r3 = com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.f()
            if (r3 == 0) goto L_0x00c8
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "ALTER table nebula_app_install add column user_id TEXT default "
            r3.<init>(r4)
            java.lang.String r4 = com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate.a()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = ";"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, r3)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "ALTER table nebula_app_info_table add column user_id TEXT default "
            r3.<init>(r4)
            java.lang.String r4 = com.alipay.mobile.nebulaappcenter.dbapi.H5DaoTemplate.a()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = ";"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.nebulaappcenter.dbhelp.H5DBUtil.a(r6, r3)
        L_0x00b9:
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.String r4 = "add for 13 -> 14"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            java.lang.Class<com.alipay.mobile.nebulaappcenter.dbbean.H5UrlAppMapBean> r3 = com.alipay.mobile.nebulaappcenter.dbbean.H5UrlAppMapBean.class
            com.j256.ormlite.table.TableUtils.createTable(r7, r3)     // Catch:{ SQLException -> 0x00d1 }
            r2 = 1
            goto L_0x0039
        L_0x00c8:
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.String r4 = "not login, just clear table and recreate"
            com.alipay.mobile.nebula.util.H5Log.d(r3, r4)
            goto L_0x0039
        L_0x00d1:
            r0 = move-exception
            java.lang.String r3 = "H5DBCompatHelper"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "createTable error: "
            r4.<init>(r5)
            java.lang.StringBuilder r4 = r4.append(r0)
            java.lang.String r4 = r4.toString()
            com.alipay.mobile.nebula.util.H5Log.e(r3, r4)
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulaappcenter.dbhelp.a.a(com.alibaba.sqlcrypto.sqlite.SQLiteDatabase, com.j256.ormlite.support.ConnectionSource, int, int):boolean");
    }
}
