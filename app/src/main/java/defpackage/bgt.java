package defpackage;

import com.amap.bundle.mapstorage.MapSharePreference;

/* renamed from: bgt reason: default package */
/* compiled from: VuiSp */
public final class bgt {
    private static MapSharePreference a = new MapSharePreference((String) "SharedPreferences");

    public static int a(String str, int i) {
        return a.getIntValue(str, i);
    }

    public static String a(String str, String str2) {
        return a.getStringValue(str, str2);
    }

    public static boolean a(String str) {
        return a.contains(str);
    }

    public static void b(String str, int i) {
        a.putIntValue(str, i);
    }

    public static void b(String str, String str2) {
        a.putStringValue(str, str2);
    }
}
