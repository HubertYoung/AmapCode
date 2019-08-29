package defpackage;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Process;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.android.phone.inside.sdk.util.GlobalConstants;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

/* renamed from: euw reason: default package */
/* compiled from: CommonUtil */
public class euw {
    public static final String a = "euw";

    public static String h() {
        return "";
    }

    public static String i() {
        return "";
    }

    public static String j() {
        return "";
    }

    public static boolean a(Context context, String str) {
        try {
            if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
            return false;
        }
    }

    public static String a(Context context) {
        return (String) ewp.b(context, "hmt_agent_online_setting", "muid", "");
    }

    public static boolean b(Context context) {
        if (a(context, (String) "android.permission.ACCESS_WIFI_STATE")) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
                if (allNetworkInfo != null) {
                    for (int i = 0; i < allNetworkInfo.length; i++) {
                        if (allNetworkInfo[i].getTypeName().equals("WIFI") && allNetworkInfo[i].isConnected()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        a((String) "isWiFiActive : lost permission:android.permission.ACCESS_WIFI_STATE");
        return false;
    }

    public static boolean c(Context context) {
        NetworkInfo networkInfo;
        if (!a(context, (String) "android.permission.INTERNET")) {
            a((String) "isNetworkAvailable : lost permission:android.permission.INTERNET");
            return false;
        } else if (a(context, (String) "android.permission.ACCESS_NETWORK_STATE")) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            try {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e.getMessage());
                a(sb.toString());
                networkInfo = null;
            }
            if (networkInfo != null && networkInfo.isAvailable()) {
                return true;
            }
            a((String) "isNetworkAvailable : Network error");
            return false;
        } else {
            a((String) "isNetworkAvailable : lost permission: android.permission.ACCESS_NETWORK_STATE");
            return false;
        }
    }

    public static String a() {
        return Long.valueOf(System.currentTimeMillis()).toString();
    }

    public static String d(Context context) {
        if (context == null) {
            return "";
        }
        String str = (String) ewp.b(context, "manual_setting_appkey", "");
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("HMT_APPKEY");
                if (!TextUtils.isEmpty(string)) {
                    try {
                        return string.toString().trim();
                    } catch (Exception e) {
                        Exception exc = e;
                        str = string;
                        e = exc;
                        a((String) "getAppKey exception : Could not read HMT_APPKEY meta-data from AndroidManifest.xml");
                        StringBuilder sb = new StringBuilder("Collected:");
                        sb.append(e.getMessage());
                        a(sb.toString());
                        return str;
                    }
                } else {
                    a((String) "getAppKey : Could not read HMT_APPKEY meta-data from AndroidManifest.xml.");
                }
            }
        } catch (Exception e2) {
            e = e2;
        }
        return str;
    }

    public static String e(Context context) {
        if (context == null) {
            return "";
        }
        String str = (String) ewp.b(context, "manual_setting_channel_id", "");
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        String str2 = "null";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("HMT_CHANNEL");
                if (string != null) {
                    try {
                        return string.toString().trim();
                    } catch (Exception e) {
                        Exception exc = e;
                        str2 = string;
                        e = exc;
                        a((String) "getChannel : Could not read HMT_CHANNEL meta-data from AndroidManifest.xml.");
                        StringBuilder sb = new StringBuilder("Collected:");
                        sb.append(e.getMessage());
                        a(sb.toString());
                        return str2;
                    }
                }
            }
        } catch (Exception e2) {
            e = e2;
            a((String) "getChannel : Could not read HMT_CHANNEL meta-data from AndroidManifest.xml.");
            StringBuilder sb2 = new StringBuilder("Collected:");
            sb2.append(e.getMessage());
            a(sb2.toString());
            return str2;
        }
        return str2;
    }

    public static String a(Context context, int i) {
        List list;
        if (context == null) {
            return "";
        }
        try {
            String obj = context.toString();
            String substring = obj.substring(0, obj.indexOf(AUScreenAdaptTool.PREFIX_ID));
            String packageName = context.getPackageName();
            return (packageName == null || packageName.equals("") || i != 1) ? substring : substring.replaceFirst(packageName, "");
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
            if (a(context, (String) "android.permission.GET_TASKS")) {
                try {
                    list = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningTasks(1);
                } catch (Exception e2) {
                    StringBuilder sb2 = new StringBuilder("Collected:");
                    sb2.append(e2.getMessage());
                    a(sb2.toString());
                    list = null;
                }
                if (list != null && list.size() > 0) {
                    ComponentName componentName = ((RunningTaskInfo) list.get(0)).topActivity;
                    StringBuilder sb3 = new StringBuilder("getActivityName : ");
                    sb3.append(componentName.getClassName());
                    a(sb3.toString());
                    String className = componentName.getClassName();
                    String packageName2 = context.getPackageName();
                    return (packageName2 == null || packageName2.equals("") || i != 1) ? className : className.replaceFirst(packageName2, "");
                }
            } else {
                a((String) "getActivityName : lost permission:android.permission.GET_TASKS");
            }
            return "";
        }
    }

    public static String f(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
            return "";
        }
    }

    public static String b() {
        String str = VERSION.RELEASE;
        a("getOsVersion : ".concat(String.valueOf(str)));
        return str == null ? "" : str;
    }

    public static String g(Context context) {
        boolean z;
        String str;
        if (context == null) {
            return "";
        }
        String packageName = context.getPackageName();
        StringBuilder sb = new StringBuilder();
        sb.append(evd.r);
        sb.append(packageName);
        String str2 = (String) ewp.b(context, sb.toString(), evd.t, "");
        if (b(str2)) {
            return str2;
        }
        String str3 = (String) ewp.b(context, evd.s, evd.t, "");
        if (b(str3)) {
            return str3;
        }
        String w = w(context);
        boolean z2 = false;
        if (!TextUtils.isEmpty(w) && !w.equals("000000000000000") && !w.equals("00000000")) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            w = "";
        }
        String y = y(context);
        if (!TextUtils.isEmpty(y) && !y.equals("02:00:00:00:00:00") && !y.equals("00:00:00:00:00:00") && !y.equals("ff:ff:ff:ff:ff:ff")) {
            z2 = true;
        }
        if (!z2) {
            y = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(w);
        sb2.append(y);
        String sb3 = sb2.toString();
        if (TextUtils.isEmpty(sb3)) {
            str = p(context);
            if (TextUtils.isEmpty(str) || str.equals("9774d56d682e549c") || str.length() < 15) {
                str = new BigInteger(64, new SecureRandom()).toString(16);
            }
        } else {
            str = ewn.a(sb3);
        }
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(evd.r);
            sb4.append(packageName);
            ewp.a(context, sb4.toString(), evd.t, str);
            ewp.a(context, evd.s, evd.t, str);
        }
        return str;
    }

    private static boolean b(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals("0f607264fc6318a92b9e13c65db7cd3c") && !str.equals("3f0fe74b555ff95d563a2cfe3cb9c834") && !str.equals("5284047f4ffb4e04824a2fd1d1f0cd62") && !str.equals("528c8e6cd4a3c6598999a0e9df15ad32") && !str.equals("b21929f60cb26fe36e48926c33f1903c") && !str.equals("dd4b21e9ef71e1291183a46b913ae6f2") && !str.equals("feef34bbe6f4a1f343ad614c1b25f9b9")) {
            return true;
        }
        return false;
    }

    public static int h(Context context) {
        int intValue = ((Integer) ewp.b(context, "hmt_agent_online_setting", "hmtlocal_report_policy_server", Integer.valueOf(10000))).intValue();
        a("Policy mode from server is ".concat(String.valueOf(intValue)));
        if (intValue == 10000) {
            intValue = ((Integer) ewp.b(context, "hmt_agent_online_setting", "hmtlocal_report_policy_client", Integer.valueOf(10000))).intValue();
        }
        a("Plicy mode from client is ".concat(String.valueOf(intValue)));
        if (intValue == 10000) {
            return 0;
        }
        return intValue;
    }

    public static void a(Context context, int i, String str) {
        a("setReportPolicy: reportType = ".concat(String.valueOf(i)));
        if (i == 0 || i == 1) {
            synchronized (evd.f) {
                ewp.a(context, "hmt_agent_online_setting", "hmtlocal_report_policy_".concat(String.valueOf(str)), Integer.valueOf(i));
            }
        }
    }

    public static Boolean a(String[] strArr, String str) {
        if (strArr != null) {
            for (String equals : strArr) {
                if (equals.equals(str)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    public static void a(Context context, String[] strArr, String str) {
        if (!str.equals("server")) {
            str = "client";
        }
        String str2 = "";
        if (strArr != null && strArr.length > 0) {
            for (String append : strArr) {
                StringBuilder sb = new StringBuilder();
                sb.append(str2);
                sb.append(append);
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(MetaRecord.LOG_SEPARATOR);
                str2 = sb3.toString();
            }
        }
        synchronized (evd.f) {
            ewp.a(context, "hmt_agent_online_setting", "hmtlocal_untracked_".concat(String.valueOf(str)), str2);
        }
    }

    public static String[] i(Context context) {
        String str = (String) ewp.b(context, "hmt_agent_online_setting", "hmtlocal_untracked_server", "");
        if (TextUtils.isEmpty(str)) {
            str = (String) ewp.b(context, "hmt_agent_online_setting", "hmtlocal_untracked_client", "");
        }
        if (str == null || str == "") {
            return null;
        }
        return str.split(MetaRecord.LOG_SEPARATOR);
    }

    public static String[] j(Context context) {
        String str = (String) ewp.b(context, "hmt_untracked_activity", "");
        if (!TextUtils.isEmpty(str)) {
            return str.split(MetaRecord.LOG_SEPARATOR);
        }
        return null;
    }

    public static Boolean b(String[] strArr, String str) {
        if (strArr != null) {
            for (String equals : strArr) {
                if (equals.equals(str)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    public static String k(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return "";
        }
        String simOperator = telephonyManager.getSimOperator();
        if (simOperator == null) {
            simOperator = "";
        }
        return simOperator;
    }

    public static boolean l(Context context) {
        SensorManager sensorManager;
        if (!k()) {
            return false;
        }
        try {
            sensorManager = (SensorManager) context.getSystemService("sensor");
        } catch (NoSuchFieldError e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
            sensorManager = null;
        }
        if (sensorManager == null) {
            return false;
        }
        return true;
    }

    private static boolean k() {
        try {
            return !GlobalConstants.EXCEPTIONTYPE.equals(Build.MODEL) && !GlobalConstants.EXCEPTIONTYPE.equals(Build.PRODUCT) && !"generic".equals(Build.DEVICE);
        } catch (NoSuchFieldError e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
            return !GlobalConstants.EXCEPTIONTYPE.equals(Build.MODEL) && !GlobalConstants.EXCEPTIONTYPE.equals(Build.PRODUCT);
        }
    }

    public static boolean m(Context context) {
        if (a(context, (String) "android.permission.INTERNET")) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getTypeName().equals("WIFI")) {
                return true;
            }
            a((String) "isNetworkTypeWifi : Network not wifi");
            return false;
        }
        a((String) "isNetworkTypeWifi : lost permission = android.permission.INTERNET");
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
        if (r0.length() <= 0) goto L_0x002f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String n(android.content.Context r3) {
        /*
            java.lang.String r0 = ""
            if (r3 != 0) goto L_0x0007
            java.lang.String r3 = ""
            return r3
        L_0x0007:
            java.lang.String r1 = "manual_app_version"
            java.lang.String r2 = ""
            java.lang.Object r1 = defpackage.ewp.b(r3, r1, r2)     // Catch:{ Exception -> 0x0035 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Exception -> 0x0035 }
            boolean r0 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0032 }
            if (r0 != 0) goto L_0x0018
            return r1
        L_0x0018:
            android.content.pm.PackageManager r0 = r3.getPackageManager()     // Catch:{ Exception -> 0x0032 }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ Exception -> 0x0032 }
            r2 = 0
            android.content.pm.PackageInfo r3 = r0.getPackageInfo(r3, r2)     // Catch:{ Exception -> 0x0032 }
            java.lang.String r0 = r3.versionName     // Catch:{ Exception -> 0x0032 }
            if (r0 == 0) goto L_0x002f
            int r3 = r0.length()     // Catch:{ Exception -> 0x0035 }
            if (r3 > 0) goto L_0x004b
        L_0x002f:
            java.lang.String r3 = ""
            return r3
        L_0x0032:
            r3 = move-exception
            r0 = r1
            goto L_0x0036
        L_0x0035:
            r3 = move-exception
        L_0x0036:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)
            java.lang.String r3 = r3.getMessage()
            r1.append(r3)
            java.lang.String r3 = r1.toString()
            a(r3)
        L_0x004b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.euw.n(android.content.Context):java.lang.String");
    }

    public static void a(String str) {
        str.startsWith("Collected:");
        if (evd.b) {
            TextUtils.isEmpty(str);
        }
    }

    public static void c() {
        boolean z = evd.b;
    }

    public static String o(Context context) {
        return eva.a(context);
    }

    public static String d() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str2.startsWith(str)) {
            return c(str2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(c(str));
        sb.append(Token.SEPARATOR);
        sb.append(str2);
        return sb.toString();
    }

    private static String c(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char charAt = str.charAt(0);
        if (Character.isUpperCase(charAt)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(charAt));
        sb.append(str.substring(1));
        return sb.toString();
    }

    public static boolean e() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < 5) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(strArr[i]);
                sb.append("su");
                if (new File(sb.toString()).exists()) {
                    return true;
                }
                i++;
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder("Collected:");
                sb2.append(e.getMessage());
                a(sb2.toString());
            }
        }
        return false;
    }

    public static String p(Context context) {
        try {
            String string = Secure.getString(context.getContentResolver(), "android_id");
            if (string == null) {
                string = "";
            }
            return string;
        } catch (NullPointerException e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
            return "";
        }
    }

    public static String q(Context context) {
        if (!evi.b()) {
            evi.a(context);
        }
        String a2 = evi.a();
        return a2 == null ? "" : a2;
    }

    public static String r(Context context) {
        String q = q(context);
        return TextUtils.isEmpty(q) ? "" : ewn.a(q);
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0031 A[SYNTHETIC, Splitter:B:12:0x0031] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String s(android.content.Context r5) {
        /*
            r0 = 0
            android.content.Context r1 = r5.getApplicationContext()     // Catch:{ Exception -> 0x0016 }
            android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch:{ Exception -> 0x0016 }
            java.lang.String r2 = r5.getPackageName()     // Catch:{ Exception -> 0x0014 }
            r3 = 0
            android.content.pm.ApplicationInfo r2 = r1.getApplicationInfo(r2, r3)     // Catch:{ Exception -> 0x0014 }
            r0 = r2
            goto L_0x002d
        L_0x0014:
            r2 = move-exception
            goto L_0x0018
        L_0x0016:
            r2 = move-exception
            r1 = r0
        L_0x0018:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Collected:"
            r3.<init>(r4)
            java.lang.String r2 = r2.getMessage()
            r3.append(r2)
            java.lang.String r2 = r3.toString()
            a(r2)
        L_0x002d:
            java.lang.String r2 = ""
            if (r1 == 0) goto L_0x0066
            java.lang.CharSequence r0 = r1.getApplicationLabel(r0)     // Catch:{ Exception -> 0x0050 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x0050 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0050 }
            r1.<init>()     // Catch:{ Exception -> 0x0050 }
            r1.append(r0)     // Catch:{ Exception -> 0x0050 }
            java.lang.String r0 = "_"
            r1.append(r0)     // Catch:{ Exception -> 0x0050 }
            java.lang.String r5 = n(r5)     // Catch:{ Exception -> 0x0050 }
            r1.append(r5)     // Catch:{ Exception -> 0x0050 }
            java.lang.String r5 = r1.toString()     // Catch:{ Exception -> 0x0050 }
            goto L_0x0067
        L_0x0050:
            r5 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Collected:"
            r0.<init>(r1)
            java.lang.String r5 = r5.getMessage()
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            a(r5)
        L_0x0066:
            r5 = r2
        L_0x0067:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.euw.s(android.content.Context):java.lang.String");
    }

    public static String f() {
        return evd.l;
    }

    public static String g() {
        return evd.m;
    }

    public static String t(Context context) {
        int i;
        if (context == null) {
            return "";
        }
        try {
            i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
            i = 0;
        }
        return String.valueOf(i);
    }

    public static String u(Context context) {
        String str;
        if (context == null) {
            return "";
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            str = packageManager.getPackageInfo(context.getPackageName(), 0).applicationInfo.loadLabel(packageManager).toString();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
            str = "";
        }
        return str;
    }

    public static String v(Context context) {
        String str = "";
        String b = ewo.h().b(context);
        if (!TextUtils.isEmpty(b)) {
            str = ewf.a("mobileanalytics", b);
        }
        return str.toLowerCase();
    }

    public static String x(Context context) {
        String str = "";
        String y = y(context);
        if (!TextUtils.isEmpty(y)) {
            str = ewf.a("mobileanalytics", y);
        }
        return str.toLowerCase();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0055 A[Catch:{ Exception -> 0x0095 }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0056 A[Catch:{ Exception -> 0x0095 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String y(android.content.Context r5) {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = "android.permission.ACCESS_WIFI_STATE"
            boolean r1 = a(r5, r1)     // Catch:{ Exception -> 0x0095 }
            if (r1 == 0) goto L_0x008f
            java.lang.String r1 = "wifi"
            java.lang.Object r1 = r5.getSystemService(r1)     // Catch:{ Exception -> 0x0095 }
            android.net.wifi.WifiManager r1 = (android.net.wifi.WifiManager) r1     // Catch:{ Exception -> 0x0095 }
            if (r1 != 0) goto L_0x0016
            return r0
        L_0x0016:
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x0095 }
            r3 = 17
            if (r2 != r3) goto L_0x001f
            java.lang.String r1 = "02:00:00:00:00:00"
            goto L_0x005a
        L_0x001f:
            r2 = 0
            android.net.wifi.WifiInfo r1 = r1.getConnectionInfo()     // Catch:{ Exception -> 0x003c, NoSuchFieldError -> 0x0025 }
            goto L_0x0053
        L_0x0025:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0095 }
            java.lang.String r4 = "Collected:"
            r3.<init>(r4)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ Exception -> 0x0095 }
            r3.append(r1)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x0095 }
            a(r1)     // Catch:{ Exception -> 0x0095 }
            goto L_0x0052
        L_0x003c:
            r1 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0095 }
            java.lang.String r4 = "Collected:"
            r3.<init>(r4)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ Exception -> 0x0095 }
            r3.append(r1)     // Catch:{ Exception -> 0x0095 }
            java.lang.String r1 = r3.toString()     // Catch:{ Exception -> 0x0095 }
            a(r1)     // Catch:{ Exception -> 0x0095 }
        L_0x0052:
            r1 = r2
        L_0x0053:
            if (r1 != 0) goto L_0x0056
            return r0
        L_0x0056:
            java.lang.String r1 = r1.getMacAddress()     // Catch:{ Exception -> 0x0095 }
        L_0x005a:
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0095 }
            if (r2 == 0) goto L_0x0061
            return r0
        L_0x0061:
            java.lang.String r2 = "02:00:00:00:00:00"
            boolean r2 = r1.equals(r2)     // Catch:{ Exception -> 0x0095 }
            if (r2 == 0) goto L_0x008a
            java.lang.String r1 = M(r5)     // Catch:{ Exception -> 0x0095 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Exception -> 0x0095 }
            if (r2 != 0) goto L_0x0078
            java.lang.String r5 = r1.toLowerCase()     // Catch:{ Exception -> 0x0095 }
            return r5
        L_0x0078:
            java.lang.String r5 = N(r5)     // Catch:{ Exception -> 0x0095 }
            boolean r1 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0095 }
            if (r1 != 0) goto L_0x0087
            java.lang.String r5 = r5.toLowerCase()     // Catch:{ Exception -> 0x0095 }
            return r5
        L_0x0087:
            java.lang.String r5 = "02:00:00:00:00:00"
            return r5
        L_0x008a:
            java.lang.String r5 = r1.toLowerCase()     // Catch:{ Exception -> 0x0095 }
            return r5
        L_0x008f:
            java.lang.String r5 = "get_mac : lost permission = android.permission.ACCESS_WIFI_STATE"
            a(r5)     // Catch:{ Exception -> 0x0095 }
            goto L_0x00ab
        L_0x0095:
            r5 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)
            java.lang.String r5 = r5.getMessage()
            r1.append(r5)
            java.lang.String r5 = r1.toString()
            a(r5)
        L_0x00ab:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.euw.y(android.content.Context):java.lang.String");
    }

    @SuppressLint({"NewApi"})
    private static String M(Context context) {
        if (a(context, (String) "android.permission.INTERNET")) {
            try {
                for (T t : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                    if (t.getName().equalsIgnoreCase("wlan0")) {
                        byte[] hardwareAddress = t.getHardwareAddress();
                        if (hardwareAddress == null) {
                            return "";
                        }
                        StringBuilder sb = new StringBuilder();
                        for (byte valueOf : hardwareAddress) {
                            sb.append(String.format("%02X:", new Object[]{Byte.valueOf(valueOf)}));
                        }
                        if (sb.length() > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        return sb.toString();
                    }
                }
            } catch (Exception e) {
                StringBuilder sb2 = new StringBuilder("Collected:");
                sb2.append(e.getMessage());
                a(sb2.toString());
            }
        } else {
            a((String) "getAdressMacByInterface : need permission = android.permission.INTERNET");
        }
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x008a A[SYNTHETIC, Splitter:B:25:0x008a] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x009b A[SYNTHETIC, Splitter:B:31:0x009b] */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String N(android.content.Context r9) {
        /*
            java.lang.String r0 = ""
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "/sys/class/net/"
            r1.<init>(r2)
            java.lang.String r2 = l()
            r1.append(r2)
            java.lang.String r2 = "/address"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.io.File r2 = new java.io.File
            r2.<init>(r1)
            boolean r1 = r2.exists()
            if (r1 == 0) goto L_0x00b6
            boolean r1 = r2.isFile()
            if (r1 == 0) goto L_0x00b6
            r9 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x006f, all -> 0x006b }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Exception -> 0x006f, all -> 0x006b }
            r3.<init>(r2)     // Catch:{ Exception -> 0x006f, all -> 0x006b }
            r1.<init>(r3)     // Catch:{ Exception -> 0x006f, all -> 0x006b }
        L_0x0035:
            java.lang.String r9 = r1.readLine()     // Catch:{ Exception -> 0x0069 }
            if (r9 == 0) goto L_0x004c
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0069 }
            r2.<init>()     // Catch:{ Exception -> 0x0069 }
            r2.append(r0)     // Catch:{ Exception -> 0x0069 }
            r2.append(r9)     // Catch:{ Exception -> 0x0069 }
            java.lang.String r9 = r2.toString()     // Catch:{ Exception -> 0x0069 }
            r0 = r9
            goto L_0x0035
        L_0x004c:
            r1.close()     // Catch:{ IOException -> 0x0051 }
            goto L_0x0122
        L_0x0051:
            r9 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)
        L_0x0059:
            java.lang.String r9 = r9.getMessage()
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            a(r9)
            goto L_0x0122
        L_0x0069:
            r9 = move-exception
            goto L_0x0073
        L_0x006b:
            r0 = move-exception
            r1 = r9
            r9 = r0
            goto L_0x0099
        L_0x006f:
            r1 = move-exception
            r8 = r1
            r1 = r9
            r9 = r8
        L_0x0073:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0098 }
            java.lang.String r3 = "Collected:"
            r2.<init>(r3)     // Catch:{ all -> 0x0098 }
            java.lang.String r9 = r9.getMessage()     // Catch:{ all -> 0x0098 }
            r2.append(r9)     // Catch:{ all -> 0x0098 }
            java.lang.String r9 = r2.toString()     // Catch:{ all -> 0x0098 }
            a(r9)     // Catch:{ all -> 0x0098 }
            if (r1 == 0) goto L_0x0122
            r1.close()     // Catch:{ IOException -> 0x008f }
            goto L_0x0122
        L_0x008f:
            r9 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)
            goto L_0x0059
        L_0x0098:
            r9 = move-exception
        L_0x0099:
            if (r1 == 0) goto L_0x00b5
            r1.close()     // Catch:{ IOException -> 0x009f }
            goto L_0x00b5
        L_0x009f:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)
            java.lang.String r0 = r0.getMessage()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            a(r0)
        L_0x00b5:
            throw r9
        L_0x00b6:
            java.lang.String r1 = "android.permission.INTERNET"
            boolean r9 = a(r9, r1)
            if (r9 == 0) goto L_0x011d
            java.lang.String r9 = l()     // Catch:{ Exception -> 0x0106 }
            java.net.NetworkInterface r9 = java.net.NetworkInterface.getByName(r9)     // Catch:{ Exception -> 0x0106 }
            if (r9 == 0) goto L_0x0122
            byte[] r9 = r9.getHardwareAddress()     // Catch:{ Exception -> 0x0106 }
            if (r9 != 0) goto L_0x00d1
            java.lang.String r9 = ""
            return r9
        L_0x00d1:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0106 }
            r1.<init>()     // Catch:{ Exception -> 0x0106 }
            int r2 = r9.length     // Catch:{ Exception -> 0x0106 }
            r3 = 0
            r4 = 0
        L_0x00d9:
            r5 = 1
            if (r4 >= r2) goto L_0x00f2
            byte r6 = r9[r4]     // Catch:{ Exception -> 0x0106 }
            java.lang.String r7 = "%02X:"
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x0106 }
            java.lang.Byte r6 = java.lang.Byte.valueOf(r6)     // Catch:{ Exception -> 0x0106 }
            r5[r3] = r6     // Catch:{ Exception -> 0x0106 }
            java.lang.String r5 = java.lang.String.format(r7, r5)     // Catch:{ Exception -> 0x0106 }
            r1.append(r5)     // Catch:{ Exception -> 0x0106 }
            int r4 = r4 + 1
            goto L_0x00d9
        L_0x00f2:
            int r9 = r1.length()     // Catch:{ Exception -> 0x0106 }
            if (r9 <= 0) goto L_0x0100
            int r9 = r1.length()     // Catch:{ Exception -> 0x0106 }
            int r9 = r9 - r5
            r1.deleteCharAt(r9)     // Catch:{ Exception -> 0x0106 }
        L_0x0100:
            java.lang.String r9 = r1.toString()     // Catch:{ Exception -> 0x0106 }
            r0 = r9
            goto L_0x0122
        L_0x0106:
            r9 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Collected:"
            r1.<init>(r2)
            java.lang.String r9 = r9.getMessage()
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            a(r9)
            goto L_0x0122
        L_0x011d:
            java.lang.String r9 = "getAddressMacByFile : need permission = android.permission.INTERNET"
            a(r9)
        L_0x0122:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.euw.N(android.content.Context):java.lang.String");
    }

    private static String l() {
        String str = "";
        if (VERSION.SDK_INT > 27) {
            return str;
        }
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getDeclaredMethod("get", new Class[]{String.class, String.class}).invoke(cls.newInstance(), new Object[]{"wifi.interface", "wlan0"});
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("Collected:");
            sb.append(e.getMessage());
            a(sb.toString());
        }
        return str;
    }

    public static String B(Context context) {
        String w = w(context);
        return (w == null || w.equals("")) ? "" : ewn.a(w);
    }

    public static String C(Context context) {
        String p = p(context);
        if (!p.equals("")) {
            return ewn.a(p);
        }
        return "";
    }

    public static String D(Context context) {
        String y = y(context);
        if (y == null || y.equals("")) {
            return "";
        }
        return ewn.a(y.replace(":", "").toUpperCase());
    }

    public static String E(Context context) {
        String y = y(context);
        if (y == null || y.equals("")) {
            return "";
        }
        return ewn.a(y.toUpperCase());
    }

    public static boolean F(Context context) {
        return context instanceof Application;
    }

    public static void G(Context context) {
        long longValue = ((Long) ewp.b(context, "hmt_data_clean_time", Long.valueOf(0))).longValue();
        if (longValue != 0) {
            evd.e = longValue * 60 * 60 * 1000;
        }
    }

    public static String H(Context context) {
        String d = d(context);
        if (d == null || d.equals("")) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(evd.j);
        sb.append(d);
        sb.append(".config");
        sb.append(eve.a());
        return sb.toString();
    }

    public static void I(Context context) {
        if (((Long) ewp.b(context, "first_init_time", Long.valueOf(0))).longValue() == 0) {
            ewp.a(context, (String) "first_init_time", (Object) Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static boolean J(Context context) {
        long longValue = ((Long) ewp.b(context, "first_init_time", Long.valueOf(0))).longValue();
        boolean z = false;
        if (longValue == 0) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis < longValue) {
            return false;
        }
        if (currentTimeMillis - longValue > 432000000) {
            z = true;
        }
        return z;
    }

    public static int K(Context context) {
        if (context == null) {
            return -1;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY);
        if (windowManager == null) {
            return -1;
        }
        Display defaultDisplay = windowManager.getDefaultDisplay();
        if (defaultDisplay == null) {
            return -1;
        }
        return defaultDisplay.getRotation();
    }

    public static boolean L(Context context) {
        boolean z = VERSION.SDK_INT >= 23 ? Process.is64Bit() : VERSION.SDK_INT >= 21 ? O(context) : false;
        StringBuilder sb = new StringBuilder("Current Process is ");
        sb.append(z ? "6" : "3");
        a(sb.toString());
        return z;
    }

    private static boolean O(Context context) {
        try {
            ClassLoader classLoader = context.getClassLoader();
            Object invoke = ClassLoader.class.getDeclaredMethod("findLibrary", new Class[]{String.class}).invoke(classLoader, new Object[]{"art"});
            if (invoke != null) {
                return ((String) invoke).contains("lib64");
            }
            return false;
        } catch (Throwable unused) {
            return P(context);
        }
    }

    private static boolean P(Context context) {
        PackageManager packageManager = context.getPackageManager();
        boolean z = false;
        if (packageManager == null || context == null) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            Field field = ApplicationInfo.class.getField("primaryCpuAbi");
            field.setAccessible(true);
            String str = (String) field.get(applicationInfo);
            if (!TextUtils.isEmpty(str) && str.equals("arm64-v8a")) {
                z = true;
            }
        } catch (Throwable unused) {
            boolean z2 = evd.b;
        }
        return z;
    }

    @SuppressLint({"MissingPermission"})
    public static String b(Context context, int i) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return "";
        }
        String str = "";
        if (VERSION.SDK_INT >= 26) {
            if (a(context, (String) "android.permission.READ_PHONE_STATE")) {
                str = telephonyManager.getImei(i);
            }
        } else if (VERSION.SDK_INT >= 21) {
            try {
                Method method = telephonyManager.getClass().getMethod("getImei", new Class[]{Integer.TYPE});
                if (method != null) {
                    str = (String) method.invoke(telephonyManager, new Object[]{Integer.valueOf(i)});
                }
            } catch (Throwable th) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(th.getMessage());
                a(sb.toString());
            }
        } else {
            a((String) "Collected:Can't call getImei method under Android L!");
        }
        return str;
    }

    @SuppressLint({"MissingPermission"})
    public static String w(Context context) {
        String str;
        String str2 = (String) ewp.b(context, "manual_setting_imei", "");
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        if (a(context, (String) "android.permission.READ_PHONE_STATE")) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return "";
            }
            try {
                str = telephonyManager.getDeviceId();
            } catch (Exception e) {
                StringBuilder sb = new StringBuilder("Collected:");
                sb.append(e.getMessage());
                a(sb.toString());
            }
        } else {
            a((String) "get_imei : lost permission = android.permission.READ_PHONE_STATE");
            str = str2;
        }
        return str == null ? "" : str;
    }

    public static int z(Context context) {
        if (a(context, (String) "android.permission.READ_PHONE_STATE")) {
            return ((TelephonyManager) context.getSystemService("phone")).getPhoneType();
        }
        a((String) "getPhoneType : lost permission = android.permission.READ_PHONE_STATE");
        return 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0039  */
    @android.annotation.SuppressLint({"MissingPermission"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String A(android.content.Context r2) {
        /*
            java.lang.String r0 = "android.permission.READ_PHONE_STATE"
            boolean r0 = a(r2, r0)     // Catch:{ Exception -> 0x0020 }
            if (r0 == 0) goto L_0x001a
            java.lang.String r0 = "phone"
            java.lang.Object r2 = r2.getSystemService(r0)     // Catch:{ Exception -> 0x0020 }
            android.telephony.TelephonyManager r2 = (android.telephony.TelephonyManager) r2     // Catch:{ Exception -> 0x0020 }
            if (r2 != 0) goto L_0x0015
            java.lang.String r2 = ""
            return r2
        L_0x0015:
            java.lang.String r2 = r2.getSubscriberId()     // Catch:{ Exception -> 0x0020 }
            goto L_0x0037
        L_0x001a:
            java.lang.String r2 = "getImsi : lost permission = android.permission.READ_PHONE_STATE"
            a(r2)     // Catch:{ Exception -> 0x0020 }
            goto L_0x0036
        L_0x0020:
            r2 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Collected:"
            r0.<init>(r1)
            java.lang.String r2 = r2.getMessage()
            r0.append(r2)
            java.lang.String r2 = r0.toString()
            a(r2)
        L_0x0036:
            r2 = 0
        L_0x0037:
            if (r2 != 0) goto L_0x003b
            java.lang.String r2 = ""
        L_0x003b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.euw.A(android.content.Context):java.lang.String");
    }
}
