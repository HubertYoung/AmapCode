package com.alipay.android.phone.inside.common.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.alibaba.analytics.core.sync.UploadQueueMgr;
import com.alipay.android.phone.inside.common.util.CommonUtil;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.beehive.photo.util.DiskFormatter;
import com.alipay.mobile.h5container.api.H5Param;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.SuperId;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import com.ut.device.UTDevice;
import java.text.SimpleDateFormat;
import java.util.Random;

public class DeviceInfo {
    private static DeviceInfo b = null;
    private static boolean s = false;
    public String[] a = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", SuperId.BIT_1_RQBXY, SuperId.BIT_1_NEARBY_SEARCH, "d", "e", "f", SuperId.BIT_1_NAVI, "h", "i", "j", SuperId.BIT_1_REALTIMEBUS_BUSSTATION, "l", "m", SuperId.BIT_1_MAIN_BUSSTATION, "o", SuperId.BIT_1_MAIN_VOICE_ASSISTANT, "q", UploadQueueMgr.MSGTYPE_REALTIME, "s", LogItem.MM_C15_K4_TIME, H5Param.URL, "v", "w", DictionaryKeys.CTRLXY_X, DictionaryKeys.CTRLXY_Y, "z", "A", DiskFormatter.B, "C", "D", "E", "F", DiskFormatter.GB, "H", LogHelper.DEFAULT_LEVEL, "J", DiskFormatter.KB, "L", DiskFormatter.MB, "N", "O", "P", "Q", "R", "S", "T", "U", SecureSignatureDefine.SG_KEY_SIGN_VERSION, "W", "X", "Y", "Z"};
    private Context c;
    private int d;
    private int e;
    private int f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private boolean o;
    private String p;
    private String q;
    private final Object r = new Object();
    @SuppressLint({"SimpleDateFormat"})
    private final SimpleDateFormat t = new SimpleDateFormat("yyMMddHHmmssSSS");
    private WifiManager u;

    private DeviceInfo(Context context) {
        this.c = context;
    }

    public static synchronized DeviceInfo a() {
        DeviceInfo deviceInfo;
        synchronized (DeviceInfo.class) {
            if (b == null) {
                DeviceInfo deviceInfo2 = new DeviceInfo(CommonUtil.a());
                b = deviceInfo2;
                Resources resources = deviceInfo2.c.getResources();
                if (resources == null) {
                    try {
                        resources = deviceInfo2.c.getPackageManager().getResourcesForApplication(deviceInfo2.c.getPackageName());
                    } catch (Throwable th) {
                        LoggerFactory.f().a((String) "DeviceInfo", th);
                    }
                }
                if (resources == null) {
                    try {
                        resources = Resources.getSystem();
                    } catch (Throwable th2) {
                        LoggerFactory.f().a((String) "DeviceInfo", th2);
                    }
                }
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                deviceInfo2.d = displayMetrics.widthPixels;
                deviceInfo2.e = displayMetrics.heightPixels;
                deviceInfo2.f = displayMetrics.densityDpi;
                deviceInfo2.g = deviceInfo2.x();
                deviceInfo2.h = deviceInfo2.z();
                deviceInfo2.i = deviceInfo2.A();
                Context context = deviceInfo2.c;
                StringBuilder sb = new StringBuilder();
                sb.append(deviceInfo2.c.getPackageName());
                sb.append(".config");
                SharedPreferences sharedPreferences = context.getSharedPreferences(sb.toString(), 0);
                String string = sharedPreferences.getString("clientKey", "");
                if ("".equals(string)) {
                    string = deviceInfo2.v();
                    sharedPreferences.edit().putString("clientKey", string).apply();
                }
                deviceInfo2.q = string;
                deviceInfo2.l = Build.BRAND;
                deviceInfo2.m = Build.MODEL;
                deviceInfo2.n = VERSION.RELEASE;
                deviceInfo2.o = w();
                try {
                    deviceInfo2.u = (WifiManager) deviceInfo2.c.getSystemService("wifi");
                } catch (Throwable th3) {
                    LoggerFactory.f().b("DeviceInfo", " mContext.getSystemService(Context.WIFI_SERVICE); Exception.", th3);
                }
            }
            deviceInfo = b;
        }
        return deviceInfo;
    }

    public static synchronized DeviceInfo b() {
        DeviceInfo a2;
        synchronized (DeviceInfo.class) {
            a2 = a();
        }
        return a2;
    }

