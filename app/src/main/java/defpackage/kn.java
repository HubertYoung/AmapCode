package defpackage;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.server.aos.serverkey;

/* renamed from: kn reason: default package */
/* compiled from: UserIdentifierTool */
public final class kn {
    public static String a(Context context, boolean z) {
        String a = a(context, z ? "amap_device_id_old" : "amap_device_id");
        return (TextUtils.isEmpty(a) || TextUtils.equals("000000000000000", a)) ? "" : a;
    }

    private static String a(@NonNull Context context, @NonNull String str) {
        String str2 = "";
        try {
            String string = System.getString(context.getContentResolver(), str);
            if (!TextUtils.isEmpty(string)) {
                String amapDecode = serverkey.amapDecode(string);
                try {
                    if (!TextUtils.isEmpty(amapDecode)) {
                        return amapDecode;
                    }
                } catch (Exception unused) {
                }
                str2 = amapDecode;
            }
        } catch (Exception unused2) {
        }
        String string2 = new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString(str, "");
        if (!TextUtils.isEmpty(string2)) {
            str2 = serverkey.amapDecode(string2);
            if (!TextUtils.isEmpty(str2)) {
                a(context, str, string2);
                return str2;
            }
        }
        return str2;
    }

    private static void a(@NonNull Context context, String str, String str2) {
        try {
            if (VERSION.SDK_INT < 23) {
                System.putString(context.getContentResolver(), str, str2);
            } else if (System.canWrite(context)) {
                System.putString(context.getContentResolver(), str, str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(Context context, String str, boolean z) {
        a(context, z ? "amap_device_id_old" : "amap_device_id", str, true);
    }

    private static void a(Context context, String str, String str2, boolean z) {
        if (!TextUtils.isEmpty(str2)) {
            if (z) {
                str2 = serverkey.amapEncode(str2);
                a(context, str, str2);
            }
            new MapSharePreference((String) "SharedPreferences").putStringValue(str, str2);
        }
    }

    public static void b(Context context, String str, boolean z) {
        a(context, z ? "amap_device_model_old" : "amap_device_model", str, false);
    }

    public static void c(Context context, String str, boolean z) {
        a(context, z ? "amap_android_id_old" : "amap_android_id", str, false);
    }

    public static String a(boolean z) {
        return new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString(z ? "amap_android_id_old" : "amap_android_id", "");
    }

    public static String b(boolean z) {
        return new MapSharePreference((String) "SharedPreferences").sharedPrefs().getString(z ? "amap_device_model_old" : "amap_device_model", "");
    }

    public static String b(@NonNull Context context) {
        String a = a(false);
        String c = agm.c(context);
        if (TextUtils.isEmpty(c)) {
            return a;
        }
        if (!TextUtils.isEmpty(a) && !a.equalsIgnoreCase(c)) {
            c(context, a, true);
        }
        c(context, c, false);
        return c;
    }

    public static String c(@NonNull Context context) {
        String b = b(false);
        String str = Build.MODEL;
        if (TextUtils.isEmpty(str)) {
            return b;
        }
        if (!TextUtils.isEmpty(b) && !b.equalsIgnoreCase(str)) {
            b(context, b, true);
        }
        b(context, str, false);
        return str;
    }

    public static String a(@NonNull Context context) {
        String a = a(context, false);
        String a2 = agm.a(context);
        if (TextUtils.isEmpty(a2) || TextUtils.equals("000000000000000", a2)) {
            return a;
        }
        if (!TextUtils.isEmpty(a) && !TextUtils.equals("000000000000000", a) && !a.equalsIgnoreCase(a2)) {
            a(context, a, true);
        }
        a(context, a2, false);
        return a2;
    }
}
