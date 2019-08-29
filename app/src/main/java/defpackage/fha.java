package defpackage;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.alipay.android.phone.a.a.a;
import mtopsdk.common.util.TBSdkLog;

/* renamed from: fha reason: default package */
/* compiled from: PhoneInfo */
public final class fha {
    private static fcy a = fcy.a();

    public static String a() {
        try {
            String str = VERSION.RELEASE;
            String str2 = Build.MANUFACTURER;
            String str3 = Build.MODEL;
            StringBuilder sb = new StringBuilder(32);
            sb.append("MTOPSDK/3.0.4.7");
            sb.append(" (");
            sb.append(a.a);
            sb.append(";");
            sb.append(str);
            sb.append(";");
            sb.append(str2);
            sb.append(";");
            sb.append(str3);
            sb.append(")");
            return sb.toString();
        } catch (Throwable th) {
            StringBuilder sb2 = new StringBuilder("[getPhoneBaseInfo] error ---");
            sb2.append(th.toString());
            TBSdkLog.d("mtopsdk.PhoneInfo", sb2.toString());
            return "";
        }
    }

    public static String a(Context context) {
        String str;
        String str2 = null;
        if (context == null) {
            return null;
        }
        try {
            str = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            if (str != null) {
                try {
                    str2 = str.trim();
                } catch (Throwable th) {
                    Throwable th2 = th;
                    str2 = str;
                    th = th2;
                    StringBuilder sb = new StringBuilder("[getOriginalImei]error ---");
                    sb.append(th.toString());
                    TBSdkLog.d("mtopsdk.PhoneInfo", sb.toString());
                    str = str2;
                    return str;
                }
                str = str2;
            }
        } catch (Throwable th3) {
            th = th3;
            StringBuilder sb2 = new StringBuilder("[getOriginalImei]error ---");
            sb2.append(th.toString());
            TBSdkLog.d("mtopsdk.PhoneInfo", sb2.toString());
            str = str2;
            return str;
        }
        return str;
    }

    public static String b(Context context) {
        String str;
        String str2 = null;
        if (context == null) {
            return null;
        }
        try {
            str = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
            if (str != null) {
                try {
                    str2 = str.trim();
                } catch (Throwable th) {
                    Throwable th2 = th;
                    str2 = str;
                    th = th2;
                    StringBuilder sb = new StringBuilder("[getOriginalImsi]error ---");
                    sb.append(th.toString());
                    TBSdkLog.d("mtopsdk.PhoneInfo", sb.toString());
                    str = str2;
                    return str;
                }
                str = str2;
            }
        } catch (Throwable th3) {
            th = th3;
            StringBuilder sb2 = new StringBuilder("[getOriginalImsi]error ---");
            sb2.append(th.toString());
            TBSdkLog.d("mtopsdk.PhoneInfo", sb2.toString());
            str = str2;
            return str;
        }
        return str;
    }

    public static String b() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.serialno", "unknown"});
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("[getSerialNum]error ---");
            sb.append(th.toString());
            TBSdkLog.d("mtopsdk.PhoneInfo", sb.toString());
            return null;
        }
    }

    @TargetApi(3)
    public static String c(Context context) {
        String str;
        if (context == null) {
            return null;
        }
        try {
            str = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("[getAndroidId]error ---");
            sb.append(th.toString());
            TBSdkLog.d("mtopsdk.PhoneInfo", sb.toString());
            str = null;
        }
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x003f A[Catch:{ Throwable -> 0x0054 }] */
    @android.annotation.TargetApi(8)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String d(android.content.Context r6) {
        /*
            if (r6 != 0) goto L_0x0005
            java.lang.String r6 = ""
            return r6
        L_0x0005:
            java.lang.String r0 = ""
            java.lang.String r1 = "MtopConfigStore"
            java.lang.String r2 = "PHONE_INFO_STORE."
            java.lang.String r3 = "mtopsdk_mac_address"
            java.lang.String r1 = defpackage.fcy.a(r6, r1, r2, r3)     // Catch:{ Throwable -> 0x0054 }
            boolean r0 = defpackage.fdd.a(r1)     // Catch:{ Throwable -> 0x0051 }
            r2 = 0
            if (r0 == 0) goto L_0x0022
            java.lang.String r6 = new java.lang.String     // Catch:{ Throwable -> 0x0051 }
            byte[] r0 = android.util.Base64.decode(r1, r2)     // Catch:{ Throwable -> 0x0051 }
            r6.<init>(r0)     // Catch:{ Throwable -> 0x0051 }
            return r6
        L_0x0022:
            java.lang.String r0 = "wifi"
            java.lang.Object r0 = r6.getSystemService(r0)     // Catch:{ Throwable -> 0x0051 }
            android.net.wifi.WifiManager r0 = (android.net.wifi.WifiManager) r0     // Catch:{ Throwable -> 0x0051 }
            if (r0 == 0) goto L_0x0038
            android.net.wifi.WifiInfo r0 = r0.getConnectionInfo()     // Catch:{ Throwable -> 0x0051 }
            if (r0 == 0) goto L_0x0038
            java.lang.String r0 = r0.getMacAddress()     // Catch:{ Throwable -> 0x0051 }
            goto L_0x0039
        L_0x0038:
            r0 = r1
        L_0x0039:
            boolean r1 = defpackage.fdd.a(r0)     // Catch:{ Throwable -> 0x0054 }
            if (r1 == 0) goto L_0x006c
            java.lang.String r1 = "MtopConfigStore"
            java.lang.String r3 = "PHONE_INFO_STORE."
            java.lang.String r4 = "mtopsdk_mac_address"
            byte[] r5 = r0.getBytes()     // Catch:{ Throwable -> 0x0054 }
            java.lang.String r2 = android.util.Base64.encodeToString(r5, r2)     // Catch:{ Throwable -> 0x0054 }
            defpackage.fcy.a(r6, r1, r3, r4, r2)     // Catch:{ Throwable -> 0x0054 }
            goto L_0x006c
        L_0x0051:
            r6 = move-exception
            r0 = r1
            goto L_0x0055
        L_0x0054:
            r6 = move-exception
        L_0x0055:
            java.lang.String r1 = "mtopsdk.PhoneInfo"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "[getLocalMacAddress]error ---"
            r2.<init>(r3)
            java.lang.String r6 = r6.toString()
            r2.append(r6)
            java.lang.String r6 = r2.toString()
            mtopsdk.common.util.TBSdkLog.d(r1, r6)
        L_0x006c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.fha.d(android.content.Context):java.lang.String");
    }
}
