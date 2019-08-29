package defpackage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.alipay.mobile.beehive.capture.service.CaptureParam;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.mobile.tinyappcommon.template.TemplateTinyApp;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.sina.weibo.BuildConfig;
import com.taobao.accs.common.Constants;
import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.util.Collections;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: fbj reason: default package */
/* compiled from: MfpBuilder */
public final class fbj {

    /* renamed from: fbj$a */
    /* compiled from: MfpBuilder */
    static final class a {
        Intent a;

        /* synthetic */ a(Context context, byte b) {
            this(context);
        }

        private a(Context context) {
            this.a = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        }
    }

    public static String a(Context context) {
        try {
            return new String(c(context).getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    private static String b(Context context) {
        StringBuilder sb = new StringBuilder();
        String packageName = context.getPackageName();
        String str = "ssosdk";
        if (!TextUtils.isEmpty(packageName) && packageName.contains(BuildConfig.APPLICATION_ID)) {
            str = "weibo";
        }
        sb.append(Build.MANUFACTURER);
        sb.append("-");
        sb.append(Build.MODEL);
        sb.append("__");
        sb.append(str);
        sb.append("__");
        try {
            sb.append("1.0".replaceAll("\\s+", "_"));
        } catch (Exception unused) {
            sb.append("unknown");
        }
        sb.append("__android__android");
        sb.append(VERSION.RELEASE);
        return sb.toString();
    }

    private static String c(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            String a2 = a();
            if (!TextUtils.isEmpty(a2)) {
                jSONObject.put("os", a2);
            }
            String d = d(context);
            if (!TextUtils.isEmpty(d)) {
                jSONObject.put(Constants.KEY_IMEI, d);
            }
            String e = e(context);
            if (!TextUtils.isEmpty(e)) {
                jSONObject.put("meid", e);
            }
            String f = f(context);
            if (!TextUtils.isEmpty(f)) {
                jSONObject.put(Constants.KEY_IMSI, f);
            }
            String g = g(context);
            if (!TextUtils.isEmpty(g)) {
                jSONObject.put("mac", g);
            }
            String h = h(context);
            if (!TextUtils.isEmpty(h)) {
                jSONObject.put("iccid", h);
            }
            String c = c();
            if (!TextUtils.isEmpty(c)) {
                jSONObject.put("serial", c);
            }
            String j = j(context);
            if (!TextUtils.isEmpty(j)) {
                jSONObject.put("androidid", j);
            }
            String e2 = e();
            if (!TextUtils.isEmpty(e2)) {
                jSONObject.put("cpu", e2);
            }
            String f2 = f();
            if (!TextUtils.isEmpty(f2)) {
                jSONObject.put(Constants.KEY_MODEL, f2);
            }
            String g2 = g();
            if (!TextUtils.isEmpty(g2)) {
                jSONObject.put("sdcard", g2);
            }
            String k = k(context);
            if (!TextUtils.isEmpty(k)) {
                jSONObject.put(CaptureParam.CAPTURE_PICTURE_SIZE, k);
            }
            String l = l(context);
            if (!TextUtils.isEmpty(l)) {
                jSONObject.put("ssid", l);
            }
            String m = m(context);
            if (!TextUtils.isEmpty(m)) {
                jSONObject.put("bssid", m);
            }
            String h2 = h();
            if (!TextUtils.isEmpty(h2)) {
                jSONObject.put("deviceName", h2);
            }
            String n = n(context);
            if (!TextUtils.isEmpty(n)) {
                jSONObject.put("connecttype", n);
            }
            String str = "";
            try {
                str = b(context);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put(com.ali.auth.third.core.model.Constants.UA, str);
            }
            double i = i(context);
            jSONObject.put("batterymaxcapacity", String.valueOf(i));
            jSONObject.put("batterycurrentcapacity", String.valueOf(i));
            a aVar = new a(context, 0);
            jSONObject.put("batterycurrentvoltage", aVar.a.getIntExtra("voltage", 0));
            jSONObject.put("batterycurrenttemperature", aVar.a.getIntExtra("temperature", 0));
            jSONObject.put("batterycurrentcapacity", (i * ((double) aVar.a.getIntExtra(H5PermissionManager.level, 0))) / ((double) aVar.a.getIntExtra(WidgetType.SCALE, 0)));
            return jSONObject.toString();
        } catch (JSONException unused) {
            return "";
        }
    }

    private static String a() {
        try {
            StringBuilder sb = new StringBuilder("Android ");
            sb.append(VERSION.RELEASE);
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    private static String d(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception unused) {
            return "";
        }
    }

    private static String e(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (Exception unused) {
            return "";
        }
    }

    private static String f(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        } catch (Exception unused) {
            return "";
        }
    }

    private static String b() {
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
        } catch (Exception unused) {
        }
        return "";
    }

    private static String g(Context context) {
        if (VERSION.SDK_INT >= 23) {
            return b();
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null) {
                return "";
            }
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            return connectionInfo != null ? connectionInfo.getMacAddress() : "";
        } catch (Exception unused) {
            return "";
        }
    }

    private static String h(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
        } catch (Exception unused) {
            return "";
        }
    }

    private static String c() {
        String str = "";
        if (VERSION.SDK_INT >= 26) {
            return d();
        }
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            str = (String) cls.getMethod("get", new Class[]{String.class, String.class}).invoke(cls, new Object[]{"ro.serialno", "unknown"});
        } catch (Exception unused) {
        }
        return str;
    }

    private static double i(Context context) {
        Object obj;
        try {
            obj = Class.forName("com.android.internal.os.PowerProfile").getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context});
        } catch (Exception unused) {
            obj = null;
        }
        try {
            return ((Double) Class.forName("com.android.internal.os.PowerProfile").getMethod("getAveragePower", new Class[]{String.class}).invoke(obj, new Object[]{"battery.capacity"})).doubleValue();
        } catch (Exception unused2) {
            return 0.0d;
        }
    }

    @TargetApi(26)
    private static String d() {
        try {
            return Build.getSerial();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String j(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception unused) {
            return "";
        }
    }

    private static String e() {
        try {
            return Build.CPU_ABI;
        } catch (Exception unused) {
            return "";
        }
    }

    private static String f() {
        try {
            return Build.MODEL;
        } catch (Exception unused) {
            return "";
        }
    }

    private static String g() {
        try {
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            return Long.toString(((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()));
        } catch (Exception unused) {
            return "";
        }
    }

    private static String k(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService(TemplateTinyApp.WINDOW_KEY)).getDefaultDisplay().getMetrics(displayMetrics);
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(displayMetrics.widthPixels));
            sb.append("*");
            sb.append(String.valueOf(displayMetrics.heightPixels));
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    private static String l(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getSSID();
            }
        } catch (Exception unused) {
        }
        return "";
    }

    private static String m(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getApplicationContext().getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getBSSID();
            }
        } catch (SecurityException unused) {
        }
        return "";
    }

    private static String h() {
        try {
            return Build.BRAND;
        } catch (Exception unused) {
            return "";
        }
    }

    private static String n(Context context) {
        String str = com.autonavi.minimap.ajx3.util.Constants.ANIMATOR_NONE;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.getType() == 0) {
                    switch (activeNetworkInfo.getSubtype()) {
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
                            return com.autonavi.minimap.ajx3.util.Constants.ANIMATOR_NONE;
                    }
                } else if (activeNetworkInfo.getType() == 1) {
                    str = "wifi";
                }
            }
        } catch (Exception unused) {
        }
        return str;
    }
}
