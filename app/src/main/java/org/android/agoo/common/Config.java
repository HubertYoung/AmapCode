package org.android.agoo.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.utl.ALog;

public class Config {
    public static String a;
    private static String b;
    private static String c;

    public static void setAgooAppKey(Context context, String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                ALog.e("Config", "setAgooAppKey appkey null", new Object[0]);
                return;
            }
            b = str;
            Editor edit = context.getSharedPreferences("Agoo_AppStore", 4).edit();
            edit.putString("agoo_app_key", str);
            edit.apply();
            ALog.d("Config", "setAgooAppKey", "appkey", str);
        } catch (Throwable th) {
            ALog.e("Config", "setAgooAppKey", th, new Object[0]);
        }
    }

    public static String a(Context context) {
        String str;
        String str2 = b;
        try {
            str = context.getSharedPreferences("Agoo_AppStore", 4).getString("agoo_app_key", b);
        } catch (Throwable th) {
            ALog.e("Config", "getAgooAppKey", th, new Object[0]);
            str = str2;
        }
        if (TextUtils.isEmpty(str)) {
            ALog.e("Config", "getAgooAppKey null!!", new Object[0]);
        }
        ALog.d("Config", "getAgooAppKey", "appkey", str);
        return str;
    }

    public static String b(Context context) {
        if (TextUtils.isEmpty(a)) {
            return ACCSManager.getDefaultConfig(context);
        }
        return a;
    }

    public static void a(Context context, String str) {
        ALog.i("Config", "setDeviceToken", "token", str);
        if (!TextUtils.isEmpty(str)) {
            c = str;
            try {
                Editor edit = context.getSharedPreferences("Agoo_AppStore", 4).edit();
                edit.putString("deviceId", str);
                edit.apply();
            } catch (Throwable th) {
                ALog.e("Config", "setDeviceToken", th, new Object[0]);
            }
        }
    }

    public static String h(Context context) {
        String str;
        String str2 = c;
        try {
            str = context.getSharedPreferences("Agoo_AppStore", 4).getString("deviceId", c);
        } catch (Throwable th) {
            ALog.e("Config", "getDeviceToken", th, new Object[0]);
            str = str2;
        }
        ALog.i("Config", "getDeviceToken", "token", str);
        return str;
    }

    public static void c(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("Agoo_AppStore", 4);
            Editor edit = sharedPreferences.edit();
            edit.putInt("agoo_UnReport_times", sharedPreferences.getInt("agoo_UnReport_times", 0) + 1);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    public static boolean d(Context context) {
        try {
            if (context.getSharedPreferences("Agoo_AppStore", 4).getInt("agoo_UnReport_times", 0) > 0) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static void e(Context context) {
        try {
            Editor edit = context.getSharedPreferences("Agoo_AppStore", 4).edit();
            edit.putInt("agoo_UnReport_times", 0);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    public static int f(Context context) {
        try {
            return context.getSharedPreferences("Agoo_AppStore", 4).getInt("agoo_UnReport_times", 0);
        } catch (Throwable unused) {
            return 0;
        }
    }

    public static void a(Context context, long j) {
        try {
            Editor edit = context.getSharedPreferences("Agoo_AppStore", 4).edit();
            edit.putLong("agoo_clear_time", j);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    public static boolean b(Context context, long j) {
        try {
            long j2 = context.getSharedPreferences("Agoo_AppStore", 4).getLong("agoo_clear_time", 0);
            StringBuilder sb = new StringBuilder("now=");
            sb.append(j);
            sb.append(",now - lastTime=");
            long j3 = j - j2;
            sb.append(j3);
            sb.append(",istrue=");
            int i = (j3 > 86400000 ? 1 : (j3 == 86400000 ? 0 : -1));
            sb.append(i > 0);
            ALog.d("isClearTime", sb.toString(), new Object[0]);
            if (j == 0 || i <= 0) {
                return false;
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static void a(Context context, boolean z) {
        try {
            Editor edit = context.getSharedPreferences("Agoo_AppStore", 4).edit();
            edit.putBoolean("agoo_enable_daemonserver", z);
            edit.apply();
        } catch (Throwable unused) {
        }
    }

    public static boolean g(Context context) {
        try {
            return context.getSharedPreferences("Agoo_AppStore", 4).getBoolean("agoo_enable_daemonserver", true);
        } catch (Throwable unused) {
            return false;
        }
    }

    public static String i(Context context) {
        try {
            return context.getSharedPreferences("Agoo_AppStore", 4).getString("app_push_user_token", "");
        } catch (Throwable unused) {
            return null;
        }
    }

    public static void b(Context context, String str) {
        try {
            Editor edit = context.getSharedPreferences("Agoo_AppStore", 4).edit();
            if (!TextUtils.isEmpty(str)) {
                edit.putString("app_push_user_token", str);
            }
            edit.apply();
        } catch (Throwable unused) {
        }
    }
}
