package com.alipay.sdk.tid;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.sdk.encrypt.b;
import java.util.Random;
import org.json.JSONObject;

public final class c {
    public static final String a = "alipay_tid_storage";
    public static final String b = "tidinfo";
    public static final String c = "tidflag";
    public static final String d = "tid";
    public static final String e = "client_key";
    public static final String f = "timestamp";
    public static final String g = "vimei";
    public static final String h = "vimsi";
    public static final String i = "com.alipay.android.app.CASHIER_TID_CHANGED";
    public static final String o = "virtualImeiAndImsi";
    public static final String p = "virtual_imei";
    public static final String q = "virtual_imsi";
    /* access modifiers changed from: private */
    public static Context r;
    private static c s;
    public String j;
    public String k;
    public long l;
    public String m;
    public String n;
    private boolean t = false;

    public static class a {
        private static String a() {
            return "!@#23457";
        }

        private static boolean a(String str, String str2) {
            if (c.r == null) {
                return false;
            }
            return c.r.getSharedPreferences(str, 0).contains(str2);
        }

        private static void b(String str, String str2) {
            if (c.r != null) {
                c.r.getSharedPreferences(str, 0).edit().remove(str2).apply();
            }
        }

        private static String c(String str, String str2) {
            return a(str, str2, true);
        }

        public static String a(String str, String str2, boolean z) {
            if (c.r == null) {
                return null;
            }
            String string = c.r.getSharedPreferences(str, 0).getString(str2, null);
            if (!TextUtils.isEmpty(string) && z) {
                String b = b();
                String a = b.a(2, string, b);
                if (TextUtils.isEmpty(a)) {
                    a = b.a(2, string, "!@#23457");
                    if (!TextUtils.isEmpty(a)) {
                        a(str, str2, a);
                    }
                }
                if (TextUtils.isEmpty(a)) {
                    String.format("LocalPreference::getLocalPreferences failed %s，%s", new Object[]{string, b});
                }
                string = a;
            }
            return string;
        }

        private static void b(String str, String str2, String str3) {
            a(str, str2, str3);
        }

        public static void a(String str, String str2, String str3) {
            if (c.r != null) {
                SharedPreferences sharedPreferences = c.r.getSharedPreferences(str, 0);
                String b = b();
                String a = b.a(1, str3, b);
                if (TextUtils.isEmpty(a)) {
                    String.format("LocalPreference::putLocalPreferences failed %s，%s", new Object[]{str3, b});
                }
                sharedPreferences.edit().putString(str2, a).apply();
            }
        }

        private static String b() {
            String str = "";
            try {
                str = c.r.getApplicationContext().getPackageName();
            } catch (Throwable unused) {
            }
            if (TextUtils.isEmpty(str)) {
                str = "unknow";
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("00000000");
            return sb.toString().substring(0, 8);
        }
    }

