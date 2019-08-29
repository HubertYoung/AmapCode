package com.alipay.deviceid.module.x;

import android.content.Context;
import android.os.Environment;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import java.io.File;
import java.util.Map;

public final class a {
    private Context a;
    private b b = b.a();
    private int c = 4;

    public a(Context context) {
        this.a = context;
    }

    public static String a(Context context, String str) {
        try {
            String b2 = aw.b(context, str);
            if (!e.a(b2)) {
                return b2;
            }
        } catch (Throwable unused) {
        }
        return "";
    }

    private static void a() {
        try {
            String[] strArr = {"device_feature_file_name", "wallet_times", "wxcasxx_v3", "wxcasxx_v4", "wxxzyy_v1"};
            for (int i = 0; i < 5; i++) {
                File file = new File(Environment.getExternalStorageDirectory(), ".SystemConfig/".concat(String.valueOf(strArr[i])));
                if (file.exists() && file.canWrite()) {
                    file.delete();
                }
            }
        } catch (Throwable unused) {
        }
    }

    private p b(Map<String, String> map) {
        try {
            Context context = this.a;
            q qVar = new q();
            String a2 = e.a(map, TransportConstants.KEY_RPC_VERSION, "");
            String a3 = a(context, e.a(map, "appName", ""));
            qVar.a((String) "android");
            qVar.b(a3);
            qVar.c(a2);
            qVar.a(d.a().a(context, map));
            return r.a(this.a, this.b.b()).a(this.a, qVar);
        } catch (Throwable th) {
            aa.a(th);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0043, code lost:
        if (com.alipay.deviceid.module.x.e.a(a(r7.a, r0)) != false) goto L_0x0045;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0079 A[Catch:{ Exception -> 0x010c }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x007b A[Catch:{ Exception -> 0x010c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int a(java.util.Map<java.lang.String, java.lang.String> r8) {
        /*
            r7 = this;
            android.content.Context r0 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r1 = "tid"
            java.lang.String r2 = ""
            java.lang.String r1 = com.alipay.deviceid.module.x.e.a(r8, r1, r2)     // Catch:{ Exception -> 0x010c }
            java.lang.String r2 = "utdid"
            java.lang.String r3 = ""
            java.lang.String r2 = com.alipay.deviceid.module.x.e.a(r8, r2, r3)     // Catch:{ Exception -> 0x010c }
            java.lang.String r3 = ""
            com.alipay.deviceid.module.x.aa.a(r0, r1, r2, r3)     // Catch:{ Exception -> 0x010c }
            java.lang.String r0 = "appName"
            java.lang.String r1 = ""
            java.lang.String r0 = com.alipay.deviceid.module.x.e.a(r8, r0, r1)     // Catch:{ Exception -> 0x010c }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010c }
            java.lang.String r2 = "[*] Server Address = "
            r1.<init>(r2)     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.b r2 = r7.b     // Catch:{ Exception -> 0x010c }
            java.lang.String r2 = r2.b()     // Catch:{ Exception -> 0x010c }
            r1.append(r2)     // Catch:{ Exception -> 0x010c }
            boolean r1 = com.alipay.deviceid.module.x.ay.a()     // Catch:{ Exception -> 0x010c }
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x0049
            android.content.Context r1 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r1 = a(r1, r0)     // Catch:{ Exception -> 0x010c }
            boolean r1 = com.alipay.deviceid.module.x.e.a(r1)     // Catch:{ Exception -> 0x010c }
            if (r1 == 0) goto L_0x0047
        L_0x0045:
            r1 = 1
            goto L_0x0077
        L_0x0047:
            r1 = 0
            goto L_0x0077
        L_0x0049:
            com.alipay.deviceid.module.x.d r1 = com.alipay.deviceid.module.x.d.a()     // Catch:{ Exception -> 0x010c }
            android.content.Context r4 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r1 = r1.a(r4)     // Catch:{ Exception -> 0x010c }
            android.content.Context r4 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r4 = com.alipay.deviceid.module.x.aw.a(r4, r0)     // Catch:{ Exception -> 0x010c }
            boolean r1 = com.alipay.deviceid.module.x.e.a(r1, r4)     // Catch:{ Exception -> 0x010c }
            r1 = r1 ^ r3
            if (r1 == 0) goto L_0x0061
            goto L_0x0045
        L_0x0061:
            android.content.Context r1 = r7.a     // Catch:{ Exception -> 0x010c }
            boolean r1 = com.alipay.deviceid.module.x.ay.a(r1, r0)     // Catch:{ Exception -> 0x010c }
            if (r1 != 0) goto L_0x006a
            goto L_0x0045
        L_0x006a:
            android.content.Context r1 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r1 = a(r1, r0)     // Catch:{ Exception -> 0x010c }
            boolean r1 = com.alipay.deviceid.module.x.e.a(r1)     // Catch:{ Exception -> 0x010c }
            if (r1 == 0) goto L_0x0047
            goto L_0x0045
        L_0x0077:
            if (r1 != 0) goto L_0x007b
            goto L_0x00f8
        L_0x007b:
            com.alipay.deviceid.module.x.p r8 = r7.b(r8)     // Catch:{ Exception -> 0x010c }
            r1 = 2
            r4 = 3
            if (r8 == 0) goto L_0x009c
            boolean r5 = r8.a     // Catch:{ Exception -> 0x010c }
            if (r5 == 0) goto L_0x0091
            java.lang.String r5 = r8.c     // Catch:{ Exception -> 0x010c }
            boolean r5 = com.alipay.deviceid.module.x.e.a(r5)     // Catch:{ Exception -> 0x010c }
            if (r5 != 0) goto L_0x009c
            r1 = 1
            goto L_0x009c
        L_0x0091:
            java.lang.String r5 = "APPKEY_ERROR"
            java.lang.String r6 = r8.b     // Catch:{ Exception -> 0x010c }
            boolean r5 = r5.equals(r6)     // Catch:{ Exception -> 0x010c }
            if (r5 == 0) goto L_0x009c
            r1 = 3
        L_0x009c:
            if (r1 == r3) goto L_0x00bd
            if (r1 == r4) goto L_0x00bb
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x010c }
            java.lang.String r3 = "[*] rpc failed. there must EXIST something error."
            r1.<init>(r3)     // Catch:{ Exception -> 0x010c }
            java.lang.String r8 = r8.b     // Catch:{ Exception -> 0x010c }
            r1.append(r8)     // Catch:{ Exception -> 0x010c }
            android.content.Context r8 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r8 = a(r8, r0)     // Catch:{ Exception -> 0x010c }
            boolean r8 = com.alipay.deviceid.module.x.e.a(r8)     // Catch:{ Exception -> 0x010c }
            if (r8 == 0) goto L_0x00f5
            r8 = 4
            r2 = 4
            goto L_0x00f5
        L_0x00bb:
            r2 = 1
            goto L_0x00f5
        L_0x00bd:
            android.content.Context r1 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r3 = "1"
            java.lang.String r4 = r8.e     // Catch:{ Exception -> 0x010c }
            boolean r3 = r3.equals(r4)     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.ax.a(r1, r3)     // Catch:{ Exception -> 0x010c }
            android.content.Context r1 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r3 = r8.g     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.ax.a(r1, r3)     // Catch:{ Exception -> 0x010c }
            android.content.Context r1 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r8 = r8.c     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.aw.b(r1, r0, r8)     // Catch:{ Exception -> 0x010c }
            android.content.Context r8 = r7.a     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.aw.a(r8)     // Catch:{ Exception -> 0x010c }
            android.content.Context r8 = r7.a     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.d r1 = com.alipay.deviceid.module.x.d.a()     // Catch:{ Exception -> 0x010c }
            android.content.Context r3 = r7.a     // Catch:{ Exception -> 0x010c }
            java.lang.String r1 = r1.a(r3)     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.aw.a(r8, r0, r1)     // Catch:{ Exception -> 0x010c }
            android.content.Context r8 = r7.a     // Catch:{ Exception -> 0x010c }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.ax.a(r8, r0, r3)     // Catch:{ Exception -> 0x010c }
        L_0x00f5:
            a()     // Catch:{ Exception -> 0x010c }
        L_0x00f8:
            r7.c = r2     // Catch:{ Exception -> 0x010c }
            android.content.Context r8 = r7.a     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.b r0 = r7.b     // Catch:{ Exception -> 0x010c }
            java.lang.String r0 = r0.b()     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.r r8 = com.alipay.deviceid.module.x.r.a(r8, r0)     // Catch:{ Exception -> 0x010c }
            android.content.Context r0 = r7.a     // Catch:{ Exception -> 0x010c }
            com.alipay.deviceid.module.x.aa.a(r0, r8)     // Catch:{ Exception -> 0x010c }
            goto L_0x0110
        L_0x010c:
            r8 = move-exception
            com.alipay.deviceid.module.x.aa.a(r8)
        L_0x0110:
            int r8 = r7.c
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.deviceid.module.x.a.a(java.util.Map):int");
    }
}
