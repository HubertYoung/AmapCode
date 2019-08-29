package defpackage;

import android.content.Context;

/* renamed from: eva reason: default package */
/* compiled from: CommonUtillNetwork */
public class eva {
    private static final String a = "eva";

    public static String a(Context context) {
        int b = b(context);
        if (b == -101) {
            return "WIFI";
        }
        switch (b) {
            case -1:
                return "";
            case 0:
                return "";
            case 1:
                return "2G";
            case 2:
                return "3G";
            case 3:
                return "4G";
            default:
                return "未知";
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x006e A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int b(android.content.Context r6) {
        /*
            r0 = 1
            r1 = 0
            r2 = -1
            r3 = -101(0xffffffffffffff9b, float:NaN)
            java.lang.String r4 = "android.permission.ACCESS_NETWORK_STATE"
            boolean r4 = defpackage.euw.a(r6, r4)     // Catch:{ Exception -> 0x0049 }
            if (r4 == 0) goto L_0x0043
            java.lang.String r4 = "connectivity"
            java.lang.Object r4 = r6.getSystemService(r4)     // Catch:{ Exception -> 0x0049 }
            android.net.ConnectivityManager r4 = (android.net.ConnectivityManager) r4     // Catch:{ Exception -> 0x0049 }
            android.net.NetworkInfo r4 = r4.getActiveNetworkInfo()     // Catch:{ Exception -> 0x0049 }
            if (r4 == 0) goto L_0x0041
            boolean r5 = r4.isAvailable()     // Catch:{ Exception -> 0x0049 }
            if (r5 == 0) goto L_0x0041
            boolean r5 = r4.isConnected()     // Catch:{ Exception -> 0x0049 }
            if (r5 == 0) goto L_0x0041
            int r4 = r4.getType()     // Catch:{ Exception -> 0x0049 }
            if (r4 != r0) goto L_0x0030
            r6 = -101(0xffffffffffffff9b, float:NaN)
            goto L_0x0060
        L_0x0030:
            if (r4 != 0) goto L_0x003f
            java.lang.String r4 = "phone"
            java.lang.Object r6 = r6.getSystemService(r4)     // Catch:{ Exception -> 0x0049 }
            android.telephony.TelephonyManager r6 = (android.telephony.TelephonyManager) r6     // Catch:{ Exception -> 0x0049 }
            int r6 = r6.getNetworkType()     // Catch:{ Exception -> 0x0049 }
            goto L_0x0060
        L_0x003f:
            r6 = 0
            goto L_0x0060
        L_0x0041:
            r6 = -1
            goto L_0x0060
        L_0x0043:
            java.lang.String r6 = "lost----> android.permission.ACCESS_NETWORK_STATE"
            defpackage.euw.a(r6)     // Catch:{ Exception -> 0x0049 }
            goto L_0x003f
        L_0x0049:
            r6 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Collected:"
            r4.<init>(r5)
            java.lang.String r6 = r6.getMessage()
            r4.append(r6)
            java.lang.String r6 = r4.toString()
            defpackage.euw.a(r6)
            goto L_0x003f
        L_0x0060:
            if (r6 == r3) goto L_0x006e
            if (r6 == r2) goto L_0x006d
            switch(r6) {
                case 1: goto L_0x006c;
                case 2: goto L_0x006c;
                case 3: goto L_0x006a;
                case 4: goto L_0x006c;
                case 5: goto L_0x006a;
                case 6: goto L_0x006a;
                case 7: goto L_0x006c;
                case 8: goto L_0x006a;
                case 9: goto L_0x006a;
                case 10: goto L_0x006a;
                case 11: goto L_0x006c;
                case 12: goto L_0x006a;
                case 13: goto L_0x0068;
                case 14: goto L_0x006a;
                case 15: goto L_0x006a;
                default: goto L_0x0067;
            }
        L_0x0067:
            return r1
        L_0x0068:
            r6 = 3
            return r6
        L_0x006a:
            r6 = 2
            return r6
        L_0x006c:
            return r0
        L_0x006d:
            return r2
        L_0x006e:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.eva.b(android.content.Context):int");
    }
}
