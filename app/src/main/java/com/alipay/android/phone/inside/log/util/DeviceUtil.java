package com.alipay.android.phone.inside.log.util;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import java.util.Locale;

public class DeviceUtil {
    private static String a;
    private static String b;
    private static String c;

    public static String a() {
        return "android";
    }

    public static String h() {
        return "0";
    }

    public static String b() {
        return Build.MANUFACTURER;
    }

    public static String c() {
        return Build.MODEL;
    }

    public static String d() {
        return VERSION.RELEASE;
    }

    public static String e() {
        return Locale.getDefault().getLanguage();
    }

    public static String f() {
        return b;
    }

    public static String g() {
        return c;
    }

    public static String i() {
        return a;
    }

    public static String j() {
        return Build.SERIAL;
    }

    public static void a(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            Integer valueOf = Integer.valueOf(displayMetrics.widthPixels);
            Integer valueOf2 = Integer.valueOf(displayMetrics.heightPixels);
            Integer valueOf3 = Integer.valueOf(displayMetrics.densityDpi);
            StringBuilder sb = new StringBuilder();
            sb.append(valueOf);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(valueOf2);
            sb.append(MergeUtil.SEPARATOR_KV);
            sb.append(valueOf3);
            a = sb.toString();
        } catch (Throwable unused) {
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            b = telephonyManager.getDeviceId();
            c = telephonyManager.getSubscriberId();
        } catch (Exception unused2) {
        } catch (Throwable unused3) {
        }
    }
}