    public static synchronized void c() {
        synchronized (DeviceInfo.class) {
            if (!s && b != null) {
                DeviceInfo deviceInfo = b;
                deviceInfo.g = deviceInfo.x();
                deviceInfo.h = deviceInfo.z();
                deviceInfo.i = deviceInfo.A();
                s = true;
            }
        }
    }

    private String v() {
        Random random = new Random(System.currentTimeMillis());
        int length = this.a.length;
        String str = "";
        for (int i2 = 0; i2 < 10; i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(this.a[random.nextInt(length)]);
            str = sb.toString();
        }
        return str;
    }

    private static boolean w() {
        Object obj = null;
        try {
            obj = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"ro.secure"});
        } catch (Exception e2) {
            LoggerFactory.f().b("DeviceInfo", "", e2);
        }
        if ((obj == null || !"1".equals(obj)) && obj != null && "0".equals(obj)) {
            return true;
        }
        return false;
    }

    public final String d() {
        String e2 = e();
        if (TextUtils.isEmpty(e2)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.g);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(this.q);
            e2 = sb.toString();
        }
        this.p = e2;
        TraceLogger f2 = LoggerFactory.f();
        StringBuilder sb2 = new StringBuilder("DeviceInfo::getmDid > ");
        sb2.append(this.p);
        f2.b((String) "DeviceInfo", sb2.toString());
        return this.p;
    }

    public final String e() {
        String str;
        try {
            str = UTDevice.getUtdid(this.c);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "DeviceInfo", th);
            str = null;
        }
        LoggerFactory.f().b((String) "DeviceInfo", "DeviceInfo::getUtdid > ".concat(String.valueOf(str)));
        return str;
    }

    public final String f() {
        TraceLogger f2 = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("getmDidAsync=");
        sb.append(this.p);
        f2.b((String) "DeviceInfo", sb.toString());
        if (this.p != null && !"".equals(this.p)) {
            return this.p;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.g);
        sb2.append(MergeUtil.SEPARATOR_KV);
        sb2.append(this.q);
        return sb2.toString();
    }

    public final int g() {
        return this.d;
    }

    public final int h() {
        return this.e;
    }

    public final String i() {
        return this.l;
    }

    public final String j() {
        return this.m;
    }

    public final String k() {
        return this.n;
    }

    public final int l() {
        return this.d;
    }

    public final int m() {
        return this.e;
    }

    public static String n() {
        return VERSION.RELEASE;
    }

    public static String o() {
        StringBuilder sb = new StringBuilder();
        sb.append(Build.MANUFACTURER);
        sb.append(Build.MODEL);
        return sb.toString();
    }

    public final String p() {
        return this.h;
    }

    public final String q() {
        return this.i;
    }

    private static boolean a(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        String trim = str.trim();
        if (trim.equalsIgnoreCase("unknown") || trim.equalsIgnoreCase("null") || trim.matches(com.alipay.mobile.common.info.DeviceInfo.ANY_ZERO_STR) || trim.length() <= 5) {
            return false;
        }
        return true;
    }

    private static boolean b(String str) {
        return str == null || str.trim().length() == 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0191  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String x() {
        /*
            r11 = this;
            android.content.Context r0 = r11.c
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            android.content.Context r2 = r11.c
            java.lang.String r2 = r2.getPackageName()
            r1.append(r2)
            java.lang.String r2 = ".config"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r2 = 0
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r2)
            java.lang.String r1 = ""
            java.lang.String r3 = ""
            int r4 = android.os.Build.VERSION.SDK_INT
            r5 = -1
            r6 = 21
            if (r4 < r6) goto L_0x003e
            android.content.Context r4 = r11.c     // Catch:{ Throwable -> 0x0032 }
            java.lang.String r6 = "android.permission.READ_PHONE_STATE"
            int r4 = android.support.v4.content.ContextCompat.checkSelfPermission(r4, r6)     // Catch:{ Throwable -> 0x0032 }
            goto L_0x003f
        L_0x0032:
            r4 = move-exception
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r6 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r7 = "DeviceInfo"
            r6.b(r7, r4)
            r4 = -1
            goto L_0x003f
        L_0x003e:
            r4 = 0
        L_0x003f:
            if (r4 != r5) goto L_0x004d
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r4 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r5 = "DeviceInfo"
            java.lang.String r6 = "PERMISSION_DENIED"
            r4.d(r5, r6)
            goto L_0x0085
        L_0x004d:
            android.content.Context r4 = r11.c     // Catch:{ Exception -> 0x0066 }
            java.lang.String r5 = "phone"
            java.lang.Object r4 = r4.getSystemService(r5)     // Catch:{ Exception -> 0x0066 }
            android.telephony.TelephonyManager r4 = (android.telephony.TelephonyManager) r4     // Catch:{ Exception -> 0x0066 }
            java.lang.String r5 = r4.getDeviceId()     // Catch:{ Exception -> 0x0066 }
            java.lang.String r1 = r4.getSubscriberId()     // Catch:{ Exception -> 0x0062 }
            r3 = r1
            r1 = r5
            goto L_0x0072
        L_0x0062:
            r1 = move-exception
            r4 = r1
            r1 = r5
            goto L_0x0067
        L_0x0066:
            r4 = move-exception
        L_0x0067:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r5 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r6 = "DeviceInfo"
            java.lang.String r7 = ""
            r5.b(r6, r7, r4)
        L_0x0072:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r4 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r5 = "DeviceInfo"
            java.lang.String r6 = "origin imei:"
            java.lang.String r7 = java.lang.String.valueOf(r1)
            java.lang.String r6 = r6.concat(r7)
            r4.a(r5, r6)
        L_0x0085:
            r11.j = r1
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r4 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r5 = "DeviceInfo"
            java.lang.String r6 = "origin imsi:"
            java.lang.String r7 = java.lang.String.valueOf(r3)
            java.lang.String r6 = r6.concat(r7)
            r4.a(r5, r6)
            r11.k = r3
            java.lang.String r4 = "clientId"
            java.lang.String r5 = ""
            java.lang.String r4 = r0.getString(r4, r5)
            boolean r5 = c(r4)
            r6 = 2
            if (r5 != 0) goto L_0x00b5
            java.lang.String r5 = new java.lang.String
            byte[] r4 = android.util.Base64.decode(r4, r6)
            r5.<init>(r4)
            r4 = r5
        L_0x00b5:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r5 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r7 = "DeviceInfo"
            java.lang.String r8 = "saved clientid:"
            java.lang.String r9 = java.lang.String.valueOf(r4)
            java.lang.String r8 = r8.concat(r9)
            r5.a(r7, r8)
            boolean r5 = c(r4)
            if (r5 == 0) goto L_0x0191
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r5 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r7 = "DeviceInfo"
            java.lang.String r8 = "client id is valid:"
            java.lang.String r9 = java.lang.String.valueOf(r4)
            java.lang.String r8 = r8.concat(r9)
            r5.a(r7, r8)
            r5 = 15
            java.lang.String r7 = r4.substring(r2, r5)
            boolean r8 = a(r3)
            if (r8 == 0) goto L_0x0103
            java.lang.String r8 = e(r3)
            int r9 = r8.length()
            if (r9 <= r5) goto L_0x00fc
            java.lang.String r8 = r8.substring(r2, r5)
        L_0x00fc:
            boolean r8 = r7.startsWith(r8)
            if (r8 != 0) goto L_0x0103
            r7 = r3
        L_0x0103:
            int r8 = r4.length()
            int r8 = r8 - r5
            int r9 = r4.length()
            java.lang.String r8 = r4.substring(r8, r9)
            boolean r9 = a(r1)
            if (r9 == 0) goto L_0x012b
            java.lang.String r9 = e(r1)
            int r10 = r9.length()
            if (r10 <= r5) goto L_0x0124
            java.lang.String r9 = r9.substring(r2, r5)
        L_0x0124:
            boolean r2 = r8.startsWith(r9)
            if (r2 != 0) goto L_0x012b
            r8 = r1
        L_0x012b:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r5 = "DeviceInfo"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "client id is valid:"
            r9.<init>(r10)
            r9.append(r7)
            java.lang.String r10 = "|"
            r9.append(r10)
            r9.append(r8)
            java.lang.String r9 = r9.toString()
            r2.a(r5, r9)
            java.lang.String r2 = r11.a(r7, r8)
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r5 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r7 = "DeviceInfo"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "normarlize, imsi:"
            r8.<init>(r9)
            r8.append(r3)
            java.lang.String r3 = ",imei:"
            r8.append(r3)
            r8.append(r1)
            java.lang.String r1 = ",newClientId:"
            r8.append(r1)
            r8.append(r2)
            java.lang.String r1 = r8.toString()
            r5.a(r7, r1)
            boolean r1 = android.text.TextUtils.equals(r2, r4)
            if (r1 != 0) goto L_0x0209
            android.content.SharedPreferences$Editor r0 = r0.edit()
            java.lang.String r1 = "clientId"
            byte[] r3 = r2.getBytes()
            java.lang.String r3 = android.util.Base64.encodeToString(r3, r6)
            r0.putString(r1, r3)
            r0.apply()
            goto L_0x0209
        L_0x0191:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r2 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r5 = "DeviceInfo"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "client is is not valid, imei:"
            r7.<init>(r8)
            r7.append(r1)
            java.lang.String r8 = ",imsi:"
            r7.append(r8)
            r7.append(r3)
            java.lang.String r7 = r7.toString()
            r2.a(r5, r7)
            boolean r2 = a(r1)
            if (r2 != 0) goto L_0x01ba
            java.lang.String r1 = r11.y()
        L_0x01ba:
            boolean r2 = a(r3)
            if (r2 != 0) goto L_0x01c4
            java.lang.String r3 = r11.y()
        L_0x01c4:
            java.lang.String r2 = r11.a(r3, r1)
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r5 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r7 = "DeviceInfo"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "normalize, imei:"
            r8.<init>(r9)
            r8.append(r1)
            java.lang.String r1 = ",imsi:"
            r8.append(r1)
            r8.append(r3)
            java.lang.String r1 = ",newClientId:"
            r8.append(r1)
            r8.append(r2)
            java.lang.String r1 = r8.toString()
            r5.a(r7, r1)
            boolean r1 = android.text.TextUtils.equals(r2, r4)
            if (r1 != 0) goto L_0x0209
            android.content.SharedPreferences$Editor r0 = r0.edit()
            java.lang.String r1 = "clientId"
            byte[] r3 = r2.getBytes()
            java.lang.String r3 = android.util.Base64.encodeToString(r3, r6)
            r0.putString(r1, r3)
            r0.apply()
        L_0x0209:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.common.info.DeviceInfo.x():java.lang.String");
    }

    private static boolean c(String str) {
        if (b(str)) {
            return false;
        }
        return str.matches("[[a-z][A-Z][0-9]]{15}\\|[[a-z][A-Z][0-9]]{15}");
    }

    private String d(String str) {
        if (!a(str)) {
            str = y();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("123456789012345");
        return e(sb.toString().substring(0, 15));
    }

    private static String e(String str) {
        if (b(str)) {
            return str;
        }
        byte[] bytes = str.getBytes();
        for (int i2 = 0; i2 < bytes.length; i2++) {
            byte b2 = bytes[i2];
            if (!((b2 >= 48 && b2 <= 57) || (b2 >= 97 && b2 <= 122) || (b2 >= 65 && b2 <= 90))) {
                bytes[i2] = 48;
            }
        }
        return new String(bytes);
    }

    private String a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(d(str));
        sb.append(MergeUtil.SEPARATOR_KV);
        sb.append(d(str2));
        return sb.toString();
    }

    private String y() {
        return this.t.format(Long.valueOf(System.currentTimeMillis()));
    }

    private String z() {
        return c(this.g) ? this.g.substring(this.g.length() - 15) : "";
    }

    private String A() {
        return c(this.g) ? this.g.substring(0, (this.g.length() - 15) - 1) : "";
    }

    public final String r() {
        return this.g;
    }

    public final String s() {
        String sb;
        try {
            CellLocation cellLocation = ((TelephonyManager) this.c.getSystemService("phone")).getCellLocation();
            if (cellLocation == null) {
                return "-1;-1";
            }
            StringBuilder sb2 = new StringBuilder();
            if (cellLocation instanceof GsmCellLocation) {
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
                int cid = gsmCellLocation.getCid();
                sb2.append(gsmCellLocation.getLac());
                sb2.append(";");
                sb2.append(cid);
                sb = sb2.toString();
            } else if (!(cellLocation instanceof CdmaCellLocation)) {
                return "-1;-1";
            } else {
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
                int baseStationLatitude = cdmaCellLocation.getBaseStationLatitude();
                sb2.append(cdmaCellLocation.getBaseStationLongitude());
                sb2.append(";");
                sb2.append(baseStationLatitude);
                sb = sb2.toString();
            }
            return sb;
        } catch (Exception e2) {
            LoggerFactory.f().b("DeviceInfo", "", e2);
            return "-1;-1";
        }
    }

    public final String t() {
        return this.q;
    }

    public final String u() {
        if (this.u == null) {
            return "";
        }
        return this.u.getConnectionInfo().getMacAddress();
    }
}
