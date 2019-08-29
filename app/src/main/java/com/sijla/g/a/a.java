package com.sijla.g.a;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.provider.Settings.Secure;
import android.system.Os;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.sijla.g.b;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class a {
    public static String a = "lipayG";
    private static int b;

    /* renamed from: com.sijla.g.a.a$a reason: collision with other inner class name */
    class C0062a implements FileFilter {
        C0062a() {
        }

        public boolean accept(File file) {
            return Pattern.matches("cpu[0-9]+", file.getName());
        }
    }

    public static String a(Context context, boolean z) {
        return "-";
    }

    public static String c() {
        return "-";
    }

    public static String d() {
        return "";
    }

    public static String e() {
        return "";
    }

    public static String f() {
        return "";
    }

    public static String g() {
        return "";
    }

    public static int y(Context context) {
        return -1;
    }

    public static String a(Context context) {
        StringBuilder sb = new StringBuilder("QM_ON_APP_FOREGROUND_");
        sb.append(context.getPackageName());
        return sb.toString();
    }

    public static String b(Context context) {
        StringBuilder sb = new StringBuilder("QM_ON_APP_BACKGROUND_");
        sb.append(context.getPackageName());
        return sb.toString();
    }

    public static String c(Context context) {
        StringBuilder sb = new StringBuilder("QM_ON_APP_NETWORK_ACTION_");
        sb.append(context.getPackageName());
        return sb.toString();
    }

    public static String d(Context context) {
        try {
            if (d(context, (String) "android.permission.GET_TASKS")) {
                ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
                if (activityManager != null) {
                    List<RunningTaskInfo> runningTasks = activityManager.getRunningTasks(1);
                    if (runningTasks != null && !runningTasks.isEmpty()) {
                        return runningTasks.get(0).topActivity.getPackageName();
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }

    public static boolean a() {
        boolean z = false;
        try {
            String externalStorageState = Environment.getExternalStorageState();
            if (externalStorageState != null) {
                if (externalStorageState.equals("mounted") && !externalStorageState.equals("mounted_ro")) {
                    z = true;
                }
                return z;
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static boolean e(Context context) {
        try {
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            if (VERSION.SDK_INT >= 20) {
                return powerManager.isInteractive();
            }
            return powerManager.isScreenOn();
        } catch (Exception unused) {
            return true;
        }
    }

    public static boolean b() {
        String[] strArr = {"/system/bin/", "/system/xbin/", "/data/local/xbin/", "/data/local/bin/", "/system/sd/xbin/"};
        for (int i = 0; i < 5; i++) {
            String str = strArr[i];
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("su");
            if (new File(sb.toString()).exists()) {
                return true;
            }
        }
        return false;
    }

    public static boolean a(Context context, String str) {
        try {
            return a(context.getPackageManager().getPackageInfo(str, 128).applicationInfo);
        } catch (Exception unused) {
            return true;
        }
    }

    public static boolean a(ApplicationInfo applicationInfo) {
        if (applicationInfo == null || (applicationInfo.flags & 128) != 0 || (applicationInfo.flags & 1) == 0) {
            return true;
        }
        return false;
    }

    public static String f(Context context) {
        int myPid = Process.myPid();
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (RunningAppProcessInfo next : runningAppProcesses) {
            if (next.pid == myPid) {
                return next.processName;
            }
        }
        return null;
    }

    public static boolean g(Context context) {
        if (!com.sijla.g.b.a.a().b()) {
            return false;
        }
        try {
            NetworkInfo B = B(context);
            return B != null && B.isConnected() && B.getState() == State.CONNECTED;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean h(Context context) {
        NetworkInfo B = B(context);
        return B != null && B.isConnected() && B.getType() == 1;
    }

    public static String i(Context context) {
        try {
            if (!h(context)) {
                return p(context);
            }
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    String ssid = connectionInfo.getSSID();
                    return b.a(ssid) ? "" : ssid.replace("'", "").replace("\"", "");
                }
            }
            return "";
        } catch (Exception unused) {
        }
    }

    private static NetworkInfo B(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo();
        }
        return null;
    }

    public static String b(Context context, String str) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String trim = packageManager.getPackageInfo(str, 0).applicationInfo.loadLabel(packageManager).toString().trim();
            return trim.length() > 0 ? trim : "";
        } catch (Exception unused) {
            return "";
        }
    }

    public static String a(String str, Context context) {
        String str2;
        try {
            str2 = context.getPackageManager().getPackageInfo(str, 0).versionName.replace(Token.SEPARATOR, "");
        } catch (Exception unused) {
            str2 = "";
        }
        return str2 == null ? "" : str2;
    }

    public static String j(Context context) {
        return a(context.getPackageName(), context);
    }

    @SuppressLint({"HardwareIds"})
    public static String k(Context context) {
        String str;
        try {
            str = Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception unused) {
            str = null;
        }
        return TextUtils.isEmpty(str) ? "-" : str;
    }

    public static String l(Context context) {
        return a(context, false);
    }

    public static String m(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (5 == telephonyManager.getSimState()) {
                String simOperator = telephonyManager.getSimOperator();
                if (simOperator != null) {
                    if (simOperator.trim().length() > 0) {
                        return simOperator;
                    }
                }
                return "unknown";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-";
    }

    public static String n(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        StringBuilder sb = new StringBuilder();
        sb.append(height);
        sb.append("*");
        sb.append(width);
        return sb.toString();
    }

    public static String o(Context context) {
        int i;
        int i2;
        int i3;
        Display defaultDisplay = ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        StringBuilder sb = new StringBuilder();
        int i4 = displayMetrics.widthPixels;
        int i5 = displayMetrics.heightPixels;
        try {
            i = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
            try {
                i2 = ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
            } catch (Exception unused) {
                i2 = i5;
                Point point = new Point();
                Display.class.getMethod("getRealSize", new Class[]{Point.class}).invoke(defaultDisplay, new Object[]{point});
                i3 = point.x;
                i2 = point.y;
                double d = (double) (((float) i3) / displayMetrics.xdpi);
                double d2 = (double) (((float) i2) / displayMetrics.ydpi);
                sb.append(String.format("%.1f", new Object[]{Double.valueOf(Math.sqrt((d * d) + (d2 * d2)))}));
                return sb.toString();
            }
        } catch (Exception unused2) {
            i = i4;
            i2 = i5;
            Point point2 = new Point();
            Display.class.getMethod("getRealSize", new Class[]{Point.class}).invoke(defaultDisplay, new Object[]{point2});
            i3 = point2.x;
            i2 = point2.y;
            double d3 = (double) (((float) i3) / displayMetrics.xdpi);
            double d22 = (double) (((float) i2) / displayMetrics.ydpi);
            sb.append(String.format("%.1f", new Object[]{Double.valueOf(Math.sqrt((d3 * d3) + (d22 * d22)))}));
            return sb.toString();
        }
        try {
            Point point22 = new Point();
            Display.class.getMethod("getRealSize", new Class[]{Point.class}).invoke(defaultDisplay, new Object[]{point22});
            i3 = point22.x;
            try {
                i2 = point22.y;
            } catch (Exception unused3) {
            }
        } catch (Exception unused4) {
            i3 = i;
        }
        double d32 = (double) (((float) i3) / displayMetrics.xdpi);
        double d222 = (double) (((float) i2) / displayMetrics.ydpi);
        sb.append(String.format("%.1f", new Object[]{Double.valueOf(Math.sqrt((d32 * d32) + (d222 * d222)))}));
        return sb.toString();
    }

    public static String p(Context context) {
        String str;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return "";
        }
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case 0:
                str = "UNKNOWN";
                break;
            case 1:
                str = "GPRS";
                break;
            case 2:
                str = "EDGE";
                break;
            case 3:
                str = "UMTS";
                break;
            case 4:
                str = "CDMA";
                break;
            case 5:
                str = "EVDO_0";
                break;
            case 6:
                str = "EVDO_A";
                break;
            case 7:
                str = "1XRTT";
                break;
            case 8:
                str = "HSDPA";
                break;
            case 9:
                str = "HSUPA";
                break;
            case 10:
                str = "HSPA";
                break;
            case 11:
                str = "IDEN";
                break;
            case 12:
                str = "EVDO_B";
                break;
            case 13:
                str = "LTE";
                break;
            case 14:
                str = "EHRPD";
                break;
            case 15:
                str = "HSPAP";
                break;
            case 16:
                str = "GSM";
                break;
            case 17:
                str = "TD_SCDMA";
                break;
            case 18:
                str = "IWLAN";
                break;
            case 20:
                str = "NR";
                break;
            default:
                str = String.valueOf(networkType);
                break;
        }
        return str;
    }

    public static String q(Context context) {
        return b(context, false);
    }

    public static String b(Context context, boolean z) {
        String str = "emt";
        try {
            if (d(context, (String) "android.permission.READ_PHONE_STATE")) {
                str = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            }
        } catch (Exception unused) {
        }
        return str == null ? "emt" : str;
    }

    public static String r(Context context) {
        return c(context, false);
    }

    public static String c(Context context, boolean z) {
        String str;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (VERSION.SDK_INT >= 23 && context.checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return "";
            }
            str = telephonyManager.getSubscriberId();
            if (str == null) {
                str = "";
            }
            return str;
        } catch (Exception unused) {
            str = "";
        }
    }

    public static String s(Context context) {
        return d(context, false);
    }

    public static String d(Context context, boolean z) {
        return C(context);
    }

    private static String C(Context context) {
        String b2 = b(D(context));
        if (b2 != null && !b2.startsWith("02:00:00")) {
            return b2.toUpperCase();
        }
        String E = E(context);
        if (E != null && !E.startsWith("02:00:00")) {
            return E.toUpperCase();
        }
        if (VERSION.SDK_INT < 26) {
            String k = k();
            if (k != null && !k.startsWith("02:00:00")) {
                return k.toUpperCase();
            }
        }
        return "";
    }

    private static String D(Context context) {
        String a2 = a((String) "wlan0");
        if (a2 != null) {
            return a2;
        }
        String a3 = a((String) "eth0");
        if (a3 != null) {
            return a3;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                String macAddress = wifiManager.getConnectionInfo().getMacAddress();
                if (macAddress != null) {
                    return macAddress;
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    private static String a(String str) {
        try {
            StringBuilder sb = new StringBuilder("/sys/class/net/");
            sb.append(str);
            sb.append("/address");
            String sb2 = sb.toString();
            StringBuilder sb3 = new StringBuilder(1000);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(sb2), 1024);
            char[] cArr = new char[1024];
            while (true) {
                int read = bufferedReader.read(cArr);
                if (read != -1) {
                    sb3.append(String.valueOf(cArr, 0, read));
                } else {
                    bufferedReader.close();
                    return sb3.toString();
                }
            }
        } catch (IOException unused) {
            return null;
        }
    }

    private static String E(Context context) {
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
        } catch (Throwable unused) {
        }
        return null;
    }

    private static String k() {
        String str = null;
        try {
            NetworkInterface byName = NetworkInterface.getByName(l());
            if (byName != null) {
                byte[] hardwareAddress = byName.getHardwareAddress();
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
                str = sb.toString();
            }
        } catch (Exception unused) {
        }
        return str;
    }

    private static String l() {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getDeclaredMethod("get", new Class[]{String.class, String.class}).invoke(cls.newInstance(), new Object[]{"wifi.interface", "wlan0"});
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String b(String str) {
        if (str == null) {
            return null;
        }
        String replaceAll = str.replaceAll("\\s", "");
        if (TextUtils.isEmpty(replaceAll)) {
            return null;
        }
        return replaceAll;
    }

    public static String t(Context context) {
        try {
            String simSerialNumber = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            return (simSerialNumber == null || simSerialNumber.trim().length() <= 0) ? "" : simSerialNumber;
        } catch (Exception unused) {
            return "";
        }
    }

    public static List<String> u(Context context) {
        ArrayList arrayList = new ArrayList();
        List<JSONObject> v = v(context);
        if (v != null) {
            for (JSONObject optString : v) {
                arrayList.add(optString.optString("appid", ""));
            }
        }
        return arrayList;
    }

    public static List<JSONObject> v(Context context) {
        return new ArrayList();
    }

    public static JSONObject a(PackageManager packageManager, String str) {
        JSONObject jSONObject = new JSONObject();
        if (packageManager != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
                long j = packageInfo.firstInstallTime;
                long j2 = packageInfo.lastUpdateTime;
                jSONObject.put("appid", str);
                jSONObject.put("instime", j);
                jSONObject.put("uptime", j2);
                jSONObject.put("installer", packageManager.getInstallerPackageName(str));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    public static boolean c(Context context, String str) {
        try {
            if (context.getPackageManager().getPackageInfo(str, 0) != null) {
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002f, code lost:
        r7 = r4;
        r4 = r0;
        r0 = r1;
        r1 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        r1 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002e, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0034 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x0013] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String h() {
        /*
            java.lang.String r0 = "/proc/meminfo"
            java.lang.String r1 = ""
            r2 = 0
            r3 = 1
            r4 = 0
            java.io.FileReader r5 = new java.io.FileReader     // Catch:{ Exception -> 0x003e }
            r5.<init>(r0)     // Catch:{ Exception -> 0x003e }
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch:{ Exception -> 0x003e }
            r6 = 8192(0x2000, float:1.14794E-41)
            r0.<init>(r5, r6)     // Catch:{ Exception -> 0x003e }
            java.lang.String r4 = r0.readLine()     // Catch:{ Exception -> 0x0036, all -> 0x0034 }
            java.lang.String r1 = ":"
            int r1 = r4.indexOf(r1)     // Catch:{ Exception -> 0x002e, all -> 0x0034 }
            int r1 = r1 + r3
            java.lang.String r1 = r4.substring(r1)     // Catch:{ Exception -> 0x002e, all -> 0x0034 }
            java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x002e, all -> 0x0034 }
            java.io.Closeable[] r3 = new java.io.Closeable[r3]
            r3[r2] = r0
            com.sijla.g.b.a(r3)
            goto L_0x0049
        L_0x002e:
            r1 = move-exception
            r7 = r4
            r4 = r0
            r0 = r1
            r1 = r7
            goto L_0x003f
        L_0x0034:
            r1 = move-exception
            goto L_0x004a
        L_0x0036:
            r4 = move-exception
            r7 = r4
            r4 = r0
            r0 = r7
            goto L_0x003f
        L_0x003b:
            r1 = move-exception
            r0 = r4
            goto L_0x004a
        L_0x003e:
            r0 = move-exception
        L_0x003f:
            r0.printStackTrace()     // Catch:{ all -> 0x003b }
            java.io.Closeable[] r0 = new java.io.Closeable[r3]
            r0[r2] = r4
            com.sijla.g.b.a(r0)
        L_0x0049:
            return r1
        L_0x004a:
            java.io.Closeable[] r3 = new java.io.Closeable[r3]
            r3[r2] = r0
            com.sijla.g.b.a(r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sijla.g.a.a.h():java.lang.String");
    }

    public static String w(Context context) {
        return a() ? Formatter.formatFileSize(context, Environment.getExternalStorageDirectory().getTotalSpace()) : "0M";
    }

    public static long i() {
        if (a()) {
            return (Environment.getExternalStorageDirectory().getFreeSpace() / 1024) / 1024;
        }
        return 0;
    }

    public static String x(Context context) {
        try {
            if (VERSION.SDK_INT < 26) {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                return (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.serialno", "unknown"});
            } else if (context.checkSelfPermission("android.permission.READ_PHONE_STATE") != 0) {
                return "";
            } else {
                return Build.getSerial();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int j() {
        if (b != 0) {
            return b;
        }
        try {
            b = new File("/sys/devices/system/cpu/").listFiles(new C0062a()).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (b <= 0) {
            b = Runtime.getRuntime().availableProcessors();
        }
        if (b <= 0) {
            b = 1;
        }
        return b;
    }

    public static boolean d(Context context, String str) {
        try {
            return VERSION.SDK_INT >= 21 ? context != null && context.checkPermission(str, Process.myPid(), Os.getuid()) == 0 : context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String z(Context context) {
        String str = "";
        try {
            NetworkInfo B = B(context);
            if (B != null && B.isConnected()) {
                if (B.getType() == 1) {
                    return "WIFI";
                }
                if (B.getType() == 0) {
                    String subtypeName = B.getSubtypeName();
                    switch (B.getSubtype()) {
                        case 1:
                        case 2:
                        case 4:
                        case 7:
                        case 11:
                            return "2G";
                        case 3:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                        case 12:
                        case 14:
                        case 15:
                            return "3G";
                        case 13:
                            return "4G";
                        default:
                            if (!subtypeName.equalsIgnoreCase("TD-SCDMA") && !subtypeName.equalsIgnoreCase("WCDMA")) {
                                if (!subtypeName.equalsIgnoreCase("CDMA2000")) {
                                    str = subtypeName;
                                    break;
                                }
                            }
                            return "3G";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String A(Context context) {
        try {
            return d.a(new String(context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
