package defpackage;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/* renamed from: agm reason: default package */
/* compiled from: DeviceUtil */
public final class agm {
    public static String a(@NonNull Context context) {
        try {
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") == 0) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    return telephonyManager.getDeviceId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String b(@NonNull Context context) {
        try {
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") == 0) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    return telephonyManager.getSubscriberId();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String c(@NonNull Context context) {
        String string = Secure.getString(context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        return (TextUtils.equals(string, "9774d56d682e549c") || TextUtils.equals(string, "9774d56d682e549c") || TextUtils.equals(string, "9774d56d682e549c") || TextUtils.equals(string, "9774d56d682e549c")) ? "" : string;
    }

    public static String a() {
        if (VERSION.SDK_INT >= 29) {
            return "";
        }
        if (VERSION.SDK_INT >= 26) {
            try {
                return (String) Class.forName("android.os.Build").getMethod("getSerial", new Class[0]).invoke(null, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if ("unknown".equalsIgnoreCase(Build.SERIAL)) {
            return "";
        }
        return Build.SERIAL;
    }

    public static String b() {
        String str = "0000000000000000";
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("cat /proc/cpuinfo").getInputStream()));
            int i = 1;
            while (true) {
                if (i >= 100) {
                    break;
                }
                String readLine = lineNumberReader.readLine();
                if (readLine == null) {
                    break;
                } else if (readLine.indexOf("Serial") >= 0) {
                    str = readLine.substring(readLine.indexOf(":") + 1, readLine.length()).trim();
                    break;
                } else {
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TextUtils.equals(str, "0000000000000000") ? "" : str;
    }
}