    private static void n() {
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00bb */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00d3 A[Catch:{ Exception -> 0x011c }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0118  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0156  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized com.alipay.sdk.tid.c a(android.content.Context r11) {
        /*
            java.lang.Class<com.alipay.sdk.tid.c> r0 = com.alipay.sdk.tid.c.class
            monitor-enter(r0)
            com.alipay.sdk.tid.c r1 = s     // Catch:{ all -> 0x0168 }
            if (r1 != 0) goto L_0x000e
            com.alipay.sdk.tid.c r1 = new com.alipay.sdk.tid.c     // Catch:{ all -> 0x0168 }
            r1.<init>()     // Catch:{ all -> 0x0168 }
            s = r1     // Catch:{ all -> 0x0168 }
        L_0x000e:
            android.content.Context r1 = r     // Catch:{ all -> 0x0168 }
            if (r1 != 0) goto L_0x0164
            com.alipay.sdk.tid.c r1 = s     // Catch:{ all -> 0x0168 }
            if (r11 == 0) goto L_0x001c
            android.content.Context r11 = r11.getApplicationContext()     // Catch:{ all -> 0x0168 }
            r = r11     // Catch:{ all -> 0x0168 }
        L_0x001c:
            boolean r11 = r1.t     // Catch:{ all -> 0x0168 }
            if (r11 != 0) goto L_0x0164
            r11 = 1
            r1.t = r11     // Catch:{ all -> 0x0168 }
            java.lang.String r2 = "alipay_tid_storage"
            java.lang.String r3 = "tidflag"
            android.content.Context r4 = r     // Catch:{ Exception -> 0x00bb }
            r5 = 0
            if (r4 != 0) goto L_0x002f
            r2 = 0
            goto L_0x0039
        L_0x002f:
            android.content.Context r4 = r     // Catch:{ Exception -> 0x00bb }
            android.content.SharedPreferences r2 = r4.getSharedPreferences(r2, r5)     // Catch:{ Exception -> 0x00bb }
            boolean r2 = r2.contains(r3)     // Catch:{ Exception -> 0x00bb }
        L_0x0039:
            if (r2 != 0) goto L_0x00bb
            java.lang.String r2 = "alipay_tid_storage"
            java.lang.String r3 = "tidflag"
            java.lang.String r4 = "true"
            com.alipay.sdk.tid.c.a.a(r2, r3, r4)     // Catch:{ Exception -> 0x00bb }
            com.alipay.sdk.tid.b r2 = com.alipay.sdk.tid.b.a()     // Catch:{ Exception -> 0x00bb }
            java.lang.String r3 = r2.a     // Catch:{ Exception -> 0x00bb }
            java.lang.String r2 = r2.b     // Catch:{ Exception -> 0x00bb }
            r6 = 0
            java.lang.Long r4 = java.lang.Long.valueOf(r6)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r6 = "virtualImeiAndImsi"
            java.lang.String r7 = "virtual_imei"
            java.lang.String r6 = com.alipay.sdk.tid.c.a.a(r6, r7, r5)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r7 = "virtualImeiAndImsi"
            java.lang.String r8 = "virtual_imsi"
            java.lang.String r7 = com.alipay.sdk.tid.c.a.a(r7, r8, r5)     // Catch:{ Exception -> 0x00bb }
            java.lang.String r8 = "TidCompatible::SyncTid02: %s，%s，%s，%s，%s"
            r9 = 5
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ Exception -> 0x00bb }
            r9[r5] = r3     // Catch:{ Exception -> 0x00bb }
            r9[r11] = r2     // Catch:{ Exception -> 0x00bb }
            r5 = 2
            r9[r5] = r4     // Catch:{ Exception -> 0x00bb }
            r5 = 3
            r9[r5] = r6     // Catch:{ Exception -> 0x00bb }
            r5 = 4
            r9[r5] = r7     // Catch:{ Exception -> 0x00bb }
            java.lang.String.format(r8, r9)     // Catch:{ Exception -> 0x00bb }
            boolean r5 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x00bb }
            if (r5 != 0) goto L_0x008b
            int r5 = r3.length()     // Catch:{ Exception -> 0x00bb }
            r8 = 64
            if (r5 != r8) goto L_0x00bb
        L_0x008b:
            boolean r5 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Exception -> 0x00bb }
            if (r5 == 0) goto L_0x009b
            boolean r5 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x00bb }
            if (r5 != 0) goto L_0x009b
            java.lang.String r6 = i()     // Catch:{ Exception -> 0x00bb }
        L_0x009b:
            boolean r5 = a(r3, r2, r6, r7)     // Catch:{ Exception -> 0x00bb }
            if (r5 != 0) goto L_0x00bb
            r1.j = r3     // Catch:{ Exception -> 0x00bb }
            r1.k = r2     // Catch:{ Exception -> 0x00bb }
            r1.m = r6     // Catch:{ Exception -> 0x00bb }
            r1.n = r7     // Catch:{ Exception -> 0x00bb }
            if (r4 != 0) goto L_0x00b2
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00bb }
            r1.l = r2     // Catch:{ Exception -> 0x00bb }
            goto L_0x00b8
        L_0x00b2:
            long r2 = r4.longValue()     // Catch:{ Exception -> 0x00bb }
            r1.l = r2     // Catch:{ Exception -> 0x00bb }
        L_0x00b8:
            r1.o()     // Catch:{ Exception -> 0x00bb }
        L_0x00bb:
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0168 }
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch:{ all -> 0x0168 }
            r3 = 0
            java.lang.String r4 = "alipay_tid_storage"
            java.lang.String r5 = "tidinfo"
            java.lang.String r11 = com.alipay.sdk.tid.c.a.a(r4, r5, r11)     // Catch:{ Exception -> 0x011c }
            boolean r4 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Exception -> 0x011c }
            if (r4 != 0) goto L_0x0118
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ Exception -> 0x011c }
            r4.<init>(r11)     // Catch:{ Exception -> 0x011c }
            java.lang.String r11 = "tid"
            java.lang.String r5 = ""
            java.lang.String r11 = r4.optString(r11, r5)     // Catch:{ Exception -> 0x011c }
            java.lang.String r5 = "client_key"
            java.lang.String r6 = ""
            java.lang.String r5 = r4.optString(r5, r6)     // Catch:{ Exception -> 0x0116 }
            java.lang.String r6 = "timestamp"
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0114 }
            long r6 = r4.optLong(r6, r7)     // Catch:{ Exception -> 0x0114 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ Exception -> 0x0114 }
            java.lang.String r2 = "vimei"
            java.lang.String r7 = ""
            java.lang.String r2 = r4.optString(r2, r7)     // Catch:{ Exception -> 0x0111 }
            java.lang.String r7 = "vimsi"
            java.lang.String r8 = ""
            java.lang.String r4 = r4.optString(r7, r8)     // Catch:{ Exception -> 0x010f }
            r3 = r5
            r5 = r4
            r4 = r2
            r2 = r6
            goto L_0x0122
        L_0x010f:
            r4 = r2
            goto L_0x0112
        L_0x0111:
            r4 = r3
        L_0x0112:
            r2 = r6
            goto L_0x011f
        L_0x0114:
            r4 = r3
            goto L_0x011f
        L_0x0116:
            r4 = r3
            goto L_0x011e
        L_0x0118:
            r11 = r3
            r4 = r11
            r5 = r4
            goto L_0x0122
        L_0x011c:
            r11 = r3
            r4 = r11
        L_0x011e:
            r5 = r4
        L_0x011f:
            r10 = r5
            r5 = r3
            r3 = r10
        L_0x0122:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0168 }
            java.lang.String r7 = "TidStorage.load "
            r6.<init>(r7)     // Catch:{ all -> 0x0168 }
            r6.append(r11)     // Catch:{ all -> 0x0168 }
            java.lang.String r7 = " "
            r6.append(r7)     // Catch:{ all -> 0x0168 }
            r6.append(r3)     // Catch:{ all -> 0x0168 }
            java.lang.String r7 = " "
            r6.append(r7)     // Catch:{ all -> 0x0168 }
            r6.append(r2)     // Catch:{ all -> 0x0168 }
            java.lang.String r7 = " "
            r6.append(r7)     // Catch:{ all -> 0x0168 }
            r6.append(r4)     // Catch:{ all -> 0x0168 }
            java.lang.String r7 = " "
            r6.append(r7)     // Catch:{ all -> 0x0168 }
            r6.append(r5)     // Catch:{ all -> 0x0168 }
            boolean r6 = a(r11, r3, r4, r5)     // Catch:{ all -> 0x0168 }
            if (r6 == 0) goto L_0x0156
            r1.d()     // Catch:{ all -> 0x0168 }
            goto L_0x0164
        L_0x0156:
            r1.j = r11     // Catch:{ all -> 0x0168 }
            r1.k = r3     // Catch:{ all -> 0x0168 }
            long r2 = r2.longValue()     // Catch:{ all -> 0x0168 }
            r1.l = r2     // Catch:{ all -> 0x0168 }
            r1.m = r4     // Catch:{ all -> 0x0168 }
            r1.n = r5     // Catch:{ all -> 0x0168 }
        L_0x0164:
            com.alipay.sdk.tid.c r11 = s     // Catch:{ all -> 0x0168 }
            monitor-exit(r0)
            return r11
        L_0x0168:
            r11 = move-exception
            monitor-exit(r0)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.c.a(android.content.Context):com.alipay.sdk.tid.c");
    }

    /* JADX WARNING: Removed duplicated region for block: B:63:0x013f  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0143  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(android.content.Context r10) {
        /*
            r9 = this;
            if (r10 == 0) goto L_0x0008
            android.content.Context r10 = r10.getApplicationContext()
            r = r10
        L_0x0008:
            boolean r10 = r9.t
            if (r10 == 0) goto L_0x000d
            return
        L_0x000d:
            r10 = 1
            r9.t = r10
            java.lang.String r0 = "alipay_tid_storage"
            java.lang.String r1 = "tidflag"
            android.content.Context r2 = r     // Catch:{ Exception -> 0x00a8 }
            r3 = 0
            if (r2 != 0) goto L_0x001c
            r0 = 0
            goto L_0x0026
        L_0x001c:
            android.content.Context r2 = r     // Catch:{ Exception -> 0x00a8 }
            android.content.SharedPreferences r0 = r2.getSharedPreferences(r0, r3)     // Catch:{ Exception -> 0x00a8 }
            boolean r0 = r0.contains(r1)     // Catch:{ Exception -> 0x00a8 }
        L_0x0026:
            if (r0 != 0) goto L_0x00a8
            java.lang.String r0 = "alipay_tid_storage"
            java.lang.String r1 = "tidflag"
            java.lang.String r2 = "true"
            com.alipay.sdk.tid.c.a.a(r0, r1, r2)     // Catch:{ Exception -> 0x00a8 }
            com.alipay.sdk.tid.b r0 = com.alipay.sdk.tid.b.a()     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r1 = r0.a     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r0 = r0.b     // Catch:{ Exception -> 0x00a8 }
            r4 = 0
            java.lang.Long r2 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r4 = "virtualImeiAndImsi"
            java.lang.String r5 = "virtual_imei"
            java.lang.String r4 = com.alipay.sdk.tid.c.a.a(r4, r5, r3)     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r5 = "virtualImeiAndImsi"
            java.lang.String r6 = "virtual_imsi"
            java.lang.String r5 = com.alipay.sdk.tid.c.a.a(r5, r6, r3)     // Catch:{ Exception -> 0x00a8 }
            java.lang.String r6 = "TidCompatible::SyncTid02: %s，%s，%s，%s，%s"
            r7 = 5
            java.lang.Object[] r7 = new java.lang.Object[r7]     // Catch:{ Exception -> 0x00a8 }
            r7[r3] = r1     // Catch:{ Exception -> 0x00a8 }
            r7[r10] = r0     // Catch:{ Exception -> 0x00a8 }
            r3 = 2
            r7[r3] = r2     // Catch:{ Exception -> 0x00a8 }
            r3 = 3
            r7[r3] = r4     // Catch:{ Exception -> 0x00a8 }
            r3 = 4
            r7[r3] = r5     // Catch:{ Exception -> 0x00a8 }
            java.lang.String.format(r6, r7)     // Catch:{ Exception -> 0x00a8 }
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x00a8 }
            if (r3 != 0) goto L_0x0078
            int r3 = r1.length()     // Catch:{ Exception -> 0x00a8 }
            r6 = 64
            if (r3 != r6) goto L_0x00a8
        L_0x0078:
            boolean r3 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Exception -> 0x00a8 }
            if (r3 == 0) goto L_0x0088
            boolean r3 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x00a8 }
            if (r3 != 0) goto L_0x0088
            java.lang.String r4 = i()     // Catch:{ Exception -> 0x00a8 }
        L_0x0088:
            boolean r3 = a(r1, r0, r4, r5)     // Catch:{ Exception -> 0x00a8 }
            if (r3 != 0) goto L_0x00a8
            r9.j = r1     // Catch:{ Exception -> 0x00a8 }
            r9.k = r0     // Catch:{ Exception -> 0x00a8 }
            r9.m = r4     // Catch:{ Exception -> 0x00a8 }
            r9.n = r5     // Catch:{ Exception -> 0x00a8 }
            if (r2 != 0) goto L_0x009f
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x00a8 }
            r9.l = r0     // Catch:{ Exception -> 0x00a8 }
            goto L_0x00a5
        L_0x009f:
            long r0 = r2.longValue()     // Catch:{ Exception -> 0x00a8 }
            r9.l = r0     // Catch:{ Exception -> 0x00a8 }
        L_0x00a5:
            r9.o()     // Catch:{ Exception -> 0x00a8 }
        L_0x00a8:
            long r0 = java.lang.System.currentTimeMillis()
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r1 = 0
            java.lang.String r2 = "alipay_tid_storage"
            java.lang.String r3 = "tidinfo"
            java.lang.String r10 = com.alipay.sdk.tid.c.a.a(r2, r3, r10)     // Catch:{ Exception -> 0x0109 }
            boolean r2 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Exception -> 0x0109 }
            if (r2 != 0) goto L_0x0105
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ Exception -> 0x0109 }
            r2.<init>(r10)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r10 = "tid"
            java.lang.String r3 = ""
            java.lang.String r10 = r2.optString(r10, r3)     // Catch:{ Exception -> 0x0109 }
            java.lang.String r3 = "client_key"
            java.lang.String r4 = ""
            java.lang.String r3 = r2.optString(r3, r4)     // Catch:{ Exception -> 0x0103 }
            java.lang.String r4 = "timestamp"
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0101 }
            long r4 = r2.optLong(r4, r5)     // Catch:{ Exception -> 0x0101 }
            java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch:{ Exception -> 0x0101 }
            java.lang.String r0 = "vimei"
            java.lang.String r5 = ""
            java.lang.String r0 = r2.optString(r0, r5)     // Catch:{ Exception -> 0x00fe }
            java.lang.String r5 = "vimsi"
            java.lang.String r6 = ""
            java.lang.String r2 = r2.optString(r5, r6)     // Catch:{ Exception -> 0x00fc }
            r1 = r3
            r3 = r2
            r2 = r0
            r0 = r4
            goto L_0x010f
        L_0x00fc:
            r2 = r0
            goto L_0x00ff
        L_0x00fe:
            r2 = r1
        L_0x00ff:
            r0 = r4
            goto L_0x010c
        L_0x0101:
            r2 = r1
            goto L_0x010c
        L_0x0103:
            r2 = r1
            goto L_0x010b
        L_0x0105:
            r10 = r1
            r2 = r10
            r3 = r2
            goto L_0x010f
        L_0x0109:
            r10 = r1
            r2 = r10
        L_0x010b:
            r3 = r2
        L_0x010c:
            r8 = r3
            r3 = r1
            r1 = r8
        L_0x010f:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "TidStorage.load "
            r4.<init>(r5)
            r4.append(r10)
            java.lang.String r5 = " "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r5 = " "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r5 = " "
            r4.append(r5)
            r4.append(r2)
            java.lang.String r5 = " "
            r4.append(r5)
            r4.append(r3)
            boolean r4 = a(r10, r1, r2, r3)
            if (r4 == 0) goto L_0x0143
            r9.d()
            return
        L_0x0143:
            r9.j = r10
            r9.k = r1
            long r0 = r0.longValue()
            r9.l = r0
            r9.m = r2
            r9.n = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.c.b(android.content.Context):void");
    }

    public final String a() {
        new StringBuilder("TidStorage.getTid ").append(this.j);
        return this.j;
    }

    public final String b() {
        new StringBuilder("TidStorage.getClientKey ").append(this.k);
        return this.k;
    }

    private String f() {
        new StringBuilder("TidStorage.getVirtualImei ").append(this.m);
        return this.m;
    }

    private String g() {
        new StringBuilder("TidStorage.getVirtualImsi ").append(this.n);
        return this.n;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x009c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h() {
        /*
            r9 = this;
            long r0 = java.lang.System.currentTimeMillis()
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r1 = 0
            java.lang.String r2 = "alipay_tid_storage"
            java.lang.String r3 = "tidinfo"
            r4 = 1
            java.lang.String r2 = com.alipay.sdk.tid.c.a.a(r2, r3, r4)     // Catch:{ Exception -> 0x0061 }
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x0061 }
            if (r3 != 0) goto L_0x005d
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch:{ Exception -> 0x0061 }
            r3.<init>(r2)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r2 = "tid"
            java.lang.String r4 = ""
            java.lang.String r2 = r3.optString(r2, r4)     // Catch:{ Exception -> 0x0061 }
            java.lang.String r4 = "client_key"
            java.lang.String r5 = ""
            java.lang.String r4 = r3.optString(r4, r5)     // Catch:{ Exception -> 0x005b }
            java.lang.String r5 = "timestamp"
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0059 }
            long r5 = r3.optLong(r5, r6)     // Catch:{ Exception -> 0x0059 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ Exception -> 0x0059 }
            java.lang.String r0 = "vimei"
            java.lang.String r6 = ""
            java.lang.String r0 = r3.optString(r0, r6)     // Catch:{ Exception -> 0x0056 }
            java.lang.String r6 = "vimsi"
            java.lang.String r7 = ""
            java.lang.String r3 = r3.optString(r6, r7)     // Catch:{ Exception -> 0x0054 }
            r1 = r2
            r2 = r0
            r0 = r5
            goto L_0x0068
        L_0x0054:
            r3 = r0
            goto L_0x0057
        L_0x0056:
            r3 = r1
        L_0x0057:
            r0 = r5
            goto L_0x0064
        L_0x0059:
            r3 = r1
            goto L_0x0064
        L_0x005b:
            r3 = r1
            goto L_0x0063
        L_0x005d:
            r2 = r1
            r3 = r2
            r4 = r3
            goto L_0x0068
        L_0x0061:
            r2 = r1
            r3 = r2
        L_0x0063:
            r4 = r3
        L_0x0064:
            r8 = r3
            r3 = r1
            r1 = r2
            r2 = r8
        L_0x0068:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "TidStorage.load "
            r5.<init>(r6)
            r5.append(r1)
            java.lang.String r6 = " "
            r5.append(r6)
            r5.append(r4)
            java.lang.String r6 = " "
            r5.append(r6)
            r5.append(r0)
            java.lang.String r6 = " "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r6 = " "
            r5.append(r6)
            r5.append(r3)
            boolean r5 = a(r1, r4, r2, r3)
            if (r5 == 0) goto L_0x009c
            r9.d()
            return
        L_0x009c:
            r9.j = r1
            r9.k = r4
            long r0 = r0.longValue()
            r9.l = r0
            r9.m = r2
            r9.n = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.tid.c.h():void");
    }

    private static boolean a(String str, String str2, String str3, String str4) {
        return TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4);
    }

    public final boolean c() {
        return TextUtils.isEmpty(this.j) || TextUtils.isEmpty(this.k) || TextUtils.isEmpty(this.m) || TextUtils.isEmpty(this.n);
    }

    public final void d() {
        this.j = "";
        String hexString = Long.toHexString(System.currentTimeMillis());
        if (hexString.length() > 10) {
            hexString = hexString.substring(hexString.length() - 10);
        }
        this.k = hexString;
        this.l = System.currentTimeMillis();
        this.m = i();
        this.n = i();
        if (r != null) {
            r.getSharedPreferences("alipay_tid_storage", 0).edit().remove("tidinfo").apply();
        }
    }

    private static String i() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(hexString);
        sb.append(random.nextInt(9000) + 1000);
        return sb.toString();
    }

    private static String j() {
        String hexString = Long.toHexString(System.currentTimeMillis());
        return hexString.length() > 10 ? hexString.substring(hexString.length() - 10) : hexString;
    }

    private void k() {
        String.format("TidStorage::delete > %s，%s，%s，%s，%s", new Object[]{this.j, this.k, Long.valueOf(this.l), this.m, this.n});
        d();
    }

    private boolean l() {
        return c();
    }

    private Long m() {
        return Long.valueOf(this.l);
    }

    public final void a(String str, String str2) {
        StringBuilder sb = new StringBuilder("tid=");
        sb.append(str);
        sb.append(",clientKey=");
        sb.append(str2);
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            this.j = str;
            this.k = str2;
            this.l = System.currentTimeMillis();
            o();
        }
    }

    private void a(String str, String str2, String str3, String str4, Long l2) {
        if (!a(str, str2, str3, str4)) {
            this.j = str;
            this.k = str2;
            this.m = str3;
            this.n = str4;
            if (l2 == null) {
                this.l = System.currentTimeMillis();
            } else {
                this.l = l2.longValue();
            }
            o();
        }
    }

    private void o() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("tid", this.j);
            jSONObject.put("client_key", this.k);
            jSONObject.put("timestamp", this.l);
            jSONObject.put("vimei", this.m);
            jSONObject.put("vimsi", this.n);
            a.a((String) "alipay_tid_storage", (String) "tidinfo", jSONObject.toString());
        } catch (Exception unused) {
        }
    }

    private static boolean p() {
        if (r == null) {
            return false;
        }
        return r.getSharedPreferences("alipay_tid_storage", 0).contains(c);
    }

    private void q() {
        boolean z;
        if (r == null) {
            z = false;
        } else {
            z = r.getSharedPreferences("alipay_tid_storage", 0).contains(c);
        }
        if (!z) {
            a.a((String) "alipay_tid_storage", (String) c, (String) "true");
            b a2 = b.a();
            String str = a2.a;
            String str2 = a2.b;
            Long valueOf = Long.valueOf(0);
            String a3 = a.a((String) o, (String) p, false);
            String a4 = a.a((String) o, (String) q, false);
            String.format("TidCompatible::SyncTid02: %s，%s，%s，%s，%s", new Object[]{str, str2, valueOf, a3, a4});
            if (TextUtils.isEmpty(str) || str.length() == 64) {
                if (TextUtils.isEmpty(a3) && !TextUtils.isEmpty(a4)) {
                    a3 = i();
                }
                if (!a(str, str2, a3, a4)) {
                    this.j = str;
                    this.k = str2;
                    this.m = a3;
                    this.n = a4;
                    if (valueOf == null) {
                        this.l = System.currentTimeMillis();
                    } else {
                        this.l = valueOf.longValue();
                    }
                    o();
                }
            }
        }
    }
}
